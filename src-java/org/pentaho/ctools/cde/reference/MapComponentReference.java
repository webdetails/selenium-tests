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

package org.pentaho.ctools.cde.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.PageUrl;
import org.pentaho.ctools.utils.ScreenshotTestRule;

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
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class MapComponentReference {

  // Instance of the driver (browser emulator)
  private static WebDriver DRIVER;
  // Instance to be used on wait commands
  private static Wait<WebDriver> WAIT;
  //Log instance
  private static Logger LOG = LogManager.getLogger( MapComponentReference.class );

  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( DRIVER );

  /**
   * Shall initialized the test before run each test case.
   */
  @BeforeClass
  public static void setUp() {
    LOG.info( "setUp##" + MapComponentReference.class.getSimpleName() );
    DRIVER = CToolsTestSuite.getDriver();
    WAIT = CToolsTestSuite.getWait();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Open the sample page
   */
  @Test( timeout = 360000 )
  public void tc0_OpenPage() {
    LOG.info( "tc0_OpenSample" );

    //Go to MapComponentReference
    DRIVER.get( PageUrl.MAP_COMPONENT_REFERENCE );

    //NOTE - we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility( DRIVER, By.cssSelector( "div.blockUI.blockOverlay" ) );

    //Wait for page render on each map test
    ElementHelper.WaitForTextPresence( DRIVER, By.xpath( "//div[@id='simpleTest']/div/div[8]/div" ), "200 km", 90 );
    ElementHelper.WaitForTextPresence( DRIVER, By.xpath( "//div[@id='testTileServices']/div/div[8]/div" ), "200 km", 90 );
    //Wait for the three marks
    ElementHelper.WaitForElementPresence( DRIVER, By.xpath( "//div[@id='testWithMarker']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='image']" ), 90 );
    ElementHelper.WaitForElementPresence( DRIVER, By.xpath( "//div[@id='testWithMarker']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='image'][2]" ), 90 );
    ElementHelper.WaitForElementPresence( DRIVER, By.xpath( "//div[@id='testWithMarker']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='image'][3]" ), 90 );
    //Wait for the three marks
    ElementHelper.WaitForElementPresence( DRIVER, By.xpath( "//div[@id='testWithGeoLocalization']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='image']" ), 90 );
    ElementHelper.WaitForElementPresence( DRIVER, By.xpath( "//div[@id='testWithGeoLocalization']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='image'][2]" ), 90 );
    ElementHelper.WaitForElementPresence( DRIVER, By.xpath( "//div[@id='testWithGeoLocalization']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='image'][3]" ), 90 );
    ElementHelper.WaitForElementPresence( DRIVER, By.xpath( "//div[@id='testWithGeoLocalization']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='image'][4]" ), 90 );
    //Wait for shapes
    ElementHelper.WaitForElementPresence( DRIVER, By.xpath( "//div[@id='testWithShapes']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='path'][2]" ), 90 );

    //Just check if the sample title is displayed.
    String actualSampleTitle = ElementHelper.WaitForElementPresentGetText( DRIVER, By.cssSelector( "div#title span" ) );
    assertEquals( "Map Component Reference", actualSampleTitle );
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
  @Test( timeout = 60000 )
  public void tc1_PageContent_DisplayTitle() {
    LOG.info( "tc1_PageContent_DisplayTitle" );

    /*
     * ## Step 1
     */
    // Validate the sample that we are testing is the one
    assertEquals( "Community Dashboard Editor", DRIVER.getTitle() );
    final String sampleTitle = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='title']/span" ) );
    assertEquals( "Map Component Reference", sampleTitle );
    final String sampleDesc = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/p" ) );
    assertEquals( "This component allows the user to either navigate through the map and see information about marked locations, or to represent quantities as the fill color of a set of shapes/regions.", sampleDesc );

    /*
     * ## Step 2
     */
    final String subTitleGlobalOptions = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/h3" ) );
    assertEquals( "Global Options", subTitleGlobalOptions );
    final String goItem1 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl/dd/b" ) );
    final String goItem2 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl/dd[2]/b" ) );
    final String goItem3 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl/dd[3]/b" ) );
    final String goItem4 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl/dd[4]/b" ) );
    final String goItem5 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl/dd[5]/b" ) );
    final String goItem6 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl/dd[6]/b" ) );
    final String goItem7 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl/dd[7]/b" ) );
    final String goItem8 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl/dd[8]/b" ) );
    final String goItem9 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl/dd[9]/b" ) );
    final String goItem10 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl/dd[10]/b" ) );
    final String goItem11 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl/dd[11]/b" ) );
    final String goItem12 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl/dd[12]/b" ) );
    final String goItem13 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl/dd[13]/b" ) );
    final String goItem14 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl/dd[14]/b" ) );
    assertEquals( "listeners:", goItem1 );
    assertEquals( "parameters:", goItem2 );
    assertEquals( "htmlObject:", goItem3 );
    assertEquals( "executeAtStart:", goItem4 );
    assertEquals( "preExecution:", goItem5 );
    assertEquals( "postExecution:", goItem6 );
    assertEquals( "Center Longitude:", goItem7 );
    assertEquals( "Center Latitude:", goItem8 );
    assertEquals( "Default zoom Level:", goItem9 );
    assertEquals( "Datasource:", goItem10 );
    assertEquals( "Map Engine:", goItem11 );
    assertEquals( "API_KEY:", goItem12 );
    assertEquals( "Tilesets to display as layers:", goItem13 );
    assertEquals( "Operation Mode:", goItem14 );

    /*
     * ## Step 3
     */
    final String subTitleOptionsMarker = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/h3[2]" ) );
    assertEquals( "Options valid in Marker mode", subTitleOptionsMarker );
    final String omItem1 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl[2]/dd/b" ) );
    final String omItem2 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl[2]/dd[2]/b" ) );
    final String omItem3 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl[2]/dd[3]/b" ) );
    final String omItem4 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl[2]/dd[4]/b" ) );
    final String omItem5 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl[2]/dd[5]/b" ) );
    final String omItem6 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl[2]/dd[6]/b" ) );
    final String omItem7 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl[2]/dd[7]/b" ) );
    final String omItem8 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl[2]/dd[8]/b" ) );
    final String omItem9 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl[2]/dd[9]/b" ) );
    final String omItem10 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl[2]/dd[10]/b" ) );
    assertEquals( "Marker image:", omItem1 );
    assertEquals( "Marker Width:", omItem2 );
    assertEquals( "Marker Height:", omItem3 );
    assertEquals( "Marker Click Parameters:", omItem4 );
    assertEquals( "Marker click Function:", omItem5 );
    assertEquals( "Cgg Graph for Marker:", omItem6 );
    assertEquals( "Cgg Graph Parameters:", omItem7 );
    assertEquals( "Div for popup window:", omItem8 );
    assertEquals( "Popup Width:", omItem9 );
    assertEquals( "Popup Height:", omItem10 );

    /*
     * ## Step 4
     */
    final String subTitleLocationMarker = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/h3[3]" ) );
    assertEquals( "Location Result Set in Marker mode", subTitleLocationMarker );
    final String lmItem1 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl[3]/dd/b" ) );
    final String lmItem2 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl[3]/dd[2]/b" ) );
    final String lmItem3 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl[3]/dd[3]/b" ) );
    final String lmItem4 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl[3]/dd[4]/b" ) );
    final String lmItem5 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl[3]/dd[5]/b" ) );
    final String lmItem6 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl[3]/dd[6]/b" ) );
    final String lmItem7 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl[3]/dd[7]/b" ) );
    final String lmItem8 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl[3]/dd[8]/b" ) );
    final String lmItem9 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl[3]/dd[9]/b" ) );
    final String lmItem10 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl[3]/dd[10]/b" ) );
    final String lmItem11 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl[3]/dd[11]/b" ) );
    final String lmItem12 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl[3]/dd[12]/b" ) );
    final String lmItem13 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl[3]/dd[13]/b" ) );
    final String lmItem14 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl[3]/dd[14]/b" ) );
    final String lmItem15 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl[3]/dd[15]/b" ) );
    assertEquals( "Longitude:", lmItem1 );
    assertEquals( "Latitude:", lmItem2 );
    assertEquals( "Address:", lmItem3 );
    assertEquals( "City:", lmItem4 );
    assertEquals( "County:", lmItem5 );
    assertEquals( "State:", lmItem6 );
    assertEquals( "Region:", lmItem7 );
    assertEquals( "Country:", lmItem8 );
    assertEquals( "Description:", lmItem9 );
    assertEquals( "marker:", lmItem10 );
    assertEquals( "markerWidth:", lmItem11 );
    assertEquals( "markerHeight:", lmItem12 );
    assertEquals( "popupContents:", lmItem13 );
    assertEquals( "popupWidth:", lmItem14 );
    assertEquals( "popupHeight:", lmItem15 );

    /*
     * ## Step 5
     */
    final String subTitleOptionsSahpes = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/h3[4]" ) );
    assertEquals( "Options valid in Shapes mode", subTitleOptionsSahpes );
    final String osItem1 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl[4]/dd/b" ) );
    final String osItem2 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl[4]/dd[2]/b" ) );
    final String osItem3 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl[4]/dd[3]/b" ) );
    final String osItem4 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='documentation']/dl[4]/dd[4]/b" ) );
    assertEquals( "Shape Definitions:", osItem1 );
    assertEquals( "Shape Mouse Over Function:", osItem2 );
    assertEquals( "Shape Mouse Out Function:", osItem3 );
    assertEquals( "Shape Mouse Click Function:", osItem4 );
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
   *    3. Click in Globe
   */
  @Test( timeout = 60000 )
  public void tc2_MapWithNoMarkersAndShapes_MapMustBeVisible() {
    LOG.info( "tc2_MapWithNoMarkersAndShapes_MapMustBeVisible" );
    //wait for initialize
    WAIT.until( ExpectedConditions.invisibilityOfElementWithText( By.xpath( "//div[@id='simpleTest']/div/div[8]/div" ), "10000 km" ) );

    //## Step1
    WAIT.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='subtitle0']/span" ) ) );
    assertEquals( "Map with no markers and no shapes - Simple Case", DRIVER.findElement( By.xpath( "//div[@id='subtitle0']/span" ) ).getText() );
    //Scale
    WAIT.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='simpleTest']/div/div[8]/div" ) ) );
    assertEquals( "200 km", DRIVER.findElement( By.xpath( "//div[@id='simpleTest']/div/div[8]/div" ) ).getText() );
    WAIT.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='simpleTest']/div/div[8]/div[2]" ) ) );
    assertEquals( "200 mi", DRIVER.findElement( By.xpath( "//div[@id='simpleTest']/div/div[8]/div[2]" ) ).getText() );
    //ButtonLayer
    assertNotNull( DRIVER.findElement( By.xpath( "//div[@id='simpleTest']/div/div[7]/div[2]/img" ) ) );
    //Get same images
    assertNotNull( DRIVER.findElement( By.xpath( "//div[@id='simpleTest']/div/div/div/img" ) ) );
    assertNotNull( DRIVER.findElement( By.xpath( "//div[@id='simpleTest']/div/div/div/img[12]" ) ) );

    //## Step2
    WAIT.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='simpleTest']/div/div[5]/div" ) ) );
    WAIT.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='simpleTest']/div/div[5]/div[2]" ) ) );
    WAIT.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='simpleTest']/div/div[5]/div[3]" ) ) );
    DRIVER.findElement( By.xpath( "//div[@id='simpleTest']/div/div[5]/div" ) ).click();
    //wait for the field update
    WAIT.until( ExpectedConditions.invisibilityOfElementWithText( By.xpath( "//div[@id='simpleTest']/div/div[8]/div" ), "200 km" ) );
    WAIT.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='simpleTest']/div/div[8]/div" ) ) );
    assertEquals( "100 km", DRIVER.findElement( By.xpath( "//div[@id='simpleTest']/div/div[8]/div" ) ).getText() );
    WAIT.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='simpleTest']/div/div[8]/div[2]" ) ) );
    assertEquals( "100 mi", DRIVER.findElement( By.xpath( "//div[@id='simpleTest']/div/div[8]/div[2]" ) ).getText() );

    //## Step3
    DRIVER.findElement( By.xpath( "//div[@id='simpleTest']/div/div[5]/div[2]" ) ).click();
    //wait for the field update
    WAIT.until( ExpectedConditions.invisibilityOfElementWithText( By.xpath( "//div[@id='simpleTest']/div/div[8]/div" ), "100 km" ) );
    WAIT.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='simpleTest']/div/div[8]/div" ) ) );
    assertEquals( "10000 km", DRIVER.findElement( By.xpath( "//div[@id='simpleTest']/div/div[8]/div" ) ).getText() );
    WAIT.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='simpleTest']/div/div[8]/div[2]" ) ) );
    assertEquals( "5000 mi", DRIVER.findElement( By.xpath( "//div[@id='simpleTest']/div/div[8]/div[2]" ) ).getText() );
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
  @Test( timeout = 60000 )
  public void tc3_MapEngineAndTileServices_MapDisplayedAfterChanges() {
    LOG.info( "tc3_MapEngineAndTileServices_MapDisplayedAfterChanges" );
    //wait for initialize
    WAIT.until( ExpectedConditions.invisibilityOfElementWithText( By.xpath( "//div[@id='testTileServices']/div/div[8]/div" ), "10000 km" ) );

    //## Step1
    WAIT.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='subtitle1']/span" ) ) );
    assertEquals( "Map engine and map tile services", DRIVER.findElement( By.xpath( "//div[@id='subtitle1']/span" ) ).getText() );
    //mapEngine and service
    assertEquals( "Select map engine:", DRIVER.findElement( By.xpath( "//div[@id='content']/div/div[6]/div/div/div" ) ).getText() );
    assertEquals( "Select tile map service:", DRIVER.findElement( By.xpath( "//div[@id='content']/div/div[6]/div/div[2]/div" ) ).getText() );
    //Zoom
    WAIT.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='testTileServices']/div/div[5]/div" ) ) );
    WAIT.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='testTileServices']/div/div[5]/div[2]" ) ) );
    WAIT.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='testTileServices']/div/div[5]/div[3]" ) ) );
    //Scale
    WAIT.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='testTileServices']/div/div[8]/div" ) ) );
    assertEquals( "200 km", DRIVER.findElement( By.xpath( "//div[@id='testTileServices']/div/div[8]/div" ) ).getText() );
    WAIT.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='testTileServices']/div/div[8]/div[2]" ) ) );
    assertEquals( "200 mi", DRIVER.findElement( By.xpath( "//div[@id='testTileServices']/div/div[8]/div[2]" ) ).getText() );
    //Check if we are using OpenLayers
    final String mapId = DRIVER.findElement( By.xpath( "//div[@id='testTileServices']/div" ) ).getAttribute( "id" );
    assertTrue( mapId.contains( "OpenLayers" ) );
    final Select mapEngine = new Select( DRIVER.findElement( By.xpath( "//div[@id='selectMapEngineObj']/select" ) ) );
    final Select mapService = new Select( DRIVER.findElement( By.xpath( "//div[@id='selectTileObj']/select" ) ) );
    assertEquals( "openlayers", mapEngine.getFirstSelectedOption().getText() );
    assertEquals( "mapquest-sat", mapService.getFirstSelectedOption().getText() );

    //## Step2
    mapEngine.selectByValue( "google" );
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    WAIT.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='testTileServices']/div[@class='gm-style']" ) ) );
    //Image with Google (left down corner)
    assertNotNull( DRIVER.findElement( By.xpath( "//div[@id='testTileServices']/div/div[2]/a/div/img" ) ) );
    //Text 'Termos de Utilização (righ down corner)
    final String strMap = "Map";
    final String strTerms = "Terms of Use";
    final String strSat = "Satellite";

    assertEquals( strTerms, DRIVER.findElement( By.xpath( "//div[@id='testTileServices']/div/div[6]/div[2]/a" ) ).getText() );
    //check if we have mapquest-sat/Mapa/Satelite
    assertEquals( "mapquest-sat", DRIVER.findElement( By.xpath( "//div[@id='testTileServices']/div/div[9]/div/div" ) ).getText() );
    assertEquals( strMap, DRIVER.findElement( By.xpath( "//div[@id='testTileServices']/div/div[9]/div[2]/div" ) ).getText() );
    assertEquals( strSat, DRIVER.findElement( By.xpath( "//div[@id='testTileServices']/div/div[9]/div[3]/div" ) ).getText() );

    //## Step3
    mapService.selectByValue( "mapbox-world-dark" );
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    WAIT.until( ExpectedConditions.invisibilityOfElementWithText( By.xpath( "//div[@id='testTileServices']/div/div[9]/div/div" ), "mapquest-sat" ) );
    //Image with Google (left down corner)
    assertNotNull( DRIVER.findElement( By.xpath( "//div[@id='testTileServices']/div/div[2]/a/div/img" ) ) );
    //Text 'Termos de Utilização (righ down corner)
    assertEquals( strTerms, DRIVER.findElement( By.xpath( "//div[@id='testTileServices']/div/div[6]/div[2]/a" ) ).getText() );
    //check if we have mapbox-world-dark/Mapa/Satelite
    assertEquals( "mapbox-world-dark", DRIVER.findElement( By.xpath( "//div[@id='testTileServices']/div/div[9]/div/div" ) ).getText() );
    assertEquals( strMap, DRIVER.findElement( By.xpath( "//div[@id='testTileServices']/div/div[9]/div[2]/div" ) ).getText() );
    assertEquals( strSat, DRIVER.findElement( By.xpath( "//div[@id='testTileServices']/div/div[9]/div[3]/div" ) ).getText() );
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
  @Test( timeout = 60000 )
  public void tc4_MapWithMarkersBasedLongLat_MarkersShouldDisplayCorrectContents() {
    LOG.info( "tc4_MapWithMarkersBasedLongLat_MarkersShouldDisplayCorrectContents" );
    /*
     * ## Step 1
     */
    final WebElement marker1 = ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='testWithMarker']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='image']" ) );
    final WebElement marker2 = ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='testWithMarker']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='image'][2]" ) );
    final WebElement marker3 = ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='testWithMarker']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='image'][3]" ) );
    assertNotNull( marker1 );
    assertNotNull( marker2 );
    assertNotNull( marker3 );
    assertEquals( "/pentaho/content/pentaho-cdf-dd/resources/custom/components/NewMapComponent/images/marker_grey.png", marker1.getAttribute( "xlink:href" ) );
    assertEquals( "/pentaho/content/pentaho-cdf-dd/resources/custom/components/NewMapComponent/images/marker_blue.png", marker2.getAttribute( "xlink:href" ) );
    assertEquals( "/pentaho/content/pentaho-cdf-dd/resources/custom/components/NewMapComponent/images/marker_grey02.png", marker3.getAttribute( "xlink:href" ) );

    /*
     * ## Step 2
     */
    //Zoom in - in order for the elements to be visible
    ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='testWithMarker']/div/div[5]/div[3]" ) ).click();
    ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='testWithMarker']/div/div[5]/div[3]" ) ).click();

    //Open Marker 1
    marker1.click();
    //Wait for loading disappear
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    final String marker1Text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='HiddenContentCol']" ) );
    ElementHelper.FindElement( DRIVER, By.xpath( "//div[@class='olPopupCloseBox']" ) ).click();
    assertEquals( "Atelier Graphique", marker1Text );
    //Open Marker 2
    marker2.click();
    //Wait for loading disappear
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    final String marker2Text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='HiddenContentCol']" ) );
    ElementHelper.FindElement( DRIVER, By.xpath( "//div[@class='olPopupCloseBox']" ) ).click();
    assertEquals( "Australian Collectors, Co.", marker2Text );
    //Open Marker 3
    marker3.click();
    //Wait for loading disappear
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    final String marker3Text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='HiddenContentCol']" ) );
    ElementHelper.FindElement( DRIVER, By.xpath( "//div[@class='olPopupCloseBox']" ) ).click();
    assertEquals( "Signal Gift Stores", marker3Text );
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
  @Test( timeout = 150000 )
  public void tc5_MapWithMarkersBasedCity_MarkersShouldDisplayCorrectContents() {
    LOG.info( "tc5_MapWithMarkersBasedCity_MarkersShouldDisplayCorrectContents" );
    /*
     * ## Step 1
     */
    //Check if the chart is already rendered
    ElementHelper.WaitForElementPresence( DRIVER, By.xpath( "//div[@id='testWithGeoLocalization']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='image']" ), 90 );
    final WebElement marker1 = ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='testWithGeoLocalization']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='image']" ) );
    final WebElement marker2 = ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='testWithGeoLocalization']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='image'][2]" ) );
    final WebElement marker3 = ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='testWithGeoLocalization']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='image'][3]" ) );
    final WebElement marker4 = ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='testWithGeoLocalization']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='image'][4]" ) );
    assertNotNull( marker1 );
    assertNotNull( marker2 );
    assertNotNull( marker3 );
    assertNotNull( marker4 );
    assertEquals( "/pentaho/content/pentaho-cdf-dd/resources/custom/components/NewMapComponent/images/marker_grey.png", marker1.getAttribute( "xlink:href" ) );
    assertEquals( "/pentaho/content/pentaho-cdf-dd/resources/custom/components/NewMapComponent/images/marker_grey02.png", marker2.getAttribute( "xlink:href" ) );
    assertEquals( "/pentaho/content/pentaho-cdf-dd/resources/custom/components/NewMapComponent/images/marker_orange.png", marker3.getAttribute( "xlink:href" ) );
    assertEquals( "/pentaho/content/pentaho-cdf-dd/resources/custom/components/NewMapComponent/images/marker_purple.png", marker4.getAttribute( "xlink:href" ) );

    /*
     * ## Step 2
     */
    //Zoom in - in order for the elements to be visible
    ElementHelper.Click( DRIVER, By.xpath( "//div[@id='testWithGeoLocalization']/div/div[5]/div[3]" ) );
    //Open Marker 1
    marker1.click();
    //Wait for loading disappear
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    final String marker1Text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='HiddenContentCol']" ) );
    ElementHelper.Click( DRIVER, By.xpath( "(//div[@class='olPopupCloseBox'])[2]" ) );
    assertEquals( "Atelier Graphique", marker1Text );
    //Open Marker 2
    marker2.click();
    //Wait for loading disappear
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    final String marker2Text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='HiddenContentCol']" ) );
    ElementHelper.Click( DRIVER, By.xpath( "(//div[@class='olPopupCloseBox'])[2]" ) );
    assertEquals( "Signal Gift Stores", marker2Text );
    //Open Marker 3
    marker3.click();
    //Wait for loading disappear
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    final String marker3Text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='HiddenContentCol']" ) );
    ElementHelper.Click( DRIVER, By.xpath( "(//div[@class='olPopupCloseBox'])[2]" ) );
    assertEquals( "La Rochelle Gifts", marker3Text );
    //Open Marker 4
    marker4.click();
    //Wait for loading disappear
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    final String marker4Text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='HiddenContentCol']" ) );
    ElementHelper.Click( DRIVER, By.xpath( "(//div[@class='olPopupCloseBox'])[2]" ) );
    assertEquals( "Baane Mini Imports", marker4Text );
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
   *    2. Chick in a shape country
   */
  @Test( timeout = 90000 )
  public void tc6_MapWithShapes_ShapesAreClickable() {
    LOG.info( "tc6_MapWithShapes_ShapesAreClickable" );

    //Zoom in the map
    ElementHelper.FindElement( DRIVER, By.xpath( "//div[12]/div/div/div[5]/div[3]" ) ).click();

    /*
     * ## Step 1
     */
    final WebElement shape1 = ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='testWithShapes']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='path'][2]" ) );
    final WebElement shape2 = ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='testWithShapes']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='path'][10]" ) );
    final WebElement shape3 = ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='testWithShapes']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='path'][13]" ) );
    final WebElement shape4 = ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='testWithShapes']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='path'][14]" ) );
    assertNotNull( shape1 );
    assertNotNull( shape2 );
    assertNotNull( shape3 );
    assertNotNull( shape4 );
    ElementHelper.WaitForAttributeValue( DRIVER, By.xpath( "//div[@id='testWithShapes']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='path'][2]" ), "fill", "rgba(255,8,0,255)", 10 );
    ElementHelper.WaitForAttributeValue( DRIVER, By.xpath( "//div[@id='testWithShapes']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='path'][10]" ), "fill", "rgba(183,212,0,255)", 15 );
    ElementHelper.WaitForAttributeValue( DRIVER, By.xpath( "//div[@id='testWithShapes']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='path'][13]" ), "fill", "rgba(167,202,0,255)", 15 );
    ElementHelper.WaitForAttributeValue( DRIVER, By.xpath( "//div[@id='testWithShapes']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='path'][14]" ), "fill", "rgba(167,202,0,255)", 15 );
    final String attrFillShape1 = ElementHelper.GetAttribute( DRIVER, By.xpath( "//div[@id='testWithShapes']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='path'][2]" ), "fill" );
    final String attrFillShape2 = ElementHelper.GetAttribute( DRIVER, By.xpath( "//div[@id='testWithShapes']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='path'][10]" ), "fill" );
    final String attrFillShape3 = ElementHelper.GetAttribute( DRIVER, By.xpath( "//div[@id='testWithShapes']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='path'][13]" ), "fill" );
    final String attrFillShape4 = ElementHelper.GetAttribute( DRIVER, By.xpath( "//div[@id='testWithShapes']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='path'][14]" ), "fill" );
    assertEquals( "rgba(255,8,0,255)", attrFillShape1 );
    assertEquals( "rgba(183,212,0,255)", attrFillShape2 );
    assertEquals( "rgba(167,202,0,255)", attrFillShape3 );
    assertEquals( "rgba(167,202,0,255)", attrFillShape4 );

    /*
     * ## Step 2
     */
    //Click in shape4 (England) and check it comes with red.
    String shapeId = shape1.getAttribute( "id" );
    shape1.click();
    //Wait for change color
    ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//*[local-name()='path' and @fill='red']" ) );
    WebElement shapeRed = ElementHelper.FindElement( DRIVER, By.id( shapeId ) );
    assertEquals( "red", shapeRed.getAttribute( "fill" ) );

    //Related to issue CDE-317
    final Actions action = new Actions( DRIVER );
    action.moveToElement( shape2 ).build().perform();
    action.moveToElement( shape1 ).build().perform();
    shapeId = shape1.getAttribute( "id" );
    shapeRed = ElementHelper.FindElement( DRIVER, By.id( shapeId ) );
    assertEquals( "red", shapeRed.getAttribute( "fill" ) );
  }

  @AfterClass
  public static void tearDown() {
    //To use when class run all test cases.
  }
}
