package de.hof_university.iisys.pp_vinf12.lucene_clustering.gui.lucene;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.SimpleFSDirectory;

import de.hof_university.iisys.pp_vinf12.lucene_clustering.gui.data.Article;
import de.hof_university.iisys.pp_vinf12.lucene_clustering.gui.data.ArticleComparator;
import de.hof_university.iisys.pp_vinf12.lucene_clustering.gui.data.Cluster;

public class ClusterAssembler {
	
	private String clusterIndex;
	private ArticleAssembler articleAssembler;
	
	public ClusterAssembler(String articleIndex, String clusterIndex) {
		this.clusterIndex = clusterIndex;
		this.articleAssembler = new ArticleAssembler(articleIndex);
	}
	
	public List<Cluster> getAllClusters() throws IOException, ParseException {
		
		List<Cluster> clusters = new ArrayList<Cluster>();
		
		SimpleFSDirectory clusterDir = new SimpleFSDirectory(new File(clusterIndex));
		DirectoryReader clusterReader = DirectoryReader.open(clusterDir);
		IndexSearcher clusterSearcher = new IndexSearcher(clusterReader);
		clusterSearcher.setSimilarity(new BM25Similarity());
		
		Query clusterQuery = new MatchAllDocsQuery();
		ScoreDoc[] clusterDocs = clusterSearcher.search(clusterQuery, clusterReader.numDocs()).scoreDocs;
		
		for (ScoreDoc clusterDoc : clusterDocs) {
			Document doc = clusterSearcher.doc(clusterDoc.doc);
			clusters.add(buildCluster(doc));		
		}
		
		return clusters;
	}
	
	private Cluster buildCluster(Document doc) throws ParseException, IOException {
		Cluster cluster = new Cluster();
		cluster.setClusterId(UUID.fromString(doc.getField("clusterID").stringValue()));
		cluster.setCreated(parseDate(doc.getField("created").stringValue()));
		cluster.setLastChange(parseDate(doc.getField("lastChange").stringValue()));
		cluster.setTopArticle(articleAssembler.getArticle(doc.getField("topArticleID").stringValue()));
		List<Article> articles = articleAssembler.getArticles(
				doc.getField("clusterID").stringValue(),
				doc.getField("topArticleID").stringValue());
		articles.sort(new ArticleComparator());
		cluster.setArticles(articles);
		
		return cluster;
	}
	
	public List<Cluster> getIdentityClusters() throws ParseException, IOException {
		
		List<Cluster> clusters = new ArrayList<Cluster>();
		List<Article> topArticles = articleAssembler.getIdentityTopArticles();
		
		for (Article topArticle : topArticles) {
			Cluster cluster = buildIdentityCluster(topArticle);
			if (cluster != null) {
				clusters.add(cluster);
			}
		}
		
		return clusters;
	}
	
	private Cluster buildIdentityCluster(Article article) throws ParseException, IOException {
		
		Cluster cluster = null;
		List<Article> articles = articleAssembler.getIdentityArticles(
				article.getArticleID().toString());
		articles.sort(new ArticleComparator());
		
		if (articles.size() > 0) {
			cluster = new Cluster();
			cluster.setTopArticle(article);
			cluster.setClusterId(article.getArticleID());
			cluster.setCreated(article.getDate());
			cluster.setLastChange(articles.get(0).getDate());
			cluster.setArticles(articles);
		}

		return cluster;
	}

	private GregorianCalendar parseDate(String dateString) throws ParseException {
		GregorianCalendar calendar = new GregorianCalendar();
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		date = simpleDateFormat.parse(dateString);
		calendar.setTime(date);
		return calendar;
	}
}
