package Utility;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Screenshot {

	public static String captureScreenshot(WebDriver driver, String fileName, String type) throws IOException {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String screenshotPath = System.getProperty("user.dir") + "//src//screenshot//Error Captures//" + fileName
				+ " " + getCurrentDateTime() +".png";

		if (type.equalsIgnoreCase("Testcase"))
			screenshotPath = System.getProperty("user.dir") + "//src//screenshot//Ouput Captures//" + fileName + ".png";

		FileUtils.copyFile(src, new File(screenshotPath));

		return screenshotPath;
	}

	public static String getCurrentDateTime() {
		// MM-dd-yyyy_HH:mm:ss
		DateFormat customFormat = new SimpleDateFormat("dd MMM yyyy-HH_mm_ss");
		Date currentDate = new Date();

		return customFormat.format(currentDate);
	}
}