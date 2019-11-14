package com.jio.qa.pages.homepage;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.jio.qa.base.TestBase;
import com.jio.qa.pages.cartpage.CartPage;

public class HomePage extends TestBase {
	
	@FindBy(xpath = "//select[@id='searchDropdownBox']")
	private WebElement allCatTxt;
	
	@FindBy(id = "twotabsearchtextbox")
	private WebElement searchBar;
	
	@FindBy(xpath = "//div[@class='nav-search-submit nav-sprite']/descendant::input[@type='submit']")
	private WebElement searchButton;
	
	@FindBy(xpath = "//div[@class='a-section a-spacing-small a-spacing-top-small']/descendant::span[1]")
	private WebElement results;
	
	@FindBy(xpath = "//span[@class='a-dropdown-prompt']")
	private WebElement sortByBtn;
	
	@FindBy(xpath = "//div[@class='a-popover-wrapper']/descendant::a[text()='Price: Low to High']")
	private WebElement lowToHighPriceFilter;
	
	@FindBy(xpath = "//span[@class='a-price-whole']")
	private List<WebElement> productCost;
	
	@FindBy(id = "add-to-cart-button")
	private WebElement addToCartBtn;
	
	@FindBy(id = "hlb-view-cart-announce")
	private WebElement cart;
	
	@FindBy(xpath = "//*[@class='a-size-medium a-text-bold']")
	private WebElement addedToCart;
	
	@FindBy(xpath = "//i[@aria-label='Prime Eligible']/preceding::i[@class='a-icon a-icon-checkbox']")
	private WebElement primeFilterChk;
	
	@FindBy(xpath = "//span[@class='a-size-medium a-color-base a-text-normal']/following::i[@aria-label='Amazon Prime']")
	private List<WebElement> primeItemsList;
	
	@FindBy(xpath = "//span[contains(text(),'Get it')]")
	private List<WebElement> nonPrimeItemsList;
	
	public String searchedProductLatest = "//div[@class='a-section a-spacing-medium']/descendant::span[@class='a-size-medium a-color-base a-text-normal' and text()='%s']/parent::a";
	
	public HomePage() {
		PageFactory.initElements(driver, this);
	}
	
	public void navigateToAllDropdown() {
		allCatTxt.click();
	}
	
	public void searchProduct(String productName) {
		searchBar.sendKeys(productName);
		searchButton.click();
	}
	
	public String getSearchResults() {
		String searchResults = results.getText();
		return searchResults;
	}
	
	public List<WebElement> retrieveAllCategories() {
		Select allOptions = new Select(allCatTxt);
		List<WebElement> elementCount = allOptions.getOptions();
		return elementCount;
	}
	
	public void clickOnSortByOption() {
		sortByBtn.click();
	}
	
	public void selectLowToHighPriceFilter() {
		lowToHighPriceFilter.click();
	}
	
	public List<String> getProductCost() {
		List<String> allProductCost = new ArrayList<String>();
		for(int i=0; i< productCost.size(); i++) {
			String value = productCost.get(i).getText();
			allProductCost.add(value);
		}
		return allProductCost;
	}
	
	public void selectAndClickOnSearchedProduct(String productName) {
		formatXpath(productName);
	}
	
	public void addToCart() {
		addToCartBtn.click();
	}
	
	public CartPage navigateToUsersCart() {
		waitUntilElementVisible(driver, addedToCart, 10);
		cart.click();
		return new CartPage();
	}
	
	public void enabledPrimeFilter() {
		boolean isEnabled = primeFilterChk.isSelected();
		if(!isEnabled)
			primeFilterChk.click();
	}
	
	public int getAllPrimeItems( ) {
		return primeItemsList.size(); 
	}
	
	public int getAllNonPrimeItems() {
		return nonPrimeItemsList.size();
	}
	
	private void formatXpath(String pName) {
		String dynamicPath = String.format(searchedProductLatest, pName);
		scrollElementToViewAndClick(dynamicPath);
	}
	

}
