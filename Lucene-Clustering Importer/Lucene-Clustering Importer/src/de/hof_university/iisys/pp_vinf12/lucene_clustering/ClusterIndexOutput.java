package de.hof_university.iisys.pp_vinf12.lucene_clustering;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

//import de.hof_university.iisys.pp_vinf12.lucene_clustering.lucene.ArticleIndexer;
//import de.hof_university.iisys.pp_vinf12.lucene_clustering.lucene.ClusterIndexer;

public class ClusterIndexOutput {

	public static void main(String[] args) throws IOException {

		String articleIndex = "articles.lucene";
		String clusterIndex = "clusters.lucene";

		Analyzer analyzer = new GermanAnalyzer(Version.LUCENE_45);

		SimpleFSDirectory clusterDir = new SimpleFSDirectory(new File(clusterIndex));
		IndexWriter clusterWriter = new IndexWriter(clusterDir, new IndexWriterConfig(Version.LUCENE_45, analyzer));
		DirectoryReader clusterReader = DirectoryReader.open(clusterWriter, true);

		for (int i=0; i<clusterReader.maxDoc(); i++) {

			Document doc = clusterReader.document(i);
			String docId = doc.get("clusterID");

			System.out.println(i + ": " + docId);
		}
	}
}
