package com.framework.pages;

import java.time.Duration;
import java.util.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.framework.base.ConfigReader;

public class contactUsPage {
	private WebDriver driver;
	private WebDriverWait wait;
	protected ConfigReader config;
	private By contactUslink = By.xpath("(//a[normalize-space()='Contact Us'])[1]");
	private By dropUs = By.xpath("//button[text()='Drop us a Word!']");
	private By UploadButton = By.cssSelector("input[type='file']");
	private By Name = By.xpath("//input[@data-aid='CONTACT_FORM_NAME']");
	private By Email = By.xpath("//input[@data-aid='CONTACT_FORM_EMAIL']");
	private By Phone = By.xpath("//input[@data-aid='CONTACT_FORM_PHONE']");
	private By Message = By.cssSelector("textarea[placeholder='Message']");
	private By send = By.xpath("//button[@type='submit']");
	private By confirmation = By.xpath("//*[@data-aid='CONTACT_FORM_SUBMIT_SUCCESS_MESSAGE']");
	private By Cookies = By.xpath("//*[@data-aid='FOOTER_COOKIE_CLOSE_RENDERED']");

	public contactUsPage(WebDriver driver) {
		this.driver = driver;
		config = new ConfigReader();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(50));
	}

	public void clickContactUs() {
		try {
			WebElement contactUs = wait.until(ExpectedConditions.presenceOfElementLocated(contactUslink));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", contactUs);
			Thread.sleep(300);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", contactUs);
		} catch (TimeoutException e) {
			System.out.println("Contact Us element not clickable after 20 seconds in headless mode");
			throw e;
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}

	public String dropUs(String filePath) throws Throwable {
		driver.findElement(dropUs).click();
		String Names = config.getProperty("name");
		Thread.sleep(3000);
		driver.findElement(Name).sendKeys(Names);
		driver.findElement(Email).sendKeys(config.getProperty("email"));
		driver.findElement(Phone).sendKeys(config.getProperty("phone"));
		driver.findElement(Message).sendKeys(config.getProperty("message"));
		uploadFile(filePath);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", driver.findElement(send));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmation));
		return element.getText();
	}

	public List<String> dropUs(List<Map<String, String>> allData, String filePath) throws InterruptedException {
		String confirmationText = "";
		List<String> responses = new ArrayList<>();
		AcceptCookies();
		for (Map<String, String> row : allData) {
			driver.findElement(dropUs).click();
			Thread.sleep(2000);
			String name = row.get("Name");
			String email = row.get("Email");
			String phone = row.get("Phone");
			String message = row.get("Message");
			driver.findElement(Name).sendKeys(name);
			driver.findElement(Email).sendKeys(email);
			driver.findElement(Phone).sendKeys(phone);
			driver.findElement(Message).sendKeys(message);
			uploadFile(filePath);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", driver.findElement(send));
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmation));
			confirmationText = element.getText();
			responses.add(confirmationText);
			if (allData.size() > 1) {
				clickContactUs();
			}
		}
		return responses;
	}

	public void uploadFile(String filePath) throws InterruptedException {
		try {
			WebElement fileInput = driver.findElement(UploadButton);
			fileInput.sendKeys(filePath);
		} catch (TimeoutException e) {
			System.out.println("File upload input not found");
			throw e;
		}
	}

	public void AcceptCookies() {
		try {
			WebElement acceptBtn = wait.until(ExpectedConditions.elementToBeClickable(Cookies));
			acceptBtn.click();
			System.out.println("Cookie popup accepted.");
		} catch (Exception e) {
			System.out.println("No cookie popup appeared.");
		}
	}
}
