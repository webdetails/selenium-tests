package org.pentaho.ctools.cde.widgets;

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

import java.util.List;

import static org.junit.Assert.*;

/**
 * NOTE - The test was created regarding issue CDF-318
 */
public class CreateWidget {
  // Instance of the driver (browser emulator)
  private WebDriver driver;
  // Instance to be used on wait commands
  private Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private String baseUrl;
  // The name for the widget to be created
  private String widgetName = "dummyCreateWidget";
  // The name for the parameter to be added
  private String paramName = "paramCreateWidget";

  @Before
  public void setUp() throws Exception {
    driver = CToolsTestSuite.getDriver();
    wait = CToolsTestSuite.getWait();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  @Test
  public void testCreateWidget() throws Exception {
    //Step 0 - Delete the widget
    WidgetUtils.RemoveWidgetByName(driver, wait, baseUrl, widgetName);

    //Create widget with specific parameter
    driver = WidgetUtils.CreateWidgetWithParameter(driver, wait, baseUrl, widgetName, paramName);
  }

  @Test
  public void checkExistentWidgetAndParameter() throws Exception {
    //Resuming Steps
    // 1. open the widget
    // 2. check if the parameter exist in settings
    // 3. check if the widget exist in 'widgets' at Component Layout

    driver = WidgetUtils.OpenWidgetEditMode(driver, wait, baseUrl, widgetName);

    //Step 3 - Check if the parameter exist in 'Settings'
    //Move to the iframe
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe")));
    WebElement frameCDEDashboard = driver.findElement(By.xpath("//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe"));
    driver.switchTo().frame(frameCDEDashboard);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='layoutPanelButton']")));
    assertTrue(driver.findElement(By.xpath("//div[@class='layoutPanelButton']")).isEnabled());
    //Press 'Settings'
    driver.findElement(By.xpath("//div[@id='headerLinks']/div[5]/a")).click();
    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@id='popup']")));
    assertNotNull(driver.findElement(By.xpath("//span[@id='widgetParameters']/div/input")));
    //The parameter MUST be equal to the one set
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='widgetParameters']/div/span")));
    assertEquals(driver.findElement(By.xpath("//span[@id='widgetParameters']/div/span")).getText(), paramName);
    //Press on the check box
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='widgetParameters']/div/input")));
    assertTrue(driver.findElement(By.xpath("//span[@id='widgetParameters']/div/input")).isSelected());
    //Press button 'Cancel'
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='popupbuttons']/button[@id='popup_state0_buttonCancel']")));
    driver.findElement(By.xpath("//div[@class='popupbuttons']/button[@id='popup_state0_buttonCancel']")).click();


    //Step 4 - Click on Component Panel and check if the widget is listed
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='componentsPanelButton']")));
    driver.findElement(By.xpath("//div[@class='componentsPanelButton']")).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='cdfdd-components-palletePallete']/div[9]/h3/span")));
    driver.findElement(By.xpath("//div[@id='cdfdd-components-palletePallete']/div[9]/h3/span")).click();
    //Getting the element where the widget created is displayed
    WebElement listOfWidgets = driver.findElement(By.xpath("//div[@id='cdfdd-components-palletePallete']/div[9]/div"));
    //Check if the widget created is listed
    WebElement theWidgetCreated = listOfWidgets.findElement(By.xpath("//a[@class='tooltip' and contains(text(),'" + widgetName + "')]"));
    assertNotNull(theWidgetCreated);
    assertEquals(theWidgetCreated.getText(), widgetName);


    //Step 5 - Click in the widget created and check if the widget is add at Components column and Properties
    theWidgetCreated.click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='table-cdfdd-components-components']/tbody/tr[4]/td")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='table-cdfdd-components-properties']/tbody/tr[2]/td")));
    assertEquals(driver.findElement(
        By.xpath("//table[@id='table-cdfdd-components-components']/tbody/tr[4]/td")).getText(),
        widgetName + " Widget");
    assertEquals(driver.findElement(
            By.xpath("//table[@id='table-cdfdd-components-properties']/tbody/tr[2]/td")).getText(),
        "Parameter " + paramName);
  }

  @After
  public void tearDown() { }
}
