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
 * - http://jira.pentaho.com/browse/CDE-402
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-927
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDE402 {
  // Instance of the driver (browser emulator)
  private static WebDriver  driver;

  // The base url to be append the relative url in test
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDE402.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDE402.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  @Before
  public void setUpTestCase() {
    //Go to User Console
    driver.get(baseUrl + "Home");

    //wait for invisibility of waiting pop-up
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='busy-indicator-container waitPopup']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//iframe[@id='home.perspective']"));

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
   *    Check ability to apply template to new CDE Dashboard
   *
   * Description:
   *    The test pretends validate the CDE-396 issue, so when user tries to apply a template
   *    to a new CDE dashboard, CDE asks for confirmation and then applies the template.
   *
   * Steps:
   *    1. Assert elements on page and click "Create New" button
   *    2. Wait for options to load and then click "CDE Dashboard" button
   *    3. Wait for new Dashboard to be created, assert elements on page and click "Apply template"
   *    4. Wait for options to appear and select a template
   *    5. Wait for confirmation of command and confirm
   *    6. Assert that selected template was applied
   */
  @Test(timeout = 15000)
  public void tc01_NewCdeDashboard_ApplyTemplate() {
    log.info("tc01_NewCdeDashboard_ApplyTemplate");

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
    ElementHelper.ClickJS(frame, By.id("btnCreateNew"));

    /*
     * ## Step 2
     */
    String cdeText = ElementHelper.GetText(frame, By.xpath("//div[@class='popover-content']/button[@id='createNewlaunch_new_cdeButton']"));
    String dataText = ElementHelper.GetText(frame, By.xpath("//div[@class='popover-content']/button[@id='createNewdatasourceButton']"));
    assertEquals("CDE Dashboard", cdeText);
    assertEquals("Data Source", dataText);
    ElementHelper.ClickJS(frame, By.xpath("//div[@class='popover-content']/button[@id='createNewlaunch_new_cdeButton']"));

    /*
     * ## Step 3
     */
    //wait for invisibility of waiting pop-up
    driver.switchTo().defaultContent();
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='busy-indicator-container waitPopup']"));

    /*try {
      Thread.sleep(10000);
    } catch (Exception e) {
      log.info("olda!!!");
    }*/

    //assert title
    String titleText = ElementHelper.GetText(driver, By.xpath("//div[@title='New CDE Dashboard']"));
    assertEquals("CDE Dashboard", titleText);

    //focus iframe
    //driver = driver.switchTo().defaultContent();
    elementFrame = ElementHelper.FindElement(driver, By.xpath("//td/..//iframe"));
    driver.switchTo().frame(elementFrame);

    //assert buttons and click Apply template
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='Save as Template']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='Apply Template']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='Add Resource']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='Add Bootstrap Panel']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='Add FreeForm']"));
    ElementHelper.Click(driver, By.xpath("//a[@title='Apply Template']"));

    /*
     * ## Step 4
     */

    ElementHelper.WaitForElementPresence(driver, By.id("popupTemplatebox"));
    ElementHelper.WaitForElementPresence(driver, By.id("thumbs"));
    ElementHelper.WaitForElementVisibility(driver, By.id("popupTemplatebox"));
    ElementHelper.WaitForElementVisibility(driver, By.id("thumbs"));
    String templateText = ElementHelper.FindElement(driver, By.xpath("//div[@id='thumbs']/div[2]/p")).getText();
    assertEquals("Two Columns Template", templateText);
    ElementHelper.ClickJS(driver, By.xpath("//div[@id='thumbs']/div[2]/p"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@id='thumbs']/div[@class='active']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@class='popupTemplatebuttons']/button[@id='popupTemplate_state0_buttonOk']"));
    ElementHelper.Click(driver, By.xpath("//div[@class='popupTemplatebuttons']/button[@id='popupTemplate_state0_buttonOk']"));

    /*
     * ## Step 5
     */
    String warningText = ElementHelper.GetText(driver, By.xpath("//div[@class='popupTemplatemessage']"));
    assertEquals("Are you sure you want to load the template?\n\nWARNING: Dashboard Layout will be overwritten!", warningText);
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@class='popupTemplatebuttons']/button[@id='popupTemplate_state0_buttonOk']"));
    ElementHelper.Click(driver, By.xpath("//div[@class='popupTemplatebuttons']/button[@id='popupTemplate_state0_buttonOk']"));

    /*
     * ## Step 6
     */
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[5]/td/span"));
    ElementHelper.Click(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[5]/td/span"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]"));
    String tr6tdText = ElementHelper.GetText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]/td"));
    String tr6td2Text = ElementHelper.GetText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]/td[2]"));
    String tr7tdText = ElementHelper.GetText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]/td"));
    String tr7td2Text = ElementHelper.GetText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]/td[2]"));
    assertEquals("Column", tr6tdText);
    assertEquals("Panel_1", tr6td2Text);
    assertEquals("Column", tr7tdText);
    assertEquals("Panel_2", tr7td2Text);
  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDE402.class.getSimpleName());
  }
}
