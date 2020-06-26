package Utility;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import setup.DriverSetup;
import setup.EnvironmentSetup;

public class BeginnerClass {

	public static WebDriver driver;
	public String caseId;
	public String status = "PASS";
	public String testCaseName;
	public static Properties prop;
	protected ExcelUtils xL = new ExcelUtils();
	public static int rtmExecution = 0;

	@BeforeSuite(alwaysRun = true)
	public void reportSetUp() {

		// Starting Hub and Node
		DriverSetup.startHubNode();

		// Creating Extent Report file
		ExtentReport.createReport();

		// Initializing the Input Property file
		try {
			prop = BaseClass.loadProp();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Initializing the RTM Excel file
		ExcelUtils.setUpRTMExcel();

	}

	@BeforeClass(alwaysRun = true)
	@Parameters({ "Browser", "Environment", "nodeURL" })
	public void setUp(@Optional("chrome") String browser, @Optional("local") String environment,
			@Optional("null") String nodeURL) throws IOException {

		driver = EnvironmentSetup.getDriver(browser, environment, nodeURL);
		ExtentReport.driver = driver;
		testCaseName = getClass().getSimpleName();
		xL.reportToExcel(testCaseName + " Test: STARTED");
		ExtentReport.log = null;
		BaseClass.js= (JavascriptExecutor) driver;

	}

	@AfterMethod(alwaysRun = true)
	public void testResult(ITestResult result, Method m) throws IOException {

		ExtentReport.getResult(result);

		// Logging the Test Result in Excel
		if (result.isSuccess()) {
			xL.reportToExcel(getClass().getSimpleName() + " Test Case: " + m.getName() + " Test Method: SUCCESS");
		} else {
			xL.reportToExcel(getClass().getSimpleName() + "Test Case: " + m.getName() + " Test Method: FAILURE");
			status = "FAIL";
		}

	}

	@AfterClass(alwaysRun = true)
	public void closeBrowser() throws IOException {

		// Closing the driver
		driver.quit();

		// Logging the execution process in HTML
		Report.closeBrowser(ExtentReport.log);
		ExtentReport.log.pass(MarkupHelper.createLabel("Browser closed successfully", ExtentColor.GREEN));

		// Logging the execution process in Execution Report Excel
		xL.statusUpdate(caseId, testCaseName, status);

		// Logging the execution process in Status Report Excel
		xL.reportToExcel(testCaseName + " Test: ENDED");

	}

	@AfterSuite(alwaysRun = true)
	public void flush() {

		ExtentReport.flushReport();

	}

}