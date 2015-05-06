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
import org.pentaho.ctools.cde.widgets.utils.WidgetUtils;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-453
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1024
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDE453 {
  // The widget name that we what to create
  private static final String WIDGET_NAME = "CDE453";
  // Indicator to check if any assert fails in the test case
  private static boolean noAssertFails = false;
  // Instance of the driver (browser emulator)
  private static WebDriver DRIVER;
  // Log instance
  private static Logger LOG = LogManager.getLogger( CDE453.class );
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( DRIVER );

  @BeforeClass
  public static void setUpClass() {
    LOG.info( "setUp##" + CDE453.class.getSimpleName() );
    DRIVER = CToolsTestSuite.getDriver();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert that when a new widget is created it is readily available in the components panel of CDE
   *
   * Description:
   *    The test pretends validate the CDE-453 issue, so when user creates a widget it's available in the
   *    components panel of CDE.
   *
   * Steps:
   *    1. Open New Dashboard and save as Widget
   *    2. Open New Dashboard and assert new Widget is present in components panel
   *    3. Delete newly created Widget
   *
   */
  @Test( timeout = 180000 )
  public void tc01_NewCDEDashboard_NewWidgetPresent() {
    LOG.info( "tc01_NewCDEDashboard_NewWidgetPresent" );

    /*
     * ## Step 1
     */
    WidgetUtils.CreateWidget( DRIVER, WIDGET_NAME );

    /*
     * ## Step 2
     */
    //Go to Components Panel
    ElementHelper.Click( DRIVER, By.xpath( "//div[@class='componentsPanelButton']" ) );
    //Expand Widgets option
    ElementHelper.ClickJS( DRIVER, By.xpath( "//h3[@id='ui-accordion-cdfdd-components-palletePallete-header-8']/span" ) );
    //Check the widget created is visible in the list of Widgets
    WebElement widgetCDE453 = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.linkText( WIDGET_NAME ) );
    assertNotNull( widgetCDE453 );
    ElementHelper.Click( DRIVER, By.linkText( WIDGET_NAME ) );
    //Check the widget was added to the list of components
    String groupName = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//tr[@id='WIDGETS']/td[2]" ) );
    assertEquals( "Widgets", groupName );
    // Check the group added is Widgets
    String displayWidgetName = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//tr[2]/td" ) );
    String expectedWidgetName = WIDGET_NAME + " Widget";
    assertEquals( expectedWidgetName, displayWidgetName );

    /*
     * ## Step 3
     */
    WidgetUtils.RemoveWidgetByName( DRIVER, WIDGET_NAME );
    noAssertFails = true;
  }

  @AfterClass
  public static void tearDownClass() {
    LOG.info( "tearDown##" + CDE453.class.getSimpleName() );

    if ( !noAssertFails ) {
      WidgetUtils.RemoveWidgetByName( DRIVER, WIDGET_NAME );
    }
  }
}
