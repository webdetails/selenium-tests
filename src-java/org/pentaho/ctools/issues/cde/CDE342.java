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
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-342
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-942
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDE342 {
  // Instance of the driver (browser emulator)
  private final WebDriver driver = CToolsTestSuite.getDriver();
  // The base url to be append the relative url in test
  private final String baseUrl = CToolsTestSuite.getBaseUrl();
  //Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDE342.class );
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( this.driver );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert autocomplete works properly
   *
   * Description:
   *    The test pretends validate the CDE-342 issue, so when user writes on the autocomplete input field, the options
   *    available update accordingly.
   *
   * Steps:
   *    1. Write on input field and check options available
   *    2. Write something where no options should be available
   *    3. Write something where only one option should be available
   */
  public void tc1_CDEDashboard_AutocompleteWorks() {
    this.log.info( "tc1_CDEDashboard_AutocompleteWorks" );

    /*
     * ## Step 1
     */
    //Go to Issue sample
    this.driver.get( this.baseUrl + "api/repos/%3Apublic%3AIssues%3ACDE%3ACDE-342%3Atest_simple_ac.wcdf/generatedContent" );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    //Wait for Input field
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "col1" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//input[@class='ui-autocomplete-input']" ) );
    assertNotNull( element );
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='col1']/input" ) ).sendKeys( "A" );
    String option1 = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//ul[@id='ui-id-1']/li/a" ) );
    String option2 = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//ul[@id='ui-id-1']/li[2]/a" ) );
    String option3 = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//ul[@id='ui-id-1']/li[3]/a" ) );
    String option4 = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//ul[@id='ui-id-1']/li[4]/a" ) );
    String option5 = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//ul[@id='ui-id-1']/li[5]/a" ) );
    assertEquals( "Anna's Decorations, Ltd", option1 );
    assertEquals( "Australian Collectors, Co.", option2 );
    assertEquals( "Souveniers And Things Co.", option3 );
    assertEquals( "Australian Gift Network, Co", option4 );
    assertEquals( "Australian Collectables, Ltd", option5 );

    /*
     * ## Step 2
     */
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='col1']/input" ) ).clear();
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='col1']/input" ) ).sendKeys( "ert" );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//ul[@id='ui-id-1']/li/a" ) );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//ul[@id='ui-id-1']/li/a" ) );

    /*
     * ## Step 3
     */
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='col1']/input" ) ).clear();
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='col1']/input" ) ).sendKeys( "Anna" );
    option2 = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//ul[@id='ui-id-1']/li/a" ) );
    assertEquals( "Anna's Decorations, Ltd", option2 );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//ul[@id='ui-id-1']/li[2]/a" ) );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//ul[@id='ui-id-1']/li[2]/a" ) );

  }
}
