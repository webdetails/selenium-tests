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
package org.pentaho.ctools.cde.reference;

import static org.junit.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
 * Testing the functionalities related with Ajax Request Reference.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AjaxRequestReference {
  // Instance of the driver (browser emulator)
  private static WebDriver       driver;
  //Instance to be used on wait commands
  private static Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private static String          baseUrl;
  //Log instance
  private static Logger          log                = LogManager.getLogger(AjaxRequestReference.class);

  @Rule
  public ScreenshotTestRule      screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + AjaxRequestReference.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    wait = CToolsTestSuite.getWait();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  @Before
  public void setUpTestCase() {
    //Go to AddinReference
    driver.get(baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3AajaxRequest.wcdf/generatedContent");

    //NOTE - we have to wait for loading disappear
    ElementHelper.WaitForElementPresenceAndInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    PageContent
   * Description:
   *    The test case pretends validate the contents presented in the sample.
   * Steps:
   *    1. Check page content.
   */
  @Test(timeout = 60000)
  public void tc01_PageContent_InformationPresent() {
    log.info("tc01_PageContent_InformationPresent");

    /*
     * ## Step 1
     */
    //Check page title
    wait.until(ExpectedConditions.titleIs("Community Dashboard Editor"));
    assertEquals("Community Dashboard Editor", driver.getTitle());
    //Check title
    String title = ElementHelper.GetText(driver, By.xpath("//div[@id='Title']/span"));
    assertEquals("Ajax Request Reference", title);
    //Check first paragh
    String expParag = "Ajax Request Component provides a way to build an Ajax request. Given an url, a response type and list of parameters it's possible to build an jQuery ajax call, which its result will be stored in the resultvar.";
    String actParag = ElementHelper.GetText(driver, By.xpath("//div[@id='DescriptionBody']/p"));
    assertEquals(expParag, actParag);
    //Check subtitle
    String subtitle = ElementHelper.GetText(driver, By.xpath("//div[@id='DescriptionBody']/div"));
    assertEquals("Component Parameters", subtitle);
    //Check parag 1
    String parag1 = ElementHelper.GetText(driver, By.xpath("//div[@id='DescriptionBody']/p[2]"));
    assertEquals("The Component definition supports the following arguments:", parag1);
    //Check subtitle2
    String subtitle2 = ElementHelper.GetText(driver, By.xpath("//div[@id='DescriptionBody']/div[2]"));
    assertEquals("Default values", subtitle2);
    //Check quote
    String quote = ElementHelper.GetText(driver, By.xpath("//div[@id='DescriptionBody']/blockquote/pre"));
    assertEquals("    defaults: {\n      ajaxRequestType: json,\n      asyncCall: true\n    }\n ", quote);
  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + AjaxRequestReference.class.getSimpleName());
  }
}
