package com.framework.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverSetup {
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	public static WebDriver initDriver(String browser, String Headless) {
		if(browser.equalsIgnoreCase(null)||browser.equals(""))
			browser="chrome";
		if (driver.get() == null) {
			switch (browser.toLowerCase()) {
			case "chrome":
				ChromeOptions chromeOptions = new ChromeOptions();
				if (Headless.toLowerCase().equals("true")) {
					chromeOptions.addArguments("--headless=new");
					chromeOptions.addArguments("--window-size=1920,1080");
					chromeOptions.addArguments("--disable-gpu");
					driver.set(new ChromeDriver(chromeOptions));
				} else {
					driver.set(new ChromeDriver());
				}
				break;
			case "edge":
				System.setProperty("webdriver.edge.driver", "src/main/resources/Drivers/msedgedriver.exe");
				EdgeOptions edgeOptions = new EdgeOptions();
				if (Headless.equalsIgnoreCase("true")) {
					edgeOptions.addArguments("--headless=new");
					edgeOptions.addArguments("--window-size=1920,1080");
					edgeOptions.addArguments("--disable-gpu");
					driver.set(new EdgeDriver(edgeOptions));
				} else {
					driver.set(new EdgeDriver());
				}
				break;
			case "firefox":
				FirefoxOptions firefoxOptions = new FirefoxOptions();
				if (Headless.equalsIgnoreCase("true")) {
					firefoxOptions.addArguments("--headless=new");
					firefoxOptions.addArguments("--window-size=1920,1080");
					firefoxOptions.addArguments("--disable-gpu");
					driver.set(new FirefoxDriver(firefoxOptions));
				} else {
					driver.set(new FirefoxDriver());
				}
				break;
			default:
				throw new IllegalArgumentException("Browser not supported: " + browser);
			}
			driver.get().manage().window().maximize();
		}
		return driver.get();
	}

	public static WebDriver getDriver() {
		return driver.get();
	}

	public static void quitDriver() {
		if (driver.get() != null) {
			driver.get().quit();
			driver.remove();
		}
	}
}
