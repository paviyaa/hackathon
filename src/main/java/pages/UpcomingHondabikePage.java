package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UpcomingHondabikePage extends Utility.BaseClass {

	public WebDriver driver;

	@FindBy(xpath = "//div[@id='brandDropdown']//h1")
	WebElement resultTitle;

	@FindBy(xpath = "//div[@id='upcoming']/div/div[@id='zw-cmnSilder']//ul/li")
	List<WebElement> HondaUpcomingbikes;

	@FindBy(xpath = "//div[@id='upcoming']//a[2]")
	WebElement slider;

	public UpcomingHondabikePage(WebDriver driver) {
		this.driver = driver;
	}

	// locate and return the list of string of upcoming bikes
	public List<String> getUpcomingBikes() {
		waitForText(driver, resultTitle, "Honda Bikes");
		return getTextFromElements(driver, HondaUpcomingbikes);
	}

	// locate and return title
	public String getTitle() {
		return resultTitle.getText();
	}

}
