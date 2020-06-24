package pages;

import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import Utility.ExcelUtils;

public class NewBikesPage extends Utility.BaseClass {
	public WebDriver driver;
	Properties prop;
	Map<String,String> data;

	@FindBy(xpath = "//form[@id='byBrandSearchFrom']//div//button[contains(text(),'Search')]")
	WebElement searchBtn;

	@FindBy(id = "minPrice")
	WebElement minPrice;

	@FindBy(id = "maxPrice")
	WebElement maxPrice;

	@FindBy(xpath = "//span[contains(text(),'Engine Capacity')]")
	WebElement clickEngine;

	@FindBy(xpath = "//div[@id='new_car_srp_leftbox']//li[7]//div[2]//ul[1]/li[1]")
	WebElement Engine; // first

	@FindBy(xpath = "//span[contains(text(),'Mileage')]")
	WebElement clickMileage;

	@FindBy(xpath = "//div[@id='new_car_srp_leftbox']//li[9]//div[2]//ul[1]/li[2]")
	WebElement Mileage; // 30-50

	@FindBy(xpath = "//div[@id='srp_main_div']//div//div//h1")
	WebElement heading;

	@FindBy(xpath = "//input[@id='bypricerange100000-200000']")
	WebElement budgetFilter;
	
	@FindBy(xpath = "//h1[@class='mb-10 mt-10 mob-pl-15 clr-bl']")
	//h1[contains(@class,'mt-5 pt-15 n zm-cmn-colorBlack')]
	WebElement pageTitle;	

	public NewBikesPage(WebDriver driver) throws Exception {
		this.driver = driver;
		this.prop = loadProp();
		this.data = ExcelUtils.readExcel();
	}

	public String getTitle() {
		waitForElement(driver, pageTitle);
		return pageTitle.getText();
	}
	
	// Clicking search button
	public WebElement getSearchElement() {
		waitForElement(driver, searchBtn);
		return searchBtn;
	}
	
	// Clicking search button
	public void clickSearch() {
		waitForElement(driver, searchBtn);
		searchBtn.click();
	}

	
}
