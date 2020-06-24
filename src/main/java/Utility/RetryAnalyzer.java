package Utility;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

	private int retryCount = 1;
	private static final int maxRetryCount = 3;
	public static boolean useCondition = false;
	Report report = new Report();

	@Override
	public boolean retry(ITestResult result) {
		if (retryCount <= maxRetryCount) {

			if (retryCount == maxRetryCount)
				useCondition = true;

			System.out.println("Test Failed! Retry Attempt: " + retryCount + "\n" + "Retrying.....");

			report.display(ExtentReport.log, "Test Failed! Retry Attempt: " + retryCount);

			ExcelUtils.resetRowValue(BeginnerClass.rtmExecution);

			BeginnerClass.rtmExecution = 0;

			retryCount++;
			return true;
		}
		return false;
	}
}