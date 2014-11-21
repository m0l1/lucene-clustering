package de.hof_university.iisys.pp_vinf12.lucene_clustering.gui;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.hof_university.iisys.pp_vinf12.lucene_clustering.data.Article;
import de.hof_university.iisys.pp_vinf12.lucene_clustering.data.Cluster;
 
@ManagedBean
@SessionScoped
public class ArticleManagedBean {
    private List<Cluster> clusterList;
    
    // http://stackoverflow.com/questions/9186364/how-do-i-display-a-list-of-items-from-a-bean-onto-a-jsf-webpage
    
    public ArticleManagedBean(){
    	
    	List<Cluster> clList = new ArrayList<Cluster>();
    	Cluster cl = new Cluster();
    	Article art = new Article();
    	art.setTitle("TestArtikelTitel");
    	cl.setTopArticle(art);
    	clList.add(cl);
    	
    }

	public List<Cluster> getClusterList() {
		return clusterList;
	}

	public void setClusterList(List<Cluster> clusterList) {
		this.clusterList = clusterList;
	}


}