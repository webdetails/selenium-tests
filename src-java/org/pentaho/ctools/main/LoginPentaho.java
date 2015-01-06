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
package org.pentaho.ctools.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * Testing the functionalities related with Login.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginPentaho {
  // Instance of the driver (browser emulator)
  private WebDriver         driver;
  // Instance to be used on wait commands
  private Wait<WebDriver>   wait;
  // The base url to be append the relative url in test
  private String            baseUrl;
  //Log instance
  private static Logger     log                = LogManager.getLogger(LoginPentaho.class);

  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(this.driver);

  @Before
  public void setUp() {
    log.debug("setUp");
    this.driver = CToolsTestSuite.getDriver();
    this.wait = CToolsTestSuite.getWait();
    this.baseUrl = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Authentication
   * Description:
   *    With an administrator user, we check if user can authenticate in the
   *    system.
   * Steps:
   *    1. Go to Pentaho solution web page.
   *    2. Enter user and password.
   *    3. User authenticated, and user name of logged user is displayed.
   */
  @Test(timeout = 60000)
  public void tc1_Login_SuccessAuthentication() {
    log.debug("tc1_Login_SuccessAuthentication");
    //## Step 1
    this.driver.get(this.baseUrl + "Login");

    //Wait for form display
    this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='login-form-container']/div/h1")));
    assertEquals("User Console", this.driver.findElement(By.xpath("//div[@id='login-form-container']/div/h1")).getText());

    //## Step 2
    //Wait for all all elements in the form to be visible
    this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("j_username")));
    this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("j_password")));
    this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.btn")));
    this.driver.findElement(By.id("j_username")).clear();
    this.driver.findElement(By.id("j_username")).sendKeys("admin");
    this.driver.findElement(By.id("j_password")).clear();
    this.driver.findElement(By.id("j_password")).sendKeys("password");
    this.driver.findElement(By.cssSelector("button.btn")).click();

    //## Step 3
    //wait for visibility of waiting pop-up
    ElementHelper.WaitForElementInvisibility(this.driver, By.xpath("//div[@class='busy-indicator-container waitPopup']"));

    //Wait to load the new page
    this.wait.until(ExpectedConditions.titleContains("Pentaho User Console"));
    this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='pucUserDropDown']/table/tbody/tr/td/div")));
    this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[@id='home.perspective']")));
    assertNotNull(this.driver.findElement(By.xpath("//iframe[@id='home.perspective']")));
    assertEquals("Pentaho User Console", this.driver.getTitle());

    //Logged as ADMIN user
    assertEquals("admin", this.driver.findElement(By.xpath("//div[@id='pucUserDropDown']/table/tbody/tr/td/div")).getText());

    //Go to the Home Perspective [IFRAME]
    //driver.switchTo().frame("home.perspective");
    //wait.until(ExpectedConditions.elementToBeClickable(By.id("btnCreateNew")));
  }

  @After
  public void tearDown() {
    log.debug("tearDown");
  }
}
