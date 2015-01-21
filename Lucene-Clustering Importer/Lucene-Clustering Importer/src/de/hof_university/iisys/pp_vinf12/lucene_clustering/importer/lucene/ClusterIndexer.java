package de.hof_university.iisys.pp_vinf12.lucene_clustering.importer.lucene;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;

import de.hof_university.iisys.pp_vinf12.lucene_clustering.importer.data.Cluster;

public class ClusterIndexer {

	private IndexWriter writer;
	private IndexSearcher searcher;
	
	public ClusterIndexer(IndexWriter writer, IndexSearcher searcher){
		this.writer = writer;
		this.searcher = searcher;
	}
	
	public void writeClusterToIndex(Cluster cluster) throws IOException{
		
		Document doc = buildDoc(cluster);
		writer.addDocument(doc);
	}
	
	public void writeClustersToIndex(List<Cluster> clusters) throws IOException{
		
		for (Cluster cluster : clusters) {
			Document doc = buildDoc(cluster);
			writer.addDocument(doc);
		}
	}
	
	public void updateCluster(Cluster cluster) throws IOException {
		
		Query query = TermRangeQuery.newStringRange("clusterID", cluster.getClusterId().toString(), cluster.getClusterId().toString(), true, true);
		TopDocs docs = searcher.search(query, 1);
		
		if (docs.totalHits == 1 && 
				searcher.doc(docs.scoreDocs[0].doc).getField("clusterID").stringValue().equals(cluster.getClusterId().toString())) {
			writer.deleteDocuments(query);
			writer.addDocument(buildDoc(cluster));
		}
		else {
			// TODO - was machen?!? -> passende Exception und loggen?
		}	
	}
	
	private Document buildDoc(Cluster cluster){
		
		Document doc = new Document();
		
		doc.add(new StringField("clusterID", cluster.getClusterId().toString(), Field.Store.YES));
		doc.add(new StringField("topArticleID", cluster.getTopArticleId().toString(), Field.Store.YES));
		doc.add(new StringField("created", new SimpleDateFormat
				("yyyy/MM/dd HH:mm:ss").format(cluster.getCreated().getTime()), Field.Store.YES));
		doc.add(new StringField("lastChange", new SimpleDateFormat
				("yyyy/MM/dd HH:mm:ss").format(cluster.getLastChange().getTime()), Field.Store.YES));
		
		return doc;
	}
}
