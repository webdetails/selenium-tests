/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2016 by Pentaho : http://www.pentaho.com
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
import org.openqa.selenium.WebElement;
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
public class LogoutPentaho extends BaseTest {
  // Instance of the driver (browser emulator)
  //
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  //Log instance
  private final Logger log = LogManager.getLogger( LogoutPentaho.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Log out
   * Description:
   *    With an administrator user, we check if user can log out from the system.
   * Steps:
   *    1. Go to Pentaho solution web page.
   *    2. Press Log Out.
   *    3. The user is logged out and is redirect to home page (login page).
   */
  @Test
  public void tc1_Logout_SuccessLogOutReturnHomePage() {
    this.log.info( "tc1_Logout_SuccessLogOutReturnHomePage" );

    //## Step 1
    this.elemHelper.Get( driver, PageUrl.PUC );

    //waiting pop-up to be visible
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

    //## Step 2
    //wait for frame to load
    String title = this.elemHelper.WaitForTitle( driver, "Pentaho User Console" );
    assertEquals( "Pentaho User Console", title );

    WebElement logoutMenu = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='pucUserDropDown']/table/tbody/tr/td/div" ) );
    WebElement homePerpective = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//iframe[@id='home.perspective']" ) );
    assertNotNull( logoutMenu );
    assertNotNull( homePerpective );

    //User drop down available
    String userName = this.elemHelper.WaitForTextPresence( driver, By.xpath( "//div[@id='pucUserDropDown']/table/tbody/tr/td/div" ), "admin" );
    assertEquals( "admin", userName );
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='pucUserDropDown']/table/tbody/tr/td/div" ) );

    //Logout option available
    WebElement logoutElement = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='customDropdownPopupMinor']/div/div/table/tbody/tr/td" ) );
    assertNotNull( logoutElement );
    String logoutText = this.elemHelper.WaitForTextPresence( driver, By.xpath( "//div[@id='customDropdownPopupMinor']/div/div/table/tbody/tr/td" ), "Log Out" );
    assertEquals( "Log Out", logoutText );
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='customDropdownPopupMinor']/div/div/table/tbody/tr/td" ) );

    //## Step 3
    //Wait for form display (login form)
    WebElement elForm = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='login-form-container']/div/h1" ) );
    assertNotNull( elForm );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_username" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_password" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "button.btn" ) );
  }
}
