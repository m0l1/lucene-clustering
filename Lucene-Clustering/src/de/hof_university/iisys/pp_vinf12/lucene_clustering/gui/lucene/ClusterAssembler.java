package de.hof_university.iisys.pp_vinf12.lucene_clustering.gui.lucene;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.SimpleFSDirectory;

import de.hof_university.iisys.pp_vinf12.lucene_clustering.gui.data.Cluster;

public class ClusterAssembler {
	
	private String clusterIndex;
	
	public ClusterAssembler(String clusterIndex) {
		this.clusterIndex = clusterIndex;
	}
	
	public List<Cluster> getAllClusters() throws IOException, ParseException {
		
		List<Cluster> clusters = new ArrayList<Cluster>();
		
		SimpleFSDirectory clusterDir = new SimpleFSDirectory(new File(clusterIndex));
		DirectoryReader clusterReader = DirectoryReader.open(clusterDir);
		IndexSearcher clusterSearcher = new IndexSearcher(clusterReader);
		clusterSearcher.setSimilarity(new BM25Similarity());
		
		Query clusterQuery = new MatchAllDocsQuery();
		ScoreDoc[] clusterDocs = clusterSearcher.search(clusterQuery, clusterReader.numDocs()).scoreDocs;
		
		for (ScoreDoc clusterDoc : clusterDocs) {
			Document doc = clusterSearcher.doc(clusterDoc.doc);
			Cluster cluster = new Cluster();
			cluster.setClusterId(UUID.fromString(doc.getField("clusterID").stringValue()));
			cluster.setCreated(parseDate(doc.getField("created").stringValue()));
			cluster.setLastChange(parseDate(doc.getField("lastChanged").stringValue()));
			
		}
		
		return clusters;
	}

	private GregorianCalendar parseDate(String dateString) throws ParseException {
		GregorianCalendar calendar = new GregorianCalendar();
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		date = simpleDateFormat.parse(dateString);
		calendar.setTime(date);
		return calendar;
	}
}
