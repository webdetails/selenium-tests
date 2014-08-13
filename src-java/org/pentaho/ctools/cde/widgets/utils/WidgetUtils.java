package org.pentaho.ctools.cde.widgets.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.utils.ElementHelper;

import java.lang.Exception;
import java.lang.Thread;
import java.util.List;

import static org.junit.Assert.*;

public class WidgetUtils {

  /**
   * This method is responsible to remove the widget from 'Browse Files'.
   *
   * @param driver
   * @param wait
   * @param widgetName
   */
  public static void RemoveWidgetByName(WebDriver driver,
                                        Wait<WebDriver> wait,
                                        String baseUrl,
                                        String widgetName){
    driver.switchTo().defaultContent();
    driver.get(baseUrl + "Home");

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[@id='home.perspective']")));
    assertNotNull(driver.findElement(By.xpath("//iframe[@id='home.perspective']")));

    //Go to the Home Perspective [IFRAME]
    driver.switchTo().frame("home.perspective");
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='well sidebar']")));
    driver.findElement(By.xpath("//div[@class='well sidebar']/button")).click();//Click in 'Browse Files'


    //Now we have to navegate to 'Public/cde/widgets
    driver.switchTo().defaultContent();
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("applicationShell")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[@id='browser.perspective']")));
    driver.switchTo().frame("browser.perspective");
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='fileBrowser']")));
    
    //Public
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='fileBrowserFolders']")));
    WebElement folders = driver.findElement(By.xpath("//div[@id='fileBrowserFolders']"));
    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@path='/public']")));
    WebElement publicFolder = folders.findElement(By.xpath("//div[@path='/public']"));
    publicFolder.findElement(By.className("expandCollapse")).click();
    //CDE
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='fileBrowserFolders']")));
    folders = driver.findElement(By.xpath("//div[@id='fileBrowserFolders']"));
    WebElement cdeFolder = folders.findElement(By.xpath("//div[@path='/public/cde']"));
    cdeFolder.findElement(By.className("expandCollapse")).click();
    //Widgets
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='fileBrowserFolders']")));
    folders = driver.findElement(By.xpath("//div[@id='fileBrowserFolders']"));
    WebElement widgetsFolder = folders.findElement(By.xpath("//div[@path='/public/cde/widgets']"));
    widgetsFolder.findElement(By.className("title")).click();
    //Check if at least one file is displayed
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='fileBrowserFiles']/div[2]/div")));
    WebElement listFiles = driver.findElement(By.xpath("//div[@id='fileBrowserFiles']/div[2]"));
    List<WebElement> theWidgetFiles = listFiles.findElements(By.xpath("//div[@class='title' and contains(text(),'" + widgetName + "')]"));

    //Check if the widget named exist
    if(theWidgetFiles != null){
      if(theWidgetFiles.size() > 0) {
        WebElement[] arrayTheWidgetFiles = new WebElement[theWidgetFiles.size()];
        theWidgetFiles.toArray(arrayTheWidgetFiles);

        //Where we want to select three files
        // <the widget>
        // <the widget>.cdfde
        // <the widget>.component.xml
        //To do that we select each file (using the CONTROL key) and delete them.
        Actions action = new Actions(driver);
        action.keyDown(Keys.CONTROL);
        for (int i = 0; i < arrayTheWidgetFiles.length; i++) {
          action.click(arrayTheWidgetFiles[i]);
        }
        action.keyUp(Keys.CONTROL);
        action.build().perform();

        //Here we still in the iframe
        assertTrue(ElementHelper.IsElementDisplayed(driver, By.id("deleteButton")));
        driver.findElement(By.id("deleteButton")).click();
        //Go back to root html
        driver.switchTo().defaultContent();
        assertEquals(driver.findElement(By.cssSelector("div.gwt-HTML")).getText(), "Are you sure you want to move all selected items to the trash?");
        driver.findElement(By.id("okButton")).click();
      }
    }
  }

  /**
   * This method shall create an widget with specific parameter.
   *
   * @param driver
   * @param wait
   * @param baseUrl
   * @param widgetName
   * @param paramName
   * @return
   */
  public static WebDriver CreateWidgetWithParameter(WebDriver driver,
                                                    Wait<WebDriver> wait,
                                                    String baseUrl,
                                                    String widgetName,
                                                    String paramName) throws Exception{
    //Step 1 - Go to homepage
    driver.switchTo().defaultContent();
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
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.popover-content > #createNewlaunch_new_cdeButton")));
    WebElement buttonCDEBashBoard = driver.findElement(By.cssSelector("div.popover-content > #createNewlaunch_new_cdeButton"));
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
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='widgetParameters']/div/input")));
    driver.findElement(By.xpath("//span[@id='widgetParameters']/div/input")).click();
    //Press button save
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='popupbuttons']/button[@id='popup_state0_buttonSave']")));
    driver.findElement(By.xpath("//div[@class='popupbuttons']/button[@id='popup_state0_buttonSave']")).click();

    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("popupbox")));

    return driver;
  }

  /**
   * This method shall create an widget with specific parameter.
   *
   * @param driver
   * @param wait
   * @param baseUrl
   * @param widgetName
   * @param paramName
   * @return
   */
  public static WebDriver CreateWidget(WebDriver driver,
                                       Wait<WebDriver> wait,
                                       String baseUrl,
                                       String widgetName) throws Exception{
    //Step 1 - Go to homepage
    driver.switchTo().defaultContent();
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
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.popover-content > #createNewlaunch_new_cdeButton")));
    WebElement buttonCDEBashBoard = driver.findElement(By.cssSelector("div.popover-content > #createNewlaunch_new_cdeButton"));
    assertEquals(buttonCDEBashBoard.getText(), "CDE Dashboard");
    buttonCDEBashBoard.click();
    driver.switchTo().defaultContent();//back to the root


    //Step 3 - Click in Component Panel
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe")));
    WebElement frameCDEDashboard = driver.findElement(By.xpath("//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe"));
    driver.switchTo().frame(frameCDEDashboard);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='componentsPanelButton']")));
    driver.findElement(By.xpath("//div[@class='componentsPanelButton']")).click();


    //Step 4 - Save the widget
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
    //Insert file name
    driver.findElement(By.xpath("//input[@id='fileInput']")).sendKeys(widgetName);
    //Insert widget name
    driver.findElement(By.xpath("//input[@id='componentInput']")).sendKeys(widgetName);
    //Press OK (SAVING)
    driver.findElement(By.xpath("//button[@id='popup_state0_buttonOk']")).click();
    //Wait for the pop-up exit
    ElementHelper.WaitForElementNotPresent(driver, 10, By.id("popupbox"));

    return driver;
  }

  /**
   * This method
   *
   * @param driver
   * @param wait
   * @param baseUrl
   * @param widgetName
   * @return
   */
  public static WebDriver OpenWidgetEditMode(WebDriver driver, Wait<WebDriver> wait, String baseUrl, String widgetName) {
    //Resuming Steps
    // 1. open the widget
    // 2. check if the parameter exist in settings
    // 3. check if the widget exist in 'widgets' at Component Layout
    driver.switchTo().defaultContent();
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
    //Public
    WebElement folders = driver.findElement(By.xpath("//div[@id='fileBrowserFolders']"));
    WebElement publicFolder = folders.findElement(By.xpath("//div[@path='/public']"));
    publicFolder.findElement(By.className("expandCollapse")).click();
    //CDE
    folders = driver.findElement(By.xpath("//div[@id='fileBrowserFolders']"));
    WebElement cdeFolder = folders.findElement(By.xpath("//div[@path='/public/cde']"));
    cdeFolder.findElement(By.className("expandCollapse")).click();
    //Widgets
    folders = driver.findElement(By.xpath("//div[@id='fileBrowserFolders']"));
    WebElement widgetsFolder = folders.findElement(By.xpath("//div[@path='/public/cde/widgets']"));
    widgetsFolder.findElement(By.className("title")).click();
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

    return driver;
  }
}
