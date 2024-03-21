package com.github.tests;

import com.github.javafaker.Faker;
import com.github.requestPOJO.CreateRepoRequest;
import com.github.requestPOJO.DeleteRepoRequest;
import com.github.requestPOJO.UpdateRepoRequest;
import com.github.responsePOJO.CreateRepoResponse;
import com.github.responsePOJO.DeleteRepoResponse;
import com.github.responsePOJO.GetSingleRepoResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.github.base.APIHelper;
import com.github.base.BaseTest;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import io.restassured.response.ResponseBodyData;

import org.apache.http.HttpStatus;
//import org.techArk.requestPOJO.AddDataRequest;
//import org.techArk.requestPOJO.DeleteDataRequest;
//import org.techArk.responsePOJO.AddDataResponse;
//import org.techArk.responsePOJO.GetDataResponse;
//import org.techArk.responsePOJO.LoginResponse;
//import org.techArk.responsePOJO.StatusResponse;
import com.github.utils.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.ARRAY_MISMATCH_TEMPLATE;

import java.util.List;

public class ValidateFunctionality extends BaseTest {
    APIHelper apiHelper;
    
    String status,full_name,default_branch,ContentType,message;
    String name,description,homepage;
    String owner,repo;
    boolean Private;

   @Test
    public void getsinglerepository() {
    
	  apiHelper = new APIHelper();
      Response data = apiHelper.getData();
      
      full_name=data.getBody().as(new TypeRef<GetSingleRepoResponse>() {}).getFull_name();
      System.out.println(full_name);
      default_branch=data.getBody().as(new TypeRef<GetSingleRepoResponse>() {}).getDefault_branch();
      Assert.assertEquals(data.getStatusCode(), HttpStatus.SC_OK, "Response code is matching for get data.");
      Assert.assertEquals(full_name, "Santhi519/CucumberSalesforceFrameworkPOM", "Full name is matching for get data.");
      Assert.assertEquals(default_branch, "master", "Default branch is matching for get data.");
      Assert.assertEquals(data.getContentType(), "application/json; charset=utf-8", "Content-type is matching for get data.");
    }
   @Test
   public void getinvalidrepository() {
	   apiHelper = new APIHelper();
	   Response data = apiHelper.getInvalidData();
	   message=data.getBody().as(new TypeRef<GetSingleRepoResponse>() {}).getMessage();
	   Assert.assertEquals(data.getStatusCode(), HttpStatus.SC_NOT_FOUND, "Response code is matching for get data.");
	   Assert.assertEquals(message, "Not Found", "Message displayed is matching with actual data.");
	   
   }
   @Test
   public void getAllrepositories() {
	   
	   apiHelper = new APIHelper();
	    Response data = apiHelper.getAllData();
	    Assert.assertEquals(data.getStatusCode(), HttpStatus.SC_OK, "Response code is matching for get data.");
	    Assert.assertEquals(data.getContentType(), "application/json; charset=utf-8", "Content-type is matching for get data.");
	    System.out.println("Total number of repositories are: "+data.jsonPath().get("name.size()"));
	    List<Integer> Repo_Names = data.body().jsonPath().getList("findAll{it->it.visibility=='public'}.full_name"); 
	    System.out.println("Repositories that are public: "+Repo_Names);
	    JsonSchemaValidate.validateSchema(data.asPrettyString(), "GetDataResponseSchema.json");
   
   }
    @Test(priority = 0, description = "validate add data functionality")
    public void validateAddDataFunctionality() {
    	apiHelper = new APIHelper();
    	name = "NewRepo"+"8";
    	description = "This is a new Repo";
    	homepage = "https://github.com";
    	Private = false;
        CreateRepoRequest repoRequest = CreateRepoRequest.builder().name(name).description(description).homepage(homepage).Private(Private).build();
        Response response = apiHelper.addRepo(repoRequest);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_CREATED, "Add data functionality is not working as expected.");
        Assert.assertEquals(response.as(CreateRepoResponse.class).getName(),"NewRepo8","Repo Name matched with the actual");
//        Assert.assertEquals(response.as(CreateRepoResponse.class).getLogin(),"Santhi519","Login Name matched with the actual");
//        Assert.assertEquals(response.as(CreateRepoResponse.class).getType(),"User","Type matched with the actual");
        String actual_login=response.jsonPath().get("owner.login");
        System.out.println(actual_login);
        String actual_type=response.jsonPath().get("owner.type");
        System.out.println(actual_type);
        Assert.assertEquals(actual_login,"Santhi519","Login Name matched with the actual");
        Assert.assertEquals(actual_type,"User","Type matched with the actual");

    }
    @Test
    public void validateAddInvalidDataFunctionality() {
//    	faker = new Faker();
    	apiHelper = new APIHelper();
    	name = "CucumberSalesforceFrameworkPOM";
    	description = "This is a new Repo";
    	homepage = "https://github.com";
    	Private = false;
        CreateRepoRequest repoRequest = CreateRepoRequest.builder().name(name).description(description).homepage(homepage).Private(Private).build();
        Response response = apiHelper.addRepo(repoRequest);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_UNPROCESSABLE_ENTITY, "Add data functionality is not working as expected.");
        String actual_message=response.jsonPath().get("errors[0].message");
        Assert.assertEquals(actual_message,"name already exists on this account","Message matched with the actual");
       
    }
    
    @Test(priority = 1, description = "Update data functionality")
    public void validateUpdatedData() {
    	
    	apiHelper = new APIHelper();
    	name = "NewRepo2";
    	description = "This is a new ****Updated**** Repo";
    	Private = false;
    	UpdateRepoRequest updateRequest = UpdateRepoRequest.builder().name(name).description(description).Private(Private).build();
        Response response = apiHelper.updateRepo(updateRequest);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_CREATED, "Update data functionality is working as expected.");
        Assert.assertEquals(response.as(CreateRepoResponse.class).getName(),"NewRepo2","Message matched with the actual");
    	
    }

    @Test(priority = 2, description = "delete data functionality")
    public void validateDeleteData() {
    	apiHelper = new APIHelper();
    	owner="Santhi519";
    	repo="NewRepo3";
        DeleteRepoRequest deleteRequest = DeleteRepoRequest.builder().owner(owner).repo(repo).build();
        Response data = apiHelper.deleteRepo(deleteRequest);
        System.out.println(data.getStatusCode());
        Assert.assertEquals(data.getStatusCode(), HttpStatus.SC_NO_CONTENT, "Delete data functionality is not working as expected.");
//       Assert.assertEquals(data.getBody().toString()," ","Body is not NULL");
        
    }
    
    @Test(description = "delete invalid data functionality")
    public void validateDeleteInvalidData() {
    	apiHelper = new APIHelper();
    	owner="Santhi519";
    	repo="New-Repocom.github.javafaker.Number";
    	
        DeleteRepoRequest deleteRequest = DeleteRepoRequest.builder().owner(owner).repo(repo).build();
        Response data = apiHelper.deleteRepo(deleteRequest);
        Assert.assertEquals(data.getStatusCode(), HttpStatus.SC_NOT_FOUND, "Delete data functionality is working as expected.");
        Assert.assertEquals(data.as(DeleteRepoResponse.class).getMessage(),"Not Found","Message matched with the actual");
        
    }


}
