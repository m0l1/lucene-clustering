package de.hof_university.iisys.pp_vinf12.lucene_clustering.gui.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.SimpleFSDirectory;

import de.hof_university.iisys.pp_vinf12.lucene_clustering.gui.data.Article;

public class ArticleAssembler {

	private String articleIndex;
	
	public ArticleAssembler(String articleIndex) {
		this.articleIndex = articleIndex;
	}

	public Article getArticle(String articleID) throws IOException {
		
		SimpleFSDirectory articleDir = new SimpleFSDirectory(new File(articleIndex));
		DirectoryReader articleReader = DirectoryReader.open(articleDir);
		IndexSearcher articleSearcher = new IndexSearcher(articleReader);
		articleSearcher.setSimilarity(new BM25Similarity());
		
		Query articleQuery = TermRangeQuery.newStringRange("articleID", articleID, articleID, true, true);
		ScoreDoc[] articleDocs = articleSearcher.search(articleQuery, 1).scoreDocs;
		
		Document doc = articleSearcher.doc(articleDocs[0].doc);
		
		return buildArticle(doc);
	}

	public List<Article> getArticles(String clusterID, String topArticleID) throws IOException {
		List<Article> articles = new ArrayList<Article>();
		
		SimpleFSDirectory articleDir = new SimpleFSDirectory(new File(articleIndex));
		DirectoryReader articleReader = DirectoryReader.open(articleDir);
		IndexSearcher articleSearcher = new IndexSearcher(articleReader);
		articleSearcher.setSimilarity(new BM25Similarity());
		
		Query articleQuery = new TermQuery(new Term("clusterID", clusterID));
		ScoreDoc[] articleDocs = articleSearcher.search(articleQuery, articleReader.numDocs()).scoreDocs;
		
		for (ScoreDoc articleDoc : articleDocs) {
			Document doc = articleSearcher.doc(articleDoc.doc);
			
			// Zur Liste hinzufuegen, ausser wenn es der
			// TopArticle mit der uebergebenen topArticleID ist
			if (!doc.getField("articleID").stringValue().equals(topArticleID)) {
				articles.add(buildArticle(doc));
			}
		}
		
		return articles;
	}

	private Article buildArticle(Document doc) {
		// TODO Auto-generated method stub
		return null;
	}

}
