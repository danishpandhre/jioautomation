package com.jio.qa.ui.homepage;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jio.qa.base.TestBase;
import com.jio.qa.base.homepage.HomePageBaseTest;
import com.jio.qa.utils.Logging;

public class HomePageTest extends TestBase implements Logging {
	HomePageBaseTest homePageBase;
	
	public HomePageTest() {
		super();
	}
	
	@BeforeMethod
	public void setUp() {
		initialisation();
		homePageBase = new HomePageBaseTest();
	}
	
	@Test(priority=1)
	public void testGetAllAmazonCategories() throws InterruptedException {
		homePageBase.getAllAmazonCategoriesMethod();
	}
	
	@Test(priority=2)
	public void testSearchProductOnWebsite() {
		homePageBase.searchProductMethod();
	}
	
	@Test(priority=3)
	public void testLowToHighPriceFilter() {
		homePageBase.sortLowToHighMethod();
	}
	
	@Test(priority=4)
	public void testAddTwoLineItemsToCartAndVerify() {
		homePageBase.addLineItemsToCartAndVerify();
	}
	
	@Test(priority=5)
	public void testDisplayOfPrimeEnabledItems() {
		homePageBase.addPrimeFilterAndVerify();
	}
	
	@AfterMethod
	public void tearDown() {
		driver.close();
	}
}
