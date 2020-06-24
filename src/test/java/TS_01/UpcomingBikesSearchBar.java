package TS_01;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import Utility.ExtentReport;
import Utility.Report;
import pages.UpcomingBikesPage;
import pages.HomePage;

public class UpcomingBikesSearchBar extends Utility.BeginnerClass {

	public static UpcomingBikesPage upcomingBikes;
	Report report = new Report();
	ExtentTest logger;

	@Test(retryAnalyzer = Utility.RetryAnalyzer.class)
	public void startBrowser() throws IOException {
		// Assigning the Test case ID
		caseId = "TC_11";

		// Initiating cell position for RTM to get update
		xL.setRowCellValues(caseId);
		
		// Creating the Test for HTML
		if (ExtentReport.log == null)
			ExtentReport.log = ExtentReport.reports.createTest("Upcoming Honda Bikes");
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
	public void loadUpcomingBikesPage() throws IOException {

		// Initializing the elements of Home Page
		HomePage homePage = PageFactory.initElements(driver, HomePage.class);

		// Asserting whether Search Bar is displayed
		Assert.assertTrue(homePage.getSearchBarElement().isDisplayed());

		// Logging the execution process in HTML
		report.display(logger, "Passing the input text to the SearchBar");

		// Passing the text in Search Bar
		homePage.setSearch("upcomingSearchText");
		
		Assert.assertTrue(false);
		
		// Updating the status of execution to RTM
		xL.updateResultToRTM("<Search text> is displayed in the search bar");

		// Logging the execution process in HTML
		report.display(logger, "Input Value is passed and autosuggested link is clicked");

	}

	@Test(retryAnalyzer = Utility.RetryAnalyzer.class, priority = 2)
	public void navigateToUpcomingBikesPage() {

		// Initializing the elements of upcoming Honda Bikes Page
		upcomingBikes = PageFactory.initElements(driver, UpcomingBikesPage.class);

		// Logging the execution process in HTML
		report.upcomingPage(logger);

		// Asserting whether page is navigated correctly
		Assert.assertTrue(upcomingBikes.getHondaTitle().contains("Honda Bikes"));

		// Updating the status of execution to RTM
		xL.updateResultToRTM("<Upcoming Honda Bikes> page is displayed");

		// Logging the execution process in HTML
		report.display(logger, "Navigated to Upcoming Honda Bikes Page");

	}

	@Test(retryAnalyzer = Utility.RetryAnalyzer.class, priority = 3)
	public void outputDisplay() {

		// Acquiring desired result from the Page
		List<String> bikesOfManufacturer = upcomingBikes.getBikes();

		// Logging the execution process in HTML
		report.display(logger, "Asserting and displaying acquired results");

		// Asserting and Displaying acquired results
		System.out.println("***************************RESULT***************************" + "\n");
		System.out.println("Upcoming Honda Bikes (Search Bar) : ");
		for (String bike : bikesOfManufacturer) {
			Assert.assertTrue(bike.contains("Honda"));
			System.out.println(bike);
		}
		System.out.println("\n" + "***************************RESULT***************************");

		// Updating the status of execution to RTM
		xL.updateResultToRTM("Displayed <Upcoming Honda Bikes> are verified");

		// Logging the execution process in HTML
		report.display(logger, " Bikes according to the input in Search Bar are asserted and displayed ");

	}

}