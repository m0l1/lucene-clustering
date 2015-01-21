package de.hof_university.iisys.pp_vinf12.lucene_clustering.importer.lucene;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;

import de.hof_university.iisys.pp_vinf12.lucene_clustering.importer.data.Article;

public class ArticleIndexer {
	
	private IndexWriter writer;
	private IndexSearcher searcher;
	
	public ArticleIndexer(IndexWriter writer, IndexSearcher searcher){
		this.writer = writer;
		this.searcher = searcher;
	}
	
	public void writeArticleToIndex(Article article) throws IOException{
		
		Document doc = buildDoc(article);
		writer.addDocument(doc);
	}
	
	public void writeArticlesToIndex(List<Article> articles) throws IOException{
		
		for (Article article : articles) {
			Document doc = buildDoc(article);
			writer.addDocument(doc);
		}
	}
	
	public void updateArticle(Article article) throws IOException {
		
		Query query = TermRangeQuery.newStringRange("articleID", article.getArticleID().toString(), article.getArticleID().toString(), true, true);
		TopDocs docs = searcher.search(query, 1);
		
		if (docs.totalHits == 1 && 
				searcher.doc(docs.scoreDocs[0].doc).getField("articleID").stringValue().equals(article.getArticleID().toString())) {
			writer.deleteDocuments(query);
			writer.addDocument(buildDoc(article));
		}
		else {
			// TODO - was machen?!? -> passende Exception und loggen?
		}
	}
	
	private Document buildDoc(Article article){
		
		Document doc = new Document();
		doc.add(new StringField("articleID", article.getArticleID().toString(), Field.Store.YES));
		doc.add(new TextField("title", article.getTitle(), Field.Store.YES));
		doc.add(new TextField("description", article.getDescription(), Field.Store.YES));
		doc.add(new StringField("source", article.getSource(), Field.Store.YES));
		doc.add(new StringField("language", article.getLanguage(), Field.Store.YES));
		doc.add(new TextField("text", article.getText(), Field.Store.YES));
		doc.add(new StringField("logo", article.getLogo(), Field.Store.YES));
		doc.add(new StringField("link", article.getLink(), Field.Store.YES));
		doc.add(new TextField("description", article.getDescription(), Field.Store.YES));
		doc.add(new StringField("date", new SimpleDateFormat
				("yyyy/MM/dd HH:mm:ss").format(article.getDate().getTime()), Field.Store.YES));
		doc.add(new StringField("clusterID", article.getClusterID().toString(), Field.Store.YES));
		
		return doc;
	}
}
