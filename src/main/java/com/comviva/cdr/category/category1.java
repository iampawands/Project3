package com.comviva.cdr.category;

import com.comviva.cdr.dao.IDao;
import com.comviva.cdr.util.TestLogger;

public class category1 implements IDao{
	
	TestLogger logger;
	private String message;
	private String sourceIP;
	
	public category1(String message , String sourceIP , TestLogger logger){
		this.message = message;
		this.sourceIP = sourceIP;
		this.logger = logger;
		System.out.println("Download Constructor");
	}

	public void writeCdrInFile() {
		logger.info(message+" "+sourceIP);
		System.out.println("Download Written");
		
	}

}
