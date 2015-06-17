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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
 * - http://jira.pentaho.com/browse/CDE-412
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-993
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDE412 {

  // Instance of the driver (browser emulator)
  private static WebDriver  DRIVER;
  // The base url to be append the relative url in test
  private static String     BASE_URL;
  // Log instance
  private static Logger     LOG                = LogManager.getLogger( CDE412.class );
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( DRIVER );

  @BeforeClass
  public static void setUpClass() {
    LOG.info( "setUp##" + CDE412.class.getSimpleName() );
    DRIVER = CToolsTestSuite.getDriver();
    BASE_URL = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Query editor is different for different queries
   *
   * Description:
   *    The test pretends validate the CDE-412 issue, so when user opens a query editor it reflects
   *    the type of query being edited.
   *
   * Steps:
   *    1. Assert elements on page and go to Datasources Panel
   *    2. Add MDX Query element, click Query button and assert title and default text. Remove MDX query
   *    3. Add MQL Query element, click Query button and assert title and default text. Remove MQL query
   *    4. Add Scriptable Query element, click Query button and assert title and default text. Remove Scriptable query
   *    4. Add Sql Query element, click Query button and assert title and default text. Remove Sql query
   */
  @Test( timeout = 120000 )
  public void tc01_CdeDashboard_QueryEditor() {
    LOG.info( "tc01_CdeDashboard_QueryEditor" );

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
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[8]/td[2]/div/button" ) );
    assertNotNull( element );
    ElementHelper.Click( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[8]/td[2]/div/button" ) );
    String title = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='wizardDialog']/div/div/h1" ) );
    assertEquals( "MDX Editor", title );
    String text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='wizardDialogBody']/form/div[2]/div/div/pre/div[2]/div/div[3]/div" ) );
    assertEquals( "select {} ON COLUMNS,", text );
    ElementHelper.Click( DRIVER, By.id( "cdfdd-wizard-button-ok" ) );
    ElementHelper.Click( DRIVER, By.xpath( "//div[@id='table-cdfdd-datasources-datasourcesOperations']/a[4]" ) );

    /*
     * ## Step 3
     */
    //Add MQL query element and click Parameters
    ElementHelper.ClickElementInvisible( DRIVER, By.xpath( "//a[@title='mql over metadata']" ) );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[4]/td[2]/div/button" ) );
    assertNotNull( element );
    ElementHelper.Click( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[4]/td[2]/div/button" ) );
    title = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='wizardDialog']/div/div/h1" ) );
    assertEquals( "MQL Editor", title );
    text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='wizardDialogBody']/form/div[2]/div/div/pre/div[2]/div/div[3]/div[2]" ) );
    assertEquals( "<mql>", text );
    ElementHelper.Click( DRIVER, By.id( "cdfdd-wizard-button-ok" ) );
    ElementHelper.Click( DRIVER, By.xpath( "//div[@id='table-cdfdd-datasources-datasourcesOperations']/a[4]" ) );

    /*
     * ## Step 4
     */
    //Add Scriptable query element and click Parameters
    ElementHelper.ClickElementInvisible( DRIVER, By.xpath( "//a[@title='scriptable over scripting']" ) );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[5]/td[2]/div/button" ) );
    assertNotNull( element );
    ElementHelper.Click( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[5]/td[2]/div/button" ) );
    title = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='wizardDialog']/div/div/h1" ) );
    assertEquals( "Scriptable Editor", title );
    text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='wizardDialogBody']/form/div[2]/div/div/pre/div[2]/div/div[3]/div" ) );
    assertEquals( "import org.pentaho.reporting.engine.classic.core.util.TypedTableModel;", text );
    ElementHelper.Click( DRIVER, By.id( "cdfdd-wizard-button-ok" ) );
    ElementHelper.Click( DRIVER, By.xpath( "//div[@id='table-cdfdd-datasources-datasourcesOperations']/a[4]" ) );

    /*
     * ## Step 5
     */
    //Add SQL query element and click Parameters
    ElementHelper.ClickElementInvisible( DRIVER, By.xpath( "//a[@title='sql over sqlJdbc']" ) );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[7]/td[2]/div/button" ) );
    assertNotNull( element );
    ElementHelper.Click( DRIVER, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[7]/td[2]/div/button" ) );
    title = ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='wizardDialog']/div/div/h1" ) ).getText();
    assertEquals( "Sql Editor", title );
    text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='wizardDialogBody']/form/div[2]/div/div/pre/div[2]/div/div[3]/div" ) );
    assertEquals( "", text );
    ElementHelper.Click( DRIVER, By.id( "cdfdd-wizard-button-ok" ) );
    ElementHelper.Click( DRIVER, By.xpath( "//div[@id='table-cdfdd-datasources-datasourcesOperations']/a[4]" ) );
  }

  @AfterClass
  public static void tearDownClass() {
    LOG.info( "tearDown##" + CDE412.class.getSimpleName() );
  }
}
