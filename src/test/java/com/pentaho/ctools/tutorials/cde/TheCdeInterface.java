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
 * Testing the functionalities related with CDE Tutorials - The CDE Inteface.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */

public class TheCdeInterface extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  //Log instance
  private final Logger log = LogManager.getLogger( TheCdeInterface.class );
  // Instance to access CDE Tutorial
  private final CdeTutorials cdetutorial = new CdeTutorials();

  WebElement h1, h2, h3;

  /**
   * ############################### Setup ###############################
   *
   * Description:
   *    Open The CDE Interface Page
   */
  @BeforeClass
  public void openTheCdeInterfacePage() {
    this.log.info( "openTheCdeInterfacePage" );

    //Wait for the loading icon to disappear
    elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    this.elemHelper.Click( driver, By.xpath( "//*[@id='sideMenu']/ul/a[2]/li" ) );

    assertEquals( "The CDE Interface", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id= 'mainContent']/h1" ) ) );

    return;
  }

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Interface Page Sections
   *    
   * Test Case Description:
   *    Check sections in The CDE Interface page.
   * 
   * Test Steps:
   *    1. Check if headings are present and correctly displayed;
   */
  @Test
  public void tc0_InterfacePageSections_Displayed() {
    this.log.info( "tc0_InterfacePageSections_Displayed" );

    //Heading #1 - The Layout Perspective
    this.h1 = this.elemHelper.WaitForElementPresence( driver, By.xpath( "//*[@id='headingOne']/a/h4" ) );
    assertNotNull( this.h1 );

    //Heading #2 - The Components Perspective
    this.h2 = this.elemHelper.WaitForElementPresence( driver, By.xpath( "//*[@id='headingTwo']/a/h4" ) );
    assertNotNull( this.h2 );

    //Heading #3 - The Datasources Perspective
    this.h3 = this.elemHelper.WaitForElementPresence( driver, By.xpath( "//*[@id='headingThree']/a/h4" ) );
    assertNotNull( this.h3 );

    //Layout, Components and Datasources
    assertNotNull( this.elemHelper.WaitForElementPresence( driver, By.xpath( "//*[@id='mainContent']/h2" ) ) );
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

    //Step #2 - Assert images are present
    this.cdetutorial.checkImagesPresenceAndHttpStatus( "mainContent" );
    this.cdetutorial.checkImagesPresenceAndHttpStatus( "collapseOne" );
    this.cdetutorial.checkImagesPresenceAndHttpStatus( "collapseTwo" );
    this.cdetutorial.checkImagesPresenceAndHttpStatus( "collapseThree" );

    //Collapse Headers
    this.h3.click();
    this.h1.click();
    this.h2.click();
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Check CDA Link
   *    
   * Test Case Description:
   *    Check if link is present and page is loaded. 
   * 
   * Test Steps:
   *    1. Click CDA link;
   *    2. Assert page is loaded.
   */
  @Test
  public void tc2_CheckCdaLink_Displayed() {
    this.log.info( "tc2_CheckCdaLink_Displayed" );

    //Wait for the loading icon to disappear
    elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Expand Datasources header
    this.h3.click();

    //Step #1 & #2
    this.cdetutorial.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'Community Data Access')]" ), By.xpath( "//h1[contains(text(),'CDA')]" ), "CDA" );
  }

}
