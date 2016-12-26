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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * Smoketest for the Dashboard Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class DashboardComponent extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( DashboardComponent.class );

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
    this.elemHelper.Get( driver, PageUrl.DASHBOARD_COMPONENT_REQUIRE );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Page Content
   *
   * Description:
   *    The test case pretends to validate the display data in Dashboard
   *    Component sample is according with expected behavior.
   *
   * Steps:
   *    1. Check for displayed text
   */
  @Test
  public void tc1_PageContent_ContentDisplayedAsExpected() {
    this.log.info( "tc1_PageContent_ContentDisplayedAsExpected" );

    /*
     * ## Step 1
     */
    //Check title
    String pageTitle = this.elemHelper.WaitForTitle( driver, "Dashboard Component" );
    assertEquals( "Dashboard Component", pageTitle );

    //Check the signature1
    String textAuthor1 = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "cde_sample1_alias1_signature" ) );
    assertEquals( "Pedro Alves, webdetails", textAuthor1 );

    //Check the signature2
    String textAuthor2 = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "cde_sample1_alias2_signature" ) );
    assertEquals( "Pedro Alves, webdetails", textAuthor2 );

    //Check the selector1
    String terrSelect = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "territorySelObj" ) );
    assertNotNull( terrSelect );

    //Check the selector2
    String prodSelect = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "prodLineSelObj" ) );
    assertNotNull( prodSelect );

    //Check we have chart 1
    WebElement elemChart1 = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='cde_sample1_alias1_chart']/div//*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']" ) );
    assertNotNull( elemChart1 );

    //Check we hav chart 2
    WebElement elemChart2 = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='cde_sample1_alias2_chart']/div//*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']" ) );
    assertNotNull( elemChart2 );

    //Check we have a table1
    WebElement elemTable1 = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='cde_sample1_alias1_tableTable']/tbody/tr/td" ) );
    assertNotNull( elemTable1 );

    //Check we have a table2
    WebElement elemTable2 = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='cde_sample1_alias2_tableTable']/tbody/tr/td" ) );
    assertNotNull( elemTable2 );
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
    String customer = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_alias1_tableTable']/tbody/tr[2]/td" ) );
    String total = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_alias1_tableTable']/tbody/tr[7]/td[2]" ) );
    String paggingInfo = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "cde_sample1_alias1_tableTable_info" ) );
    assertEquals( "Vida Sport, Ltd", customer );
    assertEquals( "78,155.28", total );
    assertEquals( "Showing 1 to 10 of 44 entries", paggingInfo );
    //Click next
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='cde_sample1_alias1_tableTable_paginate']/a[2]" ) );
    this.elemHelper.WaitForTextPresence( driver, By.id( "cde_sample1_alias1_tableTable_info" ), "Showing 11 to 20 of 44 entries" );
    //Check next page on first dashboard
    customer = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_alias1_tableTable']/tbody/tr[5]/td" ) );
    total = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_alias1_tableTable']/tbody/tr[1]/td[2]" ) );
    assertEquals( "Marseille Mini Autos", customer );
    assertEquals( "61,072.54", total );

    /*
     * ## Step 2
     */
    // Go to the end of second dashboard
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='cde_sample1_alias2_tableTable_paginate']/a[2]" ) );
    this.elemHelper.WaitForTextPresence( driver, By.id( "cde_sample1_alias2_tableTable_info" ), "Showing 11 to 20 of 44 entries" );
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='cde_sample1_alias2_tableTable_paginate']/a[2]" ) );
    this.elemHelper.WaitForTextPresence( driver, By.id( "cde_sample1_alias2_tableTable_info" ), "Showing 21 to 30 of 44 entries" );
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='cde_sample1_alias2_tableTable_paginate']/a[2]" ) );
    this.elemHelper.WaitForTextPresence( driver, By.id( "cde_sample1_alias2_tableTable_info" ), "Showing 31 to 40 of 44 entries" );
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='cde_sample1_alias2_tableTable_paginate']/a[2]" ) );
    this.elemHelper.WaitForTextPresence( driver, By.id( "cde_sample1_alias2_tableTable_info" ), "Showing 41 to 44 of 44 entries" );
    customer = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_alias2_tableTable']/tbody/tr[2]/td" ) );
    total = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_alias2_tableTable']/tbody/tr[3]/td[2]" ) );
    assertEquals( "Frau da Collezione", customer );
    assertEquals( "4,128.96", total );

  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Table 1 update
   *
   * Description:
   *    The test case pretends to validate the update of table when we select
   *    data from chart or selector.
   *
   * Steps:
   *    1. Enable/Disable series
   *    2. Select a data and navigate between pages
   *    3. Select a territory and navigate between pages
   */
  @Test
  public void tc3_UpdateTableByClickingChart_Table1UpdatedForSpecificData() {
    this.log.info( "tc3_UpdateTableByClickingChart_Table1UpdatedForSpecificData" );

    /*
     * ## Step 1
     */
    String sampleChartTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[contains(@id,'cde_sample1_alias1_chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='text']" ) );
    assertEquals( "Sales by territory and market", sampleChartTitle );
    //disable/enable APAC on chart 1
    this.elemHelper.ClickJS( driver, By.xpath( "//div[contains(@id,'cde_sample1_alias1_chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']" ) );
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//div[contains(@id,'cde_sample1_alias1_chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']" ), "fill", "rgb(150,150,150)" );
    String attributeValueApac = this.elemHelper.GetAttribute( driver, By.xpath( "//div[contains(@id,'cde_sample1_alias1_chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']" ), "fill" );
    assertEquals( "rgb(150,150,150)", attributeValueApac );
    this.elemHelper.ClickJS( driver, By.xpath( "//div[contains(@id,'cde_sample1_alias1_chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']" ) );
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//div[contains(@id,'cde_sample1_alias1_chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']" ), "fill", "rgb(0,0,0)" );

    /*
     * ## Step 2
     */
    //Click NA/Vintage Cars on first chart
    this.elemHelper.MouseOverElementAndClick( driver, By.xpath( "//div[contains(@id,'cde_sample1_alias1_chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]//*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='rect'][7]" ) );
    //Wait for page render
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 3 );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    //check contents
    String customer1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_alias1_tableTable']/tbody/tr[2]/td" ) );
    String total1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_alias1_tableTable']/tbody/tr[1]/td[2]" ) );
    assertEquals( "The Sharp Gifts Warehouse", customer1 );
    assertEquals( "168,967.80", total1 );
    //Next page
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='cde_sample1_alias1_tableTable_paginate']/a[2]" ) );
    this.elemHelper.WaitForTextPresence( driver, By.id( "cde_sample1_alias1_tableTable_info" ), "Showing 11 to 20 of 21 entries" );
    customer1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_alias1_tableTable']/tbody/tr[2]/td" ) );
    total1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_alias1_tableTable']/tbody/tr[1]/td[2]" ) );
    assertEquals( "Signal Gift Stores", customer1 );
    assertEquals( "23,203.62", total1 );

    /*
     * ## Step 3
     */
    //Select APAC on Territory Selector
    Select terrSelect = new Select( this.elemHelper.FindElement( driver, By.xpath( "//div[@id='territorySelObj']/select" ) ) );
    terrSelect.selectByValue( "APAC" );
    //Wait for page render
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 3 );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    //check contents
    customer1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_alias1_tableTable']/tbody/tr[2]/td" ) );
    total1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_alias1_tableTable']/tbody/tr[1]/td[2]" ) );
    assertEquals( "Extreme Desk Decorations, Ltd", customer1 );
    assertEquals( "69,410.95", total1 );

    //Validate other table did not respond to changes on other chart/selector
    String customer = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_alias2_tableTable']/tbody/tr[2]/td" ) );
    String total = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_alias2_tableTable']/tbody/tr[3]/td[2]" ) );
    assertEquals( "Frau da Collezione", customer );
    assertEquals( "4,128.96", total );
  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name:
   *    Table 2 update
   *
   * Description:
   *    The test case pretends to validate the update of table when we select
   *    data from chart or selector.
   *
   * Steps:
   *    1. Enable/Disable series
   *    2. Select a data and navigate between pages
   *    3. Select a territory and navigate between pages
   */
  @Test
  public void tc4_UpdateTableByClickingChart_Table2UpdatedForSpecificData() {
    this.log.info( "tc4_UpdateTableByClickingChart_Table2UpdatedForSpecificData" );

    /*
     * ## Step 1
     */
    //Assert Chart Title
    String sampleChartTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[contains(@id,'cde_sample1_alias2_chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='text']" ) );
    assertEquals( "Sales by territory and market", sampleChartTitle );
    //disable/enable APAC on chart 2
    this.elemHelper.ClickJS( driver, By.xpath( "//div[contains(@id,'cde_sample1_alias2_chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']" ) );
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//div[contains(@id,'cde_sample1_alias2_chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']" ), "fill", "rgb(150,150,150)" );
    String attributeValueApac = this.elemHelper.GetAttribute( driver, By.xpath( "//div[contains(@id,'cde_sample1_alias2_chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']" ), "fill" );
    assertEquals( "rgb(150,150,150)", attributeValueApac );
    this.elemHelper.ClickJS( driver, By.xpath( "//div[contains(@id,'cde_sample1_alias2_chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']" ) );
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//div[contains(@id,'cde_sample1_alias2_chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']" ), "fill", "rgb(0,0,0)" );

    /*
     * ## Step 2
     */
    //Click Japan/Planes on second chart
    this.elemHelper.MouseOverElementAndClick( driver, By.xpath( "//div[contains(@id,'cde_sample1_alias2_chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]//*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='rect'][3]" ) );
    //Wait for page render
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 3 );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    //check contents
    String customer1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_alias2_tableTable']/tbody/tr[2]/td" ) );
    String total1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_alias2_tableTable']/tbody/tr[1]/td[2]" ) );
    assertEquals( "Osaka Souveniers Co.", customer1 );
    assertEquals( "39,649.31", total1 );

    /*
     * ## Step 3
     */
    //Select Trucks and Buses on Product Selector
    Select prodSelect = new Select( this.elemHelper.FindElement( driver, By.xpath( "//div[@id='prodLineSelObj']/select" ) ) );
    prodSelect.selectByValue( "Trucks and Buses" );
    //Wait for page render
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 3 );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    //check contents
    customer1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_alias2_tableTable']/tbody/tr[2]/td" ) );
    total1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_alias2_tableTable']/tbody/tr[1]/td[2]" ) );
    assertEquals( "Tokyo Collectables, Ltd", customer1 );
    assertEquals( "44,498.22", total1 );

    //Validate other table did not respond to changes on other chart/selector
    customer1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_alias1_tableTable']/tbody/tr[2]/td" ) );
    total1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_alias1_tableTable']/tbody/tr[1]/td[2]" ) );
    assertEquals( "Extreme Desk Decorations, Ltd", customer1 );
    assertEquals( "69,410.95", total1 );

  }
}
