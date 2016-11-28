package com.ibm.bluemix.catalogm.util;

import java.util.Set;

import com.ibm.nosql.json.api.BasicDBList;
import com.ibm.nosql.json.api.BasicDBObject;
import com.ibm.nosql.json.util.JSON;

public class VCAPUtils {

	String _dbName;
	String _dbUser;
	String _dbPW;
	String _dbHost;
	int _dbPort;
	String _dbUrl;

	public VCAPUtils() {
		// VCAP_SERVICES is a system environment variable
		// Parse it to obtain the for DB2 connection info
		String VCAP_SERVICES = System.getenv("VCAP_SERVICES");
		if (VCAP_SERVICES == null) {
			VCAP_SERVICES = "{\"cleardb\": [ { \"name\": \"CatalogDB\", \"label\": \"cleardb\", \"plan\": \"spark\",  \"credentials\": { \"jdbcUrl\": \"jdbc:mysql://us-cdbr-iron-east-04.cleardb.net/ad_9a7676bba3c7c97?user=b746b7c4ed97b8&password=e4bc0a01\", \"uri\": \"mysql://b746b7c4ed97b8:e4bc0a01@us-cdbr-iron-east-04.cleardb.net:3306/ad_9a7676bba3c7c97?reconnect=true\", \"name\": \"ad_9a7676bba3c7c97\", \"hostname\": \"us-cdbr-iron-east-04.cleardb.net\", \"port\": \"3306\", \"username\": \"b746b7c4ed97b8\", \"password\": \"e4bc0a01\" } } ] }";
		}
		if (VCAP_SERVICES != null) {
			// parse the VCAP JSON structure
			BasicDBObject obj = (BasicDBObject) JSON.parse(VCAP_SERVICES);
			String thekey = null;
			Set<String> keys = obj.keySet();
			System.out.println("Searching through VCAP keys");
			// Look for the VCAP key that holds the SQLDB information
			for (String eachkey : keys) {
				// Just in case the service name gets changed
				// to lower case in the future, use toUpperCase
				if (eachkey.toUpperCase().contains("CLEARDB")) {
					thekey = eachkey;
				}
			}
			if (thekey == null) {
				System.out.println("Cannot find any CLEARDB service in VCAP; exit");
				return;
			}
			BasicDBList list = (BasicDBList) obj.get(thekey);
			obj = (BasicDBObject) list.get("0");
			System.out.println("Service found: " + obj.get("name"));
			// parse all the credentials from the vcap env variable
			obj = (BasicDBObject) obj.get("credentials");
			_dbHost = (String) obj.get("hostname");
			//System.out.println("hostname = "+_dbHost);
			_dbName = (String) obj.get("name");
			//System.out.println("_dbName = "+_dbName);
			_dbPort = Integer.parseInt(obj.get("port").toString());
			//System.out.println("_dbPort = "+_dbPort);
			_dbUser = (String) obj.get("username");
			//System.out.println("_dbUser = "+_dbUser);
			_dbPW = (String) obj.get("password");
			//System.out.println("_dbPW = "+_dbPW);
			_dbUrl = (String) obj.get("jdbcUrl");
			//System.out.println("_dbUrl = "+_dbUrl);
		} else {
			System.out.println("VCAP_SERVICES is null");
		}
	}

	public String getDBUrl() {
		return _dbUrl;
	}

	public String getDBUser() {
		return _dbUser;

	}

	public String getDBPassword() {
		return _dbPW;
	}

	public String getDBName() {
		return _dbName;
	}

	public String getDBHost() {
		return _dbHost;
	}

	public int getDBPort() {
		return _dbPort;
	}

}
