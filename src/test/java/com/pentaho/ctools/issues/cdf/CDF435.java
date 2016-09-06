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
package com.pentaho.ctools.issues.cdf;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.selenium.BaseTest;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDF-435
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-922
 *
 * NOTE
 * To test this script it is required to have CDF plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDF435 extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDF435.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert Prpt Component is properly loaded and working
   *
   * Description:
   *    The test pretends validate the CDF-435 issue, so using the specific dashboard created to replicate this issue
   *    make sure that the Prpt Component is properly loaded and working as expected.
   *
   * Steps:
   *    1. Assert Elements and Text on Dashboard
   *    2. Select 2004 on 'select multiple=""'
   *    3. Assert Elements and Text on Dashboard
   */
  @Test
  public void tc01_PrptComponent_RenderedResponsive() {
    this.log.info( "tc01_PrptComponent_RenderedResponsive" );

    /*
     * ## Step 1
     */
    //Go to New CDE Dashboard
    driver.get( baseUrl + "api/repos/%3Apublic%3AIssues%3ACDF%3ACDF-435%3AIssue_435.wcdf/generatedContent" );

    // Wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 90 );

    //assert Elements loaded
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "Panel_1" ) );
    assertNotNull( element );

    //focus iframe
    WebElement elementFrame = this.elemHelper.FindElement( driver, By.xpath( "//iframe[@name='report_prptFrame']" ), 180 );
    assertNotNull( elementFrame );
    WebDriver frame = driver.switchTo().frame( elementFrame );

    element = this.elemHelper.WaitForElementPresenceAndVisible( frame, By.id( "pageControl" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( frame, By.id( "reportControlPanel" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( frame, By.xpath( "//div[@id='promptPanel']//button" ) );
    assertNotNull( element );
    String buttonText = this.elemHelper.WaitForElementPresentGetText( frame, By.xpath( "//div[@id='promptPanel']//button" ) );
    assertEquals( "View Report", buttonText );
    element = this.elemHelper.WaitForElementPresenceAndVisible( frame, By.xpath( "//input[@value='[Time].[2003]']" ) );
    assertNotNull( element );
    String yearText = this.elemHelper.GetInputValue( frame, By.xpath( "//input[@value='[Time].[2003]']" ) );
    assertEquals( "[Time].[2003]", yearText );

    //focus iframe2
    elementFrame = this.elemHelper.FindElement( driver, By.xpath( "//iframe[@id='reportContent']" ) );
    frame = driver.switchTo().frame( elementFrame );

    //Check content of table
    String r1c1Text = this.elemHelper.WaitForElementPresentGetText( frame, By.xpath( "//tbody/tr[2]/td" ) );
    String r1c2Text = this.elemHelper.WaitForElementPresentGetText( frame, By.xpath( "//tbody/tr[2]/td[2]" ) );
    String r1c3Text = this.elemHelper.WaitForElementPresentGetText( frame, By.xpath( "//tbody/tr[2]/td[3]" ) );
    String r1c4Text = this.elemHelper.WaitForElementPresentGetText( frame, By.xpath( "//tbody/tr[2]/td[4]" ) );
    String r2c1Text = this.elemHelper.WaitForElementPresentGetText( frame, By.xpath( "//tbody/tr[3]/td" ) );
    String r2c2Text = this.elemHelper.WaitForElementPresentGetText( frame, By.xpath( "//tbody/tr[3]/td[2]" ) );
    String r2c3Text = this.elemHelper.WaitForElementPresentGetText( frame, By.xpath( "//tbody/tr[3]/td[3]" ) );
    String r2c4Text = this.elemHelper.WaitForElementPresentGetText( frame, By.xpath( "//tbody/tr[3]/td[4]" ) );
    String r3c1Text = this.elemHelper.WaitForElementPresentGetText( frame, By.xpath( "//tbody/tr[4]/td" ) );
    String r3c2Text = this.elemHelper.WaitForElementPresentGetText( frame, By.xpath( "//tbody/tr[4]/td[2]" ) );
    String r3c3Text = this.elemHelper.WaitForElementPresentGetText( frame, By.xpath( "//tbody/tr[4]/td[3]" ) );
    String r3c4Text = this.elemHelper.WaitForElementPresentGetText( frame, By.xpath( "//tbody/tr[4]/td[4]" ) );
    assertEquals( "Euro+ Shopping Channel", r1c1Text );
    assertEquals( "$ 210,228", r1c2Text );
    assertEquals( "2,153", r1c3Text );
    assertEquals( "5.72 %", r1c4Text );
    assertEquals( "Mini Gifts Distributors Ltd.", r2c1Text );
    assertEquals( "$ 185,128", r2c2Text );
    assertEquals( "1,898", r2c3Text );
    assertEquals( "5.03 %", r2c4Text );
    assertEquals( "Dragon Souveniers, Ltd.", r3c1Text );
    assertEquals( "$ 165,686", r3c2Text );
    assertEquals( "1,452", r3c3Text );
    assertEquals( "4.51 %", r3c4Text );

    /*
     * ## Step 2
     */
    driver.switchTo().defaultContent();
    Select select = new Select( this.elemHelper.FindElement( driver, By.xpath( "//div[@id='Panel_1']/select" ) ) );
    select.deselectAll();
    select.selectByVisibleText( "2004" );

    // Wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    /*
     * ## Step 3
     */
    //focus iframe
    elementFrame = this.elemHelper.FindElement( driver, By.xpath( "//iframe[@name='report_prptFrame']" ) );
    frame = driver.switchTo().frame( elementFrame );

    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='promptPanel']//button" ) );
    assertNotNull( element );
    buttonText = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='promptPanel']//button" ) );
    assertEquals( "View Report", buttonText );
    element = this.elemHelper.WaitForElementPresenceAndVisible( frame, By.xpath( "//input[@value='[Time].[2004]']" ) );
    assertNotNull( element );
    yearText = this.elemHelper.GetInputValue( frame, By.xpath( "//input[@value='[Time].[2004]']" ) );
    assertEquals( "[Time].[2004]", yearText );

    //focus iframe2
    elementFrame = this.elemHelper.FindElement( driver, By.xpath( "//iframe[@id='reportContent']" ) );
    frame = driver.switchTo().frame( elementFrame );

    //Check content of table
    r1c1Text = this.elemHelper.WaitForElementPresentGetText( frame, By.xpath( "//tbody/tr[2]/td" ) );
    r1c2Text = this.elemHelper.WaitForElementPresentGetText( frame, By.xpath( "//tbody/tr[2]/td[2]" ) );
    r1c3Text = this.elemHelper.WaitForElementPresentGetText( frame, By.xpath( "//tbody/tr[2]/td[3]" ) );
    r1c4Text = this.elemHelper.WaitForElementPresentGetText( frame, By.xpath( "//tbody/tr[2]/td[4]" ) );
    r2c1Text = this.elemHelper.WaitForElementPresentGetText( frame, By.xpath( "//tbody/tr[3]/td" ) );
    r2c2Text = this.elemHelper.WaitForElementPresentGetText( frame, By.xpath( "//tbody/tr[3]/td[2]" ) );
    r2c3Text = this.elemHelper.WaitForElementPresentGetText( frame, By.xpath( "//tbody/tr[3]/td[3]" ) );
    r2c4Text = this.elemHelper.WaitForElementPresentGetText( frame, By.xpath( "//tbody/tr[3]/td[4]" ) );
    r3c1Text = this.elemHelper.WaitForElementPresentGetText( frame, By.xpath( "//tbody/tr[4]/td" ) );
    r3c2Text = this.elemHelper.WaitForElementPresentGetText( frame, By.xpath( "//tbody/tr[4]/td[2]" ) );
    r3c3Text = this.elemHelper.WaitForElementPresentGetText( frame, By.xpath( "//tbody/tr[4]/td[3]" ) );
    r3c4Text = this.elemHelper.WaitForElementPresentGetText( frame, By.xpath( "//tbody/tr[4]/td[4]" ) );
    assertEquals( "Euro+ Shopping Channel", r1c1Text );
    assertEquals( "$ 375,268", r1c2Text );
    assertEquals( "3,912", r1c3Text );
    assertEquals( "7.52 %", r1c4Text );
    assertEquals( "Mini Gifts Distributors Ltd.", r2c1Text );
    assertEquals( "$ 256,474", r2c2Text );
    assertEquals( "2,425", r2c3Text );
    assertEquals( "5.14 %", r2c4Text );
    assertEquals( "Australian Collectors, Co.", r3c1Text );
    assertEquals( "$ 140,860", r3c2Text );
    assertEquals( "1,335", r3c3Text );
    assertEquals( "2.82 %", r3c4Text );
  }
}
