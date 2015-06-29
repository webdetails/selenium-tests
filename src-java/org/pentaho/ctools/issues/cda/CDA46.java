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
package org.pentaho.ctools.issues.cda;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDA-46
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-944
 *
 * NOTE
 * To test this script it is required to have CDA plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDA46 {
  // Instance of the driver (browser emulator)
  private WebDriver driver = CToolsTestSuite.getDriver();
  // The base url to be append the relative url in test
  private String baseUrl = CToolsTestSuite.getBaseUrl();
  //Access to wrapper for webdriver
  private ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private static Logger LOG = LogManager.getLogger( CDA46.class );
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( this.driver );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Asserting number beside "Data Access" has info button
   * Description:
   *    The test pretends validate the CDA-46 issue, the field beside "Data Access" has, when clicked, a info button
   *    that opens a popup with information regarding said field.
   *
   * Steps:
   *    1. Click number beside "Data Access"
   *    2. Wait for info button to appear and then click it
   *    3. Wait for popup and the assert text on it
   *
   */
  public void tc1_CdaFileViewer_OutputInfo() {
    LOG.info( "tc1_CdaFileViewer_OutputInfo" );

    /*
     * ## Step 1
     */
    //Open Sql-Jdbc Sample
    this.driver.get( this.baseUrl + "plugin/cda/api/previewQuery?path=/public/plugin-samples/cda/cdafiles/sql-jdbc.cda" );

    //Wait for buttons: button, Cache This AND Query URL
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "dataAccessSelector" ) );
    assertNotNull( element );
    Select select = new Select( this.elemHelper.FindElement( this.driver, By.id( "dataAccessSelector" ) ) );
    select.selectByValue( "1" );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//button[@id='button']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//button[@id='cachethis']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//button[@id='queryUrl']" ) );
    assertNotNull( element );

    this.elemHelper.WaitForElementPresence( this.driver, By.id( "outputIndexId" ) );
    this.elemHelper.FindElement( this.driver, By.id( "outputIndexId" ) ).click();
    this.elemHelper.WaitForElementVisibility( this.driver, By.xpath( "//div[@class='helpButton helpButtonShort']" ) );
    this.elemHelper.ClickJS( this.driver, By.xpath( "//div[@class='helpButton helpButtonShort']" ) );

    /*
     * ## Step 2
     */
    this.elemHelper.WaitForElementPresence( this.driver, By.xpath( "//div[@id='outputIndexHelp']" ) );
    this.elemHelper.WaitForElementPresence( this.driver, By.xpath( "//div[@id='outputIndexHelp']/p" ) );
    this.elemHelper.WaitForElementPresence( this.driver, By.xpath( "//div[@id='outputIndexHelp']/p[2]" ) );
    String p1Text = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='outputIndexHelp']/p" ) );
    String p2Text = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='outputIndexHelp']/p[2]" ) );
    assertEquals( "Output Index Id", p1Text );
    assertEquals( "This Id is used to select the desired set of Output Options for the current Data Access.", p2Text );
  }
}
