package com.pentaho.ctools.security;

import static org.testng.Assert.assertEquals;

import java.net.HttpURLConnection;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.BaseTest;
import com.pentaho.ctools.utils.HttpUtils;

public class AccessSystemResources extends BaseTest {

  /**
   * 
   */
  @Test
  public void tc01_AccessPage_FailAccessing() {
    String url = baseUrl + "plugin/pentaho-cdf-dd/api/resources/system/jackrabbit/repository.xml?v=1406646663089";
    driver.get( url );

    //Wait for form display
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//body" ) ) );
    assertEquals( driver.getTitle(), "Unavailable" );
    assertEquals( driver.findElement( By.xpath( "//body/div/div/div" ) ).getText(), "Sorry. We really did try." );
    assertEquals( driver.findElement( By.xpath( "//body/div/div/div[2]" ) ).getText(), "Something went wrong. Please try again" );
    assertEquals( driver.findElement( By.xpath( "//body/div/div/div[3]" ) ).getText(), "or contact your administrator." );
    //Check if return HTTP Status 401
    assertEquals( HttpUtils.GetHttpStatus( url ), HttpURLConnection.HTTP_UNAUTHORIZED );
  }
}
