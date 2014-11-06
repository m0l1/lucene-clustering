package de.hof_university.iisys.pp_vinf12.lucene_clustering.xml;
import java.util.Map;

public class XMLExample {
	public static void main(String[] args) {
		XMLParser xml = new XMLParser();
		Map<String, String> mapTest = xml.parse("C:/Users/Maxim_000/Schule/Semester 5/Eclipse/Praktikum Programmieren/rssfiles/germany/de/travel/ZEITONLINEReisen/y2013/m9/d30/RSS-2083299493.xml");
		System.out.println(mapTest.values());
	}
}
