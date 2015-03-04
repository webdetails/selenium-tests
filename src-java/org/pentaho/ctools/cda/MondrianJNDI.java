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
package org.pentaho.ctools.cda;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
 * Testing the functionalities related with Mondrianjndi.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MondrianJNDI{

  // Instance of the driver (browser emulator)
  private static WebDriver       driver;
  // Instance to be used on wait commands
  private static Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private static String          baseUrl;
  //Download directory
  private static String          downloadDir;
  //Log instance
  private static Logger          log                = LogManager.getLogger(MondrianJNDI.class);

  @Rule
  public ScreenshotTestRule      screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    driver = CToolsTestSuite.getDriver();
    wait = CToolsTestSuite.getWait();
    baseUrl = CToolsTestSuite.getBaseUrl();
    downloadDir = CToolsTestSuite.getDownloadDir();
  }

  @Before
  public void setUpTestCase() {
    // This samples is in: Public/plugin-samples/CDA/cdafiles/mondrian-jndi
    driver.get(baseUrl + "plugin/cda/api/previewQuery?path=%2Fpublic%2Fplugin-samples%2Fcda%2Fcdafiles%2Fmondrian-jndi.cda");
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Page Content
   * Description:
   *    The test case pretends to validate if the field filename and about
   *    are present and the about works as expected.
   * Steps:
   *    1. Check if we are reading the correct file
   *    2. Check if About is working
   */
  @Test(timeout = 60000)
  public void tc1_PageContent_DisplayeFilenameAndAbout() {
    log.info("tc1_PageContent_DisplayeFilenameAndAbout");

    /*
     * ## Step 1
     */
    //Check if the field 'filename' exist and expected value

    String filename = ElementHelper.WaitForTextPresence(driver, By.id("fileid"), "/public/plugin-samples/cda/cdafiles/mondrian-jndi.cda");
    String pleaseSelect = ElementHelper.WaitForTextPresence(driver, By.id("pleaseselect"), "Please select a Data Access ID");

    assertEquals("/public/plugin-samples/cda/cdafiles/mondrian-jndi.cda", filename);
    assertEquals("Please select a Data Access ID", pleaseSelect);

    /*
     * ## Step 2
     */
    //Check the About
    ElementHelper.FindElement(driver, By.linkText("About")).click();
    //element 'fileid'
    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("fileid")));
    String textElementFileid = ElementHelper.WaitForElementPresentGetText(driver, By.id("fileid"));
    //element image
    WebElement elemImg = ElementHelper.FindElement(driver, By.cssSelector("img"));
    String imgAttrSrc = elemImg.getAttribute("src");
    //element //div[@id='aboutSubContainerLeft']/div[2]/p
    String textFirstParagExpected = "CDA is a Pentaho plugin designed for accessing data with great flexibility. Born to overcoming some cons of the older implementation, CDA allows you to access any of the various Pentaho data sources and:";
    String textFirstParag = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='aboutSubContainerLeft']/div[2]/p"));
    //element //div[@id='aboutSubContainerLeft']/div[2]/p[2]
    String textSecondParagExpected = "CDA can be used as a standalone plugin on the Pentaho BI server or in combination with CDE / CDF.";
    String textSecondParag = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='aboutSubContainerLeft']/div[2]/p[2]"));

    assertEquals("Hello!", textElementFileid);
    assertEquals(baseUrl + "api/repos/cda/static/cda.png", imgAttrSrc);
    assertEquals(textFirstParagExpected, textFirstParag);
    assertEquals(textSecondParagExpected, textSecondParag);

    //Back to Mondrian JNDI
    ElementHelper.Click(driver, By.linkText("Back to CDA"));
    //Check if we are in the correct page
    filename = ElementHelper.WaitForTextPresence(driver, By.id("fileid"), "/public/plugin-samples/cda/cdafiles/mondrian-jndi.cda");
    pleaseSelect = ElementHelper.WaitForTextPresence(driver, By.id("pleaseselect"), "Please select a Data Access ID");

    assertEquals("/public/plugin-samples/cda/cdafiles/mondrian-jndi.cda", filename);
    assertEquals("Please select a Data Access ID", pleaseSelect);
    assertNotNull(ElementHelper.FindElement(driver, By.linkText("About")));
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Select Data Access
   * Description:
   *    The test case pretends to validate the data presented when we select a
   *    data access, checking the ordering and search.
   * Steps:
   *    1. Select data access 'Mdx Query on SampleData - Jndi'
   *    2. Order by Year
   *    3. Order by Price
   *    4. Order by PriceInk
   *    5. Search existence content
   *    6. Search inexistance content
   */
  @Test(timeout = 60000)
  public void tc2_SelectDataAccess_DisplayDataForSelectedDataAccess() {
    log.info("tc2_SelectDataAccess_DisplayDataForSelectedDataAccess");

    /*
     * ## Step 1
     */
    Select select = new Select(ElementHelper.FindElement(driver, By.id("dataAccessSelector")));
    select.selectByVisibleText("Mdx Query on SampleData - Jndi");
    //wait to render page
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
    //Check the presented contains
    WebElement elemStatus = ElementHelper.FindElement(driver, By.id("status"));
    assertEquals("Shipped", elemStatus.getAttribute("value"));
    //Check we have three elements and no more than that
    String textPaging = ElementHelper.WaitForElementPresentGetText(driver, By.id("contents_info"));
    assertEquals("View 1 to 3 of 3 elements", textPaging);
    //Column Time
    String columnOneRowOne = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr/td"));
    String columnOneRowTwo = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[2]/td"));
    String columnOneRowThree = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[3]/td"));
    assertEquals("All Years", columnOneRowOne);
    assertEquals("All Years", columnOneRowTwo);
    assertEquals("All Years", columnOneRowThree);
    //Column Year
    String columnTwoRowOne = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr/td[2]"));
    String columnTwoRowTwo = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[2]/td[2]"));
    String columnTwoRowThree = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[3]/td[2]"));
    assertEquals("2003", columnTwoRowOne);
    assertEquals("2004", columnTwoRowTwo);
    assertEquals("2005", columnTwoRowThree);
    //Column Price
    String columnThreeRowOne = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr/td[3]"));
    String columnThreeRowTwo = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[2]/td[3]"));
    String columnThreeRowThree = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[3]/td[3]"));
    assertEquals("3573701.2500000023", columnThreeRowOne);
    assertEquals("4750205.889999998", columnThreeRowTwo);
    assertEquals("1513074.4600000002", columnThreeRowThree);
    //Column PriceInk
    String columnFourRowOne = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr/td[4]"));
    String columnFourRowTwo = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[2]/td[4]"));
    String columnFourRowThree = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[3]/td[4]"));
    assertEquals("3.5737012500000023", columnFourRowOne);
    assertEquals("4.750205889999998", columnFourRowTwo);
    assertEquals("1.5130744600000001", columnFourRowThree);

    /*
     * ## Step 2
     */
    //Click Asc
    ElementHelper.FindElement(driver, By.xpath("//table[@id='contents']/thead/tr/th[2]")).click();
    //Click Desc
    ElementHelper.FindElement(driver, By.xpath("//table[@id='contents']/thead/tr/th[2]")).click();
    //Column Time
    columnOneRowOne = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr/td"));
    columnOneRowTwo = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[2]/td"));
    columnOneRowThree = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[3]/td"));
    assertEquals("All Years", columnOneRowOne);
    assertEquals("All Years", columnOneRowTwo);
    assertEquals("All Years", columnOneRowThree);
    //Column Year
    columnTwoRowOne = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr/td[2]"));
    columnTwoRowTwo = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[2]/td[2]"));
    columnTwoRowThree = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[3]/td[2]"));
    assertEquals("2005", columnTwoRowOne);
    assertEquals("2004", columnTwoRowTwo);
    assertEquals("2003", columnTwoRowThree);
    //Column Price
    columnThreeRowOne = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr/td[3]"));
    columnThreeRowTwo = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[2]/td[3]"));
    columnThreeRowThree = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[3]/td[3]"));
    assertEquals("1513074.4600000002", columnThreeRowOne);
    assertEquals("4750205.889999998", columnThreeRowTwo);
    assertEquals("3573701.2500000023", columnThreeRowThree);
    //Column PriceInk
    columnFourRowOne = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr/td[4]"));
    columnFourRowTwo = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[2]/td[4]"));
    columnFourRowThree = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[3]/td[4]"));
    assertEquals("1.5130744600000001", columnFourRowOne);
    assertEquals("4.750205889999998", columnFourRowTwo);
    assertEquals("3.5737012500000023", columnFourRowThree);

    /*
     * ## Step 3
     */
    //Click Asc
    ElementHelper.FindElement(driver, By.xpath("//table[@id='contents']/thead/tr/th[3]")).click();
    //Click Desc
    ElementHelper.FindElement(driver, By.xpath("//table[@id='contents']/thead/tr/th[3]")).click();
    //Column Time
    columnOneRowOne = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr/td"));
    columnOneRowTwo = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[2]/td"));
    columnOneRowThree = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[3]/td"));
    assertEquals("All Years", columnOneRowOne);
    assertEquals("All Years", columnOneRowTwo);
    assertEquals("All Years", columnOneRowThree);
    //Column Year
    columnTwoRowOne = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr/td[2]"));
    columnTwoRowTwo = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[2]/td[2]"));
    columnTwoRowThree = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[3]/td[2]"));
    assertEquals("2004", columnTwoRowOne);
    assertEquals("2003", columnTwoRowTwo);
    assertEquals("2005", columnTwoRowThree);
    //Column Price
    columnThreeRowOne = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr/td[3]"));
    columnThreeRowTwo = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[2]/td[3]"));
    columnThreeRowThree = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[3]/td[3]"));
    assertEquals("4750205.889999998", columnThreeRowOne);
    assertEquals("3573701.2500000023", columnThreeRowTwo);
    assertEquals("1513074.4600000002", columnThreeRowThree);
    //Column PriceInk
    columnFourRowOne = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr/td[4]"));
    columnFourRowTwo = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[2]/td[4]"));
    columnFourRowThree = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[3]/td[4]"));
    assertEquals("4.750205889999998", columnFourRowOne);
    assertEquals("3.5737012500000023", columnFourRowTwo);
    assertEquals("1.5130744600000001", columnFourRowThree);

    /*
     * ## Step 4
     */
    //Click Asc
    ElementHelper.FindElement(driver, By.xpath("//table[@id='contents']/thead/tr/th[4]")).click();
    //Click Desc
    ElementHelper.FindElement(driver, By.xpath("//table[@id='contents']/thead/tr/th[4]")).click();
    //Column Time
    columnOneRowOne = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr/td"));
    columnOneRowTwo = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[2]/td"));
    columnOneRowThree = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[3]/td"));
    assertEquals("All Years", columnOneRowOne);
    assertEquals("All Years", columnOneRowTwo);
    assertEquals("All Years", columnOneRowThree);
    //Column Year
    columnTwoRowOne = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr/td[2]"));
    columnTwoRowTwo = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[2]/td[2]"));
    columnTwoRowThree = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[3]/td[2]"));
    assertEquals("2004", columnTwoRowOne);
    assertEquals("2003", columnTwoRowTwo);
    assertEquals("2005", columnTwoRowThree);
    //Column Price
    columnThreeRowOne = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr/td[3]"));
    columnThreeRowTwo = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[2]/td[3]"));
    columnThreeRowThree = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[3]/td[3]"));
    assertEquals("4750205.889999998", columnThreeRowOne);
    assertEquals("3573701.2500000023", columnThreeRowTwo);
    assertEquals("1513074.4600000002", columnThreeRowThree);
    //Column PriceInk
    columnFourRowOne = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr/td[4]"));
    columnFourRowTwo = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[2]/td[4]"));
    columnFourRowThree = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr[3]/td[4]"));
    assertEquals("4.750205889999998", columnFourRowOne);
    assertEquals("3.5737012500000023", columnFourRowTwo);
    assertEquals("1.5130744600000001", columnFourRowThree);

    /*
     * ## Step 5
     */
    ElementHelper.FindElement(driver, By.xpath("//div[@id='contents_filter']/input")).clear();
    ElementHelper.FindElement(driver, By.xpath("//div[@id='contents_filter']/input")).sendKeys("2004");
    //Check we have only one element
    textPaging = ElementHelper.WaitForElementPresentGetText(driver, By.id("contents_info"));
    assertEquals("View 1 to 1 of 1 elements (filter 3 elements)", textPaging);
    //Column Time
    columnOneRowOne = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr/td"));
    assertEquals("All Years", columnOneRowOne);
    //Column Year
    columnTwoRowOne = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr/td[2]"));
    assertEquals("2004", columnTwoRowOne);
    //Column Price
    columnThreeRowOne = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr/td[3]"));
    assertEquals("4750205.889999998", columnThreeRowOne);
    //Column PriceInk
    columnFourRowOne = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr/td[4]"));
    assertEquals("4.750205889999998", columnFourRowOne);

    /*
     * ## Step 6
     */
    ElementHelper.FindElement(driver, By.xpath("//div[@id='contents_filter']/input")).clear();
    ElementHelper.FindElement(driver, By.xpath("//div[@id='contents_filter']/input")).sendKeys("Merry");
    //Check we have only one element
    textPaging = ElementHelper.WaitForElementPresentGetText(driver, By.id("contents_info"));
    assertEquals("empty (filter 3 elements)", textPaging);
    //Check if table is empty
    String textNoResult = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr/td"));
    assertEquals("No results.", textNoResult);

    //Clean data
    ElementHelper.FindElement(driver, By.xpath("//div[@id='contents_filter']/input")).clear();
    ElementHelper.FindElement(driver, By.xpath("//div[@id='contents_filter']/input")).sendKeys(Keys.RETURN);
    //Order by Year
    ElementHelper.FindElement(driver, By.xpath("//table[@id='contents']/thead/tr/th[2]")).click();
    //Order by Time
    ElementHelper.FindElement(driver, By.xpath("//table[@id='contents']/thead/tr/th[1]")).click();
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Export XLS
   * Description:
   *    The test case pretends to validate the functionality to export to excel.
   * Steps:
   *    1. Press in Export.
   */
  @Test(timeout = 60000)
  public void tc3_ExportXls_FileDownload() {
    log.info("tc3_ExportXls_FileDownload");

    /*
     * ## Step 1
     */
    Select select = new Select(ElementHelper.FindElement(driver, By.id("dataAccessSelector")));
    select.selectByVisibleText("Mdx Query on SampleData - Jndi");
    //wait to render page
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
    //Check the presented contains
    WebElement elemStatus = ElementHelper.FindElement(driver, By.id("status"));
    assertEquals("Shipped", elemStatus.getAttribute("value"));
    //Check we have three elements and no more than that
    String textPaging = ElementHelper.WaitForElementPresentGetText(driver, By.id("contents_info"));
    assertEquals("View 1 to 3 of 3 elements", textPaging);

    //Click in export as xls
    WebElement buttonExport = ElementHelper.FindElement(driver, By.id("export"));
    try {
      //Delete the existence if exist
      new File(downloadDir + "\\cda-export.xls").delete();
      assertNotNull(buttonExport);

      //Click to export
      buttonExport.click();

      //Wait for file to be created in the destination dir
      DirectoryWatcher.WatchForCreate(downloadDir);

      //Check if file was download
      assertTrue(new File(downloadDir + "\\cda-export.xls").exists());

      new File(downloadDir + "\\cda-export.xls").delete();
    }
    catch(Exception e) {
      log.error(e.getMessage());
    }
  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name:
   *    Query URL
   * Description:
   *    The test case pretends to validate return data when call query url.
   * Steps:
   *    1. Check query url dialog
   *    2. Open a new browser with query url
   */
  @Test(timeout = 60000)
  public void tc4_QueryURL_ReturnValueIsTheSameDisplayedInPage() {
    log.info("tc4_QueryURL_ReturnValueIsTheSameDisplayedInPage");

    /*
     * ## Step 1
     */
    Select select = new Select(ElementHelper.FindElement(driver, By.id("dataAccessSelector")));
    select.selectByVisibleText("Mdx Query on SampleData - Jndi");
    //wait to render page
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
    //Check the presented contains
    WebElement elemStatus = ElementHelper.FindElement(driver, By.id("status"));
    assertEquals("Shipped", elemStatus.getAttribute("value"));
    //Check we have three elements and no more than that
    String textPaging = ElementHelper.WaitForElementPresentGetText(driver, By.id("contents_info"));
    assertEquals("View 1 to 3 of 3 elements", textPaging);

    // Check query url
    WebElement buttonQueryUrl = ElementHelper.FindElement(driver, By.id("queryUrl"));
    assertEquals("Query URL", buttonQueryUrl.getText());
    buttonQueryUrl.click();

    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("queryUrlDialog")));
    String dialogTitle = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='queryUrlDialog']/p"));
    assertEquals("Query Execution URL:", dialogTitle);

    WebElement inputQueryUrl = ElementHelper.FindElement(driver, By.xpath("//input[@id='doQueryUrl']"));
    String queryUrl = inputQueryUrl.getAttribute("value");
    log.debug("Query URL: " + queryUrl);

    ElementHelper.FindElement(driver, By.linkText("Close")).click();

    /*
     * ## Step 2
     */
    driver.get(queryUrl);
    String jsonQueryExpected = "{\"queryInfo\":{\"totalRows\":\"3\"},\"resultset\":[[\"All Years\",\"2003\",3573701.2500000023,3.5737012500000023],[\"All Years\",\"2004\",4750205.889999998,4.750205889999998],[\"All Years\",\"2005\",1513074.4600000002,1.5130744600000002]],\"metadata\":[{\"colIndex\":0,\"colType\":\"String\",\"colName\":\"[Time].[(All)]\"},{\"colIndex\":1,\"colType\":\"String\",\"colName\":\"Year\"},{\"colIndex\":2,\"colType\":\"Numeric\",\"colName\":\"price\"},{\"colIndex\":3,\"colType\":\"String\",\"colName\":\"PriceInK\"}]}";
    String jsonQueryActual = ElementHelper.WaitForElementPresentGetText(driver, By.cssSelector("body"));
    assertEquals(jsonQueryExpected, jsonQueryActual);
  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name:
   *    Query URL
   * Description:
   *    The test case pretends to validate return data when call query url.
   * Steps:
   *    1. Select the option 'Mdx...'
   *    2. Click in Cache this
   *    3. Set a period
   *    4. In the new window, check the schedule
   *    5. Remove the schedule
   */
  @Test(timeout = 240000)
  public void tc5_CacheThisSimple_ScheduleIsSetSuccessful() {
    log.info("tc5_CacheThisSimple_ScheduleIsSetSuccessful");
    String selectedHours = "21";

    /*
     * ## Step 1
     */
    Select select = new Select(ElementHelper.FindElement(driver, By.id("dataAccessSelector")));
    select.selectByVisibleText("Mdx Query on SampleData - Jndi");
    //wait to render page
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
    //Check the presented contains
    WebElement elemStatus = ElementHelper.FindElement(driver, By.id("status"));
    assertEquals("Shipped", elemStatus.getAttribute("value"));
    //Check we have three elements and no more than that
    String textPaging = ElementHelper.WaitForElementPresentGetText(driver, By.id("contents_info"));
    assertEquals("View 1 to 3 of 3 elements", textPaging);

    /*
     * ## Step 2
     */
    //Click in 'Cache this'
    ElementHelper.FindElement(driver, By.id("cachethis")).click();
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='dialog']")));
    String questionActual = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='dialog']/p"));
    String questionExpect = "What schedule should this query run on? (advanced)";
    assertEquals(questionExpect, questionActual);

    /*
     * ## Step 3
     */
    String parentWindowHandle = driver.getWindowHandle();
    Select selectPeriod = new Select(ElementHelper.FindElement(driver, By.id("periodType")));
    selectPeriod.selectByValue("1");//every day

    ElementHelper.FindElement(driver, By.xpath("//input[@id='startAt']")).clear();
    ElementHelper.FindElement(driver, By.xpath("//input[@id='startAt']")).sendKeys(selectedHours);
    ElementHelper.FindElement(driver, By.linkText("Ok")).click();

    Set<String> listWindows = driver.getWindowHandles();
    //wait for popup render
    ElementHelper.WaitForNewWindow(driver);
    listWindows = driver.getWindowHandles();
    //Get popup id
    WebDriver cdaCacheManager = null;
    Iterator<String> iterWindows = listWindows.iterator();
    while(iterWindows.hasNext()) {
      String windowHandle = iterWindows.next();
      cdaCacheManager = driver.switchTo().window(windowHandle);
      if(cdaCacheManager.getTitle().equals("CDA Cache Manager")) {
        break;
      }
    }
    //Validate page:
    //Title
    String titleCdaCacheManager = cdaCacheManager.getTitle();
    assertEquals("CDA Cache Manager", titleCdaCacheManager);
    //Scheduled queries
    String subTitleText = ElementHelper.WaitForElementPresentGetText(cdaCacheManager, By.xpath("//div[@id='scheduledQueries']/div"));
    assertEquals("Scheduled Queries", subTitleText);

    /*
     * ## Step 4
     */
    //Validate Query
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String strToday = sdf.format(new Date());
    String queryName = ElementHelper.WaitForElementPresentGetText(cdaCacheManager, By.xpath("//div[@id='lines']/div/div[1]"));
    String queryParam1 = ElementHelper.WaitForElementPresentGetText(cdaCacheManager, By.xpath("//div[@id='lines']/div/div[2]//dl//dt"));
    String queryParam2 = ElementHelper.WaitForElementPresentGetText(cdaCacheManager, By.xpath("//div[@id='lines']/div/div[2]//dl//dd"));
    String queryLExec = ElementHelper.WaitForElementPresentGetText(cdaCacheManager, By.xpath("//div[@id='lines']/div/div[3]"));
    String queryNExec = ElementHelper.WaitForElementPresentGetText(cdaCacheManager, By.xpath("//div[@id='lines']/div/div[4]"));
    String queryCron = ElementHelper.WaitForElementPresentGetText(cdaCacheManager, By.xpath("//div[@id='lines']/div/div[5]"));
    String queryTime = ElementHelper.WaitForElementPresentGetText(cdaCacheManager, By.xpath("//div[@id='lines']/div/div[6]"));
    String queryStatus = ElementHelper.WaitForElementPresentGetText(cdaCacheManager, By.xpath("//div[@id='lines']/div/div[7]"));

    try {
      assertEquals("/public/plugin-samples/cda/cdafiles/mondrian-jndi.cda (1)", queryName);
      assertEquals("status", queryParam1);
      assertEquals("Shipped", queryParam2);
      assertThat("Last Execution: " + queryLExec, queryLExec, CoreMatchers.containsString("1970-01-01"));
      assertThat("Last Execution: " + queryLExec, queryLExec, CoreMatchers.containsString("00:00:00"));
      assertThat("Next Execution: " + queryNExec, queryNExec, CoreMatchers.containsString(strToday));
      assertThat("Next Execution: " + queryNExec, queryNExec, CoreMatchers.containsString(selectedHours + ":00:00"));
      assertEquals("0 0 21 * * ? *", queryCron);
      assertEquals("-1", queryTime);
      assertEquals("Success", queryStatus);
    }
    catch(AssertionError ae) {
      log.error(ae.getMessage());

      //Remove schedule
      this.removeFirstSchedule();

      //Need guarantee we close everything
      cdaCacheManager.close();
      driver.switchTo().window(parentWindowHandle);

      //raise the exception
      fail(ae.getMessage());
    }

    /*
     * ## Step 5
     */
    this.removeFirstSchedule();

    cdaCacheManager.close();
    driver.switchTo().window(parentWindowHandle);
    assertTrue(driver.getWindowHandles().size() == 1);
  }

  /**
   * This method is usage to remove any schedule entry.
   */
  public void removeFirstSchedule() {
    boolean elementPresent = false;

    elementPresent = ElementHelper.IsElementNotPresent(driver, By.cssSelector("img.deleteIcon.button"), 2);

    while(elementPresent) {
      //Press to delete schedule
      ElementHelper.FindElement(driver, By.cssSelector("img.deleteIcon.button")).click();

      //Wait for pop-up
      wait.until(ExpectedConditions.alertIsPresent());
      Alert alert = driver.switchTo().alert();
      String confirmationMsg = alert.getText();
      String expectedCnfText = "Want to delete this scheduler entry?";
      alert.accept();

      assertEquals(confirmationMsg, expectedCnfText);
      ElementHelper.WaitForAlertNotPresent(driver);
      driver.switchTo().defaultContent();

      elementPresent = ElementHelper.IsElementNotPresent(driver, By.cssSelector("img.deleteIcon.button"), 2);
    }
  }

  @After
  public void tearDownTestCase() {}

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown");
  }
}
