package de.hof_university.iisys.pp_vinf12.lucene_clustering.gui.lucene;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import de.hof_university.iisys.pp_vinf12.lucene_clustering.gui.data.Cluster;
import de.hof_university.iisys.pp_vinf12.lucene_clustering.gui.data.ClusterComparator;

public class BeanDataImpl implements BeanData {
	
	String articleIndex = "articles.lucene";
	String clusterIndex = "clusters.lucene";

	@Override
	public List<Cluster> getClusterList() {
		
		ClusterAssembler assembler = new ClusterAssembler(articleIndex, clusterIndex);
		List<Cluster> clusters = null;
		try {
			clusters = assembler.getAllClusters();
		} catch (IOException e) {
			// TODO Logging
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Logging
			e.printStackTrace();
		}
		
		clusters.sort(new ClusterComparator());
		return clusters;
	}

	@Override
	public List<Cluster> getClusterList(String searchString) {
		throw new RuntimeException(new OperationNotSupportedException());
	}

	@Override
	public List<Cluster> getEqualClusterList() {
		throw new RuntimeException(new OperationNotSupportedException());
	}

}
