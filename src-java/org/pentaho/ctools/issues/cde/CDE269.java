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
package org.pentaho.ctools.issues.cde;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

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
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDE269 {
  // Instance of the driver (browser emulator)
  private static WebDriver DRIVER;
  // The base url to be append the relative url in test
  private static String BASE_URL;
  // Log instance
  private static Logger LOG = LogManager.getLogger( CDE269.class );
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( DRIVER );

  @BeforeClass
  public static void setUpClass() {
    LOG.info( "setUp##" + CDE269.class.getSimpleName() );
    DRIVER = CToolsTestSuite.getDriver();
    BASE_URL = CToolsTestSuite.getBaseUrl();
  }

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
  @Test( timeout = 120000 )
  public void tc01_CdeGetHeaders_CorrectInfo() {
    LOG.info( "tc01_CdeGetHeaders_CorrectInfo" );

    /*
     * ## Step 1
     */

    //Open URL
    DRIVER.get( BASE_URL + "plugin/pentaho-cdf-dd/api/renderer/getHeaders?solution=&path=/public/plugin-samples/pentaho-cdf-dd&file=cde_sample1.wcdf&absolute=true&root=localhost:8080&scheme=https" );

    //Wait for Elements and assert them
    WebElement element = ElementHelper.FindElement( DRIVER, By.xpath( "//body/pre" ) );
    assertNotNull( element );
    String text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//body/pre" ) );
    text = text.replaceAll( "v=\\S+\"", "\"" );

    assertEquals( "<title>CDE Sample Dashboard</title><!-- cdf-blueprint-script-includes --> <script language=\"javascript\" type=\"text/javascript\" src=\"https://localhost:8080/pentaho/api/repos/pentaho-cdf/js/cdf-blueprint-script-includes.js?\"></script> <!-- cdf-blueprint-style-includes --> <link href=\"https://localhost:8080/pentaho/api/repos/pentaho-cdf/css/cdf-blueprint-style-includes.css?\" rel=\"stylesheet\" type=\"text/css\" /> <!-- cdf-blueprint-ie8style-includes --><!--[if lte IE 8]> <link href=\"https://localhost:8080/pentaho/api/repos/pentaho-cdf/js-legacy/lib/blueprint/ie.css?\" rel=\"stylesheet\" type=\"text/css\" /> <![endif]--> <!-- cdf-cdf-dashboard-script-includes --> <script language=\"javascript\" type=\"text/javascript\" src=\"https://localhost:8080/pentaho/api/repos/pentaho-cdf/js-legacy/lib/sparkline/jquery.sparkline.js?\"></script> <!-- cdf-cdf-dashboard-style-includes --> <script language=\"javascript\" type=\"text/javascript\" src=\"https://localhost:8080/pentaho/api/repos/pentaho-cdf-dd/js/CDF.js?\"></script> <link href=\"https://localhost:8080/pentaho/api/repos/pentaho-cdf-dd/css/CDF-CSS.css?\" rel=\"stylesheet\" type=\"text/css\" />", text );

  }

  @AfterClass
  public static void tearDownClass() {
    LOG.info( "tearDown##" + CDE269.class.getSimpleName() );
  }
}
