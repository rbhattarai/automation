package com.restassured.tests;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

import com.common.utility.Log;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class RestUtils {
    public static String path; //Rest request path
 
    public static void setBaseURI (String baseURI){
        RestAssured.baseURI = baseURI;
    }
 
    public static void setBasePath(String basePathTerm){
        RestAssured.basePath = basePathTerm;
    }
 
    public static void resetBaseURI (){
        RestAssured.baseURI = null;
    }
 
    public static void resetBasePath(){
        RestAssured.basePath = null;
    }
 
    public static void setContentType (ContentType Type){
        given().contentType(Type);
    }
 
    public static void  createSearchQueryPath(String searchTerm, String jsonPathTerm, String param, String paramValue) {
        path = searchTerm + "/" + jsonPathTerm + "?" + param + "=" + paramValue;
    }
 
    public static Response getResponse() {
        return get(path);
    }
 
    public static JsonPath getJsonPath (Response res) {
        String json = res.asString();
        Log.info("returned json: " + json +"\n");
        return new JsonPath(json);
    }
}