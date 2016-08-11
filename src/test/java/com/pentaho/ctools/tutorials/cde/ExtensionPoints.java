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

import java.util.Iterator;
import java.util.List;

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
 * Testing the functionalities related with CDE Tutorials - CCC: Extension Points.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */

public class ExtensionPoints extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  //Log instance
  private final Logger log = LogManager.getLogger( ExtensionPoints.class );
  // Instance to access CDE Tutorial
  private final CdeTutorials cdetutorial = new CdeTutorials();

  WebElement h1, h2;

  /**
   * ############################### Setup ###############################
   *
   * Description:
   *    Open Extension Points Page
   */
  @BeforeClass
  public void openExtensionPointsPage() {
    this.log.info( "openExtensionPointsPage" );

    this.elemHelper.Click( driver, By.xpath( "//*[@id='sideMenu']/ul/a[11]/li" ) );

    assertEquals( "CCC: Extension Points", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id= 'mainContent']/h1" ) ) );
  }

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Extension Points Page Sections
   *    
   * Test Case Description:
   *    Check sections in Extension Points page.
   * 
   * Test Steps:
   *    1. Check if headings are present and correctly displayed;
   */
  @Test
  public void tc0_ExtensionPointsSections_Displayed() {
    this.log.info( "tc0_ExtensionPointsSections_Displayed" );

    //Heading #1
    this.h1 = this.elemHelper.WaitForElementPresence( driver, By.xpath( "//*[@id='headingTwo']/a/h4" ) );
    assertNotNull( this.h1 );

    //Heading #2
    this.h2 = this.elemHelper.WaitForElementPresence( driver, By.xpath( "//*[@id='headingThree']/a/h4" ) );
    assertNotNull( this.h2 );
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

    /*
     * Step #1 - Expand Headers
     */
    this.h1.click();
    this.h2.click();

    /*
     * Step #2 - Assert all images are present and if they have a valid src (url - 200 OK) 
     */
    this.cdetutorial.checkImagesPresenceAndHttpStatus( "collapseOne" );
    this.cdetutorial.checkImagesPresenceAndHttpStatus( "collapseTwo" );

    //Collapse Headers
    this.h1.click();
    this.h2.click();
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

    //Multiple Preview/Edit buttons 
    List<WebElement> editLinks = driver.findElements( By.xpath( "//a[contains(text(),'Edit')]" ) );
    List<WebElement> previewLinks = driver.findElements( By.xpath( "//a[contains(text(),'Preview')]" ) );

    //Iterators for the Edit & Preview links Lists
    Iterator<WebElement> editLink = editLinks.iterator();
    Iterator<WebElement> previewLink = previewLinks.iterator();

    while ( editLink.hasNext() && previewLink.hasNext() ) {
      //Assert Edit Dashboard link is present, page loads successfuly and gets the name of the CDE Doc
      String docName = this.cdetutorial.getCdeDocName( editLink.next() );

      //Preview links is present and page is loaded 
      this.cdetutorial.clickAndCheckPageLoaded( previewLink.next(), By.xpath( "//title" ), docName );
    }
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Text links
   *    
   * Test Case Description:
   *    Click on all links in the text and check the pages are loaded. 
   * 
   * Test Steps:
   *    1. Check Protovis link;
   *    2. Check CCC link;
   */
  @Test
  public void tc3_TextLinks_Displayed() {
    this.log.info( "tc3_TextLinks_Displayed" );

    //Expand headers
    this.h1.click();
    this.h2.click();

    List<WebElement> links = driver.findElements( By.xpath( "//a[contains(text(),'Label')]" ) );

    /*
     * Step #1
     */
    this.cdetutorial.clickAndCheckPageLoaded( By.xpath( "//p/a[contains(text(),'Protovis')]" ), By.xpath( "//title" ), "Protovis" );
    /*
     * Step #2
     */
    this.cdetutorial.clickAndCheckPageLoaded( By.xpath( "//p/a[contains(text(),'CCC')]" ), By.xpath( "//h1[contains(text(),'CCC')]" ), "CCC" );
    /*
     * Step #3
     */
    this.cdetutorial.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'radians')]" ), By.xpath( "//title" ), "Radian - Wikipedia, the free encyclopedia" );

    for ( WebElement link : links ) {
      this.cdetutorial.clickAndCheckPageLoaded( link, By.xpath( "//title" ), "Protovis - Labels" );
    }

    //Collapse headers
    this.h1.click();
    this.h2.click();
  }
}
