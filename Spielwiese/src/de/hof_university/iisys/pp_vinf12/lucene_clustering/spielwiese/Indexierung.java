package de.hof_university.iisys.pp_vinf12.lucene_clustering.spielwiese;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.text.Format.Field;
import java.util.Date;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;


// Fügt jeden Artikel zum Index hinzu

public class Indexierung {
	public static void main (String[] args) throws Exception {

		// Neuen Index erzeugen		
		SimpleFSDirectory dir = new SimpleFSDirectory(new File(FileLuceneExampleWriter.INDEX_PATH));
		DirectoryReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);

		boolean create = true;
		IndexWriter writer = new IndexWriter(indexPath, new StandardAnalyser(Version.LUCENE_45), true);

		String[] search = new String[3];

	};

	writer.optimize();
	writer.close();
}

private static void addToIndex(File file, IndexWriter writer) {
	try { 
		InputStream is = new FileInputStream(file);
		Document doc = new Document();

		doc.add(new TextField("title", title, Field.Store.YES));
		doc.add(new TextField("description", description, Field.Store.YES));
		doc.add(new TextField("source", source, Field.Store.YES));
		doc.add(new Textfield("language", language, Field.Store.YES));
		doc.add(new TextField("text", text, Field.Store.Yes));
		doc.add(new Textfield("logo", logo, Field.Store.Yes));
		doc.add(new TextField("link", link, Field.Store.Yes));
		doc.add(new DateField("date", date, Field.Store.Yes));

		//Document zu Index hinzufügen
		writer.addDocument(doc);
		is.close();
	}
	catch(IOException exc) {
		System.out.println("Indexing " + article.getName() + "failed.");
		exc.printStackTrace();
	}

}

private static void parseArticle(Article article) {
	ObjectInputStream ois = new ObjectInputStream(new FileInputStream("object.data"));

	Article article = (Article) input.readObject();

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



