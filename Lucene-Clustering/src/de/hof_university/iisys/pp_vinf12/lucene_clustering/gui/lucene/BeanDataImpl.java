package de.hof_university.iisys.pp_vinf12.lucene_clustering.gui.lucene;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import de.hof_university.iisys.pp_vinf12.lucene_clustering.gui.data.Cluster;

public class BeanDataImpl implements BeanData {
	
	String articleIndex = "C:/Program Files/Development/eclipse 4.3.2/articles.lucene";
	String clusterIndex = "C:/Program Files/Development/eclipse 4.3.2/clusters.lucene";

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
