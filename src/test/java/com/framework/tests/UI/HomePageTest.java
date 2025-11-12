package com.framework.tests.UI;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.framework.base.BaseTest;
import com.framework.pages.HomePage;

public class HomePageTest extends BaseTest {
	@Test(priority = 1, groups = {"Smoke"})
	public void HomePageBannerTextValidation() throws Throwable {
		HomePage homepage = new HomePage(driver);
		String response = homepage.BannerValidation();
		Assert.assertEquals(response, "Innovative IT Solutions for Your Business Growth");
	}
}
