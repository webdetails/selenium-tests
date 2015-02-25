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
 * - http://jira.pentaho.com/browse/CDE-439
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-998
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDE439 {
  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDE439.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDE439.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert Duplicate Component Sample works properly
   *
   * Description:
   *    The test pretends validate the CDE-439 issue, so Duplicate Component Sample works correctly.
   *
   * Steps:
   *    1. Open Cgg Component sample and assert elements on page
   *    2. Assert first chart is rendered and click first "Duplicate" link
   *    3. Assert second chart is rendered and click second "Duplicate" link
   *    4. Assert third chart is rendered
   */
  @Test(timeout = 120000)
  public void tc01_CDEDashboard_DuplicateComponentWorks() {
    log.info("tc01_CDEDashboard_DuplicateComponentWorks");

    /*
     * ## Step 1
     */
    //Open CGG Component sample
    driver.get(baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3ADuplicateComponent%3AduplicateComponent.wcdf/generatedContent");

    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='content']/div/div[2]/div"));
    String title = ElementHelper.GetText(driver, By.xpath("//div[@id='content']/div/div[2]/div"));
    assertEquals("Duplicate Component sample", title);
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("column1"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("column2"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("column4"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='column1']//*[local-name()='g'][6]/*[local-name()='g']/*[local-name()='path']"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='column1']//*[local-name()='g'][6]/*[local-name()='g']/*[local-name()='path'][2]"));

    /*
     * ## Step 2
     */

    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='column2']/a"));
    ElementHelper.Click(driver, By.xpath("//div[@id='column2']/a"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("column1_1"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='column1_1']//*[local-name()='g'][6]/*[local-name()='g']/*[local-name()='path']"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='column1_1']//*[local-name()='g'][6]/*[local-name()='g']/*[local-name()='path'][2]"));

    /*
     * ## Step 3
     */
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='column4']/a"));
    ElementHelper.Click(driver, By.xpath("//div[@id='column4']/a"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("column1_2"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='column1_2']//*[local-name()='g'][6]/*[local-name()='g']/*[local-name()='path']"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='column1_2']//*[local-name()='g'][6]/*[local-name()='g']/*[local-name()='path'][2]"));

  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDE439.class.getSimpleName());
  }
}
