package com.pageobject.webdriver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pageobject.framework.EnvironmentConfiguration;

public class WebDriverConfigBean {

	private Logger log = LoggerFactory.getLogger(WebDriverConfigBean.class);

	private String deploymentEnvironment;

	private String browserType;

	private String seleniumMode;

	private String seleniumServer;

	private DeviceProfiles deviceProfile;

	public static WebDriverConfigBean aWebDriverConfig() {
		return new WebDriverConfigBean()
				.withSeleniumMode("SINGLE")
				.withSeleniumServer("local");
	}

	private WebDriverConfigBean() {
		// Use the static method, it's cleaner
	}

	public WebDriverConfigBean withBrowser(String browser) {
		this.browserType = browser;
		log.info("getting the browser " + this);
		return this;
	}

	public WebDriverConfigBean withDeploymentEnvironment(String env) {
		this.deploymentEnvironment = env;
		log.info("getting the environment " + this);
		return this;
	}

	public WebDriverConfigBean withSeleniumMode(String mode) {
		this.seleniumMode = mode;
		log.info("getting the mode " + this);
		return this;
	}

	public WebDriverConfigBean withSeleniumServer(String server) {
		this.seleniumServer = server;
		log.info("getting the selenium server " + this);
		return this;
	}

	public WebDriverConfigBean withSeleniumDeviceProfile(String profile) {
		this.deviceProfile = DeviceProfiles.valueOf(profile);
		log.info("getting the device profile " + this);
		return this;
	}

	public String getDeploymentEnvironment() {
		log.info("getting the deployment environment " + deploymentEnvironment);
		return deploymentEnvironment;
	}

	public String getBrowserType() {
		log.info("getting the browserType " + browserType);
		return browserType;
	}

	public String getSeleniumMode() {
		log.info("getting the selenium mode " + seleniumMode);
		return seleniumMode;
	}

	/**
	 * @return Not used
	 * @deprecated Nothing actually uses this
	 */
	// TODO remove this, nothing cares (unless selenium-grid does, in which case it gets reprieved...)
	@Deprecated
	public String getSeleniumServer() {
		return seleniumServer;
	}

	public String getSauceOS() {
		String sauceOS = deviceProfile.getOs();
		log.info("getting the os " + sauceOS);
		return sauceOS;
	}

	public String getSauceDeviceType() {
		String sauceDeviceType = deviceProfile.getDeviceType();
		log.info("getting the device type " + sauceDeviceType);
		return sauceDeviceType;
	}

	public String getSauceDevice() {
		log.info("getting the device details " + deviceProfile.getDeviceManufacturer());
		return deviceProfile.getDeviceManufacturer();
	}

	public String getSauceDeviceVersion() {
		String sauceDeviceVersion = deviceProfile.getOsVersion();
		log.info("getting the device version " + sauceDeviceVersion);
		return sauceDeviceVersion;
	}

	public String getSauceDeviceName() {
		log.info(deviceProfile.getDeviceModel());
		return deviceProfile.getDeviceModel();
	}

	public String getExeLocation() {
		return "/driver/chromedriver.exe";
	}
}
