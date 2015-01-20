package de.hof_university.iisys.pp_vinf12.lucene_clustering.lucene;

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
import org.apache.lucene.index.Term;
import org.apache.lucene.queries.mlt.MoreLikeThis;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

import de.hof_university.iisys.pp_vinf12.lucene_clustering.data.Article;
import de.hof_university.iisys.pp_vinf12.lucene_clustering.data.Cluster;

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
		
//		Analyzer analyzer = new GermanAnalyzer(Version.LUCENE_45);
//		
//		SimpleFSDirectory articleDir = new SimpleFSDirectory(new File(articleIndex));
//		IndexWriter articleWriter = new IndexWriter(articleDir, new IndexWriterConfig(Version.LUCENE_45, analyzer));
//		DirectoryReader articleReader = DirectoryReader.open(articleWriter, true);
//		IndexSearcher articleSearcher = new IndexSearcher(articleReader);
//		articleSearcher.setSimilarity(new BM25Similarity());
//		
//		ArticleIndexer articleIndexer = new ArticleIndexer(articleWriter, articleSearcher);
//		
//		SimpleFSDirectory clusterDir = new SimpleFSDirectory(new File(clusterIndex));
//		IndexWriter clusterWriter = new IndexWriter(clusterDir, new IndexWriterConfig(Version.LUCENE_45, analyzer));
//		DirectoryReader clusterReader = DirectoryReader.open(clusterWriter, true);
//		IndexSearcher clusterSearcher = new IndexSearcher(clusterReader);
//		clusterSearcher.setSimilarity(new BM25Similarity());
//		
//		ClusterIndexer clusterIndexer = new ClusterIndexer(clusterWriter, clusterSearcher);
//		
//		MoreLikeThis mlt = new MoreLikeThis(articleReader);
//		mlt.setAnalyzer(analyzer);
//		mlt.setFieldNames(new String[] {"text"});
		
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
			
			System.out.println("Query-Treffer: " + docs.length);
			
			ScoreDoc maxDoc = null;
			for (ScoreDoc scoreDoc : docs) {
				System.out.println(scoreDoc.score);
				if (scoreDoc.score > clusterScore && (maxDoc == null || scoreDoc.score > maxDoc.score)) {
					maxDoc = scoreDoc;
				}
			}
			
			GregorianCalendar now = new GregorianCalendar();
			if (maxDoc == null) {
				UUID id = UUID.randomUUID();
				
				System.out.println(" => neuer Cluster, id=" + id.toString());
				System.out.println("    msb " + id.getMostSignificantBits() + " / lsb " + id.getLeastSignificantBits());
				
				Cluster cluster = new Cluster();
				cluster.setClusterId(id);
				cluster.setTopArticleId(article.getArticleID());
				cluster.setCreated(now);
				cluster.setLastChange(now);
				
				article.setClusterID(id);
				
				clusterIndexer.writeClusterToIndex(cluster);
				clusterWriter.commit();
				articleIndexer.writeArticleToIndex(article);
				articleWriter.commit();
			}
			else {
				System.out.println(" => " + maxDoc.score);
				Document articleDoc = articleSearcher.doc(maxDoc.doc);
//				long msb = articleDoc.getField("clusterIDMSB").numericValue().longValue();
//				long lsb = articleDoc.getField("clusterIDLSB").numericValue().longValue();
//				UUID id = new UUID(msb, lsb);
				
				UUID id = UUID.fromString(articleDoc.getField("clusterID").stringValue());
				
//				BooleanQuery clusterQuery = new BooleanQuery();
//				clusterQuery.add(NumericRangeQuery.newLongRange("clusterIDLSB", lsb, lsb, true, true), BooleanClause.Occur.MUST);
//				clusterQuery.add(NumericRangeQuery.newLongRange("clusterIDMSB", msb, msb, true, true), BooleanClause.Occur.MUST);
				
				System.out.println("Query nach: " + articleDoc.getField("clusterID").stringValue());
//				Query clusterQuery = new TermQuery(new Term("clusterID", articleDoc.getField("clusterID").stringValue()));
				
//				TopDocs clusterDocs = clusterSearcher.search(clusterQuery, 1);
				MoreLikeThis clmlt = new MoreLikeThis(clusterReader);
				clmlt.setAnalyzer(analyzer);
				clmlt.setFieldNames(new String[] {"clusterID"});
				Query clquery = clmlt.like(new StringReader(article.getText()), null);
				TopDocs clusterDocs = clusterSearcher.search(clquery, 5);
				
//				System.out.println("scoreDocs size: " + clusterDocs.scoreDocs.length);
				System.out.println("scoreDocs size: " + clusterDocs.scoreDocs.length);
//				System.out.println("MSB ArticleHit: " + msb);
//				System.out.println("LSB ArticleHit: " + lsb);
//				System.out.println("MSB ClusterHit: " + clusterSearcher.doc(clusterDocs.scoreDocs[0].doc).getField("clusterIDMSB").numericValue().longValue());
//				System.out.println("LSB ClusterHit: " + clusterSearcher.doc(clusterDocs.scoreDocs[0].doc).getField("clusterIDLSB").numericValue().longValue());
				
				Cluster cluster = null; 
				if (clusterDocs.totalHits == 1 && 
//						clusterSearcher.doc(clusterDocs.scoreDocs[0].doc).getField("clusterIDLSB").numericValue().longValue() == lsb &&
//						clusterSearcher.doc(clusterDocs.scoreDocs[0].doc).getField("clusterIDMSB").numericValue().longValue() == msb) {
						clusterSearcher.doc(clusterDocs.scoreDocs[0].doc).getField("clusterID").stringValue().equals(articleDoc.getField("clusterID").stringValue())) {
					
					Document clusterDoc = clusterSearcher.doc(clusterDocs.scoreDocs[0].doc);
					
					cluster = new Cluster();
					cluster.setClusterId(id);
//					cluster.setTopArticleId(new UUID(
//							clusterDoc.getField("articleIDMSB").numericValue().longValue(),
//							clusterDoc.getField("articleIDLSB").numericValue().longValue()));
					cluster.setTopArticleId(UUID.fromString(clusterDoc.getField("articleID").stringValue()));
					GregorianCalendar created = new GregorianCalendar();
					created.setTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(clusterDoc.getField("created").stringValue()));
					cluster.setCreated(created);
					cluster.setLastChange(now);
						
					clusterIndexer.writeClusterToIndex(cluster);
					clusterWriter.commit();
					
					System.out.println("ClusterID in ClusterBuilder: " + id.toString());
					article.setClusterID(id);
				}
				else {
					System.out.println("FEHLER: Cluster nicht gefunden");
					// TODO - was machen?!? -> passende Exception und loggen?
				}				
				
				articleIndexer.writeArticleToIndex(article);
				articleWriter.commit();
			}
			
			clusterReader.close();
			clusterWriter.close();
			articleReader.close();
			articleWriter.close();
		}
		
//		clusterReader.close();
//		clusterWriter.close();
//		articleReader.close();
//		articleWriter.close();
	}
}
