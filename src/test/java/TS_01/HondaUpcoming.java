package TS_01;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import Utility.ExtentReport;
import Utility.Report;
import pages.HomePage;
import pages.UpcomingHondabikePage;

public class HondaUpcoming extends Utility.BeginnerClass {

	public static UpcomingHondabikePage upcomingBikes;
	Report report = new Report();
	ExtentTest logger;

	@Test(retryAnalyzer = Utility.RetryAnalyzer.class)
	public void startBrowser() throws IOException {
		// Assigning the Test case ID
		caseId = "TC_13";

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
	public void clickBrandFromDrpdwn() {

		// Asserting to confirm whether Brand name is listed
		HomePage homePage = PageFactory.initElements(driver, HomePage.class);

		// Asserting whether Honda is displayed in the Drop down
		Assert.assertTrue(homePage.getBrandElement().getText().contains("Honda"));

		// Updating the status of execution to RTM
		xL.updateResultToRTM("The 'New Bikes' dropdown is visible");

		// Logging the execution process in HTML
		report.display(logger,
				"Placing cursor in NewBikes dropdown and Selecting the brand Honda in the New Bikes drop down");

		// Clicking the given Brand
		homePage.clickBrand();

		// Logging the execution process in HTML
		report.select(logger, "Honda brand is selected in the New Bikes Drop down");

	}

	@Test(retryAnalyzer = Utility.RetryAnalyzer.class, priority = 2)
	public void navigateToUpcomingBikesPage() {

		// Initializing the elements of upcoming Honda Bikes Page
		upcomingBikes = PageFactory.initElements(driver, UpcomingHondabikePage.class);

		// Logging the execution process in HTML
		report.HondaPage(logger);

		// Asserting whether page is navigated correctly
		Assert.assertTrue(upcomingBikes.getTitle().contains("Honda Bikes"));

		// Updating the status of execution to RTM
		xL.updateResultToRTM("<Honda Bikes> page is displayed");

		// Logging the execution process in HTML
		report.display(logger, "Navigated to upcoming Honda Bikes Page");

	}

	@Test(retryAnalyzer = Utility.RetryAnalyzer.class, priority = 3)
	public void outputDisplay() {

		// Acquiring desired result from the Page
		List<String> hondabikes = upcomingBikes.getUpcomingBikes();

		// Logging the execution process in HTML
		report.display(logger, "Asserting and displaying acquired results");

		// Removing spaces if present in the acquired results
		Iterator<String> itr = hondabikes.iterator();
		while (itr.hasNext()) {
			String str = (String) itr.next();
			if (str.isEmpty())
				itr.remove();
		}

		// Asserting and Displaying acquired results
		System.out.println("***************************RESULT***************************" + "\n");
		System.out.println("Upcoming Honda Bikes (Drop down) :");
		for (String bikes : hondabikes) {
			Assert.assertTrue(bikes.contains("Honda"));
			System.out.println(bikes);
		}
		System.out.println("\n" + "***************************RESULT***************************");

		// Updating the status of execution to RTM
		xL.updateResultToRTM("Displayed <Upcoming Honda Bikes> are verified");

		// Logging the execution process in HTML
		report.display(logger, "Upcoming bikes of Honda are asserted and displayed");

	}
}