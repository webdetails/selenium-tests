package com.pentaho.ctools.security;

import static org.testng.Assert.assertEquals;

import java.net.HttpURLConnection;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.HttpUtils;
import com.pentaho.selenium.BaseTest;

public class AccessSystemResources extends BaseTest {

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Access denied to specific webpage
   * Description:
   *    The test case pretends to validate when user tries to access system files
   *    returns access denied, on the page:
   *    plugin/pentaho-cdf-dd/api/resources/system/jackrabbit/repository.xml
   *
   * Steps:
   *    1. Check 401 on http status when access the page.
   */
  @Test
  public void tc1_AccessPage_FailAccessing() {
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

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Access denied to specific webpage
   * Description:
   *    The test case pretends to validate when user tries to access system files
   *    returns access denied, on the page:
   *    plugin/pentaho-cdf-dd/api/resources/get?resource=../jackrabbit/repository.xml
   *
   * Steps:
   *    1. Check 401 on http status when access the page.
   */
  @Test
  public void tc2_AccessPage2_FailAccessing() {
    String url = baseUrl + "plugin/pentaho-cdf-dd/api/resources/get?resource=../jackrabbit/repository.xml";
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
