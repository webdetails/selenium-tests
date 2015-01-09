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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-408
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-937
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDE408 {
  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDE408.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDE408.class.getSimpleName());
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
   *    Check existence of Scrollers on Table Component's Advanced Properties
   *
   * Description:
   *    The test pretends validate the CDE-408 issue, so when user navigates to the Advanced Properties
   *    of a Table Component a scroller is added to the properties panel.
   *
   * Steps:
   *    1. Wait for new Dashboard to be created, assert elements on page and click "Components Panel"
   *    2. Wait for Components panel to be shown, expand "Others" and click "Table COmponent"
   *    3. Wait for table Component to be added and then click "Advanced Properties"
   *    4. Assert existence of scroller
   */
  @Test(timeout = 120000)
  public void tc01_NewCdeDashboard_AddedScrollers() {
    log.info("tc01_NewCdeDashboard_AddedScrollers");

    /*
     * ## Step 1
     */
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@class='layoutPanelButton']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@class='componentsPanelButton']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@class='datasourcesPanelButton']"));
    ElementHelper.Click(driver, By.xpath("//div[@class='componentsPanelButton']"));

    /*
     * ## Step 2
     */
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@id='cdfdd-components-palletePallete']/div[2]/h3/a"));
    String otherText = ElementHelper.GetText(driver, By.xpath("//div[@id='cdfdd-components-palletePallete']/div[2]/h3/a"));
    assertEquals("Others", otherText);
    ElementHelper.Click(driver, By.xpath("//div[@id='cdfdd-components-palletePallete']/div[2]/h3/a"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='table Component']"));
    ElementHelper.Click(driver, By.xpath("//a[@title='table Component']"));

    /*
     * ## Step 3
     */
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@id='cdfdd-components-properties']/div/div/div[3]"));
    ElementHelper.Click(driver, By.xpath("//div[@id='cdfdd-components-properties']/div/div/div[3]"));

    /*
     * ## Step 4
     */
    WebElement elemClickAction = driver.findElement(By.xpath("//div[@id='cdfdd-components-properties']//tr[39]/td/div[2]"));
    log.info(elemClickAction.isEnabled());

    //driver.switchTo().defaultContent();

    //colocar javascrict
    //var element = (document.frame_0.document.getElementsByClassName('scrollContainer')[3]);
    //var pos = element.scrollTop;
    //var height = element.scrollHeight;
    //element.scrollTop=height;
    //return pos
    String posText = "var pos = document.getElementsByClassName('scrollContainer')[3].scrollTop;var height = document.getElementsByClassName('scrollContainer')[3].scrollHeight;document.getElementsByClassName('scrollContainer')[3].scrollTop=height;return pos";
    String pos = ((JavascriptExecutor) driver).executeScript(posText).toString();
    log.info(pos);
    //var pos1 = element.scrollTop;
    //((JavascriptExecutor) driver).executeScript("return document.body.innerHTML;")
    String posText1 = "return document.getElementsByClassName('scrollContainer')[3].scrollTop;";
    String pos1 = ((JavascriptExecutor) driver).executeScript(posText1).toString();
    log.info(pos1);
    assertThat(pos, not(equalTo(pos1)));

  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDE408.class.getSimpleName());
  }
}
