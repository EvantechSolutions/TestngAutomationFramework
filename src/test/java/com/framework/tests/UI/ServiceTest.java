package com.framework.tests.UI;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.framework.base.BaseTest;
import com.framework.pages.ServicesPage;

public class ServiceTest extends BaseTest {
	@Test(priority = 2)
	public void ServicePageValidation() throws Throwable {
		ServicesPage Servicespage = new ServicesPage(driver);
		String response = Servicespage.ServicesValidation();
		Assert.assertEquals(response, "Collaboration");
	}
}
