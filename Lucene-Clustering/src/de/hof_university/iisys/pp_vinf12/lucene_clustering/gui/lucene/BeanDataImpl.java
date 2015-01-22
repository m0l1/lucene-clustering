package de.hof_university.iisys.pp_vinf12.lucene_clustering.gui.lucene;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Properties;

import javax.naming.OperationNotSupportedException;

import de.hof_university.iisys.pp_vinf12.lucene_clustering.gui.data.Cluster;
import de.hof_university.iisys.pp_vinf12.lucene_clustering.gui.data.ClusterComparator;

public class BeanDataImpl implements BeanData {
	
	private static final String CONFIG_PATH = "lucene-clustering.properties";
	
	String articleIndex = "articles.lucene";
	String clusterIndex = "clusters.lucene";
	
	public BeanDataImpl() {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(CONFIG_PATH));
			
			articleIndex = properties.getProperty("articleIndex");
			clusterIndex = properties.getProperty("clusterIndex");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

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
		ClusterAssembler assembler = new ClusterAssembler(articleIndex, clusterIndex);
		List<Cluster> clusters = null;
		try {
			clusters = assembler.getIdentityClusters();
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

}
