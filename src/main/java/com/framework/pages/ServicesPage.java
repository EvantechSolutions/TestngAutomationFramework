package com.framework.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.framework.base.ConfigReader;

public class ServicesPage {

    private WebDriver driver;
    private WebDriverWait wait;
    protected ConfigReader config;
    private By Serviceslink = By.xpath("(//a[normalize-space()='Services'])[1]");
    private By Collabration = By.xpath("//*[@data-aid='ABOUT_HEADLINE_RENDERED3']");
    public ServicesPage(WebDriver driver) {
        this.driver = driver;
        config = new ConfigReader();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        
    }

    public String ServicesValidation() {
    	try {
            WebElement Service = wait.until(ExpectedConditions.presenceOfElementLocated(Serviceslink));
            
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", Service);
            Thread.sleep(300); 
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", Service);
        } catch (TimeoutException e) {
            System.out.println("Contact Us element not clickable after 20 seconds in headless mode");
            throw e;
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    	((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    	WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(Collabration));
        return element.getText();
    	}
    
    
    
    
}
