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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with component Schedule Prpt.
 *
 * Naming convention for test: 'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class SchedulePrptComponent extends BaseTest {
  // Flag to delete schedule.
  private boolean bRemoveSchedule = false;
  // The schedule name for TC3
  private final String schNameTc3 = "SchedulePSTc3";
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( SchedulePrptComponent.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Open Sample Page
   */
  @Test
  public void tc0_OpenSamplePage_Display() {
    // The URL for the SchedulePrptComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Require Samples/Documentation/Component Reference/Core Components/SchedulePrptComponent
    this.elemHelper.Get( driver, PageUrl.SCHEDULE_PRPT_COMPONENT_REQUIRE );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
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
    this.log.info( "tc1_PageContent_DisplayTitle" );

    /*
     * ## Step 1
     */
    // Wait for title become visible and with value 'Community Dashboard Framework'
    String expectedPageTitle = "Community Dashboard Framework";
    String actualPageTitle = this.elemHelper.WaitForTitle( driver, expectedPageTitle );
    // Wait for visibility of 'SchedulePrptComponent'
    String expectedSampleTitle = "SchedulePrptComponent";
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
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Create a Schedule
   * Description:
   *    It is supposed to create a schedule and validate it was created with
   *    specify data and date.
   * Steps:
   *    1. Click to create a schedule prpt.
   *    2. Fill the form and submit
   *    3. Check for alert.
   *    4. On Schedule Manager, it is set the schedule.
   * @throws InterruptedException
   */
  @Test
  public void tc3_SchedulePrpt_ScheduleCreatedSuccessful() {
    this.log.info( "tc3_SchedulePrpt_ScheduleCreatedSuccessful" );
    this.bRemoveSchedule = true;
    String schLocation = "/public";

    //Initialize some data
    SimpleDateFormat sdf = new SimpleDateFormat( "MM/dd/yyyy" );
    SimpleDateFormat sdfDay = new SimpleDateFormat( "d" );
    Date dNow = new Date();
    Calendar c = Calendar.getInstance();
    c.setTime( dNow );
    c.add( Calendar.DATE, 1 );
    Date dTomorrow = c.getTime();
    c.add( Calendar.DATE, 40 ); //30th days from today
    Date d40days = c.getTime();

    /*
     * ## Step 1
     */
    this.elemHelper.Click( driver, By.xpath( "//div[@id='sampleObject']/button" ) );

    /*
     * ## Step 2
     */
    this.elemHelper.WaitForElementPresence( driver, By.id( "jqistate_basicState" ) );
    //Set schedule name
    this.elemHelper.FindElement( driver, By.id( "nameIn" ) ).clear();
    this.elemHelper.FindElement( driver, By.id( "nameIn" ) ).sendKeys( this.schNameTc3 );
    //Set schedule location
    this.elemHelper.FindElement( driver, By.id( "locationIn" ) ).clear();
    this.elemHelper.FindElement( driver, By.id( "locationIn" ) ).sendKeys( schLocation );
    //Select Monthly
    Select slRecurrence = new Select( this.elemHelper.FindElement( driver, By.id( "recurrId" ) ) );
    slRecurrence.selectByValue( "monthly" );
    //Select Hour
    Select slHours = new Select( this.elemHelper.FindElement( driver, By.id( "hours" ) ) );
    slHours.selectByValue( "9" );
    //Select Minutes
    Select slMinutes = new Select( this.elemHelper.FindElement( driver, By.id( "minutes" ) ) );
    slMinutes.selectByValue( "17" );
    //Select AM/FM
    Select slAMFM = new Select( this.elemHelper.FindElement( driver, By.id( "amPm" ) ) );
    slAMFM.selectByValue( "pm" );
    //Select Option 'The x y of every month
    this.elemHelper.Click( driver, By.xpath( "//div[@id='patternMonth']/input[2]" ) );
    //Select Month
    Select slOccDay = new Select( this.elemHelper.FindElement( driver, By.id( "monthOpt1Select" ) ) );
    slOccDay.selectByValue( "1" );
    //Select Wednesday
    Select slWeekday = new Select( this.elemHelper.FindElement( driver, By.id( "monthOpt2Select" ) ) );
    slWeekday.selectByValue( "3" );
    //Select Range Of Recurrence
    //Start - tomorrow
    this.elemHelper.FindElement( driver, By.id( "rangeStartIn" ) ).clear();
    this.elemHelper.FindElement( driver, By.id( "rangeStartIn" ) ).sendKeys( sdf.format( dTomorrow ) );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@class='ui-datepicker-calendar']" ) );
    WebElement dateCalendar = this.elemHelper.FindElement( driver, By.xpath( "//table[@class='ui-datepicker-calendar']" ) );
    List<WebElement> columns = dateCalendar.findElements( By.tagName( "td" ) );
    String tomorrowDay = sdfDay.format( dTomorrow );
    for ( WebElement cell : columns ) {
      String strCell = cell.getText();
      if ( strCell.equals( tomorrowDay ) ) {
        cell.findElement( By.linkText( tomorrowDay ) ).click();
        break;
      }
    }
    //End
    //Select End Date
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "endByRadio" ) );
    this.elemHelper.FindElement( driver, By.id( "endByRadio" ) ).click();
    this.elemHelper.FindElement( driver, By.id( "endByIn" ) ).sendKeys( sdf.format( d40days ) );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@class='ui-datepicker-calendar']" ) );
    WebElement dateCalendar2 = this.elemHelper.FindElement( driver, By.xpath( "//table[@class='ui-datepicker-calendar']" ) );
    List<WebElement> columns2 = dateCalendar2.findElements( By.tagName( "td" ) );
    String day = sdfDay.format( d40days );
    for ( WebElement cell2 : columns2 ) {
      String strCell2 = cell2.getText();
      if ( strCell2.equals( day ) ) {
        cell2.findElement( By.linkText( day ) ).click();
        break;
      }
    }

    //Submit Form
    this.elemHelper.Click( driver, By.id( "jqi_basicState_buttonOk" ) );
    //Wait for the new window.
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "jqistate_mailState" ) );
    this.elemHelper.Click( driver, By.id( "jqi_mailState_buttonOk" ) );

    // ## Step 3
    String actualConfirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    assertEquals( actualConfirmationMsg, "Successfully scheduled." );

    /*
     * ## Step 4
     */
    //-->Need to check if the schedule was created
    // Go to home page
    this.elemHelper.Get( driver, PageUrl.PUC );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

    //Click in Schedule
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='pucUserDropDown']/table/tbody/tr/td/div" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//iframe[@id='home.perspective']" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='mantle-perspective-switcher']/table/tbody/tr/td[2]" ) );
    this.elemHelper.Click( driver, By.xpath( "//div[@id='mantle-perspective-switcher']/table/tbody/tr/td[2]" ) );
    WebElement listMenyTr = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='customDropdownPopupMajor']/div/div/table/tbody" ) );
    List<WebElement> listMenuElementsTrs = listMenyTr.findElements( By.xpath( "//td[@class='gwt-MenuItem']" ) );
    for ( int i = 0; i < listMenuElementsTrs.size(); i++ ) {
      WebElement element = listMenuElementsTrs.get( i );
      if ( element.getText().equals( "Schedules" ) ) {
        element.click();
        break;
      }
    }
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.workspaceHeading" ) );
    // Now we are in Schedule page
    List<WebElement> listScheduleTrs = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='schedule-table']/tbody" ) ).findElements( By.tagName( "tr" ) );
    String scheduleName = "";
    String scheduleRepeats = "";
    String scheduleSourceFile = "";
    String scheduleOuputLocation = "";
    String scheduleLastRun = "";
    String scheduleNextRun = "";
    String scheduleCreatedBy = "";
    String scheduleStatus = "";
    for ( int j = 1; j <= listScheduleTrs.size(); j++ ) {
      WebElement elementFirstDiv = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='schedule-table']/tbody/tr[" + j + "]/td/div" ) );
      scheduleName = elementFirstDiv.getText();
      if ( scheduleName.equals( this.schNameTc3 ) ) {

        scheduleRepeats = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='schedule-table']/tbody/tr[" + j + "]/td[2]/div" ) );
        scheduleSourceFile = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='schedule-table']/tbody/tr[" + j + "]/td[3]/div" ) );
        scheduleOuputLocation = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='schedule-table']/tbody/tr[" + j + "]/td[4]/div" ) );
        scheduleLastRun = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='schedule-table']/tbody/tr[" + j + "]/td[5]/div" ) );
        scheduleNextRun = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='schedule-table']/tbody/tr[" + j + "]/td[6]/div" ) );
        scheduleCreatedBy = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='schedule-table']/tbody/tr[" + j + "]/td[7]/div" ) );
        scheduleStatus = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='schedule-table']/tbody/tr[" + j + "]/td[8]/div" ) );
        break;
      }
    }

    //Getting the week day of next run
    String dayOfWeek = "";
    if ( !scheduleNextRun.isEmpty() ) {
      try {
        Date dateNextRun = new SimpleDateFormat( "MMM dd, yyyy h:mm:ss a", Locale.US ).parse( scheduleNextRun );
        dayOfWeek = new SimpleDateFormat( "EE", Locale.US ).format( dateNextRun );
      } catch ( ParseException pe ) {
        this.log.error( pe.getMessage() );
      }
    }

    assertEquals( scheduleName, this.schNameTc3 );
    assertEquals( scheduleRepeats, "The second Wednesday of every month at 9:17:00 PM" );
    assertEquals( scheduleSourceFile, "/public/Steel Wheels/Widget Library/Report Snippets/InventorybyLine" );
    assertEquals( scheduleOuputLocation, schLocation );
    assertEquals( scheduleLastRun, "-" );
    assertEquals( dayOfWeek, "Wed" );
    assertEquals( scheduleCreatedBy, "admin" );
    assertEquals( scheduleStatus, "Normal" );
  }

  /**
   * This method will remove all created schedules on the tests.
   * Note - for a new schedule update code.
   */
  private void removeAllCreatedSchedules() {
    this.log.info( "removeAllCreatedSchedules" );
    // Go to home page
    this.elemHelper.Get( driver, PageUrl.PUC );
    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

    //Click in Schedule
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='pucUserDropDown']/table/tbody/tr/td/div" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//iframe[@id='home.perspective']" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='mantle-perspective-switcher']/table/tbody/tr/td[2]" ) );
    this.elemHelper.Click( driver, By.xpath( "//div[@id='mantle-perspective-switcher']/table/tbody/tr/td[2]" ) );
    WebElement listMenyTr = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='customDropdownPopupMajor']/div/div/table/tbody" ) );
    List<WebElement> listMenuElementsTrs = listMenyTr.findElements( By.xpath( "//td[@class='gwt-MenuItem']" ) );
    for ( int i = 0; i < listMenuElementsTrs.size(); i++ ) {
      WebElement element = listMenuElementsTrs.get( i );
      if ( element.getText().equals( "Schedules" ) ) {
        element.click();
        break;
      }
    }

    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.workspaceHeading" ) );

    // Now we are in Schedule page
    boolean someThingToDelete = true;
    int listElements = 0;
    int listElementsPrevious = -1;
    while ( someThingToDelete ) {
      someThingToDelete = false;
      List<WebElement> listScheduleTrs = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='schedule-table']/tbody" ) ).findElements( By.tagName( "tr" ) );
      listElements = listScheduleTrs.size();

      //The new list must be different from previous list
      while ( listElements == listElementsPrevious ) {
        listScheduleTrs = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='schedule-table']/tbody" ) ).findElements( By.tagName( "tr" ) );
        listElements = listScheduleTrs.size();
      }

      for ( int j = 1; j <= listElements; j++ ) {
        WebElement elementFirstDiv = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='schedule-table']/tbody/tr[" + j + "]/td/div" ) );

        if ( elementFirstDiv.getText().equals( this.schNameTc3 ) ) {
          elementFirstDiv.click(); //Select the row

          //Wait for row to be selected
          for ( int t = 0; t < 100; t++ ) {
            WebElement elementRow = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='schedule-table']/tbody/tr[" + j + "]" ) );

            if ( elementRow.getAttribute( "class" ).contains( "cellTableSelectedRow" ) ) {
              break;
            }
          }

          //Click to remove the schedule item (the selected row)
          this.elemHelper.Click( driver, By.cssSelector( "img.gwt-Image.pentaho-deletebutton" ) );
          this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='pentaho-dialog']" ) );
          this.elemHelper.Click( driver, By.id( "okButton" ) );

          this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='pentaho-dialog']" ) );

          someThingToDelete = true; //Continue checking if there is something to delete
          break;
        }
      }
      listElementsPrevious = listElements;
    }
  }

  @Override
  @AfterClass( alwaysRun = true )
  public void tearDownClass() {
    if ( this.bRemoveSchedule ) {
      this.log.info( "tearDownClass" );
      removeAllCreatedSchedules();
    }
  }
}
