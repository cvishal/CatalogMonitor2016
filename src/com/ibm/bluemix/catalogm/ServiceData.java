package com.ibm.bluemix.catalogm;

import com.ibm.bluemix.catalogm.dao.BluemixCatalog;
import com.ibm.bluemix.catalogm.dao.BluemixCatalogDao;

public class ServiceData {

	String seviceName;
	String catagory = "";
	String vendor = "";
	String desc = "";
	String stage = "";

	private BluemixCatalogDao catalogDao;

	/*
	 * sample for Object Storage
	 * 
	 * <a is="d-tile" class="tile sub-category-item  ibm_beta public"
	 * href="/catalog/object-storage/" data-type="service"
	 * data-tag="ibm_created" > <img class="tile-image" src=
	 * "//ace-catalog-production-20150723-163821.ng.bluemix.net/catalog/cache/2fb-2237476803/imgs/logos/build/dd8c95b9-a76b-4d2c-80c8-a6b5b2f88142-featured.svg"
	 * alt="Object Storage: The Object Storage service in Bluemix is based on
	 * SoftLayer Object Storage which in turn is based on OpenStack Swift. It
	 * has built-in support for provisioning independent object stores and it
	 * creates an individual subaccount per object store." onerror=
	 * "this.onError=null;this.src='//ace-catalog-production-20150723-163821.ng.bluemix.net/catalog/cache/3d6-3652049723/imgs/logos/servicedefault50.png';">
	 * <span class="tile-name">Object Storage</span><div
	 * class="tile-footer"><div class="tile-label hover-hide"> <span
	 * class="tile-provider-name">IBM</span><span
	 * class="tile-stage-name">Beta</span> </div><div class="button hover-show"
	 * >View More</div></div><div class="search-content hide"> <span
	 * class="category">Data &amp; Analytics</span><span
	 * class="service-tags">data_management,ibm_created,ibm_beta</span></div>
	 * 
	 * String seviceName = Object Storage String catagory = "service" String
	 * vendor = IBM String desc = Gain the visibility and control you need over
	 * your application. Determine the response time your users see, understand
	 * the performance and availability of the application components, and
	 * leverage analytics to keep your application up and performing well.
	 * String stage = Beta;
	 * 
	 * sample for M&A <a is="d-tile" class="tile sub-category-item  public"
	 * href="/catalog/monitoring-and-analytics/" data-type="service"
	 * data-tag="ibm_created" > <img class="tile-image" src=
	 * "//ace-catalog-production-20150723-163821.ng.bluemix.net/catalog/cache/c31-3790299466/imgs/logos/build/18664881-5aec-40b6-9177-29fac4b8d92c-featured.png"
	 * alt="Monitoring and Analytics: Gain the visibility and control you need
	 * over your application. Determine the response time your users see,
	 * understand the performance and availability of the application
	 * components, and leverage analytics to keep your application up and
	 * performing well." onerror=
	 * "this.onError=null;this.src='//ace-catalog-production-20150723-163821.ng.bluemix.net/catalog/cache/3d6-3652049723/imgs/logos/servicedefault50.png';">
	 * <span class="tile-name">Monitoring and Analytics</span><div
	 * class="tile-footer"><div class="tile-label hover-hide"> <span
	 * class="tile-provider-name">IBM</span></div> <div class=
	 * "button hover-show">View More</div></div><div class="search-content hide"
	 * > <span class="category">DevOps</span><span
	 * class="service-tags">ibm_created,bluemix_extensions,ibm_dedicated_public<
	 * /span></div>
	 *
	 * String seviceName = Monitoring and Analytics String catagory = "service"
	 * String vendor = IBM String desc = Gain the visibility and control you
	 * need over your application. Determine the response time your users see,
	 * understand the performance and availability of the application
	 * components, and leverage analytics to keep your application up and
	 * performing well. String stage = "";
	 */

	public ServiceData() {
	}

	public ServiceData(String __serviceData, boolean isExperimental) {
		 catalogDao = new BluemixCatalogDao(isExperimental);
		 setServiceData(__serviceData);
	}

	BluemixCatalog fetchEarlierDataFromDB() {
			BluemixCatalog catalog = catalogDao.getBluemixCatalogByServiceName(getSeviceName());
		if (catalog != null) {
			 //@@ REMOVAL - Update the DB for this service, saying "IsValidService" as TRUE
			catalogDao.setValidFlag("TRUE",getSeviceName());
			//@@ REMOVAL - DONE
			return catalog;
		}
		return null;
	}

	private void setServiceData(String __serviceData) {
		String endText = "</p>";
		String beginTextServiceName = "<p class=\"text__headline--catalog\"";
		String _tmpString = __serviceData.substring(__serviceData.indexOf(beginTextServiceName));
		
		System.out.println("ServicecData _tmpString : " + _tmpString);
		// Part 1
		// Sample <span class="tile-name">Apache Spark</span>
		int i = _tmpString.indexOf(beginTextServiceName) + beginTextServiceName.length();
		int j = _tmpString.indexOf(endText);
		if (i > 0) {
			String _tmpStringService = _tmpString.substring(i, j);
			System.out.println("ServicecData _tmpStringService : " + _tmpStringService);
			String [] arr = _tmpStringService.split(">");
			System.out.println("arr.length : " + arr.length);
			if (arr.length > 1)
				seviceName = arr[1];
			else
				System.out.println("Error in parsing...");
			
			setSeviceName(seviceName);
			System.out.println("snehal service name : " + seviceName);
		}

		// Part 2
		// <span class="category">Data &amp; Analytics</span>
		/*String beginTextCatName = "<span class=\"category\">";
		endText = "</span>";
		_tmpString = __serviceData.substring(__serviceData.indexOf(beginTextCatName));
		i = beginTextCatName.length();
		j = _tmpString.indexOf(endText);
		if (i > 0) {
			catagory = _tmpString.substring(i, j);
			setCatagory(catagory);
		}*/

		// Part 3
		// <span class="tile-provider-name">IBM</span><span
		// class="tile-stage-name">Beta</span>
		String beginTextProName = "<div class=\"provider-tag\"";
		endText = "</div>";
		if (__serviceData.indexOf(beginTextProName) != -1) {
			_tmpString = __serviceData.substring(__serviceData.indexOf(beginTextProName));
			i = beginTextProName.length();
			j = _tmpString.indexOf(endText);
			if (i > 0) {
				String tmpcat = _tmpString.substring(i, j);
				String [] arr = tmpcat.split(">");
				System.out.println("arr.length : " + arr.length);
				if (arr.length > 1)
					vendor = arr[1];
				
				setVendor(vendor);
				System.out.println("snehal vendor : " + vendor);
			}
		}
		

		// Part 4
		//Stages like GA, Beta or experimental
		String beginTextStageName = "<div class=\"stage-tag\"";
		endText = "</div>";
		int stageIndex = __serviceData.indexOf(beginTextStageName);
		if(stageIndex<0){
			setStage("GA");
		}else{
			_tmpString = __serviceData.substring(stageIndex);
			i = beginTextStageName.length();
			j = _tmpString.indexOf(endText);
			if (i > 0) {
				String tmpcat = _tmpString.substring(i, j);
				String [] arr = tmpcat.split(">");
				System.out.println("arr.length : " + arr.length);
				if (arr.length > 1)
					stage = arr[1];
				
				setStage(stage);
			}
		}
		System.out.println("snehal stage : " + stage);
		
	
		// Part 5
		String beginTextDescName = "<p class=\"text__desc--catalog\"";
		String endDescText = "</p>";
		if(__serviceData.indexOf(beginTextDescName) != -1) {
			String _descString = __serviceData.substring(__serviceData.indexOf(beginTextDescName));
			i = beginTextDescName.length();
			j = _descString.indexOf(endDescText);
			if (i > 0) {
				String tmpcat = _descString.substring(i, j);
				String [] arr = tmpcat.split(">");
				System.out.println("arr.length : " + arr.length);
				if (arr.length > 1)
					desc = arr[1];
				
				setDesc(desc);
				System.out.println("snehal desc : " + desc);
			}
		}
		
		
		/*int startAlt = __serviceData.indexOf(beginTextDescName);
		System.out.println("Start ALT is "+startAlt);
		//System.out.println("ServiceData"+ __serviceData);
		_tmpString = __serviceData.substring(startAlt);
		i = beginTextDescName.length();
		j = _tmpString.indexOf("onerror=");
		if (i > 0) {
			desc = _tmpString.substring(i, j);
			setDesc(desc);
		}*/
		
		System.out.println("Done creating Object for : " + seviceName);

	}

	void addIntoHistory() {
		BluemixCatalog catalog = new BluemixCatalog();
		catalog.setSeviceName(getSeviceName());
		catalog.setCatagory(getCatagory());
		catalog.setVendor(getVendor());
		catalog.setDesc(getDesc());
		catalog.setStage(getStage());
		//@@ REMOVAL -- Need to set "IsValidService" flag to TRUE
		catalog.setServiceValidFlag("TRUE");
		System.out.println("$$$$$$$$$ Adding "+getSeviceName()+". Catagory --> "+getCatagory()+". Vendor -> "+getVendor()+". Desc -> "+getDesc()+". and stage = "+getStage());
		catalogDao.addBluemixCatalog(catalog);
	}

	public String getSeviceName() {
		return seviceName;
	}

	public void setSeviceName(String seviceName) {
		this.seviceName = seviceName;
	}

	public String getCatagory() {
		return catagory;
	}

	public void setCatagory(String catagory) {
		this.catagory = catagory;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public void updateHistory() {
		BluemixCatalog catalog = new BluemixCatalog();
		catalog.setSeviceName(getSeviceName());
		catalog.setCatagory(getCatagory());
		catalog.setVendor(getVendor());
		catalog.setDesc(getDesc());
		catalog.setStage(getStage());
		System.out.println("$$$$$$$$$ Updating "+getSeviceName()+". Catagory --> "+getCatagory()+". Vendor -> "+getVendor()+". Desc -> "+getDesc()+". and stage = "+getStage());
		catalogDao.updateBluemixCatalog(catalog);
	}
}
