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
package org.pentaho.ctools.cdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Set;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * Testing the functionalities related with jFree Chart Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JFreeChartComponent{

  //Instance of the driver (browser emulator)
  private static WebDriver       driver;
  // Instance to be used on wait commands
  private static Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private static String          baseUrl;
  //Log instance
  private static Logger          log                = LogManager.getLogger(JFreeChartComponent.class);

  @Rule
  public ScreenshotTestRule      screenshotTestRule = new ScreenshotTestRule(driver);

  /**
   * Shall initialized the test before run each test case.
   */
  @BeforeClass
  public static void setUp() {
    log.info("setUp##" + JFreeChartComponent.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    wait = CToolsTestSuite.getWait();
    baseUrl = CToolsTestSuite.getBaseUrl();

    // Go to sample
    init();
  }

  /**
   * Go to the jFreeChartComponent web page.
   */
  public static void init() {
    // The URL for the CheckComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Documentation/Component
    // Reference/Core Components/jFreeChartComponent
    driver.get(baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A22-JFreeChartComponent%3Ajfreechart_component.xcdf/generatedContent");

    // Not we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Reload Sample
   * Description:
   *    Reload the sample (not refresh page).
   * Steps:
   *    1. Click in Code and then click in button 'Try me'.
   */
  @Test(timeout = 60000)
  public void tc1_PageContent_DisplayTitle() {
    log.info("tc1_PageContent_DisplayTitle");
    // Wait for title become visible and with value 'Community Dashboard Framework'
    wait.until(ExpectedConditions.titleContains("Community Dashboard Framework"));
    // Wait for visibility of 'VisualizationAPIComponent'
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dashboardContent']/div/div/div/h2/span[2]")));

    // Validate the sample that we are testing is the one
    assertEquals("Community Dashboard Framework", driver.getTitle());
    assertEquals("jFreeChartComponent", ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='dashboardContent']/div/div/div/h2/span[2]")));
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Reload Sample
   * Description:
   *    Reload the sample (not refresh page).
   * Steps:
   *    1. Click in Code and then click in button 'Try me'.
   */
  @Test(timeout = 60000)
  public void tc2_ReloadSample_SampleReadyToUse() {
    log.info("tc2_ReloadSample_SampleReadyToUse");
    // ## Step 1
    // Render again the sample
    ElementHelper.FindElement(driver, By.xpath("//div[@id='example']/ul/li[2]/a")).click();
    ElementHelper.FindElement(driver, By.xpath("//div[@id='code']/button")).click();

    // Not we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));

    // Now sample element must be displayed
    assertTrue(ElementHelper.FindElement(driver, By.id("sample")).isDisplayed());

    //Check the number of divs with id 'SampleObject'
    //Hence, we guarantee when click Try Me the previous div is replaced
    int nSampleObject = driver.findElements(By.id("sampleObject")).size();
    assertEquals(1, nSampleObject);
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Select Month
   * Description:
   *    The test case pretends to validate an alert is displayed after select
   *    a month and the alert displayed the selected month.
   * Steps:
   *    1. Open Pie Chart
   *    2. Click on chart
   *    3. Open Bar Chart
   *    4. Click on chart
   */
  @Test(timeout = 60000)
  public void tc3_ClickOnChart_AlertDisplayed() {
    log.info("tc3_ClickOnChart_AlertDisplayed");
    String title = "";

    WebElement img = ElementHelper.FindElement(driver, By.xpath("//img[@id='sampleObjectimage']"));
    String firstChart = img.getAttribute("src");

    // ## Step 1
    Actions acts = new Actions(driver);
    acts.moveToElement(ElementHelper.FindElement(driver, By.xpath("//div[contains(text(),'Details')]")));
    acts.perform();
    title = ElementHelper.WaitForElementPresentGetText(driver, By.id("sampleObjectcaptiontitle"));
    assertTrue(title.equals("Top 10 Customers"));
    ElementHelper.Click(driver, By.xpath("//div[@id='sampleObjectcaptionchartType']"));
    // Not we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
    //Check if the generated image is different from previews, is not something static
    assertNotNull(ElementHelper.FindElement(driver, By.xpath("//img[@id='sampleObjectimage']")));
    img = ElementHelper.FindElement(driver, By.xpath("//img[@id='sampleObjectimage']"));
    String secondChart = img.getAttribute("src");
    assertNotEquals(firstChart, secondChart);

    // ## Step 2
    //Click in 'Dragon Souveniers, Ltd.'
    ElementHelper.Click(driver, By.xpath("//map[@id='sampleObjectimageMap']/area[4]"));
    wait.until(ExpectedConditions.alertIsPresent());
    Alert alert = driver.switchTo().alert();
    String confirmationMsg = alert.getText();
    alert.accept();
    assertEquals("You clicked Dragon Souveniers, Ltd.", confirmationMsg);
    //Click in 'Mini Gifts Distributors Ltd'
    ElementHelper.FindElement(driver, By.xpath("//map[@id='sampleObjectimageMap']/area[9]")).click();
    wait.until(ExpectedConditions.alertIsPresent());
    alert = driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals("You clicked Mini Gifts Distributors Ltd.", confirmationMsg);

    // ## Step 3
    Actions acts2 = new Actions(driver);
    acts2.moveToElement(ElementHelper.FindElement(driver, By.xpath("//div[contains(text(),'Details')]")));
    acts2.perform();
    //Open the Pie Chart
    title = ElementHelper.WaitForElementPresentGetText(driver, By.id("sampleObjectcaptiontitle"));
    assertTrue(title.equals("Top 10 Customers"));
    ElementHelper.Click(driver, By.xpath("//div[@id='sampleObjectcaptionchartType']"));
    // Not we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
    //Check if the generated image is different from previews, is not something static
    assertNotNull(ElementHelper.FindElement(driver, By.xpath("//img[@id='sampleObjectimage']")));
    img = ElementHelper.FindElement(driver, By.xpath("//img[@id='sampleObjectimage']"));
    String thirdChart = img.getAttribute("src");
    assertNotEquals(firstChart, thirdChart);
    assertNotEquals(secondChart, thirdChart);

    // ## Step 3
    //Click in 'Australian Collectors, Co.'
    ElementHelper.FindElement(driver, By.xpath("//map[@id='sampleObjectimageMap']/area[8]")).click();
    wait.until(ExpectedConditions.alertIsPresent());
    alert = driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals("You clicked Australian Collectors, Co.", confirmationMsg);
    //Click in 'Down Under Souveniers, Inc'
    ElementHelper.FindElement(driver, By.xpath("//map[@id='sampleObjectimageMap']/area[5]")).click();
    wait.until(ExpectedConditions.alertIsPresent());
    alert = driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals("You clicked Down Under Souveniers, Inc", confirmationMsg);
  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name:
   *    Preview Chart
   * Description:
   *    The test case pretends to validate when user press on Zoom a new window
   *    is displayed.
   * Steps:
   *    1. Zoom on Bar Chart
   *    2. Zoom on Pie Chart
   */
  @Test(timeout = 120000)
  public void tc4_PreviewChart_NewWindowDisplayed() {
    log.info("tc4_PreviewChart_NewWindowDisplayed");
    String title = "";
    String attriStyle = "";

    // ## Step 1
    WebElement elemDetails = ElementHelper.FindElement(driver, By.xpath("//div[contains(text(),'Details')]"));
    Actions acts = new Actions(driver);
    acts.moveToElement(elemDetails);
    Action act = acts.build();
    act.perform();
    //Check bar title
    title = ElementHelper.WaitForElementPresentGetText(driver, By.id("sampleObjectcaptiontitle"));
    assertTrue(title.equals("Top 10 Customers"));
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='caption-bottom' and contains(@style,'margin: 0px')]"));
    //Check the bar is visible
    act.perform();
    attriStyle = ElementHelper.GetAttribute(driver, By.xpath("//div[@class='caption-bottom'][1]"), "style");
    assertFalse(attriStyle.contains("margin: 0px"));
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='caption-bottom' and contains(@style,'margin: 0px')]"));
    //Click in Zoom
    act.perform();
    ElementHelper.Click(driver, By.xpath("//div[@id='sampleObjectcaptionzoom']"));
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='caption-bottom' and contains(@style,'margin: 0px')]"));
    // Not we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));

    WebDriver popup = null;
    String parentWindowHandle = driver.getWindowHandle(); // save the current window handle.
    Set<String> setWindows = driver.getWindowHandles();
    //wait for popup render
    ElementHelper.WaitForNewWindow(driver);
    setWindows = driver.getWindowHandles();
    //Get popup id
    Iterator<String> windowIterator = setWindows.iterator();
    while(windowIterator.hasNext()) {
      String windowHandle = windowIterator.next();
      popup = driver.switchTo().window(windowHandle);
      if(popup.getTitle().isEmpty()) {
        break;
      }
    }

    WebElement popupImage = ElementHelper.FindElement(popup, By.cssSelector("img"));
    String attrSrcPopup = popupImage.getAttribute("src");

    try {
      URL url = new URL(attrSrcPopup);
      URLConnection connection = url.openConnection();
      connection.connect();

      assertEquals(HttpStatus.SC_OK, ((HttpURLConnection) connection).getResponseCode());
    }
    catch(Exception ex) {
      log.error(ex.getMessage());
    }

    popup.close();

    driver = driver.switchTo().window(parentWindowHandle);
    assertTrue(driver.getWindowHandles().size() == 1);

    // ## Step 2
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[contains(text(),'Details')]"));
    //Change to pie chart
    act.perform();
    ElementHelper.Click(driver, By.xpath("//div[@id='sampleObjectcaptionchartType']"));
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='caption-bottom' and contains(@style,'margin: 0px')]"));
    // NOTE - we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
    //Check bar title
    elemDetails = ElementHelper.FindElement(driver, By.xpath("//div[contains(text(),'Details')]"));
    acts.moveToElement(elemDetails);
    act = acts.build();
    act.perform();
    title = ElementHelper.WaitForElementPresentGetText(driver, By.id("sampleObjectcaptiontitle"));
    assertTrue(title.equals("Top 10 Customers"));
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='caption-bottom' and contains(@style,'margin: 0px')]"));
    //Check bar is visible
    act.perform();
    attriStyle = ElementHelper.GetAttribute(driver, By.xpath("//div[@class='caption-bottom'][1]"), "style");
    assertFalse(attriStyle.contains("margin: 0px"));
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='caption-bottom' and contains(@style,'margin: 0px')]"));
    //Zoom
    act.perform();
    ElementHelper.Click(driver, By.xpath("//div[@id='sampleObjectcaptionzoom']"));
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='caption-bottom' and contains(@style,'margin: 0px')]"));
    // NOTE - we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));

    parentWindowHandle = driver.getWindowHandle(); // save the current window handle.
    setWindows = driver.getWindowHandles();
    //wait for popup render
    ElementHelper.WaitForNewWindow(driver);
    setWindows = driver.getWindowHandles();
    //Get popup id
    windowIterator = setWindows.iterator();
    while(windowIterator.hasNext()) {
      String windowHandle = windowIterator.next();
      popup = driver.switchTo().window(windowHandle);
      if(popup.getTitle().isEmpty()) {
        break;
      }
    }

    popupImage = ElementHelper.FindElement(popup, By.cssSelector("img"));
    attrSrcPopup = popupImage.getAttribute("src");

    try {
      URL url = new URL(attrSrcPopup);
      URLConnection connection = url.openConnection();
      connection.connect();

      assertEquals(HttpStatus.SC_OK, ((HttpURLConnection) connection).getResponseCode());
    }
    catch(Exception ex) {
      log.error(ex.getMessage());
    }

    popup.close();
    driver.switchTo().window(parentWindowHandle);
    assertTrue(driver.getWindowHandles().size() == 1);
  }

  @AfterClass
  public static void tearDown() {
    log.debug("tearDown");

  }
}
