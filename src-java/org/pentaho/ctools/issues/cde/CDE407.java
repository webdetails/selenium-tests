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
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-407
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-928
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDE407 {
  // Instance of the driver (browser emulator)
  private static WebDriver DRIVER;
  // The base url to be append the relative url in test
  private static String BASE_URL;
  //Access to wrapper for webdriver
  private ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private static Logger LOG = LogManager.getLogger( CDE407.class );
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( DRIVER );

  @BeforeClass
  public static void setUpClass() {
    LOG.info( "setUp##" + CDE407.class.getSimpleName() );
    DRIVER = CToolsTestSuite.getDriver();
    BASE_URL = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Check ability to duplicate spacers on layout
   *
   * Description:
   *    The test pretends validate the CDE-407 issue, so when user creates a spacer
   *    and then duplicates it both spacers are shown immediately.
   *
   * Steps:
   *    1. Wait for new Dashboard to be created, assert elements on page and click "Add Row"
   *    2. Wait for "Add Space" to be visible and then click "Add Space"
   *    3. Wait for Spacer to appear on layout confirm it's highlighted and then click "Duplicate Layout Element" twice
   *    4. Wait for Spacers to appear on layout and then confirm there are three of them
   */
  @Test( timeout = 120000 )
  public void tc01_NewCdeDashboard_DuplicateSpacers() {
    LOG.info( "tc01_NewCdeDashboard_DuplicateSpacers" );

    /*
     * ## Step 1
     */
    //Go to New CDE Dashboard
    DRIVER.get( BASE_URL + "api/repos/wcdf/new" );
    this.elemHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    //assert buttons
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//a[@title='Save as Template']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//a[@title='Apply Template']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//a[@title='Add Resource']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//a[@title='Add Bootstrap Panel']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//a[@title='Add FreeForm']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//a[@title='Add Row']" ) );
    assertNotNull( element );
    this.elemHelper.Click( DRIVER, By.xpath( "//a[@title='Add Row']" ) );

    /*
     * ## Step 2
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//a[@title='Add Space']" ) );
    assertNotNull( element );
    this.elemHelper.Click( DRIVER, By.xpath( "//a[@title='Add Space']" ) );

    /*
     * ## Step 3
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//a[@title='Duplicate Layout Element']" ) );
    assertNotNull( element );
    this.elemHelper.Click( DRIVER, By.xpath( "//a[@title='Duplicate Layout Element']" ) );
    this.elemHelper.Click( DRIVER, By.xpath( "//a[@title='Duplicate Layout Element']" ) );

    /*
     * ## Step 4
     */
    String tr1tdText = this.elemHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ) );
    String tr2tdText = this.elemHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[2]/td" ) );
    String tr3tdText = this.elemHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[3]/td" ) );
    String tr4tdText = this.elemHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[4]/td" ) );
    assertEquals( "Row", tr1tdText );
    assertEquals( "Space", tr2tdText );
    assertEquals( "Space", tr3tdText );
    assertEquals( "Space", tr4tdText );

  }

  @AfterClass
  public static void tearDownClass() {
    LOG.info( "tearDown##" + CDE407.class.getSimpleName() );
  }
}
