package com.restassured.tests;

import org.apache.log4j.xml.DOMConfigurator;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.common.utility.Log;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured.*;
import io.restassured.matcher.RestAssuredMatchers.*;
import org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.hasItem;

public class TestCaseRestAssured {
	
	RequestSpecification httpRequest;
	Response response;
	String baseURI = "https://reqres.in";
	String basePath =  "/api/users";
	
	public Response getResponse(String baseURI, String basePath)
	{
		  DOMConfigurator.configure("log4j.xml");
		  
		  Log.info("Specify baseURI to RESTful WebService: " + baseURI);
		  RestAssured.baseURI = baseURI;
		  
		  Log.info("Get RequestSpecification of request");
		  httpRequest = RestAssured.given();
		  
		  Log.info("Request server and get response from path: " + basePath);
		  response = httpRequest.request(Method.GET, basePath);
		  
		  return response;
	}
	
	public String searchResponseUsingJsonPath(String jsonPathQuery)
	{
		JsonPath jp = this.getResponse(baseURI, basePath).jsonPath();
		return jp.getString(jsonPathQuery);
	}
	
	@Test (priority = 5)
	public void printResponse() {
		Log.startTestCase("TC: RestAssured Get Response");

		String responseBody = getResponse(baseURI, basePath).getBody().asString();
		Log.info("Response Body is => " + responseBody);
		  
		Log.endTestCase("End Test Case");
	}
  
	@Test (priority = 6, dependsOnMethods = { "printResponse" })
	public void verifyStatusCode() {
		Log.startTestCase("TC: RestAssured Verify Status Code");

		int statusCode = this.getResponse(baseURI, basePath).getStatusCode();
		Log.info("Assert Status Code correct: " + statusCode);
		
		Assert.assertEquals(statusCode, 200 , "Correct status code returned");
		
		Log.endTestCase("End Test Case");
	}
  
	@Test  (priority = 7, dependsOnMethods = { "printResponse" })
	public void getUser()
	{
		Log.startTestCase("TC: RestAssured Get Last Name of third user");
		Log.info("Get Last Name of third user: " + this.searchResponseUsingJsonPath("data[2].last_name"));
		
		Log.endTestCase("End Test Case");
	}
	
	//BDD Style
	@Test (priority = 8)
	public void givenUrl_whenJsonResponseHasEmailGivenValuesUnderKey_thenCorrect() {
		Log.startTestCase("TC: RestAssured BDD Search Value");

		String searchString = "janet.weaver@reqres.in";
		get(baseURI + basePath).then().assertThat()
		.body("data.email", hasItem(searchString));
		Log.info("Response contain email as: " + searchString);
		
		Log.endTestCase("End Test Case");
	}
	
	@Test (priority = 9)
	public void whenRequestHead_thenOK() {
		Log.startTestCase("TC: RestAssured BDD Verify  Status Code");

		when().request("HEAD", baseURI + basePath).then().statusCode(200);
		Log.info("Status code returned: 200");
		Log.endTestCase("End Test Case");
	}
	  
}
