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
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.CoreMatchers;
import org.junit.AfterClass;
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
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDA-106
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1008
 *
 * NOTE
 * To test this script it is required to have CDA plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDA106 {

  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDA106.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDA106.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Asserting button has tooltip
   *
   * Description:
   *    The test pretends validate the CDA-106 issue, button on CDA Cache Manager has a tooltip explaining it's function.
   *
   * Steps:
   *    1. Select "Mdx Query on SampleData - Jndi" on "dataAccessSelector"
   *    2. Wait for and assert elements and text on page. Click "Cache This"
   *    3. Assert popup and click "Ok"
   *    4. Assert Cache Manager was opened and elements on page
   *    5. Assert tooltip when over Execute Query and click Execute Query
   *    6. Assert Last Execution has changed and remove schedule
   *
   */
  @Test(timeout = 120000)
  public void tc01_CdaCacheManager_ButtonsHaveTooltips() throws InterruptedException {
    log.info("tc01_CdaCacheManager_ButtonsHaveTooltips");

    /*
     * ## Step 1
     */
    //Go to User Console
    driver.get(baseUrl + "plugin/cda/api/previewQuery?path=/public/plugin-samples/cda/cdafiles/mondrian-jndi.cda");

    //Wait for buttons: button, Cache This AND Query URL
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//button[@id='button']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//button[@id='cachethis']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//button[@id='queryUrl']"));

    ElementHelper.WaitForElementPresence(driver, By.id("dataAccessSelector"));
    Select select = new Select(ElementHelper.FindElement(driver, By.id("dataAccessSelector")));
    select.selectByVisibleText("Mdx Query on SampleData - Jndi");

    /*
     * ## Step 2
     */
    //wait to render page
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
    //Check the presented contains
    WebElement elemStatus = ElementHelper.FindElement(driver, By.id("status"));
    assertEquals("Shipped", elemStatus.getAttribute("value"));
    //Check we have three elements and no more than that
    String textPaging = ElementHelper.WaitForElementPresentGetText(driver, By.id("contents_info"));
    assertEquals("View 1 to 3 of 3 elements", textPaging);
    //Check text on table
    String columnOneRowOne = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr/td"));
    String columnTwoRowOne = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//table[@id='contents']/tbody/tr/td[2]"));
    assertEquals("All Years", columnOneRowOne);
    assertEquals("2003", columnTwoRowOne);
    ElementHelper.Click(driver, By.xpath("//button[@id='cachethis']"));

    /*
     * ## Step 3
     */
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("dialog"));
    ElementHelper.WaitForTextPresence(driver, By.xpath("//div[@id='dialog']/p"), "What schedule should this query run on?");
    ElementHelper.WaitForTextPresence(driver, By.xpath("//div[@id='dialog']/div/span/span"), "on the");
    ElementHelper.WaitForTextPresence(driver, By.xpath("//div[@id='dialog']/div/span/span[2]"), "th day of the week (1-7)");
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='dialog']/div[2]/a"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='dialog']/div[2]/a[2]"));
    ElementHelper.FindElement(driver, By.id("startAt")).sendKeys("1");
    ElementHelper.WaitForTextPresence(driver, By.xpath("//div[@id='dialog']/div[2]/a"), "Ok");
    ElementHelper.Click(driver, By.xpath("//div[@id='dialog']/div[2]/a"));

    String parentWindowHandle = driver.getWindowHandle();
    Set<String> listWindows = driver.getWindowHandles();
    //wait for popup render
    ElementHelper.WaitForNewWindow(driver);
    listWindows = driver.getWindowHandles();
    //Get popup id
    WebDriver cdaCacheManager = null;
    Iterator<String> iterWindows = listWindows.iterator();
    while (iterWindows.hasNext()) {
      String windowHandle = iterWindows.next();
      cdaCacheManager = driver.switchTo().window(windowHandle);
      if (cdaCacheManager.getTitle().equals("CDA Cache Manager")) {
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
    //Validate Query
    String queryName = ElementHelper.WaitForElementPresentGetText(cdaCacheManager, By.xpath("//div[@id='lines']/div/div[1]"));
    String queryParam1 = ElementHelper.WaitForElementPresentGetText(cdaCacheManager, By.xpath("//div[@id='lines']/div/div[2]//dl//dt"));
    String queryParam2 = ElementHelper.WaitForElementPresentGetText(cdaCacheManager, By.xpath("//div[@id='lines']/div/div[2]//dl//dd"));
    String queryLExec = ElementHelper.WaitForElementPresentGetText(cdaCacheManager, By.xpath("//div[@id='lines']/div/div[3]"));
    String queryCron = ElementHelper.WaitForElementPresentGetText(cdaCacheManager, By.xpath("//div[@id='lines']/div/div[5]"));
    String queryTime = ElementHelper.WaitForElementPresentGetText(cdaCacheManager, By.xpath("//div[@id='lines']/div/div[6]"));
    String queryStatus = ElementHelper.WaitForElementPresentGetText(cdaCacheManager, By.xpath("//div[@id='lines']/div/div[7]"));
    assertEquals("/public/plugin-samples/cda/cdafiles/mondrian-jndi.cda (1)", queryName);
    assertEquals("status", queryParam1);
    assertEquals("Shipped", queryParam2);
    assertThat("Last Execution: " + queryLExec, queryLExec, CoreMatchers.containsString("1970-01-01"));
    assertThat("Last Execution: " + queryLExec, queryLExec, CoreMatchers.containsString("00:00:00"));
    assertEquals("0 0 0 ? * 1 *", queryCron);
    assertEquals("-1", queryTime);
    assertEquals("Success", queryStatus);

    /*
     * ## Step 5
     */

    WebElement delete = ElementHelper.FindElement(cdaCacheManager, By.xpath("//a/img[@title='Remove Query']"));
    WebElement execute = ElementHelper.FindElement(cdaCacheManager, By.xpath("//a/img[@title='Execute Query']"));
    assertNotNull(execute);
    assertNotNull(delete);
    execute.click();

    /*
     * ## Step 6
     */
    String queryLExecNew = ElementHelper.WaitForElementPresentGetText(cdaCacheManager, By.xpath("//div[@id='lines']/div[1]/div[3]"));
    assertTrue(queryLExec != queryLExecNew);
    ElementHelper.Click(cdaCacheManager, By.xpath("//a/img[@title='Remove Query']"));

    //Wait for pop-up
    FluentWait<WebDriver> wait = new FluentWait<WebDriver>(cdaCacheManager).withTimeout(30, TimeUnit.SECONDS).pollingEvery(1, TimeUnit.SECONDS);
    wait.until(ExpectedConditions.alertIsPresent());
    Alert alert = cdaCacheManager.switchTo().alert();
    String confirmationMsg = alert.getText();
    String expectedCnfText = "Want to delete this scheduler entry?";
    alert.accept();
    assertEquals(confirmationMsg, expectedCnfText);
    ElementHelper.WaitForAlertNotPresent(cdaCacheManager);
    ElementHelper.WaitForElementInvisibility(cdaCacheManager, By.xpath("//div[@id='lines']/div[1]/div[1]"));

    //Need guarantee we close everything
    cdaCacheManager.close();
    driver.switchTo().window(parentWindowHandle);
    assertTrue(driver.getWindowHandles().size() == 1);
    //Wait for buttons: button, Cache This AND Query URL
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//button[@id='button']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//button[@id='cachethis']"));
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//button[@id='queryUrl']"));

  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDA106.class.getSimpleName());
  }
}
