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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with Login.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class BarChart extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( BarChart.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Bar Chart
   *
   * Description:
   *    We pretend to check if an image is displayed with bares in a chart.
   *
   * Steps:
   *    1. Open the bar chart.
   */
  @Test
  public void tc1_BarChartViz2_PngRendered() {
    this.log.info( "tc1_BarChartViz2_PngRendered" );

    /*
     * ## Step 1
     */
    this.elemHelper.Get( BaseTest.driver, PageUrl.BAR_CHART_VIZ2 );

    final WebElement elementImage = this.elemHelper.FindElement( BaseTest.driver, By.cssSelector( "img" ), 5 );
    Assert.assertNotNull( elementImage );

    final String attrWidth = elementImage.getAttribute( "width" );
    Assert.assertFalse( attrWidth.isEmpty() );
    final String attrHeight = elementImage.getAttribute( "height" );
    Assert.assertFalse( attrHeight.isEmpty() );
    Assert.assertTrue( ( Integer.parseInt( attrWidth ) > 800 ) ? true : false, "the number " + attrWidth );
    Assert.assertTrue( ( Integer.parseInt( attrHeight ) > 600 ) ? true : false, "the number " + attrHeight );

    final String attrSrc = elementImage.getAttribute( "src" );
    Assert.assertEquals( attrSrc, PageUrl.BAR_CHART_VIZ2 );
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Bar Chart
   *
   * Description:
   *    We pretend to check if an image is displayed with bares in a chart.
   *
   * Steps:
   *    1. Open the bar chart.
   */
  @Test
  public void tc2_BarChartViz3_PngRendered() {
    this.log.info( "tc2_BarChartViz3_PngRendered" );

    /*
     * ## Step 1
     */
    this.elemHelper.Get( BaseTest.driver, PageUrl.BAR_CHART_VIZ3 );

    final WebElement elementImage = this.elemHelper.FindElement( BaseTest.driver, By.cssSelector( "img" ), 5 );
    Assert.assertNotNull( elementImage );

    final String attrWidth = elementImage.getAttribute( "width" );
    Assert.assertFalse( attrWidth.isEmpty() );
    final String attrHeight = elementImage.getAttribute( "height" );
    Assert.assertFalse( attrHeight.isEmpty() );
    Assert.assertTrue( ( Integer.parseInt( attrWidth ) > 800 ) ? true : false, "the number " + attrWidth );
    Assert.assertTrue( ( Integer.parseInt( attrHeight ) > 600 ) ? true : false, "the number " + attrHeight );

    final String attrSrc = elementImage.getAttribute( "src" );
    Assert.assertEquals( attrSrc, PageUrl.BAR_CHART_VIZ3 );
  }
}
