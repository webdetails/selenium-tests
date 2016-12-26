package com.pentaho.ctools.cde.widgets;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.gui.web.ctools.cde.utils.Widgets;

/**
 * NOTE - The test was created regarding issue CDF-308
 */
public class AddParamTableComponent {

  //Instance of the driver (browser emulator)
  private WebDriver driver = null;
  // The name for the widget to be created
  private final String widgetName = "dummyWidgetTableComponent";
  // The name for the parameter to be added
  private final String paramName = "paramT2";
  // The name of the parameter to be assigned to the param value
  private final String paramArgName = "argT2";
  // The base url to be append the relative url in test
  private final String baseUrl = null;
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();

  @Test
  public void testCheckParametersAddingWidgetParameter() throws Exception {
    //Step 0 - Delete the widget
    Widgets widgets = new Widgets();
    widgets.RemoveWidgetByName( this.driver, this.widgetName );

    //Step 1 - Create widget with specific parameter
    this.driver = widgets.CreateWidgetWithParameter( this.driver, this.widgetName, this.paramName );

    //Step 2 - Access the widget
    this.driver = widgets.OpenWidgetEditMode( this.driver, this.baseUrl, this.widgetName );

    WebElement frameCDEDashboard = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe" ) );
    this.elemHelper.SwitchToFrame( driver, frameCDEDashboard );
    //Click in ComponentsPanel
    this.elemHelper.Click( driver, By.xpath( "//div[@class='componentsPanelButton']" ) );
    // Go to Others (left panel)
    this.elemHelper.Click( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[2]/h3/span" ) );
    //Click in 'Table Component'
    WebElement elementListedOthers = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[2]/div" ) );
    elementListedOthers.findElement( By.xpath( "//a[@title='table Component']" ) ).click();
    //Click in parameters
    this.elemHelper.Click( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[3]/td[2]" ) );

    //Step 4 - Add the pair arg & value (use the parameter of the widget as value)
    //Wait for the parameters Window
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='popupbox']/div[2]" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='popupbox']/div[2]/div/div[2]/div/div/div/input[@class='StringListAddButton']" ) );
    Thread.sleep( 100 );
    //Click on Add (insert pair Arg & Value)
    this.elemHelper.Click( driver, By.xpath( "//div[@id='popupbox']/div[2]/div/div[2]/div/div/div/input[@class='StringListAddButton']" ) );
    //Click on '...' to add the parameter of the widget as a value.
    ////wait for the list of Args
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='popupbox']/div[2]/div/div[2]/div/div/div/div/div/div" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='popupbox']/div[2]/div/div[2]/div/div/div/div/div/div[2]" ) );
    this.elemHelper.Click( driver, By.xpath( "//div[@id='popupbox']//div[@class='StringListValues']/input" ) );
    // Go to List parameters Values (in the widget) and add the parameter
    WebElement chooseParameterElement = this.elemHelper.FindElement( driver, By.xpath( "//body/div[@id='popupbox'][2]" ) );
    assertNotNull( chooseParameterElement );
    WebElement parameterElement = chooseParameterElement.findElement( By.xpath( "//div[contains(text(), '" + this.paramName + "')]" ) );
    assertNotNull( parameterElement );
    parameterElement.click();
    // Add the Arg
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "arg_0" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "val_0" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "popup_state0_buttonOk" ) );
    this.elemHelper.FindElement( driver, By.id( "arg_0" ) ).sendKeys( this.paramArgName );
    this.elemHelper.FindElement( driver, By.id( "val_0" ) ).sendKeys( Keys.RETURN );
    this.elemHelper.Click( driver, By.id( "popup_state0_buttonOk" ) );

    //Step 5 - Save the widget
    this.elemHelper.WaitForElementInvisibility( driver, By.id( "popupbox" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[3]/td[2]" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='headerLinks']/div[2]/a" ) );
    this.elemHelper.Click( driver, By.xpath( "//div[@id='headerLinks']/div[2]/a" ) );

    //Step 6 - Go bact to the widget and check if the parameters set previous are correct
    this.elemHelper.SwitchToDefault( driver );
    this.driver = widgets.OpenWidgetEditMode( this.driver, this.baseUrl, this.widgetName );
    //Open the 'Components layout'
    frameCDEDashboard = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe" ) );
    this.elemHelper.SwitchToFrame( driver, frameCDEDashboard );
    ////Click in ComponentsPanel
    this.elemHelper.Click( driver, By.xpath( "//div[@class='componentsPanelButton']" ) );
    //Expand the 'Table Components' (column 'Components')
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[3]/td/span" ) );
    this.elemHelper.Click( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[3]/td/span" ) );
    this.elemHelper.Click( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[4]/td" ) );
    //Click in Parameters (column 'Properties')
    this.elemHelper.Click( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[3]/td[2]" ) );
    //Wait for the parameters Window
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='popupbox']/div[2]" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='popupbox']/div[2]/div/div[2]/div/div/div/input[@class='StringListAddButton']" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='popupbox']/div[2]/div/div[2]/div/div/div/div/div/div" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='popupbox']/div[2]/div/div[2]/div/div/div/div/div/div[2]" ) );

    /*#######################################
      EXPECT RESULT:
      Check if the arg & value add in the widget are there.
     #######################################*/
    String tempArgName = this.elemHelper.GetAttribute( driver, By.xpath( "//input[@id='arg_0']" ), "value" );
    String tempValueName = this.elemHelper.GetAttribute( driver, By.xpath( "//input[@id='val_0']" ), "value" );
    assertEquals( tempArgName, this.paramArgName );
    assertEquals( tempValueName, "${p:" + this.paramName + "}" );
  }
}
