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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.gui.web.ctools.tutorials.cde.CdeTutorials;
import com.pentaho.selenium.BaseTest;

public class TheEditor extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( TheEditor.class );
  // Instance to access CDE Tutorial
  private final CdeTutorials cdetutorial = new CdeTutorials();

  /**
   * ############################### Setup ###############################
   *
   * Description:
   *    Open CDA: The Editor Page
   */
  @BeforeClass
  public void openDatasourcesPage() {
    this.log.info( "openTheEditorPage" );

    //Wait for the loading icon to disappear
    elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    this.elemHelper.Click( driver, By.xpath( "//*[@id='sideMenu']/ul/a[14]/li" ) );

    assertEquals( "CDA: The Editor", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id= 'mainContent']/h1" ) ) );
  }

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Check Images
   *    
   * Test Case Description:
   *    Check if printscreens are present. 
   * 
   * Test Steps:
   *    1. Assert if images are present;
   *    2. Check images URL returns HTTP Status OK (200).
   */
  @Test
  public void tc0_CheckImages_Displayed() {
    this.log.info( "tc0_CheckImages_Displayed" );

    //Step #2 - Assert all images are present and if they have a valid src (url - 200 OK) 
    this.cdetutorial.checkImagesPresenceAndHttpStatus( "mainContent" );
  }
}
