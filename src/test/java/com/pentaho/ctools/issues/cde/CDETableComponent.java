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
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.selenium.BaseTest;

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
public class CDETableComponent extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDETableComponent.class );
  // this.failure variable ==1 if test did not fail
  private int failure = 0;

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Table Component Tests
   *
   * Description:
   *    CDE-392: Assert that table style follows style set by Dashboards renderer
   *
   * Steps:
   *    1. Open sample in view mode and assert class of table
   *    2. Open sample in edit mode and change the dashboards renderer, also assert title of "Style" property for table
   *    3. Open sample in view mode and assert class of table
   *    4. Open sample in edit mode and undo previous changes
   */
  @Test
  public void tc01_NewCdeDashboard_TableInheritsRenderer() {
    this.log.info( "tc01_NewCdeDashboard_TableInheritsRenderer" );

    /*
     * ## Step 1
     */
    //Open sample in view mode 
    driver.get( baseUrl + "api/repos/%3Apublic%3AIssues%3ACDE%3ACDE-379%3AChart1.wcdf/generatedContent" );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    //Assert table class
    WebElement table = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='RowObject1Table']" ) );
    assertNotNull( table );
    String tableClass = this.elemHelper.GetAttribute( driver, By.xpath( "//table[@id='RowObject1Table']" ), "class" );
    assertEquals( tableClass, "table table-striped table-bordered form-inline table-responsive dataTable" );
    this.failure = 1;

    /*
     * ## Step 2
     */
    //open sample in edit mode
    driver.get( baseUrl + "api/repos/%3Apublic%3AIssues%3ACDE%3ACDE-379%3AChart1.wcdf/wcdf.edit" );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    //Go to components panel, expand other components and select table component
    WebElement buttonComponentPanel = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='componentsPanelButton']" ) );
    assertNotNull( buttonComponentPanel );
    buttonComponentPanel.click();
    WebElement othersExpander = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//tr[@id='OTHERCOMPONENTS']/td/span" ) );
    assertNotNull( othersExpander );
    othersExpander.click();
    WebElement tableComponent = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//tr[@id='OTHERCOMPONENTS']/../tr[2]/td" ) );
    assertNotNull( tableComponent );
    tableComponent.click();

    //On properties table select Advanced and then assert title of "Style" property
    WebElement advancedProperties = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='advancedProperties propertiesUnSelected']" ) );
    assertNotNull( advancedProperties );
    advancedProperties.click();
    WebElement styleProperty = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//td[@title='Table Style, if left empty will infer from the dashboard renderer type']" ) );
    assertNotNull( styleProperty );

    //Open dashboard settings, assert bootstrap is selected as renderer, select blueprint, assert new selection, save
    WebElement settingsLink = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='headerLinks']//a[@onclick='cdfdd.saveSettings()']" ) );
    assertNotNull( settingsLink );
    settingsLink.click();
    WebElement settingsPopup = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='popup']//div[@id='popupstates']" ) );
    assertNotNull( settingsPopup );
    WebElement selectedDash = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//select[@id='rendererInput']/option[@selected='']" ) );
    String selectedValue = selectedDash.getAttribute( "value" );
    assertEquals( "bootstrap", selectedValue );
    Select select = new Select( this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//select[@id='rendererInput']" ) ) );
    select.selectByValue( "blueprint" );
    WebElement saveButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='popup']//div[@id='popupstates']//button[@id='popup_state0_buttonSave']" ) );
    assertNotNull( saveButton );
    saveButton.click();
    WebElement notifySuccess = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='notifyBar']" ) );
    assertNotNull( notifySuccess );
    String successMessage = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='notifyBar']/div[@class='notify-bar-message']" ) );
    assertEquals( "Dashboard Settings saved successfully", successMessage );
    this.failure = 0;

    /*
     * ## Step 3
     */
    driver.get( baseUrl + "api/repos/%3Apublic%3AIssues%3ACDE%3ACDE-379%3AChart1.wcdf/generatedContent" );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    //Assert table class
    table = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='RowObject1Table']" ) );
    assertNotNull( table );
    tableClass = this.elemHelper.GetAttribute( driver, By.xpath( "//table[@id='RowObject1Table']" ), "class" );
    assertEquals( tableClass, "tableComponent compact dataTable" );

    /*
     * ## Step 4
     */
    //open sample in edit mode
    driver.get( baseUrl + "api/repos/%3Apublic%3AIssues%3ACDE%3ACDE-379%3AChart1.wcdf/wcdf.edit" );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    //Open dashboard settings, assert bootstrap is selected as renderer, select blueprint, assert new selection, save
    settingsLink = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='headerLinks']//a[@onclick='cdfdd.saveSettings()']" ) );
    assertNotNull( settingsLink );
    settingsLink.click();
    settingsPopup = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='popup']//div[@id='popupstates']" ) );
    assertNotNull( settingsPopup );
    selectedDash = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//select[@id='rendererInput']/option[@selected='']" ) );
    selectedValue = selectedDash.getAttribute( "value" );
    assertEquals( "blueprint", selectedValue );
    select = new Select( this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//select[@id='rendererInput']" ) ) );
    select.selectByValue( "bootstrap" );
    saveButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='popup']//div[@id='popupstates']//button[@id='popup_state0_buttonSave']" ) );
    assertNotNull( saveButton );
    saveButton.click();
    notifySuccess = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='notifyBar']" ) );
    assertNotNull( notifySuccess );
    successMessage = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='notifyBar']/div[@class='notify-bar-message']" ) );
    assertEquals( "Dashboard Settings saved successfully", successMessage );
    this.failure = 1;

  }

  @AfterClass( alwaysRun = true )
  public void tearDown() {
    this.log.info( "tearDownClass" );
    if ( this.failure == 0 ) {
      //open sample in edit mode
      driver.get( baseUrl + "api/repos/%3Apublic%3AIssues%3ACDE%3ACDE-379%3AChart1.wcdf/wcdf.edit" );
      this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

      //Open dashboard settings, assert bootstrap is selected as renderer, select blueprint, assert new selection, save
      WebElement settingsLink = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='headerLinks']//a[@onclick='cdfdd.saveSettings()']" ) );
      assertNotNull( settingsLink );
      settingsLink.click();
      WebElement settingsPopup = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='popup']//div[@id='popupstates']" ) );
      assertNotNull( settingsPopup );
      WebElement selectedDash = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//select[@id='rendererInput']/option[@selected='']" ) );
      String selectedValue = selectedDash.getAttribute( "value" );
      assertEquals( "blueprint", selectedValue );
      Select select = new Select( this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//select[@id='rendererInput']" ) ) );
      select.selectByValue( "bootstrap" );
      WebElement saveButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='popup']//div[@id='popupstates']//button[@id='popup_state0_buttonSave']" ) );
      assertNotNull( saveButton );
      saveButton.click();
      WebElement notifySuccess = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='notifyBar']" ) );
      assertNotNull( notifySuccess );
      String successMessage = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='notifyBar']/div[@class='notify-bar-message']" ) );
      assertEquals( "Dashboard Settings saved successfully", successMessage );
    }
  }
}
