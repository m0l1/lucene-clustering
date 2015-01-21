package de.hof_university.iisys.pp_vinf12.lucene_clustering.gui.data;

import java.util.Comparator;

/**
 * Comparator, der Cluster anhand des Datums der letzten Aenderung
 * vergleicht. Frühere Daten sind "groesser", damit bei Sortierung in
 * aufsteigender Reihenfolge die neuesten Cluster nach vorne sortiert
 * werden. 
 * 
 * @author Marcel Molitor
 */
public class ClusterComparator implements Comparator<Cluster> {

	@Override
	public int compare(Cluster o1, Cluster o2) {
		
		return -(o1.getLastChange().compareTo(o2.getLastChange()));
	}

}
