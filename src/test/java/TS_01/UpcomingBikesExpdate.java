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

public class UpcomingBikesExpdate extends Utility.BeginnerClass {
	public static UpcomingBikesPage upcomingBikes;
	Report report = new Report();
	ExtentTest logger;

	@Test(retryAnalyzer = Utility.RetryAnalyzer.class)
	public void startBrowser() throws IOException {
		// Assigning the Test case ID
		caseId = "TC_14";

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
	public void setSearchValue() throws IOException {

		// Initializing the elements of Home Page
		HomePage homePage = PageFactory.initElements(driver, HomePage.class);

		// Asserting whether Search Bar is displayed
		Assert.assertTrue(homePage.getSearchBarElement().isDisplayed());

		// Logging the execution process in HTML
		report.display(logger, "Passing the input text to the SearchBar");

		// Passing the text in Search Bar
		homePage.setSearch("upcomingSearchText");

		// Updating the status of execution to RTM
		xL.updateResultToRTM("<Search text> is displayed in the search bar");

		// Logging the execution process in HTML
		report.display(logger, "Input Value is passed and autosuggested link is clicked");

	}

	@Test(retryAnalyzer = Utility.RetryAnalyzer.class, priority = 2)
	public void loadUpcomingBikesPage() {

		// Initializing the elements of Upcoming Bikes page
		upcomingBikes = PageFactory.initElements(driver, UpcomingBikesPage.class);

		// Logging Execution in HTML
		report.upcomingPage(logger);

		// Asserting whether page is directed correctly
		Assert.assertTrue(upcomingBikes.getHondaTitle().contains("Honda Bikes"));

		// Updating the status of execution to RTM
		xL.updateResultToRTM("<Upcoming Honda Bikes> page is displayed");

		// Logging Execution in HTML
		report.display(logger, "Navigated to the Upcoming Honda Bikes Page");

	}

	@Test(retryAnalyzer = Utility.RetryAnalyzer.class, priority = 3)
	public void outputDisplay() {

		// Acquiring desired result from the Page
		List<String> bikesOfManufacturer = upcomingBikes.getLaunchDate();

		// Logging the execution process in HTML
		report.display(logger, "Asserting and displaying acquired results");

		// Asserting and Displaying acquired results
		System.out.println("***************************RESULT***************************" + "\n");
		System.out.println("Upcoming Honda Bikes (Exp Date) :");
		for (int i = 0; i < bikesOfManufacturer.size() - 1; i++) {
			String bike1[] = bikesOfManufacturer.get(i).replaceAll("Exp. Launch : ", "").replace("30 ", "").split(" ");
			String bike2[] = bikesOfManufacturer.get(i + 1).replaceAll("Exp. Launch : ", "").split(" ");
			int m1 = getMonthIndex(bike1[0]);
			int m2 = getMonthIndex(bike2[0]);
			Assert.assertTrue(m1 <= m2);
			Assert.assertTrue(Integer.parseInt(bike1[1]) <= Integer.parseInt(bike2[1]));
			System.out.println(bikesOfManufacturer.get(i));
		}
		System.out.println("\n" + "***************************RESULT***************************");

		// Updating the status of execution to RTM
		xL.updateResultToRTM("Expected launch date of the displayed bike <Honda> is verified");

		// Logging the execution process in HTML
		report.display(logger, "Launch date of the Upcoming honda Bikes are asserted and displayed ");

	}

	// To get the Numeric value of Month
	public int getMonthIndex(String bike) {

		String month[] = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		for (int j = 0; j < month.length; j++)
			if (month[j] == bike)
				return (j + 1);
		return -1;

	}

}