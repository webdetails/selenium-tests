/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2002 - 2026 by Pentaho Canada Inc. : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2030-06-15
 ******************************************************************************/

package com.pentaho.marketplace;

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
