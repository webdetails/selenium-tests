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
 * Testing the functionalities related with CGG - Scatter Chart.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class ScatterChart extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( ScatterChart.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Scatter Chart
   * Description:
   *    We pretend to check if an image is displayed with in a chart.
   * Steps:
   *    1. Open the scatter chart.
   */
  @Test
  public void tc1_ScatterChartViz2_SvgRendered() {
    this.log.debug( "tc1_ScatterChartViz2_SvgRendered" );
    /*
     * ## Step 1
     */
    this.elemHelper.Get( BaseTest.driver, PageUrl.SCATTER_CHART_VIZ2 );

    final WebElement elementSVG = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']" ) );
    Assert.assertNotNull( elementSVG );

    final String textCentral = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']" ) );
    Assert.assertEquals( "Central", textCentral );
    final String textEastern = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='text']" ) );
    Assert.assertEquals( "Eastern", textEastern );
    final String textSouthern = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g'][2]/*[local-name()='text']" ) );
    Assert.assertEquals( "Southern", textSouthern );
    final String textWestern = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][4]/*[local-name()='g'][2]/*[local-name()='text']" ) );
    Assert.assertEquals( "Western", textWestern );
    final String textActual = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g'][4]/*[local-name()='g']/*[local-name()='g']/*[local-name()='text']" ) );
    Assert.assertEquals( "Actual", textActual );
    final String textBudget = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g'][6]/*[local-name()='g']/*[local-name()='g']/*[local-name()='text']" ) );
    Assert.assertEquals( "Budget", textBudget );

    final WebElement circle1 = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g'][8]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='circle']" ) );
    Assert.assertNotNull( circle1 );
    final WebElement circle2 = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g'][8]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='circle'][15]" ) );
    Assert.assertNotNull( circle2 );
    final WebElement circle3 = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g'][8]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='circle'][28]" ) );
    Assert.assertNotNull( circle3 );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Scatter Chart
   * Description:
   *    We pretend to check if an image is displayed with in a chart.
   * Steps:
   *    1. Open the scatter chart.
   */
  @Test
  public void tc2_ScatterChartViz3_ImageRendered() {
    this.log.debug( "tc2_ScatterChartViz3_SvgRendered" );
    /*
     * ## Step 1
     */
    this.elemHelper.Get( BaseTest.driver, PageUrl.SCATTER_CHART_VIZ3 );

    final WebElement elementSVG = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']" ) );
    Assert.assertNotNull( elementSVG );

    final String textCentral = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.cssSelector( "svg > g > g:nth-child(4) > g > g > g > g > g:nth-child(1) > g:nth-child(2) > text" ) );
    Assert.assertEquals( "Central", textCentral );
    final String textEastern = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.cssSelector( "svg > g > g:nth-child(4) > g > g > g > g > g:nth-child(2) > g:nth-child(2) > text" ) );
    Assert.assertEquals( "Eastern", textEastern );
    final String textSouthern = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.cssSelector( "svg > g > g:nth-child(4) > g > g > g > g > g:nth-child(3) > g:nth-child(2) > text" ) );
    Assert.assertEquals( "Southern", textSouthern );
    final String textWestern = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.cssSelector( "svg > g > g:nth-child(4) > g > g > g > g > g:nth-child(4) > g:nth-child(2) > text" ) );
    Assert.assertEquals( "Western", textWestern );
    final String textActual = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.cssSelector( "svg > g > g:nth-child(5) > g > g:nth-child(4) > g > text" ) );
    Assert.assertEquals( "Actual - 10000", textActual );
    final String textBudget = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.cssSelector( "svg > g > g:nth-child(5) > g > g:nth-child(6) > g > text" ) );
    Assert.assertEquals( "Budget - Thousands", textBudget );

    final WebElement circle1 = this.elemHelper.FindElement( BaseTest.driver, By.cssSelector( "svg > g > g:nth-child(5) > g > g:nth-child(8) > g > g:nth-child(2) > circle:nth-child(1)" ) );
    Assert.assertNotNull( circle1 );
    final WebElement circle2 = this.elemHelper.FindElement( BaseTest.driver, By.cssSelector( "svg > g > g:nth-child(5) > g > g:nth-child(8) > g > g:nth-child(2) > circle:nth-child(15)" ) );
    Assert.assertNotNull( circle2 );
    final WebElement circle3 = this.elemHelper.FindElement( BaseTest.driver, By.cssSelector( "svg > g > g:nth-child(5) > g > g:nth-child(8) > g > g:nth-child(2) > circle:nth-child(28)" ) );
    Assert.assertNotNull( circle3 );
  }

}
