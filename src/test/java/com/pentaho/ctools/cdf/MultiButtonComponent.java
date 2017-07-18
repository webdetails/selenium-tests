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
package com.pentaho.ctools.cdf;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with MultiButton Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class MultiButtonComponent extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( MultiButtonComponent.class );

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Open Sample Page
   */
  @Test
  public void tc0_OpenSamplePage_Display() {
    this.log.info( "tc0_OpenSamplePage_Display" );
    // The URL for the MultiButtonComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Documentation/Component Reference/Core Components/MultiButtonComponent
    this.elemHelper.Get( driver, PageUrl.MULTIBUTTON_COMPONENT );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Validate Page Contents
   *
   * Description:
   *    Here we want to validate the page contents.
   *
   * Steps:
   *    1. Check the widget's title.
   */
  @Test
  public void tc1_PageContent_DisplayTitle() {
    this.log.info( "tc1_PageContent_DisplayTitle" );
    /*
     * ## Step1
     */
    // Wait for title become visible and with value 'Community Dashboard Framework'
    String expectedPageTitle = "Community Dashboard Framework";
    String actualPageTitle = this.elemHelper.WaitForTitle( driver, expectedPageTitle );
    // Wait for visibility of 'MultiButtonComponent'
    String expectedSampleTitle = "MultiButtonComponent";
    String actualSampleTitle = this.elemHelper.WaitForTextDifferentEmpty( driver, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) );

    // Validate the sample that we are testing is the one
    assertEquals( actualPageTitle, expectedPageTitle );
    assertEquals( actualSampleTitle, expectedSampleTitle );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Reload Sample
   *
   * Description:
   *    Reload the sample (not refresh page).
   *
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
    this.elemHelper.Click( driver, By.xpath( "//div[@id='example']/ul/li[2]/a" ) );
    this.elemHelper.Click( driver, By.xpath( "//div[@id='code']/button" ) );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    // Now sample element must be displayed
    assertTrue( this.elemHelper.FindElement( driver, By.id( "sample" ) ).isDisplayed() );

    //Check the number of divs with id 'SampleObject'
    //Hence, we guarantee when click Try Me the previous div is replaced
    int nSampleObject = this.elemHelper.FindElements( driver, By.id( "sampleObject" ) ).size();
    assertEquals( nSampleObject, 1 );
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Click In Each Button
   *
   * Description:
   *    The test case pretends to validate the buttons works ok.
   *
   * Steps:
   *    1. Click in Eastern
   *    2. Click in Central
   *    3. Click in Western
   *    4. Click in Southern
   */
  @Test
  public void tc3_ClickInEachOption_AlertDisplayed() {
    this.log.info( "tc3_ClickInEachOption_AlertDisplayed" );

    String actualConfirmationMsg = "";
    /*
     * ## Step 1
     */
    this.elemHelper.Click( driver, By.xpath( "//button[contains(text(),'Eastern')]" ) );
    actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( actualConfirmationMsg, "you chose: Eastern" );

    /*
     * ## Step 2
     */
    this.elemHelper.Click( driver, By.xpath( "//button[contains(text(),'Central')]" ) );
    actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( actualConfirmationMsg, "you chose: Central" );

    /*
     * ## Step 3
     */
    this.elemHelper.Click( driver, By.xpath( "//button[contains(text(),'Western')]" ) );
    actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( actualConfirmationMsg, "you chose: Western" );

    /*
     * ## Step 4
     */
    this.elemHelper.Click( driver, By.xpath( "//button[contains(text(),'Southern')]" ) );
    actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( actualConfirmationMsg, "you chose: Southern" );
  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name:
   *    Click Arbitrary in available Button
   *
   * Description:
   *    The test case pretends to validate no error occurs when we click
   *    arbitrary in the buttons.
   *
   * Steps:
   *    1. Click in Central
   *    2. Click in Southern
   *    3. Click in Eastern
   *    4. Click in Southern
   */
  @Test
  public void tc4_ClickArbitrary_AlertDisplayed() {
    this.log.info( "tc4_ClickArbitrary_AlertDisplayed" );
    String actualConfirmationMsg = "";

    /*
     * ## Step 1
     */
    this.elemHelper.Click( driver, By.xpath( "//button[contains(text(),'Central')]" ) );
    actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( actualConfirmationMsg, "you chose: Central" );

    /*
     * ## Step 2
     */
    this.elemHelper.Click( driver, By.xpath( "//button[contains(text(),'Southern')]" ) );
    actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( actualConfirmationMsg, "you chose: Southern" );

    // ## Step 3
    this.elemHelper.Click( driver, By.xpath( "//button[contains(text(),'Eastern')]" ) );
    actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( actualConfirmationMsg, "you chose: Eastern" );

    // ## Step 4
    this.elemHelper.Click( driver, By.xpath( "//button[contains(text(),'Southern')]" ) );
    actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( actualConfirmationMsg, "you chose: Southern" );
  }
}
