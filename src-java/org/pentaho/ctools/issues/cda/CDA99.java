/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2014 by Pentaho : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/
package org.pentaho.ctools.issues.cda;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDA-99
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-963
 *
 * NOTE
 * To test this script it is required to have CDA plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CDA99 {
  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  // Log instance
  private static Logger     log                = LogManager.getLogger(CDA99.class);
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CDA99.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  @Before
  public void setUpTestCase() {
    //Go to AddinReference
    driver.get(baseUrl + "plugin/cda/api/editFile?path=/public/plugin-samples/cda/cdafiles/olap4j.cda");

    //Wait for buttons: preview, reload, save AND file
    ElementHelper.WaitForElementVisibility(driver, By.id("preview"));
    ElementHelper.WaitForElementVisibility(driver, By.id("reload"));
    ElementHelper.WaitForElementVisibility(driver, By.id("save"));
    ElementHelper.WaitForElementVisibility(driver, By.id("staticfile"));

    //Check iframe
    driver.switchTo().frame("externalEditor");
    ElementHelper.WaitForElementVisibility(driver, By.xpath("//pre/div[2]/div/div[3]/div[1]"));
    driver.switchTo().defaultContent();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Changing connection type
   * Description:
   *    The test pretends validate the CDA-99 issue, so if we change the
   *    connection type to olap4j.defaultolap, the query are not performed,
   *    but adding olap4j it works.
   * Steps:
   *    1. Check the contents
   *    2. Change the code adding type='olap4j.defaultolap4j'
   *    3. Perform the preview and the query couldn't be performed
   *    4. Change the code adding type='olap4j'
   *    5. Perform the preview and the query could be performed
   */
  @Test(timeout = 120000)
  public void tc01_PageContent_DisplayContent() {
    log.info("tc01_PageContent_DisplayContent");

    try {

      /*
       * ## Step 1
       */
      String buttonPreviewText = ElementHelper.GetText(driver, By.id("preview"));
      String buttonReloadText = ElementHelper.GetText(driver, By.id("reload"));
      String buttonSaveText = ElementHelper.GetText(driver, By.id("save"));
      String buttonStaticFileText = ElementHelper.GetText(driver, By.id("staticfile"));
      assertEquals("Preview", buttonPreviewText);
      assertEquals("Reload", buttonReloadText);
      assertEquals("Save", buttonSaveText);
      assertEquals("/public/plugin-samples/cda/cdafiles/olap4j.cda", buttonStaticFileText);

      /*
       * ## Step 2
       */
      String code = ((JavascriptExecutor) driver).executeScript("return getEditorWindow().editor.getContents();").toString();
      code = code.replace("<DataAccess id=\"1\" connection=\"1\" type=\"olap4j\" access=\"public\">", "<DataAccess id=\"1\" connection=\"1\" type=\"olap4j.defaultolap4j\" access=\"public\">");
      ((JavascriptExecutor) driver).executeScript("getEditorWindow().editor.setContents(arguments[0]);", code);

      //Save file
      ElementHelper.Click(driver, By.id("save"));
      //Check for the message name
      String fileSaved = ElementHelper.GetText(driver, By.id("notifications"));
      assertEquals("/public/plugin-samples/cda/cdafiles/olap4j.cda saved ok.", fileSaved);

      /*
       * ## Step 3
       */
      //Perform the preview of this CDA query
      ElementHelper.Click(driver, By.id("preview"));

      WebDriver previewWindow = null;
      String currentWindowHandle = driver.getWindowHandle();
      Set<String> listWindows = driver.getWindowHandles();;

      //wait for popup render
      ElementHelper.WaitForNewWindow(driver);
      listWindows = driver.getWindowHandles();
      //Get the windowHandler of the new open window
      Iterator<String> iterWindows = listWindows.iterator();
      while (iterWindows.hasNext()) {
        String windowHandle = iterWindows.next();
        if (windowHandle.equals(currentWindowHandle) == false) {
          previewWindow = driver.switchTo().window(windowHandle);
          break;
        }
      }

      //Now in the PREVIEW WINDOW we want to check the available options
      Boolean selectNotExist = false;
      //Selector must be present
      WebElement selector = ElementHelper.FindElement(previewWindow, By.id("dataAccessSelector"));
      assertNotNull(selector);
      try {
        Select select = new Select(selector);
        select.selectByIndex(1);
      } catch (NoSuchElementException see) {
        selectNotExist = true;
      }
      assertTrue(selectNotExist);

      //Need guarantee we close everything
      previewWindow.close();
      driver.switchTo().window(currentWindowHandle);

      /*
       * ## Step 4
       */
      String code2 = ((JavascriptExecutor) driver).executeScript("return getEditorWindow().editor.getContents();").toString();
      code2 = code2.replace("<DataAccess id=\"1\" connection=\"1\" type=\"olap4j.defaultolap4j\" access=\"public\">", "<DataAccess id=\"1\" connection=\"1\" type=\"olap4j\" access=\"public\">");
      ((JavascriptExecutor) driver).executeScript("getEditorWindow().editor.setContents(arguments[0]);", code2);

      //Save file
      ElementHelper.Click(driver, By.id("save"));
      //Check for the message name
      String fileSaved2 = ElementHelper.GetText(driver, By.id("notifications"));
      assertEquals("/public/plugin-samples/cda/cdafiles/olap4j.cda saved ok.", fileSaved2);

      /*
       * ## Step 5
       */
      //Perform the preview of this CDA query
      ElementHelper.Click(driver, By.id("preview"));

      previewWindow = null;
      listWindows = null;
      //wait for popup render
      ElementHelper.WaitForNewWindow(driver);
      listWindows = driver.getWindowHandles();
      //Get the windowHandler of the new open window
      iterWindows = null;
      iterWindows = listWindows.iterator();
      while (iterWindows.hasNext()) {
        String windowHandle = iterWindows.next();
        if (windowHandle.equals(currentWindowHandle) == false) {
          previewWindow = driver.switchTo().window(windowHandle);
          break;
        }
      }

      //Now in the PREVIEW WINDOW we want to check the available options
      selectNotExist = false;
      //wait for file id contains text
      ElementHelper.WaitForTextPresent(previewWindow, By.id("fileid"), "/public/plugin-samples/cda/cdafiles/olap4j.cda");
      //Selector must be present
      selector = ElementHelper.FindElement(previewWindow, By.id("dataAccessSelector"));
      assertNotNull(selector);

      try {
        Select select = new Select(selector);
        select.selectByIndex(1);
        //Not we have to wait for loading disappear
        ElementHelper.WaitForElementInvisibility(previewWindow, By.xpath("//div[@class='blockUI blockOverlay']"));
        //Get value of status
        String value = ElementHelper.GetInputValue(previewWindow, By.id("status"));
        assertEquals("In Process", value);
      } catch (NoSuchElementException see) {
        selectNotExist = true;
      }

      //Need guarantee we close everything
      previewWindow.close();
      driver.switchTo().window(currentWindowHandle);

      assertEquals(false, selectNotExist);
    } catch (AssertionError e) {
      log.info(e.getMessage());
      try {
        Thread.sleep(100000);
      } catch (Exception e2) {
        e2.getMessage();
      }
    }
  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CDA99.class.getSimpleName());
  }
}
