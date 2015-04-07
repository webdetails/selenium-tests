package org.pentaho.ctools.cde.widgets.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;

public class WidgetUtils {

  /**
   * This method is responsible to remove the widget from 'Browse Files'.
   *
   * @param driver
   * @param wait
   * @param widgetName
   */
  public static void RemoveWidgetByName(WebDriver driver, String widgetName) {
    driver.switchTo().defaultContent();

    String baseUrl = CToolsTestSuite.getBaseUrl();
    driver.get(baseUrl + "Home");
    //wait for visibility of waiting pop-up
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='busy-indicator-container waitPopup']"));
    WebElement element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//iframe[@id='home.perspective']"));
    assertNotNull(element);

    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("viewmenu"));
    assertNotNull(element);
    element.click();
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//td[@id='showHiddenFilesMenuItem']"));
    assertNotNull(element);
    String text = element.getAttribute("class");
    if (text.equals("gwt-MenuItem-checkbox-unchecked")) {
      element.click();
      //wait for visibility of waiting pop-up
      ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='busy-indicator-container waitPopup']"));

      ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='spinner large-spinner']"));
      ElementHelper.WaitForElementInvisibility(driver, By.xpath("(//div[@class='spinner large-spinner'])[2]"));

      ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("filemenu"));
      element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("viewmenu"));
      assertNotNull(element);
      element.click();

      element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//td[@id='showHiddenFilesMenuItem']"));
      assertNotNull(element);
      text = element.getAttribute("class");
      assertEquals("gwt-MenuItem-checkbox-checked", text);
      element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("viewmenu"));
      assertNotNull(element);
      element.click();
    } else {
      element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("viewmenu"));
      element.click();
    }

    //Go to the Home Perspective [IFRAME]
    driver.switchTo().frame("home.perspective");
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@class='well sidebar']"));
    ElementHelper.Click(driver, By.xpath("//div[@class='well sidebar']/button"));//Click in 'Browse Files'

    //Now we have to navegate to 'Public/cde/widgets
    driver.switchTo().defaultContent();
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("applicationShell"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//iframe[@id='browser.perspective']"));
    driver.switchTo().frame("browser.perspective");
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("fileBrowser"));
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='spinner large-spinner']"));
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("(//div[@class='spinner large-spinner'])[2]"));

    //Public
    assertNotNull(ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='fileBrowserFolders']")));
    assertNotNull(ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@path='/public']")));
    driver.findElement(By.xpath("//div[@path='/public']")).findElement(By.className("expandCollapse")).click();
    //CDE
    assertNotNull(ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@path='/public/cde']")));
    driver.findElement(By.xpath("//div[@path='/public/cde']")).findElement(By.className("expandCollapse")).click();
    //Widgets
    assertNotNull(ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@path='/public/cde/widgets']")));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@path='/public/cde/widgets']")).findElement(By.className("title")).click();

    //wait for the page load in 'fileBrowserFiles'
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='spinner large-spinner']"));
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("(//div[@class='spinner large-spinner'])[2]"));
    //Check if at least one file is displayed
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='fileBrowserFiles']/div[2]/div"));
    WebElement listFiles = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='fileBrowserFiles']/div[2]"));
    List<WebElement> theWidgetFiles = listFiles.findElements(By.xpath("//div[@class='title' and contains(text(),'" + widgetName + "')]"));

    //Check if the widget named exist
    if (theWidgetFiles != null) {
      if (theWidgetFiles.size() > 0) {
        WebElement[] arrayTheWidgetFiles = new WebElement[theWidgetFiles.size()];
        theWidgetFiles.toArray(arrayTheWidgetFiles);

        //Where we want to select three files
        // <the widget>
        // <the widget>.cdfde
        // <the widget>.component.xml
        //To do that we select each file (using the CONTROL key) and delete them.
        Actions action = new Actions(driver);
        action.keyDown(Keys.CONTROL);
        for (WebElement arrayTheWidgetFile : arrayTheWidgetFiles) {
          action.click(arrayTheWidgetFile);
        }
        action.keyUp(Keys.CONTROL);
        action.build().perform();

        //Here we still in the iframe
        assertNotNull(ElementHelper.WaitForElementVisibility(driver, By.id("deleteButton")));
        ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("deleteButton")).click();
        //Go back to root html
        driver.switchTo().defaultContent();
        assertEquals(ElementHelper.WaitForElementPresenceAndVisible(driver, By.cssSelector("div.gwt-HTML")).getText(), "Are you sure you want to move all selected items to the trash?");
        ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("okButton")).click();

        //wait for visibility of waiting pop-up
        ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='busy-indicator-container waitPopup']"));

        ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='spinner large-spinner']"));
        ElementHelper.WaitForElementInvisibility(driver, By.xpath("(//div[@class='spinner large-spinner'])[2]"));

      }
    }
    driver.get(baseUrl + "Home");

    //wait for visibility of waiting pop-up
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='busy-indicator-container waitPopup']"));
    //Uncheck Show Hidden Files
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("viewmenu"));
    assertNotNull(element);
    element.click();
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//td[@id='showHiddenFilesMenuItem']"));
    assertNotNull(element);
    text = element.getAttribute("class");
    assertEquals("gwt-MenuItem-checkbox-checked", text);
    element.click();

    //wait for visibility of waiting pop-up
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='busy-indicator-container waitPopup']"));

    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='spinner large-spinner']"));
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("(//div[@class='spinner large-spinner'])[2]"));

    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("viewmenu"));
    assertNotNull(element);
    element.click();
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//td[@id='showHiddenFilesMenuItem']"));
    assertNotNull(element);
    text = element.getAttribute("class");
    assertEquals("gwt-MenuItem-checkbox-unchecked", text);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("viewmenu"));
    element.click();
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
  public static WebDriver CreateWidgetWithParameter(WebDriver driver, String widgetName, String paramName) throws Exception {
    //Step 1 - Go to homepage
    driver.switchTo().defaultContent();
    String baseUrl = CToolsTestSuite.getBaseUrl();
    driver.get(baseUrl + "Home");
    //wait for visibility of waiting pop-up
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='busy-indicator-container waitPopup']"));
    //Wait for the visibility of Menu and frame contents
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='mainMenubar']"));
    driver.switchTo().frame("home.perspective");
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='buttonWrapper']"));

    //Step 2 - Click in Create New (CDE Dashboard)
    //Click to create a widget
    WebElement buttonCreateNew = driver.findElement(By.xpath("//button[@id='btnCreateNew']"));
    assertEquals(buttonCreateNew.getText(), "Create New");
    buttonCreateNew.click();
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@class='popover-content']"));
    ElementHelper.WaitForTextPresence(driver, By.xpath("//div[@class='popover-content']/button[@id='createNewlaunch_new_cdeButton']"), "CDE Dashboard");
    WebElement buttonCDEBashBoard = ElementHelper.WaitForElementPresenceAndVisible(driver, By.cssSelector("div.popover-content > #createNewlaunch_new_cdeButton"));
    assertEquals(buttonCDEBashBoard.getText(), "CDE Dashboard");
    buttonCDEBashBoard.click();
    driver.switchTo().defaultContent();//back to the root

    //Step 3 - Click in Component Panel
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe"));
    WebElement frameCDEDashboard = driver.findElement(By.xpath("//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe"));
    driver.switchTo().frame(frameCDEDashboard);
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@class='componentsPanelButton']"));
    driver.findElement(By.xpath("//div[@class='componentsPanelButton']")).click();

    //Step 4 - Add a Simple Parameter
    //Click in Generic
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='cdfdd-components-palletePallete']/div[3]/h3/span"));
    driver.findElement(By.xpath("//div[@id='cdfdd-components-palletePallete']/div[3]/h3/span")).click();
    //Click in SimpleParameter
    driver.findElement(By.xpath("//div[@id='cdfdd-components-palletePallete']/div[3]/div/ul/li[3]/a")).click();
    //Add a name to parameter 'paramCreateWidget'
    //Click in Name
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//table[@id='table-cdfdd-components-properties']/tbody/tr/td[2]"));
    assertEquals(driver.findElement(By.xpath("//table[@id='table-cdfdd-components-properties']/tbody/tr/td")).getText(), "Name");
    //The below code is comment because the input text is already active.
    //driver.findElement(By.xpath("//table[@id='table-cdfdd-components-properties']/tbody/tr/td[2]")).click();
    WebElement inputPName = driver.findElement(By.xpath("//input[@name='value']"));
    inputPName.clear();
    inputPName.sendKeys(paramName);
    inputPName.sendKeys(Keys.RETURN);

    //Click in PropertyValue
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//table[@id='table-cdfdd-components-properties']/tbody/tr[2]/td[2]"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//table[@id='table-cdfdd-components-properties']/tbody/tr[2]/td[2]")).click();
    WebElement inputValue = driver.findElement(By.xpath("//input[@name='value']"));
    inputValue.clear();
    inputValue.sendKeys("1");
    inputValue.sendKeys(Keys.RETURN);

    //Step 5 - Press SAVE
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='headerLinks']/div[2]/a"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='headerLinks']/div[2]/a")).click();
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='popup']"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='container_id']/ul/li"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//input[@id='widgetRadio']")).click();
    while (true) {

      if (ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='container_id']")).isDisplayed() == false) {
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
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@class='layoutPanelButton']"));
    //ElementHelper.WaitForElementPresenceAndVisible(driver, (By.xpath("//div[@class='layoutPanelButton']")));
    assertTrue(driver.findElement(By.xpath("//div[@class='layoutPanelButton']")).isEnabled());
    //Press 'Settings'
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@id='headerLinks']/div[5]/a"));
    driver.findElement(By.xpath("//div[@id='headerLinks']/div[5]/a")).click();
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@id='popup']"));
    assertNotNull(driver.findElement(By.xpath("//span[@id='widgetParameters']/div/input")));
    //The parameter MUST be equal to the one set
    assertEquals(driver.findElement(By.xpath("//span[@id='widgetParameters']/div/span")).getText(), paramName);
    //Press on the check box
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//span[@id='widgetParameters']/div/input"));
    //ElementHelper.WaitForElementPresenceAndVisible(driver, (By.xpath("//span[@id='widgetParameters']/div/input")));
    driver.findElement(By.xpath("//span[@id='widgetParameters']/div/input")).click();
    //Press button save
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@class='popupbuttons']/button[@id='popup_state0_buttonSave']"));
    //ElementHelper.WaitForElementPresenceAndVisible(driver, (By.xpath("//div[@class='popupbuttons']/button[@id='popup_state0_buttonSave']")));
    driver.findElement(By.xpath("//div[@class='popupbuttons']/button[@id='popup_state0_buttonSave']")).click();

    ElementHelper.WaitForElementInvisibility(driver, By.id("popupbox"));
    //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("popupbox")));

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
  public static WebDriver CreateWidget(WebDriver driver, String widgetName) {
    //Step 1 - Go to homepage
    driver.switchTo().defaultContent();
    String baseUrl = CToolsTestSuite.getBaseUrl();
    driver.get(baseUrl + "Home");
    //wait for visibility of waiting pop-up
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='busy-indicator-container waitPopup']"));
    //Wait for the visibility of Menu and frame contents
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='mainMenubar']"));
    driver.switchTo().frame("home.perspective");
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='buttonWrapper']"));

    //Step 2 - Click in Create New (CDE Dashboard)
    //Click to create a widget
    WebElement buttonCreateNew = driver.findElement(By.xpath("//button[@id='btnCreateNew']"));
    assertEquals(buttonCreateNew.getText(), "Create New");
    buttonCreateNew.click();
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.cssSelector("div.popover-content > #createNewlaunch_new_cdeButton"));
    WebElement buttonCDEBashBoard = ElementHelper.WaitForElementPresenceAndVisible(driver, By.cssSelector("div.popover-content > #createNewlaunch_new_cdeButton"));
    assertEquals(buttonCDEBashBoard.getText(), "CDE Dashboard");
    buttonCDEBashBoard.click();
    driver.switchTo().defaultContent();//back to the root

    //Step 3 - Click in Component Panel
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe"));
    WebElement frameCDEDashboard = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='solutionNavigatorAndContentPanel']/div[4]/table/tbody/tr[2]/td/div/div/table/tbody/tr/td/iframe"));
    driver.switchTo().frame(frameCDEDashboard);
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@class='componentsPanelButton']"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@class='componentsPanelButton']")).click();

    //Step 4 - Save the widget
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='headerLinks']/div[2]/a"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='headerLinks']/div[2]/a")).click();
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='popup']"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='container_id']/ul/li"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//input[@id='widgetRadio']")).click();

    //Insert file name
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//input[@id='fileInput']")).sendKeys(widgetName);
    //Insert widget name
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//input[@id='componentInput']")).sendKeys(widgetName);
    //Press OK (SAVING)
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//button[@id='popup_state0_buttonOk']")).click();
    //Wait for the pop-up exit
    ElementHelper.WaitForElementInvisibility(driver, By.id("popupbox"));

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
  public static WebDriver OpenWidgetEditMode(WebDriver driver,
    Wait<WebDriver> wait,
    String baseUrl,
    String widgetName) {
    //Resuming Steps
    // 1. open the widget
    // 2. check if the parameter exist in settings
    // 3. check if the widget exist in 'widgets' at Component Layout
    driver.switchTo().defaultContent();
    driver.get(baseUrl + "Home");
    //wait for visibility of waiting pop-up
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='busy-indicator-container waitPopup']"));
    //Step 1 - Go to Homepage and click 'Browse Files'
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//iframe[@id='home.perspective']"));
    assertNotNull(driver.findElement(By.xpath("//iframe[@id='home.perspective']")));
    //Go to the Home Perspective [IFRAME]
    driver.switchTo().frame("home.perspective");
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@class='well sidebar']"));
    driver.findElement(By.xpath("//div[@class='well sidebar']/button")).click();//Click in 'Browse Files'

    //Step 2  - Go to 'widgets' and click in the created widget and press 'Edit'
    //Now we have to navegate to 'Public/cde/widgets
    driver.switchTo().defaultContent();
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("applicationShell"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//iframe[@id='browser.perspective']"));
    driver.switchTo().frame("browser.perspective");
    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("fileBrowser")));
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='spinner large-spinner']"));
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("(//div[@class='spinner large-spinner'])[2]"));

    //Public
    assertNotNull(ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@id='fileBrowserFolders']")));
    assertNotNull(ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@path='/public']")));
    driver.findElement(By.xpath("//div[@path='/public']")).findElement(By.className("expandCollapse")).click();
    //CDE
    assertNotNull(ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@path='/public/cde']")));
    driver.findElement(By.xpath("//div[@path='/public/cde']")).findElement(By.className("expandCollapse")).click();
    //Widgets
    assertNotNull(ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@path='/public/cde/widgets']")));
    driver.findElement(By.xpath("//div[@path='/public/cde/widgets']")).findElement(By.className("title")).click();

    //wait for the page load in 'fileBrowserFiles'
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='spinner large-spinner']"));
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("(//div[@class='spinner large-spinner'])[2]"));
    //Check if at least one file is displayed
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//div[@id='fileBrowserFiles']/div[2]/div"));
    WebElement listFiles = driver.findElement(By.xpath("//div[@id='fileBrowserFiles']/div[2]"));
    List<WebElement> theWidgetFiles = listFiles.findElements(By.xpath("//div[@class='title' and contains(text(),'" + widgetName + "')]"));

    //Check if the widget named exist
    if (theWidgetFiles != null) {
      if (theWidgetFiles.size() > 0) {

        Actions action = new Actions(driver);
        action.click(theWidgetFiles.get(0));
        action.build().perform();

        //Here we still in the iframe
        assertNotNull(ElementHelper.WaitForElementVisibility(driver, By.id("editButton")));
        driver.findElement(By.id("editButton")).click();

        driver.switchTo().defaultContent();//back to the root
      }
    }

    return driver;
  }
}
