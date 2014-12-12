package de.hof_university.iisys.pp_vinf12.lucene_clustering.spielwiese;

import java.io.File;
import java.io.IOException;
import java.util.GregorianCalendar;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import de.hof_university.iisys.pp_vinf12.lucene_clustering.data.Article;

// @author ckoepf
// last modified: 12.12.2014

public class Indexing {
	
		public static void main (String[] args) throws IOException {
		try {
			String indexPath = "index";
			String docsPath = null;
			boolean create = true;
			// hier wird ja dann normalerweise ein Article aus dem XML-Parser kommen
			Article article = new Article("Haribo macht Kinder froh", "und erwachsene ebenso", "Spiegel", "de", "fjkdsvnajidfgnfmdagnjdfaghdruia", "Pfad", "link", new GregorianCalendar());
			
			Directory dir = FSDirectory.open(new File(indexPath));

			@SuppressWarnings("deprecation")
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
			@SuppressWarnings("deprecation")
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_CURRENT, analyzer);
			
			if (create) {
				// Erstellt neuen Index in dem vorgegebenen Directory ohne vorher geindexte Dokumente zu löschen
				config.setOpenMode(OpenMode.CREATE);
			}
			else {
				// Fügt neues Dokument zu einem bereits bestehnden Index hinzu
				config.setOpenMode(OpenMode.CREATE_OR_APPEND);
			}
			
			IndexWriter writer = new IndexWriter(dir, config);
			indexDocs(writer, article);
			
			writer.close();
			
		} 
		catch (IOException e)
		{
			System.out.println("Caught a " + e.getClass() + "\n with message: " + e.getMessage());
		}
			
			
	}
		
		// Diese Methode indexiert nur ein Dokument per input-File, um das zu Beschleunigen, mehrere Dokumente ins input-File
		// @TODO: Benchmark Modul
		
		static void indexDocs(IndexWriter writer, Article article) throws IOException {
			Document doc = new Document();
//			Field pathField = new StringField("path", file.getPath(), Field.Store.YES);
//			doc.add(pathField);
//			doc.add(new LongField("modified", title.lastModified(), Field.Store.NO));
			doc.add(new TextField("title", article.getTitle(), Field.Store.YES));
			doc.add(new TextField("description", article.getDescription(), Field.Store.YES));
			doc.add(new TextField("source", article.getSource(), Field.Store.YES));
			doc.add(new TextField("language", article.getLanguage(), Field.Store.YES));
			doc.add(new TextField("text", article.getText(), Field.Store.YES));
			doc.add(new TextField("logo", article.getLogo(), Field.Store.YES));
			doc.add(new TextField("link", article.getLink(), Field.Store.YES));
			// TODO: Marcel, das ist jetzt dein Problem. Wir brauchen hier Date, haben aber GregCal
//			doc.add(new TextField("date", article.toString(), Field.Store.YES));
//			doc.add(new TextField("contents", article.getText()));
			
			
		}
		

}
