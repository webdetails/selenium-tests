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
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * Testing the functionalities related with component Date Range Input.
 *
 * Naming convention for test: 'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DateRangeInputComponent {
  // Instance of the driver (browser emulator)
  private static WebDriver       driver;
  // Instance to be used on wait commands
  private static Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private static String          baseUrl;

  @Rule
  public ScreenshotTestRule      screenshotTestRule = new ScreenshotTestRule(driver);

  /**
   * Shall initialized the test before run each test case.
   */
  @BeforeClass
  public static void setUp() {
    driver = CToolsTestSuite.getDriver();
    wait = CToolsTestSuite.getWait();
    baseUrl = CToolsTestSuite.getBaseUrl();

    // Go to sample
    init();
  }

  /**
   * Go to the DataRangeInput web page.
   */
  public static void init() {
    // The URL for the VisualizationAPIComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Documentation/Component
    // Reference/Core Components/DataRangeInputComponent
    driver.get(baseUrl + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:43-DateRangeInputComponent:date_range_component.xcdf/generatedContent");

    // Not we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name: 
   * 		Validate Page Contents 
   * Description: 
   * 		Here we want to validate the page contents. 
   * Steps: 
   * 		1. Check the widget's title.
   */
  @Test(timeout = 60000)
  public void tc1_PageContent_DisplayTitle() {
    // Wait for title become visible and with value 'Community Dashboard Framework'
    wait.until(ExpectedConditions.titleContains("Community Dashboard Framework"));
    // Wait for visibility of 'VisualizationAPIComponent'
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dashboardContent']/div/div/div/h2/span[2]")));

    // Validate the sample that we are testing is the one
    assertEquals("Community Dashboard Framework", driver.getTitle());
    assertEquals("DateRangeInputComponent", ElementHelper.GetText(driver, By.xpath("//div[@id='dashboardContent']/div/div/div/h2/span[2]")));
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name: 
   * 		Reload Sample 
   * Description: 
   * 		Reload the sample (not refresh page). 
   * Steps: 
   * 		1. Click in Code and then click in button 'Try me'.
   */
  @Test(timeout = 60000)
  public void tc2_ReloadSample_SampleReadyToUse() {
    // ## Step 1
    // Render again the sample
    ElementHelper.FindElement(driver, By.xpath("//div[@id='example']/ul/li[2]/a")).click();
    ElementHelper.FindElement(driver, By.xpath("//div[@id='code']/button")).click();

    // Not we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility(driver, By.xpath("//div[@class='blockUI blockOverlay']"));

    // Now sample element must be displayed
    assertTrue(ElementHelper.FindElement(driver, By.id("sample")).isDisplayed());
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name: 
   * 		Set Date Range Today 
   * Description: 
   * 		When click on Today option an alert is displayed with today date in 
   *    range interval.
   * Steps: 
   * 		1. Click in Today option
   *    2. Check for Alert
   */
  @Test(timeout = 60000)
  public void tc3_Today_DateIsSetSuccessful() {
    //## Step 1
    WebElement elemInput = ElementHelper.FindElement(driver, By.id("myInput"));
    new Actions(driver).click(elemInput).build().perform();
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[contains(text(),'Today')])[2]")));
    ElementHelper.FindElement(driver, By.linkText("Today")).click();

    //## Step 2
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date dNow = new Date();
    String strToday = sdf.format(dNow);

    wait.until(ExpectedConditions.alertIsPresent());
    Alert alert = driver.switchTo().alert();
    String confirmationMsg = alert.getText();
    String expectedCnfText = "You chose from " + strToday + " to " + strToday;
    alert.accept();

    assertEquals(confirmationMsg, expectedCnfText);
  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name:
   *    Set Date Range for the Last Seven Days.
   * Description:
   *    When click on Last 7 Days option an alert is displayed with 7 days
   *    before today and the today date.
   * Steps:
   *    1. Click in Last 7 Days option
   *    2. Check for Alert
   */
  public void tc4_LastSevenDays_DateIsSetSuccessful() {
    this.tc2_ReloadSample_SampleReadyToUse();
    //## Step 1
    WebElement elemInput = ElementHelper.FindElement(driver, By.id("myInput"));
    new Actions(driver).click(elemInput).build().perform();
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[contains(text(),'Last 7 days')])[3]")));
    //ElementHelper.FindElement(driver, By.xpath("(//a[contains(text(),'Today')])[2]")).click();
    ElementHelper.FindElement(driver, By.linkText("Last 7 days")).click();
    //The next operation is a TRICK, in order to raise an alert
    //elemInput.click();

    //## Step 2
    Calendar c = Calendar.getInstance();
    c.add(Calendar.DAY_OF_MONTH, -7);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date dNow = new Date();
    String strToday = sdf.format(dNow);

    wait.until(ExpectedConditions.alertIsPresent());
    Alert alert = driver.switchTo().alert();
    String confirmationMsg = alert.getText();
    String expectedCnfText = "You chose from " + sdf.format(c.getTime()) + " to " + strToday;
    alert.accept();

    assertEquals(confirmationMsg, expectedCnfText);
  }

  /**
   * ############################### Test Case 5 ###############################
   *
   * Test Case Name:
   *    Set Date Range Month to Today date
   * Description:
   *    When click on Month to date option an alert is displayed with begin
   *    month day to today date.
   * Steps:
   *    1. Click in Month to date option
   *    2. Check for Alert
   */
  public void tc5_MonthToDate_DateIsSetSuccessful() {
    this.tc2_ReloadSample_SampleReadyToUse();
    //## Step 1
    WebElement elemInput = ElementHelper.FindElement(driver, By.id("myInput"));
    elemInput.click();
    ElementHelper.FindElement(driver, By.xpath("(//a[contains(text(),'Month to date')])[4]")).sendKeys(Keys.ENTER);
    //The next operation is a TRICK, in order to raise an alert
    elemInput.click();

    //## Step 2
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy-MM");
    Date dNow = new Date();
    String strToday = sdf.format(dNow);
    String strCurrentMonth = sdfMonth.format(dNow) + "-01";

    wait.until(ExpectedConditions.alertIsPresent());
    Alert alert = driver.switchTo().alert();
    String confirmationMsg = alert.getText();
    String expectedCnfText = "You chose from " + strCurrentMonth + " to " + strToday;
    alert.accept();

    assertEquals(confirmationMsg, expectedCnfText);
  }

  /**
   * ############################### Test Case 6 ###############################
   *
   * Test Case Name:
   *    Set Date Range Year To Date
   * Description:
   *    When click on Year To Date option an alert is displayed with begin year
   *    date to today date in range interval.
   * Steps:
   *    1. Click in Year to date option
   *    2. Check for Alert
   */
  public void tc6_YearToDate_DateIsSetSuccessful() {
    this.tc2_ReloadSample_SampleReadyToUse();
    //## Step 1
    WebElement elemInput = ElementHelper.FindElement(driver, By.id("myInput"));
    elemInput.click();
    ElementHelper.FindElement(driver, By.xpath("(//a[contains(text(),'Year to date')])[5]")).sendKeys(Keys.ENTER);
    //The next operation is a TRICK, in order to raise an alert
    elemInput.click();

    //## Step 2
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
    Date dNow = new Date();
    String strToday = sdf.format(dNow);
    String strBeginYear = sdfYear.format(dNow) + "-01-01";

    wait.until(ExpectedConditions.alertIsPresent());
    Alert alert = driver.switchTo().alert();
    String confirmationMsg = alert.getText();
    String expectedCnfText = "You chose from " + strBeginYear + " to " + strToday;
    alert.accept();

    assertEquals(confirmationMsg, expectedCnfText);
  }

  /**
   * ############################### Test Case 7 ###############################
   *
   * Test Case Name:
   *    Set Date Range Today
   * Description:
   *    When click on The Previous Month option an alert is displayed with the
   *    start day of previous month to last day of previous month in range
   *    interval.
   * Steps:
   *    1. Click in The Previous Month option
   *    2. Check for Alert
   */
  public void tc7_ThePreviousMonth_DateIsSetSuccessful() {
    this.tc2_ReloadSample_SampleReadyToUse();
    //## Step 1
    WebElement elemInput = ElementHelper.FindElement(driver, By.id("myInput"));
    elemInput.click();
    ElementHelper.FindElement(driver, By.xpath("(//a[contains(text(),'The previous Month')])[6]")).sendKeys(Keys.ENTER);
    //The next operation is a TRICK, in order to raise an alert
    elemInput.click();

    //## Step 2
    Calendar c = Calendar.getInstance();
    c.add(Calendar.MONTH, -1);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

    String strLastMonthEndDay = sdf.format(c.getTime());
    String strLastMonthStartDay = sdf.format(c.getTime()) + "-01";
    c.add(Calendar.MONTH, 1);
    c.add(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) * -1);
    strLastMonthEndDay += "-" + c.get(Calendar.DAY_OF_MONTH);

    wait.until(ExpectedConditions.alertIsPresent());
    Alert alert = driver.switchTo().alert();
    String confirmationMsg = alert.getText();
    String expectedCnfText = "You chose from " + strLastMonthStartDay + " to " + strLastMonthEndDay;
    alert.accept();

    assertEquals(confirmationMsg, expectedCnfText);
  }

  /**
   * ############################### Test Case 8 ###############################
   *
   * Test Case Name:
   *    Set Date Range Today
   * Description:
   *    When click on Today option an alert is displayed with today date in
   *    range interval.
   * Steps:
   *    1. Click in Today option and then Cancel
   *    2. Click in Today option and then Done
   *    3. Check for Alert
   */
  public void tc8_CancelAllDatesAfterDone_DateIsCancelAndThenSetSuccessful() {
    this.tc2_ReloadSample_SampleReadyToUse();
    //## Step 1
    ElementHelper.FindElement(driver, By.id("myInput")).click();
    ElementHelper.FindElement(driver, By.xpath("(//a[contains(text(),'All Dates Before')])[7]")).sendKeys(Keys.ENTER);
    //The next operation is a TRICK, in order to raise an alert
    //ElementHelper.FindElement(driver, By.id("myInput")).click();

    /*System.out.println("Size: " + driver.findElements(By.xpath("(//button[contains(text(),'Cancel')])[7]")).size());//27
    System.out.println("Size: " + driver.findElements(By.xpath("(//button[contains(text(),'Cancel')][2])[7]")).size());//6
    System.out.println("Size: " + driver.findElements(By.xpath("(//button[contains(text(),'Cancel')][3])[7]")).size());//5
    System.out.println("Size: " + driver.findElements(By.xpath("(//button[contains(text(),'Cancel')][4])[7]")).size());//4
    System.out.println("Size: " + driver.findElements(By.xpath("(//button[contains(text(),'Cancel')][5])[7]")).size());//3
    System.out.println("Size: " + driver.findElements(By.xpath("(//button[contains(text(),'Cancel')][6])[7]")).size());//2
     */
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[contains(text(),'Cancel')])[7]")));
    driver.findElement(By.xpath("(//button[contains(text(),'Cancel')])[7]")).click();
    //WebElement elemButtonCancel = ElementHelper.FindElement(driver, By.xpath("(//button[starts-with(@class,'btnCancel')])[7]"));
    //assertNotNull(elemButtonCancel);
    //elemButtonCancel.click();

    //## Step 2
    //Click in day 29
    ElementHelper.FindElement(driver, By.id("myInput")).click();
    ElementHelper.FindElement(driver, By.xpath("(//a[contains(text(),'All Dates Before')])[8]")).sendKeys(Keys.ENTER);
    ElementHelper.FindElement(driver, By.xpath("(//a[contains(text(),'29')])[2]")).sendKeys(Keys.ENTER);

    wait.until(ExpectedConditions.alertIsPresent());
    Alert alert = driver.switchTo().alert();
    String confirmationMsg = alert.getText();
    alert.accept();

    assertEquals(confirmationMsg, "");
  }

  /**
   * ############################### Test Case 9 ###############################
   *
   * Test Case Name:
   *    Set Date Range Today
   * Description:
   *    When click on Today option an alert is displayed with today date in
   *    range interval.
   * Steps:
   *    1. Click in Today option
   *    2. Check for Alert
   */
  public void tc9_CancelDateRangeDone_DateIsCancelAndThenSetSuccessful() {}

  @AfterClass
  public static void tearDown() {}
}
