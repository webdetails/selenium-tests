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
 * - http://jira.pentaho.com/browse/CDA-108
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-970
 *
 * NOTE
 * To test this script it is required to have CDA plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDA108 {
  // Instance of the driver (browser emulator)
  private static WebDriver DRIVER;
  // The base url to be append the relative url in test
  private static String BASE_URL;
  // Log instance
  private static Logger LOG = LogManager.getLogger( CDA108.class );
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( DRIVER );

  @BeforeClass
  public static void setUpClass() {
    LOG.info( "setUp##" + CDA108.class.getSimpleName() );
    DRIVER = CToolsTestSuite.getDriver();
    BASE_URL = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Asserting query is working as expected
   * Description:
   *    The test pretends validate the CDA-108 issue, that xpath CDA sample is working as expected.
   *
   * Steps:
   *    1. Select "Sample query on SteelWheelsSales" on "dataAccessSelector"
   *    2. Wait for and assert elements and text on page
   *
   */
  @Test( timeout = 120000 )
  public void tc01_CdaFileViewer_XpathSampleWorking() {
    LOG.info( "tc01_CdaFileViewer_XpathSampleWorking" );

    /*
     * ## Step 1
     */
    //Open Xpath Sample
    DRIVER.get( BASE_URL + "plugin/cda/api/previewQuery?path=/public/plugin-samples/cda/cdafiles/xpath.cda" );

    WebElement element = ElementHelper.WaitForElementPresence( DRIVER, By.id( "dataAccessSelector" ) );
    assertNotNull( element );
    Select select = new Select( ElementHelper.FindElement( DRIVER, By.id( "dataAccessSelector" ) ) );
    select.selectByVisibleText( "Sample query on SteelWheelsSales" );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//button[@id='button']" ) );
    assertNotNull( element );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//button[@id='cachethis']" ) );
    assertNotNull( element );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//button[@id='queryUrl']" ) );
    assertNotNull( element );

    /*
     * ## Step 2
     */
    //wait to render page
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    //Check the presented contains
    WebElement elemStatus = ElementHelper.FindElement( DRIVER, By.id( "status" ) );
    assertNotNull( elemStatus );
    assertEquals( "In Process", elemStatus.getAttribute( "value" ) );
    //Check we have three elements and no more than that
    String textPaging = ElementHelper.WaitForElementPresentGetText( DRIVER, By.id( "contents_info" ) );
    assertEquals( "View 1 to 1 of 1 elements", textPaging );
    //Check text on table
    String columnOneRowOne = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='contents']/tbody/tr/td" ) );
    String columnTwoRowOne = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='contents']/tbody/tr/td[2]" ) );
    assertEquals( "103", columnOneRowOne );
    assertEquals( "Atelier graphique", columnTwoRowOne );

  }

  @AfterClass
  public static void tearDownClass() {
    LOG.info( "tearDown##" + CDA108.class.getSimpleName() );
  }
}
