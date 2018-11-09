package waterfall.test.selenium.test;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import waterfall.test.selenium.page.HeaderPage;
import waterfall.test.selenium.page.LoginPage;
import waterfall.test.selenium.page.LotteryPlaygroundPage;

public class LoginTest {
	
	public static WebDriver webDriver;
	public static LoginPage loginPage;
	public static LotteryPlaygroundPage lotteryPlaygroundPage;
	public static HeaderPage headerPage;
	
	@BeforeClass
	public static void init() {
		System.setProperty("webdriver.chrome.driver", "/usr/local/share/chromedriver.exe");
		webDriver = new ChromeDriver();
		loginPage = new LoginPage(webDriver);
		lotteryPlaygroundPage = new LotteryPlaygroundPage(webDriver);
		headerPage = new HeaderPage(webDriver);
		
		webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		webDriver.get("http://localhost:8080/lottery/login");
	}
	
	@AfterClass
	public static void fini() {
		headerPage.clickLogoutButton();
		webDriver.quit();
	}
	
	@Test
	public void loginTest() {
		loginPage.inputUsername("root");
		loginPage.inputPassword("root");
		loginPage.clickLoginButton();
		
		assertEquals("root", headerPage.getUsername());
	}
	
}
