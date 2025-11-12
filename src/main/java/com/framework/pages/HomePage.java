package com.framework.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.framework.base.ConfigReader;

public class HomePage {

	private WebDriver driver;
    private WebDriverWait wait;
    protected ConfigReader config;
 
    private By Banner = By.xpath("//h1[contains(@id,'dynamic-tagline')]");
    public HomePage(WebDriver driver) {
        this.driver = driver;
        config = new ConfigReader();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        
    }

    public String BannerValidation( ) {
    	
        	WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(Banner));
        return element.getText();
    }

    
    
    
    
}
