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
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.BaseTest;
import com.pentaho.ctools.utils.DirectoryWatcher;
import com.pentaho.ctools.utils.ElementHelper;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDA-112
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1002
 *
 * NOTE
 * To test this script it is required to have CDA plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDA112 extends BaseTest {
  // The path for the export file
  private final String exportFilePath = downloadDir + "\\cda-export.xls";
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDA112.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Asserting that export to excel works when exporting query with more then 10 parameters
   * Description:
   *    The test pretends validate the CDA-110 issue, asserting that export to excel works when exporting query with more then 10 parameters.
   *
   * Steps:
   *    1. Select "sqlDummyTWELVE" on "dataAccessSelector"
   *    2. Wait for and assert elements and text on page
   *    3. Export file and assure it has same md5 as expected
   *
   */
  @Test
  public void tc01_CdaFileViewer_ExcelOutputIndex() {
    this.log.info( "tc01_CdaFileViewer_ExcelOutputIndex" );

    /*
     * ## Step 1
     */
    //Open Issue Sample
    driver.get( baseUrl + "plugin/cda/api/previewQuery?path=/public/Issues/CDA/CDA-112/cda112.cda" );

    //wait for invisibility of waiting pop-up
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

    //Wait for buttons: button, Cache This AND Query URL
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "dataAccessSelector" ) );
    assertNotNull( element );
    Select select = new Select( this.elemHelper.FindElement( driver, By.id( "dataAccessSelector" ) ) );
    select.selectByValue( "sqlDummyTWELVE" );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='button']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='cachethis']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='queryUrl']" ) );
    assertNotNull( element );

    /*
     * ## Step 2
     */
    //wait to render page
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Check the presented contains
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
   * The function will delete the export file.
   */
  public void DeleteFile() {
    try {
      Files.deleteIfExists( Paths.get( this.exportFilePath ) );
    } catch ( Exception e ) {
      this.log.error( e.getMessage() );
    }
  }

  @AfterTest
  public void tearDown() {
    this.log.info( "tearDown##" + CDA112.class.getSimpleName() );
    //In case something went wrong we delete the file
    DeleteFile();
  }
}
