package com.ibm.bluemix.catalogm.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class EmailTemplate {
	
	String _templateFileName = "catalog1.html";
	String _templateText = "";
	
	String _catalogChanges = "No Catalog Change was detected.";
	String _announcements = "No recent annoucement was found";
	String _devWorkArticles = "TEMP";
	String _stackOverflow = "TEMP";
	
	String _email2send = "";

	public EmailTemplate() {
		StringBuffer sb = new StringBuffer();
		InputStream fstream = null;
		//fstream = new FileInputStream(_templateFileName);
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		fstream = classLoader.getResourceAsStream(_templateFileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;
		//System.out.println(sb.toString());
		//Read File Line By Line
		try {
			while ((strLine = br.readLine()) != null)   {
			  // Print the content on the console
			  replaceData(strLine);	
			  sb.append(strLine);
			  //System.out.println("----->" + strLine);
			  try {
				Thread.currentThread().sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Close the input stream
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		_email2send = sb.toString();
	}

	private void replaceData(String _strLine) {
		// TODO Auto-generated method stub
		//Check if we find any of our string starting with __ and ending with __.
		int index = _strLine.indexOf("__catalog__");
		if(index!=-1){
			System.out.println("adding catalog updates");
			_strLine.replaceAll("__catalog__",get_catalogChanges());
			return;
		}
		
		index = _strLine.indexOf("__announce__");
		if(index!=-1){
			System.out.println("adding announcements");
			_strLine.replaceAll("__announce__",get_announcements());
			return;
		}
		
		index = _strLine.indexOf("__devworks__");
		if(index!=-1){
			System.out.println("adding devworks");
			_strLine.replaceAll("__devworks__",get_devWorkArticles());
			return;
		}

		index = _strLine.indexOf("__stackof__");
		if(index!=-1){
			System.out.println("adding stack overflow");
			_strLine.replaceAll("__stackof__",get_stackOverflow());
			return;
		}
		
}

	public String get_catalogChanges() {
		return _catalogChanges;
	}

	public void set_catalogChanges(String _catalogChanges) {
		this._catalogChanges = _catalogChanges;
	}

	public String get_announcements() {
		return _announcements;
	}

	public void set_announcements(String _announcements) {
		this._announcements = _announcements;
	}

	public String get_devWorkArticles() {
		return _devWorkArticles;
	}

	public void set_devWorkArticles(String _devWorkArticles) {
		this._devWorkArticles = _devWorkArticles;
	}

	public String get_stackOverflow() {
		return _stackOverflow;
	}

	public void set_stackOverflow(String _stackOverflow) {
		this._stackOverflow = _stackOverflow;
	}

	public String get_email2send() {
		return _email2send;
	}

	public void set_email2send(String _email2send) {
		this._email2send = _email2send;
	}
	

	
	public static void main(String[] args) {
		EmailTemplate em = new EmailTemplate();
		em.sendMail();
	}

	private void sendMail() {
		// TODO Auto-generated method stub
		System.out.println("+++++++++++++++++++++++++++");
		System.out.println(_email2send);		
		System.out.println("+++++++++++++++++++++++++++");
	}
	
}
