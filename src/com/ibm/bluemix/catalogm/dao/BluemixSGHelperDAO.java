package com.ibm.bluemix.catalogm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibm.bluemix.catalogm.notifications.json.EventTime;
import com.ibm.bluemix.catalogm.notifications.json.LastUpdate;
import com.ibm.bluemix.catalogm.notifications.json.Obj;
import com.ibm.bluemix.catalogm.notifications.json.OneNotification;
import com.ibm.bluemix.catalogm.notifications.json.RegionsAffected;


public class BluemixSGHelperDAO {
	private Connection connection;
	PreparedStatement preparedStatement;
	String tableName = "sgaccount";
	
	public BluemixSGHelperDAO(){
		connection = com.ibm.bluemix.catalogm.util.DbUtil.getConnection();
	}

	

    public String[] getSGAccountDetails(){
    	String[] rc = new String[2];
        try {
            preparedStatement = (PreparedStatement) connection.
                    prepareStatement("select * from "+tableName);
            ResultSet rs = (ResultSet) preparedStatement.executeQuery();

            //Assume that DB has only 1 entry
            if (rs.next()) {
            	rc[0] = rs.getString("name");
            	rc[0] = rs.getString("pw");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
	        if (preparedStatement != null) {
	        	try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    }
        return rc;
    }

}
