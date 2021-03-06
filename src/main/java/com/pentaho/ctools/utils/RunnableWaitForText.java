package com.pentaho.ctools.utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;

class RunnableWaitForText implements Runnable {

  private WebDriver driver;
  private long timeout;
  private long pollingTime;
  private By locator;

  private String textToWait;
  private Boolean textIsEquals;
  private String currentTextPresent;

  public RunnableWaitForText( WebDriver driver, long timeout, long pollingTime, By locator, String textToWait ) {
    super();
    this.driver = driver;
    this.timeout = timeout;
    this.pollingTime = pollingTime;
    this.locator = locator;
    this.textToWait = textToWait;
  }

  public Boolean isTextEquals() {
    return this.textIsEquals;
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

  public String getTextToWait() {
    return this.textToWait;
  }

  @Override
  public void run() {
    Wait<WebDriver> wait = new FluentWait<>( this.driver ).withTimeout( Duration.ofSeconds( this.timeout ) ).pollingEvery( Duration.ofMillis( this.pollingTime ) );

    // Wait for element visible
    this.textIsEquals = wait.until( new Function<WebDriver, Boolean>() {

      @Override
      public Boolean apply( WebDriver d ) {
        try {
          List<WebElement> listElem = d.findElements( getLocator() );
          if ( listElem.size() > 0 ) {
            WebElement elem = listElem.get( 0 );
            if ( elem.isEnabled() ) {
              String text = elem.getText();
              setTextPresent( text );
              return Boolean.valueOf( getTextPresent().equals( getTextToWait() ) ); // If true we stop waiting for.
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
