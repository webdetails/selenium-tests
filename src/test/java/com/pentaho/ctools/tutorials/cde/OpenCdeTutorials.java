package com.pentaho.ctools.tutorials.cde;

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

import static org.testng.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/** 
 * Testing the functionalities related with CDE Tutorials.
 *
 * Naming convention for test: 'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class OpenCdeTutorials extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( OpenCdeTutorials.class );

  /**
   * ############################### Test Case 0 ###############################
   *
   * Description: Opens CDE Tutorials in Welcome page.
   */
  @Test
  public void tc0_open() {
    this.log.info( "tc0_open" );

    // wait for invisibility of waiting pop-up
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

    // Open CDE Tutorials Welcome page
    this.elemHelper.Get( driver, PageUrl.CDE_TUTORIALS_WELCOME );

    // Test if page was loaded
    assertEquals( "Welcome", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//*[@id='mainContent']/h1" ) ) );
  }
}
