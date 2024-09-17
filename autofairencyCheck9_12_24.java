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
	     public static final String ACCOUNT_SID = "AC5aae955764b57eb00667f2801695bb00";
	     public static final String AUTH_TOKEN = "1e2c7012e6a58c791a9371d76f8378d9";




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
    	
     String url = "https://apps.brakecheck.com/salesgui/login";	
        driver.get(url); // Try to reach the URL
        driver.findElement(By.id("email")).sendKeys("MCondes");
        driver.findElement(By.id("password")).sendKeys("Diara2009");
        driver.findElement(By.xpath("//button[normalize-space()='Log In']")).click();

        Thread.sleep(10000); // Sleep to allow page load
        driver.manage().window().maximize();
        
        WebElement elementToHover = driver.findElement(By.xpath("(//div[@id='leftSide'])[1]"));
        Actions actions = new Actions(driver);
        
        actions.moveToElement(elementToHover).perform();
   
        
        WebElement sideBarTp = driver.findElement(By.xpath("(//a[@href='/salesgui/repairOrderHistory'])[1]"));
        
        sideBarTp.click();
        
        driver.findElement(By.xpath("(//input[@placeholder='Search Criteria: Id, SB Id, Customer Name, Phone, Address, VIN, Plate, Vehicle Description.'])[1]")).sendKeys("CG3S132");

        driver.findElement(By.xpath("(//button[normalize-space()='Search'])[1]")).click();
        
        List<WebElement> searchResults = driver.findElements(By.xpath("(//tr[@class='ui-datatable-even ui-widget-content ng-star-inserted'])[1]"));
        String currentUrl = driver.getCurrentUrl();
      System.out.println("Current URL: " + currentUrl);
       
        if (searchResults.size() > 0 || currentUrl.contains("https://apps.brakecheck.com/salesgui/repairOrderHistory" )) {
            System.out.println("Autofairency is up");
            messageBody = "This is an Automated Test. Autofairency is up";
        } else {
            System.out.println("The Autofairency is down");
            messageBody = "This is an Automated Test. Autofairency is Currently Unreachable ";
        
        }
        
//        String currentUrl = driver.getCurrentUrl();
//        System.out.println("Current URL: " + currentUrl);
//        
//        if (currentUrl.contains("https://apps.brakecheck.com/salesgui/dashboard")) {
//            System.out.println("Autofairency is up");
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
    List<String> phoneNumbers = Arrays.asList("+12109921087", "+12107930377", "+12103783596", "+12108270770", "+12106299488", "+12105734626");
    
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
                new PhoneNumber("22431"), // From number (your Twilio number)
                messageBody) // Message body
            .create();

        System.out.println("SMS sent: " + message.getSid());
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Failed to send SMS: " + e.getMessage());
    }
}}