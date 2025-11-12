package com.framework.base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	protected WebDriver driver;
	protected ConfigReader config;

	@BeforeMethod(alwaysRun = true)
	public void setUp() {
		if (this instanceof BaseApi) {
            return;
        }
		config = new ConfigReader();
		String browser = config.getProperty("browser");
		String Headless = config.getProperty("headless");
		driver = DriverSetup.initDriver(browser, Headless);
		driver.get(config.getProperty("url"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		DriverSetup.quitDriver();
	}
}
