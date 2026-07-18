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

public class MarketPlacePage extends BaseTest {

  private final Logger log = LogManager.getLogger( MarketPlacePage.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    General Layout of MarketPlace
   * Description:
   *    This test will validate the general layout of the Marketplace is as expected
   *
   * Steps:
   *    1. Check layout
   */
  @Test
  public void tc1_MarketPlacePage_GeneralLayout() {
    this.log.info( "tc1_MarketPlacePage_GeneralLayout" );

    /*
     *  Step 1
     */
    MarketPlace market = new MarketPlace( driver );
    market.GoToMarketPlace();
    market.CheckMarketPlaceLayout();
  }
}
