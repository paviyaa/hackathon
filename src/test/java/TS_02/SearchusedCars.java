package TS_02;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import Utility.ExtentReport;
import Utility.Report;
import pages.HomePage;
import pages.UsedCarPage;

public class SearchusedCars extends Utility.BeginnerClass {

	public static UsedCarPage usedCarPage;
	Report report = new Report();
	ExtentTest logger;

	@Test(retryAnalyzer = Utility.RetryAnalyzer.class)
	public void startBrowser() throws IOException {
		// Assigning the Test case ID
		caseId = "TC_21";

		// Initiating cell position for RTM to get update
		xL.setRowCellValues(caseId);

		// Creating the Test for HTML
		if (ExtentReport.log == null)
			ExtentReport.log = ExtentReport.reports.createTest("Used cars in Chennai - Search");
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
	public void clickSearchUsedCarsFromDrpdwn() throws IOException {

		// Initializing the elements of Home page elements
		HomePage homePage = PageFactory.initElements(driver, HomePage.class);

		// Logging the execution process in HTML
		report.display(logger,
				"Placing cursor in UsedCar dropdown and Selecting the Search Used Cars in the UsedCar drop down");

		// Asserting whether the 'Search Used Cars' option is visible
		Assert.assertTrue(homePage.getUsedCarsElement().getText().contains("Used Cars"));

		// Updating the status of execution to RTM
		xL.updateResultToRTM("The 'Search Used Cars' dropdown is visible");

		// Clicking the 'Search Used Cars' option
		homePage.getUsedCarsElement().click();

		// Logging the execution process in HTML
		report.select(logger, "Search Used Cars is selected in the used car Drop down");

	}

	@Test(retryAnalyzer = Utility.RetryAnalyzer.class, priority = 2)
	public void setCityFilter() throws IOException {

		// Initializing the elements of Upcoming Bikes page
		usedCarPage = PageFactory.initElements(driver, UsedCarPage.class);

		// Logging the execution process in HTML
		report.usedPage(logger);
		report.select(logger, "Passing the city into the text box");

		// Setting the City filter as Chennai
		usedCarPage.selectCity("city");

		// Logging the execution process in HTML
		report.display(logger, "City value is passed and clicked");

	}

	@Test(retryAnalyzer = Utility.RetryAnalyzer.class, priority = 3)
	public void navigateToUsedCarsPage() {

		System.out.println(usedCarPage.getTitle());

		// Asserting whether the page is directed correctly
		Assert.assertTrue(usedCarPage.getTitle().contains("Chennai"));

		// Logging the execution process in HTML
		report.display(logger, "Navigated to used cars page");

		// Updating the status of execution to RTM
		xL.updateResultToRTM("<Used Cars page> is displayed");
	}

	@Test(retryAnalyzer = Utility.RetryAnalyzer.class, priority = 4)
	public void outputDisplay() {

		// Acquiring desired result from the Page
		List<String> popularModels = usedCarPage.getUsedcars();

		// Logging the execution process in HTML
		report.display(logger, "Asserting and displaying acquired results");

		// Asserting and Displaying acquired results
		for (String car : popularModels)
			System.out.println(car);

		// Updating the status of execution to RTM
		xL.updateResultToRTM("<Used cars in the City> are displayed");

		// Logging the execution process in HTML
		report.display(logger, "popular model used cars in selected city are asserted and displayed");

	}
}