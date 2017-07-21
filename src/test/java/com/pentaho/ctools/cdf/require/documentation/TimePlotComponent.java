/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2016 by Pentaho : http://www.pentaho.com
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
package com.pentaho.ctools.cdf.require.documentation;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with Time Plot Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class TimePlotComponent extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( TimePlotComponent.class );

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Open Sample Page
   */
  @Test
  public void tc0_OpenSamplePage_Dsiplay() {
    this.log.info( "tc1_PageContent_DisplayTitle" );

    // The URL for the TimePlotComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Require Samples/Documentation/Component Reference/Core Components/TimePlotComponent
    this.elemHelper.Get( driver, PageUrl.TIME_COMPONENT_REQUIRE );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 5 );
    this.elemHelper.WaitForElementNotPresent( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Open Sample
   * Description:
   *    Open Sample
   * Steps:
   *    1. Open Sample
   */
  @Test
  public void tc1_PageContent_DisplayTitle() {
    this.log.info( "tc1_PageContent_DisplayTitle" );
    // Wait for title become visible and with value 'Community Dashboard Framework'
    String pageTitle = this.elemHelper.WaitForTitle( driver, "Community Dashboard Framework" );
    // Wait for visibility of 'timePlotComponent'
    String sampleTitle = this.elemHelper.WaitForTextPresence( driver, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ), "timePlotComponent" );

    // Validate the sample that we are testing is the one
    assertEquals( pageTitle, "Community Dashboard Framework" );
    assertEquals( sampleTitle, "timePlotComponent" );
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
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='example']/ul/li[2]/a" ) );
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='code']/button" ) );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 5 );
    this.elemHelper.WaitForElementNotPresent( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    // Now sample element must be displayed
    assertTrue( this.elemHelper.FindElement( driver, By.id( "sample" ) ).isDisplayed() );

    //Check the number of divs with id 'SampleObject'
    //Hence, we guarantee when click Try Me the previous div is replaced
    int nSampleObject = this.elemHelper.FindElements( driver, By.id( "sampleObject" ) ).size();
    assertEquals( nSampleObject, 1 );

    //It could be possible to raise an error of "Error processing component" and the workaround is refresh the page.
    WebElement chart = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div.timeplot-container.timeplot" ), 2 );
    if ( chart == null ) {
      driver.navigate().refresh();
      this.log.debug( "Refreshing" );
      this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 5 );
      this.elemHelper.WaitForElementNotPresent( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
      chart = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div.timeplot-container.timeplot" ), 2 );
      if ( chart == null ) {
        driver.navigate().refresh();
        this.log.debug( "Refreshing" );
        this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 5 );
        this.elemHelper.WaitForElementNotPresent( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
        chart = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div.timeplot-container.timeplot" ), 2 );
        assertNotNull( chart );
      }
    }
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
   *    1. Check if the graphic is presented
   *    2. Move mouse over graphic and check the expected value for Total Price
   */
  @Test
  public void tc3_MouseOverPlot_TotalPriceChanged() {
    this.log.info( "tc3_MouseOverPlot_TotalPriceChanged" );

    /*
     * ## Step 1
     */
    WebElement sampleObj = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='sampleObject']/div/span" ) );
    assertNotNull( sampleObj );
    assertEquals( "total order income", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObject']/div/span" ) ) );
    assertEquals( "Total Price", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObject']/div/span[2]" ) ) );

    WebElement element2004 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[contains(text(), '2004')]" ) );
    assertNotNull( element2004 );

    /*
     * ## Step 2
     */
    this.elemHelper.MoveToElement( driver, By.cssSelector( "canvas.timeplot-canvas" ), 10, 10 );

    String valueChartExpected = "54702";
    String valueChart = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@class='timeplot-div timeplot-valueflag']" ) );
    assertEquals( valueChartExpected, valueChart );
    assertTrue( valueChart.startsWith( valueChartExpected ) );
  }
}
