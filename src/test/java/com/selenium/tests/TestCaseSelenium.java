package com.selenium.tests;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.common.utility.Log;
import com.selenium.framework.factory.BrowserFactory;
import com.selenium.framework.factory.BrowserName;

public class TestCaseSelenium {
	
  public WebDriver driver;
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
  
  @Test
  public void searchVerify() {
	  searchCategoryDropDownElement = driver.findElement(By.id("searchDropdownBox"));
	  searchTextBox = driver.findElement(By.id("twotabsearchtextbox"));
	  
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
	  sortByDD = driver.findElement(By.id("a-autoid-0-announce"));

	  Log.info("4. Sort the results by \"Average Customer Review\"");
	  sortByDD.click();
	  sortByAvgCustRev = driver.findElement(By.id("s-result-sort-select_3"));
	  sortByAvgCustRev.click();
	  
	  Log.info("5. Filter results to only show calculators in the range of $300 to $350");
	  minPrice = driver.findElement(By.id("low-price"));
	  maxPrice = driver.findElement(By.id("high-price"));
	  minPrice = (new WebDriverWait(driver,10)).until(ExpectedConditions.presenceOfElementLocated(By.id("low-price")));
	  maxPrice = (new WebDriverWait(driver,10)).until(ExpectedConditions.presenceOfElementLocated(By.id("high-price")));
	  minPrice.click();
	  minPrice.sendKeys("300");
	  maxPrice.click();
	  maxPrice.sendKeys("350");
	  goButton = driver.findElement(By.className("a-button-input"));
	  goButton.click();
	  
	  Log.info("6. Save , all the search results into a List of WebElements");
	  WebElement resultsCount = driver.findElement(By.className("sg-col-inner"));
	  assertThat(resultsCount.getText(), containsString("Electronics : $300-$350 : \"calculator\""));
	  Log.info(resultsCount.getText());
	  
	  Log.info("7. Use a for-each loop to assert that each result title contains the word calculator");
	  List<WebElement> resultTitles = driver.findElements(By.cssSelector("h2 a span"));
	  for (WebElement resultTitle : resultTitles)
	  {
		  Log.info(resultTitle.getText());
		  assertThat(resultTitle.getText().toLowerCase(), containsString(searchText.toLowerCase()));
	  }
  
	  Log.endTestCase("End Test Case");
  }
  
  @BeforeMethod
  public void beforeMethod() {
	  driver = BrowserFactory.getBrowser(BrowserName.CHROME);
	  Log.info("Open Browser with URL: " + baseUrl);
	  driver.manage().timeouts().implicitlyWait(10,  TimeUnit.SECONDS);
	  driver.get(baseUrl);
  }

  
  @AfterMethod
  public void afterMethod() {
	  BrowserFactory.closeAllDrivers();
  }
}
