package com.pageobject.webdriver;


import org.openqa.selenium.Platform;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

public class GridDriverCapabilities {

	private static DesiredCapabilities desktopCapbility;

	private static final Logger log = LoggerFactory.getLogger(GridDriverCapabilities.class);

	private static final Platform platform = null;

	public static DesiredCapabilities getGridCaps(final WebDriverConfigBean properties)
	{
		switch (properties.getSauceDeviceType())
		{
			case "desktop":
				return getRemoteDesktopCaps(properties.getSauceOS(),
						properties.getBrowserType());

			case "mobile":
				return getRemoteMobileCaps(properties);

			default:
				throw new IllegalArgumentException("Unknown device type " + properties.getSauceDevice() +
						" (expected 'desktop' or 'mobile')");
		}
	}

	private static DesiredCapabilities getRemoteDesktopCaps(final String platform, final String browserName)
	{
		switch (browserName.toUpperCase())
		{
			case "CHROME":
			{
				desktopCapbility = new DesiredCapabilities();
				desktopCapbility.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				desktopCapbility.setPlatform(getPlatform(platform));
				desktopCapbility.setCapability(CapabilityType.TAKES_SCREENSHOT, false);
				return desktopCapbility;
			}
			case "FIREFOX":
			{
				desktopCapbility = new DesiredCapabilities();
				desktopCapbility.setCapability("marionette", true);
				desktopCapbility.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				desktopCapbility.setPlatform(getPlatform(platform));
				desktopCapbility.setCapability(CapabilityType.TAKES_SCREENSHOT, false);
				return desktopCapbility;
			}
			case "IE":
			{
				desktopCapbility = new DesiredCapabilities();
				desktopCapbility.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);
				desktopCapbility.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				desktopCapbility.setCapability(CapabilityType.TAKES_SCREENSHOT, false);
				desktopCapbility.setPlatform(getPlatform(platform));
				return desktopCapbility;
			}
			case "SAFARI":
			{
				desktopCapbility = new DesiredCapabilities();
				desktopCapbility.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				desktopCapbility.setPlatform(getPlatform(platform));
				desktopCapbility.setCapability(CapabilityType.TAKES_SCREENSHOT, false);
				return desktopCapbility;
			}
			default:
			{
				log.debug("Not able to find any capabilities");
				return null;
			}
		}
	}

	private static Platform getPlatform(final String platform)
	{

		switch (platform.toUpperCase())
		{
			case "WINDOWS":
			{
				return Platform.WINDOWS;
			}
			case "WINDOWS8":
			{
				return Platform.WIN8;
			}
			case "WINDOWS8_1":
			{
				return Platform.WIN8_1;
			}
			case "WINDOWS10":
			{
				return Platform.WIN10;
			}
			case "LINUX":
			{
				return Platform.LINUX;
			}
			case "MAC":
			{
				return Platform.MAC;
			}
			default:
			{
				throw new IllegalArgumentException("Unsupported Platform type: " + platform +
						"(expected 'Windows', 'Linux', or 'Mac')");
			}
		}
	}

	private static DesiredCapabilities getRemoteMobileCaps(final WebDriverConfigBean properties)
	{
		// TODO: Implement me.
		Assert.fail("Mobile is not yet implemented.");
		return null;
	}

}
