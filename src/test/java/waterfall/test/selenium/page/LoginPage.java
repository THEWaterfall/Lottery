package waterfall.test.selenium.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

	@SuppressWarnings("unused")
	private WebDriver webDriver;
	
	public LoginPage(WebDriver webDriver) {
		PageFactory.initElements(webDriver, this);
		this.webDriver = webDriver;
	}
	
	@FindBy(name="username")
	private WebElement usernameField;
	
	@FindBy(name="password")
	private WebElement passwordField;
	
	@FindBy(name="loginBtn")
	private WebElement loginButton;
	
	public void inputUsername(String username) {
		usernameField.sendKeys(username);
	}
	
	public void inputPassword(String password) {
		passwordField.sendKeys(password);
	}
	
	public void clickLoginButton() {
		loginButton.click();
	}
	
}
