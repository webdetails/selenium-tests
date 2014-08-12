package org.pentaho.ctools.cde.widgets;

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

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
  private String widgetName = "dummyWidgetSelectCdaDatasource";

  @Before
  public void setUp() throws Exception {
    driver = CToolsTestSuite.getDriver();
    wait = CToolsTestSuite.getWait();
    baseUrl = CToolsTestSuite.getBaseUrl();

    init();
  }

  /**
   * Where we do stuff (like: clean, prepare data) before start testing.
   */
  public void init() {
    //##Step 0 - Delete the widget
    WidgetUtils.RemoveWidgetByName(driver, wait, baseUrl, widgetName);
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
  @Test
  public void tc1_SelectCdaFileAsDatasource_PathOfCdaFileCorrect() throws Exception {
    //##Step 1 - Create widget with specific parameter
    driver = WidgetUtils.CreateWidget(driver, wait, baseUrl, widgetName);


    //##Step 2 - Access the widget
    driver = WidgetUtils.OpenWidgetEditMode(driver,wait, baseUrl, widgetName);


    //##Step 3 - Click in Datasources panel and add a CDA Datasource
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe")));
    WebElement frameCDEDashboard = driver.findElement(By.xpath("//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe"));
    driver.switchTo().frame(frameCDEDashboard);
    //Click in Datasources panel
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='datasourcesPanelButton']")));
    driver.findElement(By.xpath("//div[@class='datasourcesPanelButton']")).click();
    //Go to Community Data Access (left panel)
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='cdfdd-datasources-palletePallete']/div[2]/h3/span")));
    driver.findElement(By.xpath("//div[@id='cdfdd-datasources-palletePallete']/div[2]/h3/span")).click();
    //Click in 'CDA Data Source'
    WebElement elementListedOthers = driver.findElement(By.xpath("//div[@id='cdfdd-datasources-palletePallete']/div[2]/div"));
    elementListedOthers.findElement(By.xpath("//a[@title='CDA Data Source']")).click();


    //##Step 4 - Add the cda file
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("table-cdfdd-datasources-properties")));
    //Click in the property to add the file
    driver.findElement(By.xpath("//table[@id='table-cdfdd-datasources-properties']/tbody/tr[4]/td[2]/button")).click();
    assertTrue(ElementHelper.IsElementDisplayed(driver, By.id("popupbox")));
    assertTrue(ElementHelper.IsElementDisplayed(driver, By.id("popup_state_browse")));
    //Click in 'Public'
    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("container_id")));
    WebElement listFolders = driver.findElement(By.xpath("//div[@id='container_id']"));
    listFolders.findElement(By.xpath("//a[@rel='public/']")).click();
    //Click in 'plugin-samples'
    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("container_id")));
    listFolders = driver.findElement(By.xpath("//div[@id='container_id']"));
    listFolders.findElement(By.xpath("//a[@rel='public/plugin-samples/']")).click();
    //Click in 'cda'
    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("container_id")));
    listFolders = driver.findElement(By.xpath("//div[@id='container_id']"));
    listFolders.findElement(By.xpath("//a[@rel='public/plugin-samples/cda/']")).click();
    //Click in 'cdafiles'
    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("container_id")));
    listFolders = driver.findElement(By.xpath("//div[@id='container_id']"));
    listFolders.findElement(By.xpath("//a[@rel='public/plugin-samples/cda/cdafiles/']")).click();
    //Select a file
    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("container_id")));
    listFolders = driver.findElement(By.xpath("//div[@id='container_id']"));
    listFolders.findElement(By.xpath("//a[@rel='public/plugin-samples/cda/cdafiles/compoundJoin.cda']")).click();
    //Click OK
    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("popup_browse_buttonOk")));
    driver.findElement(By.id("popup_browse_buttonOk")).click();
    ElementHelper.WaitForElementNotPresent(driver, 2, By.id("popup_browse_buttonOk"));
    //SAVE the widget
    ElementHelper.IsElementDisplayed(driver, By.xpath("//div[@id='headerLinks']/div[2]/a"));
    driver.findElement(By.xpath("//div[@id='headerLinks']/div[2]/a")).click();
    ElementHelper.WaitForElementNotPresent(driver, 5, By.id("notifyBar"));


    //## Step 5 - Check if the file persist after SAVE widget
    driver = WidgetUtils.OpenWidgetEditMode(driver,wait, baseUrl, widgetName);
    //Open
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe")));
    frameCDEDashboard = driver.findElement(By.xpath("//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe"));
    driver.switchTo().frame(frameCDEDashboard);
    //Click in Datasources panel
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='datasourcesPanelButton']")));
    driver.findElement(By.xpath("//div[@class='datasourcesPanelButton']")).click();
    //Click in CDA Data Source in the list of Datasources
    //Expand group
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("table-cdfdd-datasources-datasources")));
    driver.findElement(By.xpath("//table[@id='table-cdfdd-datasources-datasources']/tbody/tr/td/span")).click();
    //Click in CDA Data Source
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[2]")));
    driver.findElement(By.xpath("//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[2]/td")).click();

    /*#######################################
      EXPECT RESULT:
      Path shall start with '/'
     #######################################*/
    assertEquals("Path",
        driver.findElement(By.xpath("//table[@id='table-cdfdd-datasources-properties']/tbody/tr[4]/td")).getText());
    assertEquals("/public/plugin-samples/cda/cdafiles/compoundJoin.cda",
        driver.findElement(By.xpath("//table[@id='table-cdfdd-datasources-properties']/tbody/tr[4]/td[2]/div")).getText());
  }


  @After
  public void tearDown() {
    driver.manage().timeouts().implicitlyWait(200, TimeUnit.MILLISECONDS);
  }
}
