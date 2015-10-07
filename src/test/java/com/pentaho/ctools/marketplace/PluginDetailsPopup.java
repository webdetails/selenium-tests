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

import com.pentaho.gui.web.puc.MarketPlace;
import com.pentaho.selenium.BaseTest;

public class PluginDetailsPopup extends BaseTest {
  private final Logger log = LogManager.getLogger( PluginDetailsPopup.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Asserting Plugin Details Popup are correctly shown
   * Description:
   *    This test will validate the CTools plugin details and other 5 random plugins
   *
   * Steps:
   *    1. Check presence of plugins
   */
  @Test
  public void tc1_MarketPlacePage_PluginPopup() {
    this.log.info( "tc1_MarketPlacePage_PluginPopup" );

    /*
     *  Step 1
     */
    MarketPlace market = new MarketPlace( driver );
    market.GoToMarketPlace();
    market.CheckRandomPluginsDetails();
  }
}
