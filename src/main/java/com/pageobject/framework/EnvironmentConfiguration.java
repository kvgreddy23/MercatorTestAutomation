package com.pageobject.framework;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import org.slf4j.LoggerFactory;

public class EnvironmentConfiguration

{
	private static Properties MY_CONFIG = null;

	public static void populate(String environmentName) {
		org.slf4j.Logger log = LoggerFactory.getLogger(EnvironmentConfiguration.class);
		log.info("Loading environment properties for profile " + environmentName);
		String propsResourceName = "/" + environmentName + "_config.properties";
		log.info("Loaded environment properties for profile " + environmentName);
		try (InputStream input = EnvironmentConfiguration.class.getResourceAsStream(propsResourceName)) {
			MY_CONFIG = new Properties();
			MY_CONFIG.load(input);
		} catch (IOException e) {
			log.error("Could not load environment properties - this is going to break...", e);
		}
		Map<String, String> systemEnvironment = System.getenv();
		log.info("Environment variables:");
//		//systemEnvironment.keySet().forEach(log::info);
//		MY_CONFIG.setProperty("ad_userName", systemEnvironment.get("ad_username"));
//		//MY_CONFIG.setProperty("db_url", systemEnvironment.get("db_url"));
//		MY_CONFIG.setProperty("ad_password", systemEnvironment.get("ad_userpassword"));

	}

    public String getConfigProperties(String Property) {
        String newProperty = MY_CONFIG.getProperty(Property);
        return newProperty;
    }

	public static String getBaseURL() {
		return MY_CONFIG.getProperty("website");
	}
}
