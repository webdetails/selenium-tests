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
 * Testing the functionalities related with CDE Tutorials - Adding External Resources.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */

public class AddingExternalResources extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( AddingExternalResources.class );
  // Instance to access CDF Tutorial page
  private final CdeTutorials cdetutorials = new CdeTutorials();

  WebElement h1, h2;

  /**
   * ############################### Setup ###############################
   *
   * Description:
   *    Open Making the Adding External Resources Page
   */
  @BeforeClass
  public void openAddingExternalResourcesPage() {
    this.log.info( "openAddingExternalResourcesPage" );

    //Wait for the loading icon to disappear
    elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    this.elemHelper.Click( driver, By.xpath( "//*[@id='sideMenu']/ul/a[9]/li" ) );

    assertEquals( "Adding External Resources", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id= 'mainContent']/h1" ) ) );

    return;
  }

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Adding External Resources Page Sections
   *    
   * Test Case Description:
   *    Check sections in Adding External Resources page.
   * 
   * Test Steps:
   *    1. Check if headings are present and correctly displayed;
   */
  @Test
  public void tc0_AddingExternalResourcesSections_Displayed() {
    this.log.info( "tc0_AddingExternalResourcesSections_Displayed" );

    //Heading #1
    this.h1 = this.elemHelper.WaitForElementPresence( driver, By.xpath( "//*[@id='headingOne']/a/h4" ) );
    assertNotNull( this.h1 );

    //Heading #2
    this.h2 = this.elemHelper.WaitForElementPresence( driver, By.xpath( "//*[@id='headingTwo']/a/h4" ) );
    assertNotNull( this.h2 );

    return;
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

    //Step #1 - Expand Headers
    this.h1.click();
    this.h2.click();

    //Step #2 - Assert all images are present and if they have a valid src (url - 200 OK) 
    this.cdetutorials.checkImagesPresenceAndHttpStatus( "collapseOne" );
    this.cdetutorials.checkImagesPresenceAndHttpStatus( "collapseTwo" );

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
    List<WebElement> editLinks = this.elemHelper.FindElements(driver,  By.xpath( "//a[contains(text(),'Edit')]" ) );
    List<WebElement> previewLinks = this.elemHelper.FindElements(driver,  By.xpath( "//a[contains(text(),'Preview')]" ) );

    //Iterators for the Edit & Preview links Lists
    Iterator<WebElement> editLink = editLinks.iterator();
    Iterator<WebElement> previewLink = previewLinks.iterator();

    while ( editLink.hasNext() && previewLink.hasNext() ) {
      //Assert Edit Dashboard link is present, page loads successfully and gets the name of the CDE Doc
      String docName = this.cdetutorials.getCdeDocName( editLink.next() );

      //Preview links is present and page is loaded 
      this.cdetutorials.clickAndCheckPageLoaded( previewLink.next(), By.xpath( "//title" ), docName );
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
   *    1. Check XDATE link.
   */
  @Test
  public void tc3_TextLinks_Displayed() {
    this.log.info( "tc3_TextLinks_Displayed" );

    //Expand second header
    this.elemHelper.Click( driver, By.xpath( "//*[@id='headingTwo']/a/h4" ) );

    //Step #1
    this.cdetutorials.clickAndCheckPageLoaded( By.xpath( "//p/a[contains(text(),'XDate JavaScript Library')]" ), By.xpath( "//title" ), "XDate - A Modern JavaScript Date Library" );
  }
}
