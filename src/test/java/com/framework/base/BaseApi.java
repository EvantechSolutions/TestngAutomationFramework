package com.framework.base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public class BaseApi extends BaseTest{

    protected RequestSpecification requestSpec;
    public boolean isApitest;

    @BeforeClass(alwaysRun = true)
    public void setupApi() {
        String baseUrl = "https://jsonplaceholder.typicode.com"; 

        RestAssured.baseURI = baseUrl;

        requestSpec = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(ContentType.JSON)
                .addHeader("Accept", "application/json")
                .build();

        System.out.println("API Base URL: " + baseUrl);
        isApitest=true;
    }
}
