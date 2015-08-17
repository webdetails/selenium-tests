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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.BaseTest;
import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-452
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1023
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDE452 extends BaseTest {
  //Failing Variable  1- Logged in as Admin; 2- Logged in as other user; 3- Logged out
  private int failure = 1;
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDE452.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Access denied when unauthorized user tries to access CDE dashboard in edit mode
   *
   * Description:
   *    The test pretends validate the CDE-452 issue, so when unauthorized user tries to access dashboards in edit
   *    mode he gets "Access Denied".
   *
   * Steps:
   *    1. Open system dashboard and repository dashboard in edit mode
   *    2. Log out and log in with Suzy, repeat step 1 and assert it gives "Access Denied"
   *    3. Log out and log in with Pat, repeat step 1 and assert it gives "Access Denied"
   *    4. Log out and log in with Tiffany, repeat step 1 and assert it gives "Access Denied"
   */
  @Test
  public void tc1_CdeDashboard_AccessDeniedEdit() {
    this.log.info( "tc1_CdeDashboard_AccessDeniedEdit" );

    /*
     * ## Step 1
     */
    //Open system dashboard in edit mode and assert elements on page
    driver.get( baseUrl + "plugin/pentaho-cdf-dd/api/renderer/edit?absolute=false&inferScheme=false&file=i18nTest.wcdf&path=%2FCDE404%2Fdashboards%2F&solution=system&mode=edit" );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='datasourcesPanelButton']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "previewButton" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='layoutPanelButton']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='componentsPanelButton']" ) );
    assertNotNull( element );

    //Open repository dashboard in edit mode and assert elements on page
    driver.get( baseUrl + "api/repos/%3Apublic%3AIssues%3ACDF%3ACDF-430%3ACDE%3Ai18nTest.wcdf/edit" );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='datasourcesPanelButton']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "previewButton" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='layoutPanelButton']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='componentsPanelButton']" ) );
    assertNotNull( element );

    /*
     * ## Step 2
     */
    //Log out
    driver.get( PageUrl.PUC );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='pucUserDropDown']/table/tbody/tr/td[2]" ) );
    assertNotNull( element );
    element.click();
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='customDropdownPopupMinor']/div/div/table/tbody/tr/td" ) );
    assertNotNull( element );
    String text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='customDropdownPopupMinor']/div/div/table/tbody/tr/td" ) );
    assertEquals( "Log Out", text );
    element.click();

    //Logged out
    this.failure = 3;

    //Wait for form display
    text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='login-form-container']/div/h1" ) );
    assertEquals( "User Console", text );

    //Wait for all all elements in the form to be visible
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_username" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_password" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "button.btn" ) );
    assertNotNull( element );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_username" ) ).clear();
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_username" ) ).sendKeys( "suzy" );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_password" ) ).clear();
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_password" ) ).sendKeys( "password" );
    this.elemHelper.Click( driver, By.cssSelector( "button.btn" ) );

    //Logged in  as other user
    this.failure = 2;

    //wait for visibility of waiting pop-up
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

    //Wait to load the new page
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "pucUserDropDown" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "home.perspective" ) );
    assertNotNull( element );

    //Logged as ADMIN user
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//div[@id='pucUserDropDown']/table/tbody/tr/td/div" ), "suzy" );
    text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='pucUserDropDown']/table/tbody/tr/td/div" ) );
    assertEquals( "suzy", text );

    //Open system dashboard in edit mode and assert elements on page
    driver.get( baseUrl + "plugin/pentaho-cdf-dd/api/renderer/edit?absolute=false&inferScheme=false&file=i18nTest.wcdf&path=%2FCDE404%2Fdashboards%2F&solution=system&mode=edit" );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//body" ) );
    assertEquals( "Access Denied to file /system/CDE404/dashboards/i18nTest.wcdf", text );

    //Open repository dashboard in edit mode and assert elements on page
    driver.get( baseUrl + "api/repos/%3Apublic%3AIssues%3ACDF%3ACDF-430%3ACDE%3Ai18nTest.wcdf/edit" );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//body" ) );
    assertEquals( "Access Denied to file /public/Issues/CDF/CDF-430/CDE/i18nTest.wcdf", text );

    /*
     * ## Step 3
     */
    //Log out
    driver.get( PageUrl.PUC );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='pucUserDropDown']/table/tbody/tr/td[2]" ) );
    assertNotNull( element );
    element.click();
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='customDropdownPopupMinor']/div/div/table/tbody/tr/td" ) );
    assertNotNull( element );
    text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='customDropdownPopupMinor']/div/div/table/tbody/tr/td" ) );
    assertEquals( "Log Out", text );
    element.click();

    //Logged out
    this.failure = 3;

    //Wait for form display
    text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='login-form-container']/div/h1" ) );
    assertEquals( "User Console", text );

    //Wait for all all elements in the form to be visible
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_username" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_password" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "button.btn" ) );
    assertNotNull( element );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_username" ) ).clear();
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_username" ) ).sendKeys( "pat" );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_password" ) ).clear();
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_password" ) ).sendKeys( "password" );
    this.elemHelper.Click( driver, By.cssSelector( "button.btn" ) );

    //Logged in  as other user
    this.failure = 2;

    //wait for visibility of waiting pop-up
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

    //Wait to load the new page
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "pucUserDropDown" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "home.perspective" ) );
    assertNotNull( element );

    //Logged as ADMIN user
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//div[@id='pucUserDropDown']/table/tbody/tr/td/div" ), "pat" );
    text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='pucUserDropDown']/table/tbody/tr/td/div" ) );
    assertEquals( "pat", text );

    //Open system dashboard in edit mode and assert elements on page
    driver.get( baseUrl + "plugin/pentaho-cdf-dd/api/renderer/edit?absolute=false&inferScheme=false&file=i18nTest.wcdf&path=%2FCDE404%2Fdashboards%2F&solution=system&mode=edit" );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//body" ) );
    assertEquals( "Access Denied to file /system/CDE404/dashboards/i18nTest.wcdf", text );

    //Open repository dashboard in edit mode and assert elements on page
    driver.get( baseUrl + "api/repos/%3Apublic%3AIssues%3ACDF%3ACDF-430%3ACDE%3Ai18nTest.wcdf/edit" );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//body" ) );
    assertEquals( "Access Denied to file /public/Issues/CDF/CDF-430/CDE/i18nTest.wcdf", text );

    /*
     * ## Step 4
     */
    //Log out
    driver.get( PageUrl.PUC );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='pucUserDropDown']/table/tbody/tr/td[2]" ) );
    assertNotNull( element );
    element.click();
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='customDropdownPopupMinor']/div/div/table/tbody/tr/td" ) );
    assertNotNull( element );
    text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='customDropdownPopupMinor']/div/div/table/tbody/tr/td" ) );
    assertEquals( "Log Out", text );
    element.click();

    //Logged out
    this.failure = 3;

    //Wait for form display
    text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='login-form-container']/div/h1" ) );
    assertEquals( "User Console", text );

    //Wait for all all elements in the form to be visible
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_username" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_password" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "button.btn" ) );
    assertNotNull( element );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_username" ) ).clear();
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_username" ) ).sendKeys( "tiffany" );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_password" ) ).clear();
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_password" ) ).sendKeys( "password" );
    this.elemHelper.Click( driver, By.cssSelector( "button.btn" ) );

    //Logged in  as other user
    this.failure = 2;

    //wait for visibility of waiting pop-up
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

    //Wait to load the new page
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "pucUserDropDown" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "home.perspective" ) );
    assertNotNull( element );

    //Logged as ADMIN user
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//div[@id='pucUserDropDown']/table/tbody/tr/td/div" ), "tiffany" );
    text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='pucUserDropDown']/table/tbody/tr/td/div" ) );
    assertEquals( "tiffany", text );

    //Open system dashboard in edit mode and assert elements on page
    driver.get( baseUrl + "plugin/pentaho-cdf-dd/api/renderer/edit?absolute=false&inferScheme=false&file=i18nTest.wcdf&path=%2FCDE404%2Fdashboards%2F&solution=system&mode=edit" );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//body" ) );
    assertEquals( "Access Denied to file /system/CDE404/dashboards/i18nTest.wcdf", text );

    //Open repository dashboard in edit mode and assert elements on page
    driver.get( baseUrl + "api/repos/%3Apublic%3AIssues%3ACDF%3ACDF-430%3ACDE%3Ai18nTest.wcdf/edit" );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//body" ) );
    assertEquals( "Access Denied to file /public/Issues/CDF/CDF-430/CDE/i18nTest.wcdf", text );

    //Log out
    driver.get( PageUrl.PUC );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='pucUserDropDown']/table/tbody/tr/td[2]" ) );
    assertNotNull( element );
    element.click();
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='customDropdownPopupMinor']/div/div/table/tbody/tr/td" ) );
    assertNotNull( element );
    text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='customDropdownPopupMinor']/div/div/table/tbody/tr/td" ) );
    assertEquals( "Log Out", text );
    element.click();

    //Logged out
    this.failure = 3;

    //Wait for form display
    text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='login-form-container']/div/h1" ) );
    assertEquals( "User Console", text );

    //Wait for all all elements in the form to be visible
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_username" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_password" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "button.btn" ) );
    assertNotNull( element );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_username" ) ).clear();
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_username" ) ).sendKeys( "admin" );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_password" ) ).clear();
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_password" ) ).sendKeys( "password" );
    this.elemHelper.Click( driver, By.cssSelector( "button.btn" ) );

    //Logged in as Admin
    this.failure = 1;

    //wait for visibility of waiting pop-up
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

    //Wait to load the new page
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "pucUserDropDown" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "home.perspective" ) );
    assertNotNull( element );

    //Logged as ADMIN user
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//div[@id='pucUserDropDown']/table/tbody/tr/td/div" ), "admin" );
    text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='pucUserDropDown']/table/tbody/tr/td/div" ) );
    assertEquals( "admin", text );
  }

  //Function for logging in as admin in case of failure
  private void failed() {
    if ( this.failure == 2 ) {
      //Log out
      driver.get( PageUrl.PUC );
      this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );
      WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='pucUserDropDown']/table/tbody/tr/td[2]" ) );
      assertNotNull( element );
      element.click();
      element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='customDropdownPopupMinor']/div/div/table/tbody/tr/td" ) );
      assertNotNull( element );
      String text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='customDropdownPopupMinor']/div/div/table/tbody/tr/td" ) );
      assertEquals( "Log Out", text );
      element.click();

      //Wait for form display
      text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='login-form-container']/div/h1" ) );
      assertEquals( "User Console", text );

      //Wait for all all elements in the form to be visible
      element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_username" ) );
      assertNotNull( element );
      element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_password" ) );
      assertNotNull( element );
      element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "button.btn" ) );
      assertNotNull( element );
      this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_username" ) ).clear();
      this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_username" ) ).sendKeys( "admin" );
      this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_password" ) ).clear();
      this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_password" ) ).sendKeys( "password" );
      this.elemHelper.Click( driver, By.cssSelector( "button.btn" ) );

      //wait for visibility of waiting pop-up
      this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

      //Wait to load the new page
      element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "pucUserDropDown" ) );
      assertNotNull( element );
      element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "home.perspective" ) );
      assertNotNull( element );

      //Logged as ADMIN user
      this.elemHelper.WaitForTextPresence( driver, By.xpath( "//div[@id='pucUserDropDown']/table/tbody/tr/td/div" ), "admin" );
      text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='pucUserDropDown']/table/tbody/tr/td/div" ) );
      assertEquals( "admin", text );
    } else if ( this.failure == 3 ) {
      //Wait for all all elements in the form to be visible
      WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_username" ) );
      assertNotNull( element );
      element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_password" ) );
      assertNotNull( element );
      element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "button.btn" ) );
      assertNotNull( element );
      this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_username" ) ).clear();
      this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_username" ) ).sendKeys( "admin" );
      this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_password" ) ).clear();
      this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "j_password" ) ).sendKeys( "password" );
      this.elemHelper.Click( driver, By.cssSelector( "button.btn" ) );

      //wait for visibility of waiting pop-up
      this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

      //Wait to load the new page
      element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "pucUserDropDown" ) );
      assertNotNull( element );
      element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "home.perspective" ) );
      assertNotNull( element );

      //Logged as ADMIN user
      this.elemHelper.WaitForTextPresence( driver, By.xpath( "//div[@id='pucUserDropDown']/table/tbody/tr/td/div" ), "admin" );
      String text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='pucUserDropDown']/table/tbody/tr/td/div" ) );
      assertEquals( "admin", text );
    }
  }

  @AfterClass( alwaysRun = true )
  public void tearDownClass() {
    this.log.info( "tearDownClass" );
    failed();
  }
}
