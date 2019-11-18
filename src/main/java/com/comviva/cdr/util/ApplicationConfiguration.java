package com.comviva.cdr.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ApplicationConfiguration {
	 private final static ApplicationConfiguration INSTANCE = new ApplicationConfiguration();
	 
	    public static ApplicationConfiguration getInstance() {
	        return INSTANCE;
	    }
	 
	    private static Properties configuration = new Properties();
	 
	    private static Properties getConfiguration() {
	        return configuration;
	    }
	 
	    public void initilize(final String file) {
	        InputStream in = null;
	        try {
	            in = new FileInputStream(new File(file));
	            configuration.load(in);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	 
	    public String getConfiguration(final String key) {
	        return (String) getConfiguration().get(key);
	    }
	 
	
	    
	    public List<String> getValidIp() {
			String ipString =(String) getConfiguration().get("validIPList");
			String[] arrOfIp = ipString.split(",");
			List<String> listOfIp = new ArrayList<String>();
			for (String str : arrOfIp)
				listOfIp.add(str);
			return listOfIp;
		}
	    
	    public List<String> getValidProduct(){
	    	String ipString = (String)getConfiguration().get("validProductList");
	    	String[] arrOfProduct = ipString.split(",");
	    	List<String> listOfProduct = new ArrayList<String>();
	    	for (String str : arrOfProduct)
	    		listOfProduct.add(str);
	    	return listOfProduct;
	    }
	    
	  

}
