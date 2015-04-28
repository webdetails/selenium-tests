/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2015 by Pentaho : http://www.pentaho.com
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

public class ElementHelper {

  // Log instance
  private static Logger log = LogManager.getLogger( ElementHelper.class );

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
  public static WebElement FindElement( final WebDriver driver, final By locator ) {
    log.debug( "FindElement::Enter" );
    WebElement element = WaitForElementPresenceAndVisible( driver, locator, 30 );
    log.debug( "FindElement::Exit" );
    return element;
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
  public static WebElement FindElement( final WebDriver driver, final By locator, Integer timeout ) {
    log.debug( "FindElement::Enter" );
    WebElement element = WaitForElementPresenceAndVisible( driver, locator, timeout );
    log.debug( "FindElement::Exit" );
    return element;
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
  public static WebElement FindElementInvisible( WebDriver driver, By locator ) {
    log.debug( "FindElementInvisible::Enter" );
    log.debug( "Locator: " + locator.toString() );

    WebElement element = null;

    try {
      WaitForElementPresence( driver, locator );
      log.debug( "Element is presence!" );
      List<WebElement> listElements = driver.findElements( locator );
      if ( listElements.size() > 0 ) {
        WebElement elementTmp = listElements.get( 0 );
        if ( elementTmp.isEnabled() ) {
          log.debug( "Return element found it" );
          element = elementTmp;
        } else {
          log.warn( "Trying again! Enabled:" + elementTmp.isEnabled() );
          element = FindElementInvisible( driver, locator );
        }
      } else {
        log.warn( "No element found!" );
      }
    } catch ( StaleElementReferenceException s ) {
      log.warn( "Stale - got one. Locator: " + locator.toString() );
      element = FindElementInvisible( driver, locator );
    } catch ( TimeoutException te ) {
      log.warn( "TimeoutException - got one. Locator: " + locator.toString() );
    }

    log.debug( "FindElementInvisible::Exit" );
    return element;
  }

  /**
   *
   * @param driver
   * @param locator
   * @return
   */
  public static String GetTextElementInvisible( WebDriver driver, By locator ) {
    log.debug( "GetTextElementInvisible::Enter" );
    log.debug( "Locator: " + locator.toString() );

    String text = "";
    try {
      WebElement element = FindElementInvisible( driver, locator );
      text = ( (JavascriptExecutor) driver ).executeScript( "return arguments[0].textContent", element ).toString();
    } catch ( StaleElementReferenceException e ) {
      log.warn( "Stale Element Reference Exception" );
      text = FindElementInvisible( driver, locator ).getText();
    } catch ( Exception e ) {
      log.catching( e );
    }

    log.debug( "GetTextElementInvisible::Exit" );
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
  public static String WaitForTextPresence( WebDriver driver, By locator, String textToWait ) {
    log.debug( "WaitForTextPresence(Main)::Enter" );
    String str = WaitForTextPresence( driver, locator, textToWait, 10 );
    log.debug( "WaitForTextPresence(Main)::Exit" );
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
  public static String WaitForTextPresence( WebDriver driver, By locator, String textToWait, Integer timeout ) {
    log.debug( "WaitForTextPresence::Enter" );
    log.debug( "Locator: " + locator.toString() );
    String textPresent = "";

    try {
      Wait<WebDriver> wait = new FluentWait<WebDriver>( driver ).withTimeout( timeout, TimeUnit.SECONDS ).pollingEvery( 200, TimeUnit.MILLISECONDS );

      driver.manage().timeouts().implicitlyWait( 0, TimeUnit.SECONDS );

      boolean isTextPresent = wait.until( ExpectedConditions.textToBePresentInElementLocated( locator, textToWait ) );
      if ( isTextPresent == true ) {
        textPresent = textToWait;
      }

      driver.manage().timeouts().implicitlyWait( 30, TimeUnit.SECONDS );
    } catch ( StaleElementReferenceException sere ) {
      log.warn( "Stale Element Reference Exception" );
      textPresent = WaitForTextPresence( driver, locator, textToWait, timeout );
    } catch ( Exception e ) {
      log.error( "Exception" );
    }

    log.debug( "WaitForTextPresence::Exit" );
    return textPresent;
  }

  /**
   * The function will search for the element and then click on it using
   * the Click method of java script.
   *
   * @param driver
   * @param locator
   */
  public static void ClickJS( WebDriver driver, By locator ) {
    log.debug( "ClickJS::Enter" );
    log.debug( "Locator: " + locator.toString() );

    WebElement element = FindElement( driver, locator );
    if ( element != null ) {
      try {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript( "arguments[0].click();", element );
      } catch ( WebDriverException wde ) {
        if ( wde.getMessage().contains( "arguments[0].click is not a function" ) ) {
          element.click();
        }
      }
    } else {
      log.error( "Element is null " + locator.toString() );
    }

    log.debug( "ClickJS::Exit" );
  }

  /**
   * The function will search for the element and then click on it using
   * the Click method of webdriver.
   *
   * @param driver
   * @param locator
   */
  public static void Click( WebDriver driver, By locator ) {
    log.debug( "Click::Enter" );
    log.debug( "Locator: " + locator.toString() );

    try {
      WebElement element = FindElement( driver, locator );
      if ( element != null ) {
        element.click();
        log.debug( "Click::Exit" );
      } else {
        log.error( "Element is null " + locator.toString() );
      }
    } catch ( StaleElementReferenceException e ) {
      log.warn( "Stale Element Reference Exception" );
      Click( driver, locator );
    }
  }

  /**
   * The function will search for the element present (doesn't matter
   * if element is visible or not) and then click on it.
   *
   * @param driver
   * @param locator
   */
  public static void ClickElementInvisible( WebDriver driver, By locator ) {
    log.debug( "ClickElementInvisible::Enter" );
    log.debug( "Locator: " + locator.toString() );

    WebElement element = FindElementInvisible( driver, locator );
    if ( element != null ) {
      try {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript( "arguments[0].click();", element );
      } catch ( WebDriverException wde ) {
        if ( wde.getMessage().contains( "arguments[0].click is not a function" ) ) {
          element.click();
        }
      }
    } else {
      log.error( "Element is null " + locator.toString() );
    }

    log.debug( "ClickElementInvisible::Exit" );
  }

  /**
   * This method pretends to check if the element is present, if it doesn't
   * then don't wait, if element is present, wait for its invisibility.
   *
   * @param driver
   * @param locator
   */
  public static void WaitForElementInvisibility( WebDriver driver, final By locator ) {
    log.debug( "WaitForElementInvisibility(Main)::Enter" );
    WaitForElementInvisibility( driver, locator, 30 );
    log.debug( "WaitForElementInvisibility(Main)::Exit" );
  }

  /**
   * This method pretends to check if the element is present, if it doesn't
   * then don't wait, if element is present, wait for its invisibility.
   *
   * @param driver
   * @param locator
   * @param timeout
   */
  public static void WaitForElementInvisibility( final WebDriver driver, final By locator, final Integer timeout ) {
    log.debug( "WaitForElementInvisibility::Enter" );
    log.debug( "Locator: " + locator.toString() );

    driver.manage().timeouts().implicitlyWait( 0, TimeUnit.SECONDS );

    try {

      Runnable r = new Runnable() {

        @Override
        public void run() {
          Wait<WebDriver> wait = new FluentWait<WebDriver>( driver ).withTimeout( timeout, TimeUnit.SECONDS ).pollingEvery( 50, TimeUnit.MILLISECONDS );

          // Wait for element invisible
          wait.until( new Function<WebDriver, Boolean>() {

            @Override
            public Boolean apply( WebDriver d ) {
              try {
                List<WebElement> listElements = d.findElements( locator );
                if ( listElements.size() > 0 ) {
                  WebElement elem = listElements.get( 0 );
                  return ( !elem.isDisplayed() ) ? true : false;
                }
                // The element does not exit, i.e., is not visible and even present
                return true;
              } catch ( StaleElementReferenceException sere ) {
                return false;
              }
            }
          } );
        }
      };

      ExecutorService executor = Executors.newSingleThreadExecutor();
      executor.submit( r ).get( timeout + 2, TimeUnit.SECONDS );
      executor.shutdown();

    } catch ( InterruptedException ie ) {
      log.warn( "Interrupted Exception" );
    } catch ( ExecutionException ee ) {
      if ( ee.getCause().getClass().getCanonicalName().equalsIgnoreCase( TimeoutException.class.getCanonicalName() ) ) {
        log.warn( "WebDriver timeout exceeded! Looking for: " + locator.toString() );
      } else {
        log.warn( "Execution Exception" );
      }
    } catch ( java.util.concurrent.TimeoutException cte ) {
      log.warn( "Thread timeout exceeded! Looking for: " + locator.toString() );
    } catch ( Exception e ) {
      log.error( "Exception" );
      log.catching( e );
    }

    driver.manage().timeouts().implicitlyWait( 30, TimeUnit.SECONDS );

    log.debug( "WaitForElementInvisibility::Exit" );
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
  public static WebElement WaitForElementPresenceAndVisible( WebDriver driver, By locator ) {
    log.debug( "WaitForElementPresenceAndVisible(Main)::Enter" );
    WebElement element = WaitForElementPresenceAndVisible( driver, locator, 30 );
    log.debug( "WaitForElementPresenceAndVisible(Main)::Exit" );
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
  public static WebElement WaitForElementPresenceAndVisible( final WebDriver driver, final By locator,
      final Integer timeout ) {
    log.debug( "WaitForElementPresenceAndVisible::Enter" );
    log.debug( "Locator: " + locator.toString() );
    WebElement element = null;
    driver.manage().timeouts().implicitlyWait( 0, TimeUnit.SECONDS );

    try {

      class RunnableObject implements Runnable {

        private WebElement theElement;

        public RunnableObject( WebElement theElement ) {
          this.theElement = theElement;
        }

        public WebElement getValue() {
          return this.theElement;
        }

        @Override
        public void run() {
          Wait<WebDriver> wait = new FluentWait<WebDriver>( driver ).withTimeout( timeout, TimeUnit.SECONDS ).pollingEvery( 50, TimeUnit.MILLISECONDS );

          // Wait for element visible
          this.theElement = wait.until( new Function<WebDriver, WebElement>() {

            @Override
            public WebElement apply( WebDriver d ) {
              try {
                List<WebElement> listElem = d.findElements( locator );
                if ( listElem.size() > 0 ) {
                  WebElement elem = listElem.get( 0 );
                  if ( elem.isDisplayed() && elem.isEnabled() ) {
                    return elem;
                  }
                  return null;
                }
                return null;
              } catch ( StaleElementReferenceException sere ) {
                return null;
              }
            }
          } );
        }
      }

      RunnableObject r = new RunnableObject( element );

      ExecutorService executor = Executors.newSingleThreadExecutor();
      executor.submit( r ).get( timeout + 2, TimeUnit.SECONDS );
      executor.shutdown();
      element = r.getValue();
    } catch ( InterruptedException ie ) {
      log.warn( "Interrupted Exception" );
    } catch ( ExecutionException ee ) {
      if ( ee.getCause().getClass().getCanonicalName().equalsIgnoreCase( TimeoutException.class.getCanonicalName() ) ) {
        log.warn( "WebDriver timeout exceeded! Looking for: " + locator.toString() );
      } else {
        log.warn( "Execution Exception" );
      }
    } catch ( java.util.concurrent.TimeoutException cte ) {
      log.warn( "Thread timeout exceeded! Looking for: " + locator.toString() );
    } catch ( Exception e ) {
      log.error( "Exception" );
      log.catching( e );
    }

    driver.manage().timeouts().implicitlyWait( 30, TimeUnit.SECONDS );

    log.debug( "WaitForElementPresenceAndVisible::Exit" );
    return element;
  }

  /**
   * This method pretends to check if the element is present, if it doesn't
   * we wait for presence for a specific timeout (30 seconds).
   *
   * @param driver
   * @param locator
   */
  public static WebElement WaitForElementPresence( WebDriver driver, By locator ) {
    log.debug( "WaitForElementPresence(Main)::Enter" );
    WebElement element = WaitForElementPresence( driver, locator, 30 );
    log.debug( "WaitForElementPresence(Main)::Exit" );
    return element;
  }

  /**
   * This method pretends to check if the element is present, if it doesn't
   * we wait for presence for a specific timeout (input).
   *
   * @param driver
   * @param locator
   */
  public static WebElement WaitForElementPresence( final WebDriver driver, final By locator, final Integer timeout ) {
    log.debug( "WaitForElementPresence::Enter" );
    log.debug( "Locator: " + locator.toString() );
    WebElement element = null;
    driver.manage().timeouts().implicitlyWait( 0, TimeUnit.SECONDS );

    try {

      class RunnableObject implements Runnable {

        private WebElement theElement;

        public RunnableObject( WebElement theElement ) {
          this.theElement = theElement;
        }

        public WebElement getValue() {
          return this.theElement;
        }

        @Override
        public void run() {
          Wait<WebDriver> wait = new FluentWait<WebDriver>( driver ).withTimeout( timeout, TimeUnit.SECONDS ).pollingEvery( 50, TimeUnit.MILLISECONDS );

          // Wait for element visible
          this.theElement = wait.until( new Function<WebDriver, WebElement>() {

            @Override
            public WebElement apply( WebDriver d ) {
              try {
                List<WebElement> listElem = d.findElements( locator );
                if ( listElem.size() > 0 ) {
                  WebElement elem = listElem.get( 0 );
                  if ( elem.isEnabled() ) {
                    return elem;
                  }
                  return null;
                }
                return null;
              } catch ( StaleElementReferenceException sere ) {
                return null;
              }
            }
          } );
        }
      }

      RunnableObject r = new RunnableObject( element );

      ExecutorService executor = Executors.newSingleThreadExecutor();
      executor.submit( r ).get( timeout + 2, TimeUnit.SECONDS );
      executor.shutdown();
      element = r.getValue();
    } catch ( InterruptedException ie ) {
      log.warn( "Interrupted Exception" );
    } catch ( ExecutionException ee ) {
      if ( ee.getCause().getClass().getCanonicalName().equalsIgnoreCase( TimeoutException.class.getCanonicalName() ) ) {
        log.warn( "WebDriver timeout exceeded! Looking for: " + locator.toString() );
      } else {
        log.warn( "Execution Exception" );
      }
    } catch ( java.util.concurrent.TimeoutException cte ) {
      log.warn( "Thread timeout exceeded! Looking for: " + locator.toString() );
    } catch ( Exception e ) {
      log.error( "Exception" );
      log.catching( e );
    }

    driver.manage().timeouts().implicitlyWait( 30, TimeUnit.SECONDS );

    log.debug( "WaitForElementPresence::Exit" );
    return element;
  }

  /**
   * This method pretends to check if the element is present, if it doesn't
   * then don't wait, if element is present, wait for its invisibility.
   *
   * @param driver
   * @param locator
   */
  public static WebElement WaitForElementVisibility( WebDriver driver, By locator ) {
    log.debug( "WaitForElementVisibility::Enter" );
    log.debug( "Locator: " + locator.toString() );

    WebElement element = null;
    List<WebElement> elements = null;
    Wait<WebDriver> wait = new FluentWait<WebDriver>( driver ).withTimeout( 30, TimeUnit.SECONDS ).pollingEvery( 50, TimeUnit.MILLISECONDS ).ignoring( StaleElementReferenceException.class );

    driver.manage().timeouts().implicitlyWait( 0, TimeUnit.SECONDS );

    try {
      elements = driver.findElements( locator );
      if ( elements.size() > 0 ) {
        // wait for element to appear
        element = wait.until( ExpectedConditions.visibilityOfElementLocated( locator ) );
        log.debug( "Get element visible." );
      } else {
        log.warn( "No elements found!" );
      }
    } catch ( Exception e ) {
      log.warn( "Something went wrong searching for vi: " + locator.toString() );
      log.catching( e );
    }

    // ------------ ALWAYS REQUIRE TO SET THE DEFAULT VALUE --------------------
    // when set a new implicitlyWait timeout, we have to set the default
    // in order to not destroy other invocations of findElement ('WebDriver').
    driver.manage().timeouts().implicitlyWait( 30, TimeUnit.SECONDS );
    log.debug( "WaitForElementVisibility::Exit" );
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
  public static String WaitForElementPresentGetText( WebDriver driver, By locator ) {
    log.debug( "WaitForElementPresentGetText::Enter" );
    log.debug( "Locator: " + locator.toString() );

    String text = "";
    Wait<WebDriver> wait = new FluentWait<WebDriver>( driver ).withTimeout( 5, TimeUnit.SECONDS ).pollingEvery( 15, TimeUnit.MILLISECONDS ).ignoring( NoSuchElementException.class ).ignoring( StaleElementReferenceException.class );

    WebElement element = wait.until( ExpectedConditions.presenceOfElementLocated( locator ) );
    if ( element != null ) {
      try {
        // Cross-browser, see: http://www.quirksmode.org/dom/html/
        text = ( (JavascriptExecutor) driver ).executeScript( "return (arguments[0].innerText == null)?arguments[0].textContent:arguments[0].innerText", element ).toString();
        text = text.replaceAll( "\\xA0", " " );
        text = text.replaceAll( "\\s+", " " );
        text = text.replaceAll( "\\r\\n|\\r|\\n|\\t", "" );
        text = text.trim(); // remove spaces, newlines,...
      } catch ( WebDriverException wde ) {
        log.warn( "WebDriver Exception" );
        text = WaitForElementPresentGetText( driver, locator );
      }
    }

    log.debug( "WaitForElementPresentGetText::Exit" );
    return text;
  }

  /**
   * This method intends to get the value of an input field.
   *
   * @param driver
   * @param locator
   * @return
   */
  public static String GetInputValue( WebDriver driver, By locator ) {
    log.debug( "GetInputValue::Enter" );
    log.debug( "Locator: " + locator.toString() );

    String attrValue = "";
    WebElement element = FindElement( driver, locator );
    if ( element != null ) {
      attrValue = element.getAttribute( "value" );
    }

    log.debug( "GetInputValue::Exit" );
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
  public static void DragAndDrop( WebDriver driver, By from, By to ) {
    log.debug( "DragAndDrop::Enter" );
    log.debug( "From: " + from.toString() );
    log.debug( "To: " + to.toString() );

    WebElement drag = FindElement( driver, from );
    WebElement drop = FindElement( driver, to );
    if ( drag != null && drop != null ) {
      new Actions( driver ).dragAndDrop( drag, drop ).build().perform();
    }

    log.debug( "DragAndDrop::exit" );
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
  public static boolean ElementsNotOverlap( WebDriver driver, By element1, By element2 ) {
    log.debug( "ElementsNotOverlap::Enter" );
    log.debug( "Locator1: " + element1.toString() );
    log.debug( "Locator2: " + element2.toString() );

    WebElement elem1 = ElementHelper.FindElement( driver, element1 );
    WebElement elem2 = ElementHelper.FindElement( driver, element2 );

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
    log.debug( firstTop + " " + firstBottom + " " + firstLeft + " " + firstRight );
    log.debug( secondTop + " " + secondBottom + " " + secondLeft + " " + secondRight );
    // if firstElement is either to the left, the right, above or below the second return true
    boolean notIntersected = firstBottom < secondTop || firstTop > secondBottom || firstLeft > secondRight || firstRight < secondLeft;

    log.debug( "ElementsNotOverlap::Exit" );
    return notIntersected;
  }

  /**
   * This function shall wait for the new window display.
   * The code check if there is more than one window in the list. In the
   * beginning we only have the main window.
   *
   * @param driver
   */
  public static void WaitForNewWindow( WebDriver driver ) {
    log.debug( "WaitForNewWindow::Enter" );

    Wait<WebDriver> wait = new FluentWait<WebDriver>( driver ).withTimeout( 30, TimeUnit.SECONDS ).pollingEvery( 500, TimeUnit.MILLISECONDS );

    wait.until( new ExpectedCondition<Boolean>() {

      @Override
      public Boolean apply( WebDriver d ) {
        return d.getWindowHandles().size() != 1;
      }
    } );

    log.debug( "WaitForNewWindow::Exit" );
  }

  /**
   * This function shall wait for the alert window to be closed or doesn't
   * exist.
   *
   * @param driver
   */
  public static void WaitForAlertNotPresent( WebDriver driver ) {
    log.debug( "WaitForAlertNotPresent::Enter" );

    Wait<WebDriver> wait = new FluentWait<WebDriver>( driver ).withTimeout( 30, TimeUnit.SECONDS ).pollingEvery( 100, TimeUnit.MILLISECONDS );

    wait.until( new ExpectedCondition<Boolean>() {

      @Override
      public Boolean apply( WebDriver d ) {
        Boolean alertExist = false;
        try {
          d.switchTo().alert();
          alertExist = true;
        } catch ( NoAlertPresentException e ) {
          // Ignore the exception
        }
        return alertExist != true;
      }
    } );

    log.debug( "WaitForAlertNotPresent::Exit" );
  }

  /**
   * The method will wait for the frame to be available to usage. To ensure that
   * we check if an element exist inside (example a new element that refresh the
   * frame).
   *
   * @param driver
   * @param locator
   */
  public static void WaitForFrameReady( WebDriver driver, final By locator ) {
    log.debug( "WaitForFrameReady::Enter" );
    log.debug( "Locator: " + locator.toString() );

    Wait<WebDriver> wait = new FluentWait<WebDriver>( driver ).withTimeout( 30, TimeUnit.SECONDS ).pollingEvery( 100, TimeUnit.MILLISECONDS );

    wait.until( new ExpectedCondition<Boolean>() {

      @Override
      public Boolean apply( WebDriver d ) {
        Boolean elementExist = false;
        List<WebElement> listElements = d.findElements( locator );

        if ( listElements.size() > 0 ) {
          elementExist = true;
        }
        return elementExist;
      }
    } );

    log.debug( "WaitForFrameReady::Exit" );
  }

  /**
   *
   *
   * @param driver
   * @param locator
   * @param attributeName
   * @return
   */
  public static String GetAttribute( WebDriver driver, By locator, String attributeName ) {
    log.debug( "GetAttribute::Enter" );

    String attributeValue = "";
    try {
      WebElement element = FindElement( driver, locator );
      if ( element != null ) {
        attributeValue = element.getAttribute( attributeName );
      } else {
        log.warn( "Element is null - could not get attribute value!" );
      }
    } catch ( StaleElementReferenceException e ) {
      log.warn( "Stale Element Reference Exception" );
      attributeValue = GetAttribute( driver, locator, attributeName );
    }

    log.debug( "GetAttribute::Exit" );
    return attributeValue;
  }

  /**
   * This method pretends to assert the element is not present and return a
   * boolean = true if it isn't.
   *
   * @param driver
   * @param locator
   * @return
   */
  public static Boolean WaitForElementNotPresent( final WebDriver driver, final By locator ) {
    log.debug( "WaitForElementNotPresent(Main)::Enter" );
    boolean result = WaitForElementNotPresent( driver, locator, 30 );
    log.debug( "WaitForElementNotPresent(Main)::Exit" );
    return result;
  }

  /**
   * This method pretends to assert the element is not present and return a
   * boolean = true if it isn't. This method also allow user to set a specify timeout.
   *
   * @param driver
   * @param locator
   * @param timeout
   */
  public static Boolean WaitForElementNotPresent( final WebDriver driver, final By locator, final Integer timeout ) {
    log.debug( "WaitForElementNotPresent::Enter" );
    Boolean NotPresent = false;
    log.debug( "Locator: " + locator.toString() );
    driver.manage().timeouts().implicitlyWait( 0, TimeUnit.SECONDS );

    try {

      class RunnableObject implements Runnable {

        private Boolean NotPresent;

        public RunnableObject( Boolean NotPresent ) {
          this.NotPresent = NotPresent;
        }

        public Boolean getValue() {
          return this.NotPresent;
        }

        @Override
        public void run() {
          Wait<WebDriver> wait = new FluentWait<WebDriver>( driver ).withTimeout( timeout, TimeUnit.SECONDS ).pollingEvery( 50, TimeUnit.MILLISECONDS );

          // Wait for element visible
          this.NotPresent = wait.until( new Function<WebDriver, Boolean>() {

            @Override
            public Boolean apply( WebDriver d ) {
              try {
                List<WebElement> listElem = d.findElements( locator );
                if ( listElem.size() == 0 ) {
                  return true;
                }
                return false;
              } catch ( StaleElementReferenceException sere ) {
                return true;
              }
            }
          } );
        }
      }

      RunnableObject r = new RunnableObject( NotPresent );

      ExecutorService executor = Executors.newSingleThreadExecutor();
      executor.submit( r ).get( timeout + 2, TimeUnit.SECONDS );
      executor.shutdown();
      NotPresent = r.getValue();
    } catch ( InterruptedException ie ) {
      log.warn( "Interrupted Exception" );
    } catch ( ExecutionException ee ) {
      if ( ee.getCause().getClass().getCanonicalName().equalsIgnoreCase( TimeoutException.class.getCanonicalName() ) ) {
        log.warn( "WebDriver timeout exceeded! Looking for: " + locator.toString() );
      } else {
        log.warn( "Execution Exception" );
      }
    } catch ( java.util.concurrent.TimeoutException cte ) {
      log.warn( "Thread timeout exceeded! Looking for: " + locator.toString() );
    } catch ( Exception e ) {
      log.error( "Exception" );
      log.catching( e );
    }

    driver.manage().timeouts().implicitlyWait( 30, TimeUnit.SECONDS );

    log.debug( "WaitForElementNotPresent::Exit" );
    return NotPresent;
  }

  /**
   * The method pretends to wait for an element reach the expected attribute
   * value. The default timeout is 30 seconds.
   *
   * @param driver
   * @param locator
   * @param attributeName
   * @param attributeValue - attribute value to wait.
   */
  public static void WaitForAttributeValue( final WebDriver driver, final By locator, final String attributeName,
      final String attributeValue ) {
    log.debug( "WaitForAttributeValue(Main)::Enter" );
    WaitForAttributeValue( driver, locator, attributeName, attributeValue, 30 );
    log.debug( "WaitForAttributeValue(Main)::Exit" );
  }

  /**
   * The method pretends to wait for an element reach the expected attribute
   * value, specifying a timeout.
   *
   * @param driver
   * @param locator
   * @param attributeName
   * @param attributeValue - attribute value to wait.
   */
  public static void WaitForAttributeValue( final WebDriver driver, final By locator, final String attributeName,
      final String attributeValue, final Integer timeout ) {
    log.debug( "WaitForAttributeValue::Enter" );
    log.debug( "Locator: " + locator.toString() );
    log.debug( "Attribute: " + attributeName );
    log.debug( "AttributeValue: " + attributeValue );
    driver.manage().timeouts().implicitlyWait( 0, TimeUnit.SECONDS );

    try {

      class RunnableObject implements Runnable {

        @Override
        public void run() {
          Wait<WebDriver> wait = new FluentWait<WebDriver>( driver ).withTimeout( timeout, TimeUnit.SECONDS ).pollingEvery( 50, TimeUnit.MILLISECONDS );

          // Wait for element visible
          wait.until( new Function<WebDriver, Boolean>() {

            @Override
            public Boolean apply( WebDriver d ) {
              try {
                List<WebElement> listElements = d.findElements( locator );
                if ( listElements.size() > 0 ) {
                  WebElement element = listElements.get( 0 );
                  String attrValue = element.getAttribute( attributeName ).toLowerCase();
                  String attrValueFor = attributeValue.toLowerCase();
                  return attrValue.contains( attrValueFor );
                }
                return false;
              } catch ( StaleElementReferenceException sere ) {
                return true;
              }
            }
          } );
        }
      }

      RunnableObject r = new RunnableObject();

      ExecutorService executor = Executors.newSingleThreadExecutor();
      executor.submit( r ).get( timeout + 2, TimeUnit.SECONDS );
      executor.shutdown();
    } catch ( InterruptedException ie ) {
      log.warn( "Interrupted Exception" );
    } catch ( ExecutionException ee ) {
      if ( ee.getCause().getClass().getCanonicalName().equalsIgnoreCase( TimeoutException.class.getCanonicalName() ) ) {
        log.warn( "WebDriver timeout exceeded! Looking for: " + locator.toString() );
      } else {
        log.warn( "Execution Exception" );
      }
    } catch ( java.util.concurrent.TimeoutException cte ) {
      log.warn( "Thread timeout exceeded! Looking for: " + locator.toString() );
    } catch ( Exception e ) {
      log.error( "Exception" );
      log.catching( e );
    }

    driver.manage().timeouts().implicitlyWait( 30, TimeUnit.SECONDS );

    log.debug( "WaitForAttributeValue::Exit" );
  }

  /**
   * The method pretends to wait for an element reach the expected attribute
   * value. The default timeout is 30 seconds.
   *
   * @param driver
   * @param locator
   * @param attributeName
   * @param attributeValue - attribute value to wait.
   */
  public static void WaitForAttributeValueEqualsTo( final WebDriver driver, final By locator,
      final String attributeName, final String attributeValue ) {
    log.debug( "WaitForAttributeValue(Main)::Enter" );
    WaitForAttributeValueEqualsTo( driver, locator, attributeName, attributeValue, 30 );
    log.debug( "WaitForAttributeValue(Main)::Exit" );
  }

  /**
   * The method pretends to wait for an element reach the expected attribute
   * value, specifying a timeout.
   *
   * @param driver
   * @param locator
   * @param attributeName
   * @param attributeValue - attribute value to wait.
   */
  public static void WaitForAttributeValueEqualsTo( final WebDriver driver, final By locator,
      final String attributeName, final String attributeValue, final Integer timeout ) {
    log.debug( "WaitForAttributeValue::Enter" );
    log.debug( "Locator: " + locator.toString() );
    log.debug( "Attribute: " + attributeName );
    log.debug( "AttributeValue: " + attributeValue );
    driver.manage().timeouts().implicitlyWait( 0, TimeUnit.SECONDS );

    try {

      class RunnableObject implements Runnable {

        @Override
        public void run() {
          Wait<WebDriver> wait = new FluentWait<WebDriver>( driver ).withTimeout( timeout, TimeUnit.SECONDS ).pollingEvery( 50, TimeUnit.MILLISECONDS );

          // Wait for element visible
          wait.until( new Function<WebDriver, Boolean>() {

            @Override
            public Boolean apply( WebDriver d ) {
              try {
                List<WebElement> listElements = d.findElements( locator );
                if ( listElements.size() > 0 ) {
                  WebElement element = listElements.get( 0 );
                  String attrValue = element.getAttribute( attributeName ).toLowerCase();
                  String attrValueFor = attributeValue.toLowerCase();
                  return attrValue.equals( attrValueFor );
                }
                return false;
              } catch ( StaleElementReferenceException sere ) {
                return true;
              }
            }
          } );
        }
      }

      RunnableObject r = new RunnableObject();

      ExecutorService executor = Executors.newSingleThreadExecutor();
      executor.submit( r ).get( timeout + 2, TimeUnit.SECONDS );
      executor.shutdown();
    } catch ( InterruptedException ie ) {
      log.warn( "Interrupted Exception" );
    } catch ( ExecutionException ee ) {
      if ( ee.getCause().getClass().getCanonicalName().equalsIgnoreCase( TimeoutException.class.getCanonicalName() ) ) {
        log.warn( "WebDriver timeout exceeded! Looking for: " + locator.toString() );
      } else {
        log.warn( "Execution Exception" );
      }
    } catch ( java.util.concurrent.TimeoutException cte ) {
      log.warn( "Thread timeout exceeded! Looking for: " + locator.toString() );
    } catch ( Exception e ) {
      log.error( "Exception" );
      log.catching( e );
    }

    driver.manage().timeouts().implicitlyWait( 30, TimeUnit.SECONDS );

    log.debug( "WaitForAttributeValue::Exit" );
  }

  /**
   * This method shall focus on the element and then add the text.
   * 
   * @param driver
   * @param locator
   * @param text
   */
  public static void ClickAndSendKeys( final WebDriver driver, final By locator, final CharSequence... keysToSend ) {
    log.debug( "SendKeys::Enter" );
    log.debug( "Locator: " + locator.toString() );

    try {
      WebElement element = WaitForElementPresenceAndVisible( driver, locator );
      if ( element != null ) {
        element.click();
        element.sendKeys( keysToSend );
      } else {
        log.error( "Element is null!" );
      }
    } catch ( StaleElementReferenceException e ) {
      log.warn( "Stale Element Reference Exception" );
      ClickAndSendKeys( driver, locator, keysToSend );
    }
    log.debug( "SendKeys::Exit" );
  }

  /**
   * This method find the element and sendkeys.
   * 
   * @param driver
   * @param locator
   * @param text
   */
  public static void SendKeys( final WebDriver driver, final By locator, final CharSequence... keysToSend ) {
    log.debug( "SendKeys::Enter" );
    log.debug( "Locator: " + locator.toString() );

    try {
      WebElement element = WaitForElementPresenceAndVisible( driver, locator );
      if ( element != null ) {
        element.sendKeys( keysToSend );
      } else {
        log.error( "Element is null!" );
      }
    } catch ( StaleElementReferenceException e ) {
      log.warn( "Stale Element Reference Exception" );
      ClickAndSendKeys( driver, locator, keysToSend );
    }
    log.debug( "SendKeys::Exit" );
  }
}
