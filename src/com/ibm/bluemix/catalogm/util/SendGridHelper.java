package com.ibm.bluemix.catalogm.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.ibm.bluemix.catalogm.dao.EmailSubscriptionsDao;

public class SendGridHelper {
	List<String> emailAddressesList = new ArrayList<String>();
	String[] email2;
	String[] listOfCatalogUpdates;
	String[] listOfNotifications;
	String[] listOfDWArticles;
	String[] listOfStackOF;
	private String _emailFileName = "emails.txt";

	public SendGridHelper() {
		// Fill the email2 section here.
		try {
			/*StringBuffer sb = new StringBuffer();
			InputStream fstream = null;
			// fstream = new FileInputStream(_templateFileName);
			ClassLoader classLoader = Thread.currentThread()
					.getContextClassLoader();
			fstream = classLoader.getResourceAsStream(_emailFileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					fstream));
			List<String> lines = new ArrayList<String>();
			String line = null;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}*/
			
			EmailSubscriptionsDao emailDao = new EmailSubscriptionsDao();
			List<String> emailIds = emailDao.getSubscribedEmails();
			email2 = emailIds.toArray(new String[emailIds.size()]);

			// Close the input stream

			//br.close();
			
			int numOfEmails = email2.length;
			System.out.println("Email will be sent to : " + numOfEmails);
			printArray(email2);

			listOfCatalogUpdates = new String[numOfEmails];
			listOfNotifications = new String[numOfEmails];
			listOfDWArticles = new String[numOfEmails];
			listOfStackOF = new String[numOfEmails];

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void printArray(String[] email22) {
		
		
	}

	public String[] getListOfCatalogUpdates() {
		int i = 0;
		for (String string : email2) {
			listOfCatalogUpdates[i++] = "-sectiondatacatalogupdates-";
		}
		return listOfCatalogUpdates;
	}

	public String[] getListOfNotifications() {
		int i = 0;
		for (String string : email2) {
			listOfNotifications[i++] = "-sectiondatanotifications-";
		}
		return listOfNotifications;
	}

	public String[] getListOfDWArticles() {
		int i = 0;
		for (String string : email2) {
			listOfDWArticles[i++] = "-sectiondatadevworks-";
		}
		return listOfDWArticles;
	}

	public String[] getListOfStackOF() {
		int i = 0;
		for (String string : email2) {
			listOfStackOF[i++] = "-sectiondatasof-";
		}
		return listOfStackOF;
	}

	public String[] getEmail2() {
		return email2;
	}

}
