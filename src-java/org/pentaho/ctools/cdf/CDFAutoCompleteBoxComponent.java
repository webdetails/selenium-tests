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

package org.pentaho.ctools.cdf;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CDFAutoCompleteBoxComponent {
  // Instance of the driver (browser emulator)
  private WebDriver driver;
  // Instance to be used on wait commands
  private Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private String baseUrl;

  @Before
  public void setUp() throws Exception {
    driver = CToolsTestSuite.getDriver();
    wait = CToolsTestSuite.getWait();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  @Test
  public void testCDFAutoCompleteBoxComponent() throws Exception {
    driver.get(baseUrl + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:58-AutocompleteBoxComponent:autocomplete_component.xcdf/generatedContent");

    //Wait for visibility of 'AutocompleteBoxComponent'
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dashboardContent']/div/div/div/h2/span[2]")));

    // Validate the sample that we are testing is the one
    assertEquals("Community Dashboard Framework", driver.getTitle());
    assertEquals("AutocompleteBoxComponent", driver.findElement(By.xpath("//div[@id='dashboardContent']/div/div/div/h2/span[2]")).getText());

    //Render again the sample
    driver.findElement(By.xpath("//div[@id='example']/ul/li[2]/a")).click();
    driver.findElement(By.cssSelector("#code > button")).click();

    //Key press 'a'
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sampleObjectautoboxInput")));
    driver.findElement(By.id("sampleObjectautoboxInput")).sendKeys("a");

    //Retrieve data by pressing key 'a'
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("listElement")));
    assertNotNull(driver.findElement(By.id("listElement")));

    assertTrue(driver.findElement(By.xpath("//ul[@class='autobox-list']/li/input")).isEnabled());
    assertTrue(driver.findElement(By.xpath("//ul[@class='autobox-list']/li[2]/input")).isEnabled());
    assertTrue(driver.findElement(By.xpath("//ul[@class='autobox-list']/li[3]/input")).isEnabled());
    assertTrue(driver.findElement(By.xpath("//ul[@class='autobox-list']/li[4]/input")).isEnabled());
    assertTrue(driver.findElement(By.xpath("//ul[@class='autobox-list']/li[5]/input")).isEnabled());
    assertTrue(driver.findElement(By.xpath("//ul[@class='autobox-list']/li[6]/input")).isEnabled());
    assertTrue(driver.findElement(By.xpath("//ul[@class='autobox-list']/li[7]/input")).isEnabled());
    assertTrue(driver.findElement(By.xpath("//ul[@class='autobox-list']/li[8]/input")).isEnabled());
    assertTrue(driver.findElement(By.xpath("//ul[@class='autobox-list']/li[9]/input")).isEnabled());
    assertTrue(driver.findElement(By.xpath("//ul[@class='autobox-list']/li[10]/input")).isEnabled());
    assertTrue(driver.findElement(By.xpath("//ul[@class='autobox-list']/li[11]/input")).isEnabled());
  }

  @After
  public void tearDown() { }
}
