package com.comviva.cdr.dao;

import com.comviva.cdr.util.TestLogger;
import com.comviva.product.Crbt;
import com.comviva.product.ProductInterface;
import com.comviva.product.Uber;

import ch.qos.logback.core.joran.spi.JoranException;

public class DaoFactory implements Runnable{
	
	static TestLogger logger = new TestLogger(DaoFactory.class);
	String product;
	String category;
	String message;
	String sourceIP;
	
	public DaoFactory(String product , String category , String message , String sourceIP){
		this.message = message;
		this.sourceIP = sourceIP;
		this.category = category;
		this.product = product;
	}
	@Override
	public void run(){
		ProductInterface dao = null;
		if(product.equalsIgnoreCase("Crbt")) {
			dao = new Crbt(category, message, sourceIP);
			try {
				dao.writeCdr();
			} catch (JoranException e) {
				e.printStackTrace();
				logger.error("Crbt Interface not called");
			}
		}
		else if(product.equalsIgnoreCase("Uber")) {
			dao = new Uber(category , message , sourceIP);
			try {
				dao.writeCdr();
			} catch (JoranException e) {
				e.printStackTrace();
				logger.error("Uber Interface not called");
			}
		}
	}

}
