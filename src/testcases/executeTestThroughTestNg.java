package testcases;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import excelInputAndOutput.ExcelReader;
import operation.ReadObject;
import operation.UIOperation;
import utility.Constant;


public class  executeTestThroughTestNg {
	ExcelReader obj = new ExcelReader();
	ReadObject reader = new ReadObject();
	Properties allObjects;
	public static WebDriver driver = null;
	ExtentReports extent;
	ExtentTest extentLogger;
	String nodeURL;
	@Parameters({"browser"})
    @BeforeTest
	public void setUP(String browser) throws IOException {
		// TODO Auto-generated method stub
    	allObjects = reader.getObjectRepoository();
    	ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(new File(Constant.reportPath));
    	extent = new ExtentReports();
    	
    	extent.setSystemInfo("Project", "Flight Reservation");
    	extent.setSystemInfo("Environment", "Production");
    	extent.setSystemInfo("User", System.getProperty("user.name"));
    	extent.setSystemInfo("OS", System.getenv("os.name"));
    	extent.setSystemInfo("Java Version",System.getProperty("java.version"));
    	InetAddress myHost = InetAddress.getLocalHost();
    	extent.setSystemInfo("Host Name", myHost.getHostName());
    	
    	htmlReporter.config().setChartVisibilityOnOpen(true);
    	htmlReporter.config().setDocumentTitle("Flight Reservation : Test Execution Report");
    	htmlReporter.config().setReportName("Regression Cycle");
    	htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
    	htmlReporter.config().setTheme(Theme.DARK);
    	//htmlReporter.config().setAutoCreateRelativePathMedia(true);
    	
    	extent.attachReporter(htmlReporter);
    	
		if(browser.equals("chrome")){
			System.setProperty("webdriver.chrome.driver", Constant.chromeDriverPath);
			driver = new ChromeDriver();
			//DesiredCapabilities cap = DesiredCapabilities.chrome();
			//cap.setPlatform(Platform.WIN10);
			//nodeURL = "http://localhost:4444/wd/hub";
			//driver = new RemoteWebDriver(new URL(nodeURL),cap);
		}else if(browser.equals("firefox")){
			System.setProperty("webdriver.gecko.driver", Constant.geckoDriverPath);
			driver = new FirefoxDriver();
		}else if(browser.equals("ie")){
			System.setProperty("webdriver.ie.driver", Constant.ieDriverPath);
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			capabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, allObjects.getProperty("URL"));
			driver = new InternetExplorerDriver();
		}
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
    }
    
    @BeforeMethod
    public void startTestCase(Method method){
    	extentLogger = extent.createTest(method.getName());
    }
    
    @Test(priority=0)
    public void testInvokeApplication() throws IOException{
    	//extentLogger = extent.createTest("testInvokeApplication");
    	extentLogger.info("Test to verify Flight Reservation Application is launhed successfully.");
    	String url = obj.getCellData(Constant.filePath, Constant.fileName, "InvokeApplication", 1, 0);
    	String expTitle = obj.getCellData(Constant.filePath, Constant.fileName, "InvokeApplication", 1, 1);
    	// Invoke Application
    	UIOperation.invokeApplication(url);
    	// Verify page Title
    	UIOperation.assertPageTitle(expTitle);
    	//extentLogger.log(Status.PASS, MarkupHelper.createLabel("Flight Reservation application has been invoked successfully...",ExtentColor.GREEN));
    }
		
    @Test(priority=1)
    public void testLogin() throws IOException{
    	//extentLogger = extent.createTest("testLogin");
    	extentLogger.info("Test to verify login to Flight Reservation application gets successful.");
    	String username = obj.getCellData(Constant.filePath, Constant.fileName, "Login", 1, 0);
    	String password = obj.getCellData(Constant.filePath, Constant.fileName, "Login", 1, 1);
    	String expTitle = obj.getCellData(Constant.filePath, Constant.fileName, "Login", 1, 2);
    	// Enter UserName
    	UIOperation.sendKeys(By.name(allObjects.getProperty("UserName")), "UserName", username);
    	// Enter Password
    	UIOperation.sendKeys(By.name(allObjects.getProperty("Password")), "Password", password);
    	// Click on 'Sign-In' button
    	UIOperation.click(By.name(allObjects.getProperty("Sign-On")), "Sign-On");
    	// Verify Page Title
    	UIOperation.assertPageTitle(expTitle);
    	//extentLogger.log(Status.PASS, MarkupHelper.createLabel("Login Successful",ExtentColor.GREEN));
    }
    
    @Test(priority=2)
    public void testBookATicket() throws IOException{
    	//extentLogger = extent.createTest("testBookATicket");
    	extentLogger.info("Test to verify ticket gets booked successfully.");
    	String flyFrom = obj.getCellData(Constant.filePath, Constant.fileName, "BookATicket", 1, 0);
    	String flyTo = obj.getCellData(Constant.filePath, Constant.fileName, "BookATicket", 1, 1);
    	String airlinePreference = obj.getCellData(Constant.filePath, Constant.fileName, "BookATicket", 1, 2);
    	String firstname = obj.getCellData(Constant.filePath, Constant.fileName, "BookATicket", 1, 3);
    	String lastname = obj.getCellData(Constant.filePath, Constant.fileName, "BookATicket", 1, 4);
    	String creditnumber = obj.getCellData(Constant.filePath, Constant.fileName, "BookATicket", 1, 5);
    	String expText = obj.getCellData(Constant.filePath, Constant.fileName, "BookATicket", 1, 6);
    	// Select Trip Type
    	UIOperation.click(By.xpath(allObjects.getProperty("TripType")), "One Way");
    	// Select Departure From
    	UIOperation.select(By.name(allObjects.getProperty("DepartureFrom")), "Departure From", flyFrom);
    	// Select Arrival To
    	UIOperation.select(By.name(allObjects.getProperty("ArrivalTo")), "Arrival To", flyTo);
    	// Select Class Preference
    	UIOperation.click(By.xpath(allObjects.getProperty("ClassPreference")), "Class Preference");
    	// Select Airline Preference
    	UIOperation.select(By.name(allObjects.getProperty("AirlinePreference")), "Airline Preference", airlinePreference);
    	// Click on 'Find Flights'
    	UIOperation.click(By.name(allObjects.getProperty("FindFlights")), "Find Flights");
    	// Click on 'Reserve Flights'
    	UIOperation.click(By.name(allObjects.getProperty("ReserveFlights")), "Reserve Flights");
    	// Enter First Name
    	UIOperation.sendKeys(By.name(allObjects.getProperty("FirstName")), "First Name", firstname);
    	// Enter Last Name
    	UIOperation.sendKeys(By.name(allObjects.getProperty("LastName")), "Last Name", lastname);
    	// Enter Credit Card Number
    	UIOperation.sendKeys(By.name(allObjects.getProperty("CreditCardNumber")), "Credit Number", creditnumber);
    	// Click on 'Buy Flights'
    	UIOperation.click(By.name(allObjects.getProperty("BuyFlights")), "Buy Flights");
    	
    	// Verify Page Text
    	UIOperation.verifyTextOnWebPage(expText);
    	
    	//extentLogger.log(Status.PASS, MarkupHelper.createLabel("Ticket has been booked successfully...",ExtentColor.GREEN));
    	
    }
    
  
    @AfterMethod
    public void generateReport(ITestResult result) throws IOException{
    	if(result.getStatus() == ITestResult.FAILURE){
    		extentLogger.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+ " got failed", ExtentColor.RED));
    		extentLogger.log(Status.FAIL, result.getName()+" got failed due to "+result.getThrowable());
    		String screenShotPath = getScreenshot(result.getName());
    		extentLogger.addScreenCaptureFromPath(screenShotPath);
    		
    	}else if(result.getStatus() == ITestResult.SKIP){
    		extentLogger.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" got skipped as this was not ready to be executed", ExtentColor.YELLOW));
    	}else {
    		extentLogger.log(Status.PASS, MarkupHelper.createLabel(result.getName()+ " got passed.", ExtentColor.GREEN));
    		String screenShotPath = getScreenshot(result.getName());
    		extentLogger.addScreenCaptureFromPath(screenShotPath);
    		
    	}
    }
    
    
    private String getScreenshot(String screenShotName) throws IOException{
    	TakesScreenshot srcShot = (TakesScreenshot)driver;
    	File srcFile = srcShot.getScreenshotAs(OutputType.FILE);
    	String cuurentDate = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
    	String destination = "./Result/screenshots/"+screenShotName+"_"+cuurentDate+".png";
    	File destFile = new File(destination);
    	FileUtils.copyFile(srcFile, destFile);
    	return destFile.getAbsolutePath();
    }
    
    @AfterTest
    public void tearDown(){
    	driver.quit();
    	extent.flush();
    }
    
		
		
		

}

