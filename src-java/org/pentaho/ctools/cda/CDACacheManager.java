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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.CoreMatchers;
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
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * Testing the functionalities related with CDA Cache Manager.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDACacheManager {
  // Instance of the driver (browser emulator)
  private static WebDriver       driver;
  // Instance to be used on wait commands
  private static Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private static String          baseUrl;
  //Log instance
  private static Logger          log                = LogManager.getLogger(CDACacheManager.class);

  @Rule
  public ScreenshotTestRule      screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDACacheManager.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    wait = CToolsTestSuite.getWait();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  @Before
  public void setUpTestCase() {
    //Go to the CDA Cache Manager web page.
    driver.get(baseUrl + "plugin/cda/api/manageCache");
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Page Content
   * Description:
   *    The test case pretends to validate the page contents in 'CachedQueries.
   * Steps:
   *    1. Check for text 'Queries in cache'
   *    2. Check for button 'Clear Cache'
   */
  @Test
  public void tc1_PageContent_CachedQueries() {
    log.info("tc1_PageContent_CachedQueries");

    wait.until(ExpectedConditions.titleContains("CDA Cache Manager"));
    assertEquals("CDA Cache Manager", driver.getTitle());
    //Go to Cached Queries
    WebElement buttonCachedQueries = ElementHelper.FindElement(driver, By.id("cacheButton"));
    assertNotNull(buttonCachedQueries);
    buttonCachedQueries.click();

    /*
     * ## Step 1
     */
    String subTitle = ElementHelper.GetText(driver, By.xpath("//div[@id='cachedQueries']/div[3]"));
    assertEquals("Queries in cache", subTitle);

    /*
     * ## Step 2
     */
    String buttonTextClearCache = ElementHelper.GetText(driver, By.id("clearCacheButton"));
    assertEquals("Clear Cache", buttonTextClearCache);
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Clean Cache
   * Description:
   *    The test case pretends to validate the clean cache functionality.
   * Steps:
   *    1. Press to clear cache
   *    2. Check no query is listed
   */
  @Test
  public void tc2_ClearCache_AllQueriesWhereRemove() {
    log.info("tc2_ClearCache_AllQueriesWhereRemove");

    //Go to Cached Queries
    WebElement buttonCachedQueries = ElementHelper.FindElement(driver, By.id("cacheButton"));
    assertNotNull(buttonCachedQueries);
    buttonCachedQueries.click();

    //Click in clear cache
    ElementHelper.FindElement(driver, By.id("clearCacheButton")).click();

    /*
     * ## Step 1
     */
    //Wait for pop-up 1
    wait.until(ExpectedConditions.alertIsPresent());
    Alert alert = driver.switchTo().alert();
    String confirmationMsg = alert.getText();
    String expectedCnfText = "This will remove ALL items from cache. Are you sure?";
    alert.accept();
    assertEquals(confirmationMsg, expectedCnfText);

    //Wait for pop-up 2
    wait.until(ExpectedConditions.alertIsPresent());
    alert = driver.switchTo().alert();
    confirmationMsg = alert.getText();
    expectedCnfText = "items have been removed from cache";
    alert.accept();
    assertThat("The displayed popup: " + confirmationMsg, confirmationMsg, CoreMatchers.containsString(expectedCnfText));

    /*
     * ## Step 2
     */
    String textEmptyCache = ElementHelper.GetText(driver, By.xpath("//div[@id='cachedQueriesOverviewLines']/div"));
    assertEquals("Cache is empty.", textEmptyCache);
  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDACacheManager.class.getSimpleName());
  }
}
