package com.ibm.bluemix.catalogm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ibm.bluemix.catalogm.community.Discussion;
import com.ibm.bluemix.catalogm.community.TopDiscussions;
import com.ibm.bluemix.catalogm.dao.BluemixCatalog;
import com.ibm.bluemix.catalogm.dao.BluemixCatalogDao;
import com.ibm.bluemix.catalogm.devworks.Article;
import com.ibm.bluemix.catalogm.devworks.PublishedArticles;
import com.ibm.bluemix.catalogm.notifications.json.OneNotification;
import com.ibm.bluemix.catalogm.notifications.json.SingleNotification;
import com.ibm.bluemix.catalogm.util.NewEmailClient;
import com.ibm.bluemix.catalogm.util.DbUtil;


@Service
public class Scheduler {
	
	String __start ="<a class=\"tile";
	String __end ="</a>";
	String notificationURL = "https://status.ng.bluemix.net/api/notifications";
	
	boolean emailNeeded = false;
	
	
	
	//@Scheduled(cron="0 0/360 * * * ?")
	@Scheduled(cron="0 30 13 * * *" )
	//@Scheduled(fixedRate=43200000)
	//@Scheduled(fixedRate=86400000)
	public void catalogCheck(){
         URL url;
         String response = null;
         

         try {
             // get URL content

             String a="https://console.ng.bluemix.net/catalog/";
             url = new URL(a);
             URLConnection conn = url.openConnection();

             // open the stream and put it into BufferedReader
             BufferedReader br = new BufferedReader(
                                new InputStreamReader(conn.getInputStream()));

             String inputLine;
             StringBuilder sb = new StringBuilder();
             while ((inputLine = br.readLine()) != null) {
            	 sb.append(inputLine);
                 //System.out.println(inputLine);
             }
             response = sb.toString();
             br.close();
             checkAndNotify(response, false);
             System.out.println("Done");

         } catch (MalformedURLException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         } finally {
         	  DbUtil.closeConnection();         		
        }


	//Lets look at experimental one too 
	
//         try {
//             // get URL content
//
//             String a="https://console.ng.bluemix.net/catalog/labs/";
//             url = new URL(a);
//             URLConnection conn = url.openConnection();
//
//             // open the stream and put it into BufferedReader
//             BufferedReader br = new BufferedReader(
//                                new InputStreamReader(conn.getInputStream()));
//
//             String inputLine;
//             StringBuilder sb = new StringBuilder();
//             while ((inputLine = br.readLine()) != null) {
//            	 sb.append(inputLine);
//                 //System.out.println(inputLine);
//             }
//             response = sb.toString();
//             br.close();
//             checkAndNotify(response, true);
//             System.out.println("Done");
//
//         } catch (MalformedURLException e) {
//             e.printStackTrace();
//         } catch (IOException e) {
//             e.printStackTrace();
//         }

	
	}
	
	//https://console.stage1.ng.bluemix.net/catalog/
	private void checkAndNotify(String response, boolean __isExperimental) {
		emailNeeded = false;
		
        //We need to maintain a List of all serviceNames. 
        //With this list, will know if any of service appears twice in the catalog, we ignore the second catagory.
        //This is temp workaround. Otherwise everytime our prog think that service changed catagory and send same message again and again
        List<String> allServiceNames = new ArrayList<String>();

		
		String _cType;
		if(__isExperimental){
			_cType = "Experimental-Catalog-Bluemix-US";			
		}else{
			_cType = "Main-Catalog-Bluemix-US";
		}
        NewEmailClient email = new NewEmailClient(_cType);
//		System.out.println("############# respose from URL ##################");
//		System.out.println(response);
//		System.out.println("#################################################");
		
        
      //@@ REMOVAL - At the begining, we will set "IsServiceValid" flag to FALSE for all the services in DB.
      //@@ REMOVAL - Then, while chceking the service details, we will update the flag to ture.        
      //@@ REMOVAL - If need service is added the flag is set to true
      //@@ REMOVAL - At the last, we will have services flag to true (except once which are removed)
        BluemixCatalogDao dao = new BluemixCatalogDao(false);
        dao.setValidFlag("FALSE");
      //@@ REMOVAL - Done
        
		int servicesCount = 0;
		int startPoint = 0;
		
		String __category_start ="<div class=\"category-section ";
		String __category_end = "</div>";
		


while (response.indexOf(__category_start,startPoint) > 0) {
			
			startPoint = response.indexOf(__category_start);
			response = response.substring(startPoint);
			startPoint = 0;
			int endPoint = response.indexOf(__category_end);
			String __categoryData = response.substring(0, endPoint);
			response = response.substring(endPoint);
			
			//System.out.println("response : " + response);
			
			System.out.println("__categoryData : " + __categoryData);
			
			String beginTextCatName = "<span class=\"category-name\"";
			String endText = "</span>";
			String _tmpString = __categoryData.substring(__categoryData.indexOf(beginTextCatName));
			System.out.println("_tmpString : " + _tmpString);
			
			
			int i = beginTextCatName.length();
			int j = _tmpString.indexOf(endText);
			System.out.println("i : " + i+"");
			System.out.println("j : " + j+"");
			String category = "";
			if (i > 0) {
				String tmpcat = _tmpString.substring(i, j);
				System.out.println("tmpcat : " + tmpcat);
				String [] arr = tmpcat.split(">");
				System.out.println("arr.length : " + arr.length);
				if (arr.length > 1)
					category = arr[1];
				else
					System.out.println("Error in parsing...");
				System.out.println("snehal category : " + category);
			}
			
			startPoint = 0;
			System.out.println("response.indexOf(__category_start,startPoint) : " + response.indexOf(__category_start,startPoint));
			System.out.println("response.indexOf(__start,startPoint) : " + response.indexOf(__start,startPoint));
			while ((response.indexOf(__category_start,startPoint) > response.indexOf(__start,startPoint)) | (response.indexOf(__category_start,startPoint) == -1) & (response.indexOf(__start,startPoint) != -1)) {
				System.out.println("Inside second while...");
				startPoint = response.indexOf(__start);
				response = response.substring(startPoint);
				startPoint = 0;
				endPoint = response.indexOf(__end);
				String __serviceData = response.substring(0, endPoint);
			
				response = response.substring(endPoint);
				servicesCount++;
				//System.out.println(" ******** "+__serviceData);
				//ServiceData myservice = new ServiceData(__serviceData);
				ServiceData myservice = new ServiceData(__serviceData,__isExperimental);
				myservice.setCatagory(category);
				BluemixCatalog earlierData = myservice.fetchEarlierDataFromDB(); //		 //@@ REMOVAL HERE inside fetchEarlierDataFromDB()
				String currentServiceName = myservice.getSeviceName();
			
				if(earlierData!=null){				
					boolean dbUpdateNeeded = false;
					//Check if data is same or changed.
					if(!earlierData.getCatagory().equalsIgnoreCase(myservice.getCatagory())){
						//As a special case.... if 1 service is part of two catagory, then we take only first and ignore second catagory
						if(allServiceNames.contains(currentServiceName)){
							String msg = "Just FYI: We found that "+currentServiceName+ " is part of two catagories. 1."+earlierData.getCatagory()+" & 2."+myservice.getCatagory()+".";
							//email.appendMessage("<p>"+msg+"</p>");
							System.out.println(msg);
							continue;
						}
						//Change is catagory name.. Alert
						email.appendMessage("<p>Change in existing service :"+currentServiceName+"</p><p>Earlier Category : "+earlierData.getCatagory()+"</p><p>New Category : "+myservice.getCatagory()+"</p>");
						if(!dbUpdateNeeded) dbUpdateNeeded=true;
					}				
					if(!earlierData.getStage().equalsIgnoreCase(myservice.getStage())){
						//Change is Stage name.. Alert
						email.appendMessage("<p>Change in existing service :"+currentServiceName+"</p><p>Earlier Stage : "+earlierData.getStage()+"</p><p>New Stage : "+myservice.getStage()+"</p>");
						if(!dbUpdateNeeded) dbUpdateNeeded=true;
					}
					if(!earlierData.getDesc().equalsIgnoreCase(myservice.getDesc())){
						//Change is Description name.. Alert
						email.appendMessage("<p>Change in existing service :"+currentServiceName+"</p><p>Earlier Description : "+earlierData.getDesc()+"</p><p>New Description : "+myservice.getDesc()+"</p>");
						if(!dbUpdateNeeded) dbUpdateNeeded=true;
					}
					if(!earlierData.getVendor().equalsIgnoreCase(myservice.getVendor())){
						//Change is Vendor name.. Alert
						email.appendMessage("<p>Change in existing service :"+currentServiceName+"</p><p>Earlier Vendor : "+earlierData.getVendor()+"</p><p>New Vendor : "+myservice.getVendor()+"</p>");
						if(!dbUpdateNeeded) dbUpdateNeeded=true;
					}
					//At the end .. update the DB instance with new data.. 
					if(dbUpdateNeeded) {
						System.out.println("Service : "+earlierData.getSeviceName()+". Data is updated. Please look at the details in Mail.");
						emailNeeded = true;
						myservice.updateHistory();
					}
					
				}else{
					    email.appendMessage(getFormattedMessage(myservice));				    
					    System.out.println("Service : "+myservice.getSeviceName()+". IS NEW SERVICE..");
					    if(currentServiceName!=null && currentServiceName.equalsIgnoreCase("")){
					    	System.out.println("### VISHAL: Looks like Service name is coming as NULL.");
					    	System.out.println("### VISHAL: Desc is "+myservice.getDesc());
					    	email.thereIsAnError();
					    }else{
					    	emailNeeded = true;
					    	myservice.addIntoHistory();
					    }
						
				}
				//Service check is complete, lets add in allServicesNames.
				allServiceNames.add(currentServiceName);
			}
			
		}
		
		
		
		
		//@@ REMOVAL - Look at DB and check which service has "IsValidService" flag as FALSE.. report that one as Removed service.
		List<BluemixCatalog> removedServices = dao.getInvalidServicesAndCleanFromDB();
		for (BluemixCatalog removedService : removedServices) {
			emailNeeded = true;
			email.appendMessage(getFormattedMessageRemoval(removedService));			
		}
		
		
		
		//SNEHAL
		System.out.println("@@@@@@@@@@@@@ Done  with Catalog Updates..............");
		
		SingleNotification sn = new SingleNotification(notificationURL);
		List<OneNotification> listOfAnnouncements = sn.processAnnouncements();
		if(listOfAnnouncements.size()>0){
			emailNeeded = true;
		}
		email.processAnnouncements(notificationURL, listOfAnnouncements);
		System.out.println("Total Announcements : " + listOfAnnouncements.size());
		
		System.out.println("@@@@@@@@@@@@@ Done  with Notification Updates..............");
		

		//Add following only if there is any change in catalog or annoucements.
		if(!emailNeeded){
			System.out.println("Email is not needed as there is no change in catalog");
			System.out.println("Returning --> Total Services are = "+servicesCount);
			return;
		}
		
	
		System.out.println("@@@@@@@@@@@@@ Starting DeveloperWorks read ................");
		
		PublishedArticles articles = new PublishedArticles();
		List<Article> topArticles = articles.readDevWorksFeeds();
		email.processPublishedArticles(topArticles);
		System.out.println("DW Articles : " + topArticles.size());
		
		System.out.println("@@@@@@@@@@@@@ Done with DevloperWorks read ............");

		
		System.out.println("@@@@@@@@@@@@@ Starting Community read ................");
		
		TopDiscussions discs = new TopDiscussions();
		List<Discussion> topDiscussions = discs.readStackoverflowFeeds();
		email.processTopDiscussions(topDiscussions);
		System.out.println("Community Stack Overflow  : " + topDiscussions.size());
		
		System.out.println("@@@@@@@@@@@@@ Done with Community updates ............");

		//SNEHAL		
		
		if(emailNeeded){
			email.sendEmail();
		}else{
			System.out.println("Email is not needed as there is no change in catalog");
		}
		
		System.out.println("############## Number of services found = "+servicesCount);
	}

	private String getFormattedMessageRemoval(BluemixCatalog removedService) {
		return "<p>Service is REMOVED :</p><p> Name : "+removedService.getSeviceName()+"</p><p>Vendor : "+removedService.getVendor()+"</p><p>Description : "+ removedService.getDesc()+ "</p>";
	}


	private String getFormattedMessage(ServiceData __myService) {
		return "<p>New Service Found :</p><p> Name : "+__myService.seviceName+"</p><p>Category : "+__myService.getCatagory()+"</p><p>Stage : "+__myService.getStage()+"</p><p>Vendor : "+__myService.getVendor()+"</p><p>Description : "+ __myService.getDesc()+ "</p>";
	}
	
	public  void printSomething() {
		// TODO Auto-generated method stub
		System.out.println("Print Something.....");
	}

	}
