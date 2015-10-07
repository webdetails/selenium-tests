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
import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.selenium.BaseTest;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDF-469
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1026
 *
 * NOTE
 * To test this script it is required to have CDF plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDF469 extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDF469.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert legends are shown only once
   *
   * Description:
   *    The test pretends validate the CDF-469 issue, when adding the measure name to the legend of a chart
   *    it shows once and only once.
   *
   * Steps:
   *    1. Open created dashboard and assert each legend is shown once and only once.
   */
  @Test
  public void tc01_CCCProperties_MeasureOnLegend() {
    this.log.info( "tc01_CCCProperties_MeasureOnLegend" );

    /*
     * ## Step 1
     */
    //Open created dashboard
    driver.get( baseUrl + "api/repos/%3Apublic%3AIssues%3ACDF%3ACDF-469%3Acdf-469.wcdf/generatedContent" );

    // Wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Assert legends are there once and only once
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[contains(@id,'chartObj')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']" ) );
    assertNotNull( element );
    String text = element.getText();
    assertEquals( "London / Count", text );
    assertTrue( this.elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[contains(@id,'chartObj')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='text']" ), 30 ) );
    assertTrue( this.elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[contains(@id,'chartObj')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text'][2]" ), 30 ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[contains(@id,'chartObj')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='text']" ) );
    assertNotNull( element );
    text = element.getText();
    assertEquals( "Paris / Count", text );
    assertTrue( this.elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[contains(@id,'chartObj')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][3]/*[local-name()='text']" ), 30 ) );
    assertTrue( this.elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[contains(@id,'chartObj')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='text'][2]" ), 30 ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[contains(@id,'chartObj')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g'][2]/*[local-name()='text']" ) );
    assertNotNull( element );
    text = element.getText();
    assertEquals( "Lisbon / Count", text );
    assertTrue( this.elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[contains(@id,'chartObj')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g'][3]/*[local-name()='text']" ), 30 ) );
    assertTrue( this.elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[contains(@id,'chartObj')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g'][2]/*[local-name()='text'][2]" ), 30 ) );
  }
}
