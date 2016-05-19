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
import static org.testng.AssertJUnit.assertFalse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.pentaho.gui.web.puc.Sparkl;
import com.pentaho.gui.web.puc.sparkl.PluginCard;
import com.pentaho.selenium.BaseTest;

public class DeletePlugin extends BaseTest {

  private final Logger log = LogManager.getLogger( DeletePlugin.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Able to edit existing plugin
   * Description:
   *    This test will validate the ability to edit existing plugins
   *
   * Steps:
   *    1. Open Sparkl and assert desired plugin exists
   *    2. Delete Plugin and assert messages sent to user
   *    3. Assert Plugin is no longer present
   */
  @Test
  public void tc1_Sparkl_DeletePlugin() {
    this.log.info( "tc1_Sparkl_DeletePlugin" );

    /*
     *  Step 1
     */
    Sparkl sparkl = new Sparkl( driver );
    sparkl.OpenSparkl();
    PluginCard plugin = new PluginCard( "TestPlugin", "testPlugin", "this is the description" );
    if ( !sparkl.PluginCardExists( plugin ) ) {
      plugin.setName( "ATest" );
    }

    /*
     *  Step 2
     */
    String confirm = sparkl.DeletePlugin( plugin );
    assertEquals( "Are you sure you want to delete the plugin ?", confirm );

    /*
     *  Step 3
     */
    //Failing test due to http://jira.pentaho.com/browse/SPARKL-141. Plugin needs to be removed by hand at the end of testing.
    assertFalse( sparkl.PluginExists( plugin ) );
  }
}
