package com.selenium.pageFactory;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SearchResultsPageFactory {
	final WebDriver driver;
	
	public SearchResultsPageFactory (WebDriver driver)
	{
		this.driver = driver;
	}
	
	@FindBy (how = How.CLASS_NAME, using = "sg-col-inner")
	public WebElement resultsCount;
	
	@FindBy (how = How.CSS, using = "h2 a span")
	public List<WebElement> resultTitles;
	
}
