package setup;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import Utility.BaseClass;

public class EnvironmentSetup extends BaseClass {

	protected static WebDriver driver;
    static Properties prop;
	public static WebDriver getDriver(String browser, String environment, String nodeURL)  {

		// Creating object for driverSetup
		DriverSetup setup = new DriverSetup();
		try { 
			prop= loadProp();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Assigning environment for the testcases to run
		if (environment.equalsIgnoreCase("grid")) {
			driver = setup.createDriverGrid(browser.toLowerCase(), nodeURL);
		}

		else if (environment.equalsIgnoreCase("local")) {
			driver = setup.createDriver(browser.toLowerCase());
		}

		// Maximizing the Browser Window
		driver.manage().window().maximize();

		// Navigating to the Official Website
		driver.get(prop.getProperty("url"));

		// Waiting for the page to load completely
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		return driver;
	}
}
