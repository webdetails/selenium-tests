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
package com.pentaho.ctools.issues.cde;

import static org.testng.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.selenium.BaseTest;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-396
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-926
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDE396 extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDE396.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Asserting refreshing CDE returns confirmation
   *
   * Description:
   *    The test pretends validate the CDE-396 issue, so when user refreshes CDE a confirmation
   *    that the request was concluded is received.
   *
   * Steps:
   *    1. Assert confirmation text
   *
   */
  @Test
  public void tc1_RefreshCde_ReturnsInfo() {
    this.log.info( "tc1_RefreshCde_ReturnsInfo" );

    //Go to User Console
    driver.get( baseUrl + "plugin/pentaho-cdf-dd/api/renderer/refresh" );

    /*
     * ## Step 1
     */
    String confirmationText = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//body/pre" ) );
    assertEquals( "Refreshed CDE Successfully", confirmationText );
  }
}
