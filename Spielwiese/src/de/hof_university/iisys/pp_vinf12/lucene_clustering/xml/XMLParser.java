package de.hof_university.iisys.pp_vinf12.lucene_clustering.xml;


// @author Maximilian Louis
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.jdom2.Element;
import org.jdom2.input.DOMBuilder;
import org.xml.sax.SAXException;

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
	 * Diese Methode liefert eine Map<String, String> mit den Inhalten des Item-Elements der übergebenen XML-Datei.
	 * Pfade mit \ wie z.B. C:\Users\ werden automatisch zu C:/Users/ konvertiert.
	 * @return  Map<String, String> mit allen Feldern von challen/item der XML
	 * @param Vollständiger Pfad zu einer XML-Datei
	 */
	public Map<String, String> parse(String path){
		File file = new File(path.replace("\\", "/"));
		try {
			doc = builder.parse(file);
			org.jdom2.Document jDoc = domBuilder.build(doc);
			root = jDoc.getRootElement(); 
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		Element item = root.getChild("channel").getChild("item");
		Map<String, String> map = new HashMap<String, String>();
		List<Element> elements = item.getChildren();
		for(Element element : elements){
			map.put(element.getName(), element.getValue());
		}
		return map;
	}

}