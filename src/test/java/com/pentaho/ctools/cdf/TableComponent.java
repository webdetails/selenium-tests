/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2015 by Pentaho : http://www.pentaho.com
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

package com.pentaho.ctools.cdf;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.BaseTest;
import com.pentaho.ctools.utils.ElementHelper;

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
public class TableComponent extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  //Log instance
  private final Logger log = LogManager.getLogger( TableComponent.class );

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Open Sample Data
   */
  @Test
  public void tc0_OpenSampleData_Display() {
    this.log.info( "tc0_OpenSampleData_Display" );

    //The URL for the TableComponent under CDF samples
    //This samples is in: Public/plugin-samples/CDF/Documentation/Component Reference/Core Components/Table Component
    driver.get( baseUrl + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:64-TableComponent:table_component.xcdf/generatedContent" );

    //NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
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
    this.log.info( "tc1_PageContent_DisplayTitle" );

    //Wait for title become visible and with value 'Community Dashboard Framework'
    wait.until( ExpectedConditions.titleContains( "Community Dashboard Framework" ) );
    //Wait for visibility of 'TableComponent'
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );

    // Validate the sample that we are testing is the one
    assertEquals( "Community Dashboard Framework", driver.getTitle() );
    assertEquals( "TableComponent", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );
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
  public void tc2_ReloadSample_SampleReadyToUse() {
    this.log.info( "tc2_ReloadSample_SampleReadyToUse" );

    //Render again the sample
    this.elemHelper.FindElement( driver, By.xpath( "//div[@id='example']/ul/li[2]/a" ) ).click();
    this.elemHelper.FindElement( driver, By.xpath( "//div[@id='code']/button" ) ).click();

    //NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //The table is now displayed
    assertTrue( this.elemHelper.FindElement( driver, By.id( "sample" ) ).isDisplayed() );
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
    this.log.info( "tc3_Paging_NavigateBetweenPages" );

    wait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "sampleObjectTable_length" ) ) );
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "sampleObjectTable_filter" ) ) );
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "sampleObjectTable" ) ) );
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "sampleObjectTable_info" ) ) );
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "sampleObjectTable_paginate" ) ) );

    //## Step 1
    assertEquals( "Showing 1 to 10 of 50 entries", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObjectTable_info']" ) ) );
    assertEquals( "Amica Models & Co.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td" ) ) );
    assertEquals( "94,117", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td[2]" ) ) );
    assertEquals( "200,995", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]" ) ) );
    assertEquals( "Baane Mini Imports", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[6]/td" ) ) );
    assertEquals( "Cruz & Sons Co.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td" ) ) );
    assertEquals( "94,016", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]" ) ) );

    //## Step 2
    //Where we press NEXT
    WebElement page2 = this.elemHelper.FindElement( driver, By.xpath( "//a[@id='sampleObjectTable_next']" ) );
    assertNotNull( page2 );
    page2.sendKeys( Keys.ENTER );
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='sampleObjectTable_paginate']/span/a[2][@class='paginate_button current']" ) ) );
    assertEquals( "Showing 11 to 20 of 50 entries", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObjectTable_info']" ) ) );
    assertEquals( "Danish Wholesale Imports", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td" ) ) );
    assertEquals( "145,042", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td[2]" ) ) );
    assertEquals( "174,140", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]" ) ) );
    assertEquals( "Extreme Desk Decorations, Ltd", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[6]/td" ) ) );
    assertEquals( "Handji Gifts& Co", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td" ) ) );
    assertEquals( "115,499", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]" ) ) );

    //## Step 3
    //Where we press 5
    WebElement page5 = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='sampleObjectTable_paginate']/span/a[5]" ) );
    assertNotNull( page5 );
    page5.sendKeys( Keys.ENTER );
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='sampleObjectTable_paginate']/span/a[5][@class='paginate_button current']" ) ) );
    assertEquals( "Showing 41 to 50 of 50 entries", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObjectTable_info']" ) ) );
    assertEquals( "Suominen Souveniers", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td" ) ) );
    assertEquals( "113,961", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td[2]" ) ) );
    assertEquals( "160,010", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]" ) ) );
    assertEquals( "Toys of Finland, Co.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[6]/td" ) ) );
    assertEquals( "Vitachrome Inc.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td" ) ) );
    assertEquals( "88,041", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]" ) ) );

    //## Step 4
    this.elemHelper.FindElement( driver, By.xpath( "//a[@id='sampleObjectTable_previous']" ) ).sendKeys( Keys.ENTER );
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='sampleObjectTable_paginate']/span/a[4][@class='paginate_button current']" ) ) );
    this.elemHelper.FindElement( driver, By.xpath( "//a[@id='sampleObjectTable_previous']" ) ).sendKeys( Keys.ENTER );
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='sampleObjectTable_paginate']/span/a[3][@class='paginate_button current']" ) ) );
    this.elemHelper.FindElement( driver, By.xpath( "//a[@id='sampleObjectTable_previous']" ) ).sendKeys( Keys.ENTER );
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='sampleObjectTable_paginate']/span/a[2][@class='paginate_button current']" ) ) );
    this.elemHelper.FindElement( driver, By.xpath( "//a[@id='sampleObjectTable_previous']" ) ).sendKeys( Keys.ENTER );
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='sampleObjectTable_paginate']/span/a[@class='paginate_button current']" ) ) );

    assertEquals( "Showing 1 to 10 of 50 entries", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObjectTable_info']" ) ) );
    assertEquals( "Amica Models & Co.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td" ) ) );
    assertEquals( "94,117", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td[2]" ) ) );
    assertEquals( "200,995", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]" ) ) );
    assertEquals( "Baane Mini Imports", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[6]/td" ) ) );
    assertEquals( "Cruz & Sons Co.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td" ) ) );
    assertEquals( "94,016", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]" ) ) );
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
    this.log.info( "tc4_Sort_ElementsAreSort" );

    //## Step 1
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//table[@id='sampleObjectTable']/thead/tr/th[@class='column0 string sorting_asc']" ) ) );
    this.elemHelper.FindElement( driver, By.xpath( "//table[@id='sampleObjectTable']/thead/tr/th" ) ).click(); //Set to DESC
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//table[@id='sampleObjectTable']/thead/tr/th[@class='column0 string sorting_desc']" ) ) );
    //Check Data
    assertNotNull( this.elemHelper.FindElement( driver, By.xpath( "//div[@id='sampleObjectTable_paginate']/span/a[1][@class='paginate_button current']" ) ) );
    assertEquals( "Showing 1 to 10 of 50 entries", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObjectTable_info']" ) ) );
    assertEquals( "Vitachrome Inc.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td" ) ) );
    assertEquals( "88,041", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td[2]" ) ) );
    assertEquals( "118,008", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]" ) ) );
    assertTrue( this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[6]/td" ) ).contains( "Toms" ) );
    assertEquals( "Suominen Souveniers", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td" ) ) );
    assertEquals( "113,961", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]" ) ) );

    //## Step 2
    this.elemHelper.FindElement( driver, By.xpath( "//table[@id='sampleObjectTable']/thead/tr/th[2]" ) ).click(); //Sort Sales to ASC
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//table[@id='sampleObjectTable']/thead/tr/th[2][@class='column1 numeric sorting_asc']" ) ) );
    //Check Data
    assertNotNull( this.elemHelper.FindElement( driver, By.xpath( "//div[@id='sampleObjectTable_paginate']/span/a[1][@class='paginate_button current']" ) ) );
    assertEquals( "Showing 1 to 10 of 50 entries", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObjectTable_info']" ) ) );
    assertEquals( "Collectable Mini Designs Co.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td" ) ) );
    assertEquals( "87,489", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td[2]" ) ) );
    assertEquals( "88,805", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]" ) ) );
    assertEquals( "Amica Models & Co.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[6]/td" ) ) );
    assertTrue( this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td" ) ).contains( "Toms" ) );
    assertEquals( "100,307", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]" ) ) );

    //## Step 3
    WebElement page3 = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='sampleObjectTable_paginate']/span/a[3]" ) );
    assertNotNull( page3 );
    page3.sendKeys( Keys.ENTER );
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='sampleObjectTable_paginate']/span/a[3][@class='paginate_button current']" ) ) );
    //Check Data
    assertNotNull( this.elemHelper.FindElement( driver, By.xpath( "//div[@id='sampleObjectTable_paginate']/span/a[3][@class='paginate_button current']" ) ) );
    assertEquals( "Showing 21 to 30 of 50 entries", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObjectTable_info']" ) ) );
    assertEquals( "Handji Gifts& Co", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td" ) ) );
    assertEquals( "115,499", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td[2]" ) ) );
    assertEquals( "117,714", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]" ) ) );
    assertEquals( "Corrida Auto Replicas, Ltd", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[6]/td" ) ) );
    assertEquals( "Scandinavian Gift Ideas", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td" ) ) );
    assertEquals( "134,259", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]" ) ) );

    //## Step 4
    this.elemHelper.FindElement( driver, By.xpath( "//table[@id='sampleObjectTable']/thead/tr/th[2]" ) ).click(); //Sort Sales to DESC
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//table[@id='sampleObjectTable']/thead/tr/th[2][@class='column1 numeric sorting_desc']" ) ) );
    //Check Data
    assertNotNull( this.elemHelper.FindElement( driver, By.xpath( "//div[@id='sampleObjectTable_paginate']/span/a[1][@class='paginate_button current']" ) ) );
    assertEquals( "Showing 1 to 10 of 50 entries", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObjectTable_info']" ) ) );
    assertEquals( "Euro+ Shopping Channel", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td" ) ) );
    assertEquals( "912,294", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td[2]" ) ) );
    assertEquals( "200,995", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]" ) ) );
    assertEquals( "Down Under Souveniers, Inc", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[6]/td" ) ) );
    assertEquals( "Kelly's Gift Shop", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td" ) ) );
    assertEquals( "158,345", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]" ) ) );

    //## Step 5
    WebElement page2 = this.elemHelper.FindElement( driver, By.xpath( "//a[@id='sampleObjectTable_next']" ) );
    assertNotNull( page2 );
    page2.sendKeys( Keys.ENTER );
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='sampleObjectTable_paginate']/span/a[2][@class='paginate_button current']" ) ) );
    assertNotNull( this.elemHelper.FindElement( driver, By.xpath( "//div[@id='sampleObjectTable_paginate']/span/a[2][@class='paginate_button current']" ) ) );
    assertEquals( "Showing 11 to 20 of 50 entries", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObjectTable_info']" ) ) );
    assertEquals( "AV Stores, Co.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td" ) ) );
    assertEquals( "157,808", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td[2]" ) ) );
    assertEquals( "151,571", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]" ) ) );
    assertEquals( "Danish Wholesale Imports", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[6]/td" ) ) );
    assertEquals( "Reims Collectables", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td" ) ) );
    assertEquals( "135,043", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]" ) ) );

    //## Step 6
    this.elemHelper.FindElement( driver, By.xpath( "//a[@id='sampleObjectTable_next']" ) ).sendKeys( Keys.ENTER );
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='sampleObjectTable_paginate']/span/a[3][@class='paginate_button current']" ) ) );
    this.elemHelper.FindElement( driver, By.xpath( "//a[@id='sampleObjectTable_next']" ) ).sendKeys( Keys.ENTER );
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='sampleObjectTable_paginate']/span/a[4][@class='paginate_button current']" ) ) );
    this.elemHelper.FindElement( driver, By.xpath( "//a[@id='sampleObjectTable_next']" ) ).sendKeys( Keys.ENTER );
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='sampleObjectTable_paginate']/span/a[5][@class='paginate_button current']" ) ) );
    assertNotNull( this.elemHelper.FindElement( driver, By.xpath( "//div[@id='sampleObjectTable_paginate']/span/a[5][@class='paginate_button current']" ) ) );
    assertEquals( "Showing 41 to 50 of 50 entries", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObjectTable_info']" ) ) );
    assertTrue( this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td" ) ).contains( "Toms" ) );
    assertEquals( "100,307", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td[2]" ) ) );
    assertEquals( "98,496", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]" ) ) );
    assertEquals( "Cruz & Sons Co.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[6]/td" ) ) );
    assertEquals( "Collectable Mini Designs Co.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td" ) ) );
    assertEquals( "87,489", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]" ) ) );

    //## Step 7
    WebElement page1 = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='sampleObjectTable_paginate']/span/a[1]" ) );
    assertNotNull( page1 );
    page1.sendKeys( Keys.ENTER );
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='sampleObjectTable_paginate']/span/a[1][@class='paginate_button current']" ) ) );
    //Check Data
    assertNotNull( this.elemHelper.FindElement( driver, By.xpath( "//div[@id='sampleObjectTable_paginate']/span/a[1][@class='paginate_button current']" ) ) );
    assertEquals( "Showing 1 to 10 of 50 entries", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObjectTable_info']" ) ) );
    assertEquals( "Euro+ Shopping Channel", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td" ) ) );
    assertEquals( "912,294", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td[2]" ) ) );
    assertEquals( "200,995", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]" ) ) );
    assertEquals( "Down Under Souveniers, Inc", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[6]/td" ) ) );
    assertEquals( "Kelly's Gift Shop", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td" ) ) );
    assertEquals( "158,345", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]" ) ) );

    //reset to initial state
    this.elemHelper.FindElement( driver, By.xpath( "//table[@id='sampleObjectTable']/thead/tr/th" ) ).click(); //Set Customers to ASC
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//table[@id='sampleObjectTable']/thead/tr/th[@class='column0 string sorting_asc']" ) ) );
    assertEquals( "Showing 1 to 10 of 50 entries", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObjectTable_info']" ) ) );
    assertEquals( "Amica Models & Co.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td" ) ) );
    assertEquals( "94,117", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td[2]" ) ) );
    assertEquals( "200,995", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]" ) ) );
    assertEquals( "Baane Mini Imports", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[6]/td" ) ) );
    assertEquals( "Cruz & Sons Co.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td" ) ) );
    assertEquals( "94,016", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]" ) ) );
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
    this.log.info( "tc5_DisplayEntries_DisplayTheNumberOfEntriesSelected" );

    assertEquals( "Showing 1 to 10 of 50 entries", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObjectTable_info']" ) ) );
    assertEquals( "Amica Models & Co.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td" ) ) );
    assertEquals( "94,117", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td[2]" ) ) );
    assertEquals( "200,995", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]" ) ) );
    assertEquals( "Baane Mini Imports", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[6]/td" ) ) );
    assertEquals( "Cruz & Sons Co.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td" ) ) );
    assertEquals( "94,016", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]" ) ) );

    //## Step 1
    Select displayEntries = new Select( this.elemHelper.FindElement( driver, By.xpath( "//div[@id='sampleObjectTable_length']/label/select" ) ) );
    displayEntries.selectByValue( "25" );
    assertEquals( "Showing 1 to 25 of 50 entries", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObjectTable_info']" ) ) );
    assertEquals( "Amica Models & Co.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td" ) ) );
    assertEquals( "94,117", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td[2]" ) ) );
    assertEquals( "200,995", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]" ) ) );
    assertEquals( "Baane Mini Imports", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[6]/td" ) ) );
    assertEquals( "Cruz & Sons Co.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td" ) ) );
    assertEquals( "94,016", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]" ) ) );
    //11 to 25
    assertEquals( "Dragon Souveniers, Ltd.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[14]/td" ) ) );
    assertEquals( "172,990", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[14]/td[2]" ) ) );
    assertEquals( "98,924", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[17]/td[2]" ) ) );
    assertEquals( "Handji Gifts& Co", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[20]/td" ) ) );
    assertEquals( "La Corne D'abondance, Co.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[25]/td" ) ) );
    assertEquals( "97,204", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[25]/td[2]" ) ) );

    //## Step 2
    displayEntries = new Select( this.elemHelper.FindElement( driver, By.xpath( "//div[@id='sampleObjectTable_length']/label/select" ) ) );
    displayEntries.selectByValue( "50" );
    assertEquals( "Showing 1 to 50 of 50 entries", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObjectTable_info']" ) ) );
    assertEquals( "Amica Models & Co.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td" ) ) );
    assertEquals( "94,117", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td[2]" ) ) );
    assertEquals( "200,995", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]" ) ) );
    assertEquals( "Baane Mini Imports", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[6]/td" ) ) );
    assertEquals( "Cruz & Sons Co.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td" ) ) );
    assertEquals( "94,016", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]" ) ) );
    //11 to 25
    assertEquals( "Dragon Souveniers, Ltd.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[14]/td" ) ) );
    assertEquals( "172,990", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[14]/td[2]" ) ) );
    assertEquals( "98,924", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[17]/td[2]" ) ) );
    assertEquals( "Handji Gifts& Co", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[20]/td" ) ) );
    assertEquals( "La Corne D'abondance, Co.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[25]/td" ) ) );
    assertEquals( "97,204", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[25]/td[2]" ) ) );
    //26 to 50
    assertEquals( "Muscle Machine Inc", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[31]/td" ) ) );
    assertEquals( "197,737", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[31]/td[2]" ) ) );
    assertEquals( "151,571", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[39]/td[2]" ) ) );
    assertEquals( "Toys of Finland, Co.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[46]/td" ) ) );
    assertEquals( "Vitachrome Inc.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[50]/td" ) ) );
    assertEquals( "88,041", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[50]/td[2]" ) ) );

    //Reset display to 10
    displayEntries = new Select( this.elemHelper.FindElement( driver, By.xpath( "//div[@id='sampleObjectTable_length']/label/select" ) ) );
    displayEntries.selectByValue( "10" );
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='sampleObjectTable_paginate']/span/a[5]" ) ) );
    assertEquals( "Showing 1 to 10 of 50 entries", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObjectTable_info']" ) ) );
    assertEquals( "Amica Models & Co.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td" ) ) );
    assertEquals( "94,117", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td[2]" ) ) );
    assertEquals( "200,995", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]" ) ) );
    assertEquals( "Baane Mini Imports", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[6]/td" ) ) );
    assertEquals( "Cruz & Sons Co.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td" ) ) );
    assertEquals( "94,016", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]" ) ) );
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
    this.log.info( "tc6_SearchEngine_TableDisplayedContentSearch" );

    //## Step 1
    this.elemHelper.FindElement( driver, By.xpath( "//div[@id='sampleObjectTable_filter']/label/input" ) ).sendKeys( "Co." );
    assertEquals( "Showing 1 to 10 of 13 entries (filtered from 50 total entries)", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObjectTable_info']" ) ) );
    assertTrue( this.elemHelper.FindElement( driver, By.id( "sampleObjectTable_previous" ) ).isDisplayed() );
    assertTrue( this.elemHelper.FindElement( driver, By.id( "sampleObjectTable_previous" ) ).isEnabled() );
    assertTrue( this.elemHelper.FindElement( driver, By.id( "sampleObjectTable_next" ) ).isDisplayed() );
    assertTrue( this.elemHelper.FindElement( driver, By.id( "sampleObjectTable_next" ) ).isEnabled() );
    //check paging 1 and 2
    assertTrue( this.elemHelper.FindElement( driver, By.xpath( "//div[@id='sampleObjectTable_paginate']/span/a[1]" ) ).isEnabled() );
    assertTrue( this.elemHelper.FindElement( driver, By.xpath( "//div[@id='sampleObjectTable_paginate']/span/a[1]" ) ).isDisplayed() );
    assertTrue( this.elemHelper.FindElement( driver, By.xpath( "//div[@id='sampleObjectTable_paginate']/span/a[2]" ) ).isEnabled() );
    assertTrue( this.elemHelper.FindElement( driver, By.xpath( "//div[@id='sampleObjectTable_paginate']/span/a[2]" ) ).isDisplayed() );
    assertEquals( "Amica Models & Co.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td" ) ) );
    assertEquals( "94,117", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td[2]" ) ) );
    assertEquals( "157,808", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]" ) ) );
    assertEquals( "Cruz & Sons Co.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[6]/td" ) ) );
    assertEquals( "Saveley & Henriot, Co.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td" ) ) );
    assertEquals( "142,874", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[10]/td[2]" ) ) );
    //Click Next
    this.elemHelper.FindElement( driver, By.xpath( "//a[@id='sampleObjectTable_next']" ) ).sendKeys( Keys.ENTER );
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='sampleObjectTable_paginate']/span/a[2][@class='paginate_button current']" ) ) );
    assertEquals( "Souveniers And Things Co.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[1]/td" ) ) );
    assertEquals( "Toys of Finland, Co.", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[3]/td" ) ) );
    assertEquals( "111,250", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr[3]/td[2]" ) ) );

    //## Step 2
    this.elemHelper.FindElement( driver, By.xpath( "//div[@id='sampleObjectTable_filter']/label/input" ) ).clear();
    this.elemHelper.FindElement( driver, By.xpath( "//div[@id='sampleObjectTable_filter']/label/input" ) ).sendKeys( "Euro" );
    assertTrue( this.elemHelper.FindElement( driver, By.id( "sampleObjectTable_previous" ) ).isDisplayed() );
    assertTrue( this.elemHelper.FindElement( driver, By.id( "sampleObjectTable_previous" ) ).isEnabled() );
    assertTrue( this.elemHelper.FindElement( driver, By.id( "sampleObjectTable_next" ) ).isDisplayed() );
    assertTrue( this.elemHelper.FindElement( driver, By.id( "sampleObjectTable_next" ) ).isEnabled() );
    assertTrue( this.elemHelper.FindElement( driver, By.xpath( "//div[@id='sampleObjectTable_paginate']/span/a[1]" ) ).isEnabled() );
    assertTrue( this.elemHelper.FindElement( driver, By.xpath( "//div[@id='sampleObjectTable_paginate']/span/a[1]" ) ).isDisplayed() );
    assertTrue( this.elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@id='sampleObjectTable_paginate']/span/a[2]" ), 2 ) );
    assertEquals( "Showing 1 to 1 of 1 entries (filtered from 50 total entries)", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObjectTable_info']" ) ) );
    assertEquals( "Euro+ Shopping Channel", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td" ) ) );
    assertEquals( "912,294", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td[2]" ) ) );

    //## Step 3
    this.elemHelper.FindElement( driver, By.xpath( "//div[@id='sampleObjectTable_filter']/label/input" ) ).clear();
    this.elemHelper.FindElement( driver, By.xpath( "//div[@id='sampleObjectTable_filter']/label/input" ) ).sendKeys( "TODO" );
    assertTrue( this.elemHelper.FindElement( driver, By.id( "sampleObjectTable_previous" ) ).isDisplayed() );
    assertTrue( this.elemHelper.FindElement( driver, By.id( "sampleObjectTable_previous" ) ).isEnabled() );
    assertTrue( this.elemHelper.FindElement( driver, By.id( "sampleObjectTable_next" ) ).isDisplayed() );
    assertTrue( this.elemHelper.FindElement( driver, By.id( "sampleObjectTable_next" ) ).isEnabled() );
    assertTrue( this.elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@id='sampleObjectTable_paginate']/span/a[1]" ), 2 ) );
    assertEquals( "Showing 0 to 0 of 0 entries (filtered from 50 total entries)", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObjectTable_info']" ) ) );
    assertEquals( "No matching records found", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='sampleObjectTable']/tbody/tr/td" ) ) );
  }
}
