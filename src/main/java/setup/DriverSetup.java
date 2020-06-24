package setup;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.grid.selenium.GridLauncherV3;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverSetup {

	private ThreadLocal<WebDriver> TL_driver = new ThreadLocal<WebDriver>();

	// Creating Webdriver for Testcase execution locally
	public WebDriver createDriver(String browser) {
		System.out.println("Starting " + browser + " locally");

		switch (browser) {
		case "chrome":
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("profile.default_content_setting_values.notifications", 2);
			ChromeOptions option = new ChromeOptions();
			option.setExperimentalOption("prefs", prefs);

			WebDriverManager.chromedriver().arch64().browserVersion("83.0.4103.106").setup();
			TL_driver.set(new ChromeDriver(option));
			break;

		case "firefox":

			WebDriverManager.firefoxdriver().arch64().browserVersion("77.0.1").setup();
			TL_driver.set(new FirefoxDriver());
			break;

		case "ie":
			WebDriverManager.iedriver().arch32().browserVersion("11.1282.17763.0").setup();
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			TL_driver.set(new InternetExplorerDriver());
			break;

		case "edge":
			WebDriverManager.edgedriver().arch64().browserVersion("83.0.478.54").setup();
			TL_driver.set(new EdgeDriver());
			break;

		case "opera":
			WebDriverManager.operadriver().arch64().browserVersion("68.0.3618.173").setup();
			TL_driver.set(new OperaDriver());
			break;
		}

		return TL_driver.get();
	}

	public void startHubNode() {
		// Launching Hub
		GridLauncherV3.main(new String[] { "-role", "hub", "-port", "4445" });

		// Launching Node
		GridLauncherV3.main(new String[] { "-role", "node", "-hub", "http://localhost:4445/grid/register", "-browser",
				"browserName=chrome,maxInstances=5", "-browser",
				"browserName=firefox,maxInstances=5", "-browser",
				"browserName=iexplore,maxInstances=5", "-browser",
				"browserName=edge,maxInstances=5", "-browser",
				"browserName=opera,maxInstances=5", "-port", "6666" });
	}

	// Creating WebTL_driver for Testcase execution in Selenium Grid
	@SuppressWarnings("deprecation")
	public WebDriver createDriverGrid(String browser, String nodeURL) {

		DesiredCapabilities capabilities = new DesiredCapabilities();
		System.out.println("Starting " + browser + " on grid");

		// Starting Hub and Node
		startHubNode();

		// Creating driver
		switch (browser) {
		case "chrome":
			WebDriverManager.chromedriver().arch64().browserVersion("83.0.4103.106").setup();
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("profile.default_content_setting_values.notifications", 2);
			ChromeOptions option = new ChromeOptions();
			option.setExperimentalOption("prefs", prefs);
			capabilities.setBrowserName(DesiredCapabilities.chrome().getBrowserName());
			break;

		case "firefox":
			WebDriverManager.firefoxdriver().arch64().browserVersion("77.0.1").setup();
			capabilities.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
			break;

		case "ie":
			WebDriverManager.iedriver().arch32().browserVersion("11.1282.17763.0").setup();
			capabilities.setBrowserName(DesiredCapabilities.internetExplorer().getBrowserName());
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			break;

		case "edge":
			WebDriverManager.edgedriver().arch64().browserVersion("83.0.478.54").setup();
			capabilities.setBrowserName(DesiredCapabilities.edge().getBrowserName());
			break;

		case "opera":
			WebDriverManager.operadriver().arch64().browserVersion("68.0.3618.173").setup();
			capabilities.setBrowserName(DesiredCapabilities.opera().getBrowserName());
			break;
		}

		try {
			TL_driver.set(new RemoteWebDriver(new URL(nodeURL), capabilities));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return TL_driver.get();
	}
}