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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-384
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-940
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDE384 {
  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDE384.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDE384.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Check external resources path when editing
   *
   * Description:
   *    The test pretends validate the CDE-384 issue, so when user edits two different external
   *    resources the path to both of them is accurate.
   *
   * Steps:
   *    1. Wait for new Dashboard to be created, assert elements on page and click "Add Resource"
   *    2. Wait for popup to appear, assert text and click "Ok"
   *    3. Wait for properties and click on "^", wait for popup and expand "public", expand "plugin-samples",
   *    expand "pentaho-cdf-dd", click "cdeReference.css" and click "Ok"
   *    4. Click to edit resource, wait for popup, assert text on path, click "X"
   *    5. Go to Datasources Panel, expand Community Data Access and click CDA Datasource
   *    6. On properties click "^", wait for popup and expand "public", expand "plugin-samples",
   *    expand "cda", expand "cdafiles", click "compundJoin.cda" and click "Ok"
   *    7. Click to edit cda file, wait for popup, assert text on path, click "X"
   *    8. Go to Layout Panel, click to edit resource, wait for popup, assert text on path, click "X"
   */
  @Test(timeout = 120000)
  public void tc01_ExternalResources_AssertPath() {
    log.info("tc01_ExternalResources_AssertPath");

    /*
     * ## Step 1
     */
    //Go to New CDE Dashboard
    driver.get(baseUrl + "api/repos/wcdf/new");
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
    ElementHelper.Click(driver, By.xpath("//a[@title='Add Resource']"));

    /*
     * ## Step 2
     */
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//select[@id='resourceType']"));
    assertNotNull(element);
    Select select = new Select(ElementHelper.FindElement(driver, By.xpath("//select[@id='resourceType']")));
    select.selectByValue("Css");
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//select[@id='resourceSource']"));
    assertNotNull(element);
    Select select1 = new Select(ElementHelper.FindElement(driver, By.xpath("//select[@id='resourceSource']")));
    select1.selectByValue("file");
    ElementHelper.Click(driver, By.xpath("//button[@id='popup_state0_buttonOk']"));

    /*
     * ## Step 3
     */
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//button[@class='cdfdd-resourceFileExplorerRender']"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//button[@class='cdfdd-resourceFileExplorerRender']"));
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("container_id"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='container_id']//a[@rel='public/']"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//div[@id='container_id']//a[@rel='public/']"));
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='container_id']//a[@rel='public/plugin-samples/']"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//div[@id='container_id']//a[@rel='public/plugin-samples/']"));
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='container_id']//a[@rel='public/plugin-samples/pentaho-cdf-dd/']"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//div[@id='container_id']//a[@rel='public/plugin-samples/pentaho-cdf-dd/']"));
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='container_id']//a[@rel='public/plugin-samples/pentaho-cdf-dd/cdeReference.css']"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//div[@id='container_id']//a[@rel='public/plugin-samples/pentaho-cdf-dd/cdeReference.css']"));
    ElementHelper.Click(driver, By.xpath("//button[@id='popup_browse_buttonOk']"));

    /*
     * ## Step 4
     */
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//button[@class='cdfddInput']"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//button[@class='cdfddInput']"));
    WebElement elementFrame = ElementHelper.FindElement(driver, By.xpath("//iframe"));
    WebDriver frame = driver.switchTo().frame(elementFrame);
    element = ElementHelper.WaitForElementPresenceAndVisible(frame, By.xpath("//span[@id='infoArea']"));
    assertNotNull(element);
    String pathText = ElementHelper.WaitForTextPresence(frame, By.xpath("//span[@id='infoArea']"), "/public/plugin-samples/pentaho-cdf-dd/cdeReference.css");
    assertEquals("/public/plugin-samples/pentaho-cdf-dd/cdeReference.css", pathText);
    driver.switchTo().defaultContent();
    ElementHelper.Click(driver, By.id("popup_edit_buttonClose"));

    /*
     * ## Step 5
     */
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@class='layoutPanelButton']"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@class='componentsPanelButton']"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@class='datasourcesPanelButton']"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//div[@class='datasourcesPanelButton']"));
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='cdfdd-datasources-pallete']/div/div[2]/h3/a"));
    assertNotNull(element);
    String cdaText = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='cdfdd-datasources-pallete']/div/div[2]/h3/a"));
    assertEquals("Community Data Access", cdaText);
    ElementHelper.Click(driver, By.xpath("//div[@id='cdfdd-datasources-pallete']/div/div[2]/h3/a"));
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//a[@title='CDA Data Source']"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//a[@title='CDA Data Source']"));

    /*
     * ## Step 6
     */
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//table[@id='table-cdfdd-datasources-properties']//button[@class='cdfdd-resourceFileExplorerRender']"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//table[@id='table-cdfdd-datasources-properties']//button[@class='cdfdd-resourceFileExplorerRender']"));
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("container_id"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='container_id']//a[@rel='public/']"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//div[@id='container_id']//a[@rel='public/']"));
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='container_id']//a[@rel='public/plugin-samples/']"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//div[@id='container_id']//a[@rel='public/plugin-samples/']"));
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='container_id']//a[@rel='public/plugin-samples/cda/']"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//div[@id='container_id']//a[@rel='public/plugin-samples/cda/']"));
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='container_id']//a[@rel='public/plugin-samples/cda/cdafiles/']"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//div[@id='container_id']//a[@rel='public/plugin-samples/cda/cdafiles/']"));
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='container_id']//a[@rel='public/plugin-samples/cda/cdafiles/compoundJoin.cda']"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//div[@id='container_id']//a[@rel='public/plugin-samples/cda/cdafiles/compoundJoin.cda']"));
    ElementHelper.Click(driver, By.xpath("//button[@id='popup_browse_buttonOk']"));

    /*
     * ## Step 7
     */
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//table[@id='table-cdfdd-datasources-properties']//button[@class='cdfddInput']"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//table[@id='table-cdfdd-datasources-properties']//button[@class='cdfddInput']"));
    elementFrame = ElementHelper.FindElement(driver, By.xpath("//iframe"));
    frame = driver.switchTo().frame(elementFrame);
    element = ElementHelper.WaitForElementPresenceAndVisible(frame, By.xpath("//span[@id='infoArea']"));
    assertNotNull(element);
    String pathText1 = ElementHelper.WaitForTextPresence(frame, By.xpath("//span[@id='infoArea']"), "/public/plugin-samples/cda/cdafiles/compoundJoin.cda");
    assertEquals("/public/plugin-samples/cda/cdafiles/compoundJoin.cda", pathText1);
    driver.switchTo().defaultContent();
    ElementHelper.Click(driver, By.id("popup_edit_buttonClose"));

    /*
     * ## Step 8
     */
    driver.switchTo().defaultContent();
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@class='layoutPanelButton']"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@class='componentsPanelButton']"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@class='datasourcesPanelButton']"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//div[@class='layoutPanelButton']"));
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//button[@class='cdfddInput']"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//button[@class='cdfddInput']"));
    elementFrame = ElementHelper.FindElement(driver, By.xpath("//iframe"));
    frame = driver.switchTo().frame(elementFrame);
    element = ElementHelper.WaitForElementPresenceAndVisible(frame, By.xpath("//span[@id='infoArea']"));
    assertNotNull(element);
    String pathText3 = ElementHelper.WaitForTextPresence(frame, By.xpath("//span[@id='infoArea']"), "/public/plugin-samples/pentaho-cdf-dd/cdeReference.css");
    assertEquals("/public/plugin-samples/pentaho-cdf-dd/cdeReference.css", pathText3);
    driver.switchTo().defaultContent();
    ElementHelper.Click(driver, By.id("popup_edit_buttonClose"));

  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDE384.class.getSimpleName());
  }
}
