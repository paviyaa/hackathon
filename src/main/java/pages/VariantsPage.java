package pages;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class VariantsPage extends Utility.BaseClass {

	public WebDriver driver;

	@FindBy(xpath = "//h1[@class='zm-cmn-colorBlack mobHeading inline-block']")
	WebElement resultTitle;

	@FindBy(xpath = "//div[@class='zw-con']/div/div/div/table/tbody/tr")
	List<WebElement> specs;

	@FindBy(xpath = "//div[@id='tab-container']/div/table/tbody/tr/td/span/a")
	List<WebElement> variants;

	@FindBy(xpath = "//a[@class='active']")
	WebElement titleCheck;

	public VariantsPage(WebDriver driver) {
		this.driver = driver;
	}

	// getting title
	public String getTitle() {
		return titleCheck.getText();
	}

	// Getting list of string of the selected bikes specification
	public List<String> getSpecification() {
		return getTextFromElements(driver, specs);

	}

	// Getting list of string of the selecte bikes variants name
	public List<String> getVariant() {
		return getTextFromElements(driver, variants);
	}

}