
package TS_01;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import Utility.ExtentReport;
import Utility.Report;
import pages.UpcomingBikesPage;
import pages.HomePage;

public class UpcomingRequirement extends Utility.BeginnerClass {

	public static UpcomingBikesPage upcomingBikes;
	Report report = new Report();
	ExtentTest logger;

	@Test(groups = { "SmokeTest" }, retryAnalyzer = Utility.RetryAnalyzer.class)
	public void startBrowser() throws IOException {
		// Assigning the Test case ID
		caseId = "TC_15";

		// Initiating cell position for RTM to get update
		xL.setRowCellValues(caseId);

		// Creating the Test for HTML
		if (ExtentReport.log == null)
			ExtentReport.log = ExtentReport.reports.createTest("UpcomingBIkes display");
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

	@Test(groups = { "SmokeTest" }, retryAnalyzer = Utility.RetryAnalyzer.class, priority = 1)
	public void loadUpcomingBikesPage() throws IOException {

		// Initializing the elements of Home Page
		HomePage homePage = PageFactory.initElements(driver, HomePage.class);

		// Asserting to confirm whether search bar is displayed
		Assert.assertTrue(homePage.getSearchBarElement().isDisplayed());

		// Logging the execution process in HTML
		report.select(logger, "Passing the Input Text into the search bar");

		// Updating the status of execution to RTM
		xL.updateResultToRTM("<Search text> is displayed in the search bar");

		// Passing the text in Search Bar
		homePage.setSearch("upcomingSearchText");

		// Logging the execution process in HTML
		report.display(logger, "Input Value is passed and autosuggested link is clicked");

	}

	@Test(groups = { "SmokeTest" }, retryAnalyzer = Utility.RetryAnalyzer.class, priority = 2)
	public void navigateToUpcomingBikesPage() {

		// Initializing the elements of upcoming Honda Bikes Page
		upcomingBikes = PageFactory.initElements(driver, UpcomingBikesPage.class);

		// Logging the execution process in HTML
		report.HondaPage(logger);

		// Asserting whether Upcoming Bikepage is displayed
		Assert.assertTrue(upcomingBikes.getTitle1().contains("Upcoming Bikes"));

		// Updating the status of execution to RTM
		xL.updateResultToRTM("<Upcoming Honda Bikes> page is displayed");

		// Logging the execution process in HTML
		report.display(logger, "Navigated to upcoming Honda Bikes page");

	}

	@Test(groups = { "SmokeTest" }, retryAnalyzer = Utility.RetryAnalyzer.class, priority = 3)
	public void outputDisplay() throws IOException {

		// Applying the sorting filter <Descending>
		List<String> bikePrice = upcomingBikes.sortByPrice("priceSortFilter");

		// Acquiring desired result from the Page
		List<String> bikeName = upcomingBikes.getBikes();
		List<String> bikeLaunch = upcomingBikes.getLaunchDate();
		List<Integer> intPrice = getPrice(bikePrice);

		// Declaration of result tags
		List<String> resultPrice = new ArrayList<String>();
		List<String> resultName = new ArrayList<String>();
		List<String> resultLaunch = new ArrayList<String>();

		// Logging the Execution into HTML
		report.display(logger, "Asserting and displaying acquired results");

		// Asserting and Assigning acquired results to respective result tags
		for (int i = 0; i < intPrice.size(); i++) {
			if (intPrice.get(i) <= 400000) {
				resultPrice.add(bikePrice.get(i));
				resultName.add(bikeName.get(i));
				resultLaunch.add(bikeLaunch.get(i));
			}
		}

		// Updating the status of execution to RTM
		xL.updateResultToRTM("Price and Name of the displayed bike <Honda> is verified for given requirement");

		// Logging the Execution into HTML
		report.display(logger, "Upcoming bikes of the brand honda are asserted and written into Excel");

		// Exporting the result to Excel-Output File
		xL.writeToExcel("Upcoming Requirements", "Requirement", resultName);
		xL.updateToExcel("Upcoming Requirements", resultPrice);
		xL.updateToExcel("Upcoming Requirements", resultLaunch);

		// Displaying acquired results
		System.out.println("***************************RESULT***************************" + "\n");
		System.out.println("Upcoming Honda Bikes (Sorting Filter) : ");
		System.out.println("Results are successfully written into output file ' Upcoming Requirements.xlsx ' !!!");
		System.out.println("Path to File : ./test-output/Output Files/Upcoming Requirements.xlsx");
		System.out.println("\n" + "***************************RESULT***************************");

	}

	// To convert List of Strings into List of Integers
	public static List<Integer> getPrice(List<String> priceList) {

		List<String> trimPrice = new ArrayList<String>();
		List<Integer> intPrice = new ArrayList<Integer>();
		for (String price : priceList) {
			trimPrice.add(price.split("\\s+")[1]);
		}
		for (int i = 0; i < trimPrice.size(); i++) {
			if (trimPrice.get(i).contains(",")) {
				intPrice.add(Integer.parseInt(trimPrice.get(i).replaceAll(",", "")));
			} else if (trimPrice.get(i).contains(".")) {
				double temp = Double.parseDouble(trimPrice.get(i));
				intPrice.add((int) (temp * 100000));
			}
		}
		return intPrice;

	}

}