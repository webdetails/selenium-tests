package com.pentaho.ctools.cde.widgets;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.gui.web.ctools.cde.utils.Widgets;

/**
 * NOTE - The test was created regarding issue CDE-302
 */
public class SimpleExtensionPoints {

  // Instance of the driver (browser emulator)
  private WebDriver driver;
  // Instance to be used on wait commands
  private final Wait<WebDriver> wait = null;
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
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@class='componentsPanelButton']" ) ) );
    this.driver.findElement( By.xpath( "//div[@class='componentsPanelButton']" ) ).click();
    //Go to Charts (left panel)
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='cdfdd-components-palletePallete']/div/h3/span" ) ) );
    this.driver.findElement( By.xpath( "//div[@id='cdfdd-components-palletePallete']/div/h3/span" ) ).click();
    //Click in 'CDA Data Source'
    final WebElement elementListedOthers = this.driver.findElement( By.xpath( "//div[@id='cdfdd-components-palletePallete']/div/div" ) );
    assertNotNull( elementListedOthers );
    elementListedOthers.findElement( By.xpath( "//a[@title='CCC Pie Chart']" ) ).click();

    //##Step 3 - In properties 'Extension Points' add a simple value
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "table-cdfdd-components-components" ) ) );
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "table-cdfdd-components-properties" ) ) );
    //Click in "Advanced Properties"
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//table[@id='table-cdfdd-components-properties']/caption/div[3][contains(text(),'Advanced Properties')]" ) ) );
    this.driver.findElement( By.xpath( "//table[@id='table-cdfdd-components-properties']/caption/div[3][contains(text(),'Advanced Properties')]" ) ).click();
    this.elemHelper.WaitForElementVisibility( this.driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody//td[@title='CCC Extension points']" ) );
    //Click in 'Extension Point'
    WebElement extensionPoint = this.driver.findElement( By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody//td[@title='CCC Extension points']/.." ) );
    //Get the two elements 'td' and click in the td element that represent the value list of 'Extension Points' property
    List<WebElement> listOfElements = extensionPoint.findElements( By.cssSelector( "td" ) );
    assertTrue( listOfElements.size() == 2 );
    WebElement valueExtension = listOfElements.get( 1 );
    valueExtension.click();
    //Wait for the popup window is displayed
    this.elemHelper.WaitForElementVisibility( this.driver, By.id( "popup" ) );
    this.elemHelper.WaitForElementVisibility( this.driver, By.cssSelector( "input.StringListAddButton" ) );
    final WebElement addButton = this.driver.findElement( By.cssSelector( "input.StringListAddButton" ) );
    final String addButtonText = addButton.getAttribute( "value" );
    assertEquals( addButtonText, "Add" );
    addButton.click();
    //Wait for the pair arg and value
    //Thread.sleep(100);
    this.elemHelper.WaitForElementPresence( this.driver, By.id( "parameters_0" ) );
    this.elemHelper.WaitForElementVisibility( this.driver, By.id( "arg_0" ) );
    this.elemHelper.WaitForElementVisibility( this.driver, By.id( "parameter_button_0" ) );
    //Clicking in '...'
    this.driver.findElement( By.id( "parameter_button_0" ) ).click();
    //Show popup
    this.elemHelper.WaitForElementVisibility( this.driver, By.cssSelector( "div.ace_line" ) );
    this.elemHelper.WaitForElementVisibility( this.driver, By.xpath( "(//button[@id='popup_state0_buttonOk'])[2]" ) );
    this.driver.findElement( By.cssSelector( "textarea.ace_text-input" ) ).clear();
    this.driver.findElement( By.cssSelector( "textarea.ace_text-input" ) ).sendKeys( this.paramValue );
    this.driver.findElement( By.xpath( "(//button[@id='popup_state0_buttonOk'])[2]" ) ).click();
    //dismiss popup
    this.wait.until( ExpectedConditions.invisibilityOfElementLocated( By.xpath( "(//div[@id='popupbox'])[2]" ) ) );
    this.elemHelper.WaitForElementVisibility( this.driver, By.id( "arg_0" ) );
    this.elemHelper.WaitForElementVisibility( this.driver, By.id( "parameter_button_0" ) );
    this.driver.findElement( By.id( "arg_0" ) ).sendKeys( this.paramArg );
    this.driver.findElement( By.xpath( "//button[@id='popup_state0_buttonOk']" ) ).click();
    this.wait.until( ExpectedConditions.invisibilityOfElementLocated( By.xpath( "//div[@id='popupbox']" ) ) );
    //Click in button SAVE
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//table[@id='table-cdfdd-components-properties']" ) ) );
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='headerLinks']/div[2]/a" ) ) );
    this.driver.findElement( By.xpath( "//div[@id='headerLinks']/div[2]/a" ) ).click();
    this.elemHelper.WaitForElementInvisibility( this.driver, By.id( "notifyBar" ) );

    //## Step 4 - Check if the value persist
    this.driver = widgets.OpenWidgetEditMode( this.driver, this.wait, this.baseUrl, this.widgetName );
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe" ) ) );
    final WebElement frameCDEDashboard = this.driver.findElement( By.xpath( "//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe" ) );
    this.driver.switchTo().frame( frameCDEDashboard );
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@class='componentsPanelButton']" ) ) );
    this.driver.findElement( By.xpath( "//div[@class='componentsPanelButton']" ) ).click();
    //Go to Extension Point
    //Expand Group
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr/td" ) ) );
    this.driver.findElement( By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr/td/span" ) ).click();
    //Click in chart
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[2]" ) ) );
    this.driver.findElement( By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[2]/td" ) ).click();
    //Display the properties
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "table-cdfdd-components-properties" ) ) );
    //Click in "Advanced Properties"
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//table[@id='table-cdfdd-components-properties']/caption/div[3][contains(text(),'Advanced Properties')]" ) ) );
    this.driver.findElement( By.xpath( "//table[@id='table-cdfdd-components-properties']/caption/div[3][contains(text(),'Advanced Properties')]" ) ).click();
    this.elemHelper.WaitForElementVisibility( this.driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody//td[@title='CCC Extension points']" ) );
    //Click in 'Extension Point'
    extensionPoint = this.driver.findElement( By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody//td[@title='CCC Extension points']/.." ) );
    //Get the two elements 'td' and click in the td element that represent the value list of 'Extension Points' property
    listOfElements = extensionPoint.findElements( By.cssSelector( "td" ) );
    assertTrue( listOfElements.size() == 2 );
    valueExtension = listOfElements.get( 1 );
    valueExtension.click();
    //Wait for the popup window is displayed
    this.elemHelper.WaitForElementVisibility( this.driver, By.id( "popup" ) );
    this.elemHelper.WaitForElementVisibility( this.driver, By.cssSelector( "input.StringListAddButton" ) );
    //Wait for the pair arg and value
    this.elemHelper.WaitForElementPresence( this.driver, By.id( "parameters_0" ) );
    this.elemHelper.WaitForElementVisibility( this.driver, By.id( "arg_0" ) );
    this.elemHelper.WaitForElementVisibility( this.driver, By.id( "parameter_button_0" ) );
    assertEquals( this.paramArg, this.driver.findElement( By.id( "arg_0" ) ).getAttribute( "value" ) );
    //Clicking in '...'
    this.driver.findElement( By.id( "parameter_button_0" ) ).click();
    this.elemHelper.WaitForElementVisibility( this.driver, By.cssSelector( "div.ace_line" ) );
    this.elemHelper.WaitForElementVisibility( this.driver, By.xpath( "(//button[@id='popup_state0_buttonOk'])[2]" ) );
    assertEquals( this.paramValue, this.driver.findElement( By.cssSelector( "div.ace_content" ) ).getText() );
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
    this.driver = widgets.OpenWidgetEditMode( this.driver, this.wait, this.baseUrl, this.widgetName );

    //##Step 2 - Change the extension point value
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe" ) ) );
    WebElement frameCDEDashboard = this.driver.findElement( By.xpath( "//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe" ) );
    this.driver.switchTo().frame( frameCDEDashboard );
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@class='componentsPanelButton']" ) ) );
    this.driver.findElement( By.xpath( "//div[@class='componentsPanelButton']" ) ).click();
    //Go to Extension Point
    //Expand Group
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr/td" ) ) );
    this.driver.findElement( By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr/td/span" ) ).click();
    //Click in chart
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[2]" ) ) );
    this.driver.findElement( By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[2]/td" ) ).click();
    //Display the properties
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "table-cdfdd-components-properties" ) ) );
    //Click in "Advanced Properties"
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//table[@id='table-cdfdd-components-properties']/caption/div[3][contains(text(),'Advanced Properties')]" ) ) );
    this.driver.findElement( By.xpath( "//table[@id='table-cdfdd-components-properties']/caption/div[3][contains(text(),'Advanced Properties')]" ) ).click();
    this.elemHelper.WaitForElementVisibility( this.driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody//td[@title='CCC Extension points']" ) );
    //Click in 'Extension Point'
    WebElement extensionPoint = this.driver.findElement( By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody//td[@title='CCC Extension points']/.." ) );
    //Get the two elements 'td' and click in the td element that represent the value list of 'Extension Points' property
    List<WebElement> listOfElements = extensionPoint.findElements( By.cssSelector( "td" ) );
    assertTrue( listOfElements.size() == 2 );
    WebElement valueExtension = listOfElements.get( 1 );
    valueExtension.click();
    //Wait for the popup window is displayed
    this.elemHelper.WaitForElementVisibility( this.driver, By.id( "popup" ) );
    this.elemHelper.WaitForElementVisibility( this.driver, By.cssSelector( "input.StringListAddButton" ) );
    //Wait for the pair arg and value
    this.elemHelper.WaitForElementPresence( this.driver, By.id( "parameters_0" ) );
    this.elemHelper.WaitForElementVisibility( this.driver, By.id( "arg_0" ) );
    this.elemHelper.WaitForElementVisibility( this.driver, By.id( "parameter_button_0" ) );
    assertEquals( this.paramArg, this.driver.findElement( By.id( "arg_0" ) ).getAttribute( "value" ) );
    //Clicking in '...'
    this.driver.findElement( By.id( "parameter_button_0" ) ).click();
    this.elemHelper.WaitForElementVisibility( this.driver, By.cssSelector( "div.ace_line" ) );
    this.elemHelper.WaitForElementVisibility( this.driver, By.xpath( "(//button[@id='popup_state0_buttonOk'])[2]" ) );
    assertEquals( this.paramValue, this.driver.findElement( By.cssSelector( "div.ace_content" ) ).getText() );
    //Add a new value
    this.driver.findElement( By.cssSelector( "textarea.ace_text-input" ) ).clear();
    this.driver.findElement( By.cssSelector( "textarea.ace_text-input" ) ).sendKeys( this.paramValueEditable );
    this.driver.findElement( By.xpath( "(//button[@id='popup_state0_buttonOk'])[2]" ) ).click();
    //dismiss popup
    this.wait.until( ExpectedConditions.invisibilityOfElementLocated( By.xpath( "(//div[@id='popupbox'])[2]" ) ) );
    this.elemHelper.WaitForElementVisibility( this.driver, By.id( "arg_0" ) );
    this.elemHelper.WaitForElementVisibility( this.driver, By.id( "parameter_button_0" ) );
    this.driver.findElement( By.xpath( "//button[@id='popup_state0_buttonOk']" ) ).click();
    this.wait.until( ExpectedConditions.invisibilityOfElementLocated( By.xpath( "//div[@id='popupbox']" ) ) );
    //Click in button SAVE
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//table[@id='table-cdfdd-components-properties']" ) ) );
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='headerLinks']/div[2]/a" ) ) );
    this.driver.findElement( By.xpath( "//div[@id='headerLinks']/div[2]/a" ) ).click();
    this.elemHelper.WaitForElementInvisibility( this.driver, By.id( "notifyBar" ) );

    //## Step 3 - Check if the value persist
    this.driver = widgets.OpenWidgetEditMode( this.driver, this.wait, this.baseUrl, this.widgetName );
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe" ) ) );
    frameCDEDashboard = this.driver.findElement( By.xpath( "//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe" ) );
    this.driver.switchTo().frame( frameCDEDashboard );
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@class='componentsPanelButton']" ) ) );
    this.driver.findElement( By.xpath( "//div[@class='componentsPanelButton']" ) ).click();
    //Go to Extension Point
    //Expand Group
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr/td" ) ) );
    this.driver.findElement( By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr/td/span" ) ).click();
    //Click in chart
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[2]" ) ) );
    this.driver.findElement( By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[2]/td" ) ).click();
    //Display the properties
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "table-cdfdd-components-properties" ) ) );
    //Click in "Advanced Properties"
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//table[@id='table-cdfdd-components-properties']/caption/div[3][contains(text(),'Advanced Properties')]" ) ) );
    this.driver.findElement( By.xpath( "//table[@id='table-cdfdd-components-properties']/caption/div[3][contains(text(),'Advanced Properties')]" ) ).click();
    this.elemHelper.WaitForElementVisibility( this.driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody//td[@title='CCC Extension points']" ) );
    //Click in 'Extension Point'
    extensionPoint = this.driver.findElement( By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody//td[@title='CCC Extension points']/.." ) );
    //Get the two elements 'td' and click in the td element that represent the value list of 'Extension Points' property
    listOfElements = extensionPoint.findElements( By.cssSelector( "td" ) );
    assertTrue( listOfElements.size() == 2 );
    valueExtension = listOfElements.get( 1 );
    valueExtension.click();
    //Wait for the popup window is displayed
    this.elemHelper.WaitForElementVisibility( this.driver, By.id( "popup" ) );
    this.elemHelper.WaitForElementVisibility( this.driver, By.cssSelector( "input.StringListAddButton" ) );
    //Wait for the pair arg and value
    this.elemHelper.WaitForElementPresence( this.driver, By.id( "parameters_0" ) );
    this.elemHelper.WaitForElementVisibility( this.driver, By.id( "arg_0" ) );
    this.elemHelper.WaitForElementVisibility( this.driver, By.id( "parameter_button_0" ) );
    assertEquals( this.paramArg, this.driver.findElement( By.id( "arg_0" ) ).getAttribute( "value" ) );
    //Clicking in '...'
    this.driver.findElement( By.id( "parameter_button_0" ) ).click();
    this.elemHelper.WaitForElementVisibility( this.driver, By.cssSelector( "div.ace_line" ) );
    this.elemHelper.WaitForElementVisibility( this.driver, By.xpath( "(//button[@id='popup_state0_buttonOk'])[2]" ) );
    assertEquals( this.paramValueEditable, this.driver.findElement( By.cssSelector( "div.ace_content" ) ).getText() );
  }
}
