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

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;

import static org.junit.Assert.*;

/**
 * Testing the functionalies related with component Visualization API.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VisualizationAPIComponent {
  // Instance of the driver (browser emulator)
  private static WebDriver driver;
  // Instance to be used on wait commands
  private static Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private static String baseUrl;

  /**
   * Shall inicialized the test before run each test case.
   */
  @BeforeClass
  public static void setUp(){
    driver = CToolsTestSuite.getDriver();
    wait = CToolsTestSuite.getWait();
    baseUrl = CToolsTestSuite.getBaseUrl();

    //Go to sample
    init();
  }

  /**
   * Go to the TableComponent web page.
   */
  public static void init(){
    //The URL for the VisualizationAPIComponent under CDF samples
    //This samples is in: Public/plugin-samples/CDF/Documentation/Component Reference/Core Components/VisualizationAPIComponent
    driver.get(baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A60-VisualizationAPIComponent%3Avisualizationapi_component.xcdf/generatedContent");

    //Wait for visibility of 'VisualizationAPIComponent'
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dashboardContent']/div/div/div/h2/span[2]")));
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Validate Page Contents
   * Description:
   *    Here we want to validate the page contents.
   * Steps:
   *    1. Check the widget's title.
   */
  @Test
  public void tc1_PageContent_DisplayTitle() {
    // Validate the sample that we are testing is the one
    assertEquals("Community Dashboard Framework", driver.getTitle());
    ElementHelper.IsElementDisplayed(driver, By.xpath("//div[@id='dashboardContent']/div/div/div/h2/span[2]"));
    assertEquals("VisualizationAPIComponent", driver.findElement(By.xpath("//div[@id='dashboardContent']/div/div/div/h2/span[2]")).getText());
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
  public void tc2_ReloadSample_SampleReadyToUse(){
    //## Step 1
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sample")));
    assertTrue(driver.findElement(By.id("sample")).isDisplayed());
    //Click in 'Code'
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='example']/ul/li[2]/a")));
    driver.findElement(By.xpath("//div[@id='example']/ul/li[2]/a")).click();
    assertFalse(driver.findElement(By.id("sample")).isDisplayed());
    //Click in 'Try me'
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#code > button")));
    driver.findElement(By.cssSelector("#code > button")).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sample")));
    assertTrue(driver.findElement(By.id("sample")).isDisplayed());
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Validate MAX number
   * Description:
   *    When the user access the component, it is presented the max number of
   *    array set.
   * Steps:
   *    1. Check the presented value for MAX.
   */
  @Test
  public void tc3_MaxNumber_PresentCorrectValue() {
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("example")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sample")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sampleObject")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("visualPanelElement-0")));

    //## Step 1
    assertEquals("35659", driver.findElement(By.xpath("//div[@id='visualPanelElement-0']/span")).getText());
  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name:
   *    Validate MIN number
   * Description:
   *    When the user access the component, it is presented the min number of
   *    array set.
   * Steps:
   *    1. Change the option parameter to MIN and reload sample
   *    2. Check the presented value for MIN.
   */
  @Test
  public void tc4_MinNumber_PresentCorrectValue() {
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("example")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sample")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sampleObject")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("visualPanelElement-0")));
    //## Step 1 - Change the option parameter to MIN and reload sample
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sample")));
    assertTrue(driver.findElement(By.id("sample")).isDisplayed());
    //Click in 'Code'
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='example']/ul/li[2]/a")));
    driver.findElement(By.xpath("//div[@id='example']/ul/li[2]/a")).click();
    assertFalse(driver.findElement(By.id("sample")).isDisplayed());
    //Click in 'Try me'
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("example")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("code")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='code']/button")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("samplecode")));

    ((JavascriptExecutor) driver).executeScript("$('#samplecode').text($('#samplecode').text().replace('MAX', 'MIN'));");

    driver.findElement(By.xpath("//button")).click();

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sample")));
    assertTrue(driver.findElement(By.id("sample")).isDisplayed());


    //## Step 2 - Check the presented value for MIN.
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("example")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sample")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sampleObject")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("visualPanelElement-0")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='visualPanelElement-0']/span"));
    assertEquals("0", driver.findElement(By.xpath("//div[@id='visualPanelElement-0']/span")).getText());
  }

  /**
   * ############################### Test Case 5 ###############################
   *
   * Test Case Name:
   *    Validate AVG number
   * Description:
   *    When the user access the component, it is presented the avg number of
   *    array set.
   * Steps:
   *    1. Change the option parameter to AVG and reload sample
   *    2. Check the presented value for AVG.
   */
  @Test
  public void tc5_AvgNumber_PresentCorrectValue() {
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("example")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sample")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sampleObject")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("visualPanelElement-0")));
    //## Step 1 - Change the option parameter to AVG and reload sample
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sample")));
    assertTrue(driver.findElement(By.id("sample")).isDisplayed());
    //Click in 'Code'
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='example']/ul/li[2]/a")));
    driver.findElement(By.xpath("//div[@id='example']/ul/li[2]/a")).click();
    assertFalse(driver.findElement(By.id("sample")).isDisplayed());
    //Click in 'Try me'
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("example")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("code")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='code']/button")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("samplecode")));

    ((JavascriptExecutor) driver).executeScript("$('#samplecode').text($('#samplecode').text().replace('MIN', 'AVG'));");

    driver.findElement(By.xpath("//button")).click();

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sample")));
    assertTrue(driver.findElement(By.id("sample")).isDisplayed());


    //## Step 2 - Check the presented value for AVG.
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("example")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sample")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sampleObject")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("visualPanelElement-0")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='visualPanelElement-0']/span"));
    assertEquals("4787.772727272727", driver.findElement(By.xpath("//div[@id='visualPanelElement-0']/span")).getText());
  }

  @AfterClass
  public static void tearDown() { }
}
