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

public class ClusteringTestOutput {

	public static void main(String[] args) throws IOException {

		String articleIndex = "articles.lucene";
		String clusterIndex = "clusters.lucene";

		Analyzer analyzer = new GermanAnalyzer(Version.LUCENE_45);

		SimpleFSDirectory articleDir = new SimpleFSDirectory(new File(articleIndex));
		IndexWriter articleWriter = new IndexWriter(articleDir, new IndexWriterConfig(Version.LUCENE_45, analyzer));
		DirectoryReader articleReader = DirectoryReader.open(articleWriter, true);
		IndexSearcher articleSearcher = new IndexSearcher(articleReader);
		articleSearcher.setSimilarity(new BM25Similarity());

//		ArticleIndexer articleIndexer = new ArticleIndexer(articleWriter, articleSearcher);

		SimpleFSDirectory clusterDir = new SimpleFSDirectory(new File(clusterIndex));
		IndexWriter clusterWriter = new IndexWriter(clusterDir, new IndexWriterConfig(Version.LUCENE_45, analyzer));
		DirectoryReader clusterReader = DirectoryReader.open(clusterWriter, true);
		IndexSearcher clusterSearcher = new IndexSearcher(clusterReader);
		clusterSearcher.setSimilarity(new BM25Similarity());

//		ClusterIndexer clusterIndexer = new ClusterIndexer(clusterWriter, clusterSearcher);

//		Query query = NumericRangeQuery.newLongRange("clusterIDLSB", Long.MIN_VALUE, Long.MAX_VALUE, true, true);
//		Query query = new TermQuery(new Term("clusterID", "678bc33e-c3d3-4197-967e-2710bd283dc8"));
//		TopDocs clusterDocs = clusterSearcher.search(query, 1000);
//		System.out.println("Cluster hits: " + clusterDocs.totalHits);
		
		for (int i=0; i<clusterReader.maxDoc(); i++) {

			Document doc = clusterReader.document(i);
			String clusterId = doc.get("clusterID");

			Query articleQuery = new TermQuery(new Term("clusterID", clusterId));
			TopDocs articleDocs = articleSearcher.search(articleQuery, 1000);
			
			System.out.println("----------");
			System.out.println(articleDocs.totalHits);
			System.out.println("----------");
			for (ScoreDoc articleScoreDoc : articleDocs.scoreDocs) {
				Document articleDoc = articleSearcher.doc(articleScoreDoc.doc);
//				if (articleDoc.getField("clusterIDLSB").numericValue().longValue() == lsb &&
//						articleDoc.getField("clusterIDMSB").numericValue().longValue() == msb) {
					System.out.println(articleDoc.get("source") + " // " + articleDoc.get("title"));
//				}
			}
			System.out.println("----------\n");
		}
	}
}
