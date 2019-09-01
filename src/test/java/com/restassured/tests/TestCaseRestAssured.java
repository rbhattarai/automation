package com.restassured.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.common.utility.Log;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

import org.apache.log4j.xml.DOMConfigurator;

public class TestCaseRestAssured {
	
	String baseURI = "https://reqres.in";
	
  @Test
  public void getUsersDetails() {
	  DOMConfigurator.configure("log4j.xml");
	  
	  Log.startTestCase("TC: RestAssured Get Details");
	  Log.info("Specify baseURI to RESTful WebService");
	  RestAssured.baseURI = baseURI;
	  
	  Log.info("Get RequestSpecification of request");
	  RequestSpecification httpRequest = RestAssured.given();
	  
	  Log.info("Request server and get response");
	  Response response = httpRequest.request(Method.GET, "/api/users");
	  
	  Log.info("Print response body");
	  String responseBody = response.getBody().asString();
	  System.out.println("Response Body is => " + responseBody);
	  
	  Log.endTestCase("End Test Case");
  }
  
//  @Test (dependsOnMethods = { "getUsersDetails" })
  @Test
  public void verifyStatusCode() {
	  DOMConfigurator.configure("log4j.xml");
	  
	  Log.startTestCase("TC: RestAssured Verify Status Code");
	  Log.info("Specify baseURI to RESTful WebService");
	  RestAssured.baseURI = baseURI;
	  
	  Log.info("Get RequestSpecification of request");
	  RequestSpecification httpRequest = RestAssured.given();
	  
	  Log.info("Request server and get response");
	  Response response = httpRequest.request(Method.GET, "/api/Users");

	  int statusCode = response.getStatusCode();
	  Log.info("Assert Status Code correct: " + statusCode);
	  Assert.assertEquals(statusCode, 200 , "Correct status code returned");
	  Log.endTestCase("End Test Case");
  }
  
  @Test
  public void returnSpecificUser()
  {
	  DOMConfigurator.configure("log4j.xml");
	  
	  Log.startTestCase("TC: RestAssured Verify Specific Data");
	  Log.info("Specify baseURI to RESTful WebService");
	  RestAssured.baseURI = baseURI;
	  
	  Log.info("Get RequestSpecification of request");
	  RequestSpecification httpRequest = RestAssured.given();
	  
	  Log.info("Request server and get response");
	  Response response = httpRequest.request(Method.GET, "/api/users");
	  
	  JsonPath jp = response.jsonPath();
	  
	  String id = jp.getString("data[2].last_name");
	  
	  System.out.println("id: " + id);
  }
  
}
