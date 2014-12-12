package de.hof_university.iisys.pp_vinf12.lucene_clustering.spielwiese;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.GregorianCalendar;

import de.hof_university.iisys.pp_vinf12.lucene_clustering.data.Article;

public class IndexReader {
	// Konstruktor
	
	// getListofArticles, Liste von Artikeln an Max List<Article>
	// 
	
	//
	
	
	
	private static Article parseArticle(Article article) throws IOException, ClassNotFoundException {

		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("object.data"));

		String title = (String) ois.readObject();
		String description = (String) ois.readObject();
		String source = (String) ois.readObject();
		String language = (String) ois.readObject();
		String text = (String) ois.readObject();
		String logo = (String) ois.readObject();
		String link = (String) ois.readObject();
		GregorianCalendar date =  (GregorianCalendar) ois.readObject();
		
		Article ret = new Article(title, description, source, language, text, logo, link, date);
		return ret;

		
		
	}

}
