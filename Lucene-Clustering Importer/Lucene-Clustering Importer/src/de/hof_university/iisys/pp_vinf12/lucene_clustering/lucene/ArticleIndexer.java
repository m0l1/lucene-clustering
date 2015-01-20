package de.hof_university.iisys.pp_vinf12.lucene_clustering.lucene;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;

import de.hof_university.iisys.pp_vinf12.lucene_clustering.data.Article;


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
		
//		BooleanQuery query = new BooleanQuery();
//		query.add(NumericRangeQuery.newLongRange("articleIDLSB", article.getArticleID().getLeastSignificantBits(), article.getArticleID().getLeastSignificantBits(), true, true), BooleanClause.Occur.MUST);
//		query.add(NumericRangeQuery.newLongRange("articleIDMSB", article.getArticleID().getMostSignificantBits(), article.getArticleID().getMostSignificantBits(), true, true), BooleanClause.Occur.MUST);
		Query query = new TermQuery(new Term("articleID", article.getArticleID().toString()));
		TopDocs docs = searcher.search(query, 1);
		
		if (docs.totalHits == 1 && 
//				searcher.doc(docs.scoreDocs[0].doc).getField("articleIDLSB").numericValue().longValue() == article.getArticleID().getLeastSignificantBits() &&
//				searcher.doc(docs.scoreDocs[0].doc).getField("articleIDMSB").numericValue().longValue() == article.getArticleID().getMostSignificantBits()) {
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
//		doc.add(new LongField("articleIDLSB", article.getArticleID().getLeastSignificantBits(), Field.Store.YES));
//		doc.add(new LongField("articleIDMSB", article.getArticleID().getMostSignificantBits(), Field.Store.YES));
		doc.add(new TextField("articleID", article.getArticleID().toString(), Field.Store.YES));
		doc.add(new TextField("title", article.getTitle(), Field.Store.YES));
		doc.add(new TextField("description", article.getDescription(), Field.Store.YES));
		doc.add(new TextField("source", article.getSource(), Field.Store.YES));
		doc.add(new TextField("language", article.getLanguage(), Field.Store.YES));
		doc.add(new TextField("text", article.getText(), Field.Store.YES));
		doc.add(new TextField("logo", article.getLogo(), Field.Store.YES));
		doc.add(new TextField("link", article.getLink(), Field.Store.YES));
		doc.add(new TextField("description", article.getDescription(), Field.Store.YES));
		doc.add(new TextField("date", new SimpleDateFormat
				("yyyy/MM/dd HH:mm:ss").format(article.getDate().getTime()), Field.Store.YES));
//		doc.add(new LongField("clusterIDLSB", article.getClusterID().getLeastSignificantBits(), Field.Store.YES));
//		doc.add(new LongField("clusterIDMSB", article.getClusterID().getMostSignificantBits(), Field.Store.YES));
		doc.add(new TextField("clusterID", article.getClusterID().toString(), Field.Store.YES));
		
		return doc;
	
	}
}
