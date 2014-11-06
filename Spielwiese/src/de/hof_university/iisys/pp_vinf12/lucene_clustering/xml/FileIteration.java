package de.hof_university.iisys.pp_vinf12.lucene_clustering.xml;
import java.io.File;

public class FileIteration {
	
	public static void main(String[] args) {
	    File[] files = new File("C:/Users/Maxim_000/Schule/Semester 5/Eclipse/Praktikum Programmieren/rssfiles/germany").listFiles();
	    showFiles(files);
	}

	public static void showFiles(File[] files) {
	    for (File file : files) {
	        if (file.isDirectory()) {
	            showFiles(file.listFiles()); 
	        } else {
	            System.out.println(file.getAbsolutePath());
	        }
	    }
	}
	
}
