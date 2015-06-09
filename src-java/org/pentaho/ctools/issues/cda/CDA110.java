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
import org.junit.AfterClass;
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
  private static WebDriver  DRIVER;
  // The base url to be append the relative url in test
  private static String     BASE_URL;
  // Log instance
  private static Logger     LOG                = LogManager.getLogger( CDA110.class );
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( DRIVER );

  @BeforeClass
  public static void setUpClass() {
    LOG.info( "setUp##" + CDA110.class.getSimpleName() );
    DRIVER = CToolsTestSuite.getDriver();
    BASE_URL = CToolsTestSuite.getBaseUrl();
  }

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
  @Test( timeout = 120000 )
  public void tc01_CdaFileViewer_FullOuterJoin() {
    LOG.info( "tc01_CdaFileViewer_FullOuterJoin" );

    /*
     * ## Step 1
     */
    //Open sample CDA file
    DRIVER.get( BASE_URL + "plugin/cda/api/previewQuery?path=/public/Issues/CDA/CDA-110/cda110.cda" );

    //wait for invisibility of waiting pop-up
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

    //Wait for buttons: button, Cache This AND Query URL
    WebElement selector = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.id( "dataAccessSelector" ) );
    assertNotNull( selector );
    Select select = new Select( ElementHelper.FindElement( DRIVER, By.id( "dataAccessSelector" ) ) );
    select.selectByValue( "fullOuter" );
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    WebElement refreshButton = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//button[@id='button']" ) );
    assertNotNull( refreshButton );
    WebElement cacheButton = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//button[@id='cachethis']" ) );
    assertNotNull( cacheButton );
    WebElement urlButton = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//button[@id='queryUrl']" ) );
    assertNotNull( urlButton );

    /*
     * ## Step 2
     */
    //wait to render page
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Check text on table
    String firstColumn = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='contents']/thead/tr/th" ) );
    String secondColumn = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='contents']/thead/tr/th[2]" ) );
    String thirdColumn = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='contents']/thead/tr/th[3]" ) );
    String fourthColumn = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='contents']/thead/tr/th[4]" ) );
    String fifthColumn = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='contents']/thead/tr/th[5]" ) );
    String sixthColumn = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='contents']/thead/tr/th[6]" ) );
    assertEquals( "YEAR_ID", firstColumn );
    assertEquals( "STATUS", secondColumn );
    assertEquals( "TOTALPRICE", thirdColumn );
    assertEquals( "YEAR_ID_1", fourthColumn );
    assertEquals( "STATUS_1", fifthColumn );
    assertEquals( "TRIPLEPRICE", sixthColumn );
  }

  @AfterClass
  public static void tearDownClass() {
    LOG.info( "tearDown##" + CDA110.class.getSimpleName() );

  }
}
