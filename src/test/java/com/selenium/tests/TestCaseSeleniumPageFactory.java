package com.selenium.tests;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.common.utility.Log;
import com.selenium.pageFactory.SearchPageFactory;
import com.selenium.pageFactory.SearchResultsPageFactory;


public class TestCaseSeleniumPageFactory extends AppTest {
	
	@Test (priority = 4)
	public void amazonProductSearchAndVerifyPageFactory()
	{
		Log.startTestCase("TC: SeleniumWD using Page Factory Search and Verify");

		//searchPage can be alternatively initialized in SearchPageFactory constructor
		searchPage = PageFactory.initElements(driver, SearchPageFactory.class); 
		resultsPage = PageFactory.initElements(driver, SearchResultsPageFactory.class );
//		  ajaxElement = new AjaxElementLocatorFactory(driver, 100);
		
		Log.info("1. Change the search type to Electronics");
		searchPage.searchCategoryDropDownElement.click();
		Select searchCategoryDropDown = new Select(searchPage.searchCategoryDropDownElement);
		searchCategoryDropDown.selectByVisibleText("Electronics");
		  
		Log.info("2. Enter the search term calculators");
		searchPage.searchTextBox.clear();
		searchPage.searchTextBox.sendKeys(searchText);
//		searchPage.searchTextBox.sendKeys(searchText + "\n" + Keys.ENTER);
		  
		Log.info("3. Click the Search button");
		searchPage.searchTextBox.submit();
		
		Log.info("4. Sort the results by \"Average Customer Review\"");
		searchPage.sortByDD.click();
		searchPage.sortByAvgCustRev.click();
		  
		Log.info("5. Filter results to only show calculators in the range of $300 to $350");
		searchPage.minPrice.sendKeys("300");
		searchPage.maxPrice.sendKeys("350");
		searchPage.goButton.click();
		  
		Log.info("6. Save , all the search results into a List of WebElements");
		assertThat (resultsPage.resultsCount.getText(), containsString ("Electronics : $300-$350 : \"calculator\""));
		Log.info(resultsPage.resultsCount.getText());
			  
		Log.info("7. Use a for-each loop to assert that each result title contains the word calculator");
		List<WebElement> resultTitles = resultsPage.resultTitles;		
		for (WebElement resultTitle : resultTitles)
		{
		Log.info(resultTitle.getText());
		assertThat(resultTitle.getText().toLowerCase(), containsString(searchText.toLowerCase()));
		}

		Log.endTestCase("End Test Case");	
	}
}
