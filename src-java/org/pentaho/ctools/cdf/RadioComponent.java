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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Testing the functionalities related with Radio Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RadioComponent {
  //Instance of the driver (browser emulator)
  private static WebDriver       driver;
  // Instance to be used on wait commands
  private static Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private static String          baseUrl;
  //Log instance
  private static Logger log = LogManager.getLogger(RadioComponent.class);
  
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  /**
   * Shall initialized the test before run each test case.
   */
  @BeforeClass
  public static void setUp() {
    log.debug("setup");
    driver  = CToolsTestSuite.getDriver();
    wait    = CToolsTestSuite.getWait();
    baseUrl = CToolsTestSuite.getBaseUrl();

    // Go to sample
    init();
  }

  /**
   * Go to the RadioComponent web page.
   */
  public static void init() {
    // The URL for the CheckComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Documentation/Component
    // Reference/Core Components/QueryComponent
    driver.get(baseUrl+ "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A52-RadioComponent%3Aradio_component.xcdf/generatedContent");

    // Not we have to wait for loading disappear
    ElementHelper.IsElementInvisible(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
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
  @Test
  public void tc1_PageContent_DisplayTitle() {
    // Wait for title become visible and with value 'Community Dashboard Framework'
    wait.until(ExpectedConditions.titleContains("Community Dashboard Framework"));
    // Wait for visibility of 'VisualizationAPIComponent'
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dashboardContent']/div/div/div/h2/span[2]")));

    // Validate the sample that we are testing is the one
    assertEquals("Community Dashboard Framework", driver.getTitle());
    assertEquals("RadioComponent",ElementHelper.GetText(driver, By.xpath("//div[@id='dashboardContent']/div/div/div/h2/span[2]")));
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
  @Test
  public void tc2_ReloadSample_SampleReadyToUse() {
    // ## Step 1
    // Render again the sample
    ElementHelper.FindElement(driver, By.xpath("//div[@id='example']/ul/li[2]/a")).click();
    ElementHelper.FindElement(driver, By.xpath("//div[@id='code']/button")).click();

    // Not we have to wait for loading disappear
    ElementHelper.IsElementInvisible(driver, By.xpath("//div[@class='blockUI blockOverlay']"));

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
   *    Select options one by one
   * Description: 
   *    We pretend validate the selection of each option one by one.
   * Steps:
   *    1. Select Eastern
   *    2. Select Central
   *    3. Select Western
   *    4. Select Southern
   */
  @Test
  public void tc3_SelectEachItem_AlertDisplayed() {
    log.debug("tc3_SelectEachItem_AlertDisplayed");

    // ## Step 1
    assertTrue(ElementHelper.FindElement(driver, By.xpath("//input[@value='Southern']")).isSelected());
    assertFalse(ElementHelper.FindElement(driver, By.xpath("//input[@value='Eastern']")).isSelected());
    assertFalse(ElementHelper.FindElement(driver, By.xpath("//input[@value='Central']")).isSelected());
    assertFalse(ElementHelper.FindElement(driver, By.xpath("//input[@value='Western']")).isSelected());
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='Eastern']")));
    ElementHelper.FindElement(driver, By.xpath("//input[@value='Eastern']")).click();
    wait.until(ExpectedConditions.alertIsPresent());
    Alert alert = driver.switchTo().alert();
    String confirmationMsg = alert.getText();
    alert.accept();
    assertEquals("you chose: Eastern", confirmationMsg);
    assertFalse(ElementHelper.FindElement(driver, By.xpath("//input[@value='Southern']")).isSelected());
    assertTrue(ElementHelper.FindElement(driver, By.xpath("//input[@value='Eastern']")).isSelected());
    assertFalse(ElementHelper.FindElement(driver, By.xpath("//input[@value='Central']")).isSelected());
    assertFalse(ElementHelper.FindElement(driver, By.xpath("//input[@value='Western']")).isSelected());


    // ## Step 2
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='Central']")));
    ElementHelper.FindElement(driver, By.xpath("//input[@value='Central']")).click();
    wait.until(ExpectedConditions.alertIsPresent());
    alert = driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals("you chose: Central", confirmationMsg);
    assertFalse(ElementHelper.FindElement(driver, By.xpath("//input[@value='Southern']")).isSelected());
    assertFalse(ElementHelper.FindElement(driver, By.xpath("//input[@value='Eastern']")).isSelected());
    assertTrue(ElementHelper.FindElement(driver, By.xpath("//input[@value='Central']")).isSelected());
    assertFalse(ElementHelper.FindElement(driver, By.xpath("//input[@value='Western']")).isSelected());


    // ## Step 3
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='Western']")));
    ElementHelper.FindElement(driver, By.xpath("//input[@value='Western']")).click();
    wait.until(ExpectedConditions.alertIsPresent());
    alert = driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals("you chose: Western", confirmationMsg);
    assertFalse(ElementHelper.FindElement(driver, By.xpath("//input[@value='Southern']")).isSelected());
    assertFalse(ElementHelper.FindElement(driver, By.xpath("//input[@value='Eastern']")).isSelected());
    assertFalse(ElementHelper.FindElement(driver, By.xpath("//input[@value='Central']")).isSelected());
    assertTrue(ElementHelper.FindElement(driver, By.xpath("//input[@value='Western']")).isSelected());


    // ## Step 4
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='Southern']")));
    ElementHelper.FindElement(driver, By.xpath("//input[@value='Southern']")).click();
    wait.until(ExpectedConditions.alertIsPresent());
    alert = driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals("you chose: Southern", confirmationMsg);
    assertTrue(ElementHelper.FindElement(driver, By.xpath("//input[@value='Southern']")).isSelected());
    assertFalse(ElementHelper.FindElement(driver, By.xpath("//input[@value='Eastern']")).isSelected());
    assertFalse(ElementHelper.FindElement(driver, By.xpath("//input[@value='Central']")).isSelected());
    assertFalse(ElementHelper.FindElement(driver, By.xpath("//input[@value='Western']")).isSelected());
  }
  
  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name: 
   *    Select arbitrary options
   * Description: 
   *    We pretend validate the selection every available options but arbitrary.
   * Steps:
   *    1. Select Western
   *    2. Select Southern
   *    3. Select Central
   *    4. Select Western
   */
  @Test
  public void tc4_SelectArbitrary_AlertDisplayed() {
    log.debug("tc4_SelectArbitrary_AlertDisplayed");

    // ## Step 1
    assertTrue(ElementHelper.FindElement(driver, By.xpath("//input[@value='Southern']")).isSelected());
    assertFalse(ElementHelper.FindElement(driver, By.xpath("//input[@value='Eastern']")).isSelected());
    assertFalse(ElementHelper.FindElement(driver, By.xpath("//input[@value='Central']")).isSelected());
    assertFalse(ElementHelper.FindElement(driver, By.xpath("//input[@value='Western']")).isSelected());
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='Western']")));
    ElementHelper.FindElement(driver, By.xpath("//input[@value='Western']")).click();
    wait.until(ExpectedConditions.alertIsPresent());
    Alert alert = driver.switchTo().alert();
    String confirmationMsg = alert.getText();
    alert.accept();
    assertEquals("you chose: Western", confirmationMsg);
    assertFalse(ElementHelper.FindElement(driver, By.xpath("//input[@value='Southern']")).isSelected());
    assertFalse(ElementHelper.FindElement(driver, By.xpath("//input[@value='Eastern']")).isSelected());
    assertFalse(ElementHelper.FindElement(driver, By.xpath("//input[@value='Central']")).isSelected());
    assertTrue(ElementHelper.FindElement(driver, By.xpath("//input[@value='Western']")).isSelected());


    // ## Step 2
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='Southern']")));
    ElementHelper.FindElement(driver, By.xpath("//input[@value='Southern']")).click();
    wait.until(ExpectedConditions.alertIsPresent());
    alert = driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals("you chose: Southern", confirmationMsg);
    assertTrue(ElementHelper.FindElement(driver, By.xpath("//input[@value='Southern']")).isSelected());
    assertFalse(ElementHelper.FindElement(driver, By.xpath("//input[@value='Eastern']")).isSelected());
    assertFalse(ElementHelper.FindElement(driver, By.xpath("//input[@value='Central']")).isSelected());
    assertFalse(ElementHelper.FindElement(driver, By.xpath("//input[@value='Western']")).isSelected());


    // ## Step 3
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='Central']")));
    ElementHelper.FindElement(driver, By.xpath("//input[@value='Central']")).click();
    wait.until(ExpectedConditions.alertIsPresent());
    alert = driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals("you chose: Central", confirmationMsg);
    assertFalse(ElementHelper.FindElement(driver, By.xpath("//input[@value='Southern']")).isSelected());
    assertFalse(ElementHelper.FindElement(driver, By.xpath("//input[@value='Eastern']")).isSelected());
    assertTrue(ElementHelper.FindElement(driver, By.xpath("//input[@value='Central']")).isSelected());
    assertFalse(ElementHelper.FindElement(driver, By.xpath("//input[@value='Western']")).isSelected());


    // ## Step 4
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='Western']")));
    ElementHelper.FindElement(driver, By.xpath("//input[@value='Western']")).click();
    wait.until(ExpectedConditions.alertIsPresent());
    alert = driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals("you chose: Western", confirmationMsg);
    assertFalse(ElementHelper.FindElement(driver, By.xpath("//input[@value='Southern']")).isSelected());
    assertFalse(ElementHelper.FindElement(driver, By.xpath("//input[@value='Eastern']")).isSelected());
    assertFalse(ElementHelper.FindElement(driver, By.xpath("//input[@value='Central']")).isSelected());
    assertTrue(ElementHelper.FindElement(driver, By.xpath("//input[@value='Western']")).isSelected());
  }

  @AfterClass
  public static void tearDown() {
    log.debug("tearDown");
  }
}
