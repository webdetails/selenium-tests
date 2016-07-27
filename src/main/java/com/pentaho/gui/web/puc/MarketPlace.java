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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.HttpUtils;
import com.pentaho.ctools.utils.PageUrl;

public class MarketPlace {

  private WebDriver driver;
  private ElementHelper elemHelper = new ElementHelper();
  private static Logger log = LogManager.getLogger( MarketPlace.class );

  public MarketPlace( WebDriver driver ) {
    this.driver = driver;
  }

  /**
   * This method will navigate to the Marketplace page
   *
   */
  public void GoToMarketPlace() {
    log.info( "Enter: GoToMarketPlace" );
    this.elemHelper.Get( this.driver, PageUrl.PUC );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='mantle-perspective-switcher']//div[@class='custom-dropdown-label']" ) );

    log.info( "Enter: Click MarketPlace Button" );
    //Click 'Home' dropdown
    this.elemHelper.Click( this.driver, By.xpath( "//div[@id='mantle-perspective-switcher']//div[@class='custom-dropdown-label']" ) );

    //Assert dropdown shown and click MarketPlace
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='customDropdownPopupMajor']//td[contains(text(),'Marketplace')]" ) );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@id='customDropdownPopupMajor']//td[contains(text(),'Marketplace')]" ) );
    log.info( "Exit: Click MarketPlace Button" );

    log.info( "Enter: Assert marketplace perspective shown" );
    //Focus Browser Perspective and refresh repository
    this.driver.switchTo().defaultContent();
    assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "applicationShell" ) ) );
    assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//iframe[@id='marketplace.perspective.osgi']" ) ) );
    this.driver.switchTo().frame( "marketplace.perspective.osgi" );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "floatingBarsG" ) );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.id( "floatingBarsG" ) );
    assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='filterBar clearfix']" ), 60 ) );
    assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='filteredPluginsContainer ng-isolate-scope']" ) ) );
    log.info( "Exit: Assert marketplace perspective shown" );
    log.info( "Exit: GoToMarketPlace" );
  }

  /**
   * This method will check for existence of Plugin on list given Plugin's name and author and return TRUE if it exists.
   * It looks for plugin name within the title of div's where plugin's names are stored, the same for author
   *
   * Ex: PluginExists("Pentaho Marketplace", "Pentaho")
   * @param name
   * @param author
   * @returns exists
   */
  public boolean PluginExists( String name, String author ) {
    log.info( "Enter: PluginExists" );
    Boolean exists = false;
    //Look for Plugin by name and author
    WebElement nameDiv = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='filteredPluginsContainer ng-isolate-scope']//div[@title='" + name + "']" ) );
    WebElement authorDiv = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='filteredPluginsContainer ng-isolate-scope']//div[@title='" + name + "']/../div[@title='" + author + "']" ) );
    if ( nameDiv != null && authorDiv != null ) {
      exists = true;
    }
    log.info( "Exit: PluginExists" );

    return exists;
  }

  /**
   * This method will check the layout of the Marketplace. It does so by following these steps:
   * 1 - Check the existence of and text on the Available and Installed tabs
   * 2 - Checks the existence of the filters and Stages info popup
   * 3 - Asserts Marketplace plugin exists on the list
   * 4 - Asserts Hyperlink on bottom of page is accurate
   *
   */
  public void CheckMarketPlaceLayout() {
    //Assert tabs
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='pentaho-tab-bar']/div/div" ) );
    assertNotNull( element );
    String text = element.getText();
    assertEquals( "Available", text );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='pentaho-tab-bar']/div[2]/div" ) );
    assertNotNull( element );
    text = element.getText();
    assertEquals( "Installed", text );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='pentaho-tab-bar']/button[@title='Refresh']" ) );
    assertNotNull( element );
    element.click();
    this.elemHelper.WaitForElementInvisibility( this.driver, By.id( "floatingBarsG" ) );

    //Assert filters
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='pentaho-tab-deck-panel']/div/div/div[@data-options='pluginTypes']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='pentaho-tab-deck-panel']/div/div/div[@data-options='developmentStages']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='pentaho-tab-deck-panel']/div/div/input[@data-ng-model='searchText']" ) );
    assertNotNull( element );
    CheckStagesPopup();

    //Assert Marketplace plugin exists on list
    assertTrue( PluginExists( "Pentaho Marketplace ", "Pentaho" ) );

    //Assert hyperlink on bottom of page is accurate
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@href='http://community.pentaho.com/marketplace/plugins/']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@href='http://community.pentaho.com/marketplace/plugins/']/div/div/div" ) );
    assertNotNull( element );
    text = element.getText();
    assertEquals( "Contribute your Plugin Today", text );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@href='http://community.pentaho.com/marketplace/plugins/']/div/div/div[2]" ) );
    assertNotNull( element );
    text = element.getText();
    assertEquals( "All info to setup a new plugin.", text );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@href='http://community.pentaho.com/marketplace/plugins/']/div/div[2]/div" ) );
    assertNotNull( element );
    text = element.getText();
    assertEquals( "Learn More", text );
    CheckPluginListLayout();

  }

  /**
   * This method will check What are Stages popup works correctly.
   *
   * It will open the popup and check all different dev stages are shown
   *
   */
  public void CheckStagesPopup() {
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='pentaho-tab-deck-panel']/div/div[2]/a" ) );
    assertNotNull( element );
    String text = element.getText();
    assertEquals( "What are stages?", text );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@class='pentaho-tab-deck-panel']/div/div[2]/a" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@window-class='stagesInfoDialog']/div/div" ) );
    text = element.getText();
    assertEquals( "Understanding Plugin Stages", text );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@window-class='stagesInfoDialog']//div[@class='dev-stage ng-scope dev-stage-customer-01']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@window-class='stagesInfoDialog']//div[@class='dev-stage ng-scope dev-stage-customer-02']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@window-class='stagesInfoDialog']//div[@class='dev-stage ng-scope dev-stage-customer-03']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@window-class='stagesInfoDialog']//div[@class='dev-stage ng-scope dev-stage-customer-04']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@window-class='stagesInfoDialog']//div[@class='dev-stage ng-scope dev-stage-community-01']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@window-class='stagesInfoDialog']//div[@class='dev-stage ng-scope dev-stage-community-02']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@window-class='stagesInfoDialog']//div[@class='dev-stage ng-scope dev-stage-community-03']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@window-class='stagesInfoDialog']//div[@class='dev-stage ng-scope dev-stage-community-04']" ) );
    assertNotNull( element );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@window-class='stagesInfoDialog']/div/button[@class='closeButton']" ) );
    this.elemHelper.WaitForElementNotPresent( this.driver, By.xpath( "//div[@class='pentaho-tab-deck-panel']/div/div[2]/a" ), 3 );
  }

  /**
   * This method will check that clicking Installed tab only shows and shows all Installed plugins. It must be called from the Marketplace, with the "Available"
   * tab selected and the driver set to marketplace.perspective.
   *
   * It will get a list of all plugins on the Available tab that are installed and compare to a list of plugins shown after Installed tab is clicked
   *
   */
  public void CheckInstalledTab() {
    //List all installed Plugins
    List<WebElement> allPlugins = this.driver.findElements( By.xpath( "//div[@class='filteredPluginsContainer ng-isolate-scope']/div/ul/li" ) );
    List<String> installedPlugins = new ArrayList<String>();
    int nTotal = allPlugins.size();
    for ( int i = 1; i <= nTotal; i++ ) {
      String text = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@data-ng-show='plugins']/ul/li[" + i + "]/div/div[3]/div/div[@class='infoVersionStatusMessage ng-binding']" ) );
      String text2 = "Installed";
      if ( text.equals( text2 ) ) {
        text = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@data-ng-show='plugins']/ul/li[" + i + "]/div/div/div[@class='pluginName ng-binding']" ) );
        installedPlugins.add( text );
      }
    }

    //Click "Installed tab and List all shown Plugins
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='pentaho-tab-bar']/div[2]/div" ) );
    assertNotNull( element );
    String text = element.getText();
    assertEquals( "Installed", text );
    element.click();
    allPlugins = this.driver.findElements( By.xpath( "//div[@class='filteredPluginsContainer ng-isolate-scope']/div/ul/li" ) );
    List<String> shownPlugins = new ArrayList<String>();
    nTotal = allPlugins.size();
    for ( int i = 1; i <= nTotal; i++ ) {
      text = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@data-ng-show='plugins']/ul/li[" + i + "]/div/div/div[@class='pluginName ng-binding']" ) );
      shownPlugins.add( text );
    }

    //Assert both lists are equal
    assertEquals( installedPlugins, shownPlugins );
  }

  /**
   * This method will filter given specific parameters and assert shown plugins are the ones expected
   * It must be called from the Marketplace, with the driver set to marketplace.perspective.
   *
   * ALL APPLIED FILTERS ARE CLEARED AT THE END OF THE METHOD
   * The "type" param is an int array given as follows
   * 1  Apps
   * 2    Admin
   * 3    Analysis
   * 4    Dashboard
   * 5    Data
   * 6    Development
   * 8  Language Packs
   * 11 Visualizations
   *
   * The "stage" param is an int array given as follows
   * 1  Customer
   * 2    Development Phase
   * 3    Snapshot Release
   * 4    Limited Support
   * 5    Production Release
   * 7  Community
   * 8    Development Phase
   * 9    Snapshot Release
   * 10   Stable Release
   * 11   Mature Release
   *
   * @param type
   * @param stage
   * @param search
   * @return resultList
   */
  public List<String> CheckFiltersApplied( List<Number> type, List<Number> stage, String search ) {
    //If there is a type filter add it
    if ( !type.isEmpty() ) {
      WebElement element = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@data-selected-options='selectedTypes']/div/button/span" ) );
      assertNotNull( element );
      element.click();
      for ( Number i : type ) {
        element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@data-selected-options='selectedTypes']/div/ul/li[" + i + "]/a/input" ) );
        assertNotNull( element );
        element.click();
      }
      element = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@data-selected-options='selectedTypes']/div/button/span" ) );
      assertNotNull( element );
      element.click();
    }

    //If there is a stage filter add it
    if ( !stage.isEmpty() ) {
      WebElement element = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@data-options='developmentStages']/div/button/span" ) );
      assertNotNull( element );
      element.click();
      for ( Number i : stage ) {
        element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@data-options='developmentStages']/div/ul/li[" + i + "]/a/input" ) );
        assertNotNull( element );
        element.click();
      }
      element = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@data-options='developmentStages']/div/button/span" ) );
      assertNotNull( element );
      element.click();
    }

    //If there is a search filter add it
    if ( !search.isEmpty() ) {
      WebElement element = this.elemHelper.FindElement( this.driver, By.xpath( "//input[@data-ng-model='searchText']" ) );
      assertNotNull( element );
      element.sendKeys( search );
    }
    List<String> resultList = new ArrayList<String>();

    //Check if there are plugins for the filters added
    if ( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='filteredPluginsContainer ng-isolate-scope']/div/ul/li" ), 1 ) != null ) {

      //List all shown plugins
      List<WebElement> allPlugins = this.driver.findElements( By.xpath( "//div[@class='filteredPluginsContainer ng-isolate-scope']/div/ul/li" ) );
      int nTotal = allPlugins.size();
      for ( int i = 1; i <= nTotal; i++ ) {
        String text = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@data-ng-show='plugins']/ul/li[" + i + "]/div/div/div[@class='pluginName ng-binding']" ) );
        resultList.add( text );
      }

      //Check if layout wasn't compromised
      CheckPluginListLayout();
    }

    //Remove added filters
    if ( !search.isEmpty() ) {
      WebElement element = this.elemHelper.FindElement( this.driver, By.xpath( "//input[@data-ng-model='searchText']" ) );
      assertNotNull( element );
      element.clear();
    }
    if ( !stage.isEmpty() || !type.isEmpty() ) {
      RefreshPage();
    }
    return resultList;
  }

  /**
   * This method will check Plugin list layout is as expected
   *
   * It must be called from the Marketplace, with the driver set to marketplace.perspective.
   *
   */
  public void CheckPluginListLayout() {
    List<WebElement> allPlugins = this.driver.findElements( By.xpath( "//div[@class='filteredPluginsContainer ng-isolate-scope']/div/ul/li" ) );
    int nTotal = allPlugins.size();
    if ( nTotal != 0 ) {
      if ( nTotal >= 5 ) {
        int[] pluginToTest = { nTotal / 2,
            nTotal / 3,
            nTotal / 4,
            nTotal / 5 };
        for ( int i : pluginToTest ) {
          CheckIndividualPluginLayout( i );
        }
      } else {
        for ( int i = 1; i <= nTotal; i++ ) {
          CheckIndividualPluginLayout( i );
        }
      }
    }
  }

  /**
   * This method will check individual Plugin layout is as expected given the position of said plugin.
   *
   * It must be called from the Marketplace, with the driver set to marketplace.perspective.
   * 
   * @param position
   */
  public void CheckIndividualPluginLayout( int position ) {
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@data-ng-show='plugins']/ul/li[" + position + "]/div/div/div[@class='pluginName ng-binding']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@data-ng-show='plugins']/ul/li[" + position + "]/div/div/div[@class='pluginAuthor ng-binding']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@data-ng-show='plugins']/ul/li[" + position + "]/div/div[2]/div[@data-ng-class='devStageClass']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@data-ng-show='plugins']/ul/li[" + position + "]/div/div[3]/div/div[@class='infoVersionStatusMessage ng-binding']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@data-ng-show='plugins']/ul/li[" + position + "]/div/div[3]/div/div[@class='infoVersion ng-binding']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@data-ng-show='plugins']/ul/li[" + position + "]/div/div[3]/button/span[@class='text ng-binding']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@data-ng-show='plugins']/ul/li[" + position + "]/div/div[3]/button/span[@class='icon']" ) );
    assertNotNull( element );
  }

  /**
   * This method will refresh the Marketplace Page
   *
   * It must be called from the Marketplace, with the driver set to marketplace.perspective.
   *
   */
  public void RefreshPage() {
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='pentaho-tab-bar']/button[@title='Refresh']" ) );
    assertNotNull( element );
    element.click();
    this.elemHelper.WaitForElementInvisibility( this.driver, By.id( "floatingBarsG" ) );
  }

  /**
   * This method will check CTools Plugin details popup and other random plugins are accurate.
   *
   * This method must be called from the Marketplace page with the driver set to the marketplace.perspective and no
   * filters applied
   *
   *  It will use the CheckPluginPopup method feeding it the CTools position and 5 other random positions to be checked
   *
   */
  public void CheckRandomPluginsDetails() {
    List<WebElement> allPlugins = this.driver.findElements( By.xpath( "//div[@class='filteredPluginsContainer ng-isolate-scope']/div/ul/li" ) );
    int nTotal = allPlugins.size();
    CheckPluginPopup( 2 );
    CheckPluginPopup( 3 );
    CheckPluginPopup( 4 );
    CheckPluginPopup( 5 );
    Random randomGenerator = new Random();
    for ( int i = 1; i <= 5; i++ ) {
      int pos = 0;
      while ( pos < 6 ) {
        pos = randomGenerator.nextInt( nTotal );
      }
      CheckPluginPopup( pos );
    }

  }

  /**
   * This method will check Plugin details popup is accurate given it's position on the plugins list.
   *
   * This method must be called from the Marketplace page with the driver set to the marketplace.perspective
   *
   * It opens the plugin Details and asserts title, author, version, screenshots and buttons
   *
   * @param pos
   */
  public void CheckPluginPopup( int pos ) {
    log.info( "checking plugin in position: " + pos );
    //Get Plugin name and author
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@data-ng-show='plugins']/ul/li[" + pos + "]/div/div/div[@class='pluginName ng-binding']" ) );
    assertNotNull( element );
    String title = element.getText();
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@data-ng-show='plugins']/ul/li[" + pos + "]/div/div/div[@class='pluginAuthor ng-binding']" ) );
    assertNotNull( element );
    String author = element.getText();
    boolean installed = false;
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@data-ng-show='plugins']/ul/li[" + pos + "]//div[@class='infoVersionStatusMessage ng-binding']" ) );
    assertNotNull( element );
    if ( element.getText() == "Installed" ) {
      installed = true;
    }

    //Open Plugin
    this.elemHelper.Click( this.driver, By.xpath( "//div[@data-ng-show='plugins']/ul/li[" + pos + "]/div/div/div[@class='pluginName ng-binding']" ) );

    //Wait for popup to open fully
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@class='pluginDetailInfoContainer']/div/div[2]/div/div[@class='pluginName ng-binding']" ) );

    //Assert title and author shown are the same and image exists
    element = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@class='pluginDetailInfoContainer']/div/div[2]/div/div[@class='pluginName ng-binding']" ) );
    assertNotNull( element );
    assertEquals( title, element.getText() );
    element = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@class='pluginDetailInfoContainer']/div/div[2]/div/div[@class='pluginAuthor ng-binding']" ) );
    assertNotNull( element );
    assertEquals( author, element.getText() );
    element = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@class='pluginDetailInfoContainer']/div[2]/div/div[4]/div[2]/a[@class='ng-binding ng-scope']" ) );
    assertNotNull( element );
    assertEquals( author, element.getText() );
    element = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@class='pluginImage']/img" ) );
    if ( element != null ) {
      String text = element.getAttribute( "data-ng-src" );
      assertEquals( HttpStatus.SC_OK, HttpUtils.GetHttpStatus( text, "admin", "password" ) );
    }

    //Assert version shown on button is the same as in the info
    element = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@class='pluginDetailInfoContainer']/div/div/div[2]/button/span[@class='multiselectButtonText ng-binding']" ) );
    assertNotNull( element );
    String version = element.getText();
    version = version.replace( "Version: ", "" );
    version = version.trim();
    version = version.replace( ")", "" );
    String[] versions = version.split( "\\(" );
    element = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@class='pluginDetailInfoContainer']/div[2]/div/div[2]/div[2]/span[@class='ng-binding ng-scope']" ) );
    assertNotNull( element );
    assertEquals( versions[1], element.getText().trim() );
    element = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@class='pluginDetailInfoContainer']/div[2]/div/div[3]/div[2]/span[@class='ng-binding ng-scope']" ) );
    assertNotNull( element );
    assertEquals( versions[0], element.getText().trim() );

    //Assert buttons are shown
    element = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@class='pluginDetailHeaderButtons']/button" ) );
    assertNotNull( element );
    if ( installed ) {
      element = this.elemHelper.WaitForElementPresence( this.driver, By.xpath( "//div[@class='pluginDetailHeaderButtons']/button[2]" ) );
      assertNotNull( element );
    }

    //Assert Screenshot is showing
    element = this.elemHelper.WaitForElementPresence( this.driver, By.xpath( "//div[@class='carousel-inner']/div/img" ) );
    if ( element != null ) {
      List<WebElement> listElements = this.driver.findElements( By.xpath( "//div[@class='carousel-inner']/div" ) );
      for ( int i = 1; i < listElements.size(); i++ ) {
        element = this.elemHelper.WaitForElementPresence( this.driver, By.xpath( "//div[@class='carousel-inner']/div[" + i + "]/img" ) );
        String text = element.getAttribute( "data-ng-src" );
        assertEquals( HttpStatus.SC_OK, HttpUtils.GetHttpStatus( text, "admin", "password" ) );
      }
    }

    //Close popup and assert it is closed
    element = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@class='modal-container ng-scope']/button[@class='closeButton']" ) );
    assertNotNull( element );
    element.click();
    this.elemHelper.WaitForElementNotPresent( this.driver, By.xpath( "//div[@class='modal-container ng-scope']/button[@class='closeButton']" ), 3 );

  }

  /**
   * This method will check if plugin is installed. It needs the plugin name.
   * 
   * This method must be called from the Marketplace page with the driver set to the marketplace.perspective
   * 
   * @param name
   * @return installed
   */
  public boolean CheckIfPluginInstalled( String name ) {
    boolean installed = false;
    //assert plugin exists
    WebElement plugin = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@data-ng-show='plugins']//div[@title='" + name + "']" ) );
    assertNotNull( plugin );

    //check if is installed
    String status = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@data-ng-show='plugins']//div[@title='" + name + "']/../..//div[@class='infoVersionStatusMessage ng-binding']" ) );
    if ( status.contains( "Installed" ) ) {
      installed = true;
    }
    return installed;
  }

  /**
   * This method will check Installing plugin works correctly. It needs the plugin name and the success message in case it is not default.
   * 
   * This method must be called from the Marketplace page with the driver set to the marketplace.perspective
   * 
   * @param name
   * @param message
   */
  public void CheckInstallPlugin( String name, String message ) {
    //assert plugin exists
    WebElement plugin = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@data-ng-show='plugins']//div[@title='" + name + "']" ) );
    assertNotNull( plugin );

    //assert it is not installed
    assertFalse( CheckIfPluginInstalled( name ) );

    //Check button says "Install" and click it
    String buttonMessage = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@data-ng-show='plugins']//div[@title='" + name + "']/../..//span[@class='text ng-binding']" ) );
    assertEquals( buttonMessage, "Install" );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@data-ng-show='plugins']//div[@title='" + name + "']/../..//span[@class='text ng-binding']" ) );

    //Check popup text
    String popupMessage = this.elemHelper.WaitForElementPresentGetText( this.driver, By.cssSelector( "div.dialog.ng-scope div.body div.ng-binding" ) );
    String expectedMessage = "You are about to install " + name + ". Do you want to proceed?";
    assertEquals( popupMessage, expectedMessage );

    //Click Ok
    WebElement okButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@window-class='confirmationDialog']//div[@class='buttonsContainer']/button" ) );
    assertNotNull( okButton );
    okButton.click();

    //wait for and assert popup message
    if ( message.length() > 0 ) {
      expectedMessage = message;
    } else {
      expectedMessage = "Plugin " + name + " installed successfully. You must restart your server for changes to take effect.";
    }
    this.elemHelper.WaitForTextPresence( this.driver, By.cssSelector( "div.dialog.ng-scope div.body div.ng-binding" ), expectedMessage, 360 );
    popupMessage = this.elemHelper.WaitForElementPresentGetText( this.driver, By.cssSelector( "div.dialog.ng-scope div.body div.ng-binding" ) );
    assertEquals( popupMessage, expectedMessage );

    //Click Ok and assert dialog is gone
    okButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@window-class='confirmationDialog']//div[@class='buttonsContainer']/button" ) );
    assertNotNull( okButton );
    okButton.click();
    this.elemHelper.WaitForElementNotPresent( this.driver, By.xpath( "//div[@window-class='confirmationDialog']" ), 3 );

    //Assert plugin status is installed
    assertTrue( CheckIfPluginInstalled( name ) );

    //Assert button reads "Up to Date"
    buttonMessage = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@data-ng-show='plugins']//div[@title='" + name + "']/../..//span[@class='text ng-binding']" ) );
    assertEquals( buttonMessage, "Up to Date" );
  }

  /**
   * This method will check Uninstalling plugin works correctly. It needs the plugin name and the success message in case it is not default.
   * 
   * This method must be called from the Marketplace page with the driver set to the marketplace.perspective
   * 
   * @param name
   * @param message
   */
  public void CheckUninstallPlugin( String name, String message ) {
    //assert plugin exists
    WebElement plugin = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@data-ng-show='plugins']//div[@title='" + name + "']" ) );
    assertNotNull( plugin );

    //assert it is installed
    assertTrue( CheckIfPluginInstalled( name ) );

    //Open plugin details
    plugin = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@data-ng-show='plugins']//div[@title='" + name + "']" ) );
    assertNotNull( plugin );
    plugin.click();
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@class='pluginDetailInfoContainer']/div/div[2]/div/div[@class='pluginName ng-binding']" ) );

    //assert Uninstall button is enabled and click it
    WebElement uninstallButton = this.elemHelper.WaitForElementPresence( this.driver, By.xpath( "//div[@class='pluginDetailHeaderButtons']/button[2]" ) );
    assertNotNull( uninstallButton );
    uninstallButton.click();

    //Check popup text
    String popupMessage = this.elemHelper.WaitForElementPresentGetText( this.driver, By.cssSelector( "div.dialog.ng-scope div.body div.ng-binding" ) );
    String expectedMessage = "You are about to uninstall " + name + ". Do you want to proceed?";
    assertEquals( popupMessage, expectedMessage );

    //Click Ok
    WebElement okButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@window-class='confirmationDialog']//div[@class='buttonsContainer']/button" ) );
    assertNotNull( okButton );
    okButton.click();

    //wait for and assert popup message
    if ( message.length() > 0 ) {
      expectedMessage = message;
    } else {
      expectedMessage = "Plugin " + name + " uninstalled successfully. You must restart your server for changes to take effect.";
    }
    this.elemHelper.WaitForTextPresence( this.driver, By.cssSelector( "div.dialog.ng-scope div.body div.ng-binding" ), expectedMessage, 360 );
    popupMessage = this.elemHelper.WaitForElementPresentGetText( this.driver, By.cssSelector( "div.dialog.ng-scope div.body div.ng-binding" ) );
    assertEquals( popupMessage, expectedMessage );

    //Click Ok and assert dialog is gone
    okButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@window-class='confirmationDialog']//div[@class='buttonsContainer']/button" ) );
    assertNotNull( okButton );
    okButton.click();
    this.elemHelper.WaitForElementNotPresent( this.driver, By.xpath( "//div[@window-class='confirmationDialog']" ), 3 );

    //assert uninstall button is disabled
    this.elemHelper.WaitForElementNotPresent( this.driver, By.xpath( "//div[@class='pluginDetailHeaderButtons']/button[2]" ), 3 );

    //close popup
    WebElement closeButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@class='modal-container ng-scope']/button[@class='closeButton']" ) );
    assertNotNull( closeButton );
    closeButton.click();
    this.elemHelper.WaitForElementNotPresent( this.driver, By.xpath( "//div[@class='modal-container ng-scope']/button[@class='closeButton']" ), 3 );

    //Assert plugin status is installed
    assertFalse( CheckIfPluginInstalled( name ) );

    //Assert button reads "Up to Date"
    String buttonMessage = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@data-ng-show='plugins']//div[@title='" + name + "']/../..//span[@class='text ng-binding']" ) );
    assertEquals( buttonMessage, "Install" );
  }

  /**
   * This method will navigate to Installed tab, it has to be called from the Marketplacepage with the driver set to marketplace.perspective.
   *
   */
  public void GoToInstalledTab() {
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='pentaho-tab-bar']/div[2]/div" ) );
    assertNotNull( element );
    String text = element.getText();
    assertEquals( "Installed", text );
    element.click();
  }

  /**
   * This method will return an integer representing the amount of plugins currently shown on the Marketplace, 
   * it has to be called from the Marketplacepage with the driver set to marketplace.perspective.
   *
   *@return size
   */
  public int PluginListSize() {
    int size = 0;
    List<WebElement> allPlugins = this.driver.findElements( By.xpath( "//div[@class='filteredPluginsContainer ng-isolate-scope']/div/ul/li" ) );
    size = allPlugins.size();
    return size;
  }
}
