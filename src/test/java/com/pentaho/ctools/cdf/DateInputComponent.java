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
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with DateInputComponent.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class DateInputComponent extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( PrptComponent.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    DateInputComponent
   *
   * Description:
   *    We pretend to check the component when user pick a data an alert message
   *    is displayed indicating the date picked.
   *
   * Steps:
   *    1. Go to Pentaho solution web page.
   *    2. Render the component again.
   *    3. Choose the date '2011-10-23'.
   */
  @Test
  public void tc1_DataInput_DisplayPopupWithPickedDate() {
    this.log.info( "tc1_DataInput_DisplayPopupWithPickedDate" );
    /*
     * ## Step 1
     */
    this.elemHelper.Get( driver, PageUrl.DATEINPUT_COMPONENT );

    //NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    //Wait for visibility of 'DateInputComponent'
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) );
    // Validate the sample that we are testing is the one
    assertEquals( "Community Dashboard Framework", driver.getTitle() );
    assertEquals( "DateInputComponent", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );

    /*
     * ## Step 2
     */
    //Render again the sample
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='example']/ul/li[2]/a" ) );
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='code']/button" ) );
    //NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    //Now sample element must be displayed
    assertTrue( this.elemHelper.FindElement( driver, By.id( "sample" ) ).isDisplayed() );

    /*
     * ## Step 3
     */
    //Pick a date
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "myInput" ) );
    this.elemHelper.FindElement( driver, By.id( "myInput" ) ).sendKeys( "''" );

    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "ui-datepicker-div" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.className( "ui-datepicker-month" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.className( "ui-datepicker-year" ) );
    Select month = new Select( this.elemHelper.FindElement( driver, By.className( "ui-datepicker-month" ) ) );
    month.selectByValue( "9" );
    Select year = new Select( this.elemHelper.FindElement( driver, By.className( "ui-datepicker-year" ) ) );
    year.selectByValue( "2011" );
    //Day 23
    this.elemHelper.SendKeys( driver, By.xpath( "//table[@class='ui-datepicker-calendar']//tbody//tr[5]/td/a" ), Keys.ENTER );

    String confirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    /*##########################################################################
      EXPECTED RESULT:
      - The popup alert shall displayed the data picked.
     #########################################################################*/
    assertEquals( confirmationMsg, "You chose: 2011-10-23" );
  }
}
