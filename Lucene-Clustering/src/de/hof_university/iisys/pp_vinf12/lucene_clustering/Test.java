package de.hof_university.iisys.pp_vinf12.lucene_clustering;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hof_university.iisys.pp_vinf12.lucene_clustering.data.Article;
import de.hof_university.iisys.pp_vinf12.lucene_clustering.lucene.ArticleIndexer;
import de.hof_university.iisys.pp_vinf12.lucene_clustering.xml.FileIterator;
import de.hof_university.iisys.pp_vinf12.lucene_clustering.xml.XMLParser;

public class Test {
	
	public static void main(String[] args) {
		FileIterator it = new FileIterator("");
		XMLParser parser = new XMLParser();
		ArticleIndexer indexer = new ArticleIndexer("lucene.index");
		
		List<Article> articles = new ArrayList();
		List<String> files = it.getFileList();
		
		for(String file : files) {
			Article article = parser.parse(file);
			articles.add(article);		
		}
		
		try {
			indexer.writeArticlesToIndex(articles);
		} catch (IOException e) {
			System.err.println("Indexen fehlgeschlagen \n äh jetzt fertig\n");
			System.err.println(e.getMessage());
			
			
		}
		
		
		
	}

}
