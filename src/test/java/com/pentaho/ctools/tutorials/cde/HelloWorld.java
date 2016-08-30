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
package com.pentaho.ctools.tutorials.cde;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.gui.web.ctools.tutorials.cde.CdeTutorials;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with CDE Tutorials - Hello World.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */

public class HelloWorld extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  //Log instance
  private final Logger log = LogManager.getLogger( HelloWorld.class );
  // Instance to access CDE Tutorial
  private final CdeTutorials cdetutorial = new CdeTutorials();

  WebElement h1, h2, h3;

  /**
   * ############################### Setup ###############################
   *
   * Description:
   *    Open Hello World Page
   */
  @BeforeClass
  public void openHelloWorldPage() {
    this.log.info( "openHelloWorldPage" );

    //Wait for the loading icon to disappear
    elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    this.elemHelper.Click( driver, By.xpath( "//*[@id='sideMenu']/ul/a[3]/li" ) );

    assertEquals( "Hello World", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id= 'mainContent']/h1" ) ) );
  }

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Hello World Page Sections
   *    
   * Test Case Description:
   *    Check sections in Hello World page.
   * 
   * Test Steps:
   *    1. Check if headings are present and correctly displayed;
   */
  @Test
  public void tc0_HelloWorldPageSections_Displayed() {
    this.log.info( "tc0_HelloWorldPageSections_Displayed" );

    //Heading #1 - 1. Create a new Dashboard
    this.h1 = this.elemHelper.WaitForElementPresence( driver, By.xpath( "//*[@id='headingOne']/a/h4" ) );
    assertNotNull( this.h1 );

    //Heading #2 - 2. Create Rows and Columns
    this.h2 = this.elemHelper.WaitForElementPresence( driver, By.xpath( "//*[@id='headingTwo']/a/h4" ) );
    assertNotNull( this.h2 );

    //Heading #3 - 3. Create a Text Component
    this.h3 = this.elemHelper.WaitForElementPresence( driver, By.xpath( "//*[@id='headingThree']/a/h4" ) );
    assertNotNull( this.h3 );
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Check Images
   *    
   * Test Case Description:
   *    Check if printscreens are present. 
   * 
   * Test Steps:
   *    1. Expand headers;
   *    2. Assert if images are present;
   *    3. Check images URL returns HTTP Status OK (200).
   */
  @Test
  public void tc1_CheckImages_Displayed() {
    this.log.info( "tc1_CheckImages_Displayed" );

    // Wait for the loading icon to disappear
    elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Step #1 - Expand Headers
    this.h1.click();
    this.h2.click();
    this.h3.click();

    //Step #2 - Assert all images are present and if they have a valid src (url - 200 OK) 
    this.cdetutorial.checkImagesPresenceAndHttpStatus( "collapseOne" );
    this.cdetutorial.checkImagesPresenceAndHttpStatus( "collapseTwo" );
    this.cdetutorial.checkImagesPresenceAndHttpStatus( "collapseThree" );

    //Collapse Headers
    this.h1.click();
    this.h2.click();
    this.h3.click();
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Preview and Edit Links.
   *    
   * Test Case Description:
   *    Check Preview Dashboard and Edit Dashboard links.
   * 
   * Test Steps:
   *    1. Assert if links are present;
   *    2. Check if pages are loaded. 
   */
  @Test
  public void tc2_PreviewAndEditLinks_Displayed() {
    this.log.info( "tc2_PreviewAndEditLinks_Displayed" );

    // Store the current window handle
    String winHandleBefore = driver.getWindowHandle();

    //Click given link
    this.elemHelper.Click( driver, By.xpath( "//a[contains(text(),'Edit')]" ) );

    // Switch to new window opened
    for ( String winHandle : driver.getWindowHandles() ) {
      driver.switchTo().window( winHandle );
    }

    String docName = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//*[@class='cdfdd-title']" ) );

    //Confirm page was loaded
    assertEquals( this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//title[contains(text(),'Webdetails CDE')]" ) ), "Webdetails CDE" );

    // Close the new window, if that window no more required
    driver.close();

    // Switch back to original browser (first window)
    driver.switchTo().window( winHandleBefore );

    this.cdetutorial.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'Preview')]" ), By.xpath( "//title" ), docName );
  }

}
