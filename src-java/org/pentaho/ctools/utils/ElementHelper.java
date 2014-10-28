package org.pentaho.ctools.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ElementHelper {
  //Log instance
  private static Logger log = LogManager.getLogger(ElementHelper.class);

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
	public static boolean IsElementInvisible(WebDriver driver, By locator) {
		boolean bElementInvisible = false;
		try {
			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
			driver.findElement(locator);
		} catch (NoSuchElementException s) {
		  log.error("NuSuchElement - got it. Locator: " + locator.toString());
		  bElementInvisible = true;
		}

		if (!bElementInvisible) {
		  Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
	        .withTimeout(30, TimeUnit.SECONDS)
	        .pollingEvery(1, TimeUnit.SECONDS);

	    driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			bElementInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
		}

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		return bElementInvisible;
	}
	
	/**
	 * TODO
	 * 
	 * @param driver
	 * @param locator
	 * @return
	 */
	public static WebElement IsElementVisible(WebDriver driver, By locator) {
	  WebElement element = null;
	  driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	  
	  Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
        .withTimeout(30, TimeUnit.SECONDS)
        .pollingEvery(200, TimeUnit.MILLISECONDS)
        .ignoring(NoSuchElementException.class);
		
		//Wait for element presence
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		return element;
	}
	
	/**
	 * TODO
	 * 
	 * @param driver
	 * @param locator
	 * @return
	 */
	public static boolean IsElementPresent(WebDriver driver, By locator) {
	  boolean isElementPresent = false;
	  Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
        .withTimeout(30, TimeUnit.SECONDS)
        .pollingEvery(200, TimeUnit.MILLISECONDS)
        .ignoring(NoSuchElementException.class);
	  
	  driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	  
	  WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	  if (element != null) {
	    isElementPresent = true;
	  }
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    
    return isElementPresent;
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
	  log.debug("Enter:FindElement");
	  try {
	    IsElementVisible(driver, locator);
	    log.debug("Element is visble");
			List<WebElement> listElements = driver.findElements(locator);
			if (listElements.size() > 0) {
  			WebElement element = listElements.get(0);
  			if ( element.isDisplayed() && element.isEnabled() ){
  			  log.debug("return element found it");
  				return element;
  			}
  			else {
  			  log.warn("Trying again! Displayed:" + element.isDisplayed() + " Enabled:" + element.isEnabled() + " Locator: " + locator.toString());
  				return FindElement(driver, locator);
  			}
			} else {
			  log.warn("Trying obtain! Locator: " + locator.toString());
			  return null;
			}
		} catch (StaleElementReferenceException s) {
		  log.error("Stale - got one. Locator: " + locator.toString());
			return FindElement(driver, locator);
		} catch ( ElementNotVisibleException v) {
		  log.error("NotVisible - got one. Locator: " + locator.toString());
			return IsElementVisible(driver, locator);
		}
	}
	
	
	/**
	 * 
	 * @param driver
	 * @param locator
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
	
	
	/**
	 * 
	 * @param driver
	 * @param locator
	 * @param text
	 * @return
	 */
  public static String WaitForText(WebDriver driver, By locator, String text) {
    String strText = "";
    
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
        .withTimeout(30, TimeUnit.SECONDS)
        .pollingEvery(200, TimeUnit.MILLISECONDS)
        .ignoring(NoSuchElementException.class);
    
    driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    
    boolean found = wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, strText));
    if (found) 
      strText = GetText(driver, locator);
    
    
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    
    return strText;
  }
	
}