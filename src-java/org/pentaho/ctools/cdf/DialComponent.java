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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Testing the functionalities related with Dial Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DialComponent {
  //Instance of the driver (browser emulator)
  private static WebDriver       driver;
  // Instance to be used on wait commands
  private static Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private static String          baseUrl;

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
   * Go to the CheckComponent web page.
   */
  public static void init() {
    // The URL for the CheckComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Documentation/Component
    // Reference/Core Components/CheckComponent
    driver.get(baseUrl+ "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A25-DialComponent%3Adial_component.xcdf/generatedContent?ts=1414172476249");

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
    assertEquals("DialComponent",ElementHelper.GetText(driver, By.xpath("//div[@id='dashboardContent']/div/div/div/h2/span[2]")));
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
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name: 
   *    Check options
   * Description: 
   *    Here we pretend to check each option one by one and validate if an alert
   *    is raised with correct message. 
   * Steps: 
   *    1. Check in Southern and validate alert
   *    2. Check in Eastern and validate alert
   *    3. Check in Central and validate alert
   *    4. Check in Western and validate alert
   */
  @Test
  public void tc3_CheckEachOption_AfterCheckAnAlertIsDisplayed() {
    WebElement dialElement = ElementHelper.FindElement(driver, By.cssSelector("img"));
    assertNotNull(dialElement);
    
    String attrSrc = dialElement.getAttribute("src");
    System.out.println(attrSrc);
    assertTrue(attrSrc.startsWith(baseUrl + "getImage?image=tmp_chart_admin-"));
  }
  
  @AfterClass
  public static void tearDown() {
  }
}
