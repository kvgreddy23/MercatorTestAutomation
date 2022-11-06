package com.pageobject.webdriver;

import java.net.MalformedURLException;
import java.net.URL;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;

public class DriverInitilizer {

	private static final String HUB_URL = "http://localhost:4444/wd/hub";

	public static WebDriver initialiseDriver(final WebDriverConfigBean properties)
	{
		switch (properties.getSeleniumMode().toUpperCase())
		{
			case "LOCAL":
			{
				return localBrowser(properties);
			}
			case "GRID":
			{
				return gridBrowser(properties);
			}
			case "ANDROIDDRIVER":
			{
				return androidDriver(properties);
			}
			case "IOSDRIVER":
			{
				return iosDriver(properties);
			}
			default:
			{
				throw new IllegalArgumentException("Unknown operation mode: " + properties.getSeleniumMode());
			}
		}
	}

	private static WebDriver localBrowser(final WebDriverConfigBean properties)
	{
		final WebDriver driver;
		try
		{
			switch (properties.getBrowserType().toUpperCase())
			{
				case "CHROME":
				{
					System.setProperty("webdriver.chrome.driver",
							System.getProperty("user.dir") + properties.getExeLocation());

					final ChromeOptions options = new ChromeOptions();
//					options.setCapability("acceptSslCerts", properties.isAcceptSSLCerts());
					driver = new ChromeDriver(options);

					break;
				}
				case "FIREFOX":
				{
					System.setProperty("webdriver.gecko.driver",
							System.getProperty("user.dir") + properties.getExeLocation());

					final FirefoxOptions options = new FirefoxOptions();
					options.setCapability("marionette", false);
//					options.setCapability("acceptSslCerts", properties.isAcceptSSLCerts());
					driver = new FirefoxDriver(options);

					break;
				}
				case "EDGE":
				{
				System.setProperty("webdriver.edge.driver",
						System.getProperty("user.dir") + properties.getExeLocation());

					final EdgeOptions options = new EdgeOptions();
				//	options.setBinary("C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\chromedriver.exe");
//					options.setCapability("acceptSslCerts", properties.isAcceptSSLCerts());
					EdgeDriver edgeDriver = new EdgeDriver(options);
                     driver = edgeDriver;

					break;
				}
				case "IE":
				{
					System.setProperty("webdriver.ie.driver",
							System.getProperty("user.dir") + properties.getExeLocation());

					final InternetExplorerOptions options = new InternetExplorerOptions();
					driver = new InternetExplorerDriver(options);

					break;
				}
//				case "HTMLUNITDRIVER":
//				{
//				//	driver = new HtmlUnitDriver();
//					break;
//				}
				default:
				{
					throw new IllegalArgumentException("Unsupported browser type: " + properties.getBrowserType() +
							"(expected 'Firefox', 'Chrome', or 'IE')");
				}
			}

			driver.manage()
					.window()
					.maximize();

			return driver;
		}
		catch (final Exception ex)
		{
//			log.error("Failed to open browser " + properties.getBrowserType(), ex);
			throw new IllegalStateException(ex);
		}
	}

	private static WebDriver gridBrowser(final WebDriverConfigBean properties)
	{
		try
		{
			final DesiredCapabilities caps = GridDriverCapabilities.getGridCaps(properties);
			final WebDriver driver = new RemoteWebDriver(new URL(HUB_URL), caps);

			driver.manage()
					.window()
					.maximize();

			return driver;
		}
		catch (final MalformedURLException e)
		{
			throw new IllegalStateException("Malformed Selenium hub URL: " + HUB_URL, e);
		}
	}

	private static WebDriver androidDriver(final WebDriverConfigBean properties)
	{
		Assert.fail("Android driver not yet implemented.");
		return null;
	}

	private static WebDriver iosDriver(final WebDriverConfigBean properties)
	{
		Assert.fail("iOS driver not yet implemented.");
		return null;
	}

}
