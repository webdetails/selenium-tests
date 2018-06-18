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
package com.pentaho.ctools.cdf.samples.require.documentation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with Select Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class SelectComponent extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( SelectComponent.class );

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Open Sample Page
   */
  @Test
  public void tc0_OpenSamplePage_Display() {
    this.log.info( "tc0_OpenSamplePage_Display" );

    // The URL for the SelectComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Require Samples/Documentation/Component Reference/Core Components/SelectComponent
    this.elemHelper.Get( BaseTest.driver, PageUrl.SELECT_COMPONENT_REQUIRE );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.cssSelector( "div.blockUI.blockOverlay" ), 5 );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Page Content
   * Description:
   *    Check the contents presented in the page.
   * Steps:
   *    1. Validate the title and subtitle.
   */
  @Test
  public void tc1_PageContent_DisplayTitle() {
    this.log.info( "tc1_PageContent_DisplayTitle" );

    /*
     * ## Step 1
     */
    // Wait for title become visible and with value 'Community Dashboard Framework'
    final String expectedPageTitle = "Community Dashboard Framework";
    final String actualPageTitle = this.elemHelper.WaitForTitle( BaseTest.driver, expectedPageTitle );
    // Wait for visibility of 'SelectComponent'
    final String expectedSampleTitle = "SelectComponent";
    final String actualSampleTitle = this.elemHelper.WaitForTextDifferentEmpty( BaseTest.driver, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) );

    // Validate the sample that we are testing is the one
    Assert.assertEquals( actualPageTitle, expectedPageTitle );
    Assert.assertEquals( actualSampleTitle, expectedSampleTitle );
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
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "li:nth-child(2) > a" ) );
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#code > button" ) );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.cssSelector( "div.blockUI.blockOverlay" ), 5 );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    // Now sample element must be displayed
    Assert.assertTrue( this.elemHelper.FindElement( BaseTest.driver, By.id( "sample" ) ).isDisplayed() );

    // Check the number of divs with id 'SampleObject'
    // Hence, we guarantee when click Try Me the previous div is replaced
    final int nSampleObject = this.elemHelper.FindElements( BaseTest.driver, By.id( "sampleObject" ) ).size();
    Assert.assertEquals( nSampleObject, 1 );
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Select options one by one
   * Description:
   *    We pretend validate the selection of each option one by one.
   * Steps:
   *    1. Select Dusseldorf
   *    2. Select Lisbon
   */
  @Test
  public void tc3_SelectEachItem_AlertDisplayed() {
    this.log.info( "tc3_SelectEachItem_AlertDisplayed" );
    String actualConfirmationMsg = "";

    /*
     * ## Step 1
     */
    this.elemHelper.SelectByValue( BaseTest.driver, By.cssSelector( "select" ), "2" );
    actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( BaseTest.driver );
    Assert.assertEquals( actualConfirmationMsg, "You chose: 2" );

    // ## Step 2
    this.elemHelper.SelectByValue( BaseTest.driver, By.cssSelector( "select" ), "1" );
    actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( BaseTest.driver );
    Assert.assertEquals( actualConfirmationMsg, "You chose: 1" );
  }
}
