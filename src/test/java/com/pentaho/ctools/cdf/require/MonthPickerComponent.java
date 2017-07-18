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
import static org.testng.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with Month Picker Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class MonthPickerComponent extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( MonthPickerComponent.class );

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Open Sample Page
   */
  @Test
  public void tc0_OpenSamplePage_Display() {
    this.log.info( "tc0_OpenSamplePage_Display" );

    // The URL for the MonthPickerComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Require Samples/Documentation/Component Reference/Core Components/MonthPickerComponent
    this.elemHelper.Get( driver, PageUrl.MONTH_PICKER_COMPONENT_REQUIRE );

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
    // Wait for visibility of 'MonthPickerComponent'
    String expectedSampleTitle = "MonthPickerComponent";
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
   *    Select Month
   * Description:
   *    The test case pretends to validate an alert is displayed after select
   *    a month and the alert displayed the selected month.
   * Steps:
   *    1. Pick a month and check for alert
   */
  @Test
  public void tc3_SelectMonth_AlertDisplayed() {
    this.log.info( "tc3_SelectMonth_AlertDisplayed" );

    /*
     * ## Step 1
     */
    Calendar c = Calendar.getInstance();
    c.add( Calendar.YEAR, 2006 - c.get( Calendar.YEAR ) );
    c.add( Calendar.MONTH, 2 );
    Date timeToPick = c.getTime();

    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM" );
    String strTimeToPick = sdf.format( timeToPick );

    Select select = new Select( this.elemHelper.FindElement( driver, By.id( "monthPickerComponent" ) ) );
    select.selectByValue( strTimeToPick );

    String confirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( confirmationMsg, "You chose: " + strTimeToPick );
  }
}
