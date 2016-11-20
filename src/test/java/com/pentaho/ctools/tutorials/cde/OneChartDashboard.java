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
 * Testing the functionalities related with CDE Tutorials - A One-Chart Dashboard.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */

public class OneChartDashboard extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( OneChartDashboard.class );
  // Instance to access CDE Tutorial
  private final CdeTutorials cdetutorial = new CdeTutorials();

  WebElement h1, h2, h3, h4;

  /**
   * ############################### Setup ###############################
   *
   * Description:
   *    Open A One-Chart Dashboard Page
   */
  @BeforeClass
  public void openOneChartDashboardPage() {
    this.log.info( "openOneChartDashboardPage" );

    //Wait for the loading icon to disappear
    elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    this.elemHelper.Click( driver, By.xpath( "//*[@id='sideMenu']/ul/a[4]/li" ) );

    assertEquals( "A One-Chart Dashboard", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id= 'mainContent']/h1" ) ) );
  }

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    One-Chart Dashboard Page Sections
   *    
   * Test Case Description:
   *    Check sections in One-Chart Dashboard page.
   * 
   * Test Steps:
   *    1. Check if headings are present and correctly displayed;
   */
  @Test
  public void tc0_OneChartDashboardPageSections_Displayed() {
    this.log.info( "tc0_OneChartDashboardPageSections_Displayed" );

    //Heading #1
    this.h1 = this.elemHelper.WaitForElementPresence( driver, By.xpath( "//*[@id='headingOne']/a/h4" ) );
    assertNotNull( this.h1 );

    //Heading #2
    this.h2 = this.elemHelper.WaitForElementPresence( driver, By.xpath( "//*[@id='headingTwo']/a/h4" ) );
    assertNotNull( this.h2 );

    //Heading #3 
    this.h3 = this.elemHelper.WaitForElementPresence( driver, By.xpath( "//*[@id='headingThree']/a/h4" ) );
    assertNotNull( this.h3 );

    //Heading #4
    this.h4 = this.elemHelper.WaitForElementPresence( driver, By.xpath( "//*[@id='headingFour']/a/h4" ) );
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

    //Wait for the loading icon to disappear
    elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Step #1 - Expand Headers
    this.h1.click();
    this.h2.click();
    this.h3.click();
    this.h4.click();

    //Step #2 - Assert all images are present and if they have a valid src (url - 200 OK) 
    this.cdetutorial.checkImagesPresenceAndHttpStatus( "collapseOne" );
    this.cdetutorial.checkImagesPresenceAndHttpStatus( "collapseTwo" );
    this.cdetutorial.checkImagesPresenceAndHttpStatus( "collapseThree" );
    this.cdetutorial.checkImagesPresenceAndHttpStatus( "collapseFour" );

    //Collapse Headers
    this.h1.click();
    this.h2.click();
    this.h3.click();
    this.h4.click();
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

    //Assert Edit Dashboard link is present, page loads successfuly and gets the name of the CDE Doc
    String docName = this.cdetutorial.getCdeDocName( By.xpath( "//a[contains(text(),'Edit')]" ) );

    //Preview link is present and page is loaded
    this.cdetutorial.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'Preview')]" ), By.xpath( "//title" ), docName );
  }
}
