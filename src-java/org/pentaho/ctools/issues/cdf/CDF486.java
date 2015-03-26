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
 * - http://jira.pentaho.com/browse/CDF-486
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1028
 *
 * NOTE
 * To test this script it is required to have CDF plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDF486 {
  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDF486.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDF486.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert dashboardType=clean has desired effect.
   *
   * Description:
   *    Whe adding dashboardType=clean to dashboard's url it renders without any css associated.
   *
   * Steps:
   *    1. Open CDF blueprint sample and assert position of specific elements
   *    2. Open same sample adding clean option to URL and assert position of elements is different
   *
   */
  @Test(timeout = 120000)
  public void tc01_CdfDashboardType_CleanStyle() {
    log.info("tc01_CdfDashboardType_CleanStyle");

    /*
     * ## Step 1
     */
    //Go to New CDE Dashboard
    driver.get(baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A20-samples%3Ablueprint%3Ablueprint.xcdf/generatedContent");

    // Wait for loading disappear
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
    WebElement element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@class='panelTitle']/div[@class='container']/div[2]"));
    assertNotNull(element);
    int imagex = element.getLocation().x;
    int imagey = element.getLocation().y;
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("pieChart_object"));
    assertNotNull(element);
    element.getLocation();
    element.getLocation();
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("barChart_object"));
    assertNotNull(element);
    int barChartx = element.getLocation().x;
    int barCharty = element.getLocation().y;
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("dialChart_object"));
    assertNotNull(element);
    int dialChartx = element.getLocation().x;
    int dialCharty = element.getLocation().y;

    /*
     * ## Step 1
     */
    //Go to New CDE Dashboard
    driver.get(baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A20-samples%3Ablueprint%3Ablueprint.xcdf/generatedContent?dashboardType=clean");

    // Wait for loading disappear
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@class='panelTitle']/div[@class='container']/div[2]"));
    assertNotNull(element);
    int imagex1 = element.getLocation().x;
    int imagey1 = element.getLocation().y;
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("pieChart_object"));
    assertNotNull(element);
    element.getLocation();
    element.getLocation();
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("barChart_object"));
    assertNotNull(element);
    int barChartx1 = element.getLocation().x;
    int barCharty1 = element.getLocation().y;
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("dialChart_object"));
    assertNotNull(element);
    int dialChartx1 = element.getLocation().x;
    int dialCharty1 = element.getLocation().y;
    assertTrue(imagex != imagex1);
    assertTrue(imagey != imagey1);
    assertTrue(barChartx != barChartx1);
    assertTrue(barCharty != barCharty1);
    assertTrue(dialChartx != dialChartx1);
    assertTrue(dialCharty != dialCharty1);
  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDF486.class.getSimpleName());
  }
}
