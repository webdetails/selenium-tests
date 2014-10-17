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

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;

import static org.junit.Assert.*;

/**
 * Testing the functionals related with Tables, paging, sort, display rows,
 * search in table contents.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 * Issues History:
 * - CDF-346: validate paging, because previous we only had 10 entries of data.
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TableComponent {
  // Instance of the driver (browser emulator)
  private static WebDriver driver;
  // Instance to be used on wait commands
  private static Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private static String baseUrl;

  /**
   * Shall inicialized the test before run each test case.
   */
  @BeforeClass
  public static void setUp(){
    driver = CToolsTestSuite.getDriver();
    wait = CToolsTestSuite.getWait();
    baseUrl = CToolsTestSuite.getBaseUrl();

    //Go to sample
    init();
  }

  /**
   * Go to the TableComponent web page.
   */
  public static void init(){
    //The URL for the TableComponent under CDF samples
    //This samples is in: Public/plugin-samples/CDF/Documentation/Component Reference/Core Components/Table Component
    driver.get(baseUrl + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:64-TableComponent:table_component.xcdf/generatedContent");
    
    //Not we have to wait for loading disappear
    ElementHelper.IsElementInvisible(driver, wait, By.xpath("//div[@class='blockUI blockOverlay']"));
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Validate Page Contents
   * Description:
   *    Here we want to validate the page contents.
   * Steps:
   *    1. Check the widget's title.
   */
  @Test
  public void tc1_PageContent_DisplayTitle() {
  	//Wait for title become visible and with value 'Community Dashboard Framework'
  	wait.until(ExpectedConditions.titleContains("Community Dashboard Framework"));
  	//Wait for visibility of 'TableComponent'
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dashboardContent']/div/div/div/h2/span[2]")));
  	
    // Validate the sample that we are testing is the one
    assertEquals("Community Dashboard Framework", driver.getTitle());
    assertEquals("TableComponent", ElementHelper.GetText(driver, By.xpath("//div[@id='dashboardContent']/div/div/div/h2/span[2]")));
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Reload Sample
   * Description:
   *    Reload the sample (not refresh page).
   * Steps:
   *    1. Click in Code and then click in button 'Try me'.
   */
  @Test
  public void tc2_ReloadSample_SampleReadyToUse(){
  	//Render again the sample
  	ElementHelper.FindElement(driver, By.xpath("//div[@id='example']/ul/li[2]/a")).click();
    ElementHelper.FindElement(driver, By.xpath("//div[@id='code']/button")).click();
    
    //Not we have to wait for loading disappear
    ElementHelper.IsElementInvisible(driver, wait, By.xpath("//div[@class='blockUI blockOverlay']"));

    //The table is now displayed
    assertTrue(ElementHelper.FindElement(driver, By.id("sample")).isDisplayed());
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Check Paging
   * Description:
   *    User has the possibility to navigate between pages, and new date shall
   *    be displayed.
   * Steps:
   *    1. Check the data in first page is correct.
   *    2. Go to the next page and check the data.
   *    3. Go to the end page and check the data.
   *    4. Go to the first page and check the data.
   */
  @Test
  public void tc3_Paging_NavigateBetweenPages() {
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sampleObjectTable_length")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sampleObjectTable_filter")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sampleObjectTable")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sampleObjectTable_info")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sampleObjectTable_paginate")));

    //## Step 1
    assertEquals("Showing 1 to 10 of 50 entries", ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObjectTable_info']")));
    assertEquals("Amica Models & Co.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td")));
    assertEquals("94,117", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td[2]")));
    assertEquals("200,995", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]")));
    assertEquals("Baane Mini Imports", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[6]/td")));
    assertEquals("Cruz & Sons Co.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td")));
    assertEquals("94,016", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]")));

    //## Step 2
    //Where we press NEXT
    WebElement page2 = ElementHelper.FindElement(driver, By.xpath("//a[@id='sampleObjectTable_next']"));
    assertNotNull(page2);
    page2.sendKeys(Keys.ENTER);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='sampleObjectTable_paginate']/span/a[2][@class='paginate_button current']")));
    assertEquals("Showing 11 to 20 of 50 entries", ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObjectTable_info']")));
    assertEquals("Danish Wholesale Imports", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td")));
    assertEquals("145,042", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td[2]")));
    assertEquals("174,140", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]")));
    assertEquals("Extreme Desk Decorations, Ltd", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[6]/td")));
    assertEquals("Handji Gifts& Co", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td")));
    assertEquals("115,499", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]")));

    //## Step 3
    //Where we press 5
    WebElement page5 = ElementHelper.FindElement(driver, By.xpath("//div[@id='sampleObjectTable_paginate']/span/a[5]"));
    assertNotNull(page5);
    page5.sendKeys(Keys.ENTER);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='sampleObjectTable_paginate']/span/a[5][@class='paginate_button current']")));
    assertEquals("Showing 41 to 50 of 50 entries", ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObjectTable_info']")));
    assertEquals("Suominen Souveniers", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td")));
    assertEquals("113,961", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td[2]")));
    assertEquals("160,010", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]")));
    assertEquals("Toys of Finland, Co.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[6]/td")));
    assertEquals("Vitachrome Inc.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td")));
    assertEquals("88,041", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]")));

    //## Step 4
    ElementHelper.FindElement(driver, By.xpath("//a[@id='sampleObjectTable_previous']")).sendKeys(Keys.ENTER);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='sampleObjectTable_paginate']/span/a[4][@class='paginate_button current']")));
    ElementHelper.FindElement(driver, By.xpath("//a[@id='sampleObjectTable_previous']")).sendKeys(Keys.ENTER);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='sampleObjectTable_paginate']/span/a[3][@class='paginate_button current']")));
    ElementHelper.FindElement(driver, By.xpath("//a[@id='sampleObjectTable_previous']")).sendKeys(Keys.ENTER);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='sampleObjectTable_paginate']/span/a[2][@class='paginate_button current']")));
    ElementHelper.FindElement(driver, By.xpath("//a[@id='sampleObjectTable_previous']")).sendKeys(Keys.ENTER);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='sampleObjectTable_paginate']/span/a[@class='paginate_button current']")));

    assertEquals("Showing 1 to 10 of 50 entries", ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObjectTable_info']")));
    assertEquals("Amica Models & Co.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td")));
    assertEquals("94,117", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td[2]")));
    assertEquals("200,995", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]")));
    assertEquals("Baane Mini Imports", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[6]/td")));
    assertEquals("Cruz & Sons Co.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td")));
    assertEquals("94,016", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]")));
  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name:
   *    Sort
   * Description:
   *    Testing the sort on Customer and Sales, and when user is not in the
   *    first page.
   * Steps:
   *    1. Sort in Customer (Desc)
   *    2. Sort in Sales (Asc)
   *    3. Page to the third page
   *    4. Sort in Sales (Desc)
   *    5. Go to the next page and check the data.
   *    6. Go to the end page and check the data.
   *    7. Go to the first page and check the data.
   */
  @Test
  public void tc4_Sort_ElementsAreSort() {
    //## Step 1
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='sampleObjectTable']/thead/tr/th[@class='column0 string sorting_asc']")));
    ElementHelper.FindElement(driver, By.xpath("//table[@id='sampleObjectTable']/thead/tr/th")).click();//Set to DESC
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='sampleObjectTable']/thead/tr/th[@class='column0 string sorting_desc']")));
    //Check Data
    assertNotNull(ElementHelper.FindElement(driver, By.xpath("//div[@id='sampleObjectTable_paginate']/span/a[1][@class='paginate_button current']")));
    assertEquals("Showing 1 to 10 of 50 entries", ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObjectTable_info']")));
    assertEquals("Vitachrome Inc.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td")));
    assertEquals("88,041", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td[2]")));
    assertEquals("118,008", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]")));
    assertTrue(ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[6]/td")).contains("Toms"));
    assertEquals("Suominen Souveniers", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td")));
    assertEquals("113,961", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]")));

    //## Step 2
    ElementHelper.FindElement(driver, By.xpath("//table[@id='sampleObjectTable']/thead/tr/th[2]")).click();//Sort Sales to ASC
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='sampleObjectTable']/thead/tr/th[2][@class='column1 numeric sorting_asc']")));
    //Check Data
    assertNotNull(ElementHelper.FindElement(driver, By.xpath("//div[@id='sampleObjectTable_paginate']/span/a[1][@class='paginate_button current']")));
    assertEquals("Showing 1 to 10 of 50 entries", ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObjectTable_info']")));
    assertEquals("Collectable Mini Designs Co.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td")));
    assertEquals("87,489", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td[2]")));
    assertEquals("88,805", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]")));
    assertEquals("Amica Models & Co.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[6]/td")));
    assertTrue(ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td")).contains("Toms"));
    assertEquals("100,307", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]")));

    //## Step 3
    WebElement page3 = ElementHelper.FindElement(driver, By.xpath("//div[@id='sampleObjectTable_paginate']/span/a[3]"));
    assertNotNull(page3);
    page3.sendKeys(Keys.ENTER);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='sampleObjectTable_paginate']/span/a[3][@class='paginate_button current']")));
    //Check Data
    assertNotNull(ElementHelper.FindElement(driver, By.xpath("//div[@id='sampleObjectTable_paginate']/span/a[3][@class='paginate_button current']")));
    assertEquals("Showing 21 to 30 of 50 entries", ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObjectTable_info']")));
    assertEquals("Handji Gifts& Co", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td")));
    assertEquals("115,499", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td[2]")));
    assertEquals("117,714", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]")));
    assertEquals("Corrida Auto Replicas, Ltd", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[6]/td")));
    assertEquals("Scandinavian Gift Ideas", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td")));
    assertEquals("134,259", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]")));

    //## Step 4
    ElementHelper.FindElement(driver, By.xpath("//table[@id='sampleObjectTable']/thead/tr/th[2]")).click();//Sort Sales to DESC
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='sampleObjectTable']/thead/tr/th[2][@class='column1 numeric sorting_desc']")));
    //Check Data
    assertNotNull(ElementHelper.FindElement(driver, By.xpath("//div[@id='sampleObjectTable_paginate']/span/a[1][@class='paginate_button current']")));
    assertEquals("Showing 1 to 10 of 50 entries", ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObjectTable_info']")));
    assertEquals("Euro+ Shopping Channel", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td")));
    assertEquals("912,294", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td[2]")));
    assertEquals("200,995", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]")));
    assertEquals("Down Under Souveniers, Inc", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[6]/td")));
    assertEquals("Kelly's Gift Shop", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td")));
    assertEquals("158,345", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]")));

    //## Step 5
    WebElement page2 = ElementHelper.FindElement(driver, By.xpath("//a[@id='sampleObjectTable_next']"));
    assertNotNull(page2);
    page2.sendKeys(Keys.ENTER);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='sampleObjectTable_paginate']/span/a[2][@class='paginate_button current']")));
    assertNotNull(ElementHelper.FindElement(driver, By.xpath("//div[@id='sampleObjectTable_paginate']/span/a[2][@class='paginate_button current']")));
    assertEquals("Showing 11 to 20 of 50 entries", ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObjectTable_info']")));
    assertEquals("AV Stores, Co.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td")));
    assertEquals("157,808", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td[2]")));
    assertEquals("151,571", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]")));
    assertEquals("Danish Wholesale Imports", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[6]/td")));
    assertEquals("Reims Collectables", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td")));
    assertEquals("135,043", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]")));

    //## Step 6
    ElementHelper.FindElement(driver, By.xpath("//a[@id='sampleObjectTable_next']")).sendKeys(Keys.ENTER);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='sampleObjectTable_paginate']/span/a[3][@class='paginate_button current']")));
    ElementHelper.FindElement(driver, By.xpath("//a[@id='sampleObjectTable_next']")).sendKeys(Keys.ENTER);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='sampleObjectTable_paginate']/span/a[4][@class='paginate_button current']")));
    ElementHelper.FindElement(driver, By.xpath("//a[@id='sampleObjectTable_next']")).sendKeys(Keys.ENTER);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='sampleObjectTable_paginate']/span/a[5][@class='paginate_button current']")));
    assertNotNull(ElementHelper.FindElement(driver, By.xpath("//div[@id='sampleObjectTable_paginate']/span/a[5][@class='paginate_button current']")));
    assertEquals("Showing 41 to 50 of 50 entries", ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObjectTable_info']")));
    assertTrue(ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td")).contains("Toms"));
    assertEquals("100,307", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td[2]")));
    assertEquals("98,496", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]")));
    assertEquals("Cruz & Sons Co.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[6]/td")));
    assertEquals("Collectable Mini Designs Co.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td")));
    assertEquals("87,489", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]")));

    //## Step 7
    WebElement page1 = ElementHelper.FindElement(driver, By.xpath("//div[@id='sampleObjectTable_paginate']/span/a[1]"));
    assertNotNull(page1);
    page1.sendKeys(Keys.ENTER);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='sampleObjectTable_paginate']/span/a[1][@class='paginate_button current']")));
    //Check Data
    assertNotNull(ElementHelper.FindElement(driver, By.xpath("//div[@id='sampleObjectTable_paginate']/span/a[1][@class='paginate_button current']")));
    assertEquals("Showing 1 to 10 of 50 entries", ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObjectTable_info']")));
    assertEquals("Euro+ Shopping Channel", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td")));
    assertEquals("912,294", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td[2]")));
    assertEquals("200,995", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]")));
    assertEquals("Down Under Souveniers, Inc", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[6]/td")));
    assertEquals("Kelly's Gift Shop", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td")));
    assertEquals("158,345", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]")));


    //reset to initial state
    ElementHelper.FindElement(driver, By.xpath("//table[@id='sampleObjectTable']/thead/tr/th")).click();//Set Customers to ASC
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='sampleObjectTable']/thead/tr/th[@class='column0 string sorting_asc']")));
    assertEquals("Showing 1 to 10 of 50 entries", ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObjectTable_info']")));
    assertEquals("Amica Models & Co.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td")));
    assertEquals("94,117", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td[2]")));
    assertEquals("200,995", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]")));
    assertEquals("Baane Mini Imports", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[6]/td")));
    assertEquals("Cruz & Sons Co.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td")));
    assertEquals("94,016", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]")));
  }

  /**
   * ############################### Test Case 5 ###############################
   *
   * Test Case Name:
   *    Display Entries
   * Description:
   *    When select the number of entries, the table displayed the number of
   *    entries selected with data.
   * Steps:
   *    1. Select 25 and paging
   *    2. Select 50 (no paging)
   */
  @Test
  public void tc5_DisplayEntries_DisplayTheNumberOfEntriesSelected() {
    assertEquals("Showing 1 to 10 of 50 entries", ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObjectTable_info']")));
    assertEquals("Amica Models & Co.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td")));
    assertEquals("94,117", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td[2]")));
    assertEquals("200,995", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]")));
    assertEquals("Baane Mini Imports", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[6]/td")));
    assertEquals("Cruz & Sons Co.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td")));
    assertEquals("94,016", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]")));

    //## Step 1
    Select displayEntries = new Select(ElementHelper.FindElement(driver, By.xpath("//div[@id='sampleObjectTable_length']/label/select")));
    displayEntries.selectByValue("25");
    assertEquals("Showing 1 to 25 of 50 entries", ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObjectTable_info']")));
    assertEquals("Amica Models & Co.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td")));
    assertEquals("94,117", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td[2]")));
    assertEquals("200,995", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]")));
    assertEquals("Baane Mini Imports", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[6]/td")));
    assertEquals("Cruz & Sons Co.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td")));
    assertEquals("94,016", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]")));
    //11 to 25
    assertEquals("Dragon Souveniers, Ltd.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[14]/td")));
    assertEquals("172,990", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[14]/td[2]")));
    assertEquals("98,924", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[17]/td[2]")));
    assertEquals("Handji Gifts& Co", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[20]/td")));
    assertEquals("La Corne D'abondance, Co.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[25]/td")));
    assertEquals("97,204", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[25]/td[2]")));

    //## Step 2
    displayEntries = new Select(ElementHelper.FindElement(driver, By.xpath("//div[@id='sampleObjectTable_length']/label/select")));
    displayEntries.selectByValue("50");
    assertEquals("Showing 1 to 50 of 50 entries", ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObjectTable_info']")));
    assertEquals("Amica Models & Co.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td")));
    assertEquals("94,117", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td[2]")));
    assertEquals("200,995", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]")));
    assertEquals("Baane Mini Imports", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[6]/td")));
    assertEquals("Cruz & Sons Co.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td")));
    assertEquals("94,016", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]")));
    //11 to 25
    assertEquals("Dragon Souveniers, Ltd.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[14]/td")));
    assertEquals("172,990", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[14]/td[2]")));
    assertEquals("98,924", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[17]/td[2]")));
    assertEquals("Handji Gifts& Co", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[20]/td")));
    assertEquals("La Corne D'abondance, Co.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[25]/td")));
    assertEquals("97,204", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[25]/td[2]")));
    //26 to 50
    assertEquals("Muscle Machine Inc", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[31]/td")));
    assertEquals("197,737", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[31]/td[2]")));
    assertEquals("151,571", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[39]/td[2]")));
    assertEquals("Toys of Finland, Co.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[46]/td")));
    assertEquals("Vitachrome Inc.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[50]/td")));
    assertEquals("88,041", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[50]/td[2]")));

    //Reset display to 10
    displayEntries = new Select(ElementHelper.FindElement(driver, By.xpath("//div[@id='sampleObjectTable_length']/label/select")));
    displayEntries.selectByValue("10");
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='sampleObjectTable_paginate']/span/a[5]")));
    assertEquals("Showing 1 to 10 of 50 entries", ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObjectTable_info']")));
    assertEquals("Amica Models & Co.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td")));
    assertEquals("94,117", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td[2]")));
    assertEquals("200,995", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]")));
    assertEquals("Baane Mini Imports", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[6]/td")));
    assertEquals("Cruz & Sons Co.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td")));
    assertEquals("94,016", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]")));
  }

  /**
   * ############################### Test Case 6 ###############################
   *
   * Test Case Name:
   *    Search Engine
   * Description:
   *    When search for something the table is refresh with the contents
   *    searched.
   * Steps:
   *    1. Search for 'Co.' (Check paging, display entries, sort)
   *    2. Search for 'Euro' (Check paging, display entries, sort)
   *    3. Search for 'TODO' (no result)
   */
  @Test
  public void tc6_SearchEngine_TableDisplayedContentSearch() {
    //## Step 1
    ElementHelper.FindElement(driver, By.xpath("//div[@id='sampleObjectTable_filter']/label/input")).sendKeys("Co.");
    assertEquals("Showing 1 to 10 of 13 entries (filtered from 50 total entries)", ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObjectTable_info']")));
    assertTrue(ElementHelper.FindElement(driver, By.id("sampleObjectTable_previous")).isDisplayed());
    assertTrue(ElementHelper.FindElement(driver, By.id("sampleObjectTable_previous")).isEnabled());
    assertTrue(ElementHelper.FindElement(driver, By.id("sampleObjectTable_next")).isDisplayed());
    assertTrue(ElementHelper.FindElement(driver, By.id("sampleObjectTable_next")).isEnabled());
    //check paging 1 and 2
    assertTrue(ElementHelper.FindElement(driver, By.xpath("//div[@id='sampleObjectTable_paginate']/span/a[1]")).isEnabled());
    assertTrue(ElementHelper.FindElement(driver, By.xpath("//div[@id='sampleObjectTable_paginate']/span/a[1]")).isDisplayed());
    assertTrue(ElementHelper.FindElement(driver, By.xpath("//div[@id='sampleObjectTable_paginate']/span/a[2]")).isEnabled());
    assertTrue(ElementHelper.FindElement(driver, By.xpath("//div[@id='sampleObjectTable_paginate']/span/a[2]")).isDisplayed());
    assertEquals("Amica Models & Co.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td")));
    assertEquals("94,117", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td[2]")));
    assertEquals("157,808", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]")));
    assertEquals("Cruz & Sons Co.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[6]/td")));
    assertEquals("Saveley & Henriot, Co.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td")));
    assertEquals("142,874", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]")));
    //Click Next
    ElementHelper.FindElement(driver, By.xpath("//a[@id='sampleObjectTable_next']")).sendKeys(Keys.ENTER);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='sampleObjectTable_paginate']/span/a[2][@class='paginate_button current']")));
    assertEquals("Souveniers And Things Co.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[1]/td")));
    assertEquals("Toys of Finland, Co.", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[3]/td")));
    assertEquals("111,250", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]")));

    //## Step 2
    ElementHelper.FindElement(driver, By.xpath("//div[@id='sampleObjectTable_filter']/label/input")).clear();
    ElementHelper.FindElement(driver, By.xpath("//div[@id='sampleObjectTable_filter']/label/input")).sendKeys("Euro");
    assertTrue(ElementHelper.FindElement(driver, By.id("sampleObjectTable_previous")).isDisplayed());
    assertTrue(ElementHelper.FindElement(driver, By.id("sampleObjectTable_previous")).isEnabled());
    assertTrue(ElementHelper.FindElement(driver, By.id("sampleObjectTable_next")).isDisplayed());
    assertTrue(ElementHelper.FindElement(driver, By.id("sampleObjectTable_next")).isEnabled());
    assertTrue(ElementHelper.FindElement(driver, By.xpath("//div[@id='sampleObjectTable_paginate']/span/a[1]")).isEnabled());
    assertTrue(ElementHelper.FindElement(driver, By.xpath("//div[@id='sampleObjectTable_paginate']/span/a[1]")).isDisplayed());
    assertFalse(ElementHelper.IsElementPresent(driver, 3, By.xpath("//div[@id='sampleObjectTable_paginate']/span/a[2]")));
    assertEquals("Showing 1 to 1 of 1 entries (filtered from 50 total entries)", ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObjectTable_info']")));
    assertEquals("Euro+ Shopping Channel", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td")));
    assertEquals("912,294", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td[2]")));

    //## Step 3
    ElementHelper.FindElement(driver, By.xpath("//div[@id='sampleObjectTable_filter']/label/input")).clear();
    ElementHelper.FindElement(driver, By.xpath("//div[@id='sampleObjectTable_filter']/label/input")).sendKeys("TODO");
    assertTrue(ElementHelper.FindElement(driver, By.id("sampleObjectTable_previous")).isDisplayed());
    assertTrue(ElementHelper.FindElement(driver, By.id("sampleObjectTable_previous")).isEnabled());
    assertTrue(ElementHelper.FindElement(driver, By.id("sampleObjectTable_next")).isDisplayed());
    assertTrue(ElementHelper.FindElement(driver, By.id("sampleObjectTable_next")).isEnabled());
    assertFalse(ElementHelper.IsElementPresent(driver, 3, By.xpath("//div[@id='sampleObjectTable_paginate']/span/a[1]")));
    assertEquals("Showing 0 to 0 of 0 entries (filtered from 50 total entries)", ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObjectTable_info']")));
    assertEquals("No matching records found", ElementHelper.GetText(driver, By.xpath("//table[@id='sampleObjectTable']/tbody/tr/td")));
  }

  @AfterClass
  public static void tearDown() { }
}
