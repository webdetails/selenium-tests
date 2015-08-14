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
 * - http://jira.pentaho.com/browse/CDE-410
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-994
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDEDataPanel extends BaseTest {
  //Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDEDataPanel.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    This test is meant to validate all issues related to CDE's Data Panel
   *
   * Description:
   *    CDE-410: Shortcuts work
   *
   * Steps:
   *    1. Open CDE sample in edit mode, go to data panel and add some datasources
   *    2. Assert down, up, left and right shortcuts work on components panel
   *    3. Assert tab and enter shortcuts work on data panel
   *        
   **/
  @Test
  public void tc01_CDEDashboardEdit_DataPanel() {
    this.log.info( "tc01_CDEDashboardEdit_DataPanel" );

    /*
     * ## Step 1
     */
    //Open CDE sample in edit mode
    this.driver.get( PageUrl.CDE_DASHBOARD );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Go to Components Panel
    this.elemHelper.Click( this.driver, By.xpath( "//div[@title='Datasources Panel']/a" ) );

    //Add some Datasources
    WebElement mdxExpander = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='cdfdd-datasources-palletePallete']/div[6]/h3/span" ) );
    assertNotNull( mdxExpander );
    mdxExpander.click();
    WebElement addDenormalMdx = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='cdfdd-datasources-palletePallete']/div[6]//a[@title='denormalizedMdx over mondrianJdbc']" ) );
    assertNotNull( addDenormalMdx );
    addDenormalMdx.click();
    WebElement addMDX = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='cdfdd-datasources-palletePallete']/div[6]//a[@title='mdx over mondrianJndi']" ) );
    assertNotNull( addMDX );
    addMDX.click();
    WebElement sqlExpander = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='cdfdd-datasources-palletePallete']/div[12]/h3/span" ) );
    assertNotNull( sqlExpander );
    sqlExpander.click();
    WebElement addSqlJdbc = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='cdfdd-datasources-palletePallete']/div[12]//a[@title='sql over sqlJdbc']" ) );
    assertNotNull( addSqlJdbc );
    addSqlJdbc.click();
    WebElement addSqlJndi = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='cdfdd-datasources-palletePallete']/div[12]//a[@title='sql over sqlJndi']" ) );
    assertNotNull( addSqlJndi );
    addSqlJndi.click();

    /*
     * ## Step 2
     */
    //Select first chart group
    this.elemHelper.Click( this.driver, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr/td" ) );
    this.elemHelper.WaitForAttributeValue( this.driver, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr" ), "class", "expanded initialized parent ui-state-active" );
    String mdxGroup = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr" ) ).getAttribute( "class" );
    assertEquals( mdxGroup, "expanded initialized parent ui-state-active" );

    //Assert groups are expanded and clicking left will collapse them, also assert down arrow moves between showing elements
    WebElement mdxDenormal = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[2]" ) );
    assertNotNull( mdxDenormal );
    WebElement sqlJdbc = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[5]" ) );
    assertNotNull( sqlJdbc );
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
    this.elemHelper.WaitForAttributeValue( this.driver, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[4]" ), "class", "initialized parent ui-state-active collapsed" );
    String otherGroup = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[4]" ) ).getAttribute( "class" );
    assertEquals( otherGroup, "initialized parent ui-state-active collapsed" );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[2]" ) );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[5]" ) );

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
    mdxDenormal = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[2]" ) );
    assertNotNull( mdxDenormal );
    sqlJdbc = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[5]" ) );
    assertNotNull( sqlJdbc );

    //Go to to sqlJndi and assert it's selected
    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    this.elemHelper.WaitForAttributeValue( this.driver, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[6]" ), "class", "child-of-SQL initialized collapsed ui-state-active" );
    String areaChartClass = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[6]" ) ).getAttribute( "class" );
    assertEquals( areaChartClass, "child-of-SQL initialized collapsed ui-state-active" );

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
    this.elemHelper.WaitForAttributeValue( this.driver, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr" ), "class", "initialized ui-state-active" );
    String nameProperty = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr" ) ).getAttribute( "class" );
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
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }

    /*
     * ## Step 4
     */
    //Change "Parameter" and assert using down arrow to navigate is blocked
    this.elemHelper.WaitForAttributeValue( this.driver, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[5]" ), "class", "initialized ui-state-active" );
    String parameterProperty = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[5]" ) ).getAttribute( "class" );
    assertEquals( parameterProperty, "initialized ui-state-active" );
    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_ENTER );
      robot.keyRelease( KeyEvent.VK_ENTER );
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
      robot.keyPress( KeyEvent.VK_UP );
      robot.keyRelease( KeyEvent.VK_UP );
      robot.keyPress( KeyEvent.VK_ESCAPE );
      robot.keyRelease( KeyEvent.VK_ESCAPE );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }

    this.elemHelper.WaitForAttributeValue( this.driver, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[5]" ), "class", "initialized ui-state-active" );
    parameterProperty = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[5]" ) ).getAttribute( "class" );
    assertEquals( parameterProperty, "initialized ui-state-active" );

    //assert values are changed
    String nameValue = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr/td[2]" ) );
    assertEquals( nameValue, "a" );

    //Click tab and assert focus has gone back to first table
    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_TAB );
      robot.keyRelease( KeyEvent.VK_TAB );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    this.elemHelper.WaitForAttributeValue( this.driver, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[6]" ), "class", "initialized" );
    parameterProperty = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[6]" ) ).getAttribute( "class" );
    assertEquals( parameterProperty, "initialized" );
  }
}
