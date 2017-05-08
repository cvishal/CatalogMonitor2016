package com.ibm.bluemix.catalogm.devworks;

import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.zip.GZIPInputStream;

import org.xml.sax.InputSource;

public class PublishedArticles {
	
	public List<Article> readDevWorksFeeds() {
		List<Article> topArticles = new ArrayList<Article>();
		
		try {
			URLConnection openConnection = new URL("http://www.ibm.com/developerworks/views/global/rss/libraryview.jsp?site_id=1&contentarea_by=Cloud%20computing&topic_by=Bluemix&product_by=-1&search_by=&industry_by=-1&sort_by=Date&series_title_by=&type_by=All%20Types").openConnection();
			//openConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
			InputStream is = openConnection.getInputStream();
			
			if("gzip".equals(openConnection.getContentEncoding())){
				is = new GZIPInputStream(is);
			}			
			InputSource source = new InputSource(is);			
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(source);
			
	       
	        // Modify the feed title
	        String newTitle = feed.getTitle() + " (Warmed)";
	        feed.setTitle(newTitle);

	        // Iterate through feed items, adding a footer each item
	        Iterator entryIter = feed.getEntries().iterator();
	        int count = 1;
	        while (entryIter.hasNext() & count <= 10)
	        {
	        	Article article = new Article();
	            SyndEntry entry = (SyndEntry) entryIter.next();
	            System.out.println(entry.getTitle());
	            count++;
	            article.setTitle(entry.getTitle());
	            article.setLink(entry.getLink());
	            article.setPublishDate(entry.getPublishedDate().toString());
	            article.setDescription(entry.getDescription().getValue());
	            
	            topArticles.add(article);
	        }
		}  catch(Exception e) {
			e.printStackTrace();
		}
		
		return topArticles;
	}
	
}