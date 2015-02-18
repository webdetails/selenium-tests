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
import org.junit.Before;
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
 * - http://jira.pentaho.com/browse/CDF-424
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-934
 *
 * NOTE
 * To test this script it is required to have CDF plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDF424 {
  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDF424.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDF424.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  @Before
  public void setUpTestCase() {

    //Go to New CDE Dashboard
    driver.get(baseUrl + "api/repos/%3Apublic%3AIssues%3ACDF-424%3ACDF-424.wcdf/generatedContent");

    // Wait for loading disappear
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));

    //assert Elements loaded
    ElementHelper.WaitForElementVisibility(driver, By.id("placeprotovis"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[contains(@id,'placeprotovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][4]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[contains(@id,'placeprotovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][4]/*[local-name()='g']/*[local-name()='g'][4]/*[local-name()='text']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[contains(@id,'placeprotovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][5]/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='text']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[contains(@id,'placeprotovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][5]/*[local-name()='g']/*[local-name()='g'][6]/*[local-name()='text']"));
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert CCC chart properties have proper priority level
   *
   * Description:
   *    The test pretends validate the CDF-424 issue, so when user sets ortho2AxisLabel_textStyle property in a chart it overrides axisLabel_textStyle for the ortho2 axis.
   *
   * Steps:
   *    1. Assert colour of axis elements is correct
   */
  @Test(timeout = 120000)
  public void tc01_CCCProperties_PrioritizedCorrectly() {
    log.info("tc01_CCCProperties_PrioritizedCorrectly");

    /*
     * ## Step 1
     */
    String axis1Text = ElementHelper.FindElement(driver, By.xpath("//div[contains(@id,'placeprotovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][4]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']")).getAttribute("fill");
    String axis2Text = ElementHelper.FindElement(driver, By.xpath("//div[contains(@id,'placeprotovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][4]/*[local-name()='g']/*[local-name()='g'][4]/*[local-name()='text']")).getAttribute("fill");
    String axis3Text = ElementHelper.FindElement(driver, By.xpath("//div[contains(@id,'placeprotovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][5]/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='text']")).getAttribute("fill");
    String axis4Text = ElementHelper.FindElement(driver, By.xpath("//div[contains(@id,'placeprotovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][5]/*[local-name()='g']/*[local-name()='g'][6]/*[local-name()='text']")).getAttribute("fill");
    assertEquals("rgb(0,0,255)", axis3Text);
    assertEquals("rgb(0,0,255)", axis4Text);
    assertEquals("rgb(255,0,0)", axis1Text);
    assertEquals("rgb(255,0,0)", axis2Text);

  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDF424.class.getSimpleName());
  }
}
