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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
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
public class CCCV2ShowCase {
  // Instance of the driver (browser emulator)
  private static WebDriver       driver;
  //Instance to be used on wait commands
  private static Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private static String          baseUrl;
  //Log instance
  private static Logger          log                = LogManager.getLogger(CCCV2ShowCase.class);

  @Rule
  public ScreenshotTestRule      screenshotTestRule = new ScreenshotTestRule(driver);

  @BeforeClass
  public static void setUpClass() {
    log.info("setUp##" + CCCV2ShowCase.class.getSimpleName());
    driver = CToolsTestSuite.getDriver();
    wait = CToolsTestSuite.getWait();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  @Before
  public void setUpTestCase() {
    //Go to AddinReference
    driver.get(baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3AtestCCCv2-II.wcdf/generatedContent");

    //NOTE - we have to wait for loading disappear
    ElementHelper.WaitForElementPresenceAndInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    ChartContent
   * Description:
   *    The test case pretends validate the contents presented in the sample.
   * Steps:
   *    1. Check chart Bar Chart
   *    2. Check chart Line Chart
   *    3. Check chart Pie Chart
   */
  @Test(timeout = 60000)
  public void tc01_ChartContent_DisplayedCorrect() {
    log.info("tc01_ChartContent_DisplayedCorrect");

    /*
     * ## Step 0
     */
    //Check page title
    wait.until(ExpectedConditions.titleIs("Community Dashboard Editor"));
    assertEquals("Community Dashboard Editor", driver.getTitle());
    //Check title
    String title = ElementHelper.GetText(driver, By.xpath("//div[@id='Headers']/div"));
    assertEquals("CCC v2 - Show Case", title);
    //Wait for the charts load
    ElementHelper.IsElementVisible(driver, By.xpath("//*[@width='266']"));

    /*
     * ## Step 1 - Bar Chart
     */
    String barChartTitle = ElementHelper.GetText(driver, By.id("BarChartTitleRow"));
    assertEquals("Bar Chart", barChartTitle);
    //Check bars
    WebElement barChartRect1 = ElementHelper.FindElement(driver, By.xpath("//div[@id='BarChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='rect'][1]"));
    WebElement barChartRect2 = ElementHelper.FindElement(driver, By.xpath("//div[@id='BarChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='rect'][2]"));
    WebElement barChartRect3 = ElementHelper.FindElement(driver, By.xpath("//div[@id='BarChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='rect'][3]"));
    assertNotNull(barChartRect1);
    assertNotNull(barChartRect2);
    assertNotNull(barChartRect3);
    assertEquals("196.11771571469967", barChartRect1.getAttribute("width"));
    assertEquals("266", barChartRect2.getAttribute("width"));
    assertEquals("105.63893814477713", barChartRect3.getAttribute("width"));
    //Check bars value
    String barChartRectValue1 = ElementHelper.GetText(driver, By.xpath("//div[@id='BarChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text'][1]"));
    String barChartRectValue2 = ElementHelper.GetText(driver, By.xpath("//div[@id='BarChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text'][2]"));
    String barChartRectValue3 = ElementHelper.GetText(driver, By.xpath("//div[@id='BarChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text'][3]"));
    assertEquals("3.68M", barChartRectValue1);
    assertEquals("4.99M", barChartRectValue2);
    assertEquals("1.98M", barChartRectValue3);
    //Mouse hover elements
    Actions acts = new Actions(driver);
    acts.moveToElement(barChartRect2);
    acts.perform();
    String seriesLabel = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[1]/td[1]/span"));
    acts.perform();
    String seriesValue = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[1]/td[3]/span"));
    acts.perform();
    String timeLabel = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[2]/td[1]/span"));
    acts.perform();
    String timeValue = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[2]/td[3]/span"));
    acts.perform();
    String valueLabel = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[3]/td[1]/span"));
    acts.perform();
    String valueValue = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[3]/td[3]/span"));
    assertEquals("Series", seriesLabel);
    assertEquals("Sales", seriesValue);
    assertEquals("Time", timeLabel);
    assertEquals("2004", timeValue);
    assertEquals("Value", valueLabel);
    assertEquals("4,987,739.84", valueValue);
  }

  @Test(timeout = 90000)
  public void tc02_ChartContent_DisplayedCorrect() {
    log.info("tc02_ChartContent_DisplayedCorrect");

    /*
     * ## Step 0
     */
    //Check page title
    wait.until(ExpectedConditions.titleIs("Community Dashboard Editor"));
    assertEquals("Community Dashboard Editor", driver.getTitle());
    //Check title
    String title = ElementHelper.GetText(driver, By.xpath("//div[@id='Headers']/div"));
    assertEquals("CCC v2 - Show Case", title);
    //Wait for the charts load
    ElementHelper.IsElementVisible(driver, By.xpath("//*[@width='266']"));

    /*
     * ## Step 2 - Line Chart
     */
    String lineChartTitle = ElementHelper.GetText(driver, By.id("LineChartTitleRow"));
    assertEquals("Line Chart", lineChartTitle);
    //Check lines
    WebElement lineChartCircle1 = ElementHelper.FindElement(driver, By.xpath("//div[@id='LineChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='circle'][1]"));
    WebElement lineChartCircle2 = ElementHelper.FindElement(driver, By.xpath("//div[@id='LineChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='circle'][7]"));
    WebElement lineChartCircle3 = ElementHelper.FindElement(driver, By.xpath("//div[@id='LineChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='circle'][11]"));
    assertEquals("120.414021784", lineChartCircle1.getAttribute("cy"));
    assertEquals("136.53248991200002", lineChartCircle2.getAttribute("cy"));
    assertEquals("24.50347088000001", lineChartCircle3.getAttribute("cy"));
    //Mouse hover elements
    WebElement lineChartCircle4 = ElementHelper.FindElement(driver, By.xpath("//div[@id='LineChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='circle'][2]"));
    Actions acts2 = new Actions(driver);
    acts2.moveToElement(lineChartCircle4);
    acts2.perform();
    String measuresLabel = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[1]/td[1]/span"));
    acts2.perform();
    String measuresValue = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[1]/td[3]/span"));
    acts2.perform();
    String categoryLabel = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[2]/td[1]/span"));
    acts2.perform();
    String categoryValue = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[2]/td[3]/span"));
    acts2.perform();
    String valueLineValue = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[4]/td[3]/span"));
    assertEquals("Measures", measuresLabel);
    assertEquals("Quantity", measuresValue);
    assertEquals("Category", categoryLabel);
    assertEquals("Feb", categoryValue);
    assertEquals("7,959", valueLineValue);
  }

  @Test(timeout = 90000)
  public void tc03_ChartContent_DisplayedCorrect() {
    log.info("tc03_ChartContent_DisplayedCorrect");

    /*
     * ## Step 0
     */
    //Check page title
    wait.until(ExpectedConditions.titleIs("Community Dashboard Editor"));
    assertEquals("Community Dashboard Editor", driver.getTitle());
    //Check title
    String title = ElementHelper.GetText(driver, By.xpath("//div[@id='Headers']/div"));
    assertEquals("CCC v2 - Show Case", title);
    //Wait for the charts load
    ElementHelper.IsElementVisible(driver, By.xpath("//*[@width='266']"));


    /*
     * ## Step 3 - Pie Chart
     */
    String pieChartTitle = ElementHelper.GetText(driver, By.id("PieChartTitleRow"));
    assertEquals("Pie Chart", pieChartTitle);
    //Check pies
    WebElement pieChartCircle1 = ElementHelper.FindElement(driver, By.xpath("//div[@id='PieChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='path'][1]"));
    WebElement pieChartCircle2 = ElementHelper.FindElement(driver, By.xpath("//div[@id='PieChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='path'][2]"));
    WebElement pieChartCircle3 = ElementHelper.FindElement(driver, By.xpath("//div[@id='PieChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='path'][3]"));
    assertNotNull(pieChartCircle1);
    assertNotNull(pieChartCircle2);
    assertNotNull(pieChartCircle3);
    //Interact with pie chart disabling two series
    //Series 2003
    WebElement serie2003 = ElementHelper.FindElement(driver, By.xpath("//div[@id='PieChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='text']"));
    serie2003.click();
    ElementHelper.WaitForElementPresenceAndInvisibility(driver, By.xpath("//div[@id='PieChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='path'][3]"));
    //Series 2004
    WebElement serie2004 = ElementHelper.FindElement(driver, By.xpath("//div[@id='PieChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='text']"));
    serie2004.click();
    ElementHelper.WaitForElementPresenceAndInvisibility(driver, By.xpath("//div[@id='PieChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='path'][2]"));
    //Check value of the serie2005
    String serie2005Value = ElementHelper.GetText(driver, By.xpath("//div[@id='PieChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='text']"));
    assertEquals("1.98M", serie2005Value);
    //Check mouse hover
    /* Does not work on jenkins!!
    WebElement pieChartSerie2005 = ElementHelper.FindElement(driver, By.xpath("//div[@id='PieChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='text']"));
    Actions acts3 = new Actions(driver);
    acts3.moveToElement(pieChartSerie2005);
    acts3.perform();
    String pieTimeLabel = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//tr[1]/td[1]/span"));
    acts3.perform();
    String pieTimeValue = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//tr[1]/td[3]/span"));
    acts3.perform();
    String pieSeriesLabel = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//tr[2]/td[1]/span"));
    acts3.perform();
    String pieSeriesValue = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//tr[2]/td[3]/span"));
    acts3.perform();
    String pieValueLabel = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//tr[3]/td[1]/span"));
    acts3.perform();
    String pieValueValue = ElementHelper.WaitForElementPresentGetText(driver, By.xpath("//tr[3]/td[3]/span"));
    assertEquals("Time", pieTimeLabel);
    assertEquals("2005", pieTimeValue);
    assertEquals("Series", pieSeriesLabel);
    assertEquals("Sales", pieSeriesValue);
    assertEquals("Value", pieValueLabel);
    assertEquals("1,980,825.34", pieValueValue);*/
  }

  @AfterClass
  public static void tearDownClass() {
    log.info("tearDown##" + CCCV2ShowCase.class.getSimpleName());
  }
}
