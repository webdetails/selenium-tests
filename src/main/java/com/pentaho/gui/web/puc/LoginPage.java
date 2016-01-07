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
package com.pentaho.gui.web.puc;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;

public class LoginPage {

  // The driver
  private WebDriver driver;
  // Access to wrapper for webdriver
  private ElementHelper elemHelper = new ElementHelper();
  // Logging instance
  private static Logger LOG = LogManager.getLogger( LoginPage.class );

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
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='login-form-container']/div/h1" ) );
    assertEquals( this.driver.findElement( By.xpath( "//div[@id='login-form-container']/div/h1" ) ).getText(), "User Console" );

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
    this.driver.findElement( By.id( "j_username" ) ).clear();
    this.driver.findElement( By.id( "j_username" ) ).sendKeys( user );
    this.driver.findElement( By.id( "j_password" ) ).clear();
    this.driver.findElement( By.id( "j_password" ) ).sendKeys( pass );
    this.driver.findElement( By.cssSelector( "button.btn" ) ).click();

    //wait for visibility of waiting pop-up
    this.elemHelper.WaitForElementPresence( this.driver, By.cssSelector( "div.busy-indicator-container.waitPopup" ), 20 );
    this.elemHelper.WaitForElementNotPresent( this.driver, By.cssSelector( "div.busy-indicator-container.waitPopup" ) );

    //Wait to load the new page
    String expectedTitlePage = "Pentaho User Console";
    String actualTitlePage = this.elemHelper.WaitForTitle( this.driver, expectedTitlePage );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='pucUserDropDown']/table/tbody/tr/td/div" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//iframe[@id='home.perspective']" ) );
    assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//iframe[@id='home.perspective']" ) ) );
    assertEquals( actualTitlePage, "Pentaho User Console", expectedTitlePage );

    //Logged as ADMIN user
    String expectedUser = user;
    String actualUser = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//div[@id='pucUserDropDown']/table/tbody/tr/td/div" ), expectedUser );
    assertEquals( actualUser, expectedUser );
  }
}
