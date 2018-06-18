/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2018 by Hitachi Vantara
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
package com.pentaho.ctools.cde.samples.require.realtime;

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
 * Testing the functionalities related with Bullet Chart test case.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class RealTimeSampleDashboard extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( RealTimeSampleDashboard.class );

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Open Sample Page
   */
  @Test
  public void tc00_OpenSamplePage_Display() {
    this.log.info( "tc00_OpenSamplePage_Display" );
    // Go to BullertChartTestCase
    this.elemHelper.Get( BaseTest.driver, PageUrl.REAL_TIMTE_DASHBOARD_REQUIRE );

    //NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.cssSelector( "div.blockUI.blockOverlay" ), 3 );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Content Page is Presented.
   * 
   * Descriptions:
   *    There are some text that we need to validate if it's present.
   * 
   * Steps:
   *    1. Validate component text
   *    2. Validate title and subsections
   *    3. Chart title
   *    4. Check contact us
   * 
   */
  @Test( dependsOnMethods = "com.pentaho.ctools.cde.samples.require.realtime.RealTimeSampleDashboard.tc00_OpenSamplePage_Display" )
  public void tc01_ContentPage_Presented() {
    this.log.info( "tc01_ContentPage_Presented" );

    /**
     * Step 1 - Validate component text
     */
    String crtComponentInfo = this.elemHelper.WaitForElementPresentGetText( driver, By.cssSelector( "#headerTitle" ) );
    Assert.assertEquals( crtComponentInfo, "CDE - Community Dashboard Editor" );

    /**
     * Step 2 - Validate title and subsections
     */
    String crtTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.cssSelector( "#titleColumn > h1" ) );
    Assert.assertEquals( crtTitle, "Real Time Dashboard" );

    String crtSubSection2 = this.elemHelper.WaitForElementPresentGetText( driver, By.cssSelector( "#mainText > h4:nth-child(4)" ) );
    Assert.assertEquals( crtSubSection2, "Use CTools to View Data in Real-Time" );

    String crtLastParaphrase = this.elemHelper.WaitForElementPresentGetText( driver, By.cssSelector( "#mainText > p:nth-child(9)" ) );
    Assert.assertEquals( crtLastParaphrase, "You will now be able to view the streaming data in your transformation in real-time in a CCC Line Chart and in a Table." );

    /**
     * Step 3 - Chart title
     */
    String crtChartTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.cssSelector( "#graphicRowTitle > div > div" ) );
    Assert.assertEquals( crtChartTitle, "Car G Forces Over Time" );

    /**
     * Step 4 - Check Contact US 
     */
    String crtContactUs = this.elemHelper.WaitForElementPresentGetText( driver, By.cssSelector( "#contactUsButton > span > a" ) );
    Assert.assertEquals( crtContactUs, "Contact Us" );

    String crtLinkEmail = this.elemHelper.GetAttribute( driver, By.cssSelector( "#contactUsButton > span > a" ), "href" );
    Assert.assertEquals( crtLinkEmail, "mailto:portugal.leads@hitachivantara.com" );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Chart is render with values.
   * 
   * Descriptions:
   *    We want to check that chart is rendering with values.
   * 
   * Steps:
   *    1. Check Lat and Long
   *    2. Chart title
   *    3. Check Y axis
   * 
   */
  @Test( dependsOnMethods = "com.pentaho.ctools.cde.samples.require.realtime.RealTimeSampleDashboard.tc01_ContentPage_Presented" )
  public void tc02_ChartRender_Display() {
    this.log.info( "tc02_ChartRender_Display" );

    /**
     * Step 1 - Check Lat and Long
     */
    String crtLat = this.elemHelper.WaitForElementPresentGetText( driver, By.cssSelector( "#graphicColumnprotovis > svg > g:nth-child(4) > g > g > g > g > g:nth-child(1) > g:nth-child(2) > text" ) );
    Assert.assertEquals( crtLat, "Lat.G" );
    String crtLong = this.elemHelper.WaitForElementPresentGetText( driver, By.cssSelector( "#graphicColumnprotovis > svg > g:nth-child(4) > g > g > g > g > g:nth-child(2) > g:nth-child(2) > text" ) );
    Assert.assertEquals( crtLong, "Long.G" );

    /**
     * Step 2 - Chart title
     */
    this.elemHelper.FindElement( driver, By.cssSelector( "#graphicColumnprotovis > svg > g:nth-child(5) > g > g:nth-child(7) > g > text" ) );
    String crtTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.cssSelector( "#graphicColumnprotovis > svg > g:nth-child(5) > g > g:nth-child(7) > g > text" ) );
    Assert.assertEquals( crtTitle, "G forces" );

    /**
     * Step 3 - Check Y axis
     */
    WebElement crtAxisY = this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "#graphicColumnprotovis > svg > g:nth-child(5) > g > g:nth-child(8) > g:nth-child(1)" ) );
    Assert.assertNotNull( crtAxisY );
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Table is render with values.
   * 
   * Descriptions:
   *    We want to check that table is rendering with values.
   * 
   * Steps:
   *    1. Check table header
   *    2. Check table is being updated (refresh)
   * 
   */
  @Test( dependsOnMethods = "com.pentaho.ctools.cde.samples.require.realtime.RealTimeSampleDashboard.tc02_ChartRender_Display" )
  public void tc03_TableRender_Display() {
    this.log.info( "tc03_TableRender_Display" );

    /**
     * Step 1 - Check table header
     */
    String crtHeaderTime = this.elemHelper.WaitForElementPresentGetText( driver, By.cssSelector( "#tableColumnTable > thead > tr > th.column0.formattedText.sorting_desc" ) );
    Assert.assertEquals( crtHeaderTime, "Time" );
    String crtHeaderLap = this.elemHelper.WaitForElementPresentGetText( driver, By.cssSelector( "#tableColumnTable > thead > tr > th:nth-child(2)" ) );
    Assert.assertEquals( crtHeaderLap, "Lap" );
    String crtHeaderLat = this.elemHelper.WaitForElementPresentGetText( driver, By.cssSelector( "#tableColumnTable > thead > tr > th:nth-child(3)" ) );
    Assert.assertEquals( crtHeaderLat, "Lat. G" );
    String crtHeaderLong = this.elemHelper.WaitForElementPresentGetText( driver, By.cssSelector( "#tableColumnTable > thead > tr > th:nth-child(4)" ) );
    Assert.assertEquals( crtHeaderLong, "Long. G" );
    String crtHeaderKmh = this.elemHelper.WaitForElementPresentGetText( driver, By.cssSelector( "#tableColumnTable > thead > tr > th:nth-child(5)" ) );
    Assert.assertEquals( crtHeaderKmh, "Km/h" );
    String crtHeaderGear = this.elemHelper.WaitForElementPresentGetText( driver, By.cssSelector( "#tableColumnTable > thead > tr > th:nth-child(6)" ) );
    Assert.assertEquals( crtHeaderGear, "Gear" );
    String crtHeaderRpm = this.elemHelper.WaitForElementPresentGetText( driver, By.cssSelector( "#tableColumnTable > thead > tr > th:nth-child(7)" ) );
    Assert.assertEquals( crtHeaderRpm, "Rpm" );
    String crtHeaderAccel = this.elemHelper.WaitForElementPresentGetText( driver, By.cssSelector( "#tableColumnTable > thead > tr > th:nth-child(8)" ) );
    Assert.assertEquals( crtHeaderAccel, "Accelerator" );
    String crtHeaderBrake = this.elemHelper.WaitForElementPresentGetText( driver, By.cssSelector( "#tableColumnTable > thead > tr > th:nth-child(9)" ) );
    Assert.assertEquals( crtHeaderBrake, "Brake" );

    /**
     * Step 1 - Check table is being updated (refresh)
     */
    //Get values from Km/h and Rpm than compare with previous value after 15seconds
    String oldValuekmh = this.elemHelper.WaitForElementPresentGetText( driver, By.cssSelector( "#tableColumnTable > tbody > tr > td:nth-child(5)" ) );
    String oldValueRpm = this.elemHelper.WaitForElementPresentGetText( driver, By.cssSelector( "#tableColumnTable > tbody > tr > td:nth-child(7)" ) );

    this.log.debug( "Waiting 16s" );
    long now = System.currentTimeMillis();
    long expectedElapsedTime = now + 15000;
    while ( now < expectedElapsedTime ) {
      now = System.currentTimeMillis();
    }
    this.log.debug( "Stop waiting" );

    String newValuekmh = this.elemHelper.WaitForElementPresentGetText( driver, By.cssSelector( "#tableColumnTable > tbody > tr > td:nth-child(5)" ) );
    String newValueRpm = this.elemHelper.WaitForElementPresentGetText( driver, By.cssSelector( "#tableColumnTable > tbody > tr > td:nth-child(7)" ) );
    Assert.assertNotEquals( oldValuekmh, newValuekmh );
    Assert.assertNotEquals( oldValueRpm, newValueRpm );
  }
}
