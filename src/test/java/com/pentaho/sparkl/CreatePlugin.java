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
package com.pentaho.sparkl;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.BAServerService;
import com.pentaho.gui.web.puc.LoginPage;
import com.pentaho.gui.web.puc.Sparkl;
import com.pentaho.gui.web.puc.sparkl.PluginCard;
import com.pentaho.gui.web.puc.sparkl.PluginElement;
import com.pentaho.gui.web.puc.sparkl.PluginInfo;
import com.pentaho.selenium.BaseTest;

public class CreatePlugin extends BaseTest {

  private final Logger log = LogManager.getLogger( CreatePlugin.class );
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
   *    Able to edit existing plugin
   * Description:
   *    This test will validate the ability to edit existing plugins
   *
   * Steps:
   *    1. Open Sparkl and Create plugin
   *    2. Restart server
   *    3. Save plugin info
   *    4. Create Elements
   *    5. Delete Elements
   */
  @Test
  public void tc1_Sparkl_CreatePlugin() {
    this.log.info( "tc1_Sparkl_CreatePlugin" );

    /*
     *  Step 1
     */
    Sparkl sparkl = new Sparkl( driver );
    sparkl.OpenSparkl();
    String confirm = sparkl.CreatePlugin( "testPlugin" );
    assertEquals( "The plugin testPlugin was successfully created!Please restart the server for full functionality.", confirm );

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
    sparkl.OpenSparkl();
    PluginCard plugin = new PluginCard( "TestPlugin", "testPlugin", "" );
    sparkl.GoToEditPage( plugin );
    PluginInfo newInfo = new PluginInfo( "TestPlugin", "Date", "1", "this is the description", "Author", "author@company.com", "company", "company.com" );

    //Enter new info and assert it is saved correctly
    newInfo = sparkl.EnterPluginInfo( newInfo );
    sparkl.SavePluginInfo();
    PluginInfo shownInfo = sparkl.GetPluginInfo();
    assertEquals( shownInfo, newInfo );

    /*
     *  Step 4
     */
    sparkl.GoToElements();
    //Create dashboard and assert it is present
    PluginElement dashAdmin = new PluginElement( "dashadmin", "dashboard", "clean", true );
    sparkl.CreateElement( dashAdmin );
    assertTrue( sparkl.IsElementPresent( dashAdmin ) );

    //Assert dashboard is runnable
    String dashboard = sparkl.ViewDashboard( dashAdmin );
    assertEquals( dashboard, "dashadmin" );

    //Assert dashboard is editable
    String editDashboard = sparkl.EditDashboard( dashAdmin );
    assertEquals( editDashboard, "dashadmin" );

    //Create dashboard and assert it is present
    PluginElement dashAll = new PluginElement( "dashall", "dashboard", "sparkl", false );
    sparkl.CreateElement( dashAll );
    assertTrue( sparkl.IsElementPresent( dashAll ) );

    //Assert dashboard is runnable
    String dashAllView = sparkl.ViewDashboard( dashAll );
    assertEquals( dashAllView, "dashall" );

    //Assert dashboard is editable
    String editDashAll = sparkl.EditDashboard( dashAll );
    assertEquals( editDashAll, "dashall" );

    //Create endpoint and assert it is present
    PluginElement endAdmin = new PluginElement( "endadmin", "endpoint", "ktr", true );
    sparkl.CreateElement( endAdmin );
    assertTrue( sparkl.IsElementPresent( endAdmin ) );

    //Assert endpoint is runnable
    String runEndAdmin = sparkl.RunEndpoint( endAdmin );
    assertEquals( runEndAdmin, "endadmin" );

    //Create endpoint and assert it is present
    PluginElement endAll = new PluginElement( "endall", "endpoint", "kjb", false );
    sparkl.CreateElement( endAll );
    assertTrue( sparkl.IsElementPresent( endAll ) );

    //Assert endpoint is runnable
    String runEndAll = sparkl.RunEndpoint( endAll );
    assertEquals( runEndAll, "endall" );

    /*
     *  Step 5
     */
    //Delete admin dashboard and assert no longer present
    sparkl.DeleteElement( dashAdmin );
    assertFalse( sparkl.IsElementPresent( dashAdmin ) );

    //Delete non-admin dashboard and assert no longer present
    sparkl.DeleteElement( dashAll );
    assertFalse( sparkl.IsElementPresent( dashAll ) );

    //Delete admin endpoint and assert no longer present
    sparkl.DeleteElement( endAdmin );
    assertFalse( sparkl.IsElementPresent( endAdmin ) );

    //Delete non-admin endpoint and assert no longer present
    sparkl.DeleteElement( endAll );
    assertFalse( sparkl.IsElementPresent( endAll ) );

  }
}
