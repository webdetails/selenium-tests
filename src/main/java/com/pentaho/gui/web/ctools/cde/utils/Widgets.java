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
package com.pentaho.gui.web.ctools.cde.utils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.gui.web.ctools.cde.CDEditor;
import com.pentaho.gui.web.puc.BrowseFiles;

public class Widgets {
  // The location of all widgets created through CDE Dashboard
  private final String widgetsFolder = "/public/cde/widgets";
  // Log instance
  private final Logger log = LogManager.getLogger( Widgets.class );
  // The wrap class of Webdriver
  private ElementHelper elemHelper = new ElementHelper();

  /**
   * Constructor
   */
  public Widgets() {
  }

  /**
   * This method is responsible to remove the widget from 'Browse Files'.
   *
   * @param driver
   * @param widgetName
   */
  public void RemoveWidgetByName( final WebDriver driver, final String widgetName ) {
    this.log.info( "RemoveWidgetByName::Enter" );
    BrowseFiles browser = new BrowseFiles( driver );
    browser.CheckShowHiddenFiles();
    browser.DeleteMultipleFilesByName( this.widgetsFolder, widgetName );
    this.log.info( "RemoveWidgetByName::Exit" );
  }

  /**
   * This method shall create an widget with specific parameter.
   *
   * @param driver
   * @param wait
   * @param baseUrl
   * @param widgetName
   * @param paramName
   * @return
   */
  public WebDriver CreateWidgetWithParameter( WebDriver driver, String widgetName, String paramName ) {
    // Step 1 - Go to homepage
    this.elemHelper.SwitchToDefault( driver );
    this.elemHelper.Get( driver, PageUrl.PUC );
    // wait for visibility of waiting pop-up
    elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );
    // Wait for the visibility of Menu and frame contents
    elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='mainMenubar']" ) );
    this.elemHelper.SwitchToFrame( driver, "home.perspective" );
    elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='buttonWrapper']" ) );

    // Step 2 - Click in Create New (CDE Dashboard)
    // Click to create a widget
    WebElement buttonCreateNew = elemHelper.FindElement( driver, By.xpath( "//button[@id='btnCreateNew']" ) );
    assertEquals( buttonCreateNew.getText(), "Create New" );
    buttonCreateNew.click();
    elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='popover-content']" ) );
    elemHelper.WaitForTextPresence( driver, By.xpath( "//div[@class='popover-content']/button[@id='createNewlaunch_new_cdeButton']" ), "CDE Dashboard" );
    WebElement buttonCDEBashBoard = elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div.popover-content > #createNewlaunch_new_cdeButton" ) );
    assertEquals( buttonCDEBashBoard.getText(), "CDE Dashboard" );
    buttonCDEBashBoard.click();
    this.elemHelper.SwitchToDefault( driver ); // back to the root

    // Step 3 - Click in Component Panel
    WebElement frameCDEDashboard = elemHelper.FindElement( driver, By.xpath( "//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe" ) );
    this.elemHelper.SwitchToFrame( driver, frameCDEDashboard );
    elemHelper.Click( driver, By.xpath( "//div[@class='componentsPanelButton']" ) );

    // Step 4 - Add a Simple Parameter
    // Click in Generic
    elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[3]/h3/span" ) );
    elemHelper.FindElement( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[3]/h3/span" ) ).click();
    // Click in SimpleParameter
    elemHelper.FindElement( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[3]/div/ul/li[3]/a" ) ).click();
    // Add a name to parameter 'paramCreateWidget'
    // Click in Name
    elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr/td[2]" ) );
    assertEquals( elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr/td" ) ), "Name" );
    // The below code is comment because the input text is already active.
    // elemHelper.FindElement( driver,By.xpath("//table[@id='table-cdfdd-components-properties']/tbody/tr/td[2]")).click();
    WebElement inputPName = elemHelper.FindElement( driver, By.xpath( "//input[@name='value']" ) );
    inputPName.clear();
    inputPName.sendKeys( paramName );
    inputPName.sendKeys( Keys.RETURN );

    // Click in PropertyValue
    elemHelper.Click( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[2]/td[2]" ) );
    WebElement inputValue = elemHelper.FindElement( driver, By.xpath( "//input[@name='value']" ) );
    inputValue.clear();
    inputValue.sendKeys( "1" );
    inputValue.sendKeys( Keys.RETURN );

    // Step 5 - Press SAVE
    elemHelper.Click( driver, By.xpath( "//div[@id='headerLinks']/div[2]/a" ) );
    elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='popup']" ) );
    elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='container_id']/ul/li" ) );
    elemHelper.Click( driver, By.xpath( "//input[@id='widgetRadio']" ) );
    while ( true ) {

      if ( elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='container_id']" ) ).isDisplayed() == false ) {
        break;
      }
      try {
        Thread.sleep( 100 );
      } catch ( InterruptedException e ) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    // Insert file name
    elemHelper.SendKeys( driver, By.xpath( "//input[@id='fileInput']" ), widgetName );
    // Insert widget name
    elemHelper.SendKeys( driver, By.xpath( "//input[@id='componentInput']" ), widgetName );
    // Press OK (SAVING)
    elemHelper.Click( driver, By.xpath( "//button[@id='popup_state0_buttonOk']" ) );

    // Step 6 - Check if the parameter exist in 'Settings'
    // Popup message informing saving
    elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='layoutPanelButton']" ) );
    // elemHelper.WaitForElementPresenceAndVisible(driver, (By.xpath("//div[@class='layoutPanelButton']")));
    assertTrue( elemHelper.FindElement( driver, By.xpath( "//div[@class='layoutPanelButton']" ) ).isEnabled() );
    // Press 'Settings'
    elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='headerLinks']/div[5]/a" ) );
    elemHelper.Click( driver, By.xpath( "//div[@id='headerLinks']/div[5]/a" ) );
    elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='popup']" ) );
    assertNotNull( elemHelper.FindElement( driver, By.xpath( "//span[@id='widgetParameters']/div/input" ) ) );
    // The parameter MUST be equal to the one set
    assertEquals( elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//span[@id='widgetParameters']/div/span" ) ), paramName );
    // Press on the check box
    elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//span[@id='widgetParameters']/div/input" ) );
    // elemHelper.WaitForElementPresenceAndVisible(driver, (By.xpath("//span[@id='widgetParameters']/div/input")));
    elemHelper.Click( driver, By.xpath( "//span[@id='widgetParameters']/div/input" ) );
    // Press button save
    elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='popupbuttons']/button[@id='popup_state0_buttonSave']" ) );
    // elemHelper.WaitForElementPresenceAndVisible(driver,
    // (By.xpath("//div[@class='popupbuttons']/button[@id='popup_state0_buttonSave']")));
    elemHelper.Click( driver, By.xpath( "//div[@class='popupbuttons']/button[@id='popup_state0_buttonSave']" ) );

    elemHelper.WaitForElementInvisibility( driver, By.id( "popupbox" ) );

    return driver;
  }

  /**
   * This method shall create an widget with specific parameter.
   *
   * @param driver
   * @param widgetName
   * @return
   */
  public void CreateWidget( final WebDriver driver, final String widgetName ) {
    this.log.info( "CreateWidget::Enter" );
    // Open New CDE Dashboard
    WebDriver thedriver = this.elemHelper.SwitchToDefault( driver );

    CDEditor cdeEditor = new CDEditor( thedriver );
    cdeEditor.GoToNewCDE();
    cdeEditor.SaveWidget( widgetName );

    this.log.info( "CreateWidget::Exit" );
  }

  /**
   * This method
   *
   * @param driver
   * @param wait
   * @param baseUrl
   * @param widgetName
   * @return
   */
  public WebDriver OpenWidgetEditMode( WebDriver driver, String baseUrl, String widgetName ) {
    // Resuming Steps
    // 1. open the widget
    // 2. check if the parameter exist in settings
    // 3. check if the widget exist in 'widgets' at Component Layout
    this.elemHelper.SwitchToDefault( driver );
    this.elemHelper.Get( driver, PageUrl.PUC );
    // wait for visibility of waiting pop-up
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );
    // Step 1 - Go to Homepage and click 'Browse Files'
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//iframe[@id='home.perspective']" ) );
    assertNotNull( this.elemHelper.FindElement( driver, By.xpath( "//iframe[@id='home.perspective']" ) ) );
    // Go to the Home Perspective [IFRAME]
    this.elemHelper.SwitchToFrame( driver, "home.perspective" );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='well sidebar']" ) );
    this.elemHelper.Click( driver, By.xpath( "//div[@class='well sidebar']/button" ) ); // Click in 'Browse Files'

    // Step 2 - Go to 'widgets' and click in the created widget and press 'Edit'
    // Now we have to navigate to 'Public/cde/widgets
    this.elemHelper.SwitchToDefault( driver );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "applicationShell" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//iframe[@id='browser.perspective']" ) );
    this.elemHelper.SwitchToFrame( driver, "browser.perspective" );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "fileBrowser" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='spinner large-spinner']" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );

    // Public
    assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='fileBrowserFolders']" ) ) );
    assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@path='/public']" ) ) );
    this.elemHelper.FindElement( driver, By.xpath( "//div[@path='/public']" ) ).findElement( By.className( "expandCollapse" ) ).click();
    // CDE
    assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@path='/public/cde']" ) ) );
    this.elemHelper.FindElement( driver, By.xpath( "//div[@path='/public/cde']" ) ).findElement( By.className( "expandCollapse" ) ).click();
    // Widgets
    assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@path='/public/cde/widgets']" ) ) );
    this.elemHelper.FindElement( driver, By.xpath( "//div[@path='/public/cde/widgets']" ) ).findElement( By.className( "title" ) ).click();

    // wait for the page load in 'fileBrowserFiles'
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='spinner large-spinner']" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );
    // Check if at least one file is displayed
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='fileBrowserFiles']/div[2]/div" ) );
    WebElement listFiles = elemHelper.FindElement( driver, By.xpath( "//div[@id='fileBrowserFiles']/div[2]" ) );
    List<WebElement> theWidgetFiles = listFiles.findElements( By.xpath( "//div[@class='title' and contains(text(),'" + widgetName + "')]" ) );

    // Check if the widget named exist
    if ( theWidgetFiles != null ) {
      if ( theWidgetFiles.size() > 0 ) {

        Actions action = new Actions( driver );
        action.click( theWidgetFiles.get( 0 ) );
        action.build().perform();

        // Here we still in the iframe
        assertNotNull( elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "editButton" ) ) );
        elemHelper.Click( driver, By.id( "editButton" ) );

        this.elemHelper.SwitchToDefault( driver ); // back to the root
      }
    }

    return driver;
  }
}
