package com.selenium.pageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class SearchPageFactory {
	
	final WebDriver driver;
	
	public SearchPageFactory(WebDriver driver)
	{
		this.driver = driver;
		//PageFactory.initElements() should be done to initialize all WebElements either in this construtor or in Test
//		PageFactory.initElements(driver, this);
	}
	
	@FindBy (id = "searchDropdownBox")
	public WebElement searchCategoryDropDownElement;
	//or
//	@FindBy (how = How.ID, using = "searchDropdownBox")
//	public WebElement searchCategoryDropDownElement;
	
	@FindBy (how = How.ID, using = "twotabsearchtextbox")
	public WebElement searchTextBox;
	
	@FindBy (how = How.ID, using = "a-autoid-0-announce")
	public WebElement sortByDD;
	
	@FindBy (how = How.ID, using = "s-result-sort-select_3")
	public WebElement sortByAvgCustRev;
	
	@FindBy (how = How.ID, using = "low-price")
	public WebElement minPrice;
	
	@FindBy (how = How.ID, using = "high-price")
	public WebElement maxPrice;

	@FindBy (how = How.CLASS_NAME, using = "a-button-input")
	public WebElement goButton;
}
