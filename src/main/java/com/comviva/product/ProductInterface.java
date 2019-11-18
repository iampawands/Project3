package com.comviva.product;

import ch.qos.logback.core.joran.spi.JoranException;

public interface ProductInterface {
	
	void writeCdr() throws JoranException;
	
}
