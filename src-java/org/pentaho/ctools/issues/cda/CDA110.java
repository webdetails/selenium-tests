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
package org.pentaho.ctools.issues.cda;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDA-110
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1003
 *
 * NOTE
 * To test this script it is required to have CDA plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDA110 {

  // Instance of the driver (browser emulator)
  private WebDriver driver = CToolsTestSuite.getDriver();
  // The base url to be append the relative url in test
  private String baseUrl = CToolsTestSuite.getBaseUrl();
  //Access to wrapper for webdriver
  private ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private static Logger LOG = LogManager.getLogger( CDA110.class );
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( this.driver );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Asserting that CDA with full outer join work
   * Description:
   *    The test pretends validate the CDA-110 issue, asserting that a CDA with full outer join work as expected.
   *
   * Steps:
   *    1. Open sample and select "full outer" on the datasources
   *    2. Wait for and assert elements and text on page
   *    
   */
  public void tc1_CdaFileViewer_FullOuterJoin() {
    LOG.info( "tc1_CdaFileViewer_FullOuterJoin" );

    /*
     * ## Step 1
     */
    //Open sample CDA file
    this.driver.get( this.baseUrl + "plugin/cda/api/previewQuery?path=/public/Issues/CDA/CDA-110/cda110.cda" );

    //wait for invisibility of waiting pop-up
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

    //Wait for buttons: button, Cache This AND Query URL
    WebElement selector = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "dataAccessSelector" ) );
    assertNotNull( selector );
    Select select = new Select( this.elemHelper.FindElement( this.driver, By.id( "dataAccessSelector" ) ) );
    select.selectByValue( "fullOuter" );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    WebElement refreshButton = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//button[@id='button']" ) );
    assertNotNull( refreshButton );
    WebElement cacheButton = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//button[@id='cachethis']" ) );
    assertNotNull( cacheButton );
    WebElement urlButton = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//button[@id='queryUrl']" ) );
    assertNotNull( urlButton );

    /*
     * ## Step 2
     */
    //wait to render page
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Check text on table
    String firstColumn = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='contents']/thead/tr/th" ) );
    String secondColumn = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='contents']/thead/tr/th[2]" ) );
    String thirdColumn = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='contents']/thead/tr/th[3]" ) );
    String fourthColumn = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='contents']/thead/tr/th[4]" ) );
    String fifthColumn = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='contents']/thead/tr/th[5]" ) );
    String sixthColumn = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='contents']/thead/tr/th[6]" ) );
    assertEquals( "YEAR_ID", firstColumn );
    assertEquals( "STATUS", secondColumn );
    assertEquals( "TOTALPRICE", thirdColumn );
    assertEquals( "YEAR_ID_1", fourthColumn );
    assertEquals( "STATUS_1", fifthColumn );
    assertEquals( "TRIPLEPRICE", sixthColumn );
  }
}
