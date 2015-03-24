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
 * - http://jira.pentaho.com/browse/CDF-474
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1027
 *
 * NOTE
 * To test this script it is required to have CDF plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDF474 {
  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDF474.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDF474.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert autoWidth property of select2 plugin works as expected.
   *
   * Description:
   *    Assert drop down's width matches expected.
   *
   * Steps:
   *    1. Open Issue's prepared sample, click to expand drop down and assert it's style
   */
  @Test(timeout = 120000)
  public void tc01_SelectComponent_Select2AutoWidth() {
    log.info("tc01_SelectComponent_Select2AutoWidth");

    /*
     * ## Step 1
     */
    //Go to New CDE Dashboard
    driver.get(baseUrl + "api/repos/:public:Issues:CDF-474:CDF-474.wcdf/generatedContent");

    // Wait for loading disappear
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));

    WebElement element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='col1']/div/a/span[2]/b"));
    assertNotNull(element);
    element.click();
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("select2-drop"));
    assertNotNull(element);
    String text = element.getAttribute("style");
    //assertEquals("left: 88px; width: 64px; top: 26px; bottom: auto; display: block;", text);
    assertTrue(text.contains("width: 64px;"));
    //ElementHelper.Click(driver, By.xpath("//div[@id='select2-drop']/ul/li[2]/div/span"));
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='col2']/div/a/span[2]/b"));
    assertNotNull(element);
    element.click();
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("select2-drop"));
    assertNotNull(element);
    String text1 = element.getAttribute("style");
    assertTrue(text != text1);
  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDF474.class.getSimpleName());
  }
}
