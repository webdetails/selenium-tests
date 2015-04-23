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
package org.pentaho.ctools.issues.cde;

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
 * - http://jira.pentaho.com/browse/CDE-446
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1022
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDE446 {
  // Instance of the driver (browser emulator)
  private static WebDriver DRIVER;
  // The base url to be append the relative url in test
  private static String BASE_URL;
  // Log instance
  private static Logger LOG = LogManager.getLogger( CDE446.class );
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( DRIVER );

  @BeforeClass
  public static void setUpClass() {
    LOG.info( "setUp##" + CDE446.class.getSimpleName() );
    DRIVER = CToolsTestSuite.getDriver();
    BASE_URL = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Trying to access Kettle file doesn't add anything to saved path
   *
   * Description:
   *    The test pretends validate the CDE-446 issue, so when user accesses a kettle file for a datasource the path
   *    selected isn't altered.
   *
   * Steps:
   *    1. Open created dashboard's cda file
   *    2. Select "DataAccess ID: kettleTransform" on the data selector
   *    3. Assert data shown is expected
   *
   */
  @Test( timeout = 120000 )
  public void tc01_CdeDashboard_KettleDatasourceWorks() {
    LOG.info( "tc01_CdeDashboard_KettleDatasourceWorks" );

    /*
     * ## Step 1
     */
    //Open created dashboard's cda file
    DRIVER.get( BASE_URL + "plugin/cda/api/previewQuery?path=/public/Issues/CDE/CDE-446/CDE-446.cda" );
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    WebElement element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.id( "dataAccessSelector" ) );
    assertNotNull( element );
    Select select = new Select( ElementHelper.FindElement( DRIVER, By.id( "dataAccessSelector" ) ) );
    select.selectByVisibleText( "DataAccess ID: kettleTransform" );

    //Wait for buttons: button, Cache This AND Query URL
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//button[@id='button']" ) );
    assertNotNull( element );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//button[@id='cachethis']" ) );
    assertNotNull( element );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//button[@id='queryUrl']" ) );
    assertNotNull( element );

    /*
     * ## Step 2
     */
    //Check text on table
    String text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='contents']/tbody/tr/td[3]" ) );
    assertEquals( "District Manager", text );
    text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='contents']/tbody/tr[2]/td[3]" ) );
    assertEquals( "Senior Sales Rep", text );
    text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='contents']/tbody/tr[3]/td[3]" ) );
    assertEquals( "Sales Rep", text );
    text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='contents']/tbody/tr[4]/td[3]" ) );
    assertEquals( "Account Executive", text );

  }

  @AfterClass
  public static void tearDownClass() {
    LOG.info( "tearDown##" + CDE446.class.getSimpleName() );
  }
}
