/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2016 by Pentaho : http://www.pentaho.com
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

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDF-424
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-934
 *
 * NOTE
 * To test this script it is required to have CDF plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDF424 extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDF424.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert CCC chart properties have proper priority level
   *
   * Description:
   *    The test pretends validate the CDF-424 issue, so when user sets 
   *    ortho2AxisLabel_textStyle property in a chart it overrides 
   *    axisLabel_textStyle for the ortho2 axis.
   *
   * Steps:
   *    1. Assert color of axis elements is correct
   */
  @Test
  public void tc01_CCCProperties_PrioritizedCorrectly() {
    this.log.info( "tc01_CCCProperties_PrioritizedCorrectly" );

    /*
     * ## Step 1
     */
    //Go to Issue Sample
    this.elemHelper.Get( driver, PageUrl.ISSUES_CDF_424 );

    // Wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    //assert Elements loaded
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "placeprotovis" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[contains(@id,'placeprotovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][4]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[contains(@id,'placeprotovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][4]/*[local-name()='g']/*[local-name()='g'][4]/*[local-name()='text']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[contains(@id,'placeprotovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][5]/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='text']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[contains(@id,'placeprotovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][5]/*[local-name()='g']/*[local-name()='g'][6]/*[local-name()='text']" ) );
    assertNotNull( element );

    String axis1Text = this.elemHelper.FindElement( driver, By.xpath( "//div[contains(@id,'placeprotovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][4]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']" ) ).getAttribute( "fill" );
    String axis2Text = this.elemHelper.FindElement( driver, By.xpath( "//div[contains(@id,'placeprotovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][4]/*[local-name()='g']/*[local-name()='g'][4]/*[local-name()='text']" ) ).getAttribute( "fill" );
    String axis3Text = this.elemHelper.FindElement( driver, By.xpath( "//div[contains(@id,'placeprotovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][5]/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='text']" ) ).getAttribute( "fill" );
    String axis4Text = this.elemHelper.FindElement( driver, By.xpath( "//div[contains(@id,'placeprotovis')]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][5]/*[local-name()='g']/*[local-name()='g'][6]/*[local-name()='text']" ) ).getAttribute( "fill" );
    assertEquals( "rgb(0,0,255)", axis3Text );
    assertEquals( "rgb(0,0,255)", axis4Text );
    assertEquals( "rgb(255,0,0)", axis1Text );
    assertEquals( "rgb(255,0,0)", axis2Text );
  }
}
