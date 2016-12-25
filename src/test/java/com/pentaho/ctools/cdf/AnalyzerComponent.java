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
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with Analyzer Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class AnalyzerComponent extends BaseTest {
	// Access to wrapper for webdriver
	private final ElementHelper elemHelper = new ElementHelper();
	//Log instance
	private final Logger log = LogManager.getLogger( AnalyzerComponent.class );

	/**
	 * ############################### Test Case 0 ###############################
	 *
	 * Test Case Name:
	 *    Open Sample Page
	 */
	@Test
	public void tc0_OpenSamplePage_Display() {
		this.log.info( "tc0_OpenSamplePage_Display" );
		// The URL for the PrptComponent under CDF samples
		// This sample is in: Public/plugin-samples/CDF/Documentation/Component Reference/Core Components/PrptComponent
		this.elemHelper.Get( driver, PageUrl.ANALYZER_COMPONENT );

		// NOTE - we have to wait for loading disappear
		this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

		//Check if the some elements are already displayed
		//Check if element with "Line" is visible
		this.elemHelper.WaitForElementPresence( driver, By.id( "ui-id-1" ) );
		WebDriver frameSampleAnalyzer = this.elemHelper.SwitchToFrame( driver, "iframe_sampleObject" );
		WebElement elemLine = this.elemHelper.WaitForElementPresenceAndVisible( frameSampleAnalyzer, By.id( "sortHandle_1" ), 45 );
		assertNotNull( elemLine );
		//Back to root
		this.elemHelper.SwitchToDefault( driver );
	}

	/**
	 * ############################### Test Case 1 ###############################
	 *
	 * Test Case Name:
	 *    Display Content
	 * Description:
	 *    Check if the contents present in page is the expected.
	 * Steps:
	 *    1. Check title web page and sample title.
	 */
	@Test
	public void tc1_PageContent_DisplayTitle() {
		this.log.info( "tc1_PageContent_DisplayTitle" );
		// Wait for title become visible and with value 'Community Dashboard Framework'
		wait.until( ExpectedConditions.titleContains( "Community Dashboard Framework" ) );
		// Wait for visibility of 'VisualizationAPIComponent'
		wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );

		// Validate the sample that we are testing is the one
		assertEquals( "Community Dashboard Framework", driver.getTitle() );
		assertEquals( "AnalyzerComponent", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );
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

		// ## Step 1
		// Render again the sample
		this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='example']/ul/li[2]/a" ) );
		this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='code']/button" ) );

		// NOTE - we have to wait for loading disappear
		this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

		//Check if the some elements are already displayed
		//Check if element with "Line" is visible
		this.elemHelper.WaitForElementPresence( driver, By.id( "sampleObject" ) );
		WebDriver frameSampleAnalyzer = this.elemHelper.SwitchToFrame( driver, "iframe_sampleObject" );
		WebElement elemLine = this.elemHelper.WaitForElementPresenceAndVisible( frameSampleAnalyzer, By.id( "sortHandle_1" ), 45 );
		assertNotNull( elemLine );

		//Back to root
		this.elemHelper.SwitchToDefault( driver );

		// Now sample element must be displayed
		assertTrue( this.elemHelper.FindElement( driver, By.id( "sample" ) ).isDisplayed() );

		//Check the number of divs with id 'SampleObject'
		//Hence, we guarantee when click Try Me the previous div is replaced
		int nSampleObject = driver.findElements( By.id( "sampleObject" ) ).size();
		assertEquals( 1, nSampleObject );
	}

}
