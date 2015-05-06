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
package org.pentaho.ctools.cdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.cde.reference.MapComponentFullTest;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.PageUrl;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * Testing the functionalities related with Traffic Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class TrafficComponent {

  //Instance of the driver (browser emulator)
  private static WebDriver DRIVER;
  // Instance to be used on wait commands
  private static Wait<WebDriver> WAIT;
  // Log instance
  private static Logger LOG = LogManager.getLogger( MapComponentFullTest.class );

  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( DRIVER );

  /**
   * Shall initialized the test before run each test case.
   */
  @BeforeClass
  public static void setUp() {
    LOG.info( "setUp##" + TrafficComponent.class.getSimpleName() );
    DRIVER = CToolsTestSuite.getDriver();
    WAIT = CToolsTestSuite.getWait();
  }

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Load Page
   */
  @Test( timeout = 60000 )
  public void tc0_LoadPage() {
    LOG.info( "tc0_LoadPage" );

    // The URL for the CheckComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Documentation/Component Reference/Core Components/TrafficComponent
    DRIVER.get( PageUrl.TRAFFIC_COMPONENT );

    // NOTE - we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility( DRIVER, By.cssSelector( "div.blockUI.blockOverlay" ) );
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
  @Test( timeout = 60000 )
  public void tc1_PageContent_DisplayTitle() {
    LOG.info( "tc1_PageContent_DisplayTitle" );
    // Wait for title become visible and with value 'Community Dashboard Framework'
    WAIT.until( ExpectedConditions.titleContains( "Community Dashboard Framework" ) );
    // Wait for visibility of 'VisualizationAPIComponent'
    WAIT.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );

    // Validate the sample that we are testing is the one
    assertEquals( "Community Dashboard Framework", DRIVER.getTitle() );
    assertEquals( "TrafficComponent", ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );
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
  @Test( timeout = 60000 )
  public void tc2_ReloadSample_SampleReadyToUse() {
    LOG.info( "tc2_ReloadSample_SampleReadyToUse" );
    /*
     *  ## Step 1
     */
    // Render again the sample
    ElementHelper.Click( DRIVER, By.xpath( "//div[@id='example']/ul/li[2]/a" ) );
    ElementHelper.Click( DRIVER, By.xpath( "//div[@id='code']/button" ) );

    // NOTE - we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility( DRIVER, By.cssSelector( "div.blockUI.blockOverlay" ) );

    // Now sample element must be displayed
    assertTrue( ElementHelper.FindElement( DRIVER, By.id( "sample" ) ).isDisplayed() );

    //Check the number of divs with id 'SampleObject'
    //Hence, we guarantee when click Try Me the previous div is replaced
    int nSampleObject = DRIVER.findElements( By.id( "sampleObject" ) ).size();
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
  @Test( timeout = 60000 )
  public void tc3_MouseOverTrafficLight_TooltipDisplayed() {
    LOG.info( "tc3_MouseOverTrafficLight_TooltipDisplayed" );
    /*
     *  ## Step 1
     */
    WebElement elemTraffic = ElementHelper.FindElement( DRIVER, By.cssSelector( "div.img.trafficYellow" ) );
    assertNotNull( elemTraffic );

    /*
     *  ## Step 2
     */
    Actions acts = new Actions( DRIVER );
    acts.moveToElement( elemTraffic, 5, 5 );
    acts.build().perform();

    String text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@class='ui-tooltip-content']" ) );
    String expectedTextV1 = "Value: 1.43199389E8";
    String expectedTextV2 = "70000000";
    String expectedTextV3 = "150000000";
    assertTrue( text.contains( expectedTextV1 ) );
    assertTrue( text.contains( expectedTextV2 ) );
    assertTrue( text.contains( expectedTextV3 ) );
  }

  @AfterClass
  public static void tearDown() {
    //To use when class run all test cases.
    LOG.info( "tearDown##" + TrafficComponent.class.getSimpleName() );
  }
}
