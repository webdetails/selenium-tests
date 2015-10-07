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
package com.pentaho.ctools.cde;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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
  //Log instance
  private final Logger log = LogManager.getLogger( SampleDashboard.class );

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Open Sample Page
   */
  @Test
  public void tc0_OpenSamplePage_Display() {
    //Go to the CDE Sample Dashboard web page.
    driver.get( PageUrl.SAMPLE_DASHBOARD );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Page Content
   * Description:
   *    The test case pretends to validate the display data in CDE Sample
   *    Dashboard is according with expected behavior.
   * Steps:
   *    1. Check for display text
   */
  @Test
  public void tc1_PageContent_ContentDisplayedAsExpected() {
    this.log.info( "tc1_PageContent_CachedQueries" );

    /*
     * ## Step 1
     */
    //Check title
    String pageTitle = this.elemHelper.WaitForTitle( driver, "CDE Sample Dashboard" );
    assertEquals( "CDE Sample Dashboard", pageTitle );

    //Check subtitle
    String textSubTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='body']/div[2]/div" ) );
    assertEquals( "How to start", textSubTitle );

    //Check a paragraph
    String textSomeParag = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='body']/div[2]/div[2]/p[2]" ) );
    assertEquals( "Totally extensible, virtually everything is possible, and CDE doesn't stop any of the advanced tricks supported by CDF.", textSomeParag );

    //Check the last paragraph
    String textLastParg = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='body']/div[2]/div[2]/p[10]" ) );
    assertEquals( "Enjoy", textLastParg );

    //Check the signature
    String textAuthor = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='body']/div[2]/div[3]" ) );
    assertEquals( "Pedro Alves, webdetails", textAuthor );

    //Check we have a chart
    WebElement elemChart = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='chart']/div//*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']" ) );
    assertNotNull( elemChart );

    //Check we have a table
    WebElement elemTable = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='tableTable']/tbody/tr/td" ) );
    assertNotNull( elemTable );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Paging
   * Description:
   *    The test case pretends to validate paging functionality.
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
    String customer = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[2]/td" ) );
    String total = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[7]/td[2]" ) );
    String paggingInfo = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "tableTable_info" ) );
    assertEquals( "Vida Sport, Ltd", customer );
    assertEquals( "78,155.28", total );
    assertEquals( "Showing 1 to 10 of 44 entries", paggingInfo );
    //Click next
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='tableTable_paginate']/a[2]" ) );

    this.elemHelper.WaitForTextPresence( driver, By.id( "tableTable_info" ), "Showing 11 to 20 of 44 entries" );
    //Check next page
    customer = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[5]/td" ) );
    total = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[1]/td[2]" ) );
    assertEquals( "Marseille Mini Autos", customer );
    assertEquals( "61,072.54", total );

    /*
     * ## Step 2
     */
    //Go to the end
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='tableTable_paginate']/a[2]" ) );
    this.elemHelper.WaitForTextPresence( driver, By.id( "tableTable_info" ), "Showing 21 to 30 of 44 entries" );
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='tableTable_paginate']/a[2]" ) );
    this.elemHelper.WaitForTextPresence( driver, By.id( "tableTable_info" ), "Showing 31 to 40 of 44 entries" );
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='tableTable_paginate']/a[2]" ) );
    this.elemHelper.WaitForTextPresence( driver, By.id( "tableTable_info" ), "Showing 41 to 44 of 44 entries" );
    customer = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[2]/td" ) );
    total = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[3]/td[2]" ) );
    assertEquals( "Frau da Collezione", customer );
    assertEquals( "4,128.96", total );

    /*
     * ## Step 3
     */
    //Click in preview page
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='tableTable_paginate']/a[1]" ) );
    this.elemHelper.WaitForTextPresence( driver, By.id( "tableTable_info" ), "Showing 31 to 40 of 44 entries" );
    customer = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[5]/td" ) );
    total = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[1]/td[2]" ) );
    assertEquals( "CAF Imports", customer );
    assertEquals( "20,743.56", total );

    /*
     * ## Step 4
     */
    //Click in preview page
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='tableTable_paginate']/a[1]" ) );
    this.elemHelper.WaitForTextPresence( driver, By.id( "tableTable_info" ), "Showing 21 to 30 of 44 entries" );
    customer = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[5]/td" ) );
    total = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[1]/td[2]" ) );
    assertEquals( "Amica Models & Co.", customer );
    assertEquals( "36,212.68", total );

    /*
     * ## Step 5
     */
    //Go to next
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='tableTable_paginate']/a[2]" ) );
    this.elemHelper.WaitForTextPresence( driver, By.id( "tableTable_info" ), "Showing 31 to 40 of 44 entries" );
    customer = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[5]/td" ) );
    total = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[1]/td[2]" ) );
    assertEquals( "CAF Imports", customer );
    assertEquals( "20,743.56", total );
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Sorting
   * Description:
   *    The test case pretends to validate sorting functionality.
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
    this.elemHelper.ClickJS( driver, By.xpath( "//table[@id='tableTable']/thead/tr/th" ) );
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[1]/td[2]" ), "20,743.56" );
    //Check the contents
    customer = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[5]/td" ) );
    total = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[1]/td[2]" ) );
    assertEquals( "Auto Canal+ Petit", customer );
    assertEquals( "20,743.56", total );
    //Go to next page and check the contents
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='tableTable_paginate']/a[2]" ) );
    this.elemHelper.WaitForTextPresence( driver, By.id( "tableTable_info" ), "Showing 11 to 20 of 44 entries" );
    customer = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[5]/td" ) );
    total = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[1]/td[2]" ) );
    assertEquals( "Enaco Distributors", customer );
    assertEquals( "25,239.36", total );

    /*
     * ## Step 2
     */
    this.elemHelper.ClickJS( driver, By.xpath( "//table[@id='tableTable']/thead/tr/th[2]" ) );
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[1]/td[2]" ), "3,508.80" );
    this.elemHelper.ClickJS( driver, By.xpath( "//table[@id='tableTable']/thead/tr/th[2]" ) );
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[1]/td[2]" ), "409,484.24" );
    //Check the contents
    customer = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[5]/td" ) );
    total = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[1]/td[2]" ) );
    assertEquals( "Salzburg Collectables", customer );
    assertEquals( "409,484.24", total );
    //Go to next page and check the contents
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='tableTable_paginate']/a[2]" ) );
    this.elemHelper.WaitForTextPresence( driver, By.id( "tableTable_info" ), "Showing 11 to 20 of 44 entries" );
    customer = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[5]/td" ) );
    total = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[1]/td[2]" ) );
    assertEquals( "Marseille Mini Autos", customer );
    assertEquals( "61,072.54", total );
  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name:
   *    Table update
   * Description:
   *    The test case pretends to validate the update of table when we select
   *    data from chart.
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
    String sampleChartTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[contains(@id,'chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='text']" ) );
    assertEquals( "Sales by territory and market", sampleChartTitle );
    //disable APAC
    this.elemHelper.ClickJS( driver, By.xpath( "//div[contains(@id,'chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']" ) );
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//div[contains(@id,'chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']" ), "fill", "rgb(150,150,150)" );
    //disable/enable EMEA
    this.elemHelper.ClickJS( driver, By.xpath( "//div[contains(@id,'chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='text']" ) );
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//div[contains(@id,'chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='text']" ), "fill", "rgb(150,150,150)" );
    this.elemHelper.ClickJS( driver, By.xpath( "//div[contains(@id,'chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='text']" ) );
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//div[contains(@id,'chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='text']" ), "fill", "rgb(0,0,0)" );
    //disable Japan
    this.elemHelper.ClickJS( driver, By.xpath( "//div[contains(@id,'chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g'][2]/*[local-name()='text']" ) );
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//div[contains(@id,'chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g'][2]/*[local-name()='text']" ), "fill", "rgb(150,150,150)" );
    //disable NA
    this.elemHelper.ClickJS( driver, By.xpath( " //div[contains(@id,'chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][4]/*[local-name()='g'][2]/*[local-name()='text']" ) );
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//div[contains(@id,'chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g'][2]/*[local-name()='text']" ), "fill", "rgb(150,150,150)" );

    String attributeValueApac = this.elemHelper.GetAttribute( driver, By.xpath( "//div[contains(@id,'chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']" ), "fill" );
    String attributeValueEMEA = this.elemHelper.GetAttribute( driver, By.xpath( "//div[contains(@id,'chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g'][2]/*[local-name()='text']" ), "fill" );
    String attributeValueJapan = this.elemHelper.GetAttribute( driver, By.xpath( "//div[contains(@id,'chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g'][2]/*[local-name()='text']" ), "fill" );
    String attributeValueNA = this.elemHelper.GetAttribute( driver, By.xpath( " //div[contains(@id,'chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][4]/*[local-name()='g'][2]/*[local-name()='text']" ), "fill" );
    assertEquals( "rgb(150,150,150)", attributeValueApac );
    assertEquals( "rgb(0,0,0)", attributeValueEMEA );
    assertEquals( "rgb(150,150,150)", attributeValueJapan );
    assertEquals( "rgb(150,150,150)", attributeValueNA );

    /*
     * ## Step 2
     */
    this.elemHelper.MouseOverElementAndClick( driver, By.xpath( "//div[contains(@id,'chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g'][2]//..//*[local-name()='rect'][2]" ) );
    //Wait for page render
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 3 );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    //check contents
    this.elemHelper.WaitForTextPresence( driver, By.id( "tableTable_info" ), "Showing 1 to 10 of 21 entries" );
    String paggingInfo = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "tableTable_info" ) );
    String customer1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[2]/td" ) );
    String total1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[1]/td[2]" ) );
    String customer2 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[5]/td" ) );
    String total2 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[7]/td[2]" ) );
    assertEquals( "Daedalus Designs Imports", customer1 );
    assertEquals( "63,753.07", total1 );
    assertEquals( "UK Collectables, Ltd.", customer2 );
    assertEquals( "30,524.29", total2 );
    assertEquals( "Showing 1 to 10 of 21 entries", paggingInfo );
    //Next page
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='tableTable_paginate']/a[2]" ) );
    this.elemHelper.WaitForTextPresence( driver, By.id( "tableTable_info" ), "Showing 11 to 20 of 21 entries" );
    paggingInfo = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "tableTable_info" ) );
    customer1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[2]/td" ) );
    total1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[1]/td[2]" ) );
    customer2 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[5]/td" ) );
    total2 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[7]/td[2]" ) );
    assertEquals( "Suominen Souveniers", customer1 );
    assertEquals( "21,244.34", total1 );
    assertEquals( "L'ordine Souveniers", customer2 );
    assertEquals( "5,442.80", total2 );
    assertEquals( "Showing 11 to 20 of 21 entries", paggingInfo );
    //Next page
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='tableTable_paginate']/a[2]" ) );
    this.elemHelper.WaitForTextPresence( driver, By.id( "tableTable_info" ), "Showing 21 to 21 of 21 entries" );
    paggingInfo = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "tableTable_info" ) );
    customer1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[1]/td" ) );
    total1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tableTable']/tbody/tr[1]/td[2]" ) );
    assertEquals( "La Corne D'abondance, Co.", customer1 );
    assertEquals( "2,173.60", total1 );
    assertEquals( "Showing 21 to 21 of 21 entries", paggingInfo );
  }
}
