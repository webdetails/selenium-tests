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

package com.pentaho.ctools.cdf;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.selenium.BaseTest;
import com.pentaho.selenium.ConfigurationSettings;

/**
 * Testing the functionalities related with component Visualization API.
 *
 * Naming convention for test: 'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class VisualizationAPIComponent extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
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
    driver.get( baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A60-VisualizationAPIComponent%3Avisualization_component.xcdf/generatedContent" );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
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
    wait.until( ExpectedConditions.titleContains( "Community Dashboard Framework" ) );
    // Wait for visibility of 'VisualizationAPIComponent'
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );

    // Validate the sample that we are testing is the one
    assertEquals( "Community Dashboard Framework", driver.getTitle() );
    assertEquals( "VisualizationAPIComponent", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );
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
    String maxValue = "";

    if ( pentahoReleaseVersion.equalsIgnoreCase( ConfigurationSettings.PENTAHO_RELEASE_VERSION_6X ) ) {
      maxValue = "35659.00";
    } else {
      maxValue = "35659";
    }
    String value = this.elemHelper.WaitForTextPresence( driver, By.xpath( "//div[@id='sample']/div[2]/div/span" ), maxValue );
    assertEquals( value, maxValue );
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
   */
  @Test
  public void tc4_MinNumber_PresentCorrectValue() {
    this.log.info( "tc4_MinNumber_PresentCorrectValue" );
    /*
     * ## Step 1 - Change the option parameter to MIN and reload sample
     */
    String minValue = "";
    // Render again the sample
    this.elemHelper.FindElement( driver, By.xpath( "//div[@id='example']/ul/li[2]/a" ) ).click();

    // Wait for board load
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='code']" ) ) );
    // Change contains to MIN
    ( (JavascriptExecutor) driver ).executeScript( "$('#samplecode').text($('#samplecode').text().replace('MAX', 'MIN'));" );

    String strText = "";
    while ( strText.indexOf( "MIN" ) == -1 ) {
      strText = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "samplecode" ) );
    }

    // Click in Try me
    this.elemHelper.FindElement( driver, By.xpath( "//div[@id='code']/button" ) ).click();
    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    // Now sample element must be displayed
    assertTrue( this.elemHelper.FindElement( driver, By.id( "sample" ) ).isDisplayed() );

    /*
     * ## Step 2 - Check the presented value for MIN.
     */
    wait.until( ExpectedConditions.presenceOfElementLocated( By.xpath( "//div[@id='sample']" ) ) );
    wait.until( ExpectedConditions.presenceOfElementLocated( By.xpath( "//div[@id='sample']/div[2]/div/span" ) ) );

    if ( pentahoReleaseVersion.equalsIgnoreCase( ConfigurationSettings.PENTAHO_RELEASE_VERSION_6X ) ) {
      minValue = "490.00";
    } else {
      minValue = "0";
    }
    String value = this.elemHelper.WaitForTextPresence( driver, By.xpath( "//div[@id='sample']/div[2]/div/span" ), minValue );
    assertEquals( value, minValue );
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
   *    1. Change the option parameter to AVG and reload sample.
   *    2. Check the presented value for AVG.
   */
  @Test
  public void tc5_AvgNumber_PresentCorrectValue() {
    this.log.info( "tc5_AvgNumber_PresentCorrectValue" );
    String avgValue = "";

    /*
     * ## Step 1 - Change the option parameter to AVG and reload sample
     */
    // Render again the sample
    this.elemHelper.FindElement( driver, By.xpath( "//div[@id='example']/ul/li[2]/a" ) ).click();

    // Wait for board load
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='code']" ) ) );
    // Change contains to AVG
    ( (JavascriptExecutor) driver ).executeScript( "$('#samplecode').text($('#samplecode').text().replace('MIN', 'AVG'));" );

    String strText = "";
    while ( strText.indexOf( "AVG" ) == -1 ) {
      strText = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "samplecode" ) );
    }

    // Click in Try me
    this.elemHelper.FindElement( driver, By.xpath( "//div[@id='code']/button" ) ).click();
    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    // Now sample element must be displayed
    assertTrue( this.elemHelper.FindElement( driver, By.id( "sample" ) ).isDisplayed() );

    /*
     * ## Step 2 - Check the presented value for MIN.
     */
    wait.until( ExpectedConditions.presenceOfElementLocated( By.xpath( "//div[@id='sample']" ) ) );
    wait.until( ExpectedConditions.presenceOfElementLocated( By.xpath( "//div[@id='sample']/div[2]/div/span" ) ) );

    if ( pentahoReleaseVersion.equalsIgnoreCase( ConfigurationSettings.PENTAHO_RELEASE_VERSION_6X ) ) {
      avgValue = "4787.77";
    } else {
      avgValue = "4787.772727272727";
    }
    String value = this.elemHelper.WaitForTextPresence( driver, By.xpath( "//div[@id='sample']/div[2]/div/span" ), avgValue );
    assertEquals( value, avgValue );
  }
}
