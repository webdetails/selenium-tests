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

import com.pentaho.ctools.utils.BaseTest;
import com.pentaho.ctools.utils.ElementHelper;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDA-45
 * - http://jira.pentaho.com/browse/CDA-46
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-946
 * - http://jira.pentaho.com/browse/QUALITY-944
 *
 * NOTE
 * To test this script it is required to have CDA plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDAHelpButtons extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDAHelpButtons.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Asserting info button that appears in input fields has display property set as block
   * Description:
   *    The test pretends validate the CDA-45 issue, no matter how long the string the user inputs on the input fields the
   *    text never goes behind the "?".
   *
   * Steps:
   *    1. Select Data Access "SQL Query on SampleData - Jdbc"
   *    2. Check popup for help on Output Index
   *    3. Click number beside Data Access and check info button's style property for "display: block;"
   *    4. Click "Status:" input field and check info button's style property for "display: block;"
   *    5. Click "orderDate:" input field and check info button's style property for "display: block;"
   *
   */
  @Test
  public void tc1_CdaFileViewer_OutputInfo() {
    this.log.info( "t01_CdaFileViewer_OutputInfo" );

    /*
     * ## Step 1
     */
    //Open Sql-Jdbc Sample
    driver.get( baseUrl + "plugin/cda/api/previewQuery?path=/public/plugin-samples/cda/cdafiles/sql-jdbc.cda" );

    //Wait for buttons: button, Cache This AND Query URL
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "dataAccessSelector" ) );
    assertNotNull( element );
    Select select = new Select( this.elemHelper.FindElement( driver, By.id( "dataAccessSelector" ) ) );
    select.selectByValue( "1" );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='button']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='cachethis']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='queryUrl']" ) );
    assertNotNull( element );

    //wait to render page
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    this.elemHelper.WaitForElementPresence( driver, By.id( "outputIndexId" ) );
    this.elemHelper.FindElement( driver, By.id( "outputIndexId" ) ).click();
    this.elemHelper.WaitForElementVisibility( driver, By.xpath( "//div[@class='helpButton helpButtonShort']" ) );
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@class='helpButton helpButtonShort']" ) );

    /*
     * ## Step 2
     */
    this.elemHelper.WaitForElementPresence( driver, By.xpath( "//div[@id='outputIndexHelp']" ) );
    this.elemHelper.WaitForElementPresence( driver, By.xpath( "//div[@id='outputIndexHelp']/p" ) );
    this.elemHelper.WaitForElementPresence( driver, By.xpath( "//div[@id='outputIndexHelp']/p[2]" ) );
    String p1Text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='outputIndexHelp']/p" ) );
    String p2Text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='outputIndexHelp']/p[2]" ) );
    assertEquals( "Output Index Id", p1Text );
    assertEquals( "This Id is used to select the desired set of Output Options for the current Data Access.", p2Text );
    WebElement closeButton = this.elemHelper.WaitForElementPresence( driver, By.xpath( "//div[@id='outputIndexHelp']//a[@class='jqmClose']" ) );
    assertNotNull( closeButton );
    this.elemHelper.WaitForElementPresence( driver, By.xpath( "//div[@id='outputIndexHelp']//a[@class='jqmClose']" ) ).click();

    /*
     * ## Step 3
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "status" ) );
    assertNotNull( element );
    element.click();
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='helpButton']" ) );
    assertNotNull( element );
    String styleText1 = this.elemHelper.FindElement( driver, By.id( "status" ) ).getAttribute( "class" );
    assertEquals( "cdaButton cdaButtonSelected", styleText1 );

    /*
     * ## Step 4
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "orderDate" ) );
    assertNotNull( element );
    element.click();
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='parameterHolder']/div[2]/div[2]/div" ) );
    assertNotNull( element );
    String styleText2 = this.elemHelper.FindElement( driver, By.id( "orderDate" ) ).getAttribute( "class" );
    assertEquals( "cdaButton cdaButtonSelected", styleText2 );

    /*
     * ## Step 5
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "outputIndexId" ) );
    assertNotNull( element );
    element.click();
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='helpButton helpButtonShort']" ) );
    String styleText = this.elemHelper.FindElement( driver, By.id( "outputIndexId" ) ).getAttribute( "class" );
    assertEquals( "cdaButton cdaButtonShort cdaButtonSelected", styleText );
  }
}
