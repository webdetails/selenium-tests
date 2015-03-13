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
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-452
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1023
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDE452 {
  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDE452.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDE452.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Access denied when unauthorized user tries to access CDE dashboard in edit mode
   *
   * Description:
   *    The test pretends validate the CDE-452 issue, so when unauthorized user tries to access dashboards in edit
   *    mode he gets "Access Denied".
   *
   * Steps:
   *    1. Open system dashboard and repository dashboard in edit mode
   *    2. Log out and log in with Suzy, repeat step 1 and assert it gives "Access Denied"
   *    3. Log out and log in with Pat, repeat step 1 and assert it gives "Access Denied"
   *    4. Log out and log in with Tiffany, repeat step 1 and assert it gives "Access Denied"
   */
  @Test(timeout = 120000)
  public void tc01_CdeDashboard_AccessDeniedEdit() {
    log.info("tc01_CdeDashboard_AccessDeniedEdit");

    /*
     * ## Step 1
     */
    //Open system dashboard in edit mode and assert elements on page
    driver.get(baseUrl + "plugin/pentaho-cdf-dd/api/renderer/edit?absolute=false&inferScheme=false&file=i18nTest.wcdf&path=%2FCDE404%2Fdashboards%2F&solution=system&mode=edit");

    WebElement element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@class='datasourcesPanelButton']"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("previewButton"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@class='layoutPanelButton']"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@class='componentsPanelButton']"));
    assertNotNull(element);

    //Open repository dashboard in edit mode and assert elements on page
    driver.get(baseUrl + "api/repos/%3Apublic%3AIssues%3ACDF-430%3ACDE%3Ai18nTest.wcdf/edit");

    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@class='datasourcesPanelButton']"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("previewButton"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@class='layoutPanelButton']"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@class='componentsPanelButton']"));
    assertNotNull(element);

    /*
     * ## Step 2
     */
    //Log out
    driver.get(baseUrl + "Home");
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='busy-indicator-container waitPopup']"));
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='pucUserDropDown']/table/tbody/tr/td[2]"));
    assertNotNull(element);
    element.click();
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='customDropdownPopupMinor']/div/div/table/tbody/tr/td"));
    assertNotNull(element);
    String text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='customDropdownPopupMinor']/div/div/table/tbody/tr/td"));
    assertEquals("Log Out", text);
    element.click();

    //Wait for form display
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='login-form-container']/div/h1"));
    assertEquals("User Console", text);

    //Wait for all all elements in the form to be visible
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("j_username"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("j_password"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.cssSelector("button.btn"));
    assertNotNull(element);
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("j_username")).clear();
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("j_username")).sendKeys("suzy");
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("j_password")).clear();
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("j_password")).sendKeys("password");
    ElementHelper.Click(driver, By.cssSelector("button.btn"));

    //wait for visibility of waiting pop-up
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='busy-indicator-container waitPopup']"));

    //Wait to load the new page
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='pucUserDropDown']/table/tbody/tr/td/div"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//iframe[@id='home.perspective']"));
    assertNotNull(element);

    //Logged as ADMIN user
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='pucUserDropDown']/table/tbody/tr/td/div"));
    assertEquals("suzy", text);

    //Open system dashboard in edit mode and assert elements on page
    driver.get(baseUrl + "plugin/pentaho-cdf-dd/api/renderer/edit?absolute=false&inferScheme=false&file=i18nTest.wcdf&path=%2FCDE404%2Fdashboards%2F&solution=system&mode=edit");
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//body"));
    assertEquals("Access Denied to file /system/CDE404/dashboards/i18nTest.wcdf", text);

    //Open repository dashboard in edit mode and assert elements on page
    driver.get(baseUrl + "api/repos/%3Apublic%3AIssues%3ACDF-430%3ACDE%3Ai18nTest.wcdf/edit");
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//body"));
    assertEquals("Access Denied to file /public/Issues/CDF-430/CDE/i18nTest.wcdf", text);

    /*
     * ## Step 3
     */
    //Log out
    driver.get(baseUrl + "Home");
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='busy-indicator-container waitPopup']"));
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='pucUserDropDown']/table/tbody/tr/td[2]"));
    assertNotNull(element);
    element.click();
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='customDropdownPopupMinor']/div/div/table/tbody/tr/td"));
    assertNotNull(element);
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='customDropdownPopupMinor']/div/div/table/tbody/tr/td"));
    assertEquals("Log Out", text);
    element.click();

    //Wait for form display
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='login-form-container']/div/h1"));
    assertEquals("User Console", text);

    //Wait for all all elements in the form to be visible
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("j_username"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("j_password"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.cssSelector("button.btn"));
    assertNotNull(element);
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("j_username")).clear();
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("j_username")).sendKeys("pat");
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("j_password")).clear();
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("j_password")).sendKeys("password");
    ElementHelper.Click(driver, By.cssSelector("button.btn"));

    //wait for visibility of waiting pop-up
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='busy-indicator-container waitPopup']"));

    //Wait to load the new page
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='pucUserDropDown']/table/tbody/tr/td/div"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//iframe[@id='home.perspective']"));
    assertNotNull(element);

    //Logged as ADMIN user
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='pucUserDropDown']/table/tbody/tr/td/div"));
    assertEquals("pat", text);

    //Open system dashboard in edit mode and assert elements on page
    driver.get(baseUrl + "plugin/pentaho-cdf-dd/api/renderer/edit?absolute=false&inferScheme=false&file=i18nTest.wcdf&path=%2FCDE404%2Fdashboards%2F&solution=system&mode=edit");
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//body"));
    assertEquals("Access Denied to file /system/CDE404/dashboards/i18nTest.wcdf", text);

    //Open repository dashboard in edit mode and assert elements on page
    driver.get(baseUrl + "api/repos/%3Apublic%3AIssues%3ACDF-430%3ACDE%3Ai18nTest.wcdf/edit");
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//body"));
    assertEquals("Access Denied to file /public/Issues/CDF-430/CDE/i18nTest.wcdf", text);

    /*
     * ## Step 4
     */
    //Log out
    driver.get(baseUrl + "Home");
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='busy-indicator-container waitPopup']"));
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='pucUserDropDown']/table/tbody/tr/td[2]"));
    assertNotNull(element);
    element.click();
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='customDropdownPopupMinor']/div/div/table/tbody/tr/td"));
    assertNotNull(element);
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='customDropdownPopupMinor']/div/div/table/tbody/tr/td"));
    assertEquals("Log Out", text);
    element.click();

    //Wait for form display
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='login-form-container']/div/h1"));
    assertEquals("User Console", text);

    //Wait for all all elements in the form to be visible
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("j_username"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("j_password"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.cssSelector("button.btn"));
    assertNotNull(element);
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("j_username")).clear();
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("j_username")).sendKeys("tiffany");
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("j_password")).clear();
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("j_password")).sendKeys("password");
    ElementHelper.Click(driver, By.cssSelector("button.btn"));

    //wait for visibility of waiting pop-up
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='busy-indicator-container waitPopup']"));

    //Wait to load the new page
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='pucUserDropDown']/table/tbody/tr/td/div"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//iframe[@id='home.perspective']"));
    assertNotNull(element);

    //Logged as ADMIN user
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='pucUserDropDown']/table/tbody/tr/td/div"));
    assertEquals("tiffany", text);

    //Open system dashboard in edit mode and assert elements on page
    driver.get(baseUrl + "plugin/pentaho-cdf-dd/api/renderer/edit?absolute=false&inferScheme=false&file=i18nTest.wcdf&path=%2FCDE404%2Fdashboards%2F&solution=system&mode=edit");
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//body"));
    assertEquals("Access Denied to file /system/CDE404/dashboards/i18nTest.wcdf", text);

    //Open repository dashboard in edit mode and assert elements on page
    driver.get(baseUrl + "api/repos/%3Apublic%3AIssues%3ACDF-430%3ACDE%3Ai18nTest.wcdf/edit");
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//body"));
    assertEquals("Access Denied to file /public/Issues/CDF-430/CDE/i18nTest.wcdf", text);

  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDE452.class.getSimpleName());
  }
}
