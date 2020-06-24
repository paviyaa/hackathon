package Utility;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentReport {
	public static ExtentReports reports;
	public static ExtentTest log;

	public static WebDriver driver;
	static Report report = new Report();

	public static void createReport() {
		ExtentHtmlReporter htmlreporter = new ExtentHtmlReporter(
				System.getProperty("user.dir") + "//src//test//report//TestSuite Report.html");
		reports = new ExtentReports();
		reports.attachReporter(htmlreporter);
		reports.setSystemInfo("OS", "Windows");
		reports.setSystemInfo("Browser", "Chrome");
		htmlreporter.config().setDocumentTitle("Test Results");
		htmlreporter.config().setReportName("Report Consolidated");
		htmlreporter.config().setTimeStampFormat("MM-dd-yyyy HH:mm:ss");
	}

	public static void getResult(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			ExtentReport.log.fail(MarkupHelper.createLabel(result.getName() + " Test failed", ExtentColor.RED));
			try {
				if (RetryAnalyzer.useCondition == true) {
					ExtentReport.log.fail(result.getThrowable());
					ExtentReport.log.fail("Captured Screenshot", MediaEntityBuilder
							.createScreenCaptureFromPath(Screenshot.captureScreenshot(driver, result.getName(), ""))
							.build());
					System.out.println("Screenshot Captured !!");
				}
			} catch (IOException e) {
				System.out.println("Unable to capture screenshot! " + e.getMessage());
			}

		} else if (result.getStatus() == ITestResult.SUCCESS) {
			ExtentReport.log.pass(MarkupHelper.createLabel(result.getName() + " Test passed", ExtentColor.GREEN));

		} else {
			ExtentReport.log.skip(MarkupHelper.createLabel(result.getName() + " Test skipped", ExtentColor.YELLOW));
			ExtentReport.log.skip(result.getThrowable());
		}
	}

	public static void flushReport() {
		reports.flush();
	}

}