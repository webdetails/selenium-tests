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
 * - http://jira.pentaho.com/browse/CDE-286
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1016
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDE286 {
  // Instance of the driver (browser emulator)
  private static WebDriver DRIVER;
  //Instance to be used on wait commands
  private static String BASE_URL;
  //Access to wrapper for webdriver
  private ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private static Logger LOG = LogManager.getLogger( CDE286.class );
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( DRIVER );

  @BeforeClass
  public static void setUpClass() {
    LOG.info( "setUp##" + CDE286.class.getSimpleName() );
    DRIVER = CToolsTestSuite.getDriver();
    BASE_URL = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Asserting CGG Dial Chart can be exported and viewed via URL
   *
   * Description:
   *    The test pretends validate the CDE-286 issue, so when user exports a CGG Dial Component it can
   *    be viewed via the URL given.
   *
   * Steps:
   *    1. Wait for new Dashboard to be created, assert elements on page and click "Components Panel"
   *    2. Add CGG Dial Component and fill it's properties
   *    3. Click "Shift+G" to create export file and choose URL
   *    4. Add "&param=25" to URL and assert Dial Component is properly shown
   * @throws InterruptedException 
   *
   */
  @Test( timeout = 120000 )
  public void tc01_NewCdeDashboard_CggDialComponentExport() {
    LOG.info( "tc01_NewCdeDashboard_CggDialComponentExport" );

    /*
     * ## Step 1
     */
    //Go to New CDE Dashboard
    DRIVER.get( BASE_URL + "api/repos/wcdf/new" );
    //assert buttons
    WebElement buttonSaveTemplate = this.elemHelper.WaitForElementPresence( DRIVER, By.xpath( "//a[@title='Save as Template']" ) );
    WebElement buttonApplyTemplate = this.elemHelper.WaitForElementPresence( DRIVER, By.xpath( "//a[@title='Apply Template']" ) );
    WebElement buttonAddResource = this.elemHelper.WaitForElementPresence( DRIVER, By.xpath( "//a[@title='Add Resource']" ) );
    WebElement buttonAddBoostrap = this.elemHelper.WaitForElementPresence( DRIVER, By.xpath( "//a[@title='Add Bootstrap Panel']" ) );
    WebElement buttonAddFreeForm = this.elemHelper.WaitForElementPresence( DRIVER, By.xpath( "//a[@title='Add FreeForm']" ) );
    WebElement buttonAddRow = this.elemHelper.WaitForElementPresence( DRIVER, By.xpath( "//a[@title='Add Row']" ) );
    WebElement buttonLayout = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@class='layoutPanelButton']" ) );
    WebElement buttonComponents = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@class='componentsPanelButton']" ) );
    WebElement buttonDatasources = this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@class='datasourcesPanelButton']" ) );
    assertNotNull( buttonSaveTemplate );
    assertNotNull( buttonApplyTemplate );
    assertNotNull( buttonAddResource );
    assertNotNull( buttonAddBoostrap );
    assertNotNull( buttonAddFreeForm );
    assertNotNull( buttonAddRow );
    assertNotNull( buttonLayout );
    assertNotNull( buttonComponents );
    assertNotNull( buttonDatasources );
    this.elemHelper.Click( DRIVER, By.cssSelector( "div.componentsPanelButton" ) );

    /*
     * ## Step 2
     */
    this.elemHelper.Click( DRIVER, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div/h3/span" ) );
    this.elemHelper.Click( DRIVER, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div/div/ul/li[24]/a[@title='CGG Dial Chart']" ) );
    String componentName = this.elemHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[2]/td" ) );
    assertEquals( "CGG Dial Chart", componentName );

    //Add Name
    String expectedChartName = "dial";
    this.elemHelper.FindElement( DRIVER, By.xpath( "//div[@id='cdfdd-components-properties']/div/div[2]/table/tbody/tr/td[2]/form/input" ) ).sendKeys( "dial" );
    this.elemHelper.FindElement( DRIVER, By.xpath( "//div[@id='cdfdd-components-properties']/div/div[2]/table/tbody/tr/td[2]/form/input" ) ).submit();
    this.elemHelper.WaitForTextPresence( DRIVER, By.xpath( "//div[@id='cdfdd-components-properties']/div/div[2]/table/tbody/tr/td[2]" ), "dial" );
    String actualChartName = this.elemHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='cdfdd-components-properties']/div/div[2]/table/tbody/tr/td[2]" ) );
    assertEquals( expectedChartName, actualChartName );

    //Add Color Range
    String strColor1 = "blue";
    String strColor2 = "green";
    String strColor3 = "brown";
    this.elemHelper.Click( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[2]/td[2]" ) );
    //We need to wait for the animation finish for the display popup
    this.elemHelper.WaitForAttributeValueEqualsTo( DRIVER, By.id( "popup" ), "style", "position: absolute; top: 15%; left: 50%; margin-left: -143px; z-index: 1000;" );
    //Add Colors
    this.elemHelper.Click( DRIVER, By.cssSelector( "input.StringArrayAddButton" ) );
    WebElement elemArg0 = this.elemHelper.FindElement( DRIVER, By.cssSelector( "input#arg_0" ) );
    this.elemHelper.Click( DRIVER, By.cssSelector( "input.StringArrayAddButton" ) );
    WebElement elemArg1 = this.elemHelper.FindElement( DRIVER, By.cssSelector( "input#arg_1" ) );
    this.elemHelper.Click( DRIVER, By.cssSelector( "input.StringArrayAddButton" ) );
    WebElement elemArg2 = this.elemHelper.FindElement( DRIVER, By.cssSelector( "input#arg_2" ) );
    assertNotNull( elemArg0 );
    assertNotNull( elemArg1 );
    assertNotNull( elemArg2 );
    //Add the first color
    this.elemHelper.ClickAndSendKeys( DRIVER, By.cssSelector( "input#arg_0" ), strColor1 );
    //Add the second color
    this.elemHelper.ClickAndSendKeys( DRIVER, By.cssSelector( "input#arg_1" ), strColor2 );
    //Add the third color
    this.elemHelper.ClickAndSendKeys( DRIVER, By.cssSelector( "input#arg_2" ), strColor3 );
    //Submit
    this.elemHelper.Click( DRIVER, By.id( "popup_state0_buttonOk" ) );
    //Wait For Popup Disappear
    this.elemHelper.WaitForElementNotPresent( DRIVER, By.id( "popupbox" ) );
    //Check the colors array
    this.elemHelper.WaitForTextPresence( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[2]/td[2]" ), "[\"" + strColor1 + "\",\"" + strColor2 + "\",\"" + strColor3 + "\"]" );
    String rangeColorArray = this.elemHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[2]/td[2]" ) );
    assertEquals( "[\"blue\",\"green\",\"brown\"]", rangeColorArray );

    //Add Intervals Array
    String strInterval0 = "0";
    String strInterval1 = "25";
    String strInterval2 = "50";
    String strInterval3 = "100";
    this.elemHelper.Click( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[3]/td[2]" ) );
    //We need to wait for the animation finish for the display popup
    this.elemHelper.WaitForAttributeValueEqualsTo( DRIVER, By.id( "popup" ), "style", "position: absolute; top: 15%; left: 50%; margin-left: -143px; z-index: 1000;" );
    //Add intervals
    this.elemHelper.Click( DRIVER, By.cssSelector( "input.StringArrayAddButton" ) );
    WebElement elemInterArg0 = this.elemHelper.FindElement( DRIVER, By.cssSelector( "input#arg_0" ) );
    this.elemHelper.Click( DRIVER, By.cssSelector( "input.StringArrayAddButton" ) ); // Add arg1
    WebElement elemInterArg1 = this.elemHelper.FindElement( DRIVER, By.cssSelector( "input#arg_1" ) );
    this.elemHelper.Click( DRIVER, By.cssSelector( "input.StringArrayAddButton" ) ); // Add arg2
    WebElement elemInterArg2 = this.elemHelper.FindElement( DRIVER, By.cssSelector( "input#arg_2" ) );
    this.elemHelper.Click( DRIVER, By.cssSelector( "input.StringArrayAddButton" ) ); // Add arg3
    WebElement elemInterArg3 = this.elemHelper.FindElement( DRIVER, By.cssSelector( "input#arg_3" ) );
    assertNotNull( elemInterArg0 );
    assertNotNull( elemInterArg1 );
    assertNotNull( elemInterArg2 );
    assertNotNull( elemInterArg3 );
    //Add interval 0
    this.elemHelper.ClickAndSendKeys( DRIVER, By.cssSelector( "input#arg_0" ), strInterval0 );
    //Add interval 1
    this.elemHelper.ClickAndSendKeys( DRIVER, By.cssSelector( "input#arg_1" ), strInterval1 );
    //Add interval 2
    this.elemHelper.ClickAndSendKeys( DRIVER, By.cssSelector( "input#arg_2" ), strInterval2 );
    //Add interval 3
    this.elemHelper.ClickAndSendKeys( DRIVER, By.cssSelector( "input#arg_3" ), strInterval3 );

    // Submit
    this.elemHelper.ClickJS( DRIVER, By.id( "popup_state0_buttonOk" ) );
    //Check if was saved
    String expectedIntervalArray = "[\"" + strInterval0 + "\",\"" + strInterval1 + "\",\"" + strInterval2 + "\",\"" + strInterval3 + "\"]";
    this.elemHelper.WaitForTextPresence( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[3]/td[2]" ), expectedIntervalArray );
    String actualIntervalsArray = this.elemHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[3]/td[2]" ) );
    assertEquals( expectedIntervalArray, actualIntervalsArray );

    //Add Parameter
    String expectedParameter = "27";
    this.elemHelper.Click( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[4]/td[2]" ) );
    this.elemHelper.FindElement( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[4]/td[2]/form/input" ) ).sendKeys( expectedParameter );
    this.elemHelper.FindElement( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[4]/td[2]/form/input" ) ).submit();
    String actualParameter = this.elemHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[4]/td[2]" ) );
    assertEquals( expectedParameter, actualParameter );

    //Add Height
    String expectedHeight = "321";
    this.elemHelper.Click( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[6]/td[2]" ) );
    this.elemHelper.FindElement( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[6]/td[2]/form/input" ) ).sendKeys( expectedHeight );
    this.elemHelper.FindElement( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[6]/td[2]/form/input" ) ).submit();
    String actualHeight = this.elemHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[6]/td[2]" ) );
    assertEquals( expectedHeight, actualHeight );

    //Add With
    String expectedWith = "215";
    this.elemHelper.Click( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[7]/td[2]" ) );
    this.elemHelper.FindElement( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[7]/td[2]/form/input" ) ).sendKeys( expectedWith );
    this.elemHelper.FindElement( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[7]/td[2]/form/input" ) ).submit();
    String actualWith = this.elemHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[7]/td[2]" ) );
    assertEquals( expectedWith, actualWith );

    /*
     * ## Step 3
     */
    Robot robot;
    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_SHIFT );
      robot.keyPress( KeyEvent.VK_G );
      robot.keyRelease( KeyEvent.VK_G );
      robot.keyRelease( KeyEvent.VK_SHIFT );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    WebElement cggDialog = this.elemHelper.WaitForElementPresence( DRIVER, By.id( "cggDialog" ) );
    assertNotNull( cggDialog );
    String actualCggDialogTitle = this.elemHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='cggDialog']/h3" ) );
    assertEquals( "Choose what charts to render as CGG", actualCggDialogTitle );
    String actualChartNameTorender = this.elemHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='cggDialog']/div/span" ) );
    assertEquals( expectedChartName, actualChartNameTorender );
    this.elemHelper.Click( DRIVER, By.xpath( "//div[@id='cggDialog']/div/input" ) );
    this.elemHelper.Click( DRIVER, By.xpath( "//div[@id='cggDialog']/div/button" ) );
    String actualUrl = this.elemHelper.GetAttribute( DRIVER, By.xpath( "//div[@id='cggDialog']/div/div/input" ), "value" );
    String expectedURL = "http://localhost:8080/pentaho/plugin/cgg/api/services/draw?script=/system/pentaho-cdf-dd/resources/custom/components/cgg/charts/dial.js&paramcolors=" + strColor1 + "&paramcolors=" + strColor2 + "&paramcolors=" + strColor3 + "&paramscale=" + strInterval0 + "&paramscale=" + strInterval1 + "&paramscale=" + strInterval2 + "&paramscale=" + strInterval3 + "&height=" + expectedHeight + "&width=" + expectedWith + "&outputType=png";
    assertEquals( expectedURL, actualUrl );

    /*
     * ## Step 4
     */
    DRIVER.get( expectedURL + "&paramvalue=25" );
    WebElement elemImg = this.elemHelper.FindElement( DRIVER, By.cssSelector( "img" ) );
    assertNotNull( elemImg );
    String actualImgUrl = this.elemHelper.GetAttribute( DRIVER, By.cssSelector( "img" ), "src" );
    assertEquals( expectedURL + "&paramvalue=25", actualImgUrl );

  }

  @AfterClass
  public static void tearDownClass() {
    LOG.info( "tearDown##" + CDE286.class.getSimpleName() );
  }
}
