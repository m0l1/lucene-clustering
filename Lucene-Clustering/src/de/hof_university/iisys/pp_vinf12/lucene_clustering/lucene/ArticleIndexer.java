package de.hof_university.iisys.pp_vinf12.lucene_clustering.lucene;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

import de.hof_university.iisys.pp_vinf12.lucene_clustering.data.Article;


public class ArticleIndexer {
	
	private String path = "";
	
	
	public ArticleIndexer(String path){
		this.path = path;
	}
	
	
	public void writeArticlesToIndex(List<Article> articles) throws IOException{
		
		SimpleFSDirectory dir = new SimpleFSDirectory(new File(path));
		
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_45);
		
		IndexWriter writer = new IndexWriter(dir, new IndexWriterConfig(Version.LUCENE_45, analyzer));
		
		for (Article article : articles) {
			Document doc = buildDoc(article);
			writer.addDocument(doc);
		}
		
		
		writer.close();
	}
	
	private Document buildDoc(Article article){
		

		Document doc = new Document();
		doc.add(new TextField("title", article.getTitle(), Field.Store.YES));
		doc.add(new TextField("description", article.getDescription(), Field.Store.YES));
		doc.add(new TextField("source", article.getSource(), Field.Store.YES));
		doc.add(new TextField("language", article.getLanguage(), Field.Store.YES));
		doc.add(new TextField("text", article.getText(), Field.Store.YES));
		doc.add(new TextField("logo", article.getLogo(), Field.Store.YES));
		doc.add(new TextField("link", article.getLink(), Field.Store.YES));
		doc.add(new TextField("description", article.getDescription(), Field.Store.YES));
		doc.add(new TextField("date", new SimpleDateFormat
				("yyyy.MM.dd.HH.mm.ss").format(article.getDate()), Field.Store.YES));
		doc.add(new IntField("clusterID", article.getClusterID(), Field.Store.YES));
		
		return doc;

		
	}
}
