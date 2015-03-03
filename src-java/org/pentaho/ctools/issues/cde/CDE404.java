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

import java.util.Iterator;
import java.util.Set;

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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-404
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-971
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDE404{

  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDE404.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDE404.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Able to edit external resource from a dashbord belonging to a plugin with upper-case leter on the name
   *
   * Description:
   *    The test pretends validate the CDE-404 issue, so user is able to edit a external resource when
   *    accessing it from a dashboard that belongs to a plugin with upper case letter on the name.
   *
   * Steps:
   *    1. Assert elements on page and click "Elements"
   *    2. Look for existing dashboard and click to edit it.
   *    3. Wait for dashboard to load and click add resources.
   *    4. Select external file as resource
   *    5. Select file
   *    6. Edit resource and assert elements on external editor
   */
  @Test(timeout = 180000)
  public void tc01_ExternalResources_PluginDashboard() {
    log.info("tc01_ExternalResources_PluginDashboard");

    //Go to New CDE Dashboard
    driver.get(baseUrl + "plugin/sparkl/api/plugininfo?pluginId=CDE404");
    //wait for invisibility of waiting pop-up
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"), 60);

    /*
     * ## Step 1
     */
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='tabMultiButtonObj']/div/div/button"));
    String button1 = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='tabMultiButtonObj']/div/div/button"));
    assertEquals("About", button1);
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='tabMultiButtonObj']/div/div[2]/button"));
    button1 = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='tabMultiButtonObj']/div/div[2]/button"));
    assertEquals("Elements", button1);
    ElementHelper.Click(driver, By.xpath("//div[@id='tabMultiButtonObj']/div/div[2]/button"));

    /*
     * ## Step 2
     */
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='elementsTableObjTable_wrapper']/table/tbody/tr/td"));
    String text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='elementsTableObjTable_wrapper']/table/tbody/tr/td"));
    assertEquals("test", text);
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='elementsTableObjTable_wrapper']/table/tbody/tr/td[2]"));
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='elementsTableObjTable_wrapper']/table/tbody/tr/td[2]"));
    assertEquals("Dashboard", text);
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='elementsTableObjTable_wrapper']/table/tbody/tr/td[3]"));
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='elementsTableObjTable_wrapper']/table/tbody/tr/td[3]"));
    assertEquals("All Users", text);
    WebElement button = ElementHelper.FindElementInvisible(driver, By.xpath("//div[@id='elementsTableObjTable_wrapper']/table/tbody/tr/td[4]/div/button[2]"));
    Actions action = new Actions(driver);
    action.moveToElement(button).build().perform();
    ElementHelper.Click(driver, By.xpath("//div[@id='elementsTableObjTable_wrapper']/table/tbody/tr/td[4]/div/button[2]"));

    String parentHandle = driver.getWindowHandle();
    Set<String> setWindows = driver.getWindowHandles();
    ElementHelper.WaitForNewWindow(driver);
    setWindows = driver.getWindowHandles();
    //Get popup id
    WebDriver popup = null;
    Iterator<String> windowIterator = setWindows.iterator();

    while(windowIterator.hasNext()) {
      String windowHandle = windowIterator.next();
      popup = driver.switchTo().window(windowHandle);
      if(popup.getTitle().equals("Webdetails CDE")) {
        break;
      }
    }
    log.info("after while");
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//a[@title='Save as Template']"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//a[@title='Apply Template']"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//a[@title='Add Resource']"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//a[@title='Add FreeForm']"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//a[@title='Add Row']"));
    ElementHelper.Click(driver, By.xpath("//a[@title='Add Resource']"));

    /*
     * ## Step 4
     */
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//select[@id='resourceType']"));
    Select select = new Select(ElementHelper.FindElement(driver, By.xpath("//select[@id='resourceType']")));
    select.selectByValue("Css");
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//select[@id='resourceSource']"));
    Select select1 = new Select(ElementHelper.FindElement(driver, By.xpath("//select[@id='resourceSource']")));
    select1.selectByValue("file");
    ElementHelper.Click(driver, By.xpath("//button[@id='popup_state0_buttonOk']"));

    /*
     * ## Step 5
     */
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//button[@class='cdfdd-resourceFileExplorerRender']"));
    ElementHelper.Click(driver, By.xpath("//button[@class='cdfdd-resourceFileExplorerRender']"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("container_id"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//a[@rel='static/']"));
    ElementHelper.Click(driver, By.xpath("//a[@rel='static/']"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//a[@rel='static/system/']"));
    ElementHelper.Click(driver, By.xpath("//a[@rel='static/system/']"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//a[@rel='static/system/css/']"));
    ElementHelper.Click(driver, By.xpath("//a[@rel='static/system/css/']"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//a[@rel='static/system/css/cpk.css']"));
    ElementHelper.Click(driver, By.xpath("//a[@rel='static/system/css/cpk.css']"));
    ElementHelper.Click(driver, By.xpath("//button[@id='popup_browse_buttonOk']"));

    /*
     * ## Step 6
     */
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//button[@class='cdfddInput']"));
    ElementHelper.Click(driver, By.xpath("//button[@class='cdfddInput']"));
    WebElement elementframe = ElementHelper.FindElement(driver, By.xpath("//iframe"));
    WebDriver frame = driver.switchTo().frame(elementframe);
    ElementHelper.WaitForElementVisibility(frame, By.xpath("//span[@id='infoArea']"));
    String pathText = ElementHelper.WaitForElementPresentGetText(frame, By.xpath("//span[@id='infoArea']"));
    assertEquals("/system/CDE404/static/system/css/cpk.css", pathText);
    String lineText = ElementHelper.WaitForElementPresentGetText(frame, By.xpath("//pre[@id='editArea']/div[2]/div/div[3]/div/span"));
    assertEquals("/* This Source Code Form is subject to the terms of the Mozilla Public", lineText);
    lineText = ElementHelper.WaitForElementPresentGetText(frame, By.xpath("//pre[@id='editArea']/div[2]/div/div[3]/div[2]/span"));
    assertEquals("* License, v. 2.0. If a copy of the MPL was not distributed with this file,", lineText);
    lineText = ElementHelper.WaitForElementPresentGetText(frame, By.xpath("//pre[@id='editArea']/div[2]/div/div[3]/div[3]/span"));
    assertEquals("* You can obtain one at http://mozilla.org/MPL/2.0/. */", lineText);

    driver.switchTo().defaultContent();
    ElementHelper.Click(driver, By.xpath("//div[@class='popupclose']"));
    driver.close();
    driver.switchTo().window(parentHandle);
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='elementsTableObjTable_wrapper']/table/tbody/tr/td"));
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='elementsTableObjTable_wrapper']/table/tbody/tr/td"));
    assertEquals("test", text);

  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDE404.class.getSimpleName());
  }
}
