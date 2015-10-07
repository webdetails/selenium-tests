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
package com.pentaho.ctools.cdf.require;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with Dial Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class DialComponent extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  //Log instance
  private final Logger log = LogManager.getLogger( DialComponent.class );

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Open Sample Page
   */
  @Test
  public void tc0_OpenSamplePage_Display() {
    this.log.info( "tc0_OpenSamplePage_Display" );

    // The URL for the CheckComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Documentation/ComponentReference/Core Components/DialComponent
    driver.get( baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A25-DialComponent%3Adial_component.xcdf/generatedContent" );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
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
  @Test
  public void tc1_PageContent_DisplayTitle() {
    this.log.info( "tc1_PageContent_DisplayTitle" );
    // Wait for title become visible and with value 'Community Dashboard Framework'
    wait.until( ExpectedConditions.titleContains( "Community Dashboard Framework" ) );
    // Wait for visibility of 'VisualizationAPIComponent'
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );

    // Validate the sample that we are testing is the one
    assertEquals( "Community Dashboard Framework", driver.getTitle() );
    assertEquals( "DialComponent", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );
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
  @Test
  public void tc2_ReloadSample_SampleReadyToUse() {
    this.log.info( "tc2_ReloadSample_SampleReadyToUse" );

    /*
     *  ## Step 1
     */
    // Render again the sample
    this.elemHelper.FindElement( driver, By.xpath( "//div[@id='example']/ul/li[2]/a" ) ).click();
    this.elemHelper.FindElement( driver, By.xpath( "//div[@id='code']/button" ) ).click();

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    // Now sample element must be displayed
    assertTrue( this.elemHelper.FindElement( driver, By.id( "sample" ) ).isDisplayed() );
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Dial Component
   * Description:
   *    We pretend validate the generated graphic (in a image) and if url for
   *    the image is valid.
   * Steps:
   *    1. Check if a graphic was generated
   *    2. Check the http request for the generated image
   */
  @Test
  public void tc3_GenerateGraphic_GraphicGeneratedAndHttp200() {
    this.log.info( "tc3_GenerateGraphic_GraphicGeneratedAndHttp200" );

    /*
     * ## Step 1
     */
    WebElement dialElement = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "img" ) );
    assertNotNull( dialElement );

    String attrSrc = this.elemHelper.GetAttribute( driver, By.cssSelector( "img" ), "src" );
    String attrWidth = this.elemHelper.GetAttribute( driver, By.cssSelector( "img" ), "width" );
    String attrHeight = this.elemHelper.GetAttribute( driver, By.cssSelector( "img" ), "height" );
    assertTrue( attrSrc.startsWith( baseUrl + "getImage?image=tmp_chart_admin-" ) );
    assertEquals( attrWidth, "400" );
    assertEquals( attrHeight, "200" );

    // ## Step 2
    try {
      URL url = new URL( attrSrc );
      URLConnection connection = url.openConnection();
      connection.connect();

      assertEquals( HttpStatus.SC_OK, ( (HttpURLConnection) connection ).getResponseCode() );

    } catch ( Exception ex ) {
      ex.printStackTrace();
    }
  }
}
