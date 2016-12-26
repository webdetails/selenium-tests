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
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.gui.web.ctools.tutorials.cde.CdeTutorials;
import com.pentaho.selenium.BaseTest;

public class WhereToGo extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( WhereToGo.class );
  // Instance to access CDE Tutorial
  private final CdeTutorials cdetutorial = new CdeTutorials();

  //Headings of the page
  WebElement[] headings = new WebElement[5];

  /**
   * ############################### Setup ###############################
   *
   * Description:
   *    Open Where To Go From Here Page
   */
  @BeforeClass
  public void openWhereToGoPage() {
    this.log.info( "openWhereToGotPage" );

    this.elemHelper.Click( driver, By.xpath( "//*[@id='sideMenu']/ul/a[20]/li" ) );

    assertEquals( "Where to Go From Here", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id= 'mainContent']/h1" ) ) );

    this.headings[0] = this.elemHelper.FindElement( driver, By.xpath( "//*[@id='headingOne']/a" ) );
    this.headings[1] = this.elemHelper.FindElement( driver, By.xpath( "//*[@id='headingTwo']/a" ) );
    this.headings[2] = this.elemHelper.FindElement( driver, By.xpath( "//*[@id='headingThree']/a" ) );
    this.headings[3] = this.elemHelper.FindElement( driver, By.xpath( "//*[@id='headingFour']/a" ) );
    this.headings[4] = this.elemHelper.FindElement( driver, By.xpath( "//*[@id='headingFive']/a" ) );
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

    //Step #1
    //Expand 1st header
    this.headings[0].click();

    //Check link and if pages are loaded for every C-Tool
    for ( int i = 0; i < ctools.length; i++ ) {
      String ctoolLocator = String.format( "//ul[@class='ctools-suite']/li[%d]/a", ( i + 1 ) );

      //Step #2
      this.cdetutorial.clickAndCheckPageLoaded( By.xpath( ctoolLocator ), By.xpath( "//h1[contains(@class,'ctool-name')]" ), ctools[i] );
    }

    //Collapse 1st header
    this.headings[0].click();
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
   *    1. Expand headers;
   *    2. Check all links;
   *    3. Collapse headers.
   */
  @Test
  public void tc2_OtherLinks_Displayed() {
    this.log.info( "tc2_TextLinks_Displayed" );

    //Expand headers
    for ( WebElement h : this.headings ) {
      h.click();
    }

    this.cdetutorial.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'HTML')]" ), By.xpath( "//title" ), "HTML Tutorial" );

    this.cdetutorial.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'CSS')]" ), By.xpath( "//title" ), "CSS Tutorial" );

    this.cdetutorial.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'jQuery')]" ), By.xpath( "//title" ), "jQuery" );

    this.cdetutorial.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'JavaScript (JS)')]" ), By.xpath( "//title" ), "JavaScript and HTML DOM Reference" );

    this.cdetutorial.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'Bootstrap')]" ), By.xpath( "//title" ), "Bootstrap · The world's most popular mobile-first and responsive front-end framework." );

    this.cdetutorial.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'Protovis')]" ), By.xpath( "//title" ), "Protovis" );

    this.cdetutorial.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'Datatables')]" ), By.xpath( "//title" ), "DataTables | Table plug-in for jQuery" );

    this.cdetutorial.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'Sparklines')]" ), By.xpath( "//title" ), "jQuery Sparklines" );

    this.cdetutorial.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'Chosen')]" ), By.xpath( "//title" ), "Chosen: A jQuery Plugin by Harvest to Tame Unwieldy Select Boxes" );

    this.cdetutorial.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'Select2')]" ), By.xpath( "//title" ), "Select2 - The jQuery replacement for select boxes" );

    this.cdetutorial.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'Facebook page')]" ), By.xpath( "//title" ), "Webdetails | Facebook" );

    this.cdetutorial.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'Pedro Alves Blog')]" ), By.xpath( "//title" ), "Pedro Alves on Business Intelligence" );

    this.cdetutorial.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'Community Tools - CTools')]" ), By.xpath( "//title" ), "Community Tools - CTools" );

    this.cdetutorial.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'here')]" ), By.xpath( "//title" ), "Webdetails • Business Analytics" );

    //Collapse headers
    for ( WebElement h : this.headings ) {
      h.click();
    }
  }
}
