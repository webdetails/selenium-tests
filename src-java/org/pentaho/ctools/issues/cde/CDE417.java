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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.HttpUtils;
import org.pentaho.ctools.utils.PUCSettings;
import org.pentaho.ctools.utils.ScreenshotTestRule;
import org.pentaho.gui.web.puc.BrowseFiles;

/**
 * The script is testing the issue: - http://jira.pentaho.com/browse/CDE-417
 *
 * and the automation test is described: - http://jira.pentaho.com/browse/QUALITY-1017
 *
 * NOTE To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test: 'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDE417 {

  // Instance of the driver (browser emulator)
  private static WebDriver DRIVER;
  // The base url to be append the relative url in test
  private static String BASE_URL;
  // Log instance
  private static Logger LOG = LogManager.getLogger( CDE417.class );
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( DRIVER );

  @BeforeClass
  public static void setUpClass() {
    LOG.info( "setUp##" + CDE417.class.getSimpleName() );
    DRIVER = CToolsTestSuite.getDriver();
    BASE_URL = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name: 
   *   Assert Popup Export shows the chart on the popup
   *
   * Description: 
   *   The test pretends validate the CDE-417 issue, so when user clicks to export chart, popup shown has
   *   chart preview. Also, to validate the CDE-424 issue we'll delete the .js file prior to opening the dashboard and
   *   saving the dashboard should create the .js file again and enable exporting.
   *
   * Steps:
   * 424
   *    1. Open PUC and click Browse Files
   *    2. Go to dashboard folder, click BarChart.js file and click Move To Trash
   *    3. Open Export Popup Sample and save Dashboard
   * 417
   *    4. Assert elements on dashboard
   *    5. Click to export chart as PNG, click export, assert chart is shown
   *    6. Click to export chart as SVG, click export, assert chart is shown
   * 
   * @throws InterruptedException
   *
   */
  @Test( timeout = 360000 )
  public void tc01_PopupExportComponent_PreviewerRendersChart() throws InterruptedException {
    LOG.info( "tc01_PopupExportComponent_PreviewerRendersChart" );

    /*
     * ## Step 1
     */
    // Show Hidden Files
    BrowseFiles browser = new BrowseFiles( DRIVER );
    if ( !PUCSettings.SHOWHIDDENFILES ) {
      browser.CheckShowHiddenFiles();
    }

    /*
     * ## Step 2
     */
    String pathBarChart = "/public/plugin-samples/pentaho-cdf-dd/tests/ExportPopup/BarChart.js";
    String pathExportPopupBarChart = "/public/plugin-samples/pentaho-cdf-dd/tests/ExportPopup/ExportPopupComponent_BarChart.js";
    boolean fileDeleteBarChart = browser.DeleteFile( pathBarChart );
    boolean fileDeleteExportPopupBarChart = browser.DeleteFile( pathExportPopupBarChart );
    assertTrue( fileDeleteBarChart );
    assertTrue( fileDeleteExportPopupBarChart );

    DRIVER.switchTo().defaultContent();
    WebDriver frame = DRIVER.switchTo().frame( "browser.perspective" );
    ElementHelper.WaitForAttributeValue( frame, By.xpath( "//div[@id='fileBrowserFiles']/div[2]/div[1]" ), "title", "ExportPopupComponent.cda" );
    String nameOfExportPopupCda = ElementHelper.GetAttribute( frame, By.xpath( "//div[@id='fileBrowserFiles']/div[2]/div[1]" ), "title" );
    assertEquals( "ExportPopupComponent.cda", nameOfExportPopupCda );

    /*
     * ## Step 3
     */
    // Go to Export Popup Component sample
    DRIVER.get( BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3AExportPopup%3AExportPopupComponent.wcdf/edit" );
    WebElement element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.id( "Save" ) );
    assertNotNull( element );
    ElementHelper.Click( DRIVER, By.id( "Save" ) );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.cssSelector( "div.notify-bar-message" ) );
    assertNotNull( element );
    String saveMessage = ElementHelper.WaitForElementPresentGetText( DRIVER, By.cssSelector( "div.notify-bar-message" ) );
    assertEquals( "Dashboard saved successfully", saveMessage );

    /* 
     * ## Step 4 
     */
    DRIVER.get( BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3AExportPopup%3AExportPopupComponent.wcdf/generatedContent" );
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    // Assert chart and export buttons
    WebElement elem = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='ChartExportPNGExporting']/div" ) );
    assertNotNull( elem );
    elem = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='ChartExportSVGExporting']/div" ) );
    assertNotNull( elem );
    String serie1 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='TheChartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][2]/*[name()='text']" ) );
    String serie2 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='TheChartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][2]/*[name()='g'][2]/*[name()='text']" ) );
    String serie3 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='TheChartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][3]/*[name()='g'][2]/*[name()='text']" ) );
    String serie4 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='TheChartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][4]/*[name()='g'][2]/*[name()='text']" ) );
    String serie5 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='TheChartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][5]/*[name()='g'][2]/*[name()='text']" ) );
    assertEquals( "Car", serie1 );
    assertEquals( "Bike", serie2 );
    assertEquals( "Ship", serie3 );
    assertEquals( "Plane", serie4 );
    assertEquals( "Train", serie5 );

    /* 
     * ## Step 5 
     */
    ElementHelper.Click( DRIVER, By.xpath( "//div[@id='ChartExportPNGExporting']/div" ) );
    elem = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@class='exportElement']" ) );
    assertNotNull( elem );
    elem.click();
    String exportOptions = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='fancybox-content']/div/div/div/div/div[1]" ) );
    String exportOptions1 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='fancybox-content']/div/div/div/div/div[2]" ) );
    String exportOptions2 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='fancybox-content']/div/div/div/div/div[3]" ) );
    String exportOptions3 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='fancybox-content']/div/div/div/div/div[4]" ) );
    String exportOptions4 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='fancybox-content']/div/div/div/div/div[5]" ) );
    String exportOptions5 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='fancybox-content']/div/div/div/div/div[8]" ) );
    assertEquals( "Export Options", exportOptions );
    assertEquals( "Small", exportOptions1 );
    assertEquals( "Medium", exportOptions2 );
    assertEquals( "Large", exportOptions3 );
    assertEquals( "Custom", exportOptions4 );
    assertEquals( "Export", exportOptions5 );
    // Check URL of displayed image
    String chartSRCUrl = ElementHelper.GetAttribute( DRIVER, By.xpath( "//div[@id='fancybox-content']/div/div/div/div[2]/img" ), "src" );
    assertEquals( BASE_URL + "plugin/cgg/api/services/draw?outputType=png&script=%2Fpublic%2Fplugin-samples%2Fpentaho-cdf-dd%2Ftests%2FExportPopup%2FBarChart.js&paramwidth=350&paramheight=200", chartSRCUrl );
    assertEquals( 200, HttpUtils.GetResponseCode( chartSRCUrl, "admin", "password" ) );
    // Close dialog box
    ElementHelper.Click( DRIVER, By.id( "fancybox-close" ) );
    assertTrue( ElementHelper.WaitForElementNotPresent( DRIVER, By.xpath( "//div[@id='fancybox-content']/div/div/div/div/div[1]" ) ) );

    /* 
     * ## Step 6 
     */
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div/div[@class='exportElement']" ) );
    ElementHelper.Click( DRIVER, By.xpath( "//div[@id='ChartExportSVGExporting']/div" ) );
    elem = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[9]/div[@class='exportElement']" ) );
    assertNotNull( elem );
    elem.click();
    String exportOptionsSvg = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='fancybox-content']/div/div/div/div/div[1]" ) );
    String exportOptionsSvg1 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='fancybox-content']/div/div/div/div/div[2]" ) );
    String exportOptionsSvg2 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='fancybox-content']/div/div/div/div/div[3]" ) );
    String exportOptionsSvg3 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='fancybox-content']/div/div/div/div/div[4]" ) );
    String exportOptionsSvg4 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='fancybox-content']/div/div/div/div/div[5]" ) );
    String exportOptionsSvg5 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='fancybox-content']/div/div/div/div/div[8]" ) );
    assertEquals( "Export Options", exportOptionsSvg );
    assertEquals( "Small", exportOptionsSvg1 );
    assertEquals( "Medium", exportOptionsSvg2 );
    assertEquals( "Large", exportOptionsSvg3 );
    assertEquals( "Custom", exportOptionsSvg4 );
    assertEquals( "Export", exportOptionsSvg5 );
    // Check URL of displayed image
    String chartSvgSRCUrl = ElementHelper.GetAttribute( DRIVER, By.xpath( "//div[@id='fancybox-content']/div/div/div/div[2]/img" ), "src" );
    assertEquals( BASE_URL + "plugin/cgg/api/services/draw?outputType=svg&script=%2Fpublic%2Fplugin-samples%2Fpentaho-cdf-dd%2Ftests%2FExportPopup%2FBarChart.js&paramwidth=350&paramheight=200", chartSvgSRCUrl );
    assertEquals( 200, HttpUtils.GetResponseCode( chartSvgSRCUrl, "admin", "password" ) );
    // Close dialog box
    ElementHelper.Click( DRIVER, By.id( "fancybox-close" ) );
    assertTrue( ElementHelper.WaitForElementNotPresent( DRIVER, By.xpath( "//div[@id='fancybox-content']/div/div/div/div/div[1]" ) ) );
  }

  @AfterClass
  public static void tearDownClass() {
    LOG.info( "tearDown##" + CDE417.class.getSimpleName() );
  }
}
