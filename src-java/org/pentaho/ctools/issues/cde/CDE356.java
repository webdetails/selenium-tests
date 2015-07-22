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
import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.PageUrl;
import org.pentaho.ctools.utils.ScreenshotTestRule;
import org.pentaho.gui.web.puc.BrowseFiles;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-356
 * - http://jira.pentaho.com/browse/CDE-397
 * - http://jira.pentaho.com/browse/CDE-468
 * - http://jira.pentaho.com/browse/CDE-536
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1083
 * - http://jira.pentaho.com/browse/QUALITY-1084
 * - http://jira.pentaho.com/browse/QUALITY-1092
 * - http://jira.pentaho.com/browse/QUALITY-1101
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDE356 {

  //Failure variable ==1 if test did not fail
  private int failure = 2;
  // Instance of the driver (browser emulator)
  private final WebDriver driver = CToolsTestSuite.getDriver();
  // Log instance
  private final Logger log = LogManager.getLogger( CDE356.class );
  //Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();

  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( this.driver );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert New dashboard can be created from the edit dashboard screen
   *
   * Description:
   *    The test pretends validate the CDE-356 issue, so when user tries to create a new dashboard from a edit
   *    dashboard screen it works as expected
   * Steps:
   *    1. Wait for new Dashboard to be created, add Row and click New
   *    2. CLick cancel on the popup and assert row is still present. Click New
   *    3. Click Ok on the popup, assert row is no longer present, add row
   *    4. Try to save dashboard with no name and assert it throws error (CDE-397)
   *    5. Input name with "$"at the start (CDE-536) and then click folder (CDE-468), save dashboard, click new,and assert new dashboard is shown
   *    6. Delete created files
   */
  @ Test
  public void tc01_CdeDashboard_CreateNewFromEdit() {
    this.log.info( "tc01_CdeDashboard_CreateNewFromEdit" );

    /*
     * ## Step 1
     */
    //Go to New CDE Dashboard
    this.driver.get( PageUrl.CDE_DASHBOARD );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Add Row and assert it was added
    WebElement addRow = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='table-cdfdd-layout-treeOperations']//a[@title='Add Row']" ) );
    assertNotNull( addRow );
    addRow.click();
    WebElement addedRow = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ) );
    assertNotNull( addedRow );
    String elementName = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ) );
    assertEquals( "Row", elementName );

    //Click New
    WebElement newButton = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='headerLinks']//a[@onclick='cdfdd.newDashboard()']" ) );
    assertNotNull( newButton );
    newButton.click();
    WebElement warningPopup = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "popup_state_state0" ) );
    assertNotNull( warningPopup );
    String warningMessage = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='popup_state_state0']/div/span" ) );
    assertEquals( "Unsaved changes will be lost.", warningMessage );

    /*
     * ## Step 2
     */
    //Click Cancel and assert row is still there
    WebElement cancelButton = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "popup_state0_buttonCancel" ) );
    assertNotNull( cancelButton );
    cancelButton.click();
    this.elemHelper.WaitForElementNotPresent( this.driver, By.id( "popup_state_state0" ) );
    addedRow = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ) );
    assertNotNull( addedRow );
    elementName = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ) );
    assertEquals( "Row", elementName );

    //Click New
    newButton = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='headerLinks']//a[@onclick='cdfdd.newDashboard()']" ) );
    assertNotNull( newButton );
    newButton.click();
    warningPopup = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "popup_state_state0" ) );
    assertNotNull( warningPopup );
    warningMessage = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='popup_state_state0']/div/span" ) );
    assertEquals( "Unsaved changes will be lost.", warningMessage );

    /*
     * ## Step 3
     */
    //Click Ok and assert row is not present
    WebElement okButton = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "popup_state0_buttonOk" ) );
    assertNotNull( okButton );
    okButton.click();
    this.elemHelper.WaitForElementNotPresent( this.driver, By.id( "popup_state_state0" ) );
    this.elemHelper.WaitForElementNotPresent( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ) );

    //Add row
    addRow = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='table-cdfdd-layout-treeOperations']//a[@title='Add Row']" ) );
    assertNotNull( addRow );
    addRow.click();
    addedRow = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ) );
    assertNotNull( addedRow );
    elementName = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ) );
    assertEquals( "Row", elementName );

    /*
     * ## Step 4
     */
    //Save dashboard with no name
    WebElement saveButton = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='headerLinks']//a[@onclick='cdfdd.save()']" ) );
    assertNotNull( saveButton );
    saveButton.click();
    WebElement savePopup = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "popup_state_state0" ) );
    assertNotNull( savePopup );
    WebElement homeFolder = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='container_id']//a[@rel='home/']" ) );
    assertNotNull( homeFolder );
    homeFolder.click();
    WebElement inputField = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "fileInput" ) );
    assertNotNull( inputField );
    okButton = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "popup_state0_buttonOk" ) );
    okButton.click();
    WebElement saveError = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='popupbox'][2]//div[@class='popupmessage']" ) );
    assertNotNull( saveError );
    String errorMessage = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='popupbox'][2]//div[@class='popupmessage']" ) );
    assertEquals( "Please insert a valid file name.", errorMessage );
    okButton = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='popupbox'][2]//div[@class='popupbuttons']/button[@id='popup_state0_buttonOk']" ) );
    assertNotNull( okButton );
    okButton.click();

    /*
     * ## Step 5
     */
    //Save dashboard
    inputField = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "fileInput" ) );
    assertNotNull( inputField );
    inputField.click();
    inputField.sendKeys( "$CDE356" );
    WebElement publicFolder = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='container_id']//a[@rel='public/']" ) );
    assertNotNull( publicFolder );
    publicFolder.click();
    okButton = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "popup_state0_buttonOk" ) );
    okButton.click();
    this.failure = 0;
    this.elemHelper.WaitForElementNotPresent( this.driver, By.id( "popup_state_state0" ) );
    WebElement dashTitle = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@title='$CDE356']" ) );
    assertNotNull( dashTitle );

    //Click New
    newButton = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='headerLinks']//a[@onclick='cdfdd.newDashboard()']" ) );
    assertNotNull( newButton );
    newButton.click();
    this.elemHelper.WaitForElementNotPresent( this.driver, By.xpath( "//div[@title='$CDE356']" ) );
    this.elemHelper.WaitForElementNotPresent( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ) );

    /*
     * ## Step 6
     */
    BrowseFiles browse = new BrowseFiles( this.driver );
    browse.DeleteMultipleFilesByName( "/public", "$CDE356" );
    browse.EmptyTrash();
    this.failure = 1;
  }

  @After
  public void tearDown() {
    this.log.info( "tearDown##" + CDE356.class.getSimpleName() );
    if ( this.failure == 0 ) {
      BrowseFiles browse = new BrowseFiles( this.driver );
      browse.DeleteMultipleFilesByName( "/public", "$CDE356" );
      browse.EmptyTrash();
    }

  }
}
