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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDA-108
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-970
 *
 * NOTE
 * To test this script it is required to have CDA plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDA108 {
  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDA108.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDA108.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  @Before
  public void setUpTestCase() {
    //Go to User Console
    driver.get(baseUrl + "plugin/cda/api/previewQuery?path=/public/plugin-samples/cda/cdafiles/xpath.cda");

    //Wait for buttons: button, Cache This AND Query URL
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//button[@id='button']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//button[@id='cachethis']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//button[@id='queryUrl']"));

  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Asserting query is working as expected
   * Description:
   *    The test pretends validate the CDA-108 issue, that xpath CDA sample is working as expected.
   *
   * Steps:
   *    1. Select "Sample query on SteelWheelsSales" on "dataAccessSelector"
   *    2. Wait for and assert elements and text on page
   *
   */
  @Test(timeout = 120000)
  public void tc01_CdaFileViewer_XpathSampleWorking() {
    log.info("tc01_CdaFileViewer_XpathSampleWorking");

    /*
     * ## Step 1
     */
    ElementHelper.WaitForElementPresence(driver, By.id("dataAccessSelector"));
    Select select = new Select(ElementHelper.FindElement(driver, By.id("dataAccessSelector")));
    select.selectByVisibleText("Sample query on SteelWheelsSales");

    /*
     * ## Step 2
     */
    //wait to render page
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
    //Check the presented contains
    WebElement elemStatus = ElementHelper.FindElement(driver, By.id("status"));
    assertEquals("In Process", elemStatus.getAttribute("value"));
    //Check we have three elements and no more than that
    String textPaging = ElementHelper.GetText(driver, By.id("contents_info"));
    assertEquals("View 1 to 1 of 1 elements", textPaging);
    //Check text on table
    String columnOneRowOne = ElementHelper.GetText(driver, By.xpath("//table[@id='contents']/tbody/tr/td"));
    String columnTwoRowOne = ElementHelper.GetText(driver, By.xpath("//table[@id='contents']/tbody/tr/td[2]"));
    assertEquals("103", columnOneRowOne);
    assertEquals("Atelier graphique", columnTwoRowOne);

  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDA108.class.getSimpleName());
  }
}
