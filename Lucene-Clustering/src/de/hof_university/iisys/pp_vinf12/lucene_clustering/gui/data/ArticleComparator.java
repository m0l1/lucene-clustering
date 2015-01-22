package de.hof_university.iisys.pp_vinf12.lucene_clustering.gui.data;

import java.util.Comparator;

public class ArticleComparator implements Comparator<Article> {

	@Override
	public int compare(Article o1, Article o2) {
		
		if (o1 == null || o2 == null) {
			return 0;
		}
		return -(o1.getDate().compareTo(o2.getDate()));
	}

}
