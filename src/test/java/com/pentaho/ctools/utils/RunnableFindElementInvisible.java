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

public class RunnableFindElementInvisible implements Runnable {

	private WebDriver driver;
	private long timeout;
	private long pollingTime;
	private By locator;

	private WebElement theElement;

	public RunnableFindElementInvisible( WebDriver driver, long timeout, long pollingTime, By locator ) {
		super();
		this.driver = driver;
		this.timeout = timeout;
		this.pollingTime = pollingTime;
		this.locator = locator;
	}

	public WebElement getInvisbleElement() {
		return this.theElement;
	}

	public By getLocator() {
		return this.locator;
	}

	@Override
	public void run() {
		Wait<WebDriver> wait = new FluentWait<>( this.driver ).withTimeout( this.timeout, TimeUnit.SECONDS ).pollingEvery( this.pollingTime, TimeUnit.MILLISECONDS );

		// Wait for element present and not visible
		this.theElement = wait.until( new Function<WebDriver, WebElement>() {

			@Override
			public WebElement apply( WebDriver d ) {
				try {
					List<WebElement> listElem = d.findElements( getLocator() );
					if ( listElem.size() > 0 ) {
						WebElement elem = listElem.get( 0 );
						if ( elem != null && ( elem.isEnabled() && !elem.isDisplayed() ) ) {
							return elem;
						}
						return null;
					}
					return null;
				} catch ( StaleElementReferenceException sere ) {
					return null;
				}
			}
		} );
	}

}
