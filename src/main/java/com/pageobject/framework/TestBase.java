package com.pageobject.framework;


import static com.pageobject.webdriver.WebDriverConfigBean.aWebDriverConfig;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.pageobject.webdriver.DriverInitilizer;

import org.openqa.selenium.Cookie;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.SessionId;

import org.slf4j.LoggerFactory;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import com.pageobject.helpers.Helpers;
import com.pageobject.webdriver.WebDriverConfigBean;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestBase
{
	// TODO Subclasses of this should not declare that they implement the SauceXXX interfaces,
	// they don't need to

	protected static final int DEFAULT_WAIT_TIME = 30000; //milli seconds

	protected static final int DEFAULT_WAIT_IN_SECONDS = 30;

	protected static WebDriver driver = null;

	protected static org.slf4j.Logger APPLICATION_LOGS = LoggerFactory.getLogger(TestBase.class);

//	private SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication();


	public String newEventWtihTaxonomyTags;

	public String updatedEventId;

	public static ExtentReports extent;

//	@BeforeSuite
//	public void extentSetup(ITestContext context) {
//		ExtentReportManager.setOutputDirectory(context);
//		extent = ExtentReportManager.getInstance();
//	}

	//	@BeforeTest
	//	@Parameters({ "browser", "env", "mode", "deviceProfile" })
	//	public void setupEventAndPlacesData(String browser, String env, String mode, String deviceProfile){
	//		EnvironmentConfiguration.populate(env);
	//		EnvironmentConfiguration.populateTestData("events");
	//		WebDriverConfigBean webDriverConfig = aWebDriverConfig()
	//				.withBrowser(browser)
	//				.withDeploymentEnvironment(env)
	//				.withSeleniumMode(mode)
	//				.withSeleniumDeviceProfile(deviceProfile);
	//		driver = WebDriverManager.openBrowser(webDriverConfig, getClass());
	//		if (env.equals("cie") && mode.equals("GRID") && deviceProfile.equals("GRID_MAC")) {
	//			updatedEventId = EventBuilder.updateSchedule();
	//			newEventWtihTaxonomyTags = EventBuilder.createNewEventWtihTaxonomyTags(env);
	//			APPLICATION_LOGS.debug("Waiting for 3 minutes for event and places update");
	//			Helpers.pause(180000);
	//		}

	//	}

	@BeforeClass
//	@Parameters({ "browser", "env", "mode", "deviceProfile" })
	public void initializeTests() {
		EnvironmentConfiguration.populate("test");
		WebDriverConfigBean webDriverConfig = aWebDriverConfig()
				.withBrowser("chrome")
				.withDeploymentEnvironment("test")
				.withSeleniumMode("LOCAL")
				.withSeleniumDeviceProfile("WINDOWS_7_CHROME");
		driver = DriverInitilizer.initialiseDriver(webDriverConfig);
//		if (env.equals("cie") && mode.equals("GRID") && deviceProfile.equals("GRID_MAC")) {
////			updatedEventId = EventBuilder.updateSchedule();
//			APPLICATION_LOGS.debug("Waiting for 3 minutes for event and places update");
//			Helpers.pause(180000);
//		}
		APPLICATION_LOGS.debug("Will use baseURL " + EnvironmentConfiguration.getBaseURL());
		APPLICATION_LOGS.debug("Starting the Test in the class  -- " + getClass().getSimpleName());

		navigate(EnvironmentConfiguration.getBaseURL());
	}

	@BeforeMethod
//	@Parameters("browser")
	public final void testCaseName(Method method) {
		String testName = method.getName();
		APPLICATION_LOGS.debug("\t**** " + testName + " ****\t");
//		ExtentTestManager.startTest(testName);
	}

	@AfterMethod
//	@Parameters("mode")
	public final void results(ITestResult testResult, Method method) throws IOException {
		int result = testResult.getStatus();
//		ExtentTestManager.getTest().getTest().setStartedTime(Helpers.getTime(testResult.getStartMillis()));
//		ExtentTestManager.getTest().getTest().setEndedTime(Helpers.getTime(testResult.getEndMillis()));
		switch (result) {
			case ITestResult.FAILURE:
				APPLICATION_LOGS.debug("Test Failed due to assertion failure -- Please see the screenshot");
				String screenShotname = method.getName();
				//takeScreenShot(screenShotname);
				//Helpers.getScreenshot(screenShotname);
//				if (mode.equals("SAUCE")) {
////					updateTestStatus(testResult.getStatus(), method.getClass().getSimpleName().toString(), getSessionId());
//				}

				//String image = ExtentTestManager.getTest().addScreenCapture("screenshots/" + screenShotname + ".png");
//				ExtentTestManager.getTest().log(LogStatus.FAIL, "FAILED");
//				ExtentTestManager.getTest().log(LogStatus.FAIL, testResult.getThrowable());
//				ExtentTestManager.getTest().log(LogStatus.INFO, image);
				break;
			case ITestResult.SUCCESS:
				APPLICATION_LOGS.debug("(Pass)");
//				ExtentTestManager.getTest().log(LogStatus.PASS, "PASSED");
//				if (mode.equals("SAUCE")) {
////					updateTestStatus(testResult.getStatus(), method.getClass().getSimpleName().toString(), getSessionId());
//				}
				break;
			case ITestResult.SKIP:
				APPLICATION_LOGS.debug("(Skipped)");
//				ExtentTestManager.getTest().log(LogStatus.SKIP, "SKIPPED");
//				if (mode.equals("SAUCE")) {
////					updateTestStatus(testResult.getStatus(), method.getClass().getSimpleName().toString(), getSessionId());
//				}
				break;
			default:
				APPLICATION_LOGS.error("Unexpected test result status code: " + result);
		}
		APPLICATION_LOGS.debug("*******************************************************************************************");
//		ExtentTestManager.endTest();
//		extent.flush();

	}

	public String getCookieByName(WebDriver driver, String cookieName) {
		Cookie aCookie = driver.manage().getCookieNamed(cookieName);
//		setLog("Getting the cookies name -- " + aCookie.getName());
		return aCookie.getName();
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

	//	@AfterTest
	//	public void closeEventBrowser(){
	//		if(driver != null) {
	//			driver.quit();
	//		}
	//	}

//	@AfterSuite
//	public void closeExtentReport() {
//		extent.flush();
//		extent.close();
//
//	}

	public void navigate(String url) {
		driver.get(url);
	}

	public void takeScreenShot(String testName) {
		APPLICATION_LOGS.trace("about to take a screenshot " + testName);
//		File screenShotnameFile = new File(DriverPaths.LOGS_PATH + testName + ".png");
		APPLICATION_LOGS.trace("screenshot file '{}'", testName);
		driver = new Augmenter().augment(driver);
		byte[] screenshotData = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		APPLICATION_LOGS.trace("taken screenshot bytes'{}'", screenshotData.length);
		try {
			APPLICATION_LOGS.trace("about to write screenshot");
//			FileUtils.writeByteArrayToFile(screenshotFile, screenshotData);
			APPLICATION_LOGS.trace("screenshot written!");
		} catch (Exception e) {
//			APPLICATION_LOGS.error("Could not write screenshot to " + screenshoteenshotFile, e);
		}
	}

	public void updateTestStatus(int result, String method, String session) {
//		String jobId = getSessionId();
//		SauceREST client = new SauceREST(sauceUser, sauceKey);
		Map<String, Object> sauceJob = new HashMap<>();
		// sauceJob.put("name", "Test method: " + method);

//		if (result == ITestResult.SUCCESS) {
//			client.jobPassed(jobId);
//		} else if (result == ITestResult.FAILURE) {
//			client.jobFailed(jobId);
//		} else if (result == ITestResult.SKIP) {
//			client.jobFailed(jobId);
//		}
//
//		client.updateJobInfo(jobId, sauceJob);

	}

	/**
	 * {@inheritDoc}
	 */
//	@Override
//	public final String getSessionId() {
//		if (driver == null) {
//			throw new IllegalStateException("WebDriver initialisation failure.");
//		}
//		if (!(driver instanceof SessionIdAccessor)) {
//			throw new IllegalStateException("WebDriver does not implement SessionIdAccessor");
//		}
//		SessionId sessionId = ((SessionIdAccessor) driver).getSessionId();
//		if (sessionId == null) {
//			throw new IllegalStateException("WebDriver has no active session");
//		} else {
//			return sessionId.toString();
//		}
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public final SauceOnDemandAuthentication getAuthentication() {
//		return authentication;
//	}
//
//	public void setLog(String message) {
//		APPLICATION_LOGS.debug(message);
//		ExtentTest testReporter = ExtentTestManager.getTest();
//		if (testReporter != null) {
//			TestStepLogger.log(message);
//		}
//	}
	
}
