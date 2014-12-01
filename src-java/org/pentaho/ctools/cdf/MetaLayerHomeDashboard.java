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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MetaLayerHomeDashboard {
  //Instance of the driver (browser emulator)
  private static WebDriver       driver;
  // Instance to be used on wait commands
  private static Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private static String          baseUrl;
  //Log instance
  private static Logger          log                = LogManager.getLogger(MetaLayerHomeDashboard.class);

  @Rule
  public ScreenshotTestRule      screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + MetaLayerHomeDashboard.class.getSimpleName());
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
  @Test(timeout = 60000)
  public void tc1_LinkDetails_PopupJPivot() {
    log.info("tc1_LinkDetails_PopupJPivot");
    /*
     * ## Step 1
     */
    driver.get(baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A20-samples%3Ahome_dashboard_2%3Ahome_dashboard_metalyer.xcdf/generatedContent");

    //Not we have to wait for loading disappear
    ElementHelper.IsElementInvisible(driver, By.xpath("//div[@class='blockUI blockOverlay']"));

    //Wait for title become visible and with value 'Community Dashboard Framework'
    wait.until(ExpectedConditions.titleContains("Community Dashboard Framework"));
    //Wait for visibility of 'Top Ten Customers'
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='titleObject']")));
    // Validate the sample that we are testing is the one
    assertEquals("Community Dashboard Framework", driver.getTitle());
    assertEquals("Top Ten Customers", ElementHelper.GetText(driver, By.xpath("//div[@id='titleObject']")));

    /*
     * ## Step 2
     */
    //Wait for visibility of 'topTenCustomersDetailsObject' the text 'Details'
    WebElement linkDetails = ElementHelper.FindElement(driver, By.linkText("Details..."));
    assertEquals("Details...", linkDetails.getText());
    //click on the 'Details...'
    linkDetails.click();

    /*
     * ## Step 3
     */
    //Wait for the frame
    ElementHelper.IsElementVisible(driver, By.cssSelector("div.fancybox-inner"));
    ElementHelper.IsElementVisible(driver, By.xpath("//iframe"));
    WebElement frame = ElementHelper.FindElement(driver, By.xpath("//iframe"));
    String valueFrameAttrId = frame.getAttribute("id");
    String valueFrameAttrSrc = frame.getAttribute("src");

    ///pentaho/plugin/jpivot/Pivot?solution=system&path=%2Fpublic%2Fplugin-samples%2Fpentaho-cdf%2Factions&action=jpivot.xaction&width=500&height=600
    //Check if we have the sizes 500 and 600
    assertTrue(StringUtils.containsIgnoreCase(valueFrameAttrSrc, "action=jpivot.xaction&width=500&height=600"));

    //Wait for the element be visible.
    WebDriver driverFrame = driver.switchTo().frame(valueFrameAttrId);

    //BEGING: Testing debug
    //Printing the html of the element to check what problem is being occurred when tyring to find 'internal_content'
    log.info("MetaLayerHomeDashboard html");
    log.info("TODO:" + ((JavascriptExecutor) driver).executeScript("return document.body.innerHTML;").toString());
    //ENDING: Testing debug

    assertNotNull(ElementHelper.FindElement(driverFrame, By.xpath("//div[@id='internal_content']")));
    assertEquals("Measures", ElementHelper.GetText(driverFrame, By.xpath("//div[@id='internal_content']/table/tbody/tr[2]/td[2]/p/table/tbody/tr/th[2]")));
    assertEquals("Australian Collectors, Co.", ElementHelper.GetText(driverFrame, By.xpath("//div[@id='internal_content']/table[1]/tbody/tr[2]/td[2]/p[1]/table/tbody/tr[5]/th/div")));
    assertEquals("180,125", ElementHelper.GetText(driverFrame, By.xpath("//div[@id='internal_content']/table[1]/tbody/tr[2]/td[2]/p[1]/table/tbody/tr[7]/td")));

    //Close pop-up
    driver.switchTo().defaultContent();
    ElementHelper.FindElement(driver, By.xpath("/html/body/div[3]/div/div/a")).click();
    ElementHelper.IsElementInvisible(driver, By.cssSelector("div.fancybox-inner"));
  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + MetaLayerHomeDashboard.class.getSimpleName());
  }
}
