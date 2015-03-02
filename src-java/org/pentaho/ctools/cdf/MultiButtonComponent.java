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
import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * Testing the functionalities related with MultiButton Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MultiButtonComponent {
  //Instance of the driver (browser emulator)
  private static WebDriver       driver;
  // Instance to be used on wait commands
  private static Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private static String          baseUrl;
  //Log instance
  private static Logger          log                = LogManager.getLogger(MultiButtonComponent.class);

  @Rule
  public ScreenshotTestRule      screenshotTestRule = new ScreenshotTestRule(driver);

  /**
   * Shall initialized the test before run each test case.
   */
  @BeforeClass
  public static void setUp() {
    log.info("setUp##" + MultiButtonComponent.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    wait = CToolsTestSuite.getWait();
    baseUrl = CToolsTestSuite.getBaseUrl();

    // Go to sample
    init();
  }

  /**
   * Go to the MultiButtonComponent web page.
   */
  public static void init() {
    // The URL for the CheckComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Documentation/Component
    // Reference/Core Components/MultiButtonComponent
    driver.get(baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A56-MultiButtonComponent%3Amultibutton_component.xcdf/generatedContent");

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
    wait.until(ExpectedConditions.titleContains("Community Dashboard Framework"));
    // Wait for visibility of 'VisualizationAPIComponent'
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dashboardContent']/div/div/div/h2/span[2]")));

    // Validate the sample that we are testing is the one
    assertEquals("Community Dashboard Framework", driver.getTitle());
    assertEquals("MultiButtonComponent", ElementHelper.GetText(driver, By.xpath("//div[@id='dashboardContent']/div/div/div/h2/span[2]")));
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
   *    Click In Each Button
   * Description:
   *    The test case pretends to validate the buttons works ok.
   * Steps:
   *    1. Click in Eastern
   *    2. Click in Central
   *    3. Click in Western
   *    4. Click in Southern
   */
  @Test(timeout = 60000)
  public void tc3_ClickInEachOption_AlertDisplayed() {
    log.info("tc3_ClickInEachOption_AlertDisplayed");

    // ## Step 1
    ElementHelper.FindElement(driver, By.xpath("//button[contains(text(),'Eastern')]")).click();
    wait.until(ExpectedConditions.alertIsPresent());
    Alert alert = driver.switchTo().alert();
    String confirmationMsg = alert.getText();
    alert.accept();
    assertEquals("you chose: Eastern", confirmationMsg);

    // ## Step 2
    ElementHelper.FindElement(driver, By.xpath("//button[contains(text(),'Central')]")).click();
    wait.until(ExpectedConditions.alertIsPresent());
    alert = driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals("you chose: Central", confirmationMsg);

    // ## Step 3
    ElementHelper.FindElement(driver, By.xpath("//button[contains(text(),'Western')]")).click();
    wait.until(ExpectedConditions.alertIsPresent());
    alert = driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals("you chose: Western", confirmationMsg);

    // ## Step 4
    ElementHelper.FindElement(driver, By.xpath("//button[contains(text(),'Southern')]")).click();
    wait.until(ExpectedConditions.alertIsPresent());
    alert = driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals("you chose: Southern", confirmationMsg);
  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name:
   *    Click Arbitrary in available Button
   * Description:
   *    The test case pretends to validate no error occurs when we click
   *    arbitrary in the buttons.
   * Steps:
   *    1. Click in Central
   *    2. Click in Southern
   *    3. Click in Eastern
   *    4. Click in Southern
   */
  @Test(timeout = 60000)
  public void tc4_ClickArbitrary_AlertDisplayed() {
    log.debug("tc4_ClickArbitrary_AlertDisplayed");

    // ## Step 1
    ElementHelper.FindElement(driver, By.xpath("//button[contains(text(),'Central')]")).click();
    wait.until(ExpectedConditions.alertIsPresent());
    Alert alert = driver.switchTo().alert();
    String confirmationMsg = alert.getText();
    alert.accept();
    assertEquals("you chose: Central", confirmationMsg);

    // ## Step 2
    ElementHelper.FindElement(driver, By.xpath("//button[contains(text(),'Southern')]")).click();
    wait.until(ExpectedConditions.alertIsPresent());
    alert = driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals("you chose: Southern", confirmationMsg);

    // ## Step 3
    ElementHelper.FindElement(driver, By.xpath("//button[contains(text(),'Eastern')]")).click();
    wait.until(ExpectedConditions.alertIsPresent());
    alert = driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals("you chose: Eastern", confirmationMsg);

    // ## Step 4
    ElementHelper.FindElement(driver, By.xpath("//button[contains(text(),'Southern')]")).click();
    wait.until(ExpectedConditions.alertIsPresent());
    alert = driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals("you chose: Southern", confirmationMsg);
  }

  @AfterClass
  public static void tearDown() {
    log.info("tearDown##" + MultiButtonComponent.class.getSimpleName());
  }
}
