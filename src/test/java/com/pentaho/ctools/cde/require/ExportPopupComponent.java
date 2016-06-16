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
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.DirectoryWatcher;
import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.HttpUtils;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with Export Popup Component (Sniff Test).
 *
 * Naming convention for test: 'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class ExportPopupComponent extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( ExportPopupComponent.class );
  // The path for the export file
  private final String exportFilePath = downloadDir + "\\export_chart_png.png";
  // The path for the export file
  private final String exportFilePath2 = downloadDir + "\\export_chart_svg.svg";
  // The path for the export file
  private final String exportFilePath3 = downloadDir + "\\export.csv";
  // The path for the export file
  private final String exportFilePath4 = downloadDir + "\\export.xls";
  // The path for the export file
  private final String exportFilePath5 = downloadDir + "\\export.json";
  // The path for the export file
  private final String exportFilePath6 = downloadDir + "\\export.xml";

  /**
   * The function will delete the export file.
   */
  public void DeleteFile() {
    try {
      Files.deleteIfExists( Paths.get( this.exportFilePath ) );
      Files.deleteIfExists( Paths.get( this.exportFilePath2 ) );
      Files.deleteIfExists( Paths.get( this.exportFilePath3 ) );
      Files.deleteIfExists( Paths.get( this.exportFilePath4 ) );
      Files.deleteIfExists( Paths.get( this.exportFilePath5 ) );
      Files.deleteIfExists( Paths.get( this.exportFilePath6 ) );

    } catch ( Exception e ) {
      this.log.error( e.getMessage() );
    }
  }

  /**
   * ############################### Test Case 1 ###############################
   *
      * Test Case Name:
   *    Sniff test to sample
   *
   * Description:
   *    This test is to assert simple functionality of sample 
   *
      * Steps:
   *    1. Open Duplicate Component sample and assert elements on page
   *    2. Export to PNG and assert file
   *    3. Export to SVG and assert file
   *    4. Export to CSV and assert file
   *    5. Export to XLS and assert file
   *    6. Export to JSON and assert file
   *    7. Export to XML and assert file
   *    
   */
  @Test
  public void tc01_CDEDashboard_ExportPopupComponentWorks() {
    this.log.info( "tc01_CDEDashboard_ExportPopupComponentWorks" );

    /*
     * ## Step 1
     */
    //Open Duplicate Component sample
    driver.get( PageUrl.EXPORT_POPUP_COMPONENT_REQUIRE );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    //Get Title of sample
    String title = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "Title" ) );
    assertEquals( "Export Popup Component Reference", title );

    //Assert presence of elements
    WebElement chart = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "TheChartprotovis" ) );
    WebElement chartBar = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='TheChartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[name()='g']/*[name()='g'][2]/*[name()='g']/*[name()='g'][2]/*[name()='g']/*[name()='g']/*[name()='g'][2]/*[name()='rect'][4]" ) );
    WebElement chartLegend = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='TheChartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[name()='g']/*[name()='g'][2]/*[name()='g']/*[name()='g'][4]/*[name()='g']/*[name()='g'][6]/*[name()='text']" ) );
    WebElement table = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "TheTableTable" ) );
    WebElement columnHeader = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='TheTableTable']/thead/tr/th" ) );
    WebElement c2r6 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='TheTableTable']/tbody/tr[6]/td[2]" ) );
    assertNotNull( chart );
    assertNotNull( chartBar );
    assertNotNull( chartLegend );
    assertNotNull( table );
    assertNotNull( columnHeader );
    assertNotNull( c2r6 );

    /*
     * ## Step 2
     */
    //Click to Export to PNG
    WebElement exportPNG = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='ChartExportPNGExporting']/div" ) );
    assertNotNull( exportPNG );
    exportPNG.click();
    WebElement linkExport = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div.exportElement" ) );
    assertNotNull( linkExport );
    linkExport.click();

    //Assert chart popup
    WebElement exportChartPopup = this.elemHelper.FindElement( driver, By.id( "fancybox-content" ) );
    assertNotNull( exportChartPopup );

    // Check URL of displayed image
    String chartSRCUrl = this.elemHelper.GetAttribute( driver, By.xpath( "//div[@id='fancybox-content']/div/div/div/div[2]/img" ), "src" );
    assertEquals( baseUrl + "plugin/cgg/api/services/draw?outputType=png&script=%2Fpublic%2Fplugin-samples%2Fpentaho-cdf-dd%2Fpentaho-cdf-dd-require%2Ftests%2FExportPopup%2FBarChart.js&paramwidth=350&paramheight=200", chartSRCUrl );
    assertEquals( 200, HttpUtils.GetHttpStatus( chartSRCUrl, pentahoBaServerUsername, pentahoBaServerPassword ) );

    // Export chart and assert export was successful
    WebElement chartExportButton = this.elemHelper.FindElement( driver, By.cssSelector( "div.exportChartPopupButton.exportChartOkButton" ) );
    assertNotNull( chartExportButton );

    //Click export and assert file is correctly downloaded
    try {
      //Delete the existence if exist
      new File( this.exportFilePath ).delete();

      //Click to export
      chartExportButton.click();

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
        if ( nSize >= 5000 ) {
          break;
        }
        this.log.info( "BeforeSleep " + nSize );
        Thread.sleep( 100 );
      }

      this.log.info( "File size :" + FileUtils.sizeOf( exportFile ) );

      //Check if the file downloaded is the expected
      String md5 = DigestUtils.md5Hex( Files.readAllBytes( exportFile.toPath() ) );
      assertEquals( md5, "3bc1e421664d221d7c3fdc9cd56069ca" );

      //The delete file
      DeleteFile();

    } catch ( Exception e ) {
      this.log.error( e.getMessage() );
    }

    // Close dialog box
    this.elemHelper.Click( driver, By.id( "fancybox-close" ) );
    assertTrue( this.elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@id='fancybox-content']/div/div/div/div/div[1]" ) ) );

    /*
     * ## Step 3
     */
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.exportElement" ) );
    //Click export Button
    WebElement exportCountryChartButton = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='ChartExportSVGExporting']/div" ) );
    assertNotNull( exportCountryChartButton );
    exportCountryChartButton.click();

    //Assert popup and click Export Chart link
    WebElement exportCountryChartLink = this.elemHelper.FindElement( driver, By.xpath( "//body/div[9]/div[1]" ) );
    assertNotNull( exportCountryChartLink );
    exportCountryChartLink.click();

    //Assert chart popup
    WebElement exportCountryChartPopup = this.elemHelper.FindElement( driver, By.id( "fancybox-content" ) );
    assertNotNull( exportCountryChartPopup );

    // Check URL of displayed image
    String countryChartSRCUrl = this.elemHelper.GetAttribute( driver, By.xpath( "//div[@id='fancybox-content']/div/div/div/div[2]/img" ), "src" );
    assertEquals( baseUrl + "plugin/cgg/api/services/draw?outputType=svg&script=%2Fpublic%2Fplugin-samples%2Fpentaho-cdf-dd%2Fpentaho-cdf-dd-require%2Ftests%2FExportPopup%2FBarChart.js&paramwidth=350&paramheight=200", countryChartSRCUrl );
    assertEquals( 200, HttpUtils.GetHttpStatus( countryChartSRCUrl, pentahoBaServerUsername, pentahoBaServerPassword ) );

    // Export chart and assert export was successful
    WebElement countryChartExportButton = this.elemHelper.FindElement( driver, By.cssSelector( "div.exportChartPopupButton.exportChartOkButton" ) );
    assertNotNull( countryChartExportButton );

    //Click export and assert file is correctly downloaded
    try {
      //Delete the existence if exist
      new File( this.exportFilePath2 ).delete();

      //Click to export
      countryChartExportButton.click();

      //Wait for file to be created in the destination dir
      DirectoryWatcher dw = new DirectoryWatcher();
      dw.WatchForCreate( downloadDir );

      //Check if the file really exist
      File exportFile = new File( this.exportFilePath2 );
      // assertTrue(exportFile.exists());

      //Wait for the file to be downloaded totally
      for ( int i = 0; i < 50; i++ ) { //we only try 50 times == 5000 ms
        long nSize = FileUtils.sizeOf( exportFile );
        //Since the file always contents the same data, we wait for the expected bytes
        if ( nSize >= 30000 ) {
          break;
        }
        this.log.info( "BeforeSleep " + nSize );
        Thread.sleep( 100 );
      }

      this.log.info( "File size :" + FileUtils.sizeOf( exportFile ) );

      //Check if the file downloaded is the expected
      String md5 = DigestUtils.md5Hex( Files.readAllBytes( exportFile.toPath() ) );
      assertEquals( md5, "954fae4599f547ff62c8d2684fd9b497" );

      //The delete file
      DeleteFile();

    } catch ( Exception e ) {
      this.log.error( e.getMessage() );
    }

    // Close dialog box
    this.elemHelper.Click( driver, By.id( "fancybox-close" ) );
    assertTrue( this.elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@id='fancybox-content']/div/div/div/div/div[1]" ) ) );

    /*
     * ## Step 4
     */
    //Click to Export to CSV
    WebElement exportCSV = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='DataExportCSVExporting']/div" ) );
    assertNotNull( exportCSV );
    exportCSV.click();
    linkExport = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//body/div[10]/div[1]" ) );
    assertNotNull( linkExport );

    //Click export and assert file is correctly downloaded
    try {
      //Delete the existence if exist
      new File( this.exportFilePath3 ).delete();

      //Click to export
      linkExport.click();

      //Wait for file to be created in the destination dir
      DirectoryWatcher dw = new DirectoryWatcher();
      dw.WatchForCreate( downloadDir );

      //Check if the file really exist
      File exportFile = new File( this.exportFilePath3 );
      // assertTrue(exportFile.exists());

      //Wait for the file to be downloaded totally
      for ( int i = 0; i < 50; i++ ) { //we only try 50 times == 5000 ms
        long nSize = FileUtils.sizeOf( exportFile );
        //Since the file always contents the same data, we wait for the expected bytes
        if ( nSize >= 3000 ) {
          break;
        }
        this.log.info( "BeforeSleep " + nSize );
        Thread.sleep( 100 );
      }

      this.log.info( "File size :" + FileUtils.sizeOf( exportFile ) );

      //Check if the file downloaded is the expected
      String md5 = DigestUtils.md5Hex( Files.readAllBytes( exportFile.toPath() ) );
      assertEquals( md5, "a1be04c7c26ad6fbfb1d1920568b96ff" );

      //The delete file
      DeleteFile();

    } catch ( Exception e ) {
      this.log.error( e.getMessage() );
    }

    /*
     * ## Step 5
     */
    //Click to Export to CSV
    WebElement exportXLS = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='DataExportXLSExporting']/div" ) );
    assertNotNull( exportXLS );
    exportXLS.click();
    linkExport = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//body/div[11]/div[1]" ) );
    assertNotNull( linkExport );

    //Click export and assert file is correctly downloaded
    try {
      //Delete the existence if exist
      new File( this.exportFilePath4 ).delete();

      //Click to export
      linkExport.click();

      //Wait for file to be created in the destination dir
      DirectoryWatcher dw = new DirectoryWatcher();
      dw.WatchForCreate( downloadDir );

      //Check if the file really exist
      File exportFile = new File( this.exportFilePath4 );
      // assertTrue(exportFile.exists());

      //Wait for the file to be downloaded totally
      for ( int i = 0; i < 50; i++ ) { //we only try 50 times == 5000 ms
        long nSize = FileUtils.sizeOf( exportFile );
        //Since the file always contents the same data, we wait for the expected bytes
        if ( nSize >= 20000 ) {
          break;
        }
        this.log.info( "BeforeSleep " + nSize );
        Thread.sleep( 100 );
      }

      this.log.info( "File size :" + FileUtils.sizeOf( exportFile ) );

      //Check if the file downloaded is the expected
      String md5 = DigestUtils.md5Hex( Files.readAllBytes( exportFile.toPath() ) );
      assertEquals( md5, "b680747e0e0f6e881a063027222d76d9" );

      //The delete file
      DeleteFile();

    } catch ( Exception e ) {
      this.log.error( e.getMessage() );
    }

    /*
     * ## Step 6
     */
    //Click to Export to JSON
    WebElement exportJSON = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='DataExportJSONExporting']/div" ) );
    assertNotNull( exportJSON );
    exportJSON.click();
    linkExport = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//body/div[12]/div[1]" ) );
    assertNotNull( linkExport );

    //Click export and assert file is correctly downloaded
    try {
      //Delete the existence if exist
      new File( this.exportFilePath5 ).delete();

      //Click to export
      linkExport.click();

      //Wait for file to be created in the destination dir
      DirectoryWatcher dw = new DirectoryWatcher();
      dw.WatchForCreate( downloadDir );

      //Check if the file really exist
      File exportFile = new File( this.exportFilePath5 );
      // assertTrue(exportFile.exists());

      //Wait for the file to be downloaded totally
      for ( int i = 0; i < 50; i++ ) { //we only try 50 times == 5000 ms
        long nSize = FileUtils.sizeOf( exportFile );
        //Since the file always contents the same data, we wait for the expected bytes
        if ( nSize >= 4000 ) {
          break;
        }
        this.log.info( "BeforeSleep " + nSize );
        Thread.sleep( 100 );
      }

      this.log.info( "File size :" + FileUtils.sizeOf( exportFile ) );

      //Check if the file downloaded is the expected
      String md5 = DigestUtils.md5Hex( Files.readAllBytes( exportFile.toPath() ) );
      assertEquals( md5, "44fe88dc404ec0b240cd3095955ff084" );

      //The delete file
      DeleteFile();

    } catch ( Exception e ) {
      this.log.error( e.getMessage() );
    }

    /*
     * ## Step 7
     */
    //Click to Export to XML
    WebElement exportXML = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='DataExportXMLExporting']/div" ) );
    assertNotNull( exportXML );
    exportXML.click();
    linkExport = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//body/div[13]/div[1]" ) );
    assertNotNull( linkExport );

    //Click export and assert file is correctly downloaded
    try {
      //Delete the existence if exist
      new File( this.exportFilePath6 ).delete();

      //Click to export
      linkExport.click();

      //Wait for file to be created in the destination dir
      DirectoryWatcher dw = new DirectoryWatcher();
      dw.WatchForCreate( downloadDir );

      //Check if the file really exist
      File exportFile = new File( this.exportFilePath6 );
      // assertTrue(exportFile.exists());

      //Wait for the file to be downloaded totally
      for ( int i = 0; i < 50; i++ ) { //we only try 50 times == 5000 ms
        long nSize = FileUtils.sizeOf( exportFile );
        //Since the file always contents the same data, we wait for the expected bytes
        if ( nSize >= 6000 ) {
          break;
        }
        this.log.info( "BeforeSleep " + nSize );
        Thread.sleep( 100 );
      }

      this.log.info( "File size :" + FileUtils.sizeOf( exportFile ) );

      //Check if the file downloaded is the expected
      String md5 = DigestUtils.md5Hex( Files.readAllBytes( exportFile.toPath() ) );
      assertEquals( md5, "a33133fbd76bb2cf6d0c8e3949c08e81" );

      //The delete file
      DeleteFile();

    } catch ( Exception e ) {
      this.log.error( e.getMessage() );
    }

  }
}
