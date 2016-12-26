package com.pentaho.ctools.cde.widgets;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.gui.web.ctools.cde.utils.Widgets;

/**
 * NOTE - The test was created regarding issue CDF-318
 */
public class CreateWidget {
  // Instance of the driver (browser emulator)
  private WebDriver driver;
  // The base url to be append the relative url in test
  private String baseUrl;
  // The name for the widget to be created
  private String widgetName = "dummyCreateWidget";
  // The name for the parameter to be added
  private String paramName = "paramCreateWidget";
  // The wrap class of Webdriver.
  private final ElementHelper elemHelper = new ElementHelper();

  @Test
  public void testCreateWidget() throws Exception {
    //Step 0 - Delete the widget
    Widgets widgets = new Widgets();
    widgets.RemoveWidgetByName( this.driver, this.widgetName );

    //Create widget with specific parameter
    this.driver = widgets.CreateWidgetWithParameter( this.driver, this.widgetName, this.paramName );
  }

  @Test
  public void checkExistentWidgetAndParameter() throws Exception {
    //Resuming Steps
    // 1. open the widget
    // 2. check if the parameter exist in settings
    // 3. check if the widget exist in 'widgets' at Component Layout
    Widgets widgets = new Widgets();
    this.driver = widgets.OpenWidgetEditMode( this.driver, this.baseUrl, this.widgetName );

    //Step 3 - Check if the parameter exist in 'Settings'
    //Move to the iframe
    WebElement frameCDEDashboard = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe" ) );
    this.elemHelper.SwitchToFrame( driver, frameCDEDashboard );
    assertTrue( this.elemHelper.FindElement( driver, By.xpath( "//div[@class='layoutPanelButton']" ) ).isEnabled() );
    //Press 'Settings'
    this.elemHelper.Click( driver, By.xpath( "//div[@id='headerLinks']/div[5]/a" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='popup']" ) );
    assertNotNull( this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//span[@id='widgetParameters']/div/input" ) ) );
    //The parameter MUST be equal to the one set
    assertEquals( this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//span[@id='widgetParameters']/div/span" ) ), this.paramName );
    //Press on the check box
    assertTrue( this.elemHelper.FindElement( driver, By.xpath( "//span[@id='widgetParameters']/div/input" ) ).isSelected() );
    //Press button 'Cancel'
    this.elemHelper.Click( driver, By.xpath( "//div[@class='popupbuttons']/button[@id='popup_state0_buttonCancel']" ) );

    //Step 4 - Click on Component Panel and check if the widget is listed
    this.elemHelper.Click( driver, By.xpath( "//div[@class='componentsPanelButton']" ) );
    this.elemHelper.Click( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[9]/h3/span" ) );
    //Getting the element where the widget created is displayed
    WebElement listOfWidgets = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[9]/div" ) );
    //Check if the widget created is listed
    WebElement theWidgetCreated = listOfWidgets.findElement( By.xpath( "//a[@class='tooltip' and contains(text(),'" + this.widgetName + "')]" ) );
    assertNotNull( theWidgetCreated );
    assertEquals( theWidgetCreated.getText(), this.widgetName );

    //Step 5 - Click in the widget created and check if the widget is add at Components column and Properties
    theWidgetCreated.click();
    assertEquals( this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[4]/td" ) ), this.widgetName + " Widget" );
    assertEquals( this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[2]/td" ) ), "Parameter " + this.paramName );
  }
}
