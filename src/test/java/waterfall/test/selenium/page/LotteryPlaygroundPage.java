package waterfall.test.selenium.page;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LotteryPlaygroundPage {

	@SuppressWarnings("unused")
	private WebDriver webDriver;
	
	public LotteryPlaygroundPage(WebDriver webDriver) {
		PageFactory.initElements(webDriver, this);
		this.webDriver = webDriver;
	}
	
	@FindBy(linkText="Get ticket")
	private WebElement getTicketButton;
	
	@FindBy(className="get-more")
	private WebElement getMoreButton;
	
	@FindBy(name="amount")
	private WebElement getMoreField;
	
	@FindBy(linkText="Play")
	private WebElement playButton;
	
	@FindBy(className="ticket")
	private List<WebElement> tickets;
	
	public void clickGetTicketButton() {
		getTicketButton.click();
	}
	
	public void clickGetMoreButton() {
		getMoreButton.click();
	}
	
	public void clickPlayButton() {
		playButton.click();
	}
	
	public void inputGetMoreField(String amount) {
		getMoreField.sendKeys(amount);
	}
	
	public int getTicketListSize() {
		return tickets.size()
	}
}
