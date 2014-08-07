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

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.main.LoginPentaho;
import org.pentaho.ctools.main.LogoutPentaho;
import org.pentaho.ctools.cdf.AutoCompleteBoxComponent;
import org.pentaho.ctools.cdf.DataInputComponent;
import org.pentaho.ctools.cdf.TableComponent;
import org.pentaho.ctools.cdf.MetaLayerHomeDashboard;
import org.pentaho.ctools.cde.widgets.CreateWidget;
import org.pentaho.ctools.cde.widgets.AddParamTableComponent;
import org.pentaho.ctools.security.AccessSystemResources;

import java.util.concurrent.TimeUnit;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    LoginPentaho.class,
    //CDF
    AutoCompleteBoxComponent.class,
    DataInputComponent.class,
    TableComponent.class,
    MetaLayerHomeDashboard.class,
    //CDE - Widgets
    CreateWidget.class,
    AddParamTableComponent.class,
    //Security
    AccessSystemResources.class,
    LogoutPentaho.class
})

public class CToolsTestSuite {
  // TODO
  private static WebDriver driver;
  // TODO
  private static Wait<WebDriver> wait;
  // TODO
  private static String baseUrl;


  @BeforeClass
  public static void setUpClass() {
    System.out.println("Master setup");
    //Inicialize DRIVER
    driver = new FirefoxDriver();
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