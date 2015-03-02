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
 * - http://jira.pentaho.com/browse/CDE-388
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-939
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDE388 {
  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDE388.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDE388.class.getSimpleName());
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
   *    Drag and drop of OlapWizard working correctly
   *
   * Description:
   *    The test pretends validate the CDE-388 issue, so when user tries to use drag and drop functionality of OlapWizard
   *    it works as expected.
   *
   * Steps:
   *    1. Wait for new Dashboard to be created, assert elements on page and go to Datasources
   *    2. Assert Datasources Panel is shown, click "Wizards", click "OLAP Chart Wizard"
   *    3. Assert Wizard is shown, expand "Markets", drag "Territory" to "Rows" and "Quantity" to "Columns"
   *    4. Assert chart is shown on Preview
   */
  @Test(timeout = 120000)
  public void tc01_CdeDashboard_OlapWizardDragAndDrop() {
    log.info("tc01_CdeDashboard_OlapWizardDragAndDrop");

    /*
     * ## Step 1
     */
    ElementHelper.WaitForElementPresence(driver, By.xpath("//div[@class='datasourcesPanelButton']"));
    ElementHelper.WaitForElementPresence(driver, By.id("previewButton"));
    ElementHelper.WaitForElementPresence(driver, By.xpath("//div[@class='layoutPanelButton']"));
    ElementHelper.WaitForElementPresence(driver, By.xpath("//div[@class='componentsPanelButton']"));
    ElementHelper.Click(driver, By.xpath("//div[@class='datasourcesPanelButton']"));

    /*
     * ## Step 2
     */
    ElementHelper.WaitForTextPresence(driver, By.xpath("//div[@id='cdfdd-datasources-datasources']/div/div/div"), "Datasources");
    ElementHelper.WaitForTextPresence(driver, By.xpath("//div[@id='cdfdd-datasources-properties']/div/div/div"), "Properties");
    String classText = ElementHelper.FindElement(driver, By.xpath("//div[@title='Datasources Panel']")).getAttribute("class");
    assertEquals("panelButton panelButton-active", classText);
    ElementHelper.Click(driver, By.xpath("//div[@id='cdfdd-datasources-palletePallete']/div/h3/span"));
    ElementHelper.Click(driver, By.xpath("//div[@id='cdfdd-datasources-palletePallete']/div/div/ul/li[2]/a"));

    /*
     * ## Step 3
     */
    ElementHelper.WaitForTextPresence(driver, By.xpath("//div[@id='wizardDialog']/div/div/h1"), "OLAP Wizard");
    ElementHelper.WaitForElementPresence(driver, By.id("cdfdd-wizard-button-ok"));
    ElementHelper.WaitForElementPresence(driver, By.id("cdfdd-wizard-button-cancel"));
    Select select = new Select(ElementHelper.FindElement(driver, By.id("cdfddOlapCatalogSelect")));
    select.selectByVisibleText("SteelWheels");
    Select select1 = new Select(ElementHelper.FindElement(driver, By.id("cdfddOlapCubeSelect")));
    select1.selectByVisibleText("SteelWheelsSales");
    ElementHelper.Click(driver, By.xpath("//table[@id='cdfddOlapDimensionSelector']/tbody/tr/td/span"));
    ElementHelper.DragAndDrop(driver, By.xpath("//table[@id='cdfddOlapDimensionSelector']/tbody/tr[2]/td"), By.id("cdfdd-olap-rows"));
    ElementHelper.DragAndDrop(driver, By.xpath("//table[@id='cdfddOlapMeasureSelector']/tbody/tr/td"), By.id("cdfdd-olap-columns"));

    /*
     * ## Step 4
     */
    ElementHelper.WaitForElementPresence(driver, By.id("cdfdd-olap-preview-areaprotovis"));
    ElementHelper.WaitForElementPresence(driver, By.xpath("//div[@id='cdfdd-olap-preview-areaprotovis']//*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='rect']"));
    ElementHelper.WaitForElementPresence(driver, By.xpath("//div[@id='cdfdd-olap-preview-areaprotovis']//*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='rect'][2]"));
    ElementHelper.WaitForElementPresence(driver, By.xpath("//div[@id='cdfdd-olap-preview-areaprotovis']//*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='rect'][3]"));
    ElementHelper.WaitForElementPresence(driver, By.xpath("//div[@id='cdfdd-olap-preview-areaprotovis']//*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='rect'][4]"));
  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDE388.class.getSimpleName());
  }
}
