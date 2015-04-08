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
import org.pentaho.ctools.cde.widgets.utils.WidgetUtils;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-453
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1024
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDE453 {
  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDE453.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDE453.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    baseUrl = CToolsTestSuite.getBaseUrl();

  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert that when a new widget is created it is readily available in the components panel of CDE
   *
   * Description:
   *    The test pretends validate the CDE-453 issue, so when user creates a widget it's available in the
   *    components panel of CDE.
   *
   * Steps:
   *    1. Open New Dashboard and save as Widget
   *    2. Open New Dashboard and assert new Widget is present in components panel
   *    3. Delete newly created Widget
   *    4. Assert public/cde isn't shown (CDE442)
   *
   */
  @Test(timeout = 120000)
  public void tc01_NewCDEDashboard_NewWidgetPresent() throws Exception {
    log.info("tc01_NewCDEDashboard_NewWidgetPresent");

    /*
     * ## Step 1
     */
    WidgetUtils.CreateWidget(driver, "CDE453");

    /*
     * ## Step 2
     */
    //Create new CDE dashboard
    driver.get(baseUrl + "api/repos/wcdf/new");
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));

    //assert buttons and click "Save"
    WebElement element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//a[@title='Save as Template']"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//a[@title='Apply Template']"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//a[@title='Add Resource']"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//a[@id='Save']"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@class='componentsPanelButton']"));
    assertNotNull(element);
    element.click();
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//h3[@id='ui-accordion-cdfdd-components-palletePallete-header-8']/span"));
    assertNotNull(element);
    element.click();
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//a[@title='CDE453 Widget']"));
    assertNotNull(element);
    element.click();
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//tr[@id='WIDGETS']"));
    assertNotNull(element);

    /*
     * ## Step 3
     */
    WidgetUtils.RemoveWidgetByName(driver, "CDE453");

    /*
     * ## Step 4
     */
    /*driver.get(baseUrl + "Home");
    driver.switchTo().frame("home.perspective");
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@class='well sidebar']"));
    ElementHelper.Click(driver, By.xpath("//div[@class='well sidebar']/button"));//Click in 'Browse Files'

    //Now we have to navigate to 'Public/cde/widgets
    driver.switchTo().defaultContent();
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("applicationShell"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//iframe[@id='browser.perspective']"));
    driver.switchTo().frame("browser.perspective");

    ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("fileBrowser"));
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='spinner large-spinner']"));
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("(//div[@class='spinner large-spinner'])[2]"));

    ElementHelper.Click(driver, By.id("refreshBrowserIcon"));
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='spinner large-spinner']"));
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("(//div[@class='spinner large-spinner'])[2]"));

    //Public
    assertNotNull(ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='fileBrowserFolders']")));
    assertNotNull(ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@path='/public']")));
    driver.findElement(By.xpath("//div[@path='/public']")).findElement(By.className("expandCollapse")).click();
    //CDE
    assertTrue(ElementHelper.WaitForElementNotPresent(driver, By.xpath("//div[@path='/public/cde']")));*/

  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDE453.class.getSimpleName());
  }
}
