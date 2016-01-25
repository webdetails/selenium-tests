package com.pentaho.gui.web.puc;

import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertEquals;
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
import com.pentaho.ctools.utils.HttpUtils;

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
    HttpUtils.GetResponseCode( sparklLogo.getAttribute( "href" ) );

    log.info( "Exit: OpenSparkl" );
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
   */
  public void CheckPluginCard( String id ) {
    //check sparkl plugin entry
    WebElement pluginCard = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='pluginsListObj']//div[@title='" + id + "']" ) );
    assertNotNull( pluginCard );
    String pluginId = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='pluginsListObj']//div[@title='" + id + "']" ) );
    assertEquals( id, pluginId );
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
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 60 );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 360 );
  }

  /**
   * This method will check the existence and functionality of the Sort list
   *
   * It must be called from the Sparkl main dashboard
   */
  public void CheckSorting() {
    //check Sort list
    WebElement sortList = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='pluginsListObj']" ) );
    assertNotNull( sortList );
    String selected = this.elemHelper.WaitForElementPresentGetText( this.driver, By.cssSelector( "a.chzn-single span" ) );
    assertEquals( selected, "id" );
    CheckSortBy( selected );
    WebElement expandSort = this.elemHelper.FindElement( this.driver, By.cssSelector( "a.chzn-single span" ) );
    assertNotNull( expandSort );
    expandSort.click();
    WebElement selectName = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@class='chzn-drop']//li[contains(text(),'name')]" ) );
    assertNotNull( selectName );
    selectName.click();
    selected = this.elemHelper.WaitForElementPresentGetText( this.driver, By.cssSelector( "a.chzn-single span" ) );
    assertEquals( selected, "name" );
    CheckSortBy( selected );
  }

  /**
   * This method will check the existence and functionality of the Sort list
   *
   * It must be called from the Sparkl main dashboard
   */
  public void CheckSortBy( String by ) {

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
    } else if ( by.contains( "id" ) ) {
      List<String> elements = new ArrayList<String>();
      for ( int i = 1; i < listElements.size(); i++ ) {
        elements.add( this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@class='sparklPluginCardContainer'][i]//div[@class='id']" ) ) );
      }
      Object[] sortedElements = elements.toArray();
      Object[] unsortedElements = elements.toArray();
      Arrays.sort( sortedElements );
      assertTrue( Arrays.equals( unsortedElements, sortedElements ) );
    }

  }

  /**
   * This method will check the existence and functionality of the Sort list
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
   * This method will check the layout of the Sparkl main dashboard. It does so by following these steps:
   * 1 - Open Sparkl main dashboard and check the existence of and strings on Sparkl plugin entry
   * 2 - Check presence and functionality of refresh button
   * 3 - Check presence and functionality of Sort by drop down list 
   * 4 - Check presence and functionality of Create button
   */
  public void CheckSparklLayout() {
    OpenSparkl();
    //check sparkl plugin entry
    CheckPluginCard( "sparkl" );
    RefreshPage();
    CheckSorting();
    CheckCreateButton();
  }

  /**
   * This method will open a plugin in edit mode given its id
   *
   * It must be called from the Sparkl main dashboard
   * 
   * @param id
   */
  public void EditPlugin( String id ) {
    WebElement pluginCard = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='pluginsListObj']//div[@title='" + id + "']" ) );
    assertNotNull( pluginCard );
    String pluginId = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='pluginsListObj']//div[@title='" + id + "']" ) );
    assertEquals( id, pluginId );
    WebElement editButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='pluginsListObj']//div[@title='" + id + "']/../../..//div[@id='detailsAction']" ) );
    assertNotNull( editButton );
    editButton.click();
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 60 );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 360 );
    WebElement pluginTitle = this.elemHelper.FindElement( this.driver, By.cssSelector( "div.pluginId" ) );
    assertNotNull( pluginTitle );
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
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ), 360 );

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
    WebElement dashboardViewButton = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody//td[contains(text(),'" + name + "'/..//button[@class='viewDashboardButton']" ) );
    assertNotNull( dashboardViewButton );
    dashboardViewButton.click();
    String parentHandle = this.elemHelper.SelectNewWindow( this.driver );
    this.elemHelper.SelectParentWindow( this.driver, parentHandle );
    //TODO

  }

  /**
   * This method will open a dashboard in edit mode and assert title matches
   *
   * It must be called from the Elements page of a plugin
   * 
   * @param name
   */
  public void EditDashboard( String name ) {
    //TODO
  }

  /**
   * This method will delete a dashboard and assert it is no longer present
   *
   * It must be called from the Elements page of a plugin
   * 
   * @param name
   */
  public void DeleteDashboard( String name ) {
    //TODO
  }

  /**
   * This method will run an endpoint and assert it is present/viewable/editable
   *
   * It must be called from the Elements page of a plugin
   * 
   * @param name
   */
  public void RunEndpoint( String name ) {
    //TODO
  }

  /**
   * This method will delete an endpoint and assert it is no longer present
   *
   * It must be called from the Elements page of a plugin
   * 
   * @param name
   */
  public void DeleteEndpoint( String name ) {
    //TODO
  }

  /**
   * This method will create an endpoint and assert it is present/viewable/editable
   *
   * It must be called from the Elements page of a plugin
   * 
   * @param name
   */
  public void CreateEndpoint( String name ) {
    //TODO
  }

  /**
   * This method will check the layout and functionality of the Element edit page
   *
   * It must be called from the Edit page of a plugin
   */
  public void CheckEditElement() {
    WebElement elementsButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='tabMultiButtonObj']//button[contains(text(),'Elements')]" ) );
    assertNotNull( elementsButton );
    elementsButton.click();
    WebElement elementsTable = this.elemHelper.FindElement( this.driver, By.id( "elementsTableObjTable" ) );
    assertNotNull( elementsTable );
    if ( this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='elementsTableObjTable']/tbody/tr" ) ) != null ) {
      WebElement endpointButton = this.elemHelper.FindElement( this.driver, By.xpath( "//button[contains(text( ),'Endpoints')]" ) );
      assertNotNull( endpointButton );
      endpointButton.click();
      AssertElementsFiltered( "Kettle" );
      WebElement dashboardButton = this.elemHelper.FindElement( this.driver, By.xpath( "//button[contains(text( ),'Dashboards')]" ) );
      assertNotNull( dashboardButton );
      dashboardButton.click();
      AssertElementsFiltered( "Dashboard" );
    }

    //TODO
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

  /**
   * This method will open a plugin in edit mode given its id and assert general layout and functionality.
   * 1 - Open Sparkl main dashboard and check the existence of and strings on Sparkl plugin entry
   * 2 - Check presence and functionality of refresh button
   * 3 - Check presence and functionality of Sort by drop down list 
   * 4 - Check presence and functionality of Create button
   * 
   * @param id
   */
  public void CheckEditPlugin( String id ) {
    OpenSparkl();
    CheckPluginCard( "sparkl" );
    EditPlugin( "sparkl" );
    CheckEditAbout();
    CheckEditElement();
  }

}
