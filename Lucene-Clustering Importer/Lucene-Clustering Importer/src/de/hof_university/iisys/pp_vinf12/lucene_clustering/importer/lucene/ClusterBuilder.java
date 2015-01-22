package de.hof_university.iisys.pp_vinf12.lucene_clustering.importer.lucene;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queries.mlt.MoreLikeThis;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

import de.hof_university.iisys.pp_vinf12.lucene_clustering.importer.data.Article;
import de.hof_university.iisys.pp_vinf12.lucene_clustering.importer.data.Cluster;

public class ClusterBuilder {
	
	private String articleIndex;
	private String clusterIndex;
	private int toCompare;
	private float clusterScore;
	private float identityScore;
	
	public ClusterBuilder(String articleIndex, String clusterIndex, int toCompare, float clusterScore, float identityScore) {
		this.articleIndex = articleIndex;
		this.clusterIndex = clusterIndex;
		this.toCompare = toCompare;
		this.clusterScore = clusterScore;
		this.identityScore = identityScore;
	}

	public void buildClusters(List<Article> articles) throws IOException, ParseException {
		
		for (Article article : articles) {
			Analyzer analyzer = new GermanAnalyzer(Version.LUCENE_45);
			
			SimpleFSDirectory articleDir = new SimpleFSDirectory(new File(articleIndex));
			IndexWriter articleWriter = new IndexWriter(articleDir, new IndexWriterConfig(Version.LUCENE_45, analyzer));
			DirectoryReader articleReader = DirectoryReader.open(articleWriter, true);
			IndexSearcher articleSearcher = new IndexSearcher(articleReader);
			articleSearcher.setSimilarity(new BM25Similarity());
			
			ArticleIndexer articleIndexer = new ArticleIndexer(articleWriter, articleSearcher);
			
			SimpleFSDirectory clusterDir = new SimpleFSDirectory(new File(clusterIndex));
			IndexWriter clusterWriter = new IndexWriter(clusterDir, new IndexWriterConfig(Version.LUCENE_45, analyzer));
			DirectoryReader clusterReader = DirectoryReader.open(clusterWriter, true);
			IndexSearcher clusterSearcher = new IndexSearcher(clusterReader);
			clusterSearcher.setSimilarity(new BM25Similarity());
			
			ClusterIndexer clusterIndexer = new ClusterIndexer(clusterWriter, clusterSearcher);
			
			MoreLikeThis mlt = new MoreLikeThis(articleReader);
			mlt.setAnalyzer(analyzer);
			mlt.setFieldNames(new String[] {"text"});
			
			Query query = mlt.like(new StringReader(article.getText()), null);
			ScoreDoc[] docs = articleSearcher.search(query, toCompare).scoreDocs;
			
			ScoreDoc maxDoc = null;
			for (ScoreDoc scoreDoc : docs) {
				if (scoreDoc.score > clusterScore && (maxDoc == null || scoreDoc.score > maxDoc.score)) {
					maxDoc = scoreDoc;
				}
			}
			
			GregorianCalendar now = new GregorianCalendar();
			if (maxDoc == null) {
				UUID id = UUID.randomUUID();
				
				Cluster cluster = new Cluster();
				cluster.setClusterId(id);
				cluster.setTopArticleId(article.getArticleID());
				cluster.setCreated(now);
				cluster.setLastChange(article.getDate());
				
				article.setClusterID(id);
				
				clusterIndexer.writeClusterToIndex(cluster);
				articleIndexer.writeArticleToIndex(article);
			}
			else {
				Document articleDoc = articleSearcher.doc(maxDoc.doc);
				
				UUID id = UUID.fromString(articleDoc.getField("clusterID").stringValue());
				
				if (maxDoc.score > identityScore) {
					String identical = articleDoc.getField("identical").stringValue();
					if (identical != null && identical.length() > 0) {
						article.setIdentical(UUID.fromString(identical));
					}
					else {
						article.setIdentical(UUID.fromString(articleDoc.getField("articleID").stringValue()));
					}
					System.out.println("Identität!");
				}
				
				Query clusterQuery = TermRangeQuery.newStringRange("clusterID", articleDoc.getField("clusterID").stringValue(), articleDoc.getField("clusterID").stringValue(), true, true);
				TopDocs clusterDocs = clusterSearcher.search(clusterQuery, 5);
				
				Cluster cluster = null; 
				if (clusterDocs.totalHits >= 1 && 
						clusterSearcher.doc(clusterDocs.scoreDocs[0].doc).getField("clusterID").stringValue().equals(articleDoc.getField("clusterID").stringValue())) {
					
					Document clusterDoc = clusterSearcher.doc(clusterDocs.scoreDocs[0].doc);
					
					cluster = new Cluster();
					cluster.setClusterId(id);
					cluster.setTopArticleId(UUID.fromString(clusterDoc.getField("topArticleID").stringValue()));
					GregorianCalendar created = new GregorianCalendar();
					created.setTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(clusterDoc.getField("created").stringValue()));
					cluster.setCreated(created);
					cluster.setLastChange(article.getDate());
						
					clusterIndexer.updateCluster(cluster);
					
					article.setClusterID(id);
				}
				else {
					// TODO - was machen?!? -> passende Exception und loggen?
				}				
				
				articleIndexer.writeArticleToIndex(article);
			}
			
			clusterReader.close();
			clusterWriter.close();
			articleReader.close();
			articleWriter.close();
		}
	}
}
