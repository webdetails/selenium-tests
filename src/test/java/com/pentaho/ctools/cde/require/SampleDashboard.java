/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2016 by Pentaho : http://www.pentaho.com
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
package com.pentaho.ctools.cde.require;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with CDA Cache Manager.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class SampleDashboard extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( SampleDashboard.class );

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Open Sample Page
   */
  @Test
  public void tc0_OpenSamplePage_Display() {
    this.log.info( "tc0_OpenSamplePage_Display" );

    // Go to the CDE Sample Dashboard web page.
    this.elemHelper.Get( BaseTest.driver, PageUrl.SAMPLE_DASHBOARD_REQUIRE );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Page Content
   *
   * Description:
   *    The test case pretends to validate the display data in CDE Sample
   *    Dashboard is according with expected behavior.
   *
   * Steps:
   *    1. Check for display text
   */
  @Test
  public void tc1_PageContent_ContentDisplayedAsExpected() {
    this.log.info( "tc1_PageContent_ContentDisplayedAsExpected" );

    /*
     * ## Step 1
     */
    //Check title
    final String pageTitle = this.elemHelper.WaitForTitle( BaseTest.driver, "CDE Sample Dashboard" );
    Assert.assertEquals( "CDE Sample Dashboard", pageTitle );

    //Check subtitle
    final String textSubTitle = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='body']/div[2]/div" ) );
    Assert.assertEquals( "How to start", textSubTitle );

    //Check a paragraph
    final String textSomeParag = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='body']/div[2]/div[2]/p[2]" ) );
    Assert.assertEquals( "Totally extensible, virtually everything is possible, and CDE doesn't stop any of the advanced tricks supported by CDF.", textSomeParag );

    //Check the last paragraph
    final String textLastParg = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='body']/div[2]/div[2]/p[10]" ) );
    Assert.assertEquals( "Enjoy", textLastParg );

    //Check the signature
    final String textAuthor = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='body']/div[2]/div[3]" ) );
    Assert.assertEquals( "Pedro Alves, Hitachi Vantara", textAuthor );

    //Check we have a chart
    final WebElement elemChart = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='chart']/div//*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']" ) );
    Assert.assertNotNull( elemChart );

    //Check we have a table
    final WebElement elemTable = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr/td" ) );
    Assert.assertNotNull( elemTable );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Paging
   *
   * Description:
   *    The test case pretends to validate paging functionality.
   *
   * Steps:
   *    1. Press next
   *    2. Go to the end
   *    3. Press preview
   *    4. Press preview
   *    5. Press next
   */
  @Test
  public void tc2_Paging_UserCanPagingBettweenPages() {
    this.log.info( "tc2_Paging_UserCanPagingBettweenPages" );

    /*
     * ## Step 1
     */
    String customer = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[2]/td" ) );
    String total = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[7]/td[2]" ) );
    final String paggingInfo = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.id( "tableTable_info" ) );
    Assert.assertEquals( "Vida Sport, Ltd", customer );
    Assert.assertEquals( "78,155.28", total );
    Assert.assertEquals( "Showing 1 to 10 of 44 entries", paggingInfo );
    //Click next
    this.elemHelper.ClickJS( BaseTest.driver, By.xpath( "//div[@id='tableTable_paginate']/a[2]" ) );

    this.elemHelper.WaitForTextPresence( BaseTest.driver, By.id( "tableTable_info" ), "Showing 11 to 20 of 44 entries" );
    //Check next page
    customer = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[5]/td" ) );
    total = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[1]/td[2]" ) );
    Assert.assertEquals( "Marseille Mini Autos", customer );
    Assert.assertEquals( "61,072.54", total );

    /*
     * ## Step 2
     */
    // Go to the end
    this.elemHelper.ClickJS( BaseTest.driver, By.xpath( "//div[@id='tableTable_paginate']/a[2]" ) );
    this.elemHelper.WaitForTextPresence( BaseTest.driver, By.id( "tableTable_info" ), "Showing 21 to 30 of 44 entries" );
    this.elemHelper.ClickJS( BaseTest.driver, By.xpath( "//div[@id='tableTable_paginate']/a[2]" ) );
    this.elemHelper.WaitForTextPresence( BaseTest.driver, By.id( "tableTable_info" ), "Showing 31 to 40 of 44 entries" );
    this.elemHelper.ClickJS( BaseTest.driver, By.xpath( "//div[@id='tableTable_paginate']/a[2]" ) );
    this.elemHelper.WaitForTextPresence( BaseTest.driver, By.id( "tableTable_info" ), "Showing 41 to 44 of 44 entries" );
    customer = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[2]/td" ) );
    total = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[3]/td[2]" ) );
    Assert.assertEquals( "Frau da Collezione", customer );
    Assert.assertEquals( "4,128.96", total );

    /*
     * ## Step 3
     */
    //Click in preview page
    this.elemHelper.ClickJS( BaseTest.driver, By.xpath( "//div[@id='tableTable_paginate']/a[1]" ) );
    this.elemHelper.WaitForTextPresence( BaseTest.driver, By.id( "tableTable_info" ), "Showing 31 to 40 of 44 entries" );
    customer = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[5]/td" ) );
    total = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[1]/td[2]" ) );
    Assert.assertEquals( "CAF Imports", customer );
    Assert.assertEquals( "20,743.56", total );

    /*
     * ## Step 4
     */
    //Click in preview page
    this.elemHelper.ClickJS( BaseTest.driver, By.xpath( "//div[@id='tableTable_paginate']/a[1]" ) );
    this.elemHelper.WaitForTextPresence( BaseTest.driver, By.id( "tableTable_info" ), "Showing 21 to 30 of 44 entries" );
    customer = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[5]/td" ) );
    total = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[1]/td[2]" ) );
    Assert.assertEquals( "Amica Models & Co.", customer );
    Assert.assertEquals( "36,212.68", total );

    /*
     * ## Step 5
     */
    // Go to next
    this.elemHelper.ClickJS( BaseTest.driver, By.xpath( "//div[@id='tableTable_paginate']/a[2]" ) );
    this.elemHelper.WaitForTextPresence( BaseTest.driver, By.id( "tableTable_info" ), "Showing 31 to 40 of 44 entries" );
    customer = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[5]/td" ) );
    total = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[1]/td[2]" ) );
    Assert.assertEquals( "CAF Imports", customer );
    Assert.assertEquals( "20,743.56", total );
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Sorting
   *
   * Description:
   *    The test case pretends to validate sorting functionality.
   *
   * Steps:
   *    1. Sort Asc by Customer
   *    2. Sort Desc by Total
   */
  @Test
  public void tc3_Sorting_UserCanSortByCustomerAndTotal() {
    this.log.info( "tc3_Sorting_UserCanSortByCustomerAndTotal" );
    String customer = "";
    String total = "";

    /*
     * ## Step 1
     */
    this.elemHelper.ClickJS( BaseTest.driver, By.xpath( "//table[@id='tableTable']/thead/tr/th" ) );
    this.elemHelper.WaitForTextPresence( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[1]/td[2]" ), "20,743.56" );
    //Check the contents
    customer = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[5]/td" ) );
    total = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[1]/td[2]" ) );
    Assert.assertEquals( "Auto Canal+ Petit", customer );
    Assert.assertEquals( "20,743.56", total );
    // Go to next page and check the contents
    this.elemHelper.ClickJS( BaseTest.driver, By.xpath( "//div[@id='tableTable_paginate']/a[2]" ) );
    this.elemHelper.WaitForTextPresence( BaseTest.driver, By.id( "tableTable_info" ), "Showing 11 to 20 of 44 entries" );
    customer = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[5]/td" ) );
    total = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[1]/td[2]" ) );
    Assert.assertEquals( "Enaco Distributors", customer );
    Assert.assertEquals( "25,239.36", total );

    /*
     * ## Step 2
     */
    this.elemHelper.ClickJS( BaseTest.driver, By.xpath( "//table[@id='tableTable']/thead/tr/th[2]" ) );
    this.elemHelper.WaitForTextPresence( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[1]/td[2]" ), "3,508.80" );
    this.elemHelper.ClickJS( BaseTest.driver, By.xpath( "//table[@id='tableTable']/thead/tr/th[2]" ) );
    this.elemHelper.WaitForTextPresence( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[1]/td[2]" ), "409,484.24" );
    //Check the contents
    customer = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[5]/td" ) );
    total = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[1]/td[2]" ) );
    Assert.assertEquals( "Salzburg Collectables", customer );
    Assert.assertEquals( "409,484.24", total );
    // Go to next page and check the contents
    this.elemHelper.ClickJS( BaseTest.driver, By.xpath( "//div[@id='tableTable_paginate']/a[2]" ) );
    this.elemHelper.WaitForTextPresence( BaseTest.driver, By.id( "tableTable_info" ), "Showing 11 to 20 of 44 entries" );
    customer = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[5]/td" ) );
    total = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[1]/td[2]" ) );
    Assert.assertEquals( "Marseille Mini Autos", customer );
    Assert.assertEquals( "61,072.54", total );
  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name:
   *    Table update
   *
   * Description:
   *    The test case pretends to validate the update of table when we select
   *    data from chart.
   *
   * Steps:
   *    1. Enable/Disable series
   *    2. Select a data and navigate between pages
   */
  @Test
  public void tc4_UpdateTableByClickingChart_TableUpdatedForSpecificData() {
    this.log.info( "tc4_UpdateTableByClickingChart_TableUpdatedForSpecificData" );

    /*
     * ## Step 1
     */
    this.elemHelper.FocusElement( driver, By.id( "chart" ) );
    final String sampleChartTitle = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[contains(@id,'chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='text']" ) );
    Assert.assertEquals( "Sales by territory and market", sampleChartTitle );
    //disable APAC
    this.elemHelper.ClickJS( BaseTest.driver, By.xpath( "//div[contains(@id,'chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']" ) );
    this.elemHelper.WaitForAttributeValue( BaseTest.driver, By.xpath( "//div[contains(@id,'chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']" ), "fill", "rgb(150,150,150)" );
    //disable/enable EMEA
    this.elemHelper.ClickJS( BaseTest.driver, By.xpath( "//div[contains(@id,'chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='text']" ) );
    this.elemHelper.WaitForAttributeValue( BaseTest.driver, By.xpath( "//div[contains(@id,'chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='text']" ), "fill", "rgb(150,150,150)" );
    this.elemHelper.ClickJS( BaseTest.driver, By.xpath( "//div[contains(@id,'chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='text']" ) );
    this.elemHelper.WaitForAttributeValue( BaseTest.driver, By.xpath( "//div[contains(@id,'chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='text']" ), "fill", "rgb(0,0,0)" );
    //disable Japan
    this.elemHelper.ClickJS( BaseTest.driver, By.xpath( "//div[contains(@id,'chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g'][2]/*[local-name()='text']" ) );
    this.elemHelper.WaitForAttributeValue( BaseTest.driver, By.xpath( "//div[contains(@id,'chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g'][2]/*[local-name()='text']" ), "fill", "rgb(150,150,150)" );
    //disable NA
    this.elemHelper.ClickJS( BaseTest.driver, By.xpath( " //div[contains(@id,'chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][4]/*[local-name()='g'][2]/*[local-name()='text']" ) );
    this.elemHelper.WaitForAttributeValue( BaseTest.driver, By.xpath( " //div[contains(@id,'chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][4]/*[local-name()='g'][2]/*[local-name()='text']" ), "fill", "rgb(150,150,150)" );

    final String attributeValueApac = this.elemHelper.GetAttribute( BaseTest.driver, By.xpath( "//div[contains(@id,'chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']" ), "fill" );
    final String attributeValueEMEA = this.elemHelper.GetAttribute( BaseTest.driver, By.xpath( "//div[contains(@id,'chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='text']" ), "fill" );
    final String attributeValueJapan = this.elemHelper.GetAttribute( BaseTest.driver, By.xpath( "//div[contains(@id,'chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g'][2]/*[local-name()='text']" ), "fill" );
    final String attributeValueNA = this.elemHelper.GetAttribute( BaseTest.driver, By.xpath( " //div[contains(@id,'chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][4]/*[local-name()='g'][2]/*[local-name()='text']" ), "fill" );
    Assert.assertEquals( "rgb(150,150,150)", attributeValueApac );
    Assert.assertEquals( "rgb(0,0,0)", attributeValueEMEA );
    Assert.assertEquals( "rgb(150,150,150)", attributeValueJapan );
    Assert.assertEquals( "rgb(150,150,150)", attributeValueNA );

    /*
     * ## Step 2
     */
    this.elemHelper.MouseOverElementAndClick( BaseTest.driver, By.xpath( "//div[contains(@id,'chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]//..//*[local-name()='rect'][2]" ) );
    //Wait for page render
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.cssSelector( "div.blockUI.blockOverlay" ), 3 );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    //check contents
    this.elemHelper.WaitForTextPresence( BaseTest.driver, By.id( "tableTable_info" ), "Showing 1 to 10 of 21 entries" );
    String paggingInfo = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.id( "tableTable_info" ) );
    String customer1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[2]/td" ) );
    String total1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[1]/td[2]" ) );
    String customer2 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[5]/td" ) );
    String total2 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[7]/td[2]" ) );
    Assert.assertEquals( "Daedalus Designs Imports", customer1 );
    Assert.assertEquals( "63,753.07", total1 );
    Assert.assertEquals( "UK Collectables, Ltd.", customer2 );
    Assert.assertEquals( "30,524.29", total2 );
    Assert.assertEquals( "Showing 1 to 10 of 21 entries", paggingInfo );
    //Next page
    this.elemHelper.ClickJS( BaseTest.driver, By.xpath( "//div[@id='tableTable_paginate']/a[2]" ) );
    this.elemHelper.WaitForTextPresence( BaseTest.driver, By.id( "tableTable_info" ), "Showing 11 to 20 of 21 entries" );
    paggingInfo = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.id( "tableTable_info" ) );
    customer1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[2]/td" ) );
    total1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[1]/td[2]" ) );
    customer2 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[5]/td" ) );
    total2 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[7]/td[2]" ) );
    Assert.assertEquals( "Suominen Souveniers", customer1 );
    Assert.assertEquals( "21,244.34", total1 );
    Assert.assertEquals( "L'ordine Souveniers", customer2 );
    Assert.assertEquals( "5,442.80", total2 );
    Assert.assertEquals( "Showing 11 to 20 of 21 entries", paggingInfo );
    //Next page
    this.elemHelper.ClickJS( BaseTest.driver, By.xpath( "//div[@id='tableTable_paginate']/a[2]" ) );
    this.elemHelper.WaitForTextPresence( BaseTest.driver, By.id( "tableTable_info" ), "Showing 21 to 21 of 21 entries" );
    paggingInfo = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.id( "tableTable_info" ) );
    customer1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[1]/td" ) );
    total1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//table[@id='tableTable']/tbody/tr[1]/td[2]" ) );
    Assert.assertEquals( "La Corne D'abondance, Co.", customer1 );
    Assert.assertEquals( "2,173.60", total1 );
    Assert.assertEquals( "Showing 21 to 21 of 21 entries", paggingInfo );
  }
}
