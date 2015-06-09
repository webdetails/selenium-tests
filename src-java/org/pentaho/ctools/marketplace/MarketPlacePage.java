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
package org.pentaho.ctools.marketplace;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ScreenshotTestRule;
import org.pentaho.gui.web.puc.MarketPlace;

@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class MarketPlacePage {

  // Instance of the driver (browser emulator)
  private WebDriver         driver;
  private static Logger     LOG                = LogManager.getLogger( MarketPlacePage.class );

  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( this.driver );

  @Before
  public void setUp() throws Exception {
    LOG.info( "setUp##" + MarketPlacePage.class.getSimpleName() );
    this.driver = CToolsTestSuite.getDriver();
  }

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
  @Test( timeout = 180000 )
  public void tc1_MarketPlacePage_GeneralLayout() {

    /*
     *  Step 1
     */
    MarketPlace market = new MarketPlace( this.driver );
    market.GoToMarketPlace();
    market.CheckMarketPlaceLayout();

  }

  @After
  public void tearDown() {
    LOG.info( "tearDown##" + MarketPlacePage.class.getSimpleName() );
  }
}
