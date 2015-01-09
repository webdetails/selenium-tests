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
import org.openqa.selenium.WebElement;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-395
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-925
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDE395 {
  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDE395.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDE395.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  @Before
  public void setUpTestCase() {
    //Go to User Console
    driver.get(baseUrl + "Home");

    //wait for invisibility of waiting pop-up
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='busy-indicator-container waitPopup']"));

    //Wait for menus: filemenu, viewmenu, toolsmenu AND helpmenu
    ElementHelper.WaitForElementVisibility(driver, By.id("filemenu"));
    ElementHelper.WaitForElementVisibility(driver, By.id("viewmenu"));
    ElementHelper.WaitForElementVisibility(driver, By.id("toolsmenu"));
    ElementHelper.WaitForElementVisibility(driver, By.id("helpmenu"));
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Asserting new CDE dashboard has Bootstrap as renderer option
   *
   * Description:
   *    The test pretends validate the CDE-395 issue, so when user creates a new CDE
   *    dashboard it has the renderer option as Bootstrap by default.
   *
   * Steps:
   *    1. Assert elements on page and click "Create New" button
   *    2. Wait for options to load and then click "CDE Dashboard" button
   *    3. Wait for new Dashboard to be created, assert elements on page and click "Settings"
   *    4. Focus on popup, assert elements and assert Bootstrap option is selected by default
   */
  @Test(timeout = 120000)
  public void tc01_NewCdeDashboard_DefaultRendererBootstrap() {
    log.info("tc01_NewCdeDashboard_DefaultRendererBootstrap");

    /*
     * ## Step 1
     */
    //focus iframe
    WebElement elementFrame = ElementHelper.FindElement(driver, By.xpath("//iframe"));
    WebDriver frame = driver.switchTo().frame(elementFrame);

    //assert button text and click Create New
    String browseText = ElementHelper.GetText(frame, By.xpath("//div[@id='buttonWrapper']//button"));
    String createText = ElementHelper.GetText(frame, By.xpath("//div[@id='buttonWrapper']//button[2]"));
    String manageText = ElementHelper.GetText(frame, By.xpath("//div[@id='buttonWrapper']//button[3]"));
    String documentationText = ElementHelper.GetText(frame, By.xpath("//div[@id='buttonWrapper']//button[4]"));
    assertEquals("Browse Files", browseText);
    assertEquals("Create New", createText);
    assertEquals("Manage Data Sources", manageText);
    assertEquals("Documentation", documentationText);
    ElementHelper.Click(frame, By.id("btnCreateNew"));

    /*
     * ## Step 2
     */
    String analysisText = ElementHelper.GetText(frame, By.xpath("//div[@class='popover-content']/button[@id='createNewanalyzerButton']"));
    String interactiveText = ElementHelper.GetText(frame, By.xpath("//div[@class='popover-content']/button[@id='createNewinteractiveReportButton']"));
    String dashboardText = ElementHelper.GetText(frame, By.xpath("//div[@class='popover-content']/button[@id='createNewdashboardButton']"));
    String cdeText = ElementHelper.GetText(frame, By.xpath("//div[@class='popover-content']/button[@id='createNewlaunch_new_cdeButton']"));
    String dataText = ElementHelper.GetText(frame, By.xpath("//div[@class='popover-content']/button[@id='createNewdatasourceButton']"));
    assertEquals("Analysis Report", analysisText);
    assertEquals("Interactive Report", interactiveText);
    assertEquals("Dashboard", dashboardText);
    assertEquals("CDE Dashboard", cdeText);
    assertEquals("Data Source", dataText);
    ElementHelper.Click(frame, By.xpath("//div[@class='popover-content']/button[@id='createNewlaunch_new_cdeButton']"));

    /*
     * ## Step 3
     */
    //wait for invisibility of waiting pop-up
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='busy-indicator-container waitPopup']"));
    driver.switchTo().defaultContent();

    //assert title
    String titleText = ElementHelper.GetText(driver, By.xpath("//div[@title='New CDE Dashboard']"));
    assertEquals("CDE Dashboard", titleText);

    //focus iframe
    WebElement elementFrame1 = ElementHelper.FindElement(driver, By.xpath("//iframe"));
    WebDriver frame1 = driver.switchTo().frame(elementFrame1);

    //assert buttons and click Settings
    String newText = ElementHelper.GetText(frame1, By.xpath("//div[@id='headerLinks']/div/a"));
    String saveText = ElementHelper.GetText(frame1, By.xpath("//div[@id='headerLinks']/div[2]/a"));
    String saveasText = ElementHelper.GetText(frame1, By.xpath("//div[@id='headerLinks']/div[3]/a"));
    String reloadText = ElementHelper.GetText(frame1, By.xpath("//div[@id='headerLinks']/div[4]/a"));
    String settingsText = ElementHelper.GetText(frame1, By.xpath("//div[@id='headerLinks']/div[5]/a"));
    assertEquals("New", newText);
    assertEquals("Save", saveText);
    assertEquals("Save as...", saveasText);
    assertEquals("Reload", reloadText);
    assertEquals("Settings", settingsText);
    ElementHelper.Click(frame1, By.xpath("//div[@id='headerLinks']/div[5]/a"));

    /*
     * ## Step 4
     */
    WebElement obj1 = ElementHelper.FindElement(frame1, By.xpath("//select[@id='rendererInput']/option[@value='bootstrap']"));
    assertEquals(obj1.isSelected(), true);
  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDE395.class.getSimpleName());
  }
}
