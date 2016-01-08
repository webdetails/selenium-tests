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
import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.gui.web.puc.BrowseFiles;
import com.pentaho.selenium.BaseTest;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-270
 * - http://jira.pentaho.com/browse/CDE-366
 * - http://jira.pentaho.com/browse/CDE-395
 * - http://jira.pentaho.com/browse/CDE-402
 * - http://jira.pentaho.com/browse/CDE-406
 * - http://jira.pentaho.com/browse/CDE-407
 * - http://jira.pentaho.com/browse/CDE-410
 * - http://jira.pentaho.com/browse/CDE-425
 * - http://jira.pentaho.com/browse/CDE-432
 * - http://jira.pentaho.com/browse/CDE-527
 * - http://jira.pentaho.com/browse/CDE-528
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-943
 * - http://jira.pentaho.com/browse/QUALITY-948
 * - http://jira.pentaho.com/browse/QUALITY-925
 * - http://jira.pentaho.com/browse/QUALITY-927
 * - http://jira.pentaho.com/browse/QUALITY-992
 * - http://jira.pentaho.com/browse/QUALITY-928
 * - http://jira.pentaho.com/browse/QUALITY-994
 * - http://jira.pentaho.com/browse/QUALITY-995
 * - http://jira.pentaho.com/browse/QUALITY-997
 * - http://jira.pentaho.com/browse/QUALITY-1141
 * - http://jira.pentaho.com/browse/QUALITY-1142
 * 
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDELayoutPanel extends BaseTest {
  // this.failure variable ==1 if test did not fail
  private int failure = 1;
  // Log instance
  private final Logger log = LogManager.getLogger( CDELayoutPanel.class );
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    This test is meant to validate all issues related to CDE's Layout Panel
   *
   * Description:
   *    CDE-270: Layout elements are draggable
   *    CDE-366: Style and Dashboard type changes are kept when saving dashboard
   *    CDE-395: Bootstrap is default renderer
   *    CDE-402: When applying template, user is asked for confirmation before it is applied
   *    CDE-406: Drag and Drop of Freeform elements works
   *    CDE-407: Able to duplicate spacers
   *    CDE-410: Shortcuts work
   *    CDE-425: Shortcuts work
   *    CDE-432: Left is the first option of text alignment
   *    CDE-527: Only Column can be direct child of Rows in a bootstrap dashboard
   *    CDE-528: If a bootstrap Column has no Xs defined a default of 12 is used
   *
   * Steps:
   *    1. Open new CDE dashboard and assert bootstrap is selected as renderer
   *    2. Use shortcuts to add and delete elements
   *    3. Add some elements and Drag them to other positions, asserting new positions
   *    4. Set template and assert it is correctly applied
   *    5. Assert navigation shortcuts work as expected and set clear Xs of an element
   *    6. Edit Settings and save dashboard. Preview dashboard and assert Xs is 12 by default
   */
  @Test
  public void tc01_CDEDashboardEdit_LayoutPanel() {
    this.log.info( "tc01_CDEDashboardEdit_LayoutPanel" );

    /*
     * ## Step 1
     */
    //Go to New CDE Dashboard
    driver.get( PageUrl.CDE_DASHBOARD );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    //Open Dashboard Settings and assert bootstrap is selected as renderer
    WebElement settingsLink = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='headerLinks']//a[@onclick='cdfdd.saveSettings()']" ) );
    assertNotNull( settingsLink );
    settingsLink.click();
    WebElement settingsPopup = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='popup']//div[@id='popupstates']" ) );
    assertNotNull( settingsPopup );
    Select dashboardType = new Select( this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "rendererInput" ) ) );
    String selectedOption = dashboardType.getFirstSelectedOption().getAttribute( "value" );
    assertEquals( "bootstrap", selectedOption );
    WebElement cancelButton = this.elemHelper.FindElement( driver, By.id( "popup_state0_buttonCancel" ) );
    assertNotNull( cancelButton );
    cancelButton.click();
    this.elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@id='popup']//div[@id='popupstates']" ) );

    /*
     * ## Step 2
     */
    //Add Row and verify that columns and html can not be added outside of rows
    this.elemHelper.Click( driver, By.id( "table-cdfdd-layout-tree" ) );
    Actions a = new Actions( driver );
    a.sendKeys( "c" ).sendKeys( "h" ).sendKeys( "r" ).build().perform();
    this.elemHelper.Click( driver, By.id( "table-cdfdd-layout-tree" ) );

    //Add Column
    a.sendKeys( "c" ).build().perform();

    //Add Html
    this.elemHelper.Click( driver, By.id( "table-cdfdd-layout-tree" ) );
    a.sendKeys( "h" ).build().perform();

    //Assert elements were successfully created
    WebElement row = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ) );
    assertNotNull( row );
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ), "Row" );
    String rowText = row.getText();
    assertEquals( rowText, "Row" );
    WebElement column = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[2]/td" ) );
    assertNotNull( column );
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[2]/td" ), "Column" );
    String columnText = column.getText();
    assertEquals( columnText, "Column" );
    WebElement html = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[3]/td" ) );
    assertNotNull( html );
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[3]/td" ), "Html" );
    String htmlText = html.getText();
    assertEquals( htmlText, "Html" );

    //Add spacer and duplicate it
    column.click();
    WebElement spacerButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='Add Space']" ) );
    assertNotNull( spacerButton );
    spacerButton.click();
    WebElement spacer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[4]/td" ) );
    assertNotNull( spacer );
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[4]/td" ), "Space" );
    String spacerText = spacer.getText();
    assertEquals( spacerText, "Space" );
    column.click();
    spacer.click();
    a.keyDown( Keys.SHIFT ).sendKeys( "d" ).keyUp( Keys.SHIFT ).build().perform();
    spacer = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[4]/td" ) );
    assertNotNull( spacer );
    WebElement spacer2 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[5]/td" ) );
    assertNotNull( spacer2 );
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[4]/td" ), "Space" );
    spacerText = spacer.getText();
    assertEquals( spacerText, "Space" );
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[5]/td" ), "Space" );
    String spacerText2 = spacer2.getText();
    assertEquals( spacerText2, "Space" );

    //Delete elements and assert they are no longer present
    this.elemHelper.Click( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ) );
    a.keyDown( Keys.SHIFT ).sendKeys( "x" ).keyUp( Keys.SHIFT ).build().perform();
    assertTrue( this.elemHelper.WaitForElementNotPresent( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ) ) );

    /*
     * ## Step 3
     */
    //Add elements
    WebElement addRow = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='Add Row']" ) );
    assertNotNull( addRow );
    addRow.click();
    WebElement addColumn = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='Add Columns']" ) );
    WebElement addFreeForm = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='Add FreeForm']" ) );
    WebElement addBootstrap = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='Add Bootstrap Panel']" ) );
    assertNotNull( addFreeForm );
    assertNotNull( addColumn );
    assertNotNull( addBootstrap );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='Add Bootstrap Panel']" ) ).click();
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='Add Row']" ) ).click();
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='Add FreeForm']" ) ).click();
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='Add Row']" ) ).click();
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='Add Columns']" ) ).click();
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='Add Columns']" ) ).click();
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='Add FreeForm']" ) ).click();

    //Assert elements on Layout
    String row1 = this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ), "Row" );
    assertEquals( "Row", row1 );
    String bootstrap = this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[2]/td" ), "Bootstrap Panel" );
    assertEquals( "Bootstrap Panel", bootstrap );
    String row2 = this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]/td" ), "Row" );
    assertEquals( "Row", row2 );
    String freeform1 = this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]/td" ), "FreeForm" );
    assertEquals( "FreeForm", freeform1 );
    String row3 = this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[8]/td" ), "Row" );
    assertEquals( "Row", row3 );
    String column1 = this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[9]/td" ), "Column" );
    assertEquals( "Column", column1 );
    String column2 = this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[10]/td" ), "Column" );
    assertEquals( "Column", column2 );
    String freeform2 = this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[11]/td" ), "FreeForm" );
    assertEquals( "FreeForm", freeform2 );

    //Assert bootstrap and freeform are parent elements
    String bootstrapClass = this.elemHelper.GetAttribute( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[2]" ), "class" );
    assertTrue( bootstrapClass.contains( "parent" ) );
    String freeformClass = this.elemHelper.GetAttribute( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]" ), "class" );
    assertTrue( freeformClass.contains( "parent" ) );

    //Move elements
    this.elemHelper.DragAndDrop( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[11]/td" ), By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[1]/td" ) );
    this.elemHelper.DragAndDrop( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[8]/td" ), By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[2]/td" ) );

    //Assert new positions
    freeform1 = this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ), "FreeForm" );
    assertEquals( "FreeForm", freeform1 );
    freeform2 = this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[2]/td" ), "FreeForm" );
    assertEquals( "FreeForm", freeform2 );
    row1 = this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[3]/td" ), "Row" );
    assertEquals( "Row", row1 );
    column1 = this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[4]/td" ), "Column" );
    assertEquals( "Column", column1 );
    column2 = this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[5]/td" ), "Column" );
    assertEquals( "Column", column2 );
    row2 = this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]/td" ), "Row" );
    assertEquals( "Row", row2 );
    bootstrap = this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]/td" ), "Bootstrap Panel" );
    assertEquals( "Bootstrap Panel", bootstrap );
    row3 = this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[11]/td" ), "Row" );
    assertEquals( "Row", row3 );

    /*
     * ## Step 4
     */
    //Apply template

    WebElement templateButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='Apply Template']" ) );
    assertNotNull( templateButton );
    templateButton.click();
    this.elemHelper.WaitForFrameReady( driver, By.id( "popup" ) );
    WebElement templatePopup = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "popup" ) );
    assertNotNull( templatePopup );
    String templateText = this.elemHelper.FindElement( driver, By.xpath( "//div[@class='template-scroll']/div[2]/span" ) ).getText();
    assertEquals( "2 Columns Template", templateText );
    this.elemHelper.Click( driver, By.xpath( "//div[@class='template-scroll']/div[2]/div/img" ) );
    WebElement templateOk = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "popup_state0_buttonOk" ) );
    assertNotNull( templateOk );
    this.elemHelper.Click( driver, By.id( "popup_state0_buttonOk" ) );
    assertTrue( this.elemHelper.WaitForElementNotPresent( driver, By.id( "popup" ) ) );
    WebElement confirmationPopup = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "popup" ) );
    assertNotNull( confirmationPopup );
    String warningText = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@class='popup-body-container layout-popup']" ) );
    assertEquals( "Are you sure you want to load the template?WARNING: Dashboard Layout will be overwritten!", warningText );
    WebElement confirmationOk = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "popup_state0_buttonOk" ) );
    assertNotNull( confirmationOk );
    this.elemHelper.Click( driver, By.id( "popup_state0_buttonOk" ) );
    this.elemHelper.WaitForElementNotPresent( driver, By.id( "popup" ) );

    //Asset elements were created
    String trtdText = this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ), "Row" );
    String trtd2Text = this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td[2]" ), "Header" );
    String tr4tdText = this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[4]/td" ), "Row" );
    String tr4td2Text = this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[4]/td[2]" ), "Spacer" );
    assertEquals( "Row", trtdText );
    assertEquals( "Header", trtd2Text );
    assertEquals( "Row", tr4tdText );
    assertEquals( "Spacer", tr4td2Text );

    /*
     * ## Step 5
     */
    //Press down and assert second Row is selected
    this.elemHelper.Click( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr/td" ) );
    String firstRowClass = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr" ) ).getAttribute( "class" );
    assertEquals( firstRowClass, "ui-draggable ui-droppable initialized parent collapsed ui-state-active" );
    a.sendKeys( Keys.DOWN ).build().perform();
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[4]" ), "class", "ui-draggable ui-droppable initialized ui-state-active" );
    String secondRowClass = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[4]" ) ).getAttribute( "class" );
    assertEquals( secondRowClass, "ui-draggable ui-droppable initialized ui-state-active" );

    //Assert columns aren't visible, go to row and expand it and then assert columns are visible
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]" ) );

    a.sendKeys( Keys.DOWN ).sendKeys( Keys.RIGHT ).build().perform();
    WebElement firstColumn = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]" ) );
    assertNotNull( firstColumn );
    WebElement secondColumn = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]" ) );
    assertNotNull( secondColumn );

    //Go to second column and back to first and assert first column is selected
    a.sendKeys( Keys.DOWN ).sendKeys( Keys.DOWN ).sendKeys( "c" ).sendKeys( Keys.TAB ).build().perform();
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[8]" ), "class", "ui-state-active" );
    String firstColumnClass = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[8]" ) ).getAttribute( "class" );
    assertTrue( firstColumnClass.contains( "ui-state-active" ) );

    //Click tab key and assert focus has changed to properties table
    a.sendKeys( Keys.TAB ).build().perform();
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//table[@id='table-cdfdd-layout-properties']/tbody/tr" ), "class", "initialized ui-state-active" );
    String firstPropertyClass = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-layout-properties']/tbody/tr" ) ).getAttribute( "class" );
    assertEquals( firstPropertyClass, "initialized ui-state-active" );

    //Click enter to change following properties "Name", "Span size" and "Right border"
    a.sendKeys( Keys.ENTER ).sendKeys( "a" ).sendKeys( Keys.ENTER ).sendKeys( Keys.DOWN ).build().perform();

    //assert name has changed
    String columnName = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='table-cdfdd-layout-properties']/tbody/tr/td[2]" ) );
    assertEquals( columnName, "a" );

    //Change "Extra Small Devices" and "Text Align"
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//table[@id='table-cdfdd-layout-properties']/tbody/tr[2]" ), "class", "initialized ui-state-active" );
    String xsInput = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-layout-properties']/tbody/tr[2]" ) ).getAttribute( "class" );
    assertEquals( xsInput, "initialized ui-state-active" );
    a.sendKeys( Keys.ENTER ).build().perform();
    this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-layout-properties']/tbody/tr[2]/td[2]/form/input" ) ).clear();
    a.sendKeys( Keys.ENTER ).build().perform();

    //assert values are changed
    String xsValue = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='table-cdfdd-layout-properties']/tbody/tr[2]/td[2]" ) );
    assertEquals( xsValue, "-" );

    a.sendKeys( Keys.DOWN ).sendKeys( Keys.DOWN ).sendKeys( Keys.DOWN ).sendKeys( Keys.DOWN ).sendKeys( Keys.DOWN ).sendKeys( Keys.DOWN ).sendKeys( Keys.DOWN ).sendKeys( Keys.DOWN ).sendKeys( Keys.ENTER ).sendKeys( Keys.DOWN ).sendKeys( Keys.DOWN ).sendKeys( Keys.ENTER ).sendKeys( Keys.ENTER ).build().perform();
    String alignValue = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='table-cdfdd-layout-properties']/tbody/tr[10]/td[2]" ) );
    assertEquals( alignValue, "Left" );
    //Click tab and assert focus has gone back to first table
    a.sendKeys( Keys.TAB ).build().perform();
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//table[@id='table-cdfdd-layout-properties']/tbody/tr[8]" ), "class", "initialized" );
    String columnClass = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-layout-properties']/tbody/tr[8]" ) ).getAttribute( "class" );
    assertEquals( columnClass, "initialized" );

    //Collapse Row and assert columns aren't showing
    a.sendKeys( Keys.UP ).sendKeys( Keys.UP ).sendKeys( Keys.UP ).sendKeys( Keys.LEFT ).build().perform();
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[8]" ) );

    /*
     * ## Step 6
     */
    //Save Dashboard
    WebElement saveDashboard = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='headerLinks']//a[@id='Save']" ) );
    assertNotNull( saveDashboard );
    saveDashboard.click();
    WebElement folderSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='saveAsFEContainer']//a[@rel='public/']" ) );
    assertNotNull( folderSelector );
    folderSelector.click();
    WebElement inputName = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "fileInput" ) );
    assertNotNull( inputName );
    inputName.sendKeys( "CDE366" );
    WebElement okButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='popupbuttons']/button[@id='popup_state0_buttonOk']" ) );
    okButton.click();
    this.elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@class='popupbuttons']" ) );
    WebElement title = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@title='CDE366']" ) );
    assertNotNull( title );
    this.failure = 0;

    //Open Dashboard Settings
    settingsLink = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='headerLinks']//a[@onclick='cdfdd.saveSettings()']" ) );
    assertNotNull( settingsLink );
    settingsLink.click();
    settingsPopup = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='popup']//div[@id='popupstates']" ) );
    assertNotNull( settingsPopup );

    //Edit Style and Dashboard Type
    Select style = new Select( this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "styleInput" ) ) );
    style.selectByVisibleText( "WDDocs" );
    Select dashType = new Select( this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "rendererInput" ) ) );
    dashType.selectByVisibleText( "blueprint" );

    //Click save and assert user gets a message of "Saved Successfully"
    okButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "popup_state0_buttonOk" ) );
    assertNotNull( okButton );
    okButton.click();
    WebElement notifySuccess = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='notifyBar']" ) );
    assertNotNull( notifySuccess );
    String successMessage = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='notifyBar']/div[@class='notify-bar-message']" ) );
    assertEquals( "Dashboard Settings saved successfully", successMessage );

    //Open dashboard in preview mode
    this.elemHelper.Get( driver, PageUrl.ISSUES_CDE_366 );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    //Assert Xs is set to 12 as default
    WebElement xsColumn = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='Body']/div[3]" ) );
    assertNotNull( xsColumn );
    String xsNumber = xsColumn.getAttribute( "class" );
    assertEquals( "col-xs-12 last", xsNumber );

    //Open dashboard in edit
    this.elemHelper.Get( driver, PageUrl.ISSUES_CDE_366_EDIT );

    //Open Settings and assert Style and Dashboard Type were saved 
    settingsLink = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='headerLinks']//a[@onclick='cdfdd.saveSettings()']" ) );
    assertNotNull( settingsLink );
    settingsLink.click();
    settingsPopup = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='popup']//div[@id='popupstates']" ) );
    assertNotNull( settingsPopup );
    WebElement selectedStyle = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//select[@id='styleInput']/option[@selected='']" ) );
    String selectedValue = selectedStyle.getAttribute( "value" );
    assertEquals( "WDDocs", selectedValue );
    WebElement selectedDash = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//select[@id='rendererInput']/option[@selected='']" ) );
    selectedValue = selectedDash.getAttribute( "value" );
    assertEquals( "blueprint", selectedValue );

    /*
     * ## Step 7
     */
    BrowseFiles browse = new BrowseFiles( driver );
    browse.DeleteMultipleFilesByName( "/public", "CDE366" );
    browse.EmptyTrash();
    this.failure = 1;
  }

  @AfterClass( alwaysRun = true )
  public void tearDown() {
    this.log.info( "tearDownClass" );
    if ( this.failure == 0 ) {
      BrowseFiles browse = new BrowseFiles( driver );
      browse.DeleteMultipleFilesByName( "/public", "CDE366" );
      browse.EmptyTrash();
    }
  }
}
