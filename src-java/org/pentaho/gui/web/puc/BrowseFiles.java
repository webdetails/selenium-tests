package org.pentaho.gui.web.puc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.PUCSettings;

public class BrowseFiles {
  private WebDriver     driver;
  //private String        baseURL;
  private static Logger log = LogManager.getLogger(BrowseFiles.class);

  public BrowseFiles(WebDriver driver) {
    this.driver = driver;
    driver.get("http://localhost:8080/pentaho/Home");
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='busy-indicator-container waitPopup']"));
    //this.baseURL = baseURL;
    SetSHOWHIDDENFILES();
    CheckShowHiddenFiles();
    GoToBrowseFiles();
  }

  private void GoToBrowseFiles() {
    log.info("Enter: GoToBrowseFiles");
    //driver.get(baseURL + "Home");
    driver.switchTo().frame("home.perspective");
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@class='well sidebar']"));

    log.info("Enter: Click Browse Files Button");
    //Click in 'Browse Files'
    ElementHelper.Click(driver, By.xpath("//div[@class='well sidebar']/button"));
    log.info("Exit: Click Browse Files Button");

    log.info("Enter: Assert browser perspective shown");
    //Focus Browser Perspective and refresh repository
    driver.switchTo().defaultContent();
    assertNotNull(ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("applicationShell")));
    assertNotNull(ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//iframe[@id='browser.perspective']")));
    driver.switchTo().frame("browser.perspective");

    assertNotNull(ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("fileBrowser")));
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='spinner large-spinner']"));
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("(//div[@class='spinner large-spinner'])[2]"));

    ElementHelper.Click(driver, By.id("refreshBrowserIcon"));
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='spinner large-spinner']"));
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("(//div[@class='spinner large-spinner'])[2]"));

    //Assert Panels
    assertNotNull(ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='fileBrowserFolders']")));
    assertNotNull(ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='fileBrowserFiles']")));
    assertNotNull(ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='fileBrowserButtons']")));
    log.info("Exit: Assert browser perspective shown");
    log.info("Exit: GoToBrowseFiles");
  }

  public void EmptyTrash() {
    log.info("Enter: EmptyTrash");
    log.info("Enter: Checking if Trash is empty");
    //Select Trash folder
    assertNotNull(ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@path='.trash']/div/div[@class='title']")));
    ElementHelper.Click(driver, By.xpath("//div[@path='.trash']/div/div[@class='title']"));
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='spinner large-spinner']"));
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("(//div[@class='spinner large-spinner'])[2]"));

    //If Trash not empty, empty it
    WebElement element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='fileBrowserFiles']/div[2]/div[@class='emptyFolder']"), 3);
    log.info("Exit: Checking if Trash is empty");
    if (element == null) {
      log.info("Enter: Emptying Trash");
      assertNotNull(ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='fileBrowserButtons']//button[@id='purge']")));
      ElementHelper.Click(driver, By.xpath("//div[@id='fileBrowserButtons']//button[@id='purge']"));
      driver.switchTo().defaultContent();
      assertNotNull(ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@class='dialogTopCenterInner']")));
      String text = ElementHelper.WaitForTextPresence(driver, By.xpath("//div[@class='dialogTopCenterInner']/div"), "Empty Trash");
      assertEquals("Empty Trash", text);
      assertNotNull(ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("okButton")));
      ElementHelper.Click(driver, By.id("okButton"));
      driver.switchTo().frame("browser.perspective");
      ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='busy-indicator-container waitPopup']"));
      ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='spinner large-spinner']"));
      ElementHelper.WaitForElementInvisibility(driver, By.xpath("(//div[@class='spinner large-spinner'])[2]"));
      assertNotNull(ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='fileBrowserFiles']/div[2]/div")));
      text = ElementHelper.WaitForTextPresence(driver, By.xpath("//div[@id='fileBrowserFiles']/div[2]/div/span"), "There are no files in this folder.");
      assertEquals("There are no files in this folder.", text);
      log.info("Exit: Emptying Trash");
    }
    log.info("Exit: EmptyTrash");
  }

  public boolean SelectFile(String path) {
    log.info("Enter: SelectFile");
    Boolean selected = true;
    String[] folders = path.split("/");
    log.info("Enter: Expanding Path");
    for (int i = 1; i < folders.length; i++) {
      folders[i] = folders[i - 1] + "/" + folders[i];
      if (i == folders.length - 1) {
        log.info("Enter: Selecting File");
        if (ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='fileBrowserFiles']/div[2]/div[@path='" + folders[i] + "']/div[2]")) != null) {
          assertNotNull(ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='fileBrowserFiles']/div[2]/div[@path='" + folders[i] + "']/div[2]")));
          ElementHelper.Click(driver, By.xpath("//div[@id='fileBrowserFiles']/div[2]/div[@path='" + folders[i] + "']/div[2]"));
          ElementHelper.WaitForAttributeValue(driver, By.xpath("//div[@id='fileBrowserFiles']/div[2]/div[@path='" + folders[i] + "']"), "class", "file selected");
          String text = ElementHelper.GetAttribute(driver, By.xpath("//div[@id='fileBrowserFiles']/div[2]/div[@path='" + folders[i] + "']"), "class");
          assertEquals("file selected", text);
          log.info("Exit: Selecting File");
        }
        else {
          log.info("File " + folders[i] + " was not found");
          selected = false;
        }
      }
      else if (i == folders.length - 2) {
        log.info("Exit: Expanding Path");
        log.info("Enter: Selecting Folder");
        if (ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']/div/div[3]")) != null) {
          assertNotNull(ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']/div/div[3]")));
          ElementHelper.Click(driver, By.xpath("//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']/div/div[3]"));
          ElementHelper.WaitForAttributeValue(driver, By.xpath("//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']"), "class", "selected");
          String text = ElementHelper.GetAttribute(driver, By.xpath("//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']"), "class");
          assertTrue(text.contains("selected"));
          log.info("Enter: Selecting Folder");
        }
        else {
          log.info("Folder " + folders[i] + " was not found");
          selected = false;
        }
      }
      else {
        //If folder exists select it
        if (ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']/div/div[@class='expandCollapse']")) != null) {
          assertNotNull(ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']/div/div[@class='expandCollapse']")));
          ElementHelper.Click(driver, By.xpath("//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']/div/div[@class='expandCollapse']"));
          ElementHelper.WaitForAttributeValue(driver, By.xpath("//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']"), "class", "open");
          String text = ElementHelper.GetAttribute(driver, By.xpath("//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']"), "class");
          assertTrue(text.contains("open"));
          log.info("Exit: Expanding Path");
        }
        else {
          log.info("Folder " + folders[i] + " was not found");
          selected = false;
        }
      }
    }
    log.info("Exit: SelectFile");
    return selected;
  }

  public void SelectFolder(String path) {
    log.info("Enter: SelectFolder");
    String[] folders = path.split("/");
    log.info("Enter: Expanding Path");
    for (int i = 1; i < folders.length; i++) {
      folders[i] = folders[i - 1] + "/" + folders[i];
      if (i == folders.length - 1) {
        log.info("Exit: Expanding Path");
        log.info("Enter: Selecting Folder");
        assertNotNull(ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']/div/div[3]")));
        ElementHelper.Click(driver, By.xpath("//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']/div/div[3]"));
        ElementHelper.WaitForAttributeValue(driver, By.xpath("//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']"), "class", "selected");
        String text = ElementHelper.GetAttribute(driver, By.xpath("//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']"), "class");
        assertTrue(text.contains("selected"));
        log.info("Exit: Selecting Folder");
      }
      else {
        assertNotNull(ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']/div/div[@class='expandCollapse']")));
        ElementHelper.Click(driver, By.xpath("//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']/div/div[@class='expandCollapse']"));
        ElementHelper.WaitForAttributeValue(driver, By.xpath("//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']"), "class", "open");
        String text = ElementHelper.GetAttribute(driver, By.xpath("//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']"), "class");
        assertTrue(text.contains("open"));
      }
    }
    log.info("Exit: SelectFolder");
  }

  public void CheckShowHiddenFiles() {
    WebElement element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("viewmenu"));
    assertNotNull(element);
    element.click();
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//td[@id='showHiddenFilesMenuItem']"));
    assertNotNull(element);
    String text = element.getAttribute("class");
    if (text.equals("gwt-MenuItem-checkbox-unchecked")) {
      element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//td[@id='showHiddenFilesMenuItem']"));
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
    PUCSettings.SHOWHIDDENFILES = true;
  }

  public void UncheckShowHiddenFiles() {
    WebElement element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//iframe[@id='home.perspective']"));
    assertNotNull(element);
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("viewmenu"));
    assertNotNull(element);
    element.click();
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//td[@id='showHiddenFilesMenuItem']"));
    assertNotNull(element);
    String text = element.getAttribute("class");
    if (text.equals("gwt-MenuItem-checkbox-checked")) {
      element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//td[@id='showHiddenFilesMenuItem']"));
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
      assertEquals("gwt-MenuItem-checkbox-unchecked", text);
      element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("viewmenu"));
      assertNotNull(element);
      element.click();
    } else {
      element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("viewmenu"));
      element.click();
    }
    PUCSettings.SHOWHIDDENFILES = false;
  }

  public boolean SetSHOWHIDDENFILES() {
    WebElement element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("viewmenu"));
    assertNotNull(element);
    element.click();
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//td[@id='showHiddenFilesMenuItem']"));
    assertNotNull(element);
    String text = element.getAttribute("class");
    if (text.equals("gwt-MenuItem-checkbox-checked")) {
      PUCSettings.SHOWHIDDENFILES = true;
    }
    else if (text.equals("gwt-MenuItem-checkbox-unchecked")) {
      PUCSettings.SHOWHIDDENFILES = false;
    }
    element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("viewmenu"));
    element.click();
    return PUCSettings.SHOWHIDDENFILES;
  }

  public void DeleteFile(String path) {
    if (SelectFile(path)) {
      WebElement element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='fileBrowserButtons']/div[2]/button[@id='deleteButton']"));
      assertNotNull(element);
      ElementHelper.Click(driver, By.xpath("//div[@id='fileBrowserButtons']/div[2]/button[@id='deleteButton']"));
      driver.switchTo().defaultContent();
      element = ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("okButton"));
      assertNotNull(element);
      ElementHelper.Click(driver, By.id("okButton"));
      //wait for invisibility of waiting pop-up
      ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='busy-indicator-container waitPopup']"));
      // Wait for loading disappear
      ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
    }
    else {
      log.info("file not deleted for it was not found");
    }
  }
}
