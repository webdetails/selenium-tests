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
package org.pentaho.ctools.issues.cde;

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
 * - http://jira.pentaho.com/browse/CDE-438
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-999
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDE438 {
  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDE438.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDE438.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert CGG works properly
   *
   * Description:
   *    The test pretends validate the CDE-438 issue, so CGG sample renders correctly.
   *
   * Steps:
   *    1. Open Cgg Component sample and assert elements on page
   *    2. Assert first chart is rendered
   *    3. Assert second chart is rendered
   */
  @Test(timeout = 120000)
  public void tc01_CDEDashboard_CggComponentWorks() {
    log.info("tc01_CDEDashboard_CggComponentWorks");

    /*
     * ## Step 1
     */
    //Open CGG Component sample
    driver.get(baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3ACggComponent%3AcggComponent.wcdf/generatedContent");

    // NOTE - we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));

    WebElement element = ElementHelper.FindElement(driver, By.xpath("//div[@id='content']/div/div[2]/div"));
    assertNotNull(element);
    String title = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='content']/div/div[2]/div"));
    assertEquals("Cgg Component sample", title);
    element = ElementHelper.FindElement(driver, By.id("column1"));
    assertNotNull(element);
    element = ElementHelper.FindElement(driver, By.id("column2"));
    assertNotNull(element);

    /*
     * ## Step 2
     */
    element = ElementHelper.FindElement(driver, By.xpath("//div[@id='column1']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][1]"));
    assertNotNull(element);
    element = ElementHelper.FindElement(driver, By.xpath("//div[@id='column1']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][2]"));
    assertNotNull(element);
    element = ElementHelper.FindElement(driver, By.xpath("//div[@id='column1']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][3]"));
    assertNotNull(element);
    element = ElementHelper.FindElement(driver, By.xpath("//div[@id='column1']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][4]"));
    assertNotNull(element);
    element = ElementHelper.FindElement(driver, By.xpath("//div[@id='column1']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][5]"));
    assertNotNull(element);
    element = ElementHelper.FindElement(driver, By.xpath("//div[@id='column1']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][6]"));
    assertNotNull(element);
    element = ElementHelper.FindElement(driver, By.xpath("//div[@id='column1']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][7]"));
    assertNotNull(element);
    element = ElementHelper.FindElement(driver, By.xpath("//div[@id='column1']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][8]"));
    assertNotNull(element);
    element = ElementHelper.FindElement(driver, By.xpath("//div[@id='column1']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][9]"));
    assertNotNull(element);
    element = ElementHelper.FindElement(driver, By.xpath("//div[@id='column1']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][10]"));
    assertNotNull(element);
    element = ElementHelper.FindElement(driver, By.xpath("//div[@id='column1']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][11]"));
    assertNotNull(element);

    /*
     * ## Step 3
     */
    element = ElementHelper.FindElement(driver, By.xpath("//div[@id='column2']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][1]"));
    assertNotNull(element);
    element = ElementHelper.FindElement(driver, By.xpath("//div[@id='column2']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][2]"));
    assertNotNull(element);
    element = ElementHelper.FindElement(driver, By.xpath("//div[@id='column2']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][3]"));
    assertNotNull(element);
    element = ElementHelper.FindElement(driver, By.xpath("//div[@id='column2']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][4]"));
    assertNotNull(element);
    element = ElementHelper.FindElement(driver, By.xpath("//div[@id='column2']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][5]"));
    assertNotNull(element);
    element = ElementHelper.FindElement(driver, By.xpath("//div[@id='column2']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][6]"));
    assertNotNull(element);
    element = ElementHelper.FindElement(driver, By.xpath("//div[@id='column2']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][7]"));
    assertNotNull(element);

  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDE438.class.getSimpleName());
  }
}
