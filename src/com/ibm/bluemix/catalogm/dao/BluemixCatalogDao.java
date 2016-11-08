package com.ibm.bluemix.catalogm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibm.bluemix.catalogm.util.DbUtil;

public class BluemixCatalogDao {
	private Connection connection;
	PreparedStatement preparedStatement;
	String tableName = "blumix_catalog";
	
	public BluemixCatalogDao(boolean isExperimental){
		if(isExperimental){
			tableName = "blumix_catalog_exp";
		}
		connection = DbUtil.getConnection();
	}
	
	 public void addBluemixCatalog(BluemixCatalog catalog) {
	        try {
	        	preparedStatement = (PreparedStatement) connection
	                    .prepareStatement("insert into "+tableName+"(service_name,catagory,vendor,long_description,stage,ISSERVICEVALID) values (?, ?, ?, ? ,?,?)");
	            // Parameters start with 1
	            preparedStatement.setString(1, catalog.getSeviceName());
	            preparedStatement.setString(2, catalog.getCatagory());
	            preparedStatement.setString(3, catalog.getVendor());
	            preparedStatement.setString(4, catalog.getDesc());
	            //preparedStatement.setString(5, catalog.getStage());
	            preparedStatement.setString(5, "");
	            preparedStatement.setString(6, "TRUE");
	            preparedStatement.executeUpdate();
	            System.out.println("&&&&&&&&&&&&&&& DB New Record Inserted..... for "+catalog.getSeviceName());
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
	    }

	    public void deleteBluemixCatalog(int id) {
	        try {
	            PreparedStatement preparedStatement = (PreparedStatement) connection
	                    .prepareStatement("delete from "+tableName+" where id=?");
	            // Parameters start with 1
	            preparedStatement.setInt(1, id);
	            preparedStatement.executeUpdate();

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

	    }

	    public void updateBluemixCatalog(BluemixCatalog catalog) {
	        try {
	            preparedStatement = (PreparedStatement) connection
	                    .prepareStatement("update "+tableName+" set catagory=?, vendor=?, long_description=? , stage=?" +
	                            "where service_name=?");
	            // Parameters start with 1
	            preparedStatement.setString(1, catalog.getCatagory());
	            preparedStatement.setString(2, catalog.getVendor());
	            preparedStatement.setString(3, catalog.getDesc());
	            //preparedStatement.setString(4, catalog.getStage());
	            preparedStatement.setString(4, "");
	            preparedStatement.setString(5, catalog.getSeviceName());
	            preparedStatement.executeUpdate();
	            System.out.println("&&&&&&&&&&&&&&& DB Current Record updated..... for "+catalog.getSeviceName());
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
	    }

//	    public List<BluemixCatalog> getBluemixCatalogs() {
//	        List<BluemixCatalog> catalogs = new ArrayList<BluemixCatalog>();
//	        try {
//	            Statement statement = (Statement) connection.createStatement();
//	            ResultSet rs = (ResultSet) statement.executeQuery("select * from "+tableName+"");
//	            while (rs.next()) {
//	            	BluemixCatalog catalog = new BluemixCatalog();
//	            	catalog.setSeviceName(rs.getString("service_name"));
//	            	catalog.setCatagory(rs.getString("catagory"));
//	            	catalog.setDesc(rs.getString("vendor"));
//	            	catalog.setVendor(rs.getString("long_description"));
//	            	catalog.setStage(rs.getString("stage"));
//	            	catalogs.add(catalog);
//	            }
//	        } catch (SQLException e) {
//	            e.printStackTrace();
//	        }
//
//	        return catalogs;
//	    }

	    public BluemixCatalog getBluemixCatalogByServiceName(String serviceName) {
	    	BluemixCatalog catalog = null;
	        try {
	            preparedStatement = (PreparedStatement) connection.
	                    prepareStatement("select * from "+tableName+" where service_name=?");
	            preparedStatement.setString(1, serviceName);
	            ResultSet rs = (ResultSet) preparedStatement.executeQuery();

	            if (rs.next()) {
	            	catalog = new BluemixCatalog();
	            	catalog.setSeviceName(rs.getString("service_name"));
	            	catalog.setCatagory(rs.getString("catagory"));
	            	catalog.setVendor(rs.getString("vendor"));
	            	catalog.setDesc(rs.getString("long_description"));
	            	catalog.setStage(rs.getString("stage"));
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
	        printCatalog(catalog);
	        return catalog;
	    }
	    

		private void printCatalog(BluemixCatalog catalog) {
			//System.out.println("@@@ DB has returned.. ServiceName:"+catalog.getSeviceName()+". Catagory:"+catalog.getCatagory()+". Vendor:"+catalog.getVendor()+"@@@@@@@@@@");
			
		}


	    public void setValidFlag(String f) {
	        try {
	            preparedStatement = (PreparedStatement) connection
	                    .prepareStatement("UPDATE "+tableName+" set ISSERVICEVALID=?");
	            // Parameters start with 1
	            preparedStatement.setString(1, f);
	            preparedStatement.executeUpdate();

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

	    }


	    public void setValidFlag(String f,String serviceName) {
	        try {
	            preparedStatement = (PreparedStatement) connection
	                    .prepareStatement("UPDATE "+tableName+" set ISSERVICEVALID=? where service_name=?");
	            // Parameters start with 1
	            preparedStatement.setString(1, f);
	            preparedStatement.setString(2, serviceName);
	            preparedStatement.executeUpdate();

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

	    }


	    public List<BluemixCatalog> getInvalidServicesAndCleanFromDB() {
	    	List<BluemixCatalog> invalidServices = new ArrayList<BluemixCatalog>();
	        try {
	            preparedStatement = (PreparedStatement) connection
	                    .prepareStatement("SELECT * from "+tableName+" where ISSERVICEVALID=?");
	            // Parameters start with 1
	            preparedStatement.setString(1, "FALSE");
	            ResultSet rs = (ResultSet) preparedStatement.executeQuery();
				while (rs.next()) {
					BluemixCatalog catalog = new BluemixCatalog();
					catalog.setSeviceName(rs.getString("service_name"));
					catalog.setCatagory(rs.getString("catagory"));
					catalog.setDesc(rs.getString("long_description"));
					catalog.setVendor(rs.getString("vendor"));
					catalog.setStage(rs.getString("stage"));
					invalidServices.add(catalog);
					System.out.println("Service : "+catalog.getSeviceName()+" : Seems to be removed.... DB Entry would be removed.....");
				}

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        //Remove all invalid services.
	        try {
	            preparedStatement = (PreparedStatement) connection
	                    .prepareStatement("DELETE from "+tableName+" where ISSERVICEVALID=?");
	            // Parameters start with 1
	            preparedStatement.setString(1, "FALSE");
	            preparedStatement.executeUpdate();
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

	        return invalidServices;
	    }

}
