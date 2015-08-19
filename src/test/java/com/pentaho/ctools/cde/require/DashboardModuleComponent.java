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
package com.pentaho.ctools.cde.require;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.BaseTest;
import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;

/**
 * Smoketest for the Dashboard Module Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class DashboardModuleComponent extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  //Log instance
  private final Logger log = LogManager.getLogger( DashboardModuleComponent.class );

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Open Sample Page
   */
  @Test
  public void tc0_OpenSamplePage_Display() {
    //Go to the CDE Sample Dashboard web page.
    driver.get( PageUrl.DASHBOARD_MODULE_COMPONENT_REQUIRE );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Page Content
   * Description:
   *    The test case pretends to validate the display data in Dashboard Component sample is according with expected behavior.
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
    String pageTitle = this.elemHelper.WaitForTitle( driver, "Community Dashboard Framework" );
    assertEquals( "Community Dashboard Framework", pageTitle );

    //Check the signature1
    String textAuthor1 = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "cde_sample1_alias1_signature" ) );
    assertEquals( "Pedro Alves, webdetails", textAuthor1 );

    //Check the signature2
    String textAuthor2 = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "cde_sample1_alias2_signature" ) );
    assertEquals( "Pedro Alves, webdetails", textAuthor2 );

    //Check the signature3
    String textAuthor3 = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "cde_sample1_sampleDash3_signature" ) );
    assertEquals( "Pedro Alves, webdetails", textAuthor3 );

    //Check the chart1
    String elemChart1 = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "//div[@id='cde_sample1_alias1_chart']/div//*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']" ) );
    assertNotNull( elemChart1 );

    //Check the chart2
    String elemChart2 = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "//div[@id='cde_sample1_alias2_chart']/div//*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']" ) );
    assertNotNull( elemChart2 );

    //Check the chart3
    WebElement elemChart3 = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='cde_sample1_sampleDash3_chart']/div//*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']" ) );
    assertNotNull( elemChart3 );

    //Check the table1
    WebElement elemTable1 = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='cde_sample1_alias1_tableTable']/tbody/tr/td" ) );
    assertNotNull( elemTable1 );

    //Check the table2
    WebElement elemTable2 = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='cde_sample1_alias2_tableTable']/tbody/tr/td" ) );
    assertNotNull( elemTable2 );

    //Check the table3
    WebElement elemTable3 = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='cde_sample1_sampleDash3_tableTable']/tbody/tr/td" ) );
    assertNotNull( elemTable3 );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Paging
   * Description:
   *    The test case pretends to validate paging functionality.
   * Steps:
   *    1. Press next on first table
   *    2. Press next on second table
   *    3. Press next on third table
   *    
   */
  @Test
  public void tc2_Paging_UserCanPaging() {
    this.log.info( "tc2_Paging_UserCanPaging" );

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
    customer = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_alias2_tableTable']/tbody/tr[2]/td" ) );
    total = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_alias2_tableTable']/tbody/tr[7]/td[2]" ) );
    paggingInfo = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "cde_sample1_alias2_tableTable_info" ) );
    assertEquals( "Vida Sport, Ltd", customer );
    assertEquals( "78,155.28", total );
    assertEquals( "Showing 1 to 10 of 44 entries", paggingInfo );
    //Click next
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='cde_sample1_alias2_tableTable_paginate']/a[2]" ) );
    this.elemHelper.WaitForTextPresence( driver, By.id( "cde_sample1_alias2_tableTable_info" ), "Showing 11 to 20 of 44 entries" );
    //Check next page on first dashboard
    customer = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_alias2_tableTable']/tbody/tr[5]/td" ) );
    total = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_alias2_tableTable']/tbody/tr[1]/td[2]" ) );
    assertEquals( "Marseille Mini Autos", customer );
    assertEquals( "61,072.54", total );

    /*
     * ## Step 3
     */
    customer = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_sampleDash3_tableTable']/tbody/tr[2]/td" ) );
    total = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_sampleDash3_tableTable']/tbody/tr[7]/td[2]" ) );
    paggingInfo = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "cde_sample1_sampleDash3_tableTable_info" ) );
    assertEquals( "Vida Sport, Ltd", customer );
    assertEquals( "78,155.28", total );
    assertEquals( "Showing 1 to 10 of 44 entries", paggingInfo );
    //Click next
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='cde_sample1_sampleDash3_tableTable_paginate']/a[2]" ) );
    this.elemHelper.WaitForTextPresence( driver, By.id( "cde_sample1_sampleDash3_tableTable_info" ), "Showing 11 to 20 of 44 entries" );
    //Check next page on first dashboard
    customer = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_sampleDash3_tableTable']/tbody/tr[5]/td" ) );
    total = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_sampleDash3_tableTable']/tbody/tr[1]/td[2]" ) );
    assertEquals( "Marseille Mini Autos", customer );
    assertEquals( "61,072.54", total );

  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Click chart updates table
   * Description:
   *    The test case pretends to validate the update of table when we click the corresponding
   *    chart
   * Steps:
   *    1. Enable/Disable series on first chart, click chart and navigate
   *    2. Enable/Disable series on second chart, click chart and navigate
   *    3. Enable/Disable series on third chart, click chart and navigate
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
     * ## Step 2
     */
    sampleChartTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[contains(@id,'cde_sample1_alias2_chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='text']" ) );
    assertEquals( "Sales by territory and market", sampleChartTitle );
    //disable/enable APAC on chart 1
    this.elemHelper.ClickJS( driver, By.xpath( "//div[contains(@id,'cde_sample1_alias2_chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']" ) );
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//div[contains(@id,'cde_sample1_alias2_chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']" ), "fill", "rgb(150,150,150)" );
    attributeValueApac = this.elemHelper.GetAttribute( driver, By.xpath( "//div[contains(@id,'cde_sample1_alias2_chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']" ), "fill" );
    assertEquals( "rgb(150,150,150)", attributeValueApac );
    this.elemHelper.ClickJS( driver, By.xpath( "//div[contains(@id,'cde_sample1_alias2_chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']" ) );
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//div[contains(@id,'cde_sample1_alias2_chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']" ), "fill", "rgb(0,0,0)" );
    //Click NA/Vintage Cars on first chart
    this.elemHelper.MouseOverElementAndClick( driver, By.xpath( "//div[contains(@id,'cde_sample1_alias2_chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]//*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='rect'][7]" ) );
    //Wait for page render
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 3 );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    //check contents
    customer1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_alias2_tableTable']/tbody/tr[2]/td" ) );
    total1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_alias2_tableTable']/tbody/tr[1]/td[2]" ) );
    assertEquals( "The Sharp Gifts Warehouse", customer1 );
    assertEquals( "168,967.80", total1 );
    //Next page
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='cde_sample1_alias2_tableTable_paginate']/a[2]" ) );
    this.elemHelper.WaitForTextPresence( driver, By.id( "cde_sample1_alias2_tableTable_info" ), "Showing 11 to 20 of 21 entries" );
    customer1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_alias2_tableTable']/tbody/tr[2]/td" ) );
    total1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_alias2_tableTable']/tbody/tr[1]/td[2]" ) );
    assertEquals( "Signal Gift Stores", customer1 );
    assertEquals( "23,203.62", total1 );

    /*
     * ## Step 3
     */
    sampleChartTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[contains(@id,'cde_sample1_sampleDash3_chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='text']" ) );
    assertEquals( "Sales by territory and market", sampleChartTitle );
    //disable/enable APAC on chart 1
    this.elemHelper.ClickJS( driver, By.xpath( "//div[contains(@id,'cde_sample1_sampleDash3_chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']" ) );
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//div[contains(@id,'cde_sample1_sampleDash3_chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']" ), "fill", "rgb(150,150,150)" );
    attributeValueApac = this.elemHelper.GetAttribute( driver, By.xpath( "//div[contains(@id,'cde_sample1_sampleDash3_chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']" ), "fill" );
    assertEquals( "rgb(150,150,150)", attributeValueApac );
    this.elemHelper.ClickJS( driver, By.xpath( "//div[contains(@id,'cde_sample1_sampleDash3_chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']" ) );
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//div[contains(@id,'cde_sample1_sampleDash3_chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text']" ), "fill", "rgb(0,0,0)" );
    //Click NA/Vintage Cars on first chart
    this.elemHelper.MouseOverElementAndClick( driver, By.xpath( "//div[contains(@id,'cde_sample1_sampleDash3_chart')]/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]//*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='rect'][7]" ) );
    //Wait for page render
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 3 );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    //check contents
    customer1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_sampleDash3_tableTable']/tbody/tr[2]/td" ) );
    total1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_sampleDash3_tableTable']/tbody/tr[1]/td[2]" ) );
    assertEquals( "The Sharp Gifts Warehouse", customer1 );
    assertEquals( "168,967.80", total1 );
    //Next page
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='cde_sample1_sampleDash3_tableTable_paginate']/a[2]" ) );
    this.elemHelper.WaitForTextPresence( driver, By.id( "cde_sample1_sampleDash3_tableTable_info" ), "Showing 11 to 20 of 21 entries" );
    customer1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_sampleDash3_tableTable']/tbody/tr[2]/td" ) );
    total1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='cde_sample1_sampleDash3_tableTable']/tbody/tr[1]/td[2]" ) );
    assertEquals( "Signal Gift Stores", customer1 );
    assertEquals( "23,203.62", total1 );
  }

}
