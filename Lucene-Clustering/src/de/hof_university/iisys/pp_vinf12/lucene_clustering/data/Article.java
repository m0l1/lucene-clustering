package de.hof_university.iisys.pp_vinf12.lucene_clustering.data;

import java.util.GregorianCalendar;

/**
 * 
 * Diese Klasse legt einen neuen Article an.
 * Artikel-Objekte werden beim Parsen von XML-Dateien und beim Auslesen des Indexes erstellt.
 * Die Klasse gewährleistet in erster Linie die Sicherheit der Attribute für die Präsentation.
 * 
 * @author CKöpf, JTrautmann
 * @version 1.1
 * 
 */

public class Article {
	
	/** Überschrift des Artikels */
	private String title;
	/** Beschreibung des Artikels */
	private String description;
	/** Zeitung/Magazin des Artikels */
	private String source;
	/** Sprache des Artikels */
	private String language;
	/** Inhalt des Artikels */
	private String text;
	/** Logo des Veröffentlichers, wenn keines vorhanden, wird das Standardbild verwendet */
	private String logo = "/resources/default.jpg";
	/** Link zum Originalartikel */
	private String link;
	/** Veröffentlichungsdatum */
	private GregorianCalendar date;
	
	//Wird für XML-Parser benötigt!
	/** Default-Konstruktor */
	public Article(){
	}
	
	/** Vollständiger Konstruktor, alle Member werden initialisiert */
	public Article(String title, String description, String source,
			String language, String text, String logo, String link,
			GregorianCalendar date) {
		super();
		this.title = title;
		this.description = description;
		this.source = source;
		this.language = language;
		this.text = text;
		this.setLogo(logo);
		this.link = link;
		this.date = date;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		if(logo != "")
			this.logo = logo;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public GregorianCalendar getDate() {
		return date;
	}
	public void setDate(GregorianCalendar date) {
		this.date = date;
	}

}
