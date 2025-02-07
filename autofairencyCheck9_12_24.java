import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.util.Iterator;
import java.util.Set;
import java.util.List;
import java.util.Properties;
import java.util.Arrays;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class autofairencyCheck9_12_24 {
	


	
	 	  // Twilio credentials
	     public static final String ACCOUNT_SID = "123";
	     public static final String AUTH_TOKEN = "123";




public static void main(String[] args) {
    
    // Initialize Twilio
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    
 
    
   
    String messageBody = null;
    WebDriver driver = null;
  
    
    try {
    	
    System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--incognito");
    
    driver = new ChromeDriver(options);
    
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));	
    	
     String url = "https://.brakecheck.com/";	
        driver.get(url); // Try to reach the URL
        driver.findElement(By.id("email")).sendKeys("");
        driver.findElement(By.id("password")).sendKeys("");
        driver.findElement(By.xpath("//button[normalize-space()='Log In']")).click();

        Thread.sleep(10000); // Sleep to allow page load
        driver.manage().window().maximize();
        
        WebElement elementToHover = driver.findElement(By.xpath("(//div[@id='leftSide'])[1]"));
        Actions actions = new Actions(driver);
        
        actions.moveToElement(elementToHover).perform();
   
        
        WebElement sideBarTp = driver.findElement(By.xpath("(//a[@href=''])[1]"));
        
        sideBarTp.click();
        
        driver.findElement(By.xpath("(//input[@placeholder='Search Criteria: Id, SB Id, Customer Name, Phone, Address, VIN, Plate, Vehicle Description.'])[1]")).sendKeys("CG3S132");

        driver.findElement(By.xpath("(//button[normalize-space()='Search'])[1]")).click();
        
        List<WebElement> searchResults = driver.findElements(By.xpath("(//tr[@class='ui-datatable-even ui-widget-content ng-star-inserted'])[1]"));
        String currentUrl = driver.getCurrentUrl();
      System.out.println("Current URL: " + currentUrl);
       
        if (searchResults.size() > 0 || currentUrl.contains("https://" )) {
            System.out.println("Autofairency is up");
            messageBody = "This is an Automated Test. Autofairency is up";
        } else {
            System.out.println("The Autofairency is down");
            messageBody = "This is an Automated Test. Autofairency is Currently Unreachable ";
        
        }
        
//        String currentUrl = driver.getCurrentUrl();
//        System.out.println("Current URL: " + currentUrl);
//        
//        if (currentUrl.contains("https://")) {
//            System.out.println(" is up");
//            messageBody = "This is an Automated Test. Autofairency is up";
//        } else {
//            System.out.println("The Autofairency is down");
//            messageBody = "This is an Automated Test. Autofairency is Currently Unreachable";
//        }
        
        } catch (Exception e) { // Catch any issues when reaching the URL
        System.out.println("Failed to reach the site: " + e.getMessage());
        messageBody = "This is an Automated Test. Autofairency is Currently Unreachable due to connection failure";
    }finally {
        if (driver != null) {
            driver.quit(); // Make sure to close the driver in the end
        }
    
    // List of phone numbers to notify
    List<String> phoneNumbers = Arrays.asList("+123456789");
    
    // Send SMS to each phone number
    for (String phoneNumber : phoneNumbers) {
        sendTextMessage(phoneNumber, messageBody);
    }
   }
}

public static void sendTextMessage(String to, String messageBody) {
    try {
        System.out.println("Attempting to send SMS to " + to + "...");
        Message message = Message.creator(
                new PhoneNumber(to), // To number
                new PhoneNumber("22222"), // From number (your Twilio number)
                messageBody) // Message body
            .create();

        System.out.println("SMS sent: " + message.getSid());
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Failed to send SMS: " + e.getMessage());
    }
}}
