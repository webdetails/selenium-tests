/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2002 - 2026 by Pentaho Canada Inc. : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2030-06-15
 ******************************************************************************/

package com.pentaho.ctools.cda.samples;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with CDA Cache Manager.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDACacheManager extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDACacheManager.class );

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
    this.log.info( "tc1_PageContent_CachedQueries" );

    // Go to the CDA Cache Manager web page.
    this.elemHelper.Get( BaseTest.driver, PageUrl.CDA_CACHE_MANAGER );

    final String expectedTitle = "CDA Cache Manager";
    final String actualTitle = this.elemHelper.WaitForTitle( BaseTest.driver, expectedTitle );
    Assert.assertEquals( actualTitle, expectedTitle );

    // Go to Cached Queries
    final WebElement buttonCachedQueries = this.elemHelper.FindElement( BaseTest.driver, By.id( "cacheButton" ) );
    Assert.assertNotNull( buttonCachedQueries );
    buttonCachedQueries.click();

    /*
     * ## Step 1
     */
    final String subTitle = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='cachedQueries']/div[3]" ) );
    Assert.assertEquals( "Queries in cache", subTitle );

    /*
     * ## Step 2
     */
    final String buttonTextClearCache = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.id( "clearCacheButton" ) );
    Assert.assertEquals( "Clear Cache", buttonTextClearCache );
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
    this.log.info( "tc2_ClearCache_AllQueriesWhereRemove" );

    // Go to Cached Queries
    WebElement buttonCachedQueries = this.elemHelper.FindElement( BaseTest.driver, By.id( "cacheButton" ) );
    Assert.assertNotNull( buttonCachedQueries );
    this.elemHelper.ClickJS( BaseTest.driver, By.id( "cacheButton" ) );

    final JavascriptExecutor jsDriver = (JavascriptExecutor) BaseTest.driver;
    jsDriver.executeScript( "window.confirm = function(message) {document.lastConfirmMessage = message; return true; }" );
    jsDriver.executeScript( "window.alert = function(message) {document.lastAlertMessage = message; return true; }" );

    //Click in clear cache
    this.elemHelper.ClickJS( BaseTest.driver, By.id( "clearCacheButton" ) );

    final String message = (String) jsDriver.executeScript( "return document.lastConfirmMessage" );
    final String message2 = (String) jsDriver.executeScript( "return document.lastAlertMessage" );

    /*
     * ## Step 1
     */
    String expectedCnfText = "This will remove ALL items from cache. Are you sure?";
    Assert.assertEquals( expectedCnfText, message );

    //Wait for pop-up 2
    expectedCnfText = "items have been removed from cache";
    MatcherAssert.assertThat( "The displayed popup: " + message2, message2, CoreMatchers.containsString( expectedCnfText ) );

    /*
     * ## Step 2
     */
    // Go to Cached Queries
    buttonCachedQueries = this.elemHelper.FindElement( BaseTest.driver, By.id( "cacheButton" ) );
    Assert.assertNotNull( buttonCachedQueries );
    this.elemHelper.ClickJS( BaseTest.driver, By.id( "cacheButton" ) );

    final String textEmptyCache = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='cachedQueriesOverviewLines']/div" ) );
    Assert.assertEquals( "Cache is empty.", textEmptyCache );
  }
}
