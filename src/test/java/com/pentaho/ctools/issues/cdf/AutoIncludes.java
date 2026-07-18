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

package com.pentaho.ctools.issues.cdf;

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
 * - http://jira.pentaho.com/browse/CDF-548
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1149
 *
 * NOTE
 * To test this script it is required to have CDF plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class AutoIncludes extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( AutoIncludes.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert Auto-Includes is working as expected
   *
   * Description:
   *    Open sample and assert query data was automatically included to the dashboard
   *
   * Steps:
   *    1. Open created sample and click button
   *    2. Assert alert shows expected text
   */
  @Test
  public void tc1_CdfAutoIncludes_ReturnsValues() {
    this.log.info( "tc1_CdfAutoIncludes_ReturnsValues" );

    /*
     * ## Step 1
     */
    //Open Created sample and click button
    this.elemHelper.Get( driver, PageUrl.ISSUES_AUTO_INCLUDES );

    //Click Query button
    this.elemHelper.Click( driver, By.xpath( "//div[@id='table']/button" ) );

    /*
     * ## Step 2
     */
    String alertMessage = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( "Shipped,2004,4114929.960000002,4.114929960000002,Shipped,2005,1513074.4600000002,1.5130744600000001", alertMessage );
  }
}
