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
package com.pentaho.ctools.cde.reference;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.BaseTest;
import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;

/**
 * Testing the functionalities related with Filter Component (Sniff Test).
 *
 * Naming convention for test: 'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class FilterComponent extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( FilterComponent.class );

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
   *    1. Open filter_reference sample and assert elements on page
   *    2. Click Single Selection, search for eighteen and assert it has one result
   *    3. Select it. Assert text changed and selector closed
   *    4. Click Multiple Selection select a value and click cancel. Assert no change to text 
   *    5. Click again, select multiple values, click apply and assert text changed
   *        
   */

  @Test
  public void tc01_FilterComponent_FilterReference() {
    this.log.info( "tc01_FilterComponent_FilterReference" );

    /*
     * ## Step 1
     */
    //Open Filter Component Reference sample
    driver.get( PageUrl.FILTER_REFERENCE_COMPONENT );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Get Title of sample
    String title = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='ref_intro_quickref']/h1" ) );
    assertEquals( "Filter Component Reference", title );

    //Assert presence of elements
    WebElement singleFilter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "singleFilterObj_group" ) );
    WebElement multipleFilter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "multiFilterObj_group" ) );
    WebElement singleSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_group']//span[@class='filter-root-info-selected-item']" ) );
    WebElement multiSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//span[@class='filter-root-info-selected-items']" ) );
    WebElement singleText = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "txt_singleSelectionObj_group" ) );
    WebElement multiText = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "txt_multiSelectionObj_group" ) );
    assertNotNull( singleFilter );
    assertNotNull( multipleFilter );
    assertNotNull( singleSelector );
    assertNotNull( multiSelector );
    assertNotNull( singleText );
    assertNotNull( multiText );

    /*
     * ## Step 2
     */
    //Click Single Selector
    singleSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_group']//span[@class='filter-root-info-selected-item']" ) );
    assertNotNull( singleSelector );
    singleSelector.click();

    //search for eighteen and assert only option
    WebElement filterSearch = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "input.filter-filter-input" ) );
    assertNotNull( filterSearch );
    filterSearch.sendKeys( "eighteen" );
    WebElement filterOption = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='filter-root-items']//div[@title='Eighteen']" ) );
    assertNotNull( filterOption );
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='filter-root-items']/div" ) ) );

    /*
     * ## Step 3
     */
    WebElement selectEighteen = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='filter-root-items']//div[@title='Eighteen']/../div" ) );
    assertNotNull( selectEighteen );
    selectEighteen.click();
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='filter-root-items']" ) ) );
    String selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='singleFilterObj_group']//span[@class='filter-root-info-selected-item']" ) );
    assertEquals( "Eighteen", selectedString );
    String selectedItem = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "txt_singleSelectionObj_group" ) );
    assertEquals( "Result: [\"[Ones].[Eighteen]\"]", selectedItem );

    /*
     * ## Step 4
     */
    multiSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//span[@class='filter-root-info-selected-items']" ) );
    assertNotNull( multiSelector );
    multiSelector.click();
    WebElement selectThree = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@title='Three']/../div" ) );
    assertNotNull( selectThree );
    selectThree.click();
    WebElement cancelButton = this.elemHelper.FindElement( driver, By.xpath( "//div[@class='filter-control-buttons']//button[contains(text(),'Cancel')]" ) );
    assertNotNull( cancelButton );
    cancelButton.click();
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='filter-control-buttons']//button[contains(text(),'Cancel')]" ) ) );
    String selectedMultiString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='multiFilterObj_group']//span[@class='filter-root-info-selected-items']" ) );
    assertEquals( "None", selectedMultiString );
    String selectedMultiItem = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "txt_multiSelectionObj_group" ) );
    assertEquals( "Result: [] Selected items:", selectedMultiItem );

    /*
     * ## Step 5
     */
    //Open multi selector
    multiSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//span[@class='filter-root-info-selected-items']" ) );
    assertNotNull( multiSelector );
    multiSelector.click();
    //Search for th, assert shown results and select shown options
    WebElement filterMultiSearch = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='filter-root-container multi-select expanded']//input[@class='filter-filter-input']" ) );
    assertNotNull( filterMultiSearch );
    filterMultiSearch.sendKeys( "th" );
    selectThree = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@title='Three']/../div" ) );
    assertNotNull( selectThree );
    selectThree.click();
    WebElement selectThirteen = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@title='Thirteen']/../div" ) );
    assertNotNull( selectThirteen );
    selectThirteen.click();
    WebElement selectTwentyThree = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@title='Twenty-three']/../div" ) );
    assertNotNull( selectTwentyThree );
    selectTwentyThree.click();
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@title='One']/../div" ) ) );
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@title='Eight']/../div" ) ) );
    WebElement applyButton = this.elemHelper.FindElement( driver, By.xpath( "//div[@class='filter-control-buttons']//button[contains(text(),'Apply')]" ) );
    assertNotNull( applyButton );
    applyButton.click();
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='filter-control-buttons']//button[contains(text(),'Apply')]" ) ) );

    //Assert correct values selected
    selectedMultiString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='multiFilterObj_group']//span[@class='filter-root-info-selected-items']" ) );
    assertEquals( "3 / 29", selectedMultiString );
    selectedMultiItem = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "txt_multiSelectionObj_group" ) );
    assertEquals( "Result: [\"[Zeroes].[Three]\",\"[Ones].[Thirteen]\",\"[Twos].[Twenty-three]\"] Selected items: [Zeroes].[Three][Ones].[Thirteen][Twos].[Twenty-three]", selectedMultiItem );

  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Sniff test to Filter Component AddIn Reference
   *
   * Description:
   *    This test is to assert simple functionality of sample 
   *
   * Steps:
   *    1. Open filter_reference sample and assert elements on page
   *    2. Assert opening one filter closes one that was previously opened
   *    3. Open first single filter, search, assert results, select and assert string
   *    4. Open second single filter, search, assert results, select and assert string
   *    5. Click Multiple Selection select a value and click cancel. Assert no change to text 
   *    6. Click again, select multiple values, click apply and assert text changed
   *        
   */

  @Test
  public void tc02_FilterComponent_AddInReference() {
    this.log.info( "tc02_FilterComponent_AddInReference" );

    /*
     * ## Step 1
     */
    //Open Filter Component Addin Reference sample
    driver.get( PageUrl.FILTER_ADDIN_COMPONENT );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Get Title of sample
    String title = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='docHeader']/h1" ) );
    assertEquals( "Filter Component Add-in Reference", title );

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

    /*
     * ## Step 2
     */
    //Click Single Selector and assert filter is opened
    WebElement singleSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_simple']//div[@class='filter-collapse-icon collapsed']" ) );
    assertNotNull( singleSelector );
    singleSelector.click();
    WebElement optionSingleContainer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_simple']//div[@class='filter-root-body']" ) );
    assertNotNull( optionSingleContainer );

    //Click Multi Group Selector, assert it is opened and that previous filter is closed
    WebElement multiGroupSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@class='filter-collapse-icon collapsed']" ) );
    assertNotNull( multiGroupSelector );
    multiGroupSelector.click();
    WebElement optionMultiGroupContainer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@class='filter-root-body']" ) );
    assertNotNull( optionMultiGroupContainer );
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='singleFilterObj_simple']//div[@class='filter-root-body']" ) ) );

    //Click Single Group Selector, assert it is opened and that previous filter is closed
    WebElement singleGroupSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_group']//div[@class='filter-collapse-icon collapsed']" ) );
    assertNotNull( singleGroupSelector );
    singleGroupSelector.click();
    WebElement optionSingleGroupContainer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_group']//div[@class='filter-root-body']" ) );
    assertNotNull( optionSingleGroupContainer );
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@class='filter-root-body']" ) ) );

    //Click Multi Selector, assert it is opened and that previous filter is closed
    WebElement multiSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_simple']//div[@class='filter-collapse-icon collapsed']" ) );
    assertNotNull( multiSelector );
    multiSelector.click();
    WebElement optionGroupContainer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_simple']//div[@class='filter-root-body']" ) );
    assertNotNull( optionGroupContainer );
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='singleFilterObj_group']//div[@class='filter-root-body']" ) ) );

    /*
     * ## Step 3
     */
    //Click Single Selector and assert filter is opened
    singleSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_simple']//div[@class='filter-collapse-icon collapsed']" ) );
    assertNotNull( singleSelector );
    singleSelector.click();
    optionSingleContainer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_simple']//div[@class='filter-root-body']" ) );
    assertNotNull( optionSingleContainer );
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='multiFilterObj_simple']//div[@class='filter-root-body']" ) ) );

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
    zeroSelector.click();
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='singleFilterObj_simple']//div[@class='filter-root-body']" ) ) );
    String selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='singleFilterObj_simple']//span[@class='filter-root-info-selected-item']" ) );
    assertEquals( "Zero", selectedString );

    /*
     * ## Step 4
     */
    //Click Multi Selector and assert filter is opened
    multiSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_simple']//div[@class='filter-collapse-icon collapsed']" ) );
    assertNotNull( multiSelector );
    multiSelector.click();
    WebElement optionMultiContainer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_simple']//div[@class='filter-root-body']" ) );
    assertNotNull( optionMultiContainer );

    //Select All, click Apply and assert filter closed and strings correct
    WebElement allSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_simple']//div[@class='filter-root-selection-icon none-selected']" ) );
    assertNotNull( allSelector );
    allSelector.click();
    WebElement applyButton = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='multiFilterObj_simple']//button[contains(text(),'Apply')]" ) );
    assertNotNull( applyButton );
    applyButton.click();
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='multiFilterObj_simple']//div[@class='filter-root-body']" ) ) );
    selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='multiFilterObj_simple']//span[@class='filter-root-info-selected-items']" ) );
    assertEquals( "All", selectedString );

    /*
     * ## Step 5
     */
    //Click Single Selector and assert filter is opened
    singleSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_group']//div[@class='filter-collapse-icon collapsed']" ) );
    assertNotNull( singleSelector );
    singleSelector.click();
    optionSingleContainer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_group']//div[@class='filter-root-body']" ) );
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
    filterClear.click();
    WebElement oneOption = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_group']//div[@title='One']" ) );
    assertNotNull( oneOption );

    //Select One, assert filter closed and strings correct
    WebElement oneSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_group']//div[@title='One']/../div" ) );
    assertNotNull( oneSelector );
    oneSelector.click();
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='singleFilterObj_group']//div[@class='filter-root-body']" ) ) );
    selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='singleFilterObj_group']//span[@class='filter-root-info-selected-item']" ) );
    assertEquals( "One", selectedString );

    /*
     * ## Step 6
     */
    //Click Multi Selector and assert filter is opened
    multiSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@class='filter-collapse-icon collapsed']" ) );
    assertNotNull( multiSelector );
    multiSelector.click();
    optionMultiContainer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@class='filter-root-body']" ) );
    assertNotNull( optionMultiContainer );

    //Select 4 options, click Apply and assert filter closed and strings correct
    filterSearch = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//input[@class='filter-filter-input']" ) );
    assertNotNull( filterSearch );
    filterSearch.sendKeys( "twelve" );
    WebElement twelveSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@title='Twelve']/../div" ) );
    assertNotNull( twelveSelector );
    twelveSelector.click();
    filterSearch = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//input[@class='filter-filter-input']" ) );
    assertNotNull( filterSearch );
    filterSearch.sendKeys( "twenty" );
    WebElement twentyFiveSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@title='Twenty-five']/../div" ) );
    assertNotNull( twentyFiveSelector );
    twentyFiveSelector.click();
    filterSearch = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//input[@class='filter-filter-input']" ) );
    assertNotNull( filterSearch );
    filterSearch.sendKeys( "six" );
    WebElement sixSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@title='Six']/../div" ) );
    assertNotNull( sixSelector );
    sixSelector.click();
    filterSearch = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//input[@class='filter-filter-input']" ) );
    assertNotNull( filterSearch );
    filterSearch.sendKeys( "eighteen" );
    WebElement eighteenSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@title='Eighteen']/../div" ) );
    assertNotNull( eighteenSelector );
    eighteenSelector.click();
    applyButton = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='multiFilterObj_group']//button[contains(text(),'Apply')]" ) );
    assertNotNull( applyButton );
    applyButton.click();
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@class='filter-root-body']" ) ) );
    selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='multiFilterObj_group']//span[@class='filter-root-info-selected-items']" ) );
    assertEquals( "4 / 29", selectedString );

  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Sniff test to Filter Component Visual Guide Reference
   *
   * Description:
   *    This test is to assert simple functionality of sample 
   *
   * Steps:
   *    1. Open filter_visual_guide sample and assert elements on page
   *    2. Open first set of filters, make selections and assert string
   *    3. Open third filter and assert limited to five selections
   *    4. Open second set of filters, make selections and assert string
   *    5. Assert third set of filters expanded, make selections, assert string and filter still expanded
   *    6. Open first set of filters, make selections and assert string
   *    7. Open add in filter and assert colors, make selection and asser string. Assert message on two next filters
   *    8. Assert two last filters are expanded, make selections, assert string and filter still expanded
   *        
   */

  @Test
  public void tc03_FilterComponent_VisualGuide() {
    this.log.info( "tc03_FilterComponent_VisualGuide" );

    /*
     * ## Step 1
     */
    //Open Filter Component Visual Guide sample
    driver.get( PageUrl.FILTER_VISUAL_COMPONENT );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Get Title of sample
    String title = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='docHeader']/h1" ) );
    assertEquals( "Visual Guide to the Filter Component", title );

    //Assert presence of elements
    WebElement singleFilter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "singleFilterObj_simple" ) );
    WebElement multipleFilter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "multiFilterObj_simple" ) );
    WebElement limitedFilter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "multiFilterObj_limited" ) );
    WebElement singleGroupFilter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "singleFilterObj_group" ) );
    WebElement multiGroupFilter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "multiFilterObj_group" ) );
    WebElement singleExpandedFilter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "singleFilterObj_expanded" ) );
    WebElement multiExpandedFilter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "multiFilterObj_expanded" ) );
    WebElement singlePaginatedFilter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "singleFilterObj_simple_paginated" ) );
    WebElement multiPaginatedFilter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "multiFilterObj_simple_paginated" ) );
    WebElement singleAddinFilter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "singleFilterObj_addIn" ) );
    WebElement singleNoDataFilter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "singleFilterObj_noData" ) );
    WebElement singleCustomFilter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "singleFilterObj_noData_custom" ) );
    WebElement singleValuesFilter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "singleFilterObj_values" ) );
    WebElement multiValuesFilter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "multiFilterObj_values" ) );
    assertNotNull( limitedFilter );
    assertNotNull( singleFilter );
    assertNotNull( multipleFilter );
    assertNotNull( singleGroupFilter );
    assertNotNull( multiGroupFilter );
    assertNotNull( singleExpandedFilter );
    assertNotNull( multiExpandedFilter );
    assertNotNull( singlePaginatedFilter );
    assertNotNull( multiPaginatedFilter );
    assertNotNull( singleAddinFilter );
    assertNotNull( singleCustomFilter );
    assertNotNull( singleNoDataFilter );
    assertNotNull( singleValuesFilter );
    assertNotNull( multiValuesFilter );

    /*
     * ## Step 2
     */
    //Click Single Selector and assert filter is opened
    WebElement singleSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_simple']//div[@class='filter-collapse-icon collapsed']" ) );
    assertNotNull( singleSelector );
    singleSelector.click();
    WebElement optionSingleContainer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_simple']//div[@class='filter-root-body']" ) );
    assertNotNull( optionSingleContainer );

    //Select Zero, assert filter closed and strings correct
    WebElement oneSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_simple']//div[@title='One']/../div" ) );
    assertNotNull( oneSelector );
    oneSelector.click();
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='singleFilterObj_simple']//div[@class='filter-root-body']" ) ) );
    String selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='singleFilterObj_simple']//span[@class='filter-root-info-selected-item']" ) );
    assertEquals( "One", selectedString );

    //Click Multi Selector and assert filter is opened
    WebElement multiSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_simple']//div[@class='filter-collapse-icon collapsed']" ) );
    assertNotNull( multiSelector );
    multiSelector.click();
    WebElement optionMultiContainer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_simple']//div[@class='filter-root-body']" ) );
    assertNotNull( optionMultiContainer );

    //Select All, click Apply and assert filter closed and strings correct
    WebElement allSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_simple']//div[@class='filter-root-selection-icon none-selected']" ) );
    assertNotNull( allSelector );
    allSelector.click();
    WebElement applyButton = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='multiFilterObj_simple']//button[contains(text(),'Apply')]" ) );
    assertNotNull( applyButton );
    applyButton.click();
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='multiFilterObj_simple']//div[@class='filter-root-body']" ) ) );
    selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='multiFilterObj_simple']//span[@class='filter-root-info-selected-items']" ) );
    assertEquals( "All", selectedString );

    /*
     * ## Step 3
     */
    //Click Limited Selector and assert filter is opened
    singleSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_limited']//div[@class='filter-collapse-icon collapsed']" ) );
    assertNotNull( singleSelector );
    singleSelector.click();
    optionSingleContainer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_limited']//div[@class='filter-root-body']" ) );
    assertNotNull( optionSingleContainer );

    //Select 5 options, assert limit message shown
    oneSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_limited']//div[@title='One']/../div" ) );
    assertNotNull( oneSelector );
    oneSelector.click();
    WebElement twoSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_limited']//div[@title='Two']/../div" ) );
    assertNotNull( twoSelector );
    twoSelector.click();
    WebElement threeSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_limited']//div[@title='Three']/../div" ) );
    assertNotNull( threeSelector );
    threeSelector.click();
    WebElement fourSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_limited']//div[@title='Four']/../div" ) );
    assertNotNull( fourSelector );
    fourSelector.click();
    WebElement fiveSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_limited']//div[@title='Five']/../div" ) );
    assertNotNull( fiveSelector );
    fiveSelector.click();
    String limitMessage = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='multiFilterObj_limited']//div[@class='filter-root-notification']" ) );
    assertEquals( "The selection limit (5) for specific items has been reached.", limitMessage );

    //Click one more element and assert not selected
    WebElement sixSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_limited']//div[@title='Six']/../div" ) );
    assertNotNull( sixSelector );
    sixSelector.click();
    WebElement sixNotSelected = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_limited']//div[@title='Six']/../../../div[@class='filter-item-container none-selected']" ) );
    assertNotNull( sixNotSelected );

    //Apply and assert string
    applyButton = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='multiFilterObj_limited']//button[contains(text(),'Apply')]" ) );
    assertNotNull( applyButton );
    applyButton.click();
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='multiFilterObj_limited']//div[@class='filter-root-body']" ) ) );
    selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='multiFilterObj_limited']//span[@class='filter-root-info-selected-items']" ) );
    assertEquals( "5 / 19", selectedString );

    /*
     * ## Step 4
     */
    //Click Single Selector and assert filter is opened
    singleSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_group']//div[@class='filter-collapse-icon collapsed']" ) );
    assertNotNull( singleSelector );
    singleSelector.click();
    optionSingleContainer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_group']//div[@class='filter-root-body']" ) );
    assertNotNull( optionSingleContainer );

    //Select One, assert filter closed and strings correct
    oneSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_group']//div[@title='One']/../div" ) );
    assertNotNull( oneSelector );
    oneSelector.click();
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='singleFilterObj_group']//div[@class='filter-root-body']" ) ) );
    selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='singleFilterObj_group']//span[@class='filter-root-info-selected-item']" ) );
    assertEquals( "One", selectedString );

    //Click Multi Selector and assert filter is opened
    multiSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@class='filter-collapse-icon collapsed']" ) );
    assertNotNull( multiSelector );
    multiSelector.click();
    optionMultiContainer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@class='filter-root-body']" ) );
    assertNotNull( optionMultiContainer );

    //Select All, click Apply and assert filter closed and strings correct
    allSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@class='filter-root-selection-icon none-selected']" ) );
    assertNotNull( allSelector );
    allSelector.click();
    applyButton = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='multiFilterObj_group']//button[contains(text(),'Apply')]" ) );
    assertNotNull( applyButton );
    applyButton.click();
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='multiFilterObj_group']//div[@class='filter-root-body']" ) ) );
    selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='multiFilterObj_group']//span[@class='filter-root-info-selected-items']" ) );
    assertEquals( "All", selectedString );

    /*
     * ## Step 5
     */
    //Assert filters are opened
    WebElement expandedSingleSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_expanded']//div[@class='filter-root-body']" ) );
    assertNotNull( expandedSingleSelector );
    WebElement expandedMultiSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_expanded']//div[@class='filter-root-body']" ) );
    assertNotNull( expandedMultiSelector );

    //Select One, assert filter closed and strings correct
    oneSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_expanded']//div[@title='One']/../div" ) );
    assertNotNull( oneSelector );
    oneSelector.click();
    selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='singleFilterObj_expanded']//span[@class='filter-root-info-selected-item']" ) );
    assertEquals( "One", selectedString );

    //Select All, click Apply and assert filter closed and strings correct
    allSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_expanded']//div[@class='filter-root-selection-icon none-selected']" ) );
    assertNotNull( allSelector );
    allSelector.click();
    applyButton = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='multiFilterObj_expanded']//button[contains(text(),'Apply')]" ) );
    assertNotNull( applyButton );
    applyButton.click();
    selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='multiFilterObj_expanded']//span[@class='filter-root-info-selected-items']" ) );
    assertEquals( "All", selectedString );

    //Assert filters are opened
    expandedSingleSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_expanded']//div[@class='filter-root-body']" ) );
    assertNotNull( expandedSingleSelector );
    expandedMultiSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_expanded']//div[@class='filter-root-body']" ) );
    assertNotNull( expandedMultiSelector );

    /*
     * ## Step 6
     */
    //Click Single Selector and assert filter is opened
    singleSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_simple_paginated']//div[@class='filter-collapse-icon collapsed']" ) );
    assertNotNull( singleSelector );
    singleSelector.click();
    optionSingleContainer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_simple_paginated']//div[@class='filter-root-body']" ) );
    assertNotNull( optionSingleContainer );

    //Select One, assert filter closed and strings correct
    oneSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_simple_paginated']//div[@title='Entry 1']/../div" ) );
    assertNotNull( oneSelector );
    oneSelector.click();
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='singleFilterObj_simple_paginated']//div[@class='filter-root-body']" ) ) );
    selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='singleFilterObj_simple_paginated']//span[@class='filter-root-info-selected-item']" ) );
    assertEquals( "Entry 1", selectedString );

    //Click Multi Selector and assert filter is opened
    multiSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_simple_paginated']//div[@class='filter-collapse-icon collapsed']" ) );
    assertNotNull( multiSelector );
    multiSelector.click();
    optionMultiContainer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_simple_paginated']//div[@class='filter-root-body']" ) );
    assertNotNull( optionMultiContainer );

    //Select All, click Apply and assert filter closed and strings correct
    allSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_simple_paginated']//div[@class='filter-root-selection-icon none-selected']" ) );
    assertNotNull( allSelector );
    allSelector.click();
    applyButton = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='multiFilterObj_simple_paginated']//button[contains(text(),'Apply')]" ) );
    assertNotNull( applyButton );
    applyButton.click();
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='multiFilterObj_simple_paginated']//div[@class='filter-root-body']" ) ) );
    selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='multiFilterObj_simple_paginated']//span[@class='filter-root-info-selected-items']" ) );
    assertEquals( "20 / 1000", selectedString );

    /*
     * ## Step 7
     */
    //Click Single Selector and assert filter is opened
    singleSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_addIn']//div[@class='filter-collapse-icon collapsed']" ) );
    assertNotNull( singleSelector );
    singleSelector.click();
    optionSingleContainer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_addIn']//div[@class='filter-root-body']" ) );
    assertNotNull( optionSingleContainer );

    //Assert different colors
    String oneColor = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_addIn']//div[@title='One']/../../div" ) ).getAttribute( "style" );
    assertFalse( oneColor.isEmpty() );
    String twoColor = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_addIn']//div[@title='Two']/../../div" ) ).getAttribute( "style" );
    assertNotNull( twoColor );
    String threeColor = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_addIn']//div[@title='Three']/../../div" ) ).getAttribute( "style" );
    assertNotNull( threeColor );
    String fourColor = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_addIn']//div[@title='Four']/../../div" ) ).getAttribute( "style" );
    assertNotNull( fourColor );

    //Select One, assert filter closed and strings correct
    oneSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_addIn']//div[@title='One']/../div" ) );
    assertNotNull( oneSelector );
    oneSelector.click();
    assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='singleFilterObj_addIn']//div[@class='filter-root-body']" ) ) );
    selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='singleFilterObj_addIn']//span[@class='filter-root-info-selected-item']" ) );
    assertEquals( "One", selectedString );

    //Assert messages on next 2 filters
    String firstMessage = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='singleFilterObj_noData']//div[@class='filter-root-header-label']" ) );
    assertEquals( "Unavailable", firstMessage );
    String secondMessage = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='singleFilterObj_noData_custom']//div[@class='filter-root-header-label']" ) );
    assertEquals( "No available data", secondMessage );

    /*
     * ## Step 8
     */
    //Assert filters are opened
    WebElement valuesSingleSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_values']//div[@class='filter-root-body']" ) );
    assertNotNull( valuesSingleSelector );
    WebElement valuesMultiSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_values']//div[@class='filter-root-body']" ) );
    assertNotNull( valuesMultiSelector );

    //Select One, assert filter closed and strings correct
    WebElement filterSearch = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_values']//input[@class='filter-filter-input']" ) );
    assertNotNull( filterSearch );
    filterSearch.sendKeys( "one" );
    oneSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_values']//div[@title='One']/../div" ) );
    assertNotNull( oneSelector );
    WebElement oneValue = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_values']//div[@title='One']/../div[@class='filter-item-value']" ) );
    assertNotNull( oneValue );
    oneSelector.click();
    selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='singleFilterObj_values']//span[@class='filter-root-info-selected-item']" ) );
    assertEquals( "One", selectedString );

    //Select All, click Apply and assert filter closed and strings correct
    filterSearch = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_values']//input[@class='filter-filter-input']" ) );
    assertNotNull( filterSearch );
    filterSearch.sendKeys( "one" );
    oneValue = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_values']//div[@title='One']/../div[@class='filter-item-value']" ) );
    assertNotNull( oneValue );
    allSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_values']//div[@class='filter-root-selection-icon none-selected']" ) );
    assertNotNull( allSelector );
    allSelector.click();
    applyButton = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='multiFilterObj_values']//button[contains(text(),'Apply')]" ) );
    assertNotNull( applyButton );
    applyButton.click();
    selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='multiFilterObj_values']//span[@class='filter-root-info-selected-items']" ) );
    assertEquals( "All", selectedString );

    //Assert filters are opened
    valuesSingleSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_values']//div[@class='filter-root-body']" ) );
    assertNotNull( valuesSingleSelector );
    valuesMultiSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_values']//div[@class='filter-root-body']" ) );
    assertNotNull( valuesMultiSelector );

  }
}
