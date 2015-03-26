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

public class BrowseFiles {
  private WebDriver     driver;
  private String        baseURL;
  private static Logger log = LogManager.getLogger(BrowseFiles.class);

  public BrowseFiles(WebDriver driver, String baseURL) {
    this.driver = driver;
    this.baseURL = baseURL;
    GoToBrowseFiles();
  }

  private void GoToBrowseFiles() {
    log.info("Enter: GoToBrowseFiles");
    driver.get(baseURL + "Home");
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

  public void SelectFile(String path) {
    log.info("Enter: SelectFile");
    String[] folders = path.split("/");
    log.info("Enter: Expanding Path");
    for (int i = 1; i < folders.length; i++) {
      folders[i] = folders[i - 1] + "/" + folders[i];
      if (i == folders.length - 1) {
        log.info("Enter: Selecting File");
        assertNotNull(ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='fileBrowserFiles']/div[2]/div[@path='" + folders[i] + "']/div[2]")));
        ElementHelper.Click(driver, By.xpath("//div[@id='fileBrowserFiles']/div[2]/div[@path='" + folders[i] + "']/div[2]"));
        ElementHelper.WaitForAttributeValue(driver, By.xpath("//div[@id='fileBrowserFiles']/div[2]/div[@path='" + folders[i] + "']"), "class", "file selected");
        String text = ElementHelper.GetAttribute(driver, By.xpath("//div[@id='fileBrowserFiles']/div[2]/div[@path='" + folders[i] + "']"), "class");
        assertEquals("file selected", text);
        log.info("Exit: Selecting File");
      }
      else if (i == folders.length - 2) {
        log.info("Exit: Expanding Path");
        log.info("Enter: Selecting Folder");
        assertNotNull(ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']/div/div[3]")));
        ElementHelper.Click(driver, By.xpath("//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']/div/div[3]"));
        ElementHelper.WaitForAttributeValue(driver, By.xpath("//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']"), "class", "selected");
        String text = ElementHelper.GetAttribute(driver, By.xpath("//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']"), "class");
        assertTrue(text.contains("selected"));
        log.info("Enter: Selecting Folder");
      }
      else {
        assertNotNull(ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']/div/div[@class='expandCollapse']")));
        ElementHelper.Click(driver, By.xpath("//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']/div/div[@class='expandCollapse']"));
        ElementHelper.WaitForAttributeValue(driver, By.xpath("//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']"), "class", "open");
        String text = ElementHelper.GetAttribute(driver, By.xpath("//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']"), "class");
        assertTrue(text.contains("open"));
        log.info("Exit: Expanding Path");
      }
    }
    log.info("Exit: SelectFile");
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
}
