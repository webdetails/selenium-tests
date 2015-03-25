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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-408
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-937
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDE408 {
  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDE408.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDE408.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Check existence of Scrollers on Table Component's Advanced Properties
   *
   * Description:
   *    The test pretends validate the CDE-408 issue, so when user navigates to the Advanced Properties
   *    of a Table Component a scroller is added to the properties panel.
   *
   * Steps:
   *    1. Wait for new Dashboard to be created, assert elements on page and click "Components Panel"
   *    2. Wait for Components panel to be shown, expand "Others" and click "Table COmponent"
   *    3. Wait for table Component to be added and then click "Advanced Properties"
   *    4. Assert existence of scroller
   */
  @Test(timeout = 120000)
  public void tc01_NewCdeDashboard_AddedScrollers() {
    log.info("tc01_NewCdeDashboard_AddedScrollers");

    /*
     * ## Step 1
     */

    //Go to New CDE Dashboard
    driver.get(baseUrl + "api/repos/wcdf/new");

    //assert buttons
    WebElement element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//a[@title='Save as Template']"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//a[@title='Apply Template']"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//a[@title='Add Resource']"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//a[@title='Add Bootstrap Panel']"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//a[@title='Add FreeForm']"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//a[@title='Add Row']"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@class='layoutPanelButton']"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@class='componentsPanelButton']"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@class='datasourcesPanelButton']"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//div[@class='componentsPanelButton']"));

    /*
     * ## Step 2
     */
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='cdfdd-components-palletePallete']/div[2]/h3/a"));
    assertNotNull(element);
    String otherText = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='cdfdd-components-palletePallete']/div[2]/h3/a"));
    assertEquals("Others", otherText);
    ElementHelper.Click(driver, By.xpath("//div[@id='cdfdd-components-palletePallete']/div[2]/h3/a"));
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//a[@title='table Component']"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//a[@title='table Component']"));

    /*
     * ## Step 3
     */
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='cdfdd-components-properties']/div/div/div[3]"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//div[@id='cdfdd-components-properties']/div/div/div[3]"));

    /*
     * ## Step 4
     */
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='cdfdd-components-properties']//tr[39]/td/div[2]"));
    assertNotNull(element);
    String posText = "var pos = document.getElementsByClassName('scrollContainer')[3].scrollTop;var height = document.getElementsByClassName('scrollContainer')[3].scrollHeight;document.getElementsByClassName('scrollContainer')[3].scrollTop=height;return pos";
    String pos = ((JavascriptExecutor) driver).executeScript(posText).toString();
    String posText1 = "return document.getElementsByClassName('scrollContainer')[3].scrollTop;";
    String pos1 = ((JavascriptExecutor) driver).executeScript(posText1).toString();
    assertThat(pos, not(equalTo(pos1)));

  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDE408.class.getSimpleName());
  }
}
