package org.pentaho.ctools.cde.widgets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.cde.widgets.utils.WidgetUtils;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;

/**
 * NOTE - The test was created regarding issue CDF-308
 */
public class AddParamTableComponent {
  // Instance of the driver (browser emulator)
  private WebDriver driver;
  // Instance to be used on wait commands
  private Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private String baseUrl;
  // The name for the widget to be created
  private String widgetName = "dummyWidgetTableComponent";
  // The name for the parameter to be added
  private String paramName = "paramT2";
  // The name of the parameter to be assigned to the param value
  private String paramArgName = "argT2";

  @Before
  public void setUp() throws Exception {
    this.driver = CToolsTestSuite.getDriver();
    this.wait = CToolsTestSuite.getWait();
    this.baseUrl = CToolsTestSuite.getBaseUrl();
  }

  @Test( timeout = 60000 )
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
    ElementHelper.WaitForElementVisibility( this.driver, By.id( "table-cdfdd-components-properties" ) );
    this.driver.findElement( By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[3]/td[2]" ) ).click();

    //Step 4 - Add the pair arg & value (use the parameter of the widget as value)
    //Wait for the parameters Window
    ElementHelper.WaitForElementVisibility( this.driver, By.xpath( "//div[@id='popupbox']/div[2]" ) );
    ElementHelper.WaitForElementVisibility( this.driver, By.xpath( "//div[@id='popupbox']/div[2]/div/div[2]/div/div/div/input[@class='StringListAddButton']" ) );
    Thread.sleep( 100 );
    //Click on Add (insert pair Arg & Value)
    this.driver.findElement( By.xpath( "//div[@id='popupbox']/div[2]/div/div[2]/div/div/div/input[@class='StringListAddButton']" ) ).click();
    //Click on '...' to add the parameter of the widget as a value.
    ////wait for the list of Args
    ElementHelper.WaitForElementVisibility( this.driver, By.xpath( "//div[@id='popupbox']/div[2]/div/div[2]/div/div/div/div/div/div" ) );
    ElementHelper.WaitForElementVisibility( this.driver, By.xpath( "//div[@id='popupbox']/div[2]/div/div[2]/div/div/div/div/div/div[2]" ) );
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
    ElementHelper.WaitForElementVisibility( this.driver, By.id( "arg_0" ) );
    ElementHelper.WaitForElementVisibility( this.driver, By.id( "val_0" ) );
    ElementHelper.WaitForElementVisibility( this.driver, By.id( "popup_state0_buttonOk" ) );
    this.driver.findElement( By.id( "arg_0" ) ).sendKeys( this.paramArgName );
    this.driver.findElement( By.id( "val_0" ) ).sendKeys( Keys.RETURN );
    this.driver.findElement( By.id( "popup_state0_buttonOk" ) ).click();

    //Step 5 - Save the widget
    this.wait.until( ExpectedConditions.invisibilityOfElementLocated( By.id( "popupbox" ) ) );
    ElementHelper.WaitForElementVisibility( this.driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[3]/td[2]" ) );
    ElementHelper.WaitForElementVisibility( this.driver, By.xpath( "//div[@id='headerLinks']/div[2]/a" ) );
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
    ElementHelper.WaitForElementVisibility( this.driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[3]/td/span" ) );
    this.driver.findElement( By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[3]/td/span" ) ).click();
    this.driver.findElement( By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[4]/td" ) ).click();
    //Click in Parameters (column 'Properties')
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "table-cdfdd-components-properties" ) ) );
    this.driver.findElement( By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[3]/td[2]" ) ).click();
    //Wait for the parameters Window
    ElementHelper.WaitForElementVisibility( this.driver, By.xpath( "//div[@id='popupbox']/div[2]" ) );
    ElementHelper.WaitForElementVisibility( this.driver, By.xpath( "//div[@id='popupbox']/div[2]/div/div[2]/div/div/div/input[@class='StringListAddButton']" ) );
    ElementHelper.WaitForElementVisibility( this.driver, By.xpath( "//div[@id='popupbox']/div[2]/div/div[2]/div/div/div/div/div/div" ) );
    ElementHelper.WaitForElementVisibility( this.driver, By.xpath( "//div[@id='popupbox']/div[2]/div/div[2]/div/div/div/div/div/div[2]" ) );

    /*#######################################
      EXPECT RESULT:
      Check if the arg & value add in the widget are there.
     #######################################*/
    String tempArgName = this.driver.findElement( By.xpath( "//input[@id='arg_0']" ) ).getAttribute( "value" );
    String tempValueName = this.driver.findElement( By.xpath( "//input[@id='val_0']" ) ).getAttribute( "value" );
    assertEquals( tempArgName, this.paramArgName );
    assertEquals( tempValueName, "${p:" + this.paramName + "}" );
  }

  @After
  public void tearDown() {
    //To use after test case run.
  }
}
