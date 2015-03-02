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
 * - http://jira.pentaho.com/browse/CDE-399
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-966
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDE399 {
  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDE399.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDE399.class.getSimpleName());
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
   *    Check User can collapse Accordion menu on Components panel
   *
   * Description:
   *    The test pretends validate the CDE-399 issue, so when user clicks header of item of Accordion menu on
   *    "Components Panel" after it has been expanded it collapses.
   *
   * Steps:
   *    1. Wait for new Dashboard to be created, assert elements on page and click "Components Panel"
   *    2. Wait for Components panel to be shown and expand "Others"
   *    3. Wait for "Others" to expand and then click "Others" to collapse it, after that assert it has been collapsed
   */
  @Test(timeout = 120000)
  public void tc01_NewCdeDashboard_AccordionMenuCollapses() {
    log.info("tc01_NewCdeDashboard_AccordionMenuCollapses");

    /*
     * ## Step 1
     */
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@class='layoutPanelButton']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@class='componentsPanelButton']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@class='datasourcesPanelButton']"));
    ElementHelper.ClickJS(driver, By.xpath("//div[@class='componentsPanelButton']"));

    /*
     * ## Step 2
     */
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@id='cdfdd-components-palletePallete']/div[2]/h3/a"));
    String otherText = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='cdfdd-components-palletePallete']/div[2]/h3/a"));
    assertEquals("Others", otherText);
    ElementHelper.ClickJS(driver, By.xpath("//div[@id='cdfdd-components-palletePallete']/div[2]/h3/a"));

    /*
     * ## Step 3
     */
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='table Component']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@id='cdfdd-components-palletePallete']/div[2]/h3/a"));
    otherText = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='cdfdd-components-palletePallete']/div[2]/h3/a"));
    assertEquals("Others", otherText);
    ElementHelper.ClickJS(driver, By.xpath("//div[@id='cdfdd-components-palletePallete']/div[2]/h3/a"));
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//a[@title='table Component']"));

  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDE399.class.getSimpleName());
  }
}
