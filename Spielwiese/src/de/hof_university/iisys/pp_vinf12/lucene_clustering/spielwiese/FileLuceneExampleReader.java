package de.hof_university.iisys.pp_vinf12.lucene_clustering.spielwiese;
import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;


public class FileLuceneExampleReader {
	
	
	public static void main(String[] args) throws IOException, ParseException {

		SimpleFSDirectory dir = new SimpleFSDirectory(new File(FileLuceneExampleWriter.INDEX_PATH));
		DirectoryReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_45);
		QueryParser parser = new QueryParser(Version.LUCENE_45, "content", analyzer);
		
		String[] search = new String[2];
		search[0] = "woodchuck";
		search[1] = "string";
		
		for (int i = 0; i < search.length; ++i) {
			System.out.println("Suche nach: " + search[i]);
			Query query = parser.parse(search[i]);
			TopDocs td = searcher.search(query,10);
			ScoreDoc[] sd = td.scoreDocs;
			for (int j = 0; j < sd.length; ++j) {
				Document doc = searcher.doc(sd[j].doc);
				System.out.println(doc.get("title"));
			}
			System.out.println();
		}
	}
	
	
}
