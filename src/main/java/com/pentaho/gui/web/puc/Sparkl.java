package com.pentaho.gui.web.puc;

import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNull;
import static org.testng.AssertJUnit.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pentaho.ctools.utils.ElementHelper;
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
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 60 );
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
    WebElement pluginEntry = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='toolsmenu']//td[contains(text(),'" + name + "')]" ) );
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
    WebElement pluginEntry = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='toolsmenu']//td[contains(text(),'" + name + "')]" ) );
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
      WebElement pluginEntry = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='toolsmenu']//td[contains(text(),'" + name + "')]" ) );
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
   * This method will check the plugin card existence given plugin's id
   * 
   * @param id
   * @return exists
   */
  public boolean PluginCardExists( String id ) {
    //check sparkl plugin entry
    boolean exists = false;
    WebElement pluginCard = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='pluginsListObj']//span[@title='" + id + "']" ) );
    assertNotNull( pluginCard );
    exists = true;
    return exists;
  }

  /**
   * This method will check the plugin card existence given plugin's id and return plugin ID
   * 
   * @param id
   * @return ID
   */
  public String PluginCardId( String id ) {
    //check sparkl plugin entry
    PluginCardExists( id );
    String pluginId = "";
    pluginId = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='pluginsListObj']//span[@title='" + id + "']" ) );
    return pluginId;
  }

  /**
   * This method will check the plugin card existence given plugin's id and return plugin Title
   * 
   * @param id
   * @return Title
   */
  public String PluginCardTitle( String id ) {
    //check sparkl plugin entry
    PluginCardExists( id );
    String pluginTitle = "";
    pluginTitle = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='pluginsListObj']//span[@title='" + id + "']/../../div[@class='id']" ) );
    return pluginTitle;
  }

  /**
   * This method will check the plugin card existence given plugin's id and return plugin Description
   * 
   * @param id
   * @return Description
   */
  public String PluginCardDescription( String id ) {
    //check sparkl plugin entry
    PluginCardExists( id );
    this.elemHelper.MoveToElement( this.driver, By.xpath( "//div[@id='pluginsListObj']//span[@title='" + id + "']" ) );
    String pluginDescription = "";
    pluginDescription = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='pluginsListObj']//span[@title='" + id + "']/../../..//div[@class='descriptionContainer']/div[@class='body']" ) );
    return pluginDescription;
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
    WebElement selectParameter = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@class='chzn-drop']//li[contains(text(),'" + parameter + "')]" ) );
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
    if ( by.contains( "name" ) ) {
      List<String> elements = new ArrayList<String>();
      for ( int i = 1; i < listElements.size(); i++ ) {
        elements.add( this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@class='sparklPluginCardContainer'][i]//div[@class='nameContainer']/span" ) ) );
      }
      Object[] sortedElements = elements.toArray();
      Object[] unsortedElements = elements.toArray();
      Arrays.sort( sortedElements );
      assertTrue( Arrays.equals( unsortedElements, sortedElements ) );
      result = Arrays.equals( unsortedElements, sortedElements );
    } else if ( by.contains( "id" ) ) {
      List<String> elements = new ArrayList<String>();
      for ( int i = 1; i < listElements.size(); i++ ) {
        elements.add( this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@class='sparklPluginCardContainer'][i]//div[@class='id']" ) ) );
      }
      Object[] sortedElements = elements.toArray();
      Object[] unsortedElements = elements.toArray();
      Arrays.sort( sortedElements );
      result = Arrays.equals( unsortedElements, sortedElements );
    }
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
   * This method will open a plugin in edit mode given its id
   *
   * It must be called from the Sparkl main dashboard
   * 
   * @param id
   */
  public void GoToEditPage( String id ) {
    WebElement pluginCard = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='pluginsListObj']//span[@title='" + id + "']" ) );
    assertNotNull( pluginCard );
    String pluginId = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='pluginsListObj']//span[@title='" + id + "']" ) );
    assertEquals( id, pluginId );
    WebElement editButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='pluginsListObj']//span[@title='" + id + "']/../../../..//div[@id='detailsAction']" ) );
    assertNotNull( editButton );
    editButton.click();
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 60 );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 60 );
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
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 60 );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 60 );
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
  public void ClickDelete( String id ) {
    WebElement deleteButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='pluginsListObj']//span[@title='" + id + "']/../../../..//div[@id='deleteAction']" ) );
    assertNotNull( deleteButton );
    deleteButton.click();
    WebElement confirmationDialog = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@aria-describedby='dialogGrabComponentObj']" ) );
    assertNotNull( confirmationDialog );

  }

  /**
   * This method will fully delete a plugin
   *
   * It must be called from Sparkl main page
   * 
   * @param id
   */
  public void DeletePlugin( String id ) {
    ClickDelete( id );
    WebElement confirmDelete = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@aria-describedby='dialogGrabComponentObj']//button[@id='deletePluginButtonOK']" ) );
    assertNotNull( confirmDelete );
    confirmDelete.click();
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 60 );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 60 );
    //Assert Sparkl page is shown
    WebElement sparklLogo = this.elemHelper.FindElement( this.driver, By.cssSelector( "a.sparklLogo" ) );
    assertNotNull( sparklLogo );

  }

  /**
   * This method will cancel the deletion of a plugin. It returns the confirmation message shown when clicking delete
   *
   * It must be called from Sparkl main page
   * 
   * @param id
   * @return message
   */
  public String DeleteCancel( String id ) {
    ClickDelete( id );
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
  public String RunPlugin( String id ) {
    WebElement runButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='pluginsListObj']//span[@title='" + id + "']/../../../..//div[@id='runAppAction']" ) );
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
    //TODO
    String name = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='nameContainer']//input" ) );
    String date = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='dateContainer']//input" ) );
    String version = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='versionContainer']//input" ) );
    String description = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='descriptionContainer']//input" ) );
    String authorName = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='authorNameContainer']//input" ) );
    String authorEmail = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='authorEmailContainer']//input" ) );
    String authorCompany = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='authorCompanyContainer']//input" ) );
    String authorCompanyUrl = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='authorCompanyUrlContainer']//input" ) );
    PluginInfo pluginInfo = new PluginInfo( name, date, version, description, authorName, authorEmail, authorCompany, authorCompanyUrl );
    return pluginInfo;
  }

  /**
   * This method will check the layout and functionality of the About edit page
   *
   * It must be called from the Edit page of a plugin
   */
  public void CheckEditAbout() {
    String testString = "testtesttesttest";
    WebElement aboutButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='tabMultiButtonObj']//button[contains(text(),'About')]" ) );
    assertNotNull( aboutButton );
    aboutButton.click();
    String name = EditAboutField( "nameContainer", testString );
    String date = EditAboutField( "dateContainer", testString );
    String version = EditAboutField( "versionContainer", testString );
    String description = EditAboutField( "descriptionContainer", testString );
    String authorName = EditAboutField( "authorNameContainer", testString );
    String authorEmail = EditAboutField( "authorEmailContainer", testString );
    String authorCompany = EditAboutField( "authorCompanyContainer", testString );
    String authorCompanyUrl = EditAboutField( "authorCompanyUrlContainer", testString );
    WebElement submitButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='submitChangesButtonObj']//button" ) );
    assertNotNull( submitButton );
    submitButton.click();
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 60 );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 360 );
    this.driver.navigate().refresh();
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 60 );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 360 );
    assertEquals( testString, EditAboutField( "nameContainer", name ) );
    assertEquals( testString, EditAboutField( "dateContainer", date ) );
    assertEquals( testString, EditAboutField( "versionContainer", version ) );
    assertEquals( testString, EditAboutField( "descriptionContainer", description ) );
    assertEquals( testString, EditAboutField( "authorNameContainer", authorName ) );
    assertEquals( testString, EditAboutField( "authorEmailContainer", authorEmail ) );
    assertEquals( testString, EditAboutField( "authorCompanyContainer", authorCompany ) );
    assertEquals( testString, EditAboutField( "authorCompanyUrlContainer", authorCompanyUrl ) );
    submitButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='submitChangesButtonObj']//button" ) );
    assertNotNull( submitButton );
    submitButton.click();
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 60 );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 60 );

  }

  /**
   * This method will check that all elements shown are of the selected type
   *
   * It must be called from the Elements page of a pluginwith the filter already set to the mentioned type
   * 
   * It receives the desired type
   */
  public void AssertElementsFiltered( String type ) {
    List<WebElement> listElements = this.driver.findElements( By.xpath( "//table[@id='elementsTableObjTable']/tbody/tr" ) );
    for ( int i = 1; i < listElements.size() + 1; i++ ) {
      String actualType = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody/tr[" + i + "]/td[2]" ) );
      assertEquals( type, actualType );
    }
  }

  /**
   * This method will open a dashboard in view mode and assert title matches
   *
   * It must be called from the Elements page of a plugin
   * 
   * @param name
   */
  public void ViewDashboard( String name ) {
    WebElement dashboardLine = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[contains(text(),'" + name + "')]" ) );
    assertNotNull( dashboardLine );
    this.elemHelper.MoveToElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[contains(text(),'" + name + "')]" ) );
    WebElement dashboardViewButton = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[contains(text(),'" + name + "')]/..//button[@class='viewDashboardButton']" ) );
    assertNotNull( dashboardViewButton );
    dashboardViewButton.click();
    String parentHandle = this.elemHelper.SelectNewWindow( this.driver );
    String url = this.driver.getCurrentUrl();
    url = url.substring( url.indexOf( "api", 0 ) + 4, url.length() );
    log.info( url );
    assertEquals( name, url );
    this.elemHelper.SelectParentWindow( this.driver, parentHandle );

  }

  /**
   * This method will open a dashboard in edit mode and assert title matches
   *
   * It must be called from the Elements page of a plugin
   * 
   * @param name
   */
  public void EditDashboard( String name ) {
    WebElement dashboardLine = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[contains(text(),'" + name + "')]" ) );
    assertNotNull( dashboardLine );
    this.elemHelper.MoveToElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[contains(text(),'" + name + "')]" ) );
    WebElement dashboardEditButton = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[contains(text(),'" + name + "')]/..//button[@class='editDashboardButton']" ) );
    assertNotNull( dashboardEditButton );
    dashboardEditButton.click();
    String parentHandle = this.elemHelper.SelectNewWindow( this.driver );
    String title = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@class='cdfdd-title']" ) );
    title = title.toLowerCase();
    String nameLower = name.toLowerCase();
    log.info( title );
    assertEquals( nameLower, title );
    this.elemHelper.SelectParentWindow( this.driver, parentHandle );
  }

  /**
   * This method will create an element
   *
   * It must be called from the Elements page of a plugin
   * 
   * It must be called with the name for the element, which element it is (dashboard or endpoint)which type it should be (clean or sparkl/kjb or ktr)
   * and if it is only for admin (yes or no)
   * 
   * @param name
   * @param element
   * @param type
   * @param admin
   */
  public void CreateElement( String name, String element, String type, String admin ) {
    //TODO
    WebElement createButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='openNewElementButtonObj']/button" ) );
    assertNotNull( createButton );
    createButton.click();
    TestNamingElement();
    WebElement nameInput = this.elemHelper.FindElement( this.driver, By.id( "render_newElementNameForm" ) );
    assertNotNull( nameInput );
    nameInput.sendKeys( name );
    if ( element.equals( "dashboard" ) ) {
      WebElement selectType = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='newElementTypeSelectorObj']/div/div" ) );
      assertNotNull( selectType );
      selectType.click();
      WebElement dashboardSelect = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='newElementTypeSelectorObj']/div/div//li[contains(text(),'Dashboard')]" ) );
      assertNotNull( dashboardSelect );
      dashboardSelect.click();
      WebElement selectTemplate = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='newElementTemplateSelectorObj']/div/div" ) );
      assertNotNull( selectTemplate );
      selectTemplate.click();
      if ( type.equals( "clean" ) ) {
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
      if ( type.equals( "ktr" ) ) {
        WebElement ktrSelect = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='newElementTemplateSelectorObj']/div/div//li[contains(text(),'Clean Transformation')]" ) );
        assertNotNull( ktrSelect );
        ktrSelect.click();
      } else {
        WebElement kjbSelect = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='newElementTemplateSelectorObj']/div/div//li[contains(text(),'Clean Job')]" ) );
        assertNotNull( kjbSelect );
        kjbSelect.click();
      }
    }
    if ( admin.equals( "yes" ) ) {
      WebElement adminButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='newElementAdminButtonObj']/button/div" ) );
      assertNotNull( adminButton );
      adminButton.click();
    }
    WebElement createElement = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='newElementAddButtonObj']/button" ) );
    assertNotNull( createElement );
    createElement.click();

  }

  /**
   * This method will create a dashboard and assert it is present/viewable/editable
   *
   * It must be called from the Elements page of a plugin
   * 
   * It must be called with the name for the dashboard, which type it should be (clean or sparkl) and if it is only for admin (yes or no)
   * 
   * @param name
   * @param type
   * @param admin
   */
  public void CreateDashboard( String name, String type, String admin ) {
    CreateElement( name, "dashboard", type, admin );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 60 );
    WebElement dashboardLine = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[contains(text(),'" + name + "')]" ) );
    assertNotNull( dashboardLine );
    if ( admin.equals( "yes" ) ) {
      String adminOnly = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[contains(text(),'" + name + "')]/../td[3]" ) );
      assertEquals( "Admin Only", adminOnly );
    } else {
      String adminOnly = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[contains(text(),'" + name + "')]/../td[3]" ) );
      assertEquals( "All Users", adminOnly );
    }
    ViewDashboard( name );
    EditDashboard( name );
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
    String usedName = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td" ) );
    nameInput.sendKeys( usedName );
    WebElement inUseNameBanner = this.elemHelper.FindElement( this.driver, By.id( "inUseLabel" ) );
    assertNotNull( inUseNameBanner );
    nameInput.clear();
  }

  /**
   * This method will delete an element and assert it is no longer present
   *
   * It must be called from the Elements page of a plugin
   * 
   * @param name
   */
  public void DeleteElement( String name ) {
    WebElement elementLine = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[contains(text(),'" + name + "')]" ) );
    assertNotNull( elementLine );
    String type = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[contains(text(),'" + name + "')]/../td[2]" ) );
    this.elemHelper.MoveToElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[contains(text(),'" + name + "')]" ) );
    if ( type.equals( "Dashboard" ) ) {
      WebElement elementDeleteButton = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[contains(text(),'" + name + "')]/..//button[@class='deleteDashboardButton']" ) );
      assertNotNull( elementDeleteButton );
      elementDeleteButton.click();
    } else {
      WebElement elementDeleteButton = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[contains(text(),'" + name + "')]/..//button[@class='deleteEndpointButton']" ) );
      assertNotNull( elementDeleteButton );
      elementDeleteButton.click();
    }
    String confirmationMessage = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='dialogGrabComponentObj']/span" ) );
    assertEquals( confirmationMessage, "You are about to delete " + name + ". Please, press OK to continue..." );
    WebElement okButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='dialogGrabComponentObj']/..//button/span[contains(text(),'OK')]" ) );
    assertNotNull( okButton );
    okButton.click();
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 60 );
    elementLine = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[contains(text(),'" + name + "')]" ), 5 );
    assertNull( elementLine );
  }

  /**
   * This method will run an endpoint
   *
   * It must be called from the Elements page of a plugin
   * 
   * @param name
   */
  public void RunEndpoint( String name ) {
    WebElement endpointLine = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[contains(text(),'" + name + "')]" ) );
    assertNotNull( endpointLine );
    this.elemHelper.MoveToElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[contains(text(),'" + name + "')]" ) );
    WebElement endpointRunButton = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[contains(text(),'" + name + "')]/..//button[@class='runEndpointButton']" ) );
    assertNotNull( endpointRunButton );
    endpointRunButton.click();
    String parentHandle = this.elemHelper.SelectNewWindow( this.driver );
    String url = this.driver.getCurrentUrl();
    url = url.substring( url.indexOf( "api", 0 ) + 4, url.length() );
    log.info( url );
    assertEquals( name, url );
    this.elemHelper.SelectParentWindow( this.driver, parentHandle );
  }

  /**
   * This method will create an endpoint and assert it is present/viewable/editable
   *
   * It must be called from the Elements page of a plugin
   * 
   * @param name
   * @param type
   * @param admin
   */
  public void CreateEndpoint( String name, String type, String admin ) {
    CreateElement( name, "endpoint", type, admin );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 60 );
    WebElement endpointLine = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[contains(text(),'" + name + "')]" ) );
    assertNotNull( endpointLine );
    if ( admin.equals( "yes" ) ) {
      String adminOnly = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[contains(text(),'" + name + "')]/../td[3]" ) );
      assertEquals( "Admin Only", adminOnly );
    } else {
      String adminOnly = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[contains(text(),'" + name + "')]/../td[3]" ) );
      assertEquals( "All Users", adminOnly );
    }
    RunEndpoint( name );
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
   * This method will check the layout and functionality of the Element edit page
   *
   * It must be called from the Edit page of a plugin
   */
  public void CheckEditElement() {
    GoToElements();
    if ( this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody/tr" ) ) != null ) {
      WebElement endpointButton = this.elemHelper.FindElement( this.driver, By.xpath( "//button[contains(text( ),'Endpoints')]" ) );
      assertNotNull( endpointButton );
      endpointButton.click();
      AssertElementsFiltered( "Kettle" );
      CreateEndpoint( "test", "ktr", "yes" );
      CreateEndpoint( "newendpoint", "kjb", "no" );
      DeleteElement( "test" );
      DeleteElement( "newendpoint" );
      WebElement dashboardButton = this.elemHelper.FindElement( this.driver, By.xpath( "//button[contains(text( ),'Dashboards')]" ) );
      assertNotNull( dashboardButton );
      dashboardButton.click();
      AssertElementsFiltered( "Dashboard" );
      String name = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td" ) );
      ViewDashboard( name );
      EditDashboard( name );
      CreateDashboard( "test", "clean", "yes" );
      CreateDashboard( "newdash", "sparkl", "no" );
      DeleteElement( "test" );
      DeleteElement( "newdash" );
    }
  }

  /**
   * This method will edit a field value on the About edit page given the id of the field and the new value
   * 
   * It returns the previous value of the field
   *
   * It must be called from the Edit page of a plugin
   */
  public String EditAboutField( String id, String newValue ) {
    WebElement pluginField = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='" + id + "']//input" ) );
    assertNotNull( pluginField );
    String previousValue = pluginField.getAttribute( "value" );
    pluginField.clear();
    pluginField.sendKeys( newValue );
    return previousValue;
  }

}
