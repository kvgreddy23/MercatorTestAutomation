package com.pageobject.framework;

import java.awt.*;
import static com.pageobject.framework.TestLogger.*;
import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.openqa.selenium.WebDriver;



public class Utils 
{
	
	/**
	 * Utils.java
	 * @param timeMillis
	 */
	public static void pause(long timeMillis)
	{
		try 
		{
			Thread.sleep(timeMillis);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Capture the screen of the current graphics device
	 * @param fileName : input an image name (String)
	 * @throws InterruptedException
	 */
	
	public static void captureScreen(String fileName)
	{
		String path;
		BufferedImage screenCaptuer;
		
		pause(3000);
		try
		{
			Robot robot = new Robot();
			Rectangle screenSize = getScreenSize();
			screenCaptuer = robot.createScreenCapture(screenSize);
			//Save as PNG
			String curDir = System.getProperty("user.dir");
			path = curDir + "/target/screenshoot";
			File f = new File(path);
			if (!f.exists()) f.mkdir();
			ImageIO.write(screenCaptuer, "png", new File(path + fileName));			
		}
		catch (AWTException e)
		{
			error("Failed to capture screenshot");
		}
		catch(IOException e)
		{
			path = "Failed to captuer screenshpt" + e.getMessage(); 
		}
	}
	
	
	/**
	 * 
	 * @return the size of the default screen
	 */
	
	public static Rectangle getScreenSize()
	{
		GraphicsEnvironment graphE = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice graphD = graphE.getDefaultScreenDevice();
		Window displayM = graphD.getFullScreenWindow();
		if(displayM != null)
			return new Rectangle(displayM.getWidth(), displayM.getHeight());
		else
			return new Rectangle(1000, 1000);
	}
	
	
	/**
	 * Simulating keyboard presses
	 * @param firstKey: Send the first key (Type: KeyEvent)
	 * @param secondKey: Send the second key (Type: KeyEvent)
	 * @throw InterruptedException
	 * 
	 */
	
	public static void javaSimulateKeyPress(int firstKey, Object... params)
	{
		int secondKey = (Integer) (params.length > 0 ? params[0]: KeyEvent.VK_ENTER);
		try
		{
			Robot robot = new Robot();
			
			//Simulate a key press
			
			robot.keyPress(firstKey);
			robot.keyPress(secondKey);
			pause(3000);
			robot.keyRelease(secondKey);
			robot.keyRelease(firstKey);
		}
		catch(AWTException e)
		{
			e.printStackTrace();
		}
	}
	
	
	//The method gets the absolute path from the relative path
	
	public static String getAbsoluteFilePath(String relativeFilePath)
	{
		String curDir = System.getProperty("user.dir");
		String absolutePath = curDir + "/src/main/resources/" + relativeFilePath;
		return absolutePath;
	}
	
	public static String getFileContent(String filePath)
	{
		String path = getAbsoluteFilePath(filePath);
		FileInputStream fi = null;
		try
		{
			fi = new FileInputStream(path);
			
		}
		catch(FileNotFoundException e)
		{
			error("Failed to find location of.... " + filePath);
		}
		String inputStreamString = new Scanner(fi, "UTF-8").useDelimiter("\\A").next();
		return inputStreamString;
	}
	
	public static String getFileNameFromCurrentUrl(WebDriver driver, Object... params)
	{
		Boolean extension = (Boolean) (params.length > 0 ? params[0] : false);
		
		String currentUrl = driver.getCurrentUrl();
		File file = new File(currentUrl);
		String fileNameWithExtension = file.getName();
		
		if(extension)
		{
			int position = fileNameWithExtension.lastIndexOf(".");
			String fileNameWtihOutExtension = null;
			if(position >= 0 )
			{
				fileNameWtihOutExtension = fileNameWithExtension.substring(0, position);
			}
			else
			{
				fileNameWtihOutExtension = fileNameWithExtension;
			}
			return fileNameWtihOutExtension;
		}
		else
		{
			return fileNameWithExtension;
		}
	}
	
	public static String getIPOfLocal()
	{
		info("Get IP of localhost");
		String interName = "";
		Map <String, String> inter = getInterfaces();
		for (String key: inter.keySet())
		{
			if(key.contains("eth"))
			{
				interName = inter.get(key);
				break;
			}
		}
		info(interName);
		return interName;
	}
	
	public static Map<String, String>getInterfaces()
	{
		Map<String, String> inter = new HashMap <String, String>();
		String IP = ""; 
		try
		{
			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
			
			while(e.hasMoreElements())
			{
				NetworkInterface ni = (NetworkInterface) e.nextElement();
				info("Net interface :" + ni.getName());
				
				Enumeration<InetAddress> e2 = ni.getInetAddresses();
				while (e2.hasMoreElements())
				{
				InetAddress ip = (InetAddress)e2.nextElement();
					if(!ip.isLinkLocalAddress())
					{
						IP = ip.getHostAddress();
					}
				}
			info("IP address: "+ IP.toString());
			inter.put(ni.getName(), IP.toString());
			}
		}
	catch (Exception e)
	{
		e.printStackTrace();
	}
	return inter;
}

}
