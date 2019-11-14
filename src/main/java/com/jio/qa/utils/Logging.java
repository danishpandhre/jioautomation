package com.jio.qa.utils;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public interface Logging {
	default Logger getLogger() {
		return Logger.getLogger(getClass());
	}
	
	static void configureLog4j() {
		String log4jFilePath = System.getProperty("user.dir") + File.separator + "log4j.properties";
		PropertyConfigurator.configure(log4jFilePath);
	}

}
