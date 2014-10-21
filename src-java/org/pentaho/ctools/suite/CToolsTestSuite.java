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

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.cdf.AutoCompleteBoxComponent;
import org.pentaho.ctools.cdf.DataInputComponent;
import org.pentaho.ctools.cdf.MetaLayerHomeDashboard;
import org.pentaho.ctools.cdf.SchedulePrptComponent;
import org.pentaho.ctools.cdf.TableComponent;
import org.pentaho.ctools.cdf.VisualizationAPIComponent;
import org.pentaho.ctools.main.LoginPentaho;
import org.pentaho.ctools.main.LogoutPentaho;
import org.pentaho.ctools.security.AccessSystemResources;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	//##### Execution Order #####
	LoginPentaho.class,
  //CDF
  AutoCompleteBoxComponent.class,
  DataInputComponent.class,
  TableComponent.class,
  MetaLayerHomeDashboard.class,
  VisualizationAPIComponent.class,
  SchedulePrptComponent.class,
  //CDE
  //MapComponentReference.class,
  //CDE - Widgets
  //CreateWidget.class,
  //AddParamTableComponent.class,
  //SelectCdaFileAsDatasource.class,
  //SimpleExtensionPoints.class,
  //Security
  AccessSystemResources.class,
	LogoutPentaho.class
})

public class CToolsTestSuite {
  /// Instance of the driver (browser emulator)
  private static WebDriver driver;
  // Instance to be used on wait commands
  private static Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private static String baseUrl;


  @BeforeClass
  public static void setUpClass() throws IOException {
    System.out.println("Master setup");

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


    //Setting properties for webdriver
    DesiredCapabilities capabilities = DesiredCapabilities.firefox();
    //capabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);
    capabilities.setCapability(FirefoxDriver.PROFILE, ffProfile);


    //JavaScriptError.addExtension(ffProfile);
    driver = new FirefoxDriver(capabilities);
    driver.manage().window().maximize();
    driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);

    
    //Initialize WAIT
    wait = new FluentWait<WebDriver>(driver)
        .withTimeout(30, TimeUnit.SECONDS)
        .pollingEvery(5, TimeUnit.SECONDS);

    //Initialize BASEURL
    baseUrl = "http://localhost:8080/pentaho/";
  }

  @AfterClass
  public static void tearDownClass() {
    System.out.println("Master tearDown");

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
  public static String getBaseUrl(){
    return baseUrl;
  }

  /**
   *
   * @return
   */
  public static Wait<WebDriver> getWait() {
    return wait;
  }
}