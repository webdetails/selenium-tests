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
package org.pentaho.ctools.issues.cde;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

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
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDE149 {
  // Instance of the driver (browser emulator)
  private static WebDriver DRIVER;
  // The base url to be append the relative url in test
  private static String BASE_URL;
  // Log instance
  private static Logger LOG = LogManager.getLogger( CDE149.class );
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( DRIVER );

  @BeforeClass
  public static void setUpClass() {
    LOG.info( "setUp##" + CDE149.class.getSimpleName() );
    DRIVER = CToolsTestSuite.getDriver();
    BASE_URL = CToolsTestSuite.getBaseUrl();
  }

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
  @Test( timeout = 120000 )
  public void tc01_CdeDashboard_ParametersNotOverlap() {
    LOG.info( "tc01_CdeDashboard_ParametersNotOverlap" );

    /*
     * ## Step 1
     */

    //Go to New CDE Dashboard
    DRIVER.get( BASE_URL + "api/repos/wcdf/new" );
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    //Assert elements on page and go to Datasources Panel
    WebElement element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@class='datasourcesPanelButton']" ) );
    assertNotNull( element );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.id( "previewButton" ) );
    assertNotNull( element );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@class='layoutPanelButton']" ) );
    assertNotNull( element );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@class='componentsPanelButton']" ) );
    assertNotNull( element );
    ElementHelper.Click( DRIVER, By.xpath( "//div[@class='datasourcesPanelButton']" ) );

    /*
     * ## Step 2
     */
    //Add MDX query element and click Parameters
    ElementHelper.ClickElementInvisible( DRIVER, By.xpath( "//a[@title='denormalizedMdx over mondrianJdbc']" ) );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//td[@title='Parameters to be sent to the xaction']" ) );
    assertNotNull( element );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[9]/td[2]" ) );
    assertNotNull( element );
    ElementHelper.Click( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[9]/td[2]" ) );

    /*
     * ## Step 3
     */
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.id( "popupbox" ) );
    assertNotNull( element );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.id( "ParameterList" ) );
    assertNotNull( element );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.id( "popup_state0_buttonOk" ) );
    assertNotNull( element );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.id( "popup_state0_buttonCancel" ) );
    assertNotNull( element );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//input[@class='ParameterListAddButton']" ) );
    assertNotNull( element );
    for ( int i = 1; i < 16; i++ ) {
      ElementHelper.Click( DRIVER, By.xpath( "//input[@class='ParameterListAddButton']" ) );
    }

    /*
     * ## Step 4
     */
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@class='ParameterList']/table/tbody/tr[16]/td/div/input" ) );
    assertNotNull( element );
    assertTrue( ElementHelper.ElementsNotOverlap( DRIVER, By.xpath( "//div[@class='ParameterList']/table/tbody/tr[16]/td[4]/div/input" ), By.id( "popup_state0_buttonCancel" ) ) );
    assertTrue( ElementHelper.ElementsNotOverlap( DRIVER, By.xpath( "//div[@class='ParameterList']/table/tbody/tr[16]/td[5]/input" ), By.id( "popup_state0_buttonCancel" ) ) );
    assertTrue( ElementHelper.ElementsNotOverlap( DRIVER, By.xpath( "//div[@class='ParameterList']/table/tbody/tr[16]/td[4]/div/input" ), By.id( "popup_state0_buttonOk" ) ) );
    assertTrue( ElementHelper.ElementsNotOverlap( DRIVER, By.xpath( "//div[@class='ParameterList']/table/tbody/tr[16]/td[5]/input" ), By.id( "popup_state0_buttonOk" ) ) );
    assertTrue( ElementHelper.ElementsNotOverlap( DRIVER, By.xpath( "//div[@class='ParameterList']/table/tbody/tr[16]/td[4]/div/input" ), By.xpath( "//input[@class='ParameterListAddButton']" ) ) );
    assertTrue( ElementHelper.ElementsNotOverlap( DRIVER, By.xpath( "//div[@class='ParameterList']/table/tbody/tr[16]/td[5]/input" ), By.xpath( "//input[@class='ParameterListAddButton']" ) ) );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@class='ParameterList']/table/tbody/tr[15]/td/div/input" ) );
    assertNotNull( element );
    assertTrue( ElementHelper.ElementsNotOverlap( DRIVER, By.xpath( "//div[@class='ParameterList']/table/tbody/tr[15]/td[4]/div/input" ), By.id( "popup_state0_buttonCancel" ) ) );
    assertTrue( ElementHelper.ElementsNotOverlap( DRIVER, By.xpath( "//div[@class='ParameterList']/table/tbody/tr[15]/td[5]/input" ), By.id( "popup_state0_buttonCancel" ) ) );
    assertTrue( ElementHelper.ElementsNotOverlap( DRIVER, By.xpath( "//div[@class='ParameterList']/table/tbody/tr[15]/td[4]/div/input" ), By.id( "popup_state0_buttonOk" ) ) );
    assertTrue( ElementHelper.ElementsNotOverlap( DRIVER, By.xpath( "//div[@class='ParameterList']/table/tbody/tr[15]/td[5]/input" ), By.id( "popup_state0_buttonOk" ) ) );
    assertTrue( ElementHelper.ElementsNotOverlap( DRIVER, By.xpath( "//div[@class='ParameterList']/table/tbody/tr[15]/td[4]/div/input" ), By.xpath( "//input[@class='ParameterListAddButton']" ) ) );
    assertTrue( ElementHelper.ElementsNotOverlap( DRIVER, By.xpath( "//div[@class='ParameterList']/table/tbody/tr[15]/td[5]/input" ), By.xpath( "//input[@class='ParameterListAddButton']" ) ) );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@class='ParameterList']/table/tbody/tr[16]/td/div/input" ) );
    assertNotNull( element );
    assertTrue( ElementHelper.ElementsNotOverlap( DRIVER, By.xpath( "//div[@class='ParameterList']/table/tbody/tr[14]/td[4]/div/input" ), By.id( "popup_state0_buttonCancel" ) ) );
    assertTrue( ElementHelper.ElementsNotOverlap( DRIVER, By.xpath( "//div[@class='ParameterList']/table/tbody/tr[14]/td[5]/input" ), By.id( "popup_state0_buttonCancel" ) ) );
    assertTrue( ElementHelper.ElementsNotOverlap( DRIVER, By.xpath( "//div[@class='ParameterList']/table/tbody/tr[14]/td[4]/div/input" ), By.id( "popup_state0_buttonOk" ) ) );
    assertTrue( ElementHelper.ElementsNotOverlap( DRIVER, By.xpath( "//div[@class='ParameterList']/table/tbody/tr[14]/td[5]/input" ), By.id( "popup_state0_buttonOk" ) ) );
    assertTrue( ElementHelper.ElementsNotOverlap( DRIVER, By.xpath( "//div[@class='ParameterList']/table/tbody/tr[14]/td[4]/div/input" ), By.xpath( "//input[@class='ParameterListAddButton']" ) ) );
    assertTrue( ElementHelper.ElementsNotOverlap( DRIVER, By.xpath( "//div[@class='ParameterList']/table/tbody/tr[14]/td[5]/input" ), By.xpath( "//input[@class='ParameterListAddButton']" ) ) );
  }

  @AfterClass
  public static void tearDownClass() {
    LOG.info( "tearDown##" + CDE149.class.getSimpleName() );
  }
}
