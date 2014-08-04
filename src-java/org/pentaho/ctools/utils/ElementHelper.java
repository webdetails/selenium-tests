package org.pentaho.ctools.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.NoSuchElementException;

public class ElementHelper {

  /**
   * This method shall check if the element to search is displayed and
   * is enabled.
   *
   * @param path - DOM element to search.
   */
  public static void IsElementDisplayed(WebDriver driver, By path) {
    for (int i = 0; i < 100; i++) {
      try {
        WebElement element = driver.findElement(path);
        if (element != null) {
          if (element.isDisplayed() && element.isEnabled()) {
            return;
          }
        }
      } catch ( NoSuchElementException ex ){
        System.out.println(ex.getMessage());
      }

      try {
        Thread.sleep(200);

      } catch (InterruptedException ex) {
        System.out.println("Exception timeout");
      }
    }
  }
}