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

public class Usedcarschn extends Utility.BeginnerClass {

	public static UsedCarPage usedCarPage;

	Report report = new Report();
	ExtentTest logger;

	@Test(groups = { "SmokeTest" }, retryAnalyzer = Utility.RetryAnalyzer.class)
	public void startBrowser() throws IOException {
		// Assigning the Test case ID
		caseId = "TC_22";

		// Initiating cell position for RTM to get update
		xL.setRowCellValues(caseId);

		// Creating the Test for HTML
		if (ExtentReport.log == null)
			ExtentReport.log = ExtentReport.reports.createTest("Used cars in Chennai - Direct");
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
	public void clickSearchUsedCarsFromDrpdwn() throws IOException {

		// Asserting to confirm whether Brand name is listed
		HomePage homePage = PageFactory.initElements(driver, HomePage.class);
		
		// Asserting to confirm whether Brand name is listed
		Assert.assertTrue(homePage.getUsedCarsChn().getText().contains("Chennai"));


		// Asserting whether the 'Search Used Cars' option is visible
		Assert.assertTrue(homePage.getUsedCarsElement().getText().contains("Used Cars"));
		
		// Updating the status of execution to RTM
		xL.updateResultToRTM("The 'Used Cars' dropdown is visible");
		
		// Logging the execution process in HTML
		report.display(logger,
				"Placing cursor in UsedCar dropdown and Selecting the Search Used Cars in the UsedCar drop down");

		// Clicking the given Brand
		homePage.getUsedCarsChn().click();

		// Logging the execution process in HTML
		report.select(logger, "Search Used Cars is selected in the used car Drop down");

	}

	@Test(groups = { "SmokeTest" }, retryAnalyzer = Utility.RetryAnalyzer.class, priority = 2)
	public void navigateToUsedCarsPage() throws IOException {
		
		// Initializing the elements of Upcoming Bikes page
		usedCarPage = PageFactory.initElements(driver, UsedCarPage.class);
		
		// Logging the execution process in HTML
		report.usedPage(logger);
		
		// Asserting whether page is directed correctly
		Assert.assertTrue(usedCarPage.getTitle().contains("Used Cars in Chennai"));

		// Logging the execution process in HTML
		report.display(logger, "Navigated to used cars page");
	
	}

	@Test(groups = { "SmokeTest" }, retryAnalyzer = Utility.RetryAnalyzer.class, priority = 3)
	public void outputDisplay() throws IOException {
		
		// Acquiring desired result from the Page
		List<String> popularModels = usedCarPage.getUsedcars();
		
		// Logging the execution process in HTML
		report.display(logger, "Asserting and displaying acquired results");

		// Exporting the output to Excel File
		xL.writeToExcel("Used Cars", "Car Details", popularModels);
		
		// Displaying acquired results
		System.out.println("***************************RESULT***************************" + "\n");
		System.out.println("Upcoming Honda Bikes (Sorting Filter) : ");
		for (String car : popularModels)
			System.out.println(car);
		System.out.println("Results are successfully written into output file ' Used Cars.xlsx ' !!!");
		System.out.println("Path to File : ./test-output/Output Files/Used Cars.xlsx");
		System.out.println("\n" + "***************************RESULT***************************");
		
		// Updating the status of execution to RTM
		xL.updateResultToRTM("<Used cars in the City> are displayed");
		
		// Logging the execution process in HTML
		report.display(logger, "popular model used cars in selected city are asserted and written into Excel");
	
	}

}
