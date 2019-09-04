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

public class TestCaseRestAssured {
	
	String baseURI = "https://reqres.in";
	String basePath =  "/api/users";
	
	public Response getResponse(String baseURI, String basePath)
	{
		  DOMConfigurator.configure("log4j.xml");
		  
		  Log.startTestCase("TC: RestAssured Get Details");
		  Log.info("Specify baseURI to RESTful WebService");
		  RestAssured.baseURI = baseURI;
		  
		  Log.info("Get RequestSpecification of request");
		  RequestSpecification httpRequest = RestAssured.given();
		  
		  Log.info("Request server and get response");
		  Response response = httpRequest.request(Method.GET, basePath);
		  
		  return response;
	}
	
	public String searchResponseUsingJsonPath(String jsonPathQuery)
	{
		JsonPath jp = this.getResponse(baseURI, basePath).jsonPath();
		return jp.getString(jsonPathQuery);
	}
	
	@Test (priority = 5)
	public void printResponse() {
			  
		Log.info("Print response body");
		String responseBody = getResponse(baseURI, basePath).getBody().asString();
		System.out.println("Response Body is => " + responseBody);
		  
		Log.endTestCase("End Test Case");
	}
  
	@Test (priority = 6, dependsOnMethods = { "printResponse" })
	public void verifyStatusCode() {
		DOMConfigurator.configure("log4j.xml");
		  
		int statusCode = this.getResponse(baseURI, basePath).getStatusCode();
		Log.info("Assert Status Code correct: " + statusCode);
		Assert.assertEquals(statusCode, 200 , "Correct status code returned");
		Log.endTestCase("End Test Case");
	}
  
	@Test  (priority = 7, dependsOnMethods = { "printResponse" })
	public void getUser()
	{
		System.out.println("User Name: " + this.searchResponseUsingJsonPath("data[2].last_name"));
	}
	  
}
