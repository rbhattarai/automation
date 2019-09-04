package com.selenium.tests;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.common.utility.Log;
import com.selenium.objectsRepository.ObjectRepoParser;

public class TestCaseSelenium extends AppTest {
	
  WebElement searchTextBox;
  WebElement searchButton;
  WebElement searchResults;
  WebElement searchCategoryDropDownElement;
  WebElement sortByDD;
  WebElement sortByAvgCustRev;
  WebElement minPrice;
  WebElement maxPrice;
  WebElement goButton;
  
	@Test (priority = 1)
	public void searchVerifyRawSelenium() {
	  Log.startTestCase("TC: Raw SeleniumWD AppTest Search and Verify");

	  searchCategoryDropDownElement = driver.findElement(By.id("searchDropdownBox"));
	  searchTextBox = driver.findElement(By.id("twotabsearchtextbox"));
	  
	  Log.info("1. Change the search type to Electronics");
	  searchCategoryDropDownElement.click();
	  Select searchCategoryDropDown = new Select(searchCategoryDropDownElement);
	  searchCategoryDropDown.selectByVisibleText("Electronics");
	  
	  Log.info("2. Enter the search term calculators");
	  searchTextBox.clear();
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
  
  @Test (priority = 2)
  public void searchVerifySeleniumUsingObjectRepo() throws IOException {
	  Log.startTestCase("TC: SeleniumWD using Object Repo Search and Verify");

	  objRepoParser = new ObjectRepoParser("ObjectRepo.properties");

	  searchCategoryDropDownElement = driver.findElement(objRepoParser.getObjectLocator("searchCategoryDropDownElement"));
	  searchTextBox = driver.findElement(objRepoParser.getObjectLocator("searchTextBox"));
	  
	  Log.info("1. Change the search type to Electronics");
	  searchCategoryDropDownElement.click();
	  Select searchCategoryDropDown = new Select(searchCategoryDropDownElement);
	  searchCategoryDropDown.selectByVisibleText("Electronics");
	  
	  Log.info("2. Enter the search term calculators");
	  searchTextBox.clear();
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
