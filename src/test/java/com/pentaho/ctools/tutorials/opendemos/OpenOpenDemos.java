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


package com.pentaho.ctools.tutorials.opendemos;

import static org.testng.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

public class OpenOpenDemos extends BaseTest {
  // Access to wrapper for webdriver
  private static final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private static final Logger log = LogManager.getLogger( OpenOpenDemos.class );

  /**
   * Open Open Demos Homepage.
   */
  @Test
  public void tc0_open() {
    log.info( "tc0_apen" );

    // wait for invisibility of waiting pop-up
    elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

    // Open CDE Tutorials Welcome page
    elemHelper.Get( driver, PageUrl.OPEN_DEMOS );

    // Test if page was loaded
    assertEquals( "OPEN DEMOS", elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//p[@class='title']" ) ) );

    return;
  }
}
