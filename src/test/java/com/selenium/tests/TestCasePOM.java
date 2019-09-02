package com.selenium.tests;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.common.utility.Log;
import com.selenium.framework.factory.BrowserFactory;
import com.selenium.framework.factory.BrowserName;
import com.selenium.pageObjects.SearchPage;
import com.selenium.pageObjects.SearchResultsPage;

public class TestCasePOM {
	
	private static WebDriver driver = null;
	public String baseUrl = "http://www.amazon.com";
	public String searchText = "calculator";


	//@Test
	public void amazonSearchAndVerify()
	{
		Log.startTestCase("TC: SeleniumWD POM Search and Verify");
		
		Log.info("1. Change the search type to Electronics");
		SearchPage.searchCategoryDropDownElement(driver).click();
		Select searchCategoryDropDown = new Select(SearchPage.searchCategoryDropDownElement(driver));
		searchCategoryDropDown.selectByVisibleText("Electronics");
		  
		Log.info("2. Enter the search term calculators");
		SearchPage.searchTextBox(driver).sendKeys(searchText);
//		SearchPage.searchTextBox(driver).sendKeys(searchText + "\n" + Keys.ENTER);
		  
		Log.info("3. Click the Search button");
		SearchPage.searchTextBox(driver).submit();
		
		Log.info("4. Sort the results by \"Average Customer Review\"");
		SearchPage.sortByDD(driver).click();
		SearchPage.sortByAvgCustRev(driver).click();
		  
		Log.info("5. Filter results to only show calculators in the range of $300 to $350");
		SearchPage.minPrice(driver).sendKeys("300");
		SearchPage.maxPrice(driver).sendKeys("350");
		SearchPage.goButton(driver).click();
		  
		Log.info("6. Save , all the search results into a List of WebElements");
		assertThat (SearchResultsPage.resultsCount(driver).getText(), containsString ("Electronics : $300-$350 : \"calculator\""));
		Log.info(SearchResultsPage.resultsCount(driver).getText());
			  
		Log.info("7. Use a for-each loop to assert that each result title contains the word calculator");
		List<WebElement> resultTitles = SearchResultsPage.resultTitles(driver);		
		for (WebElement resultTitle : resultTitles)
		{
		Log.info(resultTitle.getText());
		assertThat(resultTitle.getText().toLowerCase(), containsString(searchText.toLowerCase()));
		}

		Log.endTestCase("End Test Case");			 
	}
	
	@BeforeClass
	public void beforeClass() {
		  driver = BrowserFactory.getBrowser(BrowserName.CHROME);
		  Log.info("Open Browser with URL: " + baseUrl);
		  driver.manage().timeouts().implicitlyWait(10,  TimeUnit.SECONDS);
		  driver.get(baseUrl);
	}
	
	@AfterClass
	public void afterClass() {
		driver.close();
	}

}
