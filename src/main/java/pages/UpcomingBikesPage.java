package pages;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import Utility.ExcelUtils;

public class UpcomingBikesPage extends Utility.BaseClass {

	WebDriver driver;
	Map<String,String> data;
	
	@FindBy(xpath = "//ol//li[2]//a[1]//span[1]")
	WebElement resultTitle;
	
	@FindBy(xpath="//ol//li[3]//span[1]")
	WebElement HondaTitle;
	
	@FindBy(xpath="//html//body//main//div//div//div//div//div//div//div//div//h1")
	WebElement resultTitle2;

	@FindBy(xpath = "//select[@id='makeId']")
	WebElement selectManufacturerDropDown;

	@FindBy(xpath = "//select[@id='sorting']")
	WebElement sortByDropDown;

	@FindBy(xpath = "//div[@class='zw-con']//a")
	List<WebElement> selectBrandsBtn;

	@FindBy(xpath = "//div[@id='carModels']//strong/parent::a")
	List<WebElement> bikeNames;

	@FindBy(xpath = "//div[@id='carModels']//a[1]/strong")
	List<WebElement> modelNames;

	@FindBy(xpath = "//div[@id='carModels']//a[1]/strong/following::div[1]")
	List<WebElement> modelPrices;

	@FindBy(xpath = "//div[@id='carModels']//a[1]/strong/following::div[2]")
	List<WebElement> expectedLaunch;


	public UpcomingBikesPage(WebDriver driver) throws Exception {
		this.driver = driver;
		this.data = ExcelUtils.readExcel();
	}

	public List<String> filterByManufacturer(String manufacturer) {
		waitForElement(driver, selectManufacturerDropDown);
		selectInDropDown(selectManufacturerDropDown, data.get(manufacturer));
		return getTextFromElements(driver, modelNames);
	}
	
	public List<String> sortByLaunchDate() {
		return getTextFromElements(driver, expectedLaunch);
	}

	public List<String> sortByPrice(String conditionkey) {
		return getTextFromElements(driver, modelPrices);
	}

	public List<String> filterByBrand(String brand) {
		for (WebElement brandBtn : selectBrandsBtn) {
			String brandBtnText = brandBtn.getText();
			if (brandBtnText.equalsIgnoreCase(data.get(brand))) {
				brandBtn.click();
				break;
			}
		}
		waitForText(driver, resultTitle, "Upcoming Honda Bikes in India");
		return getTextFromElements(driver, modelNames);
	}

	// Selecting bikes link in the passed index
	public void selectBike(int index) {
		bikeNames.get(index).click();
	}

	// Selecting manufacturer
	public void selectManufacturer(String manufacturer) {
		selectInDropDown(selectManufacturerDropDown, data.get(manufacturer));
	}

	// Getting list of string of the selected brand bikes name
	public List<String> getBikes() {
		return getTextFromElements(driver, modelNames);
	}

	// Getting list of string of the upcoming bikes launch date
	public List<String> getLaunchDate() {
		return getTextFromElements(driver, expectedLaunch);
	}

	// selecting upcoming by price category filter
	public void selectPriceCategory(String price) {
		driver.findElement(By.linkText(data.get(price))).click();
	}
	
	//getting title of the page
	public String getTitle1() {
	    return resultTitle.getText();
	}
	//getting title of the page
	public String getTitle2() {
	    return resultTitle2.getText();
	}
	
	public String getHondaTitle() {
	    return HondaTitle.getText();
	}
}