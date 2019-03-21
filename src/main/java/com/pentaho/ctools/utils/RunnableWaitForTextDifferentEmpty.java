package com.pentaho.ctools.utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

class RunnableWaitForTextDifferentEmpty implements Runnable {

  private String currentTextPresent;
  private final WebDriver driver;
  private final By locator;
  private final long pollingTime;

  private final long timeout;

  public RunnableWaitForTextDifferentEmpty( final WebDriver driver, final long timeout, final long pollingTime,
      final By locator ) {
    super();
    this.driver = driver;
    this.timeout = timeout;
    this.pollingTime = pollingTime;
    this.locator = locator;
  }

  public By getLocator() {
    return this.locator;
  }

  public String getTextPresent() {
    return this.currentTextPresent;
  }

  @Override
  public void run() {
    final Wait<WebDriver> wait = new FluentWait<>( this.driver ).withTimeout( Duration.ofSeconds( this.timeout ) ).pollingEvery( Duration.ofMillis( this.pollingTime ) );

    // Wait for element visible
    wait.until( d -> {
      try {
        final List<WebElement> listElem = d.findElements( RunnableWaitForTextDifferentEmpty.this.getLocator() );
        if ( listElem.size() > 0 ) {
          final WebElement elem = listElem.get( 0 );
          if ( elem.isEnabled() ) {
            final String text = elem.getText();
            RunnableWaitForTextDifferentEmpty.this.setTextPresent( text );
            return Boolean.valueOf( !( RunnableWaitForTextDifferentEmpty.this.getTextPresent().isEmpty() || RunnableWaitForTextDifferentEmpty.this.getTextPresent().equals( "" ) || RunnableWaitForTextDifferentEmpty.this.getTextPresent().equalsIgnoreCase( "null" ) ) );// If true we stop waiting for.
          }
          return Boolean.valueOf( false );
        }
        return Boolean.valueOf( false );
      } catch ( final StaleElementReferenceException sere ) {
        return Boolean.valueOf( false );
      }
    } );
  }

  public void setTextPresent( final String text ) {
    this.currentTextPresent = text;
  }
}
