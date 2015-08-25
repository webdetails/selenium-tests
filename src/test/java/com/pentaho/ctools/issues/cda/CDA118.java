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
package com.pentaho.ctools.issues.cda;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.BaseTest;
import com.pentaho.ctools.utils.ElementHelper;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDA-123
 * - http://jira.pentaho.com/browse/CDA-147
 * - http://jira.pentaho.com/browse/CDA-145
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1122
 * - http://jira.pentaho.com/browse/QUALITY-1140
 * - http://jira.pentaho.com/browse/QUALITY-1129
 *  
 * NOTE
 * To test this script it is required to have CDA plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDA118 extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDA118.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Asserting functionalities on query fail
   * Description:
   *    CDA-123: Feedback given when query fails
   *    CDA-147: Can change parameter when query fails and refresh query
   *    CDA-145: Adding outputColumnName to the URL filters the query results
   *
   * Steps:
   *    1. Select "Sql Query on SampleData - Jndi" on "dataAccessSelector" and assert error message
   *    2. Change value on salesParam box to 10000 and refresh query
   *    3. Wait for and assert elements and text on page
   *    4. Open the query with outpuColumnName in the URL and assert results were filtered
   *
   */
  @Test
  public void tc01_CdaFileViewer_QueryFail() {
    this.log.info( "tc01_CdaFileViewer_QueryFail" );

    /*
     * ## Step 1
     */
    //Open Issue Sample
    driver.get( baseUrl + "plugin/cda/api/previewQuery?path=/public/Issues/CDA/CDA-118/sql-jndi.cda" );

    //wait for invisibility of waiting pop-up
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

    //Wait for data selector and select available option
    WebElement dataAccess = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "dataAccessSelector" ) );
    assertNotNull( dataAccess );
    Select select = new Select( this.elemHelper.FindElement( driver, By.id( "dataAccessSelector" ) ) );
    select.selectByVisibleText( "Sql Query on SampleData - Jndi" );

    //wait to render page
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Assert error message
    String errorMessage = this.elemHelper.WaitForElementPresentGetText( driver, By.cssSelector( "span.error-status" ) );
    assertEquals( "Error Executing Query", errorMessage );

    /*
     * ## Step 2
     */
    WebElement salesParam = this.elemHelper.FindElement( driver, By.id( "sales" ) );
    assertNotNull( salesParam );
    salesParam.clear();
    salesParam.sendKeys( "10000" );
    WebElement refreshButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='button']" ) );
    assertNotNull( refreshButton );
    refreshButton.click();

    /*
     * ## Step 3
     */
    //wait to render page
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Assert elements on page
    WebElement cacheButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='cachethis']" ) );
    assertNotNull( cacheButton );
    WebElement queryButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='queryUrl']" ) );
    assertNotNull( queryButton );

    //Check the presented contains
    WebElement newSalesParam = this.elemHelper.FindElement( driver, By.id( "sales" ) );
    assertEquals( "10000", newSalesParam.getAttribute( "value" ) );

    //Check text on table
    String columnOneRowOne = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='contents']/tbody/tr/td" ) );
    String columnTwoRowOne = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='contents']/tbody/tr/td[2]" ) );
    assertEquals( "Cancelled", columnOneRowOne );
    assertEquals( "2004", columnTwoRowOne );

    /*
     * ## Step 4
     */
    //Open Sample with outpuColumnName in the URL
    driver.get( baseUrl + "plugin/cda/api/doQuery?paramsales=10000&paramorderDate=2004-03-01&path=%2Fpublic%2FIssues%2FCDA%2FCDA-118%2Fsql-jndi.cda&dataAccessId=1&outputIndexId=1&&outputColumnName=STATUS" );

    //Assert query result
    String result = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//body" ) );
    assertEquals( "{\"queryInfo\":{\"totalRows\":\"6\"},\"resultset\":[[\"Shipped\"],[\"Cancelled\"],[\"Shipped\"],[\"Disputed\"],[\"On Hold\"],[\"In Process\"]],\"metadata\":[{\"colIndex\":0,\"colType\":\"String\",\"colName\":\"STATUS\"}]}", result );
  }

}
