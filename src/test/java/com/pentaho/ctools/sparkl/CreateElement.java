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
package com.pentaho.ctools.sparkl;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.pentaho.gui.web.puc.Sparkl;
import com.pentaho.gui.web.puc.sparkl.PluginCard;
import com.pentaho.gui.web.puc.sparkl.PluginElement;
import com.pentaho.selenium.BaseTest;

public class CreateElement extends BaseTest {

  private final Logger log = LogManager.getLogger( CreateElement.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Create Element
   * Description:
   *    This test will validate the ability to create elements (dashboards and
   *    endpoints) in a Sparkl plugin
   *
   * Steps:
   *    1. Create dashboard for only admin and assert it is present/editable/viewable
   *    2. Create dashboard for all users and assert it is present/editable/viewable
   *    3. Create an endpoint for only admin and assert it is present/runable
   *    4. Create an endpoint for all users and assert it is present/runable
   */
  @Test
  public void tc1_Sparkl_CreateElement() {
    this.log.info( "tc1_Sparkl_CreateElement" );

    /*
     *  Step 1
     */
    Sparkl sparkl = new Sparkl( driver );
    sparkl.OpenSparkl();
    PluginCard plugin = new PluginCard( "ATest", "aTest", "testDescription" );
    sparkl.GoToEditPage( plugin );
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

    //Assert dashboard is deletable
    String deletePlugininfo = sparkl.DeleteCancelElement( dashAdmin );
    assertEquals( "You are about to delete dashadmin. Please, press OK to continue...", deletePlugininfo );

    /*
     *  Step 2
     */
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

    //Assert dashboard is deletable
    String deleteDashAll = sparkl.DeleteCancelElement( dashAll );
    assertEquals( "You are about to delete dashall. Please, press OK to continue...", deleteDashAll );

    /*
     *  Step 3
     */
    //Create endpoint and assert it is present
    PluginElement endAdmin = new PluginElement( "endadmin", "endpoint", "ktr", true );
    sparkl.CreateElement( endAdmin );
    assertTrue( sparkl.IsElementPresent( endAdmin ) );

    //Assert endpoint is runnable
    String runEndAdmin = sparkl.RunEndpoint( endAdmin );
    assertEquals( runEndAdmin, "endadmin" );

    //Assert endpoint is deletable
    String deleteEndAdmin = sparkl.DeleteCancelElement( endAdmin );
    assertEquals( "You are about to delete endadmin. Please, press OK to continue...", deleteEndAdmin );

    /*
     *  Step 4
     */
    //Create endpoint and assert it is present
    PluginElement endAll = new PluginElement( "endall", "endpoint", "kjb", false );
    sparkl.CreateElement( endAll );
    assertTrue( sparkl.IsElementPresent( endAll ) );

    //Assert endpoint is runnable
    String runEndAll = sparkl.RunEndpoint( endAll );
    assertEquals( runEndAll, "endall" );

    //Assert endpoint is deletable
    String deleteEndAll = sparkl.DeleteCancelElement( endAll );
    assertEquals( "You are about to delete endall. Please, press OK to continue...", deleteEndAll );

  }
}
