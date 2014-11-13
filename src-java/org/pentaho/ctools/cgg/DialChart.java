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
 * Testing the functionalities related with CGG - Dial Chart.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DialChart {
  // Instance of the driver (browser emulator)
  private WebDriver         driver;
  // The base url to be append the relative url in test
  private String            baseUrl;
  //Log instance
  private static Logger     log                = LogManager.getLogger(DialChart.class);

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
   *    Dial  Chart
   * Description:
   *    We pretend to check if a image is display with dial in a chart.
   * Steps:
   *    1. Open the dial chart.
   */
  @Test(timeout = 60000)
  public void tc1_DialChart_ImageRendered() {
    log.debug("tc1_DialChart_ImageRendered");
    /*
     * ## Step 1
     */
    this.driver.get(this.baseUrl + "plugin/cgg/api/services/draw?script=/public/dial.js&outputType=svg&paramvalue=35 ");

    //Check Pointer
    WebElement elemPointer = ElementHelper.FindElement(this.driver, By.xpath("//*[local-name()='g'][6]/*[local-name()='polygon'][@id='ponteiro' and @transform='rotate(62.99999999999999,300,275)']"));
    WebElement basePointers = ElementHelper.FindElement(this.driver, By.xpath("//*[local-name()='g'][6]/*[local-name()='path'][@id='base_ponteiro']"));
    assertNotNull(elemPointer);
    assertNotNull(basePointers);

    //Check Colors
    WebElement elemColor1 = ElementHelper.FindElement(this.driver, By.xpath("//*[local-name()='g'][6]/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='path'][@fill='rgb(255,0,0)']"));
    WebElement elemColor2 = ElementHelper.FindElement(this.driver, By.xpath("//*[local-name()='g'][6]/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='path'][2][@fill='rgb(255,255,0)']"));
    WebElement elemColor3 = ElementHelper.FindElement(this.driver, By.xpath("//*[local-name()='g'][6]/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='path'][3][@fill='rgb(0,128,0)']"));
    assertNotNull(elemColor1);
    assertNotNull(elemColor2);
    assertNotNull(elemColor3);
  }

  @After
  public void tearDown() {
    log.debug("tearDown");
  }
}
