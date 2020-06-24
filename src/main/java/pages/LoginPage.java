package pages;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import Utility.ExcelUtils;

public class LoginPage extends Utility.BaseClass {

	public WebDriver driver;
	Properties prop;
	Map<String,String> data;

	@FindBy(id = "identifierId")
	WebElement emailInput;

	@FindBy(name = "password")
	WebElement sendPwd;

	@FindBy(xpath = "//span[contains(text(),'Next')]")
	WebElement nextBtn;

	@FindBy(xpath = "//div[contains(text(),'Enter an email or phone number')]")
	WebElement errorEmail;

	@FindBy(xpath = "//span[contains(text(),'Enter a password')]")
	WebElement errorPwd;

	@FindBy(xpath = "//span[contains(text(),'Wrong password. Try again or click Forgot password')]")
	WebElement errorLogin;

	@FindBy(xpath = "//span[@id='forum_login_welcome_lg']")
	WebElement loginMsg;

	@FindBy(id = "feedClose")
	WebElement exit;
	
	@FindBy(xpath="//div[@class='o6cuMc']")
	WebElement errorincrtEmail;

	public LoginPage(WebDriver driver) throws Exception {
		this.driver = driver;
		this.prop = loadProp();
		this.data =  ExcelUtils.readExcel();
	}

	// locate and Switch to the child tabs
	public void switchWindow() {
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		SwitchToNewWindow(driver);
	}

	// locate and pass emailid to the element
	public void setEmail(String emailid) {
		if(executionCount > 0)
			emailInput.clear();
		emailInput.sendKeys(data.get(emailid));
		executionCount++;
	}

	// Clicking next button
	public void clickNext() {
		waitForElement(driver, nextBtn);
		nextBtn.click();
	}

	// Locate and return the error msg
	public String errorEmail() {
		return errorEmail.getText();
	}

	// Locate and pass the password to the element
	public void setPwd(String pass) {
		if(executionCount > 0)
			emailInput.clear();
		sendPwd.sendKeys(data.get(pass));
		executionCount++;
	}

	// Locate and return the error msg
	public String errorPwd() {
		return errorPwd.getText();
	}

	// Locate and return the error msg
	public String errorlogin() {
		return errorLogin.getText();
	}

	// Click exit
	public void exitIcon() {
		waitForElement(driver, exit);
		exit.click();
	}

	// locate and return welcome msg
	public String doLogin() {
		return loginMsg.getText();
	}
	
	// Locate and return the error msg
	public String errorincrtEmail() {
		return errorincrtEmail.getText();
	}
}
