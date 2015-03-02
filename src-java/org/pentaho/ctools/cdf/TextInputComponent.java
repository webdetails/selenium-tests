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

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * Testing the functionalities related with Text Input Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TextInputComponent {
  //Instance of the driver (browser emulator)
  private static WebDriver       driver;
  // Instance to be used on wait commands
  private static Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private static String          baseUrl;

  @Rule
  public ScreenshotTestRule      screenshotTestRule = new ScreenshotTestRule(driver);

  /**
   * Shall initialized the test before run each test case.
   */
  @BeforeClass
  public static void setUp() {
    driver = CToolsTestSuite.getDriver();
    wait = CToolsTestSuite.getWait();
    baseUrl = CToolsTestSuite.getBaseUrl();

    // Go to sample
    init();
  }

  /**
   * Go to the TextInputComponent web page.
   */
  public static void init() {
    // The URL for the CheckComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Documentation/Component
    // Reference/Core Components/TextInputComponent
    driver.get(baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A37-TextInputComponent%3Atext_input_component.xcdf/generatedContent");

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
    // Wait for title become visible and with value 'Community Dashboard Framework'
    wait.until(ExpectedConditions.titleContains("Community Dashboard Framework"));
    // Wait for visibility of 'VisualizationAPIComponent'
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dashboardContent']/div/div/div/h2/span[2]")));

    // Validate the sample that we are testing is the one
    assertEquals("Community Dashboard Framework", driver.getTitle());
    assertEquals("TextInputComponent", ElementHelper.GetText(driver, By.xpath("//div[@id='dashboardContent']/div/div/div/h2/span[2]")));
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
   *    Insert a small text
   * Description:
   *    We pretend validate when we insert a small text an alert is raised.
   * Steps:
   *    1. Insert text
   *    2. Check for alert
   *    3. Check the input text inserted
   */
  @Test(timeout = 60000)
  public void tc3_InputSmallPhrase_AlertDispayed() {
    // ## Step 1
    String strInputString = "Hello World!";
    ElementHelper.FindElement(driver, By.id("myInput")).clear();
    ElementHelper.FindElement(driver, By.id("myInput")).sendKeys(strInputString);
    ElementHelper.FindElement(driver, By.id("myInput")).sendKeys(Keys.ENTER);

    // ## Step 2
    wait.until(ExpectedConditions.alertIsPresent());
    Alert alert = driver.switchTo().alert();
    String confirmationMsg = alert.getText();
    String expectedCnfText = "you typed: " + strInputString;
    alert.accept();

    assertEquals(expectedCnfText, confirmationMsg);
  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name:
   *    Insert a long text
   * Description:
   *    We pretend validate when we insert a long text an alert is raised.
   * Steps:
   *    1. Insert text
   *    2. Check for alert
   *    3. Check the input text inserted
   */
  @Test(timeout = 60000)
  public void tc4_InputLongPhrase_AlertDispayed() {
    // ## Step 1
    String strInputString = "Hello World! Hello World! Hello World! Hello World! Hello World! Hello World!";
    strInputString += strInputString;
    strInputString += strInputString;
    strInputString += strInputString;
    strInputString += strInputString;
    ElementHelper.FindElement(driver, By.id("myInput")).clear();
    //After clean text, we need to trait the pop-up
    wait.until(ExpectedConditions.alertIsPresent());
    Alert alert = driver.switchTo().alert();
    alert.accept();

    ElementHelper.FindElement(driver, By.id("myInput")).sendKeys(strInputString);
    ElementHelper.FindElement(driver, By.id("myInput")).sendKeys(Keys.ENTER);

    // ## Step 2
    wait.until(ExpectedConditions.alertIsPresent());
    alert = driver.switchTo().alert();
    String confirmationMsg = alert.getText();
    String expectedCnfText = "you typed: " + strInputString;
    alert.accept();

    assertEquals(expectedCnfText, confirmationMsg);
  }

  /**
   * ############################### Test Case 5 ###############################
   *
   * Test Case Name:
   *    Insert special characters
   * Description:
   *    We pretend validate when we insert a special characters an alert is
   *    raised.
   * Steps:
   *    1. Insert text
   *    2. Check for alert
   *    3. Check the input text inserted
   */
  @Test(timeout = 60000)
  public void tc5_InputSpecialPhrase_AlertDispayed() {
    // ## Step 1
    String strInputString = "`|!\"1#$%&/()=?*»ª:_Ç<>/*-+";
    ElementHelper.FindElement(driver, By.id("myInput")).clear();
    //After clean text, we need to trait the pop-up
    wait.until(ExpectedConditions.alertIsPresent());
    Alert alert = driver.switchTo().alert();
    alert.accept();

    ElementHelper.FindElement(driver, By.id("myInput")).sendKeys(strInputString);
    ElementHelper.FindElement(driver, By.id("myInput")).sendKeys(Keys.ENTER);

    // ## Step 2
    wait.until(ExpectedConditions.alertIsPresent());
    alert = driver.switchTo().alert();
    String confirmationMsg = alert.getText();
    String expectedCnfText = "you typed: " + strInputString;
    alert.accept();

    assertEquals(expectedCnfText, confirmationMsg);
  }

  @AfterClass
  public static void tearDown() {}
}
