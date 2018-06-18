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
package com.pentaho.ctools.cde.samples.require.reference.others;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with Filter Component (Sniff Test).
 *
 * Naming convention for test: 'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class FilterReference extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( FilterReference.class );

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Open Sample Page
   */
  @Test
  public void tc00_OpenSamplePage_Display() {
    this.log.info( "tc00_OpenSamplePage_Display" );

    /*
     * ## Step 1
     */
    //Open Filter Component Reference sample
    this.elemHelper.Get( driver, PageUrl.FILTER_REFERENCE_COMPONENT_REQUIRE );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 5 );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    //Get Page Title
    String expectedPageTitle = "filter_reference";
    String actualPageTitle = this.elemHelper.WaitForTitle( driver, expectedPageTitle );
    assertEquals( actualPageTitle, expectedPageTitle );
    //Get Title of sample
    String expectedSampleTitle = "Filter Component Reference";
    String actualSampleTitle = this.elemHelper.WaitForTextPresence( driver, By.xpath( "//div[@id='ref_intro_quickref']/h1" ), expectedSampleTitle );
    assertEquals( actualSampleTitle, expectedSampleTitle );

    //Assert presence of elements
    WebElement singleFilter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "singleFilterObj_group" ) );
    assertNotNull( singleFilter );
    WebElement multipleFilter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "multiFilterObj_group" ) );
    assertNotNull( multipleFilter );
    WebElement singleSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_group']//span[@class='filter-root-info-selected-item']" ) );
    assertNotNull( singleSelector );
    WebElement multiSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//span[@class='filter-root-info-selected-items']" ) );
    assertNotNull( multiSelector );
    WebElement singleText = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "txt_singleSelectionObj_group_col" ) );
    assertNotNull( singleText );
    WebElement multiText = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "txt_multiSelectionObj_group_col" ) );
    assertNotNull( multiText );
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Sniff test to Filter Component Reference
   *
   * Description:
   *    This test is to assert simple functionality of sample
   *
   * Steps:
   *    1. Click Single Selection, search for eighteen and assert it has one result
   *    2. Select it. Assert text changed and selector closed
   *    3. Click Multiple Selection select a value and click cancel. Assert no change to text
   *    4. Click again, select multiple values, click apply and assert text changed
   *
   */
  @Test
  public void tc01_FilterComponent_FilterReference() {
    this.log.info( "tc01_FilterComponent_FilterReference" );

    /*
     * ## Step 1
     */
    //Click Single Selector
    WebElement singleSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_group']//span[@class='filter-root-info-selected-item']" ) );
    assertNotNull( singleSelector );
    singleSelector.click();

    //search for eighteen and assert only option
    WebElement filterSearch = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "input.filter-filter-input" ) );
    assertNotNull( filterSearch );
    filterSearch.sendKeys( "eighteen" );
    WebElement filterOption = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_group']//div[@class='filter-root-items']//div[@title='Eighteen']" ) );
    assertNotNull( filterOption );
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='singleFilterObj_group']//div[@class='filter-root-items']/div" ) ) );

    /*
     * ## Step 2
     */
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='singleFilterObj_group']//div[@class='filter-root-items']//div[@title='Eighteen']/../div" ) );
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='filter-root-items']" ) ) );
    String selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='singleFilterObj_group']//span[@class='filter-root-info-selected-item']" ) );
    assertEquals( "Eighteen", selectedString );
    String selectedItem = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "txt_singleSelectionObj_group_col" ) );
    assertEquals( "Result: [\"[Ones].[Eighteen]\"]", selectedItem );

    /*
     * ## Step 3
     */
    WebElement multiSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//span[@class='filter-root-info-selected-items']" ) );
    assertNotNull( multiSelector );
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='multiFilterObj_group']//span[@class='filter-root-info-selected-items']" ) );
    WebElement selectThree = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@title='Three']/../div" ) );
    assertNotNull( selectThree );
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@title='Three']/../div" ) );
    WebElement cancelButton = this.elemHelper.FindElement( driver, By.xpath( "//button[contains(text(),'Cancel')]" ) );
    assertNotNull( cancelButton );
    this.elemHelper.ClickJS( driver, By.xpath( "//button[contains(text(),'Cancel')]" ) );
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//button[contains(text(),'Cancel')]" ) ) );
    String selectedMultiString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='multiFilterObj_group']//span[@class='filter-root-info-selected-items']" ) );
    assertEquals( "None", selectedMultiString );
    String selectedMultiItem = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "txt_multiSelectionObj_group_col" ) );
    assertEquals( "Result: [] Selected items:", selectedMultiItem );

    /*
     * ## Step 4
     */
    //Open multi selector
    multiSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//span[@class='filter-root-info-selected-items']" ) );
    assertNotNull( multiSelector );
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='multiFilterObj_group']//span[@class='filter-root-info-selected-items']" ) );
    //Search for th, assert shown results and select shown options
    this.elemHelper.SendKeys( driver, By.cssSelector( "div#multiFilterObj_group input.filter-filter-input" ), "th" );
    WebElement groupZeroes = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "  //div[@id='multiFilterObj_group']//div[contains(text(),'Zeroes')]" ) );
    assertNotNull( groupZeroes );
    selectThree = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@title='Three']/../div" ) );
    assertNotNull( selectThree );
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@title='Three']/../div" ) );
    WebElement groupOnes = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "  //div[@id='multiFilterObj_group']//div[contains(text(),'Ones')]" ) );
    assertNotNull( groupOnes );
    WebElement selectThirteen = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@title='Thirteen']/../div" ) );
    assertNotNull( selectThirteen );
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@title='Thirteen']/../div" ) );
    WebElement selectTwentyThree = this.elemHelper.FindElementInvisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@title='Twenty-three']" ) );
    assertNotNull( selectTwentyThree );
    WebElement applyButton = this.elemHelper.FindElement( driver, By.xpath( "//button[contains(text(),'Apply')]" ) );
    assertNotNull( applyButton );
    this.elemHelper.ClickJS( driver, By.xpath( "//button[contains(text(),'Apply')]" ) );
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//button[contains(text(),'Apply')]" ) ) );

    //Assert correct values selected
    selectedMultiString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='multiFilterObj_group']//span[@class='filter-root-info-selected-items']" ) );
    assertEquals( "2 / 29", selectedMultiString );
    selectedMultiItem = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "txt_multiSelectionObj_group_col" ) );
    assertEquals( selectedMultiItem, "Result: [\"[Zeroes].[Three]\",\"[Ones].[Thirteen]\"] Selected items: [Zeroes].[Three] [Ones].[Thirteen]" );
  }
}
