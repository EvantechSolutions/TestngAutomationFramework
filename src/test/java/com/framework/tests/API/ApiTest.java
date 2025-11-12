package com.framework.tests.API;

import com.framework.base.BaseApi;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class ApiTest extends BaseApi {
	@Test
	public void GetUsers() {
		Response res = given().spec(requestSpec).when().get("/users").then().extract().response();
		Assert.assertEquals(res.statusCode(), 200);
		System.out.println("First user: " + res.jsonPath().getString("[0].name"));
	}

	@Test
	public void CreatePostUser() {
		String body = "{ \"title\": \"foo\", \"body\": \"bar\", \"userId\": 1 }";
		Response res = given().spec(requestSpec).body(body).when().post("/posts").then().extract().response();
		Assert.assertEquals(res.statusCode(), 201);
		Assert.assertEquals(res.jsonPath().getString("title"), "foos");
	}
	@Test
	public void Users() {
	    throw new org.testng.SkipException("Skipping  Users test intentionally for demo");
	}
}
