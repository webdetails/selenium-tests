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

package com.pentaho.ctools.cgg;

import static org.testng.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with CGG - Dial Chart.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class DialChart extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( DialChart.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Dial  Chart
   * Description:
   *    We pretend to check if a image is display with dial in a chart.
   * Steps:
   *    1. Open the dial chart.
   */
  @Test
  public void tc1_DialChart_ImageRendered() {
    this.log.info( "tc1_DialChart_ImageRendered" );
    /*
     * ## Step 1
     */
    this.elemHelper.Get( driver, PageUrl.DIAL_CHART );

    //Check Pointer
    WebElement elemPointer = this.elemHelper.FindElement( driver, By.xpath( "//*[local-name()='g'][6]/*[local-name()='polygon'][@id='ponteiro' and @transform='rotate(62.99999999999999,300,275)']" ) );
    assertNotNull( elemPointer );
    WebElement basePointers = this.elemHelper.FindElement( driver, By.xpath( "//*[local-name()='g'][6]/*[local-name()='path'][@id='base_ponteiro']" ) );
    assertNotNull( basePointers );

    //Check Colors
    WebElement elemColor1 = this.elemHelper.FindElement( driver, By.xpath( "//*[local-name()='g'][6]/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='path'][@fill='rgb(255,0,0)']" ) );
    assertNotNull( elemColor1 );
    WebElement elemColor2 = this.elemHelper.FindElement( driver, By.xpath( "//*[local-name()='g'][6]/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='path'][2][@fill='rgb(255,255,0)']" ) );
    assertNotNull( elemColor2 );
    WebElement elemColor3 = this.elemHelper.FindElement( driver, By.xpath( "//*[local-name()='g'][6]/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='path'][3][@fill='rgb(0,128,0)']" ) );
    assertNotNull( elemColor3 );
  }
}
