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

import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.selenium.BaseTest;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDF-486
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1108
 *
 * NOTE
 * To test this script it is required to have CDF plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CleanDashboard extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CleanDashboard.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert user is able to open a dashboard with no css applied to it
   *
   * Description:
   *    Open dashboard with added parameter to URL and assert clean script is loaded
   *
   * Steps:
   *    1. Open created sample and assert blueprint script is loaded
   *    2. Add parameter to URL, refresh sample and assert clean script is loaded
   */
  @Test
  public void tc1_CdfDashboardType_CleanDashboard() {
    this.log.info( "tc1_CdfDashboardType_CleanDashboard" );

    /*
     * ## Step 1
     */
    //Open Created sample and click button
    driver.get( baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Astart_here.xcdf/generatedContent" );

    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 60 );

    //Assert script
    WebElement loadedScript = this.elemHelper.WaitForElementPresence( driver, By.tagName( "script" ) );
    assertNotNull( loadedScript );
    String scriptSource = loadedScript.getAttribute( "src" );
    assertTrue( scriptSource.contains( "/pentaho/api/repos/pentaho-cdf/js/cdf-blueprint-script-includes.js" ) );

    /*
     * ## Step 2
     */
    //Open Created sample and click button
    driver.get( baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Astart_here.xcdf/generatedContent?dashboardType=clean" );

    //Assert script
    loadedScript = this.elemHelper.WaitForElementPresence( driver, By.tagName( "script" ) );
    assertNotNull( loadedScript );
    scriptSource = loadedScript.getAttribute( "src" );
    assertTrue( scriptSource.contains( "/pentaho/api/repos/pentaho-cdf/js/cdf-clean-script-includes.js" ) );
  }
}
