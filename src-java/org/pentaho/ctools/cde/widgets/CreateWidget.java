/*!
  * This program is free software;you can redistribute it and/or modify it under the
  * terms of the GNU Lesser General Public License,version2.1as published by the Free Software Foundation.
  *
  * You should have received a copy of the GNU Lesser General Public License along with this
  * program;if not,you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
  * or from the Free Software Foundation,Inc., 51 Franklin Street,Fifth Floor,Boston,MA 02110-1301 USA.
  *
  * This program is distributed in the hope that it will be useful,but WITHOUT ANY WARRANTY;
  * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
  * See the GNU Lesser General Public License for more details.
  *
  * Copyright(c)2002-2014 Pentaho Corporation..All rights reserved.
  */

package org.pentaho.ctools.cde.widgets;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.cde.widgets.WidgetUtils;


import java.lang.Thread;

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
    //WidgetUtils.RemoveWidgetByName(driver, wait, baseUrl, widgetName);


    //Step 1 - Go to homepage
    driver.get(baseUrl + "Home");

    //Wait for the visibility of Menu and frame contents
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='mainMenubar']")));
    driver.switchTo().frame("home.perspective");
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='buttonWrapper']")));


    //Step 2 - Click in Create New (CDE Dashboard)
    //Click to create a widget
    WebElement buttonCreateNew = driver.findElement(By.xpath("//button[@id='btnCreateNew']"));
    assertEquals(buttonCreateNew.getText(), "Create New");
    buttonCreateNew.click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='popover-content']/button")));
    WebElement buttonCDEBashBoard = driver.findElement(By.xpath("//div[@class='popover-content']/button"));
    assertEquals(buttonCDEBashBoard.getText(), "CDE Dashboard");
    buttonCDEBashBoard.click();
    driver.switchTo().defaultContent();//back to the root


    //Step 3 - Click in Component Panel
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe")));
    WebElement frameCDEDashboard = driver.findElement(By.xpath("//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe"));
    driver.switchTo().frame(frameCDEDashboard);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='componentsPanelButton']")));
    driver.findElement(By.xpath("//div[@class='componentsPanelButton']")).click();


    //Step 4 - Add a Simple Parameter
    //Click in Generic
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='cdfdd-components-palletePallete']/div[3]/h3/span")));
    driver.findElement(By.xpath("//div[@id='cdfdd-components-palletePallete']/div[3]/h3/span")).click();
    //Click in SimpleParameter
    driver.findElement(By.xpath("//div[@id='cdfdd-components-palletePallete']/div[3]/div/ul/li[3]/a")).click();
    //Add a name to parameter 'paramCreateWidget'
    //Click in Name
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='table-cdfdd-components-properties']/tbody/tr/td[2]")));
    assertEquals(driver.findElement(By.xpath("//table[@id='table-cdfdd-components-properties']/tbody/tr/td")).getText(), "Name");
    //The below code is comment because the input text is already active.
    //driver.findElement(By.xpath("//table[@id='table-cdfdd-components-properties']/tbody/tr/td[2]")).click();
    WebElement inputPName = driver.findElement(By.xpath("//input[@name='value']"));
    inputPName.clear();
    inputPName.sendKeys(paramName);
    inputPName.sendKeys(Keys.RETURN);

    //Click in PropertyValue
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='table-cdfdd-components-properties']/tbody/tr[2]/td[2]")));
    driver.findElement(By.xpath("//table[@id='table-cdfdd-components-properties']/tbody/tr[2]/td[2]")).click();
    WebElement inputValue = driver.findElement(By.xpath("//input[@name='value']"));
    inputValue.clear();
    inputValue.sendKeys("1");
    inputValue.sendKeys(Keys.RETURN);


    //Step 5 - Press SAVE
    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@id='headerLinks']/div[2]/a")));
    driver.findElement(By.xpath("//div[@id='headerLinks']/div[2]/a")).click();
    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@id='popup']")));
    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@id='container_id']/ul/li")));
    driver.findElement(By.xpath("//input[@id='widgetRadio']")).click();
    while(true) {

      if(driver.findElement(By.xpath("//div[@id='container_id']")).isDisplayed() == false){
        break;
      } else {
        Thread.sleep(100);
      }
    }
    //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='container_id']")));
    //Insert file name
    driver.findElement(By.xpath("//input[@id='fileInput']")).sendKeys(widgetName);
    //Insert widget name
    driver.findElement(By.xpath("//input[@id='componentInput']")).sendKeys(widgetName);
    //Press OK (SAVING)
    driver.findElement(By.xpath("//button[@id='popup_state0_buttonOk']")).click();


    //Step 6 - Check if the parameter exist in 'Settings'
    //Popup message informing saving
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='layoutPanelButton']")));
    assertTrue(driver.findElement(By.xpath("//div[@class='layoutPanelButton']")).isEnabled());
    //Press 'Settings'
    driver.findElement(By.xpath("//div[@id='headerLinks']/div[5]/a")).click();
    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@id='popup']")));
    assertNotNull(driver.findElement(By.xpath("//span[@id='widgetParameters']/div/input")));
    //The parameter MUST be equal to the one set
    assertEquals(driver.findElement(By.xpath("//span[@id='widgetParameters']/div/span")).getText(), paramName);
    //Press on the check box
    driver.findElement(By.xpath("//span[@id='widgetParameters']/div/input")).click();
    //Press button save
    driver.findElement(By.xpath("//div[@class='popupbuttons']/button[@id='popup_state0_buttonSave']")).click();
  }

  @Test
  public void checkExistentWidgetAndParameter() throws Exception {
    //Resuming Steps
    // 1. open the widget
    // 2. check if the parameter exist in settings
    // 3. check if the widget exist in 'widgets' at Component Layout
    driver.get(baseUrl + "Home");


    //Step 1 - Go to Homepage and click 'Browse Files'
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[@id='home.perspective']")));
    assertNotNull(driver.findElement(By.xpath("//iframe[@id='home.perspective']")));
    //Go to the Home Perspective [IFRAME]
    driver.switchTo().frame("home.perspective");
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='well sidebar']")));
    driver.findElement(By.xpath("//div[@class='well sidebar']/button")).click();//Click in 'Browse Files'


    //Step 2  - Go to 'widgets' and click in the created widget and press 'Edit'
    //Now we have to navegate to 'Public/cde/widgets
    driver.switchTo().defaultContent();
    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("applicationShell")));
    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//iframe[@id='browser.perspective']")));
    driver.switchTo().frame("browser.perspective");
    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@id='fileBrowser']")));
    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@id='fileBrowserFolders']")));
    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@id='buttonsHeader']")));
    //Collapse folder 'Public'
    driver.findElement(By.xpath("//div[@id='fileBrowserFolders']/div[2]/div[2]/div/div")).click();
    //Collapse folder 'CDE'
    driver.findElement(By.xpath("//div[@id='fileBrowserFolders']/div[2]/div[2]/div[2]/div[2]/div/div")).click();
    //Collapse folder 'widgets'
    driver.findElement(By.xpath("//div[@id='fileBrowserFolders']/div[2]/div[2]/div[2]/div[2]/div[2]/div[4]/div/div[3]")).click();
    //Click in the created widget
    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@id='fileBrowserFiles']/div[2]")));
    WebElement listFiles = driver.findElement(By.xpath("//div[@id='fileBrowserFiles']/div[2]"));
    List<WebElement> theWidgetFiles = listFiles.findElements(By.xpath("//div[@class='title' and contains(text(),'" + widgetName + "')]"));
    assertNotNull(theWidgetFiles);
    //Check if the widget named exist
    if(theWidgetFiles != null){
      if(theWidgetFiles.size() > 0) {
        ((WebElement)theWidgetFiles.get(0)).click();
      }
    }
    //Click in the EDIT button
    driver.findElement(By.id("editButton")).click();
    driver.switchTo().defaultContent();//back to the root


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
    assertEquals(driver.findElement(By.xpath("//span[@id='widgetParameters']/div/span")).getText(), paramName);
    //Press on the check box
    assertTrue(driver.findElement(By.xpath("//span[@id='widgetParameters']/div/input")).isSelected());
    //Press button 'Cancel'
    driver.findElement(By.xpath("//div[@class='popupbuttons']/button[@id='popup_state0_buttonCancel']")).click();


    //Step 4 - Click on Cmponent Panel and check if the widget is listed
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
