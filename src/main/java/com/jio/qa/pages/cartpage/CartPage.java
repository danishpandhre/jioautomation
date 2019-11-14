package com.jio.qa.pages.cartpage;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.jio.qa.base.TestBase;

public class CartPage extends TestBase {
	
	@FindBy(xpath = "//div[@data-name='Subtotals']/preceding::input[@type='submit' and @value='Delete']")
	private List<WebElement> removeFromCart;
	
	@FindBy(xpath = "//span[@id='sc-subtotal-label-activecart']/preceding::span[@class='a-size-medium sc-product-title a-text-bold']")
	private WebElement productInShoppingCart;
	
	@FindBy(xpath = "//div[@data-name='Subtotals']/preceding::div[@data-action='delete']/span[@class='a-size-base']")
	private WebElement itemRemovalMsg;
	
	@FindBy(xpath = "//span[@class='a-size-medium sc-product-title a-text-bold']")
	private List<WebElement> cartLineItemsCount;
	
	public CartPage() {
		PageFactory.initElements(driver, this);
	}
	
	public void removeProductFromCart() {
		for(int i=0; i<removeFromCart.size(); i++) {
			implicitWaitOnPageLoad();
			removeFromCart.get(i).click();
		}
	}
	
	public String storeShoppingCardProducts() {
		String productName = productInShoppingCart.getText();
		return productName;
	}
	
	public int getCartLineItems() {
		int lineItemCount = cartLineItemsCount.size();
		return lineItemCount;
	}
}
