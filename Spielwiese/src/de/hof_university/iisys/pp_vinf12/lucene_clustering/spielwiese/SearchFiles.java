package de.hof_university.iisys.pp_vinf12.lucene_clustering.spielwiese;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import de.hof_university.iisys.pp_vinf12.lucene_clustering.spielwiese.Indexing;

public class SearchFiles {

	public static void main(String  [] args) throws Exception {
	
	 System.out.println("Enter your search keyword in here : ");
	    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
	    String queryString = bufferRead.readLine();
	    
	    String text = "Text aus der IndexReaderKlasse";

	    Query q = new QueryParser(Version.LUCENE_CURRENT,  text , analyzer).parse(queryString);
	}
	
	
    //SearchFiles
	private static void searchFiles(){
		
		String field = "contents";
		int hitsPerPage = 10;
		
		//dir: muss die Liste der geindexten Artikeln rein
		IndexReader reader = IndexReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
		TFIDFSimilarity bestMatch;
		searcher.search(q, bestmatch);
	}
}
