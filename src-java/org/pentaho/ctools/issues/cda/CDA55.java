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
package org.pentaho.ctools.issues.cda;

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
import org.openqa.selenium.support.ui.Select;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDA-55
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-964
 *
 * NOTE
 * To test this script it is required to have CDA plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDA55 {
  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDA55.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDA55.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert running SQL query on KEttle with multiple parameters works
   * Description:
   *    The test pretends validate the CDA-55 issue, the when running a query with multiple parameters on Kettle
   *    with SQL Table Input works.
   *
   * Steps:
   *    1. Select "Sample query on SteelWheelsSales" on "dataAccessSelector"
   *    2. Wait for and assert elements and text on page
   *
   */
  @Test(timeout = 120000)
  public void tc01_CdaFileViewer_KettleMultipleParam() {
    log.info("tc01_CdaFileViewer_KettleMultipleParam");

    /*
     * ## Step 1
     */
    //Go to User Console
    driver.get(baseUrl + "plugin/cda/api/previewQuery?path=/public/Issues/CDA/CDA-55/sample-kettle-ParamArray.cda");

    WebElement element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("dataAccessSelector"));
    assertNotNull(element);
    Select select = new Select(ElementHelper.FindElement(driver, By.id("dataAccessSelector")));
    select.selectByVisibleText("Sample query on SteelWheelsSales");
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//button[@id='button']"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//button[@id='cachethis']"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//button[@id='queryUrl']"));
    assertNotNull(element);

    /*
     * ## Step 2
     */
    //wait to render page
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
    //Check the presented contains
    WebElement elemCountries = ElementHelper.FindElement(driver, By.id("countries"));
    assertEquals("France;USA", elemCountries.getAttribute("value"));
    WebElement elemCostumers = ElementHelper.FindElement(driver, By.id("Costumers"));
    assertEquals("103;112", elemCostumers.getAttribute("value"));
    //Check we have two elements and no more than that
    String textPaging = ElementHelper.WaitForElementPresentGetText(driver, By.id("contents_info"));
    assertEquals("View 1 to 2 of 2 elements", textPaging);
    //Check text on table
    String columnOneRowOne = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr/td"));
    String columnTwoRowOne = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr/td[2]"));
    assertEquals("103", columnOneRowOne);
    assertEquals("Atelier graphique", columnTwoRowOne);
    String columnOneRowTwo = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[2]/td"));
    String columnTwoRowTwo = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[2]/td[2]"));
    assertEquals("112", columnOneRowTwo);
    assertEquals("Signal Gift Stores", columnTwoRowTwo);

  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDA55.class.getSimpleName());
  }
}
