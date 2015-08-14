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
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.pentaho.ctools.suite.CToolsTestSuite;
import com.pentaho.ctools.utils.BaseTest;
import com.pentaho.ctools.utils.ElementHelper;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-446
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1022
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDE446 extends BaseTest {
  // The base url to be append the relative url in test
  private final String baseUrl = CToolsTestSuite.getBaseUrl();
  //Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDE446.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Trying to access Kettle file doesn't add anything to saved path
   *
   * Description:
   *    The test pretends validate the CDE-446 issue, so when user accesses a kettle file for a datasource the path
   *    selected isn't altered.
   *
   * Steps:
   *    1. Open created dashboard's cda file
   *    2. Select "DataAccess ID: kettleTransform" on the data selector
   *    3. Assert data shown is expected
   *
   */
  @Test
  public void tc1_CdeDashboard_KettleDatasourceWorks() {
    this.log.info( "tc1_CdeDashboard_KettleDatasourceWorks" );

    /*
     * ## Step 1
     */
    //Open created dashboard's cda file
    this.driver.get( this.baseUrl + "plugin/cda/api/previewQuery?path=/public/Issues/CDE/CDE-446/CDE-446.cda" );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "dataAccessSelector" ) );
    assertNotNull( element );
    Select select = new Select( this.elemHelper.FindElement( this.driver, By.id( "dataAccessSelector" ) ) );
    select.selectByVisibleText( "DataAccess ID: kettleTransform" );

    //Wait for buttons: button, Cache This AND Query URL
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//button[@id='button']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//button[@id='cachethis']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//button[@id='queryUrl']" ) );
    assertNotNull( element );

    /*
     * ## Step 2
     */
    //Check text on table
    String text = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='contents']/tbody/tr/td[3]" ) );
    assertEquals( "District Manager", text );
    text = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='contents']/tbody/tr[2]/td[3]" ) );
    assertEquals( "Senior Sales Rep", text );
    text = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='contents']/tbody/tr[3]/td[3]" ) );
    assertEquals( "Sales Rep", text );
    text = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='contents']/tbody/tr[4]/td[3]" ) );
    assertEquals( "Account Executive", text );
  }
}
