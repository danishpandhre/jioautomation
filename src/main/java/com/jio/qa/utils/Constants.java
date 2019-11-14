package com.jio.qa.utils;

public interface Constants {
	
	public interface TIMECONSTANTS{
		long PAGE_LOAD_TIME_OUT = 30;
        long IMPLICIT_WAIT = 30;
	}
	
	public interface CONFIGS {
        String CONFIG_FILE_PATH = "src/main/java/com/jio/qa/config/config.properties";
        String CHROME_DRIVER_PATH = "src/main/java/com/jio/qa/resources/drivers/chromedriver/chromedriver.exe";
        String FIREFOX_DRIVER_PATH = "src/main/java/com/jio/qa/resources/drivers/firefoxdriver/geckodriver_64.exe";
	}

}
