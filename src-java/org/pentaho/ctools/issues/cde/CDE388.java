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
import org.openqa.selenium.support.ui.Select;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-388
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-939
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDE388 {
  // Instance of the driver (browser emulator)
  private static WebDriver DRIVER;
  // The base url to be append the relative url in test
  private static String BASE_URL;
  // Log instance
  private static Logger LOG = LogManager.getLogger( CDE388.class );

  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( DRIVER );

  @BeforeClass
  public static void setUpClass() {
    LOG.info( "setUp##" + CDE388.class.getSimpleName() );
    DRIVER = CToolsTestSuite.getDriver();
    BASE_URL = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Drag and drop of OlapWizard working correctly
   *
   * Description:
   *    The test pretends validate the CDE-388 issue, so when user tries to use drag and drop functionality of OlapWizard
   *    it works as expected.
   *
   * Steps:
   *    1. Wait for new Dashboard to be created, assert elements on page and go to data sources
   *    2. Assert data sources Panel is shown, click "Wizards", click "OLAP Chart Wizard"
   *    3. Assert Wizard is shown, expand "Markets", drag "Territory" to "Rows" and "Quantity" to "Columns"
   *    4. Assert chart is shown on Preview
   */
  @Test( timeout = 120000 )
  public void tc01_CdeDashboard_OlapWizardDragAndDrop() {
    LOG.info( "tc01_CdeDashboard_OlapWizardDragAndDrop" );

    /*
     * ## Step 1
     */

    //Go to New CDE Dashboard
    DRIVER.get( BASE_URL + "api/repos/wcdf/new" );
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    //assert buttons
    WebElement element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//a[@title='Save as Template']" ) );
    assertNotNull( element );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//a[@title='Apply Template']" ) );
    assertNotNull( element );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//a[@title='Add Resource']" ) );
    assertNotNull( element );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//a[@title='Add Bootstrap Panel']" ) );
    assertNotNull( element );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//a[@title='Add FreeForm']" ) );
    assertNotNull( element );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//a[@title='Add Row']" ) );
    assertNotNull( element );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@class='datasourcesPanelButton']" ) );
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
    ElementHelper.WaitForTextPresence( DRIVER, By.xpath( "//div[@id='cdfdd-datasources-datasources']/div/div/div" ), "Datasources" );
    ElementHelper.WaitForTextPresence( DRIVER, By.xpath( "//div[@id='cdfdd-datasources-properties']/div/div/div" ), "Properties" );
    String classText = ElementHelper.FindElement( DRIVER, By.xpath( "//div[@title='Datasources Panel']" ) ).getAttribute( "class" );
    assertEquals( "panelButton panelButton-active", classText );
    ElementHelper.Click( DRIVER, By.xpath( "//div[@id='cdfdd-datasources-palletePallete']/div/h3/span" ) );
    ElementHelper.Click( DRIVER, By.xpath( "//div[@id='cdfdd-datasources-palletePallete']/div/div/ul/li[2]/a" ) );

    /*
     * ## Step 3
     */
    String text = ElementHelper.WaitForTextPresence( DRIVER, By.xpath( "//div[@id='wizardDialog']/div/div/h1" ), "OLAP Wizard" );
    assertEquals( "OLAP Wizard", text );
    Select select = new Select( ElementHelper.FindElement( DRIVER, By.id( "cdfddOlapCatalogSelect" ) ) );
    select.selectByVisibleText( "SteelWheels" );
    Select select1 = new Select( ElementHelper.FindElement( DRIVER, By.id( "cdfddOlapCubeSelect" ) ) );
    select1.selectByVisibleText( "SteelWheelsSales" );
    ElementHelper.Click( DRIVER, By.xpath( "//table[@id='cdfddOlapDimensionSelector']/tbody/tr/td/span" ) );
    ElementHelper.DragAndDrop( DRIVER, By.xpath( "//table[@id='cdfddOlapDimensionSelector']/tbody/tr[2]/td" ), By.id( "cdfdd-olap-rows" ) );
    ElementHelper.DragAndDrop( DRIVER, By.xpath( "//table[@id='cdfddOlapMeasureSelector']/tbody/tr/td" ), By.id( "cdfdd-olap-columns" ) );

    /*
     * ## Step 4
     */
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.id( "cdfdd-olap-preview-areaprotovis" ) );
    assertNotNull( element );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='cdfdd-olap-preview-areaprotovis']//*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='rect']" ) );
    assertNotNull( element );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='cdfdd-olap-preview-areaprotovis']//*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='rect'][2]" ) );
    assertNotNull( element );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='cdfdd-olap-preview-areaprotovis']//*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='rect'][3]" ) );
    assertNotNull( element );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='cdfdd-olap-preview-areaprotovis']//*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='rect'][4]" ) );
    assertNotNull( element );

  }

  @AfterClass
  public static void tearDownClass() {
    LOG.info( "tearDown##" + CDE388.class.getSimpleName() );
  }
}
