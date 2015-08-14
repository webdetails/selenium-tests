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
package com.pentaho.ctools.marketplace;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.BaseTest;
import com.pentaho.gui.web.puc.MarketPlace;

public class InstalledTab extends BaseTest {
  private final Logger log = LogManager.getLogger( InstalledTab.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Asserting all Plugins from PluginList are available
   * Description:
   *    This test will validate the presence of all plugins on the Marketplace plugin list
   *
   * Steps:
   *    1. Check presence of plugins
   */
  @Test
  public void tc1_MarketPlacePage_InstalledTab() {
    this.log.info( "tc1_MarketPlacePage_InstalledTab" );

    /*
     *  Step 1
     */
    MarketPlace market = new MarketPlace( this.driver );
    market.GoToMarketPlace();
    market.CheckInstalledTab();
    market.CheckPluginListLayout();
  }
}
