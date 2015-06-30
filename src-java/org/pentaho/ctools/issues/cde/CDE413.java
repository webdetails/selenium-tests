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
 * - http://jira.pentaho.com/browse/CDE-413
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-936
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDE413 {
  // Instance of the driver (browser emulator)
  private final WebDriver driver = CToolsTestSuite.getDriver();
  //Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDE413.class );
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( this.driver );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Check "Column Type" editor has nothing unexpected
   *
   * Description:
   *    The test pretends validate the CDE-413 issue, so when user edits column types of a table component
   *    the editor has nothing written other than the title of the input fields.
   *
   * Steps:
   *    1. Wait for new Dashboard to be created, assert elements on page and click "Components Panel"
   *    2. Wait for Components panel to be shown, expand "Others" and click "Table COmponent"
   *    3. Wait for table Component to be added and then click "Column Types" to edit
   *    4. Wait for popup to appear, click "Add" and then assert all elements on popup
   */
  @Test( timeout = 120000 )
  public void tc01_NewCdeDashboard_ColumnTypeEditor() {
    this.log.info( "tc01_NewCdeDashboard_ColumnTypeEditor" );

    /*
     * ## Step 1
     */
    //Go to New CDE Dashboard
    //Go to New CDE Dashboard
    this.driver.get( PageUrl.CDE_DASHBOARD );
    //assert buttons
    WebElement buttonSaveTemplate = this.elemHelper.WaitForElementPresence( this.driver, By.xpath( "//a[@title='Save as Template']" ) );
    WebElement buttonApplyTemplate = this.elemHelper.WaitForElementPresence( this.driver, By.xpath( "//a[@title='Apply Template']" ) );
    WebElement buttonAddResource = this.elemHelper.WaitForElementPresence( this.driver, By.xpath( "//a[@title='Add Resource']" ) );
    WebElement buttonAddBoostrap = this.elemHelper.WaitForElementPresence( this.driver, By.xpath( "//a[@title='Add Bootstrap Panel']" ) );
    WebElement buttonAddFreeForm = this.elemHelper.WaitForElementPresence( this.driver, By.xpath( "//a[@title='Add FreeForm']" ) );
    WebElement buttonAddRow = this.elemHelper.WaitForElementPresence( this.driver, By.xpath( "//a[@title='Add Row']" ) );
    WebElement buttonLayout = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='layoutPanelButton']" ) );
    WebElement buttonComponents = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='componentsPanelButton']" ) );
    WebElement buttonDatasources = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='datasourcesPanelButton']" ) );
    assertNotNull( buttonSaveTemplate );
    assertNotNull( buttonApplyTemplate );
    assertNotNull( buttonAddResource );
    assertNotNull( buttonAddBoostrap );
    assertNotNull( buttonAddFreeForm );
    assertNotNull( buttonAddRow );
    assertNotNull( buttonLayout );
    assertNotNull( buttonComponents );
    assertNotNull( buttonDatasources );
    this.elemHelper.Click( this.driver, By.cssSelector( "div.componentsPanelButton" ) );

    /*
     * ## Step 2
     */
    String otherText = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[2]/h3/a" ) );
    assertEquals( "Others", otherText );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[2]/h3/a" ) );
    WebElement optionTableComponent = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.cssSelector( "a[title='table Component']" ) );
    assertNotNull( optionTableComponent );
    this.elemHelper.Click( this.driver, By.cssSelector( "a[title='table Component']" ) );

    /*
     * ## Step 3
     */
    //Click in the columnTypes - property
    WebElement propertyColumnTypes = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='cdfdd-components-properties']//tr[4]/td[2]" ) );
    assertNotNull( propertyColumnTypes );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@id='cdfdd-components-properties']//tr[4]/td[2]" ) );
    //We need to wait for the animation finish for the display popup
    this.elemHelper.WaitForAttributeValueEqualsTo( this.driver, By.id( "popup" ), "style", "position: absolute; top: 15%; left: 50%; margin-left: -143px; z-index: 1000;" );

    /*
     * ## Step 4
     */
    this.elemHelper.Click( this.driver, By.cssSelector( "input.StringArrayAddButton" ) );
    WebElement arg0RemoveElement = this.elemHelper.FindElement( this.driver, By.id( "remove_button_0" ) );
    WebElement arg0InputElement = this.elemHelper.FindElement( this.driver, By.id( "arg_0" ) );
    WebElement arg0DragDropIconElement = this.elemHelper.FindElement( this.driver, By.cssSelector( "div.StringArrayDragIcon" ) );
    assertNotNull( arg0RemoveElement );
    assertNotNull( arg0InputElement );
    assertNotNull( arg0DragDropIconElement );
  }

}
