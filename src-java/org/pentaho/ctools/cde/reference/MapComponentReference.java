/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2014 by Pentaho : http://www.pentaho.com
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

package org.pentaho.ctools.cde;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;

import static org.junit.Assert.*;

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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MapComponentReference {
  // Instance of the driver (browser emulator)
  private static WebDriver driver;
  // Instance to be used on wait commands
  private static Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private static String baseUrl;

  /**
   * Shall inicialized the test before run each test case.
   */
  @BeforeClass
  public static void setUp(){
    driver = CToolsTestSuite.getDriver();
    wait = CToolsTestSuite.getWait();
    baseUrl = CToolsTestSuite.getBaseUrl();

    //Go to sample
    init();
  }

  /**
   * Go to the TableComponent web page.
   */
  public static void init(){
    //The URL for the Map Component Reference under CDE samples
    //This sample is in: Public/plugin-samples/CDE/CDE Reference/Map Component Reference
    driver.get(baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3Amaps.wcdf/generatedContent");

    //Wait for visibility of 'Map Component Reference'
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='title']/span")));
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
   */
  @Test(timeout = 60000)
  public void tc1_PageContent_DisplayTitle() {
    // Validate the sample that we are testing is the one
    assertEquals("Community Dashboard Editor", driver.getTitle());
    ElementHelper.IsElementDisplayed(driver, By.xpath("//div[@id='title']/span"));
    assertEquals("Map Component Reference", driver.findElement(By.xpath("//div[@id='title']/span")).getText());

    //Loading popup
    //Wait until popup is present (the loading)
    ElementHelper.WaitForElementNotPresent(driver, 60, By.xpath("//div[@class='blockUI blockOverlay']"));
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
  @Test(timeout = 60000)
  public void tc2_MapWithNoMarkersAndShapes_MapMustBeVisible() throws Exception{
    //wait for inicialize
    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//div[@id='simpleTest']/div/div[8]/div"), "10000 km"));

    //## Step1
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='subtitle0']/span")));
    assertEquals("Map with no markers and no shapes - Simple Case", driver.findElement(By.xpath("//div[@id='subtitle0']/span")).getText());
    //Scale
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='simpleTest']/div/div[8]/div")));
    assertEquals("200 km", driver.findElement(By.xpath("//div[@id='simpleTest']/div/div[8]/div")).getText());
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='simpleTest']/div/div[8]/div[2]")));
    assertEquals("200 mi", driver.findElement(By.xpath("//div[@id='simpleTest']/div/div[8]/div[2]")).getText());
    //ButtonLayer
    assertNotNull(driver.findElement(By.xpath("//div[@id='simpleTest']/div/div[7]/div[2]/img")));
    //Get same images
    assertNotNull(driver.findElement(By.xpath("//div[@id='simpleTest']/div/div/div/img")));
    assertNotNull(driver.findElement(By.xpath("//div[@id='simpleTest']/div/div/div/img[12]")));

    //## Step2
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='simpleTest']/div/div[5]/div")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='simpleTest']/div/div[5]/div[2]")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='simpleTest']/div/div[5]/div[3]")));
    driver.findElement(By.xpath("//div[@id='simpleTest']/div/div[5]/div")).click();
    //wait for the field update
    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//div[@id='simpleTest']/div/div[8]/div"), "200 km"));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='simpleTest']/div/div[8]/div")));
    assertEquals("100 km", driver.findElement(By.xpath("//div[@id='simpleTest']/div/div[8]/div")).getText());
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='simpleTest']/div/div[8]/div[2]")));
    assertEquals("100 mi", driver.findElement(By.xpath("//div[@id='simpleTest']/div/div[8]/div[2]")).getText());

    //## Step3
    driver.findElement(By.xpath("//div[@id='simpleTest']/div/div[5]/div[2]")).click();
    //wait for the field update
    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//div[@id='simpleTest']/div/div[8]/div"), "100 km"));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='simpleTest']/div/div[8]/div")));
    assertEquals("10000 km", driver.findElement(By.xpath("//div[@id='simpleTest']/div/div[8]/div")).getText());
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='simpleTest']/div/div[8]/div[2]")));
    assertEquals("5000 mi", driver.findElement(By.xpath("//div[@id='simpleTest']/div/div[8]/div[2]")).getText());
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
  @Test(timeout = 60000)
  public void tc3_MapEngineAndTileServices_MapDisplayedAfterChanges() {
    //wait for inicialize
    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//div[@id='testTileServices']/div/div[8]/div"), "10000 km"));

    //## Step1
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='subtitle1']/span")));
    assertEquals("Map engine and map tile services", driver.findElement(By.xpath("//div[@id='subtitle1']/span")).getText());
    //mapEngine and service
    assertEquals("Select map engine:", driver.findElement(By.xpath("//div[@id='content']/div/div[6]/div/div/div")).getText());
    assertEquals("Select tile map service:", driver.findElement(By.xpath("//div[@id='content']/div/div[6]/div/div[2]/div")).getText());
    //Zoom
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='testTileServices']/div/div[5]/div")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='testTileServices']/div/div[5]/div[2]")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='testTileServices']/div/div[5]/div[3]")));
    //Scale
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='testTileServices']/div/div[8]/div")));
    assertEquals("200 km", driver.findElement(By.xpath("//div[@id='testTileServices']/div/div[8]/div")).getText());
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='testTileServices']/div/div[8]/div[2]")));
    assertEquals("200 mi", driver.findElement(By.xpath("//div[@id='testTileServices']/div/div[8]/div[2]")).getText());
    //Check if we are using OpenLayers
    String mapId = driver.findElement(By.xpath("//div[@id='testTileServices']/div")).getAttribute("id");
    assertTrue(mapId.contains("OpenLayers"));
    Select mapEngine = new Select(driver.findElement(By.xpath("//div[@id='selectMapEngineObj']/select")));
    Select mapService = new Select(driver.findElement(By.xpath("//div[@id='selectTileObj']/select")));
    assertEquals("openlayers", mapEngine.getFirstSelectedOption().getText());
    assertEquals("mapquest-sat", mapService.getFirstSelectedOption().getText());

    //## Step2
    mapEngine.selectByValue("google");
    ElementHelper.WaitForElementNotPresent(driver, 60, By.xpath("//div[@class='blockUI blockOverlay']"));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='testTileServices']/div[@class='gm-style']")));
    //Image with Google (left down corner)
    assertNotNull(driver.findElement(By.xpath("//div[@id='testTileServices']/div/div[2]/a/div/img")));
    //Text 'Termos de Utilização (righ down corner)
    String strMap = "Map";
    String strTerms = "Terms of Use";
    String strSat = "Satellite";

    assertEquals(strTerms, driver.findElement(By.xpath("//div[@id='testTileServices']/div/div[6]/div[2]/a")).getText());
    //check if we have mapquest-sat/Mapa/Satelite
    assertEquals("mapquest-sat", driver.findElement(By.xpath("//div[@id='testTileServices']/div/div[9]/div/div")).getText());
    assertEquals(strMap, driver.findElement(By.xpath("//div[@id='testTileServices']/div/div[9]/div[2]/div")).getText());
    assertEquals(strSat, driver.findElement(By.xpath("//div[@id='testTileServices']/div/div[9]/div[3]/div")).getText());

    //## Step3
    mapService.selectByValue("mapbox-world-dark");
    ElementHelper.WaitForElementNotPresent(driver, 60, By.xpath("//div[@class='blockUI blockOverlay']"));
    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//div[@id='testTileServices']/div/div[9]/div/div"), "mapquest-sat"));
    //Image with Google (left down corner)
    assertNotNull(driver.findElement(By.xpath("//div[@id='testTileServices']/div/div[2]/a/div/img")));
    //Text 'Termos de Utilização (righ down corner)
    assertEquals(strTerms, driver.findElement(By.xpath("//div[@id='testTileServices']/div/div[6]/div[2]/a")).getText());
    //check if we have mapbox-world-dark/Mapa/Satelite
    assertEquals("mapbox-world-dark", driver.findElement(By.xpath("//div[@id='testTileServices']/div/div[9]/div/div")).getText());
    assertEquals(strMap, driver.findElement(By.xpath("//div[@id='testTileServices']/div/div[9]/div[2]/div")).getText());
    assertEquals(strSat, driver.findElement(By.xpath("//div[@id='testTileServices']/div/div[9]/div[3]/div")).getText());
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
  @Test(timeout = 60000)
  public void tc4_MapWithMarkersBasedLongLat_MarkersShouldDisplayCorrectContents() throws Exception{
    ((JavascriptExecutor) driver).executeScript("window.onerror = function(){alert('an error');} ");

    //## Step1
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='subtitle2']/span")));
    assertEquals("Map with Markers based on Long/Lat and attached descriptions and Click Function enabled", driver.findElement(By.xpath("//div[@id='subtitle2']/span")).getText());
    assertNotNull(driver.findElement(By.id("testWithMarker")));
    assertEquals("200 km", driver.findElement(By.xpath("//div[@id='testWithMarker']/div/div[8]/div")).getText());
    assertEquals("200 mi", driver.findElement(By.xpath("//div[@id='testWithMarker']/div/div[8]/div[2]")).getText());

    //Check if we have markers (only three)
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='testWithMarker']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[name()='g'][3]/*[local-name()='g']/*[local-name()='image']")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='testWithMarker']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[name()='g'][3]/*[local-name()='g']/*[local-name()='image'][2]")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='testWithMarker']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[name()='g'][3]/*[local-name()='g']/*[local-name()='image'][3]")));

    //## Step2
    /* TODO - waiting to the issue CDE-295 to be closed

    Actions actions = new Actions(driver);
    WebElement firstMarker = driver.findElement(By.xpath("//div[@id='testWithMarker']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='image'][2]"));
    actions.click(firstMarker).build();
    actions.perform();

    Thread.sleep(30000);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='testWithMarker']/div/div/div[6]/div/div/div")));
    System.out.println("lol: " + driver.findElement(By.xpath("//div[@id='HiddenContentCol']")).getText());
    assertEquals("Australian Collectors, Co.", driver.findElement(By.xpath("//div[@id='HiddenContentCol']")).getText());
    Thread.sleep(30000);
    //close popup
    driver.findElement(By.xpath("//div[@id='testWithMarker']/div/div/div[6]/div/div[2]")).click();
    //click in 'Australian Collectors, Co.'
    actions.click(driver.findElement(By.xpath("//div[@id='testWithMarker']/div/div/div[5]/*[name()='svg']/*[name()='g'][3]/*[name()='g']/*[name()='image'][2]")));
    actions.build().perform();
    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//div[@id='testWithMarker']/div/div/div[6]/div/div/div"), "Australian Collectors, Co."));
    assertEquals("Atelier Graphique", driver.findElement(By.xpath("//div[@id='testWithMarker']/div/div/div[6]/div/div/div")).getText());
    //close popup
    driver.findElement(By.xpath("//div[@id='testWithMarker']/div/div/div[6]/div/div[2]")).click();
    //click in 'Signal Gift Stores'
    actions.click(driver.findElement(By.xpath("//div[@id='testWithMarker']/div/div/div[5]/*[name()='svg']/*[name()='g'][3]/*[name()='g']/*[name()='image'][2]")));
    actions.build().perform();
    wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//div[@id='testWithMarker']/div/div/div[6]/div/div/div"), "Atelier Graphique"));
    assertEquals("Signal Gift Stores", driver.findElement(By.xpath("//div[@id='testWithMarker']/div/div/div[6]/div/div/div")).getText());
    //close popup
    driver.findElement(By.xpath("//div[@id='testWithMarker']/div/div/div[6]/div/div[2]")).click();
    */
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
  @Test(timeout = 60000)
  public void tc5_MapWithMarkersBasedCity_MarkersShouldDisplayCorrectContents() {
    //## Step1
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='subtitle3']/span")));
    assertEquals("Map with markers based on city names and popup windows on click", driver.findElement(By.xpath("//div[@id='subtitle3']/span")).getText());
    assertNotNull(driver.findElement(By.id("testWithGeoLocalization")));
    assertEquals("2000 km", driver.findElement(By.xpath("//div[@id='testWithGeoLocalization']/div/div[8]/div")).getText());
    assertEquals("1000 mi", driver.findElement(By.xpath("//div[@id='testWithGeoLocalization']/div/div[8]/div[2]")).getText());

    //Check if we have markers (only three)
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='testWithGeoLocalization']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[name()='g'][3]/*[local-name()='g']/*[local-name()='image']")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='testWithGeoLocalization']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[name()='g'][3]/*[local-name()='g']/*[local-name()='image'][2]")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='testWithGeoLocalization']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[name()='g'][3]/*[local-name()='g']/*[local-name()='image'][3]")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='testWithGeoLocalization']/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[name()='g'][3]/*[local-name()='g']/*[local-name()='image'][4]")));
  }

  /**
   * ############################### Test Case 6 ###############################
   *
   * Test Case Name:
   *    Map with Shapes
   * Description:
   *    Check if the map has shapes and by click in a known shape must change
   *    its colour.
   * Steps:
   *    1. Check the data exist
   *    2. Chick in a shape country
   *    3. Chick country with no shape
   */
  @Test(timeout = 60000)
  public void tc6_MapWithShapes_ShapesAreClickable() {
    //## Step1
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='subtitle4']/span")));
    assertEquals("Map with Shapes (population per km2, top 100 countries)", driver.findElement(By.xpath("//div[@id='subtitle4']/span")).getText());
    assertNotNull(driver.findElement(By.id("testWithShapes")));
    assertEquals("2000 km", driver.findElement(By.xpath("//div[@id='testWithShapes']/div/div[8]/div")).getText());
    assertEquals("1000 mi", driver.findElement(By.xpath("//div[@id='testWithShapes']/div/div[8]/div[2]")).getText());

    //## Step2
    //## Step3
  }

  @AfterClass
  public static void tearDown() {}
}
