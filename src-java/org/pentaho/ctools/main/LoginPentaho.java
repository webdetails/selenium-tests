/*!
  * This program is free software;you can redistribute it and/or modify it under the
  * terms of the GNU Lesser General Public License,version2.1as published by the Free Software Foundation.
  *
  * You should have received a copy of the GNU Lesser General Public License along with this
  * program;if not,you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
  * or from the Free Software Foundation,Inc., 51 Franklin Street,Fifth Floor,Boston,MA 02110-1301 USA.
  *
  * This program is distributed in the hope that it will be useful,but WITHOUT ANY WARRANTY;
  * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
  * See the GNU Lesser General Public License for more details.
  *
  * Copyright(c)2002-2014 Pentaho Corporation..All rights reserved.
  */

package org.pentaho.ctools.main;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

  @Test
  public void testLoginPentaho() throws Exception {
    driver.get(baseUrl + "Login");

    //Wait for form display
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='login-form-container']/div/h1")));

    assertEquals("User Console", driver.findElement(By.xpath("//div[@id='login-form-container']/div/h1")).getText());
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("admin");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("password");
    driver.findElement(By.cssSelector("button.btn")).click();

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[@id='home.perspective']")));
    assertNotNull(driver.findElement(By.xpath("//iframe[@id='home.perspective']")));
    assertEquals("Pentaho User Console", driver.getTitle());


    WebElement homeperspective = driver.findElement(By.id("home.perspective"));
    driver.switchTo().frame("home.perspective");

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonWrapper")));
    driver.findElement(By.xpath("//div[@id='buttonWrapper']/div/div/button")).click();


    driver.switchTo().defaultContent();

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("applicationShell")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[@id='browser.perspective']")));
    driver.switchTo().frame("browser.perspective");
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='fileBrowser']")));
    assertNotNull(driver.findElement(By.xpath("//div[@id='fileBrowser']")));
  }

  @After
  public void tearDown(){}
}
