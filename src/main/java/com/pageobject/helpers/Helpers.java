package com.pageobject.helpers;

import java.util.List;
import java.util.Set;



import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.pageobject.framework.Utils;

import static com.pageobject.framework.TestLogger.*;

public class Helpers 
{
	public WebDriver driver;
	public WebDriver newDriver;
	protected String baseUrl;
	protected int DEFAULT_TIMEOUT = 30000; //milliseconds = 30 seconds
	protected int WAIT_INTERVAL = 1000; //milliseconds
	public int loopCount = 0;
	public  final int ACTION_REPEAT = 5;
	protected boolean ieFlag;
	protected boolean chromeFlag;
	public Actions action;
	
	public WebElement getElement(Object locator, Object... opParams)
	{
		By by = locator instanceof By ? (By)locator : By.xpath(locator.toString());
		WebDriver wDriver = (WebDriver) (opParams.length > 0 ? opParams[0]: driver);
		WebElement elem = null;
		try
		{
			elem = wDriver.findElement(by);
		}
		catch (NoSuchElementException e)
		{
			
		}
		return elem;
	}
	
	//return element when the element is displayed
	
	public WebElement getDisplayedElement(Object locator, Object... opParams)
	{
		By by = locator instanceof By ? (By)locator : By.xpath(locator.toString());
		WebDriver wDriver = (WebDriver) (opParams.length > 0 ? opParams[0]: driver);
		WebElement e = null;
		try
		{
			if(by != null)
				e = wDriver.findElement(by);
			if(e !=null)
			{
				if(isDisplay(by)) return e;
			}
		}
		catch(NoSuchElementException ex)
		{
			
		}
		catch(StaleElementReferenceException ex)
		{
			checkCycling(ex, 10);
			Utils.pause(WAIT_INTERVAL);
			getDisplayedElement(locator);
		}
		finally
		{
			loopCount = 0;
		}
		return null;
	}
	
	public boolean isElementPresent(Object locator)
	{
		return getElement(locator) != null;
		
	}
	public boolean isElementNotPresent(Object locator)
	{
		return !isElementPresent(locator);
	}
	
	
	/**
	 * 
	 * @param locator
	 * @param opParams
	 * @return
	 */
	
	public WebElement waitForAndGetElement(Object locator, Object... opParams)
	{
		WebElement elem = null;
		int timeout = (Integer) (opParams.length > 0 ? opParams[0] : DEFAULT_TIMEOUT);
		int isAssert = (Integer) (opParams.length > 1 ? opParams[1]: 1);
		int notDisplayE = (Integer) (opParams.length > 2 ? opParams[2] : 0);
		WebDriver wDriver = (WebDriver) (opParams.length > 3 ? opParams[3]: driver);
		for (int tick = 0; tick < timeout/WAIT_INTERVAL; tick++ )
		{
			if (notDisplayE == 2)
			{
				elem = getElement(locator, wDriver);
						
			}
			else
			{
				elem = getDisplayedElement(locator, wDriver);
			}
			if (null != elem) return elem;
			Utils.pause(WAIT_INTERVAL);
		}
		if (isAssert == 1)
			assert false: ("Timeout after " + timeout + " ms waiting for element present " + locator);
		info("cannot find element after " + timeout/1000 + "s.");
		return null;
		
	}
	
	/**
	 * 
	 * @param locator
	 * @param opParams
	 * @return
	 */
	
	public WebElement waitForElementNotPresent(Object locator, int... opParams)
	{
		WebElement elem = null;
		int timeout = (Integer) (opParams.length > 0 ? opParams[0] : DEFAULT_TIMEOUT);
		int isAssert = (Integer) (opParams.length > 1 ? opParams[1]: 1);
		int notDisplayE = (Integer) (opParams.length > 2 ? opParams[2] : 0);
		
		
		for (int tick = 0; tick < timeout/WAIT_INTERVAL; tick++)
		{
			if (notDisplayE == 2)
			{
				elem = getElement(locator);
			}
			else
			{
				elem = getDisplayedElement(locator);
			}
			if (null == elem) return null;
			Utils.pause(WAIT_INTERVAL);
		}
		if (isAssert == 1)
			assert false: ("Timeout after " + timeout + "ms waiting for element not present: " +locator);
		info("Element doesn't dissaoear after" + timeout/1000 + "s.");
		return elem;
	}
	
	public boolean isTextPresent(String text, int...opts)
	{
		int display = opts.length > 0 ? opts[0] : 1;
		Utils.pause(500);
		String allVisibleTexts = getText(By.xpath("//body"), display);
		return allVisibleTexts.contains(text);
	}
	
	public String getText(Object locator, int... opts)
	{
		WebElement element = null;
		int display = opts.length > 0 ? opts[0] : 1 ;
		try
		{
			element = waitForAndGetElement(locator, DEFAULT_TIMEOUT, 1, display);
			return element.getText();
		}
		catch(StaleElementReferenceException e)
		{
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			return getText(locator);
		}
		finally 
		{
			loopCount = 0;
		}
				
	}
	
	public List<WebElement> getElements(String xpath)
	{
		try
		{
			return driver.findElements(By.xpath(xpath));
		}
		catch(StaleElementReferenceException e)
		{
			checkCycling(e, 5);
			Utils.pause(10000);
			return getElements(xpath);
		}
		finally
		{
			loopCount = 0;
		}
	}
	
	public boolean isTextNotPresent(String text)
	{
		return !isTextPresent(text);
	}
	
	public void dragAndDropToObject(Object sourceLocator, Object targetLocator)
	{
		info("--Drag and drop to object--");
		Actions action = new Actions(driver);
		try
		{
			WebElement source = waitForAndGetElement(sourceLocator);
			WebElement target = waitForAndGetElement(targetLocator);
			
			action.dragAndDrop(source, target).build().perform();
			
		}
		catch(UnhandledAlertException e)
		{
			
		}
		
		try
		{
			Alert alert = driver.switchTo().alert();
			alert.accept();
			switchToParentWindow();
		}
		catch(NoAlertPresentException eNoAlert)
		{
			
		}
		finally 
		{
			loopCount = 0;
		}
		Utils.pause(1000);
	}
	
	public void click(Object locator, Object... opParams)
	{
		int notDisplay = (Integer) (opParams.length > 0 ? opParams[0] : 0);
		Actions actions = new Actions(driver);
		
		try
		{
			WebElement element = waitForAndGetElement(locator, DEFAULT_TIMEOUT, 1, notDisplay);
			if(element.isEnabled())
				actions.click(element).perform();
			else
			{
				debug("Element is not enabled");
				click(locator, notDisplay);
			}
		}
		catch(StaleElementReferenceException e)
		{
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			click(locator, notDisplay);
			
		}
		catch(Exception e)
		{
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			click(locator, notDisplay);
		}
		finally
		{
			loopCount = 0;
		}
		Utils.pause(500);
	}
	
	public void clearCache()
	{
		Actions actionObject = new Actions(driver);
		try
		{
			actionObject.sendKeys(Keys.CONTROL).sendKeys(Keys.F5).build().perform();
		}
		catch(WebDriverException e)
		{
			debug("Retrying clear cache...");
			actionObject.sendKeys(Keys.CONTROL).sendKeys(Keys.F5).build().perform();
		}
	}
	
	//Use this method to verify if a check-box is ticked or not 
	public void verifyCheckBox(Object locator, int... opParams)
	{
		int notDisplayElement = opParams.length > 0 ? opParams[0]: 0 ;
		Actions actions = new Actions(driver);
		try
		{
			WebElement element = waitForAndGetElement(locator, DEFAULT_TIMEOUT, 1, notDisplayElement);
			
			if(!element.isSelected())
			{
				actions.click(element).perform();
			}
			else
			{
				info("Element " + locator + "is already checked.");
				
			}
		}
			catch(StaleElementReferenceException e)
			{
				checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
				Utils.pause(WAIT_INTERVAL);
				verifyCheckBox(locator, opParams);
			}
			finally
			{
				loopCount = 0;
			}
	}
	
	public String getValue(Object locator)
	{
		try
		{
			return waitForAndGetElement(locator).getAttribute("value");
		}
		catch(StaleElementReferenceException e)
		{
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			return getValue(locator);
		}
		finally
		{
			loopCount = 0;
		}
	}
	
	public void mouseOver(Object locator, boolean safeToSERE, Object... opParams)
	{
		WebElement element;
		Actions actions = new Actions(driver);
		int notDisplay = (Integer) (opParams.length > 0 ? opParams[0] : 0);
		try
		{
			if (safeToSERE)
			{
				for (int i = 1; i < ACTION_REPEAT; i++)
				{
					element = waitForAndGetElement(locator, 5000, 0, notDisplay);
					if (element == null)
					{
						Utils.pause(WAIT_INTERVAL);
					}
					else
					{
						actions.moveToElement(element).perform();
						break;
					}
				}
			}
			else
			{
				element = waitForAndGetElement(locator);
				actions.moveToElement(element).perform();
			}
		}
		catch (StaleElementReferenceException e)
		{
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			mouseOver(locator, safeToSERE);
		}
		finally
		{
			loopCount = 0;
		}
	}
	
	public void mouseOverAndClick(Object locator)
	{
		WebElement element;
		Actions actions = new Actions(driver);
		if (ieFlag)
		{
			element = getDisplayedElement(locator);
		}
		else
		{
			element = waitForAndGetElement(locator);
		}
		actions.moveToElement(element).click(element).build().perform();
	}
	
	public void waitForTextPresent(String text, int... opts)
	{
		int waitTime = opts.length > 0 ? opts[0] : DEFAULT_TIMEOUT;
		int display = opts.length > 1 ? opts[1] : 1;
		for (int second = 0; ; second++ )
		{
			if (second >= waitTime/WAIT_INTERVAL)
			{
				Assert.fail("Timeout at waitForTextPresent: " + text);
			}
			if (isTextPresent(text, display))
			{
				break;
			}
			Utils.pause(WAIT_INTERVAL);
		}
	}
	
	public void waitForTextNotPresent(String text, int...wait)
	{
		int waitTime = wait.length > 0 ? wait[0] : DEFAULT_TIMEOUT;
		for (int second = 0 ; ; second++ )
		{
			if (second >= waitTime/WAIT_INTERVAL)
			{
				Assert.fail("Timeout at waitForTextNotPresent:  " + text);
			}
			if (isTextNotPresent(text))
			{
				break;
			}
			Utils.pause(WAIT_INTERVAL);
		}
	}
	
	public void waitForMessage(String message, int... wait)
	{
		int waitTime = wait.length > 0 ? wait[0] : DEFAULT_TIMEOUT;
		Utils.pause(500);
		
		waitForAndGetElement("//*[contains(text(), '" + message + "')]", waitTime);
		
	}
	
	public void clearAndEnterText(Object locator, String value, boolean validate, Object...opParams)
	{
		int notDisplay = (Integer) (opParams.length > 0 ? opParams[0] : 0);
		try
		{
			for (int loop =1; ; loop++)
			{
				if (loop >= ACTION_REPEAT)
				{
					Assert.fail("Timeout at type: " + value + " into " + locator);
				}
				WebElement element = waitForAndGetElement(locator, DEFAULT_TIMEOUT, 1, notDisplay);
				if (element != null)
				{
					if (validate) element.clear();
					element.click();
					element.sendKeys(value);
					if (!validate || value.equals(getValue(locator)))
					{
						break;
					}
				}
				info ("Repeate action ..." + loop + " time(s)");
				Utils.pause(WAIT_INTERVAL);
			}
		}
		catch (StaleElementReferenceException e)
		{
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			clearAndEnterText(locator, value, validate, opParams);
		}
		catch (Exception e)
		{
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			clearAndEnterText(locator, value, validate, opParams);
		}
		finally
		{
			loopCount = 0;
		}
		
	}
	
	//Select option from combo box
	
	public void select(Object locator, String option, int... display )
	{
		int isDisplay = display.length > 0 ? display[0] : 1;
		
		try
		{
			for (int second = 0; ; second++ )
			{
				if (second >= DEFAULT_TIMEOUT/WAIT_INTERVAL)
				{
					Assert.fail("Timeout at select: " + option + " into " +locator);
				}
				Select select = new Select(waitForAndGetElement(locator, DEFAULT_TIMEOUT, 1, isDisplay));
				select.selectByVisibleText(option);
				if (option.equals(select.getFirstSelectedOption().getText()));
				{
					break;
				}
			}
		}
		catch (StaleElementReferenceException e)
		{
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			select(locator, option);
		}
		finally 
		{
			loopCount = 0;
		}
	}
	
	//Uncheck a checkbox
	
	public void unChekcBox(Object locator, int... opParams)
	{
		int notDisplayElement = opParams.length > 0 ? opParams[0] : 0;
		Actions actions = new Actions(driver);
		try
		{
			WebElement element = waitForAndGetElement(locator, DEFAULT_TIMEOUT, 1, notDisplayElement);
			
			if(element.isSelected())
			{
				actions.click(element).perform();
			}
			else
			{
				info ("Element " + locator + " is already unchecked. ");
			}
			
		}
		catch (StaleElementReferenceException e)
		{
			checkCycling(e, 5);
			Utils.pause(1000);
			unChekcBox(locator, opParams);
		}
		finally
		{
			loopCount = 0;
		}
	}
	
	public void rightClickOnElement (Object locator, int... opParams)
	{
		int display = opParams.length > 0 ? opParams[0]: 0;
		Actions actions = new Actions(driver);
		Utils.pause(500);
		try
		{
			WebElement element = waitForAndGetElement(locator, DEFAULT_TIMEOUT, 1, display);
			actions.contextClick(element).perform();
		}
		catch (StaleElementReferenceException e)
		{
			checkCycling (e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			rightClickOnElement(locator);
		}
		catch (Exception e)
		{
			checkCycling (e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			click(locator);
		}
		finally
		{
			loopCount = 0;
		}
	}
	
	//Double click on Element
	
	public void doubleClickOnElement(Object locator)
	{
		Actions actions =  new Actions(driver);
		try
		{
			WebElement element = waitForAndGetElement(locator);
			actions.doubleClick(element).perform();
			
		}
		catch (StaleElementReferenceException e)
		{
			checkCycling(e, 5);
			Utils.pause(1000);
			doubleClickOnElement(locator);
		}
		finally
		{
			loopCount = 0;
		}
	}
	
	public void checkCycling(Exception e, int loopCountAllowed)
	{
		info ("Exception : " + e.getClass().getName());
		if (loopCount > loopCountAllowed)
		{
			Assert.fail("Cycled: " + e.getMessage());
			
		}
		info("Repeat... " + loopCount + " time(s)");
		loopCount++;
	}
	
	//Method to switch to the parent window
	
	public void switchToParentWindow()
	{
		try
		{
			Set<String> availableWindows = driver.getWindowHandles();
			String windowIdParent = null;
			int counter = 1;
			for (String windowId : availableWindows)
			{
				if (counter == 1)
				{
					windowIdParent = windowId;
				}
				
				counter++;
			}
			driver.switchTo().window(windowIdParent);
			Utils.pause(1000);
		}
		catch (WebDriverException e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean isDisplay(Object locator)
	{
		boolean bool = false;
		WebElement e = getElement(locator);
		try
		{
			if (e != null)
				bool = e.isDisplayed();
		}
		catch (StaleElementReferenceException ex)
		{
			checkCycling(ex, 10);
			Utils.pause(WAIT_INTERVAL);
			isDisplay(locator);
		}
		finally
		{
			loopCount = 0;
		}
		return bool;
	}
	
	/**
	 * @param classElement
	 * @param object
	 * @return = true : if there is not scroll bar on element
	 * 				 = false : if there is scroll bar on element
	 */	
	
	public boolean checkScrollBarExists(By object)
	{
		WebElement element = waitForAndGetElement(object);
		String scrollHeight = String.valueOf(((JavascriptExecutor)driver).executeScript("return arguments[0].scrollHeight;", element));
		String offsetHeight = String.valueOf(((JavascriptExecutor)driver).executeScript("return arguments[0].offsetHeight;", element));
		info("scrollHeight: " + scrollHeight);
		info("offsetHeight: " + offsetHeight);
		int scroll = Integer.parseInt(scrollHeight);
		int offset = Integer.parseInt(offsetHeight);
		return scroll == offset;
	}
	
	public void copyPasteString(By orgin, By target, String value)
	{
		WebElement element1 = driver.findElement(orgin);
		WebElement element2 = driver.findElement(target);
		
		info("Type into the first locator");
		element1.clear();
		element1.sendKeys(value);
		
		info("Copy from the first locator");
		element1.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		element1.sendKeys(Keys.chord(Keys.CONTROL, "c"));
		
		info("Paste to the second locator");
		element2.click();
		element2.sendKeys(Keys.chord(Keys.CONTROL, "v"));
	}

	public static void pause(int time) {
		try{
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
