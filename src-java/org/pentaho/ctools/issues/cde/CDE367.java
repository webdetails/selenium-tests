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
 * - http://jira.pentaho.com/browse/CDE-367
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-941
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDE367 {
  // Instance of the driver (browser emulator)
  private final WebDriver driver = CToolsTestSuite.getDriver();
  //Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDE367.class );
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( this.driver );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Edit colour of background by writing "#3d558c"
   *
   * Description:
   *    The test pretends validate the CDE-367 issue, so when user tries to edit the background colour of an
   *    element by pasting a string of a colour it works.
   *
   * Steps:
   *    1. Wait for new Dashboard to be created, assert elements on page and click "Add Row"
   *    2. Go to Properties, check the box for BackgroundColor and write "#3d558c" on input field
   *    3. Assert input field for "colorpicker_hex" has "3d558c"
   */
  @ Test
  public void tc01_CdeDashboard_ColorPickerPaste() {
    this.log.info( "tc01_CdeDashboard_ColorPickerPaste" );

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
    this.elemHelper.Click( this.driver, By.xpath( "//a[@title='Add Row']" ) );

    /*
     * ## Step 2
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//form[@class='cdfddInput']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//form[@class='cdfddInput']/input[@class='colorcheck']" ) );
    assertNotNull( element );
    this.elemHelper.Click( this.driver, By.xpath( "//form[@class='cdfddInput']/input[@class='colorcheck']" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//form[@class='cdfddInput']/input[@class='colorinput']" ) );
    assertNotNull( element );
    this.elemHelper.Click( this.driver, By.xpath( "//form[@class='cdfddInput']/input[@class='colorinput']" ) );
    this.elemHelper.FindElement( this.driver, By.xpath( "//form[@class='cdfddInput']/input[@class='colorinput']" ) ).sendKeys( "#3d558c" );
    this.elemHelper.Click( this.driver, By.xpath( "//form[@class='cdfddInput']/input[@class='colorinput']" ) );

    /*
     * ## Step 3
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='colorpicker']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='colorpicker']/div[@class='colorpicker_hex']" ) );
    assertNotNull( element );
    String hexText = this.elemHelper.GetInputValue( this.driver, By.xpath( "//div[@class='colorpicker']/div[@class='colorpicker_hex']/input" ) );
    assertEquals( "3d558c", hexText );

  }

}
