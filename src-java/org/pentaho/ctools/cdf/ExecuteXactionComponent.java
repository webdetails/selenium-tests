/*
 * !*****************************************************************************
 * Selenium Tests For CTools Copyright (C) 2002-2015 by Pentaho :
 * http://www.pentaho.com
 * ********************************************************
 * ********************** Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 * ****************************************************************************
 */
package org.pentaho.ctools.cdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * Testing the functionalities related with Xaction Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class ExecuteXactionComponent {

  //Instance of the driver (browser emulator)
  private final WebDriver driver = CToolsTestSuite.getDriver();
  // Instance to be used on wait commands
  private final Wait<WebDriver> wait = CToolsTestSuite.getWait();
  // The base url to be append the relative url in test
  private final String baseUrl = CToolsTestSuite.getBaseUrl();
  //Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  //Log instance
  private final Logger log = LogManager.getLogger( ExecuteXactionComponent.class );

  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( this.driver );

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Open Sample Page
   */
  @Test
  public void tc0_OpenSamplePage_Display() {
    // The URL for the ExecuteXactionComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Documentation/Component Reference/Core Components/ExecuteXactionComponent
    this.driver.get( this.baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A76-ExecuteXactionComponent%3Aexecute_xaction_component.xcdf/generatedContent" );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Reload Sample
   * Description:
   *    Reload the sample (not refresh page).
   * Steps:
   *    1. Click in Code and then click in button 'Try me'.
   */
  @ Test
  public void tc1_PageContent_DisplayTitle() {
    // Wait for title become visible and with value 'Community Dashboard Framework'
    this.wait.until( ExpectedConditions.titleContains( "Community Dashboard Framework" ) );
    // Wait for visibility of 'VisualizationAPIComponent'
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );

    // Validate the sample that we are testing is the one
    assertEquals( "Community Dashboard Framework", this.driver.getTitle() );
    assertEquals( "ExecuteXactionComponent", this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Reload Sample
   * Description:
   *    Reload the sample (not refresh page).
   * Steps:
   *    1. Click in Code and then click in button 'Try me'.
   */
  @ Test
  public void tc2_ReloadSample_SampleReadyToUse() {
    // ## Step 1
    // Render again the sample
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='example']/ul/li[2]/a" ) ).click();
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='code']/button" ) ).click();

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    // Now sample element must be displayed
    assertTrue( this.elemHelper.FindElement( this.driver, By.id( "sample" ) ).isDisplayed() );

    //Check the number of divs with id 'SampleObject'
    //Hence, we guarantee when click Try Me the previous div is replaced
    final int nSampleObject = this.driver.findElements( By.id( "sampleObject" ) ).size();
    assertEquals( 1, nSampleObject );
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Execute Xacion
   * Description:
   *    We pretend validate the generated chart (in an image) and if the image
   *    has a valid url.
   * Steps:
   *    1. Click to generate chart
   *    2. Check if a chart was generated
   *    3. Check the http request for the image generated
   */
  @ Test
  public void tc3_PressToGenerateChart_ChartIsDisplayed() {
    // ## Step 1
    final String buttonName = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//button/span" ) );
    assertEquals( "Execute XAction", buttonName );
    //Click in button
    this.elemHelper.FindElement( this.driver, By.xpath( "//button" ) ).click();

    // ## Step 1
    this.wait.until( ExpectedConditions.presenceOfElementLocated( By.id( "fancybox-content" ) ) );
    this.driver.switchTo().frame( "fancybox-frame" );
    //Check the title
    final String chartTitle = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table/tbody/tr/td" ) );
    assertEquals( "Action Successful", chartTitle );
    //Check for the displayed image
    final WebElement xactionElement = this.elemHelper.FindElement( this.driver, By.cssSelector( "img" ) );
    assertNotNull( xactionElement );

    final String attrSrc = xactionElement.getAttribute( "src" );
    final String attrWidth = xactionElement.getAttribute( "width" );
    final String attrHeight = xactionElement.getAttribute( "height" );

    assertTrue( attrSrc.startsWith( this.baseUrl + "getImage?image=tmp_chart_admin-" ) );
    assertEquals( attrWidth, "500" );
    assertEquals( attrHeight, "600" );

    // ## Step 3
    try {
      final URL url = new URL( attrSrc );
      final URLConnection connection = url.openConnection();
      connection.connect();

      assertEquals( HttpStatus.SC_OK, ( (HttpURLConnection) connection ).getResponseCode() );
    } catch ( final Exception ex ) {
      this.log.error( ex.getMessage() );
    }

    //Close pop-up window
    this.driver.switchTo().defaultContent();
    this.elemHelper.FindElement( this.driver, By.id( "fancybox-close" ) ).click();
    this.elemHelper.WaitForElementInvisibility( this.driver, By.id( "fancybox-content" ) );
    assertNotNull( this.elemHelper.FindElement( this.driver, By.xpath( "//button" ) ) );
  }
}
