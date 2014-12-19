package de.hof_university.iisys.pp_vinf12.lucene_clustering.gui;

import java.util.List;

import de.hof_university.iisys.pp_vinf12.lucene_clustering.data.Cluster;

public interface BeanData {
	//Methode um die geclusterten Artikel für die Hauptseite zu bekommen
	public List<Cluster> getClusterList();
	//Methode um alle Artikel (und deren zugehörige Artikel) zu bekommen, die searchString enthalten
	public List<Cluster> getClusterList(String searchString);
	//Methode um alle Artikel, die absolut identisch (quasi kopiert und auf verschiedenen Seiten) sind
	//in Clustern zusammengefasst zu bekommen
	public List<Cluster> getEqualClusterList();
}
