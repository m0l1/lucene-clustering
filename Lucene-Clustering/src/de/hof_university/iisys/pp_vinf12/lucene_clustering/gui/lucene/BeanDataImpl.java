package de.hof_university.iisys.pp_vinf12.lucene_clustering.gui.lucene;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.SimpleFSDirectory;

import de.hof_university.iisys.pp_vinf12.lucene_clustering.gui.data.Cluster;

public class BeanDataImpl implements BeanData {
	
	String articleIndex = "articles.lucene";
	String clusterIndex = "clusters.lucene";

	@Override
	public List<Cluster> getClusterList() {
		
		
		
		return null;
	}

	@Override
	public List<Cluster> getClusterList(String searchString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cluster> getEqualClusterList() {
		// TODO Auto-generated method stub
		return null;
	}

}
