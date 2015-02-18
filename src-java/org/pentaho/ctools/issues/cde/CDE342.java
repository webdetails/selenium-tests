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
 * - http://jira.pentaho.com/browse/CDE-342
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-942
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDE342 {
  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDE342.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDE342.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  @Before
  public void setUpTestCase() {
    //Go to User Console
    driver.get(baseUrl + "api/repos/%3Apublic%3AIssues%3ACDE-342%3Atest_simple_ac.wcdf/generatedContent");

    //Wait for Input field
    ElementHelper.WaitForElementVisibility(driver, By.id("col1"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//input[@class='ui-autocomplete-input']"));
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert autocomplete works properly
   *
   * Description:
   *    The test pretends validate the CDE-342 issue, so when user writes on the autocomplete input field, the options
   *    available update accordingly.
   *
   * Steps:
   *    1. Write on input field and check options available
   *    2. Write something where no options should be available
   *    3. Write something where only one option should be available
   */
  @Test(timeout = 120000)
  public void tc01_CDEDashboard_AutocompleteWorks() {
    log.info("tc01_CDEDashboard_AutocompleteWorks");

    /*
     * ## Step 1
     */
    ElementHelper.FindElement(driver, By.xpath("//div[@id='col1']/input")).sendKeys("A");
    String option1 = ElementHelper.GetText(driver, By.xpath("//ul[@id='ui-id-1']/li/a"));
    String option2 = ElementHelper.GetText(driver, By.xpath("//ul[@id='ui-id-1']/li[2]/a"));
    String option3 = ElementHelper.GetText(driver, By.xpath("//ul[@id='ui-id-1']/li[3]/a"));
    String option4 = ElementHelper.GetText(driver, By.xpath("//ul[@id='ui-id-1']/li[4]/a"));
    String option5 = ElementHelper.GetText(driver, By.xpath("//ul[@id='ui-id-1']/li[5]/a"));
    assertEquals("Anna's Decorations, Ltd", option1);
    assertEquals("Australian Collectors, Co.", option2);
    assertEquals("Souveniers And Things Co.", option3);
    assertEquals("Australian Gift Network, Co", option4);
    assertEquals("Australian Collectables, Ltd", option5);

    /*
     * ## Step 2
     */
    ElementHelper.FindElement(driver, By.xpath("//div[@id='col1']/input")).clear();
    ElementHelper.FindElement(driver, By.xpath("//div[@id='col1']/input")).sendKeys("ert");
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//ul[@id='ui-id-1']/li/a"));
    ElementHelper.IsElementInvisible(driver, By.xpath("//ul[@id='ui-id-1']/li/a"));

    /*
     * ## Step 3
     */
    ElementHelper.FindElement(driver, By.xpath("//div[@id='col1']/input")).clear();
    ElementHelper.FindElement(driver, By.xpath("//div[@id='col1']/input")).sendKeys("Anna");
    option2 = ElementHelper.GetText(driver, By.xpath("//ul[@id='ui-id-1']/li/a"));
    assertEquals("Anna's Decorations, Ltd", option2);
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//ul[@id='ui-id-1']/li[2]/a"));
    ElementHelper.IsElementInvisible(driver, By.xpath("//ul[@id='ui-id-1']/li[2]/a"));

  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDE342.class.getSimpleName());
  }
}
