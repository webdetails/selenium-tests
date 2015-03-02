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
import static org.junit.Assert.assertNotNull;

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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * Testing the functionalities related with CCV V2 Show Case.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WidgetReference {
  // Instance of the driver (browser emulator)
  private static WebDriver  driver;
  // The base url to be append the relative url in test
  private static String     baseUrl;
  //Log instance
  private static Logger     log                = LogManager.getLogger(WidgetReference.class);

  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + WidgetReference.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  @Before
  public void setUpTestCase() {
    //Go to AddinReference
    driver.get(baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3Awidgets.wcdf/generatedContent");

    //NOTE - we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    PageContent
   * Description:
   *    The test case pretends validate page contents of this sample.
   * Steps:
   *    1. Check page content
   */
  @Test(timeout = 30000)
  public void tc01_PageContent_DisplayContent() {
    log.info("tc01_PageContent_DisplayContent");

    /*
     * ## Step 1
     */
    String sampleTitle = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='Title']/span"));
    String firstParag = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='DescriptionBody']/p"));
    String sampleSubTitle1 = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='DescriptionBody']/div"));
    String sampleSubTitle2 = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='DescriptionBody']/div[2]"));
    String lastParag = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='DescriptionBody']/p[7]"));

    assertEquals("Widget reference", sampleTitle);
    assertEquals("Widgets are a way to turn complex component and layout setups into reusable components. Widgets are developed as if they were dashboards and used as if they were components in client dashboards.", firstParag);
    assertEquals("Using Widgets", sampleSubTitle1);
    assertEquals("Widget Implementation", sampleSubTitle2);
    assertEquals("Since you cannot know what names will be given to those objects when developing the widget, CDE now provides a reference mechanism for those cases. Anywhere you'd use a parameter parameterName, you should instead use the form ${p:parameterName}. Similarly you should reference components as ${c:componentName}, and htmlObjects as ${h:htmlObject}. Be mindful that this mechanism also obviates the need to use render_componentName everywhere you want to reference a component. The render_ prefix is an implementation detail, and shouldn't be relied upon. Since all dashboards (not just widgets) can use the ${p/c/h:} references, it's a best practice to just use that syntax in all contexts.", lastParag);
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Widget Chart
   * Description:
   *    The test case pretends validate the information presented in the chart
   *    when we interact with.
   * Steps:
   *    1. Check contents
   *    2. Check years
   *    3. Disable series
   *    4. Check tooltip
   */
  @Test(timeout = 90000)
  public void tc02_WidgetChart_ContentsDisplayCorrect() {
    log.info("tc02_WidgetChart_ContentsDisplayCorrect");

    /*
     * ## Step 1
     */
    String widgetTitle = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='SampleTitle']"));
    String widgetDesc = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@id='SampleDescHtml']/p"));
    assertEquals("Sample Widget", widgetTitle);
    assertEquals("This is a simple sample Widget. The widget contains only a single bar chart and a year parameter, and the parameter is linked to the documentation dashboard's year parameter, which is exposed in the pulldown.", widgetDesc);

    /*
     * ## Step 2
     */
    Select select = new Select(ElementHelper.FindElement(driver, By.xpath("//select")));
    //>Select 2003
    select.selectByValue("2003");
    //wait for loading bar disappear
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
    WebElement rect = ElementHelper.FindElement(driver, By.xpath("//div[@id='widgetSample_chartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]//*[local-name()='rect'][4]"));
    log.info(rect.getAttribute("height"));
    assertEquals("185.54946181445413", rect.getAttribute("height"));//185.54946181445413
    //>Select 2004
    select.selectByValue("2004");
    //wait for loading bar disappear
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
    rect = ElementHelper.FindElement(driver, By.xpath("//div[@id='widgetSample_chartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]//*[local-name()='rect'][4]"));
    log.info(rect.getAttribute("height"));
    assertEquals("122.4067513368984", rect.getAttribute("height"));//122.4067513368984
    //>Select 2005
    select.selectByValue("2005");
    //wait for loading bar disappear
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
    rect = ElementHelper.FindElement(driver, By.xpath("//div[@id='widgetSample_chartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]//*[local-name()='rect'][4]"));
    log.info(rect.getAttribute("height"));
    assertEquals("133.12012705746463", rect.getAttribute("height"));//133.12012705746463

    /*
     * ## Step 3
     */
    //Click in Classic Cars
    ElementHelper.ClickJS(driver, By.xpath("//div[@id='widgetSample_chartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='text']"));
    //Click in Motorcycles
    ElementHelper.ClickJS(driver, By.xpath("//div[@id='widgetSample_chartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='text']"));
    //Click Planes
    ElementHelper.ClickJS(driver, By.xpath("//div[@id='widgetSample_chartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='g'][3]/*[local-name()='g'][2]/*[local-name()='text']"));
    //Click in Ships
    ElementHelper.ClickJS(driver, By.xpath("//div[@id='widgetSample_chartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='g'][4]/*[local-name()='g'][2]/*[local-name()='text']"));
    //Click in Trains
    ElementHelper.ClickJS(driver, By.xpath("//div[@id='widgetSample_chartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='text']"));
    //Click in Vintage Cars
    ElementHelper.ClickJS(driver, By.xpath("//div[@id='widgetSample_chartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][3]/*[local-name()='g'][2]/*[local-name()='text']"));
    //Wait for the previous series disable
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@id='widgetSample_chartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]"));
    //Check chart
    WebElement rect1 = ElementHelper.FindElement(driver, By.xpath("//div[@id='widgetSample_chartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='rect'][1]"));
    WebElement rect2 = ElementHelper.FindElement(driver, By.xpath("//div[@id='widgetSample_chartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='rect'][2]"));
    WebElement rect3 = ElementHelper.FindElement(driver, By.xpath("//div[@id='widgetSample_chartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='rect'][3]"));
    assertNotNull(rect1);
    assertNotNull(rect2);
    assertNotNull(rect3);
    log.info(rect1.getAttribute("height"));
    log.info(rect2.getAttribute("height"));
    log.info(rect3.getAttribute("height"));
    assertEquals("127.83732057416269", rect1.getAttribute("height"));//127.83732057416269
    assertEquals("219", rect2.getAttribute("height"));//219
    assertEquals("156.39114832535884", rect3.getAttribute("height"));//156.39114832535884

    /*
     * ## Step 4
     */
    //Check tooltip
    Actions acts = new Actions(driver);
    acts.moveToElement(rect1);
    acts.perform();
    String tooltipValue = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@class='tipsy tipsy-s']/div[2]"));
    assertEquals("Trucks and Buses, APAC:  488", tooltipValue);

  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + WidgetReference.class.getSimpleName());
  }
}
