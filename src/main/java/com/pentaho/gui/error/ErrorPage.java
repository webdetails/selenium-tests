/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2002 - 2026 by Pentaho Canada Inc. : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2030-06-15
 ******************************************************************************/

package com.pentaho.gui.error;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.pentaho.ctools.utils.ElementHelper;

public class ErrorPage {
	// The Driver
	private WebDriver driver = null;
	// Access to wrapper for webdriver
	private ElementHelper elemHelper = new ElementHelper();
	// Logging instance
	private final Logger log = LogManager.getLogger( ErrorPage.class );

	/**
	 * Constructor.
	 *
	 * @param driver
	 */
	public ErrorPage( WebDriver driver ) {
		this.driver = driver;
	}

	/**
	 * The method will evaluate if the page present is the "Sorry. something went wrong.".
	 */
	public boolean isPageSorryWeDidTryPresent() {
		this.log.debug( "Check for error page \"Sorry. something went wrong.\"." );

		boolean pagePresent = false;

		this.elemHelper.WaitForElementPresence( this.driver, By.cssSelector( "div.warning" ) );

		String webPageTitle = this.elemHelper.WaitForTitle( this.driver, "Unavailable" );
		String errorTitle = this.elemHelper.WaitForTextDifferentEmpty( this.driver, By.cssSelector( "div.warning-header" ) );
		String errorPhrase1 = this.elemHelper.WaitForTextDifferentEmpty( this.driver, By.cssSelector( "div:nth-child(2)" ) );
		String errorPhrase2 = this.elemHelper.WaitForTextDifferentEmpty( this.driver, By.cssSelector( "div:nth-child(3)" ) );

		pagePresent = webPageTitle.equals( "Unavailable" ) && errorTitle.equals( "Sorry, something went wrong." ) && errorPhrase1.equals( "Please try again or contact" ) && errorPhrase2.equals( "your system administrator." );

		return pagePresent;
	}
}
