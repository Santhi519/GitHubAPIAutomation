package com.github.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;

import com.github.requestPOJO.CreateRepoRequest;
import com.github.requestPOJO.DeleteRepoRequest;
import com.github.requestPOJO.UpdateRepoRequest;
import com.github.responsePOJO.GetSingleRepoResponse;
//import org.techArk.requestPOJO.AddDataRequest;
//import org.techArk.requestPOJO.DeleteDataRequest;
//import org.techArk.requestPOJO.LoginRequest;
//import org.techArk.requestPOJO.UpdateDataRequest;
//import org.techArk.responsePOJO.LoginResponse;
import com.github.utils.EnvironmentDetails;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

@Slf4j
public class APIHelper {
   
	RequestSpecification reqSpec;

    public APIHelper() {
//    	System.out.println(""+EnvironmentDetails.getProperty("baseURL"));
        RestAssured.baseURI ="https://api.github.com/";
        reqSpec = RestAssured.given();
    }

    public Response getData() {
    	GetSingleRepoResponse  getDataResponse=null;
        reqSpec = RestAssured.given();
        reqSpec.headers("Authorization","Bearer "/* Mention the token here after the Bearer  +EnvironmentDetails.getProperty("token")*/);
        Response response = null;
        try {
        	
        	reqSpec.body(new ObjectMapper().writeValueAsString(getDataResponse));
			response=reqSpec.pathParam("OWNER", "Santhi519")
					.pathParam("REPO", "CucumberSalesforceFrameworkPOM")
					.get(RestAssured.baseURI+"repos/{OWNER}/{REPO}");
            response.then().log().all();
        } catch (Exception e) {
            Assert.fail("Get data is failing due to :: " + e.getMessage());
        }
        return response;
    }
    
    public Response getInvalidData() {
    	GetSingleRepoResponse  getDataResponse=null;
        reqSpec = RestAssured.given();
        reqSpec.headers("Authorization","Bearer"/* Mention the token here after the Bearer  +EnvironmentDetails.getProperty("token")*/);
        Response response = null;
        try {
        	
        	reqSpec.body(new ObjectMapper().writeValueAsString(getDataResponse));
			response=reqSpec.pathParam("OWNER", "Santhi519")
					.pathParam("REPO", "InvalidRepo")
					.get(RestAssured.baseURI+"repos/{OWNER}/{REPO}");
           response.then().log().all();
        } catch (Exception e) {
            Assert.fail("Get data is failing due to :: " + e.getMessage());
        }
        return response;
    }
    
    public Response getAllData() {
    	GetSingleRepoResponse  getDataResponse=null;
        reqSpec = RestAssured.given();
        reqSpec.headers("Authorization","Bearer" /* Mention the token here after the Bearer*/);  //+ EnvironmentDetails.getProperty("Token"));
        Response response = null;
        try {
        	
       	reqSpec.body(new ObjectMapper().writeValueAsString(getDataResponse));
			response=reqSpec.get(RestAssured.baseURI+"user/repos");
            response.then().log().all();
        } catch (Exception e) {
            Assert.fail("Get data is failing due to :: " + e.getMessage());
        }
        return response;
    }

    public Response addRepo(CreateRepoRequest repoRequest) {
        reqSpec = RestAssured.given();
        reqSpec.headers("Authorization","Bearer" /* Mention the token here after the Bearer*/); //+ EnvironmentDetails.getProperty("token"));
        Response response = null;
        try {
            log.info("Adding below data :: " + new ObjectMapper().writeValueAsString(repoRequest));
//            reqSpec.headers(getHeaders(false));
            reqSpec.body(new ObjectMapper().writeValueAsString(repoRequest)); //Serializing addData Request POJO classes to byte stream
            response = reqSpec.post(RestAssured.baseURI+"user/repos");
            response.then().log().all();
        } catch (Exception e) {
            Assert.fail("Add data functionality is failing due to :: " + e.getMessage());
        }
        return response;
    }
    
    public Response updateRepo(UpdateRepoRequest updateRequest) {
        reqSpec = RestAssured.given();
        reqSpec.headers("Authorization","Bearer "/* Mention the token here after the Bearer*/);  //"+ EnvironmentDetails.getProperty("token"));
//        reqSpec.headers(getHeaders(false));
        Response response = null;
        try {
            reqSpec.body(new ObjectMapper().writeValueAsString(updateRequest)); //Serializing addData Request POJO classes to byte stream
            response=reqSpec.pathParam("OWNER", "Santhi519")
					.pathParam("REPO", "NewRepo2")
					.put(RestAssured.baseURI+"repos/{OWNER}/{REPO}");
         
            response.then().log().all();
        } catch (Exception e) {
            Assert.fail("Update data functionality is failing due to :: " + e.getMessage());
        }
        return response;
    }

    public Response deleteRepo(DeleteRepoRequest deleteRequest) {
        reqSpec = RestAssured.given();
        reqSpec.headers("Authorization","Bearer "/* Mention the token here after the Bearer*/);  //"+ EnvironmentDetails.getProperty("token"));
//        reqSpec.headers(getHeaders(false));
        Response response = null;
        try {
            reqSpec.body(new ObjectMapper().writeValueAsString(deleteRequest)); //Serializing addData Request POJO classes to byte stream
            response=reqSpec.pathParam("OWNER", "Santhi519")
					.pathParam("REPO", "NewRepo3")
					.delete(RestAssured.baseURI+"repos/{OWNER}/{REPO}");
           
            response.then().log().all();
        } catch (Exception e) {
            Assert.fail("Delete data functionality is failing due to :: " + e.getMessage());
        }
        return response;
    }


//    public HashMap<String, String> getHeaders(boolean forLogin) {
//        HashMap<String, String> headers = new HashMap();
//        headers.put("Content-Type", "application/json");
//        if (!forLogin) {
//            headers.put("Authorization", "Bearer " + EnvironmentDetails.getProperty("token"));
//        }
//        return headers;
//    }

}
