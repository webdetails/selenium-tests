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

import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.BaseTest;
import com.pentaho.gui.web.puc.MarketPlace;

public class PluginsList extends BaseTest {
  private final Logger log = LogManager.getLogger( PluginsList.class );

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
  public void tc1_MarketPlacePage_PluginList() {
    this.log.info( "tc1_MarketPlacePage_PluginList" );

    /*
     *  Step 1
     */
    MarketPlace market = new MarketPlace( driver );
    market.GoToMarketPlace();
    assertTrue( market.PluginExists( "Pentaho Marketplace", "Pentaho" ) );
    assertTrue( market.PluginExists( "Community Dashboards Framework", "Webdetails" ) );
    assertTrue( market.PluginExists( "Community Data Access", "Webdetails" ) );
    assertTrue( market.PluginExists( "Community Dashboard Editor", "Webdetails" ) );
    assertTrue( market.PluginExists( "Community Graphics Generator", "Webdetails" ) );
    assertTrue( market.PluginExists( "Sparkl - Pentaho Application Builder", "Webdetails" ) );
    assertTrue( market.PluginExists( "Pentaho Repository Synchronizer", "Webdetails" ) );
    assertTrue( market.PluginExists( "Community Startup Tabs", "Webdetails" ) );
    assertTrue( market.PluginExists( "Community Text Editor", "Webdetails" ) );
    assertTrue( market.PluginExists( "Community Data Validation", "Webdetails" ) );
    assertTrue( market.PluginExists( "Community Distributed Cache", "Webdetails" ) );
    assertTrue( market.PluginExists( "Community File Repository", "Webdetails" ) );
    assertTrue( market.PluginExists( "Startup Rule Engine", "Webdetails" ) );
    assertTrue( market.PluginExists( "D3 Component Library", "Webdetails" ) );
    assertTrue( market.PluginExists( "Saiku Analytics", "Meteorite BI" ) );
    assertTrue( market.PluginExists( "Saiku Chart Plus", "IT4biz" ) );
    assertTrue( market.PluginExists( "Pentaho CE Audit", "IT4biz" ) );
    assertTrue( market.PluginExists( "Pentaho Performance Monitoring", "IT4biz" ) );
    assertTrue( market.PluginExists( "IvyBC - Ivy Bootstrap Components (Free)", "Ivy Information Systems Ltd" ) );
    assertTrue( market.PluginExists( "IvyDD - Ivy Dashboard Designer", "Ivy Information Systems Ltd" ) );
    assertTrue( market.PluginExists( "IvySE - Ivy Schema Editor", "Ivy Information Systems" ) );
    assertTrue( market.PluginExists( "IvyUD - Ivy User Details", "Ivy Information Systems Ltd" ) );
    assertTrue( market.PluginExists( "IvyDC - Ivy Dashboard Components", "Ivy Information Systems" ) );
    assertTrue( market.PluginExists( "IvyGS - Ivy Git Sync", "Ivy Information Systems Ltd" ) );
    assertTrue( market.PluginExists( "OpenI-Pentaho Plugin", "OpenI" ) );
    assertTrue( market.PluginExists( "Pivot4J Analytics", "Pivot4J Project" ) );
    assertTrue( market.PluginExists( "Change Password", "Kleyson Rios" ) );
    assertTrue( market.PluginExists( "BTable", "Biz Tech" ) );
    assertTrue( market.PluginExists( "Alfresco Audit Analysis and Reporting", "Francesco Corti" ) );
    assertTrue( market.PluginExists( "Pentaho Log Manager", "Inquidia Consulting" ) );
    assertTrue( market.PluginExists( "Environment Display", "Webdetails" ) );
    assertTrue( market.PluginExists( "Pentaho Analytics Shell", "Roland Bouman" ) );
    assertTrue( market.PluginExists( "WAQR", "Pentaho Legacy - Plugins developed by Pentaho but no longer supported" ) );
    assertTrue( market.PluginExists( "Bissol Table Data Editor", "Diethard Steiner (Bissol Consulting Ltd)" ) );
    assertTrue( market.PluginExists( "Pentaho Database Inspection Service", "Roland Bouman" ) );
    assertTrue( market.PluginExists( "Pentaho Analysis Editor", "Roland Bouman" ) );
    assertTrue( market.PluginExists( "Mondrian Translator", "Nicolas Haquet / Linalis" ) );
    assertTrue( market.PluginExists( "Catalan Language Pack Installer", "Blau Advisors Strategic Partners,S.L." ) );
    assertTrue( market.PluginExists( "Dutch Language Pack Installer", "C. van Kemenade" ) );
    assertTrue( market.PluginExists( "French Language Pack Installer", "Sylvain Decloix" ) );
    assertTrue( market.PluginExists( "German Language Pack Installer", "Thomas Starl" ) );
    assertTrue( market.PluginExists( "Hindi Language Pack Installer", "InfoAxon Technologies" ) );
    assertTrue( market.PluginExists( "Italian Language Pack Installer", "Serasoft S.r.l." ) );
    assertTrue( market.PluginExists( "Japanese Language Pack Installer", "Akito Tanaka" ) );
    assertTrue( market.PluginExists( "Korean Language Pack Installer", "Xavier Cho" ) );
    assertTrue( market.PluginExists( "Portuguese (Brazilian variant) Language Pack Installer", "Oncase, Open Consulting, IT4biz" ) );
    assertTrue( market.PluginExists( "Portuguese (European variant) Language Pack Installer", "Xpand-IT" ) );
    assertTrue( market.PluginExists( "Spanish Language Pack Installer", "Domingo Lavin" ) );
  }
}
