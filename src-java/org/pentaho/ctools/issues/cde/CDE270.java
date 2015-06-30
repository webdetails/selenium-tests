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
 * - http://jira.pentaho.com/browse/CDE-270
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-943
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDE270 {
  // Instance of the driver (browser emulator)
  private final WebDriver driver = CToolsTestSuite.getDriver();
  //Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDE270.class );
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( this.driver );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Drag and drop of elements of layout panel works
   *
   * Description:
   *    The test pretends validate the CDE-270 issue, so when user tries to use drag and drop functionality of Layout elements
   *    it works as expected.
   *
   * Steps:
   *    1. Assert elements on page and add 3 sets of a row and 3 columns and assert layout elements
   *    2. Drag 2nd set into 1st column of first set and assert layout elements
   *    3. Drag 3rd set into 2nd column of first set and assert layout elements
   *    4. Drag 2nd column of first set into row of 2nd set (located in 1st column of first set) and assert layout elements
   */
  @Test( timeout = 120000 )
  public void tc01_CdeDashboard_LayoutDragAndDrop() {
    this.log.info( "tc01_CdeDashboard_LayoutDragAndDrop" );

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

    //Assert elements on page
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='datasourcesPanelButton']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "previewButton" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='layoutPanelButton']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='componentsPanelButton']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@title='Add Row']" ) );
    assertNotNull( element );
    element.click();
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@title='Add Columns']" ) );
    assertNotNull( element );

    //Add 3 sets of one row and three columns
    this.elemHelper.Click( this.driver, By.xpath( "//a[@title='Add Columns']" ) );
    this.elemHelper.Click( this.driver, By.xpath( "//a[@title='Add Columns']" ) );
    this.elemHelper.Click( this.driver, By.xpath( "//a[@title='Add Columns']" ) );
    this.elemHelper.Click( this.driver, By.xpath( "//a[@title='Add Row']" ) );
    this.elemHelper.Click( this.driver, By.xpath( "//a[@title='Add Columns']" ) );
    this.elemHelper.Click( this.driver, By.xpath( "//a[@title='Add Columns']" ) );
    this.elemHelper.Click( this.driver, By.xpath( "//a[@title='Add Columns']" ) );
    this.elemHelper.Click( this.driver, By.xpath( "//a[@title='Add Row']" ) );
    this.elemHelper.Click( this.driver, By.xpath( "//a[@title='Add Columns']" ) );
    this.elemHelper.Click( this.driver, By.xpath( "//a[@title='Add Columns']" ) );
    this.elemHelper.Click( this.driver, By.xpath( "//a[@title='Add Columns']" ) );

    //Assert elements on Layout
    String text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ), "Row" );
    assertEquals( "Row", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[2]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[3]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[4]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[5]/td" ), "Row" );
    assertEquals( "Row", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[8]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[9]/td" ), "Row" );
    assertEquals( "Row", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[10]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[11]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[12]/td" ), "Column" );
    assertEquals( "Column", text );

    /*
     * ## Step 2
     */
    this.elemHelper.DragAndDrop( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[5]/td" ), By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[2]/td" ) );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ), "Row" );
    assertEquals( "Row", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[2]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[3]/td" ), "Row" );
    assertEquals( "Row", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[4]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[5]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]/td" ), "Row" );
    assertEquals( "Row", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[8]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[9]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[10]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[11]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[12]/td" ), "Column" );
    assertEquals( "Column", text );

    /*
     * ## Step 3
     */
    this.elemHelper.DragAndDrop( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]/td" ), By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[11]/td" ) );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ), "Row" );
    assertEquals( "Row", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[2]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[3]/td" ), "Row" );
    assertEquals( "Row", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[4]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[5]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[8]/td" ), "Row" );
    assertEquals( "Row", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[9]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[10]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[11]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[12]/td" ), "Column" );
    assertEquals( "Column", text );

    /*
     * ## Step 4
     */
    this.elemHelper.DragAndDrop( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]/td" ), By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[3]/td" ) );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ), "Row" );
    assertEquals( "Row", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[2]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[3]/td" ), "Row" );
    assertEquals( "Row", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[4]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[5]/td" ), "Row" );
    assertEquals( "Row", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[8]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[9]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[10]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[11]/td" ), "Column" );
    assertEquals( "Column", text );
    text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[12]/td" ), "Column" );
    assertEquals( "Column", text );

  }
}
