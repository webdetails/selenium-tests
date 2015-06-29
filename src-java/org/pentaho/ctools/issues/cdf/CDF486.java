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
package org.pentaho.ctools.issues.cdf;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
 * - http://jira.pentaho.com/browse/CDF-486
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1028
 *
 * NOTE
 * To test this script it is required to have CDF plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDF486 {
  // Instance of the driver (browser emulator)
  private final WebDriver driver = CToolsTestSuite.getDriver();
  // The base url to be append the relative url in test
  private final String baseUrl = CToolsTestSuite.getBaseUrl();
  //Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDF486.class );
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( this.driver );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert dashboardType=clean has desired effect.
   *
   * Description:
   *    When adding dashboardType=clean to dashboard's url it renders without any css associated.
   *
   * Steps:
   *    1. Open CDF blueprint sample and assert position of specific elements
   *    2. Open same sample adding clean option to URL and assert position of elements is different
   *
   */
  @Test( timeout = 120000 )
  public void tc1_CdfDashboardType_CleanStyle() {
    this.log.info( "tc1_CdfDashboardType_CleanStyle" );

    /*
     * ## Step 1
     */
    //Go to New CDE Dashboard
    this.driver.get( this.baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A20-samples%3Ablueprint%3Ablueprint.xcdf/generatedContent" );

    // Wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='panelTitle']/div[@class='container']/div[2]" ) );
    assertNotNull( element );
    int imagex = element.getLocation().x;
    int imagey = element.getLocation().y;
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "pieChart_object" ) );
    assertNotNull( element );
    element.getLocation();
    element.getLocation();
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "barChart_object" ) );
    assertNotNull( element );
    int barChartx = element.getLocation().x;
    int barCharty = element.getLocation().y;
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "dialChart_object" ) );
    assertNotNull( element );
    int dialChartx = element.getLocation().x;
    int dialCharty = element.getLocation().y;

    /*
     * ## Step 1
     */
    //Go to New CDE Dashboard
    this.driver.get( this.baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A20-samples%3Ablueprint%3Ablueprint.xcdf/generatedContent?dashboardType=clean" );

    // Wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='panelTitle']/div[@class='container']/div[2]" ) );
    assertNotNull( element );
    int imagex1 = element.getLocation().x;
    int imagey1 = element.getLocation().y;
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "pieChart_object" ) );
    assertNotNull( element );
    element.getLocation();
    element.getLocation();
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "barChart_object" ) );
    assertNotNull( element );
    int barChartx1 = element.getLocation().x;
    int barCharty1 = element.getLocation().y;
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "dialChart_object" ) );
    assertNotNull( element );
    int dialChartx1 = element.getLocation().x;
    int dialCharty1 = element.getLocation().y;
    assertTrue( imagex != imagex1 );
    assertTrue( imagey != imagey1 );
    assertTrue( barChartx != barChartx1 );
    assertTrue( barCharty != barCharty1 );
    assertTrue( dialChartx != dialChartx1 );
    assertTrue( dialCharty != dialCharty1 );
  }
}
