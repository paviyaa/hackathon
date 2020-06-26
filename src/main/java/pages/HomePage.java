package pages;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import Utility.ExcelUtils;

public class HomePage extends Utility.BaseClass {

	public WebDriver driver;
	Map<String, String> data;

	@FindBy(id = "headerSearch")
	WebElement searchBar;

	@FindBy(xpath = "//body/ul/li/a[1]/b/span[contains( text(),'Honda')]")
	WebElement search;

	@FindBy(xpath = "//div[@id='des_lIcon']")
	WebElement login;

	@FindBy(xpath = "//span[contains(text(),'Continue with Google')]")
	WebElement googleLogin;

	@FindBy(xpath = "//span[@class='hd fnt-20 fnt-black fnt-m rel i-b ml-10 lh-24 txt-l login-title headingText default']")
	WebElement popupTitle;

	@FindBy(xpath = "//ul[@class='h-d-nav fnt-16']//a[contains(text(),'New Bikes')]")
	WebElement newBikesdrp;

	@FindBy(xpath = "//li[3]//ul[1]//li[1]//div[2]//ul[1]//li[2]//a[1]")
	WebElement brand;

	@FindBy(xpath = "//a[contains(text(),'Search Bikes')]")
	WebElement searchBikes;

	@FindBy(xpath = "//a[contains(text(),'Upcoming Bikes')]")
	WebElement upcomingBikes;

	@FindBy(linkText = "Used Cars")
	WebElement usedCardrp;

	@FindBy(xpath = "//a[contains(text(),'Search Used Cars')]")
	WebElement usedCars;

	@FindBy(xpath = "//a[contains(text(),'Used Cars in Chennai')]")
	WebElement usedCarsChn;

	@FindBy(css = "div.container.m-w-p0:nth-child(18) section:nth-child(5) div.zwn-by_brand.zw-con.pt-0 div.text-center.pl-5.pr-5 > a.zw-cmn-blueColor:nth-child(3)")
	WebElement upcomingLink;

	@FindBy(xpath = "//form[@id='search-Sml']//span[@id='suggest_crossicon_headerSearch']")
	WebElement clearSearch;

	public HomePage(WebDriver driver) throws Exception {
		this.driver = driver;
		this.data = ExcelUtils.readExcel();
	}

	// Dropping the NavPanel and returning Brand element
	public WebElement getBrandElement() {
		doAction(driver, newBikesdrp);
		return brand;
	}

	public void clickBrand() {
		clickElement(brand);
	}

	// Dropping the NavPanel and returning Upcoming Bike element
	public WebElement getUpcomingBikesElement() {
		doAction(driver, newBikesdrp);
		return upcomingBikes;
	}

	public void clickUpcomingBikesElement() {
		clickElement(upcomingBikes);
	}

	// locate and pass the text in search bar
	public void setSearch(String Text) {
		if (!getEnteredValue(searchBar).isEmpty())
			searchBar.clear();
		searchBar.sendKeys(data.get(Text));
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		search.click();

	}

	// Click login and proceed with continue with google
	public void clickLogin() {
		login.click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	// Click login and proceed with continue with google
	public void clickLoginCopy() {
		login.click();
		waitForElement(driver, googleLogin);
	}

	public String getSigninPopupTitle() {
		waitForElement(driver, popupTitle);
		return popupTitle.getText();
	}

	public void clickGoogleLogin() {
		clickElement(googleLogin);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	// clicking the upcoming bikes link
	public void clickLinkhome() {
		waitForElement(driver, upcomingLink);
		upcomingLink.click();
	}

	// clicking searchusedcars in the dropdown
	public WebElement getUsedCarsElement() {
		doAction(driver, usedCardrp);
		return usedCars;
	}

	// Clicking searchbikes in dropdown
	public void clickLinksearchbikesdrpdwn() {
		doAction(driver, newBikesdrp);
		searchBikes.click();
	}

	public WebElement getSearchBikesElement() {
		doAction(driver, newBikesdrp);
		return searchBikes;
	}

	// Clicking usedCars in chennai in dropdown
	public WebElement getUsedCarsChn() {
		doAction(driver, usedCardrp);
		return usedCarsChn;
	}

	// locate and return parent Window GUI code
	public String getParent() {
		return driver.getWindowHandle();
	}

	// Switching to parent tab
	public void setParent(String home) {
		driver.switchTo().window(home);
	}

	// Return search bar webelement
	public WebElement getSearchBarElement() {
		return searchBar;
	}

	public WebElement getSearchUsedCar() {
		doAction(driver, usedCardrp);
		return usedCars;
	}

	public WebElement getSearchUsedCarChn() {
		doAction(driver, usedCardrp);
		return usedCarsChn;
	}

}
