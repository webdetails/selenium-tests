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
package org.pentaho.ctools.cdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.PageUrl;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * Testing the functionalities related with DateInputComponent.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class DateInputComponent {

  // Instance of the driver (browser emulator)
  private static WebDriver DRIVER;
  // Instance to be used on wait commands
  private static Wait<WebDriver> WAIT;
  //Access to wrapper for webdriver
  private ElementHelper elemHelper = new ElementHelper();
  //Log instance
  private static Logger LOG = LogManager.getLogger( PrptComponent.class );

  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( DRIVER );

  /**
   * Shall initialized the test before run each test case.
   */
  @BeforeClass
  public static void setUp() {
    LOG.info( "setUp##" + DateInputComponent.class.getSimpleName() );
    DRIVER = CToolsTestSuite.getDriver();
    WAIT = CToolsTestSuite.getWait();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    DateInputComponent
   * Description:
   *    We pretend to check the component when user pick a data an alert message
   *    is displayed indicating the date picked.
   * Steps:
   *    1. Go to Pentaho solution web page.
   *    2. Render the component again.
   *    3. Choose the date '2011-10-23'.
   */
  @ Test
  public void tc1_DataInput_DisplayPopupWithPickedDate() {
    /*
     * ## Step 1
     */
    DRIVER.get( PageUrl.DATEINPUT_COMPONENT );

    //NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Wait for visibility of 'DateInputComponent'
    this.elemHelper.WaitForElementVisibility( DRIVER, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) );
    // Validate the sample that we are testing is the one
    assertEquals( "Community Dashboard Framework", DRIVER.getTitle() );
    assertEquals( "DateInputComponent", this.elemHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );

    /*
     * ## Step 2
     */
    //Render again the sample
    this.elemHelper.ClickJS( DRIVER, By.xpath( "//div[@id='example']/ul/li[2]/a" ) );
    this.elemHelper.ClickJS( DRIVER, By.xpath( "//div[@id='code']/button" ) );
    //NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    //Now sample element must be displayed
    assertTrue( this.elemHelper.FindElement( DRIVER, By.id( "sample" ) ).isDisplayed() );

    /*
     * ## Step 3
     */
    //Pick a date
    this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.id( "myInput" ) );
    this.elemHelper.FindElement( DRIVER, By.id( "myInput" ) ).sendKeys( "''" );

    this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.id( "ui-datepicker-div" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.className( "ui-datepicker-month" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.className( "ui-datepicker-year" ) );
    Select month = new Select( this.elemHelper.FindElement( DRIVER, By.className( "ui-datepicker-month" ) ) );
    month.selectByValue( "9" );
    Select year = new Select( this.elemHelper.FindElement( DRIVER, By.className( "ui-datepicker-year" ) ) );
    year.selectByValue( "2011" );
    //Day 23
    this.elemHelper.FindElement( DRIVER, By.xpath( "//table[@class='ui-datepicker-calendar']//tbody//tr[5]/td/a" ) ).sendKeys( Keys.ENTER );

    WAIT.until( ExpectedConditions.alertIsPresent() );
    Alert alert = DRIVER.switchTo().alert();
    String confirmationMsg = alert.getText();
    alert.accept();

    /*##########################################################################
      EXPECTED RESULT:
      - The popup alert shall displayed the data picked.
     #########################################################################*/
    assertEquals( confirmationMsg, "You chose: 2011-10-23" );
  }

  @AfterClass
  public static void tearDown() {
    LOG.info( "tearDown##" + DateInputComponent.class.getSimpleName() );
  }

}
