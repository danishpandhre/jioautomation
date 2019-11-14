package com.jio.qa.utils;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;

import com.jio.qa.base.TestBase;

public class TestUtils extends TestBase implements Logging{
	public static String takeScreenshotAtEndOfTest() throws IOException {
		String currentDirectory = System.getProperty("user.dir");
		TakesScreenshot scrShot = (TakesScreenshot) driver;
		File scrFile = scrShot.getScreenshotAs(OutputType.FILE);
		String filePath = currentDirectory + "/screenshots/" + System.currentTimeMillis() + ".png";
		File destination = new File(filePath);
		FileHandler.copy(scrFile, destination);
		return filePath;
	}
}
