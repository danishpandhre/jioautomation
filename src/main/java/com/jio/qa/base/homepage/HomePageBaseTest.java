package com.jio.qa.base.homepage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.collections4.CollectionUtils;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;

import com.jio.qa.base.BaseWebTest;
import com.jio.qa.utils.Logging;

public class HomePageBaseTest extends BaseWebTest implements Logging {
	
	public final String PRODUCT_NAME = "usb hub 3.0";
	public static final String ORIGINAL_PRODUCT = "boAt Rockerz 255 Sports Bluetooth Wireless Earphone with Immersive Stereo Sound and Hands Free Mic (Neon)";
	public static final String CHANGED_PRODUCT = "boAt Bass Heads 225 in-Ear Headphones with Mic (Forest Green)";
	
	public void getAllAmazonCategoriesMethod() throws InterruptedException {
		List<String> allCategoriesList = new ArrayList<String>();
		homePage.navigateToAllDropdown();
		List<WebElement> allOptionsList = homePage.retrieveAllCategories();
		for(int i=0; i<allOptionsList.size(); i++) {
			String value = allOptionsList.get(i).getText();
			allCategoriesList.add(value);
		}
		getLogger().info("========= LIST OF CATEGORIES ON AMAZON WEBSITE =========" +allCategoriesList);
		Assert.assertTrue(allCategoriesList.size() > 0, "No Categories are displayed");
	}
	
	public void searchProductMethod() {
		homePage.searchProduct(PRODUCT_NAME);
		String message = homePage.getSearchResults();
		String[] arr = message.split("over");
		String[] count = arr[1].split("results");
		String productCount = count[0].trim();
		getLogger().info(" ========= TOTAL NUMBER OF PRODUCTS LISTED FOR THE SEARCH CRITERIA ========= " +productCount);
		List<Integer> pCount = removeCommaFromPrices(Arrays.asList(productCount));
		Assert.assertTrue(pCount.size() > 0, "No search results found");
	}
	
	public void sortLowToHighMethod() {
		homePage.searchProduct(PRODUCT_NAME);
		homePage.clickOnSortByOption();
		homePage.selectLowToHighPriceFilter();
		List<String> allPrices = homePage.getProductCost();
		getLogger().info("========= PRICE PRIOR TO COMMA REMOVAL =========" +allPrices);
		List<Integer> priceList = removeCommaFromPrices(allPrices);
		boolean isSorted = IntStream.range(1, priceList.size())
		        .map(index -> priceList.get(index - 1).compareTo(priceList.get(index)))
		        .allMatch(order -> order <= 0);
		getLogger().info("========= IS ASCENDING =========" +isSorted);
		Assert.assertTrue(isSorted, "Product Price is not displayed in ascending order "
				+ "after applying Low to High Price Filter");
	}
	
	public void addLineItemsToCartAndVerify() {
		List<String> productList = Arrays.asList(ORIGINAL_PRODUCT, CHANGED_PRODUCT);
		List<String> cardList = new ArrayList<String>();
		int counter = 0;
		for(int i=0; i<productList.size(); i++) {
			homePage.searchProduct(productList.get(i));
			homePage.selectAndClickOnSearchedProduct(productList.get(i));
			testBase.switchToNewWindow();
			homePage.addToCart();
			cartPage = homePage.navigateToUsersCart();
			cardList.add(cartPage.storeShoppingCardProducts());
			counter++;
		}
		
		Assert.assertEquals(cardList.size(), counter, "Number of items displayed in the shopping "
				+ "cart is incorrect");
		Assert.assertTrue(CollectionUtils.isEqualCollection(cardList, productList), ""
				+ "Incorrect items are present in the shopping cart");
		getLogger().info("====== LINE ITEMS INSIDE THE CART ARE ======" +cardList);
		cartPage.removeProductFromCart();
	}
	
	public void addPrimeFilterAndVerify() {
		homePage.searchProduct(PRODUCT_NAME);
		homePage.enabledPrimeFilter();
		int primeItemsSize = homePage.getAllPrimeItems();
		getLogger().info("===== List of Prime Items ====="+primeItemsSize);
		int nonPrimeItemsSize = homePage.getAllNonPrimeItems();
		getLogger().info("===== List of non-prime items ====="+nonPrimeItemsSize);
		Assert.assertFalse(nonPrimeItemsSize > 0, String.format("%s non-prime Item(s) are displayed on UI", nonPrimeItemsSize));
	}
	
	private List<Integer> removeCommaFromPrices(List<String> list) {
		List<String> commaSeparatedList = new ArrayList<String>();
		for(int i=0; i< list.size(); i++) {
			String str = list.get(i).replaceAll(",", "");
			commaSeparatedList.add(str);
		}
		getLogger().info("========= PRICE AFTER COMMA REMOVAL IS =========" +commaSeparatedList);
		return commaSeparatedList.stream().
				map(s-> Integer.parseInt(s)).collect(Collectors.toList());
	}
	
	
	
	
}
