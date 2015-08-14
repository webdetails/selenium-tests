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
 * - http://jira.pentaho.com/browse/CDF-442
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1010
 *
 * NOTE
 * To test this script it is required to have CDF plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDF442 extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDF442.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    CCC recognizes No Data state and doesn't throw an error
   *
   * Description:
   *    The test pretends validate the CDF-442 issue, so when in a specific "No Data" state, CDF doesn't throw error.
   *
   * Steps:
   *    1. Open sample and assert div with id cdfErrorDiv doesn't exist
   *
   */
  @Test
  public void tc01_CCCBarChart_NoErrorThrown() {
    this.log.info( "tc01_CCCBarChart_NoErrorThrown" );

    /*
     * ## Step 1
     */
    //Go to New CDE Dashboard
    driver.get( baseUrl + "api/repos/%3Apublic%3AIssues%3ACDF%3ACDF-442%3ACDF442.wcdf/generatedContent" );

    // Wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //assert Elements loaded
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "col1protovis" ) );
    assertNotNull( element );
  }
}
