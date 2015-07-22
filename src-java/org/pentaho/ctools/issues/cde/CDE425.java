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
import static org.junit.Assert.assertTrue;

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
 * - http://jira.pentaho.com/browse/CDE-425
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-995
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDE425 {
  // Instance of the driver (browser emulator)
  private final WebDriver driver = CToolsTestSuite.getDriver();
  //Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDE425.class );
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( this.driver );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Shortcuts work
   *
   * Description:
   *    The test pretends validate the CDE-425 issue, so shortcuts referred on issue work.
   *
   * Steps:
   *    1. Open new CDE dashboard and assert elements on page
   *    2. Click "r" and assert new Row was created
   *    3. Click "c" and assert column was created
   *    4. Click "h" and assert html element was created
   *    5. Click row element and click "shift+d" and assert row was duplicated
   *    6. Click newly created row element and click "shift+x" and assert row was deleted
   **/
  @ Test
  public void tc01_CdeDashboard_LayoutShorcuts() {
    this.log.info( "tc01_CdeDashboard_LayoutShortcuts" );

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
    Robot robot1;
    try {
      robot1 = new Robot();
      robot1.keyPress( KeyEvent.VK_R );
      robot1.keyRelease( KeyEvent.VK_R );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    /*element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ) );
    if ( element == null ) {
      this.log.info( "Element was null!!" );
      try {
        robot = new Robot();
        robot.keyPress( KeyEvent.VK_R );
        robot.keyRelease( KeyEvent.VK_R );
      } catch ( AWTException e ) {
        e.printStackTrace();
      }
    }*/
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ) );
    assertNotNull( element );
    this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ), "Row" );

    /*
     * ## Step 3
     */
    this.elemHelper.Click( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ) );
    Robot robot;
    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_C );
      robot.keyRelease( KeyEvent.VK_C );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[2]/td" ) );
    assertNotNull( element );
    this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[2]/td" ), "Column" );

    /*
     * ## Step 4
     */
    this.elemHelper.Click( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[2]/td" ) );

    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_H );
      robot.keyRelease( KeyEvent.VK_H );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[3]/td" ) );
    assertNotNull( element );
    this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[3]/td" ), "Html" );

    /*
     * ## Step 5
     */
    this.elemHelper.Click( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ) );
    try {
      robot = new Robot();
      robot.keyPress( 16 );
      robot.keyPress( KeyEvent.VK_D );
      robot.keyRelease( KeyEvent.VK_D );
      robot.keyRelease( 16 );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[4]/td" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[5]/td" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]/td" ) );
    assertNotNull( element );
    this.elemHelper.Click( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td/span" ) );
    this.elemHelper.Click( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[2]/td/span" ) );
    String text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ), "Row" );
    assertEquals( "Row", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[2]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[3]/td" ), "Html" );
    assertEquals( "Html", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[4]/td" ), "Row" );
    assertEquals( "Row", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[5]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]/td" ), "Html" );
    assertEquals( "Html", text );

    /*
     * ## Step 6
     */
    this.elemHelper.Click( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ) );
    try {
      robot = new Robot();
      robot.keyPress( 16 );
      robot.keyPress( KeyEvent.VK_X );
      robot.keyRelease( KeyEvent.VK_X );
      robot.keyRelease( 16 );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[4]/td" ) );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ), "Row" );
    assertEquals( "Row", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[2]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[3]/td" ), "Html" );
    assertEquals( "Html", text );
    assertTrue( this.elemHelper.WaitForElementNotPresent( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[4]/td" ), 1 ) );
  }

}
