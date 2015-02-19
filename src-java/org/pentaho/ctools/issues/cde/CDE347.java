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
 * - http://jira.pentaho.com/browse/CDE-347
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-924
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDE347 {
  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDE347.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDE347.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  @Before
  public void setUpTestCase() {
    //Go to User Console
    driver.get(baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3Accc_bullet.wcdf/edit");

    //Wait for buttons: Layout. Components, Datasources AND Preview
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@title='Datasources Panel']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@title='Components Panel']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@title='Layout Panel']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@title='Preview your Dashboard']"));

  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Asserting correct rendering of charts of CDE's Bullet Chart testcase
   *
   * Description:
   *    The test pretends validate the CDE-347 issue, so when user previews the Bullet Chart
   *    test case dashboard, coming from edit mode, the charts are rendered as expected.
   *
   * Steps:
   *    1. Assert elements on page
   *    2. Click preview button and focus on fancybox
   *    3. Assert charts are rendered correctly
   */
  @Test(timeout = 120000)
  public void tc01_BulletChartTestCase_ChartsRendered() {
    log.info("tc01_BulletChartTestCase_ChartsRendered");

    /*
     * ## Step 1
     */
    String c1r1Text = ElementHelper.GetText(driver, By.xpath("//tr/td"));
    String c2r1Text = ElementHelper.GetText(driver, By.xpath("//tr/td[2]"));
    String c1r4Text = ElementHelper.GetText(driver, By.xpath("//tr[6]/td"));
    String c2r4Text = ElementHelper.GetText(driver, By.xpath("//tr[6]/td[2]"));
    assertEquals("Resource", c1r1Text);
    assertEquals("template", c2r1Text);
    assertEquals("Row", c1r4Text);
    assertEquals("obj1", c2r4Text);

    /*
     * ## Step 2
     */
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@title='Preview your Dashboard']"));
    ElementHelper.ClickJS(driver, By.id("previewButton"));
    ElementHelper.WaitForElementVisibility(driver, By.id("fancybox-content"));
    WebElement elementFrame = ElementHelper.FindElement(driver, By.xpath("//iframe"));
    WebDriver frame = driver.switchTo().frame(elementFrame);

    /*
     * ## Step 3
     */
    WebElement obj1 = ElementHelper.WaitForElementVisibility(frame, By.xpath("//div[@id='obj1protovis']//*[local-name()='path']"));
    WebElement obj2 = ElementHelper.WaitForElementVisibility(frame, By.xpath("//div[@id='obj1protovis']//*[local-name()='path'][2]"));
    WebElement obj3 = ElementHelper.WaitForElementVisibility(frame, By.xpath("//div[@id='obj2protovis']//*[local-name()='g'][2]/*[local-name()='rect']"));
    WebElement obj4 = ElementHelper.WaitForElementVisibility(frame, By.xpath("//div[@id='obj3protovis']//*[local-name()='path']"));
    WebElement obj5 = ElementHelper.WaitForElementVisibility(frame, By.xpath("//div[@id='obj4protovis']//*[local-name()='path']"));
    WebElement obj6 = ElementHelper.WaitForElementVisibility(frame, By.xpath("//div[@id='obj5protovis']//*[local-name()='path']"));
    assertNotNull(obj1);
    assertNotNull(obj2);
    assertNotNull(obj3);
    assertNotNull(obj4);
    assertNotNull(obj5);
    assertNotNull(obj6);
  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDE347.class.getSimpleName());
  }
}
