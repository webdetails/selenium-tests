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
 * - http://jira.pentaho.com/browse/CDE-269
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1014
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDE269 extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDE269.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Asserting GetHeaders endpoint returns correct information
   *
   * Description:
   *    The test pretends validate the CDE-269 issue, so GetHeaders endpoint returns correct information.
   *
   * Steps:
   *    1. Open URL and assert information shown
   */
  @Test
  public void tc01_CdeGetHeaders_CorrectInfo() {
    this.log.info( "tc01_CdeGetHeaders_CorrectInfo" );

    /*
     * ## Step 1
     */
    //Open URL
    this.elemHelper.Get( driver, PageUrl.ISSUES_CDE_269 );

    //Wait for Elements and assert them
    WebElement element = this.elemHelper.FindElement( driver, By.xpath( "//body/pre" ) );
    assertNotNull( element );
    String text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//body/pre" ) );
    text = text.replaceAll( "v=\\S+\"", "\"" );

    assertEquals( "<title>CDE Sample Dashboard</title><!-- cdf-blueprint-script-includes --> <script language=\"javascript\" type=\"text/javascript\" src=\"https://localhost:8080/pentaho/api/repos/pentaho-cdf/js/cdf-blueprint-script-includes.js?\"></script> <!-- cdf-blueprint-style-includes --> <link href=\"https://localhost:8080/pentaho/api/repos/pentaho-cdf/css/cdf-blueprint-style-includes.css?\" rel=\"stylesheet\" type=\"text/css\" /> <!-- cdf-blueprint-ie8style-includes --><!--[if lte IE 8]> <link href=\"https://localhost:8080/pentaho/api/repos/pentaho-cdf/js-legacy/lib/blueprint/ie.css?\" rel=\"stylesheet\" type=\"text/css\" /> <![endif]--> <!-- cdf-cdf-dashboard-script-includes --> <script language=\"javascript\" type=\"text/javascript\" src=\"https://localhost:8080/pentaho/api/repos/pentaho-cdf/js-legacy/lib/sparkline/jquery.sparkline.js?\"></script> <!-- cdf-cdf-dashboard-style-includes --> <script language=\"javascript\" type=\"text/javascript\" src=\"https://localhost:8080/pentaho/api/repos/pentaho-cdf-dd/js/CDF.js?\"></script> <link href=\"https://localhost:8080/pentaho/api/repos/pentaho-cdf-dd/css/CDF-CSS.css?\" rel=\"stylesheet\" type=\"text/css\" />", text );
  }
}
