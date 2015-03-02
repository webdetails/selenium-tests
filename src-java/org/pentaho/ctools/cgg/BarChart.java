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
package org.pentaho.ctools.cgg;

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
public class BarChart{

  // Instance of the driver (browser emulator)
  private WebDriver         driver;
  // The base url to be append the relative url in test
  private String            baseUrl;
  //Log instance
  private static Logger     log                = LogManager.getLogger(BarChart.class);

  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(this.driver);

  @Before
  public void setUp() {
    log.debug("setUp");
    this.driver = CToolsTestSuite.getDriver();
    this.baseUrl = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Bar Chart
   * Description:
   *    We pretend to check if an image is displayed with bares in a chart.
   * Steps:
   *    1. Open the bar chart.
   */
  @Test(timeout = 60000)
  public void tc1_BarChart_ImageRendered() {
    log.debug("tc1_BarChart_ImageRendered");
    //## Step 1
    this.driver.get(this.baseUrl + "plugin/cgg/api/services/draw?script=/public/testBarChart.js&outputType=png");

    WebElement elementImage = ElementHelper.FindElement(this.driver, By.cssSelector("img"));
    assertNotNull(elementImage);
    String attrWidth = elementImage.getAttribute("width");
    String attrHeight = elementImage.getAttribute("height");
    String attrSrc = elementImage.getAttribute("src");

    assertEquals("852", attrWidth);
    assertEquals("637", attrHeight);
    assertEquals(this.baseUrl + "plugin/cgg/api/services/draw?script=/public/testBarChart.js&outputType=png", attrSrc);
  }

  @After
  public void tearDown() {
    log.debug("tearDown");
  }
}
