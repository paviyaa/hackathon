package TS_03;

import java.io.IOException;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import Utility.ExtentReport;
import Utility.Report;
import Utility.Screenshot;
import pages.HomePage;
import pages.LoginPage;

public class IncorrectEmailLogin extends Utility.BeginnerClass {

	Report report = new Report();
	HomePage homePage;
	ExtentTest logger;
	LoginPage loginPage;

	@Test(retryAnalyzer = Utility.RetryAnalyzer.class)
	public void startBrowser() throws IOException {

		// Assigning the Test case ID
		caseId = "TC_33";

		// Initiating cell position for RTM to get update
		xL.setRowCellValues(caseId);

		// Creating Search by brand Test for HTML
		if (ExtentReport.log == null)
			ExtentReport.log = ExtentReport.reports.createTest("IncorrectEmail");
		logger = ExtentReport.log;

		// Updating the status of execution to RTM
		if (driver != null)
			xL.updateResultToRTM("Browser is launched");
		else
			xL.updateResultToRTM("Browser is not launched");

		// Logging the execution process in HTML
		report.startBrowser(logger);

		// Asserting to confirm ZigWheels page
		Assert.assertTrue(driver.getTitle().contains("ZigWheels"));

		// Updating the status of execution to RTM
		xL.updateResultToRTM("Application is launched");

		// Logging the execution process in HTML
		report.display(logger, "Browser is launched and navigated to Homepage of the website");

	}

	@Test(retryAnalyzer = Utility.RetryAnalyzer.class, priority = 1)
	public void clickLogin() {

		// Initializing the elements of Home Page
		homePage = PageFactory.initElements(driver, HomePage.class);

		// Logging the execution process in HTML
		report.display(logger, "Clicking login button");

		// Clicking the LoginButton
		homePage.clickLogin();

		// Asserting whether Log in via page is displayed
		Assert.assertTrue(homePage.getSigninPopupTitle().contains("Login/Register"));

		// Updating the status of execution to RTM
		xL.updateResultToRTM("<Log in via> page  is displayed");

		// Logging the execution process in HTML
		report.display(logger, "login button is clicked and log in via page is displayed");

	}

	@Test(retryAnalyzer = Utility.RetryAnalyzer.class, priority = 2)
	public void clickGoogleLogin() {

		// Logging the execution process in HTML
		report.select(logger, "Clicking Continuing Signin with Google");

		// Continuing the sign-in with Google
		homePage.clickGoogleLogin();

		// Logging the execution process in HTML
		report.display(logger, "Continue with google is Clicked");

	}

	@Test(retryAnalyzer = Utility.RetryAnalyzer.class, priority = 3)
	public void switchToSigninWindow() {

		// Initializing the elements of Signin Popup Page
		loginPage = PageFactory.initElements(driver, LoginPage.class);

		// switching to Popup Window
		loginPage.switchWindow();

		// Logging the execution process in HTML
		report.login(logger);

		// Asserting whether window is navigated correctly
		Assert.assertTrue(driver.getTitle().contains("Sign in"));

		// Updating the status of execution to RTM
		xL.updateResultToRTM("<Sign in> page of Google Accounts is displayed");

		// Logging the execution process in HTML
		report.display(logger, "Navigated to the login page");

	}

	@Test(retryAnalyzer = Utility.RetryAnalyzer.class, priority = 4)
	public void setValuesForSignin() {

		// Logging the execution process in HTML
		report.select(logger, "Giving incorrect value to Email");

		// Passing value for Email Box
		loginPage.setEmail("incorrectMailID");

		// Clicking Next button
		loginPage.clickNext();

	}

	@Test(retryAnalyzer = Utility.RetryAnalyzer.class, priority = 5)
	public void outputDisplay() {

		// Acquiring Error message
		String errorMessage = loginPage.errorincrtEmail();

		// Logging the execution process in HTML
		report.display(logger, "Asserting and displaying acquired results");

		// Asserting and Displaying acquired result
		Assert.assertTrue(errorMessage.contains("Couldn't find your Google Account"));

		// Asserting and Displaying acquired results
		System.out.println("***************************RESULT***************************" + "\n");
		System.out.println(errorMessage);
		System.out.println("\n" + "***************************RESULT***************************");

		// Capturing Screenshot
		try {
			logger.pass("Captured Screenshot",
					MediaEntityBuilder
							.createScreenCaptureFromPath(
									Screenshot.captureScreenshot(driver, getClass().getSimpleName(), "Testcase"))
							.build());
			System.out.println("Screenshot Captured !!");
		} catch (IOException e) {
			System.out.println("Unable to capture screenshot" + e.getMessage());
		}

		// Updating the status of execution to RTM
		xL.updateResultToRTM("Error message is displayed");

		// Logging the execution process in HTML
		report.display(logger, " Error message saying 'Couldn't find your Google account' is asserted and dispalyed");

	}

}