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

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

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
 * - http://jira.pentaho.com/browse/CDE-149
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-990
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDE149 extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDE149.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Adding parameters
   *
   * Description:
   *    The test pretends validate the CDE-149 issue, so when user adds parameters to the parameter popup, they never
   *    overlap with the buttons.
   *
   * Steps:
   *    1. Assert elements on page and go to Datasources Panel
   *    2. Add a Query element, go to it's poperties and click parameters
   *    3. Assert parameter popup and buttons. Add 15 parameters
   *    4. Assert that last three parameter's fields do not intercept any of the buttons
   */
  @Test
  public void tc01_CdeDashboard_ParametersNotOverlap() {
    this.log.info( "tc01_CdeDashboard_ParametersNotOverlap" );

    /*
     * ## Step 1
     */

    //Go to New CDE Dashboard
    driver.get( PageUrl.CDE_DASHBOARD );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    //Assert elements on page and go to Datasources Panel
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='datasourcesPanelButton']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "previewButton" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='layoutPanelButton']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='componentsPanelButton']" ) );
    assertNotNull( element );
    this.elemHelper.Click( driver, By.xpath( "//div[@class='datasourcesPanelButton']" ) );

    /*
     * ## Step 2
     */
    //Add MDX query element and click Parameters
    this.elemHelper.ClickElementInvisible( driver, By.xpath( "//a[@title='denormalizedMdx over mondrianJdbc']" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//td[@title='Parameters to be sent to the xaction']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[9]/td[2]" ) );
    assertNotNull( element );
    this.elemHelper.Click( driver, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[9]/td[2]" ) );

    /*
     * ## Step 3
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "popupbox" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='popup-list-body ui-sortable']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "popup_state0_buttonOk" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "popup_state0_buttonCancel" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@class='popup-add-row-button']" ) );
    assertNotNull( element );
    for ( int i = 1; i < 16; i++ ) {
      this.elemHelper.Click( driver, By.xpath( "//button[@class='popup-add-row-button']" ) );
    }

    /*
     * ## Step 4
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "parameters_16" ) );
    assertNotNull( element );
    assertTrue( this.elemHelper.ElementsNotOverlap( driver, By.id( "parameters_16" ), By.id( "popup_state0_buttonCancel" ) ) );
    assertTrue( this.elemHelper.ElementsNotOverlap( driver, By.id( "parameters_16" ), By.id( "popup_state0_buttonOk" ) ) );
    assertTrue( this.elemHelper.ElementsNotOverlap( driver, By.id( "parameters_16" ), By.xpath( "//button[@class='popup-add-row-button']" ) ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "parameters_15" ) );
    assertNotNull( element );
    assertTrue( this.elemHelper.ElementsNotOverlap( driver, By.id( "parameters_15" ), By.id( "popup_state0_buttonCancel" ) ) );
    assertTrue( this.elemHelper.ElementsNotOverlap( driver, By.id( "parameters_15" ), By.id( "popup_state0_buttonOk" ) ) );
    assertTrue( this.elemHelper.ElementsNotOverlap( driver, By.id( "parameters_15" ), By.xpath( "//button[@class='popup-add-row-button']" ) ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "parameters_14" ) );
    assertNotNull( element );
    assertTrue( this.elemHelper.ElementsNotOverlap( driver, By.id( "parameters_14" ), By.id( "popup_state0_buttonCancel" ) ) );
    assertTrue( this.elemHelper.ElementsNotOverlap( driver, By.id( "parameters_14" ), By.id( "popup_state0_buttonOk" ) ) );
    assertTrue( this.elemHelper.ElementsNotOverlap( driver, By.id( "parameters_14" ), By.xpath( "//button[@class='popup-add-row-button']" ) ) );
  }

}
