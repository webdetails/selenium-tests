/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2016 by Pentaho : http://www.pentaho.com
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
package com.pentaho.ctools.cdf.tutorials;

import static org.testng.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

public class DrillableDashboard extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  //Log instance
  private final Logger log = LogManager.getLogger( DrillableDashboard.class );
  // Instance to access CDF Tutorial page
  private final CdfTutorials cdftutorials = new CdfTutorials();

  /**
   * ############################### Test Case 0 ###############################
   *
   * Description:
   * 	Opens Drillable Dashboard tutorial and check if the page was loaded.
   */
  @Test
  public void tc0_openDrillableDashboards() {
    this.log.info( "openDrillableDashboards" );

    //Open CDE Tutorials Welcome page 
    this.elemHelper.Get( driver, PageUrl.CDF_TUTORIAL_DRILLABLE_DASHBOARD );

    //Test if page was loaded
    assertEquals( this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//*[@id='title']/h1" ) ), "Drillable Dashboard" );
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Check Tabs Names
   *    
   * Test Case Description:
   *    Calls a function that check that the first tab is named "Intro" and the 
   *    remaining tabs are named accordingly to the step number (e.g. Step1...StepX).
   * 	  The function will call another function that will check if every tab has 
   *    a right panel with a title similar to the tab name.
   *
   * Test Steps:
   * 		1. Call the function checkTabs with the quantity of steps that the sample has.
   */
  @Test
  public void tc1_checkTabsNames() {
    this.log.info( "checkTabsNames" );

    this.cdftutorials.checkTabs( 4 );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Check Webdetails Logo
   *    
   * Test Case Description:
   * 	  Calls a function that clicks on the Webdetails logo at the bottom of the 
   *    page and check if the page was loaded.
   * 
   * Test Steps:
   * 		1. Call the function checkWebdetailsLogoLink.
   */
  @Test
  public void tc2_checkWebdetailsLogo() {
    this.log.info( "checkWebdetailsLogo" );

    this.cdftutorials.checkWebdetailsLogoLink();
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Check Tooltips 
   *    
   * Test Case Description:
   * 	  Calls a function that checks every tooltips in every tab of the sample, 
   *    clicks it and make sure the code file that pops up isn't empty.
   * 
   * Test Steps:
   * 		1. Call the function checkTooltips.
   */
  @Test
  public void tc3_checkTooltips() {
    this.cdftutorials.checkTooltips( 4 );
  }
}
