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

import static org.junit.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

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
 * - http://jira.pentaho.com/browse/CDE-425
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-995
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDE425 {
  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDE425.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDE425.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Shortcuts work
   *
   * Description:
   *    The test pretends validate the CDE-425 issue, so shortcuts referred on issue work.
   *
   * Steps:
   *    1. Open new CDE dashboard and assert elements on page
   *    2. Click "r" and assert new Row was created
   *    3. Click "c" and assert column was created
   *    4. Click "h" and assert html element was created
   *    5. Click row element and click "shift+d" and assert row was duplicated
   *    6. Click newly created row element and click "shift+x" and assert row was deleted
   **/
  @Test(timeout = 120000)
  public void tc01_CdeDashboard_LayoutShorcuts() {
    log.info("tc01_CdeDashboard_LayoutShortcuts");

    /*
     * ## Step 1
     */
    //Go to New CDE Dashboard
    driver.get(baseUrl + "api/repos/wcdf/new");

    //assert buttons
    ElementHelper.WaitForElementPresence(driver, By.xpath("//a[@title='Save as Template']"));
    ElementHelper.WaitForElementPresence(driver, By.xpath("//a[@title='Apply Template']"));
    ElementHelper.WaitForElementPresence(driver, By.xpath("//a[@title='Add Resource']"));
    ElementHelper.WaitForElementPresence(driver, By.xpath("//a[@title='Add Bootstrap Panel']"));
    ElementHelper.WaitForElementPresence(driver, By.xpath("//a[@title='Add FreeForm']"));
    ElementHelper.WaitForElementPresence(driver, By.xpath("//a[@title='Add Row']"));
    ElementHelper.WaitForElementPresence(driver, By.xpath("//div[@class='datasourcesPanelButton']"));
    ElementHelper.WaitForElementPresence(driver, By.id("previewButton"));
    ElementHelper.WaitForElementPresence(driver, By.xpath("//div[@class='layoutPanelButton']"));
    ElementHelper.WaitForElementPresence(driver, By.xpath("//div[@class='componentsPanelButton']"));
    ElementHelper.WaitForElementPresence(driver, By.xpath("//a[@title='Add Row']"));

    /*
     * ## Step 2
     */
    ElementHelper.Click(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody"));
    Robot robot;
    try {
      robot = new Robot();
      robot.keyPress(KeyEvent.VK_R);
      robot.keyRelease(KeyEvent.VK_R);
    } catch (AWTException e) {
      e.printStackTrace();
    }
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr/td"));
    ElementHelper.WaitForTextPresence(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr/td"), "Row");

    /*
     * ## Step 3
     */
    ElementHelper.Click(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr/td"));
    try {
      robot = new Robot();
      robot.keyPress(KeyEvent.VK_C);
      robot.keyRelease(KeyEvent.VK_C);
    } catch (AWTException e) {
      e.printStackTrace();
    }
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[2]/td"));
    ElementHelper.WaitForTextPresence(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[2]/td"), "Column");

    /*
     * ## Step 4
     */
    ElementHelper.Click(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[2]/td"));
    try {
      robot = new Robot();
      robot.keyPress(KeyEvent.VK_H);
      robot.keyRelease(KeyEvent.VK_H);
    } catch (AWTException e) {
      e.printStackTrace();
    }
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[3]/td"));
    ElementHelper.WaitForTextPresence(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[3]/td"), "Html");

    /*
     * ## Step 5
     */
    ElementHelper.Click(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr/td"));
    try {
      robot = new Robot();
      robot.keyPress(16);
      robot.keyPress(KeyEvent.VK_D);
      robot.keyRelease(KeyEvent.VK_D);
      robot.keyRelease(16);
    } catch (AWTException e) {
      e.printStackTrace();
    }
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[4]/td"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[5]/td"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]/td"));
    ElementHelper.Click(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr/td/span"));
    ElementHelper.Click(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[2]/td/span"));
    ElementHelper.WaitForTextPresence(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr/td"), "Row");
    ElementHelper.WaitForTextPresence(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[2]/td"), "Column");
    ElementHelper.WaitForTextPresence(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[3]/td"), "Html");
    ElementHelper.WaitForTextPresence(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[4]/td"), "Row");
    ElementHelper.WaitForTextPresence(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[5]/td"), "Column");
    ElementHelper.WaitForTextPresence(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]/td"), "Html");

    /*
     * ## Step 6
     */
    ElementHelper.Click(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr/td"));
    try {
      robot = new Robot();
      robot.keyPress(16);
      robot.keyPress(KeyEvent.VK_X);
      robot.keyRelease(KeyEvent.VK_X);
      robot.keyRelease(16);
    } catch (AWTException e) {
      e.printStackTrace();
    }
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[4]/td"));
    ElementHelper.WaitForTextPresence(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr/td"), "Row");
    ElementHelper.WaitForTextPresence(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[2]/td"), "Column");
    ElementHelper.WaitForTextPresence(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[3]/td"), "Html");
    assertTrue(!ElementHelper.IsElementNotPresent(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[4]/td"), 1));

  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDE425.class.getSimpleName());
  }
}
