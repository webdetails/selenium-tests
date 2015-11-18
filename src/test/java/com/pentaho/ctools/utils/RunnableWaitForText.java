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

class RunnableWaitForText implements Runnable {

  private WebDriver driver;
  private Integer timeout;
  private Integer pollingTime;
  private By locator;

  private String textToWait;
  private Boolean textIsEquals;
  private String currentTextPresent;

  public RunnableWaitForText( WebDriver driver, Integer timeout, Integer pollingTime, By locator, String textToWait ) {
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
    Wait<WebDriver> wait = new FluentWait<WebDriver>( this.driver ).withTimeout( this.timeout, TimeUnit.SECONDS ).pollingEvery( this.pollingTime, TimeUnit.MILLISECONDS );

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
              return getTextPresent().equals( getTextToWait() ); //If true we stop waiting for.
            }
            return false;
          }
          return false;
        } catch ( StaleElementReferenceException sere ) {
          return false;
        }
      }
    } );
  }
}
