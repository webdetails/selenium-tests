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
package com.pentaho.ctools.issues.cde;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.BaseTest;
import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-399
 * - http://jira.pentaho.com/browse/CDE-410
 * - http://jira.pentaho.com/browse/CDE-529
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-966
 * - http://jira.pentaho.com/browse/QUALITY-994
 * - http://jira.pentaho.com/browse/QUALITY-1143 
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDEComponentPanel extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDEComponentPanel.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    This test validates all issues related to CDE's Component Panel
   *
   * Description:
   *    399: Accordion works as expected
   *    410: Shortcuts work
   *    529: Opening a popup blocks the use of the shortcuts to navigate
   *    
   * Steps:
   *    1. Open CDE sample in edit mode, go to components panel and add some components
   *    2. Assert down, up, left and right shortcuts work on components panel
   *    3. Assert tab and enter shortcuts work on components panel
   *    4. Assert opening parameter popup blocks navigation by arrows
   *    
   */
  @Test
  public void tc01_CDEDashboardEdit_ComponentPanel() {
    this.log.info( "tc01_CDEDashboardEdit_ComponentPanel" );

    /*
     * ## Step 1
     */
    //Open CDE sample in edit mode
    driver.get( PageUrl.CDE_DASHBOARD );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Go to Components Panel
    this.elemHelper.Click( driver, By.xpath( "//div[@title='Components Panel']/a" ) );

    //Assert behavior of accordion
    WebElement otherExpander = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[2]/h3/a" ) );
    assertNotNull( otherExpander );
    String otherText = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[2]/h3/a" ) );
    assertEquals( "Others", otherText );
    this.elemHelper.Click( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[2]/h3/a" ) );
    WebElement tableComponent = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='table Component']" ) );
    assertNotNull( tableComponent );
    WebElement genericExpander = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[3]/h3/a" ) );
    assertNotNull( genericExpander );
    String genericText = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[3]/h3/a" ) );
    assertEquals( "Generic", genericText );
    this.elemHelper.Click( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[3]/h3/a" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//a[@title='table Component']" ) );

    //Add some Components
    WebElement chartExpander = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div/h3/span" ) );
    assertNotNull( chartExpander );
    chartExpander.click();
    WebElement addAreaChart = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div//a[@title='CCC Area Chart']" ) );
    assertNotNull( addAreaChart );
    addAreaChart.click();
    WebElement addBulletChart = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div//a[@title='CCC Bullet Chart']" ) );
    assertNotNull( addBulletChart );
    addBulletChart.click();
    otherExpander = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[2]/h3/span" ) );
    assertNotNull( otherExpander );
    otherExpander.click();
    WebElement addTable = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[2]//a[@title='table Component']" ) );
    assertNotNull( addTable );
    addTable.click();
    WebElement addButton = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[2]//a[@title='Button Component']" ) );
    assertNotNull( addButton );
    addButton.click();

    /*
     * ## Step 2
     */
    //Select first chart group
    this.elemHelper.Click( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr/td" ) );
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr" ), "class", "expanded initialized parent ui-state-active" );
    String chartGroup = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr" ) ).getAttribute( "class" );
    assertEquals( chartGroup, "expanded initialized parent ui-state-active" );

    //Assert groups are expanded and clicking left will collapse them, also assert down arrow moves between showing elements
    WebElement areaChart = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[2]" ) );
    assertNotNull( areaChart );
    tableComponent = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[5]" ) );
    assertNotNull( tableComponent );
    Robot robot;
    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_LEFT );
      robot.keyRelease( KeyEvent.VK_LEFT );
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
      robot.keyPress( KeyEvent.VK_LEFT );
      robot.keyRelease( KeyEvent.VK_LEFT );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[4]" ), "class", "initialized parent ui-state-active collapsed" );
    String otherGroup = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[4]" ) ).getAttribute( "class" );
    assertEquals( otherGroup, "initialized parent ui-state-active collapsed" );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[2]" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[5]" ) );

    //Assert clicking right arrow expands groups    
    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_RIGHT );
      robot.keyRelease( KeyEvent.VK_RIGHT );
      robot.keyPress( KeyEvent.VK_UP );
      robot.keyRelease( KeyEvent.VK_UP );
      robot.keyPress( KeyEvent.VK_RIGHT );
      robot.keyRelease( KeyEvent.VK_RIGHT );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    areaChart = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[2]" ) );
    assertNotNull( areaChart );
    tableComponent = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[5]" ) );
    assertNotNull( tableComponent );

    //Go to to area chart and assert it's selected
    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[2]" ), "class", "child-of-CHARTS initialized collapsed ui-state-active" );
    String areaChartClass = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[2]" ) ).getAttribute( "class" );
    assertEquals( areaChartClass, "child-of-CHARTS initialized collapsed ui-state-active" );

    /*
     * ## Step 3
     */
    //Click tab key and assert focus has changed to properties table
    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_TAB );
      robot.keyRelease( KeyEvent.VK_TAB );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr" ), "class", "initialized ui-state-active" );
    String nameProperty = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr" ) ).getAttribute( "class" );
    assertEquals( nameProperty, "initialized ui-state-active" );

    //Click enter to change following properties "Name" and "Parameters"
    try {
      robot = new Robot();
      //change "Name
      robot.keyPress( KeyEvent.VK_ENTER );
      robot.keyRelease( KeyEvent.VK_ENTER );
      robot.keyPress( KeyEvent.VK_A );
      robot.keyRelease( KeyEvent.VK_A );
      robot.keyPress( KeyEvent.VK_ENTER );
      robot.keyRelease( KeyEvent.VK_ENTER );
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }

    /*
     * ## Step 4
     */
    //Change "Parameter" and assert using down arrow to navigate is blocked
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[4]" ), "class", "initialized ui-state-active" );
    String parameterProperty = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[4]" ) ).getAttribute( "class" );
    assertEquals( parameterProperty, "initialized ui-state-active" );
    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_ENTER );
      robot.keyRelease( KeyEvent.VK_ENTER );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    assertNotNull( this.elemHelper.FindElement( driver, By.id( "popup" ) ) );
    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_TAB );
      robot.keyRelease( KeyEvent.VK_TAB );
      robot.keyPress( KeyEvent.VK_TAB );
      robot.keyRelease( KeyEvent.VK_TAB );
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
      robot.keyPress( KeyEvent.VK_UP );
      robot.keyRelease( KeyEvent.VK_UP );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    WebElement closePopup = this.elemHelper.FindElement( driver, By.id( "popup_state0_buttonCancel" ) );
    assertNotNull( closePopup );
    closePopup.click();
    assertTrue( this.elemHelper.WaitForElementNotPresent( driver, By.id( "popup" ) ) );

    //assert values are changed
    String nameValue = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr/td[2]" ) );
    assertEquals( nameValue, "a" );

    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[4]" ), "class", "initialized ui-state-active" );
    parameterProperty = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[4]" ) ).getAttribute( "class" );
    assertEquals( parameterProperty, "initialized ui-state-active" );

    //Click tab and assert focus has gone back to first table
    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_TAB );
      robot.keyRelease( KeyEvent.VK_TAB );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[4]" ), "class", "initialized" );
    parameterProperty = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[4]" ) ).getAttribute( "class" );
    assertEquals( parameterProperty, "initialized" );
  }
}
