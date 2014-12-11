package de.hof_university.iisys.pp_vinf12.lucene_clustering.data;

import java.util.ArrayList;
import java.util.List;

import de.hof_university.iisys.pp_vinf12.lucene_clustering.gui.DatenTest;

public class ClusterWrapper {
	private List<Cluster> clusterList = new ArrayList<Cluster>();
	public ClusterWrapper() {
		setClusterList(DatenTest.getData());
	}

	public List<Cluster> getClusterList() {
		return clusterList;
	}

	public void setClusterList(List<Cluster> clusterList) {
		this.clusterList = clusterList;
	}
	
}