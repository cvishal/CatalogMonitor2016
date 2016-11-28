package com.ibm.bluemix.catalogm.util;
import java.util.List;

import com.ibm.bluemix.catalogm.community.Discussion;
import com.ibm.bluemix.catalogm.devworks.Article;
import com.ibm.bluemix.catalogm.notifications.json.OneNotification;
import com.sendgrid.*;

public class EmailClient {
	
	String messageText = "<html> <head> <meta http-equiv=Content-Type content=\"text/html; charset=windows-1252\"> </head> <body lang=EN-US> <div class=WordSection1> <p class=MsoNormal>Hi.</p> <p class=MsoNormal style='border:none;padding:0in'> Welcome to Catalog Monitor Utility. Please note recent updates to IBM Bluemix Catalog.</p> </div>";
	int numberOfupdates = 0;
	private String catalogType = "Regular";
	private boolean someMajorErrorFlag = false;
	

	int numberOfAnnouncements = 0;
	int numberOfDiscussions = 0;
	int numberOfArticles = 0;

	String announcementMessage = "";
	String discussionsMessage = "";
	String articleMessage = "";

	
	private static final String adminemail = "cvishal@in.ibm.com";
	
	public EmailClient(String typeOfCatlog){
		catalogType = typeOfCatlog;// Regular_US or Experimental_US		
	}
	
	public void appendMessage(String msgLine){
		
		StringBuilder sb = new StringBuilder();
		sb.append("Update: "+(numberOfupdates+1));
		sb.append("</div>");
		sb.append("-> CATALOG : "+catalogType);
		sb.append(msgLine);
		sb.append("</div>");
		sb.append("++++++++++++++++++++");
		sb.append("<p class=MsoNormal>&nbsp;</p>");
		messageText = messageText+sb.toString();
		//System.out.println("------------> Append - Message = "+ messageText);
		numberOfupdates++;
	}



	public boolean hasChangesToNotify() {
		if(numberOfupdates==0){
			System.out.println("***** There are no changes observed. No Email will be sent..............................");
			return false;
		}else{
			System.out.println("***** There are "+numberOfupdates+" changes observed. Email will be sent ..............................");
			return true;
		}
		
	}


	public void sendEmail() {
		//End of the message will be added here
		StringBuilder sb = new StringBuilder();
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");
		messageText = messageText+sb.toString();
		sb = new StringBuilder();
		sb.append("<p class=MsoNormal>&nbsp;</p>");
		sb.append("Thank. Please note this is BETA Feature... Contact cvishal@in.ibm.com if you start getting too many mails......");
		sb.append("<p class=MsoNormal>&nbsp;</p>");
		messageText = messageText+sb.toString();
		System.out.println("Total updates are "+numberOfupdates);
		if(numberOfupdates>10){
			System.out.println("Some Problem in parser.................oops....");
			sendEmailWorker(true);
		} else {
			sendEmailWorker(false);
		}
		System.out.println("Email sent successfully");
	}



	private void sendEmailWorker(boolean errorFlag){
		SendGrid sendgrid = new SendGrid("Gp9O0Zbc0B", "ZOpWrLbHaGIk7206");
	    SendGrid.Email email = new SendGrid.Email();
	    email.addTo("cvishal@in.ibm.com");
	    email.addTo("spansari@in.ibm.com");
	    //email.addTo("vish.ac4@gmail.com");
	    if(!errorFlag && !someMajorErrorFlag){
//	    	email.addTo("smith_naik@in.ibm.com");	
//	    	email.addTo("harish_shenoy@in.ibm.com");
//	    	email.addTo("vmanoria@in.ibm.com");
//	    	email.addTo("masusarl@in.ibm.com");
//	    	email.addTo("sushilve@in.ibm.com");
//	    	email.addTo("senthilkb@in.ibm.com");
//	    	email.addTo("ravisrir@in.ibm.com");
//	    	email.addTo("murali.ts@in.ibm.com");
//	    	email.addTo("niskulka@in.ibm.com");
//	    	email.addTo("abalasu1@in.ibm.com");
//	    	email.addTo("pallavi.nishtala@in.ibm.com");
//	    	email.addTo("nnyamgon@in.ibm.com");
//	    	email.addTo("r_balasub@in.ibm.com");
//	    	email.addTo("gadrakat@in.ibm.com");
//	    	email.addTo("shivahr@in.ibm.com");
//	    	email.addTo("sasubraj@in.ibm.com");
//	    	email.addTo("debasis.das@in.ibm.com");
//	    	email.addTo("gigovind@in.ibm.com");
//	    	email.addTo("purohit@us.ibm.com");
//	    	email.addTo("nbhogal@in.ibm.com");
//	    	email.setSubject("** Updates: Bluemix US Catalog, New Announcements, DW Articles and StackOverflow Q&A ***");
	    	email.setSubject("** TEST MAIL ***");
	    }else{
	    	email.setSubject("**VISHAL..... Need to check there is some Error:  "+catalogType+" *** ");
	    }
	    email.setFrom(adminemail);
	    email.setHtml(messageText);
	    try {
			SendGrid.Response response = sendgrid.send(email);
		} catch (SendGridException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void thereIsAnError() {
		// TODO Auto-generated method stub
		someMajorErrorFlag=true;
	}
	
	public void processAnnouncements(String url2Monitor, List<OneNotification> listofAnnouncements) {
		if (listofAnnouncements.size() > 0) {
			announcementMessage = announcementMessage + "<br>";
			announcementMessage = announcementMessage + "-------------------------------------------------------------------------------<br>";
			announcementMessage = announcementMessage + "Please note the recent announcements to IBM Bluemix <br>";
			
			for (OneNotification notification : listofAnnouncements) {
				StringBuilder sb = new StringBuilder();
				sb.append("Announcement : " + (numberOfAnnouncements + 1));
				sb.append("</div>");
				sb.append("</div>");
				sb.append("<p class=MsoNormal>Announcement :"+notification.getObj().getTitle()+"</p><p class=MsoNormal>Description : "+notification.getObj().getText()+"</p>");//<p class=MsoNormal>Regions affected : "+notification.getObj().getRegionsAffected()[0]+"</p>");
				sb.append("</div>");
				sb.append("<p class=MsoNormal>&nbsp;</p>");
				announcementMessage = announcementMessage + sb.toString();
				//System.out.println("------------> Append announcement - Message = "+ messageText); 
				numberOfAnnouncements++;
			}
			announcementMessage = announcementMessage + "-------------------------------------------------------------------------------<br>";
			messageText = messageText + announcementMessage + "</div>";
		}
		
	}


	public void processTopDiscussions(List<Discussion> topDiscussions) {
		
			discussionsMessage = discussionsMessage + "<br>";
			discussionsMessage = discussionsMessage + "-------------------------------------------------------------------------------<br>";
			discussionsMessage = discussionsMessage + "Please note the recent Discussions on IBM Bluemix from StackOverflow Community : <br>";
			
			for (Discussion disc : topDiscussions) {
				StringBuilder sb = new StringBuilder();
				sb.append("Discussion : " + (numberOfDiscussions + 1));
				sb.append("</div>");
				sb.append("</div>");
				
				sb.append("<p class=MsoNormal>Discussion on  :" + disc.getTitle()+"</p><p class=MsoNormal>URL : "+ disc.getLink() +"</p>");
				
				sb.append("</div>");
				sb.append("<p class=MsoNormal>&nbsp;</p>");
				discussionsMessage = discussionsMessage + sb.toString();
				//System.out.println("------------> Append announcement - Message = "+ messageText); 
				numberOfDiscussions++;
			}
			
			discussionsMessage = discussionsMessage + "-------------------------------------------------------------------------------<br>";
			messageText = messageText + discussionsMessage + "</div>";
		
		
	}
	
	public void processPublishedArticles(List<Article> topArticles) {
		
			articleMessage = articleMessage + "<br>";
			articleMessage = articleMessage + "-------------------------------------------------------------------------------<br>";
			articleMessage = articleMessage + "Please note the recent Published Articles on IBM Bluemix : <br>";
			
			for (Article article : topArticles) {
				StringBuilder sb = new StringBuilder();
				sb.append("Article : " + (numberOfArticles + 1));
				sb.append("</div>");
				sb.append("</div>");
				
				sb.append("<p class=MsoNormal>Article on  :" + article.getTitle()+"</p><p class=MsoNormal>URL : "+ article.getLink() +"</p>");
				
				sb.append("</div>");
				sb.append("<p class=MsoNormal>&nbsp;</p>");
				articleMessage = articleMessage + sb.toString();
				//System.out.println("------------> Append announcement - Message = "+ messageText); 
				numberOfArticles++;
			}
			
			articleMessage = articleMessage + "-------------------------------------------------------------------------------<br>";
			messageText = messageText + articleMessage + "</div>";
		
		
	}

}
