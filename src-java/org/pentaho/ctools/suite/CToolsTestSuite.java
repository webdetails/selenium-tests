/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2014 by Pentaho : http://www.pentaho.com
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.cdf.AutoCompleteBoxComponent;
import org.pentaho.ctools.cdf.ButtonComponent;
import org.pentaho.ctools.cdf.CheckComponent;
import org.pentaho.ctools.cdf.CommentComponent;
import org.pentaho.ctools.cdf.DateInputComponent;
import org.pentaho.ctools.cdf.DateRangeInputComponent;
import org.pentaho.ctools.cdf.DialComponent;
import org.pentaho.ctools.cdf.ExecutePrptComponent;
import org.pentaho.ctools.cdf.ExecuteXactionComponent;
import org.pentaho.ctools.cdf.JFreeChartComponent;
import org.pentaho.ctools.cdf.MetaLayerHomeDashboard;
import org.pentaho.ctools.cdf.MonthPickerComponent;
import org.pentaho.ctools.cdf.MultiButtonComponent;
import org.pentaho.ctools.cdf.OpenFlashChartComponent;
import org.pentaho.ctools.cdf.PrptComponent;
import org.pentaho.ctools.cdf.QueryComponent;
import org.pentaho.ctools.cdf.RadioComponent;
import org.pentaho.ctools.cdf.SchedulePrptComponent;
import org.pentaho.ctools.cdf.SelectComponent;
import org.pentaho.ctools.cdf.SelectMultiComponent;
import org.pentaho.ctools.cdf.TableComponent;
import org.pentaho.ctools.cdf.TextComponent;
import org.pentaho.ctools.cdf.TextInputComponent;
import org.pentaho.ctools.cdf.TimePlotComponent;
import org.pentaho.ctools.cdf.TrafficComponent;
import org.pentaho.ctools.cdf.VisualizationAPIComponent;
import org.pentaho.ctools.cdf.XactionComponent;
import org.pentaho.ctools.cgg.BarChart;
import org.pentaho.ctools.cgg.DialChart;
import org.pentaho.ctools.cgg.ScatterChart;
import org.pentaho.ctools.main.LoginPentaho;
import org.pentaho.ctools.main.LogoutPentaho;
import org.pentaho.ctools.security.AccessSystemResources;

@RunWith(Suite.class)
@Suite.SuiteClasses({
/*LoginPentaho.class,
CommentComponent.class,
LogoutPentaho.class*/
//##### Execution Order #####
//Start Tests
LoginPentaho.class,
//CDF
AutoCompleteBoxComponent.class, ButtonComponent.class, CheckComponent.class, CommentComponent.class, DateInputComponent.class, DateRangeInputComponent.class, DialComponent.class, ExecutePrptComponent.class, ExecuteXactionComponent.class, JFreeChartComponent.class, MetaLayerHomeDashboard.class, MonthPickerComponent.class, MultiButtonComponent.class, OpenFlashChartComponent.class, PrptComponent.class, QueryComponent.class, RadioComponent.class, SchedulePrptComponent.class, SelectComponent.class, SelectMultiComponent.class, TableComponent.class, TextComponent.class, TextInputComponent.class, TimePlotComponent.class, TrafficComponent.class, VisualizationAPIComponent.class, XactionComponent.class,
//CDE
//MapComponentReference.class,
//CDE - Widgets
//CreateWidget.class,
//AddParamTableComponent.class,
//SelectCdaFileAsDatasource.class,
//SimpleExtensionPoints.class,
//CGG
BarChart.class, ScatterChart.class, DialChart.class,
//Security
AccessSystemResources.class,
//End Tests
LogoutPentaho.class})
public class CToolsTestSuite {
  /// Instance of the driver (browser emulator)
  private static WebDriver       driver;
  // Instance to be used on wait commands
  private static Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private static String          baseUrl;
  //Directory are all download files persist
  private static String          downloadDir;

  //Log instance
  //private static Logger log = LogManager.getLogger(CToolsTestSuite.class);
  private static Logger          log;

  @BeforeClass
  public static void setUpClass() throws IOException {
    System.setProperty("log4j.configurationFile", "log4j2.xml");
    log = LogManager.getLogger(CToolsTestSuite.class);
    log.info("Master setup");

    //Initialize BASEURL
    baseUrl = "http://localhost:8080/pentaho/";
    downloadDir = System.getProperty("user.home") + "\\SeleniumDonwloadDir";
    new File(downloadDir).mkdir();

    //System.setProperty("webdriver.log.file", "/dev/stdout");
    //System.setProperty("webdriver.firefox.logfile", "/dev/stdout");

    //Setting log preferences
    //LoggingPreferences logs = new LoggingPreferences();
    //logs.enable(LogType.BROWSER, Level.ALL);
    //logs.enable(LogType.SERVER, Level.ALL);
    //logs.enable(LogType.DRIVER, Level.ALL);
    //logs.enable(LogType.PROFILER, Level.ALL);
    //logs.enable(LogType.CLIENT, Level.ALL);
    //logs.enable(LogType.PERFORMANCE, Level.ALL);

    //Initialize DRIVER
    FirefoxProfile ffProfile = new FirefoxProfile();
    ffProfile.setPreference("intl.accept_languages", "en-us");
    ffProfile.setPreference("browser.download.folderList", 2);//0 - Desktop, 1- Donwload dir, 2 - specify dir
    ffProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
    ffProfile.setPreference("browser.download.manager.showWhenStarting", false);
    ffProfile.setPreference("browser.download.dir", downloadDir);
    ffProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "table/excel;" + "application/vnd.ms-excel;" + "application/msexcel;" + "application/x-msexcel;" + "application/x-ms-excel;" + "application/x-excel;" + "application/x-dos_ms_excel;" + "application/xls;" + "application/x-xls;" + "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;" + "text/csv;" + "application/rtf");

    //Setting properties for webdriver
    DesiredCapabilities capabilities = DesiredCapabilities.firefox();
    //capabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);
    capabilities.setCapability(FirefoxDriver.PROFILE, ffProfile);

    //JavaScriptError.addExtension(ffProfile);
    driver = new FirefoxDriver(capabilities);
    driver.manage().window().setSize(new Dimension(1980, 1080));
    driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);

    //Initialize WAIT
    wait = new FluentWait<WebDriver>(driver).withTimeout(30, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS);
  }

  @AfterClass
  public static void tearDownClass() {
    log.info("Master tearDown");

    /*
    List<JavaScriptError> jsErrors = JavaScriptError.readErrors(driver);
    System.out.println("###start displaying errors");
    for(int i = 0; i < jsErrors.size(); i++) {
      System.out.println("Error Message: " + jsErrors.get(i).getErrorMessage());
      System.out.println("Line Number: " + jsErrors.get(i).getLineNumber());
      System.out.println("Source Name: " + jsErrors.get(i).getSourceName());
    }
    System.out.println("###start displaying errors");*/

    driver.close();
    driver.quit();
  }

  /**
   * TODO
   * @return
   */
  public static WebDriver getDriver() {
    return driver;
  }

  /**
   * TODO
   * @return
   */
  public static String getBaseUrl() {
    return baseUrl;
  }

  /**
   *
   * @return
   */
  public static Wait<WebDriver> getWait() {
    return wait;
  }

  /**
   *
   * @return
   */
  public static String getDownloadDir() {
    return downloadDir;
  }
}
