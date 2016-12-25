/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2016 by Pentaho : http://www.pentaho.com
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
package com.pentaho.gui.web.ctools.tutorials.cde;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.List;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.HttpUtils;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with CDE Tutorials.
 *
 * Naming convention for test: 'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CdeTutorials extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CdeTutorials.class );

  /**
   * Description: Click on a link and check the page was loaded by confirming a presence of some text.
   * 
   * Parameters: linkLocator (By): Locator of the link where user wants to click; textLocator (By): Locator of the text
   * the driver needs to find in order to confirm the page was loaded; elementText (String): Text the driver needs to
   * find in order to confirm the page was loaded.
   * 
   * Returns: nothing.
   */
  public void clickAndCheckPageLoaded( By linkLocator, By textLocator, String elementText ) {
    this.log.info( "clickAndCheckPageLoaded" );

    // Store the current window handle
    String winHandleBefore = driver.getWindowHandle();

    // Wait for the loading icon to disappear
    elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    // Click given link
    this.elemHelper.Click( driver, linkLocator );

    // Switch to new window opened
    for ( String winHandle : driver.getWindowHandles() ) {
      driver.switchTo().window( winHandle );
    }

    // Confirm page was loaded
    assertEquals( this.elemHelper.WaitForElementPresentGetText( driver, textLocator ), elementText );

    // Close the new window, if that window no more required
    driver.close();

    // Switch back to original browser (first window)
    driver.switchTo().window( winHandleBefore );
  }

  /**
   * Description: Click on a link and check the page was loaded by confirming a presence of some text.
   * 
   * Parameters: element (WebElement): element where user wants to click; textLocator (By): Locator of the text the
   * driver needs to find in order to confirm the page was loaded; elementText (String): Text the driver needs to find
   * in order to confirm the page was loaded.
   * 
   * Returns: nothing.
   */
  public void clickAndCheckPageLoaded( WebElement element, By textLocator, String elementText ) {
    this.log.info( "clickAndCheckPageLoaded" );

    // Store the current window handle
    String winHandleBefore = driver.getWindowHandle();

    // Click given link
    element.click();

    // Switch to new window opened
    for ( String winHandle : driver.getWindowHandles() ) {
      driver.switchTo().window( winHandle );
    }

    // Confirm page was loaded
    assertEquals( this.elemHelper.WaitForElementPresentGetText( driver, textLocator ), elementText );

    // Close the new window, if that window no more required
    driver.close();

    // Switch back to original browser (first window)
    driver.switchTo().window( winHandleBefore );
  }

  /**
   * Description: Click on a link and check the page was loaded by confirming a presence of some text. Gets the CDE
   * Document name and returns it.
   * 
   * Parameters: linkLocator (By): Locator of the link where user wants to click; textLocator (By): Locator of the text
   * the driver needs to find in order to confirm the page was loaded; elementText (String): Text the driver needs to
   * find in order to confirm the page was loaded.
   * 
   * Returns: docName (String): Name of the CDE document.
   */
  public String getCdeDocName( By linkLocator ) {
    this.log.info( "clickAndCheckPageLoaded" );

    // Store the current window handle
    String winHandleBefore = driver.getWindowHandle();

    // Click given link
    this.elemHelper.Click( driver, linkLocator );

    // Switch to new window opened
    for ( String winHandle : driver.getWindowHandles() ) {
      driver.switchTo().window( winHandle );
    }

    // Get CDE Document Name
    String docName = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//*[@class='cdfdd-title']" ) );

    // Confirm page was loaded
    assertEquals( this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//title[contains(text(),'Webdetails CDE')]" ) ), "Webdetails CDE" );

    // Close the new window, if that window no more required
    driver.close();

    // Switch back to original browser (first window)
    driver.switchTo().window( winHandleBefore );

    return docName;
  }

  /**
   * Description: Click on a link and check the page was loaded by confirming a presence of some text. Gets the CDE
   * Document name and returns it.
   * 
   * Parameters: element (WebElement): WebElement where the user wants to click; textLocator (By): Locator of the text
   * the driver needs to find in order to confirm the page was loaded; elementText (String): Text the driver needs to
   * find in order to confirm the page was loaded.
   * 
   * Returns: docName (String): Name of the CDE document.
   */
  public String getCdeDocName( WebElement element ) {
    this.log.info( "clickAndCheckPageLoaded" );

    // Store the current window handle
    String winHandleBefore = driver.getWindowHandle();

    // Wait for the loading icon to disappear
    elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    // Click given link
    element.click();

    // Switch to new window opened
    for ( String winHandle : driver.getWindowHandles() ) {
      driver.switchTo().window( winHandle );
    }

    // Get CDE Document Name
    String docName = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//*[@class='cdfdd-title']" ) );

    // Confirm page was loaded
    assertEquals( this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//title[contains(text(),'Webdetails CDE')]" ) ), "Webdetails CDE" );

    // Close the new window, if that window no more required
    driver.close();

    // Switch back to original browser (first window)
    driver.switchTo().window( winHandleBefore );

    return docName;
  }

  /**
   * Description: Assert if images are present and if get requests with the images src as url returns HTTP Status 200
   * OK.
   * 
   * Parameters: parentId - Web element containing the images with id = parentID
   * 
   * Returns: nothing.
   */
  public void checkImagesPresenceAndHttpStatus( String parentId ) {
    // Builds the Locator to find all the images under the web element with id = parentID
    String xpathLocator = String.format( "//*[@id='%s']//img", parentId );

    // Locates every image under the parentId web element
    List<WebElement> img = driver.findElements( By.xpath( xpathLocator ) );

    // Test if images were found
    assertNotNull( img );

    // Iterates through every image
    for ( WebElement we : img ) {
      // Assert if get request returns HTTP Status 200 OK. URL = image src attribute
      assertEquals( HttpUtils.GetHttpStatus( we.getAttribute( "src" ), pentahoBaServerUsername, pentahoBaServerPassword ), HttpStatus.SC_OK );
    }
  }

}
