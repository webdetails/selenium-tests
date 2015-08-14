/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2015 by Pentaho : http://www.pentaho.com
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
package com.pentaho.ctools.issues.cde;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.BaseTest;
import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-395
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-925
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDE395 extends BaseTest {
  //Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDE395.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Asserting new CDE dashboard has Bootstrap as renderer option
   *
   * Description:
   *    The test pretends validate the CDE-395 issue, so when user creates a new CDE
   *    dashboard it has the renderer option as Bootstrap by default.
   *
   * Steps:
   *    1. Create New Dashboard, assert elements on page and click "Settings"
   *    2. Focus on popup, assert elements and assert Bootstrap option is selected by default
   */
  @Test
  public void tc01_NewCdeDashboard_DefaultRendererBootstrap() {
    this.log.info( "tc01_NewCdeDashboard_DefaultRendererBootstrap" );

    /*
     * ## Step 1
     */
    //Create new CDE dashboard
    this.driver.get( PageUrl.CDE_DASHBOARD );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    //assert buttons and click Settings
    String newText = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='headerLinks']/div/a" ) );
    String saveText = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='headerLinks']/div[2]/a" ) );
    String saveasText = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='headerLinks']/div[3]/a" ) );
    String reloadText = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='headerLinks']/div[4]/a" ) );
    String settingsText = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='headerLinks']/div[5]/a" ) );
    assertEquals( "New", newText );
    assertEquals( "Save", saveText );
    assertEquals( "Save as...", saveasText );
    assertEquals( "Reload", reloadText );
    assertEquals( "Settings", settingsText );
    this.elemHelper.ClickJS( this.driver, By.xpath( "//div[@id='headerLinks']/div[5]/a" ) );

    /*
     * ## Step 2
     */
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "popup" ) );
    assertNotNull( element );
    WebElement obj1 = this.elemHelper.FindElement( this.driver, By.xpath( "//select[@id='rendererInput']/option[@value='bootstrap']" ) );
    assertEquals( obj1.isSelected(), true );
  }
}
