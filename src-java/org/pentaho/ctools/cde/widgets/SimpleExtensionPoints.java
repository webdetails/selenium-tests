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

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


/**
 * NOTE - The test was created regarding issue CDE-302
 */
public class SimpleExtensionPoints {
  // Instance of the driver (browser emulator)
  private WebDriver driver;
  // Instance to be used on wait commands
  private Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private String baseUrl;
  // The name for the widget to be created
  private String widgetName = "dummyWidgetExtensionPoint";
  // The param to add in Extension Point
  private String paramArg = "arg1";
  // The Extension point value
  private String paramValue = "My Extension Point";
  // The new value for the extension point
  private String paramValueEditable = "My New Editable Extension Point";

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
  public void init() {}


  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Add an Extension Point
   * Description:
   *    In this test case we pretend to add a simple extension point and then
   *    validate the persistance.
   * Steps:
   *    1. Create a widget
   *    2. Add a chart
   *    3. In properties 'Extension Points' add a simple value
   *    4. Check if the value persist
   *
   * @throws Exception
   */
  @Test(timeout = 60000)
  public void tc1_AddSimpleExtensionPoint_ExtensionPointAdded() throws Exception {
    //##Step 0 - Delete the widget
    WidgetUtils.RemoveWidgetByName(driver, wait, baseUrl, widgetName);

    //##Step 1 - Create a widget
    driver = WidgetUtils.CreateWidget(driver, wait, baseUrl, widgetName);

    //##Step 2 - Add a chart
    // Click in Datasources panel and add a CDA Datasource
    //Click in Components panel
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='componentsPanelButton']")));
    driver.findElement(By.xpath("//div[@class='componentsPanelButton']")).click();
    //Go to Charts (left panel)
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='cdfdd-components-palletePallete']/div/h3/span")));
    driver.findElement(By.xpath("//div[@id='cdfdd-components-palletePallete']/div/h3/span")).click();
    //Click in 'CDA Data Source'
    WebElement elementListedOthers = driver.findElement(By.xpath("//div[@id='cdfdd-components-palletePallete']/div/div"));
    assertNotNull(elementListedOthers);
    elementListedOthers.findElement(By.xpath("//a[@title='CCC Pie Chart']")).click();


    //##Step 3 - In properties 'Extension Points' add a simple value
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("table-cdfdd-components-components")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("table-cdfdd-components-properties")));
    //Click in "Advanced Properties"
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='table-cdfdd-components-properties']/caption/div[3][contains(text(),'Advanced Properties')]")));
    driver.findElement(By.xpath("//table[@id='table-cdfdd-components-properties']/caption/div[3][contains(text(),'Advanced Properties')]")).click();
    ElementHelper.IsElementDisplayed(driver, By.xpath("//table[@id='table-cdfdd-components-properties']/tbody//td[@title='CCC Extension points']"));
    //Click in 'Extension Point'
    WebElement extensionPoint = driver.findElement(By.xpath("//table[@id='table-cdfdd-components-properties']/tbody//td[@title='CCC Extension points']/.."));
    //Get the two elements 'td' and click in the td element that represent the value list of 'Extension Points' property
    List<WebElement> listOfElements = extensionPoint.findElements(By.cssSelector("td"));
    assertTrue(listOfElements.size() == 2);
    WebElement valueExtension = listOfElements.get(1);
    valueExtension.click();
    //Wait for the popup window is displayed
    ElementHelper.IsElementDisplayed(driver, By.id("popup"));
    ElementHelper.IsElementDisplayed(driver, By.cssSelector("input.StringListAddButton"));
    WebElement addButton = driver.findElement(By.cssSelector("input.StringListAddButton"));
    String addButtonText = addButton.getAttribute("value");
    assertEquals(addButtonText, "Add");
    addButton.click();
    //Wait for the pair arg and value
    //Thread.sleep(100);
    ElementHelper.IsElementPresent(driver, 5, By.id("parameters_0"));
    ElementHelper.IsElementDisplayed(driver, By.id("arg_0"));
    ElementHelper.IsElementDisplayed(driver, By.id("parameter_button_0"));
    //Clicking in '...'
    driver.findElement(By.id("parameter_button_0")).click();
    //Show popup
    ElementHelper.IsElementDisplayed(driver, By.cssSelector("div.ace_line"));
    ElementHelper.IsElementDisplayed(driver, By.xpath("(//button[@id='popup_state0_buttonOk'])[2]"));
    driver.findElement(By.cssSelector("textarea.ace_text-input")).clear();
    driver.findElement(By.cssSelector("textarea.ace_text-input")).sendKeys(paramValue);
    driver.findElement(By.xpath("(//button[@id='popup_state0_buttonOk'])[2]")).click();
    //dismiss popup
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("(//div[@id='popupbox'])[2]")));
    ElementHelper.IsElementDisplayed(driver, By.id("arg_0"));
    ElementHelper.IsElementDisplayed(driver, By.id("parameter_button_0"));
    driver.findElement(By.id("arg_0")).sendKeys(paramArg);
    driver.findElement(By.xpath("//button[@id='popup_state0_buttonOk']")).click();
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='popupbox']")));
    //Click in button SAVE
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='table-cdfdd-components-properties']")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='headerLinks']/div[2]/a")));
    driver.findElement(By.xpath("//div[@id='headerLinks']/div[2]/a")).click();
    ElementHelper.WaitForElementNotPresent(driver, 5, By.id("notifyBar"));

    //## Step 4 - Check if the value persist
    driver = WidgetUtils.OpenWidgetEditMode(driver, wait, baseUrl, widgetName);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe")));
    WebElement frameCDEDashboard = driver.findElement(By.xpath("//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe"));
    driver.switchTo().frame(frameCDEDashboard);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='componentsPanelButton']")));
    driver.findElement(By.xpath("//div[@class='componentsPanelButton']")).click();
    //Go to Extension Point
    //Expand Group
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='table-cdfdd-components-components']/tbody/tr/td")));
    driver.findElement(By.xpath("//table[@id='table-cdfdd-components-components']/tbody/tr/td/span")).click();
    //Click in chart
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='table-cdfdd-components-components']/tbody/tr[2]")));
    driver.findElement(By.xpath("//table[@id='table-cdfdd-components-components']/tbody/tr[2]/td")).click();
    //Display the properties
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("table-cdfdd-components-properties")));
    //Click in "Advanced Properties"
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='table-cdfdd-components-properties']/caption/div[3][contains(text(),'Advanced Properties')]")));
    driver.findElement(By.xpath("//table[@id='table-cdfdd-components-properties']/caption/div[3][contains(text(),'Advanced Properties')]")).click();
    ElementHelper.IsElementDisplayed(driver, By.xpath("//table[@id='table-cdfdd-components-properties']/tbody//td[@title='CCC Extension points']"));
    //Click in 'Extension Point'
    extensionPoint = driver.findElement(By.xpath("//table[@id='table-cdfdd-components-properties']/tbody//td[@title='CCC Extension points']/.."));
    //Get the two elements 'td' and click in the td element that represent the value list of 'Extension Points' property
    listOfElements = extensionPoint.findElements(By.cssSelector("td"));
    assertTrue(listOfElements.size() == 2);
    valueExtension = listOfElements.get(1);
    valueExtension.click();
    //Wait for the popup window is displayed
    ElementHelper.IsElementDisplayed(driver, By.id("popup"));
    ElementHelper.IsElementDisplayed(driver, By.cssSelector("input.StringListAddButton"));
    //Wait for the pair arg and value
    ElementHelper.IsElementPresent(driver, 5, By.id("parameters_0"));
    ElementHelper.IsElementDisplayed(driver, By.id("arg_0"));
    ElementHelper.IsElementDisplayed(driver, By.id("parameter_button_0"));
    assertEquals(paramArg, driver.findElement(By.id("arg_0")).getAttribute("value"));
    //Clicking in '...'
    driver.findElement(By.id("parameter_button_0")).click();
    ElementHelper.IsElementDisplayed(driver, By.cssSelector("div.ace_line"));
    ElementHelper.IsElementDisplayed(driver, By.xpath("(//button[@id='popup_state0_buttonOk'])[2]"));
    assertEquals(paramValue, driver.findElement(By.cssSelector("div.ace_content")).getText());
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
  @Test(timeout = 60000)
  public void tc2_EditSimpleExtensionPoint_ExtensionPointEditable() throws Exception {
    //##Step 1 - Access to widget in Edit mode
    driver = WidgetUtils.OpenWidgetEditMode(driver, wait, baseUrl, widgetName);

    //##Step 2 - Change the extension point value
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe")));
    WebElement frameCDEDashboard = driver.findElement(By.xpath("//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe"));
    driver.switchTo().frame(frameCDEDashboard);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='componentsPanelButton']")));
    driver.findElement(By.xpath("//div[@class='componentsPanelButton']")).click();
    //Go to Extension Point
    //Expand Group
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='table-cdfdd-components-components']/tbody/tr/td")));
    driver.findElement(By.xpath("//table[@id='table-cdfdd-components-components']/tbody/tr/td/span")).click();
    //Click in chart
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='table-cdfdd-components-components']/tbody/tr[2]")));
    driver.findElement(By.xpath("//table[@id='table-cdfdd-components-components']/tbody/tr[2]/td")).click();
    //Display the properties
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("table-cdfdd-components-properties")));
    //Click in "Advanced Properties"
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='table-cdfdd-components-properties']/caption/div[3][contains(text(),'Advanced Properties')]")));
    driver.findElement(By.xpath("//table[@id='table-cdfdd-components-properties']/caption/div[3][contains(text(),'Advanced Properties')]")).click();
    ElementHelper.IsElementDisplayed(driver, By.xpath("//table[@id='table-cdfdd-components-properties']/tbody//td[@title='CCC Extension points']"));
    //Click in 'Extension Point'
    WebElement extensionPoint = driver.findElement(By.xpath("//table[@id='table-cdfdd-components-properties']/tbody//td[@title='CCC Extension points']/.."));
    //Get the two elements 'td' and click in the td element that represent the value list of 'Extension Points' property
    List<WebElement> listOfElements = extensionPoint.findElements(By.cssSelector("td"));
    assertTrue(listOfElements.size() == 2);
    WebElement valueExtension = listOfElements.get(1);
    valueExtension.click();
    //Wait for the popup window is displayed
    ElementHelper.IsElementDisplayed(driver, By.id("popup"));
    ElementHelper.IsElementDisplayed(driver, By.cssSelector("input.StringListAddButton"));
    //Wait for the pair arg and value
    ElementHelper.IsElementPresent(driver, 5, By.id("parameters_0"));
    ElementHelper.IsElementDisplayed(driver, By.id("arg_0"));
    ElementHelper.IsElementDisplayed(driver, By.id("parameter_button_0"));
    assertEquals(paramArg, driver.findElement(By.id("arg_0")).getAttribute("value"));
    //Clicking in '...'
    driver.findElement(By.id("parameter_button_0")).click();
    ElementHelper.IsElementDisplayed(driver, By.cssSelector("div.ace_line"));
    ElementHelper.IsElementDisplayed(driver, By.xpath("(//button[@id='popup_state0_buttonOk'])[2]"));
    assertEquals(paramValue, driver.findElement(By.cssSelector("div.ace_content")).getText());
    //Add a new value
    driver.findElement(By.cssSelector("textarea.ace_text-input")).clear();
    driver.findElement(By.cssSelector("textarea.ace_text-input")).sendKeys(paramValueEditable);
    driver.findElement(By.xpath("(//button[@id='popup_state0_buttonOk'])[2]")).click();
    //dismiss popup
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("(//div[@id='popupbox'])[2]")));
    ElementHelper.IsElementDisplayed(driver, By.id("arg_0"));
    ElementHelper.IsElementDisplayed(driver, By.id("parameter_button_0"));
    driver.findElement(By.xpath("//button[@id='popup_state0_buttonOk']")).click();
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='popupbox']")));
    //Click in button SAVE
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='table-cdfdd-components-properties']")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='headerLinks']/div[2]/a")));
    driver.findElement(By.xpath("//div[@id='headerLinks']/div[2]/a")).click();
    ElementHelper.WaitForElementNotPresent(driver, 5, By.id("notifyBar"));

    //## Step 3 - Check if the value persist
    driver = WidgetUtils.OpenWidgetEditMode(driver, wait, baseUrl, widgetName);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe")));
    frameCDEDashboard = driver.findElement(By.xpath("//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe"));
    driver.switchTo().frame(frameCDEDashboard);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='componentsPanelButton']")));
    driver.findElement(By.xpath("//div[@class='componentsPanelButton']")).click();
    //Go to Extension Point
    //Expand Group
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='table-cdfdd-components-components']/tbody/tr/td")));
    driver.findElement(By.xpath("//table[@id='table-cdfdd-components-components']/tbody/tr/td/span")).click();
    //Click in chart
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='table-cdfdd-components-components']/tbody/tr[2]")));
    driver.findElement(By.xpath("//table[@id='table-cdfdd-components-components']/tbody/tr[2]/td")).click();
    //Display the properties
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("table-cdfdd-components-properties")));
    //Click in "Advanced Properties"
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='table-cdfdd-components-properties']/caption/div[3][contains(text(),'Advanced Properties')]")));
    driver.findElement(By.xpath("//table[@id='table-cdfdd-components-properties']/caption/div[3][contains(text(),'Advanced Properties')]")).click();
    ElementHelper.IsElementDisplayed(driver, By.xpath("//table[@id='table-cdfdd-components-properties']/tbody//td[@title='CCC Extension points']"));
    //Click in 'Extension Point'
    extensionPoint = driver.findElement(By.xpath("//table[@id='table-cdfdd-components-properties']/tbody//td[@title='CCC Extension points']/.."));
    //Get the two elements 'td' and click in the td element that represent the value list of 'Extension Points' property
    listOfElements = extensionPoint.findElements(By.cssSelector("td"));
    assertTrue(listOfElements.size() == 2);
    valueExtension = listOfElements.get(1);
    valueExtension.click();
    //Wait for the popup window is displayed
    ElementHelper.IsElementDisplayed(driver, By.id("popup"));
    ElementHelper.IsElementDisplayed(driver, By.cssSelector("input.StringListAddButton"));
    //Wait for the pair arg and value
    ElementHelper.IsElementPresent(driver, 5, By.id("parameters_0"));
    ElementHelper.IsElementDisplayed(driver, By.id("arg_0"));
    ElementHelper.IsElementDisplayed(driver, By.id("parameter_button_0"));
    assertEquals(paramArg, driver.findElement(By.id("arg_0")).getAttribute("value"));
    //Clicking in '...'
    driver.findElement(By.id("parameter_button_0")).click();
    ElementHelper.IsElementDisplayed(driver, By.cssSelector("div.ace_line"));
    ElementHelper.IsElementDisplayed(driver, By.xpath("(//button[@id='popup_state0_buttonOk'])[2]"));
    assertEquals(paramValueEditable, driver.findElement(By.cssSelector("div.ace_content")).getText());
  }


  @After
  public void tearDown() {}
}
