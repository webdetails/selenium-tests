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

package com.pentaho.ctools.cde.samples.legacy.reference;

import static org.testng.Assert.assertEquals;
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
 * Testing the functionalities related with Visualization API Reference.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class VisualizationAPIReference extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( PopupComponent.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    PageContent
   *    
   * Description:
   *    The test case pretends validate the contents of the sample is presented
   *    in the page.
   *    
   * Steps:
   *    1. Open Page.
   *    2. Check the title of the page.
   */
  @Test
  public void tc01_PageContent_PageLoads() {
    this.log.info( "tc01_PageContent_PageLoads" );

    /*
     * ## Step 1
     */
    // Go to Visualization API Reference
    this.elemHelper.Get( driver, PageUrl.VIZUALIZATION_API_REFRENCE );

    //NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 2 );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    /*
     * ## Step 2
     */
    String expectedTitle = "Visualization API reference";
    String actualTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.cssSelector( "#Title > span" ) );
    assertEquals( actualTitle, expectedTitle );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    CheckChart
   *    
   * Description:
   *    The test case pretends to validate the contents of the chart.
   *    
   * Steps:
   *    1. Check chart title.
   *    2. Check Series.
   *    3. Check chart content.
   */
  @Test
  public void tc02_CheckChart_IsRendered() {
    this.log.info( "tc02_CheckChart_IsRendered" );

    /*
     * ## Step 1
     */
    String expectedChartTitle = "VizAPI Bar Chart";
    String actualChartTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "Dashboard" ) );
    assertEquals( actualChartTitle, expectedChartTitle );

    /*
     * ## Step 2 
     */
    //Serie 1
    String serie1 = this.elemHelper.WaitForTextDifferentEmpty( driver, By.cssSelector( "#ChartObj > svg > g:nth-child(4) > g > g > g > g > g:nth-child(1) > g:nth-child(2) > text" ) );
    assertEquals( serie1, "Classic Cars" );
    //Serie 2
    String serie2 = this.elemHelper.WaitForTextDifferentEmpty( driver, By.cssSelector( "#ChartObj > svg > g:nth-child(4) > g > g > g > g > g:nth-child(2) > g:nth-child(2) > text" ) );
    assertEquals( serie2, "Motorcycles" );
    //Serie 3
    String serie3 = this.elemHelper.WaitForTextDifferentEmpty( driver, By.cssSelector( "#ChartObj > svg > g:nth-child(4) > g > g > g > g > g:nth-child(3) > g:nth-child(2) > text" ) );
    assertEquals( serie3, "Planes" );
    //Serie 4
    String serie4 = this.elemHelper.WaitForTextDifferentEmpty( driver, By.cssSelector( "#ChartObj > svg > g:nth-child(4) > g > g > g > g > g:nth-child(4) > g:nth-child(2) > text" ) );
    assertEquals( serie4, "Ships" );
    //Serie 5
    String serie5 = this.elemHelper.WaitForTextDifferentEmpty( driver, By.cssSelector( "#ChartObj > svg > g:nth-child(4) > g > g > g > g > g:nth-child(5) > g:nth-child(2) > text" ) );
    assertEquals( serie5, "Trains" );
    //Serie 6
    String serie6 = this.elemHelper.WaitForTextDifferentEmpty( driver, By.cssSelector( "#ChartObj > svg > g:nth-child(4) > g > g > g > g > g:nth-child(6) > g:nth-child(2) > text" ) );
    assertEquals( serie6, "Trucks and Buses" );
    //Serie 7
    String serie7 = this.elemHelper.WaitForTextDifferentEmpty( driver, By.cssSelector( "#ChartObj > svg > g:nth-child(4) > g > g > g > g > g:nth-child(7) > g:nth-child(2) > text" ) );
    assertEquals( serie7, "Vintage Cars" );

    /*
     * ## Step 3 
     */
    //Serie 1
    WebElement rectSerie1 = this.elemHelper.FindElement( driver, By.cssSelector( "#ChartObj > svg > g:nth-child(5) > g > g:nth-child(3) > g > g > g:nth-child(1) > rect" ) );
    assertNotNull( rectSerie1 );
    String colorSerie1 = this.elemHelper.GetAttribute( driver, By.cssSelector( "#ChartObj > svg > g:nth-child(5) > g > g:nth-child(3) > g > g > g:nth-child(1) > rect" ), "fill" );
    assertEquals( colorSerie1, "rgb(0,93,166)" );
    //Serie 2
    WebElement rectSerie2 = this.elemHelper.FindElement( driver, By.cssSelector( "#ChartObj > svg > g:nth-child(5) > g > g:nth-child(3) > g > g > g:nth-child(2) > rect" ) );
    assertNotNull( rectSerie2 );
    String colorSerie2 = this.elemHelper.GetAttribute( driver, By.cssSelector( "#ChartObj > svg > g:nth-child(5) > g > g:nth-child(3) > g > g > g:nth-child(2) > rect" ), "fill" );
    assertEquals( colorSerie2, "rgb(3,169,244)" );
    //Serie 3
    WebElement rectSerie3 = this.elemHelper.FindElement( driver, By.cssSelector( "#ChartObj > svg > g:nth-child(5) > g > g:nth-child(3) > g > g > g:nth-child(3) > rect" ) );
    assertNotNull( rectSerie3 );
    String colorSerie3 = this.elemHelper.GetAttribute( driver, By.cssSelector( "#ChartObj > svg > g:nth-child(5) > g > g:nth-child(3) > g > g > g:nth-child(3) > rect" ), "fill" );
    assertEquals( colorSerie3, "rgb(255,121,0)" );
    //Serie 4
    WebElement rectSerie4 = this.elemHelper.FindElement( driver, By.cssSelector( "#ChartObj > svg > g:nth-child(5) > g > g:nth-child(3) > g > g > g:nth-child(4) > rect" ) );
    assertNotNull( rectSerie4 );
    String colorSerie4 = this.elemHelper.GetAttribute( driver, By.cssSelector( "#ChartObj > svg > g:nth-child(5) > g > g:nth-child(3) > g > g > g:nth-child(4) > rect" ), "fill" );
    assertEquals( colorSerie4, "rgb(242,194,73)" );
    //Serie 5
    WebElement rectSerie5 = this.elemHelper.FindElement( driver, By.cssSelector( "#ChartObj > svg > g:nth-child(5) > g > g:nth-child(3) > g > g > g:nth-child(5) > rect" ) );
    assertNotNull( rectSerie5 );
    String colorSerie5 = this.elemHelper.GetAttribute( driver, By.cssSelector( "#ChartObj > svg > g:nth-child(5) > g > g:nth-child(3) > g > g > g:nth-child(5) > rect" ), "fill" );
    assertEquals( colorSerie5, "rgb(95,67,196)" );
    //Serie 6
    WebElement rectSerie6 = this.elemHelper.FindElement( driver, By.cssSelector( "#ChartObj > svg > g:nth-child(5) > g > g:nth-child(3) > g > g > g:nth-child(6) > rect" ) );
    assertNotNull( rectSerie6 );
    String colorSerie6 = this.elemHelper.GetAttribute( driver, By.cssSelector( "#ChartObj > svg > g:nth-child(5) > g > g:nth-child(3) > g > g > g:nth-child(6) > rect" ), "fill" );
    assertEquals( colorSerie6, "rgb(148,111,221)" );
    //Serie 7
    WebElement rectSerie7 = this.elemHelper.FindElement( driver, By.cssSelector( "#ChartObj > svg > g:nth-child(5) > g > g:nth-child(3) > g > g > g:nth-child(7) > rect" ) );
    assertNotNull( rectSerie7 );
    String colorSerie7 = this.elemHelper.GetAttribute( driver, By.cssSelector( "#ChartObj > svg > g:nth-child(5) > g > g:nth-child(3) > g > g > g:nth-child(7) > rect" ), "fill" );
    assertEquals( colorSerie7, "rgb(0,132,91)" );
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    MouseOver
   *    
   * Description:
   *    The test case pretends validate the tooltip when mouse over a bar.
   *    
   * Steps:
   *    1. Check tooltip.
   */
  @Test
  public void tc03_MouseOver_BarsChangeColor() {
    this.log.info( "tc03_MouseOver_BarsChangeColor" );

    /*
     * ## Step 1 
     */
    // Mouse over first rectangle
    this.elemHelper.FocusAndMoveToElement( driver, By.cssSelector( "#ChartObj > svg > g:nth-child(5) > g > g:nth-child(3) > g > g > g:nth-child(2) > rect" ), -20, 5 );
    // Get tooltip
    String actualTooltip = this.elemHelper.WaitForTextDifferentEmpty( driver, By.cssSelector( "div.tipsy > div" ) );
    assertEquals( actualTooltip, "Product: Motorcycles\nSales: 1 274 125.2" );
  }
}
