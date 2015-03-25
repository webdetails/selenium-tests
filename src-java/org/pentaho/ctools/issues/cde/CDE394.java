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
package org.pentaho.ctools.issues.cde;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.lang3.math.NumberUtils;
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
 * - http://jira.pentaho.com/browse/CDE-394
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-979
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDE394 {
  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDE394.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDE394.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Asserting all dots of line chart stay inside container
   *
   * Description:
   *    The test pretends validate the CDE-394 issue, so with specific conditions that used to trigger
   *    an error, the dots of the line chart are all shown inside the container.
   *
   * Steps:
   *    1. Get width of Chart
   *    2. Get "cx" for rightmost dots
   *    3. Assert "cx" are smaller than chart's width
   */
  @Test(timeout = 120000)
  public void tc01_NewCdeDashboard_LineChartContained() {
    log.info("tc01_NewCdeDashboard_LineChartContained");

    /*
     * ## Step 1
     */
    //Go to Issue Sample
    driver.get(baseUrl + "api/repos/%3Apublic%3AIssues%3ACDE-394%3ACDE-394%25282%2529.wcdf/generatedContent");

    //wait for invisibility of waiting pop-up
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='busy-indicator-container waitPopup']"));

    //Wait for menus: filemenu, viewmenu, toolsmenu AND helpmenu
    ElementHelper.WaitForElementVisibility(driver, By.id("column1protovis"));
    WebElement element = ElementHelper.FindElement(driver, By.xpath("//div[contains(@id,'column1protovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='rect']"));
    assertNotNull(element);
    String widthText = element.getAttribute("width");
    double width = Double.parseDouble(widthText);

    /*
     * ## Step 2
     */
    element = ElementHelper.FindElement(driver, By.xpath("//div[contains(@id,'column1protovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][5]/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='circle'][14]"));
    assertNotNull(element);
    String cx1Text = element.getAttribute("cx");
    double cx1 = Double.parseDouble(cx1Text);
    element = ElementHelper.FindElement(driver, By.xpath("//div[contains(@id,'column1protovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][5]/*[local-name()='g']/*[local-name()='g'][6]/*[local-name()='circle'][14]"));
    assertNotNull(element);
    String cx2Text = element.getAttribute("cx");
    double cx2 = Double.parseDouble(cx2Text);
    element = ElementHelper.FindElement(driver, By.xpath("//div[contains(@id,'column1protovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][5]/*[local-name()='g']/*[local-name()='g'][9]/*[local-name()='circle'][14]"));
    assertNotNull(element);
    String cx3Text = element.getAttribute("cx");
    double cx3 = Double.parseDouble(cx3Text);

    /*
     * ## Step 3
     */
    assertEquals(width, NumberUtils.max(width, cx1, cx3), NumberUtils.max(width, cx2, cx3));

  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDE394.class.getSimpleName());
  }
}
