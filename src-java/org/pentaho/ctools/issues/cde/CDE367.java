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
 * - http://jira.pentaho.com/browse/CDE-367
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-941
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDE367 {
  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDE367.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDE367.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  @Before
  public void setUpTestCase() {

    //Go to New CDE Dashboard
    driver.get(baseUrl + "api/repos/wcdf/new");

    //assert buttons
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='Save as Template']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='Apply Template']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='Add Resource']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='Add Bootstrap Panel']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='Add FreeForm']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='Add Row']"));
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Edit colour of background by writing "#3d558c"
   *
   * Description:
   *    The test pretends validate the CDE-367 issue, so when user tries to edit the background colour of an
   *    element by pasting a string of a colour it works.
   *
   * Steps:
   *    1. Wait for new Dashboard to be created, assert elements on page and click "Add Row"
   *    2. Go to Properties, check the box for BackgroundColor and write "#3d558c" on input field
   *    3. Assert input field for "colorpicker_hex" has "3d558c"
   */
  @Test(timeout = 120000)
  public void tc01_CdeDashboard_ColorPickerPaste() {
    log.info("tc01_CdeDashboard_ColorPickerPaste");

    /*
     * ## Step 1
     */
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='Save as Template']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='Apply Template']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='Add Resource']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='Add Bootstrap Panel']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='Add FreeForm']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='Add Row']"));
    ElementHelper.Click(driver, By.xpath("//a[@title='Add Row']"));

    /*
     * ## Step 2
     */
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//form[@class='cdfddInput']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//form[@class='cdfddInput']/input[@class='colorcheck']"));
    ElementHelper.Click(driver, By.xpath("//form[@class='cdfddInput']/input[@class='colorcheck']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//form[@class='cdfddInput']/input[@checked='checked']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//form[@class='cdfddInput']/input[@class='colorinput']"));
    ElementHelper.Click(driver, By.xpath("//form[@class='cdfddInput']/input[@class='colorinput']"));
    ElementHelper.FindElement(driver, By.xpath("//form[@class='cdfddInput']/input[@class='colorinput']")).sendKeys("#3d558c");
    ElementHelper.Click(driver, By.xpath("//form[@class='cdfddInput']/input[@class='colorinput']"));

    /*
     * ## Step 3
     */
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@class='colorpicker']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@class='colorpicker']/div[@class='colorpicker_hex']"));
    String hexText = ElementHelper.GetInputValue(driver, By.xpath("//div[@class='colorpicker']/div[@class='colorpicker_hex']/input"));
    log.info(hexText);
    assertEquals("3d558c", hexText);

  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDE367.class.getSimpleName());
  }
}