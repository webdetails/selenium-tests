package com.pentaho.ctools.utils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;

class RunnableWaitForTextDifferentOf implements Runnable {

	private WebDriver driver;
	private long timeout;
	private long pollingTime;
	private By locator;
	private String text;

	private Boolean textIsDifferent;
	private String currentTextPresent;

	public RunnableWaitForTextDifferentOf( WebDriver driver, String text, long timeout, long pollingTime, By locator ) {
		super();
		this.driver = driver;
		this.timeout = timeout;
		this.pollingTime = pollingTime;
		this.locator = locator;
		this.text = text;
	}

	public Boolean getTextIsDifferent() {
		return this.textIsDifferent;
	}

	public String getTextPresent() {
		return this.currentTextPresent;
	}

	public void setTextPresent( String text ) {
		this.currentTextPresent = text;
	}

	public By getLocator() {
		return this.locator;
	}

	public String getText() {
		return this.text;
	}

	@Override
	public void run() {
		Wait<WebDriver> wait = new FluentWait<>( this.driver ).withTimeout( this.timeout, TimeUnit.SECONDS ).pollingEvery( this.pollingTime, TimeUnit.MILLISECONDS );

		// Wait for element visible
		this.textIsDifferent = wait.until( new Function<WebDriver, Boolean>() {

			@Override
			public Boolean apply( WebDriver d ) {
				try {
					List<WebElement> listElem = d.findElements( getLocator() );
					if ( listElem != null && listElem.size() > 0 ) {
						WebElement elem = listElem.get( 0 );
						if ( elem != null && elem.isEnabled() ) {
							String currentText = elem.getText();
							setTextPresent( currentText );
							return Boolean.valueOf( !currentText.equals( getText() ) );
						}
						return Boolean.valueOf( false );
					}
					return Boolean.valueOf( false );
				} catch ( StaleElementReferenceException sere ) {
					return Boolean.valueOf( false );
				}
			}
		} );
	}
}
