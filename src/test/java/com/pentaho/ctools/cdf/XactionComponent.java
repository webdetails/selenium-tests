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
package com.pentaho.ctools.cdf;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with Xaction Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class XactionComponent extends BaseTest {
  // Access to wrapper for webdriver.
  private final ElementHelper elemHelper = new ElementHelper();

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Open Sample Page
   */
  @Test
  public void tc0_OpenSamplePage_Display() {
    // The URL for the XactionComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Documentation/Component Reference/Core Components/XactionComponent
    driver.get( baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A10-XactionComponent%3Axaction_component.xcdf/generatedContent" );

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
    // Wait for title become visible and with value 'Community Dashboard Framework'
    wait.until( ExpectedConditions.titleContains( "Community Dashboard Framework" ) );
    // Wait for visibility of 'VisualizationAPIComponent'
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );

    // Validate the sample that we are testing is the one
    assertEquals( "Community Dashboard Framework", driver.getTitle() );
    assertEquals( "XactionComponent", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );
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
    // ## Step 1
    // Render again the sample
    this.elemHelper.FindElement( driver, By.xpath( "//div[@id='example']/ul/li[2]/a" ) ).click();
    this.elemHelper.FindElement( driver, By.xpath( "//div[@id='code']/button" ) ).click();

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    // Now sample element must be displayed
    assertTrue( this.elemHelper.FindElement( driver, By.id( "sample" ) ).isDisplayed() );

    //Check the number of divs with id 'SampleObject'
    //Hence, we guarantee when click Try Me the previous div is replaced
    int nSampleObject = driver.findElements( By.id( "sampleObject" ) ).size();
    assertEquals( 1, nSampleObject );
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Xacion
   * Description:
   *    We pretend validate the generated graphic (in an image) and if the image
   *    has a valid url.
   * Steps:
   *    1. Check if a graphic was generated
   *    2. Check the http request for the image generated
   */
  @Test
  public void tc3_GenerateChart_ChartIsDisplayed() {
    // ## Step 1
    WebElement xactionElement = this.elemHelper.FindElement( driver, By.cssSelector( "img" ) );
    assertNotNull( xactionElement );

    String attrSrc = xactionElement.getAttribute( "src" );
    String attrWidth = xactionElement.getAttribute( "width" );
    String attrHeight = xactionElement.getAttribute( "height" );

    assertTrue( attrSrc.startsWith( baseUrl + "getImage?image=tmp_chart_admin-" ) );
    assertEquals( attrWidth, "500" );
    assertEquals( attrHeight, "600" );

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
