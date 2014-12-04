package de.hof_university.iisys.pp_vinf12.lucene_clustering.gui;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.hof_university.iisys.pp_vinf12.lucene_clustering.data.Cluster;
 
@ManagedBean
@SessionScoped
public class ArticleManagedBean {
    private List<Cluster> cluster;
 
    public ArticleManagedBean(){
    }

	public List<Cluster> getCluster() {
		return cluster;
	}

	public void setCluster(List<Cluster> cluster) {
		this.cluster = cluster;
	}

}