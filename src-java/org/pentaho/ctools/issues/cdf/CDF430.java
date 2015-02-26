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
package org.pentaho.ctools.issues.cdf;

import static org.junit.Assert.assertEquals;

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
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDF-430
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1007
 *
 * NOTE
 * To test this script it is required to have CDF plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDF430 {
  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDF430.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDF430.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert i18n messages are prioritized correctly
   *
   * Description:
   *    The test pretends validate the CDF-430 issue, so  i18n messages are prioritized correctly for CDE, CDF and Sparkl dashboards.
   *
   * Steps:
   *    1. Open created Sparkl sample dashboard and assert text is displayed as expected
   *    2. Open created CDE sample dashboard and assert text is displayed as expected
   *    3. Open created CDF sample dashboard and assert text is displayed as expected
   *
   */
  @Test(timeout = 120000)
  public void tc01_i18nMessages_PioritizedCorrectly() {
    log.info("tc01_i18nMessages_PioritizedCorrectly");

    /*
     * ## Step 1
     */
    //Go to Sparkl sample
    driver.get(baseUrl + "plugin/CDE404/api/i18ntest");

    // Wait for loading disappear
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));

    //assert Elements loaded
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("Panel_1"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("Panel_2"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("Panel_3"));
    String text = ElementHelper.GetText(driver, By.id("Panel_1"));
    assertEquals(text, "my message 1, coming from messages.properties");
    text = ElementHelper.GetText(driver, By.id("Panel_2"));
    assertEquals(text, "my message 2, overriden by messages_en.properties");
    text = ElementHelper.GetText(driver, By.id("Panel_3"));
    assertEquals(text, "my message 3, overriden by messages_en-US.properties");

    /*
     * ## Step 2
     */
    //Go to CDE sample
    driver.get(baseUrl + "api/repos/%3Apublic%3AIssues%3ACDF-430%3ACDE%3Ai18nTest.wcdf/generatedContent");

    // Wait for loading disappear
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));

    //assert Elements loaded
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("Panel_1"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("Panel_2"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("Panel_3"));
    text = ElementHelper.GetText(driver, By.id("Panel_1"));
    assertEquals(text, "my message 1, coming from messages.properties");
    text = ElementHelper.GetText(driver, By.id("Panel_2"));
    assertEquals(text, "my message 2, overriden by messages_en.properties");
    text = ElementHelper.GetText(driver, By.id("Panel_3"));
    assertEquals(text, "my message 3, overriden by messages_en-US.properties");

    /*
     * ## Step 3
     */
    //Go to CDE sample
    driver.get(baseUrl + "api/repos/%3Apublic%3AIssues%3ACDF-430%3ACDF%3Acdf_i18nTest.xcdf/generatedContent");

    // Wait for loading disappear
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));

    //assert Elements loaded
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='sampleButton01']/button/span"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='sampleButton02']/button/span"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='sampleButton03']/button/span"));
    text = ElementHelper.GetText(driver, By.xpath("//div[@id='sampleButton01']/button/span"));
    assertEquals(text, "My button 01 label");
    text = ElementHelper.GetText(driver, By.xpath("//div[@id='sampleButton02']/button/span"));
    assertEquals(text, "messages_en button 02 label");
    text = ElementHelper.GetText(driver, By.xpath("//div[@id='sampleButton03']/button/span"));
    assertEquals(text, "messages_en-US button 03 label");

  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDF430.class.getSimpleName());
  }
}
