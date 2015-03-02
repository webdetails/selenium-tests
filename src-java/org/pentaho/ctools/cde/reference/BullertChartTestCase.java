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
package org.pentaho.ctools.cde.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
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
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * Testing the functionalities related with Bullert Chart test case.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BullertChartTestCase {
  // Instance of the driver (browser emulator)
  private static WebDriver       driver;
  //Instance to be used on wait commands
  private static Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private static String          baseUrl;
  //Log instance
  private static Logger          log                = LogManager.getLogger(BullertChartTestCase.class);

  @Rule
  public ScreenshotTestRule      screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + BullertChartTestCase.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    wait = CToolsTestSuite.getWait();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  @Before
  public void setUpTestCase() {
    //Go to AddinReference
    driver.get(baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3Accc_bullet.wcdf/generatedContent");

    //NOTE - we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    PageContent
   * Description:
   *    The test case pretends validate charts presented.
   * Steps:
   *    1. Check chart 1 - No data sent
   *    2. Check chart 2 - Returning one value only (65)
   *    3. Check chart 3 - Returning name and value
   *    4. Check chart 4 - Title, value and marker
   *    5. Check chart 5 - Complete dataset
   * @throws InterruptedException
   */
  @Test(timeout = 60000)
  public void tc01_ChartContent_DisplayedCorrect() throws InterruptedException {
    log.info("tc01_ChartContent_DisplayedCorrect");

    /*
     * ## Step 0
     */
    //Check page title
    wait.until(ExpectedConditions.titleIs("Community Dashboard Editor"));
    assertEquals("Community Dashboard Editor", driver.getTitle());
    //Check title
    String title = ElementHelper.WaitForElementPresentGetText(driver, By.cssSelector("#title > span"));
    assertEquals("Bullet chart test case", title);

    /*
     * ## Step 1
     */
    //Chart 1
    //Check title
    String subtitle1 = ElementHelper.WaitForElementPresentGetText(driver, By.cssSelector("#subtitle1 > span"));
    assertEquals("No data sent", subtitle1);
    //Check serie title and subtitle
    String cht1Title1 = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='obj1']/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][6]/*[local-name()='text']"));
    assertEquals("Title", cht1Title1);
    String cht1Subtitle1 = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='obj1']/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][7]/*[local-name()='text']"));
    assertEquals("No data", cht1Subtitle1);
    //Check chart
    WebElement cht1SizeBar = ElementHelper.FindElement(driver, By.xpath("//div[@id='obj1']/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='rect']"));
    assertNotNull(cht1SizeBar);
    assertEquals("258.77777777777777", cht1SizeBar.getAttribute("width"));
    WebElement cht1RectWhite1 = ElementHelper.FindElement(driver, By.xpath("//div[@id='obj1']/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='path'][1]"));
    WebElement cht1RectWhite2 = ElementHelper.FindElement(driver, By.xpath("//div[@id='obj1']/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='path'][2]"));
    assertNotNull(cht1RectWhite1);
    assertNotNull(cht1RectWhite2);
    assertEquals("translate(100.46666666666665,15) ", cht1RectWhite1.getAttribute("transform"));
    assertEquals("translate(200.9333333333333,15) ", cht1RectWhite2.getAttribute("transform"));

    /*
     * ## Step 2
     */
    //Chart 2
    //Check title
    String subtitle2 = ElementHelper.WaitForElementPresentGetText(driver, By.id("subtitle2"));
    assertEquals("Returning one value only (65)", subtitle2);
    //Check serie title and subtitle
    String cht2Title1 = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='obj2']/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][6]/*[local-name()='text']"));
    assertEquals("Value only", cht2Title1);
    String cht2Subtitle1 = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='obj2']/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][7]/*[local-name()='text']"));
    assertEquals("value is 65", cht2Subtitle1);
    //Check chart
    WebElement cht2SizeBar = ElementHelper.FindElement(driver, By.xpath("//div[@id='obj2']/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='rect']"));
    assertNotNull(cht2SizeBar);
    assertEquals("197.88888888888889", cht2SizeBar.getAttribute("width"));
    WebElement cht2RectWhite1 = ElementHelper.FindElement(driver, By.xpath("//div[@id='obj2']/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='path'][1]"));
    assertNotNull(cht2RectWhite1);
    assertEquals("translate(91.33333333333333,12.5) ", cht2RectWhite1.getAttribute("transform"));

    /*
     * ## Step 3
     */
    //Chart 3
    //Chart 31
    //Check title
    String subtitle3 = ElementHelper.WaitForElementPresentGetText(driver, By.id("subtitle3"));
    assertEquals("Returning name and value", subtitle3);
    //Check serie title and subtitle
    String cht31Title1 = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='obj3']/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][6]/*[local-name()='text']"));
    assertEquals("Atelier graphique", cht31Title1);
    String cht31Subtitle1 = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='obj3']/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][7]/*[local-name()='text']"));
    assertEquals("Customer", cht31Subtitle1);
    //Check chart
    WebElement cht31SizeBar = ElementHelper.FindElement(driver, By.xpath("//div[@id='obj3']/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='rect']"));
    assertNotNull(cht31SizeBar);
    assertEquals("208.6", cht31SizeBar.getAttribute("width"));
    WebElement cht31RectWhite1 = ElementHelper.FindElement(driver, By.xpath("//div[@id='obj3']/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='path'][1]"));
    assertNotNull(cht31RectWhite1);
    assertEquals("translate(596,10) ", cht31RectWhite1.getAttribute("transform"));
    //Chart 32
    //Check serie title and subtitle
    String cht32Title1 = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='obj3']/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][6]/*[local-name()='text']"));
    assertEquals("Signal Gift Stores", cht32Title1);
    String cht32Subtitle1 = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='obj3']/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][7]/*[local-name()='text']"));
    assertEquals("Customer", cht32Subtitle1);
    //Check chart
    WebElement cht32SizeBar = ElementHelper.FindElement(driver, By.xpath("//div[@id='obj3']/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='rect']"));
    assertNotNull(cht32SizeBar);
    assertEquals("713.2133333333334", cht32SizeBar.getAttribute("width"));
    WebElement cht32RectWhite1 = ElementHelper.FindElement(driver, By.xpath("//div[@id='obj3']/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][3]/*[local-name()='path'][1]"));
    assertNotNull(cht32RectWhite1);
    assertEquals("translate(596,10) ", cht32RectWhite1.getAttribute("transform"));
    //Chart 33
    //Check serie title and subtitle
    String cht33Title1 = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='obj3']/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g'][6]/*[local-name()='text']"));
    assertEquals("Australian Collectors, Co.", cht33Title1);
    String cht33Subtitle1 = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='obj3']/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g'][7]/*[local-name()='text']"));
    assertEquals("Customer", cht33Subtitle1);
    //Check chart
    WebElement cht33SizeBar = ElementHelper.FindElement(driver, By.xpath("//div[@id='obj3']/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g'][2]/*[local-name()='rect']"));
    assertNotNull(cht33SizeBar);
    assertEquals("894", cht33SizeBar.getAttribute("width"));
    WebElement cht33RectWhite1 = ElementHelper.FindElement(driver, By.xpath("//div[@id='obj3']/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g'][3]/*[local-name()='path'][1]"));
    assertNotNull(cht33RectWhite1);
    assertEquals("translate(457.2890025575448,10) ", cht33RectWhite1.getAttribute("transform"));

    /*
     * ## Step 4
     */
    Actions acts = new Actions(driver);
    acts.moveToElement(ElementHelper.FindElement(driver, By.cssSelector("div.webdetailsFooterWebdetails")));
    acts.perform();
    //Chart 4
    //Check title
    String subtitle4 = ElementHelper.WaitForElementPresentGetText(driver, By.id("subtitle4"));
    assertEquals("Title, value and marker", subtitle4);
    //Check serie title and subtitle
    String cht4Title1 = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='obj4']/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][6]/*[local-name()='text']"));
    assertEquals("Atelier graphique", cht4Title1);
    String cht4Subtitle1 = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='obj4']/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][7]/*[local-name()='text']"));
    assertEquals("Subtitle", cht4Subtitle1);
    //Check chart
    WebElement cht4SizeBar = ElementHelper.FindElement(driver, By.xpath("//div[@id='obj4']/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='rect']"));
    assertNotNull(cht4SizeBar);
    WebElement cht4RectWhite1 = ElementHelper.FindElement(driver, By.xpath("//div[@id='obj4']/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='path'][1]"));
    assertNotNull(cht4RectWhite1);

    /*
     * ## Step 5
     */
    //Chart 5
    //Check title
    String subtitle5 = ElementHelper.WaitForElementPresentGetText(driver, By.id("subtitle5"));
    assertEquals("Complete dataset", subtitle5);
    //Check serie title and subtitle
    String cht5Title1 = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='obj5']/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][6]/*[local-name()='text']"));
    assertEquals("Atelier graphique", cht5Title1);
    String cht5Subtitle1 = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='obj5']/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][7]/*[local-name()='text']"));
    assertEquals("Carine Schmitt", cht5Subtitle1);
    //Check chart
    WebElement cht5SizeBar = ElementHelper.FindElement(driver, By.xpath("//div[@id='obj5']/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='rect']"));
    assertNotNull(cht5SizeBar);
    WebElement cht5RectWhite1 = ElementHelper.FindElement(driver, By.xpath("//div[@id='obj5']/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='path'][1]"));
    assertNotNull(cht5RectWhite1);
  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + BullertChartTestCase.class.getSimpleName());
  }
}
