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
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.DirectoryWatcher;
import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.selenium.BaseTest;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDA-100
 * - http://jira.pentaho.com/browse/CDA-112
 * - http://jira.pentaho.com/browse/CDA-118
 * - http://jira.pentaho.com/browse/CDA-121
 * - http://jira.pentaho.com/browse/CDA-144
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-965
 * - http://jira.pentaho.com/browse/QUALITY-1002
 * - http://jira.pentaho.com/browse/QUALITY-1119
 * - http://jira.pentaho.com/browse/QUALITY-1013
 * - http://jira.pentaho.com/browse/QUALITY-1128
 *
 * NOTE
 * To test this script it is required to have CDA plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDAExportToXls extends BaseTest {
  //The path for the export file
  private String exportFilePath;
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDAExportToXls.class );

  @BeforeClass
  public void SetUp() {
    // The path for the export file
    this.exportFilePath = downloadDir + "\\cda-export.xls";
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

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Asserting that export to excel follows output options
   * Description:
   *    The test pretends validate the CDA-100 issue, asserting that export to excel follows output options.
   *
   * Steps:
   *    1. Select "Sample query on SampleData - Jdbc" on "dataAccessSelector"
   *    2. Wait for and assert elements and text on page
   *    3. Export file and assure it has same md5 as expected
   *
   */
  @Test
  public void tc01_CdaFileViewer_ExportOutputOptions() {
    this.log.info( "tc01_CdaFileViewer_ExportOutputOption" );

    /*
     * ## Step 1
     */
    //Open sample CDA file
    driver.get( baseUrl + "plugin/cda/api/previewQuery?path=/public/Issues/CDA/CDA-100/CDA-100.cda" );

    //wait for invisibility of waiting pop-up
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ), 180 );

    //Wait for buttons: button, Cache This AND Query URL
    WebElement DataSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "dataAccessSelector" ) );
    assertNotNull( DataSelector );
    Select select = new Select( this.elemHelper.FindElement( driver, By.id( "dataAccessSelector" ) ) );
    select.selectByVisibleText( "Sql Query on SampleData - Jdbc" );
    this.elemHelper.FindElement( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 180 );
    WebElement cacheButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='cachethis']" ) );
    assertNotNull( cacheButton );
    WebElement queryButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='queryUrl']" ) );
    assertNotNull( queryButton );
    WebElement refreshButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='button']" ) );
    assertNotNull( refreshButton );

    /*
     * ## Step 2
     */
    //wait to render page
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 180 );

    //Check the presented contains
    WebElement elemStatus = this.elemHelper.FindElement( driver, By.id( "status" ) );
    assertEquals( "Shipped", elemStatus.getAttribute( "value" ) );
    elemStatus = this.elemHelper.FindElement( driver, By.id( "orderDate" ) );
    assertEquals( "2003-03-01", elemStatus.getAttribute( "value" ) );

    //Check text on table
    String columnOneRowOne = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='contents']/tbody/tr/td" ) );
    String columnTwoRowOne = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='contents']/tbody/tr/td[2]" ) );
    assertEquals( "S10_1678", columnOneRowOne );
    assertEquals( "10107", columnTwoRowOne );

    /*
     * ## Step 3
     */
    WebElement buttonExport = this.elemHelper.FindElement( driver, By.id( "export" ) );
    assertNotNull( buttonExport );
    try {
      //Delete the existence if exist
      new File( this.exportFilePath ).delete();

      //Click to export
      buttonExport.click();

      //Wait for file to be created in the destination dir
      DirectoryWatcher dw = new DirectoryWatcher();
      dw.WatchForCreate( downloadDir );

      //Check if the file really exist
      File exportFile = new File( this.exportFilePath );
      // assertTrue(exportFile.exists());

      //Wait for the file to be downloaded totally
      for ( int i = 0; i < 50; i++ ) { //we only try 50 times == 5000 ms
        long nSize = FileUtils.sizeOf( exportFile );
        //Since the file always contents the same data, we wait for the expected bytes
        if ( nSize >= 300000 ) {
          break;
        }
        this.log.info( "BeforeSleep " + nSize );
        Thread.sleep( 100 );
      }

      this.log.info( "File size :" + FileUtils.sizeOf( exportFile ) );

      //Check if the file downloaded is the expected
      String md5 = DigestUtils.md5Hex( Files.readAllBytes( exportFile.toPath() ) );
      assertEquals( md5, "f87ea229efc4da71d47a90ef2029564d" );

      //The delete file
      DeleteFile();

    } catch ( Exception e ) {
      this.log.error( e.getMessage() );
    }
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Asserting that export to excel works when exporting query with more then 10 parameters
   * Description:
   *    The test pretends validate the CDA-112 issue, asserting that export to excel works when exporting query with more then 10 parameters.
   *
   * Steps:
   *    1. Select "sqlDummyTWELVE" on "dataAccessSelector"
   *    2. Wait for and assert elements and text on page
   *    3. Export file and assure it has same md5 as expected
   *
   */
  @Test
  public void tc02_CdaFileViewer_ExportMultiParams() {
    this.log.info( "tc02_CdaFileViewer_ExportMultiParams" );

    /*
     * ## Step 1
     */
    //Open Issue Sample
    driver.get( baseUrl + "plugin/cda/api/previewQuery?path=/public/Issues/CDA/CDA-112/cda112.cda" );

    //wait for invisibility of waiting pop-up
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ), 180 );

    //Wait for buttons: button, Cache This AND Query URL
    WebElement DataSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "dataAccessSelector" ) );
    assertNotNull( DataSelector );
    Select select = new Select( this.elemHelper.FindElement( driver, By.id( "dataAccessSelector" ) ) );
    select.selectByValue( "sqlDummyTWELVE" );
    this.elemHelper.FindElement( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 180 );
    WebElement cacheButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='cachethis']" ) );
    assertNotNull( cacheButton );
    WebElement queryButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='queryUrl']" ) );
    assertNotNull( queryButton );
    WebElement refreshButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='button']" ) );
    assertNotNull( refreshButton );

    /*
     * ## Step 2
     */
    //wait to render page
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    //Check the 12 parameters were applied
    WebElement elemStatus = this.elemHelper.FindElement( driver, By.id( "p1" ) );
    assertEquals( "Alpha Cognac", elemStatus.getAttribute( "value" ) );
    elemStatus = this.elemHelper.FindElement( driver, By.id( "p2" ) );
    assertEquals( "Alpha Cognac", elemStatus.getAttribute( "value" ) );
    elemStatus = this.elemHelper.FindElement( driver, By.id( "p3" ) );
    assertEquals( "Alpha Cognac", elemStatus.getAttribute( "value" ) );
    elemStatus = this.elemHelper.FindElement( driver, By.id( "p4" ) );
    assertEquals( "Alpha Cognac", elemStatus.getAttribute( "value" ) );
    elemStatus = this.elemHelper.FindElement( driver, By.id( "p5" ) );
    assertEquals( "Alpha Cognac", elemStatus.getAttribute( "value" ) );
    elemStatus = this.elemHelper.FindElement( driver, By.id( "p6" ) );
    assertEquals( "Alpha Cognac", elemStatus.getAttribute( "value" ) );
    elemStatus = this.elemHelper.FindElement( driver, By.id( "p7" ) );
    assertEquals( "Alpha Cognac", elemStatus.getAttribute( "value" ) );
    elemStatus = this.elemHelper.FindElement( driver, By.id( "p8" ) );
    assertEquals( "Alpha Cognac", elemStatus.getAttribute( "value" ) );
    elemStatus = this.elemHelper.FindElement( driver, By.id( "p9" ) );
    assertEquals( "Alpha Cognac", elemStatus.getAttribute( "value" ) );
    elemStatus = this.elemHelper.FindElement( driver, By.id( "p10" ) );
    assertEquals( "Alpha Cognac", elemStatus.getAttribute( "value" ) );
    elemStatus = this.elemHelper.FindElement( driver, By.id( "p11" ) );
    assertEquals( "Alpha Cognac", elemStatus.getAttribute( "value" ) );
    elemStatus = this.elemHelper.FindElement( driver, By.id( "p12" ) );
    assertEquals( "Alpha Cognac", elemStatus.getAttribute( "value" ) );

    //Check text on table
    String columnOneRowOne = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='contents']/tbody/tr/td" ) );
    String columnTwoRowOne = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='contents']/tbody/tr/td[2]" ) );
    assertEquals( "242", columnOneRowOne );
    assertEquals( "Alpha Cognac", columnTwoRowOne );

    /*
     * ## Step 3
     */
    WebElement buttonExport = this.elemHelper.FindElement( driver, By.id( "export" ) );
    assertNotNull( buttonExport );
    try {
      //Delete the existence if exist
      new File( this.exportFilePath ).delete();

      //Click to export
      buttonExport.click();

      //Wait for file to be created in the destination dir
      DirectoryWatcher dw = new DirectoryWatcher();
      dw.WatchForCreate( downloadDir );
      Thread.sleep( 5000 );

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
      assertEquals( md5, "f84e5cd4a4a8ffc4499f2e69f62cbcc7" );

      //The delete file
      DeleteFile();

    } catch ( Exception e ) {
      this.log.error( e.getMessage() );
    }
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Asserting that export to excel works when exporting query with an integer parameter
   * Description:
   *    CDA-118: asserting that export to excel works when exporting query with an integer parameter.
   *
   * Steps:
   *    1. Select "Sql Query on SampleData - Jndi" on "dataAccessSelector" and assert error message
   *    3. Wait for and assert elements and text on page
   *    4. Export file and assure it has same md5 as expected
   */
  @Test
  public void tc03_CdaFileViewer_ExportIntegerParameter() {
    this.log.info( "tc03_CdaFileViewer_ExportIntegerParameter" );

    /*
     * ## Step 1
     */
    //Open Issue Sample
    driver.get( baseUrl + "plugin/cda/api/previewQuery?path=/public/Issues/CDA/CDA-118/sql-jndi-Copy.cda" );

    //wait for invisibility of waiting pop-up
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ), 180 );

    //Wait for data selector and select available option
    WebElement dataAccess = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "dataAccessSelector" ) );
    assertNotNull( dataAccess );
    Select select = new Select( this.elemHelper.FindElement( driver, By.id( "dataAccessSelector" ) ) );
    select.selectByVisibleText( "Sql Query on SampleData - Jndi" );

    /*
      * ## Step 2
      */
    this.elemHelper.FindElement( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 180 );

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
    WebElement buttonExport = this.elemHelper.FindElement( driver, By.id( "export" ) );
    assertNotNull( buttonExport );
    try {
      //Delete the existence if exist
      new File( this.exportFilePath ).delete();

      //Click to export
      buttonExport.click();

      //Wait for file to be created in the destination dir
      DirectoryWatcher dw = new DirectoryWatcher();
      dw.WatchForCreate( downloadDir );

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
  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name:
   *    Asserting that export to excel of query with String Array parameter empty works
   * Description:
   *    The test pretends validate the CDA-144 issue, asserting that export to excel of query with String Array parameter empty works.
   *
   * Steps:
   *    1. Open sql-stringArrayjndi CDA sample and assert elements on page and select "Sql Query with String Array on SampleData - Jndi" on "dataAccessSelector"
   *    2. Wait for and assert elements and text on page. Clear parameter and refresh query
   *    3. Assert new text and elementss
   *    4. Export file and assure it has same md5 as expected
   *
   */
  @Test
  public void tc04_CdaFileViewer_ExportEmptyStringArray() {
    this.log.info( "tc04_CdaFileViewer_ExportEmptyStringArray" );

    /*
     * ## Step 1
     */
    //Go to sql-jdbc CDA sample
    driver.get( baseUrl + "plugin/cda/api/previewQuery?path=/public/plugin-samples/cda/cdafiles/sql-stringArray-jndi.cda" );

    //wait for invisibility of waiting pop-up
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ), 180 );

    //Wait for buttons: button, Cache This AND Query URL
    WebElement DataSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "dataAccessSelector" ) );
    assertNotNull( DataSelector );
    Select select = new Select( this.elemHelper.FindElement( driver, By.id( "dataAccessSelector" ) ) );
    select.selectByVisibleText( "Sql Query with String Array on SampleData - Jndi" );
    this.elemHelper.FindElement( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 180 );
    WebElement refreshButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='button']" ) );
    assertNotNull( refreshButton );
    WebElement cacheButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='cachethis']" ) );
    assertNotNull( cacheButton );
    WebElement queryButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='queryUrl']" ) );
    assertNotNull( queryButton );
    WebElement element = this.elemHelper.FindElement( driver, By.id( "dataAccessSelector" ) );
    assertNotNull( element );

    /*
     * ## Step 2
     */
    //Check the presented contains
    WebElement elemStatus = this.elemHelper.FindElement( driver, By.id( "countries" ) );
    assertEquals( "France;USA", elemStatus.getAttribute( "value" ) );
    //Check text on table
    String columnOneRowOne = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='contents']/tbody/tr/td" ) );
    String columnTwoRowOne = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='contents']/tbody/tr/td[2]" ) );
    assertEquals( "Alpha Cognac", columnOneRowOne );
    assertEquals( "61100", columnTwoRowOne );
    elemStatus = this.elemHelper.FindElement( driver, By.id( "countries" ) );
    elemStatus.clear();
    refreshButton = this.elemHelper.FindElement( driver, By.xpath( "//button[@id='button']" ) );
    assertNotNull( refreshButton );
    refreshButton.click();

    /*
     * ## Step 3
     */
    //wait to render page
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 180 );
    //Check the presented contains
    elemStatus = this.elemHelper.FindElement( driver, By.id( "countries" ) );
    assertEquals( "", elemStatus.getAttribute( "value" ) );
    //Check text on table
    columnOneRowOne = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='contents']/tbody/tr/td" ) );
    assertEquals( "No results.", columnOneRowOne );

    //Export query
    WebElement buttonExport = this.elemHelper.FindElement( driver, By.id( "export" ) );
    assertNotNull( buttonExport );
    try {
      //Delete the existence if exist
      new File( this.exportFilePath ).delete();
      this.log.info( this.exportFilePath );
      //Click to export
      buttonExport.click();

      //Wait for file to be created in the destination dir
      DirectoryWatcher dw = new DirectoryWatcher();
      dw.WatchForCreate( downloadDir );

      //Check if the file really exist
      File exportFile = new File( this.exportFilePath );
      assertTrue( exportFile.exists() );

      //Wait for the file to be downloaded totally
      for ( int i = 0; i < 50; i++ ) { //we only try 50 times == 5000 ms
        long nSize = FileUtils.sizeOf( exportFile );
        //Since the file always contents the same data, we wait for the expected bytes
        if ( nSize >= 249856 ) {
          break;
        }
        Thread.sleep( 100 );
      }

      //Check if the file downloaded is the expected
      String md5 = DigestUtils.md5Hex( Files.readAllBytes( exportFile.toPath() ) );
      assertEquals( md5, "ae1fb420a78099b25fa03d7e10207d47" );

      //The delete file
      DeleteFile();

    } catch ( Exception e ) {
      this.log.error( e.getMessage() );
    }
  }

  @AfterClass( alwaysRun = true )
  public void tearDownClass() {
    this.log.info( "tearDownClass" );
    //In case something went wrong we delete the file
    DeleteFile();
  }
}
