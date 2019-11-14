package com.jio.qa.base;

import static com.jio.qa.utils.Constants.CONFIGS.FIREFOX_DRIVER_PATH;
import static com.jio.qa.utils.Constants.CONFIGS.CHROME_DRIVER_PATH;
import static com.jio.qa.utils.Constants.CONFIGS.CONFIG_FILE_PATH;
import static com.jio.qa.utils.Constants.TIMECONSTANTS.IMPLICIT_WAIT;
import static com.jio.qa.utils.Constants.TIMECONSTANTS.PAGE_LOAD_TIME_OUT;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.BasicConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.jio.qa.utils.Logging;
import com.jio.qa.utils.WebEventListener;

public class TestBase implements Logging {
	
	protected static WebDriver driver;
    public Properties properties;
    EventFiringWebDriver eventDriver;
    WebEventListener eventListener; 
	protected String outputDirectory = System.getProperty("user.dir")+"/test-output/ExtentReport.html";
    
    public TestBase() {
		try {
            properties = new Properties();
            FileInputStream inputStream = new FileInputStream(CONFIG_FILE_PATH);
            properties.load(inputStream);
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
	}
    
    
    public void initialisation(){
        String browserName = properties.getProperty("browser");
        if(browserName.equals("chrome")){
            System.setProperty("webdriver.chrome.driver",CHROME_DRIVER_PATH);
            driver = new ChromeDriver();
        } else if(browserName.equals("firefox")){
        	System.setProperty("webdriver.gecko.driver", FIREFOX_DRIVER_PATH);
        	driver = new FirefoxDriver();
        } else {
        	getLogger().info("Browser not found");
        }
        registerEventListener();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIME_OUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
        driver.get(properties.getProperty("url"));
        configureLogs();
        Logging.configureLog4j();
    }
    
    private void registerEventListener() {
		eventDriver = new EventFiringWebDriver(driver);
        eventListener = new WebEventListener();
        eventDriver.register(eventListener);
        driver = eventDriver;
	}
    
    public static void configureLogs() {
    	BasicConfigurator.configure();
    }
    
    public void waitUntilElementVisible(WebDriver driver, WebElement element, int timeout) {
    	new WebDriverWait(driver, timeout)
    		.until(ExpectedConditions.visibilityOf((element)));
    }
    
    public void implicitWaitOnPageLoad() {
    	driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIME_OUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
    }
    
    public void scrollElementToViewAndClick(String xpath) {
    	for(int i=0;i<5; i++) {
    		try {
    			WebElement element = driver.findElement(By.xpath(xpath));
        		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(false);", element);
        		((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
        		//element.click();
        		break;
    		} catch(Exception e) {
    			getLogger().info(e.getMessage());
    		}
    	}
	}
    
    public void scrollElementIntoView(String xpath) {
    	WebElement element = driver.findElement(By.xpath(xpath));
    	((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
    
    public void switchToNewWindow() {
    	String parentWindow = driver.getWindowHandle();
    	Set<String> handles = driver.getWindowHandles();
    	for(String winHandle : handles){
    		if(!winHandle.equals(parentWindow)) {
    		    driver.switchTo().window(winHandle);
    		}
    	}
    }
    
    public void closeMutipleWindows() {
    	Set<String> handles = driver.getWindowHandles();
    	for(String winHandle : handles) {
    		driver.switchTo().window(winHandle);
    		driver.close();
    	}
    }
    
    @BeforeSuite
   	public void deleteExistingReport() {
   		try {
   			Files.deleteIfExists(Paths.get(outputDirectory));
   		}
   		catch(Exception e) {
   			e.printStackTrace();
   		}	
    }
    
    @AfterSuite
    public void afterSuiteMethod() {
    	driver.quit();
    }

}
