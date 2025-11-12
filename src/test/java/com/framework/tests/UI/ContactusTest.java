package com.framework.tests.UI;

import java.util.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.framework.base.BaseTest;
import com.framework.pages.contactUsPage;
import Utility.DataManager;
import Utility.DataMangerSetup;

public class ContactusTest extends BaseTest {
	@Test(priority = 3)
	public void FormSubmission() throws Throwable {
		contactUsPage contactus = new contactUsPage(driver);
		contactus.clickContactUs();
		String projectPath = System.getProperty("user.dir");
		String filePath = projectPath + "/src/main/resources/1000_F_634676819_b1bxBejIyThDWBIkga2w3Cl0gTyyOSPL.jpg";
		DataManager dataManager = DataMangerSetup.getDataManager("csv",
				projectPath + "/src/main/resources/TestData/DataTest.csv", null, null);
		List<Map<String, String>> allData = dataManager.getAllTestData("Sheet1");
		List<String> responses = contactus.dropUs(allData, filePath);
		for (String response : responses) {
			Assert.assertEquals(response, "Thank you for your inquiry! We will get back to you within 24 hours.");
		}
	}
}
