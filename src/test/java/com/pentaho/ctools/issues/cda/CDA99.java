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
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.Iterator;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.ctools.utils.SamplePath;
import com.pentaho.gui.error.ErrorPage;
import com.pentaho.selenium.BaseTest;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDA-99
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-963
 *
 * NOTE
 * To test this script it is required to have CDA plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDA99 extends BaseTest {
	// Indicates if the sample changed
	protected boolean sampleChanged = false;
	// The preview window
	protected WebDriver previewWindow = null;
	// The main window
	protected String currentWindowHandle = "";
	// Access to wrapper for webdriver
	private final ElementHelper elemHelper = new ElementHelper();
	// Log instance
	private final Logger log = LogManager.getLogger( CDA99.class );

	/**
	 * ############################### Test Case 1 ###############################
	 *
	 * Test Case Name:
	 *    Changing connection type
	 * Description:
	 *    The test pretends validate the CDA-99 issue, so if we change the
	 *    connection type to olap4j.defaultolap, the query are not performed,
	 *    but adding olap4j it works.
	 * Steps:
	 *    1. Check the contents
	 *    2. Change the code adding type='olap4j.defaultolap4j'
	 *    3. Perform the preview and the query couldn't be performed
	 *    4. Change the code adding type='olap4j'
	 *    5. Perform the preview and the query could be performed
	 */
	@Test
	public void tc01_PageContent_DisplayContent() {
		this.log.info( "tc01_PageContent_DisplayContent" );

		/*
		 * ## Step 1
		 */
		// Go to Olap4j Sample
		driver.get( PageUrl.OLAP4J_EDIT );

		// Wait for buttons: preview, reload, save AND file
		WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "preview" ) );
		assertNotNull( element );
		element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "reload" ) );
		assertNotNull( element );
		element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "save" ) );
		assertNotNull( element );
		element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "staticfile" ) );
		assertNotNull( element );
		String buttonPreviewText = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "preview" ) );
		String buttonReloadText = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "reload" ) );
		String buttonSaveText = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "save" ) );
		String buttonStaticFileText = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "staticfile" ) );
		assertEquals( buttonPreviewText, "Preview" );
		assertEquals( buttonReloadText, "Reload" );
		assertEquals( buttonSaveText, "Save" );
		assertEquals( buttonStaticFileText, SamplePath.OLAP4J );

		// Check iframe
		this.elemHelper.SwitchToFrame( driver, "externalEditor" );
		this.elemHelper.WaitForElementVisibility( driver, By.xpath( "//pre/div[2]/div/div[3]/div[1]" ) );
		this.elemHelper.SwitchToDefault( driver );

		/*
		 * ## Step 2
		 */
		String code = ( (JavascriptExecutor) driver ).executeScript( "return getEditorWindow().editor.getContents();" ).toString();
		code = code.replace( "<DataAccess id=\"1\" connection=\"1\" type=\"olap4j\" access=\"public\">", "<DataAccess id=\"1\" connection=\"1\" type=\"olap4j.defaultolap4j\" access=\"public\">" );
		( (JavascriptExecutor) driver ).executeScript( "getEditorWindow().editor.setContents(arguments[0]);", code );

		// Save file
		this.elemHelper.ClickJS( driver, By.id( "save" ) );
		this.sampleChanged = true;
		// Check for the message name
		String fileSaved = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "notifications" ) ).getText();
		assertEquals( SamplePath.OLAP4J + " saved ok.", fileSaved );

		/*
		 * ## Step 3
		 */
		// Perform the preview of this CDA query
		this.elemHelper.ClickJS( driver, By.id( "preview" ) );

		String currentWindowHandle = driver.getWindowHandle();
		Set<String> listWindows = driver.getWindowHandles();

		// wait for popup render
		this.elemHelper.WaitForNewWindow( driver );
		listWindows = driver.getWindowHandles();
		// Get the windowHandler of the new open window
		Iterator<String> iterWindows = listWindows.iterator();
		while ( iterWindows.hasNext() ) {
			String windowHandle = iterWindows.next();
			if ( windowHandle.equals( currentWindowHandle ) == false ) {
				this.previewWindow = driver.switchTo().window( windowHandle );
				break;
			}
		}

		// Now in the PREVIEW WINDOW we want to check the available options
		ErrorPage errorPage = new ErrorPage( this.previewWindow );
		assertTrue( errorPage.isPageSorryWeDidTryPresent() );

		// Need guarantee we close everything
		this.previewWindow.close();
		this.previewWindow = null;
		driver.switchTo().window( currentWindowHandle );

		/*
		 * ## Step 4
		 */
		String code2 = ( (JavascriptExecutor) driver ).executeScript( "return getEditorWindow().editor.getContents();" ).toString();
		code2 = code2.replace( "<DataAccess id=\"1\" connection=\"1\" type=\"olap4j.defaultolap4j\" access=\"public\">", "<DataAccess id=\"1\" connection=\"1\" type=\"olap4j\" access=\"public\">" );
		( (JavascriptExecutor) driver ).executeScript( "getEditorWindow().editor.setContents(arguments[0]);", code2 );

		// Save file
		this.elemHelper.ClickJS( driver, By.id( "save" ) );
		this.sampleChanged = false;
		// Check for the message name
		String fileSaved2 = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "notifications" ) );
		assertEquals( SamplePath.OLAP4J + " saved ok.", fileSaved2 );

		/*
		 * ## Step 5
		 */
		// Perform the preview of this CDA query
		this.elemHelper.ClickJS( driver, By.id( "preview" ) );

		this.previewWindow = null;
		listWindows = null;
		// wait for popup render
		this.elemHelper.WaitForNewWindow( driver );
		listWindows = driver.getWindowHandles();
		// Get the windowHandler of the new open window
		iterWindows = null;
		iterWindows = listWindows.iterator();
		while ( iterWindows.hasNext() ) {
			String windowHandle = iterWindows.next();
			if ( windowHandle.equals( currentWindowHandle ) == false ) {
				this.previewWindow = driver.switchTo().window( windowHandle );
				break;
			}
		}

		// Now in the PREVIEW WINDOW we want to check the available options
		boolean selectNotExist = false;
		// wait for file id contains text
		this.elemHelper.WaitForTextPresence( this.previewWindow, By.id( "fileid" ), "/public/plugin-samples/cda/cdafiles/olap4j.cda" );
		// Selector must be present
		WebElement selector = this.elemHelper.FindElement( this.previewWindow, By.id( "dataAccessSelector" ) );
		assertNotNull( selector );

		try {
			Select select = new Select( selector );
			select.selectByIndex( 1 );
			// NOTE - we have to wait for loading disappear
			this.elemHelper.WaitForElementInvisibility( this.previewWindow, By.cssSelector( "div.blockUI.blockOverlay" ) );
			// Get value of status
			String value = this.elemHelper.GetInputValue( this.previewWindow, By.id( "status" ) );
			assertEquals( "In Process", value );
		} catch ( NoSuchElementException see ) {
			this.log.warn( see );
			selectNotExist = true;
		}

		// Need guarantee we close everything
		this.previewWindow.close();
		driver.switchTo().window( currentWindowHandle );

		assertFalse( selectNotExist );
	}

	/**
	 * The tear down method of this class.
	 * If the preview window stills opened, we close it.
	 * If the file olap4j.cda was changed, we revert the change.
	 */
	@Override
	@AfterClass( alwaysRun = true )
	public void tearDownClass() {
		this.log.debug( "tearDownClass" );
		if ( this.previewWindow != null ) {
			this.log.debug( "closing preview" );
			this.previewWindow.close();
			driver.switchTo().window( this.currentWindowHandle );
		}

		if ( this.sampleChanged ) {
			this.log.debug( "revert changes" );
			// If we changed the sample, so we need to revert the change.
			driver.get( PageUrl.OLAP4J_EDIT );

			// Wait for buttons: preview, reload, save AND file
			this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "preview" ) );
			this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "reload" ) );
			this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "save" ) );
			this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "staticfile" ) );
			this.elemHelper.WaitForElementPresentGetText( driver, By.id( "preview" ) );
			this.elemHelper.WaitForElementPresentGetText( driver, By.id( "reload" ) );
			this.elemHelper.WaitForElementPresentGetText( driver, By.id( "save" ) );
			this.elemHelper.WaitForElementPresentGetText( driver, By.id( "staticfile" ) );

			// Check iframe
			this.elemHelper.SwitchToFrame( driver, "externalEditor" );
			this.elemHelper.WaitForElementVisibility( driver, By.xpath( "//pre/div[2]/div/div[3]/div[1]" ) );
			this.elemHelper.SwitchToDefault( driver );

			/*
			 * ## Step 2
			 */
			String code = ( (JavascriptExecutor) driver ).executeScript( "return getEditorWindow().editor.getContents();" ).toString();
			code = code.replace( "<DataAccess id=\"1\" connection=\"1\" type=\"olap4j.defaultolap4j\" access=\"public\">", "<DataAccess id=\"1\" connection=\"1\" type=\"olap4j\" access=\"public\">" );
			( (JavascriptExecutor) driver ).executeScript( "getEditorWindow().editor.setContents(arguments[0]);", code );

			// Save file
			this.elemHelper.ClickJS( driver, By.id( "save" ) );
			// Check for the message name
			this.elemHelper.WaitForElementPresentGetText( driver, By.id( "notifications" ) );
		}
	}
}
