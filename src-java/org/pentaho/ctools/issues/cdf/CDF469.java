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
package org.pentaho.ctools.issues.cdf;

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
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDF-469
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1026
 *
 * NOTE
 * To test this script it is required to have CDF plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDF469 {
  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDF469.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDF469.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert legends are shown only once
   *
   * Description:
   *    The test pretends validate the CDF-469 issue, when adding the measure name to the legend of a chart
   *    it shows once and only once.
   *
   * Steps:
   *    1. Open created dashboard and assert each legend is shown once and only once.
   */
  @Test(timeout = 120000)
  public void tc01_CCCProperties_MeasureOnLegend() {
    log.info("tc01_CCCProperties_MeasureOnLegend");

    /*
     * ## Step 1
     */
    //Open created dashboard
    driver.get(baseUrl + "api/repos/%3Apublic%3AIssues%3ACDF%3ACDF-469%3Acdf-469.wcdf/generatedContent");

    // Wait for loading disappear
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));

    //Assert legends are there once and only once
    WebElement element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[contains(@id,'chartObj')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']"));
    assertNotNull(element);
    String text = element.getText();
    assertEquals("London / Count", text);
    assertTrue(ElementHelper.WaitForElementNotPresent(driver, By.xpath("//div[contains(@id,'chartObj')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='text']"), 30));
    assertTrue(ElementHelper.WaitForElementNotPresent(driver, By.xpath("//div[contains(@id,'chartObj')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text'][2]"), 30));
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[contains(@id,'chartObj')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='text']"));
    assertNotNull(element);
    text = element.getText();
    assertEquals("Paris / Count", text);
    assertTrue(ElementHelper.WaitForElementNotPresent(driver, By.xpath("//div[contains(@id,'chartObj')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][3]/*[local-name()='text']"), 30));
    assertTrue(ElementHelper.WaitForElementNotPresent(driver, By.xpath("//div[contains(@id,'chartObj')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='text'][2]"), 30));
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[contains(@id,'chartObj')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g'][2]/*[local-name()='text']"));
    assertNotNull(element);
    text = element.getText();
    assertEquals("Lisbon / Count", text);
    assertTrue(ElementHelper.WaitForElementNotPresent(driver, By.xpath("//div[contains(@id,'chartObj')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g'][3]/*[local-name()='text']"), 30));
    assertTrue(ElementHelper.WaitForElementNotPresent(driver, By.xpath("//div[contains(@id,'chartObj')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g'][2]/*[local-name()='text'][2]"), 30));

  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDF469.class.getSimpleName());
  }
}
