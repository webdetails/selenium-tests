package org.pentaho.ctools.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.concurrent.TimeUnit;

public class ElementHelper {

	/**
	 * This method shall check if the element to search is displayed and is
	 * enabled, if not, wait until reach 20000 (20sec).
	 *
	 * @param driver
	 *          The access to browser.
	 * @param path
	 *          DOM element to search.
	 */
	public static boolean IsElementDisplayed(WebDriver driver, By path) {
		boolean elementDisplayed = false;

		for (int i = 0; i < 100; i++) {
			try {
				driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
				WebElement element = driver.findElement(path);
				if (element != null) {
					if (element.isDisplayed() && element.isEnabled()) {
						elementDisplayed = true;
						break;
					}
				}
			} catch (NoSuchElementException ex) {
				System.out.println(ex.getMessage());
			}

			try {
				Thread.sleep(200);
			} catch (InterruptedException ex) {
				System.out.println("Exception timeout");
			}
		}

		return elementDisplayed;
	}

	/**
	 * This method shall verify if the element exist in DOM, if not exist wait the
	 * amount of time specified.
	 *
	 * @param driver
	 *          The access to browser.
	 * @param timeToWaitSec
	 *          The amount of time to wait for the element.
	 * @param path
	 *          The element path in DOM (css, id, xpath)
	 * @return true Element exist in DOM. false Element does not exist.
	 */
	public static boolean IsElementPresent(WebDriver driver, long timeToWaitSec,
	    By path) {
		boolean elementPresent = true;

		try {
			driver.manage().timeouts()
			    .implicitlyWait(timeToWaitSec, TimeUnit.SECONDS);
			driver.findElement(path);
		} catch (NoSuchElementException ex) {
			elementPresent = false;
		}

		// Restore to initial value
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		return elementPresent;
	}

	/**
	 * TODO
	 *
	 * @param driver
	 * @param maxTimeToWaitSec
	 * @param path
	 */
	public static void WaitForElementNotPresent(WebDriver driver,
	    long maxTimeToWaitSec, By path) {
		if (maxTimeToWaitSec > 0) {
			int timeToWaitSec = 2;

			for (int i = 0; i < maxTimeToWaitSec; i = i + timeToWaitSec) {
				Boolean isPresent = IsElementPresent(driver, timeToWaitSec, path);
				if (isPresent == false) {
					break;
				}
			}
		}
	}

	/**
	 * TODO
	 * 
	 * @param driver
	 * @param wait
	 * @param locator
	 * @return
	 */
	public static boolean IsElementInvisible(WebDriver driver, Wait<WebDriver> wait, By locator) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		boolean b = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='busy-indicator-container waitPopup']")));
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return b;
	}
	
	public static WebElement IsElementVisible(WebDriver driver, By locator) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
        .withTimeout(30, TimeUnit.SECONDS)
        .pollingEvery(1, TimeUnit.SECONDS);
		
		//Wait for element presence
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	

	/**
	 * This method works as a wrapper for findElement method of WebDriver.
	 * So, in same cases, we may have the issue 'Stale Element Reference', i.e.,
	 * the element is not ready in DOM. Hence, to prevent exception, we develop
	 * a function that is the same of findElement but avoid this exception.
	 *  
	 * @param driver
	 * @param locator
	 * @return
	 */
	public static WebElement FindElement(WebDriver driver, By locator) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
        .withTimeout(30, TimeUnit.SECONDS)
        .pollingEvery(1, TimeUnit.SECONDS);
		
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			
			WebElement element = driver.findElements(locator).get(0);
			if ( element.isDisplayed() && element.isEnabled() ){
				return element;
			}
			else {
				System.out.println("Trying again!");
				return FindElement(driver, locator);
			}
		} catch (StaleElementReferenceException s) {
			System.out.println("Stale - got one");
			return FindElement(driver, locator);
		} catch ( ElementNotVisibleException v) {
			System.out.println("NotVisible - got one");
			return IsElementVisible(driver, locator);
		}
	}
	
	
	/**
	 * 
	 * @param elemetn
	 * @return
	 */
	public static String GetText(WebDriver driver, By locator) {
		String text = ""; 
		try {
			text = FindElement(driver, locator).getText();
		} catch (StaleElementReferenceException e) {
			System.out.println("Got stale");
			text = FindElement(driver, locator).getText();
		}
		return text;
	}
}