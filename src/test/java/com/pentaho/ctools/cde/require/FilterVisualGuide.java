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
package com.pentaho.ctools.cde.require;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertFalse;
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
public class FilterVisualGuide extends BaseTest {
	// Access to wrapper for webdriver
	private final ElementHelper elemHelper = new ElementHelper();
	// Log instance
	private final Logger log = LogManager.getLogger( FilterVisualGuide.class );

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
		//Open Filter Component Visual Guide sample
		this.elemHelper.Get( driver, PageUrl.FILTER_VISUAL_COMPONENT_REQUIRE );

		// NOTE - we have to wait for loading disappear
		this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 5 );
		this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

		//Get Page Title
		String expectedPageTitle = "filter_visual_guide";
		String actualPageTitle = this.elemHelper.WaitForTitle( driver, expectedPageTitle );
		assertEquals( actualPageTitle, expectedPageTitle );
		//Get Title of sample
		String expectedSampleTitle = "Filter Component Visual Guide";
		String actualSampleTitle = this.elemHelper.WaitForTextPresence( driver, By.cssSelector( "#docHeader > h1" ), expectedSampleTitle );
		assertEquals( actualSampleTitle, expectedSampleTitle );

		//Assert presence of elements
		WebElement singleFilter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "singleFilterObj_simple" ) );
		WebElement multipleFilter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "multiFilterObj_simple" ) );
		WebElement limitedFilter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "multiFilterObj_limited" ) );
		WebElement singleGroupFilter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "singleFilterObj_group" ) );
		WebElement multiGroupFilter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "multiFilterObj_group" ) );
		WebElement singleExpandedFilter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "singleFilterObj_expanded" ) );
		WebElement multiExpandedFilter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "multiFilterObj_expanded" ) );
		WebElement singlePaginatedFilter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "singleFilterObj_simple_paginated" ), 60 );
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

	}

	/**
	* ############################### Test Case 1 ###############################
	*
	* Test Case Name:
	*    Sniff test to Filter Component Visual Guide Reference
	*
	* Description:
	*    This test is to assert simple functionality of sample
	*
	* Steps:
	*    1. Open first set of filters, make selections and assert string
	*    2. Open third filter and assert limited to five selections
	*    3. Open second set of filters, make selections and assert string
	*    4. Assert third set of filters expanded, make selections, assert string and filter still expanded
	*    5. Open first set of filters, make selections and assert string
	*    6. Open add in filter and assert colors, make selection and assert string. Assert message on two next filters
	*    7. Assert two last filters are expanded, make selections, assert string and filter still expanded
	*
	*/
	@Test
	public void tc01_FilterComponent_VisualGuide() {
		this.log.info( "tc01_FilterComponent_VisualGuide" );

		/*
		 * ## Step 1
		 */
		//Click Single Selector and assert filter is opened
		WebElement singleSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#singleFilterObj_simple div.filter-collapse-icon.collapsed" ) );
		assertNotNull( singleSelector );
		this.elemHelper.ClickJS( driver, By.cssSelector( "div#singleFilterObj_simple div.filter-collapse-icon.collapsed" ) );
		WebElement optionSingleContainer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#singleFilterObj_simple div.filter-root-body" ) );
		assertNotNull( optionSingleContainer );

		//Select Zero, assert filter closed and strings correct
		WebElement oneSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_simple']//div[@title='One']/../div" ) );
		assertNotNull( oneSelector );
		this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='singleFilterObj_simple']//div[@title='One']/../div" ) );
		assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div#singleFilterObj_simple div.filter-root-body" ) ) );
		String selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='singleFilterObj_simple']//span[@class='filter-root-info-selected-item']" ) );
		assertEquals( "One", selectedString );

		//Click Multi Selector and assert filter is opened
		WebElement multiSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#multiFilterObj_simple div.filter-collapse-icon.collapsed" ) );
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
		 * ## Step 2
		 */
		//Click Limited Selector and assert filter is opened
		singleSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#multiFilterObj_limited div.filter-collapse-icon.collapsed" ) );
		assertNotNull( singleSelector );
		this.elemHelper.ClickJS( driver, By.cssSelector( "div#multiFilterObj_limited div.filter-collapse-icon.collapsed" ) );
		optionSingleContainer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#multiFilterObj_limited div.filter-root-body" ) );
		assertNotNull( optionSingleContainer );

		//Select 5 options, assert limit message shown
		oneSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_limited']//div[@title='One']/../div" ) );
		assertNotNull( oneSelector );
		this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='multiFilterObj_limited']//div[@title='One']/../div" ) );
		WebElement twoSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_limited']//div[@title='Two']/../div" ) );
		assertNotNull( twoSelector );
		this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='multiFilterObj_limited']//div[@title='Two']/../div" ) );
		WebElement threeSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_limited']//div[@title='Three']/../div" ) );
		assertNotNull( threeSelector );
		this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='multiFilterObj_limited']//div[@title='Three']/../div" ) );
		WebElement fourSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_limited']//div[@title='Four']/../div" ) );
		assertNotNull( fourSelector );
		this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='multiFilterObj_limited']//div[@title='Four']/../div" ) );
		WebElement fiveSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_limited']//div[@title='Five']/../div" ) );
		assertNotNull( fiveSelector );
		this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='multiFilterObj_limited']//div[@title='Five']/../div" ) );
		String limitMessage = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='multiFilterObj_limited']//div[@class='filter-root-notification']" ) );
		assertEquals( limitMessage, "The selection limit (5) for specific items has been reached." );

		//Click one more element and assert not selected
		WebElement sixSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='multiFilterObj_limited']//div[@title='Six']/../div" ) );
		assertNotNull( sixSelector );
		this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='multiFilterObj_limited']//div[@title='Six']/../div" ) );
		WebElement sixNotSelected = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#example_limited div.filter-root-items div.filter-root-child div.filter-item-container.none-selected" ) );
		assertNotNull( sixNotSelected );

		//Apply and assert string
		applyButton = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='multiFilterObj_limited']//button[contains(text(),'Apply')]" ) );
		assertNotNull( applyButton );
		this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='multiFilterObj_limited']//button[contains(text(),'Apply')]" ) );
		assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div#multiFilterObj_limited div.filter-root-body" ) ) );
		selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='multiFilterObj_limited']//span[@class='filter-root-info-selected-items']" ) );
		assertEquals( "5 / 19", selectedString );

		/*
		 * ## Step 3
		 */
		//Click Single Selector and assert filter is opened
		singleSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#singleFilterObj_group div.filter-collapse-icon.collapsed" ) );
		assertNotNull( singleSelector );
		this.elemHelper.ClickJS( driver, By.cssSelector( "div#singleFilterObj_group div.filter-collapse-icon.collapsed" ) );
		optionSingleContainer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#singleFilterObj_group div.filter-root-body" ) );
		assertNotNull( optionSingleContainer );

		//Select One, assert filter closed and strings correct
		oneSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_group']//div[@title='One']/../div" ) );
		assertNotNull( oneSelector );
		this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='singleFilterObj_group']//div[@title='One']/../div" ) );
		assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div#singleFilterObj_group div.filter-root-body" ) ) );
		selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='singleFilterObj_group']//span[@class='filter-root-info-selected-item']" ) );
		assertEquals( "One", selectedString );

		//Click Multi Selector and assert filter is opened
		multiSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#multiFilterObj_group div.filter-collapse-icon.collapsed" ) );
		assertNotNull( multiSelector );
		this.elemHelper.ClickJS( driver, By.cssSelector( "div#multiFilterObj_group div.filter-collapse-icon.collapsed" ) );
		optionMultiContainer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#multiFilterObj_group div.filter-root-body" ) );
		assertNotNull( optionMultiContainer );

		//Select All, click Apply and assert filter closed and strings correct
		allSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#multiFilterObj_group div.filter-root-selection-icon.none-selected" ) );
		assertNotNull( allSelector );
		this.elemHelper.ClickJS( driver, By.cssSelector( "div#multiFilterObj_group div.filter-root-selection-icon.none-selected" ) );
		applyButton = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='multiFilterObj_group']//button[contains(text(),'Apply')]" ) );
		assertNotNull( applyButton );
		this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='multiFilterObj_group']//button[contains(text(),'Apply')]" ) );
		assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div#multiFilterObj_group div.filter-root-body" ) ) );
		selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='multiFilterObj_group']//span[@class='filter-root-info-selected-items']" ) );
		assertEquals( "All", selectedString );

		/*
		 * ## Step 4
		 */
		//Assert filters are opened
		WebElement expandedSingleSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#singleFilterObj_expanded div.filter-root-body" ) );
		assertNotNull( expandedSingleSelector );
		WebElement expandedMultiSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#multiFilterObj_expanded div.filter-root-body" ) );
		assertNotNull( expandedMultiSelector );

		//Select One, assert filter closed and strings correct
		oneSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_expanded']//div[@title='One']/../div" ) );
		assertNotNull( oneSelector );
		this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='singleFilterObj_expanded']//div[@title='One']/../div" ) );
		selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='singleFilterObj_expanded']//span[@class='filter-root-info-selected-item']" ) );
		assertEquals( "One", selectedString );

		//Select All, click Apply and assert filter closed and strings correct
		allSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#multiFilterObj_expanded div.filter-root-selection-icon.none-selected" ) );
		assertNotNull( allSelector );
		this.elemHelper.ClickJS( driver, By.cssSelector( "div#multiFilterObj_expanded div.filter-root-selection-icon.none-selected" ) );
		applyButton = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='multiFilterObj_expanded']//button[contains(text(),'Apply')]" ) );
		assertNotNull( applyButton );
		this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='multiFilterObj_expanded']//button[contains(text(),'Apply')]" ) );
		selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='multiFilterObj_expanded']//span[@class='filter-root-info-selected-items']" ) );
		assertEquals( "All", selectedString );

		//Assert filters are opened
		expandedSingleSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#singleFilterObj_expanded div.filter-root-body" ) );
		assertNotNull( expandedSingleSelector );
		expandedMultiSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#multiFilterObj_expanded div.filter-root-body" ) );
		assertNotNull( expandedMultiSelector );

		/*
		 * ## Step 5
		 */
		//Click Single Selector and assert filter is opened
		singleSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#singleFilterObj_simple_paginated div.filter-collapse-icon.collapsed" ) );
		assertNotNull( singleSelector );
		this.elemHelper.ClickJS( driver, By.cssSelector( "div#singleFilterObj_simple_paginated div.filter-collapse-icon.collapsed" ) );
		optionSingleContainer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#singleFilterObj_simple_paginated div.filter-root-body" ) );
		assertNotNull( optionSingleContainer );

		//Select One, assert filter closed and strings correct
		oneSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_simple_paginated']//div[@title='Entry 1']/../div" ) );
		assertNotNull( oneSelector );
		this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='singleFilterObj_simple_paginated']//div[@title='Entry 1']/../div" ) );
		this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
		assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div#singleFilterObj_simple_paginated div.filter-root-body" ) ) );
		selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='singleFilterObj_simple_paginated']//span[@class='filter-root-info-selected-item']" ) );
		assertEquals( "Entry 1", selectedString );

		//Click Multi Selector and assert filter is opened
		multiSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#multiFilterObj_simple_paginated div.filter-collapse-icon.collapsed" ) );
		assertNotNull( multiSelector );
		this.elemHelper.ClickJS( driver, By.cssSelector( "div#multiFilterObj_simple_paginated div.filter-collapse-icon.collapsed" ) );
		optionMultiContainer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#multiFilterObj_simple_paginated div.filter-root-body" ) );
		assertNotNull( optionMultiContainer );

		//Select All, click Apply and assert filter closed and strings correct
		allSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#multiFilterObj_simple_paginated div.filter-root-selection-icon.none-selected" ) );
		assertNotNull( allSelector );
		this.elemHelper.ClickJS( driver, By.cssSelector( "div#multiFilterObj_simple_paginated div.filter-root-selection-icon.none-selected" ) );
		applyButton = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='multiFilterObj_simple_paginated']//button[contains(text(),'Apply')]" ) );
		assertNotNull( applyButton );
		this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='multiFilterObj_simple_paginated']//button[contains(text(),'Apply')]" ) );
		this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
		assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div#multiFilterObj_simple_paginated div.filter-root-body" ) ) );
		selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='multiFilterObj_simple_paginated']//span[@class='filter-root-info-selected-items']" ) );
		assertEquals( "All", selectedString );

		/*
		 * ## Step 6
		 */
		//Click Single Selector and assert filter is opened
		singleSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#singleFilterObj_addIn div.filter-collapse-icon.collapsed" ) );
		assertNotNull( singleSelector );
		this.elemHelper.ClickJS( driver, By.cssSelector( "div#singleFilterObj_addIn div.filter-collapse-icon.collapsed" ) );
		optionSingleContainer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#singleFilterObj_addIn div.filter-root-body" ) );
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
		this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='singleFilterObj_addIn']//div[@title='One']/../div" ) );
		assertTrue( this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='singleFilterObj_addIn']//div[@class='filter-root-body']" ) ) );
		selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='singleFilterObj_addIn']//span[@class='filter-root-info-selected-item']" ) );
		assertEquals( "One", selectedString );

		//Assert messages on next 2 filters
		String firstMessage = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='singleFilterObj_noData']//div[@class='filter-root-header-label']" ) );
		assertEquals( "Unavailable", firstMessage );
		String secondMessage = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='singleFilterObj_noData_custom']//div[@class='filter-root-header-label']" ) );
		assertEquals( "No available data", secondMessage );

		/*
		 * ## Step 7
		 */
		//Assert filters are opened
		WebElement valuesSingleSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#singleFilterObj_values div.filter-root-body" ) );
		assertNotNull( valuesSingleSelector );
		WebElement valuesMultiSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#multiFilterObj_values div.filter-root-body" ) );
		assertNotNull( valuesMultiSelector );

		//Select One, assert filter closed and strings correct
		WebElement filterSearch = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_values']//input[@class='filter-filter-input']" ) );
		assertNotNull( filterSearch );
		filterSearch.sendKeys( "one" );
		oneSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_values']//div[@title='One']/../div" ) );
		assertNotNull( oneSelector );
		WebElement oneValue = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_values']//div[@title='One']/../div[@class='filter-item-value']" ) );
		assertNotNull( oneValue );
		this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='singleFilterObj_values']//div[@title='One']/../div[@class='filter-item-value']" ) );
		selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='singleFilterObj_values']//span[@class='filter-root-info-selected-item']" ) );
		assertEquals( "One", selectedString );

		//Select All, click Apply and assert filter closed and strings correct
		filterSearch = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_values']//input[@class='filter-filter-input']" ) );
		assertNotNull( filterSearch );
		filterSearch.sendKeys( "one" );
		oneValue = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='singleFilterObj_values']//div[@title='One']/../div[@class='filter-item-value']" ) );
		assertNotNull( oneValue );
		allSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#multiFilterObj_values div.filter-root-selection-icon.none-selected" ) );
		assertNotNull( allSelector );
		this.elemHelper.ClickJS( driver, By.cssSelector( "div#multiFilterObj_values div.filter-root-selection-icon.none-selected" ) );
		applyButton = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='multiFilterObj_values']//button[contains(text(),'Apply')]" ) );
		assertNotNull( applyButton );
		this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='multiFilterObj_values']//button[contains(text(),'Apply')]" ) );
		selectedString = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='multiFilterObj_values']//span[@class='filter-root-info-selected-items']" ) );
		assertEquals( "All", selectedString );

		//Assert filters are opened
		valuesSingleSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#singleFilterObj_values div.filter-root-body" ) );
		assertNotNull( valuesSingleSelector );
		valuesMultiSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div#multiFilterObj_values div.filter-root-body" ) );
		assertNotNull( valuesMultiSelector );

	}
}
