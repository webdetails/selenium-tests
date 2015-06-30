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
package org.pentaho.ctools.issues.cdf;

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
 * - http://jira.pentaho.com/browse/CDF-379
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-989
 *
 * NOTE
 * To test this script it is required to have CDF plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDF379 {
  // Instance of the driver (browser emulator)
  private final WebDriver driver = CToolsTestSuite.getDriver();
  // The base url to be append the relative url in test
  private final String baseUrl = CToolsTestSuite.getBaseUrl();
  //Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDF379.class );
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( this.driver );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert CCC chart renders properly
   *
   * Description:
   *    The test pretends validate the CDF-379 issue, with a set value for the OrthoAxisSize
   *    chart renders correctly.
   *
   * Steps:
   *    1. Assert chart is displayed correctly
   */
  @Test( timeout = 120000 )
  public void tc01_CCCProperties_DisplayChart() {
    this.log.info( "tc01_CCCProperties_DisplayChart" );

    /*
     * ## Step 1
     */
    //Go to New CDE Dashboard
    this.driver.get( this.baseUrl + "api/repos/%3Apublic%3AIssues%3ACDE%3ACDE-379%3AChart1.wcdf/generatedContent" );

    // Wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[contains(@id,'RowObject1protovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='rect']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[contains(@id,'RowObject1protovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='rect'][2]" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[contains(@id,'RowObject1protovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='rect'][3]" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[contains(@id,'RowObject1protovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='rect'][4]" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[contains(@id,'RowObject1protovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='rect'][5]" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[contains(@id,'RowObject1protovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='rect'][6]" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[contains(@id,'RowObject1protovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='rect']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[contains(@id,'RowObject1protovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='rect'][2]" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[contains(@id,'RowObject1protovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='rect'][3]" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[contains(@id,'RowObject1protovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='rect'][4]" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[contains(@id,'RowObject1protovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='rect'][5]" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[contains(@id,'RowObject1protovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='rect'][6]" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[contains(@id,'RowObject1protovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='rect']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[contains(@id,'RowObject1protovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='rect'][2]" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[contains(@id,'RowObject1protovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][4]/*[local-name()='rect']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[contains(@id,'RowObject1protovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][4]/*[local-name()='rect'][2]" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[contains(@id,'RowObject1protovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][5]/*[local-name()='rect']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[contains(@id,'RowObject1protovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][5]/*[local-name()='rect'][2]" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[contains(@id,'RowObject1protovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][5]/*[local-name()='rect'][3]" ) );
    assertNotNull( element );
  }
}
