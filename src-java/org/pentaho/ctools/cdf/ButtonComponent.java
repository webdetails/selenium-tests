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
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.PageUrl;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * Testing the functionalities related with Button Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class ButtonComponent {
  //Instance of the driver (browser emulator)
  private final WebDriver driver = CToolsTestSuite.getDriver();
  //Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();

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
    // The URL for the ButtonComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Documentation/Component Reference/Core Components/ButtonComponent
    this.driver.get( PageUrl.BUTTON_COMPONENT );

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
    // Wait for title become visible and with value 'Community Dashboard Framework'
    String pageTitle = this.elemHelper.WaitForTitle( this.driver, "Community Dashboard Framework" );
    // Wait for visibility of 'VisualizationAPIComponent'
    String sampleTitle = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ), "ButtonComponent" );

    // Validate the sample that we are testing is the one
    assertEquals( "Community Dashboard Framework", pageTitle );
    assertEquals( "ButtonComponent", sampleTitle );
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
   *    Button Clickable
   * Description:
   *    The test case validate if the button is clickable and when click an
   *    alert is displayed.
   * Steps:
   *    1. Click in button
   *    2. Check for alert
   */
  @Test
  public void tc3_ClickableButton_AlertMessageDisplayed() {
    /*
     * ## Step 1
     */
    String buttonText = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//button" ) );
    assertEquals( "A button", buttonText );
    this.elemHelper.FindElement( this.driver, By.xpath( "//button" ) ).click();

    /*
     * ## Step 2
     */
    String confirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( this.driver );
    String buttonTextAfterClick = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//button" ), "Yes, a clickable button" );

    assertEquals( "Button was clicked", confirmationMsg );
    assertEquals( "Yes, a clickable button", buttonTextAfterClick );
  }
}
