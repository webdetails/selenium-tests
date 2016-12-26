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
package com.pentaho.ctools.cdf.require;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.Iterator;
import java.util.Set;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ActionsHelper;
import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.HttpUtils;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with jFree Chart Component.
 *
 * Naming convention for test: 'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class JFreeChartComponent extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( JFreeChartComponent.class );

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *   Open Sample Page
   */
  @Test
  public void tc0_OpenSamplePage_Display() {
    this.log.info( "tc0_OpenSamplePage_Display" );

    // The URL for the CheckComponent under CDF samples
    // This sample is in: Public/plugin-samples/CDF/Require Samples/Documentation/Component Reference/Core Components/jFreeChartComponent
    this.elemHelper.Get( driver, PageUrl.JFREE_CHART_COMPONENT_REQUIRE );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 5 );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *   Check Page Content
   *
   * Description:
   *   Validate the page content.
   *
   * Steps:
   *   1. Check for web page title and sample title.
   */
  @Test
  public void tc1_PageContent_DisplayTitle() {
    this.log.info( "tc1_PageContent_DisplayTitle" );

    // Wait for title become visible and with value 'Community Dashboard Framework'
    String expectedPageTitle = "Community Dashboard Framework";
    String actualPageTitle = this.elemHelper.WaitForTitle( driver, expectedPageTitle );
    // Wait for visibility of 'jFreeChartComponent'
    String expectedSampleTitle = "jFreeChartComponent";
    String actualSampleTitle = this.elemHelper.WaitForTextDifferentEmpty( driver, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) );

    // Validate the sample that we are testing is the one
    assertEquals( actualPageTitle, expectedPageTitle );
    assertEquals( actualSampleTitle, expectedSampleTitle );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *   Reload Sample
   *
   * Description:
   *   Reload the sample (not refresh page).
   *
   * Steps:
   *   1. Click in Code and then click in button 'Try me'.
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
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    // Now sample element must be displayed
    assertTrue( this.elemHelper.FindElement( driver, By.id( "sample" ) ).isDisplayed() );

    // Check the number of divs with id 'SampleObject'
    // Hence, we guarantee when click Try Me the previous div is replaced
    int nSampleObject = this.elemHelper.FindElements( driver, By.id( "sampleObject" ) ).size();
    assertEquals( 1, nSampleObject );
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *   Select Month
   *
   * Description:
   *   The test case pretends to validate an alert is displayed after select a
   *   month and the alert displayed the selected month.
   *
   * Steps:
   *   1. Open Pie Chart
   *   2. Click on chart
   *   3. Open Bar Chart
   *   4. Click on chart
   */
  @Test
  public void tc3_ClickOnChart_AlertDisplayed() {
    this.log.info( "tc3_ClickOnChart_AlertDisplayed" );

    String title = "";
    String confirmationMsg = "";
    String firstChart = this.elemHelper.GetAttribute( driver, By.xpath( "//img[@id='sampleObjectimage']" ), "src" );

    /*
     * ## Step 1
     */
    ActionsHelper actsHelper = new ActionsHelper( driver );
    actsHelper.MouseOver( By.xpath( "//div[contains(text(),'Details')]" ) );
    title = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "sampleObjectcaptiontitle" ) );
    assertTrue( title.equals( "Top 10 Customers" ) );
    this.elemHelper.Click( driver, By.xpath( "//div[@id='sampleObjectcaptionchartType']" ) );
    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    // Check if the generated image is different from previews, is not something static
    assertNotNull( this.elemHelper.FindElement( driver, By.xpath( "//img[@id='sampleObjectimage']" ) ) );
    String secondChart = this.elemHelper.GetAttribute( driver, By.xpath( "//img[@id='sampleObjectimage']" ), "src" );
    assertNotEquals( firstChart, secondChart );

    /*
     * ## Step 2
     */
    // Click in 'Dragon Souveniers, Ltd.'
    this.elemHelper.Click( driver, By.xpath( "//map[@id='sampleObjectimageMap']/area[4]" ) );
    confirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( confirmationMsg, "You clicked Dragon Souveniers, Ltd." );
    // Click in 'Mini Gifts Distributors Ltd'
    this.elemHelper.FindElement( driver, By.xpath( "//map[@id='sampleObjectimageMap']/area[9]" ) ).click();
    confirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( confirmationMsg, "You clicked Mini Gifts Distributors Ltd." );

    /*
     * ## Step 3
     */
    Actions acts2 = new Actions( driver );
    acts2.moveToElement( this.elemHelper.FindElement( driver, By.xpath( "//div[contains(text(),'Details')]" ) ) );
    acts2.perform();
    // Open the Pie Chart
    title = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "sampleObjectcaptiontitle" ) );
    assertTrue( title.equals( "Top 10 Customers" ) );
    this.elemHelper.Click( driver, By.xpath( "//div[@id='sampleObjectcaptionchartType']" ) );
    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    // Check if the generated image is different from previews, is not something static
    assertNotNull( this.elemHelper.FindElement( driver, By.xpath( "//img[@id='sampleObjectimage']" ) ) );
    String thirdChart = this.elemHelper.GetAttribute( driver, By.xpath( "//img[@id='sampleObjectimage']" ), "src" );
    assertNotEquals( firstChart, thirdChart );
    assertNotEquals( secondChart, thirdChart );

    /*
     * ## Step 4
     */
    // Click in 'Australian Collectors, Co.'
    this.elemHelper.FindElement( driver, By.xpath( "//map[@id='sampleObjectimageMap']/area[8]" ) ).click();
    confirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( confirmationMsg, "You clicked Australian Collectors, Co." );
    // Click in 'Down Under Souveniers, Inc'
    this.elemHelper.FindElement( driver, By.xpath( "//map[@id='sampleObjectimageMap']/area[5]" ) ).click();
    confirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( confirmationMsg, "You clicked Down Under Souveniers, Inc" );
  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name:
   *   Preview Chart
   *
   * Description:
   *   The test case pretends to validate when user press on Zoom a new window
   *   is displayed.
   *
   * Steps:
   *   1. Zoom on Bar Chart
   *   2. Zoom on Pie Chart
   */
  @Test
  public void tc4_PreviewChart_NewWindowDisplayed() {
    this.log.info( "tc4_PreviewChart_NewWindowDisplayed" );
    String title = "";
    /*
     * ## Step 1
     */
    // Check bar title
    this.elemHelper.MouseOverElementAndClick( driver, By.cssSelector( "div.caption-details" ) );
    title = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "sampleObjectcaptiontitle" ) );
    assertTrue( title.equals( "Top 10 Customers" ) );
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//div[@class='caption-bottom']" ), "style", "margin: 0px", 10 );

    // Click in Zoom
    this.elemHelper.MouseOverElementAndClick( driver, By.cssSelector( "div.caption-details" ) );
    this.elemHelper.Click( driver, By.xpath( "//div[@id='sampleObjectcaptionzoom']" ) );
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//div[@class='caption-bottom']" ), "style", "margin: 0px", 10 );
    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    WebDriver popup = null;
    String parentWindowHandle = driver.getWindowHandle(); // save the current window handle.
    Set<String> setWindows = driver.getWindowHandles();
    // wait for popup render
    this.elemHelper.WaitForNewWindow( driver );
    setWindows = driver.getWindowHandles();
    // Get popup id
    Iterator<String> windowIterator = setWindows.iterator();
    while ( windowIterator.hasNext() ) {
      String windowHandle = windowIterator.next();
      popup = driver.switchTo().window( windowHandle );
      if ( popup.getTitle().isEmpty() ) {
        break;
      }
    }

    if ( popup != null ) {
      String attrSrcPopup = this.elemHelper.GetAttribute( popup, By.cssSelector( "img" ), "src" );
      assertEquals( HttpStatus.SC_OK, HttpUtils.GetHttpStatus( attrSrcPopup ) );
      popup.close();

      driver = driver.switchTo().window( parentWindowHandle );
      assertTrue( driver.getWindowHandles().size() == 1 );
      this.elemHelper.SwitchToDefault( driver );

      /*
       * ## Step 2
       */
      // Change to pie chart
      this.elemHelper.MouseOverElementAndClick( driver, By.cssSelector( "div.caption-details" ) );
      this.elemHelper.Click( driver, By.xpath( "//div[@id='sampleObjectcaptionchartType']" ) );
      this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//div[@class='caption-bottom']" ), "style", "margin: 0px", 3 );
      // NOTE - we have to wait for loading disappear
      this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
      // Zoom
      this.elemHelper.MouseOverElementAndClick( driver, By.cssSelector( "div.caption-details" ) );
      this.elemHelper.Click( driver, By.xpath( "//div[@id='sampleObjectcaptionzoom']" ) );
      this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//div[@class='caption-bottom']" ), "style", "margin: 0px", 3 );
      // NOTE - we have to wait for loading disappear
      this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

      parentWindowHandle = driver.getWindowHandle(); // save the current window handle.
      setWindows = driver.getWindowHandles();
      // wait for popup render
      this.elemHelper.WaitForNewWindow( driver );
      setWindows = driver.getWindowHandles();
      // Get popup id
      windowIterator = setWindows.iterator();
      while ( windowIterator.hasNext() ) {
        String windowHandle = windowIterator.next();
        popup = driver.switchTo().window( windowHandle );
        if ( popup.getTitle().isEmpty() ) {
          break;
        }
      }

      attrSrcPopup = this.elemHelper.GetAttribute( popup, By.cssSelector( "img" ), "src" );
      assertEquals( HttpStatus.SC_OK, HttpUtils.GetHttpStatus( attrSrcPopup ) );

      popup.close();
      driver.switchTo().window( parentWindowHandle );
      assertTrue( driver.getWindowHandles().size() == 1 );
    } else {
      assertNotNull( popup );
    }
  }
}
