/*!
  * This program is free software;you can redistribute it and/or modify it under the
  * terms of the GNU Lesser General Public License,version2.1as published by the Free Software Foundation.
  *
  * You should have received a copy of the GNU Lesser General Public License along with this
  * program;if not,you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
  * or from the Free Software Foundation,Inc., 51 Franklin Street,Fifth Floor,Boston,MA 02110-1301 USA.
  *
  * This program is distributed in the hope that it will be useful,but WITHOUT ANY WARRANTY;
  * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
  * See the GNU Lesser General Public License for more details.
  *
  * Copyright(c)2002-2014 Pentaho Corporation..All rights reserved.
  */

package org.pentaho.ctools.suite;

import net.jsourcerer.webdriver.jserrorcollector.JavaScriptError;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.pentaho.ctools.cde.MapComponentReference;
import org.pentaho.ctools.cde.widgets.SelectCdaFileAsDatasource;
import org.pentaho.ctools.cde.widgets.AddParamTableComponent;
import org.pentaho.ctools.cde.widgets.CreateWidget;
import org.pentaho.ctools.cde.widgets.SimpleExtensionPoints;
import org.pentaho.ctools.cdf.AutoCompleteBoxComponent;
import org.pentaho.ctools.cdf.DataInputComponent;
import org.pentaho.ctools.cdf.MetaLayerHomeDashboard;
import org.pentaho.ctools.cdf.TableComponent;
import org.pentaho.ctools.cdf.VisualizationAPIComponent;
import org.pentaho.ctools.main.LoginPentaho;
import org.pentaho.ctools.main.LogoutPentaho;
import org.pentaho.ctools.security.AccessSystemResources;

import javax.sound.midi.SysexMessage;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    LoginPentaho.class,
    //CDF
    /*AutoCompleteBoxComponent.class,
    DataInputComponent.class,
    TableComponent.class,
    MetaLayerHomeDashboard.class,*/
    VisualizationAPIComponent.class,
    //CDE
    /*
    MapComponentReference.class,
    //CDE - Widgets
    CreateWidget.class,
    AddParamTableComponent.class,
    SelectCdaFileAsDatasource.class,
    SimpleExtensionPoints.class,
    //Security
    AccessSystemResources.class,*/
    LogoutPentaho.class
})

public class CToolsTestSuite {
  /// Instance of the driver (browser emulator)
  private static WebDriver driver;
  WebDriverWait
  // Instance to be used on wait commands
  private static Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private static String baseUrl;


  @BeforeClass
  public static void setUpClass() throws IOException {
    System.out.println("Master setup");

    System.setProperty("webdriver.log.file", "/dev/stdout");
    System.setProperty("webdriver.firefox.logfile", "/dev/stdout");

    //Setting log preferences
    LoggingPreferences logs = new LoggingPreferences();
    logs.enable(LogType.BROWSER, Level.ALL);
    logs.enable(LogType.SERVER, Level.ALL);
    logs.enable(LogType.DRIVER, Level.ALL);
    logs.enable(LogType.PROFILER, Level.ALL);
    logs.enable(LogType.CLIENT, Level.ALL);
    logs.enable(LogType.PERFORMANCE, Level.ALL);

    DesiredCapabilities capabilities = DesiredCapabilities.firefox();
    capabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);

    //Inicialize DRIVER
    FirefoxProfile ffProfile = new FirefoxProfile();
    ffProfile.setPreference("intl.accept_languages", "en-us");


    capabilities.setCapability(FirefoxDriver.PROFILE, ffProfile);

    JavaScriptError.addExtension(ffProfile);
    driver = new FirefoxDriver(capabilities);
    driver.manage().window().maximize();
    driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    //Inicialize WAIT
    wait = new FluentWait<WebDriver>(driver)
        .withTimeout(30, TimeUnit.SECONDS)
        .pollingEvery(1, TimeUnit.SECONDS)
        .ignoring(org.openqa.selenium.NoSuchElementException.class);

    //Inicialize BASEURL
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