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

package com.pentaho.ctools.cde.require;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * This script pretends to validate the sample Map Component Full Test
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 * Issues History:
 * -
 *
 */
public class MapComponentFullTest extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( MapComponentFullTest.class );

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Open Sample Page
   */
  @Test
  public void tc0_OpenSamplePage_Display() {
    this.log.info( "tc0_OpenSamplePage_Display" );

    // Go to MapComponentFullTest
    this.elemHelper.Get( BaseTest.driver, PageUrl.MAP_COMPONENT_FULL_TEST_REQUIRE );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.cssSelector( "div.blockUI.blockOverlay" ), 5 );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "div.blockUI.blockOverlay" ), 300 );

    // Check if the chart is already rendered
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.xpath( "//*[local-name()='image'][1]" ), 90 );
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.xpath( "//*[local-name()='image'][2]" ), 90 );
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.xpath( "//*[local-name()='image'][3]" ), 90 );
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.xpath( "//*[local-name()='image'][4]" ), 90 );
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.xpath( "//*[local-name()='image'][5]" ), 90 );
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.xpath( "//*[local-name()='image'][6]" ), 90 );
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.xpath( "//*[local-name()='image'][7]" ), 90 );
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Validate Page Contents
   *
   * Description:
   *    Here we want to validate the page contents.
   *
   * Steps:
   *    1. Check the widget's title.
   *    2. Check Sample title
   *    3. Check Map title
   */
  @Test
  public void tc1_PageContent_DisplayContents() {
    this.log.info( "tc1_PageContent_DisplayContents" );

    /*
     * ## Step 1
     */
    // Wait for title become visible and with value 'Map Component Full Test'
    final String expectedPageTitle = "Map Component Full Test";
    final String actualPageTitle = this.elemHelper.WaitForTitle( BaseTest.driver, expectedPageTitle );
    // Wait for visibility of 'Map Component Full Test'
    final String expectedSampleTitle = "Map Component Full Test";
    final String actualSampleTitle = this.elemHelper.WaitForTextDifferentEmpty( BaseTest.driver, By.cssSelector( "#content div div span" ) );
    final String expectedSampleMapTitle = "Full Map with CGG Markers and PopupWindows";
    final String actualSampleMapTitle = this.elemHelper.WaitForTextDifferentEmpty( BaseTest.driver, By.xpath( "//div[2]/span" ) );

    // Validate the sample that we are testing is the one
    Assert.assertEquals( actualPageTitle, expectedPageTitle );
    Assert.assertEquals( actualSampleTitle, expectedSampleTitle );
    Assert.assertEquals( actualSampleMapTitle, expectedSampleMapTitle );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Full Map with CGG Markers and PopupWindows
   *
   * Description:
   *    In this test case we pretend to check if the markers and popups windows
   *    are displayed.
   *
   * Steps:
   *    1. Check the data exist
   *    2. Chick in each marker
   *    3. Check tooltip
   *    4. Check disabling series in pie chart
   */
  @Test
  public void tc2_MapCGGMarkersAndPopupWindows_MarkersAndPopupsDisplayed() {
    this.log.info( "tc2_MapCGGMarkersAndPopupWindows_MarkersAndPopupsDisplayed" );

    final String popupTitle = "Sales For Product";
    final String serieVintage = "Vintage Cars";
    final String serieTruck = "Trucks and Buses";
    final String serieShips = "Ships";
    final String serieMotorcycles = "Motorcycles";
    final String serieTrains = "Trains";
    final String serieClassic = "Classic Cars";

    /*
     * ## Step 1
     */
    final WebElement marker1 = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//*[local-name()='image'][1]" ) );
    final WebElement marker2 = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//*[local-name()='image'][2]" ) );
    final WebElement marker3 = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//*[local-name()='image'][3]" ) );
    final WebElement marker4 = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//*[local-name()='image'][4]" ) );
    final WebElement marker5 = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//*[local-name()='image'][5]" ) );
    final WebElement marker6 = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//*[local-name()='image'][6]" ) );
    final WebElement marker7 = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//*[local-name()='image'][7]" ) );
    Assert.assertNotNull( marker1 );
    Assert.assertNotNull( marker2 );
    Assert.assertNotNull( marker3 );
    Assert.assertNotNull( marker4 );
    Assert.assertNotNull( marker5 );
    Assert.assertNotNull( marker6 );
    Assert.assertNotNull( marker7 );

    /*
     * ## Step 2
     */
    // Zoom in - in order for the elements to be visible
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#testWithGeoLocalization div:nth-child(2) div div:nth-child(5)" ) );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    final Actions acts2 = new Actions( BaseTest.driver );
    acts2.moveToElement( this.elemHelper.FindElement( BaseTest.driver, By.id( "dfooter" ) ) ).perform();
    // >>> Open Marker 1
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#testWithGeoLocalization div:nth-child(2) div div:nth-child(5) g:nth-child(3) g image:nth-child(1)" ) );
    // Wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis" ) );
    // Check we have the expect series displayed
    final String marker1Serie1 = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(1) > g:nth-child(1) > g:nth-child(2) > text" ) );
    final String marker1Serie2 = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(1) > g:nth-child(2) > g:nth-child(2) > text" ) );
    final String marker1Serie3 = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(1) > g:nth-child(3) > g:nth-child(2) > text" ) );
    final String marker1Serie4 = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > text" ) );
    final String marker1Serie5 = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(2) > g:nth-child(2) > text" ) );
    Assert.assertEquals( marker1Serie1, serieVintage );
    Assert.assertEquals( marker1Serie2, serieTruck );
    Assert.assertEquals( marker1Serie3, serieShips );
    Assert.assertEquals( marker1Serie4, serieMotorcycles );
    Assert.assertEquals( marker1Serie5, serieTrains );
    final String marker1PopupTitle = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(1) > g:nth-child(1) > text" ) );
    Assert.assertEquals( marker1PopupTitle, popupTitle );
    // Check the pie chart is present
    final String marker1Series5Color = this.elemHelper.GetAttribute( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(2) > g:nth-child(1) > g:nth-child(1) > g:nth-child(1) > path" ), "fill" );
    Assert.assertEquals( marker1Series5Color, "rgb(148,103,189)" );
    // Close popup
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "div.olPopupCloseBox" ) );

    // >>> Open Marker 2
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#testWithGeoLocalization div:nth-child(2) div div:nth-child(5) g:nth-child(3) g image:nth-child(2)" ) );
    // Wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis" ) );
    // Check we have the expect series displayed
    final String marker2Serie1 = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(1) > g:nth-child(1) > g:nth-child(2) > text" ) );
    final String marker2Serie2 = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(1) > g:nth-child(2) > g:nth-child(2) > text" ) );
    final String marker2Serie3 = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(1) > g:nth-child(3) > g:nth-child(2) > text" ) );
    final String marker2Serie4 = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > text" ) );
    Assert.assertEquals( marker2Serie1, serieClassic );
    Assert.assertEquals( marker2Serie2, serieVintage );
    Assert.assertEquals( marker2Serie3, serieTruck );
    Assert.assertEquals( marker2Serie4, serieShips );
    final String marker2PopupTitle = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(1) > g:nth-child(1) > text" ) );
    Assert.assertEquals( marker2PopupTitle, popupTitle );
    // Check the pie chart is present
    final String marker2Series2Color = this.elemHelper.GetAttribute( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(1) > g:nth-child(1) > path" ), "fill" );
    Assert.assertEquals( marker2Series2Color, "rgb(255,127,14)" );
    // Close popup
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "div.olPopupCloseBox" ) );

    // >>> Open Marker 3
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#testWithGeoLocalization div:nth-child(2) div div:nth-child(5) g:nth-child(3) g image:nth-child(3)" ) );
    // Wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis" ) );
    // Check we have the expect series displayed
    final String marker3Serie1 = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(1) > g:nth-child(1) > g:nth-child(2) > text" ) );
    final String marker3Serie2 = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(1) > g:nth-child(2) > g:nth-child(2) > text" ) );
    final String marker3Serie3 = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(1) > g:nth-child(3) > g:nth-child(2) > text" ) );
    Assert.assertEquals( marker3Serie1, serieTruck );
    Assert.assertEquals( marker3Serie2, serieShips );
    Assert.assertEquals( marker3Serie3, serieMotorcycles );
    final String marker3PopupTitle = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(1) > g:nth-child(1) > text" ) );
    Assert.assertEquals( marker3PopupTitle, popupTitle );
    // Check the pie chart is present
    final String marker3Series1Color = this.elemHelper.GetAttributeInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(1) > g:nth-child(1) > g:nth-child(1) > g:nth-child(1) > g:nth-child(1) > path" ), "fill" );
    Assert.assertEquals( marker3Series1Color, "rgb(31,119,180)" );
    // Close popup
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "div.olPopupCloseBox" ) );

    // >>> Open Marker 4
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#testWithGeoLocalization div:nth-child(2) div div:nth-child(5) g:nth-child(3) g image:nth-child(4)" ) );
    // Wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis" ) );
    // Check we have the expect series displayed
    final String marker4Serie1 = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(1) > g:nth-child(1) > g:nth-child(2) > text" ) );
    final String marker4Serie2 = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(1) > g:nth-child(2) > g:nth-child(2) > text" ) );
    Assert.assertEquals( marker4Serie1, serieMotorcycles );
    Assert.assertEquals( marker4Serie2, serieTrains );
    final String marker4PopupTitle = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(1) > g:nth-child(1) > text" ) );
    Assert.assertEquals( marker4PopupTitle, popupTitle );
    // Check the pie chart is present
    final String marker4Series1Color = this.elemHelper.GetAttribute( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(1) > g:nth-child(1) > g:nth-child(1) > g:nth-child(1) > g:nth-child(1) > path" ), "fill" );
    Assert.assertEquals( marker4Series1Color, "rgb(31,119,180)" );
    // Close popup
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "div.olPopupCloseBox" ) );

    // >>> Open Marker 5
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#testWithGeoLocalization div:nth-child(2) div div:nth-child(5) g:nth-child(3) g image:nth-child(5)" ) );
    // Wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis" ) );
    // Check we have the expect series displayed
    final String marker5Serie1 = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(1) > g:nth-child(1) > g:nth-child(2) > text" ) );
    final String marker5Serie2 = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(1) > g:nth-child(2) > g:nth-child(2) > text" ) );
    final String marker5Serie3 = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(1) > g:nth-child(3) > g:nth-child(2) > text" ) );
    final String marker5Serie4 = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > text" ) );
    final String marker5Serie5 = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(2) > g:nth-child(2) > text" ) );
    final String marker5Serie6 = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(3) > g:nth-child(2) > text" ) );
    Assert.assertEquals( marker5Serie1, serieClassic );
    Assert.assertEquals( marker5Serie2, serieVintage );
    Assert.assertEquals( marker5Serie3, serieTruck );
    Assert.assertEquals( marker5Serie4, serieShips );
    Assert.assertEquals( marker5Serie5, serieMotorcycles );
    Assert.assertEquals( marker5Serie6, serieTrains );
    final String marker5PopupTitle = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(1) > g:nth-child(1) > text" ) );
    Assert.assertEquals( marker5PopupTitle, popupTitle );
    // Check the pie chart is present
    final String marker5Series6Color = this.elemHelper.GetAttribute( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(3) > g:nth-child(1) > g:nth-child(1) > g:nth-child(1) > path" ), "fill" );
    Assert.assertEquals( marker5Series6Color, "rgb(140,86,75)" );
    // Close popup
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "div.olPopupCloseBox" ) );

    // >>> Open Marker 6
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#testWithGeoLocalization div:nth-child(2) div div:nth-child(5) g:nth-child(3) g image:nth-child(6)" ) );
    // Wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis" ) );
    // Check we have the expect series displayed
    final String marker6Serie1 = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(1) > g:nth-child(1) > g:nth-child(2) > text" ) );
    final String marker6Serie2 = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(1) > g:nth-child(2) > g:nth-child(2) > text" ) );
    final String marker6Serie3 = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(1) > g:nth-child(3) > g:nth-child(2) > text" ) );
    final String marker6Serie4 = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > text" ) );
    final String marker6Serie5 = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(2) > g:nth-child(2) > text" ) );
    final String marker6Serie6 = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(3) > g:nth-child(2) > text" ) );
    Assert.assertEquals( marker6Serie1, serieClassic );
    Assert.assertEquals( marker6Serie2, serieVintage );
    Assert.assertEquals( marker6Serie3, serieTruck );
    Assert.assertEquals( marker6Serie4, serieShips );
    Assert.assertEquals( marker6Serie5, serieMotorcycles );
    Assert.assertEquals( marker6Serie6, serieTrains );
    final String marker6PopupTitle = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(1) > g:nth-child(1) > text" ) );
    Assert.assertEquals( marker6PopupTitle, popupTitle );
    // Check the pie chart is present
    final String marker6Series4Color = this.elemHelper.GetAttribute( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(1) > g:nth-child(1) > g:nth-child(1) > path" ), "fill" );
    Assert.assertEquals( marker6Series4Color, "rgb(214,39,40)" );
    // Close popup
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "div.olPopupCloseBox" ) );

    // >>> Open Marker 7
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#testWithGeoLocalization div:nth-child(2) div div:nth-child(5) g:nth-child(3) g image:nth-child(7)" ) );
    // Wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( BaseTest.driver, By.xpath( "//div[@id='HiddenContentCol']" ) );
    // Check we have the expect series displayed
    final String marker7Serie1 = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(1) > g:nth-child(1) > g:nth-child(2) > text" ) );
    final String marker7Serie2 = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(1) > g:nth-child(2) > g:nth-child(2) > text" ) );
    Assert.assertEquals( marker7Serie1, serieTrains );
    Assert.assertEquals( marker7Serie2, serieMotorcycles );
    final String marker7PopupTitle = this.elemHelper.GetTextElementInvisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(1) > g:nth-child(1) > text" ) );
    Assert.assertEquals( marker7PopupTitle, popupTitle );
    // Check the pie chart is present
    final String marker7Series2Color = this.elemHelper.GetAttribute( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(1) > g:nth-child(1) > path" ), "fill" );
    Assert.assertEquals( marker7Series2Color, "rgb(255,127,14)" );
    // Close popup
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "div.olPopupCloseBox" ) );

    /*
     * ## Step 3
     */
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#testWithGeoLocalization div:nth-child(2) div div:nth-child(5) g:nth-child(3) g image:nth-child(5)" ) );
    // Wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis" ) );
    // Move mouse to element
    final WebElement marker5Serie1Tooltip = this.elemHelper.FindElement( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(3) > g:nth-child(1) > g:nth-child(1) > path" ) );
    assertNotNull( marker5Serie1Tooltip );
    this.elemHelper.MoveToElement( driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(3) > g:nth-child(1) > g:nth-child(1) > path" ) );

    String actualTooltip = this.elemHelper.GetAttributeInvisible( driver, By.cssSelector( "div.fakeTipsyTarget" ), "original-title" );
    String expectedTooltip = "<div class=\"ccc-tt\"><table class=\"ccc-tt-ds ccc-tt-plot ccc-tt-plot-pie ccc-tt-chartOrient-h\" data-ccc-color=\"rgb(31,119,180)\"><tBody><tr class=\"ccc-tt-dim ccc-tt-dimValueType-Any ccc-tt-dimDiscrete\"><td class=\"ccc-tt-dimLabel\"><span>Product</span></td><td class=\"ccc-tt-dimRoles\"><span class=\"ccc-tt-role ccc-tt-role-color\"><span class=\"ccc-tt-roleIcon\"></span><span class=\"ccc-tt-roleLabel\">Color</span></span><span class=\"ccc-tt-role ccc-tt-role-category\"><span class=\"ccc-tt-roleIcon\"></span><span class=\"ccc-tt-roleLabel\">Category</span></span></td><td class=\"ccc-tt-dimValue\"><span class=\"ccc-tt-value\">Classic Cars</span></td></tr><tr class=\"ccc-tt-dim ccc-tt-dimValueType-Any ccc-tt-dimDiscrete\"><td class=\"ccc-tt-dimLabel\"><span>Series</span></td><td class=\"ccc-tt-dimRoles\"></td><td class=\"ccc-tt-dimValue\"><span class=\"ccc-tt-value\">Quantity</span></td></tr><tr class=\"ccc-tt-dim ccc-tt-dimValueType-Number ccc-tt-dimContinuous\"><td class=\"ccc-tt-dimLabel\"><span>Value</span></td><td class=\"ccc-tt-dimRoles\"><span class=\"ccc-tt-role ccc-tt-role-value\"><span class=\"ccc-tt-roleIcon\"></span><span class=\"ccc-tt-roleLabel\">Value</span></span></td><td class=\"ccc-tt-dimValue\"><span class=\"ccc-tt-value\">1,381</span>";
    String expectedTooltip2 = "<span class=\"ccc-tt-valuePct\">17%</span></td></tr></tBody></table></div>";
    assertTrue( actualTooltip.contains( expectedTooltip ) );
    assertTrue( actualTooltip.contains( expectedTooltip2 ) );

    Actions acts = new Actions( BaseTest.driver );
    WebElement element = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='HiddenContentColprotovis']/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g']/*[local-name()='path'][1]" ) );
    acts.moveToElement( element );
    acts.perform();
    final String tooltipProduct = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@class='tipsy tipsy-sww']/div[2]/div/table/tbody/tr[1]/td[1]/span" ) );
    assertEquals( tooltipProduct, "Product" );

    /*
    this.elemHelper.MouseOverElementAndClick( driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(3) > g:nth-child(1) > g:nth-child(1) > path" ) );
    final String tooltipProduct = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@class='tipsy tipsy-sww']/div[2]/div/table/tbody/tr[1]/td[1]/span" ) );
    this.elemHelper.MouseOverElementAndClick( driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(3) > g:nth-child(1) > g:nth-child(1) > path" ) );
    final String tooltipProductValue = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@class='tipsy tipsy-sww']/div[2]/div/table/tbody/tr[1]/td[3]/span" ) );
    this.elemHelper.MouseOverElementAndClick( driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(3) > g:nth-child(1) > g:nth-child(1) > path" ) );
    final String tooltipSeries = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@class='tipsy tipsy-sww']/div[2]/div/table/tbody/tr[2]/td[1]/span" ) );
    this.elemHelper.MouseOverElementAndClick( driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(3) > g:nth-child(1) > g:nth-child(1) > path" ) );
    final String tooltipSeriesValue = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@class='tipsy tipsy-sww']/div[2]/div/table/tbody/tr[2]/td[3]/span" ) );
    this.elemHelper.MouseOverElementAndClick( driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(3) > g:nth-child(1) > g:nth-child(1) > path" ) );
    final String tooltipValues = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@class='tipsy tipsy-sww']/div[2]/div/table/tbody/tr[3]/td[1]/span" ) );
    this.elemHelper.MouseOverElementAndClick( driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(3) > g:nth-child(1) > g:nth-child(1) > path" ) );
    final String tooltipValuesValue = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@class='tipsy tipsy-sww']/div[2]/div/table/tbody/tr[3]/td[3]/span[1]" ) );
    this.elemHelper.MouseOverElementAndClick( driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(3) > g:nth-child(1) > g:nth-child(1) > path" ) );
    final String tooltipValuesValueP = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@class='tipsy tipsy-sww']/div[2]/div/table/tbody/tr[3]/td[3]/span[2]" ) );
    Assert.assertEquals( tooltipProduct, "Product" );
    Assert.assertEquals( tooltipProductValue, "Classic Cars" );
    Assert.assertEquals( tooltipSeries, "Series" );
    Assert.assertEquals( tooltipSeriesValue, "Quantity" );
    Assert.assertEquals( tooltipValues, "Value" );
    Assert.assertEquals( tooltipValuesValue, "1,381" );
    Assert.assertEquals( tooltipValuesValueP, "17%" );*/

    /*
     * ## Step 4
     */
    // Disable marker 5 of series 1
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(1) > g:nth-child(1) > g:nth-child(2) > text" ) );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(3) > g:nth-child(1) > g:nth-child(1) > path:nth-child(6)" ) );
    // Disable marker 5 of series 2
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(1) > g:nth-child(2) > g:nth-child(2) > text" ) );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(3) > g:nth-child(1) > g:nth-child(1) > path:nth-child(5)" ) );
    // Disable marker 5 of series 3
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(1) > g:nth-child(3) > g:nth-child(2) > text" ) );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(3) > g:nth-child(1) > g:nth-child(1) > path:nth-child(4)" ) );
    // Disable marker 5 of series 4
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > text" ) );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(3) > g:nth-child(1) > g:nth-child(1) > path:nth-child(3)" ) );
    // Disable marker 5 of series 5
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(1) > g:nth-child(2) > g:nth-child(2) > g:nth-child(2) > text" ) );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(3) > g:nth-child(1) > g:nth-child(1) > path:nth-child(2)" ) );
    // Check the values for the pie char for series 6
    final String marker5Serie6Value = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(3) > g:nth-child(1) > g:nth-child(2) > g:nth-child(2) > text" ) );
    final String marker5Serie6Per = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.cssSelector( "#HiddenContentColprotovis > svg:nth-child(1) > g:nth-child(3) > g:nth-child(3) > g:nth-child(1) > g:nth-child(2) > g:nth-child(2) > text:nth-child(2)" ) );
    Assert.assertEquals( marker5Serie6Value, "530" );
    Assert.assertEquals( marker5Serie6Per, "(100%)" );
  }
}
