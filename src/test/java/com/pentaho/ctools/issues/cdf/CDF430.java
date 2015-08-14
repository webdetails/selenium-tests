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
package com.pentaho.ctools.issues.cdf;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.BaseTest;
import com.pentaho.ctools.utils.ElementHelper;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDF-430
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1007
 *
 * NOTE
 * To test this script it is required to have CDF plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDF430 extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDF430.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert i18n messages are prioritized correctly
   *
   * Description:
   *    The test pretends validate the CDF-430 issue, so  i18n messages are prioritized correctly for CDE, CDF and Sparkl dashboards.
   *
   * Steps:
   *    1. Open created Sparkl sample dashboard and assert text is displayed as expected
   *    2. Open created CDE sample dashboard and assert text is displayed as expected
   *    3. Open created CDF sample dashboard and assert text is displayed as expected
   *
   */
  @Test
  public void tc01_i18nMessages_PioritizedCorrectly() {
    this.log.info( "tc01_i18nMessages_PioritizedCorrectly" );

    /*
     * ## Step 1
     */
    //Set locale
    driver.get( baseUrl + "Home?locale=en-US" );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

    //Go to Sparkl sample
    driver.get( baseUrl + "plugin/CDE404/api/i18ntest" );

    // Wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //assert Elements loaded
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "Panel_1" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "Panel_2" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "Panel_3" ) );
    assertNotNull( element );
    this.elemHelper.WaitForTextPresence( driver, By.id( "Panel_1" ), "my message 1, coming from messages.properties" );
    String text = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "Panel_1" ) );
    assertEquals( text, "my message 1, coming from messages.properties" );
    this.elemHelper.WaitForTextPresence( driver, By.id( "Panel_2" ), "my message 2, overriden by messages_en.properties" );
    text = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "Panel_2" ) );
    assertEquals( text, "my message 2, overriden by messages_en.properties" );
    this.elemHelper.WaitForTextPresence( driver, By.id( "Panel_3" ), "my message 3, overriden by messages_en-US.properties" );
    text = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "Panel_3" ) );
    assertEquals( text, "my message 3, overriden by messages_en-US.properties" );

    /*
     * ## Step 2
     */
    //Go to CDE sample
    driver.get( baseUrl + "api/repos/%3Apublic%3AIssues%3ACDF%3ACDF-430%3ACDE%3Ai18nTest.wcdf/generatedContent" );

    // Wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //assert Elements loaded
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "Panel_1" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "Panel_2" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "Panel_3" ) );
    assertNotNull( element );
    this.elemHelper.WaitForTextPresence( driver, By.id( "Panel_1" ), "my message 1, coming from messages.properties" );
    text = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "Panel_1" ) );
    assertEquals( text, "my message 1, coming from messages.properties" );
    this.elemHelper.WaitForTextPresence( driver, By.id( "Panel_2" ), "my message 2, overriden by messages_en.properties" );
    text = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "Panel_2" ) );
    assertEquals( text, "my message 2, overriden by messages_en.properties" );
    this.elemHelper.WaitForTextPresence( driver, By.id( "Panel_3" ), "my message 3, overriden by messages_en-US.properties" );
    text = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "Panel_3" ) );
    assertEquals( text, "my message 3, overriden by messages_en-US.properties" );

    /*
     * ## Step 3
     */
    //Go to CDE sample
    driver.get( baseUrl + "api/repos/%3Apublic%3AIssues%3ACDF%3ACDF-430%3ACDF%3Acdf_i18nTest.xcdf/generatedContent?locale=en-US" );

    // Wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //assert Elements loaded
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='sampleButton01']/button/span" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='sampleButton02']/button/span" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='sampleButton03']/button/span" ) );
    assertNotNull( element );
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//div[@id='sampleButton01']/button/span" ), "My button 01 label" );
    text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleButton01']/button/span" ) );
    assertEquals( text, "My button 01 label" );
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//div[@id='sampleButton02']/button/span" ), "messages_en button 02 label" );
    text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleButton02']/button/span" ) );
    assertEquals( text, "messages_en button 02 label" );
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//div[@id='sampleButton03']/button/span" ), "messages_en-US button 03 label" );
    text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleButton03']/button/span" ) );
    assertEquals( text, "messages_en-US button 03 label" );
  }
}
