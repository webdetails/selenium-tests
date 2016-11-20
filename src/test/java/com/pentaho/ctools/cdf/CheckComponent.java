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
 * Testing the functionalities related with Check Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CheckComponent extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CheckComponent.class );

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Open Sample
   */
  @Test
  public void tc0_OpenSamplePage_Dipslay() {
    this.log.info( "tc0_OpenSamplePage_Dipslay" );

    // The URL for the CheckComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Documentation/Component Reference/Core Components/CheckComponent
    this.elemHelper.Get( driver, PageUrl.CHECK_COMPONENT );

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

    /**
     * ##Step1
     */
    // Wait for title become visible and with value 'Community Dashboard Framework'
    String expectedPageTitle = "Community Dashboard Framework";
    String actualPageTitle = this.elemHelper.WaitForTitle( driver, expectedPageTitle );
    // Wait for visibility of 'VisualizationAPIComponent'
    String expectedSampleTitle = "CheckComponent";
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
   * Description:
   *    Reload the sample (not refresh page).
   * Steps:
   *    1. Click in Code and then click in button 'Try me'.
   */
  @Test
  public void tc2_ReloadSample_SampleReadyToUse() {
    this.log.info( "tc2_ReloadSample_SampleReadyToUse" );
    // ## Step 1
    // Render again the sample
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='example']/ul/li[2]/a" ) );
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='code']/button" ) );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    // Now sample element must be displayed
    assertTrue( this.elemHelper.FindElement( driver, By.id( "sample" ) ).isDisplayed() );
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Check options
   * Description:
   *    Here we pretend to check each option one by one and validate if an alert
   *    is raised with correct message.
   * Steps:
   *    1. Check in Southern and validate alert
   *    2. Check in Eastern and validate alert
   *    3. Check in Central and validate alert
   *    4. Check in Western and validate alert
   */
  @Test
  public void tc3_CheckEachOption_AfterCheckAnAlertIsDisplayed() {
    this.log.info( "tc3_CheckEachOption_AfterCheckAnAlertIsDisplayed" );
    String confirmationMsg = "";
    /*
     *  ## Step 1
     */
    //Click in Southern
    this.elemHelper.ClickJS( driver, By.xpath( "//input[@name='regionSelector' and @value='Southern']" ) );
    confirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( confirmationMsg, "you chose: Southern" );

    /*
     * ## Step 2
     */
    //Click in Eastern
    this.elemHelper.ClickJS( driver, By.xpath( "//input[@name='regionSelector' and @value='Eastern']" ) );
    confirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( confirmationMsg, "you chose: Southern,Eastern" );

    /*
     * ## Step 3
     */
    //Click in Central
    this.elemHelper.ClickJS( driver, By.xpath( "//input[@name='regionSelector' and @value='Central']" ) );
    confirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( confirmationMsg, "you chose: Southern,Eastern,Central" );

    /*
     * ## Step 4
     */
    //Click in Western
    this.elemHelper.ClickJS( driver, By.xpath( "//input[@name='regionSelector' and @value='Western']" ) );
    confirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( confirmationMsg, "you chose: Southern,Eastern,Central,Western" );
  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name:
   *    Unchecked options
   * Description:
   *    Here we pretend to unchecked each option one by one and validate if an
   *    alert is raised with correct message.
   * Steps:
   *    1. Unchecked Southern and validate alert
   *    2. Unchecked Eastern and validate alert
   *    3. Unchecked Central and validate alert
   *    4. Unchecked Western and validate alert
   */
  @Test
  public void tc4_UncheckedEachOption_AfterUncheckAnAlertIsDisplayed() {
    this.log.info( "tc4_UncheckedEachOption_AfterUncheckAnAlertIsDisplayed" );
    String confirmationMsg = "";

    /*
     * ## Step 1
     */
    //Click in Southern
    this.elemHelper.ClickJS( driver, By.xpath( "//input[@name='regionSelector' and @value='Southern']" ) );

    confirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( confirmationMsg, "you chose: Eastern,Central,Western" );

    /*
     * ## Step 2
     */
    //Click in Eastern
    this.elemHelper.ClickJS( driver, By.xpath( "//input[@name='regionSelector' and @value='Eastern']" ) );
    confirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( confirmationMsg, "you chose: Central,Western" );

    /*
     * ## Step 3
     */
    //Click in Central
    this.elemHelper.ClickJS( driver, By.xpath( "//input[@name='regionSelector' and @value='Central']" ) );
    confirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( confirmationMsg, "you chose: Western" );

    /*
     * ## Step 4
     */
    //Click in Western
    this.elemHelper.ClickJS( driver, By.xpath( "//input[@name='regionSelector' and @value='Western']" ) );
    confirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( confirmationMsg, "you chose: " );
  }

  /**
   * ############################### Test Case 5 ###############################
   *
   * Test Case Name:
   *    Check and Unchecked Arbitrary
   * Description:
   *    Here we pretend to check and unchecked arbitrary and validate the alert
   *    message according each action (check and unchecked).
   * Steps:
   *    1. Check and unchecked arbitrary, and validate alert message
   */
  @Test
  public void tc5_UncheckedEachOption_AfterUncheckAnAlertIsDisplayed() {
    this.log.info( "tc5_UncheckedEachOption_AfterUncheckAnAlertIsDisplayed" );
    String confirmationMsg = "";

    /*
     * ##Step 1
     */
    //Click in Central
    this.elemHelper.ClickJS( driver, By.xpath( "//input[@name='regionSelector' and @value='Central']" ) );
    confirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( confirmationMsg, "you chose: Central" );

    //Click in Southern
    this.elemHelper.ClickJS( driver, By.xpath( "//input[@name='regionSelector' and @value='Southern']" ) );
    confirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( confirmationMsg, "you chose: Southern,Central" );

    //UnChecked Southern
    this.elemHelper.ClickJS( driver, By.xpath( "//input[@name='regionSelector' and @value='Southern']" ) );
    confirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( confirmationMsg, "you chose: Central" );

    //Click in Western
    this.elemHelper.ClickJS( driver, By.xpath( "//input[@name='regionSelector' and @value='Western']" ) );
    confirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( confirmationMsg, "you chose: Central,Western" );

    //Click in Western
    this.elemHelper.ClickJS( driver, By.xpath( "//input[@name='regionSelector' and @value='Eastern']" ) );
    confirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( confirmationMsg, "you chose: Eastern,Central,Western" );

    //Unchecked Central
    this.elemHelper.ClickJS( driver, By.xpath( "//input[@name='regionSelector' and @value='Central']" ) );
    confirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( confirmationMsg, "you chose: Eastern,Western" );
  }
}
