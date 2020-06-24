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

public class Price extends Utility.BeginnerClass {

	public static UpcomingBikesPage upcomingBikes;
	Report report = new Report();
	ExtentTest logger;

	@Test(retryAnalyzer = Utility.RetryAnalyzer.class)
	public void startBrowser() throws IOException {
		// Assigning the Test case ID
		caseId = "TC_12";

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

		// Logging the execution process HTML
		report.startBrowser(logger);

		// Asserting to confirm ZigWheels page
		Assert.assertTrue(driver.getTitle().contains("ZigWheels"));

		// Updating the status of execution to RTM
		xL.updateResultToRTM("Application is launched");

		// Logging the execution process HTML
		report.display(logger, "Browser is launched and navigated to Homepage of the website");

	}

	@Test(retryAnalyzer = Utility.RetryAnalyzer.class, priority = 1)
	public void clickUpcomingFromDrpdwn() throws IOException {

		// Initializing the elements of Home Page
		HomePage homePage = PageFactory.initElements(driver, HomePage.class);

		// Logging the execution process in HTML
		report.display(logger,
				"Placing cursor in NewBikes dropdown and Selecting the Upcoming Bikes in the New Bikes drop down");

		// Asserting whether Upcoming bikes is listed
		Assert.assertTrue(homePage.getUpcomingBikesElement().getText().contains("Upcoming Bikes"));

		// Updating the status of execution to RTM
		xL.updateResultToRTM("The 'New Bikes' dropdown is visible");

		// clicking upcoming bikes in dropdown
		homePage.clickUpcomingBikesElement();

		// Logging the execution process in HTML
		report.display(logger, "Upcoming Bikes is selected in the New Bikes Drop down");

	}

	@Test(retryAnalyzer = Utility.RetryAnalyzer.class, priority = 2)
	public void selectPriceRangeLink() {

		// Initializing the elements of upcoming bikespage
		upcomingBikes = PageFactory.initElements(driver, UpcomingBikesPage.class);

		// Logging the execution process in HTML
		report.upcomingPage(logger);

		// Asserting whether Upcoming Bikepage is displayed
		Assert.assertTrue(upcomingBikes.getTitle1().contains("Upcoming Bikes"));

		// Updating the status of execution to RTM
		xL.updateResultToRTM("<Upcoming Bikes page> is displayed");

		// Logging the execution process in HTML
		report.display(logger, "Navigated to Upcoming Bikes Page");
		report.select(logger, "Selecting the price Category in the Upcoming Bikes Page");

		// Selecting the price filter
		upcomingBikes.selectPriceCategory("price");

		// Asserting whether Upcoming Bikepage is displayed
		Assert.assertTrue(upcomingBikes.getTitle2().contains("Upcoming Bikes Under 5 Lakhs"));

		// Logging the execution process in HTML
		report.select(logger, "Price Category is selected and refreshed to the Selected price category page");

	}

	// getting values from the elements and asserting
	@Test(retryAnalyzer = Utility.RetryAnalyzer.class, priority = 3)
	public void outputDisplay() {

		// Acquiring desired result from the Page
		List<String> bikesOfManufacturer = upcomingBikes.sortByPrice("price");

		// Updating the status of execution to RTM
		xL.updateResultToRTM("Upcoming  Bikes under selected <Price category> are displayed");

		// Logging the execution process in HTML
		report.display(logger, "Asserting and displaying acquired results");

		// Asserting and Displaying acquired results
		System.out.println("***************************RESULT***************************" + "\n");
		System.out.println("Upcoming Honda Bikes (Price Filter) :");
		for (String bike : bikesOfManufacturer) {
			if (bike.contains("lakh")) {
				String str = bike.replaceAll("lakh onwards", "");
				str = str.replaceAll("Rs. ", "");
				String[] strArr = str.split("-");
				if (strArr.length == 1) {
					float i = Float.parseFloat(strArr[0].trim());
					Assert.assertTrue(i >= 2 && i <= 5);
				} else {
					float i1 = getFloat(strArr[0]);
					float i2 = Float.parseFloat(strArr[1].trim());
					Assert.assertTrue(i1 >= 2 || i2 <= 5);
				}
				System.out.println(bike);
			}
		}
		System.out.println("\n" + "***************************RESULT***************************");

		// Updating the status of execution to RTM
		xL.updateResultToRTM("Displayed <Upcoming Bikes> are verified");

		// Logging the execution process in HTML
		report.display(logger, "Upcoming Bikes according to Price filter selected are asserted and dispalyed");

	}

	// To convert the price to Float
	public float getFloat(String str) {

		String strCopy;
		float i1;
		if (str.contains(",")) {
			strCopy = "0." + str.replace(",", "").trim();
			i1 = Float.parseFloat(strCopy);
		} else
			i1 = Float.parseFloat(str.trim());
		return i1;

	}

}