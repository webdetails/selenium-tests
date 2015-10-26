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
package com.pentaho.ctools.main;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with Login.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class LoginPentaho extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  //Log instance
  private final Logger log = LogManager.getLogger( LoginPentaho.class );

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
  @Test
  public void tc1_Login_SuccessAuthentication() {
    this.log.info( "tc1_Login_SuccessAuthentication" );

    /*
     * ## Step 1
     */
    driver.get( PageUrl.PUC_LOGIN );

    //Wait for form display
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='login-form-container']/div/h1" ) );
    assertEquals( driver.findElement( By.xpath( "//div[@id='login-form-container']/div/h1" ) ).getText(), "User Console" );

    //## Step 2
    //Wait for all all elements in the form to be visible
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_username" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_password" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "button.btn" ) );
    driver.findElement( By.id( "j_username" ) ).clear();
    driver.findElement( By.id( "j_username" ) ).sendKeys( "admin" );
    driver.findElement( By.id( "j_password" ) ).clear();
    driver.findElement( By.id( "j_password" ) ).sendKeys( "password" );
    driver.findElement( By.cssSelector( "button.btn" ) ).click();

    //## Step 3
    //wait for visibility of waiting pop-up
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.busy-indicator-container.waitPopup" ), 20 );
    this.elemHelper.WaitForElementNotPresent( driver, By.cssSelector( "div.busy-indicator-container.waitPopup" ) );

    //Wait to load the new page
    String expectedTitlePage = "Pentaho User Console";
    String actualTitlePage = this.elemHelper.WaitForTitle( driver, expectedTitlePage );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='pucUserDropDown']/table/tbody/tr/td/div" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//iframe[@id='home.perspective']" ) );
    assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//iframe[@id='home.perspective']" ) ) );
    assertEquals( actualTitlePage, "Pentaho User Console", expectedTitlePage );

    //Logged as ADMIN user
    String expectedUser = "admin";
    String actualUser = this.elemHelper.WaitForTextPresence( driver, By.xpath( "//div[@id='pucUserDropDown']/table/tbody/tr/td/div" ), expectedUser );
    assertEquals( actualUser, expectedUser );
  }
}
