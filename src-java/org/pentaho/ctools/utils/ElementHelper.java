/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2014 by Pentaho : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/
package org.pentaho.ctools.utils;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;

public class ElementHelper{

  //Log instance
  private static Logger log = LogManager.getLogger(ElementHelper.class);

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
  public static WebElement FindElement(final WebDriver driver, final By locator) {
    log.debug("FindElement::Enter");
    WebElement element = WaitForElementPresenceAndVisible(driver, locator, 30);
    log.debug("FindElement::Exit");
    return element;
    /*WebElement element = null;
    //element = WaitForElementPresenceAndVisible(driver, locator);
    final long timeout = 29;
    driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

    try {

      class RunnableObject implements Runnable{

        private WebElement theElement;

        public RunnableObject(WebElement theElement){
          this.theElement = theElement;
        }

        public WebElement getValue() {
          return this.theElement;
        }

        @Override
        public void run() {
          Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
            .withTimeout(timeout, TimeUnit.SECONDS)
            .pollingEvery(50, TimeUnit.MILLISECONDS)
            .ignoring(NoSuchElementException.class)
            .ignoring(StaleElementReferenceException.class);

          //Wait for element invisible
          this.theElement = wait.until(new Function<WebDriver, WebElement>(){

            @Override
            public WebElement apply(WebDriver d) {
              WebElement elem = d.findElement(locator);
              if(elem.isDisplayed() && elem.isEnabled()) {
                return elem;
              }
              return null;
            }
          });
        }
      }

      RunnableObject r = new RunnableObject(element);

      ExecutorService executor = Executors.newSingleThreadExecutor();
      executor.submit(r).get(timeout + 1, TimeUnit.SECONDS);
      executor.shutdown();
      element = r.getValue();
    }
    catch(TimeoutException te) {
      log.warn("Timeout exceeded! Looking for: " + locator.toString());
      log.fatal("Exception", te);
    }
    catch(InterruptedException e) {
      log.fatal("Exception", e);
    }
    catch(ExecutionException e) {
      log.fatal("Exception", e);
    }
    catch(java.util.concurrent.TimeoutException cte) {
      log.warn("Timeout exceeded! Looking for: " + locator.toString());
      log.fatal("Exception", cte);
    }

    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    log.debug("FindElement::Exit");
    return element;
     */
    /*
    try {
      WaitForElementPresenceAndVisible(driver, locator);
      log.debug("Element is visble");
      List<WebElement> listElements = driver.findElements(locator);
      if(listElements.size() > 0) {
        WebElement element = listElements.get(0);
        if(element.isDisplayed() && element.isEnabled()) {
          log.debug("Return element found");
          log.debug("FindElement::Exit");
          return element;
        } else {
          log.warn("Trying again! Displayed:" + element.isDisplayed() + " Enabled:" + element.isEnabled() + " Locator: " + locator.toString());
          return FindElement(driver, locator);
        }
      } else {
        log.warn("Trying obtain! Locator: " + locator.toString());
        return null;
      }
    }
    catch(StaleElementReferenceException s) {
      log.error("Stale - got one. Locator: " + locator.toString());
      return FindElement(driver, locator);
    }
    catch(ElementNotVisibleException v) {
      log.error("NotVisible - got one. Locator: " + locator.toString());
      return WaitForElementPresenceAndVisible(driver, locator);
    }*/
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
  public static WebElement FindElementInvisible(WebDriver driver, By locator) {
    log.debug("FindElementInvisible::Enter");
    log.debug("Locator: " + locator.toString());

    try {
      WaitForElementPresence(driver, locator);
      log.debug("Element is visble");
      List<WebElement> listElements = driver.findElements(locator);
      if(listElements.size() > 0) {
        WebElement element = listElements.get(0);
        if(element.isEnabled()) {
          log.debug("Return element found it");
          return element;
        } else {
          log.warn("Trying again! Enabled:" + element.isEnabled() + " Locator: " + locator.toString());
          return FindElement(driver, locator);
        }
      } else {
        log.warn("Trying obtain! Locator: " + locator.toString());
        return null;
      }
    }
    catch(StaleElementReferenceException s) {
      log.error("Stale - got one. Locator: " + locator.toString());
      return FindElement(driver, locator);
    }
    catch(ElementNotVisibleException v) {
      log.error("NotVisible - got one. Locator: " + locator.toString());
      return WaitForElementPresenceAndVisible(driver, locator);
    }
    catch(TimeoutException te) {
      log.error("TimeoutException - got one. Locator: " + locator.toString());
      log.fatal("Exception", te);
      log.debug("Trying again.");
      return driver.findElement(locator);
    }
  }

  /**
   *
   * @param driver
   * @param locator
   * @return
   */
  public static String GetTextElementInvisible(WebDriver driver, By locator) {
    log.debug("GetTextElementInvisible::Enter");
    log.debug("Locator: " + locator.toString());

    String text = "";
    try {
      WebElement element = FindElementInvisible(driver, locator);
      text = ((JavascriptExecutor) driver).executeScript("return arguments[0].textContent", element).toString();
    }
    catch(StaleElementReferenceException e) {
      log.debug("Got stale");
      text = FindElementInvisible(driver, locator).getText();
    }
    catch(Exception e) {
      log.fatal("Exception", e);
    }

    log.debug("GetTextElementInvisible::Exit");
    return text;
  }

  /**
   * This method wait for text to be present.
   *
   * @param driver
   * @param locator
   * @param text
   * @return
   */
  public static String WaitForTextPresence(WebDriver driver, By locator, String textToWait) {
    log.debug("WaitForTextPresence(Main)::Enter");
    String str = WaitForTextPresence(driver, locator, textToWait, 10);
    log.debug("WaitForTextPresence(Main)::Exit");
    return str;
  }

  /**
   * This method wait for text to be present.
   *
   * @param driver
   * @param locator
   * @param text
   * @param timeout - in seconds
   * @return
   */
  public static String WaitForTextPresence(WebDriver driver, By locator, String textToWait, Integer timeout) {
    log.debug("WaitForTextPresence::Enter");
    log.debug("Locator: " + locator.toString());
    String textPresent = "";
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timeout, TimeUnit.SECONDS).pollingEvery(200, TimeUnit.MILLISECONDS);

    driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

    boolean isTextPresent = wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, textToWait));
    if(isTextPresent == true) {
      textPresent = textToWait;
    }

    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    log.debug("WaitForTextPresence::Exit");
    return textPresent;
  }

  /**
   * The function will search for the element and then click on it using
   * the Click method of java script.
   *
   * @param driver
   * @param locator
   */
  public static void ClickJS(WebDriver driver, By locator) {
    log.debug("ClickJS::Enter");
    log.debug("Locator: " + locator.toString());

    WebElement element = FindElement(driver, locator);
    if(element != null) {
      try {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
      }
      catch(WebDriverException wde) {
        if(wde.getMessage().contains("arguments[0].click is not a function")) {
          element.click();
        }
      }
    } else {
      log.error("Element is null " + locator.toString());
    }

    log.debug("ClickJS::Exit");
  }

  /**
   * The function will search for the element and then click on it using
   * the Click method of webdriver.
   *
   * @param driver
   * @param locator
   */
  public static void Click(WebDriver driver, By locator) {
    log.debug("Click::Enter");
    log.debug("Locator: " + locator.toString());

    try {
      WebElement element = FindElement(driver, locator);
      if(element != null) {
        element.click();
        log.debug("Click::Exit");
      } else {
        log.error("Element is null " + locator.toString());
      }
    }
    catch(StaleElementReferenceException e) {
      log.fatal("Exception", e);
      Click(driver, locator);
    }
  }

  /**
   * The function will search for the element present (doesn't matter
   * if element is visible or not) and then click on it.
   *
   * @param driver
   * @param locator
   */
  public static void ClickElementInvisible(WebDriver driver, By locator) {
    log.debug("ClickElementInvisible::Enter");
    log.debug("Locator: " + locator.toString());

    WebElement element = FindElementInvisible(driver, locator);
    if(element != null) {
      try {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
      }
      catch(WebDriverException wde) {
        if(wde.getMessage().contains("arguments[0].click is not a function")) {
          element.click();
        }
      }
    } else {
      log.error("Element is null " + locator.toString());
    }

    log.debug("ClickElementInvisible::Exit");
  }

  /**
   * This method pretends to check if the element is present, if it doesn't
   * then don't wait, if element is present, wait for its invisibility.
   *
   * @param driver
   * @param locator
   */
  public static void WaitForElementInvisibility(WebDriver driver, final By locator) {
    log.debug("WaitForElementInvisibility(Main)::Enter");
    WaitForElementInvisibility(driver, locator, 30);
    log.debug("WaitForElementInvisibility(Main)::Exit");
  }

  /**
   * This method pretends to check if the element is present, if it doesn't
   * then don't wait, if element is present, wait for its invisibility.
   *
   * @param driver
   * @param locator
   * @param timeout
   */
  public static void WaitForElementInvisibility(final WebDriver driver, final By locator, final Integer timeout) {
    log.debug("WaitForElementInvisibility::Enter");
    log.debug("Locator: " + locator.toString());

    driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

    try {

      Runnable r = new Runnable(){

        @Override
        public void run() {
          Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timeout, TimeUnit.SECONDS).pollingEvery(50, TimeUnit.MILLISECONDS);

          //Wait for element invisible
          wait.until(new Function<WebDriver, Boolean>(){

            @Override
            public Boolean apply(WebDriver d) {
              try {
                WebElement elem = d.findElement(locator);
                return elem.isDisplayed() == false;
              }
              catch(NoSuchElementException nsee) {
                return true;
              }
              catch(StaleElementReferenceException sere) {
                return false;
              }
            }
          });
        }
      };

      ExecutorService executor = Executors.newSingleThreadExecutor();
      executor.submit(r).get(timeout + 2, TimeUnit.SECONDS);
      executor.shutdown();

    }
    catch(TimeoutException te) {
      log.warn("Timeout exceeded! Looking for: " + locator.toString(), te);
    }
    catch(InterruptedException e) {
      log.fatal("Exception", e);
    }
    catch(ExecutionException e) {
      log.fatal("Exception", e);
    }
    catch(java.util.concurrent.TimeoutException cte) {
      log.warn("Timeout exceeded! Looking for: " + locator.toString(), cte);
    }

    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    log.debug("WaitForElementInvisibility::Exit");
  }

  /**
   * This method pretends to check if the element is present, if it doesn't
   * we wait for presence for a specific timeout (input), after this, we will
   * wait for element visible. And, if the element is present then we have to
   * check if is visible if not wait for visibility.
   *
   * The default timeout to wait for elements is 30 seconds.
   *
   * @param driver
   * @param locator
   */
  public static WebElement WaitForElementPresenceAndVisible(WebDriver driver, By locator) {
    log.debug("WaitForElementPresenceAndVisible(Main)::Enter");
    WebElement element = WaitForElementPresenceAndVisible(driver, locator, 30);
    log.debug("WaitForElementPresenceAndVisible(Main)::Exit");
    return element;
  }

  /**
   * This method pretends to check if the element is present, if it doesn't
   * we wait for presence for a specific timeout (input), after this, we will
   * wait for element visible. And, if the element is present then we have to
   * check if is visible if not wait for visibility.
   *
   * @param driver
   * @param locator
   * @param timeout
   */
  public static WebElement WaitForElementPresenceAndVisible(final WebDriver driver, final By locator, final Integer timeout) {
    log.debug("WaitForElementPresenceAndVisible::Enter");
    log.debug("Locator: " + locator.toString());
    WebElement element = null;
    driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

    try {

      class RunnableObject implements Runnable{

        private WebElement theElement;

        public RunnableObject(WebElement theElement){
          this.theElement = theElement;
        }

        public WebElement getValue() {
          return this.theElement;
        }

        @Override
        public void run() {
          Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timeout, TimeUnit.SECONDS).pollingEvery(50, TimeUnit.MILLISECONDS);

          //Wait for element visible
          this.theElement = wait.until(new Function<WebDriver, WebElement>(){

            @Override
            public WebElement apply(WebDriver d) {
              try {
                WebElement elem = d.findElement(locator);
                log.info(elem.isDisplayed());
                log.info(elem.isEnabled());
                if(elem.isDisplayed() && elem.isEnabled()) {
                  return elem;
                }
                log.info("not ready");
                return null;
              }
              catch(NoSuchElementException nsee) {
                log.info("nosuch");
                return null;
              }
              catch(StaleElementReferenceException sere) {
                log.info("stale");
                return null;
              }
            }
          });
        }
      };

      RunnableObject r = new RunnableObject(element);

      ExecutorService executor = Executors.newSingleThreadExecutor();
      executor.submit(r).get(timeout + 2, TimeUnit.SECONDS);
      executor.shutdown();
      element = r.getValue();
    }
    catch(TimeoutException te) {
      log.warn("Timeout exceeded! Looking for: " + locator.toString(), te);
    }
    catch(InterruptedException e) {
      log.fatal("Exception", e);
    }
    catch(ExecutionException e) {
      log.fatal("Exception", e);
    }
    catch(java.util.concurrent.TimeoutException cte) {
      log.warn("Timeout exceeded! Looking for: " + locator.toString(), cte);
    }

    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    log.debug("WaitForElementPresenceAndVisible::Exit");
    return element;
    /*log.debug("WaitForElementPresenceAndVisible::Enter");
    log.debug("Locator: " + locator.toString());

    WebElement element = null;
    List<WebElement> elements = null;
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timeout, TimeUnit.SECONDS).pollingEvery(50, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class);;

    driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

    try {
      elements = driver.findElements(locator);
      int size = elements.size();
      if(size == 0) {
        //wait for element presence
        elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        if(elements.size() != 0) {
          //wait for element visible
          elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
          if(elements.size() != 0) {
            element = elements.get(0);
          }
        }
      } else {
        element = elements.get(0);
        if(element.isDisplayed() == true && element.isEnabled() == true) {
          log.warn("We have some elements! Nr: " + size);
        }
        else {
          //wait for element visible
          log.warn("Wait for visibility!");
          elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
          if(elements.size() != 0) {
            element = elements.get(0);
          }
        }
      }
    }
    catch(StaleElementReferenceException sere) {
      log.warn("Stale Exception", sere);
      return WaitForElementPresenceAndVisible(driver, locator, timeout);
    }
    catch(TimeoutException te) {
      log.warn("Timeout Exception", te);
      return element;
    }
    catch(Exception e) {
      log.warn("Something went wrong searching for pr: " + locator.toString(), e);
    }

    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    log.debug("WaitForElementPresenceAndVisible::Exit");
    return element;*/
  }

  /**
   * This method pretends to check if the element is present, if it doesn't
   * we wait for presence for a specific timeout (30 seconds).
   *
   * @param driver
   * @param locator
   */
  public static WebElement WaitForElementPresence(WebDriver driver, By locator) {
    log.debug("WaitForElementPresence(Main)::Enter");
    WebElement element = WaitForElementPresence(driver, locator, 30);
    log.debug("WaitForElementPresence(Main)::Exit");
    return element;
  }

  /**
   * This method pretends to check if the element is present, if it doesn't
   * we wait for presence for a specific timeout (input).
   *
   * @param driver
   * @param locator
   */
  public static WebElement WaitForElementPresence(WebDriver driver, By locator, Integer timeout) {
    log.debug("WaitForElementPresence::Enter");
    log.debug("Locator: " + locator.toString());

    WebElement element = null;
    List<WebElement> elements = null;
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timeout, TimeUnit.SECONDS).pollingEvery(50, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class);

    driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

    try {
      elements = driver.findElements(locator);
      int size = elements.size();
      if(size == 0) {
        //wait for element presence
        elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        if(elements.size() != 0) {
          element = elements.get(0);
          log.debug("Get element present.");
        }
      } else {
        element = elements.get(0);
        log.warn("We have some elements! Nr: " + size);
      }
    }
    catch(Exception e) {
      log.warn("Something went wrong searching for pr: " + locator.toString());
      log.fatal("Exception", e);
    }

    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    log.debug("WaitForElementPresence::Exit");
    return element;
  }

  /**
   * This method pretends to check if the element is present, if it doesn't
   * then don't wait, if element is present, wait for its invisibility.
   *
   * @param driver
   * @param locator
   */
  public static WebElement WaitForElementVisibility(WebDriver driver, By locator) {
    log.debug("WaitForElementVisibility::Enter");
    log.debug("Locator: " + locator.toString());

    WebElement element = null;
    List<WebElement> elements = null;
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(30, TimeUnit.SECONDS).pollingEvery(50, TimeUnit.MILLISECONDS).ignoring(StaleElementReferenceException.class);

    driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

    try {
      elements = driver.findElements(locator);
      if(elements.size() > 0) {
        //wait for element to appear
        element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        log.debug("Get element visible.");
      } else {
        log.warn("No elements found!");
      }
    }
    catch(Exception e) {
      log.warn("Something went wrong searching for vi: " + locator.toString());
      log.fatal("Exception", e);
    }

    //------------ ALWAYS REQUIRE TO SET THE DEFAULT VALUE --------------------
    //when set a new implicitlyWait timeout, we have to set the default
    //in order to not destroy other invocations of findElement ('WebDriver').
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    log.debug("WaitForElementVisibility::Exit");
    return element;
  }

  /**
   * This method check if the element is present, if don't waits for it.
   * And then returns the textContent.
   *
   * NOTE - the method was created to help on the tool tips, when mouse over an
   * element and we want to read the elements.
   *
   * @param driver
   * @param locator
   * @return
   */
  public static String WaitForElementPresentGetText(WebDriver driver, By locator) {
    log.debug("WaitForElementPresentGetText::Enter");
    log.debug("Locator: " + locator.toString());

    String text = "";
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(5, TimeUnit.SECONDS).pollingEvery(15, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class);

    WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    if(element != null) {
      try {
        //Cross-browser, see: http://www.quirksmode.org/dom/html/
        text = ((JavascriptExecutor) driver).executeScript("return (arguments[0].innerText == null)?arguments[0].textContent:arguments[0].innerText", element).toString();
        //text = text.replaceAll("[^\\x00-\\x7F]", "");//only ascii charachters between 0 to 127
        text = text.replaceAll("\\xA0", " ");
        text = text.replaceAll("\\s+", " ");
        text = text.replaceAll("\\r\\n|\\r|\\n|\\t", "");
        text = text.trim();//remove spaces, newlines,...
      }
      catch(WebDriverException wde) {
        log.fatal("Exception", wde);
        text = element.getText();
      }
    }

    log.debug("WaitForElementPresentGetText::Exit");
    return text;
  }

  /**
   * This method pretends to check if the element is present, if it doesn't
   * we wait for presence for a specific timeout (30 seconds).
   *
   * @param driver
   * @param locator
   */
  public static boolean IsElementNotPresent(WebDriver driver, By locator, Integer timeout) {
    log.debug("IsElementNotPresent::Enter");
    log.debug("Locator: " + locator.toString());

    boolean isElementPresent = false;

    driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);

    try {
      //When searching for the element the 'findElement' will wait a
      //maximum of 'timeout' seconds to return the element or throw exception
      WebElement element = driver.findElement(locator);
      if(element != null) {
        isElementPresent = true;
      }
    }
    catch(NoSuchElementException nsee) {
      log.warn("Element not present! " + locator.toString());
    }
    catch(Exception e) {
      log.warn("Something went wrong searching for: " + locator.toString());
      log.fatal("Exception", e);
    }

    //------------ ALWAYS REQUIRE TO SET THE DEFAULT VALUE --------------------
    //when set a new implicitlyWait timeout, we have to set the default
    //in order to not destroy other invocations of findElement ('WebDriver').
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    log.debug("IsElementNotPresent::Exit");
    return isElementPresent;
  }

  /**
   * This method pretends to check if the element is present, if it doesn't
   * we wait for presence for a specific timeout (30 seconds).
   *
   * @param driver
   * @param locator
   */
  public static boolean IsElementNotPresent(WebDriver driver, By locator) {
    log.debug("IsElementNotPresent(Main)::Enter");
    boolean result = IsElementNotPresent(driver, locator, 30);
    log.debug("IsElementNotPresent(Main)::Exit");
    return result;
  }

  /**
   * This method intends to get the value of an input field.
   *
   * @param driver
   * @param locator
   * @return
   */
  public static String GetInputValue(WebDriver driver, By locator) {
    log.debug("GetInputValue::Enter");
    log.debug("Locator: " + locator.toString());

    String attrValue = "";
    WebElement element = FindElement(driver, locator);
    if(element != null) {
      attrValue = element.getAttribute("value");
    }

    log.debug("GetInputValue::Exit");
    return attrValue;
  }

  /**
   * This method intends to drag and drop an element.
   *
   * @param driver
   * @param from
   * @param to
   * @return
   */
  public static void DragAndDrop(WebDriver driver, By from, By to) {
    log.debug("DragAndDrop::Enter");
    log.debug("From: " + from.toString());
    log.debug("To: " + to.toString());

    WebElement drag = FindElement(driver, from);
    WebElement drop = FindElement(driver, to);
    if(drag != null && drop != null) {
      new Actions(driver).dragAndDrop(drag, drop).build().perform();
    }

    log.debug("DragAndDrop::exit");
  }

  /**
   * This method intends to check if two elements overlap or are contained inside each other, returns true if
   * elements don't overlap.
   *
   * @param driver
   * @param element1
   * @param element2
   * @return
   */
  public static boolean ElementsNotOverlap(WebDriver driver, By element1, By element2) {
    log.debug("ElementsNotOverlap::Enter");
    log.debug("Locator1: " + element1.toString());
    log.debug("Locator2: " + element2.toString());

    WebElement elem1 = ElementHelper.FindElement(driver, element1);
    WebElement elem2 = ElementHelper.FindElement(driver, element2);

    // get borders of first element
    Point firstLocation = elem1.getLocation();
    Dimension firstDimension = elem1.getSize();
    int firstLeft = firstLocation.getX();
    int firstTop = firstLocation.getY();
    int firstRight = firstLeft + firstDimension.getWidth();
    int firstBottom = firstTop + firstDimension.getHeight();
    // get borders of second element
    Point secondLocation = elem2.getLocation();
    Dimension secondDimension = elem2.getSize();
    int secondLeft = secondLocation.getX();
    int secondTop = secondLocation.getY();
    int secondRight = secondLeft + secondDimension.getWidth();
    int secondBottom = secondTop + secondDimension.getHeight();
    log.debug(firstTop + " " + firstBottom + " " + firstLeft + " " + firstRight);
    log.debug(secondTop + " " + secondBottom + " " + secondLeft + " " + secondRight);
    //if firstElement is either to the left, the right, above or below the second return true
    boolean notIntersected = firstBottom < secondTop
      || firstTop > secondBottom
      || firstLeft > secondRight
      || firstRight < secondLeft;

    log.debug("ElementsNotOverlap::Exit");
    return notIntersected;
  }

  /**
   * This function shall wait for the new window display.
   * The code check if there is more than one window in the list. In the
   * beginning we only have the main window.
   *
   * @param driver
   */
  public static void WaitForNewWindow(WebDriver driver) {
    log.debug("WaitForNewWindow::Enter");

    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(30, TimeUnit.SECONDS).pollingEvery(500, TimeUnit.MILLISECONDS);

    wait.until(new ExpectedCondition<Boolean>(){

      @Override
      public Boolean apply(WebDriver d) {
        return d.getWindowHandles().size() != 1;
      }
    });

    log.debug("WaitForNewWindow::Exit");
  }

  /**
   * This function shall wait for the alert window to be closed or doesn't
   * exist.
   *
   * @param driver
   */
  public static void WaitForAlertNotPresent(WebDriver driver) {
    log.debug("WaitForAlertNotPresent::Enter");

    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(30, TimeUnit.SECONDS).pollingEvery(100, TimeUnit.MILLISECONDS);

    wait.until(new ExpectedCondition<Boolean>(){

      @Override
      public Boolean apply(WebDriver d) {
        Boolean alertExist = false;
        try {
          d.switchTo().alert();
          alertExist = true;
        }
        catch(NoAlertPresentException e) {
          //Ignore the exception
        }
        return alertExist != true;
      }
    });

    log.debug("WaitForAlertNotPresent::Exit");
  }

  /**
   * The method will wait for the frame to be available to usage. To ensure that
   * we check if an element exist inside (example a new element that refresh the
   * frame).
   *
   * @param driver
   * @param locator
   */
  public static void WaitForFrameReady(WebDriver driver, final By locator) {
    log.debug("WaitForFrameReady::Enter");
    log.debug("Locator: " + locator.toString());

    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(30, TimeUnit.SECONDS).pollingEvery(100, TimeUnit.MILLISECONDS);

    wait.until(new ExpectedCondition<Boolean>(){

      @Override
      public Boolean apply(WebDriver d) {
        Boolean elementExist = false;
        List<WebElement> listElements = d.findElements(locator);

        if(listElements.size() > 0) {
          elementExist = true;
        }
        return elementExist;
      }
    });

    log.debug("WaitForFrameReady::Exit");
  }

  /**
   *
   *
   * @param driver
   * @param locator
   * @param attributeName
   * @return
   */
  public static String GetAttribute(WebDriver driver, By locator, String attributeName) {
    log.debug("GetAttribute::Enter");
    log.debug("Locator: " + locator.toString());

    try {
      WebElement element = FindElement(driver, locator);
      log.debug("GetAttribute::Exit");
      return element.getAttribute(attributeName);
    }
    catch(StaleElementReferenceException e) {
      log.fatal("Exception", e);
      return GetAttribute(driver, locator, attributeName);
    }
  }
}
