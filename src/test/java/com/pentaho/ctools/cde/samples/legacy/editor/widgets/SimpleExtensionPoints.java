package com.pentaho.ctools.cde.samples.legacy.editor.widgets;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.gui.web.ctools.cde.utils.Widgets;

/**
 * NOTE - The test was created regarding issue CDE-302
 */
public class SimpleExtensionPoints {

  // Instance of the driver (browser emulator)
  private WebDriver driver;
  // The base url to be append the relative url in test
  private final String baseUrl = null;
  // The name for the widget to be created
  private final String widgetName = "dummyWidgetExtensionPoint";
  // The param to add in Extension Point
  private final String paramArg = "arg1";
  // The Extension point value
  private final String paramValue = "My Extension Point";
  // The new value for the extension point
  private final String paramValueEditable = "My New Editable Extension Point";
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Add an Extension Point
   * Description:
   *    In this test case we pretend to add a simple extension point and then
   *    validate the persistence.
   * Steps:
   *    1. Create a widget
   *    2. Add a chart
   *    3. In properties 'Extension Points' add a simple value
   *    4. Check if the value persist
   *
   * @throws Exception
   */
  @Test
  public void tc1_AddSimpleExtensionPoint_ExtensionPointAdded() throws Exception {
    Widgets widgets = new Widgets();
    //##Step 0 - Delete the widget
    widgets.RemoveWidgetByName( this.driver, this.widgetName );

    //##Step 1 - Create a widget
    widgets.CreateWidget( this.driver, this.widgetName );

    //##Step 2 - Add a chart
    // Click in Datasources panel and add a CDA Datasource
    //Click in Components panel
    this.elemHelper.Click( driver, By.xpath( "//div[@class='componentsPanelButton']" ) );
    // Go to Charts (left panel)
    this.elemHelper.Click( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div/h3/span" ) );
    //Click in 'CDA Data Source'
    final WebElement elementListedOthers = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div/div" ) );
    assertNotNull( elementListedOthers );
    elementListedOthers.findElement( By.xpath( "//a[@title='CCC Pie Chart']" ) ).click();

    //##Step 3 - In properties 'Extension Points' add a simple value
    //Click in "Advanced Properties"
    this.elemHelper.Click( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/caption/div[3][contains(text(),'Advanced Properties')]" ) );
    //Click in 'Extension Point'
    WebElement extensionPoint = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody//td[@title='CCC Extension points']/.." ) );
    //Get the two elements 'td' and click in the td element that represent the value list of 'Extension Points' property
    List<WebElement> listOfElements = extensionPoint.findElements( By.cssSelector( "td" ) );
    assertTrue( listOfElements.size() == 2 );
    WebElement valueExtension = listOfElements.get( 1 );
    valueExtension.click();
    //Wait for the popup window is displayed
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "popup" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.cssSelector( "input.StringListAddButton" ) );
    final WebElement addButton = this.elemHelper.FindElement( driver, By.cssSelector( "input.StringListAddButton" ) );
    final String addButtonText = addButton.getAttribute( "value" );
    assertEquals( addButtonText, "Add" );
    addButton.click();
    //Wait for the pair arg and value
    //Thread.sleep(100);
    this.elemHelper.WaitForElementPresence( this.driver, By.id( "parameters_0" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "arg_0" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "parameter_button_0" ) );
    //Clicking in '...'
    this.elemHelper.Click( driver, By.id( "parameter_button_0" ) );
    //Show popup
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.cssSelector( "div.ace_line" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "(//button[@id='popup_state0_buttonOk'])[2]" ) );
    this.elemHelper.FindElement( driver, By.cssSelector( "textarea.ace_text-input" ) ).clear();
    this.elemHelper.FindElement( driver, By.cssSelector( "textarea.ace_text-input" ) ).sendKeys( this.paramValue );
    this.elemHelper.Click( driver, By.xpath( "(//button[@id='popup_state0_buttonOk'])[2]" ) );
    //dismiss popup
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "(//div[@id='popupbox'])[2]" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "arg_0" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "parameter_button_0" ) );
    this.elemHelper.FindElement( driver, By.id( "arg_0" ) ).sendKeys( this.paramArg );
    this.elemHelper.Click( driver, By.xpath( "//button[@id='popup_state0_buttonOk']" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='popupbox']" ) );
    //Click in button SAVE
    this.elemHelper.Click( driver, By.xpath( "//div[@id='headerLinks']/div[2]/a" ) );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.id( "notifyBar" ) );

    //## Step 4 - Check if the value persist
    this.driver = widgets.OpenWidgetEditMode( this.driver, this.baseUrl, this.widgetName );
    final WebElement frameCDEDashboard = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe" ) );
    this.elemHelper.SwitchToFrame( driver, frameCDEDashboard );
    this.elemHelper.Click( driver, By.xpath( "//div[@class='componentsPanelButton']" ) );
    // Go to Extension Point
    //Expand Group
    this.elemHelper.Click( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr/td/span" ) );
    //Click in chart
    this.elemHelper.Click( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[2]/td" ) );
    //Display the properties
    //Click in "Advanced Properties"
    this.elemHelper.Click( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/caption/div[3][contains(text(),'Advanced Properties')]" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody//td[@title='CCC Extension points']" ) );
    //Click in 'Extension Point'
    extensionPoint = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody//td[@title='CCC Extension points']/.." ) );
    //Get the two elements 'td' and click in the td element that represent the value list of 'Extension Points' property
    listOfElements = extensionPoint.findElements( By.cssSelector( "td" ) );
    assertTrue( listOfElements.size() == 2 );
    valueExtension = listOfElements.get( 1 );
    valueExtension.click();
    //Wait for the popup window is displayed
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "popup" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.cssSelector( "input.StringListAddButton" ) );
    //Wait for the pair arg and value
    this.elemHelper.WaitForElementPresence( this.driver, By.id( "parameters_0" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "arg_0" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "parameter_button_0" ) );
    assertEquals( this.paramArg, this.elemHelper.GetAttribute( driver, By.id( "arg_0" ), "value" ) );
    //Clicking in '...'
    this.elemHelper.Click( driver, By.id( "parameter_button_0" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.cssSelector( "div.ace_line" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "(//button[@id='popup_state0_buttonOk'])[2]" ) );
    assertEquals( this.paramValue, this.elemHelper.WaitForElementPresentGetText( driver, By.cssSelector( "div.ace_content" ) ) );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Edit an Extension Point
   * Description:
   *    In this test case we pretend to edit a simple extension point and then
   *    validate the persistance.
   * Steps:
   *    1. Access to widget in Edit mode
   *    2. Change the extension point value
   *    3. Check if the changed persist
   *
   * @throws Exception
   */
  @Test
  public void tc2_EditSimpleExtensionPoint_ExtensionPointEditable() throws Exception {
    Widgets widgets = new Widgets();
    //##Step 1 - Access to widget in Edit mode
    this.driver = widgets.OpenWidgetEditMode( this.driver, this.baseUrl, this.widgetName );

    //##Step 2 - Change the extension point value
    WebElement frameCDEDashboard = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe" ) );
    this.elemHelper.SwitchToFrame( driver, frameCDEDashboard );
    this.elemHelper.Click( driver, By.xpath( "//div[@class='componentsPanelButton']" ) );
    // Go to Extension Point
    //Expand Group
    this.elemHelper.Click( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr/td/span" ) );
    //Click in chart
    this.elemHelper.Click( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[2]/td" ) );
    //Display the properties
    //Click in "Advanced Properties"
    this.elemHelper.Click( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/caption/div[3][contains(text(),'Advanced Properties')]" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody//td[@title='CCC Extension points']" ) );
    //Click in 'Extension Point'
    WebElement extensionPoint = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody//td[@title='CCC Extension points']/.." ) );
    //Get the two elements 'td' and click in the td element that represent the value list of 'Extension Points' property
    List<WebElement> listOfElements = extensionPoint.findElements( By.cssSelector( "td" ) );
    assertTrue( listOfElements.size() == 2 );
    WebElement valueExtension = listOfElements.get( 1 );
    valueExtension.click();
    //Wait for the popup window is displayed
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "popup" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.cssSelector( "input.StringListAddButton" ) );
    //Wait for the pair arg and value
    this.elemHelper.WaitForElementPresence( this.driver, By.id( "parameters_0" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "arg_0" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "parameter_button_0" ) );
    assertEquals( this.paramArg, this.elemHelper.GetAttribute( driver, By.id( "arg_0" ), "value" ) );
    //Clicking in '...'
    this.elemHelper.Click( driver, By.id( "parameter_button_0" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.cssSelector( "div.ace_line" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "(//button[@id='popup_state0_buttonOk'])[2]" ) );
    assertEquals( this.paramValue, this.elemHelper.WaitForElementPresentGetText( driver, By.cssSelector( "div.ace_content" ) ) );
    //Add a new value
    this.elemHelper.FindElement( driver, By.cssSelector( "textarea.ace_text-input" ) ).clear();
    this.elemHelper.FindElement( driver, By.cssSelector( "textarea.ace_text-input" ) ).sendKeys( this.paramValueEditable );
    this.elemHelper.Click( driver, By.xpath( "(//button[@id='popup_state0_buttonOk'])[2]" ) );
    //dismiss popup
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "(//div[@id='popupbox'])[2]" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "arg_0" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "parameter_button_0" ) );
    this.elemHelper.Click( driver, By.xpath( "//button[@id='popup_state0_buttonOk']" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='popupbox']" ) );
    //Click in button SAVE
    this.elemHelper.Click( driver, By.xpath( "//div[@id='headerLinks']/div[2]/a" ) );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.id( "notifyBar" ) );

    //## Step 3 - Check if the value persist
    this.driver = widgets.OpenWidgetEditMode( this.driver, this.baseUrl, this.widgetName );
    frameCDEDashboard = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe" ) );
    this.elemHelper.SwitchToFrame( driver, frameCDEDashboard );
    this.elemHelper.Click( driver, By.xpath( "//div[@class='componentsPanelButton']" ) );
    // Go to Extension Point
    //Expand Group
    this.elemHelper.Click( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr/td/span" ) );
    //Click in chart
    this.elemHelper.Click( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[2]/td" ) );
    //Display the properties
    //Click in "Advanced Properties"
    this.elemHelper.Click( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/caption/div[3][contains(text(),'Advanced Properties')]" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody//td[@title='CCC Extension points']" ) );
    //Click in 'Extension Point'
    extensionPoint = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody//td[@title='CCC Extension points']/.." ) );
    //Get the two elements 'td' and click in the td element that represent the value list of 'Extension Points' property
    listOfElements = extensionPoint.findElements( By.cssSelector( "td" ) );
    assertTrue( listOfElements.size() == 2 );
    valueExtension = listOfElements.get( 1 );
    valueExtension.click();
    //Wait for the popup window is displayed
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "popup" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.cssSelector( "input.StringListAddButton" ) );
    //Wait for the pair arg and value
    this.elemHelper.WaitForElementPresence( this.driver, By.id( "parameters_0" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "arg_0" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "parameter_button_0" ) );
    assertEquals( this.paramArg, this.elemHelper.GetAttribute( driver, By.id( "arg_0" ), "value" ) );
    //Clicking in '...'
    this.elemHelper.Click( driver, By.id( "parameter_button_0" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.cssSelector( "div.ace_line" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "(//button[@id='popup_state0_buttonOk'])[2]" ) );
    assertEquals( this.paramValueEditable, this.elemHelper.WaitForElementPresentGetText( driver, By.cssSelector( "div.ace_content" ) ) );
  }
}
