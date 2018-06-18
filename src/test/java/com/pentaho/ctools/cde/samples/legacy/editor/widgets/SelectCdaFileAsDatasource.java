package com.pentaho.ctools.cde.samples.legacy.editor.widgets;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.gui.web.ctools.cde.utils.Widgets;

/**
 * NOTE - The test was created regarding issue CDE-140
 */
public class SelectCdaFileAsDatasource {
  // Instance of the driver (browser emulator)
  private WebDriver driver;
  // The base url to be append the relative url in test
  private final String baseUrl = null;
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // The name for the widget to be created
  private final String widgetName = "dummyWidgetSelectCdaDatasource";

  /**
   * Where we do stuff (like: clean, prepare data) before start testing.
   */
  @BeforeClass
  public void setUpClass() {
    Widgets widgets = new Widgets();
    // ##Step 0 - Delete the widget
    widgets.RemoveWidgetByName( this.driver, this.widgetName );
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Select CDA File As Datasource
   * Description:
   *    When we insert a CDA file as data source, the path shall be a full path.
   * Steps:
   *    1. Create a widget
   *    2. Go to the widget and assign a data source file (CDA)
   *    3. Go back the widget and check if persist the data source field
   *
   * @throws Exception
   */
  @Test
  public void tc1_SelectCdaFileAsDatasource_PathOfCdaFileCorrect() throws Exception {
    Widgets widgets = new Widgets();

    // ##Step 1 - Create widget with specific parameter
    widgets.CreateWidget( this.driver, this.widgetName );

    // ##Step 2 - Access the widget
    this.driver = widgets.OpenWidgetEditMode( this.driver, this.baseUrl, this.widgetName );

    // ##Step 3 - Click in Datasources panel and add a CDA Datasource
    WebElement frameCDEDashboard = this.elemHelper.FindElement( driver, ( By.xpath( "//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe" ) ) );
    this.elemHelper.SwitchToFrame( driver, frameCDEDashboard );
    // Click in Datasources panel
    this.elemHelper.Click( driver, By.xpath( "//div[@class='datasourcesPanelButton']" ) );
    // Go to Community Data Access (left panel)
    this.elemHelper.Click( driver, By.xpath( "//div[@id='cdfdd-datasources-palletePallete']/div[2]/h3/span" ) );
    // Click in 'CDA Data Source'
    final WebElement elementListedOthers = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='cdfdd-datasources-palletePallete']/div[2]/div" ) );
    elementListedOthers.findElement( By.xpath( "//a[@title='CDA Data Source']" ) ).click();

    // ##Step 4 - Add the cda file
    // Click in the property to add the file
    this.elemHelper.Click( driver, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[4]/td[2]/button" ) );
    assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "popupbox" ) ) );
    assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "popup_state_browse" ) ) );
    // Click in 'Public'
    final WebElement listFolders = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='container_id']" ) );
    listFolders.findElement( By.xpath( "//a[@rel='public/']" ) ).click();
    // Click in 'plugin-samples'
    assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@rel='public/plugin-samples/']" ) ) );
    this.elemHelper.Click( driver, By.xpath( "//a[@rel='public/plugin-samples/']" ) );
    // Click in 'cda'
    assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@rel='public/plugin-samples/cda/']" ) ) );
    this.elemHelper.Click( driver, By.xpath( "//a[@rel='public/plugin-samples/cda/']" ) );
    // Click in 'cdafiles'
    assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@rel='public/plugin-samples/cda/cdafiles/']" ) ) );
    this.elemHelper.FindElement( driver, By.xpath( "//a[@rel='public/plugin-samples/cda/cdafiles/']" ) );
    // Select a file
    assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@rel='public/plugin-samples/cda/cdafiles/compoundJoin.cda']" ) ) );
    this.elemHelper.FindElement( driver, By.xpath( "//a[@rel='public/plugin-samples/cda/cdafiles/compoundJoin.cda']" ) );
    // Click OK
    this.elemHelper.Click( driver, By.id( "popup_browse_buttonOk" ) );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.id( "popup_browse_buttonOk" ) );
    // SAVE the widget
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='headerLinks']/div[2]/a" ) );
    this.elemHelper.Click( driver, By.xpath( "//div[@id='headerLinks']/div[2]/a" ) );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.id( "notifyBar" ) );

    // ## Step 5 - Check if the file persist after SAVE widget
    this.driver = widgets.OpenWidgetEditMode( this.driver, this.baseUrl, this.widgetName );
    // Open
    frameCDEDashboard = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe" ) );
    this.elemHelper.SwitchToFrame( driver, frameCDEDashboard );
    // Click in Datasources panel
    this.elemHelper.Click( driver, By.xpath( "//div[@class='datasourcesPanelButton']" ) );
    // Click in CDA Data Source in the list of Datasources
    // Expand group
    this.elemHelper.Click( driver, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr/td/span" ) );
    // Click in CDA Data Source
    this.elemHelper.Click( driver, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[2]/td" ) );

    /*#######################################
      EXPECT RESULT:
      Path shall start with '/'
     #######################################*/
    assertEquals( "Path", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[4]/td" ) ) );
    assertEquals( "/public/plugin-samples/cda/cdafiles/compoundJoin.cda", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[4]/td[2]/div" ) ) );
  }
}
