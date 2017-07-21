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
package com.pentaho.sparkl;

import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.gui.web.puc.Sparkl;
import com.pentaho.gui.web.puc.sparkl.PluginCard;
import com.pentaho.selenium.BaseTest;

public class SparklMain extends BaseTest {
  private ElementHelper elemHelper = new ElementHelper();
  private final Logger log = LogManager.getLogger( SparklMain.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Tools menu entry
   * Description:
   *    This test will validate the existence of the tools menu entry for Sparkl and its functionality
   *
   * Steps:
   *    1. Check entry exists and has text "Sparkl"
   *    2. Click entry and assert that new tab is shown with Sparkl
   */
  @Test
  public void tc1_Sparkl_ToolsEntry() {
    this.log.info( "tc1_Sparkl_ToolsEntry" );

    /*
     *  Step 1
     */
    Sparkl sparkl = new Sparkl( driver );
    String toolsEntry = sparkl.ToolsEntryName( "Sparkl" );
    assertEquals( toolsEntry, "Sparkl" );

    /*
     *  Step 2
     */
    sparkl.ToolsEntryWorks( "Sparkl" );
    WebElement pluginTab = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='pentaho-tab-bar']//div[@title='Sparkl']" ) );
    assertNotNull( pluginTab );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Plugin Cards
   * Description:
   *    This test will validate the general layout and functionality of plugin cards
   *
   * Steps:
   *    1. Check layout (buttons, strings and description)
   *    2. Buttons work
   */
  @Test
  public void tc2_Sparkl_PluginCards() {
    this.log.info( "tc2_Sparkl_PluginCards" );

    /*
     *  Step 1
     */
    Sparkl sparkl = new Sparkl( driver );
    sparkl.OpenSparkl();
    PluginCard pluginSparkl = new PluginCard( "Sparkl", "sparkl", "Create and explore pentaho plugins." );

    //Check id, title and description of multiple plugins
    assertTrue( sparkl.PluginCardExists( pluginSparkl ) );
    PluginCard shownPlugin = sparkl.GetPluginCard( pluginSparkl );
    assertEquals( pluginSparkl, shownPlugin );

    /*
     *  Step 2
     */
    //Run Sparkl
    String mainDashUrl = sparkl.RunPlugin( pluginSparkl );
    assertEquals( "http://localhost:8080/pentaho/plugin/sparkl/api/main", mainDashUrl );

    //Edit Sparkl
    sparkl.GoToEditPage( pluginSparkl );
    WebElement editTitle = this.elemHelper.FindElement( driver, By.cssSelector( "div.pluginId" ) );
    assertNotNull( editTitle );
    sparkl.CloseEditPage();

    //Delete and cancel deletion of Sparkl
    String confirmationMessage = sparkl.DeleteCancelPlugin( pluginSparkl );
    assertEquals( "Are you sure you want to delete the plugin ?", confirmationMessage );
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Sorting of Plugins
   * Description:
   *    This test will validate the Sorting of plugins works as expected
   *
   * Steps:
   *    1. Sort by name and assert plugins are correctly sorted
   *    2. Sort by id and assert plugins are correctly sorted
   */
  @Test
  public void tc3_Sparkl_SortingPlugin() {
    this.log.info( "tc3_Sparkl_SortingPlugin" );

    /*
     *  Step 1
     */
    Sparkl sparkl = new Sparkl( driver );
    sparkl.OpenSparkl();
    sparkl.SortBy( "name" );
    assertTrue( sparkl.CheckSortBy( "name" ) );

    /*
     *  Step 2
     */
    sparkl.SortBy( "id" );
    assertTrue( sparkl.CheckSortBy( "id" ) );
  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name:
   *    Create Button
   * Description:
   *    This test will validate the Create Button works as expected
   *
   * Steps:
   *    1. Click Create Button and assert newPlugin screen shown
   */
  @Test
  public void tc4_Sparkl_CreateButton() {
    this.log.info( "tc4_Sparkl_CreateButton" );

    /*
     *  Step 1
     */
    Sparkl sparkl = new Sparkl( driver );
    sparkl.OpenSparkl();
    sparkl.CheckCreateButton();
    WebElement newPluginContainer = this.elemHelper.FindElement( driver, By.id( "newPluginIdFormObj" ) );
    assertNotNull( newPluginContainer );
  }

  /**
   * ############################### Test Case 5 ###############################
   *
   * Test Case Name:
   *    Refresh Button
   * Description:
   *    This test will validate the Refresh Button works as expected
   *
   * Steps:
   *    1. Click Refresh button and assert loading icon is shown
   */
  @Test
  public void tc5_Sparkl_RefreshButton() {
    this.log.info( "tc5_Sparkl_RefreshButton" );

    /*
     *  Step 1
     */
    Sparkl sparkl = new Sparkl( driver );
    sparkl.OpenSparkl();
    sparkl.RefreshPage();
    assertNotNull( this.elemHelper.FindElement( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 60 ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 60 );
  }
}
