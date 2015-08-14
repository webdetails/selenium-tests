/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2015 by Pentaho : http://www.pentaho.com
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
package com.pentaho.ctools.issues.cda;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.CoreMatchers;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.BaseTest;
import com.pentaho.ctools.utils.ElementHelper;

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
public class CDA106 extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDA106.class );

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
  @Test
  public void tc01_CdaCacheManager_ButtonsHaveTooltips() {
    this.log.info( "tc01_CdaCacheManager_ButtonsHaveTooltips" );

    /*
     * ## Step 1
     */
    //Open MondrianJndi Sample
    driver.get( baseUrl + "plugin/cda/api/previewQuery?path=/public/plugin-samples/cda/cdafiles/mondrian-jndi.cda" );

    //Wait for buttons: button, Cache This AND Query URL
    WebElement element = this.elemHelper.WaitForElementPresence( driver, By.id( "dataAccessSelector" ) );
    assertNotNull( element );
    Select select = new Select( this.elemHelper.FindElement( driver, By.id( "dataAccessSelector" ) ) );
    select.selectByVisibleText( "Mdx Query on SampleData - Jndi" );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='button']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='cachethis']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='queryUrl']" ) );
    assertNotNull( element );

    /*
     * ## Step 2
     */
    //wait to render page
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Check the presented contains
    WebElement elemStatus = this.elemHelper.FindElement( driver, By.id( "status" ) );
    assertNotNull( elemStatus );
    assertEquals( "Shipped", elemStatus.getAttribute( "value" ) );

    //Check we have three elements and no more than that
    String textPaging = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "contents_info" ) );
    assertEquals( "View 1 to 3 of 3 elements", textPaging );

    //Check text on table
    String columnOneRowOne = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='contents']/tbody/tr/td" ) );
    String columnTwoRowOne = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='contents']/tbody/tr/td[2]" ) );
    assertEquals( "All Years", columnOneRowOne );
    assertEquals( "2003", columnTwoRowOne );
    this.elemHelper.Click( driver, By.xpath( "//button[@id='cachethis']" ) );

    /*
     * ## Step 3
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "dialog" ) );
    assertNotNull( element );
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//div[@id='dialog']/p" ), "What schedule should this query run on?" );
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//div[@id='dialog']/div/span/span" ), "on the" );
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//div[@id='dialog']/div/span/span[2]" ), "th day of the week (1-7)" );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='dialog']/div[2]/a" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='dialog']/div[2]/a[2]" ) );
    assertNotNull( element );
    this.elemHelper.FindElement( driver, By.id( "startAt" ) ).sendKeys( "1" );
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//div[@id='dialog']/div[2]/a" ), "Ok" );
    this.elemHelper.Click( driver, By.xpath( "//div[@id='dialog']/div[2]/a" ) );

    String parentWindowHandle = driver.getWindowHandle();
    Set<String> listWindows = driver.getWindowHandles();

    //wait for popup render
    this.elemHelper.WaitForNewWindow( driver );
    listWindows = driver.getWindowHandles();

    //Get popup id
    WebDriver cdaCacheManager = null;
    Iterator<String> iterWindows = listWindows.iterator();
    while ( iterWindows.hasNext() ) {
      String windowHandle = iterWindows.next();
      cdaCacheManager = driver.switchTo().window( windowHandle );
      if ( cdaCacheManager.getTitle().equals( "CDA Cache Manager" ) ) {
        break;
      }
    }
    //Validate page:
    //Title
    String titleCdaCacheManager = cdaCacheManager.getTitle();
    assertEquals( "CDA Cache Manager", titleCdaCacheManager );

    //Scheduled queries
    String subTitleText = this.elemHelper.WaitForElementPresentGetText( cdaCacheManager, By.xpath( "//div[@id='scheduledQueries']/div" ) );
    assertEquals( "Scheduled Queries", subTitleText );

    //Validate Query
    String queryName = this.elemHelper.WaitForElementPresentGetText( cdaCacheManager, By.xpath( "//div[@id='lines']/div/div[1]" ) );
    String queryParam1 = this.elemHelper.WaitForElementPresentGetText( cdaCacheManager, By.xpath( "//div[@id='lines']/div/div[2]//dl//dt" ) );
    String queryParam2 = this.elemHelper.WaitForElementPresentGetText( cdaCacheManager, By.xpath( "//div[@id='lines']/div/div[2]//dl//dd" ) );
    String queryLExec = this.elemHelper.WaitForElementPresentGetText( cdaCacheManager, By.xpath( "//div[@id='lines']/div/div[3]" ) );
    String queryCron = this.elemHelper.WaitForElementPresentGetText( cdaCacheManager, By.xpath( "//div[@id='lines']/div/div[5]" ) );
    String queryTime = this.elemHelper.WaitForElementPresentGetText( cdaCacheManager, By.xpath( "//div[@id='lines']/div/div[6]" ) );
    String queryStatus = this.elemHelper.WaitForElementPresentGetText( cdaCacheManager, By.xpath( "//div[@id='lines']/div/div[7]" ) );
    assertEquals( "/public/plugin-samples/cda/cdafiles/mondrian-jndi.cda (1)", queryName );
    assertEquals( "status", queryParam1 );
    assertEquals( "Shipped", queryParam2 );
    assertThat( "Last Execution: " + queryLExec, queryLExec, CoreMatchers.containsString( "1970-01-01" ) );
    assertThat( "Last Execution: " + queryLExec, queryLExec, CoreMatchers.containsString( "00:00:00" ) );
    assertEquals( "0 0 0 ? * 1 *", queryCron );
    assertEquals( "-1", queryTime );
    assertEquals( "Success", queryStatus );

    /*
     * ## Step 5
     */

    WebElement delete = this.elemHelper.FindElement( cdaCacheManager, By.xpath( "//a/img[@title='Remove Query']" ) );
    WebElement execute = this.elemHelper.FindElement( cdaCacheManager, By.xpath( "//a/img[@title='Execute Query']" ) );
    assertNotNull( execute );
    assertNotNull( delete );
    execute.click();

    /*
     * ## Step 6
     */
    String queryLExecNew = this.elemHelper.WaitForElementPresentGetText( cdaCacheManager, By.xpath( "//div[@id='lines']/div[1]/div[3]" ) );
    assertTrue( queryLExec != queryLExecNew );
    this.elemHelper.Click( cdaCacheManager, By.xpath( "//a/img[@title='Remove Query']" ) );

    //Wait for pop-up
    FluentWait<WebDriver> wait = new FluentWait<WebDriver>( cdaCacheManager ).withTimeout( 30, TimeUnit.SECONDS ).pollingEvery( 1, TimeUnit.SECONDS );
    wait.until( ExpectedConditions.alertIsPresent() );
    Alert alert = cdaCacheManager.switchTo().alert();
    String confirmationMsg = alert.getText();
    String expectedCnfText = "Want to delete this scheduler entry?";
    alert.accept();
    assertEquals( confirmationMsg, expectedCnfText );
    this.elemHelper.WaitForAlertNotPresent( cdaCacheManager );
    this.elemHelper.WaitForElementInvisibility( cdaCacheManager, By.xpath( "//div[@id='lines']/div[1]/div[1]" ) );

    //Need guarantee we close everything
    cdaCacheManager.close();
    driver.switchTo().window( parentWindowHandle );
    assertTrue( driver.getWindowHandles().size() == 1 );

    //Wait for buttons: button, Cache This AND Query URL
    element = this.elemHelper.WaitForElementPresence( driver, By.id( "dataAccessSelector" ) );
    assertNotNull( element );
    select = new Select( this.elemHelper.FindElement( driver, By.id( "dataAccessSelector" ) ) );
    select.selectByVisibleText( "Mdx Query on SampleData - Jndi" );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='button']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='cachethis']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='queryUrl']" ) );
    assertNotNull( element );

  }

}
