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
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-392
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-938
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDE392 {
  // Instance of the driver (browser emulator)
  private static WebDriver       driver;
  //Instance to be used on wait commands
  private static Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private static String          baseUrl;
  // Log instance
  private static Logger          log                = LogManager.getLogger(CDE392.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule      screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDE392.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    baseUrl = CToolsTestSuite.getBaseUrl();
    wait = CToolsTestSuite.getWait();
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
   *    Asserting new table inherits Dashboard's rendering option
   *
   * Description:
   *    The test pretends validate the CDE-392 issue, so when user creates a new table component it inherits
   *    the dashboard's renderer option.
   *
   * Steps:
   *    1. Wait for new Dashboard to be created, assert elements on page and click "Settings"
   *    2. Focus on popup, assert elements and assert Bootstrap option is selected by default
   *    3. Go to "Components Panel", expand "Others" and click "Table Component"
   *    4. Click "Advanced Properties", find row with "Style" property and assert "Bootstrap" is selected
   *    5. Go to Settings and select another rendering option
   *    6. Assert confirmation popup is shown
   */
  @Test(timeout = 120000)
  public void tc01_NewCdeDashboard_TableInheritsRenderer() {
    log.info("tc01_NewCdeDashboard_TableInheritsRenderer");

    /*
     * ## Step 1
     */
    String newText = ElementHelper.GetText(driver, By.xpath("//div[@id='headerLinks']/div/a"));
    String saveText = ElementHelper.GetText(driver, By.xpath("//div[@id='headerLinks']/div[2]/a"));
    String saveasText = ElementHelper.GetText(driver, By.xpath("//div[@id='headerLinks']/div[3]/a"));
    String reloadText = ElementHelper.GetText(driver, By.xpath("//div[@id='headerLinks']/div[4]/a"));
    String settingsText = ElementHelper.GetText(driver, By.xpath("//div[@id='headerLinks']/div[5]/a"));
    assertEquals("New", newText);
    assertEquals("Save", saveText);
    assertEquals("Save as...", saveasText);
    assertEquals("Reload", reloadText);
    assertEquals("Settings", settingsText);
    ElementHelper.ClickJS(driver, By.xpath("//div[@id='headerLinks']/div[5]/a"));

    /*
     * ## Step 2
     */
    WebElement obj1 = ElementHelper.FindElement(driver, By.xpath("//select[@id='rendererInput']/option[@value='bootstrap']"));
    assertEquals(obj1.isSelected(), true);
    ElementHelper.ClickJS(driver, By.xpath("//div[@class='popupclose']"));

    /*
     * ## Step 3
     */
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@class='layoutPanelButton']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@class='componentsPanelButton']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@class='datasourcesPanelButton']"));
    ElementHelper.ClickJS(driver, By.xpath("//div[@class='componentsPanelButton']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@id='cdfdd-components-palletePallete']/div[2]/h3/a"));
    String otherText = ElementHelper.GetText(driver, By.xpath("//div[@id='cdfdd-components-palletePallete']/div[2]/h3/a"));
    assertEquals("Others", otherText);
    ElementHelper.ClickJS(driver, By.xpath("//div[@id='cdfdd-components-palletePallete']/div[2]/h3/a"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//a[@title='table Component']"));
    ElementHelper.ClickJS(driver, By.xpath("//a[@title='table Component']"));

    /*
     * ## Step 4
     */
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@id='cdfdd-components-properties']/div/div/div[3]"));
    ElementHelper.ClickJS(driver, By.xpath("//div[@id='cdfdd-components-properties']/div/div/div[3]"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//table[@id='table-cdfdd-components-properties']//td[@title='Table style']"));
    String styleText = ElementHelper.GetText(driver, By.xpath("//table[@id='table-cdfdd-components-properties']//td[@title='Table style']"));
    String style1Text = ElementHelper.GetText(driver, By.xpath("//table[@id='table-cdfdd-components-properties']//td[@title='Table style']/../td[2]"));
    assertEquals("Style", styleText);
    assertEquals("Bootstrap", style1Text);

    /*
     * ## Step 5
     */
    String newText1 = ElementHelper.GetText(driver, By.xpath("//div[@id='headerLinks']/div/a"));
    String saveText1 = ElementHelper.GetText(driver, By.xpath("//div[@id='headerLinks']/div[2]/a"));
    String saveasText1 = ElementHelper.GetText(driver, By.xpath("//div[@id='headerLinks']/div[3]/a"));
    String reloadText1 = ElementHelper.GetText(driver, By.xpath("//div[@id='headerLinks']/div[4]/a"));
    String settingsText1 = ElementHelper.GetText(driver, By.xpath("//div[@id='headerLinks']/div[5]/a"));
    assertEquals("New", newText1);
    assertEquals("Save", saveText1);
    assertEquals("Save as...", saveasText1);
    assertEquals("Reload", reloadText1);
    assertEquals("Settings", settingsText1);
    ElementHelper.ClickJS(driver, By.xpath("//div[@id='headerLinks']/div[5]/a"));

    ElementHelper.WaitForElementVisibility(driver, By.xpath("//select[@id='rendererInput']"));
    Select select = new Select(ElementHelper.FindElement(driver, By.xpath("//select[@id='rendererInput']")));
    select.selectByValue("blueprint");
    ElementHelper.ClickJS(driver, By.xpath("//button[@id='popup_state0_buttonSave']"));

    /*
     * ## Step 6
     */
    wait.until(ExpectedConditions.alertIsPresent());
    Alert alert = driver.switchTo().alert();
    String confirmationMsg = alert.getText();
    String expectedCnfText = "The dashboard contains Table Components whose 'Table Style' property needs to be updated.\nWould you like to UPDATE those properties?";
    alert.accept();
    assertEquals(confirmationMsg, expectedCnfText);

  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDE392.class.getSimpleName());
  }
}
