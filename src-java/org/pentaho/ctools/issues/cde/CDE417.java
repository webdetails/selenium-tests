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
package org.pentaho.ctools.issues.cde;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.DirectoryWatcher;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.HttpUtils;
import org.pentaho.ctools.utils.PUCSettings;
import org.pentaho.ctools.utils.ScreenshotTestRule;
import org.pentaho.gui.web.puc.BrowseFiles;

/**
 * The script is testing the issue:
 *  - http://jira.pentaho.com/browse/CDE-417
 *  - http://jira.pentaho.com/browse/CDE-424
 *  - http://jira.pentaho.com/browse/CDF-448
 *  - http://jira.pentaho.com/browse/CDF-502
 *  - http://jira.pentaho.com/browse/CDE-514
 *   *    
 * and the automation test is described:
 *  - http://jira.pentaho.com/browse/QUALITY-1017
 *  - http://jira.pentaho.com/browse/QUALITY-1019
 *  - http://jira.pentaho.com/browse/QUALITY-1147
 *  - http://jira.pentaho.com/browse/QUALITY-1110
 *  - http://jira.pentaho.com/browse/QUALITY-1096
 *  
 * NOTE To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test: 'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDE417 {
  // Instance of the driver (browser emulator)
  private final WebDriver driver = CToolsTestSuite.getDriver();
  // The base url to be append the relative url in test
  private final String baseUrl = CToolsTestSuite.getBaseUrl();
  //Download directory
  private final String downloadDir = CToolsTestSuite.getDownloadDir();
  // The path for the export file
  private final String exportFilePath = this.downloadDir + "\\export.png";
  // The path for the export file
  private final String exportFilePath2 = this.downloadDir + "\\export.svg";
  //Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDE417.class );
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( this.driver );

  /**
   * The function will delete the export file.
   */
  public void DeleteFile() {
    try {
      Files.deleteIfExists( Paths.get( this.exportFilePath ) );
      Files.deleteIfExists( Paths.get( this.exportFilePath2 ) );
    } catch ( Exception e ) {
      this.log.error( e.getMessage() );
    }
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name: 
   *   Popup Export issues
   *
   * Description: 
   *   CDE-417 issue, so when user clicks to export chart, popup shown has chart preview. 
   *   CDE-424 issue we'll delete the .js file prior to opening the dashboard and saving the dashboard should create 
   *   the .js file again and enable exporting.
   *   CDF-448 exporting chart with skip ticks (chart is too small to show all labels) is successful
   *   CDF-502 export chart with array parameters is successful
   *   CDE-514 all charts are available on charts to export property and all elements are available on the data to export property
   *   
   * Steps:
   * 424
   *    1. Open PUC and click Browse Files
   *    2. Go to dashboard folder, click countryChart.js file and click Move To Trash
   *    3. Open created sample and save Dashboard
   * 514
   *    4. Go to Export Component and assert all charts appear on the Chart to Export property
   *    5. Assert all charts and the table appear on data to export property
   *    
   * 417/448/502
   *    6. Click to export chart, click export, assert chart is shown and assert export works as expected (448 and 502)
   *    7. Click to export chart2, click export, assert chart is shown and assert export works as expected
   * 
   * @throws InterruptedException
   *
   */

  @Test( timeout = 360000 )
  public void tc01_PopupExportComponent_PreviewerRendersChart() throws InterruptedException {
    this.log.info( "tc01_PopupExportComponent_PreviewerRendersChart" );

    /*
     * ## Step 1
     */
    //Show Hidden Files
    BrowseFiles browser = new BrowseFiles( this.driver );
    if ( !PUCSettings.SHOWHIDDENFILES ) {
      browser.CheckShowHiddenFiles();
    }

    /*
     * ## Step 2
     */
    //String pathChart = "/public/Issues/CDF/CDF-548/chart.js";
    //String pathCdfChart = "/public/Issues/CDF/CDF-548/CDF-548_chart.js";
    String pathCountryChart = "/public/Issues/CDF/CDF-548/countryChart.js";
    String pathCdfCountryChart = "/public/Issues/CDF/CDF-548/CDF-548_countryChart.js";
    //boolean fileDeleteChart = browser.DeleteFile( pathChart );
    //boolean fileDeleteCdfChart = browser.DeleteFile( pathCdfChart );
    boolean fileDeleteCountryChart = browser.DeleteFile( pathCountryChart );
    boolean fileDeleteCdfCountryChart = browser.DeleteFile( pathCdfCountryChart );
    //assertTrue( fileDeleteChart );
    //assertTrue( fileDeleteCdfChart );
    assertTrue( fileDeleteCountryChart );
    assertTrue( fileDeleteCdfCountryChart );

    this.driver.switchTo().defaultContent();
    WebDriver frame = this.driver.switchTo().frame( "browser.perspective" );
    this.elemHelper.WaitForAttributeValue( frame, By.xpath( "//div[@id='fileBrowserFiles']/div[2]/div[1]" ), "title", "CDF-548.wcdf" );
    String nameOfCdf548Wcdf = this.elemHelper.GetAttribute( frame, By.xpath( "//div[@id='fileBrowserFiles']/div[2]/div[1]" ), "title" );
    assertEquals( "CDF-548.wcdf", nameOfCdf548Wcdf );

    /*
     * ## Step 3
     */
    // Go to Export Popup Component sample in Edit mode
    this.driver.get( this.baseUrl + "api/repos/%3Apublic%3AIssues%3ACDF%3ACDF-548%3ACDF-548.wcdf/edit" );

    //Save Dashboard
    WebElement saveButton = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "Save" ) );
    assertNotNull( saveButton );
    this.elemHelper.Click( this.driver, By.id( "Save" ) );
    WebElement saveNotify = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.cssSelector( "div.notify-bar-message" ) );
    assertNotNull( saveNotify );
    String saveMessage = this.elemHelper.WaitForElementPresentGetText( this.driver, By.cssSelector( "div.notify-bar-message" ) );
    assertEquals( "Dashboard saved successfully", saveMessage );

    /* 
     * ## Step 4 
      */
    //Go to components panel and expand "Others"
    WebElement componentsPanelButton = this.elemHelper.FindElement( this.driver, By.cssSelector( "div.componentsPanelButton" ) );
    assertNotNull( componentsPanelButton );
    componentsPanelButton.click();
    WebElement componentsTable = this.elemHelper.FindElement( this.driver, By.id( "cdfdd-components-components" ) );
    assertNotNull( componentsTable );
    WebElement othersExpander = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='table-cdfdd-components-components']//tr[@id='OTHERCOMPONENTS']/td/span" ) );
    assertNotNull( othersExpander );
    othersExpander.click();

    //Select Export Popup Component
    WebElement exportPopupElement = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='table-cdfdd-components-components']//td[contains(text(),'exportPopup')]" ) );
    assertNotNull( exportPopupElement );
    exportPopupElement.click();

    //Open options for Chart Component to Export and assert "chart" and "countryChart"
    WebElement chartExportProperty = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='table-cdfdd-components-properties']//td[@title='Id for Chart Component to Export']/../td[2]" ) );
    assertNotNull( chartExportProperty );
    chartExportProperty.click();
    WebElement chartExportInput = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='table-cdfdd-components-properties']//td[@title='Id for Chart Component to Export']/../td[2]/form/input" ) );
    assertNotNull( chartExportInput );
    chartExportInput.clear();
    Robot robot;
    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    WebElement chartExportOptions = this.elemHelper.FindElement( this.driver, By.xpath( "//body/ul" ) );
    assertNotNull( chartExportOptions );
    String chartOption1 = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//body/ul/li/a" ) );
    String chartOption2 = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//body/ul/li[2]/a" ) );
    assertEquals( "chart", chartOption1 );
    assertEquals( "countryChart", chartOption2 );
    this.elemHelper.Click( this.driver, By.xpath( "//body/ul/li/a" ) );

    /* 
     * ## Step 5 
      */
    //Open options for Data Component to Export and assert "table", "chart" and "countryChart"
    WebElement dataExportProperty = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='table-cdfdd-components-properties']//td[@title='Id for Data Component to Export']/../td[2]" ) );
    assertNotNull( dataExportProperty );
    dataExportProperty.click();
    WebElement dataExportInput = this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='table-cdfdd-components-properties']//td[@title='Id for Data Component to Export']/../td[2]/form/input" ) );
    assertNotNull( dataExportInput );
    dataExportInput.clear();
    Robot robot1;
    try {
      robot1 = new Robot();
      robot1.keyPress( KeyEvent.VK_DOWN );
      robot1.keyRelease( KeyEvent.VK_DOWN );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    WebElement dataExportOptions = this.elemHelper.FindElement( this.driver, By.xpath( "//body/ul" ) );
    assertNotNull( dataExportOptions );
    String dataOption1 = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//body/ul/li/a" ) );
    String dataOption2 = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//body/ul/li[2]/a" ) );
    String dataOption3 = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//body/ul/li[3]/a" ) );
    assertEquals( "table", dataOption1 );
    assertEquals( "chart", dataOption2 );
    assertEquals( "countryChart", dataOption3 );

    /* 
     * ## Step 6 
     */
    this.driver.get( this.baseUrl + "api/repos/%3Apublic%3AIssues%3ACDF%3ACDF-548%3ACDF-548.wcdf/generatedContent" );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Click export Button
    WebElement exportChartButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='export']/div" ) );
    assertNotNull( exportChartButton );
    exportChartButton.click();

    //Assert popup and click Export Chart link
    WebElement exportChartLink = this.elemHelper.FindElement( this.driver, By.cssSelector( "div.exportElement" ) );
    assertNotNull( exportChartLink );
    exportChartLink.click();

    //Assert chart popup
    WebElement exportChartPopup = this.elemHelper.FindElement( this.driver, By.id( "fancybox-content" ) );
    assertNotNull( exportChartPopup );

    // Check URL of displayed image
    String chartSRCUrl = this.elemHelper.GetAttribute( this.driver, By.xpath( "//div[@id='fancybox-content']/div/div/div/div[2]/img" ), "src" );
    assertEquals( this.baseUrl + "plugin/cgg/api/services/draw?outputType=png&script=%2Fpublic%2FIssues%2FCDF%2FCDF-548%2Fchart.js&paramcountries=France&paramcountries=USA&paramwidth=400&paramheight=400", chartSRCUrl );
    assertEquals( 200, HttpUtils.GetResponseCode( chartSRCUrl, "admin", "password" ) );

    // Export chart and assert export was successful
    WebElement chartExportButton = this.elemHelper.FindElement( this.driver, By.cssSelector( "div.exportChartPopupButton.exportChartOkButton" ) );
    assertNotNull( chartExportButton );

    //Click export and assert file is correctly downloaded
    try {
      //Delete the existence if exist
      new File( this.exportFilePath ).delete();

      //Click to export
      chartExportButton.click();

      //Wait for file to be created in the destination dir
      DirectoryWatcher dw = new DirectoryWatcher();
      dw.WatchForCreate( this.downloadDir );

      //Check if the file really exist
      File exportFile = new File( this.exportFilePath );
      // assertTrue(exportFile.exists());

      //Wait for the file to be downloaded totally
      for ( int i = 0; i < 50; i++ ) { //we only try 50 times == 5000 ms
        long nSize = FileUtils.sizeOf( exportFile );
        //Since the file always contents the same data, we wait for the expected bytes
        if ( nSize >= 10000 ) {
          break;
        }
        this.log.info( "BeforeSleep " + nSize );
        Thread.sleep( 100 );
      }

      this.log.info( "File size :" + FileUtils.sizeOf( exportFile ) );

      //Check if the file downloaded is the expected
      String md5 = DigestUtils.md5Hex( Files.readAllBytes( exportFile.toPath() ) );
      assertEquals( md5, "c8c3ad02461f7b5c8a36ee2aec0eca95" );

      //The delete file
      DeleteFile();

    } catch ( Exception e ) {
      this.log.error( e.getMessage() );
    }

    // Close dialog box
    this.elemHelper.Click( this.driver, By.id( "fancybox-close" ) );
    assertTrue( this.elemHelper.WaitForElementNotPresent( this.driver, By.xpath( "//div[@id='fancybox-content']/div/div/div/div/div[1]" ) ) );

    /* 
     * ## Step 7
     */
    this.elemHelper.WaitForElementInvisibility( this.driver, By.cssSelector( "div.exportElement" ) );
    //Click export Button
    WebElement exportCountryChartButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='export2']/div" ) );
    assertNotNull( exportCountryChartButton );
    exportCountryChartButton.click();

    //Assert popup and click Export Chart link
    WebElement exportCountryChartLink = this.elemHelper.FindElement( this.driver, By.xpath( "//div[contains(text(),'Export Chart2')]" ) );
    assertNotNull( exportCountryChartLink );
    exportCountryChartLink.click();

    //Assert chart popup
    WebElement exportCountryChartPopup = this.elemHelper.FindElement( this.driver, By.id( "fancybox-content" ) );
    assertNotNull( exportCountryChartPopup );

    // Check URL of displayed image
    String countryChartSRCUrl = this.elemHelper.GetAttribute( this.driver, By.xpath( "//div[@id='fancybox-content']/div/div/div/div[2]/img" ), "src" );
    assertEquals( this.baseUrl + "plugin/cgg/api/services/draw?outputType=svg&script=%2Fpublic%2FIssues%2FCDF%2FCDF-548%2FcountryChart.js&paramwidth=400&paramheight=300", countryChartSRCUrl );
    assertEquals( 200, HttpUtils.GetResponseCode( countryChartSRCUrl, "admin", "password" ) );

    // Export chart and assert export was successful
    WebElement countryChartExportButton = this.elemHelper.FindElement( this.driver, By.cssSelector( "div.exportChartPopupButton.exportChartOkButton" ) );
    assertNotNull( countryChartExportButton );

    //Click export and assert file is correctly downloaded
    try {
      //Delete the existence if exist
      new File( this.exportFilePath2 ).delete();

      //Click to export
      countryChartExportButton.click();

      //Wait for file to be created in the destination dir
      DirectoryWatcher dw = new DirectoryWatcher();
      dw.WatchForCreate( this.downloadDir );

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
      assertEquals( md5, "c0d04625306dd1f32afed115c0bf94be" );

      //The delete file
      DeleteFile();

    } catch ( Exception e ) {
      this.log.error( e.getMessage() );
    }

    // Close dialog box
    this.elemHelper.Click( this.driver, By.id( "fancybox-close" ) );
    assertTrue( this.elemHelper.WaitForElementNotPresent( this.driver, By.xpath( "//div[@id='fancybox-content']/div/div/div/div/div[1]" ) ) );

  }

}
