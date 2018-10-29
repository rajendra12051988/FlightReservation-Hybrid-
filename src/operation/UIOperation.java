package operation;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import testcases.executeTestThroughTestNg;


public class UIOperation extends executeTestThroughTestNg  {
	
	static Logger logger = Logger.getLogger("devpinoyLogger");
	
		/* Function Name : invokeApplication
		 * Parameters : url [String] 
		 * Description : This function invokes the application under test. */
	
		public static void invokeApplication(String url){
			logger.info("Launching Application : "+url);
			try{
			driver.get(url);
			logger.info("Launched Application : "+url);
			}catch(Exception e){
				logger.error("Error occurred while invoking Flight Reservation Application :"+e.getMessage());
			}
		}
			
		/* Function Name : assertPageTitle
		 * Parameters : expTitle [String] 
		 * Description : This function asserts page title.  */
			
		public static void assertPageTitle(String expTitle){
			try{
				Assert.assertTrue(driver.getTitle().equals(expTitle));
			}catch(Exception e){
				logger.error("Exception occurred while asserting page title : "+e.getMessage());
			}
			
		}
		
		/* Function Name : click
		 * Parameters : locator[By], objectName[String] 
		 * Description : This function clicks on web object. */
			
		public static void click(By locator, String objectName){
			try{
			logger.info("Clicking on '"+objectName+"'");
			WebElement elementTobeClicked = driver.findElement(locator);
			highlightElement(driver, elementTobeClicked);
			elementTobeClicked.click();
			logger.info("Clicked on '"+objectName+"'");
			}catch(Exception e){
				logger.error("Exception occurred while clicking on '"+objectName+"' : "+e.getMessage());
			}
		}
		
		/* Function Name : click
		 * Parameters : locator[By], objectName[String], value[String] 
		 * Description : This function selects a specified value from a drop down. */
			
		public static void select(By locator, String objectName, String value){
			try{
			logger.info("Selecting '"+value+"' from '"+objectName+"' dropdown");
			WebElement elementTobeSelected = driver.findElement(locator);
			highlightElement(driver, elementTobeSelected);
			Select select = new Select(elementTobeSelected);
			select.selectByVisibleText(value);
			logger.info("Selected'"+value+"' from '"+objectName+"' dropdown");
			}catch(Exception e){
				logger.error("Exception occurred while selecting '"+value+"' from '"+objectName+"' : "+e.getMessage());
			}
		}
		
		/* Function Name : sendKeys
		 * Parameters : locator[By], objectName[String], value[String] 
		 * Description : This function enters a specified value in an edit field. */
		
		public static void sendKeys(By locator, String objectName, String value){
			try{
			logger.info("Entering '"+value+"' in the '"+objectName+"' edit field");
			WebElement elementTobeSet = driver.findElement(locator);
			highlightElement(driver,elementTobeSet);
			elementTobeSet.sendKeys(value);
			logger.info("Entered'"+value+"' in the '"+objectName+"' edit field");
			}catch(Exception e){
				logger.error("Exception occurred while entering '"+value+"' in the '"+objectName+"' edit field : "+e.getMessage());
			}
		}
		
		/* Function Name : verifyTextOnWebPage
		 * Parameters : value[String] 
		 * Description : This function verifies a specified text on a web page. */
		
		public static void verifyTextOnWebPage(String value){
			try{
			logger.info("Verifying text '"+value+"' on web page...");
			Assert.assertTrue(driver.getPageSource().contains(value));
			logger.info("Verified text '"+value+"' on web page...");
			}catch(Exception e){
				logger.error("Exception occurred while verifying '"+value+"' on webPage : "+e.getMessage());
			}
		
	}
	
	
	private static void highlightElement(WebDriver driver,WebElement element){
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].setAttribute('style','background: yellow; border: 2px solid red;');",element);
	}
	
	
}
