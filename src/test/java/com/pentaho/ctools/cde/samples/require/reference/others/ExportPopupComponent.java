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
package com.pentaho.ctools.cde.samples.require.reference.others;

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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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
  private String exportFilePath = "/export_chart_png.png";
  // The path for the export file
  private String exportFilePath2 = "/export_chart_svg.svg";
  // The path for the export file
  private String exportFilePath3 = "/export.csv";
  // The path for the export file
  private String exportFilePath4 = "/export.xls";
  // The path for the export file
  private String exportFilePath5 = "/export.json";
  // The path for the export file
  private String exportFilePath6 = "/export.xml";

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
   * The method pretends to delete possible existing files before run the test case.
   */
  @BeforeClass
  public void setup() {
    this.exportFilePath = downloadDir + this.exportFilePath;
    this.exportFilePath2 = downloadDir + this.exportFilePath2;
    this.exportFilePath3 = downloadDir + this.exportFilePath3;
    this.exportFilePath4 = downloadDir + this.exportFilePath4;
    this.exportFilePath5 = downloadDir + this.exportFilePath5;
    this.exportFilePath6 = downloadDir + this.exportFilePath6;
    this.DeleteFile();
  }

  /**
   * The method pretends to delete possible existing files after run the test case.
   */
  @AfterClass
  public void teardown() {
    this.DeleteFile();
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
    this.elemHelper.Get( driver, PageUrl.EXPORT_POPUP_COMPONENT_REQUIRE );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    //Get Title of sample
    String title = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "Title" ) );
    assertEquals( "Export Popup Component Reference", title );

    //Assert presence of elements
    WebElement chart = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "TheChartprotovis" ) );
    assertNotNull( chart );
    WebElement chartBar = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='TheChartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[name()='g']/*[name()='g'][2]/*[name()='g']/*[name()='g'][2]/*[name()='g']/*[name()='g']/*[name()='g'][2]/*[name()='rect'][4]" ) );
    assertNotNull( chartBar );
    WebElement chartLegend = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='TheChartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[name()='g']/*[name()='g'][2]/*[name()='g']/*[name()='g'][4]/*[name()='g']/*[name()='g'][6]/*[name()='text']" ) );
    assertNotNull( chartLegend );
    WebElement table = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "sampletableCol" ) );
    assertNotNull( table );
    WebElement columnHeader = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "#sampletableColTable > thead > tr > th" ) );
    assertNotNull( columnHeader );
    WebElement c2r6 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "#sampletableColTable > tbody > tr:nth-child(6) > td.column1.number" ) );
    assertNotNull( c2r6 );

    /*
     * ## Step 2
     */
    //Click to Export to PNG
    this.elemHelper.FocusElement( driver, By.id( "ExportCharts" ) );
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='ChartExportPNGExporting']/div" ) );
    this.elemHelper.Click( driver, By.cssSelector( "div.exportElement" ) );

    //Assert chart popup
    WebElement exportChartPopup = this.elemHelper.FindElement( driver, By.id( "fancybox-content" ) );
    assertNotNull( exportChartPopup );

    // Check URL of displayed image
    String chartSRCUrl = this.elemHelper.GetAttribute( driver, By.xpath( "//div[@id='fancybox-content']/div/div/div/div[2]/img" ), "src" );
    assertEquals( chartSRCUrl, baseUrl + "plugin/cgg/api/services/draw?outputType=png&script=%2Fpublic%2Fplugin-samples%2Fpentaho-cdf-dd%2Ftests%2FExportPopup%2FBarChart.js&paramwidth=350&paramheight=200" );
    assertEquals( HttpUtils.GetHttpStatus( chartSRCUrl, pentahoBaServerUsername, pentahoBaServerPassword ), 200 );

    //Click export and assert file is correctly downloaded
    try {
      //Click to export
      this.elemHelper.Click( driver, By.cssSelector( "div.exportChartPopupButton.exportChartOkButton" ) );

      //Wait for file to be created in the destination dir
      //Check if the file really exist
      File exportFile = new File( this.exportFilePath );

      //Wait for the file to be downloaded totally
      for ( int i = 0; i < 50; i++ ) { //we only try 50 times == 5000 ms
        if ( exportFile.exists() ) {
          long nSize = FileUtils.sizeOf( exportFile );
          //Since the file always contents the same data, we wait for the expected bytes
          if ( nSize >= 5000 ) {
            break;
          }
          this.log.info( "BeforeSleep " + nSize );
        }
        Thread.sleep( 100 );
      }

      this.log.info( "File size :" + FileUtils.sizeOf( exportFile ) );

      //Check if the file downloaded is the expected
      String md5 = DigestUtils.md5Hex( Files.readAllBytes( exportFile.toPath() ) );
      assertEquals( md5, "b9cc6ec4ac71cbcfb9fcd741f8fc62f7" );

      Thread.sleep( 500 );
    } catch ( Exception e ) {
      this.log.error( e.getMessage() );
    }

    // Close dialog box
    this.elemHelper.ClickJS( driver, By.id( "fancybox-close" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.id( "fancybox-wrap" ) );
    assertTrue( this.elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@id='fancybox-content']/div/div/div/div/div[1]" ) ) );

    /*
     * ## Step 3
     */
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.exportElement" ) );
    //Click export Button
    this.elemHelper.ClickJS( driver, By.cssSelector( "#ChartExportSVGExporting > div" ) );
    //Assert popup and click Export Chart link
    Boolean increment = false;
    WebElement exportsvg = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div.popupComponent:nth-child(11) > div" ), 3 );
    if ( exportsvg != null )
      this.elemHelper.Click( driver, By.cssSelector( "div.popupComponent:nth-child(11) > div" ) );
    else {
      increment = true;
      this.elemHelper.Click( driver, By.cssSelector( "div.popupComponent:nth-child(12) > div" ) );
    }

    //Assert chart popup
    WebElement exportCountryChartPopup = this.elemHelper.FindElement( driver, By.id( "fancybox-content" ) );
    assertNotNull( exportCountryChartPopup );

    // Check URL of displayed image
    String countryChartSRCUrl = this.elemHelper.GetAttribute( driver, By.xpath( "//div[@id='fancybox-content']/div/div/div/div[2]/img" ), "src" );
    assertEquals( countryChartSRCUrl, baseUrl + "plugin/cgg/api/services/draw?outputType=svg&script=%2Fpublic%2Fplugin-samples%2Fpentaho-cdf-dd%2Ftests%2FExportPopup%2FBarChart.js&paramwidth=350&paramheight=200" );
    assertEquals( 200, HttpUtils.GetHttpStatus( countryChartSRCUrl, pentahoBaServerUsername, pentahoBaServerPassword ) );

    //Click export and assert file is correctly downloaded
    try {
      //Click to export
      this.elemHelper.Click( driver, By.cssSelector( "div.exportChartPopupButton.exportChartOkButton" ) );

      //Wait for file to be created in the destination dir
      //Check if the file really exist
      File exportFile = new File( this.exportFilePath2 );
      // assertTrue(exportFile.exists());

      //Wait for the file to be downloaded totally
      for ( int i = 0; i < 50; i++ ) { //we only try 50 times == 5000 ms
        if ( exportFile.exists() ) {
          long nSize = FileUtils.sizeOf( exportFile );
          //Since the file always contents the same data, we wait for the expected bytes
          if ( nSize >= 30000 ) {
            break;
          }
          this.log.info( "BeforeSleep " + nSize );
        }
        Thread.sleep( 100 );
      }

      this.log.info( "File size :" + FileUtils.sizeOf( exportFile ) );

      //Check if the file downloaded is the expected
      String md5 = DigestUtils.md5Hex( Files.readAllBytes( exportFile.toPath() ) );
      assertEquals( md5, "4e0c6695e4aac3b2b7d52fac25258897" );

      Thread.sleep( 500 );
    } catch ( Exception e ) {
      this.log.error( e.getMessage() );
      assertTrue( false );
    }

    // Close dialog box
    this.elemHelper.ClickJS( driver, By.id( "fancybox-close" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.id( "fancybox-wrap" ) );
    assertTrue( this.elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@id='fancybox-content']/div/div/div/div/div[1]" ) ) );

    /*
     * ## Step 4
     */
    this.elemHelper.WaitForElementInvisibility( driver, By.id( "fancybox-overlay" ) );
    //Click to Export to CSV
    this.elemHelper.ClickJS( driver, By.cssSelector( "#DataExportCSVExporting > div" ) );

    //Click export and assert file is correctly downloaded
    try {
      //Click to export
      if ( increment ) {
        this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div.popupComponent:nth-child(13) > div" ), 3 );
        this.elemHelper.Click( driver, By.cssSelector( "div.popupComponent:nth-child(13) > div" ) );
      } else {
        WebElement exportCSV = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div.popupComponent:nth-child(12) > div" ), 3 );
        if ( exportCSV != null ) {
          this.elemHelper.Click( driver, By.cssSelector( "div.popupComponent:nth-child(12) > div" ) );
        } else {
          increment = true;
          this.elemHelper.Click( driver, By.cssSelector( "div.popupComponent:nth-child(13) > div" ) );
        }
      }

      //Wait for file to be created in the destination dir
      //Check if the file really exist
      File exportFile = new File( this.exportFilePath3 );

      //Wait for the file to be downloaded totally
      for ( int i = 0; i < 50; i++ ) { //we only try 50 times == 5000 ms
        if ( exportFile.exists() ) {
          long nSize = FileUtils.sizeOf( exportFile );
          //Since the file always contents the same data, we wait for the expected bytes
          if ( nSize >= 3000 ) {
            break;
          }
          this.log.info( "BeforeSleep " + nSize );
        }
        Thread.sleep( 100 );
      }

      this.log.info( "File size :" + FileUtils.sizeOf( exportFile ) );

      //Check if the file downloaded is the expected
      String md5 = DigestUtils.md5Hex( Files.readAllBytes( exportFile.toPath() ) );
      assertEquals( md5, "ce173f0d7430a31e070cbed042cb6068" );

      Thread.sleep( 500 );
    } catch ( Exception e ) {
      this.log.error( e.getMessage() );
      assertTrue( false );
    }

    /*
     * ## Step 5
     */
    //Click to Export to XLS
    this.elemHelper.ClickJS( driver, By.cssSelector( "#DataExportXLSExporting > div" ) );

    //Click export and assert file is correctly downloaded
    try {
      //Click to export
      if ( increment ) {
        this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div.popupComponent:nth-child(14) > div" ), 3 );
        this.elemHelper.Click( driver, By.cssSelector( "div.popupComponent:nth-child(14) > div" ) );
      } else {
        WebElement exportCSV = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div.popupComponent:nth-child(13) > div" ), 3 );
        if ( exportCSV != null ) {
          this.elemHelper.Click( driver, By.cssSelector( "div.popupComponent:nth-child(13) > div" ) );
        } else {
          increment = true;
          this.elemHelper.Click( driver, By.cssSelector( "div.popupComponent:nth-child(14) > div" ) );
        }
      }

      //Wait for file to be created in the destination dir
      //Check if the file really exist
      File exportFile = new File( this.exportFilePath4 );

      //Wait for the file to be downloaded totally
      for ( int i = 0; i < 50; i++ ) { //we only try 50 times == 5000 ms
        if ( exportFile.exists() ) {
          long nSize = FileUtils.sizeOf( exportFile );
          //Since the file always contents the same data, we wait for the expected bytes
          if ( nSize >= 20000 ) {
            break;
          }
          this.log.info( "BeforeSleep " + nSize );
        }
        Thread.sleep( 100 );
      }

      this.log.info( "File size :" + FileUtils.sizeOf( exportFile ) );

      //Check if the file downloaded is the expected
      String md5 = DigestUtils.md5Hex( Files.readAllBytes( exportFile.toPath() ) );
      assertEquals( md5, "2f16c56802d4d31dfc09a00bbcf8e71a" );

      Thread.sleep( 500 );
    } catch ( Exception e ) {
      this.log.error( e.getMessage() );
      assertTrue( false );
    }

    /*
     * ## Step 6
     */
    //Click to Export to JSON
    this.elemHelper.ClickJS( driver, By.cssSelector( "#DataExportJSONExporting > div" ) );

    //Click export and assert file is correctly downloaded
    try {
      //Click to export
      if ( increment ) {
        this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div.popupComponent:nth-child(15) > div" ), 3 );
        this.elemHelper.Click( driver, By.cssSelector( "div.popupComponent:nth-child(15) > div" ) );
      } else {
        WebElement exportCSV = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div.popupComponent:nth-child(14) > div" ), 3 );
        if ( exportCSV != null ) {
          this.elemHelper.Click( driver, By.cssSelector( "div.popupComponent:nth-child(14) > div" ) );
        } else {
          increment = true;
          this.elemHelper.Click( driver, By.cssSelector( "div.popupComponent:nth-child(15) > div" ) );
        }
      }

      //Wait for file to be created in the destination dir
      //Check if the file really exist
      File exportFile = new File( this.exportFilePath5 );

      //Wait for the file to be downloaded totally
      for ( int i = 0; i < 50; i++ ) { //we only try 50 times == 5000 ms
        if ( exportFile.exists() ) {
          long nSize = FileUtils.sizeOf( exportFile );
          //Since the file always contents the same data, we wait for the expected bytes
          if ( nSize >= 4000 ) {
            break;
          }
          this.log.info( "BeforeSleep " + nSize );
        }
        Thread.sleep( 100 );
      }

      this.log.info( "File size :" + FileUtils.sizeOf( exportFile ) );

      //Check if the file downloaded is the expected
      String md5 = DigestUtils.md5Hex( Files.readAllBytes( exportFile.toPath() ) );
      assertEquals( md5, "de77ef9444d37f191737549a8c7a87fc" );

      Thread.sleep( 500 );
    } catch ( Exception e ) {
      this.log.error( e.getMessage() );
      assertTrue( false );
    }

    /*
     * ## Step 7
     */
    //Click to Export to XML
    this.elemHelper.ClickJS( driver, By.cssSelector( "#DataExportXMLExporting > div" ) );

    //Click export and assert file is correctly downloaded
    try {
      //Click to export
      if ( increment ) {
        this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div.popupComponent:nth-child(16) > div" ), 3 );
        this.elemHelper.Click( driver, By.cssSelector( "div.popupComponent:nth-child(16) > div" ) );
      } else {
        WebElement exportCSV = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div.popupComponent:nth-child(15) > div" ), 3 );
        if ( exportCSV != null ) {
          this.elemHelper.Click( driver, By.cssSelector( "div.popupComponent:nth-child(15) > div" ) );
        } else {
          increment = true;
          this.elemHelper.Click( driver, By.cssSelector( "div.popupComponent:nth-child(16) > div" ) );
        }
      }

      //Wait for file to be created in the destination dir
      //Check if the file really exist
      File exportFile = new File( this.exportFilePath6 );

      //Wait for the file to be downloaded totally
      for ( int i = 0; i < 50; i++ ) { //we only try 50 times == 5000 ms
        if ( exportFile.exists() ) {
          long nSize = FileUtils.sizeOf( exportFile );
          //Since the file always contents the same data, we wait for the expected bytes
          if ( nSize >= 6000 ) {
            break;
          }
          this.log.info( "BeforeSleep " + nSize );
        }
        Thread.sleep( 100 );
      }

      this.log.info( "File size :" + FileUtils.sizeOf( exportFile ) );

      //Check if the file downloaded is the expected
      String md5 = DigestUtils.md5Hex( Files.readAllBytes( exportFile.toPath() ) );
      assertEquals( md5, "1aeba3355dee869b97832729c7945474" );

      Thread.sleep( 500 );
    } catch ( Exception e ) {
      this.log.error( e.getMessage() );
      assertTrue( false );
    }

  }
}
