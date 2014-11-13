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
package org.pentaho.ctools.cdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpStatus;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.DirectoryWatcher;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * Testing the functionalities related with Prpt Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PrptComponent {
  //Instance of the driver (browser emulator)
  private static WebDriver       driver;
  // Instance to be used on wait commands
  private static Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private static String          baseUrl;
  //Log instance
  private static Logger          log                = LogManager.getLogger(PrptComponent.class);

  @Rule
  public ScreenshotTestRule      screenshotTestRule = new ScreenshotTestRule(driver);

  /**
   * Shall initialized the test before run each test case.
   */
  @BeforeClass
  public static void setUp() {
    log.debug("setup");
    driver = CToolsTestSuite.getDriver();
    wait = CToolsTestSuite.getWait();
    baseUrl = CToolsTestSuite.getBaseUrl();

    // Go to sample
    init();
  }

  /**
   * Go to the PrptComponent web page.
   */
  public static void init() {
    // The URL for the CheckComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Documentation/Component
    // Reference/Core Components/PrptComponent
    driver.get(baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A63-PentahoReportingComponent%3Aprpt_component.xcdf/generatedContent");

    // Not we have to wait for loading disappear
    ElementHelper.IsElementInvisible(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Reload Sample
   * Description:
   *    Reload the sample (not refresh page).
   * Steps:
   *    1. Click in Code and then click in button 'Try me'.
   */
  @Test(timeout = 60000)
  public void tc1_PageContent_DisplayTitle() {
    // Wait for title become visible and with value 'Community Dashboard Framework'
    wait.until(ExpectedConditions.titleContains("Community Dashboard Framework"));
    // Wait for visibility of 'VisualizationAPIComponent'
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dashboardContent']/div/div/div/h2/span[2]")));

    // Validate the sample that we are testing is the one
    assertEquals("Community Dashboard Framework", driver.getTitle());
    assertEquals("PrptComponent", ElementHelper.GetText(driver, By.xpath("//div[@id='dashboardContent']/div/div/div/h2/span[2]")));
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Reload Sample
   * Description:
   *    Reload the sample (not refresh page).
   * Steps:
   *    1. Click in Code and then click in button 'Try me'.
   */
  @Test(timeout = 60000)
  public void tc2_ReloadSample_SampleReadyToUse() {
    // ## Step 1
    // Render again the sample
    ElementHelper.FindElement(driver, By.xpath("//div[@id='example']/ul/li[2]/a")).click();
    ElementHelper.FindElement(driver, By.xpath("//div[@id='code']/button")).click();

    // Not we have to wait for loading disappear
    ElementHelper.IsElementInvisible(driver, By.xpath("//div[@class='blockUI blockOverlay']"));

    // Now sample element must be displayed
    assertTrue(ElementHelper.FindElement(driver, By.id("sample")).isDisplayed());

    //Check the number of divs with id 'SampleObject'
    //Hence, we guarantee when click Try Me the previous div is replaced
    int nSampleObject = driver.findElements(By.id("sampleObject")).size();
    assertEquals(1, nSampleObject);
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Select HTML and PDF (content type)
   * Description:
   *    The test case pretends validate the result when change for option HTML
   *    and PDF for content-type.
   * Steps:
   *    1. Select PDF
   *    2. Select HTML
   */
  @Test(timeout = 60000)
  public void tc3_SelectHTMLAndPDF_PageDisplayedAccording() {
    log.debug("tc3_SelectHTMLAndPDF_PageDisplayedAccording");

    // ## Step 1
    Select select = new Select(ElementHelper.FindElement(driver, By.cssSelector("select")));
    select.selectByValue("application/pdf");
    // Not we have to wait for loading disappear
    ElementHelper.IsElementInvisible(driver, By.xpath("//div[@class='blockUI blockOverlay']"));

    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("sampleObject")));
    driver.switchTo().frame("sampleObject_prptFrame");
    //Check presence of tool bar elements
    assertNotNull(ElementHelper.FindElement(driver, By.xpath("//div[@id='toolbar']/div")));
    assertNotNull(ElementHelper.FindElement(driver, By.xpath("//div[@id='toolbar']/div[2]")));
    assertNotNull(ElementHelper.FindElement(driver, By.xpath("//div[@id='toolbar']/span")));
    //Check the Product Name and Output Type
    String prodName = ElementHelper.GetText(driver, By.xpath("//td/div/div"));
    assertEquals("Product Name", prodName);
    assertNotNull(ElementHelper.FindElement(driver, By.xpath("//td/div/div[2]/select")));
    String outputTypeName = ElementHelper.GetText(driver, By.xpath("//tr[2]/td/div/div"));
    assertEquals("Output Type", outputTypeName);
    assertNotNull(ElementHelper.FindElement(driver, By.xpath("//tr[2]/td/div/div[2]/select")));
    //Check for View Report button
    String buttonName = ElementHelper.GetText(driver, By.xpath("//button/span"));
    assertEquals("View Report", buttonName);
    //Check the generated image
    driver.switchTo().frame("reportContent");
    WebElement image = ElementHelper.FindElement(driver, By.cssSelector("img.style-3"));
    assertNotNull(image);
    String attrSrc = image.getAttribute("src");
    assertTrue(attrSrc.startsWith(baseUrl + "getImage?image=picture"));
    try {
      URL url = new URL(attrSrc);
      URLConnection connection = url.openConnection();
      connection.connect();

      assertEquals(HttpStatus.SC_OK, ((HttpURLConnection) connection).getResponseCode());
    } catch (Exception ex) {
      log.error(ex.getMessage());
    }

    // ## Step 2
    driver.switchTo().defaultContent();
    select = new Select(ElementHelper.FindElement(driver, By.cssSelector("select")));
    select.selectByValue("text/html");

    // Not we have to wait for loading disappear
    ElementHelper.IsElementInvisible(driver, By.xpath("//div[@class='blockUI blockOverlay']"));

    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("sampleObject")));
    driver.switchTo().frame("sampleObject_prptFrame");
    //Check presence of tool bar elements
    assertNotNull(ElementHelper.FindElement(driver, By.xpath("//div[@id='toolbar']/div")));
    assertNotNull(ElementHelper.FindElement(driver, By.xpath("//div[@id='toolbar']/div[2]")));
    assertNotNull(ElementHelper.FindElement(driver, By.xpath("//div[@id='toolbar']/span")));
    //Check the Product Name and Output Type
    prodName = ElementHelper.GetText(driver, By.xpath("//td/div/div"));
    assertEquals("Product Name", prodName);
    assertNotNull(ElementHelper.FindElement(driver, By.xpath("//td/div/div[2]/select")));
    outputTypeName = ElementHelper.GetText(driver, By.xpath("//tr[2]/td/div/div"));
    assertEquals("Output Type", outputTypeName);
    assertNotNull(ElementHelper.FindElement(driver, By.xpath("//tr[2]/td/div/div[2]/select")));
    //Check for View Report button
    buttonName = ElementHelper.GetText(driver, By.xpath("//button/span"));
    assertEquals("View Report", buttonName);
    //Check the generated image
    driver.switchTo().frame("reportContent");
    image = ElementHelper.FindElement(driver, By.cssSelector("img.style-3"));
    assertNotNull(image);
    attrSrc = image.getAttribute("src");
    assertTrue(attrSrc.startsWith(baseUrl + "getImage?image=picture"));
    try {
      URL url = new URL(attrSrc);
      URLConnection connection = url.openConnection();
      connection.connect();

      assertEquals(HttpStatus.SC_OK, ((HttpURLConnection) connection).getResponseCode());
    } catch (Exception ex) {
      log.error(ex.getMessage());
    }
  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name:
   *    Toggle Prompt Panel
   * Description:
   *    The test case pretends disable and enable the prompt panel.
   * Steps:
   *    1. Enable prompt panel
   *    2. Disable prompt panel
   */
  @Test(timeout = 60000)
  public void tc4_TogglePromptPanel_PromptPanelEnableDisable() {
    log.debug("tc4_TogglePromptPanel_PromptPanelEnableDisable");
    driver.switchTo().defaultContent();

    // ## Step 1
    driver.switchTo().frame("sampleObject_prptFrame");
    assertNotNull(ElementHelper.FindElement(driver, By.id("reportControlPanel")));
    ElementHelper.FindElement(driver, By.xpath("//span[@id='toolbar-parameterToggle']/span")).click();
    assertTrue(ElementHelper.IsElementInvisible(driver, By.id("reportControlPanel")));

    // ## Step 2
    ElementHelper.FindElement(driver, By.xpath("//span[@id='toolbar-parameterToggle']/span")).click();
    assertNotNull(ElementHelper.FindElement(driver, By.id("reportControlPanel")));
  }

  /**
   * ############################### Test Case 5 ###############################
   *
   * Test Case Name:
   *    Select Several Products
   * Description:
   *    The test case pretends to validate that when we select a product a new
   *    report is generated.
   * Steps:
   *    1. Select product '1939 Chevrolet Deluxe Coupe'
   *    2. Select product 'Diamond T620 Semi-Skirted Tanker'
   *    3. Click in View Report
   */
  @Test(timeout = 60000)
  public void tc5_SelectSeveralProducts_ReportIsRefreshed() {
    log.debug("tc5_SelectSeveralProducts_ReportIsRefreshed");
    driver.switchTo().defaultContent();
    driver.switchTo().frame("sampleObject_prptFrame");
    String previewsAttrSrc = "";

    // ## Step 1
    driver.switchTo().frame("reportContent");
    WebElement image = ElementHelper.FindElement(driver, By.cssSelector("img.style-3"));
    String attrSrc = image.getAttribute("src");
    previewsAttrSrc = attrSrc;
    driver.switchTo().defaultContent();
    driver.switchTo().frame("sampleObject_prptFrame");
    Select selProductName = new Select(ElementHelper.FindElement(driver, By.xpath("//td/div/div[2]/select")));
    selProductName.selectByValue("S18_3856");
    ElementHelper.IsElementInvisible(driver, By.id("glasspane"));
    assertTrue(ElementHelper.FindElement(driver, By.xpath("//td/div/div[2]/select/option[@value='S18_3856']")).isSelected());
    driver.switchTo().frame("reportContent");
    image = ElementHelper.FindElement(driver, By.cssSelector("img.style-3"));
    attrSrc = image.getAttribute("src");
    assertNotEquals(previewsAttrSrc, attrSrc);
    previewsAttrSrc = attrSrc;

    // ## Step 2
    driver.switchTo().defaultContent();
    driver.switchTo().frame("sampleObject_prptFrame");
    selProductName = new Select(ElementHelper.FindElement(driver, By.xpath("//td/div/div[2]/select")));
    selProductName.selectByValue("S50_1392");
    ElementHelper.IsElementInvisible(driver, By.id("glasspane"));
    assertTrue(ElementHelper.FindElement(driver, By.xpath("//td/div/div[2]/select/option[@value='S50_1392']")).isSelected());
    driver.switchTo().frame("reportContent");
    image = ElementHelper.FindElement(driver, By.cssSelector("img.style-3"));
    attrSrc = image.getAttribute("src");
    assertNotEquals(previewsAttrSrc, attrSrc);
    previewsAttrSrc = attrSrc;

    // ## Step 3
    driver.switchTo().defaultContent();
    driver.switchTo().frame("sampleObject_prptFrame");
    ElementHelper.FindElement(driver, By.xpath("//button/span")).click();
    ElementHelper.IsElementInvisible(driver, By.id("glasspane"));
    assertTrue(ElementHelper.FindElement(driver, By.xpath("//td/div/div[2]/select/option[@value='S50_1392']")).isSelected());
    driver.switchTo().frame("reportContent");
    assertNotNull(ElementHelper.FindElement(driver, By.cssSelector("img.style-3")));
  }

  /**
   * ############################### Test Case 6 ###############################
   *
   * Test Case Name:
   *    Output Type
   * Description:
   *    We pretend to test all Output Type options.
   * Steps:
   *    1. Select: HTML (Paginated)
   *    2. Select: HTML (Single Page)
   *    3. Select: PDF
   *    4. Select: Excel
   *    5. Select: Excel 2007
   *    6. Select: Comma Separated Value
   *    7. Select: Rich-Text-Format
   *    8. Select: Text
   * @throws InterruptedException
   */
  @Test(timeout = 60000)
  public void tc6_SelectAllOutputTypeOptions_DialogBoxIsRaised() throws InterruptedException {
    log.debug("tc6_SelectAllOutputTypeOptions_DialogBoxIsRaised");
    driver.switchTo().defaultContent();

    String downloadDir = CToolsTestSuite.getDownloadDir();

    // ## Step 1
    driver.switchTo().frame("sampleObject_prptFrame");
    Select select = new Select(ElementHelper.FindElement(driver, By.xpath("//tr[2]/td/div/div[2]/select")));
    select.selectByValue("table/html;page-mode=page");
    ElementHelper.IsElementInvisible(driver, By.id("glasspane"));
    //Check the generated image
    driver.switchTo().frame("reportContent");
    WebElement image = ElementHelper.FindElement(driver, By.cssSelector("img.style-3"));
    assertNotNull(image);
    String attrSrc = image.getAttribute("src");
    assertTrue(attrSrc.startsWith(baseUrl + "getImage?image=picture"));

    // ## Step 2
    driver.switchTo().defaultContent();
    driver.switchTo().frame("sampleObject_prptFrame");
    select = new Select(ElementHelper.FindElement(driver, By.xpath("//tr[2]/td/div/div[2]/select")));
    select.selectByValue("table/html;page-mode=stream");
    ElementHelper.IsElementInvisible(driver, By.id("glasspane"));
    //Check the generated image
    driver.switchTo().frame("reportContent");
    image = ElementHelper.FindElement(driver, By.cssSelector("img.style-3"));
    assertNotNull(image);
    attrSrc = image.getAttribute("src");
    assertTrue(attrSrc.startsWith(baseUrl + "getImage?image=picture"));

    // ## Step 3
    driver.switchTo().defaultContent();
    driver.switchTo().frame("sampleObject_prptFrame");
    select = new Select(ElementHelper.FindElement(driver, By.xpath("//tr[2]/td/div/div[2]/select")));
    select.selectByValue("pageable/pdf");
    ElementHelper.IsElementInvisible(driver, By.id("glasspane"));
    //Check the generated image
    driver.switchTo().frame("reportContent");
    ElementHelper.IsElementInvisible(driver, By.cssSelector("img.style-3"));
    assertNotNull(ElementHelper.FindElement(driver, By.id("pageContainer1")));

    // ## Step 4
    driver.switchTo().defaultContent();
    driver.switchTo().frame("sampleObject_prptFrame");
    new File(downloadDir + "\\Product Sales.xls").delete();
    select = new Select(ElementHelper.FindElement(driver, By.xpath("//tr[2]/td/div/div[2]/select")));
    select.selectByValue("table/excel;page-mode=flow");
    //Wait for file to be created in the destination dir
    DirectoryWatcher.WatchForCreate(downloadDir);
    ElementHelper.IsElementInvisible(driver, By.id("glasspane"));    
    assertTrue(new File(downloadDir + "\\Product Sales.xls").exists());
    new File(downloadDir + "\\Product Sales.xls").delete();

    // ## Step 5
    new File(downloadDir + "\\Product Sales.xlsx").delete();
    select = new Select(ElementHelper.FindElement(driver, By.xpath("//tr[2]/td/div/div[2]/select")));
    select.selectByValue("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;page-mode=flow");
    //Wait for file to be created in the destination dir
    DirectoryWatcher.WatchForCreate(downloadDir);
    ElementHelper.IsElementInvisible(driver, By.id("glasspane"));
    assertTrue(new File(downloadDir + "\\Product Sales.xlsx").exists());
    new File(downloadDir + "\\Product Sales.xlsx").delete();

    // ## Step 6
    new File(downloadDir + "\\Product Sales.csv").delete();
    select = new Select(ElementHelper.FindElement(driver, By.xpath("//tr[2]/td/div/div[2]/select")));
    select.selectByValue("table/csv;page-mode=stream");
    //Wait for file to be created in the destination dir
    DirectoryWatcher.WatchForCreate(downloadDir);
    ElementHelper.IsElementInvisible(driver, By.id("glasspane"));    
    assertTrue(new File(downloadDir + "\\Product Sales.csv").exists());
    new File(downloadDir + "\\Product Sales.csv").delete();

    // ## Step 7
    new File(downloadDir + "\\Product Sales.rtf").delete();
    select = new Select(ElementHelper.FindElement(driver, By.xpath("//tr[2]/td/div/div[2]/select")));
    select.selectByValue("table/rtf;page-mode=flow");
    //Wait for file to be created in the destination dir
    DirectoryWatcher.WatchForCreate(downloadDir);
    ElementHelper.IsElementInvisible(driver, By.id("glasspane"));
    assertTrue(new File(downloadDir + "\\Product Sales.rtf").exists());
    new File(downloadDir + "\\Product Sales.rtf").delete();

    // ## Step 8
    //TODO - pageable/text
  }

  @AfterClass
  public static void tearDown() {
    log.debug("tearDown");
  }
}
