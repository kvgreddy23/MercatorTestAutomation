package com.pageobject.webdriver;

public enum DeviceProfiles {

	ANDROID_EMULATOR_5_1("mobile", "Android", "android", "Android Emulator", "5.1"),
	SAMSUNG_GALAXY_NEXUS_EMULATOR_4_4("mobile", "Android", "android", "Samsung Galaxy Nexus Emulator", "4.4"),
	GOOGLE_NEXUS_7_EMULATOR_4_4("mobile", "Android", "android", "Google Nexus 7 HD Emulator", "4.4"),
	ANDROID_EMULATOR_5_0("mobile", "Android", "android", "Android Emulator", "5.0"),
	IPHONE_6SPLUS("mobile", "iOS", "iphone", "6 Plus Simulator", "9.3"),
	IPHONE_5S_9_1("mobile", "iOS", "iphone", "5s Simulator", "9.1"),
	IPHONE_5S_9_0("mobile", "iOS", "iphone", "5s Simulator", "9.0"),
	IPHONE_4S_8_3("mobile", "iOS", "iphone", "4s Simulator", "8.3"),
	IPHONE_4S_8_4("mobile", "iOS", "iphone", "4s Simulator", "8.4"),
	IPHONE_4S_9_1("mobile", "iOS", "iphone", "4s Simulator", "9.1"),
	IPHONE_4S_8_2("mobile", "iOS", "iphone", "4s Simulator", "9.2"),

	//Desktop grid profiles

	GRID_MAC("desktop", "MAC", "Mac", "Mac", "9.0"),
	GRID_LINUX_FIREFOX("desktop", "LINUX", "Linux", "Linux", " "),
	GRID_WINDOWS_IE("desktop", "WINDOWS", "Windows", "Windows", " "),
	WINDOWS_7_CHROME("desktop", "WINDOWS", "Windows", "Windows", " "),
	GRID_ANDROID_DEVICE_c0808ba002ac4bf("mobile", "Android", "c0808ba002ac4bf", "Android Emulator", "5.0"),
	GRID_NEXUS_5_038ca452828e05b7("mobile", "Android", "038ca452828e05b7", "Nexus 5", "6.0.1"),

	//Desktop sauce profiles
	MAC_OSX_10_11("desktop", "OS X 10.11", "Mac_Capitan", "Mac_OS", "9.0"),
	LINUX_FIREFOX_45("desktop", "Linux", "Dell", "Linux_Redhat", "45.0"),
	LINUX_CHROME_45("desktop", "Linux", "Dell", "Linux_Redhat", "48.0"),
	LINUX_OPERA_12("desktop", "Linux", "Dell", "Linux_Redhat", "12.15"),
	WINDOWS_7_IE10("desktop", "Windows 7", "Lenovo", "Think_Pad", "10.0"),
	WINDOWS_10_CHROME("desktop", "Windows 10", "Lenovo", "Think_Pad", "49.0");

	private String device;

	private String os;

	private String deviceManufacturer;

	private String deviceModel;

	private String osVerison;

	private DeviceProfiles(String deviceType, String os, String deviceName, String model, String version) {
		this.device = deviceType;
		this.os = os;
		this.deviceManufacturer = deviceName;
		this.deviceModel = model;
		this.osVerison = version;
	}

	public String getOs() {
		return os;
	}

	public String getDeviceManufacturer() {
		return deviceManufacturer;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public String getDeviceType() {
		return device;
	}

	public String getOsVersion() {
		return osVerison;
	}
}
