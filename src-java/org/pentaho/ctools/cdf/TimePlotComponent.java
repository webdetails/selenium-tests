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
package org.pentaho.ctools.cdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * Testing the functionalities related with Time Plot Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TimePlotComponent{

  //Instance of the driver (browser emulator)
  private static WebDriver       driver;
  // Instance to be used on wait commands
  private static Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private static String          baseUrl;
  //Log instance
  private static Logger          log                = LogManager.getLogger(TimePlotComponent.class);

  @Rule
  public ScreenshotTestRule      screenshotTestRule = new ScreenshotTestRule(driver);

  /**
   * Shall initialized the test before run each test case.
   */
  @BeforeClass
  public static void setUp() {
    log.info("setUp##" + TimePlotComponent.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    wait = CToolsTestSuite.getWait();
    baseUrl = CToolsTestSuite.getBaseUrl();

    // Go to sample
    init();
  }

  /**
   * Go to the TimePlotComponent web page.
   */
  public static void init() {
    // The URL for the CheckComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Documentation/Component
    // Reference/Core Components/TimePlotComponent
    driver.get(baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A31-TimePlotComponent%3Atimeplot_component.xcdf/generatedContent");

    // Not we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Reload Sample
   * Description:
   *    Reload the sample (not refresh page).
   * Steps:
   *    1. Click in Code and then click in button 'Try me'.
   */
  @Test(timeout = 60000)
  public void tc1_PageContent_DisplayTitle() {
    log.info("tc1_PageContent_DisplayTitle");
    // Wait for title become visible and with value 'Community Dashboard Framework'
    //wait.until(ExpectedConditions.titleContains("Community Dashboard Framework"));
    // Wait for visibility of 'VisualizationAPIComponent'
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dashboardContent']/div/div/div/h2/span[2]")));

    // Validate the sample that we are testing is the one
    //assertEquals("Community Dashboard Framework", driver.getTitle());
    assertEquals("timePlotComponent", ElementHelper.GetText(driver, By.xpath("//div[@id='dashboardContent']/div/div/div/h2/span[2]")));
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Reload Sample
   * Description:
   *    Reload the sample (not refresh page).
   * Steps:
   *    1. Click in Code and then click in button 'Try me'.
   */
  @Test(timeout = 60000)
  public void tc2_ReloadSample_SampleReadyToUse() {
    log.info("tc2_ReloadSample_SampleReadyToUse");
    // ## Step 1
    // Render again the sample
    ElementHelper.FindElement(driver, By.xpath("//div[@id='example']/ul/li[2]/a")).click();
    ElementHelper.FindElement(driver, By.xpath("//div[@id='code']/button")).click();

    // Not we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));

    // Now sample element must be displayed
    assertTrue(ElementHelper.FindElement(driver, By.id("sample")).isDisplayed());

    //Check the number of divs with id 'SampleObject'
    //Hence, we guarantee when click Try Me the previous div is replaced
    int nSampleObject = driver.findElements(By.id("sampleObject")).size();
    assertEquals(1, nSampleObject);
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Time Plot
   * Description:
   *    For this component we need to validate when user move mouse over plot
   *    we have new values for Total Price.
   * Steps:
   *    1. Check if the graphic is presented
   *    2. Move mouse over graphic and check the expected value for Total Price
   */
  @Test(timeout = 60000)
  public void tc3_MouseOverPlot_TotalPriceChanged() {
    log.info("tc3_MouseOverPlot_TotalPriceChanged");
    // ## Step 1
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='sampleObject']/div/span")));
    assertEquals("total order income     ", ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObject']/div/span")));
    assertEquals("Total Price   ", ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObject']/div/span[2]")));

    WebElement element2004 = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[contains(text(), '2004')]"));
    assertNotNull(element2004);

    // ## Step 2
    Actions acts = new Actions(driver);
    acts.moveToElement(ElementHelper.FindElement(driver, By.cssSelector("canvas.timeplot-canvas")), 10, 10);
    acts.build().perform();

    String expectedText = "Total Price = 6,864";
    String text = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='sampleObject']/div/span[2]"));

    assertTrue(text.startsWith(expectedText));
  }

  @AfterClass
  public static void tearDown() {
    log.info("tearDown##" + TimePlotComponent.class.getSimpleName());
  }
}
