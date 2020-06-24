package pages;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import Utility.ExcelUtils;

public class NewBikesSearchPage extends Utility.BaseClass {
	public WebDriver driver;
	Properties prop;
	Map<String, String> data;

	@FindBy(id = "make_suggest")
	WebElement brand;

	@FindBy(xpath = "//h1[contains(@class,'mt-5 pt-15 n zm-cmn-colorBlack')]")
	WebElement pageTitle;

	@FindBy(xpath = "//li[@class='ui-menu-item']")
	WebElement autoSuggest;

	@FindBy(linkText = "Clear All")
	WebElement clear;
	
	@FindBy(xpath = "//span[contains(text(),'Engine Capacity')]")
	WebElement clickEngine;

	@FindBy(xpath = "//div[@id='new_car_srp_leftbox']//li[7]//div[2]//ul[1]/li[1]")
	WebElement Engine; // first

	@FindBy(xpath = "//span[contains(text(),'Mileage')]")
	WebElement clickMileage;

	@FindBy(xpath = "//div[@id='new_car_srp_leftbox']//li[9]//div[2]//ul[1]/li[2]")
	WebElement Mileage; // 30-50

	@FindBy(xpath = "//div[@id='new_car_srp_middle']/div[@class='row']/div/ul/li/div/div[2]/div/a/span")
	List<WebElement> getBrandname;

	@FindBy(xpath = "//div[@id='new_car_srp_middle']/div[@class='row']/div/ul/li/div/div[2]/div/p")
	List<WebElement> getBudgets;

	@FindBy(xpath = "//div[@id='new_car_srp_middle']/div[@class='row']/div/ul/li/div/div[2]/div/div[5]/div/ul/li[1]/span[2]")
	List<WebElement> getEnginecapacity;

	@FindBy(xpath = "//div[@id='new_car_srp_middle']/div[@class='row']/div/ul/li/div/div[2]/div/div[5]/div/ul/li[2]/span[2]")
	List<WebElement> getMileagebike;

	public NewBikesSearchPage(WebDriver driver) throws Exception {
		this.driver = driver;
		this.prop = loadProp();
		this.data = ExcelUtils.readExcel();
	}

	public WebElement getSearchBarElement() {
		waitForElement(driver, brand);
		return brand;
	}

	public String getTitle() {
		waitForElement(driver, pageTitle);
		return pageTitle.getText();
	}

	// Locate and passing the brand name in the filter
	public void selectBrand(String brandname) {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		if(executionCount > 0)
			brand.clear();
		brand.sendKeys(data.get(brandname));
		executionCount++;
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		autoSuggest.click();

	}

	// Getting list of string of the selected brand bikes name
	public List<String> getBrand() {
		waitForElement(driver, clear);
		return getTextFromElements(driver, getBrandname);
	}

	// selecting budget in the filter
	public void selectBudget(String range) {
		String xPath = "//label[contains(text(),'" + data.get(range) + "')]";
		WebElement priceRangeFilter = driver.findElement(By.xpath(xPath));
		priceRangeFilter.click();
	}

	// Getting list of string of the bike's price
	public List<String> getBudget() {
		waitForElement(driver, clear);
		return getTextFromElements(driver, getBudgets);
	}

	// selecting Enginecapacity in the filter
	public void selectEngine() {
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		waitForElement(driver, clickEngine);
		clickEngine.click();
		Engine.click();
	}

	// Getting list of string of the bike's engine capacity
	public List<String> getEngine() {
		waitForElement(driver, clear);
		return getTextFromElements(driver, getEnginecapacity);
	}

	// selecting mileage in the filter
	public void selectMileage() {
		clickMileage.click();
		Mileage.click();
	}

	// Getting list of string of the bike's mileage
	public List<String> getMileage() {
		waitForElement(driver, clear);
		return getTextFromElements(driver, getMileagebike);
	}
}
