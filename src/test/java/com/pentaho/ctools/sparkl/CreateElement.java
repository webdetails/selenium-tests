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
import com.pentaho.selenium.BaseTest;

public class CreateElement extends BaseTest {

  private final Logger log = LogManager.getLogger( CreateElement.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    General Layout of Sparkl
   * Description:
   *    This test will validate the general layout of Sparkl is as expected
   *
   * Steps:
   *    1. Check layout
   */
  @Test
  public void tc1_MarketPlacePage_GeneralLayout() {
    this.log.info( "tc1_Sparkl_GeneralLayout" );

    /*
     *  Step 1
     */
    Sparkl sparkl = new Sparkl( driver );
    sparkl.ToolsEntryWorks( "Sparkl" );

  }
}
