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
package org.pentaho.ctools.issues.cda;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.codec.digest.DigestUtils;
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
import org.openqa.selenium.support.ui.Select;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.DirectoryWatcher;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDA-100
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-965
 *
 * NOTE
 * To test this script it is required to have CDA plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDA100 {
  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  //Download directory
  private static String     downloadDir;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDA100.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDA100.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    baseUrl = CToolsTestSuite.getBaseUrl();
    downloadDir = CToolsTestSuite.getDownloadDir();
  }

  @Before
  public void setUpTestCase() {
    //Go to User Console
    driver.get(baseUrl + "plugin/cda/api/previewQuery?path=/public/Issues/CDA-100/CDA-100.cda");

    //wait for invisibility of waiting pop-up
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='busy-indicator-container waitPopup']"));

    //Wait for buttons: button, Cache This AND Query URL
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//button[@id='button']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//button[@id='cachethis']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//button[@id='queryUrl']"));

  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Asserting that export to excel follows output options
   * Description:
   *    The test pretends validate the CDA-108 issue, asserting that export to excel follows output options.
   *
   * Steps:
   *    1. Select "Sample query on SampleData - Jdbc" on "dataAccessSelector"
   *    2. Wait for and assert elements and text on page
   *    3. Export file and assure it has same md5 as expected
   *
   */
  @Test(timeout = 120000)
  public void tc01_CdaFileViewer_ExcelOutputIndex() {
    log.info("tc01_CdaFileViewer_ExcelOutputIndex");

    /*
     * ## Step 1
     */
    ElementHelper.WaitForElementPresence(driver, By.id("dataAccessSelector"));
    Select select = new Select(ElementHelper.FindElement(driver, By.id("dataAccessSelector")));
    select.selectByVisibleText("Sql Query on SampleData - Jdbc");

    /*
     * ## Step 2
     */
    //wait to render page
    ElementHelper.IsElementInvisible(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
    //Check the presented contains
    WebElement elemStatus = ElementHelper.FindElement(driver, By.id("status"));
    assertEquals("Shipped", elemStatus.getAttribute("value"));
    elemStatus = ElementHelper.FindElement(driver, By.id("orderDate"));
    assertEquals("2003-03-01", elemStatus.getAttribute("value"));
    //Check text on table
    String columnOneRowOne = ElementHelper.GetText(driver, By.xpath("//table[@id='contents']/tbody/tr/td"));
    String columnTwoRowOne = ElementHelper.GetText(driver, By.xpath("//table[@id='contents']/tbody/tr/td[2]"));
    assertEquals("S10_1678", columnOneRowOne);
    assertEquals("10107", columnTwoRowOne);

    /*
     * ## Step 3
     */
    WebElement buttonExport = ElementHelper.FindElement(driver, By.id("export"));
    try {
      //Delete the existence if exist
      new File(downloadDir + "\\cda-export.xls").delete();
      assertNotNull(buttonExport);

      //Click to export
      buttonExport.click();

      //Wait for file to be created in the destination dir
      DirectoryWatcher.WatchForCreate(downloadDir);

      //Assert File exists
      File exportFile = new File(downloadDir + "\\cda-export.xls");
      assertTrue(exportFile.exists());

      FileInputStream export = new FileInputStream(exportFile);
      //Assert md5 is as expected
      String md5 = DigestUtils.md5Hex(export);
      export.close();
      assertEquals(md5, "d41d8cd98f00b204e9800998ecf8427e");

    } catch (Exception e) {
      log.error(e.getMessage());
    }

    while (new File(downloadDir + "\\cda-export.xls").exists()) {
      //Delete created file
      new File(downloadDir + "\\cda-export.xls").delete();
    }
    log.info(new File(downloadDir + "\\cda-export.xls").exists());
  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDA108.class.getSimpleName());
  }
}
