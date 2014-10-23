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

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
  private WebDriver driver;
  // Instance to be used on wait commands
  private Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private String baseUrl;

  @Before
  public void setUp() {
    driver = CToolsTestSuite.getDriver();
    wait = CToolsTestSuite.getWait();
    baseUrl = CToolsTestSuite.getBaseUrl();
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
  @Test
  public void tc1_Login_SuccessAuthentication() throws Exception {
  	//## Step 1
  	driver.get(baseUrl + "Login");

    //Wait for form display
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='login-form-container']/div/h1")));
    assertEquals("User Console", driver.findElement(By.xpath("//div[@id='login-form-container']/div/h1")).getText());
    
    
    //## Step 2
    //Wait for all all elements in the form to be visible
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("j_username")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("j_password")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.btn")));
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("admin");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("password");
    driver.findElement(By.cssSelector("button.btn")).click();
    
    
    //## Step 3
    //wait for visibility of waiting pop-up
    //wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='busy-indicator-container waitPopup']")));
    //wait for invisibility of waiting pop-up
    ElementHelper.IsElementInvisible(driver, By.xpath("//div[@class='busy-indicator-container waitPopup']"));
        
    //Wait to load the new page
    wait.until(ExpectedConditions.titleContains("Pentaho User Console"));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='pucUserDropDown']/table/tbody/tr/td/div")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[@id='home.perspective']")));
    assertNotNull(driver.findElement(By.xpath("//iframe[@id='home.perspective']")));
    assertEquals("Pentaho User Console", driver.getTitle());
    
    //Logged as ADMIN user
    assertEquals("admin", driver.findElement(By.xpath("//div[@id='pucUserDropDown']/table/tbody/tr/td/div")).getText());
    
    //Go to the Home Perspective [IFRAME]
    //driver.switchTo().frame("home.perspective");
    //wait.until(ExpectedConditions.elementToBeClickable(By.id("btnCreateNew")));
  }

  @After
  public void tearDown(){}
}
