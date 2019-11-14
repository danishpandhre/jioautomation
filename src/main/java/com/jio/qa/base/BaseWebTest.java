package com.jio.qa.base;

import com.jio.qa.pages.cartpage.CartPage;
import com.jio.qa.pages.homepage.HomePage;

public class BaseWebTest {
	protected HomePage homePage;
	protected CartPage cartPage;
	
	protected TestBase testBase;
	
	protected BaseWebTest() {
		homePage = new HomePage();
		cartPage = new CartPage();
		
		testBase = new TestBase();
	}
}
