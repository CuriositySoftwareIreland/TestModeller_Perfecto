package utilities;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;


import io.appium.java_client.AppiumDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class CapabilityLoader
{

	private static final ThreadLocal<WebDriver> threadLocal = new ThreadLocal<WebDriver>();

	public static WebDriver getDriver() {
		return threadLocal.get();
	}

	public static void setDriver(WebDriver driver) {
		threadLocal.set(driver);
	}

	public void createWebDriver(ITestContext test) throws Exception {

		//Replace <<cloud name>> with your perfecto cloud name (e.g. demo) or pass it as maven properties: -DcloudName=<<cloud name>>  
		String cloudName = "<<cloud name>>";
		//Replace <<security token>> with your perfecto security token or pass it as maven properties: -DsecurityToken=<<SECURITY TOKEN>>  More info: https://developers.perfectomobile.com/display/PD/Generate+security+tokens
		String securityToken = "<<security token>>";
		DesiredCapabilities capabilities = new DesiredCapabilities("", "", Platform.ANY);
		capabilities.setCapability("platformName", test.getCurrentXmlTest().getParameter("platformName"));
		capabilities.setCapability("platformVersion", test.getCurrentXmlTest().getParameter("platformVersion"));
		capabilities.setCapability("browserName", test.getCurrentXmlTest().getParameter("browserName"));
		capabilities.setCapability("browserVersion", test.getCurrentXmlTest().getParameter("browserVersion"));
		capabilities.setCapability("location", test.getCurrentXmlTest().getParameter("location"));
		capabilities.setCapability("resolution", test.getCurrentXmlTest().getParameter("resolution"));
		capabilities.setCapability("model", test.getCurrentXmlTest().getParameter("model"));
		capabilities.setCapability("securityToken", Perfectoutils.fetchSecurityToken(securityToken));
		capabilities.setCapability("useAppiumForWeb",  Boolean.parseBoolean(test.getCurrentXmlTest().getParameter("useAppiumForWeb")));
		capabilities.setCapability("enableAppiumBehavior", Boolean.parseBoolean(test.getCurrentXmlTest().getParameter("enableAppiumBehavior")));
		capabilities.setCapability("takesScreenshot", Boolean.parseBoolean(test.getCurrentXmlTest().getParameter("takesScreenshot")));
		capabilities.setCapability("screenshotOnError", Boolean.parseBoolean(test.getCurrentXmlTest().getParameter("screenshotOnError")));

		WebDriver driver = null;
		try {
			if(test.getCurrentXmlTest().getParameter("platformName").equalsIgnoreCase("ios")||test.getCurrentXmlTest().getParameter("platformName").equalsIgnoreCase("android")) {
				driver = (RemoteWebDriver)new AppiumDriver(new URL("https://"+Perfectoutils.fetchCloudName(cloudName)+".perfectomobile.com/nexperience/perfectomobile/wd/hub/fast"), capabilities);
			}
			else {
				driver = new RemoteWebDriver(new URL("https://"+Perfectoutils.fetchCloudName(cloudName)+".perfectomobile.com/nexperience/perfectomobile/wd/hub/fast"), capabilities);
			}
			setDriver(driver);
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}
}
