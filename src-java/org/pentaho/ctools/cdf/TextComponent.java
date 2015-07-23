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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
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
 * Testing the functionalities related with Text Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class TextComponent {

  //Time of day
  private Date dNow = new Date();
  //Instance of the driver (browser emulator)
  private final WebDriver driver = CToolsTestSuite.getDriver();
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  //Log instance
  private final Logger log = LogManager.getLogger( TextComponent.class );
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
    // The URL for the TextComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Documentation/Component Reference/Core Components/TextComponent
    this.driver.get( PageUrl.TEXT_COMPONENT );

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
  @Test
  public void tc1_PageContent_DisplayTitle() {
    // Wait for title become visible and with value 'Community Dashboard Framework'
    String title = this.elemHelper.WaitForTitle( this.driver, "Community Dashboard Framework" );
    // Wait for visibility of 'VisualizationAPIComponent'
    String sampleTitle = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ), "TextComponent" );

    // Validate the sample that we are testing is the one
    assertEquals( "Community Dashboard Framework", title );
    assertEquals( "TextComponent", sampleTitle );
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
    this.elemHelper.ClickJS( this.driver, By.xpath( "//div[@id='example']/ul/li[2]/a" ) );
    this.elemHelper.ClickJS( this.driver, By.xpath( "//div[@id='code']/button" ) );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    this.dNow = Calendar.getInstance().getTime();

    // Now sample element must be displayed
    assertTrue( this.elemHelper.FindElement( this.driver, By.id( "sample" ) ).isDisplayed() );

    //Check the number of divs with id 'SampleObject'
    //Hence, we guarantee when click Try Me the previous div is replaced
    int nSampleObject = this.driver.findElements( By.id( "sampleObject" ) ).size();
    assertEquals( 1, nSampleObject );
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Text Component
   * Description:
   *    The component just displayed a text in sample area, so we want to
   *    validate the displayed text is according specification.
   * Steps:
   *    1. Validate the displayed text
   */
  @Test
  public void tc3_DisplayedText_ContainsExpectedText() {
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "sampleObject" ) );

    SimpleDateFormat sdf = new SimpleDateFormat( "EE MMM dd yyyy HH:mm", Locale.US );
    String strToday = sdf.format( this.dNow );

    String text = this.elemHelper.WaitForElementPresentGetText( this.driver, By.id( "sampleObject" ) );
    String expectedText = "My text generated in " + strToday;

    boolean displayTime = text.startsWith( expectedText );
    if ( !displayTime ) {
      this.log.error( "Displayed time: " + text );
      this.log.error( "Expected time: " + expectedText );
    }

    TimeZone tz = Calendar.getInstance().getTimeZone();
    int offset = tz.getOffset( System.currentTimeMillis() );
    String offsetText = String.format( "%s%02d%02d", offset >= 0 ? "+" : "-", offset / 3600000, offset / 60000 % 60 );

    Assert.assertThat( "Displayed time: " + text + "Expected time: " + expectedText, text, CoreMatchers.containsString( expectedText ) );
    Assert.assertThat( "Displayed time: " + text, text, CoreMatchers.containsString( "GMT" + offsetText ) );
  }
}
