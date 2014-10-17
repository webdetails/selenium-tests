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

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Testing the functionalities related with MetaLayerHome.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
/**
 * NOTE - The test was created regarding issue CDF-318
 */
public class MetaLayerHomeDashboard {
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

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    MetaLayer Home Dashboard - clicking details
   * Description:
   *    We pretend to validate when user click on 'Details...' a pop-up message 
   *    is displayed.
   * Steps:
   *    1. Open the MetaLayer Home Dashboard.
   *    2. Click in 'Details...'.
   *    3. Check if we have width = 500 and height = 600
   */
  @Test
  public void tc1_LinkDetails_PopupJPivot() throws Exception {
    //## Step 1
    driver.get(baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A20-samples%3Ahome_dashboard_2%3Ahome_dashboard_metalyer.xcdf/generatedContent");

    //Not we have to wait for loading disappear
    ElementHelper.IsElementInvisible(driver, wait, By.xpath("//div[@class='blockUI blockOverlay']"));
    
    //Wait for title become visible and with value 'Community Dashboard Framework'
  	wait.until(ExpectedConditions.titleContains("Community Dashboard Framework"));
    //Wait for visibility of 'Top Ten Customers'
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='titleObject']")));
    // Validate the sample that we are testing is the one
    assertEquals("Community Dashboard Framework", driver.getTitle());
    assertEquals("Top Ten Customers", ElementHelper.FindElement(driver, By.xpath("//div[@id='titleObject']")).getText());
    
    
    //## Step 2
    //Wait for visibility of 'topTenCustomersDetailsObject' the text 'Details'
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='topTenCustomersDetailsObject']")));
    WebElement linkDetails = ElementHelper.FindElement(driver, By.xpath("//div[@id='topTenCustomersDetailsObject']/a"));
    assertEquals("Details...", linkDetails.getText());
    //click on the 'Details...'
    linkDetails.click();

    
    //## Step 3
    //Wait for the frame
    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("fancybox-wrap")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[@id='fancybox-frame']")));
    WebElement frame = ElementHelper.FindElement(driver, By.xpath("//iframe[@id='fancybox-frame']"));
    String valueFrameAttrSrc = frame.getAttribute("src");

    ///pentaho/plugin/jpivot/Pivot?solution=system&path=%2Fpublic%2Fplugin-samples%2Fpentaho-cdf%2Factions&action=jpivot.xaction&width=500&height=600
    //Check if we have the sizes 500 and 600
    assertTrue(StringUtils.containsIgnoreCase(valueFrameAttrSrc, "action=jpivot.xaction&width=500&height=600"));

    //Wait for the element be visible.
    driver.switchTo().frame("fancybox-frame");
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='internal_content']")));
    assertNotNull(ElementHelper.FindElement(driver, By.xpath("//div[@id='internal_content']")));
    assertEquals("Measures", ElementHelper.FindElement(driver, By.xpath("//div[@id='internal_content']/table/tbody/tr[2]/td[2]/p/table/tbody/tr/th[2]")).getText());
    
    //Close pop-up
    driver.switchTo().defaultContent();
    ElementHelper.FindElement(driver, By.xpath("//a[@id='fancybox-close']")).sendKeys(Keys.ENTER);
    ElementHelper.IsElementInvisible(driver, wait, By.id("fancybox-wrap"));
  }

  @After
  public void tearDown() { }
}
