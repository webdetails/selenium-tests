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
package org.pentaho.ctools.main;

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
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.PageUrl;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * Testing the functionalities related with Login.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class LoginPentaho {
  // Instance of the driver (browser emulator)
  private final WebDriver driver = CToolsTestSuite.getDriver();
  //Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  //Log instance
  private final Logger log = LogManager.getLogger( LoginPentaho.class );

  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( this.driver );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Authentication
   * Description:
   *    With an administrator user, we check if user can authenticate in the
   *    system.
   * Steps:
   *    1. Go to Pentaho solution web page.
   *    2. Enter user and password.
   *    3. User authenticated, and user name of logged user is displayed.
   */
  @Test( timeout = 60000 )
  public void tc1_Login_SuccessAuthentication() {
    this.log.debug( "tc1_Login_SuccessAuthentication" );
    //## Step 1
    this.driver.get( PageUrl.PUC_LOGIN );

    //Wait for form display
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='login-form-container']/div/h1" ) );
    assertEquals( "User Console", this.driver.findElement( By.xpath( "//div[@id='login-form-container']/div/h1" ) ).getText() );

    //## Step 2
    //Wait for all all elements in the form to be visible
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "j_username" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "j_password" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.cssSelector( "button.btn" ) );
    this.driver.findElement( By.id( "j_username" ) ).clear();
    this.driver.findElement( By.id( "j_username" ) ).sendKeys( "admin" );
    this.driver.findElement( By.id( "j_password" ) ).clear();
    this.driver.findElement( By.id( "j_password" ) ).sendKeys( "password" );
    this.driver.findElement( By.cssSelector( "button.btn" ) ).click();

    //## Step 3
    //wait for visibility of waiting pop-up
    this.elemHelper.WaitForElementPresence( this.driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ), 4 );
    this.elemHelper.WaitForElementNotPresent( this.driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

    //Wait to load the new page
    this.elemHelper.WaitForTitle( this.driver, "Pentaho User Console" );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='pucUserDropDown']/table/tbody/tr/td/div" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//iframe[@id='home.perspective']" ) );
    assertNotNull( this.driver.findElement( By.xpath( "//iframe[@id='home.perspective']" ) ) );
    assertEquals( "Pentaho User Console", this.driver.getTitle() );

    //Logged as ADMIN user
    assertEquals( "admin", this.driver.findElement( By.xpath( "//div[@id='pucUserDropDown']/table/tbody/tr/td/div" ) ).getText() );
  }
}
