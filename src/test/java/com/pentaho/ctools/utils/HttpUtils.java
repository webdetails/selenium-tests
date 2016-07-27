/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2016 by Pentaho : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/
package com.pentaho.ctools.utils;

import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;

public class HttpUtils {

  private static Logger LOG = LogManager.getLogger( HttpUtils.class );

  /**
   * This method shall return the status of HTTP request.
   *
   * @param url
   * @return
   * @throws Exception
   */
  public static int GetHttpStatus( String url ) {
    LOG.debug( "The URL: " + url );
    int nStatusCode = HttpStatus.SC_BAD_REQUEST;

    try {
      URL oUrl = new URL( url );
      URLConnection uc = oUrl.openConnection();
      uc.connect();
      nStatusCode = ( (HttpURLConnection) uc ).getResponseCode();
      LOG.debug( "HTTP Status:" + nStatusCode );
    } catch ( Exception ex ) {
      LOG.error( ex.getMessage() );
    }

    return nStatusCode;
  }

  /**
   * This method shall look for HttpErrors return the status of HTTP request. Will return false if no error with that number was found.
   *
   * @param driver
   * @param ErrorNumber
   * @return
   * @throws Exception
   */
  public static boolean GetHttpError( WebDriver driver, String ErrorNumber ) {
    Boolean errorFound = false;
    driver.manage().timeouts().implicitlyWait( 1, TimeUnit.SECONDS );

    for ( int i = 0; i < 1000; i++ ) {
      try {
        driver.findElement( By.id( "web_" + ErrorNumber ) );
        errorFound = true;
      } catch ( NoSuchElementException s ) {
        LOG.error( "NoSuchElement - got it." );
        break;
      } catch ( StaleElementReferenceException s ) {
        LOG.error( "Stale - got it." );
      }
    }
    driver.manage().timeouts().implicitlyWait( 30, TimeUnit.SECONDS );
    return errorFound;
  }

  /**
   * This method shall return the status of HTTP request. When authentication is needed.
   *
   * @param url
   * @param username
   * @param password
   * @return
   * @throws Exception
   */
  public static int GetHttpStatus( String url, String username, String password ) {
    LOG.debug( "The URL: " + url );
    int nStatusCode = HttpStatus.SC_BAD_REQUEST;

    String authString = username + ":" + password;
    String authStringEnc = Base64.getEncoder().encodeToString( authString.getBytes() );

    URL oUrl;
    try {
      oUrl = new URL( url );

      URLConnection urlConnection = oUrl.openConnection();
      urlConnection.setRequestProperty( "Authorization", "Basic " + authStringEnc );
      urlConnection.connect();

      nStatusCode = ( (HttpURLConnection) urlConnection ).getResponseCode();
    } catch ( Exception e ) {
      LOG.error( "Exception trying to access: " + url, e );
    }
    return nStatusCode;
  }

  /**
   * The method check if a host is listening in the specified port.
   * 
   * @param host - Host name.
   * @param port - Port
   * @return - true is listening in the port, false otherwise.
   */
  public static boolean ServerListening( String host, int port ) {
    try (Socket s = new Socket( host, port );) {
      return true;
    } catch ( Exception e ) {
      return false;
    }
  }
}
