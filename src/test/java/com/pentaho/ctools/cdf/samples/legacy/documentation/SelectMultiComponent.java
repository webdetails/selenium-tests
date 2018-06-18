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
package com.pentaho.ctools.cdf.samples.legacy.documentation;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with Select Multi Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class SelectMultiComponent extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( SelectMultiComponent.class );

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Open Sample Page
   */
  @Test
  public void tc0_OpenSamplePage_Display() {
    this.log.info( "tc0_OpenSamplePage_Display" );

    // The URL for the SelectMultiComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Documentation/Component Reference/Core Components/SelectMultiComponent
    this.elemHelper.Get( driver, PageUrl.SELECT_MULTI_COMPONENT );

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
     * ## Step 1
     */
    // Wait for title become visible and with value 'Community Dashboard Framework'
    String expectedPageTitle = "Community Dashboard Framework";
    String actualPageTitle = this.elemHelper.WaitForTitle( driver, expectedPageTitle );
    // Wait for visibility of 'SelectMultiComponent'
    String expectedSampleTitle = "SelectMultiComponent";
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

    // ## Step 1
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
   *    Select options one by one
   *
   * Description:
   *    We pretend validate the selection of each option one by one.
   *
   * Steps:
   *    1. Select Southern
   *    2. Select Eastern
   *    3. Select Central
   *    4. Select Western
   */
  @Test
  public void tc3_SelectEachItem_AlertDisplayed() {
    this.log.info( "tc3_SelectEachItem_AlertDisplayed" );
    String actualConfirmationMsg = "";

    /*
     * ## Step 1
     */
    Select list = new Select( this.elemHelper.FindElement( driver, By.cssSelector( "select" ) ) );
    list.selectByValue( "Southern" );
    actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( actualConfirmationMsg, "you chose: Southern" );

    /*
     * ## Step 2
     */
    list.deselectByValue( "Southern" );
    this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );

    list.selectByValue( "Eastern" );
    actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( actualConfirmationMsg, "you chose: Eastern" );

    /*
     * ## Step 3
     */
    list.deselectByValue( "Eastern" );
    this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );

    list.selectByValue( "Central" );
    actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( actualConfirmationMsg, "you chose: Central" );

    /*
     * ## Step 4
     */
    list.deselectByValue( "Central" );
    this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );

    list.selectByValue( "Western" );
    actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( actualConfirmationMsg, "you chose: Western" );

    //RESET
    list.deselectByValue( "Western" );
    this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name:
   *    Select and deselect all
   *
   * Description:
   *    We pretend validate the selection and deselection of all options.
   *
   * Steps:
   *    1. Select all options
   *    2. Deselect all options
   */
  @Test
  public void tc4_SelectAll_AlertDisplayed() {
    this.log.info( "tc4_SelectAll_AlertDisplayed" );
    String actualConfirmationMsg = "";

    /*
     * ## Step 1
     */
    Select list = new Select( this.elemHelper.FindElement( driver, By.cssSelector( "select" ) ) );
    list.selectByValue( "Southern" );
    actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( actualConfirmationMsg, "you chose: Southern" );

    list.selectByValue( "Eastern" );
    actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( actualConfirmationMsg, "you chose: Southern,Eastern" );

    list.selectByValue( "Central" );
    actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( actualConfirmationMsg, "you chose: Southern,Eastern,Central" );

    list.selectByValue( "Western" );
    actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( actualConfirmationMsg, "you chose: Southern,Eastern,Central,Western" );

    /*
     * ## Step 2
     */
    list.deselectByValue( "Southern" );
    actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( actualConfirmationMsg, "you chose: Eastern,Central,Western" );

    list.deselectByValue( "Eastern" );
    actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( actualConfirmationMsg, "you chose: Central,Western" );

    list.deselectByValue( "Central" );
    actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( actualConfirmationMsg, "you chose: Western" );

    list.deselectByValue( "Western" );
    actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( actualConfirmationMsg, "you chose: " );
  }

  /**
   * ############################### Test Case 5 ###############################
   *
   * Test Case Name:
   *    Select arbitrary
   *
   * Description:
   *    We pretend validate the selection options arbitrary.
   *
   * Steps:
   *    1. Select Arbitrary
   */
  //@Test
  public void tc5_SelectArbitrary_AlertDisplayed() {
    this.log.info( "tc5_SelectArbitrary_AlertDisplayed" );
    String actualConfirmationMsg = "";

    /*
     * ## Step 1
     */
    Select list = new Select( this.elemHelper.FindElement( driver, By.cssSelector( "select" ) ) );
    list.selectByValue( "Eastern" );
    actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( actualConfirmationMsg, "you chose: Eastern" );

    list.selectByValue( "Central" );
    actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( actualConfirmationMsg, "you chose: Eastern,Central" );

    list.selectByValue( "Southern" );
    actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( actualConfirmationMsg, "you chose: Southern,Eastern,Central" );

    list.deselectByValue( "Eastern" );
    actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( actualConfirmationMsg, "you chose: Southern,Central" );

    list.selectByValue( "Eastern" );
    actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( actualConfirmationMsg, "you chose: Southern,Eastern,Central" );

    list.selectByValue( "Western" );
    actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( actualConfirmationMsg, "you chose: Southern,Eastern,Central,Western" );

    list.deselectByValue( "Central" );
    actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( actualConfirmationMsg, "you chose: Southern,Eastern,Western" );
  }
}
