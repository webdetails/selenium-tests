package com.pentaho.ctools.cde.widgets;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.Test;

import com.pentaho.ctools.cde.widgets.utils.WidgetUtils;
import com.pentaho.ctools.suite.CToolsTestSuite;
import com.pentaho.ctools.utils.ElementHelper;

/**
 * NOTE - The test was created regarding issue CDF-308
 */
public class AddParamTableComponent {

  //Instance of the driver (browser emulator)
  private WebDriver driver = CToolsTestSuite.getDriver();
  // The name for the widget to be created
  private final String widgetName = "dummyWidgetTableComponent";
  // The name for the parameter to be added
  private final String paramName = "paramT2";
  // The name of the parameter to be assigned to the param value
  private final String paramArgName = "argT2";
  // Instance to be used on wait commands
  private final Wait<WebDriver> wait = CToolsTestSuite.getWait();
  // The base url to be append the relative url in test
  private final String baseUrl = CToolsTestSuite.getBaseUrl();
  //Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();

  @Test
  public void testCheckParametersAddingWidgetParameter() throws Exception {
    //Step 0 - Delete the widget
    WidgetUtils.RemoveWidgetByName( this.driver, this.widgetName );

    //Step 1 - Create widget with specific parameter
    this.driver = WidgetUtils.CreateWidgetWithParameter( this.driver, this.widgetName, this.paramName );

    //Step 2 - Access the widget
    this.driver = WidgetUtils.OpenWidgetEditMode( this.driver, this.wait, this.baseUrl, this.widgetName );

    //Step 3 - Add the TableComponent and go to Parameters
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe" ) ) );
    WebElement frameCDEDashboard = this.driver.findElement( By.xpath( "//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe" ) );
    this.driver.switchTo().frame( frameCDEDashboard );
    //Click in ComponentsPanel
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@class='componentsPanelButton']" ) ) );
    this.driver.findElement( By.xpath( "//div[@class='componentsPanelButton']" ) ).click();
    //Go to Others (left panel)
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[2]/h3/span" ) ) );
    this.driver.findElement( By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[2]/h3/span" ) ).click();
    //Click in 'Table Component'
    WebElement elementListedOthers = this.driver.findElement( By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[2]/div" ) );
    elementListedOthers.findElement( By.xpath( "//a[@title='table Component']" ) ).click();
    //Click in parameters
    this.elemHelper.WaitForElementVisibility( this.driver, By.id( "table-cdfdd-components-properties" ) );
    this.driver.findElement( By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[3]/td[2]" ) ).click();

    //Step 4 - Add the pair arg & value (use the parameter of the widget as value)
    //Wait for the parameters Window
    this.elemHelper.WaitForElementVisibility( this.driver, By.xpath( "//div[@id='popupbox']/div[2]" ) );
    this.elemHelper.WaitForElementVisibility( this.driver, By.xpath( "//div[@id='popupbox']/div[2]/div/div[2]/div/div/div/input[@class='StringListAddButton']" ) );
    Thread.sleep( 100 );
    //Click on Add (insert pair Arg & Value)
    this.driver.findElement( By.xpath( "//div[@id='popupbox']/div[2]/div/div[2]/div/div/div/input[@class='StringListAddButton']" ) ).click();
    //Click on '...' to add the parameter of the widget as a value.
    ////wait for the list of Args
    this.elemHelper.WaitForElementVisibility( this.driver, By.xpath( "//div[@id='popupbox']/div[2]/div/div[2]/div/div/div/div/div/div" ) );
    this.elemHelper.WaitForElementVisibility( this.driver, By.xpath( "//div[@id='popupbox']/div[2]/div/div[2]/div/div/div/div/div/div[2]" ) );
    this.wait.until( ExpectedConditions.elementToBeClickable( By.xpath( "//div[@id='popupbox']//div[@class='StringListValues']/input" ) ) );
    this.driver.findElement( By.xpath( "//div[@id='popupbox']//div[@class='StringListValues']/input" ) ).click();
    //Go to List parameters Values (in the widget) and add the parameter
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//body/div[@id='popupbox'][2]" ) ) );
    WebElement chooseParameterElement = this.driver.findElement( By.xpath( "//body/div[@id='popupbox'][2]" ) );
    assertNotNull( chooseParameterElement );
    WebElement parameterElement = chooseParameterElement.findElement( By.xpath( "//div[contains(text(), '" + this.paramName + "')]" ) );
    assertNotNull( parameterElement );
    parameterElement.click();
    // Add the Arg
    this.elemHelper.WaitForElementVisibility( this.driver, By.id( "arg_0" ) );
    this.elemHelper.WaitForElementVisibility( this.driver, By.id( "val_0" ) );
    this.elemHelper.WaitForElementVisibility( this.driver, By.id( "popup_state0_buttonOk" ) );
    this.driver.findElement( By.id( "arg_0" ) ).sendKeys( this.paramArgName );
    this.driver.findElement( By.id( "val_0" ) ).sendKeys( Keys.RETURN );
    this.driver.findElement( By.id( "popup_state0_buttonOk" ) ).click();

    //Step 5 - Save the widget
    this.wait.until( ExpectedConditions.invisibilityOfElementLocated( By.id( "popupbox" ) ) );
    this.elemHelper.WaitForElementVisibility( this.driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[3]/td[2]" ) );
    this.elemHelper.WaitForElementVisibility( this.driver, By.xpath( "//div[@id='headerLinks']/div[2]/a" ) );
    this.driver.findElement( By.xpath( "//div[@id='headerLinks']/div[2]/a" ) ).click();

    //Step 6 - Go bact to the widget and check if the parameters set previous are correct
    this.driver.switchTo().defaultContent();
    this.driver = WidgetUtils.OpenWidgetEditMode( this.driver, this.wait, this.baseUrl, this.widgetName );
    //Open the 'Components layout'
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe" ) ) );
    frameCDEDashboard = this.driver.findElement( By.xpath( "//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe" ) );
    this.driver.switchTo().frame( frameCDEDashboard );
    ////Click in ComponentsPanel
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@class='componentsPanelButton']" ) ) );
    this.driver.findElement( By.xpath( "//div[@class='componentsPanelButton']" ) ).click();
    //Expand the 'Table Components' (column 'Components')
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//table[@id='table-cdfdd-components-components']" ) ) );
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//table[@id='table-cdfdd-components-properties']" ) ) );
    this.elemHelper.WaitForElementVisibility( this.driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[3]/td/span" ) );
    this.driver.findElement( By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[3]/td/span" ) ).click();
    this.driver.findElement( By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[4]/td" ) ).click();
    //Click in Parameters (column 'Properties')
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "table-cdfdd-components-properties" ) ) );
    this.driver.findElement( By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[3]/td[2]" ) ).click();
    //Wait for the parameters Window
    this.elemHelper.WaitForElementVisibility( this.driver, By.xpath( "//div[@id='popupbox']/div[2]" ) );
    this.elemHelper.WaitForElementVisibility( this.driver, By.xpath( "//div[@id='popupbox']/div[2]/div/div[2]/div/div/div/input[@class='StringListAddButton']" ) );
    this.elemHelper.WaitForElementVisibility( this.driver, By.xpath( "//div[@id='popupbox']/div[2]/div/div[2]/div/div/div/div/div/div" ) );
    this.elemHelper.WaitForElementVisibility( this.driver, By.xpath( "//div[@id='popupbox']/div[2]/div/div[2]/div/div/div/div/div/div[2]" ) );

    /*#######################################
      EXPECT RESULT:
      Check if the arg & value add in the widget are there.
     #######################################*/
    String tempArgName = this.driver.findElement( By.xpath( "//input[@id='arg_0']" ) ).getAttribute( "value" );
    String tempValueName = this.driver.findElement( By.xpath( "//input[@id='val_0']" ) ).getAttribute( "value" );
    assertEquals( tempArgName, this.paramArgName );
    assertEquals( tempValueName, "${p:" + this.paramName + "}" );
  }
}
