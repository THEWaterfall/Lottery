package waterfall.test.selenium.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HeaderPage {

	@SuppressWarnings("unused")
	private WebDriver webDriver;
	
	public HeaderPage(WebDriver webDriver) {
		PageFactory.initElements(webDriver, this);
		this.webDriver = webDriver;
	}
	
	@FindBy(id="username")
	private WebElement username;
	
	@FindBy(linkText="Admin panel")
	private WebElement adminPanelItem;
	
	@FindBy(linkText="Playground")
	private WebElement playgroundItem;
	
	@FindBy(linkText="Ladder")
	private WebElement ladderItem;
	
	@FindBy(linkText="Logout")
	private WebElement logoutButton;
	
	public String getUsername() {
		return username.getText();
	}
	
	public void clickAdminPanelItem() {
		adminPanelItem.click();
	}
	
	public void clickPlaygroundItem() {
		playgroundItem.click();
	}
	
	public void clickLadderItem() {
		ladderItem.click();
	}
	
	public void clickLogoutButton() {
		logoutButton.click();
	}
}
