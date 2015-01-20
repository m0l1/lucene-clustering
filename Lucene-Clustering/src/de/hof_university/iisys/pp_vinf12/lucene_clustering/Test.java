package de.hof_university.iisys.pp_vinf12.lucene_clustering;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queries.mlt.MoreLikeThis;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

import de.hof_university.iisys.pp_vinf12.lucene_clustering.data.Article;
import de.hof_university.iisys.pp_vinf12.lucene_clustering.lucene.ArticleIndexer;
import de.hof_university.iisys.pp_vinf12.lucene_clustering.xml.RecursiveFileLister;
import de.hof_university.iisys.pp_vinf12.lucene_clustering.xml.FileLineLister;
import de.hof_university.iisys.pp_vinf12.lucene_clustering.xml.XMLParser;

public class Test {
	
	public static void main(String[] args) throws IOException, ParseException {
		RecursiveFileLister it = new RecursiveFileLister("testfiles");
		XMLParser parser = new XMLParser();
		FileLineLister puller = new FileLineLister();
		ArticleIndexer indexer = new ArticleIndexer("lucene.index");
		
		List<Article> articles = new ArrayList<Article>();
		List<String> notificationFiles = it.getFileList();
		List<String> xmlFiles = new ArrayList<String>();
		System.out.println("Files: " + notificationFiles.size());
		
		for(String file : notificationFiles) {
			List<String> tmpList = new ArrayList<>();
			tmpList = puller.listXMLFilesInFile(file);
			for(String tmp : tmpList) {
				xmlFiles.add(tmp);
			}
			
		}
		
		System.out.println("XML-Files: " + xmlFiles.size());
		
		for(String file : xmlFiles) {
			Article article = parser.parse(file);
			articles.add(article);		
		}
		
		try {
			indexer.writeArticlesToIndex(articles);
		} catch (IOException e) {
			System.err.println("Indexen fehlgeschlagen \n äh jetzt fertig\n");
			System.err.println(e.getMessage());		
		}
		
		SimpleFSDirectory dir = new SimpleFSDirectory(new File("lucene.index"));
		DirectoryReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		searcher.setSimilarity(new BM25Similarity());
		Analyzer analyzer = new GermanAnalyzer(Version.LUCENE_45);
//		QueryParser queryParser = new QueryParser(Version.LUCENE_45, "title", analyzer);
//		
//		Query query = queryParser.parse("München");
//		TopDocs td = searcher.search(query, 10);
//		ScoreDoc[] sd = td.scoreDocs;
//		for (int j = 0; j < sd.length; ++j) {
//			Document doc = searcher.doc(sd[j].doc);
//			System.out.println(doc.get("source") + " // " + doc.get("title") + " // " + doc.get("date"));
//		}

		MoreLikeThis mlt = new MoreLikeThis(reader);
		mlt.setAnalyzer(analyzer);
		mlt.setFieldNames(new String[] {"text"});
		
		for (Article article : articles) {
			Query query = mlt.like(new StringReader(article.getText()), null);
			ScoreDoc[] docs = searcher.search(query, 5).scoreDocs;
			
			System.out.println(article.getSource() + " // " + article.getTitle());
			System.out.println("\t" + docs.length + " Ähnliche");
			for (ScoreDoc doc : docs) {
				System.out.println("\t(" + doc.score + ") - " + searcher.doc(doc.doc).get("source") + " // " + searcher.doc(doc.doc).get("title"));
			}
			System.out.println();
		}
	}
}
