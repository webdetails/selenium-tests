/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2016 by Pentaho : http://www.pentaho.com
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
package com.pentaho.gui.web.puc;

import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.gui.web.puc.sparkl.PluginCard;
import com.pentaho.gui.web.puc.sparkl.PluginElement;
import com.pentaho.gui.web.puc.sparkl.PluginInfo;

public class Sparkl {

  private WebDriver driver;
  private ElementHelper elemHelper = new ElementHelper();
  private static Logger log = LogManager.getLogger( Sparkl.class );

  public Sparkl( WebDriver driver ) {
    this.driver = driver;
    this.driver.get( "http://localhost:8080/pentaho/Home" );
  }

  /**
   * This method will open Sparkl
   *
   */
  public void OpenSparkl() {
    log.info( "Enter: OpenSparkl" );
    this.driver.get( "http://localhost:8080/pentaho/plugin/sparkl/api/main" );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 360 );

    //Assert Sparkl page is shown
    WebElement sparklLogo = this.elemHelper.FindElement( this.driver, By.cssSelector( "a.sparklLogo" ) );
    assertNotNull( sparklLogo );

    log.info( "Exit: OpenSparkl" );
  }

  /**
   * This method will check for existence of Tools menu entry given Plugin's name and return the string on the entry.
   * 
   * It must be called from Home page
   *
   * Ex: ToolsEntryName("Sparkl")
   * 
   * @param name
   * @returns text
   */
  public String ToolsEntryName( String name ) {
    log.info( "Enter: ToolsEntryName" );
    String text;
    //Expand tools menu
    WebElement toolsMenu = this.elemHelper.FindElement( this.driver, By.cssSelector( "td#toolsmenu" ) );
    assertNotNull( toolsMenu );
    toolsMenu.click();
    //Look for Plugin by name
    WebElement pluginEntry = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='toolsmenu']//td[text() = '" + name + "']" ) );
    if ( pluginEntry != null ) {
      text = pluginEntry.getText();
    } else {
      log.info( "Tools entry for plugin " + name + " does not exist" );
      text = "";
    }
    log.info( "Exit: ToolsEntryName" );
    toolsMenu = this.elemHelper.FindElement( this.driver, By.cssSelector( "td#toolsmenu" ) );
    assertNotNull( toolsMenu );
    toolsMenu.click();
    return text;
  }

  /**
   * This method will check for existence of Tools menu entry given Plugin's name and return TRUE if it exists.
   * 
   * It must be called from Home page
   *
   * Ex: ToolsEntryExists("Sparkl")
   * 
   * @param name
   * @returns exists
   */
  public boolean ToolsEntryExists( String name ) {
    log.info( "Enter: ToolsEntryExists" );
    Boolean exists = false;
    //Expand tools menu
    WebElement toolsMenu = this.elemHelper.FindElement( this.driver, By.cssSelector( "td#toolsmenu" ) );
    assertNotNull( toolsMenu );
    toolsMenu.click();
    //Look for Plugin by name
    WebElement pluginEntry = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='toolsmenu']//td[text() = '" + name + "']" ) );
    if ( pluginEntry != null ) {
      exists = true;
    }
    log.info( "Exit: ToolsEntryExists" );
    return exists;
  }

  /**
   * This method will check for existence of Tools menu entry given Plugin's name and click entry asserting new
   * tab is shown with plugin name.
   *
   *It must be called from Home page
   *
   * Ex: ToolsEntryWorks("Sparkl")
   * 
   * @param name
   */
  public void ToolsEntryWorks( String name ) {
    log.info( "Enter: ToolsEntryWorks" );
    if ( ToolsEntryExists( name ) ) {
      WebElement pluginEntry = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='toolsmenu']//td[text() = '" + name + "']" ) );
      assertNotNull( pluginEntry );
      pluginEntry.click();
    } else {
      log.info( name + " plugin is not present in tools menu." );
    }
    WebElement pluginTab = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='pentaho-tab-bar']//div[@title='" + name + "']" ) );
    assertNotNull( pluginTab );
    log.info( "Exit: ToolsEntryWorks" );
  }

  /**
   * This method will check the plugin card existence given the PluginCard object
   * 
   * @param id
   * @return exists
   */
  public boolean PluginCardExists( PluginCard plugin ) {
    //check sparkl plugin entry
    boolean exists = false;
    PluginCard existingPlugin = GetPluginCard( plugin );
    if ( plugin.equals( existingPlugin ) ) {
      exists = true;
    }
    return exists;
  }

  /**
   * This method will check the plugin existence given the PluginCard object
   * 
   * @param id
   * @return exists
   */
  public boolean PluginExists( PluginCard plugin ) {
    //check sparkl plugin entry
    boolean exists = false;
    String id = plugin.getId();
    String pluginId = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='pluginsListObj']//div[@title='" + id + "']" ) );
    if ( pluginId.equals( id ) ) {
      exists = true;
    }
    return exists;
  }

  /**
   * This method will get the plugin card information given the PluginCard object and return a new PluginCard object
   * 
   * @param id
   * @return exists
   */
  public PluginCard GetPluginCard( PluginCard plugin ) {
    //check sparkl plugin entry
    String id = plugin.getId();
    String pluginName = "";
    String pluginId = "";
    String pluginDescription = "";
    pluginName = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='pluginsListObj']//div[@title='" + id + "']/../div/span" ) );
    pluginId = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='pluginsListObj']//div[@title='" + id + "']" ) );
    this.elemHelper.MoveToElement( this.driver, By.xpath( "//div[@id='pluginsListObj']//div[@title='" + id + "']" ), 0, 0 );
    pluginDescription = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='pluginsListObj']//div[@title='" + id + "']/../..//div[@class='descriptionContainer']/div[@class='body']" ) );
    PluginCard shownPlugin = new PluginCard( pluginName, pluginId, pluginDescription );
    return shownPlugin;
  }

  /**
   * This method will check the existence and functionality of the refresh button
   *
   * It must be called from the Sparkl main dashboard
   */
  public void RefreshPage() {
    //check Refresh button
    WebElement refreshButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='refreshObj']/button" ) );
    assertNotNull( refreshButton );
    refreshButton.click();
  }

  /**
   * This method will select the sorting parameter given the option (name, id, version)
   *
   * It must be called from the Sparkl main dashboard
   * 
   * @param parameter
   */
  public void SortBy( String parameter ) {
    //check Sort list
    WebElement sortList = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='pluginsListObj']" ) );
    assertNotNull( sortList );
    WebElement expandSort = this.elemHelper.FindElement( this.driver, By.cssSelector( "a.chzn-single span" ) );
    assertNotNull( expandSort );
    expandSort.click();
    WebElement selectParameter = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@class='chzn-drop']//li[text() = '" + parameter + "']" ) );
    assertNotNull( selectParameter );
    selectParameter.click();
    String selected = this.elemHelper.WaitForElementPresentGetText( this.driver, By.cssSelector( "a.chzn-single span" ) );
    assertEquals( selected, parameter );
  }

  /**
   * This method will check the existence and functionality of the Sort list given parameter to sort by
   *
   * It must be called from the Sparkl main dashboard
   */
  public boolean CheckSortBy( String by ) {
    boolean result = false;
    List<WebElement> listElements = this.driver.findElements( By.cssSelector( "div.sparklPluginCardContainer" ) );
    log.info( listElements.size() );
    String searchXpath = "";
    if ( by.contains( "name" ) ) {
      searchXpath = "div[@class='nameContainer']/span";
    } else if ( by.contains( "id" ) ) {
      searchXpath = "div[@class='id']";
    }
    List<String> elements = new ArrayList<String>();
    for ( int i = 1; i < listElements.size() + 1; i++ ) {
      elements.add( this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@class='sparklPluginCardContainer'][" + i + "]//" + searchXpath ) ).toLowerCase() );
    }
    Object[] sortedElements = elements.toArray();
    Object[] unsortedElements = elements.toArray();
    Arrays.sort( sortedElements );
    result = Arrays.equals( unsortedElements, sortedElements );
    return result;
  }

  /**
   * This method will check the existence and functionality of the Create Button
   *
   * It must be called from the Sparkl main dashboard
   */
  public void CheckCreateButton() {
    WebElement createContainer = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='pluginsListObj']/div//div[@class='optionsContainer']" ) );
    assertNotNull( createContainer );
    this.elemHelper.MoveToElement( this.driver, By.xpath( "//div[@id='pluginsListObj']/div//div[@class='optionsContainer']" ), 0, 0 );
    WebElement createButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='pluginsListObj']/div//div[@class='optionsContainer']//span" ) );
    assertNotNull( createButton );
    createButton.click();
    WebElement newPluginContainer = this.elemHelper.FindElement( this.driver, By.id( "newPluginIdFormObj" ) );
    assertNotNull( newPluginContainer );

  }

  /**
   * This method will open a plugin in edit mode given the PluginCard object
   *
   * It must be called from the Sparkl main dashboard
   * 
   * @param id
   */
  public void GoToEditPage( PluginCard plugin ) {
    String name = plugin.getName();
    WebElement pluginCard = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='pluginsListObj']//span[@title='" + name + "']" ) );
    assertNotNull( pluginCard );
    String pluginId = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='pluginsListObj']//span[@title='" + name + "']" ) );
    assertEquals( name, pluginId );
    WebElement editButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='pluginsListObj']//span[@title='" + name + "']/../../../..//div[@id='detailsAction']" ) );
    assertNotNull( editButton );
    editButton.click();
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 10 );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 10 );
    WebElement pluginTitle = this.elemHelper.FindElement( this.driver, By.cssSelector( "div.pluginId" ) );
    assertNotNull( pluginTitle );
  }

  /**
   * This method will close the plugin edit mode
   *
   * It must be called from the edit page of a plugin
   * 
   */
  public void CloseEditPage() {
    WebElement closeButton = this.elemHelper.FindElement( this.driver, By.cssSelector( "div.closeButton" ) );
    assertNotNull( closeButton );
    closeButton.click();
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 10 );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 10 );
    //Assert Sparkl page is shown
    WebElement sparklLogo = this.elemHelper.FindElement( this.driver, By.cssSelector( "a.sparklLogo" ) );
    assertNotNull( sparklLogo );
  }

  /**
   * This method will click the delete button of a plugin given its id
   *
   * It must be called from Sparkl main page
   * 
   * @param id
   */
  private void ClickDelete( String id ) {
    WebElement deleteButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='pluginsListObj']//span[@title='" + id + "']/../../../..//div[@id='deleteAction']" ) );
    assertNotNull( deleteButton );
    deleteButton.click();
    WebElement confirmationDialog = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@aria-describedby='dialogGrabComponentObj']" ) );
    assertNotNull( confirmationDialog );

  }

  /**
   * This method will fully delete a plugin given the PluginCard object and return the confirmation
   * message received by the user
   *
   * It must be called from Sparkl main page
   * 
   * @param id
   * @return confirmationMessage
   */
  public String DeletePlugin( PluginCard plugin ) {
    String name = plugin.getName();
    ClickDelete( name );
    String confirmationMessage = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='dialogGrabComponentObj']/span" ) );
    WebElement confirmDelete = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@aria-describedby='dialogGrabComponentObj']//button[@id='deletePluginButtonOK']" ) );
    assertNotNull( confirmDelete );
    confirmDelete.click();
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 60 );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 60 );
    //Assert Sparkl page is shown
    WebElement sparklLogo = this.elemHelper.FindElement( this.driver, By.cssSelector( "a.sparklLogo" ) );
    assertNotNull( sparklLogo );
    return confirmationMessage;
  }

  /**
   * This method will cancel the deletion of a plugin. It returns the confirmation message shown when clicking delete
   *
   * It must be called from Sparkl main page
   * 
   * @param id
   * @return message
   */
  public String DeleteCancelPlugin( PluginCard plugin ) {
    String name = plugin.getName();
    ClickDelete( name );
    String message = "";
    message = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@aria-describedby='dialogGrabComponentObj']/div[@id='dialogGrabComponentObj']/span" ) );
    WebElement cancelDelete = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@aria-describedby='dialogGrabComponentObj']//button[@id='deletePluginButtonCancel']" ) );
    assertNotNull( cancelDelete );
    cancelDelete.click();
    return message;
  }

  /**
   * This method will run a plugin and return the url of the opened dashboard
   *
   * It must be called from Sparkl main page
   * 
   * @param id
   * @return url
   */
  public String RunPlugin( PluginCard plugin ) {
    String name = plugin.getName();
    WebElement runButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='pluginsListObj']//span[@title='" + name + "']/../../../..//div[@id='runAppAction']" ) );
    assertNotNull( runButton );
    runButton.click();
    String parentHandle = this.elemHelper.SelectNewWindow( this.driver );
    String url = this.driver.getCurrentUrl();
    this.elemHelper.SelectParentWindow( this.driver, parentHandle );
    return url;
  }

  /**
   * This method will get the plugin information currently displayed and assign it to a PluginInfo object 
   *
   * It must be called from the About page of a plugin
   * 
   * @return PluginInfo
   */
  public PluginInfo GetPluginInfo() {
    String name = this.elemHelper.GetAttribute( this.driver, By.xpath( "//div[@id='nameContainer']//input" ), "value" );
    String date = this.elemHelper.GetAttribute( this.driver, By.xpath( "//div[@id='dateContainer']//input" ), "value" );
    String version = this.elemHelper.GetAttribute( this.driver, By.xpath( "//div[@id='versionContainer']//input" ), "value" );
    String description = this.elemHelper.GetAttribute( this.driver, By.xpath( "//div[@id='descriptionContainer']//input" ), "value" );
    String authorName = this.elemHelper.GetAttribute( this.driver, By.xpath( "//div[@id='authorNameContainer']//input" ), "value" );
    String authorEmail = this.elemHelper.GetAttribute( this.driver, By.xpath( "//div[@id='authorEmailContainer']//input" ), "value" );
    String authorCompany = this.elemHelper.GetAttribute( this.driver, By.xpath( "//div[@id='authorCompanyContainer']//input" ), "value" );
    String authorCompanyUrl = this.elemHelper.GetAttribute( this.driver, By.xpath( "//div[@id='authorCompanyUrlContainer']//input" ), "value" );
    PluginInfo pluginInfo = new PluginInfo( name, date, version, description, authorName, authorEmail, authorCompany, authorCompanyUrl );
    return pluginInfo;
  }

  /**
   * This method will enter the plugin info into the respective fields given a PluginInfo object, it will return a new pluginInfo object in
   * case any of the properties of the object need to be altered 
   *
   * It must be called from the About page of a plugin
   * 
   * @param plugin
   * @return pluginInfo
   */
  public PluginInfo EnterPluginInfo( PluginInfo plugin ) {
    //filling empty strings to avoid issue where we're unable to save emptying an info field
    if ( plugin.getName() == "" ) {
      plugin.setName( "notEmpty" );
    }
    if ( plugin.getDate() == "" ) {
      plugin.setDate( "notEmpty" );
    }
    if ( plugin.getVersion() == "" ) {
      plugin.setVersion( "notEmpty" );
    }
    if ( plugin.getDescription() == "" ) {
      plugin.setDescription( "notEmpty" );
    }
    if ( plugin.getAuthorName() == "" ) {
      plugin.setAuthorName( "notEmpty" );
    }
    if ( plugin.getAuthorEmail() == "" ) {
      plugin.setAuthorEmail( "notEmpty" );
    }
    if ( plugin.getAuthorCompany() == "" ) {
      plugin.setAuthorCompany( "notEmpty" );
    }
    if ( plugin.getAuthorCompanyUrl() == "" ) {
      plugin.setAuthorCompanyUrl( "notEmpty" );
    }
    this.elemHelper.ClearAndSendKeys( this.driver, By.xpath( "//div[@id='nameContainer']//input" ), plugin.getName() );
    this.elemHelper.ClearAndSendKeys( this.driver, By.xpath( "//div[@id='dateContainer']//input" ), plugin.getDate() );
    this.elemHelper.ClearAndSendKeys( this.driver, By.xpath( "//div[@id='versionContainer']//input" ), plugin.getVersion() );
    this.elemHelper.ClearAndSendKeys( this.driver, By.xpath( "//div[@id='descriptionContainer']//input" ), plugin.getDescription() );
    this.elemHelper.ClearAndSendKeys( this.driver, By.xpath( "//div[@id='authorNameContainer']//input" ), plugin.getAuthorName() );
    this.elemHelper.ClearAndSendKeys( this.driver, By.xpath( "//div[@id='authorEmailContainer']//input" ), plugin.getAuthorEmail() );
    this.elemHelper.ClearAndSendKeys( this.driver, By.xpath( "//div[@id='authorCompanyContainer']//input" ), plugin.getAuthorCompany() );
    this.elemHelper.ClearAndSendKeys( this.driver, By.xpath( "//div[@id='authorCompanyUrlContainer']//input" ), plugin.getAuthorCompanyUrl() );
    return plugin;
  }

  /**
   * This method will save currently present plugin info. 
   *
   * It must be called from the About page of a plugin
   * 
   */
  public void SavePluginInfo() {
    this.elemHelper.Click( this.driver, By.id( "mainRow" ) );
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='submitChangesButtonObj']/butt" ), 5 );
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='submitChangesButtonObj']/button" ) );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@id='submitChangesButtonObj']/button" ) );
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 30 );
    this.elemHelper.WaitForElementNotPresent( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 30 );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 30 );
  }

  /**
   * This method will check that all elements shown are of the selected type (endpoint or dashboard)
   *
   * It must be called from the Elements page of a pluginwith the filter already set to the mentioned type
   * 
   * It receives the desired type
   */
  public boolean AssertElementsFiltered( String type ) {
    String convertedType = "";
    if ( type.equals( "dashboard" ) ) {
      convertedType = "Dashboard";
    } else if ( type.equals( "endpoint" ) ) {
      convertedType = "Kettle";
    }
    List<WebElement> listElements = this.driver.findElements( By.xpath( "//table[@id='elementsTableObjTable']/tbody/tr" ) );
    boolean isFiltered = true;
    for ( int i = 1; i < listElements.size() + 1; i++ ) {
      String actualType = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody/tr[" + i + "]/td[2]" ) );
      log.info( convertedType );
      if ( !convertedType.equals( actualType ) ) {
        log.info( actualType );
        isFiltered = false;
      }
    }
    return isFiltered;
  }

  /**
   * This method will open a dashboard in view mode given the PluginElement object
   * and returns title of opened dashboard
   * 
   * It will return the name part of the url
   *
   * It must be called from the Elements page of a plugin
   * 
   * @param plugin
   * @return url
   */
  public String ViewDashboard( PluginElement plugin ) {
    String name = plugin.getName().toLowerCase();
    WebElement dashboardLine = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[text() = '" + name + "']" ) );
    assertNotNull( dashboardLine );
    this.elemHelper.MoveToElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[text() = '" + name + "']" ), 0, 0 );
    WebElement dashboardViewButton = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[text() = '" + name + "']/..//button[@class='viewDashboardButton']" ) );
    assertNotNull( dashboardViewButton );
    dashboardViewButton.click();
    String parentHandle = this.elemHelper.SelectNewWindow( this.driver );
    String url = this.driver.getCurrentUrl();
    url = url.substring( url.indexOf( "api", 0 ) + 4, url.length() );
    this.elemHelper.SelectParentWindow( this.driver, parentHandle );
    return url;
  }

  /**
   * This method will open a dashboard in edit mode given the PluginElement object
   * and return the title of the dashboard being edited in all lower case
   * 
   * It must be called from the Elements page of a plugin
   * 
   * @param plugin
   * @return title
   */
  public String EditDashboard( PluginElement plugin ) {
    String name = plugin.getName();
    WebElement dashboardLine = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[text() = '" + name + "']" ) );
    assertNotNull( dashboardLine );
    this.elemHelper.MoveToElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[text() = '" + name + "']" ), 0, 0 );
    WebElement dashboardEditButton = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[text() = '" + name + "']/..//button[@class='editDashboardButton']" ) );
    assertNotNull( dashboardEditButton );
    dashboardEditButton.click();
    String parentHandle = this.elemHelper.SelectNewWindow( this.driver );
    String title = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@class='cdfdd-title']" ) );
    title = title.toLowerCase();
    String nameLower = name.toLowerCase();
    this.elemHelper.SelectParentWindow( this.driver, parentHandle );
    return nameLower;
  }

  /**
   * This method will create an element
   *
   * It must be called from the Elements page of a plugin
   * 
   * It must be called with the PluginElement object
   * 
   * @param plugin
   */
  public void CreateElement( PluginElement plugin ) {
    //Load variables from object
    String name = plugin.getName();
    String type = plugin.getType();
    String template = plugin.getTemplate();
    boolean admin = plugin.getAdmin();

    //Check if element with that name exists
    if ( !IsElementPresent( plugin ) ) {

      //Click create button and test invalid naming
      WebElement createButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='openNewElementButtonObj']/button" ) );
      assertNotNull( createButton );
      createButton.click();
      TestNamingElement();

      //Enter name of Element
      WebElement nameInput = this.elemHelper.FindElement( this.driver, By.id( "render_newElementNameForm" ) );
      assertNotNull( nameInput );
      nameInput.sendKeys( name );

      //Select type/template/admin
      if ( type.equals( "dashboard" ) ) {
        WebElement selectType = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='newElementTypeSelectorObj']/div/div" ) );
        assertNotNull( selectType );
        selectType.click();
        WebElement dashboardSelect = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='newElementTypeSelectorObj']/div/div//li[contains(text(),'Dashboard')]" ) );
        assertNotNull( dashboardSelect );
        dashboardSelect.click();
        WebElement selectTemplate = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='newElementTemplateSelectorObj']/div/div" ) );
        assertNotNull( selectTemplate );
        selectTemplate.click();
        if ( template.equals( "clean" ) ) {
          WebElement cleanSelect = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='newElementTemplateSelectorObj']/div/div//li[contains(text(),'Clean Dashboard')]" ) );
          assertNotNull( cleanSelect );
          cleanSelect.click();
        } else {
          WebElement sparklSelect = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='newElementTemplateSelectorObj']/div/div//li[contains(text(),'Sparkl Default')]" ) );
          assertNotNull( sparklSelect );
          sparklSelect.click();
        }
      } else {
        WebElement selectType = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='newElementTypeSelectorObj']/div/div" ) );
        assertNotNull( selectType );
        selectType.click();
        WebElement kettleSelect = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='newElementTypeSelectorObj']/div/div//li[contains(text(),'Kettle Endpoint')]" ) );
        assertNotNull( kettleSelect );
        kettleSelect.click();
        WebElement selectTemplate = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='newElementTemplateSelectorObj']/div/div" ) );
        assertNotNull( selectTemplate );
        selectTemplate.click();
        if ( template.equals( "ktr" ) ) {
          WebElement ktrSelect = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='newElementTemplateSelectorObj']/div/div//li[contains(text(),'Clean Transformation')]" ) );
          assertNotNull( ktrSelect );
          ktrSelect.click();
        } else {
          WebElement kjbSelect = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='newElementTemplateSelectorObj']/div/div//li[contains(text(),'Clean Job')]" ) );
          assertNotNull( kjbSelect );
          kjbSelect.click();
        }
      }
      if ( admin ) {
        WebElement adminButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='newElementAdminButtonObj']/button/div" ) );
        assertNotNull( adminButton );
        adminButton.click();
      }
      WebElement createElement = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='newElementAddButtonObj']/button" ) );
      assertNotNull( createElement );
      createElement.click();
      this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 60 );
    } else {
      log.info( "An element with chosen name already exists. Choose another name" );
    }
  }

  /**
   * This method will return a boolean of whether an element is present given its name, type and admin values
   *
   * It must be called from the Elements page of a plugin
   * 
   * It must be called with the PluginElement object
   * 
   * @param PluginElement
   */
  public boolean IsElementPresent( PluginElement plugin ) {
    boolean exists = false;
    String name = plugin.getName().toLowerCase();
    String type = plugin.getType();
    boolean admin = plugin.getAdmin();
    log.info( name + type + admin );
    if ( !this.elemHelper.WaitForElementNotPresent( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[text() = '" + name + "']" ), 5 ) ) {
      WebElement elementLine = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[text() = '" + name + "']" ) );
      assertNotNull( elementLine );
      if ( type.equals( "dashboard" ) ) {
        String elementType = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[text() = '" + name + "']/../td[2]" ) );
        assertEquals( "Dashboard", elementType );
      } else {
        String elementType = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[text() = '" + name + "']/../td[2]" ) );
        assertEquals( "Kettle", elementType );
      }
      if ( admin ) {
        String adminOnly = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[text() = '" + name + "']/../td[3]" ) );
        assertEquals( "Admin Only", adminOnly );
      } else {
        String adminOnly = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[text() = '" + name + "']/../td[3]" ) );
        assertEquals( "All Users", adminOnly );
      }
      exists = true;
    }
    return exists;
  }

  /**
   * This method will test if invalid/reserved names are identified as such
   *
   * It must be called from the Elements page of a plugin with the Create New Element form open.
   * 
   */
  public void TestNamingElement() {
    WebElement nameInput = this.elemHelper.FindElement( this.driver, By.id( "render_newElementNameForm" ) );
    assertNotNull( nameInput );
    nameInput.sendKeys( " " );
    WebElement invalidNameBanner = this.elemHelper.FindElement( this.driver, By.id( "invalidLabel" ) );
    assertNotNull( invalidNameBanner );
    nameInput.clear();
    nameInput.sendKeys( "3" );
    invalidNameBanner = this.elemHelper.FindElement( this.driver, By.id( "invalidLabel" ) );
    assertNotNull( invalidNameBanner );
    nameInput.clear();
    nameInput.sendKeys( "aaa a" );
    invalidNameBanner = this.elemHelper.FindElement( this.driver, By.id( "invalidLabel" ) );
    assertNotNull( invalidNameBanner );
    nameInput.clear();
    nameInput.sendKeys( "+" );
    invalidNameBanner = this.elemHelper.FindElement( this.driver, By.id( "invalidLabel" ) );
    assertNotNull( invalidNameBanner );
    nameInput.clear();
  }

  /**
   * This method will delete and cancel deletion of an element given the PluginElement object
   * 
   * It will return the message sent to the user
   *
   * It must be called from the Elements page of a plugin
   * 
   * @param plugin
   * @return confirmationMessage
   */
  public String DeleteCancelElement( PluginElement plugin ) {
    String name = plugin.getName();
    WebElement elementLine = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[text() = '" + name + "']" ) );
    assertNotNull( elementLine );
    String type = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[text() = '" + name + "']/../td[2]" ) );
    this.elemHelper.MoveToElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[text() = '" + name + "']" ), 0, 0 );
    if ( type.equals( "Dashboard" ) ) {
      WebElement elementDeleteButton = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[text() = '" + name + "']/..//button[@class='deleteDashboardButton']" ) );
      assertNotNull( elementDeleteButton );
      elementDeleteButton.click();
    } else {
      WebElement elementDeleteButton = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[text() = '" + name + "']/..//button[@class='deleteEndpointButton']" ) );
      assertNotNull( elementDeleteButton );
      elementDeleteButton.click();
    }
    String confirmationMessage = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='dialogGrabComponentObj']/span" ) );
    assertEquals( confirmationMessage, "You are about to delete " + name + ". Please, press OK to continue..." );
    WebElement cancelButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='dialogGrabComponentObj']/..//button/span[contains(text(),'Cancel')]" ) );
    assertNotNull( cancelButton );
    cancelButton.click();
    return confirmationMessage;
  }

  /**
   * This method will delete an element given the PluginElement object. It will return the confirmation message sent to the user
   *
   * It must be called from the Elements page of a plugin
   * 
   * @param plugin
   * @return message
   */
  public String DeleteElement( PluginElement plugin ) {
    String name = plugin.getName();
    String confirmMessage = "";
    WebElement elementLine = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[text() = '" + name + "']" ) );
    if ( elementLine != null ) {
      String type = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[text() = '" + name + "']/../td[2]" ) );
      this.elemHelper.MoveToElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[text() = '" + name + "']" ), 0, 0 );
      if ( type.equals( "Dashboard" ) ) {
        WebElement elementDeleteButton = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[text() = '" + name + "']/..//button[@class='deleteDashboardButton']" ) );
        assertNotNull( elementDeleteButton );
        elementDeleteButton.click();
      } else {
        WebElement elementDeleteButton = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[text() = '" + name + "']/..//button[@class='deleteEndpointButton']" ) );
        assertNotNull( elementDeleteButton );
        elementDeleteButton.click();
      }
      confirmMessage = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='dialogGrabComponentObj']/span" ) );
      WebElement okButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='dialogGrabComponentObj']/..//button/span[contains(text(),'OK')]" ) );
      assertNotNull( okButton );
      okButton.click();
      this.elemHelper.FindElement( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 60 );
      this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 60 );
    } else {
      log.info( "The element " + name + " is not present" );
    }
    return confirmMessage;
  }

  /**
   * This method will run an endpoint given the PluginElement object
   * 
   * It will return the name part of the url
   *
   * It must be called from the Elements page of a plugin
   * 
   * @param plugin
   * @return url
   */
  public String RunEndpoint( PluginElement plugin ) {
    String name = plugin.getName();
    WebElement endpointLine = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[text() = '" + name + "']" ) );
    assertNotNull( endpointLine );
    this.elemHelper.MoveToElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[text() = '" + name + "']" ), 0, 0 );
    WebElement endpointRunButton = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[text() = '" + name + "']/..//button[@class='runEndpointButton']" ) );
    assertNotNull( endpointRunButton );
    endpointRunButton.click();
    String parentHandle = this.elemHelper.SelectNewWindow( this.driver );
    String url = this.driver.getCurrentUrl();
    url = url.substring( url.indexOf( "api", 0 ) + 4, url.length() );
    this.elemHelper.SelectParentWindow( this.driver, parentHandle );
    return url;
  }

  /**
   * This method will navigate to the Elements page.
   *
   * It must be called from the Edit page of a plugin
   */
  public void GoToElements() {
    WebElement elementsButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='tabMultiButtonObj']//button[contains(text(),'Elements')]" ) );
    assertNotNull( elementsButton );
    elementsButton.click();
    WebElement elementsTable = this.elemHelper.FindElement( this.driver, By.id( "elementsTableObjTable" ) );
    assertNotNull( elementsTable );
  }

  /**
   * This method will filter the elements given the type (dashboard or endpoint)
   * if any other type is provided this method will clik the "All" button
   *
   * It must be called from the Edit page of a plugin
   */
  public void FilterElements( String type ) {
    if ( type.equals( "endpoint" ) ) {
      WebElement endpointButton = this.elemHelper.FindElement( this.driver, By.xpath( "//button[contains(text( ),'Endpoints')]" ) );
      assertNotNull( endpointButton );
      endpointButton.click();
    } else if ( type.equals( "dashboard" ) ) {
      WebElement dashboardButton = this.elemHelper.FindElement( this.driver, By.xpath( "//button[contains(text( ),'Dashboards')]" ) );
      assertNotNull( dashboardButton );
      dashboardButton.click();
    } else {
      WebElement allButton = this.elemHelper.FindElement( this.driver, By.xpath( "//button[contains(text( ),'All')]" ) );
      assertNotNull( allButton );
      allButton.click();
    }
  }

  /**
   * This method will click the refresh button on the elements page and return true if the page
   * is blocked by the refreshing icon
   *
   * It must be called from the Edit page of a plugin
   */
  public boolean RefreshElements() {
    boolean refreshed = false;
    WebElement refreshButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='refreshObj']/button" ) );
    assertNotNull( refreshButton );
    refreshButton.click();
    WebElement refreshIcon = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 60 );
    if ( refreshIcon != null ) {
      refreshed = true;
    }
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 360 );
    return refreshed;
  }

  /**
   * This method will create a plugin given its name. It returns the confirmation message received by the user
   *
   * It must be called from the Sparkl main dashboard
   * 
   * @param name
   */
  public String CreatePlugin( String name ) {
    WebElement createContainer = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='pluginsListObj']/div//div[@class='optionsContainer']" ) );
    assertNotNull( createContainer );
    this.elemHelper.MoveToElement( this.driver, By.xpath( "//div[@id='pluginsListObj']/div//div[@class='optionsContainer']" ), 0, 0 );
    WebElement createButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='pluginsListObj']/div//div[@class='optionsContainer']//span" ) );
    assertNotNull( createButton );
    createButton.click();
    WebElement newPluginContainer = this.elemHelper.FindElement( this.driver, By.id( "newPluginIdFormObj" ) );
    assertNotNull( newPluginContainer );
    TestNamingPlugin();
    WebElement nameInput = this.elemHelper.FindElement( this.driver, By.id( "render_newPluginIdForm" ) );
    assertNotNull( nameInput );
    nameInput.sendKeys( name );
    String isNameValid = this.elemHelper.GetAttribute( this.driver, By.id( "newPluginIdFormObj" ), "class" );
    assertEquals( "newPluginContainer notEmpty", isNameValid );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@id='createNewPluginButtonObj']/button" ) );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 30 );
    String confirmationMessage = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='dialogGrabComponentObj']/span" ) );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@class='ui-dialog-buttonset']/button" ) );
    return confirmationMessage;
  }

  /**
   * This method will test if invalid/reserved names are identified as such
   *
   * It must be called from the Elements page of a plugin with the Create New Element form open.
   * 
   */
  public void TestNamingPlugin() {
    WebElement nameInput = this.elemHelper.FindElement( this.driver, By.id( "render_newPluginIdForm" ) );
    assertNotNull( nameInput );
    nameInput.sendKeys( " " );
    WebElement invalidNameBanner = this.elemHelper.FindElement( this.driver, By.cssSelector( "div.validityFeedback.invalidLabel" ) );
    assertNotNull( invalidNameBanner );
    nameInput.clear();
    nameInput.sendKeys( "3" );
    invalidNameBanner = this.elemHelper.FindElement( this.driver, By.cssSelector( "div.validityFeedback.invalidLabel" ) );
    assertNotNull( invalidNameBanner );
    nameInput.clear();
    nameInput.sendKeys( "aaa a" );
    invalidNameBanner = this.elemHelper.FindElement( this.driver, By.cssSelector( "div.validityFeedback.invalidLabel" ) );
    assertNotNull( invalidNameBanner );
    nameInput.clear();
    nameInput.sendKeys( "+" );
    invalidNameBanner = this.elemHelper.FindElement( this.driver, By.cssSelector( "div.validityFeedback.invalidLabel" ) );
    assertNotNull( invalidNameBanner );
    nameInput.clear();
    nameInput.sendKeys( "sparkl" );
    WebElement inUseNameBanner = this.elemHelper.FindElement( this.driver, By.cssSelector( "div.validityFeedback.inUseLabel" ) );
    assertNotNull( inUseNameBanner );
    nameInput.clear();
  }
}
