/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2002 - 2026 by Pentaho Canada Inc. : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2030-06-15
 ******************************************************************************/

package com.pentaho.ctools.tutorials.cde;

import static org.testng.Assert.assertEquals;

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

public class CreatingComponents extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CreatingComponents.class );
  // Instance to access CDE Tutorial
  private final CdeTutorials cdetutorial = new CdeTutorials();

  /**
   * ############################### Setup ###############################
   *
   * Description:
   *    Open Creating Components Page
   */
  @BeforeClass
  public void openCreatingComponentsPage() {
    this.log.info( "openCreatingComponentsPage" );

    //Wait for the loading icon to disappear
    elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    this.elemHelper.Click( driver, By.xpath( "//*[@id='sideMenu']/ul/a[18]/li" ) );

    assertEquals( "Creating Components", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id= 'mainContent']/h1" ) ) );

    return;
  }

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Text links
   *    
   * Test Case Description:
   *    Click on all links in the text and check the pages are loaded. 
   * 
   * Test Steps:
   *    1. Check Weather.com link.
   *    2. Check Weather in Lisbon link.
   */
  //@Test
  public void tc0_TextLinks_Displayed() {
    this.log.info( "tc0_TextLinks_Displayed" );

    List<WebElement> elements = this.elemHelper.FindElements(driver,  By.xpath( "//p/a[contains(text(),'www.weather.com')]" ) );

    this.cdetutorial.clickAndCheckPageLoaded( elements.get( 0 ), By.xpath( "//title" ), "The Weather Channel - National and Local Weather Forecast, Severe Storms, Hurricanes, Cyclones, Radar and Report from weather.com" );

    this.cdetutorial.clickAndCheckPageLoaded( elements.get( 1 ), By.xpath( ".//h1[contains(text(),'Lisbon')]" ), "Lisbon, Portugal" );

    return;
  }

  /**
   * ############################### Test Case 1 ###############################
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
  public void tc1_PreviewAndEditLinks_Displayed() {
    this.log.info( "tc1_PreviewAndEditLinks_Displayed" );

    //Multiple Preview/Edit buttons 
    List<WebElement> editLinks = this.elemHelper.FindElements(driver,  By.xpath( "//a[contains(text(),'Edit')]" ) );
    List<WebElement> previewLinks = this.elemHelper.FindElements(driver,  By.xpath( "//a[contains(text(),'Preview')]" ) );

    //Iterators for the Edit & Preview links Lists
    Iterator<WebElement> editLink = editLinks.iterator();
    Iterator<WebElement> previewLink = previewLinks.iterator();

    while ( editLink.hasNext() && previewLink.hasNext() ) {
      //Assert Edit Dashboard link is present, page loads successfully and gets the name of the CDE Doc
      String docName = this.cdetutorial.getCdeDocName( editLink.next() );

      //Preview links is present and page is loaded 
      this.cdetutorial.clickAndCheckPageLoaded( previewLink.next(), By.xpath( "//title" ), docName );
    }
  }

}
