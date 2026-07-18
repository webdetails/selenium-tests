/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2002 - 2026 by Pentaho Canada Inc. : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2030-06-15
 ******************************************************************************/

package com.pentaho.ctools.issues.cde;

import static org.testng.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
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

    // Go to User Console
    this.elemHelper.Get( driver, PageUrl.CDE_REFRESH );

    /*
     * ## Step 1
     */
    String confirmationText = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//body/pre" ) );
    assertEquals( "Refreshed CDE Successfully", confirmationText );
  }
}
