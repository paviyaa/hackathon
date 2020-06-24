package Utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseClass {

	WebDriver driver;
	String child;
	public static int executionCount = 0;

	// Selecting options in dropdown
	public void selectInDropDown(WebElement el, String choice) {
		Select dropDown = new Select(el);
		int index = 0;
		for (WebElement option : dropDown.getOptions()) {
			if (option.getText().equalsIgnoreCase(choice))
				break;
			index++;
		}
		dropDown.selectByIndex(index);
	}

	// converting list of webelements into list of string
	public List<String> getTextFromElements(WebDriver driver, List<WebElement> els) {
		List<String> result = new ArrayList<String>();
		for (WebElement el : els) {
			result.add(el.getText());
		}
		return result;
	}

	// Wait for the visibility of the element
	public void waitForElement(WebDriver driver, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	// wait for the text to be present in the element
	public void waitForText(WebDriver driver, WebElement element, String text) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.textToBePresentInElement(element, text));
	}

	// for doing mouse hover using action class
	public void doAction(WebDriver driver, WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
	}

	// Switching from parent tab to child tab
	public void SwitchToNewWindow(WebDriver driver) {
		Set<String> winHandles = driver.getWindowHandles();
		System.out.println(winHandles);
		Iterator<String> itr = winHandles.iterator();
		while (itr.hasNext()) {
			child = itr.next();
			System.out.println(child);
		}
		driver.switchTo().window(child);
	}

	// To read the properties file
	public static Properties loadProp() throws IOException {
		Properties prop = new Properties();
		try {
			InputStream readFile = new FileInputStream(
					System.getProperty("user.dir") + "//src//main//resources//InputData.properties");
			prop.load(readFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return prop;
	}
}
