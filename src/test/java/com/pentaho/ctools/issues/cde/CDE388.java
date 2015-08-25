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
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.BaseTest;
import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;

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
public class CDE388 extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDE388.class );

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
  @Test
  public void tc01_CdeDashboard_OlapWizardDragAndDrop() {
    this.log.info( "tc01_CdeDashboard_OlapWizardDragAndDrop" );

    /*
     * ## Step 1
     */

    //Go to New CDE Dashboard
    driver.get( PageUrl.CDE_DASHBOARD );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    //assert buttons
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='Save as Template']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='Apply Template']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='Add Resource']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='Add Bootstrap Panel']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='Add FreeForm']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='Add Row']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='datasourcesPanelButton']" ) );
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
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//div[@id='cdfdd-datasources-datasources']/div/div/div" ), "Datasources" );
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//div[@id='cdfdd-datasources-properties']/div/div/div" ), "Properties" );
    String classText = this.elemHelper.FindElement( driver, By.xpath( "//div[@title='Datasources Panel']" ) ).getAttribute( "class" );
    assertEquals( "panelButton panelButton-active", classText );
    this.elemHelper.Click( driver, By.xpath( "//div[@id='cdfdd-datasources-palletePallete']/div/h3/span" ) );
    this.elemHelper.Click( driver, By.xpath( "//div[@id='cdfdd-datasources-palletePallete']/div/div/ul/li[2]/a" ) );

    /*
     * ## Step 3
     */
    String text = this.elemHelper.WaitForTextPresence( driver, By.xpath( "//div[@id='wizardDialog']//div[@class='popup-title-container']" ), "OLAP Wizard" );
    assertEquals( "OLAP Wizard", text );
    Select select = new Select( this.elemHelper.FindElement( driver, By.id( "cdfddOlapCatalogSelect" ) ) );
    select.selectByVisibleText( "SteelWheels" );
    Select select1 = new Select( this.elemHelper.FindElement( driver, By.id( "cdfddOlapCubeSelect" ) ) );
    select1.selectByVisibleText( "SteelWheelsSales" );
    this.elemHelper.Click( driver, By.xpath( "//div[@id='cdfddOlapDimensionSelector']//span[@class='prompt-caption-more-less']" ) );
    this.elemHelper.Click( driver, By.xpath( "//div[@id='prompt-dimensions-accordion']/div/h3/span" ) );
    this.elemHelper.Click( driver, By.xpath( "//div[@id='dimRow-Markets']/ul/li" ) );
    this.elemHelper.DragAndDrop( driver, By.xpath( "//div[@id='dimRow-Markets']/ul/li" ), By.id( "cdfdd-olap-rows" ) );
    this.elemHelper.Click( driver, By.xpath( "//div[@id='cdfddOlapMeasureSelector']//span[@class='prompt-caption-more-less']" ) );
    this.elemHelper.DragAndDrop( driver, By.xpath( "//div[@id='prompt-measures-accordion']/div/ul/li" ), By.id( "cdfdd-olap-columns" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    /*
     * ## Step 4
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "cdfdd-olap-preview-areaprotovis" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='cdfdd-olap-preview-areaprotovis']//*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='rect']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='cdfdd-olap-preview-areaprotovis']//*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='rect'][2]" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='cdfdd-olap-preview-areaprotovis']//*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='rect'][3]" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='cdfdd-olap-preview-areaprotovis']//*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='rect'][4]" ) );
    assertNotNull( element );
  }
}
