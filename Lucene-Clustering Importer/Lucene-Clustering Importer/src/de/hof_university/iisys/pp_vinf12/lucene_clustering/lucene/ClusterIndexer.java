package de.hof_university.iisys.pp_vinf12.lucene_clustering.lucene;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;

import de.hof_university.iisys.pp_vinf12.lucene_clustering.data.Cluster;

public class ClusterIndexer {

	private IndexWriter writer;
	private IndexSearcher searcher;
	
	public ClusterIndexer(IndexWriter writer, IndexSearcher searcher){
		this.writer = writer;
		this.searcher = searcher;
	}
	
	public void writeClusterToIndex(Cluster cluster) throws IOException{
		
		System.out.println("Cluster schreiben: id=" + cluster.getClusterId().toString());
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
		
//		BooleanQuery query = new BooleanQuery();
//		query.add(NumericRangeQuery.newLongRange("clusterIDLSB", cluster.getClusterId().getLeastSignificantBits(), cluster.getClusterId().getLeastSignificantBits(), true, true), BooleanClause.Occur.MUST);
//		query.add(NumericRangeQuery.newLongRange("clusterIDMSB", cluster.getClusterId().getMostSignificantBits(), cluster.getClusterId().getMostSignificantBits(), true, true), BooleanClause.Occur.MUST);
		Query query = new TermQuery(new Term("clusterID", cluster.getClusterId().toString()));
		TopDocs docs = searcher.search(query, 1);
		
		if (docs.totalHits == 1 && 
//				searcher.doc(docs.scoreDocs[0].doc).getField("clusterIDLSB").numericValue().longValue() == cluster.getClusterId().getLeastSignificantBits() &&
//				searcher.doc(docs.scoreDocs[0].doc).getField("clusterIDMSB").numericValue().longValue() == cluster.getClusterId().getMostSignificantBits()) {
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
		
//		System.out.println("Building cluster: msb " + cluster.getClusterId().getMostSignificantBits() + " / lsb " + cluster.getClusterId().getLeastSignificantBits());

//		doc.add(new LongField("clusterIDLSB", cluster.getClusterId().getLeastSignificantBits(), Field.Store.YES));
//		doc.add(new LongField("clusterIDMSB", cluster.getClusterId().getMostSignificantBits(), Field.Store.YES));
//		doc.add(new LongField("topArticleIDLSB", cluster.getTopArticleId().getLeastSignificantBits(), Field.Store.YES));
//		doc.add(new LongField("topArticleIDMSB", cluster.getTopArticleId().getMostSignificantBits(), Field.Store.YES));
		doc.add(new TextField("clusterID", cluster.getClusterId().toString(), Field.Store.YES));
		doc.add(new TextField("topArticleID", cluster.getTopArticleId().toString(), Field.Store.YES));
		doc.add(new TextField("created", new SimpleDateFormat
				("yyyy/MM/dd HH:mm:ss").format(cluster.getCreated().getTime()), Field.Store.YES));
		doc.add(new TextField("lastChange", new SimpleDateFormat
				("yyyy/MM/dd HH:mm:ss").format(cluster.getLastChange().getTime()), Field.Store.YES));
		
		return doc;
	
	}

}
