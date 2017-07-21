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
package com.pentaho.marketplace;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNull;
import static org.testng.AssertJUnit.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.BAServerService;
import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.gui.web.puc.LoginPage;
import com.pentaho.gui.web.puc.MarketPlace;
import com.pentaho.selenium.BaseTest;

public class InstallUninstall extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( InstallUninstall.class );
  // The BA Server URL
  protected static String pentahoBaServerUrl = "http://localhost:8080/pentaho/";
  // The BA Server hostname
  protected static String pentahoBaServerHostname = "localhost";
  // The BA Server port
  protected static String pentahoBaServerPort = "8080";
  // The BA Server service name (Windows only)
  protected static String pentahoBaServerServiceName = "pentahobaserver";

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Asserting Install plugin works as expected
   * Description:
   *    This test will validate behavior of installing a plugin
   *
   * Steps:
   *    1. Check that CTE plugin is uninstalled and install it
   *    2. Restart server
   *    3. Assert plugin is installed
   */
  @Test
  public void tc1_MarketPlacePage_InstallPlugin() {
    this.log.info( "tc1_MarketPlacePage_InstallPlugin" );

    /*
     *  Step 1
     */
    MarketPlace market = new MarketPlace( driver );
    market.GoToMarketPlace();
    market.CheckInstallPlugin( "Community Text Editor", "" );

    /*
     *  Step 2
     */
    BAServerService sc = new BAServerService( pentahoBaServerUrl, pentahoBaServerHostname, pentahoBaServerPort, pentahoBaServerServiceName );
    sc.Restart();
    LoginPage login = new LoginPage( driver );
    login.Login( pentahoBaServerUsername, pentahoBaServerPassword );

    /*
     *  Step 3
     */
    market.GoToMarketPlace();
    assertTrue( market.CheckIfPluginInstalled( "Community Text Editor" ) );
    this.elemHelper.Get( driver, PageUrl.CTE_TEMPLATE );
    WebElement pathSpan = this.elemHelper.FindElement( driver, By.cssSelector( "span#infoArea" ) );
    assertNotNull( pathSpan );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Asserting Uninstall plugin works as expected
   * Description:
   *    This test will validate behavior of uninstalling a plugin
   *
   * Steps:
   *    1. Check that CTE plugin is installed and uninstall it
   *    2. Restart server
   *    3. Assert plugin is uninstalled
   */
  @Test
  public void tc2_MarketPlacePage_UnnstallPlugin() {
    this.log.info( "tc2_MarketPlacePage_UninstallPlugin" );

    /*
     *  Step 1
     */
    MarketPlace market = new MarketPlace( driver );
    market.GoToMarketPlace();
    market.CheckUninstallPlugin( "Community Text Editor", "" );

    /*
     *  Step 2
     */
    BAServerService sc = new BAServerService( pentahoBaServerUrl, pentahoBaServerHostname, pentahoBaServerPort, pentahoBaServerServiceName );
    sc.Restart();
    LoginPage login = new LoginPage( driver );
    login.Login( pentahoBaServerUsername, pentahoBaServerPassword );

    /*
     *  Step 3
     */
    market.GoToMarketPlace();
    assertFalse( market.CheckIfPluginInstalled( "Community Text Editor" ) );
    this.elemHelper.Get( driver, PageUrl.CTE_TEMPLATE );
    WebElement pathSpan = this.elemHelper.FindElement( driver, By.cssSelector( "span#infoArea" ), 5 );
    assertNull( pathSpan );
  }
}
