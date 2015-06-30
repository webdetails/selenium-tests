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
package org.pentaho.ctools.issues.cde;

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
import org.openqa.selenium.WebElement;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-439
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-998
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDE439 {
  // Instance of the driver (browser emulator)
  private final WebDriver driver = CToolsTestSuite.getDriver();
  // The base url to be append the relative url in test
  private final String baseUrl = CToolsTestSuite.getBaseUrl();
  //Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDE439.class );
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( this.driver );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert Duplicate Component Sample works properly
   *
   * Description:
   *    The test pretends validate the CDE-439 issue, so Duplicate Component Sample works correctly.
   *
   * Steps:
   *    1. Open Duplicate Component sample and assert elements on page
   *    2. Assert elements on page and click first "Duplicate" link
   *    3. Assert elements are duplicated correctly and click second "Duplicate" link
   *    4. Assert elements are duplicated correctly
   */
  @Test( timeout = 120000 )
  public void tc01_CDEDashboard_DuplicateComponentWorks() {
    this.log.info( "tc01_CDEDashboard_DuplicateComponentWorks" );

    /*
     * ## Step 1
     */
    //Open CGG Component sample
    this.driver.get( this.baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3ADuplicateComponent%3AduplicateComponent.wcdf/generatedContent" );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Get Title of sample
    String title = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='content']/div/div[2]/div" ) );
    assertEquals( "Duplicate Component sample", title );

    //Assert presence of elements
    WebElement userLabel = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='targetHTML']/div/div/div/div/div" ) );
    WebElement userText = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='targetHTML']/div/div/div/div/div[2]/input" ) );
    WebElement userButton = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='targetHTML']/div/div/div/div/div[3]/button" ) );
    WebElement passLabel = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='targetHTML']/div/div/div/div[2]/div" ) );
    WebElement passText = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='targetHTML']/div/div/div/div[2]/div[2]/input" ) );
    WebElement passButton = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='targetHTML']/div/div/div/div[2]/div[3]/button" ) );
    assertNotNull( userLabel );
    assertNotNull( userText );
    assertNotNull( userButton );
    assertNotNull( passLabel );
    assertNotNull( passText );
    assertNotNull( passButton );

    /*
     * ## Step 2
     */
    //Duplicate elements to Target HTML (default)
    WebElement firstDuplicate = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='dup1Col']/a" ) );
    assertNotNull( firstDuplicate );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@id='dup1Col']/a" ) );

    //Assert presence of elements
    WebElement userLabel1 = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='targetHTML_1']/div/div/div/div/div" ) );
    WebElement userText1 = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='targetHTML_1']/div/div/div/div/div[2]/input" ) );
    WebElement userButton1 = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='targetHTML_1']/div/div/div/div/div[3]/button" ) );
    WebElement passLabel1 = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='targetHTML_1']/div/div/div/div[2]/div" ) );
    WebElement passText1 = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='targetHTML_1']/div/div/div/div[2]/div[2]/input" ) );
    WebElement passButton1 = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='targetHTML_1']/div/div/div/div[2]/div[3]/button" ) );
    assertNotNull( userLabel1 );
    assertNotNull( userText1 );
    assertNotNull( userButton1 );
    assertNotNull( passLabel1 );
    assertNotNull( passText1 );
    assertNotNull( passButton1 );

    /*
     * ## Step 3
     */
    //Duplicate elements to Target HTML (default)
    WebElement secondDuplicate = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='dup1Col']/a" ) );
    assertNotNull( secondDuplicate );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@id='dup2Col']/a" ) );

    //Assert presence of elements
    WebElement userLabel2 = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='dupContainer']/div[@id='targetHTML_2']/div/div/div/div/div" ) );
    WebElement userText2 = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='dupContainer']/div[@id='targetHTML_2']/div/div/div/div/div[2]/input" ) );
    WebElement userButton2 = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='dupContainer']/div[@id='targetHTML_2']/div/div/div/div/div[3]/button" ) );
    WebElement passLabel2 = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='dupContainer']/div[@id='targetHTML_2']/div/div/div/div[2]/div" ) );
    WebElement passText2 = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='dupContainer']/div[@id='targetHTML_2']/div/div/div/div[2]/div[2]/input" ) );
    WebElement passButton2 = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='dupContainer']/div[@id='targetHTML_2']/div/div/div/div[2]/div[3]/button" ) );
    assertNotNull( userLabel2 );
    assertNotNull( userText2 );
    assertNotNull( userButton2 );
    assertNotNull( passLabel2 );
    assertNotNull( passText2 );
    assertNotNull( passButton2 );

  }

}
