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
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with component Date Range Input.
 *
 * Naming convention for test: 'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class DateRangeInputComponent extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Open Sample Page
   */
  @Test
  public void tc0_OpenSamplePage_Display() {
    // The URL for the DateRangeInputComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Documentation/Component Reference/Core Components/DataRangeInputComponent
    driver.get( baseUrl + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:43-DateRangeInputComponent:date_range_component.xcdf/generatedContent" );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
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
    wait.until( ExpectedConditions.titleContains( "Community Dashboard Framework" ) );
    // Wait for visibility of 'VisualizationAPIComponent'
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );

    // Validate the sample that we are testing is the one
    assertEquals( "Community Dashboard Framework", driver.getTitle() );
    assertEquals( "DateRangeInputComponent", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );
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
    this.elemHelper.FindElement( driver, By.xpath( "//div[@id='example']/ul/li[2]/a" ) ).click();
    this.elemHelper.FindElement( driver, By.xpath( "//div[@id='code']/button" ) ).click();

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
   *    Set Date Range Today
   * Description:
   *    When click on Today option an alert is displayed with today date in
   *    range interval.
   * Steps:
   *    1. Click in Today option
   *    2. Check for Alert
   */
  @Test
  public void tc3_Today_DateIsSetSuccessful() {
    /*
     * ## Step 1
     */
    this.elemHelper.Click( driver, By.id( "myInput" ) );
    //ADD THIS LINE TO RUN IN WIN8: this.elemHelper.Click( driver, By.id( "myInput2" ) );
    this.elemHelper.FindElement( driver, By.linkText( "Today" ) ).sendKeys( Keys.ENTER );

    /*
     * ## Step 2
     */
    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
    Date dNow = new Date();
    String strToday = sdf.format( dNow );

    wait.until( ExpectedConditions.alertIsPresent() );
    Alert alert = driver.switchTo().alert();
    String confirmationMsg = alert.getText();
    String expectedCnfText = "You chose from " + strToday + " to " + strToday;
    alert.accept();

    assertEquals( confirmationMsg, expectedCnfText );
  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name:
   *    Set Date Range for the Last Seven Days.
   * Description:
   *    When click on Last 7 Days option an alert is displayed with 7 days
   *    before today and the today date.
   * Steps:
   *    1. Click in Last 7 Days option
   *    2. Check for Alert
   */
  @Test
  public void tc4_LastSevenDays_DateIsSetSuccessful() {
    /*
     * ## Step 1
     */
    this.elemHelper.Click( driver, By.id( "myInput" ) );
    //ADD THIS LINE TO RUN IN WIN8: this.elemHelper.Click( driver, By.id( "myInput2" ) );
    this.elemHelper.FindElement( driver, By.linkText( "Last 7 days" ) ).sendKeys( Keys.ENTER );

    /*
     * ## Step 2
     */
    Calendar c = Calendar.getInstance();
    c.add( Calendar.DAY_OF_MONTH, -7 );
    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
    Date dNow = new Date();
    String strToday = sdf.format( dNow );

    wait.until( ExpectedConditions.alertIsPresent() );
    Alert alert = driver.switchTo().alert();
    String confirmationMsg = alert.getText();
    String expectedCnfText = "You chose from " + sdf.format( c.getTime() ) + " to " + strToday;
    alert.accept();

    assertEquals( confirmationMsg, expectedCnfText );
  }

  /**
   * ############################### Test Case 5 ###############################
   *
   * Test Case Name:
   *    Set Date Range Month to Today date
   * Description:
   *    When click on Month to date option an alert is displayed with begin
   *    month day to today date.
   * Steps:
   *    1. Click in Month to date option
   *    2. Check for Alert
   */
  @Test
  public void tc5_MonthToDate_DateIsSetSuccessful() {
    /*
     * ## Step 1
     */
    this.elemHelper.Click( driver, By.id( "myInput" ) );
    //ADD THIS LINE TO RUN IN WIN8: this.elemHelper.Click( driver, By.id( "myInput2" ) );
    this.elemHelper.FindElement( driver, By.linkText( "Month to date" ) ).sendKeys( Keys.ENTER );

    /*
     * ## Step 2
     */
    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
    SimpleDateFormat sdfMonth = new SimpleDateFormat( "yyyy-MM" );
    Date dNow = new Date();
    String strToday = sdf.format( dNow );
    String strCurrentMonth = sdfMonth.format( dNow ) + "-01";

    wait.until( ExpectedConditions.alertIsPresent() );
    Alert alert = driver.switchTo().alert();
    String confirmationMsg = alert.getText();
    String expectedCnfText = "You chose from " + strCurrentMonth + " to " + strToday;
    alert.accept();

    assertEquals( confirmationMsg, expectedCnfText );
  }

  /**
   * ############################### Test Case 6 ###############################
   *
   * Test Case Name:
   *    Set Date Range Year To Date
   * Description:
   *    When click on Year To Date option an alert is displayed with begin year
   *    date to today date in range interval.
   * Steps:
   *    1. Click in Year to date option
   *    2. Check for Alert
   */
  @Test
  public void tc6_YearToDate_DateIsSetSuccessful() {
    /*
     * ## Step 1
     */
    this.elemHelper.Click( driver, By.id( "myInput" ) );
    //ADD THIS LINE TO RUN IN WIN8: this.elemHelper.Click( driver, By.id( "myInput2" ) );
    this.elemHelper.FindElement( driver, By.linkText( "Year to date" ) ).sendKeys( Keys.ENTER );

    /*
     * ## Step 2
     */
    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
    SimpleDateFormat sdfYear = new SimpleDateFormat( "yyyy" );
    Date dNow = new Date();
    String strToday = sdf.format( dNow );
    String strBeginYear = sdfYear.format( dNow ) + "-01-01";

    wait.until( ExpectedConditions.alertIsPresent() );
    Alert alert = driver.switchTo().alert();
    String confirmationMsg = alert.getText();
    String expectedCnfText = "You chose from " + strBeginYear + " to " + strToday;
    alert.accept();

    assertEquals( confirmationMsg, expectedCnfText );
  }

  /**
   * ############################### Test Case 7 ###############################
   *
   * Test Case Name:
   *    Set Date Range Today
   * Description:
   *    When click on The Previous Month option an alert is displayed with the
   *    start day of previous month to last day of previous month in range
   *    interval.
   * Steps:
   *    1. Click in The Previous Month option
   *    2. Check for Alert
   */
  @Test
  public void tc7_ThePreviousMonth_DateIsSetSuccessful() {
    /*
     * ## Step 1
     */
    this.elemHelper.Click( driver, By.id( "myInput" ) );
    //ADD THIS LINE TO RUN IN WIN8: this.elemHelper.Click( driver, By.id( "myInput2" ) );
    this.elemHelper.FindElement( driver, By.linkText( "The previous Month" ) ).sendKeys( Keys.ENTER );

    /*
     * ## Step 2
     */
    Calendar c = Calendar.getInstance();
    c.add( Calendar.MONTH, -1 );
    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM" );

    String strLastMonthEndDay = sdf.format( c.getTime() );
    String strLastMonthStartDay = sdf.format( c.getTime() ) + "-01";
    c.add( Calendar.MONTH, 1 );
    c.add( Calendar.DAY_OF_MONTH, c.get( Calendar.DAY_OF_MONTH ) * -1 );
    strLastMonthEndDay += "-" + c.get( Calendar.DAY_OF_MONTH );

    wait.until( ExpectedConditions.alertIsPresent() );
    Alert alert = driver.switchTo().alert();
    String confirmationMsg = alert.getText();
    String expectedCnfText = "You chose from " + strLastMonthStartDay + " to " + strLastMonthEndDay;
    alert.accept();

    assertEquals( confirmationMsg, expectedCnfText );
  }

  /**
   * ############################### Test Case 8 ###############################
   *
   * Test Case Name:
   *    Set Date Range Today
   * Description:
   *    When click on All Dates Before option an alert is displayed with an 
   *    interval starting from '2014-04-22'  to the selected date.
   * Steps:
   *    1. Click in Today option and then Cancel
   *    2. Click in Today option and then Done
   *    3. Check for Alert
   */
  @Test
  public void tc8_AllDatesBeforePressCancelAndSelectDate_DateIsCancelAndThenSetSuccessful() {
    /*
     * ## Step 1
     */
    this.elemHelper.Click( driver, By.id( "myInput" ) );
    this.elemHelper.FindElement( driver, By.linkText( "All Dates Before" ) ).sendKeys( Keys.ENTER );
    this.elemHelper.Click( driver, By.xpath( "(//button[contains(text(),'Cancel')])[7]" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "(//a[text()='All Dates Before'])[2]" ), 5 );
    WebElement dataPickerDisable = this.elemHelper.WaitForElementPresence( driver, By.xpath( "(//a[text()='All Dates Before'])[2]" ), 1 );
    assertFalse( dataPickerDisable.isDisplayed() );

    /*
     * ## Step 2
     */
    //Click in day 29
    this.elemHelper.Click( driver, By.id( "myInput" ) );
    //ADD THIS LINE TO RUN IN WIN8: this.elemHelper.Click( driver, By.id( "myInput2" ) );
    this.elemHelper.FindElement( driver, By.linkText( "All Dates Before" ) ).sendKeys( Keys.ENTER );
    this.elemHelper.FindElement( driver, By.linkText( "29" ) ).sendKeys( Keys.ENTER );

    /*
     * ## Step 3
     */
    wait.until( ExpectedConditions.alertIsPresent() );
    Alert alert = driver.switchTo().alert();
    String confirmationMsg = alert.getText();
    alert.accept();

    Calendar c = Calendar.getInstance();
    c.add( Calendar.YEAR, -1 );
    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
    String beginDate = sdf.format( c.getTime() );

    assertEquals( "You chose from " + beginDate + " to 2014-09-29", confirmationMsg );
  }
}
