package com.ibm.bluemix.catalogm.servlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.bluemix.catalogm.dao.EmailSubscriptionsDao;
import com.ibm.bluemix.catalogm.util.DbUtil;

@WebServlet("/monitorservlet")
public class MonitorServlet extends HttpServlet {
	
	final String subButton = "Subscribe";
	final String unsubButton = "Unsubscribe";
	final String callback_url = "w3sso-driwbcjm4n-co15.iam.ibmcloud.com/https://w3id.alpha.sso.ibm.com/auth/sps/samlidp/saml20/";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("SP: Inside MonitorServlet.doGet");
		String user = req.getUserPrincipal().getName();
		System.out.println("Authenticated User : " + user);
		String remuser = req.getRemoteUser();
		System.out.println("Authenticated User : " + remuser);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("SP: Inside MonitorServlet.doPost");
		String user = req.getUserPrincipal().getName();
		System.out.println("Authenticated User : " + user);
				
		user = user.split(callback_url)[1];
		System.out.println("Logged in User : " + user);
		
		String button = req.getParameter("button");
		System.out.println("Button : " + button);
		String msg = "";
		
		EmailSubscriptionsDao emailsub = new EmailSubscriptionsDao();
		
		if(button.equalsIgnoreCase(subButton))
			msg = emailsub.addSubscription(user);
		else if(button.equalsIgnoreCase(unsubButton))
			msg = emailsub.removeSubscription(user);
		
		DbUtil.closeConnection();
		
		req.setAttribute("message", msg);
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}

}
