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

package com.pentaho.ctools.cde.samples.legacy.reference.samples;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * Test all different maps with markers, no markers, shapes.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 * Issues History:
 * -
 *
 */
public class MapComponentReference extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( MapComponentReference.class );

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Open the sample page
   */
  @Test
  public void tc0_OpenSamplePage_Display() {
    this.log.info( "tc0_OpenSamplePage_Display" );

    // Go to MapComponentReference
    this.elemHelper.Get( BaseTest.driver, PageUrl.MAP_COMPONENT_REFERENCE );

    //NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.cssSelector( "div.blockUI.blockOverlay" ), 5 );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "div.blockUI.blockOverlay" ), 180 );

    //Wait for page render on each map test
    this.elemHelper.WaitForTextPresence( BaseTest.driver, By.cssSelector( "#simpleTest div.olControlScaleLine.olControlNoSelect div" ), "10000 km", 90 );
    this.elemHelper.WaitForTextPresence( BaseTest.driver, By.cssSelector( "#testTileServices div.olControlNoSelect div" ), "2000 km", 90 );
    //Wait for the three marks
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.xpath( "//div[@id='testWithMarker']/div[2]/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='image']" ), 90 );
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.xpath( "//div[@id='testWithMarker']/div[2]/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='image'][2]" ), 90 );
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.xpath( "//div[@id='testWithMarker']/div[2]/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='image'][3]" ), 90 );
    //Wait for the three marks
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.xpath( "//div[@id='testWithGeoLocalization']/div[2]/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='circle']" ), 90 );
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.xpath( "//div[@id='testWithGeoLocalization']/div[2]/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='circle'][2]" ), 90 );
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.xpath( "//div[@id='testWithGeoLocalization']/div[2]/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='circle'][3]" ), 90 );
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.xpath( "//div[@id='testWithGeoLocalization']/div[2]/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='circle'][4]" ), 90 );
    //Wait for shapes
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.xpath( "//div[@id='testWithShapes']/div[2]/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='path'][2]" ), 90 );

    //Just check if the sample title is displayed.
    final String actualSampleTitle = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.cssSelector( "div#title span" ) );
    Assert.assertEquals( "Map Component Reference", actualSampleTitle );
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
   *    2. Check Global Options
   *    3. Check Options valid in Marker mode
   *    4. Check Location Result Set in Marker mode
   *    5. Check Options valid in Shapes mode
   */
  @Test
  public void tc1_PageContent_DisplayTitle() {
    this.log.info( "tc1_PageContent_DisplayTitle" );

    /*
     * ## Step 1
     */
    // Wait for title become visible and with value 'Community Dashboard Framework'
    final String expectedPageTitle = "Map Component Reference";
    final String actualPageTitle = this.elemHelper.WaitForTitle( BaseTest.driver, expectedPageTitle );
    // Wait for visibility of 'OpenFlashChartComponent'
    final String expectedSampleTitle = "Map Component Reference";
    final String actualSampleTitle = this.elemHelper.WaitForTextDifferentEmpty( BaseTest.driver, By.xpath( "//div[@id='title']/span" ) );
    final String expectedSampleDesc = "This component allows the user to either navigate through the map and see information about marked locations, or to represent quantities as the fill color of a set of shapes/regions.";
    final String actualSampleDesc = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/p" ) );

    // Validate the sample that we are testing is the one
    Assert.assertEquals( actualPageTitle, expectedPageTitle );
    Assert.assertEquals( actualSampleTitle, expectedSampleTitle );
    Assert.assertEquals( actualSampleDesc, expectedSampleDesc );

    /*
     * ## Step 2
     */
    final String subTitleGlobalOptions = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/h3" ) );
    Assert.assertEquals( subTitleGlobalOptions, "Global Options" );
    final String goItem1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl/dt" ) );
    final String goItem2 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl/dt[2]" ) );
    final String goItem3 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl/dt[3]" ) );
    final String goItem4 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl/dt[4]" ) );
    final String goItem5 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl/dt[5]" ) );
    final String goItem6 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl/dt[6]" ) );
    final String goItem7 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl/dt[7]" ) );
    final String goItem8 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl/dt[8]" ) );
    final String goItem9 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl/dt[9]" ) );
    final String goItem10 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl/dt[10]" ) );
    final String goItem11 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl/dt[11]" ) );
    final String goItem12 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl/dt[12]" ) );
    final String goItem13 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl/dt[13]" ) );
    final String goItem14 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl/dt[14]" ) );
    Assert.assertEquals( goItem1, "listeners" );
    Assert.assertEquals( goItem2, "parameters" );
    Assert.assertEquals( goItem3, "htmlObject" );
    Assert.assertEquals( goItem4, "executeAtStart" );
    Assert.assertEquals( goItem5, "preExecution" );
    Assert.assertEquals( goItem6, "postExecution" );
    Assert.assertEquals( goItem7, "Center Longitude" );
    Assert.assertEquals( goItem8, "Center Latitude" );
    Assert.assertEquals( goItem9, "Default zoom Level" );
    Assert.assertEquals( goItem10, "Datasource" );
    Assert.assertEquals( goItem11, "Map Engine" );
    Assert.assertEquals( goItem12, "API_KEY" );
    Assert.assertEquals( goItem13, "Tilesets to display as layers" );
    Assert.assertEquals( goItem14, "Operation Mode" );

    /*
     * ## Step 3
     */
    final String subTitleOptionsMarker = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/h3[2]" ) );
    Assert.assertEquals( subTitleOptionsMarker, "Options valid in Marker mode" );
    final String omItem1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl[2]/dt" ) );
    final String omItem2 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl[2]/dt[2]" ) );
    final String omItem3 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl[2]/dt[3]" ) );
    final String omItem4 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl[2]/dt[4]" ) );
    final String omItem5 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl[2]/dt[5]" ) );
    final String omItem6 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl[2]/dt[6]" ) );
    final String omItem7 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl[2]/dt[7]" ) );
    final String omItem8 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl[2]/dt[8]" ) );
    final String omItem9 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl[2]/dt[9]" ) );
    final String omItem10 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl[2]/dt[10]" ) );
    Assert.assertEquals( omItem1, "Marker image" );
    Assert.assertEquals( omItem2, "Marker Width" );
    Assert.assertEquals( omItem3, "Marker Height" );
    Assert.assertEquals( omItem4, "Marker Click Parameters" );
    Assert.assertEquals( omItem5, "Marker click Function" );
    Assert.assertEquals( omItem6, "Cgg Graph for Marker" );
    Assert.assertEquals( omItem7, "Cgg Graph Parameters" );
    Assert.assertEquals( omItem8, "Div for popup window" );
    Assert.assertEquals( omItem9, "Popup Width" );
    Assert.assertEquals( omItem10, "Popup Height" );

    /*
     * ## Step 4
     */
    final String subTitleLocationMarker = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/h3[3]" ) );
    Assert.assertEquals( subTitleLocationMarker, "Location Result Set in Marker mode" );
    final String lmItem1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl[3]/dt" ) );
    final String lmItem2 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl[3]/dt[2]" ) );
    final String lmItem3 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl[3]/dt[3]" ) );
    final String lmItem4 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl[3]/dt[4]" ) );
    final String lmItem5 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl[3]/dt[5]" ) );
    final String lmItem6 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl[3]/dt[6]" ) );
    final String lmItem7 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl[3]/dt[7]" ) );
    final String lmItem8 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl[3]/dt[8]" ) );
    final String lmItem9 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl[3]/dt[9]" ) );
    final String lmItem10 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl[3]/dt[10]" ) );
    final String lmItem11 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl[3]/dt[11]" ) );
    final String lmItem12 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl[3]/dt[12]" ) );
    final String lmItem13 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl[3]/dt[13]" ) );
    final String lmItem14 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl[3]/dt[14]" ) );
    final String lmItem15 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl[3]/dt[15]" ) );
    Assert.assertEquals( lmItem1, "Longitude" );
    Assert.assertEquals( lmItem2, "Latitude" );
    Assert.assertEquals( lmItem3, "Address" );
    Assert.assertEquals( lmItem4, "City" );
    Assert.assertEquals( lmItem5, "County" );
    Assert.assertEquals( lmItem6, "State" );
    Assert.assertEquals( lmItem7, "Region" );
    Assert.assertEquals( lmItem8, "Country" );
    Assert.assertEquals( lmItem9, "Description" );
    Assert.assertEquals( lmItem10, "marker" );
    Assert.assertEquals( lmItem11, "markerWidth" );
    Assert.assertEquals( lmItem12, "markerHeight" );
    Assert.assertEquals( lmItem13, "popupContents" );
    Assert.assertEquals( lmItem14, "popupWidth" );
    Assert.assertEquals( lmItem15, "popupHeight" );

    /*
     * ## Step 5
     */
    final String subTitleOptionsSahpes = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/h3[4]" ) );
    Assert.assertEquals( subTitleOptionsSahpes, "Options valid in Shapes mode" );
    final String osItem1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl[4]/dt" ) );
    final String osItem2 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl[4]/dt[2]" ) );
    final String osItem3 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl[4]/dt[3]" ) );
    final String osItem4 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='documentation']/dl[4]/dt[4]" ) );
    Assert.assertEquals( osItem1, "Shape Definitions" );
    Assert.assertEquals( osItem2, "Shape Mouse Over Function" );
    Assert.assertEquals( osItem3, "Shape Mouse Out Function" );
    Assert.assertEquals( osItem4, "Shape Mouse Click Function" );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Map with no markers and no shapes
   * Description:
   *    Validate contents in a simple map.
   * Steps:
   *    1. Check the data exist
   *    2. Perform a zoom
   */
  @Test
  public void tc2_MapWithNoMarkersAndShapes_MapMustBeVisible() {
    this.log.info( "tc2_MapWithNoMarkersAndShapes_MapMustBeVisible" );

    /*
     * ## Step1
     */
    final String noMarkersAndShapesTitle = this.elemHelper.WaitForTextDifferentEmpty( BaseTest.driver, By.cssSelector( "#subtitle0 span" ) );
    Assert.assertEquals( noMarkersAndShapesTitle, "Map with no markers and no shapes - Simple Case" );
    //Scale
    final String scaleKm = this.elemHelper.WaitForTextPresence( BaseTest.driver, By.cssSelector( "#simpleTest div.olControlScaleLine.olControlNoSelect div" ), "10000 km" );
    final String scaleMi = this.elemHelper.WaitForTextPresence( BaseTest.driver, By.cssSelector( "#simpleTest div.olControlScaleLine.olControlNoSelect div:nth-child(2)" ), "5000 mi" );
    Assert.assertEquals( scaleKm, "10000 km" );
    Assert.assertEquals( scaleMi, "5000 mi" );
    //Controls Zoom in and out
    final WebElement zoomin = this.elemHelper.FindElement( BaseTest.driver, By.cssSelector( "#simpleTest > div.map-controls > div > div.map-controls-zoom > div.map-control-button.map-control-zoom-in" ) );
    final WebElement zoomout = this.elemHelper.FindElement( BaseTest.driver, By.cssSelector( "#simpleTest > div.map-controls > div > div.map-controls-zoom > div.map-control-button.map-control-zoom-out" ) );
    final WebElement buttonpan = this.elemHelper.FindElement( BaseTest.driver, By.cssSelector( "#simpleTest > div.map-controls > div > div.map-controls-mode > div" ) );
    Assert.assertNotNull( zoomin );
    Assert.assertNotNull( zoomout );
    Assert.assertNotNull( buttonpan );
    //Get same images
    Assert.assertNotNull( this.elemHelper.FindElement( BaseTest.driver, By.cssSelector( "#simpleTest > div.map-container.olMap.pan.moving > div > div >div > img" ) ) );

    /*
     * ## Step2
     */
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#simpleTest > div.map-controls > div > div.map-controls-zoom > div.map-control-button.map-control-zoom-in" ) );
    //wait for the field update
    final String scaleKm2 = this.elemHelper.WaitForTextPresence( BaseTest.driver, By.cssSelector( "#simpleTest div.olControlScaleLine.olControlNoSelect div" ), "5000 km" );
    final String scaleMi2 = this.elemHelper.WaitForTextPresence( BaseTest.driver, By.cssSelector( "#simpleTest div.olControlScaleLine.olControlNoSelect div:nth-child(2)" ), "2000 mi" );
    Assert.assertEquals( scaleKm2, "5000 km" );
    Assert.assertEquals( scaleMi2, "2000 mi" );
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Map engine and map tile services.
   * Description:
   *    Validate the contents on the map and check if the map is display when
   *    we change the map engine and tile services.
   * Steps:
   *    1. Check the data exist
   *    2. Chance map to Google
   *    3. Change map service to 'mapbox-world-dark'
   */
  @Test
  public void tc3_MapEngineAndTileServices_MapDisplayedAfterChanges() {
    this.log.info( "tc3_MapEngineAndTileServices_MapDisplayedAfterChanges" );

    /*
     * ## Step1
     */
    final String noMarkersAndShapesTitle = this.elemHelper.WaitForTextDifferentEmpty( BaseTest.driver, By.cssSelector( "#subtitle1 > span" ) );
    Assert.assertEquals( noMarkersAndShapesTitle, "Map tile services" );
    //mapEngine and service
    final String dropdownLabel = this.elemHelper.WaitForTextDifferentEmpty( BaseTest.driver, By.xpath( "//div[6]/div/div[2]/div" ) );
    Assert.assertEquals( dropdownLabel, "Select tile map service:" );
    //Zoom
    this.elemHelper.WaitForElementPresenceAndVisible( BaseTest.driver, By.cssSelector( "#testTileServices > div.map-controls > div > div.map-controls-zoom > div.map-control-button.map-control-zoom-in" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( BaseTest.driver, By.cssSelector( "#testTileServices > div.map-controls > div > div.map-controls-zoom > div.map-control-button.map-control-zoom-out" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( BaseTest.driver, By.cssSelector( "#testTileServices > div.map-controls > div > div.map-controls-zoom > div.map-control-button.map-control-zoombox" ) );
    //Scale
    final String scalekm = this.elemHelper.WaitForTextDifferentEmpty( BaseTest.driver, By.cssSelector( "#testTileServices div.olControlScaleLineTop" ) );
    Assert.assertEquals( scalekm, "2000 km" );
    final String scalemi = this.elemHelper.WaitForTextDifferentEmpty( BaseTest.driver, By.cssSelector( "#testTileServices div.olControlScaleLineBottom" ) );
    Assert.assertEquals( scalemi, "1000 mi" );
    //Check if map service exist
    final WebElement mapService = this.elemHelper.FindElement( BaseTest.driver, By.cssSelector( "#selectTileObj > select" ) );
    Assert.assertNotNull( mapService );
    final String expectedTileMapServiceDefaultValue = "default";
    final Select dropMapService = new Select( mapService );
    String actualTileMapServiceDefaultValue = dropMapService.getFirstSelectedOption().getText();
    Assert.assertEquals( actualTileMapServiceDefaultValue, expectedTileMapServiceDefaultValue );

    /*
     * ## Step2
     */
    dropMapService.selectByValue( "google" );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    actualTileMapServiceDefaultValue = dropMapService.getFirstSelectedOption().getText();
    Assert.assertEquals( actualTileMapServiceDefaultValue, "google" );

    //Check for image is Google
    final String actualGoogleImgTitle = this.elemHelper.GetAttribute( BaseTest.driver, By.cssSelector( "#testTileServices > div.map-container.olMap.moving.pan > div > div > div > div:nth-child(2) > a" ), "title" );
    final String expectedGoogleImgTitle = "Click to see this area on Google Maps";
    Assert.assertEquals( actualGoogleImgTitle, expectedGoogleImgTitle );

    //Check Map render:
    final WebElement googleMapRender = this.elemHelper.FindElement( BaseTest.driver, By.cssSelector( "#testTileServices > div.map-container > div > div > div > div:nth-child(8) > div > div > div:nth-child(4) > svg" ) );
    Assert.assertNotNull( googleMapRender );

    /*
     * ## Step3
     */
    dropMapService.selectByValue( "mapbox-world-dark" );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    actualTileMapServiceDefaultValue = dropMapService.getFirstSelectedOption().getText();
    Assert.assertEquals( actualTileMapServiceDefaultValue, "mapbox-world-dark" );

    final String actualMapBoxText = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.cssSelector( "#testTileServices > div.map-container > div > div:nth-child(3) > a" ) );
    Assert.assertEquals( actualMapBoxText, "MapBox" );

    final WebElement mapBoxDarkMapRender = this.elemHelper.FindElement( BaseTest.driver, By.cssSelector( "#testTileServices > div.map-container > div > div > div:nth-child(5) > svg" ) );
    Assert.assertNotNull( mapBoxDarkMapRender );

  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name:
   *    Map with Markers based on Long/lat.
   * Description:
   *    Along validate the contents of the map we are going to validate the
   *    markers, if they displayed the correct contents.
   * Steps:
   *    1. Check the data exist
   *    2. Chick in each marker
   */
  @Test
  public void tc4_MapWithMarkersBasedLongLat_MarkersShouldDisplayCorrectContents() {
    this.log.info( "tc4_MapWithMarkersBasedLongLat_MarkersShouldDisplayCorrectContents" );
    /*
     * ## Step  1
     */

    final WebElement marker1 = this.elemHelper.FindElement( BaseTest.driver, By.cssSelector( "#testWithMarker > div.map-container > div > div > div:nth-child(5) > svg > g:nth-child(3) > g > image" ) );
    final WebElement marker2 = this.elemHelper.FindElement( BaseTest.driver, By.cssSelector( "#testWithMarker > div.map-container > div > div > div:nth-child(5) > svg > g:nth-child(3) > g > image:nth-child(2)" ) );
    final WebElement marker3 = this.elemHelper.FindElement( BaseTest.driver, By.cssSelector( "#testWithMarker > div.map-container > div > div > div:nth-child(5) > svg > g:nth-child(3) > g > image:nth-child(3)" ) );
    Assert.assertNotNull( marker1 );
    Assert.assertNotNull( marker2 );
    Assert.assertNotNull( marker3 );
    Assert.assertEquals( marker1.getAttribute( "xlink:href" ), "/pentaho/api/repos/pentaho-cdf-dd/resources/custom/amd-components/Map/images/marker_grey.png" );
    Assert.assertEquals( marker2.getAttribute( "xlink:href" ), "/pentaho/api/repos/pentaho-cdf-dd/resources/custom/amd-components/Map/images/marker_blue.png" );
    Assert.assertEquals( marker3.getAttribute( "xlink:href" ), "/pentaho/api/repos/pentaho-cdf-dd/resources/custom/amd-components/Map/images/marker_grey02.png" );

    /*
     * ## Step 2
     */
    //Zoom in - in order for the elements to be visible
    final WebElement markersZoomIn = this.elemHelper.FindElement( BaseTest.driver, By.cssSelector( "#testWithMarker div.map-control-button.map-control-zoom-out" ) );
    Assert.assertNotNull( markersZoomIn );
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#testWithMarker div.map-control-button.map-control-zoom-out" ) );
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#testWithMarker div.map-control-button.map-control-zoom-out" ) );

    //Open Marker 1
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#testWithMarker > div.map-container > div > div > div:nth-child(5) > svg > g:nth-child(3) > g > image" ) );
    final String marker1Text = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.cssSelector( "#testWithMarker #HiddenContentCol" ) );
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#testWithMarker div.olPopupCloseBox" ) );
    Assert.assertEquals( marker1Text, "Atelier Graphique" );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "#testWithMarker div.olPopup" ) );
    //Open Marker 2
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#testWithMarker > div.map-container > div > div > div:nth-child(5) > svg > g:nth-child(3) > g > image:nth-child(2)" ) );
    final String marker2Text = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.cssSelector( "#testWithMarker #HiddenContentCol" ) );
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#testWithMarker div.olPopupCloseBox" ) );
    Assert.assertEquals( marker2Text, "Australian Collectors, Co." );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "#testWithMarker div.olPopup" ) );
    //Open Marker 3
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#testWithMarker > div.map-container > div > div > div:nth-child(5) > svg > g:nth-child(3) > g > image:nth-child(3)" ) );
    final String marker3Text = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.cssSelector( "#testWithMarker #HiddenContentCol" ) );
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#testWithMarker div.olPopupCloseBox" ) );
    Assert.assertEquals( marker3Text, "Signal Gift Stores" );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "#testWithMarker div.olPopup" ) );
  }

  /**
   * ############################### Test Case 5 ###############################
   *
   * Test Case Name:
   *    Map with Markers based on city.
   * Description:
   *    Along validate the contents of the map we are going to validate the
   *    markers, if they displayed the correct contents.
   * Steps:
   *    1. Check the data exist
   *    2. Chick in each marker
   */
  @Test
  public void tc5_MapWithMarkersBasedCity_MarkersShouldDisplayCorrectContents() {
    this.log.info( "tc5_MapWithMarkersBasedCity_MarkersShouldDisplayCorrectContents" );

    final String title = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.cssSelector( "#subtitle3 > span" ) );
    Assert.assertEquals( title, "Map with markers based on city names and popup windows on click" );

    /*
     * ## Step 1
     */
    //Check if the chart is already rendered
    final WebElement marker1 = this.elemHelper.FindElement( BaseTest.driver, By.cssSelector( "#testWithGeoLocalization > div.map-container > div > div > div:nth-child(5) > svg > g:nth-child(3) > g > circle" ) );
    final WebElement marker2 = this.elemHelper.FindElement( BaseTest.driver, By.cssSelector( "#testWithGeoLocalization > div.map-container > div > div > div:nth-child(5) > svg > g:nth-child(3) > g > circle:nth-child(2)" ) );
    final WebElement marker3 = this.elemHelper.FindElement( BaseTest.driver, By.cssSelector( "#testWithGeoLocalization > div.map-container > div > div > div:nth-child(5) > svg > g:nth-child(3) > g > circle:nth-child(3)" ) );
    final WebElement marker4 = this.elemHelper.FindElement( BaseTest.driver, By.cssSelector( "#testWithGeoLocalization > div.map-container > div > div > div:nth-child(5) > svg > g:nth-child(3) > g > circle:nth-child(4)" ) );
    final WebElement marker5 = this.elemHelper.FindElement( BaseTest.driver, By.cssSelector( "#testWithGeoLocalization > div.map-container > div > div > div:nth-child(5) > svg > g:nth-child(3) > g > circle:nth-child(5)" ) );
    Assert.assertNotNull( marker1 );
    Assert.assertNotNull( marker2 );
    Assert.assertNotNull( marker3 );
    Assert.assertNotNull( marker4 );
    Assert.assertNotNull( marker5 );

    /*
     * ## Step 2
     */
    this.elemHelper.MoveToElement( BaseTest.driver, By.cssSelector( "#testWithGeoLocalization > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1)" ) );
    //Open Marker 2
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#testWithGeoLocalization > div.map-container > div > div > div:nth-child(5) > svg > g:nth-child(3) > g > circle:nth-child(2)" ) );
    final String marker2Text = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.cssSelector( "#testWithGeoLocalization #HiddenContentCol" ) );
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#testWithGeoLocalization div.olPopupCloseBox" ) );
    Assert.assertEquals( marker2Text, "Australian Collectors, Co." );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "#testWithGeoLocalization div.olPopup" ) );
    //Open Marker 3
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#testWithGeoLocalization > div.map-container > div > div > div:nth-child(5) > svg > g:nth-child(3) > g > circle:nth-child(3)" ) );
    final String marker3Text = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.cssSelector( "#testWithGeoLocalization #HiddenContentCol" ) );
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#testWithGeoLocalization div.olPopupCloseBox" ) );
    Assert.assertEquals( marker3Text, "Signal Gift Stores" );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "#testWithGeoLocalization div.olPopup" ) );
    //Open Marker 4
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#testWithGeoLocalization > div.map-container > div > div > div:nth-child(5) > svg > g:nth-child(3) > g > circle:nth-child(4)" ) );
    final String marker4Text = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.cssSelector( "#testWithGeoLocalization #HiddenContentCol" ) );
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#testWithGeoLocalization div.olPopupCloseBox" ) );
    Assert.assertEquals( marker4Text, "La Rochelle Gifts" );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "#testWithGeoLocalization div.olPopup" ) );
    //Open Marker 5
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#testWithGeoLocalization > div.map-container > div > div > div:nth-child(5) > svg > g:nth-child(3) > g > circle:nth-child(5)" ) );
    final String marker5Text = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.cssSelector( "#testWithGeoLocalization #HiddenContentCol" ) );
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#testWithGeoLocalization  div.olPopupCloseBox" ) );
    Assert.assertEquals( marker5Text, "Baane Mini Imports" );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "#testWithGeoLocalization div.olPopup" ) );

  }

  /**
   * ############################### Test Case 6 ###############################
   *
   * Test Case Name:
   *    Map with Shapes
   * Description:
   *    Check if the map has shapes and by click in a known shape must change
   *    its color.
   * Steps:
   *    1. Check the data exist
   */
  @Test
  public void tc6_MapWithShapes_Available() {
    this.log.info( "tc6_MapWithShapes_Available" );

    final String title = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.cssSelector( "#subtitle4 > span" ) );
    Assert.assertEquals( title, "Map with Shapes (population per km2, top 100 countries)" );

    /*
     * ## Step 1
     */
    final WebElement shape1 = this.elemHelper.FindElement( BaseTest.driver, By.cssSelector( "#testWithShapes div.map-container div:nth-child(5) > svg > g:nth-child(2) > g > path:nth-child(5)" ) );
    final WebElement shape2 = this.elemHelper.FindElement( BaseTest.driver, By.cssSelector( "#testWithShapes div.map-container div:nth-child(5) > svg > g:nth-child(2) > g > path:nth-child(41)" ) );
    final WebElement shape3 = this.elemHelper.FindElement( BaseTest.driver, By.cssSelector( "#testWithShapes div.map-container div:nth-child(5) > svg > g:nth-child(2) > g > path:nth-child(80)" ) );
    final WebElement shape4 = this.elemHelper.FindElement( BaseTest.driver, By.cssSelector( "#testWithShapes div.map-container div:nth-child(5) > svg > g:nth-child(2) > g > path:nth-child(111)" ) );
    Assert.assertNotNull( shape1 );
    Assert.assertNotNull( shape2 );
    Assert.assertNotNull( shape3 );
    Assert.assertNotNull( shape4 );
    this.elemHelper.WaitForAttributeValue( BaseTest.driver, By.cssSelector( "#testWithShapes div.map-container div:nth-child(5) > svg > g:nth-child(2) > g > path:nth-child(5)" ), "fill", "rgba(228,68,15,1)", 10 );
    this.elemHelper.WaitForAttributeValue( BaseTest.driver, By.cssSelector( "#testWithShapes div.map-container div:nth-child(5) > svg > g:nth-child(2) > g > path:nth-child(41)" ), "fill", "rgba(224,164,51,1)", 10 );
    this.elemHelper.WaitForAttributeValue( BaseTest.driver, By.cssSelector( "#testWithShapes div.map-container div:nth-child(5) > svg > g:nth-child(2) > g > path:nth-child(80)" ), "fill", "rgba(185,173,74,1)", 10 );
    this.elemHelper.WaitForAttributeValue( BaseTest.driver, By.cssSelector( "#testWithShapes div.map-container div:nth-child(5) > svg > g:nth-child(2) > g > path:nth-child(111)" ), "fill", "rgba(182,174,76,1)", 10 );
    final String attrFillShape1 = this.elemHelper.GetAttribute( BaseTest.driver, By.cssSelector( "#testWithShapes div.map-container div:nth-child(5) > svg > g:nth-child(2) > g > path:nth-child(5)" ), "fill" );
    final String attrFillShape2 = this.elemHelper.GetAttribute( BaseTest.driver, By.cssSelector( "#testWithShapes div.map-container div:nth-child(5) > svg > g:nth-child(2) > g > path:nth-child(41)" ), "fill" );
    final String attrFillShape3 = this.elemHelper.GetAttribute( BaseTest.driver, By.cssSelector( "#testWithShapes div.map-container div:nth-child(5) > svg > g:nth-child(2) > g > path:nth-child(80)" ), "fill" );
    final String attrFillShape4 = this.elemHelper.GetAttribute( BaseTest.driver, By.cssSelector( "#testWithShapes div.map-container div:nth-child(5) > svg > g:nth-child(2) > g > path:nth-child(111)" ), "fill" );
    Assert.assertEquals( attrFillShape1, "rgba(228,68,15,1)" );
    Assert.assertEquals( attrFillShape2, "rgba(224,164,51,1)" );
    Assert.assertEquals( attrFillShape3, "rgba(185,173,74,1)" );
    Assert.assertEquals( attrFillShape4, "rgba(182,174,76,1)" );
  }
}
