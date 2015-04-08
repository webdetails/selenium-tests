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
import static org.junit.Assert.assertTrue;

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
import org.pentaho.ctools.utils.HttpUtils;
import org.pentaho.ctools.utils.PUCSettings;
import org.pentaho.ctools.utils.ScreenshotTestRule;
import org.pentaho.gui.web.puc.BrowseFiles;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-417
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1017
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDE417 {

  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDE417.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDE417.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert Popup Export shows the chart on the popup
   *
   * Description:
   *    The test pretends validate the CDE-417 issue, so when user clicks to export chart, popup shown has chart preview.
   *    Also, to validate the CDE-424 issue we'll delete the .js file prior to opening the dashboard and saving the
   *    dashboard should create the .js file again and enable exporting.
   *
   * Steps:
   *    424
   *    1. Open PUC and click Browse Files
   *    2. Go to dashboard folder, click .js file and click Move To Trash
   *    3. Open Export Popup Sample and save Dashboard
   *    417
   *    4. Assert elements on dashboard
   *    5. Click to export chart as PNG, click export, assert chart is shown
   *    6. Click to export chart as SVG, click export, assert chart is shown
   *
   */
  @Test(timeout = 360000)
  public void tc01_PopupExportComponent_PreviewerRendersChart() {
    log.info("tc01_PopupExportComponent_PreviewerRendersChart");

    /*
     * ## Step 1
     */
    // Show Hidden Files
    BrowseFiles browser = new BrowseFiles(driver);
    if (PUCSettings.SHOWHIDDENFILES = false) {
      browser.CheckShowHiddenFiles();
    }

    /*
     * ## Step 2
     */
    browser.DeleteFile("/public/plugin-samples/pentaho-cdf-dd/tests/ExportPopup/BarChart.js");

    WebElement elementFrame = ElementHelper.FindElement(driver, By.xpath("//iframe[@id='browser.perspective']"));
    WebDriver frame = driver.switchTo().frame(elementFrame);
    ElementHelper.WaitForAttributeValue(frame, By.xpath("//div[@id='fileBrowserFiles']/div[2]/div[1]"), "title", "ExportPopupComponent.cda");
    String text = ElementHelper.GetAttribute(frame, By.xpath("//div[@id='fileBrowserFiles']/div[2]/div[1]"), "title");
    assertEquals(text, "ExportPopupComponent.cda");

    /*
     * ## Step 3
     */
    //Go to Export Popup Component sample
    driver.get(baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3AExportPopup%3AExportPopupComponent.wcdf/edit");
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
    WebElement element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//a[@onclick='cdfdd.save()']"));
    assertNotNull(element);
    ElementHelper.Click(driver, By.xpath("//a[@onclick='cdfdd.save()']"));
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='notifyBar']/div[2]"));
    assertNotNull(element);
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='notifyBar']/div[2]"));
    assertEquals(text, "Dashboard saved successfully");

    /*
     * ## Step 4
     */
    driver.get(baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3AExportPopup%3AExportPopupComponent.wcdf/generatedContent");
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
    //Assert chart and export buttons
    WebElement elem = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='ChartExportPNGExporting']/div"));
    assertNotNull(elem);
    elem = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='ChartExportSVGExporting']/div"));
    assertNotNull(elem);
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='TheChartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][2]/*[name()='text']"));
    assertEquals("Car", text);
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='TheChartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][2]/*[name()='g'][2]/*[name()='text']"));
    assertEquals("Bike", text);
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='TheChartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][3]/*[name()='g'][2]/*[name()='text']"));
    assertEquals("Ship", text);
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='TheChartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][4]/*[name()='g'][2]/*[name()='text']"));
    assertEquals("Plane", text);
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='TheChartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][5]/*[name()='g'][2]/*[name()='text']"));
    assertEquals("Train", text);

    /*
     * ## Step 5
     */
    ElementHelper.Click(driver, By.xpath("//div[@id='ChartExportPNGExporting']/div"));
    elem = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@class='exportElement']"));
    assertNotNull(elem);
    elem.click();
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='fancybox-content']/div/div/div/div/div[1]"));
    assertEquals("Export Options", text);
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='fancybox-content']/div/div/div/div/div[2]"));
    assertEquals("Small", text);
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='fancybox-content']/div/div/div/div/div[3]"));
    assertEquals("Medium", text);
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='fancybox-content']/div/div/div/div/div[4]"));
    assertEquals("Large", text);
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='fancybox-content']/div/div/div/div/div[5]"));
    assertEquals("Custom", text);
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='fancybox-content']/div/div/div/div/div[8]"));
    assertEquals("Export", text);
    text = ElementHelper.GetAttribute(driver, By.xpath("//div[@id='fancybox-content']/div/div/div/div[2]/img"), "src");
    assertEquals("http://localhost:8080/pentaho/plugin/cgg/api/services/draw?script=/public/plugin-samples/pentaho-cdf-dd/tests/ExportPopup/BarChart.js&outputType=png&paramwidth=350&paramheight=200", text);
    assertEquals(200, HttpUtils.GetResponseCode("http://localhost:8080/pentaho/plugin/cgg/api/services/draw?script=/public/plugin-samples/pentaho-cdf-dd/tests/ExportPopup/BarChart.js&outputType=png&paramwidth=350&paramheight=200", "admin", "password"));
    ElementHelper.Click(driver, By.id("fancybox-close"));
    assertTrue(ElementHelper.WaitForElementNotPresent(driver, By.xpath("//div[@id='fancybox-content']/div/div/div/div/div[1]")));

    /*
     * ## Step 6
     */
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div/div[@class='exportElement']"));
    ElementHelper.Click(driver, By.xpath("//div[@id='ChartExportSVGExporting']/div"));
    elem = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[9]/div[@class='exportElement']"));
    assertNotNull(elem);
    elem.click();
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='fancybox-content']/div/div/div/div/div[1]"));
    assertEquals("Export Options", text);
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='fancybox-content']/div/div/div/div/div[2]"));
    assertEquals("Small", text);
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='fancybox-content']/div/div/div/div/div[3]"));
    assertEquals("Medium", text);
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='fancybox-content']/div/div/div/div/div[4]"));
    assertEquals("Large", text);
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='fancybox-content']/div/div/div/div/div[5]"));
    assertEquals("Custom", text);
    text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='fancybox-content']/div/div/div/div/div[8]"));
    assertEquals("Export", text);
    text = ElementHelper.GetAttribute(driver, By.xpath("//div[@id='fancybox-content']/div/div/div/div[2]/img"), "src");
    assertEquals("http://localhost:8080/pentaho/plugin/cgg/api/services/draw?script=/public/plugin-samples/pentaho-cdf-dd/tests/ExportPopup/BarChart.js&outputType=svg&paramwidth=350&paramheight=200", text);
    assertEquals(200, HttpUtils.GetResponseCode("http://localhost:8080/pentaho/plugin/cgg/api/services/draw?script=/public/plugin-samples/pentaho-cdf-dd/tests/ExportPopup/BarChart.js&outputType=png&paramwidth=350&paramheight=200", "admin", "password"));
    ElementHelper.Click(driver, By.id("fancybox-close"));
    assertTrue(ElementHelper.WaitForElementNotPresent(driver, By.xpath("//div[@id='fancybox-content']/div/div/div/div/div[1]")));

  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDE417.class.getSimpleName());
  }
}
