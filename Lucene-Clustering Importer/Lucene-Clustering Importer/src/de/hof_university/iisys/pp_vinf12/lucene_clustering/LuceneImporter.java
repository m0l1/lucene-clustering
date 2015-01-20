package de.hof_university.iisys.pp_vinf12.lucene_clustering;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import de.hof_university.iisys.pp_vinf12.lucene_clustering.data.Article;
import de.hof_university.iisys.pp_vinf12.lucene_clustering.lucene.ClusterBuilder;
import de.hof_university.iisys.pp_vinf12.lucene_clustering.xml.ArticleImporter;

public class LuceneImporter {

	public static void main(String[] args) {
		
		String subscriptionDir = "testfiles";
		String articleIndex = "articles.lucene";
		String clusterIndex = "clusters.lucene";
		int toCompare = 5;
		float clusterScore = 30.0f;
		float identityScore = 60.0f;
		int sleepMinutes = 10;
		
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
