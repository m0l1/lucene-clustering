package de.hof_university.iisys.pp_vinf12.lucene_clustering.spielwiese;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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


public class FileLuceneExampleWriter {
	
	public static final String INDEX_PATH = "C:/Temp/mm-lucene-file-test/";
	public static final String DATA_PATH = "H:/Dokumente/5. Semester/PP/Testdaten/Text/";
	
	private static IndexWriter writer;
	
	public static void main(String[] args) throws IOException {

		SimpleFSDirectory dir = new SimpleFSDirectory(new File(FileLuceneExampleWriter.INDEX_PATH));
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_45);
		IndexWriter writer = new IndexWriter(dir, new IndexWriterConfig(Version.LUCENE_45, analyzer));

		File basedir = new File(DATA_PATH);
		traverseFiles(basedir.listFiles());

		writer.close();
	}
	
	public static void traverseFiles(File[] files) {
		for (File file : files) {
			if (file.isDirectory()) {
				traverseFiles(file.listFiles());
			} else {
				System.out.println(file.getAbsolutePath());
				parseFile(file);
			}
		}
	}
	
	public static void parseFile(File file) {
	
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			String title = reader.readLine();
			String author = reader.readLine();
			
			StringBuffer buffer = new StringBuffer(reader.readLine());
			String line;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			String content = buffer.toString();
			
			reader.close();

			Document doc = new Document();
			doc.add(new TextField("title", title, Field.Store.YES));
			doc.add(new TextField("author", author, Field.Store.NO));
			doc.add(new TextField("content", content, Field.Store.YES));

			writer.addDocument(doc);
			
		} catch (IOException e) {
			System.err.println("FEHLER beim hinzufügen von Datei " + file.getAbsolutePath() + ": ");
			System.err.println("\t" + e.getLocalizedMessage());
		}
	}
}
