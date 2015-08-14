package com.pentaho.ctools.security;

import static org.testng.Assert.assertEquals;

import java.net.HttpURLConnection;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.Test;

import com.pentaho.ctools.suite.CToolsTestSuite;
import com.pentaho.ctools.utils.BaseTest;
import com.pentaho.ctools.utils.HttpUtils;

public class AccessSystemResources extends BaseTest {
  // Instance to be used on wait commands
  private final Wait<WebDriver> wait = CToolsTestSuite.getWait();
  // The base url to be append the relative url in test
  private final String baseUrl = CToolsTestSuite.getBaseUrl();

  @Test
  public void testLoginPentaho() throws Exception {
    String url = this.baseUrl + "plugin/pentaho-cdf-dd/api/resources/system/jackrabbit/repository.xml?v=1406646663089";
    this.driver.get( url );

    //Wait for form display
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//body" ) ) );
    assertEquals( this.driver.getTitle(), "Unavailable" );
    assertEquals( this.driver.findElement( By.xpath( "//body/div/div/div" ) ).getText(), "Sorry. We really did try." );
    assertEquals( this.driver.findElement( By.xpath( "//body/div/div/div[2]" ) ).getText(), "Something went wrong. Please try again" );
    assertEquals( this.driver.findElement( By.xpath( "//body/div/div/div[3]" ) ).getText(), "or contact your administrator." );
    //Check if return HTTP Status 401
    assertEquals( HttpUtils.GetHttpStatus( url ), HttpURLConnection.HTTP_UNAUTHORIZED );
  }
}
