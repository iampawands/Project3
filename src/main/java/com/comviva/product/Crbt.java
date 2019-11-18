package com.comviva.product;

import com.comviva.cdr.category.category2;
import com.comviva.cdr.category.category1;
import com.comviva.cdr.dao.IDao;
import com.comviva.cdr.util.TestLogger;

import ch.qos.logback.core.joran.spi.JoranException;

public class Crbt implements ProductInterface{
	static TestLogger logger = new TestLogger(Crbt.class);
	private String category;
	private String message;
	private String sourceIP;
	
	public Crbt(String category , String message , String sourceIP) {
		this.category = category;
		this.message = message;
		this.sourceIP = sourceIP;
	}
	
	public void writeCdr() throws JoranException {
		IDao dao = null;
		if(category.equalsIgnoreCase("category1")) {
			dao = new category1(message , sourceIP , logger);
			dao.writeCdrInFile();
		}
		else if(category.equalsIgnoreCase("category2")) {
			dao = new category2(message , sourceIP , logger);
			dao.writeCdrInFile();
		}
		else {
			logger.error("Invalid Message");
		}
	}

}
