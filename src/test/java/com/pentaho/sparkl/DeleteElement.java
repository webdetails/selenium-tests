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

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.pentaho.gui.web.puc.Sparkl;
import com.pentaho.gui.web.puc.sparkl.PluginCard;
import com.pentaho.gui.web.puc.sparkl.PluginElement;
import com.pentaho.selenium.BaseTest;

public class DeleteElement extends BaseTest {

  private final Logger log = LogManager.getLogger( DeleteElement.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Delete Elements
   * Description:
   *    This test will validate the ability to delete elements
   *
   * Steps:
   *    1. Open Sparkl and go to Elements page of desired plugin 
   *    2. Delete created dashboards and existing dashboards
   *    3. Delete created endpoints and existing endpoints
   */
  @Test
  public void tc1_Sparkl_DeleteElement() {
    this.log.info( "tc1_Sparkl_DeleteElement" );

    /*
     *  Step 1
     */
    Sparkl sparkl = new Sparkl( driver );
    sparkl.OpenSparkl();
    PluginCard plugin = new PluginCard( "ATest", "aTest", "testDescription" );
    sparkl.GoToEditPage( plugin );
    sparkl.GoToElements();

    /*
     *  Step 2
     */
    //Delete created Dashboards and assert no longer present
    PluginElement dashAdmin = new PluginElement( "dashadmin", "dashboard", "clean", true );
    sparkl.CreateElement( dashAdmin );
    String confirmMessage = sparkl.DeleteElement( dashAdmin );
    assertEquals( confirmMessage, "You are about to delete dashadmin. Please, press OK to continue..." );
    assertFalse( sparkl.IsElementPresent( dashAdmin ) );
    PluginElement dashAll = new PluginElement( "dashall", "dashboard", "sparkl", false );
    sparkl.CreateElement( dashAll );
    confirmMessage = sparkl.DeleteElement( dashAll );
    assertEquals( confirmMessage, "You are about to delete dashall. Please, press OK to continue..." );
    assertFalse( sparkl.IsElementPresent( dashAll ) );

    //Delete already existing dashboards
    PluginElement dashExistAdmin = new PluginElement( "testdash3", "dashboard", "clean", true );
    confirmMessage = sparkl.DeleteElement( dashExistAdmin );
    assertEquals( confirmMessage, "You are about to delete testdash3. Please, press OK to continue..." );
    assertFalse( sparkl.IsElementPresent( dashExistAdmin ) );
    PluginElement dashExistAll = new PluginElement( "testdash", "dashboard", "sparkl", false );
    confirmMessage = sparkl.DeleteElement( dashExistAll );
    assertEquals( confirmMessage, "You are about to delete testdash. Please, press OK to continue..." );
    assertFalse( sparkl.IsElementPresent( dashExistAll ) );

    /*
     *  Step 3
     */
    //Delete created Endpoints and assert no longer present
    PluginElement endAdmin = new PluginElement( "endadmin", "endpoint", "ktr", true );
    sparkl.CreateElement( endAdmin );
    confirmMessage = sparkl.DeleteElement( endAdmin );
    assertEquals( confirmMessage, "You are about to delete endadmin. Please, press OK to continue..." );
    assertFalse( sparkl.IsElementPresent( endAdmin ) );
    PluginElement endAll = new PluginElement( "endall", "endpoint", "ktr", false );
    sparkl.CreateElement( endAll );
    confirmMessage = sparkl.DeleteElement( endAll );
    assertEquals( confirmMessage, "You are about to delete endall. Please, press OK to continue..." );
    assertFalse( sparkl.IsElementPresent( endAll ) );

    //Delete already existing dashboards
    PluginElement endExistAdmin = new PluginElement( "testendpoint3", "endpoint", "ktr", true );
    confirmMessage = sparkl.DeleteElement( endExistAdmin );
    assertEquals( confirmMessage, "You are about to delete testendpoint3. Please, press OK to continue..." );
    assertFalse( sparkl.IsElementPresent( endExistAdmin ) );
    PluginElement endExistAll = new PluginElement( "testendpoint", "endpoint", "ktr", false );
    confirmMessage = sparkl.DeleteElement( endExistAll );
    assertEquals( confirmMessage, "You are about to delete testendpoint. Please, press OK to continue..." );
    assertFalse( sparkl.IsElementPresent( endExistAll ) );
  }
}
