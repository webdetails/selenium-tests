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

  public ErrorPage( WebDriver driver ) {
    this.driver = driver;
  }

  /**
   * The method will evaluate if the page present is the "Sorry. We really did try.".
   */
  public Boolean isPageSorryWeDidTryPresent() {
    this.log.debug( "Check for error page \"Sorry. We really did try.\"." );

    Boolean pagePresent = false;

    this.elemHelper.WaitForElementPresence( this.driver, By.cssSelector( "div.warning" ) );

    String webPageTitle = this.elemHelper.WaitForTitle( this.driver, "Unavailable" );
    String errorTitle = this.elemHelper.WaitForTextDifferentEmpty( this.driver, By.cssSelector( "div.warning-header" ) );
    String errorPhrase1 = this.elemHelper.WaitForTextDifferentEmpty( this.driver, By.cssSelector( "div:nth-child(2)" ) );
    String errorPhrase2 = this.elemHelper.WaitForTextDifferentEmpty( this.driver, By.cssSelector( "div:nth-child(3)" ) );

    pagePresent = webPageTitle.equals( "Unavailable" ) && errorTitle.equals( "Sorry. We really did try." ) && errorPhrase1.equals( "Something went wrong. Please try again" ) && errorPhrase2.equals( "or contact your administrator." );

    return pagePresent;
  }
}
