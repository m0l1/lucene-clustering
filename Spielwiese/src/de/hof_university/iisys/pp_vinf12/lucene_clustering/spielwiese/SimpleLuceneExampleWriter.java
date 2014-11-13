package de.hof_university.iisys.pp_vinf12.lucene_clustering.spielwiese;
import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;


public class SimpleLuceneExampleWriter {
	
	public static final String PATH = "C:\\Temp\\mm-lucene-test\\";
	
	public static void main(String[] args) throws IOException {

		Document doc1 = new Document();
		String text1 = "This is a lucene test!";
		
		doc1.add(new TextField("title", "Title 1", Field.Store.YES));
		doc1.add(new TextField("author", "Arno Nym", Field.Store.NO));
		doc1.add(new TextField("content", text1, Field.Store.YES));
		
		Document doc2 = new Document();
		String text2 = "How much wood would a woodchuck chuck if a woodchuck would chuck wood? Test!";
		
		doc2.add(new TextField("title", "Title 2", Field.Store.YES));
		doc2.add(new TextField("author", "Pseudo Nym", Field.Store.NO));
		doc2.add(new TextField("content", text2, Field.Store.YES));
		
		SimpleFSDirectory dir = new SimpleFSDirectory(new File(SimpleLuceneExampleWriter.PATH));
		
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_45);
		
		IndexWriter writer = new IndexWriter(dir, new IndexWriterConfig(Version.LUCENE_45, analyzer));
		
		writer.addDocument(doc1);
		writer.addDocument(doc2);
		
		writer.close();
	}
}
