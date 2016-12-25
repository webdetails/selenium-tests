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
package com.pentaho.ctools.issues.cda;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.selenium.BaseTest;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDA-103
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-923
 *
 * NOTE
 * To test this script it is required to have CDA plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDA103 extends BaseTest {
	// Access to wrapper for webdriver
	private final ElementHelper elemHelper = new ElementHelper();
	// Log instance
	private final Logger log = LogManager.getLogger( CDA103.class );

	/**
	 * ############################### Test Case 1 ###############################
	 *
	 * Test Case Name:
	 *    Asserting clearing CDA Cache returns info
	 * Description:
	 *    The test pretends validate the CDA-103 issue, so when CDA Cache
	 *    is cleared the user receives confirmation that the Cache was cleared successfully
	 *
	 * Steps:
	 *    1. Click 'Tools' menu
	 *    2. Expand 'Refresh' option
	 *    3. Click 'CDA Cache'
	 *    4. Assert text confirming that the Cache was cleared
	 */
	@Test
	public void tc01_RefreshCdaCache_ReturnsInfo() {
		this.log.info( "tc01_RefreshCdaCache_ReturnsInfo" );

		/*
		 * ## Step 1
		 */
		//Go to User Console
		driver.get( baseUrl );

		//wait for invisibility of waiting pop-up
		this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

		//Wait for menus: filemenu, viewmenu, toolsmenu AND helpmenu
		WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "filemenu" ) );
		element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "viewmenu" ) );
		assertNotNull( element );
		element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "toolsmenu" ) );
		assertNotNull( element );
		element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "helpmenu" ) );
		assertNotNull( element );
		this.elemHelper.Click( driver, By.id( "toolsmenu" ) );

		/*
		 * ## Step 2
		 */
		element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "refreshmenu" ) );
		assertNotNull( element );
		String refreshMenuText = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "refreshmenu" ) );
		assertEquals( "Refresh", refreshMenuText );
		this.elemHelper.ClickJS( driver, By.id( "refreshmenu" ) );

		/*
		 * ## Step 3
		 */
		element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "cdaClearCache" ) );
		assertNotNull( element );
		String cdaClearCacheText = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "cdaClearCache" ) );
		assertEquals( "CDA Cache", cdaClearCacheText );
		this.elemHelper.Click( driver, By.id( "cdaClearCache" ) );

		/*
		 * ## Step 4
		 */
		//wait for invisibility of waiting pop-up
		this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

		//Check tab title and text on iframe
		element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@title='CDA Cache']" ) );
		assertNotNull( element );
		String cdaTitleText = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@title='CDA Cache']" ) );
		assertEquals( "CDA Cache", cdaTitleText );

		WebElement elementFrame = this.elemHelper.FindElement( driver, By.xpath( "//iframe" ) );
		WebDriver frame = this.elemHelper.SwitchToFrame( driver, elementFrame );
		element = this.elemHelper.WaitForElementPresenceAndVisible( frame, By.xpath( "//pre" ) );
		assertNotNull( element );
		String cdaBodyText = this.elemHelper.WaitForElementPresentGetText( frame, By.xpath( "//pre" ) );
		assertEquals( "Cache Cleared Successfully", cdaBodyText );
	}

}
