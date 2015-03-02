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
package org.pentaho.ctools.issues.cda;

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
 * - http://jira.pentaho.com/browse/CDA-46
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-944
 *
 * NOTE
 * To test this script it is required to have CDA plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDA46 {
  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDA46.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDA46.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  @Before
  public void setUpTestCase() {
    //Go to User Console
    driver.get(baseUrl + "plugin/cda/api/previewQuery?path=/public/plugin-samples/cda/cdafiles/compoundJoin.cda");

    //Wait for buttons: button, Cache This AND Query URL
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//button[@id='button']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//button[@id='cachethis']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//button[@id='queryUrl']"));

  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Asserting number beside "Data Access" has info button
   * Description:
   *    The test pretends validate the CDA-46 issue, the field beside "Data Access" has, when clicked, a info button
   *    that opens a popup with information regarding said field.
   *
   * Steps:
   *    1. Click number beside "Data Access"
   *    2. Wait for info button to appear and then click it
   *    3. Wait for popup and the assert text on it
   *
   */
  @Test(timeout = 120000)
  public void tc01_CdaFileViewer_OutputInfo() {
    log.info("tc01_CdaFileViewer_OutputInfo");

    /*
     * ## Step 1
     */
    ElementHelper.WaitForElementPresence(driver, By.id("outputIndexId"));
    ElementHelper.FindElement(driver, By.id("outputIndexId")).click();
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@class='helpButton helpButtonShort']"));
    ElementHelper.ClickJS(driver, By.xpath("//div[@class='helpButton helpButtonShort']"));

    /*
     * ## Step 2
     */
    ElementHelper.WaitForElementPresence(driver, By.xpath("//div[@id='outputIndexHelp']"));
    ElementHelper.WaitForElementPresence(driver, By.xpath("//div[@id='outputIndexHelp']/p"));
    ElementHelper.WaitForElementPresence(driver, By.xpath("//div[@id='outputIndexHelp']/p[2]"));
    String p1Text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='outputIndexHelp']/p"));
    String p2Text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='outputIndexHelp']/p[2]"));
    assertEquals("Output Index Id", p1Text);
    assertEquals("This Id is used to select the desired set of Output Options for the current Data Access.", p2Text);

  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDA46.class.getSimpleName());
  }
}
