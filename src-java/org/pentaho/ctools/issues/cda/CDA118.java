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
package org.pentaho.ctools.issues.cda;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.DirectoryWatcher;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDA-118
 * - http://jira.pentaho.com/browse/CDA-123
 * - http://jira.pentaho.com/browse/CDA-147
 * - http://jira.pentaho.com/browse/CDA-145
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1119
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
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDA118 {
  // Instance of the driver (browser emulator)
  private final WebDriver driver = CToolsTestSuite.getDriver();
  // The base url to be append the relative url in test
  private final String baseUrl = CToolsTestSuite.getBaseUrl();
  //Download directory
  private final String downloadDir = CToolsTestSuite.getDownloadDir();
  // The path for the export file
  private final String exportFilePath = this.downloadDir + "\\cda-export.xls";
  //Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDA118.class );
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( this.driver );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Asserting that export to excel works when exporting query with more then 10 parameters
   * Description:
   *    CDA-118: asserting that export to excel works when exporting query with an integer parameter.
   *    CDA-123: Feedback given when query fails
   *    CDA-147: Can change parameter when query fails and refresh query
   *    CDA-145: Adding outputColumnName to the URL filters the query results
   *
   * Steps:
   *    1. Select "Sql Query on SampleData - Jndi" on "dataAccessSelector" and assert error message
   *    2. Change value on salesParam box to 10000 and refresh query
   *    3. Wait for and assert elements and text on page
   *    4. Export file and assure it has same md5 as expected
   *    5. Open the query with outpuColumnName in the URL and assert results were filtered
   *
   */
  @Test( timeout = 120000 )
  public void tc01_CdaFileViewer_ExcelOutputIndex() {
    this.log.info( "tc01_CdaFileViewer_ExcelOutputIndex" );

    /*
     * ## Step 1
     */
    //Open Issue Sample
    this.driver.get( this.baseUrl + "plugin/cda/api/previewQuery?path=/public/Issues/CDA/CDA-118/sql-jndi.cda" );

    //wait for invisibility of waiting pop-up
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

    //Wait for data selector and select available option
    WebElement dataAccess = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "dataAccessSelector" ) );
    assertNotNull( dataAccess );
    Select select = new Select( this.elemHelper.FindElement( this.driver, By.id( "dataAccessSelector" ) ) );
    select.selectByVisibleText( "Sql Query on SampleData - Jndi" );

    //wait to render page
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Assert error message
    String errorMessage = this.elemHelper.WaitForElementPresentGetText( this.driver, By.cssSelector( "span.error-status" ) );
    assertEquals( "Error Executing Query", errorMessage );

    /*
     * ## Step 2
     */
    WebElement salesParam = this.elemHelper.FindElement( this.driver, By.id( "sales" ) );
    assertNotNull( salesParam );
    salesParam.clear();
    salesParam.sendKeys( "10000" );
    WebElement refreshButton = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//button[@id='button']" ) );
    assertNotNull( refreshButton );
    refreshButton.click();

    /*
     * ## Step 3
     */
    //wait to render page
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Assert elements on page
    WebElement cacheButton = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//button[@id='cachethis']" ) );
    assertNotNull( cacheButton );
    WebElement queryButton = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//button[@id='queryUrl']" ) );
    assertNotNull( queryButton );

    //Check the presented contains
    WebElement newSalesParam = this.elemHelper.FindElement( this.driver, By.id( "sales" ) );
    assertEquals( "10000", newSalesParam.getAttribute( "value" ) );

    //Check text on table
    String columnOneRowOne = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='contents']/tbody/tr/td" ) );
    String columnTwoRowOne = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='contents']/tbody/tr/td[2]" ) );
    assertEquals( "Cancelled", columnOneRowOne );
    assertEquals( "2004", columnTwoRowOne );

    /*
     * ## Step 4
     */
    WebElement buttonExport = this.elemHelper.FindElement( this.driver, By.id( "export" ) );
    assertNotNull( buttonExport );
    try {
      //Delete the existence if exist
      new File( this.exportFilePath ).delete();

      //Click to export
      buttonExport.click();

      //Wait for file to be created in the destination dir
      DirectoryWatcher dw = new DirectoryWatcher();
      dw.WatchForCreate( this.downloadDir );

      //Check if the file really exist
      File exportFile = new File( this.exportFilePath );
      assertTrue( exportFile.exists() );

      //Wait for the file to be downloaded totally
      for ( int i = 0; i < 50; i++ ) { //we only try 50 times == 5000 ms
        long nSize = FileUtils.sizeOf( exportFile );
        //Since the file always contents the same data, we wait for the expected bytes
        if ( nSize >= 13824 ) {
          break;
        }
        Thread.sleep( 100 );
      }

      //Check if the file downloaded is the expected
      String md5 = DigestUtils.md5Hex( Files.readAllBytes( exportFile.toPath() ) );
      assertEquals( md5, "b82cf42595b697b00eebf2a9c083c3b7" );

      //The delete file
      DeleteFile();

    } catch ( Exception e ) {
      this.log.error( e.getMessage() );
    }

    /*
     * ## Step 5
     */
    //Open Sample with outpuColumnName in the URL
    this.driver.get( this.baseUrl + "plugin/cda/api/doQuery?paramsales=10000&paramorderDate=2004-03-01&path=%2Fpublic%2FIssues%2FCDA%2FCDA-118%2Fsql-jndi.cda&dataAccessId=1&outputIndexId=1&&outputColumnName=STATUS" );

    //Assert query result
    String result = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//body" ) );
    assertEquals( "{\"queryInfo\":{\"totalRows\":\"6\"},\"resultset\":[[\"Shipped\"],[\"Cancelled\"],[\"Shipped\"],[\"Disputed\"],[\"On Hold\"],[\"In Process\"]],\"metadata\":[{\"colIndex\":0,\"colType\":\"String\",\"colName\":\"STATUS\"}]}", result );
  }

  /**
   * The function will delete the export file.
   */
  public void DeleteFile() {
    try {
      Files.deleteIfExists( Paths.get( this.exportFilePath ) );
    } catch ( Exception e ) {
      this.log.error( e.getMessage() );
    }
  }

  @After
  public void tearDown() {
    this.log.info( "tearDown##" + CDA118.class.getSimpleName() );
    //In case something went wrong we delete the file
    DeleteFile();
  }
}
