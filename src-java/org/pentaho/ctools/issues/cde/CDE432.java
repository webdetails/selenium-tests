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
import static org.junit.Assert.assertNotNull;

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
import org.openqa.selenium.WebElement;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-432
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-997
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDE432 {
  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDE432.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDE432.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Left is the first option of TextAlign property
   *
   * Description:
   *    The test pretends validate the CDE-432 issue, so when user clicks down arrow when TextAlign property is selected, "Left" is the first option shown
   *
   * Steps:
   *    1. Open new CDE dashboard and assert elements on page
   *    2. Click "r", assert row was created and click "Text Align" property
   *    3. Click down arrow twice and click enter
   *    4. Assert "Left" option is selected for the "Text Align" property
   **/
  @Test(timeout = 120000)
  public void tc01_CdeDashboard_NoExtraOptions() {
    log.info("tc01_CdeDashboard_NoExtraOptions");

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
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr/td"));
    assertNotNull(element);
    String text = ElementHelper.WaitForTextPresence(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr/td"), "Row");
    assertEquals("Row", text);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//table[@id='table-cdfdd-layout-properties']/tbody/tr[5]/td[2]"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//table[@id='table-cdfdd-layout-properties']/tbody/tr[5]/td[2]"));

    /*
     * ## Step 3
     */
    try {
      robot = new Robot();
      robot.keyPress(KeyEvent.VK_DOWN);
      robot.keyRelease(KeyEvent.VK_DOWN);
      robot.keyPress(KeyEvent.VK_DOWN);
      robot.keyRelease(KeyEvent.VK_DOWN);
      robot.keyPress(KeyEvent.VK_ENTER);
      robot.keyRelease(KeyEvent.VK_ENTER);
      robot.keyPress(KeyEvent.VK_ENTER);
      robot.keyRelease(KeyEvent.VK_ENTER);
    } catch (AWTException e) {
      e.printStackTrace();
    }

    /*
     * ## Step 4
     */
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//table[@id='table-cdfdd-layout-properties']/tbody/tr[5]/td[2]"));
    assertNotNull(element);
    String align = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='table-cdfdd-layout-properties']/tbody/tr[5]/td[2]"));
    assertEquals("Left", align);

  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDE432.class.getSimpleName());
  }
}
