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
import pages.VariantsPage;

public class VariantsBikes extends Utility.BeginnerClass {

	public static UpcomingBikesPage upcomingBikes;
	Report report = new Report();
	ExtentTest logger;

	@Test(retryAnalyzer = Utility.RetryAnalyzer.class)
	public void startBrowser() throws IOException {
		// Assigning the Test case ID
		caseId = "TC_16";

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

		// Logging the execution process in Excel and HTML
		report.display(logger, "Browser is launched and navigated to Homepage of the website");

	}

	@Test(retryAnalyzer = Utility.RetryAnalyzer.class, priority = 1)
	public void loadUpcomingBikesPage() throws IOException {

		// Initializing the elements of Home Page
		HomePage homePage = PageFactory.initElements(driver, HomePage.class);

		// Asserting to confirm whether search bar is displayed
		Assert.assertTrue(homePage.getSearchBarElement().isDisplayed());

		// Logging the execution process in HTML
		report.select(logger, "Passing the Input Text into the search bar");

		// Passing the text in Search Bar
		homePage.setSearch("upcomingSearchText");

		// Updating the status of execution to RTM
		xL.updateResultToRTM("<Search text> is displayed in the search bar");

		// Logging the execution process in HTML
		report.display(logger, "Input Value is passed and autosuggested link is clicked");

	}

	@Test(retryAnalyzer = Utility.RetryAnalyzer.class, priority = 2)
	public void selectBike() throws IOException {
		// Initializing the elements of Upcoming Bikes page
		upcomingBikes = PageFactory.initElements(driver, UpcomingBikesPage.class);

		// Logging the execution process in HTML
		report.upcomingPage(logger);

		// Asserting whether page is directed correctly
		Assert.assertTrue(upcomingBikes.getHondaTitle().contains("Honda Bikes"));

		// Updating the status of execution to RTM
		xL.updateResultToRTM("<Upcoming Honda Bikes> page is displayed");

		// Logging the execution process in HTML
		report.display(logger, "Navigated to the Upcoming Honda Bikes Page");
		report.select(logger, "Selecting the first bike displayed");

		// Clicking the link of first displayed bike
		upcomingBikes.selectBike(0);

		// Logging the execution process in HTML
		report.display(logger, "First displayed bike is  selected and redirected to the bike page");
	}

	@Test(retryAnalyzer = Utility.RetryAnalyzer.class, priority = 3)
	public void outputDisplay() {

		// Initializing the elements of Varaints Page
		VariantsPage variantsPage = PageFactory.initElements(driver, VariantsPage.class);

		// Updating the status of execution to RTM
		xL.updateResultToRTM("Selected bike page is displayed");

		// Logging the execution process in HTML
		report.display(logger, "Asserting and displaying acquired results");

		// Acquiring desired result from the Page
		List<String> variants = variantsPage.getVariant();

		// Asserting and Displaying acquired results
		System.out.println("***************************RESULT***************************" + "\n");
		System.out.println("Upcoming Honda Bikes (First Variant) : ");
		String title = variantsPage.getTitle();
		for (String bikes : variants) {
			Assert.assertTrue(bikes.contains(title));
			System.out.println(bikes);
		}
		System.out.println("\n" + "***************************RESULT***************************");

		// Updating the status of execution to RTM
		xL.updateResultToRTM("Varaints of the selected bike <Honda> is verified");

		// Logging the Execution into HTML
		report.display(logger, "the variants of first Honda bike displayed is asserted and displayed");
	}

}
