package de.hof_university.iisys.pp_vinf12.lucene_clustering.gui.lucene;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.SimpleFSDirectory;

import de.hof_university.iisys.pp_vinf12.lucene_clustering.gui.data.Article;

public class ArticleAssembler {

	private String articleIndex;
	
	public ArticleAssembler(String articleIndex) {
		this.articleIndex = articleIndex;
	}

	public Article getArticle(String articleID) throws IOException, ParseException {
		
		SimpleFSDirectory articleDir = new SimpleFSDirectory(new File(articleIndex));
		DirectoryReader articleReader = DirectoryReader.open(articleDir);
		IndexSearcher articleSearcher = new IndexSearcher(articleReader);
		articleSearcher.setSimilarity(new BM25Similarity());
		
		Query articleQuery = TermRangeQuery.newStringRange("articleID", articleID, articleID, true, true);
		ScoreDoc[] articleDocs = articleSearcher.search(articleQuery, 1).scoreDocs;
		
		Document doc = articleSearcher.doc(articleDocs[0].doc);
		
		return buildArticle(doc);
	}
	
	public Article getArticle(Document doc) throws ParseException {
		return buildArticle(doc);
	}

	public List<Article> getArticles(String clusterID, String topArticleID) throws IOException, ParseException {
		List<Article> articles = new ArrayList<Article>();
		
		SimpleFSDirectory articleDir = new SimpleFSDirectory(new File(articleIndex));
		DirectoryReader articleReader = DirectoryReader.open(articleDir);
		IndexSearcher articleSearcher = new IndexSearcher(articleReader);
		articleSearcher.setSimilarity(new BM25Similarity());
		
		Query articleQuery = new TermQuery(new Term("clusterID", clusterID));
		ScoreDoc[] articleDocs = articleSearcher.search(articleQuery, articleReader.numDocs()).scoreDocs;
		
		for (ScoreDoc articleDoc : articleDocs) {
			Document doc = articleSearcher.doc(articleDoc.doc);
			
			// Zur Liste hinzufuegen, ausser wenn es der
			// TopArticle mit der uebergebenen topArticleID ist
			if (!doc.getField("articleID").stringValue().equals(topArticleID)) {
				articles.add(buildArticle(doc));
			}
		}
		
		return articles;
	}

	private Article buildArticle(Document doc) throws ParseException {
		Article article = new Article();
		
		article.setArticleID(UUID.fromString(doc.getField("articleID").stringValue()));
		article.setTitle(doc.getField("title").stringValue());
		article.setDescription(doc.getField("description").stringValue());
		article.setSource(doc.getField("source").stringValue());
		article.setLanguage(doc.getField("language").stringValue());
		article.setLogo(doc.getField("logo").stringValue());
		article.setLink(doc.getField("link").stringValue());
		article.setDescription(doc.getField("description").stringValue());
		article.setDate(parseDate(doc.getField("date").stringValue()));
		
		return article;
	}

	private GregorianCalendar parseDate(String dateString) throws ParseException {
		GregorianCalendar calendar = new GregorianCalendar();
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		date = simpleDateFormat.parse(dateString);
		calendar.setTime(date);
		return calendar;
	}

	public List<Article> getIdentityArticles(String articleID) throws IOException, ParseException {
		
		List<Article> articles = new ArrayList<Article>();
		
		SimpleFSDirectory articleDir = new SimpleFSDirectory(new File(articleIndex));
		DirectoryReader articleReader = DirectoryReader.open(articleDir);
		IndexSearcher articleSearcher = new IndexSearcher(articleReader);
		articleSearcher.setSimilarity(new BM25Similarity());
		
		Query articleQuery = new TermQuery(new Term("similarity", articleID));
		ScoreDoc[] articleDocs = articleSearcher.search(articleQuery, articleReader.numDocs()).scoreDocs;
		
		for (ScoreDoc articleDoc : articleDocs) {
			Document doc = articleSearcher.doc(articleDoc.doc);
			articles.add(buildArticle(doc));
		}
		
		return articles;
	}

	public List<Article> getIdentityTopArticles() throws IOException, ParseException {
		
		Set<String> ids = new HashSet<String>();
		List<Article> articles = new ArrayList<Article>();
		
		SimpleFSDirectory articleDir = new SimpleFSDirectory(new File(articleIndex));
		DirectoryReader articleReader = DirectoryReader.open(articleDir);
		IndexSearcher articleSearcher = new IndexSearcher(articleReader);
		articleSearcher.setSimilarity(new BM25Similarity());
		
		Query articleQuery = new MatchAllDocsQuery();
		ScoreDoc[] articleDocs = articleSearcher.search(articleQuery, articleReader.numDocs()).scoreDocs;
		
		for (ScoreDoc articleDoc : articleDocs) {
			Document doc = articleSearcher.doc(articleDoc.doc);
			String id = doc.getField("identical").stringValue();
			if (id != null && id.length() > 0) {
				ids.add(id);
			}
		}
		
		for (String id : ids) {
			articleQuery = new TermQuery(new Term("articleID", id));
			articleDocs = articleSearcher.search(articleQuery, 1).scoreDocs;
			
			for (ScoreDoc articleDoc : articleDocs) {
				Document doc = articleSearcher.doc(articleDoc.doc);
				articles.add(buildArticle(doc));
			}
		}
				
		return articles;
	}
}
