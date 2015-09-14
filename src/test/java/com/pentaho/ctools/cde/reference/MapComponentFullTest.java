/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2015 by Pentaho : http://www.pentaho.com
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

package com.pentaho.ctools.cde.reference;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.BaseTest;
import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;

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
   *    Validate Page Contents
   * Description:
   *    Here we want to validate the page contents.
   * Steps:
   *    1. Check the widget's title.
   *    2. Check Sample title
   *    3. Check Map title
   */
  @Test
  public void tc0_OpenSamplePage() {
    this.log.info( "tc0_OpenSamplePage" );

    // Go to MapComponentFullTest
    driver.get( PageUrl.MAP_COMPONENT_FULL_TEST );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 180 );

    // Check if the chart is already rendered
    this.elemHelper.WaitForElementPresence( driver, By.xpath( "//*[local-name()='image'][1]" ), 90 );
    this.elemHelper.WaitForElementPresence( driver, By.xpath( "//*[local-name()='image'][2]" ), 90 );
    this.elemHelper.WaitForElementPresence( driver, By.xpath( "//*[local-name()='image'][3]" ), 90 );
    this.elemHelper.WaitForElementPresence( driver, By.xpath( "//*[local-name()='image'][4]" ), 90 );
    this.elemHelper.WaitForElementPresence( driver, By.xpath( "//*[local-name()='image'][5]" ), 90 );
    this.elemHelper.WaitForElementPresence( driver, By.xpath( "//*[local-name()='image'][6]" ), 90 );
    this.elemHelper.WaitForElementPresence( driver, By.xpath( "//*[local-name()='image'][7]" ), 90 );

  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Validate Page Contents
   * Description:
   *    Here we want to validate the page contents.
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
    // Check page title
    assertEquals( "Map Component Full Test", driver.getTitle() );
    // Check sample title
    String sampleTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[2]/div[2]/div/div/span" ) );
    assertEquals( "Map Component Full Test", sampleTitle );
    String sampleMapTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[2]/span" ) );
    assertEquals( "Full Map with CGG Markers and PopupWindows", sampleMapTitle );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Full Map with CGG Markers and PopupWindows
   * Description:
   *    In this test case we pretend to check if the markers and popups windows
   *    are displayed.
   * Steps:
   *    1. Check the data exist
   *    2. Chick in each marker
   *    3. Check tooltip
   *    4. Check disabling series in pie chart
   */
  @Test
  public void tc2_MapCGGMarkersAndPopupWindows_MarkersAndPopupsDisplayed() {
    this.log.info( "tc2_MapCGGMarkersAndPopupWindows_MarkersAndPopupsDisplayed" );

    /*
     * ## Step 1
     */
    WebElement marker1 = this.elemHelper.FindElement( driver, By.xpath( "//*[local-name()='image'][1]" ) );
    WebElement marker2 = this.elemHelper.FindElement( driver, By.xpath( "//*[local-name()='image'][2]" ) );
    WebElement marker3 = this.elemHelper.FindElement( driver, By.xpath( "//*[local-name()='image'][3]" ) );
    WebElement marker4 = this.elemHelper.FindElement( driver, By.xpath( "//*[local-name()='image'][4]" ) );
    WebElement marker5 = this.elemHelper.FindElement( driver, By.xpath( "//*[local-name()='image'][5]" ) );
    WebElement marker6 = this.elemHelper.FindElement( driver, By.xpath( "//*[local-name()='image'][6]" ) );
    WebElement marker7 = this.elemHelper.FindElement( driver, By.xpath( "//*[local-name()='image'][7]" ) );
    assertNotNull( marker1 );
    assertNotNull( marker2 );
    assertNotNull( marker3 );
    assertNotNull( marker4 );
    assertNotNull( marker5 );
    assertNotNull( marker6 );
    assertNotNull( marker7 );

    /*
     *
     * THE BELOW CODE HAVE TO BE MODIFIED TO PASS ON AUTOMATION MACHINE
     * THAT NIGHTLY RUNS THE TESTS.
     *
     */

    /*
     * ## Step 2
     */
    // Zoom in - in order for the elements to be visible
    this.elemHelper.FindElement( driver, By.xpath( "//div[@id='testWithGeoLocalization']/div/div[5]/div[3]" ) ).click();
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    Actions acts2 = new Actions( driver );
    acts2.moveToElement( driver.findElement( By.id( "dfooter" ) ) ).perform();
    // >>> Open Marker 1
    driver.findElement( By.xpath( "//div[@id='testWithGeoLocalization']/div/div/div[5]/*[local-name()='svg']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='image']" ) ).click();
    // Wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='HiddenContentCol']" ) );
    // Check we have the expect series displayed
    String marker1Serie1 = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='g'][1]//*[local-name()='text']" ) );
    String marker1Serie2 = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='g'][2]//*[local-name()='text']" ) );
    String marker1Serie3 = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='g'][3]//*[local-name()='text']" ) );
    String marker1Serie4 = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][1]//*[local-name()='text']" ) );
    String marker1Serie5 = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]//*[local-name()='text']" ) );
    assertEquals( "Vintage Cars", marker1Serie1 );
    assertEquals( "Trucks and Buses", marker1Serie2 );
    assertEquals( "Ships", marker1Serie3 );
    assertEquals( "Motorcycles", marker1Serie4 );
    assertEquals( "Trains", marker1Serie5 );
    String popupTitle = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//*[local-name()='svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='text']" ) );
    assertEquals( "Sales For Product", popupTitle );
    // Check the pie chart is present
    WebElement marker1SeriesTrains = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='path'][5]" ) );
    assertEquals( "rgb(148,103,189)", marker1SeriesTrains.getAttribute( "fill" ) );
    // Close popup
    this.elemHelper.FindElement( driver, By.id( "featurePopup_close" ) ).click();

    // >>> Open Marker 2
    driver.findElement( By.xpath( "//div[@id='testWithGeoLocalization']/div/div/div[5]/*[local-name()='svg']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='image'][2]" ) ).click();
    // Wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='HiddenContentCol']" ) );
    // Check we have the expect series displayed
    String marker2Serie1 = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='g'][1]//*[local-name()='text']" ) );
    String marker2Serie2 = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='g'][2]//*[local-name()='text']" ) );
    String marker2Serie3 = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='g'][3]//*[local-name()='text']" ) );
    assertEquals( "Trucks and Buses", marker2Serie1 );
    assertEquals( "Ships", marker2Serie2 );
    assertEquals( "Motorcycles", marker2Serie3 );
    String marker2PopupTitle = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//*[local-name()='svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='text']" ) );
    assertEquals( "Sales For Product", marker2PopupTitle );
    // Check the pie chart is present
    WebElement marker2SeriesShips = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='path'][3]" ) );
    assertEquals( "rgb(44,160,44)", marker2SeriesShips.getAttribute( "fill" ) );
    // Close popup
    this.elemHelper.FindElement( driver, By.id( "featurePopup_close" ) ).click();

    // >>> Open Marker 3
    driver.findElement( By.xpath( "//div[@id='testWithGeoLocalization']/div/div/div[5]/*[local-name()='svg']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='image'][3]" ) ).click();
    // Wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='HiddenContentCol']" ) );
    // Check we have the expect series displayed
    String marker3Serie1 = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='g'][1]//*[local-name()='text']" ) );
    String marker3Serie2 = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='g'][2]//*[local-name()='text']" ) );
    assertEquals( "Motorcycles", marker3Serie1 );
    assertEquals( "Trains", marker3Serie2 );
    String marker3PopupTitle = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//*[local-name()='svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='text']" ) );
    assertEquals( "Sales For Product", marker3PopupTitle );
    // Check the pie chart is present
    WebElement marker3SeriesMotorcycles = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='path'][2]" ) );
    assertEquals( "rgb(255,127,14)", marker3SeriesMotorcycles.getAttribute( "fill" ) );
    // Close popup
    this.elemHelper.FindElement( driver, By.id( "featurePopup_close" ) ).click();

    // >>> Open Marker 4
    driver.findElement( By.xpath( "//div[@id='testWithGeoLocalization']/div/div/div[5]/*[local-name()='svg']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='image'][4]" ) ).click();
    // Wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='HiddenContentCol']" ) );
    // Check we have the expect series displayed
    String marker4Serie1 = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='g'][1]//*[local-name()='text']" ) );
    String marker4Serie2 = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='g'][2]//*[local-name()='text']" ) );
    String marker4Serie3 = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='g'][3]//*[local-name()='text']" ) );
    String marker4Serie4 = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][1]//*[local-name()='text']" ) );
    String marker4Serie5 = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]//*[local-name()='text']" ) );
    String marker4Serie6 = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][3]//*[local-name()='text']" ) );
    assertEquals( "Classic Cars", marker4Serie1 );
    assertEquals( "Vintage Cars", marker4Serie2 );
    assertEquals( "Trucks and Buses", marker4Serie3 );
    assertEquals( "Ships", marker4Serie4 );
    assertEquals( "Motorcycles", marker4Serie5 );
    assertEquals( "Trains", marker4Serie6 );
    String marker4PopupTitle = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//*[local-name()='svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='text']" ) );
    assertEquals( "Sales For Product", marker4PopupTitle );
    // Check the pie chart is present
    WebElement marker4SeriesTrains = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='path'][2]" ) );
    assertEquals( "rgb(255,127,14)", marker4SeriesTrains.getAttribute( "fill" ) );
    // Close popup
    this.elemHelper.FindElement( driver, By.id( "featurePopup_close" ) ).click();

    // >>> Open Marker 5
    driver.findElement( By.xpath( "//div[@id='testWithGeoLocalization']/div/div/div[5]/*[local-name()='svg']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='image'][5]" ) ).click();
    // Wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='HiddenContentCol']" ) );
    // Check we have the expect series displayed
    String marker5Serie1 = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='g'][1]//*[local-name()='text']" ) );
    String marker5Serie2 = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='g'][2]//*[local-name()='text']" ) );
    String marker5Serie3 = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='g'][3]//*[local-name()='text']" ) );
    String marker5Serie4 = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][1]//*[local-name()='text']" ) );
    String marker5Serie5 = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]//*[local-name()='text']" ) );
    String marker5Serie6 = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][3]//*[local-name()='text']" ) );
    assertEquals( "Classic Cars", marker5Serie1 );
    assertEquals( "Vintage Cars", marker5Serie2 );
    assertEquals( "Trucks and Buses", marker5Serie3 );
    assertEquals( "Ships", marker5Serie4 );
    assertEquals( "Motorcycles", marker5Serie5 );
    assertEquals( "Trains", marker5Serie6 );
    String marker5PopupTitle = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//*[local-name()='svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='text']" ) );
    assertEquals( "Sales For Product", marker5PopupTitle );
    // Check the pie chart is present
    WebElement marker5SeriesTrains = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='path'][6]" ) );
    assertEquals( "rgb(140,86,75)", marker5SeriesTrains.getAttribute( "fill" ) );
    // Close popup
    this.elemHelper.FindElement( driver, By.id( "featurePopup_close" ) ).click();

    // >>> Open Marker 6
    driver.findElement( By.xpath( "//div[@id='testWithGeoLocalization']/div/div/div[5]/*[local-name()='svg']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='image'][6]" ) ).click();
    // Wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='HiddenContentCol']" ) );
    // Check we have the expect series displayed
    String marker6Serie1 = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='g'][1]//*[local-name()='text']" ) );
    String marker6Serie2 = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='g'][2]//*[local-name()='text']" ) );
    assertEquals( "Trains", marker6Serie1 );
    assertEquals( "Motorcycles", marker6Serie2 );
    String marker6PopupTitle = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//*[local-name()='svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='text']" ) );
    assertEquals( "Sales For Product", marker6PopupTitle );
    // Check the pie chart is present
    WebElement marker6SeriesTrains = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='path'][1]" ) );
    assertEquals( "rgb(31,119,180)", marker6SeriesTrains.getAttribute( "fill" ) );
    // Close popup
    this.elemHelper.FindElement( driver, By.id( "featurePopup_close" ) ).click();

    // >>> Open Marker 7
    driver.findElement( By.xpath( "//div[@id='testWithGeoLocalization']/div/div/div[5]/*[local-name()='svg']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='image'][7]" ) ).click();
    // Wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='HiddenContentCol']" ) );
    // Check we have the expect series displayed
    String marker7Serie1 = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='g'][1]//*[local-name()='text']" ) );
    String marker7Serie2 = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='g'][2]//*[local-name()='text']" ) );
    String marker7Serie3 = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='g'][3]//*[local-name()='text']" ) );
    String marker7Serie4 = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][1]//*[local-name()='text']" ) );
    assertEquals( "Classic Cars", marker7Serie1 );
    assertEquals( "Vintage Cars", marker7Serie2 );
    assertEquals( "Trucks and Buses", marker7Serie3 );
    assertEquals( "Ships", marker7Serie4 );
    String marker7PopupTitle = this.elemHelper.GetTextElementInvisible( driver, By.xpath( "//*[local-name()='svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='text']" ) );
    assertEquals( "Sales For Product", marker7PopupTitle );
    // Check the pie chart is present
    WebElement marker7SeriesMotorcycles = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='path'][2]" ) );
    assertEquals( "rgb(255,127,14)", marker7SeriesMotorcycles.getAttribute( "fill" ) );
    // Close popup
    this.elemHelper.FindElement( driver, By.id( "featurePopup_close" ) ).click();

    /*
     * ## Step 3
     */
    driver.findElement( By.xpath( "//div[@id='testWithGeoLocalization']/div/div/div[5]/*[local-name()='svg']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='image'][5]" ) ).click();
    // Wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='HiddenContentCol']" ) );
    // Move mouse to element
    WebElement marker5SeriesClassicCars = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='path'][1]" ) );
    Actions acts = new Actions( driver );
    acts.moveToElement( marker5SeriesClassicCars );
    acts.perform();
    String tooltipProduct = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@class='tipsy tipsy-sww']/div[2]/div/table/tbody/tr[1]/td[1]/span" ) );
    acts.perform();
    String tooltipProductValue = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@class='tipsy tipsy-sww']/div[2]/div/table/tbody/tr[1]/td[3]/span" ) );
    acts.perform();
    String tooltipSeries = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@class='tipsy tipsy-sww']/div[2]/div/table/tbody/tr[2]/td[1]/span" ) );
    acts.perform();
    String tooltipSeriesValue = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@class='tipsy tipsy-sww']/div[2]/div/table/tbody/tr[2]/td[3]/span" ) );
    acts.perform();
    String tooltipValues = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@class='tipsy tipsy-sww']/div[2]/div/table/tbody/tr[3]/td[1]/span" ) );
    acts.perform();
    String tooltipValuesValue = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@class='tipsy tipsy-sww']/div[2]/div/table/tbody/tr[3]/td[3]/span[1]" ) );
    acts.perform();
    String tooltipValuesValueP = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@class='tipsy tipsy-sww']/div[2]/div/table/tbody/tr[3]/td[3]/span[2]" ) );
    assertEquals( "Product", tooltipProduct );
    assertEquals( "Classic Cars", tooltipProductValue );
    assertEquals( "Series", tooltipSeries );
    assertEquals( "Quantity", tooltipSeriesValue );
    assertEquals( "Value", tooltipValues );
    assertEquals( "2,381", tooltipValuesValue );
    assertEquals( "37.4%", tooltipValuesValueP );

    /*
     * ## Step 4
     */
    // Disable 'Classic Cars'
    this.elemHelper.FindElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='g'][1]//*[local-name()='text']" ) ).click();
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='path'][6]" ) );
    // Disable 'Trucks and Buses'
    this.elemHelper.FindElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='g'][3]//*[local-name()='text']" ) ).click();
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='path'][5]" ) );
    // Disable 'Ships'
    this.elemHelper.FindElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][1]//*[local-name()='text']" ) ).click();
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='path'][4]" ) );
    // Disable 'Motorcycles'
    this.elemHelper.FindElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]//*[local-name()='text']" ) ).click();
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='path'][3]" ) );
    // Disable 'Trains'
    this.elemHelper.FindElementInvisible( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][3]//*[local-name()='text']" ) ).click();
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='path'][2]" ) );
    // Check the values for the pie char for serie Vintage Cars
    String marker5SerieVintageCarsValue = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='text'][1]" ) );
    String marker5SerieVintageCarsValuePer = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='HiddenContentCol']/div/*[local-name()='svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='text'][2]" ) );
    assertEquals( "1,753", marker5SerieVintageCarsValue );
    assertEquals( "(100%)", marker5SerieVintageCarsValuePer );
  }
}
