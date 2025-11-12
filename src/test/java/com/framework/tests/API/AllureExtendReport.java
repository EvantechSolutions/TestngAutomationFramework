package com.framework.tests.API;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.framework.base.BaseApi;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;

@Epic("Automation")
@Feature("User Service Tests")
public class AllureExtendReport extends BaseApi {
	@Test(description = "Verify Get Users API")
	@Story("Get Users Endpoint")
	public void GetUsers() {
		Response res = given().spec(requestSpec).when().get("/users").then().extract().response();
		Assert.assertEquals(res.statusCode(), 200, "Expected status code 200");
	}

	@Test(description = "Verify Create Post API")
	@Story("Create Post Endpoint")
	public void CreatePostUser() {
		String body = "{ \"title\": \"foo\", \"body\": \"bar\", \"userId\": 1 }";
		Response res = given().spec(requestSpec).body(body).when().post("/posts").then().extract().response();
		Assert.assertEquals(res.statusCode(), 201, "Expected status code 201");
	}

	@Test(description = "Skipped test for demo")
	@Story("Skip Scenario")
	public void Users() {
		throw new SkipException("Skipping Users test intentionally for demo purpose");
	}
}
