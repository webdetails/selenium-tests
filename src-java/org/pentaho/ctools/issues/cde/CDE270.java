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
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-270
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-943
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDE270 {
  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDE270.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDE270.class.getSimpleName());
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
   *    Drag and drop of elements of layout panel works
   *
   * Description:
   *    The test pretends validate the CDE-270 issue, so when user tries to use drag and drop functionality of Layout elements
   *    it works as expected.
   *
   * Steps:
   *    1. Assert elements on page and add 3 sets of a row and 3 columns and assert layout elements
   *    2. Drag 2nd set into 1st column of first set and assert layout elements
   *    3. Drag 3rd set into 2nd column of first set and assert layout elements
   *    4. Drag 2nd column of first set into row of 2nd set (located in 1st column of first set) and assert layout elements
   */
  @Test(timeout = 120000)
  public void tc01_CdeDashboard_LayoutDragAndDrop() {
    log.info("tc01_CdeDashboard_LayoutDragAndDrop");

    /*
     * ## Step 1
     */
    //Assert elements on page
    ElementHelper.WaitForElementPresence(driver, By.xpath("//div[@class='datasourcesPanelButton']"));
    ElementHelper.WaitForElementPresence(driver, By.id("previewButton"));
    ElementHelper.WaitForElementPresence(driver, By.xpath("//div[@class='layoutPanelButton']"));
    ElementHelper.WaitForElementPresence(driver, By.xpath("//div[@class='componentsPanelButton']"));
    ElementHelper.WaitForElementPresence(driver, By.xpath("//a[@title='Add Row']"));
    ElementHelper.Click(driver, By.xpath("//a[@title='Add Row']"));
    ElementHelper.WaitForElementPresence(driver, By.xpath("//a[@title='Add Columns']"));

    //Add 3 sets of one row and three columns
    ElementHelper.Click(driver, By.xpath("//a[@title='Add Columns']"));
    ElementHelper.Click(driver, By.xpath("//a[@title='Add Columns']"));
    ElementHelper.Click(driver, By.xpath("//a[@title='Add Columns']"));
    ElementHelper.Click(driver, By.xpath("//a[@title='Add Row']"));
    ElementHelper.Click(driver, By.xpath("//a[@title='Add Columns']"));
    ElementHelper.Click(driver, By.xpath("//a[@title='Add Columns']"));
    ElementHelper.Click(driver, By.xpath("//a[@title='Add Columns']"));
    ElementHelper.Click(driver, By.xpath("//a[@title='Add Row']"));
    ElementHelper.Click(driver, By.xpath("//a[@title='Add Columns']"));
    ElementHelper.Click(driver, By.xpath("//a[@title='Add Columns']"));
    ElementHelper.Click(driver, By.xpath("//a[@title='Add Columns']"));

    //Assert elements on Layout
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr/td"), "Row");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[2]/td"), "Column");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[3]/td"), "Column");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[4]/td"), "Column");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[5]/td"), "Row");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]/td"), "Column");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]/td"), "Column");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[8]/td"), "Column");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[9]/td"), "Row");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[10]/td"), "Column");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[11]/td"), "Column");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[12]/td"), "Column");

    /*
     * ## Step 2
     */
    ElementHelper.DragAndDrop(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[5]/td"), By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[2]/td"));
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr/td"), "Row");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[2]/td"), "Column");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[3]/td"), "Row");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[4]/td"), "Column");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[5]/td"), "Column");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]/td"), "Column");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]/td"), "Row");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[8]/td"), "Column");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[9]/td"), "Column");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[10]/td"), "Column");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[11]/td"), "Column");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[12]/td"), "Column");

    /*
     * ## Step 3
     */
    ElementHelper.DragAndDrop(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]/td"), By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[11]/td"));
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr/td"), "Row");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[2]/td"), "Column");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[3]/td"), "Row");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[4]/td"), "Column");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[5]/td"), "Column");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]/td"), "Column");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]/td"), "Column");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[8]/td"), "Row");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[9]/td"), "Column");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[10]/td"), "Column");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[11]/td"), "Column");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[12]/td"), "Column");

    /*
     * ## Step 4
     */
    ElementHelper.DragAndDrop(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]/td"), By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[3]/td"));
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr/td"), "Row");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[2]/td"), "Column");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[3]/td"), "Row");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[4]/td"), "Column");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[5]/td"), "Row");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]/td"), "Column");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]/td"), "Column");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[8]/td"), "Column");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[9]/td"), "Column");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[10]/td"), "Column");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[11]/td"), "Column");
    ElementHelper.WaitForText(driver, By.xpath("//table[@id='table-cdfdd-layout-tree']/tbody/tr[12]/td"), "Column");

  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDE270.class.getSimpleName());
  }
}
