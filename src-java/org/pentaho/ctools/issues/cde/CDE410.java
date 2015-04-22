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
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDE410 {

  // Instance of the driver (browser emulator)
  private static WebDriver DRIVER;
  // The base url to be append the relative url in test
  private static String BASE_URL;
  // Log instance
  private static Logger LOG = LogManager.getLogger( CDE410.class );
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( DRIVER );

  @BeforeClass
  public static void setUpClass() {
    LOG.info( "setUp##" + CDE410.class.getSimpleName() );
    DRIVER = CToolsTestSuite.getDriver();
    BASE_URL = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Shortcuts work
   *
   * Description:
   *    The test pretends validate the CDE-410 issue, so shortcuts referred on issue work.
   *
   * Steps:
   *    1. Open CDE sample in edit mode and assert elements on page
   *    2. Assert down and up shortcuts work on layout panel
   *    3. Assert expand and collapse work on layout panel and repeat 1st step inside expanded element
   *    4. Assert tab and enter keys work on layout panel
   *    5. Repeat steps 2, 3 and 4 for Components Panel
   *    6. Repeat steps 2, 3 and 4 for Datasources Panel
   **/
  @Test( timeout = 120000 )
  public void tc01_CdeDashboard_LayoutShortcuts() {
    LOG.info( "tc01_CdeDashboard_LayoutShortcuts" );

    /*
     * ## Step 1
     */
    //Open CDE sample in edit mode
    DRIVER.get( BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Acde_sample1.wcdf/edit" );
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    //assert elements on page
    WebElement element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//a[@title='Save as Template']" ) );
    assertNotNull( element );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//a[@title='Apply Template']" ) );
    assertNotNull( element );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//a[@title='Add Resource']" ) );
    assertNotNull( element );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//a[@title='Add FreeForm']" ) );
    assertNotNull( element );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//a[@title='Add Row']" ) );
    assertNotNull( element );

    /*
     * ## Step 2
     */
    //Press down and assert second Resource is selected
    ElementHelper.Click( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ) );
    String class1 = ElementHelper.FindElement( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr" ) ).getAttribute( "class" );
    assertEquals( class1, "ui-draggable ui-droppable initialized ui-state-active" );
    Robot robot;
    try {
      robot = new Robot();
      robot.keyPress( 40 );
      robot.keyRelease( 40 );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    ElementHelper.WaitForAttributeValue( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[2]" ), "class", "ui-draggable ui-draggable-disabled ui-droppable initialized ui-state-active" );
    class1 = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[2]" ) ).getAttribute( "class" );
    assertEquals( class1, "ui-draggable ui-draggable-disabled ui-droppable initialized ui-state-active" );

    //Go to row and back to first resource and assert it's selected
    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
      robot.keyPress( KeyEvent.VK_UP );
      robot.keyRelease( KeyEvent.VK_UP );
      robot.keyPress( KeyEvent.VK_UP );
      robot.keyRelease( KeyEvent.VK_UP );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    ElementHelper.WaitForAttributeValue( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr" ), "class", "ui-draggable ui-droppable initialized ui-state-active" );
    class1 = ElementHelper.FindElement( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr" ) ).getAttribute( "class" );
    assertEquals( class1, "ui-draggable ui-droppable initialized ui-state-active" );

    /*
     * ## Step 3
     */
    //Assert columns aren't visible, go to row and expand it and then assert columns are visible
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[4]" ) );
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[14]" ) );

    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
      robot.keyPress( KeyEvent.VK_RIGHT );
      robot.keyRelease( KeyEvent.VK_RIGHT );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[4]" ) );
    assertNotNull( element );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[14]" ) );
    assertNotNull( element );

    //Go to second column and back to first and assert first column is selected
    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
      robot.keyPress( KeyEvent.VK_UP );
      robot.keyRelease( KeyEvent.VK_UP );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    ElementHelper.WaitForAttributeValue( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[4]" ), "class", "child-of-5a376f7c-edb3-0aa2-291d-4d512631ed6e ui-draggable ui-droppable initialized parent collapsed ui-state-active" );
    class1 = ElementHelper.FindElement( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[4]" ) ).getAttribute( "class" );
    assertEquals( class1, "child-of-5a376f7c-edb3-0aa2-291d-4d512631ed6e ui-draggable ui-droppable initialized parent collapsed ui-state-active" );

    /*
     * ## Step 4
     */
    //Click tab key and assert focus has changed to properties table
    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_TAB );
      robot.keyRelease( KeyEvent.VK_TAB );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    ElementHelper.WaitForAttributeValue( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-properties']/tbody/tr" ), "class", "initialized ui-state-active" );
    class1 = ElementHelper.FindElement( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-properties']/tbody/tr" ) ).getAttribute( "class" );
    assertEquals( class1, "initialized ui-state-active" );

    //Click enter to change following properties "Name", "Span size" and "Right border"
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
      //Change "Span Size"
      ElementHelper.WaitForAttributeValue( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-properties']/tbody/tr[2]" ), "class", "initialized ui-state-active" );
      class1 = ElementHelper.FindElement( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-properties']/tbody/tr[2]" ) ).getAttribute( "class" );
      assertEquals( class1, "initialized ui-state-active" );
      robot.keyPress( KeyEvent.VK_ENTER );
      robot.keyRelease( KeyEvent.VK_ENTER );
      robot.keyPress( KeyEvent.VK_1 );
      robot.keyRelease( KeyEvent.VK_1 );
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
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
      //Change "Right Border"
      ElementHelper.WaitForAttributeValue( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-properties']/tbody/tr[7]" ), "class", "initialized ui-state-active" );
      class1 = ElementHelper.FindElement( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-properties']/tbody/tr[7]" ) ).getAttribute( "class" );
      assertEquals( class1, "initialized ui-state-active" );
      robot.keyPress( KeyEvent.VK_ENTER );
      robot.keyRelease( KeyEvent.VK_ENTER );
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
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

    //assert values are changed
    String value = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-properties']/tbody/tr/td[2]" ) );
    assertEquals( value, "a" );
    value = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-properties']/tbody/tr[2]/td[2]" ) );
    assertEquals( value, "1" );
    value = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-properties']/tbody/tr[7]/td[2]" ) );
    assertEquals( value, "False" );

    //Click tab and assert focus has gone back to first table
    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_TAB );
      robot.keyRelease( KeyEvent.VK_TAB );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    ElementHelper.WaitForAttributeValue( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-properties']/tbody/tr[7]" ), "class", "initialized" );
    class1 = ElementHelper.FindElement( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-properties']/tbody/tr[7]" ) ).getAttribute( "class" );
    assertEquals( class1, "initialized" );

    //Collapse Row and assert columns aren't showing
    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_UP );
      robot.keyRelease( KeyEvent.VK_UP );
      robot.keyPress( KeyEvent.VK_LEFT );
      robot.keyRelease( KeyEvent.VK_LEFT );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[4]" ) );
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[14]" ) );

    /*
     * ## Step 5
     */
    ElementHelper.Click( DRIVER, By.xpath( "//div[@title='Components Panel']/a" ) );
    //Press down and assert second Chart group
    ElementHelper.Click( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr/td" ) );
    ElementHelper.WaitForAttributeValue( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr" ), "class", "initialized parent collapsed ui-state-active" );
    class1 = ElementHelper.FindElement( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr" ) ).getAttribute( "class" );
    assertEquals( class1, "initialized parent collapsed ui-state-active" );
    try {
      robot = new Robot();
      robot.keyPress( 40 );
      robot.keyRelease( 40 );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    ElementHelper.WaitForAttributeValue( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[3]" ), "class", "initialized parent collapsed ui-state-active" );
    class1 = ElementHelper.FindElement( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[3]" ) ).getAttribute( "class" );
    assertEquals( class1, "initialized parent collapsed ui-state-active" );

    //Assert Charts aren't visible, go to row and expand it and then assert columns are visible
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[2]" ) );

    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_UP );
      robot.keyRelease( KeyEvent.VK_UP );
      robot.keyPress( KeyEvent.VK_RIGHT );
      robot.keyRelease( KeyEvent.VK_RIGHT );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[2]" ) );
    assertNotNull( element );

    //Go to to chart and assert it's selected
    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    ElementHelper.WaitForAttributeValue( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[2]" ), "class", "child-of-CHARTS initialized collapsed ui-state-active" );
    class1 = ElementHelper.FindElement( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[2]" ) ).getAttribute( "class" );
    assertEquals( class1, "child-of-CHARTS initialized collapsed ui-state-active" );

    //Click tab key and assert focus has changed to properties table
    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_TAB );
      robot.keyRelease( KeyEvent.VK_TAB );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    ElementHelper.WaitForAttributeValue( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr" ), "class", "initialized ui-state-active" );
    class1 = ElementHelper.FindElement( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr" ) ).getAttribute( "class" );
    assertEquals( class1, "initialized ui-state-active" );

    //Click enter to change following properties "Name", "Title" and "V1 - Bar line"
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
      //Change "Title"
      ElementHelper.WaitForAttributeValue( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[2]" ), "class", "initialized ui-state-active" );
      class1 = ElementHelper.FindElement( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[2]" ) ).getAttribute( "class" );
      assertEquals( class1, "initialized ui-state-active" );
      robot.keyPress( KeyEvent.VK_ENTER );
      robot.keyRelease( KeyEvent.VK_ENTER );
      robot.keyPress( KeyEvent.VK_1 );
      robot.keyRelease( KeyEvent.VK_1 );
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
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
      //Change "V1 - Bar line"
      ElementHelper.WaitForAttributeValue( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[9]" ), "class", "initialized ui-state-active" );
      class1 = ElementHelper.FindElement( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[9]" ) ).getAttribute( "class" );
      assertEquals( class1, "initialized ui-state-active" );
      robot.keyPress( KeyEvent.VK_ENTER );
      robot.keyRelease( KeyEvent.VK_ENTER );
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
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

    //assert values are changed
    value = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr/td[2]" ) );
    assertEquals( value, "a" );
    value = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[2]/td[2]" ) );
    assertEquals( value, "1" );
    value = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[9]/td[2]" ) );
    assertEquals( value, "False" );

    //Click tab and assert focus has gone back to first table
    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_TAB );
      robot.keyRelease( KeyEvent.VK_TAB );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    ElementHelper.WaitForAttributeValue( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[9]" ), "class", "initialized" );
    class1 = ElementHelper.FindElement( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[9]" ) ).getAttribute( "class" );
    assertEquals( class1, "initialized" );

    //Collapse Chart group and assert Chart isn't showing
    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_UP );
      robot.keyRelease( KeyEvent.VK_UP );
      robot.keyPress( KeyEvent.VK_LEFT );
      robot.keyRelease( KeyEvent.VK_LEFT );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[2]" ) );

    /*
     * ## Step 6
     */
    ElementHelper.Click( DRIVER, By.xpath( "//div[@class='datasourcesPanelButton']" ) );
    //Press down and assert second Chart group
    ElementHelper.Click( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr/td" ) );
    ElementHelper.WaitForAttributeValue( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr" ), "class", "initialized parent collapsed ui-state-active" );
    class1 = ElementHelper.FindElement( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr" ) ).getAttribute( "class" );
    assertEquals( class1, "initialized parent collapsed ui-state-active" );
    try {
      robot = new Robot();
      robot.keyPress( 40 );
      robot.keyRelease( 40 );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    ElementHelper.WaitForAttributeValue( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[3]" ), "class", "initialized parent collapsed ui-state-active" );
    class1 = ElementHelper.FindElement( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[3]" ) ).getAttribute( "class" );
    assertEquals( class1, "initialized parent collapsed ui-state-active" );

    //Assert Datasource aren't visible, go to Datasource group and expand it and then assert Datasource is visible
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[2]" ) );

    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_UP );
      robot.keyRelease( KeyEvent.VK_UP );
      robot.keyPress( KeyEvent.VK_RIGHT );
      robot.keyRelease( KeyEvent.VK_RIGHT );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[2]" ) );
    assertNotNull( element );

    //Go to Datasource and assert it's selected
    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    ElementHelper.WaitForAttributeValue( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[2]" ), "class", "child-of-MDX initialized collapsed ui-state-active" );
    class1 = ElementHelper.FindElement( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[2]" ) ).getAttribute( "class" );
    assertEquals( class1, "child-of-MDX initialized collapsed ui-state-active" );

    //Click tab key and assert focus has changed to properties table
    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_TAB );
      robot.keyRelease( KeyEvent.VK_TAB );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    ElementHelper.WaitForAttributeValue( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr" ), "class", "initialized ui-state-active" );
    class1 = ElementHelper.FindElement( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr" ) ).getAttribute( "class" );
    assertEquals( class1, "initialized ui-state-active" );

    //Click enter to change following properties "Name", "Acces Level" and "Cache"
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
      //Change "Access Level"
      ElementHelper.WaitForAttributeValue( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[2]" ), "class", "initialized ui-state-active" );
      class1 = ElementHelper.FindElement( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[2]" ) ).getAttribute( "class" );
      assertEquals( class1, "initialized ui-state-active" );
      robot.keyPress( KeyEvent.VK_ENTER );
      robot.keyRelease( KeyEvent.VK_ENTER );
      robot.keyPress( KeyEvent.VK_1 );
      robot.keyRelease( KeyEvent.VK_1 );
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
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
      //Change "Cache"
      ElementHelper.WaitForAttributeValue( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[14]" ), "class", "initialized ui-state-active" );
      class1 = ElementHelper.FindElement( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[14]" ) ).getAttribute( "class" );
      assertEquals( class1, "initialized ui-state-active" );
      robot.keyPress( KeyEvent.VK_ENTER );
      robot.keyRelease( KeyEvent.VK_ENTER );
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
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

    //assert values are changed
    value = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr/td[2]" ) );
    assertEquals( value, "a" );
    value = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[2]/td[2]" ) );
    assertEquals( value, "1" );
    value = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[14]/td[2]" ) );
    assertEquals( value, "False" );

    //Click tab and assert focus has gone back to first table
    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_TAB );
      robot.keyRelease( KeyEvent.VK_TAB );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    ElementHelper.WaitForAttributeValue( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[14]" ), "class", "initialized" );
    class1 = ElementHelper.FindElement( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[14]" ) ).getAttribute( "class" );
    assertEquals( class1, "initialized" );

    //Collapse Datasource group and assert Datasource isn't showing
    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_UP );
      robot.keyRelease( KeyEvent.VK_UP );
      robot.keyPress( KeyEvent.VK_LEFT );
      robot.keyRelease( KeyEvent.VK_LEFT );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[2]" ) );

  }

  @AfterClass
  public static void tearDownClass() {
    LOG.info( "tearDown##" + CDE410.class.getSimpleName() );
  }
}
