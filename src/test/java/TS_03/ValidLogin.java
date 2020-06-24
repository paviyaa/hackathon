package TS_03;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import Utility.ExtentReport;
import Utility.Report;
import pages.HomePage;
import pages.LoginPage;

public class ValidLogin extends Utility.BeginnerClass {

	Report report = new Report();
	ExtentTest logger;
	LoginPage loginPage;
	HomePage homePage;
	String home;

	@Test(groups = { "SmokeTest" }, retryAnalyzer = Utility.RetryAnalyzer.class)
	public void startBrowser() throws IOException {

		// Assigning the Test case ID
		caseId = "TC_31";

		// Initiating cell position for RTM to get update
		xL.setRowCellValues(caseId);

		// Creating Search by brand Test for HTML
		if (ExtentReport.log == null)
			ExtentReport.log = ExtentReport.reports.createTest("Valid Login");
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

	@Test(groups = { "SmokeTest" }, retryAnalyzer = Utility.RetryAnalyzer.class, priority = 1)
	public void clickLogin() {

		// Initializing the elements of Home Page
		homePage = PageFactory.initElements(driver, HomePage.class);

		// Logging the execution process in HTML
		report.display(logger, "Clicking login button");

		// Getting GUI code of parent tab
		home = homePage.getParent();

		// Clicking the LoginButton
		homePage.clickLogin();

		// Asserting whether Log-in via page is displayed
		Assert.assertTrue(homePage.getSigninPopupTitle().contains("Login/Register"));

		// Updating the status of execution to RTM
		xL.updateResultToRTM("<Log in via> page  is displayed");

		// Logging the execution process in HTML
		report.display(logger, "login button is clicked and log in via page is displayed");

	}

	@Test(groups = { "SmokeTest" }, retryAnalyzer = Utility.RetryAnalyzer.class, priority = 2)
	public void clickGoogleLogin() {

		// Logging the execution process in HTML
		report.select(logger, "Clicking Continuing Signin with Google");

		// Continuing the sign-in with Google
		homePage.clickGoogleLogin();

		// Logging the execution process in HTML
		report.display(logger, "Continue with google is Clicked");

	}

	@Test(groups = { "SmokeTest" }, retryAnalyzer = Utility.RetryAnalyzer.class, priority = 3)
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

	@Test(groups = { "SmokeTest" }, retryAnalyzer = Utility.RetryAnalyzer.class, priority = 4)
	public void setValuesForSignin() {

		// Logging the execution process in HTML
		report.select(logger, "Giving correct values to Email and Password");

		// Passing value for Email Box
		loginPage.setEmail("validMailID");

		// Clicking Next button
		loginPage.clickNext();

		// Updating the status of execution to RTM
		xL.updateResultToRTM("Password page is displayed");

		// Passing value for Email Box
		loginPage.setPwd("validPassword");

		// Clicking Next button
		loginPage.clickNext();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}

	@Test(groups = { "SmokeTest" }, retryAnalyzer = Utility.RetryAnalyzer.class, priority = 5)
	public void outputDisplay() {

		// Reassigning GUI code of parent tab to driver
		homePage.setParent(home);

		// Acquiring Error message
		String message = loginPage.doLogin();

		// Exiting the pop-up
		loginPage.exitIcon();

		// Logging the execution process in HTML
		report.display(logger, "Asserting and displaying acquired results");

		// Asserting and Displaying acquired result
		Assert.assertTrue(message.contains("Welcome"));

		// Asserting and Displaying acquired results
		System.out.println("***************************RESULT***************************" + "\n");
		System.out.println(message);
		System.out.println("\n" + "***************************RESULT***************************");

		// Updating the status of execution to RTM
		xL.updateResultToRTM("Success message is displayed");

		// Logging the execution process in HTML
		report.display(logger, "Success message 'Welcome' is asserted and displayed");

	}

}