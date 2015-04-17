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
package org.pentaho.ctools.issues.cda;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDA-103
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-923
 *
 * NOTE
 * To test this script it is required to have CDA plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDA103 {
  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDA103.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDA103.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Asserting clearing CDA Cache returns info
   * Description:
   *    The test pretends validate the CDA-103 issue, so when CDA Cache
   *    is cleared the user receives confirmation that the Cache was cleared successfully
   *
   * Steps:
   *    1. Click 'Tools' menu
   *    2. Expand 'Refresh' option
   *    3. Click 'CDA Cache'
   *    4. Assert text confirming that the Cache was cleared
   */
  @Test(timeout = 120000)
  public void tc01_RefreshCdaCache_ReturnsInfo() {
    log.info("tc01_RefreshCdaCache_ReturnsInfo");

    /*
     * ## Step 1
     */
    //Go to User Console
    driver.get(baseUrl);

    //wait for invisibility of waiting pop-up
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='busy-indicator-container waitPopup']"));

    //Wait for menus: filemenu, viewmenu, toolsmenu AND helpmenu
    WebElement element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("filemenu"));
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("viewmenu"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("toolsmenu"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("helpmenu"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.id("toolsmenu"));

    /*
     * ## Step 2
     */
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("refreshmenu"));
    assertNotNull(element);
    String refreshMenuText = ElementHelper.WaitForElementPresentGetText(driver, By.id("refreshmenu"));
    assertEquals("Refresh", refreshMenuText);
    ElementHelper.ClickJS(driver, By.id("refreshmenu"));

    /*
     * ## Step 3
     */
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("cdaClearCache"));
    assertNotNull(element);
    String cdaClearCacheText = ElementHelper.WaitForElementPresentGetText(driver, By.id("cdaClearCache"));
    assertEquals("CDA Cache", cdaClearCacheText);
    ElementHelper.Click(driver, By.id("cdaClearCache"));

    /*
     * ## Step 4
     */
    //wait for invisibility of waiting pop-up
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='busy-indicator-container waitPopup']"));

    //Check tab title and text on iframe
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@title='CDA Cache']"));
    assertNotNull(element);
    String cdaTitleText = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@title='CDA Cache']"));
    assertEquals("CDA Cache", cdaTitleText);

    WebElement elementFrame = ElementHelper.FindElement(driver, By.xpath("//iframe"));
    WebDriver frame = driver.switchTo().frame(elementFrame);
    element = ElementHelper.WaitForElementPresenceAndVisible(frame, By.xpath("//pre"));
    assertNotNull(element);
    String cdaBodyText = ElementHelper.WaitForElementPresentGetText(frame, By.xpath("//pre"));
    assertEquals("Cache Cleared Successfully", cdaBodyText);
  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDA103.class.getSimpleName());
  }
}
