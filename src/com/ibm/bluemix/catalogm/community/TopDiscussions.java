package com.ibm.bluemix.catalogm.community;

import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.zip.GZIPInputStream;

import org.xml.sax.InputSource;

public class TopDiscussions {
	
	public List<Discussion> readStackoverflowFeeds() {
		List<Discussion> topDiscussions = new ArrayList<Discussion>();
		
		try {
			//URLConnection openConnection = new URL("http://stackoverflow.com/feeds/tag?tagnames=ibm-bluemix&sort=newest").openConnection();
			URLConnection openConnection = new URL("https://stackoverflow.com/feeds/tag?tagnames=ibm-bluemix&amp;sort=newest").openConnection();
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
	        	Discussion disc = new Discussion();
	            SyndEntry entry = (SyndEntry) entryIter.next();
	            System.out.println(entry.getTitle());
	            count++;
	            disc.setTitle(entry.getTitle());
	            disc.setAuthor(entry.getAuthor());
	            disc.setLink(entry.getLink());
	            disc.setPublishDate(entry.getPublishedDate().toString());
	            disc.setSummary(entry.getDescription().getValue());
	            
	            topDiscussions.add(disc);
	        }
		}  catch(Exception e) {
			e.printStackTrace();
		}
		
		return topDiscussions;
	}
}
