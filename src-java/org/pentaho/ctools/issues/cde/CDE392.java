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
import org.pentaho.ctools.utils.PageUrl;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-392
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-938
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDE392 {
  // Instance of the driver (browser emulator)
  private final WebDriver driver = CToolsTestSuite.getDriver();
  //Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDE392.class );
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( this.driver );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Asserting new table inherits Dashboard's rendering option
   *
   * Description:
   *    The test pretends validate the CDE-392 issue, so when user creates a new table component it inherits
   *    the dashboard's renderer option.
   *
   * Steps:
   *    1. Wait for new Dashboard to be created, assert elements on page and click "Settings"
   *    2. Focus on popup, assert elements and assert Bootstrap option is selected by default
   *    3. Go to "Components Panel", expand "Others" and click "Table Component"
   *    4. Click "Advanced Properties", find row with "Style" property and assert "Bootstrap" is selected
   *    5. Go to Settings and select another rendering option
   *    6. Assert confirmation popup is shown
   */
  @ Test
  public void tc01_NewCdeDashboard_TableInheritsRenderer() {
    this.log.info( "tc01_NewCdeDashboard_TableInheritsRenderer" );

    /*
     * ## Step 1
     */
    //Go to New CDE Dashboard
    this.driver.get( PageUrl.CDE_DASHBOARD );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    //assert buttons
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@title='Save as Template']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@title='Apply Template']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@title='Add Resource']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@title='Add Bootstrap Panel']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@title='Add FreeForm']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@title='Add Row']" ) );
    assertNotNull( element );
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
    this.elemHelper.Click( this.driver, By.xpath( "//div[@id='headerLinks']/div[5]/a" ) );

    /*
     * ## Step 2
     */
    WebElement obj1 = this.elemHelper.FindElement( this.driver, By.xpath( "//select[@id='rendererInput']/option[@value='bootstrap']" ) );
    assertEquals( obj1.isSelected(), true );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@class='popupclose']" ) );

    /*
     * ## Step 3
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='layoutPanelButton']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='componentsPanelButton']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='datasourcesPanelButton']" ) );
    assertNotNull( element );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@class='componentsPanelButton']" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[2]/h3/a" ) );
    assertNotNull( element );
    String otherText = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[2]/h3/a" ) );
    assertEquals( "Others", otherText );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[2]/h3/a" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@title='table Component']" ) );
    assertNotNull( element );
    this.elemHelper.Click( this.driver, By.xpath( "//a[@title='table Component']" ) );

    /*
     * ## Step 4
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='cdfdd-components-properties']/div/div/div[3]" ) );
    assertNotNull( element );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@id='cdfdd-components-properties']/div/div/div[3]" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='table-cdfdd-components-properties']//td[@title='Table style']" ) );
    assertNotNull( element );
    String styleText = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='table-cdfdd-components-properties']//td[@title='Table style']" ) );
    String style1Text = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='table-cdfdd-components-properties']//td[@title='Table style']/../td[2]" ) );
    assertEquals( "Style", styleText );
    assertEquals( "Bootstrap", style1Text );

    /*
     * ## Step 5
     */
    String newText1 = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='headerLinks']/div/a" ) );
    String saveText1 = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='headerLinks']/div[2]/a" ) );
    String saveasText1 = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='headerLinks']/div[3]/a" ) );
    String reloadText1 = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='headerLinks']/div[4]/a" ) );
    String settingsText1 = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='headerLinks']/div[5]/a" ) );
    assertEquals( "New", newText1 );
    assertEquals( "Save", saveText1 );
    assertEquals( "Save as...", saveasText1 );
    assertEquals( "Reload", reloadText1 );
    assertEquals( "Settings", settingsText1 );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@id='headerLinks']/div[5]/a" ) );

    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//select[@id='rendererInput']" ) );
    assertNotNull( element );
    Select select = new Select( this.elemHelper.FindElement( this.driver, By.xpath( "//select[@id='rendererInput']" ) ) );
    select.selectByValue( "blueprint" );
    this.elemHelper.Click( this.driver, By.xpath( "//button[@id='popup_state0_buttonSave']" ) );

    /*
     * ## Step 6
     */
    String confirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( this.driver );
    String expectedCnfText = "The dashboard contains Table Components whose 'Table Style' property needs to be updated.\nWould you like to UPDATE those properties?";
    assertEquals( confirmationMsg, expectedCnfText );

  }

}
