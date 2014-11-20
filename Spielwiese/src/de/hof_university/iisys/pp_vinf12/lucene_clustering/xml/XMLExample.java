package de.hof_university.iisys.pp_vinf12.lucene_clustering.xml;
import java.util.Map;

import de.hof_university.iisys.pp_vinf12.lucene_clustering.data.Article;

public class XMLExample {
	public static void main(String[] args) {
		XMLParser xml = new XMLParser();
		Article testArticle = xml.parse("H:/RSS622323681.xml");
		System.out.println("Description: " + testArticle.getDescription());
		System.out.println("Language: " + testArticle.getLanguage());
		System.out.println("Link: " + testArticle.getLink());
		System.out.println("Logo: " + testArticle.getLogo());
		System.out.println("Source: " + testArticle.getSource());
		System.out.println("Text: " + testArticle.getText());
		System.out.println("Title: " + testArticle.getTitle());
		System.out.println("Date: " + testArticle.getDate().getTime());
		
	}
}
