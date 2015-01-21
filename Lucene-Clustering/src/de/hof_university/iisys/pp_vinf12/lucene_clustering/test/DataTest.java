//package de.hof_university.iisys.pp_vinf12.lucene_clustering.test;
//
//import java.util.*;
//
//import de.hof_university.iisys.pp_vinf12.lucene_clustering.gui.data.*;
//import de.hof_university.iisys.pp_vinf12.lucene_clustering.gui.lucene.BeanData;
//import de.hof_university.iisys.pp_vinf12.lucene_clustering.xml.XMLParser;
//
////@author mlouis doeckl
//
//public class DataTest implements BeanData {
//	public List<Cluster> getClusterList() {
//		//Clusterliste und Cluster vorbereiten
//		List<Cluster> clusterList  = new ArrayList<Cluster>();
//		Cluster c1 = new Cluster();
//		Cluster c2 = new Cluster();	
//		//xml parser anlegen
//		XMLParser parser = new XMLParser();
//		// Artikel anlegen und in eine Liste hinzufügen
//		Article a1 = parser.parse("C:/xml/1.xml");
//		Article a2 = parser.parse("C:/xml/2.xml");
//		Article a3 = parser.parse("C:/xml/3.xml");
//		Article a4 = parser.parse("C:/xml/4.xml");
//		List<Article> articles1 = new ArrayList<Article>();
//		List<Article> articles2 = new ArrayList<Article>();
//		articles1.add(a1);
//		articles1.add(a2);
//		articles2.add(a3);
//		articles2.add(a4);
//		//Cluster befÃ¼llen
//		c1.setTopArticle(parser.parse("C:/xml/5.xml"));
//		c2.setTopArticle(parser.parse("C:/xml/6.xml"));
//		c1.setArticles(articles1);
//		c2.setArticles(articles2);
//		//Cluster der Liste hinzufÃ¼gen
//		clusterList.add(c1);
//		clusterList.add(c2);
//		
//		return clusterList;
//	}
//
//	public List<Cluster> getClusterList(String searchString) {
//		List<Cluster> clusterList = this.getClusterList();
//		List<Cluster> returnClusterList = new ArrayList<Cluster>();
//		
//	    for(Cluster cluster : clusterList){
//	    	//Wenn der Titel des TopArtikels das im Suchfeld eingegebene enthält
//	    	if(cluster.getTopArticle().getTitle().contains(searchString)){
//	    		returnClusterList.add(cluster);
//	    	//Anderenfalls kann es immernoch in einem untergeordneten Artikel vorkommen
//	    	} else {
//	    		for(Article article : cluster.getArticles()){
//	    			if(article.getTitle().contains(searchString)){
//	    	    		returnClusterList.add(cluster);
//	    	    	}
//	    		}
//	    	}
//	    }
//		return returnClusterList;
//	}
//
//	public List<Cluster> getEqualClusterList() {
//		//Clusterliste und Cluster vorbereiten
//		List<Cluster> clusterList  = new ArrayList<Cluster>();
//		Cluster c1 = new Cluster();
//		Cluster c2 = new Cluster();	
//		//xml parser anlegen
//		XMLParser parser = new XMLParser();
//		// Artikel anlegen und in eine Liste hinzufügen
//		Article a1 = parser.parse("C:/xml/6.xml");
//		Article a2 = parser.parse("C:/xml/5.xml");
//		Article a3 = parser.parse("C:/xml/4.xml");
//		Article a4 = parser.parse("C:/xml/3.xml");
//		List<Article> articles1 = new ArrayList<Article>();
//		List<Article> articles2 = new ArrayList<Article>();
//		articles1.add(a1);
//		articles1.add(a2);
//		articles2.add(a3);
//		articles2.add(a4);
//		//Cluster befÃ¼llen
//		c1.setTopArticle(parser.parse("C:/xml/2.xml"));
//		c2.setTopArticle(parser.parse("C:/xml/1.xml"));
//		c1.setArticles(articles1);
//		c2.setArticles(articles2);
//		//Cluster der Liste hinzufügen
//		clusterList.add(c1);
//		clusterList.add(c2);
//		
//		return clusterList;
//	}
//}
