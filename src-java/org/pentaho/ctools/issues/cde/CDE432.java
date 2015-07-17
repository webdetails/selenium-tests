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

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.PageUrl;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-432
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-997
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDE432 {
  // Instance of the driver (browser emulator)
  private final WebDriver driver = CToolsTestSuite.getDriver();
  //Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDE432.class );
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( this.driver );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Left is the first option of TextAlign property
   *
   * Description:
   *    The test pretends validate the CDE-432 issue, so when user clicks down arrow when TextAlign property is selected, "Left" is the first option shown
   *
   * Steps:
   *    1. Open new CDE dashboard and assert elements on page
   *    2. Click "r", assert row was created and click "Text Align" property
   *    3. Click down arrow twice and click enter
   *    4. Assert "Left" option is selected for the "Text Align" property
   **/
  @Test( timeout = 120000 )
  public void tc01_CdeDashboard_NoExtraOptions() {
    this.log.info( "tc01_CdeDashboard_NoExtraOptions" );

    /*
     * ## Step 1
     */
    //Go to New CDE Dashboard
    this.driver.get( PageUrl.CDE_DASHBOARD );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    //assert buttons
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@title='Save as Template']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@title='Apply Template']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@title='Add Resource']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@title='Add Bootstrap Panel']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@title='Add FreeForm']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@title='Add Row']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='layoutPanelButton']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='componentsPanelButton']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='datasourcesPanelButton']" ) );
    assertNotNull( element );

    /*
     * ## Step 2
     */
    //this.elemHelper.Click( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody" ) );
    Robot robot;
    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_R );
      robot.keyRelease( KeyEvent.VK_R );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ) );
    if ( element == null ) {
      this.log.info( "Element was null!!" );
      try {
        robot = new Robot();
        robot.keyPress( KeyEvent.VK_R );
        robot.keyRelease( KeyEvent.VK_R );
      } catch ( AWTException e ) {
        e.printStackTrace();
      }
    }
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ) );
    assertNotNull( element );
    String text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ), "Row" );
    assertEquals( "Row", text );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-properties']/tbody/tr[5]/td[2]" ) );
    assertNotNull( element );
    this.elemHelper.Click( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-properties']/tbody/tr[5]/td[2]" ) );

    /*
     * ## Step 3
     */
    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
      robot.keyPress( KeyEvent.VK_ENTER );
      robot.keyRelease( KeyEvent.VK_ENTER );
      robot.keyPress( KeyEvent.VK_ENTER );
      robot.keyRelease( KeyEvent.VK_ENTER );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }

    /*
     * ## Step 4
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-properties']/tbody/tr[5]/td[2]" ) );
    assertNotNull( element );
    String align = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-properties']/tbody/tr[5]/td[2]" ) );
    assertEquals( "Left", align );

  }

}
