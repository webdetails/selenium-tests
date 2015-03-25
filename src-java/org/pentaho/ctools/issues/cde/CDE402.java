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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
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
  @Test(timeout = 60000)
  public void tc01_NewCdeDashboard_ApplyTemplate() {
    log.info("tc01_NewCdeDashboard_ApplyTemplate");

    /*
     * ## Step 1
     */
    //Go to User Console
    driver.get(baseUrl + "Home");

    //wait for invisibility of waiting pop-up
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='busy-indicator-container waitPopup']"));
    WebElement element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//iframe[@id='home.perspective']"));
    assertNotNull(element);

    //Wait for menus: filemenu, viewmenu, toolsmenu AND helpmenu
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("filemenu"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("viewmenu"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("toolsmenu"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("helpmenu"));
    assertNotNull(element);

    //focus iframe
    WebElement elementFrame = ElementHelper.FindElement(driver, By.xpath("//iframe"));
    WebDriver frame = driver.switchTo().frame(elementFrame);

    //assert button text and click Create New
    String browseText = ElementHelper.WaitForElementPresentGetText(frame, By.xpath("//div[@id='buttonWrapper']//button"));
    String createText = ElementHelper.WaitForElementPresentGetText(frame, By.xpath("//div[@id='buttonWrapper']//button[2]"));
    String manageText = ElementHelper.WaitForElementPresentGetText(frame, By.xpath("//div[@id='buttonWrapper']//button[3]"));
    String documentationText = ElementHelper.WaitForElementPresentGetText(frame, By.xpath("//div[@id='buttonWrapper']//button[4]"));
    assertEquals("Browse Files", browseText);
    assertEquals("Create New", createText);
    assertEquals("Manage Data Sources", manageText);
    assertEquals("Documentation", documentationText);
    ElementHelper.Click(frame, By.id("btnCreateNew"));

    /*
     * ## Step 2
     */
    String cdeText = ElementHelper.WaitForElementPresentGetText(frame, By.xpath("//div[@class='popover-content']/button[@id='createNewlaunch_new_cdeButton']"));
    String dataText = ElementHelper.WaitForElementPresentGetText(frame, By.xpath("//div[@class='popover-content']/button[@id='createNewdatasourceButton']"));
    assertEquals("CDE Dashboard", cdeText);
    assertEquals("Data Source", dataText);
    ElementHelper.Click(frame, By.xpath("//div[@class='popover-content']/button[@id='createNewlaunch_new_cdeButton']"));

    /*
     * ## Step 3
     */
    //wait for invisibility of waiting pop-up
    driver.switchTo().defaultContent();
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='busy-indicator-container waitPopup']"));

    //assert title
    String titleText = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@title='New CDE Dashboard']"));
    assertEquals("CDE Dashboard", titleText);

    //focus iframe
    elementFrame = ElementHelper.FindElement(driver, By.xpath("//iframe"));
    frame = driver.switchTo().frame(elementFrame);

    //assert buttons and click Apply template
    element = ElementHelper.WaitForElementPresenceAndVisible(frame, By.xpath("//a[@title='Save as Template']"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(frame, By.xpath("//a[@title='Apply Template']"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(frame, By.xpath("//a[@title='Add Resource']"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(frame, By.xpath("//a[@title='Add Bootstrap Panel']"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(frame, By.xpath("//a[@title='Add FreeForm']"));
    assertNotNull(element);
    ElementHelper.Click(frame, By.xpath("//a[@title='Apply Template']"));

    /*
     * ## Step 4
     */
    driver.switchTo().defaultContent();
    elementFrame = ElementHelper.FindElement(driver, By.xpath("//iframe"));
    frame = driver.switchTo().frame(elementFrame);
    ElementHelper.WaitForFrameReady(driver, By.id("popupTemplatebox"));
    element = ElementHelper.WaitForElementPresenceAndVisible(frame, By.id("popupTemplatebox"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(frame, By.id("thumbs"));
    assertNotNull(element);
    String templateText = ElementHelper.FindElement(frame, By.xpath("//div[@id='thumbs']/div[2]/p")).getText();
    assertEquals("Two Columns Template", templateText);
    ElementHelper.Click(frame, By.xpath("//div[@id='thumbs']/div[2]/p"));
    element = ElementHelper.WaitForElementPresenceAndVisible(frame, By.xpath("//div[@id='thumbs']/div[@class='active']"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(frame, By.xpath("//div[@class='popupTemplatebuttons']/button[@id='popupTemplate_state0_buttonOk']"));
    assertNotNull(element);
    ElementHelper.Click(frame, By.xpath("//div[@class='popupTemplatebuttons']/button[@id='popupTemplate_state0_buttonOk']"));

    /*
     * ## Step 5
     */
    element = ElementHelper.WaitForElementPresenceAndVisible(frame, By.xpath("//div[@class='popupTemplatemessage']"));
    assertNotNull(element);
    WebElement elem = ElementHelper.WaitForElementPresenceAndVisible(frame, By.id("popupTemplate"));
    assertNotNull(elem);
    Dimension size = elem.getSize();
    int height = size.getHeight();
    assertEquals(height, 183);
    int width = size.getWidth();
    assertEquals(width, 743);
    String warningText = ElementHelper.WaitForElementPresentGetText(frame, By.xpath("//div[@class='popupTemplatemessage']"));
    assertEquals("Are you sure you want to load the template?WARNING: Dashboard Layout will be overwritten!", warningText);
    element = ElementHelper.WaitForElementPresenceAndVisible(frame, By.xpath("//div[@class='popupTemplatebuttons']/button[@id='popupTemplate_state0_buttonOk']"));
    assertNotNull(element);
    ElementHelper.Click(frame, By.xpath("//div[@class='popupTemplatebuttons']/button[@id='popupTemplate_state0_buttonOk']"));

    /*
     * ## Step 6
     */
    element = ElementHelper.WaitForElementPresenceAndVisible(frame, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[5]/td/span"));
    assertNotNull(element);
    ElementHelper.Click(frame, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[5]/td/span"));
    element = ElementHelper.WaitForElementPresenceAndVisible(frame, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(frame, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]"));
    assertNotNull(element);
    String tr6tdText = ElementHelper.WaitForElementPresentGetText(frame, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]/td"));
    String tr6td2Text = ElementHelper.WaitForElementPresentGetText(frame, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]/td[2]"));
    String tr7tdText = ElementHelper.WaitForElementPresentGetText(frame, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]/td"));
    String tr7td2Text = ElementHelper.WaitForElementPresentGetText(frame, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]/td[2]"));
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
