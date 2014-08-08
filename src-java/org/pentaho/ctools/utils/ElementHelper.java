package org.pentaho.ctools.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class ElementHelper {

  /**
   * This method shall check if the element to search is displayed and
   * is enabled.
   *
   * @param driver  The access to browser.
   * @param path    DOM element to search.
   */
  public static void IsElementDisplayed(WebDriver driver, By path) {
    for (int i = 0; i < 100; i++) {
      try {
        WebElement element = driver.findElement(path);
        if (element != null) {
          if (element.isDisplayed() && element.isEnabled()) {
            return;
          }
        }
      } catch ( NoSuchElementException ex ){
        System.out.println(ex.getMessage());
      }

      try {
        Thread.sleep(200);

      } catch (InterruptedException ex) {
        System.out.println("Exception timeout");
      }
    }
  }

  /**
   * This method shall verify if the element exist in DOM, if not exist wait the
   * amount of time specified.
   *
   * @param driver        The access to browser.
   * @param timeToWaitSec The amount of time to wait for the element.
   * @param path          The element path in DOM (css, id, xpath)
   * @return  true        Element exist in DOM.
   *          false       Elemenet does not exist.
   */
  public static boolean IsElementPresent(WebDriver driver, long timeToWaitSec, By path) {
    boolean elementPresent = true;
    try {
      driver.manage().timeouts().implicitlyWait(timeToWaitSec, TimeUnit.SECONDS);
      driver.findElement(path);
    } catch (NoSuchElementException ex) {
      elementPresent = false;
    }

    return elementPresent;
  }

  /**
   *
   * @param driver
   * @param maxTimeToWaitSec
   * @param path
   */
  public static void WaitForElementNotPresent(WebDriver driver, long maxTimeToWaitSec, By path){
    if ( maxTimeToWaitSec > 0 ) {
      int timeToWaitSec = 2;

      for (int i = 0; i < maxTimeToWaitSec; i = i + timeToWaitSec) {
        Boolean isPresent = IsElementPresent(driver, timeToWaitSec, path);
        if (isPresent == false) {
          break;
        }
      }
    }
  }
}