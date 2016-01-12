/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2015 by Pentaho : http://www.pentaho.com
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
package com.pentaho.ctools.cde.require;

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
public class FilterAddinAccordion extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( FilterAddinAccordion.class );

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Open Sample Page
   */
  @Test
  public void tc00_OpenSamplePage_Display() {
    /*
     * ## Step 1
     */
    //Open Filter Component Add-in Reference sample
    driver.get( PageUrl.FILTER_ADDIN_COMPONENT_REQUIRE );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 5 );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    //Get Page Title
    String expectedPageTitle = "filter_addIn_reference_accordion";
    String actualPageTitle = this.elemHelper.WaitForTitle( driver, expectedPageTitle );
    assertEquals( actualPageTitle, expectedPageTitle );
    //Get Title of sample
    String expectedSampleTitle = "Filter Component Add-in Reference";
    String actualSampleTitle = this.elemHelper.WaitForTextPresence( driver, By.xpath( "//div[@id='docHeader']/h1" ), expectedSampleTitle );
    assertEquals( actualSampleTitle, expectedSampleTitle );

    //Assert presence of elements
    WebElement accordionFilter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "filterAccordion" ) );
    WebElement singleFilter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "singleFilterObj_simple" ) );
    WebElement multipleFilter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "multiFilterObj_simple" ) );
    WebElement singleGroupFilter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "singleFilterObj_group" ) );
    WebElement multiGroupFilter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "multiFilterObj_group" ) );
    assertNotNull( accordionFilter );
    assertNotNull( singleFilter );
    assertNotNull( multipleFilter );
    assertNotNull( singleGroupFilter );
    assertNotNull( multiGroupFilter );
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Sniff test to Filter Component AddIn Reference
   *
   * Description:
   *    This test is to assert simple functionality of sample 
   *
   * Steps:
   *    1. Assert opening one filter closes one that was previously opened
   *    2. Open first single filter, search, assert results, select and assert string
   *    3. Open second single filter, search, assert results, select and assert string
   *    4. Click Multiple Selection select a value and click cancel. Assert no change to text 
   *    5. Click again, select multiple values, click apply and assert text changed
   *        
   */
  @Test
  public void tc01_FilterComponent_AddInReference() {
    this.log.info( "tc01_FilterComponent_AddInReference" );

    /*
     * ## Step 1
     */
    //Click Single Selector and assert filter is opened
    WebElement singleSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#singleFilterObj_simple div.filter-collapse-icon.collapsed" ) );
    assertNotNull( singleSelector );
    this.elemHelper.ClickJS( driver, By.cssSelector( "div#singleFilterObj_simple  div.filter-collapse-icon.collapsed" ) );
    WebElement optionSingleContainer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#singleFilterObj_simple div.filter-root-body" ) );
    assertNotNull( optionSingleContainer );

    //Click Multi Group Selector, assert it is opened and that previous filter is closed
    WebElement multiGroupSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#multiFilterObj_group div.filter-collapse-icon.collapsed" ) );
    assertNotNull( multiGroupSelector );
    this.elemHelper.ClickJS( driver, By.cssSelector( "div#multiFilterObj_group div.filter-collapse-icon.collapsed" ) );
    WebElement optionMultiGroupContainer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#multiFilterObj_group div.filter-root-body" ) );
    assertNotNull( optionMultiGroupContainer );
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div#singleFilterObj_simple div.filter-root-body" ) ) );

    //Click Single Group Selector, assert it is opened and that previous filter is closed
    WebElement singleGroupSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#singleFilterObj_group div.filter-collapse-icon.collapsed" ) );
    assertNotNull( singleGroupSelector );
    this.elemHelper.ClickJS( driver, By.cssSelector( "div#singleFilterObj_group div.filter-collapse-icon.collapsed" ) );
    WebElement optionSingleGroupContainer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#singleFilterObj_group div.filter-root-body" ) );
    assertNotNull( optionSingleGroupContainer );
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div#multiFilterObj_group div.filter-root-body" ) ) );

    //Click Multi Selector, assert it is opened and that previous filter is closed
    WebElement multiSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#multiFilterObj_simple div.filter-collapse-icon.collapsed" ) );
    assertNotNull( multiSelector );
    this.elemHelper.ClickJS( driver, By.cssSelector( "div#multiFilterObj_simple div.filter-collapse-icon.collapsed" ) );
    WebElement optionGroupContainer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#multiFilterObj_simple div.filter-root-body" ) );
    assertNotNull( optionGroupContainer );
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div#singleFilterObj_group div.filter-root-body" ) ) );

    /*
     * ## Step 2
     */
    //Click Single Selector and assert filter is opened
    singleSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#singleFilterObj_simple div.filter-collapse-icon.collapsed" ) );
    assertNotNull( singleSelector );
    this.elemHelper.ClickJS( driver, By.cssSelector( "div#singleFilterObj_simple div.filter-collapse-icon.collapsed" ) );
    optionSingleContainer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#singleFilterObj_simple div.filter-root-body" ) );
    assertNotNull( optionSingleContainer );
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div#multiFilterObj_simple div.filter-root-body" ) ) );

    //Search to filter shown options
    WebElement filterSearch = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_simple']//input[@class='filter-filter-input']" ) );
    assertNotNull( filterSearch );
    filterSearch.sendKeys( "ze" );
    WebElement filterOption = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_simple']//div[@title='Zero']" ) );
    assertNotNull( filterOption );
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='singleFilterObj_simple']//div[@title='One']" ) ) );

    //Select Zero, assert filter closed and strings correct
    WebElement zeroSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_simple']//div[@title='Zero']/../div" ) );
    assertNotNull( zeroSelector );
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='singleFilterObj_simple']//div[@title='Zero']/../div" ) );
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div#singleFilterObj_simple div.filter-root-body" ) ) );
    String selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='singleFilterObj_simple']//span[@class='filter-root-info-selected-item']" ) );
    assertEquals( "Zero", selectedString );

    /*
     * ## Step 3
     */
    //Click Multi Selector and assert filter is opened
    multiSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#multiFilterObj_simple div.filter-collapse-icon.collapsed" ) );
    assertNotNull( multiSelector );
    this.elemHelper.ClickJS( driver, By.cssSelector( "div#multiFilterObj_simple div.filter-collapse-icon.collapsed" ) );
    WebElement optionMultiContainer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#multiFilterObj_simple div.filter-root-body" ) );
    assertNotNull( optionMultiContainer );

    //Select All, click Apply and assert filter closed and strings correct
    WebElement allSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#multiFilterObj_simple div.filter-root-selection-icon.none-selected" ) );
    assertNotNull( allSelector );
    this.elemHelper.ClickJS( driver, By.cssSelector( "div#multiFilterObj_simple div.filter-root-selection-icon.none-selected" ) );
    WebElement applyButton = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='multiFilterObj_simple']//button[contains(text(),'Apply')]" ) );
    assertNotNull( applyButton );
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='multiFilterObj_simple']//button[contains(text(),'Apply')]" ) );
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div#multiFilterObj_simple div.filter-root-body" ) ) );
    selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='multiFilterObj_simple']//span[@class='filter-root-info-selected-items']" ) );
    assertEquals( "All", selectedString );

    /*
     * ## Step 4
     */
    //Click Single Selector and assert filter is opened
    singleSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#singleFilterObj_group div.filter-collapse-icon.collapsed" ) );
    assertNotNull( singleSelector );
    this.elemHelper.ClickJS( driver, By.cssSelector( "div#singleFilterObj_group div.filter-collapse-icon.collapsed" ) );
    optionSingleContainer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#singleFilterObj_group div.filter-root-body" ) );
    assertNotNull( optionSingleContainer );

    //Search to filter shown options, cancel filter and assert One is shown again
    filterSearch = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_group']//input[@class='filter-filter-input']" ) );
    assertNotNull( filterSearch );
    filterSearch.sendKeys( "fif" );
    filterOption = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_group']//div[@title='Fifteen']" ) );
    assertNotNull( filterOption );
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='singleFilterObj_group']//div[@title='One']" ) ) );
    WebElement filterClear = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_group']//div[@class='filter-filter-clear']" ) );
    assertNotNull( filterClear );
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='singleFilterObj_group']//div[@class='filter-filter-clear']" ) );
    WebElement oneOption = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_group']//div[@title='One']" ) );
    assertNotNull( oneOption );

    //Select One, assert filter closed and strings correct
    WebElement oneSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_group']//div[@title='One']/../div" ) );
    assertNotNull( oneSelector );
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='singleFilterObj_group']//div[@title='One']/../div" ) );
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div#singleFilterObj_group div.filter-root-body" ) ) );
    selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='singleFilterObj_group']//span[@class='filter-root-info-selected-item']" ) );
    assertEquals( "One", selectedString );

    /*
     * ## Step 5
     */
    //Click Multi Selector and assert filter is opened
    multiSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#multiFilterObj_group div.filter-collapse-icon.collapsed" ) );
    assertNotNull( multiSelector );
    this.elemHelper.ClickJS( driver, By.cssSelector( "div#multiFilterObj_group div.filter-collapse-icon.collapsed" ) );
    optionMultiContainer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#multiFilterObj_group div.filter-root-body" ) );
    assertNotNull( optionMultiContainer );

    //Select 4 options, click Apply and assert filter closed and strings correct
    this.elemHelper.ClearAndSendKeys( driver, By.xpath( "//div[@id='multiFilterObj_group']//input[@class='filter-filter-input']" ), "twelve" );
    WebElement twelveSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@title='Twelve']/../div" ) );
    assertNotNull( twelveSelector );
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@title='Twelve']/../div" ) );
    this.elemHelper.ClearAndSendKeys( driver, By.xpath( "//div[@id='multiFilterObj_group']//input[@class='filter-filter-input']" ), "twenty" );
    WebElement twentyFiveSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@title='Twenty-two']/../div" ) );
    assertNotNull( twentyFiveSelector );
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@title='Twenty-two']/../div" ) );
    this.elemHelper.ClearAndSendKeys( driver, By.xpath( "//div[@id='multiFilterObj_group']//input[@class='filter-filter-input']" ), "six" );
    WebElement sixSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@title='Six']/../div" ) );
    assertNotNull( sixSelector );
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@title='Six']/../div" ) );
    this.elemHelper.ClearAndSendKeys( driver, By.xpath( "//div[@id='multiFilterObj_group']//input[@class='filter-filter-input']" ), "eighteen" );
    WebElement eighteenSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@title='Eighteen']/../div" ) );
    assertNotNull( eighteenSelector );
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@title='Eighteen']/../div" ) );
    applyButton = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='multiFilterObj_group']//button[contains(text(),'Apply')]" ) );
    assertNotNull( applyButton );
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='multiFilterObj_group']//button[contains(text(),'Apply')]" ) );
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div#multiFilterObj_group div.filter-root-body" ) ) );
    selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='multiFilterObj_group']//span[@class='filter-root-info-selected-items']" ) );
    assertEquals( "4 / 29", selectedString );
  }
}
