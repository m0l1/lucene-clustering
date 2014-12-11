package de.hof_university.iisys.pp_vinf12.lucene_clustering.spielwiese;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.IndexInput;
import org.apache.lucene.util.Version;

import de.hof_university.iisys.pp_vinf12.lucene_clustering.data.Article;

public class Indexing {
	
	private void Indexing() { }
		public static void main (String[] args) throws IOException {
		try {
			String indexPath = "index";
			String docsPath = null;
			boolean create = true;
			
			final File docDir = new File(docsPath);
			if(!docDir.exists() || !docDir.canRead()) {
				System.out.println("Document directory '" + docDir.getAbsolutePath() + "' does not exist or is not readable!");
				System.exit(1);
			}
			
			Directory dir = FSDirectory.open(new File(indexPath));

			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
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
			indexDocs(writer, docDir);
			
			writer.close();
			
		} 
		catch (IOException e)
		{
			System.out.println("Caught a " + e.getClass() + "\n with message: " + e.getMessage());
		}
			
			
	}
		
		// Diese Methode indexiert nur ein Dokument per input-File, um das zu Beschleunigen, mehrere Dokumente ins input-File
		// @TODO: Benchmark Modul
		
		static void indexDocs(IndexWriter writer, File file) throws IOException {
			if (file.canRead()) {
				if (file.isDirectory()) {
					String[] files = file.list();
					if(files !=null) {
						for (int i = 0; i < files.length; i++) {
							indexDocs(writer, new File(file, files[i]));
						}
					}
				}
				
				else {
					FileInputStream fis;
					try {
						fis = new FileInputStream(file);
					}
					catch (FileNotFoundException fnfe) {
						return;
					}
					try {
						Document doc = new Document();
						Field pathField = new StringField("path", file.getPath(), Field.Store.YES);
						doc.add(pathField);
						doc.add(new LongField("modified", file.lastModified(), Field.Store.NO));
						doc.add(new TextField("title", title, Field.Store.YES));
						doc.add(new TextField("description", description, Field.Store.YES));
						doc.add(new TextField("source", source, Field.Store.YES));
						doc.add(new TextField("language", language, Field.Store.YES));
						doc.add(new TextField("text", text, Field.Store.YES));
						doc.add(new TextField("logo", logo, Field.Store.YES));
						doc.add(new TextField("link", link, Field.Store.YES));
						doc.add(new TextField("date", date, Field.Store.YES));
						doc.add(new TextField("contents", new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8))));
					}
					finally {
						fis.close();
					}
				}
			}
			
		}
		
		private static void parseArticle(Article article) {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("object.data"));

			Article article = (Article) IndexInput.readObject();

			String titel = (String) ois.readObject();
			String description = (String) ois.readObject();
			String source = (String) ois.readObject();
			String language = (String) ois.readObject();
			String text = (String) ois.readObject();
			String logo = (String) ois.readObject();
			String link = (String) ois.readObject();
			Date date = (Date) ois.readObject();
		
		}
	}
