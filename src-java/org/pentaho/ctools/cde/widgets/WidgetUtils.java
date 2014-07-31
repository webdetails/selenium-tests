package org.pentaho.ctools.cde.widgets;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class WidgetUtils {

  /**
   * This method is responsible to remove the widget from 'Browse Files'.
   *
   * @param driver
   * @param wait
   * @param widgetName
   */
  public static void RemoveWidgetByName(WebDriver driver, Wait<WebDriver> wait, String baseUrl, String widgetName){
    driver.get(baseUrl + "Home");

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[@id='home.perspective']")));
    assertNotNull(driver.findElement(By.xpath("//iframe[@id='home.perspective']")));

    //Go to the Home Perspective [IFRAME]
    driver.switchTo().frame("home.perspective");
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='well sidebar']")));
    driver.findElement(By.xpath("//div[@class='well sidebar']/button")).click();//Click in 'Browse Files'


    //Now we have to navegate to 'Public/cde/widgets
    driver.switchTo().defaultContent();
    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("applicationShell")));
    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//iframe[@id='browser.perspective']")));
    driver.switchTo().frame("browser.perspective");
    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@id='fileBrowser']")));
    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@id='fileBrowserFolders']")));
    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@id='buttonsHeader']")));

    //Public
    driver.findElement(By.xpath("//div[@id='fileBrowserFolders']/div[2]/div[2]/div/div")).click();
    //CDE
    driver.findElement(By.xpath("//div[@id='fileBrowserFolders']/div[2]/div[2]/div[2]/div[2]/div/div")).click();
    //widgets
    driver.findElement(By.xpath("//div[@id='fileBrowserFolders']/div[2]/div[2]/div[2]/div[2]/div[2]/div[4]/div/div[3]")).click();

    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@id='fileBrowserFiles']/div[2]")));
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
        driver.findElement(By.id("deleteButton")).click();
        //Go back to root html
        driver.switchTo().defaultContent();
        assertEquals(driver.findElement(By.cssSelector("div.gwt-HTML")).getText(),
            "Are you sure you want to move all selected items to the trash?");
        driver.findElement(By.id("okButton")).click();
      }
    }
  }
}
