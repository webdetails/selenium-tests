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

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.BaseTest;
import com.pentaho.gui.web.puc.MarketPlace;

public class MarketplaceFilters extends BaseTest {
  private final Logger log = LogManager.getLogger( MarketplaceFilters.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Asserting Type filter works as expected
   *
   * Description:
   *    This test will validate the functionalities of the Type filter
   *
   * Steps:
   *    1. Check "Apps" and assert plugins shown
   *    2. Unchecked "Analysis" and "Data" and assert plugins shown
   *    3. Unchecked "Apps", check "Visualization" and "Language Packs" and assert plugins shown
   */
  @Test
  public void tc1_MarketPlacePage_TypeFilter() {
    this.log.info( "tc1_MarketPlacePage_TypeFilter" );
    MarketPlace market = new MarketPlace( this.driver );
    market.GoToMarketPlace();

    /*
     *  Step 1
     */
    List<Number> type = new ArrayList<Number>();
    type.add( 1 );
    List<Number> stage = new ArrayList<Number>();
    List<String> expectedList = new ArrayList<String>();
    expectedList.add( "Pentaho Marketplace" );
    expectedList.add( "Community Dashboards Framework" );
    expectedList.add( "Community Data Access" );
    expectedList.add( "Community Dashboard Editor" );
    expectedList.add( "Community Graphics Generator" );
    expectedList.add( "Sparkl - Pentaho Application Builder" );
    expectedList.add( "Pentaho Repository Synchronizer" );
    expectedList.add( "Community Startup Tabs" );
    expectedList.add( "Community Text Editor" );
    expectedList.add( "Community Data Validation" );
    expectedList.add( "Community Distributed Cache" );
    expectedList.add( "Community File Repository" );
    expectedList.add( "Startup Rule Engine" );
    expectedList.add( "Saiku Analytics" );
    expectedList.add( "Saiku Chart Plus" );
    expectedList.add( "Pentaho CE Audit" );
    expectedList.add( "Pentaho Performance Monitoring" );
    expectedList.add( "IvyBC - Ivy Bootstrap Components (Free)" );
    expectedList.add( "IvyDD - Ivy Dashboard Designer" );
    expectedList.add( "IvySE - Ivy Schema Editor" );
    expectedList.add( "IvyUD - Ivy User Details" );
    expectedList.add( "IvyDC - Ivy Dashboard Components" );
    expectedList.add( "IvyGS - Ivy Git Sync" );
    expectedList.add( "OpenI-Pentaho Plugin" );
    expectedList.add( "Pivot4J Analytics" );
    expectedList.add( "Change Password" );
    expectedList.add( "BTable" );
    expectedList.add( "Alfresco Audit Analysis and Reporting" );
    expectedList.add( "Pentaho Log Manager" );
    expectedList.add( "Environment Display" );
    expectedList.add( "Pentaho Analytics Shell" );
    expectedList.add( "WAQR" );
    expectedList.add( "Bissol Table Data Editor" );
    expectedList.add( "Pentaho Database Inspection Service" );
    expectedList.add( "Pentaho Analysis Editor" );
    List<String> resultList = new ArrayList<String>();
    resultList = market.CheckFiltersApplied( type, stage, "" );
    assertEquals( expectedList, resultList );

    /*
     *  Step 2
     */
    type.add( 3 );
    type.add( 5 );
    expectedList.clear();
    expectedList.add( "Pentaho Marketplace" );
    expectedList.add( "Community Dashboards Framework" );
    expectedList.add( "Community Dashboard Editor" );
    expectedList.add( "Community Graphics Generator" );
    expectedList.add( "Sparkl - Pentaho Application Builder" );
    expectedList.add( "Pentaho Repository Synchronizer" );
    expectedList.add( "Community Startup Tabs" );
    expectedList.add( "Community Text Editor" );
    expectedList.add( "Community Data Validation" );
    expectedList.add( "Community Distributed Cache" );
    expectedList.add( "Community File Repository" );
    expectedList.add( "Startup Rule Engine" );
    expectedList.add( "Pentaho CE Audit" );
    expectedList.add( "Pentaho Performance Monitoring" );
    expectedList.add( "IvyBC - Ivy Bootstrap Components (Free)" );
    expectedList.add( "IvyDD - Ivy Dashboard Designer" );
    expectedList.add( "IvyUD - Ivy User Details" );
    expectedList.add( "IvyDC - Ivy Dashboard Components" );
    expectedList.add( "Change Password" );
    expectedList.add( "BTable" );
    expectedList.add( "Pentaho Log Manager" );
    expectedList.add( "Environment Display" );
    expectedList.add( "Pentaho Analytics Shell" );
    expectedList.add( "Bissol Table Data Editor" );
    expectedList.add( "Pentaho Database Inspection Service" );
    expectedList.add( "Pentaho Analysis Editor" );
    resultList.clear();
    resultList = market.CheckFiltersApplied( type, stage, "" );
    assertEquals( expectedList, resultList );

    /*
     *  Step 3
     */
    type.clear();
    type.add( 8 );
    type.add( 11 );
    expectedList.clear();
    expectedList.add( "D3 Component Library" );
    expectedList.add( "Catalan Language Pack Installer" );
    expectedList.add( "Dutch Language Pack Installer" );
    expectedList.add( "French Language Pack Installer" );
    expectedList.add( "German Language Pack Installer" );
    expectedList.add( "Hindi Language Pack Installer" );
    expectedList.add( "Italian Language Pack Installer" );
    expectedList.add( "Japanese Language Pack Installer" );
    expectedList.add( "Korean Language Pack Installer" );
    expectedList.add( "Portuguese (Brazilian variant) Language Pack Installer" );
    expectedList.add( "Portuguese (European variant) Language Pack Installer" );
    expectedList.add( "Spanish Language Pack Installer" );
    resultList.clear();
    resultList = market.CheckFiltersApplied( type, stage, "" );
    assertEquals( expectedList, resultList );

  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Asserting DevStage filter works as expected
   *
   * Description:
   *    This test will validate the functionalities of the DevStage filter
   *
   * Steps:
   *    1. Check "Customer" and assert plugins shown
   *    2. Unchecked "Customer", Check Customer stages 1 and 3 and assert plugins shown
   *    3. Unchecked checked filters, check "Community", unchecked  stages 1 and 4 and assert plugins shown
   */
  @Test
  public void tc2_MarketPlacePage_DevStageFilter() {
    MarketPlace market = new MarketPlace( this.driver );

    /*
     *  Step 1
     */
    List<Number> type = new ArrayList<Number>();
    List<Number> stage = new ArrayList<Number>();
    stage.add( 1 );
    List<String> expectedList = new ArrayList<String>();
    expectedList.add( "Pentaho Marketplace" );
    expectedList.add( "Community Dashboards Framework" );
    expectedList.add( "Community Data Access" );
    expectedList.add( "Community Dashboard Editor" );
    expectedList.add( "Community Graphics Generator" );
    List<String> resultList = new ArrayList<String>();
    resultList = market.CheckFiltersApplied( type, stage, "" );
    assertEquals( expectedList, resultList );

    /*
     *  Step 2
     */
    stage.clear();
    stage.add( 2 );
    stage.add( 4 );
    expectedList.remove( 0 );
    resultList.clear();
    resultList = market.CheckFiltersApplied( type, stage, "" );
    assertEquals( expectedList, resultList );

    /*
     *  Step 3
     */
    stage.clear();
    stage.add( 7 );
    stage.add( 8 );
    stage.add( 11 );
    expectedList.clear();
    expectedList.add( "Sparkl - Pentaho Application Builder" );
    expectedList.add( "Pentaho Repository Synchronizer" );
    expectedList.add( "Community Startup Tabs" );
    expectedList.add( "Community Text Editor" );
    expectedList.add( "Community Data Validation" );
    expectedList.add( "Community Distributed Cache" );
    expectedList.add( "Community File Repository" );
    expectedList.add( "Startup Rule Engine" );
    expectedList.add( "D3 Component Library" );
    expectedList.add( "Saiku Chart Plus" );
    expectedList.add( "Pentaho CE Audit" );
    expectedList.add( "IvyBC - Ivy Bootstrap Components (Free)" );
    expectedList.add( "IvyDD - Ivy Dashboard Designer" );
    expectedList.add( "IvySE - Ivy Schema Editor" );
    expectedList.add( "IvyUD - Ivy User Details" );
    expectedList.add( "IvyDC - Ivy Dashboard Components" );
    expectedList.add( "IvyGS - Ivy Git Sync" );
    expectedList.add( "OpenI-Pentaho Plugin" );
    expectedList.add( "Pivot4J Analytics" );
    expectedList.add( "Change Password" );
    expectedList.add( "BTable" );
    expectedList.add( "Alfresco Audit Analysis and Reporting" );
    expectedList.add( "Pentaho Log Manager" );
    expectedList.add( "Environment Display" );
    expectedList.add( "Pentaho Analytics Shell" );
    expectedList.add( "Bissol Table Data Editor" );
    expectedList.add( "Mondrian Translator" );
    expectedList.add( "Catalan Language Pack Installer" );
    expectedList.add( "Dutch Language Pack Installer" );
    expectedList.add( "French Language Pack Installer" );
    expectedList.add( "German Language Pack Installer" );
    expectedList.add( "Hindi Language Pack Installer" );
    expectedList.add( "Italian Language Pack Installer" );
    expectedList.add( "Japanese Language Pack Installer" );
    expectedList.add( "Korean Language Pack Installer" );
    expectedList.add( "Portuguese (Brazilian variant) Language Pack Installer" );
    expectedList.add( "Portuguese (European variant) Language Pack Installer" );
    expectedList.add( "Spanish Language Pack Installer" );
    resultList.clear();
    resultList = market.CheckFiltersApplied( type, stage, "" );
    assertEquals( expectedList, resultList );

  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Asserting Searching filter works as expected
   *
   * Description:
   *    This test will validate the functionalities of the Search filter
   *
   * Steps:
   *    1. Input 'Webdetails' on search field and assert shown plugins
   *    2. Input 'Community' on search field and assert shown plugins
   *    3. Input 'D3' on search field and assert shown plugins
   *    4. Input 'failure' on search field and assert shown plugins
   */
  @Test
  public void tc3_MarketPlacePage_SearchFilter() {
    MarketPlace market = new MarketPlace( this.driver );
    /*
     *  Step 1
     */
    List<Number> type = new ArrayList<Number>();
    List<Number> stage = new ArrayList<Number>();
    List<String> expectedList = new ArrayList<String>();
    expectedList.add( "Community Dashboards Framework" );
    expectedList.add( "Community Data Access" );
    expectedList.add( "Community Dashboard Editor" );
    expectedList.add( "Community Graphics Generator" );
    expectedList.add( "Sparkl - Pentaho Application Builder" );
    expectedList.add( "Pentaho Repository Synchronizer" );
    expectedList.add( "Community Startup Tabs" );
    expectedList.add( "Community Text Editor" );
    expectedList.add( "Community Data Validation" );
    expectedList.add( "Community Distributed Cache" );
    expectedList.add( "Community File Repository" );
    expectedList.add( "Startup Rule Engine" );
    expectedList.add( "D3 Component Library" );
    expectedList.add( "Environment Display" );
    expectedList.add( "Catalan Language Pack Installer" );
    expectedList.add( "Dutch Language Pack Installer" );
    expectedList.add( "French Language Pack Installer" );
    expectedList.add( "German Language Pack Installer" );
    expectedList.add( "Hindi Language Pack Installer" );
    expectedList.add( "Italian Language Pack Installer" );
    expectedList.add( "Japanese Language Pack Installer" );
    expectedList.add( "Korean Language Pack Installer" );
    expectedList.add( "Portuguese (Brazilian variant) Language Pack Installer" );
    expectedList.add( "Portuguese (European variant) Language Pack Installer" );
    expectedList.add( "Spanish Language Pack Installer" );
    List<String> resultList = new ArrayList<String>();
    resultList = market.CheckFiltersApplied( type, stage, "Webdetails" );
    assertEquals( expectedList, resultList );

    /*
     *  Step 2
     */
    expectedList.clear();
    expectedList.add( "Community Dashboards Framework" );
    expectedList.add( "Community Data Access" );
    expectedList.add( "Community Dashboard Editor" );
    expectedList.add( "Community Graphics Generator" );
    expectedList.add( "Community Startup Tabs" );
    expectedList.add( "Community Text Editor" );
    expectedList.add( "Community Data Validation" );
    expectedList.add( "Community Distributed Cache" );
    expectedList.add( "Community File Repository" );
    expectedList.add( "IvyDD - Ivy Dashboard Designer" );
    expectedList.add( "BTable" );
    resultList = market.CheckFiltersApplied( type, stage, "Community" );
    assertEquals( expectedList, resultList );

    /*
     *  Step 3
     */
    expectedList.clear();
    expectedList.add( "D3 Component Library" );
    resultList = market.CheckFiltersApplied( type, stage, "D3" );
    assertEquals( expectedList, resultList );

    /*
     *  Step 4
     */
    expectedList.clear();
    resultList = market.CheckFiltersApplied( type, stage, "failure" );
    assertEquals( expectedList, resultList );
  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name:
   *    Asserting combination of filters work as expected
   *
   * Description:
   *    This test will validate the functionalities of the filters when used combined
   *
   * Steps:
   *    1. Input 'Webdetails' on search field, "Customer" on Stage Filter and "Apps" on Type Filter and assert shown plugins
   *    2. Input 'Community' on search field, check Customer stages 1 and 3  and unchecked "Analysis" and "Data" on Type and assert shown plugins
   *    3. Input 'D3' on search field, check "Community", unchecked  stages 1 and 4 and check "Visualization" and "Language Packs" on Type and assert shown plugins
   *
   **/
  @Test
  public void tc4_MarketPlacePage_FiltersCombined() {
    MarketPlace market = new MarketPlace( this.driver );
    /*
     *  Step 1
     */
    List<Number> type = new ArrayList<Number>();
    type.add( 1 );
    List<Number> stage = new ArrayList<Number>();
    stage.add( 1 );
    List<String> expectedList = new ArrayList<String>();
    expectedList.add( "Community Dashboards Framework" );
    expectedList.add( "Community Data Access" );
    expectedList.add( "Community Dashboard Editor" );
    expectedList.add( "Community Graphics Generator" );
    List<String> resultList = new ArrayList<String>();
    resultList = market.CheckFiltersApplied( type, stage, "Webdetails" );
    assertEquals( expectedList, resultList );

    /*
     *  Step 2
     */
    type.add( 3 );
    type.add( 5 );
    stage.clear();
    stage.add( 2 );
    stage.add( 4 );
    expectedList.remove( 1 );
    resultList.clear();
    resultList = market.CheckFiltersApplied( type, stage, "Community" );
    assertEquals( expectedList, resultList );

    /*
     *  Step 3
     */
    type.clear();
    type.add( 8 );
    type.add( 11 );
    stage.clear();
    stage.add( 7 );
    stage.add( 8 );
    stage.add( 11 );
    expectedList.clear();
    expectedList.add( "D3 Component Library" );
    resultList.clear();
    resultList = market.CheckFiltersApplied( type, stage, "D3" );
    assertEquals( expectedList, resultList );
  }
}
