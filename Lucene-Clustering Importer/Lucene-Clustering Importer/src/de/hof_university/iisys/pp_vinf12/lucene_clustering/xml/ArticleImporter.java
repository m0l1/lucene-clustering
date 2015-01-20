package de.hof_university.iisys.pp_vinf12.lucene_clustering.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import de.hof_university.iisys.pp_vinf12.lucene_clustering.data.Article;

public class ArticleImporter {
	
	private String subscriptionDir;
	
	public ArticleImporter(String subscriptionDir) {
		this.subscriptionDir = subscriptionDir;
	}
	
	public List<Article> importArticles() throws FileNotFoundException {
		
		RecursiveFileLister it = new RecursiveFileLister(subscriptionDir);
		
		XMLParser parser = new XMLParser();
		FileLineLister puller = new FileLineLister();
		
		List<Article> articles = new ArrayList<Article>();
		List<String> xmlFiles = new ArrayList<String>();
		
		List<String> notificationFiles = it.getFileList();
		
		for(String file : notificationFiles) {
			xmlFiles.addAll(puller.listXMLFilesInFile(file));
		}
		
		for(String file : xmlFiles) {
			Article article = parser.parse(file);
			articles.add(article);		
		}
		
		return articles;
	}
}
