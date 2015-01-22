package de.hof_university.iisys.pp_vinf12.lucene_clustering.importer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Properties;

import de.hof_university.iisys.pp_vinf12.lucene_clustering.importer.data.Article;
import de.hof_university.iisys.pp_vinf12.lucene_clustering.importer.lucene.ClusterBuilder;
import de.hof_university.iisys.pp_vinf12.lucene_clustering.importer.xml.ArticleImporter;

public class LuceneImporter {

	public static void main(String[] args) {
		
		final String CONFIG_PATH = "importer.properties";
		
		String subscriptionDir = "testfiles";
		String articleIndex = "articles.lucene";
		String clusterIndex = "clusters.lucene";
		int toCompare = 5;
		float clusterScore = 30.0f;
		float identityScore = 60.0f;
		int sleepMinutes = 10;
		
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(CONFIG_PATH));
			
			subscriptionDir = properties.getProperty("subscriptionDir");
			articleIndex = properties.getProperty("articleIndex");
			clusterIndex = properties.getProperty("clusterIndex");
			toCompare = Integer.parseInt(properties.getProperty("toCompare"));
			clusterScore = Float.parseFloat(properties.getProperty("clusterScore"));
			identityScore = Float.parseFloat(properties.getProperty("identityScore"));
			sleepMinutes = Integer.parseInt(properties.getProperty("sleepMinutes"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
				
		do {
			ArticleImporter importer = new ArticleImporter(subscriptionDir);
			ClusterBuilder builder = new ClusterBuilder(articleIndex, clusterIndex, toCompare, clusterScore, identityScore);

			try {
				List<Article> articles = importer.importArticles();
				builder.buildClusters(articles);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			}
			
			try {
				Thread.sleep(sleepMinutes * 60000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			}
			
		} while(true);
	}

}
