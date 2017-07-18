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
package com.pentaho.gui.web.puc;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;

public class LoginPage {

  // The driver
  private WebDriver driver;
  // Access to wrapper for webdriver
  private ElementHelper elemHelper = new ElementHelper();
  // Logging instance
  private final Logger LOG = LogManager.getLogger( LoginPage.class );
  // The title of the page Login
  private final String page_title_login = "Pentaho User Console - Login";
  // The title of the page Login
  private final String page_title_puc = "Pentaho User Console";

  public LoginPage( WebDriver driver ) {
    this.driver = driver;
    GoToLoginPage();
  }

  /**
   * This method will navigate to the Browse Files page
   *
   */
  private void GoToLoginPage() {
    LOG.info( "Enter: Login" );
    this.elemHelper.Get( this.driver, PageUrl.PUC_LOGIN );
    //Wait for form display
    WebElement formLogin = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "login" ) );
    assertNotNull( formLogin );
    // Check the page title
    String actualTitle = this.elemHelper.WaitForTitle( driver, page_title_login );
    assertEquals( actualTitle, page_title_login );
  }

  /**
   * This method will login to PUC using provided user and password to login
   *
   * @param user
   * @param pass
   *
   */
  public void Login( String user, String pass ) {
    //Wait for elements present and log in using provided credentials
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "j_username" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "j_password" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.cssSelector( "button.btn" ) );
    this.elemHelper.ClearAndSendKeys( driver, By.id( "j_username" ), user );
    this.elemHelper.ClearAndSendKeys( driver, By.id( "j_password" ), pass );
    this.elemHelper.Click( driver, By.cssSelector( "button.btn" ) );

    //wait for visibility of waiting pop-up
    this.elemHelper.WaitForElementPresence( this.driver, By.cssSelector( "div.pentaho-busy-indicator-title.waitPopup_title" ), 120 );
    this.elemHelper.WaitForElementNotPresent( this.driver, By.cssSelector( "div.pentaho-busy-indicator-title.waitPopup_title" ) );

    //Wait to load the new page
    String actualTitlePage = this.elemHelper.WaitForTitle( this.driver, page_title_puc );
    assertEquals( actualTitlePage, page_title_puc, actualTitlePage );
    WebElement dropdownLoggedUser = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.cssSelector( "#pucUserDropDown > table > tbody > tr > td:nth-child(1) > div" ), 120 );
    assertNotNull( dropdownLoggedUser );
    WebElement frameHomePerpective = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "home.perspective" ) );
    assertNotNull( frameHomePerpective );

    //Logged as ADMIN user
    String actualUser = this.elemHelper.WaitForTextPresence( this.driver, By.cssSelector( "#pucUserDropDown > table > tbody > tr > td:nth-child(1) > div" ), user );
    assertEquals( actualUser, user );
  }
}
