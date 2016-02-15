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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.pentaho.gui.web.puc.Sparkl;
import com.pentaho.gui.web.puc.sparkl.PluginInfo;
import com.pentaho.selenium.BaseTest;

public class CreatePlugin extends BaseTest {

  private final Logger log = LogManager.getLogger( CreatePlugin.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Able to edit existing plugin
   * Description:
   *    This test will validate the ability to edit existing plugins
   *
   * Steps:
   *    1. Open Sparkl in Edit mode and assert layout
   *    2. Assert saving of plugin information
   *    3. Assert layout of elements view
   *    4. Open existing dashboard and endpoint
   *    5. Edit existing dashboard and endpoint
   *    6. Create new dashboard and endpoint
   *    7. Delete new dashboard and endpoint
   */
  @Test
  public void tc1_Sparkl_Edit() {
    this.log.info( "tc1_Sparkl_Edit" );

    /*
     *  Step 1
     */
    Sparkl sparkl = new Sparkl( driver );

    sparkl.GoToEditPage( "sparkl" );

    /*
     *  Step 2
     */
    String testString = "testtesttesttest";
    PluginInfo currentInfo = sparkl.GetPluginInfo();
    PluginInfo newPlugin = new PluginInfo( testString, testString, testString, testString, testString, testString, testString, testString );
    sparkl.CheckEditAbout();

    /*
     *  Step 3
     */
    sparkl.CheckEditElement();

    /*
     *  Step 4
     */
  }
}
