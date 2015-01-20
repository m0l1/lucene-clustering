package de.hof_university.iisys.pp_vinf12.lucene_clustering.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileLineLister {
	
	public List<String> listXMLFilesInFile(String filePath) throws FileNotFoundException{
		List<String> list= new ArrayList<String>();
		File file = new File(filePath);
		Scanner s = new Scanner(file);
		while (s.hasNext()){
		    list.add(s.next());
		}
		s.close();
		file.delete();
		
		return list;
	}
	
}
