package TS_04;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import Utility.ExtentReport;
import Utility.Report;
import pages.HomePage;
import pages.NewBikesPage;
import pages.NewBikesSearchPage;

public class SearchByBudget extends Utility.BeginnerClass {

	NewBikesSearchPage newBikesSearch;
	NewBikesPage newBikes;
	Report report = new Report();
	ExtentTest logger;

	@Test(retryAnalyzer = Utility.RetryAnalyzer.class)
	public void startBrowser() throws IOException {
		
		//Assigning the Testcase ID
		caseId = "TC_42";

		// Initiating cell position for RTM to get update
		xL.setRowCellValues(caseId);

		// Creating Search by brand Test for HTML
		if (ExtentReport.log == null)
			ExtentReport.log = ExtentReport.reports.createTest("Search new bikes by Budget");
		logger = ExtentReport.log;

		// Updating the status of execution to RTM
		if (driver != null)
			xL.updateResultToRTM("Browser is launched");
		else
			xL.updateResultToRTM("Browser is not launched");

		// Logging the execution process in Excel and HTML
		report.startBrowser(logger);

		// Asserting to confirm ZigWheels page
		Assert.assertTrue(driver.getTitle().contains("ZigWheels"));

		// Updating the status of execution to RTM
		xL.updateResultToRTM("Application is launched");

		// Logging the execution process in HTML
		report.display(logger, "Browser is launched and navigated to Homepage of the website");

	}

	@Test(retryAnalyzer = Utility.RetryAnalyzer.class, priority = 1)
	public void clickSearchBikesFromDrpdwn() {

		// Initializing the elements of Home Page
		HomePage homePage = PageFactory.initElements(driver, HomePage.class);

		// Asserting to confirm whether Brand name is listed
		Assert.assertTrue(homePage.getSearchBikesElement().getText().contains("Search"));

		// Updating the status of execution to RTM
		xL.updateResultToRTM("The 'New Bikes' dropdown is visible");

		// Logging the execution process in HTML
		report.display(logger,
				"Placing cursor in NewBikes dropdown and Selecting the Search Bikes in the New Bikes drop down");
		// Clicking the given Brand
		homePage.getSearchBikesElement().click();

		// Logging the execution process in HTML
		report.select(logger, "Search Bikes is selected in the New Bikes Drop down");

	}

	@Test(retryAnalyzer = Utility.RetryAnalyzer.class, priority = 2)
	public void navigateToNewBikesPage() {
		// Initializing the elements of New Bikes Page
		newBikes = PageFactory.initElements(driver, NewBikesPage.class);

		// Logging the execution process in HTML
		report.newPage(logger);

		// Asserting whether page is navigated correctly
		Assert.assertTrue(newBikes.getTitle().contains("New Bikes in India"));

		// Updating the status of execution to RTM
		xL.updateResultToRTM("<New Bikes> page is displayed");

		// Logging the execution process in HTML
		report.display(logger, "Navigatd to new bikes page");
	}
	
	@Test(retryAnalyzer = Utility.RetryAnalyzer.class, priority = 3)
	public void navigateToNewBikesSearchPage() {

		// Asserting whether Search Button is displayed
		Assert.assertTrue(newBikes.getSearchElement().isDisplayed());

		// Logging the execution process in HTML
		report.display(logger, "Clicking search button in New Bikes  page");

		// Clicking the Search Button
		newBikes.getSearchElement().click();

		// Logging the execution process in HTML
		report.display(logger, "Search button is clicked and redirecting to New Bikes Search page");

		// Initializing the elements of New Bikes Search Page
		newBikesSearch = PageFactory.initElements(driver, NewBikesSearchPage.class);

		// Asserting whether Search Button is displayed
		Assert.assertTrue(newBikesSearch.getTitle().contains("New Bikes Search"));

		// Updating the status of execution to RTM
		xL.updateResultToRTM("<New Bikes Search> page is displayed");

		// Logging the execution process in HTML
		report.display(logger, "Redirected to New Bikes Search Page");

	}

	@Test(retryAnalyzer = Utility.RetryAnalyzer.class, priority = 4)
	public void setBudgetFilter() {

		// Logging the execution process in HTML
		report.display(logger, "Selecting filter for budget in the search page");
		
		// Applying Filter
		newBikesSearch.selectBudget("priceRange");

		// Logging the execution process in HTML
		report.select(logger, "budget from filter is selected");
	
	}

	@Test(retryAnalyzer = Utility.RetryAnalyzer.class, priority = 5)
	public void outputDisplay() {
		
		// Acquiring desired result from the Page
		List<String> Bikes = newBikesSearch.getBudget();

		// Updating the status of execution to RTM
		xL.updateResultToRTM("<New Bikes Search> page is displayed with applied filter");

		// Logging the execution process in HTML
		report.display(logger, "Asserting and displaying acquired results");

		// Asserting and Displaying acquired results
		System.out.println("***************************RESULT***************************" + "\n");
		System.out.println("New Bikes ( Filter) :");
		for (String bike : Bikes) {
			if (bike.contains("lakh")) {
				String str = bike.replaceAll("lakh", "");
				str = str.replaceAll("Rs. ", "");
				String[] strArr = str.split("-");
				if (strArr.length == 1) {
					float i = Float.parseFloat(strArr[0].trim());
					Assert.assertTrue(i >= 1 && i <= 2);
				} else {
					String strCopy;
					float i1;
					if (strArr[0].contains(",")) {
						strCopy = "0." + strArr[0].replaceAll(",", "").trim();
						i1 = Float.parseFloat(strCopy);
					} else
						i1 = Float.parseFloat(strArr[0].trim());
					float i2 = Float.parseFloat(strArr[1].trim());
					Assert.assertTrue(i1 >= 1 || i2 <= 2);
				}
			}
			System.out.println(bike);
		}
		System.out.println("\n" + "***************************RESULT***************************");

		// Updating the status of execution to RTM
		xL.updateResultToRTM("Displayed <New Bikes> are verified");

		// Logging the execution process in HTML
		report.display(logger, "New Bikes in the selected budget range are asserted and displayed");

	}

}