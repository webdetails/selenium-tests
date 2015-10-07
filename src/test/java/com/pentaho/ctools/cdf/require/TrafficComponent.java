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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with Traffic Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class TrafficComponent extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( TrafficComponent.class );

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Open Sample Page
   */
  @Test
  public void tc0_OpenSamplePage_Display() {
    this.log.info( "tc0_OpenSamplePage_Display" );

    // The URL for the TrafficComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Require Samples/Documentation/Component Reference/Core Components/TrafficComponent
    driver.get( PageUrl.TRAFFIC_COMPONENT_REQUIRE );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
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
  public void tc1_PageContent_DisplayTitle() {
    this.log.info( "tc1_PageContent_DisplayTitle" );
    // Wait for title become visible and with value 'Community Dashboard Framework'
    String title = this.elemHelper.WaitForTitle( driver, "Community Dashboard Framework" );
    // Wait for visibility of 'VisualizationAPIComponent'
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );

    // Validate the sample that we are testing is the one
    assertEquals( "Community Dashboard Framework", title );
    assertEquals( "TrafficComponent", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );
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
    this.elemHelper.Click( driver, By.xpath( "//div[@id='example']/ul/li[2]/a" ) );
    this.elemHelper.Click( driver, By.xpath( "//div[@id='code']/button" ) );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

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
   *    Time Plot
   * Description:
   *    For this component we need to validate when user move mouse over plot
   *    we have new values for Total Price.
   * Steps:
   *    1. Check if the plot is presented
   *    2. Move mouse over graphic and check the expected value for Total Price
   */
  @Test
  public void tc3_MouseOverTrafficLight_TooltipDisplayed() {
    this.log.info( "tc3_MouseOverTrafficLight_TooltipDisplayed" );
    /*
     *  ## Step 1
     */
    WebElement elemTraffic = this.elemHelper.FindElement( driver, By.cssSelector( "div.img.trafficYellow" ) );
    assertNotNull( elemTraffic );

    /*
     *  ## Step 2
     */
    this.elemHelper.MoveToElement( driver, By.cssSelector( "div.img.trafficYellow" ), 5, 5 );

    String text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@class='ui-tooltip-content']" ) );
    String expectedTextV1 = "Value: 1.43199389E8";
    String expectedTextV2 = "70000000";
    String expectedTextV3 = "150000000";
    assertTrue( text.contains( expectedTextV1 ) );
    assertTrue( text.contains( expectedTextV2 ) );
    assertTrue( text.contains( expectedTextV3 ) );
  }
}
