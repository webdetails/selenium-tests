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

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

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
 * - http://jira.pentaho.com/browse/CDE-286
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1016
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDE286 {
  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  //Instance to be used on wait commands
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDE286.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDE286.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Asserting CGG Dial Chart can be exported and viewed via URL
   *
   * Description:
   *    The test pretends validate the CDE-286 issue, so when user exports a CGG Dial Component it can
   *    be viewed via the URL given.
   *
   * Steps:
   *    1. Wait for new Dashboard to be created, assert elements on page and click "Components Panel"
   *    2. Add CGG Dial Component and fill it's properties
   *    3. Click "Shift+G" to create export file and choose URL
   *    4. Add "&param=25" to URL and assert Dial Component is properly shown
   *
   */
  @Test(timeout = 120000)
  public void tc01_NewCdeDashboard_CggDialComponentExport() {
    log.info("tc01_NewCdeDashboard_CggDialComponentExport");

    /*
     * ## Step 1
     */
    //Go to New CDE Dashboard
    driver.get(baseUrl + "api/repos/wcdf/new");

    // Not we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));

    //assert buttons
    WebElement element = ElementHelper.FindElement(driver, By.xpath("//a[@title='Save as Template']"));
    assertNotNull(element);
    element = ElementHelper.FindElement(driver, By.xpath("//a[@title='Apply Template']"));
    assertNotNull(element);
    element = ElementHelper.FindElement(driver, By.xpath("//a[@title='Add Resource']"));
    assertNotNull(element);
    element = ElementHelper.FindElement(driver, By.xpath("//a[@title='Add Bootstrap Panel']"));
    assertNotNull(element);
    element = ElementHelper.FindElement(driver, By.xpath("//a[@title='Add FreeForm']"));
    assertNotNull(element);
    element = ElementHelper.FindElement(driver, By.xpath("//a[@title='Add Row']"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//div[@class='componentsPanelButton']"));

    /*
     * ## Step 2
     */
    ElementHelper.Click(driver, By.xpath("//div[@id='cdfdd-components-palletePallete']/div/h3/span"));
    ElementHelper.Click(driver, By.xpath("//div[@id='cdfdd-components-palletePallete']/div/div/ul/li[24]/a[@title='CGG Dial Chart']"));
    String text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='table-cdfdd-components-components']/tbody/tr[2]/td"));
    assertEquals("CGG Dial Chart", text);

    //Add Name
    ElementHelper.FindElement(driver, By.xpath("//div[@id='cdfdd-components-properties']/div/div[2]/table/tbody/tr/td[2]/form/input")).sendKeys("dial");
    ElementHelper.FindElement(driver, By.xpath("//div[@id='cdfdd-components-properties']/div/div[2]/table/tbody/tr/td[2]/form/input")).submit();
    ElementHelper.WaitForTextPresence(driver, By.xpath("//div[@id='cdfdd-components-properties']/div/div[2]/table/tbody/tr/td[2]"), "dial");
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='cdfdd-components-properties']/div/div[2]/table/tbody/tr/td[2]"));
    assertEquals("dial", text);

    //Add Colour Range
    ElementHelper.Click(driver, By.xpath("//table[@id='table-cdfdd-components-properties']/tbody/tr[2]/td[2]"));
    element = ElementHelper.FindElement(driver, By.xpath("//div[@id='popupstates']/div/div/div/input"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//div[@id='popupstates']/div/div/div/input"));
    element = ElementHelper.FindElement(driver, By.xpath("//div[@id='StringArray']/div/div/div/input"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//div[@id='popupstates']/div/div/div/input"));
    element = ElementHelper.FindElement(driver, By.xpath("//div[@id='StringArray']/div/div[2]/div/input"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//div[@id='popupstates']/div/div/div/input"));
    element = ElementHelper.FindElement(driver, By.xpath("//div[@id='StringArray']/div/div[3]/div/input"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//div[@id='popupstates']/div/div/div/div/div/div/input"));
    ElementHelper.FindElement(driver, By.xpath("//div[@id='popupstates']/div/div/div/div/div/div/input")).sendKeys("blue");
    ElementHelper.Click(driver, By.xpath("//div[@id='popupstates']/div/div/div/div/div[2]/div/input"));
    ElementHelper.FindElement(driver, By.xpath("//div[@id='popupstates']/div/div/div/div/div[2]/div/input")).sendKeys("green");
    ElementHelper.Click(driver, By.xpath("//div[@id='popupstates']/div/div/div/div/div[3]/div/input"));
    ElementHelper.FindElement(driver, By.xpath("//div[@id='popupstates']/div/div/div/div/div[3]/div/input")).sendKeys("yellow");
    ElementHelper.Click(driver, By.xpath("//div[@id='popupstates']/div/div[2]/button"));
    ElementHelper.WaitForTextPresence(driver, By.xpath("//table[@id='table-cdfdd-components-properties']/tbody/tr[2]/td[2]"), "[\"blue\",\"green\",\"yellow\"]");
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='table-cdfdd-components-properties']/tbody/tr[2]/td[2]"));
    assertEquals("[\"blue\",\"green\",\"yellow\"]", text);

    //Add Intervals Array
    ElementHelper.Click(driver, By.xpath("//table[@id='table-cdfdd-components-properties']/tbody/tr[3]/td[2]"));
    element = ElementHelper.FindElement(driver, By.xpath("//div[@id='popupstates']/div/div/div/input"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//div[@id='popupstates']/div/div/div/input"));
    element = ElementHelper.FindElement(driver, By.xpath("//div[@id='StringArray']/div/div/div/input"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//div[@id='popupstates']/div/div/div/input"));
    element = ElementHelper.FindElement(driver, By.xpath("//div[@id='StringArray']/div/div[2]/div/input"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//div[@id='popupstates']/div/div/div/input"));
    element = ElementHelper.FindElement(driver, By.xpath("//div[@id='StringArray']/div/div[3]/div/input"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//div[@id='popupstates']/div/div/div/input"));
    ElementHelper.FindElement(driver, By.xpath("//div[@id='StringArray']/div/div[4]/div/input"));
    ElementHelper.FindElement(driver, By.xpath("//div[@id='StringArray']/div/div/div/input")).sendKeys("0");
    ElementHelper.FindElement(driver, By.xpath("//div[@id='StringArray']/div/div[2]/div/input")).sendKeys("10");
    ElementHelper.FindElement(driver, By.xpath("//div[@id='StringArray']/div/div[3]/div/input")).sendKeys("20");
    ElementHelper.FindElement(driver, By.xpath("//div[@id='StringArray']/div/div[4]/div/input")).sendKeys("30");
    ElementHelper.Click(driver, By.xpath("//div[@id='popupstates']/div/div[2]/button"));
    ElementHelper.WaitForTextPresence(driver, By.xpath("//table[@id='table-cdfdd-components-properties']/tbody/tr[3]/td[2]"), "[\"0\",\"10\",\"20\",\"30\"]");
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='table-cdfdd-components-properties']/tbody/tr[3]/td[2]"));
    assertEquals("[\"0\",\"10\",\"20\",\"30\"]", text);

    //Add Parameter
    ElementHelper.Click(driver, By.xpath("//table[@id='table-cdfdd-components-properties']/tbody/tr[4]/td[2]"));
    ElementHelper.FindElement(driver, By.xpath("//table[@id='table-cdfdd-components-properties']/tbody/tr[4]/td[2]/form/input")).sendKeys("25");
    ElementHelper.FindElement(driver, By.xpath("//table[@id='table-cdfdd-components-properties']/tbody/tr[4]/td[2]/form/input")).submit();
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='table-cdfdd-components-properties']/tbody/tr[4]/td[2]"));
    assertEquals("25", text);

    //Add Width
    ElementHelper.Click(driver, By.xpath("//table[@id='table-cdfdd-components-properties']/tbody/tr[6]/td[2]"));
    ElementHelper.FindElement(driver, By.xpath("//table[@id='table-cdfdd-components-properties']/tbody/tr[6]/td[2]/form/input")).sendKeys("300");
    ElementHelper.FindElement(driver, By.xpath("//table[@id='table-cdfdd-components-properties']/tbody/tr[6]/td[2]/form/input")).submit();
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='table-cdfdd-components-properties']/tbody/tr[6]/td[2]"));
    assertEquals("300", text);

    //Add Height
    ElementHelper.Click(driver, By.xpath("//table[@id='table-cdfdd-components-properties']/tbody/tr[7]/td[2]"));
    ElementHelper.FindElement(driver, By.xpath("//table[@id='table-cdfdd-components-properties']/tbody/tr[7]/td[2]/form/input")).sendKeys("300");
    ElementHelper.FindElement(driver, By.xpath("//table[@id='table-cdfdd-components-properties']/tbody/tr[7]/td[2]/form/input")).submit();
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='table-cdfdd-components-properties']/tbody/tr[7]/td[2]"));
    assertEquals("300", text);

    /*
     * ## Step 3
     */
    Robot robot;
    try {
      robot = new Robot();
      robot.keyPress(KeyEvent.VK_SHIFT);
      robot.keyPress(KeyEvent.VK_G);
      robot.keyRelease(KeyEvent.VK_G);
      robot.keyRelease(KeyEvent.VK_SHIFT);
    } catch (AWTException e) {
      e.printStackTrace();
    }
    element = ElementHelper.FindElement(driver, By.id("cggDialog"));
    assertNotNull(element);
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='cggDialog']/h3"));
    assertEquals("Choose what charts to render as CGG", text);
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='cggDialog']/div/span"));
    assertEquals("dial", text);
    ElementHelper.Click(driver, By.xpath("//div[@id='cggDialog']/div/input"));
    ElementHelper.Click(driver, By.xpath("//div[@id='cggDialog']/div/button"));
    text = ElementHelper.GetAttribute(driver, By.xpath("//div[@id='cggDialog']/div/div/input"), "value");
    assertEquals("http://localhost:8080/pentaho/plugin/cgg/api/services/draw?script=/system/pentaho-cdf-dd/resources/custom/components/cgg/charts/dial.js&paramcolors=blue&paramcolors=green&paramcolors=yellow&paramscale=0&paramscale=10&paramscale=20&paramscale=30&width=300&height=300&outputType=png", text);

    /*
     * ## Step 4
     */
    driver.get(text + "&paramvalue=25");
    element = ElementHelper.FindElement(driver, By.xpath("//body/img"));
    assertNotNull(element);
    text = ElementHelper.GetAttribute(driver, By.xpath("//body/img"), "src");
    assertEquals("http://localhost:8080/pentaho/plugin/cgg/api/services/draw?script=/system/pentaho-cdf-dd/resources/custom/components/cgg/charts/dial.js&paramcolors=blue&paramcolors=green&paramcolors=yellow&paramscale=0&paramscale=10&paramscale=20&paramscale=30&width=300&height=300&outputType=png&paramvalue=25", text);

  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDE286.class.getSimpleName());
  }
}
