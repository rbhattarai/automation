package com.selenium.framework.factory;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.common.utility.Log;

public class BrowserFactory {

	private static Map<String, WebDriver> drivers = new HashMap<String, WebDriver>();
	private static final String BROWSER_DRIVER = "F:\\GITHUB\\setups\\BROWSER_DRIVERS\\";

	/**
	 * Factory method for getting browsers
	 */
	public static WebDriver getBrowser(BrowserName browserName)
	{
		WebDriver driver = null;
		DOMConfigurator.configure("log4j.xml");
		
		switch (browserName)
		{
		case CHROME:
			Log.info("Open Chrome browser");
			driver = drivers.get("Chrome");
			if (driver == null) {
				System.setProperty("webdriver.chrome.driver", BROWSER_DRIVER + "chromedriver.exe");
				driver = new ChromeDriver();
				drivers.put("Chrome", driver);
			}
			break;
			
		case FIREFOX:
			Log.info("Open Firefox browser");
			driver = drivers.get("Firefox");
			if (driver == null) {
				System.setProperty("webdriver.gecko.driver", BROWSER_DRIVER + "geckodriver.exe");
				FirefoxOptions ffOptions = new FirefoxOptions();
				ffOptions.setCapability("marionette", true);
				driver = new FirefoxDriver(ffOptions);
				drivers.put("Firefox", driver);
			}
			break;
			
		case IE:
			Log.info("Open IE browser");
			driver = drivers.get("IE");
			if (driver == null) {
				System.setProperty("webdriver.ie.driver", BROWSER_DRIVER + "IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				drivers.put("IE", driver);
			}
			break;
		}	
		return driver;
	}
	
	public static void closeAllDrivers()
	{
		Log.info("Close all browsers");
		for (String key : drivers.keySet())
		{
			drivers.get(key).close();
			drivers.get(key).quit();
		}
	}
}
