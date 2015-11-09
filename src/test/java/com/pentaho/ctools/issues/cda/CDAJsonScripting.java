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
package com.pentaho.ctools.issues.cda;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.selenium.BaseTest;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDA-127
 * - http://jira.pentaho.com/browse/CDA-130
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1123
 * - http://jira.pentaho.com/browse/QUALITY-1124
 *
 * NOTE
 * To test this script it is required to have CDA plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDAJsonScripting extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDAJsonScripting.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Json Scripting  datasource testing
   * Description:
   *    CDA-127 - How Json Scripting datasource handles invalid numbers (0/0)
   *    CDA-130 - Asserting that Json Scripting datasource accepts null values.
   *    CDA-160 - Invalid values should return null instead "LibFormulaErrorValue{errorCode=503, errorMessage=Arithmetic Error}"
   *       
   * Steps:
   *    1. Open sample and select "Sample query on SteelWheelsSales" on the datasources
   *    2. Wait for and assert elements and text on page
   *    
   */
  @Test
  public void tc1_CdaFileViewer_JsonScriptingNull() {
    this.log.info( "tc1_CdaFileViewer_JsonScriptingNull" );

    /*
     * ## Step 1
     */
    //Open sample CDA file
    driver.get( baseUrl + "plugin/cda/api/previewQuery?path=/public/Issues/CDA/CDA-130/json-scripting.cda" );

    //wait for invisibility of waiting pop-up
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

    //Wait for buttons: button, Cache This AND Query URL
    WebElement selector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "dataAccessSelector" ) );
    assertNotNull( selector );
    Select select = new Select( this.elemHelper.FindElement( driver, By.id( "dataAccessSelector" ) ) );
    select.selectByValue( "1" );
    this.elemHelper.FindElement( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    WebElement refreshButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='button']" ) );
    assertNotNull( refreshButton );
    WebElement cacheButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='cachethis']" ) );
    assertNotNull( cacheButton );
    WebElement urlButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='queryUrl']" ) );
    assertNotNull( urlButton );

    /*
     * ## Step 2
     */
    //wait to render page
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Check text on table
    String firstColumn = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='contents']/tbody/tr[3]/td" ) );
    String secondColumn = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='contents']/tbody/tr[4]/td[2]" ) );
    String thirdColumn = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='contents']/tbody/tr/td[3]" ) );
    String fourthColumn = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='contents']/tbody/tr[3]/td[4]" ) );
    String fifthColumn = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='contents']/tbody/tr[2]/td[5]" ) );
    String sixthColumn = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='contents']/tbody/tr/td[6]" ) );
    assertEquals( firstColumn, "South" );
    assertEquals( secondColumn, "14" );
    assertEquals( thirdColumn, "null" );
    assertEquals( fourthColumn, "null" );
    assertEquals( fifthColumn, "null" );
    assertEquals( sixthColumn, "null" );

  }
}
