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
package com.pentaho.ctools.issues.cdf;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.BaseTest;
import com.pentaho.ctools.utils.ElementHelper;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-328
 * - http://jira.pentaho.com/browse/CDF-357
 * - http://jira.pentaho.com/browse/CDF-548
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-957
 * - http://jira.pentaho.com/browse/QUALITY-945
 * - http://jira.pentaho.com/browse/QUALITY-1149
 *
 * NOTE
 * To test this script it is required to have CDF plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class TableComponent extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( TableComponent.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Table Component tests
   *
   * Description:
   *    CDE-328 - Use more than one parameter for the expand
   *    CDF-357 - Assert search gives back expected results
   *    CDF-548 - Assert no problems with expanding after refresh/change pages
   *
   * Steps:
   *    1. Open created sample search for "class", assert expected result, clear search and assert all is shown
   *    2. Expand first row and assert expansion was successful 
   *    2. Click Refresh button and expand same row asserting expansion was successful
   *    3. Change page and expand second row asserting expansion was successful
   *
   */
  @Test
  public void tc1_CdfTableComponent_TestIssues() {
    this.log.info( "tc1_CdfTableComponent_TestIssues" );

    /*
     * ## Step 1
     */
    //Open Created sample
    driver.get( baseUrl + "api/repos/%3Apublic%3AIssues%3ACDF%3ATableExpandTest%3AtableExpandTest.wcdf/generatedContent" );

    //Wait for loading to finish
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //search for "Mot" and assert shown result
    WebElement searchInput = this.elemHelper.FindElement( driver, By.xpath( "//input[@type='search']" ) );
    assertNotNull( searchInput );
    searchInput.sendKeys( "class" );
    WebElement firstResult = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='tblMainTable']/tbody/tr/td" ) );
    assertNotNull( firstResult );
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='tblMainTable']/tbody/tr/td" ), "Classic Cars" );
    String resultClass = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tblMainTable']/tbody/tr/td" ) );
    assertEquals( "Classic Cars", resultClass );
    assertTrue( this.elemHelper.WaitForElementNotPresent( driver, By.xpath( "//table[@id='tblMainTable']/tbody/tr[2]/td" ) ) );

    //Clear search and assert all results shown
    searchInput = this.elemHelper.FindElement( driver, By.xpath( "//input[@type='search']" ) );
    assertNotNull( searchInput );
    searchInput.sendKeys( Keys.BACK_SPACE );
    searchInput.sendKeys( Keys.BACK_SPACE );
    searchInput.sendKeys( Keys.BACK_SPACE );
    searchInput.sendKeys( Keys.BACK_SPACE );
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='tblMainTable']/tbody/tr/td" ), "Motorcycles" );
    String resultOne = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tblMainTable']/tbody/tr/td" ) );
    assertEquals( "Motorcycles", resultOne );
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='tblMainTable']/tbody/tr[2]/td" ), "Classic Cars" );
    String resultTwo = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tblMainTable']/tbody/tr[2]/td" ) );
    assertEquals( "Classic Cars", resultTwo );
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='tblMainTable']/tbody/tr[3]/td" ), "Trucks and Buses" );
    String resultThree = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tblMainTable']/tbody/tr[3]/td" ) );
    assertEquals( "Trucks and Buses", resultThree );

    /*
     * ## Step 2
     */
    //Click first row and assert expansion was successful
    WebElement firstRow = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='tblMainTable']/tbody/tr/td" ) );
    assertNotNull( firstRow );
    firstRow.click();
    WebElement subTable = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='tblSubTable']/tbody/tr/td" ) );
    assertNotNull( subTable );
    String tableTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tblSubTable']/thead/tr/th/div" ) );
    assertEquals( "PRODUCTNAME", tableTitle );
    String thirdRow = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tblSubTable']/tbody/tr[3]/td" ) );
    assertEquals( "2003 Harley-Davidson Eagle Drag Bike", thirdRow );
    String ninthRow = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tblSubTable']/tbody/tr[9]/td" ) );
    assertEquals( "1982 Ducati 900 Monster", ninthRow );

    /*
     * ## Step 2
     */
    //Click Refresh button
    WebElement refreshButton = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='btnRefresh']/button" ) );
    assertNotNull( refreshButton );
    refreshButton.click();

    //Wait for loading to finish
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Click first row and assert expansion was successful
    firstRow = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='tblMainTable']/tbody/tr/td" ) );
    assertNotNull( firstRow );
    firstRow.click();
    subTable = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='tblSubTable']/tbody/tr/td" ) );
    assertNotNull( subTable );
    tableTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tblSubTable']/thead/tr/th/div" ) );
    assertEquals( "PRODUCTNAME", tableTitle );
    thirdRow = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tblSubTable']/tbody/tr[3]/td" ) );
    assertEquals( "2003 Harley-Davidson Eagle Drag Bike", thirdRow );
    ninthRow = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tblSubTable']/tbody/tr[9]/td" ) );
    assertEquals( "1982 Ducati 900 Monster", ninthRow );

    /*
     * ## Step 3
     */
    //Click Next page button
    WebElement nextPage = this.elemHelper.FindElement( driver, By.id( "tblMainTable_next" ) );
    assertNotNull( nextPage );
    nextPage.click();
    this.elemHelper.WaitForElementNotPresent( driver, By.xpath( "//table[@id='tblSubTable']/tbody/tr/td" ) );

    //Click second row and assert expansion was successful
    WebElement secondRow = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='tblMainTable']/tbody/tr[2]/td" ) );
    assertNotNull( secondRow );
    String secondProduct = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='tblMainTable']/tbody/tr[2]/td" ) ).getText();
    assertEquals( "Planes", secondProduct );
    secondRow.click();
    subTable = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='tblSubTable']/tbody/tr/td" ) );
    assertNotNull( subTable );
    tableTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tblSubTable']/thead/tr/th/div" ) );
    assertEquals( "PRODUCTNAME", tableTitle );
    thirdRow = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tblSubTable']/tbody/tr[3]/td" ) );
    assertEquals( "1928 British Royal Navy Airplane", thirdRow );
    ninthRow = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='tblSubTable']/tbody/tr[9]/td" ) );
    assertEquals( "ATA: B757-300", ninthRow );
  }
}
