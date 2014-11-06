package de.hof_university.iisys.pp_vinf12.lucene_clustering.gui;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.hof_university.iisys.pp_vinf12.lucene_clustering.data.Cluster;
 
@ManagedBean
@SessionScoped
public class ArticleManagedBean {
    private Cluster cluster = new Cluster();
 
    public ArticleManagedBean(){
    }

	public Cluster getCluster() {
		return cluster;
	}

	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}

}