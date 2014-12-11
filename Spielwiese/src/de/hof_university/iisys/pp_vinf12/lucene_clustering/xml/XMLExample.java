package de.hof_university.iisys.pp_vinf12.lucene_clustering.xml;

import java.util.List;
import de.hof_university.iisys.pp_vinf12.lucene_clustering.data.Cluster;
import de.hof_university.iisys.pp_vinf12.lucene_clustering.gui.DatenTest;

public class XMLExample {
	public static void main(String[] args) {
		List<Cluster> clusterList = DatenTest.getData();
		System.out.println(clusterList.get(1).getTopArticle().getText());
	}
}
