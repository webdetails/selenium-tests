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
import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.Test;

import com.pentaho.ctools.suite.CToolsTestSuite;
import com.pentaho.ctools.utils.BaseTest;
import com.pentaho.ctools.utils.ElementHelper;

/**
 * Testing the functionalities related with component Visualization API.
 *
 * Naming convention for test: 'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class VisualizationAPIComponent extends BaseTest {
  // Instance to be used on wait commands
  private final Wait<WebDriver> wait = CToolsTestSuite.getWait();
  // The base url to be append the relative url in test
  private final String baseUrl = CToolsTestSuite.getBaseUrl();
  //Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  //Log instance
  private final Logger log = LogManager.getLogger( VisualizationAPIComponent.class );

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name: 
   *    Open Sample Page
   */
  @Test
  public void tc0_OpenSamplePage_Display() {
    this.log.info( "tc0_OpenSamplePage_Display" );

    // The URL for the VisualizationAPIComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Documentation/Component Reference/Core Components/VisualizationAPIComponent
    this.driver.get( this.baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A60-VisualizationAPIComponent%3Avisualization_component.xcdf/generatedContent" );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name: 
   *    Validate Page Contents
   * Description: 
   *    Here we want to validate the page contents. 
   * Steps: 
   *    1. Check the widget's title.
   */
  @Test
  public void tc1_PageContent_DisplayTitle() {
    this.log.info( "tc1_PageContent_DisplayTitle" );

    // Wait for title become visible and with value 'Community Dashboard Framework'
    this.wait.until( ExpectedConditions.titleContains( "Community Dashboard Framework" ) );
    // Wait for visibility of 'VisualizationAPIComponent'
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );

    // Validate the sample that we are testing is the one
    assertEquals( "Community Dashboard Framework", this.driver.getTitle() );
    assertEquals( "VisualizationAPIComponent", this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );
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
     * ## Step 1
     */
    // Render again the sample
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='example']/ul/li[2]/a" ) ).click();
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='code']/button" ) ).click();

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    // Now sample element must be displayed
    assertTrue( this.elemHelper.FindElement( this.driver, By.id( "sample" ) ).isDisplayed() );
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Validate MAX number
   * Description: 
   *    When the user access the component, it is presented the max number of 
   *    array set.
   * Steps: 
   *    1. Check the presented value for MAX.
   */
  @Test
  public void tc3_MaxNumber_PresentCorrectValue() {
    this.log.info( "tc3_MaxNumber_PresentCorrectValue" );

    /*
     * ## Step 1
     */
    String value = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='sample']/div[2]/div/span" ) );
    for ( int i = 0; i < 100; i++ ) {
      if ( value.equals( "35659" ) ) {
        break;
      } else {
        value = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='sample']/div[2]/div/span" ) );
      }
    }

    assertEquals( "35659", value );
  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name:
   *    Validate MIN number
   * Description: 
   *    When the user access the component, it is presented the min number of 
   *    array set. 
   * Steps: 
   *    1. Change the option parameter to MIN and reload sample.
   *    2. Check the presented value for MIN.
   *
   * @throws InterruptedException
   */
  @Test
  public void tc4_MinNumber_PresentCorrectValue() {
    this.log.info( "tc4_MinNumber_PresentCorrectValue" );

    /*
     * ## Step 1 - Change the option parameter to MIN and reload sample
     */
    // Render again the sample
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='example']/ul/li[2]/a" ) ).click();

    // Wait for board load
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='code']" ) ) );

    String text = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='code']/textarea" ) ).getText().replace( "MAX", "MIN" );
    text = text.replace( "//Using postFetch to convert data types, because the query is passing //a Numeric field as String and that breaks the sample visualization", "" );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='code']/textarea" ) ).clear();
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='code']/textarea" ) ).sendKeys( text );

    // Change contains to MIN
    //((JavascriptExecutor) driver).executeScript("$('#samplecode').text($('#samplecode').text().replace('MAX', 'MIN'));");

    /*String strText = "";
    while (strText.indexOf("MIN") == -1) {
      strText = this.elemHelper.WaitForElementPresentGetText(driver, By.id("samplecode"));
    }*/

    // Click in Try me
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='code']/button" ) ).click();
    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    // Now sample element must be displayed
    assertTrue( this.elemHelper.FindElement( this.driver, By.id( "sample" ) ).isDisplayed() );

    /*
     * ## Step 2 - Check the presented value for MIN.
     */
    this.wait.until( ExpectedConditions.presenceOfElementLocated( By.xpath( "//div[@id='sample']" ) ) );
    this.wait.until( ExpectedConditions.presenceOfElementLocated( By.xpath( "//div[@id='sample']/div[2]/div/span" ) ) );

    String value = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='sample']/div[2]/div/span" ) );
    for ( int i = 0; i < 100; i++ ) {
      if ( value.equals( "0" ) ) {
        break;
      } else {
        value = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='sample']/div[2]/div/span" ) );
      }
    }

    assertEquals( "0", value );
  }

  /**
   * ############################### Test Case 5 ###############################
   *
   * Test Case Name: 
   *    Validate AVG number
   * Description: 
   *    When the user access the component, it is presented the avg number of 
   *    array set.
   * Steps: 
   *    1. Change the option parameter to AVG and reload sample 
   *    2. Check the presented value for AVG.
   */
  @Test
  public void tc5_AvgNumber_PresentCorrectValue() {
    this.log.info( "tc5_AvgNumber_PresentCorrectValue" );

    /*
     * ## Step 1 - Change the option parameter to AVG and reload sample
     */
    // Render again the sample
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='example']/ul/li[2]/a" ) ).click();

    String text = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='code']/textarea" ) ).getText().replace( "MAX", "AVG" );
    text = text.replace( "//Using postFetch to convert data types, because the query is passing //a Numeric field as String and that breaks the sample visualization", "" );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='code']/textarea" ) ).clear();
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='code']/textarea" ) ).sendKeys( text );

    // Click in Try me
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='code']/button" ) ).click();
    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    // Now sample element must be displayed
    assertTrue( this.elemHelper.FindElement( this.driver, By.id( "sample" ) ).isDisplayed() );

    /*
     * ## Step 2 - Check the presented value for MIN.
     */
    this.wait.until( ExpectedConditions.presenceOfElementLocated( By.xpath( "//div[@id='sample']" ) ) );
    this.wait.until( ExpectedConditions.presenceOfElementLocated( By.xpath( "//div[@id='sample']/div[2]/div/span" ) ) );

    String value = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='sample']/div[2]/div/span" ) );
    for ( int i = 0; i < 100; i++ ) {
      if ( value.equals( "4787.772727272727" ) ) {
        break;
      } else {
        value = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='sample']/div[2]/div/span" ) );
      }
    }
    assertEquals( "4787.772727272727", value );
  }
}
