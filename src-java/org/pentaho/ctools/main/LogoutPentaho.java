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
import org.openqa.selenium.WebElement;
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
public class LogoutPentaho {
  // Instance of the driver (browser emulator)
  private WebDriver         driver;
  // Instance to be used on wait commands
  private Wait<WebDriver>   wait;
  // The base url to be append the relative url in test
  private String            baseUrl;
  //Log instance
  private static Logger     log                = LogManager.getLogger(LogoutPentaho.class);

  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @Before
  public void setUp() {
    log.debug("setUp");
    driver = CToolsTestSuite.getDriver();
    wait = CToolsTestSuite.getWait();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Log out
   * Description:
   *    With an administrator user, we check if user can log out from the system.
   * Steps:
   *    1. Go to Pentaho solution web page.
   *    2. Press Log Out.
   *    3. The user is logged out and is redirect to home page (login page).
   */
  @Test(timeout = 120000)
  public void tc1_Logout_SuccessLogOutReturnHomePage() {
    log.debug("tc1_Logout_SuccessLogOutReturnHomePage");
    //## Step 1
    driver.get(baseUrl + "Home");

    //waiting pop-up to be visible
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='busy-indicator-container waitPopup']"));

    //## Step 2
    //wait for frame to load
    wait.until(ExpectedConditions.titleContains("Pentaho User Console"));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='pucUserDropDown']/table/tbody/tr/td/div")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[@id='home.perspective']")));
    assertEquals("Pentaho User Console", driver.getTitle());
    assertNotNull(driver.findElement(By.xpath("//iframe[@id='home.perspective']")));

    //User drop down available
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='pucUserDropDown']/table/tbody/tr/td/div")));
    assertEquals("admin", driver.findElement(By.xpath("//div[@id='pucUserDropDown']/table/tbody/tr/td/div")).getText());
    driver.findElement(By.xpath("//div[@id='pucUserDropDown']/table/tbody/tr/td/div")).click();

    //Logout option available
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='customDropdownPopupMinor']/div/div/table/tbody/tr/td")));
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='customDropdownPopupMinor']/div/div/table/tbody/tr/td")));
    assertEquals("Log Out", driver.findElement(By.xpath("//div[@id='customDropdownPopupMinor']/div/div/table/tbody/tr/td")).getText());
    ElementHelper.Click(driver, By.xpath("//div[@id='customDropdownPopupMinor']/div/div/table/tbody/tr/td"));
    
    //## Step 3
    //Wait for form display (login form)
    WebElement elForm = ElementHelper.WaitForElementPresenceAndVisible(driver, By.xpath("//div[@id='login-form-container']/div/h1"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("j_username"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.id("j_password"));
    ElementHelper.WaitForElementPresenceAndVisible(driver, By.cssSelector("button.btn"));
    assertNotNull(elForm);
  }

  @After
  public void tearDown() {
    log.debug("tearDown");
  }
}
