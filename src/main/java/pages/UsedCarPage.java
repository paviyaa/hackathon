package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import Utility.ExcelUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class UsedCarPage extends Utility.BaseClass {

	public WebDriver driver;
	Map<String, String> data;

	@FindBy(id = "usedcarttlID")
	WebElement resultTitle;

	@FindBy(xpath = "//div[@class='gsc_thin_scroll']/ul/li/label")
	List<WebElement> getusedCars;

	@FindBy(id = "gs_input5")
	WebElement sendCity;

	@FindBy(xpath = "//body/ul/li[1]/a[contains( text(),'Chennai')]")
	WebElement selectCity;

	public UsedCarPage(WebDriver driver) throws Exception {
		this.driver = driver;
		this.data = ExcelUtils.readExcel();
	}

	// Getting title
	public String getTitle() {
		waitForText(driver, resultTitle, "Chennai");
		return resultTitle.getText();
	}

	// Getting list of string of the usercars models
	public List<String> getUsedcars() {
		return getTextFromElements(driver, getusedCars);

	}

	// locate and pass the value
	public void selectCity(String city) {
		if (!getEnteredValue(sendCity).isEmpty()) {
			sendCity.clear();
		}
		sendCity.sendKeys(data.get(city));
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		waitForElement(driver, selectCity);
		selectCity.click();

	}

}
