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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDF-501
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1109
 *
 * NOTE
 * To test this script it is required to have CDF plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDF501 extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDF501.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert that parameters passed on the URL are received by the dashboard
   *
   * Description:
   *    When adding paramType=test to the URL Dashboards.context.params.type is created with the value "test".
   *    When adding type=test to the URL Dashboards.getQueryParameter("type") is created with the value "test"
   *
   * Steps:
   *    1. Open created sample adding both parameters to the URL
   *    2. Assert text on alert and click OK
   *    3. Assert text on alert and click OK
   *
   */
  @Test
  public void tc1_CdfDashboardUrl_ParamSuccessfull() {
    this.log.info( "tc1_CdfDashboardUrl_ParamSuccessfull" );

    /*
     * ## Step 1
     */
    //Open Created sample with params on the URL
    this.elemHelper.Get( driver, PageUrl.ISSUES_CDF_501 );

    /*
     * ## Step 2
     */
    String alertMessage = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( "Direct Access: success", alertMessage );

    /*
     * ## Step 3
     */
    String alertMessage1 = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( "Function: awesome", alertMessage1 );
  }
}
