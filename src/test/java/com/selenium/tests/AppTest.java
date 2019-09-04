package com.selenium.tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;

import com.common.utility.Log;
import com.selenium.framework.factory.BrowserFactory;
import com.selenium.framework.factory.BrowserName;
import com.selenium.objectsRepository.ObjectRepoParser;
import com.selenium.pageFactory.SearchPageFactory;
import com.selenium.pageFactory.SearchResultsPageFactory;

public class AppTest {
	
  public WebDriver driver;
  public String baseUrl = "http://www.amazon.com";
  public String searchText = "calculator";
  public ObjectRepoParser objRepoParser;
  public SearchPageFactory searchPage; 
  public SearchResultsPageFactory resultsPage;
//  public AjaxElementLocatorFactory ajaxElement;
  
	public AppTest() {
		  driver = BrowserFactory.getBrowser(BrowserName.CHROME);
		  Log.info("Open Browser with URL: " + baseUrl);
		  driver.manage().timeouts().implicitlyWait(10,  TimeUnit.SECONDS);
		  driver.get(baseUrl);		  
	}
	
	@AfterTest
	public void tearDown() {
		Log.info("Close all Browsers");
		BrowserFactory.closeAllDrivers();
	}
}
