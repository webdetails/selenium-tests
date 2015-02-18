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

  @Before
  public void setUpTestCase() {

    //Go to New CDE Dashboard
    driver.get(baseUrl + "api/repos/wcdf/new");

    //assert buttons
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='Save as Template']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='Apply Template']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='Add Resource']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='Add Bootstrap Panel']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='Add FreeForm']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='Add Row']"));
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
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='Save as Template']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='Apply Template']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='Add Resource']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='Add Bootstrap Panel']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='Add FreeForm']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='Add Row']"));
    ElementHelper.ClickJS(driver, By.xpath("//a[@title='Add Resource']"));

    /*
     * ## Step 2
     */
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//select[@id='resourceType']"));
    Select select = new Select(ElementHelper.FindElement(driver, By.xpath("//select[@id='resourceType']")));
    select.selectByValue("Css");
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//select[@id='resourceSource']"));
    Select select1 = new Select(ElementHelper.FindElement(driver, By.xpath("//select[@id='resourceSource']")));
    select1.selectByValue("file");
    ElementHelper.ClickJS(driver, By.xpath("//button[@id='popup_state0_buttonOk']"));

    /*
     * ## Step 3
     */
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//button[@class='cdfdd-resourceFileExplorerRender']"));
    ElementHelper.ClickJS(driver, By.xpath("//button[@class='cdfdd-resourceFileExplorerRender']"));
    ElementHelper.WaitForElementVisibility(driver, By.id("container_id"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@rel='public/']"));
    ElementHelper.ClickJS(driver, By.xpath("//a[@rel='public/']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@rel='public/plugin-samples/']"));
    ElementHelper.ClickJS(driver, By.xpath("//a[@rel='public/plugin-samples/']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@rel='public/plugin-samples/pentaho-cdf-dd/']"));
    ElementHelper.ClickJS(driver, By.xpath("//a[@rel='public/plugin-samples/pentaho-cdf-dd/']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@rel='public/plugin-samples/pentaho-cdf-dd/cdeReference.css']"));
    ElementHelper.ClickJS(driver, By.xpath("//a[@rel='public/plugin-samples/pentaho-cdf-dd/cdeReference.css']"));
    ElementHelper.ClickJS(driver, By.xpath("//button[@id='popup_browse_buttonOk']"));

    /*
     * ## Step 4
     */
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//button[@class='cdfddInput']"));
    ElementHelper.ClickJS(driver, By.xpath("//button[@class='cdfddInput']"));
    driver = driver.switchTo().frame("externalEditor");

    ElementHelper.WaitForElementVisibility(driver, By.xpath("//span[@id='infoArea']"));
    String pathText = ElementHelper.GetText(driver, By.xpath("//span[@id='infoArea']"));
    assertEquals("/public/plugin-samples/pentaho-cdf-dd/cdeReference.css", pathText);
    driver.switchTo().defaultContent();
    ElementHelper.ClickJS(driver, By.xpath("//div[@class='popupclose']"));

    /*
     * ## Step 5
     */
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@class='layoutPanelButton']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@class='componentsPanelButton']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@class='datasourcesPanelButton']"));
    ElementHelper.ClickJS(driver, By.xpath("//div[@class='datasourcesPanelButton']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@id='cdfdd-datasources-pallete']/div/div[2]/h3/a"));
    String cdaText = ElementHelper.GetText(driver, By.xpath("//div[@id='cdfdd-datasources-pallete']/div/div[2]/h3/a"));
    assertEquals("Community Data Access", cdaText);
    ElementHelper.ClickJS(driver, By.xpath("//div[@id='cdfdd-datasources-pallete']/div/div[2]/h3/a"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='CDA Data Source']"));
    ElementHelper.ClickJS(driver, By.xpath("//a[@title='CDA Data Source']"));

    /*
     * ## Step 6
     */
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//table[@id='table-cdfdd-datasources-properties']//button[@class='cdfdd-resourceFileExplorerRender']"));
    ElementHelper.ClickJS(driver, By.xpath("//table[@id='table-cdfdd-datasources-properties']//button[@class='cdfdd-resourceFileExplorerRender']"));
    ElementHelper.WaitForElementVisibility(driver, By.id("container_id"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@rel='public/']"));
    ElementHelper.ClickJS(driver, By.xpath("//a[@rel='public/']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@rel='public/plugin-samples/']"));
    ElementHelper.ClickJS(driver, By.xpath("//a[@rel='public/plugin-samples/']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@rel='public/plugin-samples/cda/']"));
    ElementHelper.ClickJS(driver, By.xpath("//a[@rel='public/plugin-samples/cda/']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@rel='public/plugin-samples/cda/cdafiles/']"));
    ElementHelper.ClickJS(driver, By.xpath("//a[@rel='public/plugin-samples/cda/cdafiles/']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@rel='public/plugin-samples/cda/cdafiles/compoundJoin.cda']"));
    ElementHelper.ClickJS(driver, By.xpath("//a[@rel='public/plugin-samples/cda/cdafiles/compoundJoin.cda']"));
    ElementHelper.ClickJS(driver, By.xpath("//button[@id='popup_browse_buttonOk']"));

    /*
     * ## Step 7
     */
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//table[@id='table-cdfdd-datasources-properties']//button[@class='cdfddInput']"));
    ElementHelper.ClickJS(driver, By.xpath("//table[@id='table-cdfdd-datasources-properties']//button[@class='cdfddInput']"));

    driver = driver.switchTo().frame("externalEditor");
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//span[@id='infoArea']"));
    String pathText1 = ElementHelper.GetText(driver, By.xpath("//span[@id='infoArea']"));
    assertEquals("/public/plugin-samples/cda/cdafiles/compoundJoin.cda", pathText1);
    driver.switchTo().defaultContent();
    ElementHelper.ClickJS(driver, By.xpath("//div[@class='popupclose']"));

    /*
     * ## Step 8
     */
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@class='layoutPanelButton']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@class='componentsPanelButton']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@class='datasourcesPanelButton']"));
    ElementHelper.ClickJS(driver, By.xpath("//div[@class='layoutPanelButton']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//button[@class='cdfddInput']"));
    ElementHelper.ClickJS(driver, By.xpath("//button[@class='cdfddInput']"));

    driver = driver.switchTo().frame("externalEditor");
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//span[@id='infoArea']"));
    String pathText3 = ElementHelper.GetText(driver, By.xpath("//span[@id='infoArea']"));
    assertEquals("/public/plugin-samples/pentaho-cdf-dd/cdeReference.css", pathText3);
    driver.switchTo().defaultContent();
    ElementHelper.ClickJS(driver, By.xpath("//div[@class='popupclose']"));

  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDE384.class.getSimpleName());
  }
}
