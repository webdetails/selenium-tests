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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.gui.web.ctools.tutorials.cde.CdeTutorials;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with CDE Tutorials - Welcome Page.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */

public class Welcome extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  //Log instance
  private final Logger log = LogManager.getLogger( Welcome.class );
  // Instance to access CDE Tutorial
  private final CdeTutorials cdetutorial = new CdeTutorials();

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Welcome Page Sections
   *    
   * Test Case Description:
   *    Check sections in Welcome page.
   * 
   * Test Steps:
   *    1. Open Welcome page.
   *    2. Check if headings are present and correctly displayed.
   */
  @Test
  public void tc0_WelcomePageSections_Displayed() {
    this.log.info( "tc0_WelcomePageSecions_Displayed" );

    //Wait for the loading icon to disappear
    elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) ); 
    
    //Step #1
    //Click on Welcome sidebar link
    this.elemHelper.Click( driver, By.xpath( "//*[@id='sideMenu']/ul/a[1]/li" ) );

    //Wait for page title to appear and assert if the titles of the pages are correctly displayed
    assertEquals( "Welcome", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id= 'mainContent']/h1" ) ) );

    //Step #2
    //Check "The CTools Family" Heading
    assertEquals( "The CTools Family", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//*[@id='headingOne']/a/h4" ) ) );

    //Check "What is a CDE Dashboard?" Heading
    assertEquals( "What is a CDE Dashboard?", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//*[@id='headingTwo']/a/h4" ) ) );
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    CTools link list
   *    
   * Test Case Description:
   *    Click on all CTools links in CTools list and check the pages are loaded. 
   * 
   * Test Steps:
   *    1. Check C-tools links.
   *    2. Check if C-tools pages are loaded.
   */
  @Test
  public void tc1_CtoolsLinkList_Displayed() {
    this.log.info( "tc1_WelcomePageSecions_Displayed" );

    String[] ctools = { "CDF",
                        "CDE",
                        "CDA",
                        "CCC",
                        "CGG",
                        "CDV",
                        "CDC",
                        "CST",
                        "CBF" };

    //Wait for the loading icon to disappear
    elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) ); 
    
    //Step #1
    //Expand first header
    this.elemHelper.Click( driver, By.xpath( "//*[@id='headingOne']/a/h4" ) );

    //Check link and if pages are loaded for every C-Tool
    for ( int i = 0; i < ctools.length; i++ ) {
      String ctoolLocator = String.format( "//ul[@class='ctools-suite']/li[%d]/a", ( i + 1 ) );

      //Step #2
      this.cdetutorial.clickAndCheckPageLoaded( By.xpath( ctoolLocator ), By.xpath( "//h1[contains(@class,'ctool-name')]" ), ctools[i] );
    }

    //Collapse first header
    this.elemHelper.Click( driver, By.xpath( "//*[@id='headingOne']/a/h4" ) );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Text links
   *    
   * Test Case Description:
   *    Click on all links in the text and check the pages are loaded. 
   * 
   * Test Steps:
   *    1. Check CDE link;
   *    2. Check CDA link;
   *    3. Check CCC link;
   *    4. Check CDF link;
   *    5. Check C-Tools Forum link;
   *    6. Check HTML link;
   *    7. Check CSS link;
   *    8. Check JQuery link;
   */
  @Test
  public void tc2_TextLinks_Displayed() {
    this.log.info( "tc2_TextLinks_Displayed" );

    //Expand second header
    this.elemHelper.Click( driver, By.xpath( "//*[@id='headingTwo']/a/h4" ) );
    //Expand first header
    this.elemHelper.Click( driver, By.xpath( "//*[@id='headingOne']/a/h4" ) );

    //Step #1 - Check CDE link
    this.cdetutorial.clickAndCheckPageLoaded( By.xpath( "//p/a[contains(text(),'CDE')]" ), By.xpath( "//h1[contains(text(),'CDE')]" ), "CDE" );

    //Step #2 - Check CDA link
    this.cdetutorial.clickAndCheckPageLoaded( By.xpath( "//p/a[contains(text(),'CDA')]" ), By.xpath( "//h1[contains(text(),'CDA')]" ), "CDA" );

    //Step #3 - Check CCC link
    this.cdetutorial.clickAndCheckPageLoaded( By.xpath( "//p/a[contains(text(),'CCC')]" ), By.xpath( "//h1[contains(text(),'CCC')]" ), "CCC" );

    //Step #4 - Check CDF link
    this.cdetutorial.clickAndCheckPageLoaded( By.xpath( "//p/a[contains(text(),'CDF')]" ), By.xpath( "//h1[contains(text(),'CDF')]" ), "CDF" );

    //Step #5 - Check C-Tools Forum link
    this.cdetutorial.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'CTools Forum')]" ), By.xpath( "//*[@id='pagetitle']/h1/span" ), "Community Tools - CTools" );

    //Step #6 - Check HTML link
    this.cdetutorial.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'HTML')]" ), By.xpath( "//title" ), "HTML Tutorial" );

    //Step #7 - Check CSS link
    this.cdetutorial.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'CSS')]" ), By.xpath( "//title" ), "CSS Tutorial" );

    //Step #8 - Check JQuery link
    this.cdetutorial.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'jQuery')]" ), By.xpath( "//title" ), "jQuery" );

    //Collapse first header
    this.elemHelper.Click( driver, By.xpath( "//*[@id='headingOne']/a/h4" ) );
    //Collapse second header
    this.elemHelper.Click( driver, By.xpath( "//*[@id='headingTwo']/a/h4" ) );
  }

}
