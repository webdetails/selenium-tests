package com.pentaho.ctools.cde.widgets.utils;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.gui.web.puc.BrowseFiles;

public class WidgetUtils {
  // The location of all widgets created through CDE Dashboard
  private static final String FOLDER_WIDGETS = "/public/cde/widgets";
  // Log instance
  private static final Logger LOG = LogManager.getLogger( WidgetUtils.class );
  // TODO 
  private static Boolean firstTimeLoadingCDEDashboardEditor = true;

  /**
   * This method is responsible to remove the widget from 'Browse Files'.
   *
   * @param driver
   * @param wait
   * @param widgetName
   */
  public static void RemoveWidgetByName( final WebDriver driver, final String widgetName ) {
    LOG.info( "RemoveWidgetByName::Enter" );
    BrowseFiles browser = new BrowseFiles( driver );
    browser.CheckShowHiddenFiles();
    browser.DeleteMultipleFilesByName( FOLDER_WIDGETS, widgetName );
    LOG.info( "RemoveWidgetByName::Exit" );
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
  public static WebDriver CreateWidgetWithParameter( WebDriver driver, String widgetName, String paramName )
    throws Exception {
    ElementHelper elemHelper = new ElementHelper();
    //Step 1 - Go to homepage
    driver.switchTo().defaultContent();
    driver.get( PageUrl.PUC );
    //wait for visibility of waiting pop-up
    elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );
    //Wait for the visibility of Menu and frame contents
    elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='mainMenubar']" ) );
    driver.switchTo().frame( "home.perspective" );
    elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='buttonWrapper']" ) );

    //Step 2 - Click in Create New (CDE Dashboard)
    //Click to create a widget
    WebElement buttonCreateNew = driver.findElement( By.xpath( "//button[@id='btnCreateNew']" ) );
    assertEquals( buttonCreateNew.getText(), "Create New" );
    buttonCreateNew.click();
    elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='popover-content']" ) );
    elemHelper.WaitForTextPresence( driver, By.xpath( "//div[@class='popover-content']/button[@id='createNewlaunch_new_cdeButton']" ), "CDE Dashboard" );
    WebElement buttonCDEBashBoard = elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div.popover-content > #createNewlaunch_new_cdeButton" ) );
    assertEquals( buttonCDEBashBoard.getText(), "CDE Dashboard" );
    buttonCDEBashBoard.click();
    driver.switchTo().defaultContent(); //back to the root

    //Step 3 - Click in Component Panel
    elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe" ) );
    WebElement frameCDEDashboard = driver.findElement( By.xpath( "//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe" ) );
    driver.switchTo().frame( frameCDEDashboard );
    elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='componentsPanelButton']" ) );
    driver.findElement( By.xpath( "//div[@class='componentsPanelButton']" ) ).click();

    //Step 4 - Add a Simple Parameter
    //Click in Generic
    elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[3]/h3/span" ) );
    driver.findElement( By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[3]/h3/span" ) ).click();
    //Click in SimpleParameter
    driver.findElement( By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[3]/div/ul/li[3]/a" ) ).click();
    //Add a name to parameter 'paramCreateWidget'
    //Click in Name
    elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr/td[2]" ) );
    assertEquals( driver.findElement( By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr/td" ) ).getText(), "Name" );
    //The below code is comment because the input text is already active.
    //driver.findElement(By.xpath("//table[@id='table-cdfdd-components-properties']/tbody/tr/td[2]")).click();
    WebElement inputPName = driver.findElement( By.xpath( "//input[@name='value']" ) );
    inputPName.clear();
    inputPName.sendKeys( paramName );
    inputPName.sendKeys( Keys.RETURN );

    //Click in PropertyValue
    elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[2]/td[2]" ) );
    elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[2]/td[2]" ) ).click();
    WebElement inputValue = driver.findElement( By.xpath( "//input[@name='value']" ) );
    inputValue.clear();
    inputValue.sendKeys( "1" );
    inputValue.sendKeys( Keys.RETURN );

    //Step 5 - Press SAVE
    elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='headerLinks']/div[2]/a" ) );
    elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='headerLinks']/div[2]/a" ) ).click();
    elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='popup']" ) );
    elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='container_id']/ul/li" ) );
    elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//input[@id='widgetRadio']" ) ).click();
    while ( true ) {

      if ( elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='container_id']" ) ).isDisplayed() == false ) {
        break;
      } else {
        Thread.sleep( 100 );
      }
    }
    //Insert file name
    driver.findElement( By.xpath( "//input[@id='fileInput']" ) ).sendKeys( widgetName );
    //Insert widget name
    driver.findElement( By.xpath( "//input[@id='componentInput']" ) ).sendKeys( widgetName );
    //Press OK (SAVING)
    driver.findElement( By.xpath( "//button[@id='popup_state0_buttonOk']" ) ).click();

    //Step 6 - Check if the parameter exist in 'Settings'
    //Popup message informing saving
    elemHelper.WaitForElementVisibility( driver, By.xpath( "//div[@class='layoutPanelButton']" ) );
    //elemHelper.WaitForElementPresenceAndVisible(driver, (By.xpath("//div[@class='layoutPanelButton']")));
    assertTrue( driver.findElement( By.xpath( "//div[@class='layoutPanelButton']" ) ).isEnabled() );
    //Press 'Settings'
    elemHelper.WaitForElementVisibility( driver, By.xpath( "//div[@id='headerLinks']/div[5]/a" ) );
    driver.findElement( By.xpath( "//div[@id='headerLinks']/div[5]/a" ) ).click();
    elemHelper.WaitForElementVisibility( driver, By.xpath( "//div[@id='popup']" ) );
    assertNotNull( driver.findElement( By.xpath( "//span[@id='widgetParameters']/div/input" ) ) );
    //The parameter MUST be equal to the one set
    assertEquals( driver.findElement( By.xpath( "//span[@id='widgetParameters']/div/span" ) ).getText(), paramName );
    //Press on the check box
    elemHelper.WaitForElementVisibility( driver, By.xpath( "//span[@id='widgetParameters']/div/input" ) );
    //elemHelper.WaitForElementPresenceAndVisible(driver, (By.xpath("//span[@id='widgetParameters']/div/input")));
    driver.findElement( By.xpath( "//span[@id='widgetParameters']/div/input" ) ).click();
    //Press button save
    elemHelper.WaitForElementVisibility( driver, By.xpath( "//div[@class='popupbuttons']/button[@id='popup_state0_buttonSave']" ) );
    //elemHelper.WaitForElementPresenceAndVisible(driver, (By.xpath("//div[@class='popupbuttons']/button[@id='popup_state0_buttonSave']")));
    driver.findElement( By.xpath( "//div[@class='popupbuttons']/button[@id='popup_state0_buttonSave']" ) ).click();

    elemHelper.WaitForElementInvisibility( driver, By.id( "popupbox" ) );
    //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("popupbox")));

    return driver;
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
  public static void CreateWidget( final WebDriver driver, final String widgetName ) {
    LOG.info( "CreateWidget::Enter" );
    ElementHelper elemHelper = new ElementHelper();

    //Open New CDE Dashboard
    WebDriver thedriver = driver.switchTo().defaultContent();

    thedriver.get( PageUrl.CDE_DASHBOARD );

    if ( firstTimeLoadingCDEDashboardEditor ) {
      firstTimeLoadingCDEDashboardEditor = false;
      try {
        //TODO - remove this sleep after upgrade Selenium from version 2.44 to upper.
        Thread.sleep( 8000 );
      } catch ( InterruptedException e ) {
        //e.printStackTrace();
      }
    }

    //wait for some contents loaded
    WebElement elemLogo = elemHelper.WaitForElementPresence( thedriver, By.cssSelector( "div.cdfdd-toolbar-logo" ) );
    WebElement buttonSave = elemHelper.WaitForElementPresence( thedriver, By.id( "Save" ) );
    WebElement buttonSaveAs = elemHelper.WaitForElementPresence( thedriver, By.id( "SaveAs" ) );
    WebElement buttonReload = elemHelper.WaitForElementPresence( thedriver, By.id( "cdfdd-main-Reload" ) );
    WebElement buttonSaveTemplate = elemHelper.WaitForElementPresence( thedriver, By.xpath( "//a[@title='Save as Template']" ) );
    WebElement buttonApplyTemplate = elemHelper.WaitForElementPresence( thedriver, By.xpath( "//a[@title='Apply Template']" ) );
    WebElement buttonAddResource = elemHelper.WaitForElementPresence( thedriver, By.xpath( "//a[@title='Add Resource']" ) );
    WebElement buttonAddBoostrap = elemHelper.WaitForElementPresence( thedriver, By.xpath( "//a[@title='Add Bootstrap Panel']" ) );
    WebElement buttonAddFreeForm = elemHelper.WaitForElementPresence( thedriver, By.xpath( "//a[@title='Add FreeForm']" ) );
    WebElement buttonAddRow = elemHelper.WaitForElementPresence( thedriver, By.xpath( "//a[@title='Add Row']" ) );
    WebElement buttonLayout = elemHelper.WaitForElementPresenceAndVisible( thedriver, By.xpath( "//div[@class='layoutPanelButton']" ) );
    WebElement buttonComponents = elemHelper.WaitForElementPresenceAndVisible( thedriver, By.xpath( "//div[@class='componentsPanelButton']" ) );
    WebElement buttonDatasources = elemHelper.WaitForElementPresenceAndVisible( thedriver, By.xpath( "//div[@class='datasourcesPanelButton']" ) );
    assertNotNull( elemLogo );
    assertNotNull( buttonSave );
    assertNotNull( buttonSaveAs );
    assertNotNull( buttonReload );
    assertNotNull( buttonSaveTemplate );
    assertNotNull( buttonApplyTemplate );
    assertNotNull( buttonAddResource );
    assertNotNull( buttonAddBoostrap );
    assertNotNull( buttonAddFreeForm );
    assertNotNull( buttonAddRow );
    assertNotNull( buttonLayout );
    assertNotNull( buttonComponents );
    assertNotNull( buttonDatasources );

    //Save the widget
    elemHelper.Click( thedriver, By.id( "Save" ) );
    //WaitFor popup visible
    elemHelper.WaitForElementPresence( thedriver, By.id( "popupbox" ) );
    elemHelper.WaitForElementPresence( thedriver, By.id( "popupfade" ) );
    //We need to wait for the animation finish for the display popup
    elemHelper.WaitForAttributeValueEqualsTo( thedriver, By.id( "popup" ), "style", "position: absolute; top: 15%; left: 50%; margin-left: -267.5px; z-index: 1000; width: 515px;" );
    //Wait for contents display
    elemHelper.WaitForElementPresenceAndVisible( thedriver, By.cssSelector( "li.directory.collapsed" ) );
    elemHelper.ClickJS( thedriver, By.id( "widgetRadio" ) );
    // Wait for explorer disabled
    elemHelper.WaitForElementInvisibility( thedriver, By.id( "container_id" ) );

    //Insert file name
    elemHelper.ClickAndSendKeys( thedriver, By.id( "fileInput" ), widgetName );
    //Insert widget name
    elemHelper.ClickAndSendKeys( thedriver, By.id( "componentInput" ), widgetName );
    //Press OK (SAVING)
    elemHelper.Click( thedriver, By.id( "popup_state0_buttonOk" ) );
    //Wait for the pop-up exit
    elemHelper.WaitForElementInvisibility( thedriver, By.id( "popupbox" ) );

    //Wait For the NotifyBar not present
    elemHelper.WaitForElementNotPresent( thedriver, By.id( "notifyBar" ) );

    //Wait for the page refreshed
    elemHelper.WaitForTextPresence( thedriver, By.cssSelector( "div.cdfdd-title" ), widgetName );

    LOG.info( "CreateWidget::Exit" );
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
  public static WebDriver OpenWidgetEditMode( WebDriver driver, Wait<WebDriver> wait, String baseUrl, String widgetName ) {
    ElementHelper elemHelper = new ElementHelper();
    //Resuming Steps
    // 1. open the widget
    // 2. check if the parameter exist in settings
    // 3. check if the widget exist in 'widgets' at Component Layout
    driver.switchTo().defaultContent();
    driver.get( baseUrl + "Home" );
    //wait for visibility of waiting pop-up
    elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );
    //Step 1 - Go to Homepage and click 'Browse Files'
    elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//iframe[@id='home.perspective']" ) );
    assertNotNull( driver.findElement( By.xpath( "//iframe[@id='home.perspective']" ) ) );
    //Go to the Home Perspective [IFRAME]
    driver.switchTo().frame( "home.perspective" );
    elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='well sidebar']" ) );
    driver.findElement( By.xpath( "//div[@class='well sidebar']/button" ) ).click(); //Click in 'Browse Files'

    //Step 2  - Go to 'widgets' and click in the created widget and press 'Edit'
    //Now we have to navegate to 'Public/cde/widgets
    driver.switchTo().defaultContent();
    elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "applicationShell" ) );
    elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//iframe[@id='browser.perspective']" ) );
    driver.switchTo().frame( "browser.perspective" );
    wait.until( ExpectedConditions.presenceOfElementLocated( By.id( "fileBrowser" ) ) );
    elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='spinner large-spinner']" ) );
    elemHelper.WaitForElementInvisibility( driver, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );

    //Public
    assertNotNull( elemHelper.WaitForElementVisibility( driver, By.xpath( "//div[@id='fileBrowserFolders']" ) ) );
    assertNotNull( elemHelper.WaitForElementVisibility( driver, By.xpath( "//div[@path='/public']" ) ) );
    driver.findElement( By.xpath( "//div[@path='/public']" ) ).findElement( By.className( "expandCollapse" ) ).click();
    //CDE
    assertNotNull( elemHelper.WaitForElementVisibility( driver, By.xpath( "//div[@path='/public/cde']" ) ) );
    driver.findElement( By.xpath( "//div[@path='/public/cde']" ) ).findElement( By.className( "expandCollapse" ) ).click();
    //Widgets
    assertNotNull( elemHelper.WaitForElementVisibility( driver, By.xpath( "//div[@path='/public/cde/widgets']" ) ) );
    driver.findElement( By.xpath( "//div[@path='/public/cde/widgets']" ) ).findElement( By.className( "title" ) ).click();

    //wait for the page load in 'fileBrowserFiles'
    elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='spinner large-spinner']" ) );
    elemHelper.WaitForElementInvisibility( driver, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );
    //Check if at least one file is displayed
    elemHelper.WaitForElementVisibility( driver, By.xpath( "//div[@id='fileBrowserFiles']/div[2]/div" ) );
    WebElement listFiles = driver.findElement( By.xpath( "//div[@id='fileBrowserFiles']/div[2]" ) );
    List<WebElement> theWidgetFiles = listFiles.findElements( By.xpath( "//div[@class='title' and contains(text(),'" + widgetName + "')]" ) );

    //Check if the widget named exist
    if ( theWidgetFiles != null ) {
      if ( theWidgetFiles.size() > 0 ) {

        Actions action = new Actions( driver );
        action.click( theWidgetFiles.get( 0 ) );
        action.build().perform();

        //Here we still in the iframe
        assertNotNull( elemHelper.WaitForElementVisibility( driver, By.id( "editButton" ) ) );
        driver.findElement( By.id( "editButton" ) ).click();

        driver.switchTo().defaultContent(); //back to the root
      }
    }

    return driver;
  }
}
