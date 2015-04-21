package org.pentaho.ctools.cde.widgets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.cde.widgets.utils.WidgetUtils;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;

/**
 * NOTE - The test was created regarding issue CDE-140
 */
public class SelectCdaFileAsDatasource {

  // Instance of the driver (browser emulator)
  private WebDriver driver;
  // Instance to be used on wait commands
  private Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private String baseUrl;
  // The name for the widget to be created
  private final String widgetName = "dummyWidgetSelectCdaDatasource";

  @Before
  public void setUp() throws Exception {
    this.driver = CToolsTestSuite.getDriver();
    this.wait = CToolsTestSuite.getWait();
    this.baseUrl = CToolsTestSuite.getBaseUrl();

    this.init();
  }

  /**
   * Where we do stuff (like: clean, prepare data) before start testing.
   */
  public void init() {
    // ##Step 0 - Delete the widget
    WidgetUtils.RemoveWidgetByName( this.driver, this.widgetName );
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
   *    3. Go back the widget and check if presist the data source fiel
   *
   * @throws Exception
   */
  @Test( timeout = 60000 )
  public void tc1_SelectCdaFileAsDatasource_PathOfCdaFileCorrect() throws Exception {
    // ##Step 1 - Create widget with specific parameter
    this.driver = WidgetUtils.CreateWidget( this.driver, this.widgetName );

    // ##Step 2 - Access the widget
    this.driver = WidgetUtils.OpenWidgetEditMode( this.driver, this.wait, this.baseUrl, this.widgetName );

    // ##Step 3 - Click in Datasources panel and add a CDA Datasource
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe" ) ) );
    WebElement frameCDEDashboard = this.driver.findElement( By.xpath( "//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe" ) );
    this.driver.switchTo().frame( frameCDEDashboard );
    // Click in Datasources panel
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@class='datasourcesPanelButton']" ) ) );
    this.driver.findElement( By.xpath( "//div[@class='datasourcesPanelButton']" ) ).click();
    // Go to Community Data Access (left panel)
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='cdfdd-datasources-palletePallete']/div[2]/h3/span" ) ) );
    this.driver.findElement( By.xpath( "//div[@id='cdfdd-datasources-palletePallete']/div[2]/h3/span" ) ).click();
    // Click in 'CDA Data Source'
    final WebElement elementListedOthers = this.driver.findElement( By.xpath( "//div[@id='cdfdd-datasources-palletePallete']/div[2]/div" ) );
    elementListedOthers.findElement( By.xpath( "//a[@title='CDA Data Source']" ) ).click();

    // ##Step 4 - Add the cda file
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "table-cdfdd-datasources-properties" ) ) );
    // Click in the property to add the file
    ElementHelper.WaitForElementVisibility( this.driver, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[4]/td[2]/button" ) );
    this.driver.findElement( By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[4]/td[2]/button" ) ).click();
    assertNotNull( ElementHelper.WaitForElementVisibility( this.driver, By.id( "popupbox" ) ) );
    assertNotNull( ElementHelper.WaitForElementVisibility( this.driver, By.id( "popup_state_browse" ) ) );
    // Click in 'Public'
    this.wait.until( ExpectedConditions.visibilityOfAllElementsLocatedBy( By.id( "container_id" ) ) );
    final WebElement listFolders = this.driver.findElement( By.xpath( "//div[@id='container_id']" ) );
    listFolders.findElement( By.xpath( "//a[@rel='public/']" ) ).click();
    // Click in 'plugin-samples'
    this.wait.until( ExpectedConditions.visibilityOfAllElementsLocatedBy( By.id( "container_id" ) ) );
    assertNotNull( ElementHelper.WaitForElementVisibility( this.driver, By.xpath( "//a[@rel='public/plugin-samples/']" ) ) );
    this.driver.findElement( By.xpath( "//a[@rel='public/plugin-samples/']" ) ).click();
    // Click in 'cda'
    assertNotNull( ElementHelper.WaitForElementVisibility( this.driver, By.xpath( "//a[@rel='public/plugin-samples/cda/']" ) ) );
    this.driver.findElement( By.xpath( "//a[@rel='public/plugin-samples/cda/']" ) ).click();
    // Click in 'cdafiles'
    assertNotNull( ElementHelper.WaitForElementVisibility( this.driver, By.xpath( "//a[@rel='public/plugin-samples/cda/cdafiles/']" ) ) );
    this.driver.findElement( By.xpath( "//a[@rel='public/plugin-samples/cda/cdafiles/']" ) ).click();
    // Select a file
    assertNotNull( ElementHelper.WaitForElementVisibility( this.driver, By.xpath( "//a[@rel='public/plugin-samples/cda/cdafiles/compoundJoin.cda']" ) ) );
    this.driver.findElement( By.xpath( "//a[@rel='public/plugin-samples/cda/cdafiles/compoundJoin.cda']" ) ).click();
    // Click OK
    this.wait.until( ExpectedConditions.visibilityOfAllElementsLocatedBy( By.id( "popup_browse_buttonOk" ) ) );
    this.driver.findElement( By.id( "popup_browse_buttonOk" ) ).click();
    ElementHelper.WaitForElementInvisibility( this.driver, By.id( "popup_browse_buttonOk" ) );
    // SAVE the widget
    ElementHelper.WaitForElementVisibility( this.driver, By.xpath( "//div[@id='headerLinks']/div[2]/a" ) );
    this.driver.findElement( By.xpath( "//div[@id='headerLinks']/div[2]/a" ) ).click();
    ElementHelper.WaitForElementInvisibility( this.driver, By.id( "notifyBar" ) );

    // ## Step 5 - Check if the file persist after SAVE widget
    this.driver = WidgetUtils.OpenWidgetEditMode( this.driver, this.wait, this.baseUrl, this.widgetName );
    // Open
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe" ) ) );
    frameCDEDashboard = this.driver.findElement( By.xpath( "//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe" ) );
    this.driver.switchTo().frame( frameCDEDashboard );
    // Click in Datasources panel
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@class='datasourcesPanelButton']" ) ) );
    this.driver.findElement( By.xpath( "//div[@class='datasourcesPanelButton']" ) ).click();
    // Click in CDA Data Source in the list of Datasources
    // Expand group
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "table-cdfdd-datasources-datasources" ) ) );
    this.driver.findElement( By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr/td/span" ) ).click();
    // Click in CDA Data Source
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[2]" ) ) );
    this.driver.findElement( By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[2]/td" ) ).click();

    /*#######################################
      EXPECT RESULT:
      Path shall start with '/'
     #######################################*/
    assertEquals( "Path", this.driver.findElement( By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[4]/td" ) ).getText() );
    assertEquals( "/public/plugin-samples/cda/cdafiles/compoundJoin.cda", this.driver.findElement( By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[4]/td[2]/div" ) ).getText() );
  }

  @After
  public void tearDown() {
    // To use after test case run.
  }
}
