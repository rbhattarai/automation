package com.selenium.tests;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.common.utility.Log;
import com.selenium.framework.factory.BrowserFactory;
import com.selenium.framework.factory.BrowserName;
import com.selenium.objectsRepository.ObjectRepoParser;

public class TestCaseSelenium {
	
  private WebDriver driver;
  private ObjectRepoParser objRepoParser;
  public String baseUrl = "http://www.amazon.com";
  public String searchText = "calculator";
  WebElement searchTextBox;
  WebElement searchButton;
  WebElement searchResults;
  WebElement searchCategoryDropDownElement;
  WebElement sortByDD;
  WebElement sortByAvgCustRev;
  WebElement minPrice;
  WebElement maxPrice;
  WebElement goButton;
  
//  @BeforeClass
  public void beforeClass() throws IOException {
	  driver = BrowserFactory.getBrowser(BrowserName.CHROME);
	  Log.info("Open Browser with URL: " + baseUrl);
	  driver.manage().timeouts().implicitlyWait(10,  TimeUnit.SECONDS);
	  driver.get(baseUrl);
	  objRepoParser = new ObjectRepoParser("ObjectRepo.properties");
  }

//  @AfterClass
  public void afterClass() {
	  BrowserFactory.closeAllDrivers();
  }
  
//  @Test
  public void searchVerify() {
	  searchCategoryDropDownElement = driver.findElement(objRepoParser.getObjectLocator("searchCategoryDropDownElement"));
	  searchTextBox = driver.findElement(objRepoParser.getObjectLocator("searchTextBox"));
	  
	  Log.startTestCase("TC: SeleniumWD Search and Verify");
	  
	  Log.info("1. Change the search type to Electronics");
	  searchCategoryDropDownElement.click();
	  Select searchCategoryDropDown = new Select(searchCategoryDropDownElement);
	  searchCategoryDropDown.selectByVisibleText("Electronics");
	  
	  Log.info("2. Enter the search term calculators");
	  searchTextBox.sendKeys(searchText);
//	  searchTextBox.sendKeys(searchText + "\n" + Keys.ENTER);
	  
	  Log.info("3. Click the Search button");
	  searchTextBox.submit();
	  sortByDD = driver.findElement(objRepoParser.getObjectLocator("sortByDD"));

	  Log.info("4. Sort the results by \"Average Customer Review\"");
	  sortByDD.click();
	  sortByAvgCustRev = driver.findElement(objRepoParser.getObjectLocator("sortByAvgCustRev"));
	  sortByAvgCustRev.click();
	  
	  Log.info("5. Filter results to only show calculators in the range of $300 to $350");
	  minPrice = driver.findElement(objRepoParser.getObjectLocator("minPrice"));
	  maxPrice = driver.findElement(objRepoParser.getObjectLocator("maxPrice"));
	  minPrice = (new WebDriverWait(driver,10)).until(ExpectedConditions.presenceOfElementLocated(By.id("low-price")));
	  maxPrice = (new WebDriverWait(driver,10)).until(ExpectedConditions.presenceOfElementLocated(By.id("high-price")));
	  minPrice.click();
	  minPrice.sendKeys("300");
	  maxPrice.click();
	  maxPrice.sendKeys("350");
	  goButton = driver.findElement(objRepoParser.getObjectLocator("goButton"));
	  goButton.click();
	  
	  Log.info("6. Save , all the search results into a List of WebElements");
	  WebElement resultsCount = driver.findElement(objRepoParser.getObjectLocator("resultsCount"));
	  assertThat(resultsCount.getText(), containsString("Electronics : $300-$350 : \"calculator\""));
	  Log.info(resultsCount.getText());
	  
	  Log.info("7. Use a for-each loop to assert that each result title contains the word calculator");
	  List<WebElement> resultTitles = driver.findElements(objRepoParser.getObjectLocator("resultTitles"));
	  for (WebElement resultTitle : resultTitles)
	  {
		  Log.info(resultTitle.getText());
		  assertThat(resultTitle.getText().toLowerCase(), containsString(searchText.toLowerCase()));
	  }
  
	  Log.endTestCase("End Test Case");
  }
}
