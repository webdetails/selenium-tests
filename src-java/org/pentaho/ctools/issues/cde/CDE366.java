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
import org.pentaho.gui.web.puc.BrowseFiles;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-366
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-948
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDE366 {

  // Instance of the driver (browser emulator)
  private static WebDriver  DRIVER;
  // The base url to be append the relative url in test
  private static String     BASE_URL;
  // Log instance
  private static Logger     LOG                = LogManager.getLogger( CDE366.class );
  //Failure variable ==1 if test did not fail
  private static int        Failure            = 0;
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( DRIVER );

  @BeforeClass
  public static void setUpClass() {
    LOG.info( "setUp##" + CDE366.class.getSimpleName() );
    DRIVER = CToolsTestSuite.getDriver();
    BASE_URL = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert "Style" and "Dashboard Type" are saved correctly
   *
   * Description:
   *    The test pretends validate the CDE-366 issue, so when user saves the style and dashboard type
   *    options on the settings of a CDE dashboard, they are saved correctly
   *
   * Steps:
   *    1. Wait for new Dashboard to be created, open dashboard settings, click save and assert user gets error message
   *    2. Apply template and save dashboard
   *    3. Edit dashboard settings and save
   *    4. Close and reopen Dashboard, assert applied template and settings changes persist
   *    5. Delete created dashboard
   */
  @Test( timeout = 240000 )
  public void tc01_CdeDashboard_SettingsPersist() {
    LOG.info( "tc01_CdeDashboard_SettingsPersist" );

    /*
     * ## Step 1
     */
    //Go to New CDE Dashboard
    DRIVER.get( BASE_URL + "api/repos/wcdf/new" );
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Open Dashboard Settings
    WebElement settingsLink = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='headerLinks']//a[@onclick='cdfdd.saveSettings()']" ) );
    assertNotNull( settingsLink );
    settingsLink.click();
    WebElement settingsPopup = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='popup']//div[@id='popupstates']" ) );
    assertNotNull( settingsPopup );

    //Click save and assert user gets a message of "Error saving settings"
    WebElement saveButton = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='popup']//div[@id='popupstates']//button[@id='popup_state0_buttonSave']" ) );
    assertNotNull( saveButton );
    saveButton.click();
    WebElement notifyError = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='notifyBar']" ) );
    assertNotNull( notifyError );
    String errorMessage = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='notifyBar']/div[@class='notify-bar-message']" ) );
    assertEquals( "Errors saving settings", errorMessage );

    /*
     * ## Step 2
     */
    //Click Apply Template and wait for popup
    WebElement applyTemplate = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='table-cdfdd-layout-treeOperations']/a[@title='Apply Template']" ) );
    assertNotNull( applyTemplate );
    applyTemplate.click();
    WebElement templatePopup = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='popupTemplate']" ) );
    assertNotNull( templatePopup );

    //Find the Two Columns Template and apply it
    ElementHelper.WaitForFrameReady( DRIVER, By.id( "popupTemplatebox" ) );
    String templateText = ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='thumbs']/div[2]/p" ) ).getText();
    assertEquals( "Two Columns Template", templateText );
    ElementHelper.Click( DRIVER, By.xpath( "//div[@id='thumbs']/div[2]/p" ) );
    ElementHelper.WaitForAttributeValue( DRIVER, By.xpath( "//div[@id='thumbs']/div[2]" ), "class", "hover active" );
    String text = ElementHelper.GetAttribute( DRIVER, By.xpath( "//div[@id='thumbs']/div[2]" ), "class" );
    assertEquals( "hover active", text );
    WebElement okButton = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@class='popupTemplatebuttons']/button[@id='popupTemplate_state0_buttonOk']" ) );
    assertNotNull( okButton );
    okButton.click();
    WebElement confirmationMessage = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@class='popupTemplatemessage']" ) );
    assertNotNull( confirmationMessage );
    String warningText = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@class='popupTemplatemessage']" ) );
    assertEquals( "Are you sure you want to load the template?WARNING: Dashboard Layout will be overwritten!", warningText );
    okButton = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@class='popupTemplatebuttons']/button[@id='popupTemplate_state0_buttonOk']" ) );
    assertNotNull( okButton );
    ElementHelper.Click( DRIVER, By.xpath( "//div[@class='popupTemplatebuttons']/button[@id='popupTemplate_state0_buttonOk']" ) );

    //Assert Template was applied
    WebElement columnExpander = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[5]/td/span" ) );
    assertNotNull( columnExpander );
    ElementHelper.Click( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[5]/td/span" ) );
    WebElement firstPanel = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]" ) );
    assertNotNull( firstPanel );
    WebElement secondPanel = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]" ) );
    assertNotNull( secondPanel );
    String tr6tdText = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]/td" ) );
    String tr6td2Text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]/td[2]" ) );
    String tr7tdText = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]/td" ) );
    String tr7td2Text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]/td[2]" ) );
    assertEquals( "Column", tr6tdText );
    assertEquals( "Panel_1", tr6td2Text );
    assertEquals( "Column", tr7tdText );
    assertEquals( "Panel_2", tr7td2Text );

    //Save Dashboard
    WebElement saveDashboard = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='headerLinks']//a[@id='Save']" ) );
    assertNotNull( saveDashboard );
    saveDashboard.click();
    WebElement folderSelector = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='container_id']//a[@rel='public/']" ) );
    assertNotNull( folderSelector );
    folderSelector.click();
    WebElement inputName = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.id( "fileInput" ) );
    assertNotNull( inputName );
    inputName.sendKeys( "CDE366" );
    okButton = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@class='popupbuttons']/button[@id='popup_state0_buttonOk']" ) );
    okButton.click();
    ElementHelper.WaitForElementNotPresent( DRIVER, By.xpath( "//div[@class='popupbuttons']" ) );
    WebElement title = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@title='CDE366']" ) );
    assertNotNull( title );

    /*
     * ## Step 3
     */
    //Open Dashboard Settings
    settingsLink = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='headerLinks']//a[@onclick='cdfdd.saveSettings()']" ) );
    assertNotNull( settingsLink );
    settingsLink.click();
    settingsPopup = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='popup']//div[@id='popupstates']" ) );
    assertNotNull( settingsPopup );

    //Edit Style and Dashboard Type
    Select style = new Select( ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.id( "styleInput" ) ) );
    style.selectByVisibleText( "WDDocs" );
    Select dashType = new Select( ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.id( "rendererInput" ) ) );
    dashType.selectByVisibleText( "blueprint" );

    //Click save and assert user gets a message of "Error saving settings"
    saveButton = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='popup']//div[@id='popupstates']//button[@id='popup_state0_buttonSave']" ) );
    assertNotNull( saveButton );
    saveButton.click();
    WebElement notifySuccess = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='notifyBar']" ) );
    assertNotNull( notifySuccess );
    String successMessage = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='notifyBar']/div[@class='notify-bar-message']" ) );
    assertEquals( "Dashboard Settings saved successfully", successMessage );

    /*
     * ## Step 4
     */
    //Open Home Folder
    DRIVER.get( BASE_URL );
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

    //Open Dashboard in edit mode
    DRIVER.get( BASE_URL + "api/repos/%3Apublic%3ACDE366.wcdf/wcdf.edit" );
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Check template is applied
    columnExpander = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[5]/td/span" ) );
    assertNotNull( columnExpander );
    ElementHelper.Click( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[5]/td/span" ) );
    firstPanel = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]" ) );
    assertNotNull( firstPanel );
    secondPanel = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]" ) );
    assertNotNull( secondPanel );
    tr6tdText = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]/td" ) );
    tr6td2Text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]/td[2]" ) );
    tr7tdText = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]/td" ) );
    tr7td2Text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]/td[2]" ) );
    assertEquals( "Column", tr6tdText );
    assertEquals( "Panel_1", tr6td2Text );
    assertEquals( "Column", tr7tdText );
    assertEquals( "Panel_2", tr7td2Text );

    //Open Settings and assert Style and Dashboard Type were saved 
    settingsLink = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='headerLinks']//a[@onclick='cdfdd.saveSettings()']" ) );
    assertNotNull( settingsLink );
    settingsLink.click();
    settingsPopup = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='popup']//div[@id='popupstates']" ) );
    assertNotNull( settingsPopup );
    WebElement selectedStyle = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//select[@id='styleInput']/option[@selected='']" ) );
    String selectedValue = selectedStyle.getAttribute( "value" );
    assertEquals( "WDDocs", selectedValue );
    WebElement selectedDash = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//select[@id='rendererInput']/option[@selected='']" ) );
    selectedValue = selectedDash.getAttribute( "value" );
    assertEquals( "blueprint", selectedValue );

    /*
     * ## Step 4
     */
    BrowseFiles browse = new BrowseFiles( DRIVER );
    browse.DeleteMultipleFilesByName( "/public", "CDE366" );
    browse.EmptyTrash();
    Failure = 1;
  }

  @AfterClass
  public static void tearDownClass() {
    if (Failure == 0) {
      BrowseFiles browse = new BrowseFiles( DRIVER );
      browse.DeleteMultipleFilesByName( "/public", "CDE366" );
      browse.EmptyTrash();
    }
    LOG.info( "tearDown##" + CDE366.class.getSimpleName() );
  }
}
