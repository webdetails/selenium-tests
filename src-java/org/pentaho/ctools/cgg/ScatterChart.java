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
package org.pentaho.ctools.cgg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
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
 * Testing the functionalities related with CGG - Scatter Chart.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class ScatterChart {
  // Instance of the driver (browser emulator)
  private WebDriver driver;
  // The base url to be append the relative url in test
  private String baseUrl;
  //Access to wrapper for webdriver
  private ElementHelper elemHelper = new ElementHelper();
  //Log instance
  private static Logger LOG = LogManager.getLogger( ScatterChart.class );

  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( this.driver );

  @Before
  public void setUp() {
    LOG.debug( "setUp" );
    this.driver = CToolsTestSuite.getDriver();
    this.baseUrl = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Bar Chart
   * Description:
   *    We pretend to check if an image is displayed with in a chart.
   * Steps:
   *    1. Open the scatter chart.
   */
  @Test( timeout = 60000 )
  public void tc1_ScatterChart_ImageRendered() {
    LOG.debug( "tc1_ScatterChart_ImageRendered" );
    //## Step 1
    this.driver.get( this.baseUrl + "plugin/cgg/api/services/draw?script=/public/testScatterChart.js&outputType=svg" );

    this.elemHelper.FindElement( this.driver, By.xpath( "//*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']" ) );

    String textCentral = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']" ) );
    String textEastern = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='text']" ) );
    String textSouthern = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g'][2]/*[local-name()='text']" ) );
    String textWestern = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][4]/*[local-name()='g'][2]/*[local-name()='text']" ) );
    String textActual = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g'][4]/*[local-name()='g']/*[local-name()='g']/*[local-name()='text']" ) );
    String textBudget = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g'][6]/*[local-name()='g']/*[local-name()='g']/*[local-name()='text']" ) );

    assertEquals( "Central", textCentral );
    assertEquals( "Eastern", textEastern );
    assertEquals( "Southern", textSouthern );
    assertEquals( "Western", textWestern );
    assertEquals( "Actual", textActual );
    assertEquals( "Budget", textBudget );

    WebElement circle1 = this.elemHelper.FindElement( this.driver, By.xpath( "//*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g'][8]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='circle']" ) );
    WebElement circle2 = this.elemHelper.FindElement( this.driver, By.xpath( "//*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g'][8]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='circle'][15]" ) );
    WebElement circle3 = this.elemHelper.FindElement( this.driver, By.xpath( "//*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g'][8]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='circle'][28]" ) );

    assertNotNull( circle1 );
    assertNotNull( circle2 );
    assertNotNull( circle3 );
  }

  @After
  public void tearDown() {
    LOG.debug( "tearDown" );
  }
}
