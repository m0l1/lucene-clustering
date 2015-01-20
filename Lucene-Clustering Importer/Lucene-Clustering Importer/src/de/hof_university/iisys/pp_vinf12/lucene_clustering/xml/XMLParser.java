package de.hof_university.iisys.pp_vinf12.lucene_clustering.xml;


// @author Maximilian Louis, Dominick Oeckel
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jdom2.Element;
import org.jdom2.input.DOMBuilder;
import org.xml.sax.SAXException;

import de.hof_university.iisys.pp_vinf12.lucene_clustering.data.Article;

public class XMLParser {
	
	private DocumentBuilder builder;
	private org.w3c.dom.Document doc;
	private DocumentBuilderFactory dbFactory;
	private Element root;
	private DOMBuilder domBuilder;
	
	public XMLParser(){
		dbFactory = DocumentBuilderFactory.newInstance();
		root = new Element("root");
		try {
			builder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		domBuilder = new DOMBuilder();
	}
	/**
	 * Diese Methode liefert einen Article mit den Inhalten der übergebenen XML-Datei.
	 * Pfade mit \ wie z.B. C:\\Users\\ werden automatisch zu C:/Users/ konvertiert.
	 * @return Article.
	 * @param Vollständiger Pfad zu einer XML-Datei
	 */
	
	public Article parse(String path){
		File file = new File(path.replace("\\", "/"));
		return parse(file);
	}
	
	public Article parse(File file){
		try {
			doc = builder.parse(file);
			org.jdom2.Document jDoc = domBuilder.build(doc);
			root = jDoc.getRootElement(); 
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		
		Article article = new Article();	
		
		Element channel = root.getChild("channel");
		article.setLanguage(channel.getChildText("language"));
		article.setSource(channel.getChildText("title"));
		if(channel.getChild("image") != null)
			article.setLogo(channel.getChild("image").getChildText("url"));
		Element item = root.getChild("channel").getChild("item");
		article.setLink(item.getChildText("link"));
		article.setText(item.getChildText("ExtractedText"));
		article.setTitle(item.getChildText("title"));
		article.setDescription(item.getChildText("description"));
		
		// date nur parsen, falls tatsaechlich eines in der Datei steht
		String date = item.getChildText("pubDate");
		if (date != null) {
			article.setDate(parseDate(date));
		}
		// sonst Zeitpunkt des Parsens, das passt wenigstens ungefähr
		else {
			article.setDate(
					parseDate(
							new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z",
									Locale.ENGLISH).format(
											new GregorianCalendar().getTime()
											)
							)
					);
		}
		
		article.setArticleID(UUID.randomUUID());
		
		return article;
	}
	
	private GregorianCalendar parseDate(String dateString) {
		dateString = dateString.substring(5);
		GregorianCalendar calender = new GregorianCalendar();
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
		try {
			date = simpleDateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calender.setTime(date);
		return calender;
	}

}