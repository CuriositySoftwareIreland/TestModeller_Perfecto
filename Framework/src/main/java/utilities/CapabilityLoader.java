package utilities;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class CapabilityLoader
{
	private String securityToken = ""; // Insert your value for the perfecto security token
	private String perfectoURL = ""; // Insert your perfecto URL
	
    public static WebDriver createWebDriver() {
		DesiredCapabilities capabilities = new DesiredCapabilities("", "", Platform.ANY);
		capabilities.setCapability("platformName", "Windows");
		capabilities.setCapability("platformVersion", "10");
		capabilities.setCapability("browserName", "Chrome");
		capabilities.setCapability("browserVersion", "beta");
		capabilities.setCapability("location", "AP Sydney");
		capabilities.setCapability("securityToken", securityToken);
		capabilities.setCapability("resolution", "1024x768");

		WebDriver driver = null;
		try {
			driver = new RemoteWebDriver(new URL(perfectoURL), capabilities);
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return driver;
    }
}
