/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2015 by Pentaho : http://www.pentaho.com
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
package org.pentaho.ctools.suite;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.main.LoginPentaho;
import org.pentaho.ctools.main.LogoutPentaho;
import org.pentaho.ctools.suite.require.SuiteRequireJS;
import org.pentaho.ctools.suite.security.SuiteSecurity;

@RunWith( Suite.class )
@Suite.SuiteClasses( {
    // ##### Execution Order #####
    // Start Tests
    LoginPentaho.class,
    // CDF
    SuiteCDF.class,
    // CDE
    SuiteCDE.class,
    // CDA
    SuiteCDA.class,
    // CGG
    SuiteCGG.class,
    // RequireJS
    SuiteRequireJS.class,
    // Security
    SuiteSecurity.class,
    // Issues
    //SuiteIssues.class,
    //Marketplace
    //SuiteMarketplace.class,
    // End Tests
    LogoutPentaho.class } )
public class CToolsTestSuite {

  // Instance of the driver (browser emulator)
  private static WebDriver DRIVER;
  // Instance to be used on wait commands
  private static Wait<WebDriver> WAIT;
  // The base url to be append the relative url in test
  private static String BASE_URL;
  // Directory are all download files persist
  private static String DOWNLOAD_DIR;

  // Log instance
  // private static Logger log = LogManager.getLogger(CToolsTestSuite.class);
  private static Logger LOG;

  @BeforeClass
  public static void setUpClass() throws IOException {
    System.setProperty( "log4j.configurationFile", "log4j2.xml" );
    LOG = LogManager.getLogger( CToolsTestSuite.class );
    LOG.info( "Master setup" );

    // Initialize BASEURL
    BASE_URL = "http://localhost:8080/pentaho/";
    DOWNLOAD_DIR = System.getProperty( "user.home" ) + "\\SeleniumDonwloadDir";
    new File( DOWNLOAD_DIR ).mkdir();

    System.setProperty( "webdriver.log.file", "/dev/stdout" );
    //System.setProperty( "webdriver.firefox.logfile", "/dev/stdout" );

    // Setting log preferences
    LoggingPreferences logs = new LoggingPreferences();
    logs.enable( LogType.BROWSER, Level.WARNING );
    /*logs.enable( LogType.SERVER, Level.WARNING );
    logs.enable( LogType.DRIVER, Level.WARNING );
    logs.enable( LogType.PROFILER, Level.WARNING );
    logs.enable( LogType.CLIENT, Level.WARNING );
    logs.enable( LogType.PERFORMANCE, Level.WARNING );*/

    /*
     * INTERNET EXPLORER DRIVER
     */
    // Initialize DRIVER
    FirefoxProfile ffProfile = new FirefoxProfile();
    ffProfile.setPreference( "general.useragent.locale", "en-US" );
    ffProfile.setPreference( "intl.accept_languages", "en-US, en" );
    ffProfile.setPreference( "browser.download.folderList", 2 ); // 0 - Desktop, 1- Download dir, 2 - specify dir
    ffProfile.setPreference( "browser.helperApps.alwaysAsk.force", false );
    ffProfile.setPreference( "browser.download.manager.showWhenStarting", false );
    ffProfile.setPreference( "browser.download.dir", DOWNLOAD_DIR );
    ffProfile.setPreference( "browser.helperApps.neverAsk.saveToDisk", "table/excel;application/vnd.ms-excel;application/msexcel;application/x-msexcel;application/x-ms-excel;application/x-excel;application/x-dos_ms_excel;application/xls;application/x-xls;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;text/csv;application/rtf" );

    // Setting properties for webdriver
    DesiredCapabilities capabilities = DesiredCapabilities.firefox();
    capabilities.setCapability( CapabilityType.LOGGING_PREFS, logs );
    capabilities.setCapability( FirefoxDriver.PROFILE, ffProfile );

    DRIVER = new FirefoxDriver( capabilities );

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

    DRIVER.manage().window().setPosition( new Point( 0, 0 ) );
    DRIVER.manage().window().setSize( new Dimension( 1360, 764 ) );
    DRIVER.manage().timeouts().pageLoadTimeout( 60, TimeUnit.SECONDS );
    DRIVER.manage().timeouts().implicitlyWait( 30, TimeUnit.SECONDS );
    DRIVER.manage().timeouts().setScriptTimeout( 30, TimeUnit.SECONDS );

    // Initialize WAIT
    WAIT = new FluentWait<WebDriver>( DRIVER ).withTimeout( 30, TimeUnit.SECONDS ).pollingEvery( 5, TimeUnit.SECONDS );
  }

  @AfterClass
  public static void tearDownClass() {
    LOG.info( "Master tearDown" );

    /*LogEntries logEntries = DRIVER.manage().logs().get( LogType.BROWSER );
    for ( LogEntry logEntry : logEntries ) {
      LOG.info( logEntry.getMessage() );
    }*/

    DRIVER.quit();
  }

  /**
   * TODO
   *
   * @return
   */
  public static WebDriver getDriver() {
    return DRIVER;
  }

  /**
   * TODO
   *
   * @return
   */
  public static String getBaseUrl() {
    return BASE_URL;
  }

  /**
   *
   * @return
   */
  public static Wait<WebDriver> getWait() {
    return WAIT;
  }

  /**
   *
   * @return
   */
  public static String getDownloadDir() {
    return DOWNLOAD_DIR;
  }
}
