package de.hof_university.iisys.pp_vinf12.lucene_clustering.xml;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileIterator {
	
	List<String> fileList = new ArrayList<String>();
	File[] path; // Oberster Ordner über den iteriert wird
	
	public FileIterator(String path){
		this.path = new File(path).listFiles();
	}
	
	public List<String> getFileList() {
		return fileList;
	}

	public void listFiles(){
		listFiles(path);
	}
	
	//Speichert alle Dateien aller Unterordner in der Liste fileList
	private void listFiles(File[] files) {
	    for (File file : files) {
	        if (file.isDirectory()) {
	            listFiles(file.listFiles()); 
	        } else {
	            System.out.println(file.getAbsolutePath());
	            fileList.add(file.getAbsolutePath());
	        }
	    }
	}
	
}
