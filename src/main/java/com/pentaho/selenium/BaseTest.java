
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
package com.pentaho.selenium;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
  // Instance of the driver (browser emulator)
  protected static WebDriver driver;
  // The base url to be append the relative url in test
  protected static String baseUrl;
  // Directory are all download files persist
  protected static String downloadDir;
  // The BA Server URL
  protected static String pentahoBaServerUrl;
  // The BA Server hostname
  protected static String pentahoBaServerHostname;
  // The BA Server port
  protected static String pentahoBaServerPort;
  // The BA Server service name (Windows only)
  protected static String pentahoBaServerServiceName;
  // The BA Server username to use on tests
  protected static String pentahoBaServerUsername = "admin";
  // The BA Server password to use on tests
  protected static String pentahoBaServerPassword = "password";

  // Log instance
  private static Logger log = LogManager.getLogger( BaseTest.class );

  @BeforeSuite
  public void setUpClass() {
    log.info( "Master setup" );

    // Initialize BASEURL
    baseUrl = "http://localhost:8080/pentaho/";
    downloadDir = System.getProperty( "java.io.tmpdir" ) + "/SeleniumDownloadDir";
    pentahoBaServerServiceName = System.getProperty( "pentaho.bi.server.service.name" );
    pentahoBaServerUrl = System.getProperty( "pentaho.bi.server.url" );
    pentahoBaServerHostname = System.getProperty( "pentaho.bi.server.hostname" );
    pentahoBaServerPort = System.getProperty( "pentaho.bi.server.port" );

    new File( downloadDir ).mkdir();

    // Setting log preferences
    LoggingPreferences logs = new LoggingPreferences();
    logs.enable( LogType.BROWSER, Level.ALL );
    logs.enable( LogType.SERVER, Level.ALL );
    logs.enable( LogType.DRIVER, Level.ALL );
    logs.enable( LogType.PROFILER, Level.ALL );
    logs.enable( LogType.CLIENT, Level.ALL );
    logs.enable( LogType.PERFORMANCE, Level.ALL );

    /*
     * Firefox DRIVER
     */
    // Initialize DRIVER
    System.setProperty( "webdriver.firefox.logfile", System.getProperty( "java.io.tmpdir" ) + "/webdriver-firefox.log" );

    FirefoxOptions foptions = new FirefoxOptions();
    foptions.addPreference( "webdriver.log.file", System.getProperty( "java.io.tmpdir" ) + "/webdriver.log" );
    foptions.addPreference( "general.useragent.locale", "en-US" );
    foptions.addPreference( "intl.accept_languages", "en-US, en" );
    foptions.addPreference( "browser.download.folderList", 2 ); // 0 - Desktop, 1- Download dir, 2 - specify dir
    foptions.addPreference( "browser.helperApps.alwaysAsk.force", false );
    foptions.addPreference( "browser.tabs.remote.autostart", false );
    foptions.addPreference( "browser.download.manager.showWhenStarting", false );
    foptions.addPreference( "browser.download.dir", downloadDir );
    foptions.addPreference( "browser.helperApps.neverAsk.saveToDisk", "application/unknown;table/excel;application/vnd.ms-excel;application/msexcel;application/x-msexcel;application/x-ms-excel;application/x-excel;application/x-dos_ms_excel;application/xls;application/x-xls;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;text/csv;application/rtf;text/xml;application/xml;image/png;image/svg+xml;application/json;application/javascript" );
    foptions.setCapability( CapabilityType.LOGGING_PREFS, logs );
    foptions.setLogLevel( FirefoxDriverLogLevel.DEBUG );

    BaseTest.driver = new FirefoxDriver( foptions );

    /*
     * INTERNET EXPLORER DRIVER
     */
    /*
     * System.setProperty("webdriver.ie.driver", "C:\\IEDriverServer_Win32_2.44.0\\IEDriverServer.exe");
     * System.setProperty("webdriver.ie.driver.host", "10.120.42.25");
     * System.setProperty("webdriver.ie.driver.loglevel", "FATAL"); System.setProperty("webdriver.ie.driver.loglevel",
     * downloadDir + "\\seleniumlogs.txt");
     *
     * // We could use any driver for our tests... DesiredCapabilities capabilities = new DesiredCapabilities();
     * capabilities.setBrowserName("internet explorer"); capabilities.setVersion("8");
     * capabilities.setPlatform(Platform.WINDOWS); capabilities.setCapability("platform", "WINDOWS");
     * capabilities.setJavascriptEnabled(true); //capabilities.setCapability(InternetExplorerDriver.HOST,
     * "10.120.40.243");
     *
     * capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
     * capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
     *
     * // Get a handle to the driver. This will throw an exception // if a matching driver cannot be located driver =
     * new RemoteWebDriver(new URL("http://10.120.42.25:4444/wd/hub"), capabilities); //driver = new
     * InternetExplorerDriver();
     */

    BaseTest.driver.manage().window().setPosition( new Point( 0, 0 ) );
    BaseTest.driver.manage().window().setSize( new Dimension( 1360, 764 ) );
    BaseTest.driver.manage().timeouts().pageLoadTimeout( 180, TimeUnit.SECONDS );
    BaseTest.driver.manage().timeouts().implicitlyWait( 30, TimeUnit.SECONDS );
    BaseTest.driver.manage().timeouts().setScriptTimeout( 30, TimeUnit.SECONDS );
  }

  @AfterSuite
  public void tearDownClass() {
    log.info( "Master tearDown" );

    BaseTest.printBrowserMessages();

    BaseTest.driver.quit();
  }

  /**
   * Print all Messages regarding
   * @return
   */
  public static void printBrowserMessages() {
    log.info( "------> PRINTING LOGS FROM BROWSER <-----" );
    /*LogEntries logEntries = driver.manage().logs().get( LogType.BROWSER ); 
    for ( LogEntry logEntry : logEntries ) {
      log.info( logEntry.getMessage() ); 
    }
    log.info( "------> PRINTING LOGS FROM SERVER <-----");
    LogEntries logEntries2 = driver.manage().logs().get( LogType.SERVER ); 
    for ( LogEntry logEntry : logEntries2 ) {
      log.info( logEntry.getMessage() ); 
    }*/
    /*log.info( "------> PRINTING LOGS FROM DRIVER <-----");
    LogEntries logEntries3 = driver.manage().logs().get( LogType.DRIVER ); 
    for ( LogEntry logEntry : logEntries3 ) {
      log.info( logEntry.getMessage() ); 
    }
    log.info( "------> PRINTING LOGS FROM PROFILER <-----");
    LogEntries logEntries4 = driver.manage().logs().get( LogType.PROFILER ); 
    for ( LogEntry logEntry : logEntries4 ) {
      log.info( logEntry.getMessage() ); 
    }
    log.info( "------> PRINTING LOGS FROM CLIENT <-----");
    LogEntries logEntries5 = driver.manage().logs().get( LogType.CLIENT ); 
    for ( LogEntry logEntry : logEntries5 ) {
      log.info( logEntry.getMessage() ); 
    }
    log.info( "------> PRINTING LOGS FROM PERFORMANCE <-----");
    LogEntries logEntries6 = driver.manage().logs().get( LogType.PERFORMANCE ); 
    for ( LogEntry logEntry : logEntries6 ) {
      log.info( logEntry.getMessage() ); 
    }
    log.info( "------> PRINTING LOGS JavaScriptError <-----");
    final List<JavaScriptError> jsErrors = JavaScriptError.readErrors(driver);
    log.debug( jsErrors );*/
    log.info( "------> END <-----" );
  }

  /**
   * TODO
   *
   * @return
   */
  public static String getBaseUrl() {
    return baseUrl;
  }

  /**
   *
   * @return
   */
  public static String getDownloadDir() {
    return downloadDir;
  }

  /**
   * Returns the current state of WebDriver.
   *
   * @return The webdriver instance.
   */
  public static WebDriver getDriver() {
    return driver;
  }
}
