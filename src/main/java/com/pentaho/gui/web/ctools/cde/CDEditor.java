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
package com.pentaho.gui.web.ctools.cde;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.awt.AWTException;
import java.awt.Robot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;

public class CDEditor {

  // The driver
  private WebDriver driver;
  // Access to wrapper for webdriver
  private ElementHelper elemHelper = new ElementHelper();
  // Logging instance
  private final static Logger LOG = LogManager.getLogger( CDEditor.class );

  public CDEditor( WebDriver driver ) {
    this.driver = driver;
  }

  /**
   * This method will create a new CDE dashboard if not yet created
   *
   */
  public void GoToNewCDE() {
    LOG.debug( "Enter: GoToNewCDE" );
    String currUrl = this.driver.getCurrentUrl();
    if ( currUrl != PageUrl.CDE_DASHBOARD ) {
      this.driver.get( PageUrl.CDE_DASHBOARD );
      this.elemHelper.WaitForElementInvisibility( this.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    }
  }

  /**
   * This method shall 
   */
  public void GoToComponentPanel() {
    LOG.debug( "Enter: GoToComponentPanel" );

    WebElement cdePanel = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "cdfdd-panels" ) );
    //If we are not in CDE Editor we go there.
    if ( cdePanel == null ) {
      GoToNewCDE();
    }
    //Go to Component Panel
    //Check if we are there already!
    WebElement componentPanelActive = this.elemHelper.WaitForElementPresence( this.driver, By.cssSelector( "div.panelButton.panelButton-active[title='Components Panel']" ), 1 );
    if ( componentPanelActive == null ) {
      this.elemHelper.ClickJS( this.driver, By.cssSelector( "div.componentsPanelButton" ) );
      this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.cssSelector( "div.panelButton.panelButton-active" ) );
    }
  }

  /**
   * This method will save the current dashboard given the title and path
   *
   * Ex. title="CDETest" path="/public/plugin-samples/pentaho-cdf-dd" will save the dashboard with the title CDETest
   * under /public/plugin-samples/pentaho-cdf-dd.
   *
   * @param path
   * @param title
   *
   */
  public void SaveDashboard( String title, String path ) {
    LOG.info( "SaveDashboard::Enter" );

    //Click "Save"
    WebElement saveDashboard = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='headerLinks']//a[@id='Save']" ) );
    assertNotNull( saveDashboard );
    saveDashboard.click();

    //Select Folder
    String[] folders = path.split( "/" );
    String currentFolder = "";
    for ( int i = 1; i < folders.length; i++ ) {
      currentFolder = currentFolder + folders[i] + "/";
      WebElement folderSelector = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='saveAsFEContainer']//a[@rel='" + currentFolder + "']" ) );
      assertNotNull( folderSelector );
      folderSelector.click();
    }

    //Input title
    WebElement inputName = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "fileInput" ) );
    assertNotNull( inputName );
    inputName.sendKeys( title );
    WebElement okButton = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='popupbuttons']/button[@id='popup_state0_buttonOk']" ) );
    okButton.click();
    this.elemHelper.WaitForElementNotPresent( this.driver, By.xpath( "//div[@class='popupbuttons']" ) );
  }

  /**
   * This method will move to Layout Panel if not there and add a Row
   *
   */
  public void AddRow() {
    LOG.info( "Enter: AddRow" );
    if ( this.elemHelper.FindElement( this.driver, By.id( "panel-layout_panel" ) ) == null ) {
      WebElement layoutButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@title='Layout Panel']/a" ) );
      assertNotNull( layoutButton );
      layoutButton.click();
    }
    WebElement addRow = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@title='Add Row']" ) );
    assertNotNull( addRow );
    addRow.click();
  }

  /**
   * This method will move to Layout Panel if not there and add a Column
   *
   */
  public void AddColumn() {
    LOG.info( "Enter: AddColumn" );
    if ( this.elemHelper.FindElement( this.driver, By.id( "panel-layout_panel" ) ) == null ) {
      WebElement layoutButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@title='Layout Panel']/a" ) );
      assertNotNull( layoutButton );
      layoutButton.click();
    }
    WebElement AddColumn = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@title='Add Columns']" ) );
    assertNotNull( AddColumn );
    AddColumn.click();
  }

  /**
   * This method will move to Layout Panel if not there and add a Bootstrap
   *
   */
  public void AddBootstrap() {
    LOG.info( "Enter: AddBootstrap" );
    if ( this.elemHelper.FindElement( this.driver, By.id( "panel-layout_panel" ) ) == null ) {
      WebElement layoutButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@title='Layout Panel']/a" ) );
      assertNotNull( layoutButton );
      layoutButton.click();
    }
    WebElement AddBootstrap = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@title='Add Bootstrap Panel']" ) );
    assertNotNull( AddBootstrap );
    AddBootstrap.click();
  }

  /**
   * This method will move to Layout Panel if not there and add a FreeForm
   *
   */
  public void AddFreeform() {
    LOG.info( "Enter: AddFreeform" );
    if ( this.elemHelper.FindElement( this.driver, By.id( "panel-layout_panel" ) ) == null ) {
      WebElement layoutButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@title='Layout Panel']/a" ) );
      assertNotNull( layoutButton );
      layoutButton.click();
    }
    WebElement AddFreeform = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@title='Add FreeForm']" ) );
    assertNotNull( AddFreeform );
    AddFreeform.click();
  }

  /**
   * This method will move to Layout Panel if not there and add a Html
   *
   */
  public void AddHtml() {
    LOG.info( "Enter: AddHtml" );
    if ( this.elemHelper.FindElement( this.driver, By.id( "panel-layout_panel" ) ) == null ) {
      WebElement layoutButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@title='Layout Panel']/a" ) );
      assertNotNull( layoutButton );
      layoutButton.click();
    }
    WebElement AddHtml = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@title='Add Html']" ) );
    assertNotNull( AddHtml );
    AddHtml.click();
  }

  /**
   * This method will move to Layout Panel if not there and add a Space
   *
   */
  public void AddSpace() {
    LOG.info( "Enter: AddSpace" );
    if ( this.elemHelper.FindElement( this.driver, By.id( "panel-layout_panel" ) ) == null ) {
      WebElement layoutButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@title='Layout Panel']/a" ) );
      assertNotNull( layoutButton );
      layoutButton.click();
    }
    WebElement AddSpace = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@title='Add Space']" ) );
    assertNotNull( AddSpace );
    AddSpace.click();
  }

  /**
   * This method will press a shortcut key. It receives the name of the key to be pressed.
   * 
   * Possible options: UP, DOWN, LEFT, RIGHT, ENTER, TAB, A
   * 
   * @param key
   */
  public void KeyShortcut( String key ) {
    LOG.info( "Enter: KeyShortcut" );
    int keyNumber;
    switch ( key ) {
      case "UP":
        keyNumber = 38;
        break;
      case "DOWN":
        keyNumber = 40;
        break;
      case "LEFT":
        keyNumber = 37;
        break;
      case "RIGHT":
        keyNumber = 39;
        break;
      case "ENTER":
        keyNumber = 10;
        break;
      case "TAB":
        keyNumber = 9;
        break;
      case "BACKSPACE":
        keyNumber = 8;
        break;
      case "A":
        keyNumber = 65;
        break;
      default:
        keyNumber = 0;
        break;
    }
    Robot robot;
    try {
      robot = new Robot();
      robot.keyPress( keyNumber );
      robot.keyRelease( keyNumber );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }

  }

  /**
   * This method will press a shortcut key. It receives the number of the key to be pressed.
   * 
   * For reference- http://www.cambiaresearch.com/articles/15/javascript-char-codes-key-codes
   * 
   * @param key
   */
  public void KeyShortcut( int keyNumber ) {
    LOG.info( "Enter: KeyShortcut" );
    Robot robot;
    try {
      robot = new Robot();
      robot.keyPress( keyNumber );
      robot.keyRelease( keyNumber );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }

  }

  /**
   * This method will press Shift + a shortcut key. It receives the number of the key to be pressed.
   * 
   * For reference- http://www.cambiaresearch.com/articles/15/javascript-char-codes-key-codes
   * 
   * @param key
   */
  public void ShiftKeyShortcut( int keyNumber ) {
    LOG.info( "Enter: ShiftKeyShortcut" );
    Robot robot;
    try {
      robot = new Robot();
      robot.keyPress( 16 );
      robot.keyPress( keyNumber );
      robot.keyRelease( keyNumber );
      robot.keyRelease( 16 );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
  }

  /**
   * This method will press open the dashboard settings and update values if values are received.
   * 
   * Will return with selected values separated by commas.
   * 
   * @param style
   * @param type
   * @returns selected
   */
  public String DashboardSettings( String style, String type ) {
    LOG.info( "Enter: DashboardSettings" );
    String selected;
    WebElement settingsLink = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='headerLinks']//a[@onclick='cdfdd.saveSettings()']" ) );
    assertNotNull( settingsLink );
    settingsLink.click();
    WebElement settingsPopup = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='popup']//div[@id='popupstates']" ) );
    assertNotNull( settingsPopup );
    if ( style != "" ) {
      Select styleSelect = new Select( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "styleInput" ) ) );
      styleSelect.selectByValue( style );
    }
    if ( type != "" ) {
      Select typeSelect = new Select( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "rendererInput" ) ) );
      typeSelect.selectByValue( type );
    }
    if ( style != "" && type != "" ) {
      Select styleSelect = new Select( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "styleInput" ) ) );
      styleSelect.selectByValue( style );
      Select typeSelect = new Select( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "rendererInput" ) ) );
      typeSelect.selectByValue( type );
      selected = style + "," + type;
    } else if ( style == "" && type != "" ) {
      Select typeSelect = new Select( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "rendererInput" ) ) );
      typeSelect.selectByValue( type );
      WebElement selectedStyle = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//select[@id='styleInput']/option[@selected='']" ) );
      selected = selectedStyle.getAttribute( "value" );
      selected = selected + "," + type;
    } else if ( style != "" && type == "" ) {
      Select styleSelect = new Select( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "rendererInput" ) ) );
      styleSelect.selectByValue( style );
      WebElement selectedType = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//select[@id='rendererInput']/option[@selected='']" ) );
      selected = style + "," + selectedType.getAttribute( "value" );
    } else {
      WebElement selectedStyle = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//select[@id='styleInput']/option[@selected='']" ) );
      selected = selectedStyle.getAttribute( "value" );
      WebElement selectedDash = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//select[@id='rendererInput']/option[@selected='']" ) );
      selected = selected + "," + selectedDash.getAttribute( "value" );
    }
    WebElement saveButton = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='popup']//div[@id='popupstates']//button[@id='popup_state0_buttonSave']" ) );
    assertNotNull( saveButton );
    saveButton.click();
    this.elemHelper.WaitForElementNotPresent( this.driver, By.xpath( "//div[@id='popup']//div[@id='popupstates']" ) );
    return selected;
  }

  /**
   * This method will catch notify banner compare it's message to the provided one returning true if they are equal.
   * 
   * @param message
   * @returns equal
   */
  public boolean NotifyBannerMessage( String message ) {
    LOG.info( "Enter: NotifyBannerMessage" );
    Boolean equal = false;
    WebElement notifySuccess = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='notifyBar']" ) );
    assertNotNull( notifySuccess );
    String returnedMessage = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='notifyBar']/div[@class='notify-bar-message']" ) );
    equal = message.equals( returnedMessage );
    return equal;
  }

  /**
   * This method will switch to Layout panel if it is not selected and apply a template given it's name.
   * e.g.: "Two Columns Template"
   *  
   * @param template
   * 
   */
  public void ApplyTemplate( String template ) {
    LOG.info( "Enter: ApplyTemplate" );
    WebElement templateButton = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@title='Apply Template']" ) );
    assertNotNull( templateButton );
    templateButton.click();
    this.elemHelper.WaitForFrameReady( this.driver, By.id( "popupTemplatebox" ) );
    WebElement templatePopup = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "popupTemplatebox" ) );
    assertNotNull( templatePopup );
    WebElement templateElem = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='thumbs']//p[contains(text(),'" + template + "')]" ) );
    templateElem.click();
    WebElement templateOk = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='popupTemplatebuttons']/button[@id='popupTemplate_state0_buttonOk']" ) );
    assertNotNull( templateOk );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@class='popupTemplatebuttons']/button[@id='popupTemplate_state0_buttonOk']" ) );
    WebElement confirmationPopup = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='popupTemplatemessage']" ) );
    assertNotNull( confirmationPopup );
    String warningText = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@class='popupTemplatemessage']" ) );
    assertEquals( "Are you sure you want to load the template?WARNING: Dashboard Layout will be overwritten!", warningText );
    WebElement confirmationOk = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='popupTemplatebuttons']/button[@id='popupTemplate_state0_buttonOk']" ) );
    assertNotNull( confirmationOk );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@class='popupTemplatebuttons']/button[@id='popupTemplate_state0_buttonOk']" ) );
    this.elemHelper.WaitForElementNotPresent( this.driver, By.xpath( "//div[@class='popupTemplatemessage']" ) );
  }

  public Popup Popup() {
    Popup dialogPopup = null;
    WebElement popup = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "popup" ) );

    if ( popup != null ) {
      String title = this.elemHelper.WaitForTextDifferentEmpty( this.driver, By.cssSelector( "#popup_state_state0 > div > div.popup-header-container > div.popup-title-container" ) );
      String message = this.elemHelper.WaitForTextDifferentEmpty( this.driver, By.cssSelector( "#popup_state_state0 > div > div.popup-body-notification > div" ) );

      dialogPopup = new Popup( title, message );

      this.elemHelper.ClickJS( this.driver, By.id( "popup_state0_buttonOk" ) );
      this.elemHelper.WaitForElementInvisibility( this.driver, By.id( "popup" ) );
    }

    return dialogPopup;
  }

  public boolean PopupPresent() {
    return ( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "popup" ), 2 ) != null ) ? true
        : false;
  }

  public boolean PopupNotPresent() {
    return this.elemHelper.WaitForElementNotPresent( this.driver, By.id( "popup" ), 2 );
  }
}
