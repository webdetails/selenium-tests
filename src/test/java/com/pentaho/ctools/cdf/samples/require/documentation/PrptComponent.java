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
package com.pentaho.ctools.cdf.samples.require.documentation;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.DirectoryWatcher;
import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with Prpt Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class PrptComponent extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( PrptComponent.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Display Content
   * Description:
   *    Check if the contents present in page is the expected.
   * Steps:
   *    1. Check title web page and sample title.
   */
  @Test
  public void tc0_OpenSamplePage_Display() {
    this.log.info( "tc0_OpenSamplePage_Display" );

    // The URL for the PrptComponent under CDF samples
    // This sample is in: Public/plugin-samples/CDF/Require Samples/Documentation/Component Reference/Core Components/PrptComponent
    this.elemHelper.Get( BaseTest.driver, PageUrl.PRPT_COMPONENT_REQUIRE );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.cssSelector( "div.blockUI.blockOverlay" ), 10 );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    //Check if the some elements are already displayed
    //Check if element with "Line" is visible
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.cssSelector( "div#sampleObject iframe" ) );
    final WebDriver frameSamplePrpt = this.elemHelper.SwitchToFrame( BaseTest.driver, "sampleObject_prptFrame" );
    final WebElement elemLine = this.elemHelper.WaitForElementPresenceAndVisible( frameSamplePrpt, By.cssSelector( "div.parameter-label" ), 45 );
    Assert.assertNotNull( elemLine );
    //Check if element with "LINE: Classic Cars" is visible
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.cssSelector( "iframe#reportContent" ) );
    final WebDriver frameReportContent = this.elemHelper.SwitchToFrame( frameSamplePrpt, "reportContent" );
    final WebElement elemLineClassic = this.elemHelper.WaitForElementPresenceAndVisible( frameReportContent, By.xpath( "//tr/td" ), 45 );
    Assert.assertNotNull( elemLineClassic );
    //Back to root
    this.elemHelper.SwitchToDefault( BaseTest.driver );
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Display Content
   * Description:
   *    Check if the contents present in page is the expected.
   * Steps:
   *    1. Check title web page and sample title.
   */
  @Test(
      dependsOnMethods = "com.pentaho.ctools.cdf.samples.require.documentation.PrptComponent.tc0_OpenSamplePage_Display" )
  public void tc1_PageContent_DisplayTitle() {
    this.log.info( "tc1_PageContent_DisplayTitle" );

    /*
     * ## Step 1
     */
    // Wait for title become visible and with value 'Community Dashboard Framework'
    final String expectedPageTitle = "Community Dashboard Framework";
    final String actualPageTitle = this.elemHelper.WaitForTitle( BaseTest.driver, expectedPageTitle );
    // Wait for visibility of 'PrptComponent'
    final String expectedSampleTitle = "PrptComponent";
    final String actualSampleTitle = this.elemHelper.WaitForTextDifferentEmpty( BaseTest.driver, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) );

    // Validate the sample that we are testing is the one
    Assert.assertEquals( actualPageTitle, expectedPageTitle );
    Assert.assertEquals( actualSampleTitle, expectedSampleTitle );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Reload Sample
   * Description:
   *    Reload the sample (not refresh page).
   * Steps:
   *    1. Click in Code and then click in button 'Try me'.
   */
  @Test(
      dependsOnMethods = "com.pentaho.ctools.cdf.samples.require.documentation.PrptComponent.tc1_PageContent_DisplayTitle" )
  public void tc2_ReloadSample_SampleReadyToUse() {
    this.log.info( "tc2_ReloadSample_SampleReadyToUse" );

    /*
     *  ## Step 1
     */
    // Render again the sample
    this.elemHelper.ClickJS( BaseTest.driver, By.xpath( "//div[@id='example']/ul/li[2]/a" ) );
    this.elemHelper.ClickJS( BaseTest.driver, By.xpath( "//div[@id='code']/button" ) );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.cssSelector( "div.blockUI.blockOverlay" ), 10 );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    //Check if the some elements are already displayed
    //Check if element with "Line" is visible
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.cssSelector( "div#sampleObject iframe" ) );
    final WebDriver frameSamplePrpt = this.elemHelper.SwitchToFrame( BaseTest.driver, "sampleObject_prptFrame" );
    final WebElement elemLine = this.elemHelper.WaitForElementPresenceAndVisible( frameSamplePrpt, By.cssSelector( "div.parameter-label" ), 45 );
    Assert.assertNotNull( elemLine );
    //Check if element with "LINE: Classic Cars" is visible
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.cssSelector( "iframe#reportContent" ) );
    final WebDriver frameReportContent = this.elemHelper.SwitchToFrame( frameSamplePrpt, "reportContent" );
    final WebElement elemLineClassic = this.elemHelper.WaitForElementPresenceAndVisible( frameReportContent, By.xpath( "//tr/td" ), 45 );
    Assert.assertNotNull( elemLineClassic );
    //Back to root
    this.elemHelper.SwitchToDefault( BaseTest.driver );

    // Now sample element must be displayed
    Assert.assertTrue( this.elemHelper.FindElement( BaseTest.driver, By.id( "sample" ) ).isDisplayed() );

    //Check the number of divs with id 'SampleObject'
    //Hence, we guarantee when click Try Me the previous div is replaced
    final int nSampleObject = this.elemHelper.FindElements( BaseTest.driver, By.id( "sampleObject" ) ).size();
    Assert.assertEquals( nSampleObject, 1 );
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Select HTML and PDF (content type)
   * Description:
   *    The test case pretends validate the result when change for option HTML
   *    and PDF for content-type.
   * Steps:
   *    1. Select PDF
   *    2. Select HTML
   */
  @Test(
      dependsOnMethods = "com.pentaho.ctools.cdf.samples.require.documentation.PrptComponent.tc2_ReloadSample_SampleReadyToUse" )
  public void tc3_SelectHTMLAndPDF_PageDisplayedAccording() {
    this.log.info( "tc3_SelectHTMLAndPDF_PageDisplayedAccording" );

    /*
     * ## Step 1
     */
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.cssSelector( "div#sampleObject iframe" ) );
    this.elemHelper.SwitchToFrame( BaseTest.driver, "sampleObject_prptFrame" );
    //Check presence of tool bar elements
    Assert.assertNotNull( this.elemHelper.FindElement( BaseTest.driver, By.cssSelector( "#toolbar" ) ) );
    final String DropDown = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.cssSelector( ".dijitValidationTextBoxLabel" ) );
    Assert.assertEquals( DropDown, "Maximum" );
    this.log.debug( DropDown );
    Assert.assertNotNull( this.elemHelper.FindElement( BaseTest.driver, By.cssSelector( ".dijitEditorIconParameters" ) ) );
    Assert.assertNotNull( this.elemHelper.FindElement( BaseTest.driver, By.cssSelector( ".dijitEditorIconRefresh" ) ) );
    //Check the Product Name and Output Type
    final WebElement elemtLine = this.elemHelper.WaitForElementPresenceAndVisible( BaseTest.driver, By.cssSelector( "div.parameter-label" ), 45 );
    Assert.assertNotNull( elemtLine );
    final String prodName = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.cssSelector( "div.parameter-label" ) );
    Assert.assertEquals( "Line", prodName );
    Assert.assertNotNull( this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//td/div/div[2]/select" ) ) );
    final String outputTypeName = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@class='parameter']/div[2]/select/../../div" ) );
    Assert.assertEquals( "Output Type", outputTypeName );
    Assert.assertNotNull( this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@class='parameter']/div[2]/select" ) ) );
    //Check for View Report button
    final String buttonName = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.cssSelector( "button.pentaho-button" ) );
    Assert.assertEquals( "View Report", buttonName );
    //Check the generated image
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.cssSelector( "iframe#reportContent" ) );
    final WebDriver driverReportContent = this.elemHelper.SwitchToFrame( BaseTest.driver, "reportContent" );
    final WebElement elemReport = this.elemHelper.WaitForElementPresenceAndVisible( driverReportContent, By.xpath( "//tbody/tr" ) );
    Assert.assertNotNull( elemReport );
    this.elemHelper.WaitForTextPresence( driverReportContent, By.xpath( "//tbody/tr" ), "LINE: Classic Cars" );
    final String strTitle = this.elemHelper.WaitForElementPresentGetText( driverReportContent, By.xpath( "//tbody/tr" ) );
    Assert.assertEquals( "LINE: Classic Cars", strTitle );
    this.elemHelper.WaitForTextPresence( driverReportContent, By.xpath( "//tbody/tr[3]/td" ), "Autoart Studio Design" );
    final String strCompany = this.elemHelper.WaitForElementPresentGetText( driverReportContent, By.xpath( "//tbody/tr[3]/td" ) );
    Assert.assertEquals( "Autoart Studio Design", strCompany );
    this.elemHelper.WaitForTextPresence( driverReportContent, By.xpath( "//tbody/tr[5]/td[3]/a" ), "1958 Chevy Corvette Limited Edition" );
    final String strValue = this.elemHelper.WaitForElementPresentGetText( driverReportContent, By.xpath( "//tbody/tr[5]/td[3]/a" ) );
    Assert.assertEquals( "1958 Chevy Corvette Limited Edition", strValue );
    final String strHref = this.elemHelper.GetAttribute( driverReportContent, By.xpath( "//tbody/tr[5]/td[3]/a" ), "href" );
    Assert.assertEquals( "http://images.google.com/images?q=1958%20Chevy%20Corvette%20Limited%20Edition", strHref );
  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name:
   *    Toggle Prompt Panel
   *
   * Description:
   *    The test case pretends disable and enable the prompt panel.
   *
   * Steps:
   *    1. Enable prompt panel
   *    2. Disable prompt panel
   */
  @Test(
      dependsOnMethods = "com.pentaho.ctools.cdf.samples.require.documentation.PrptComponent.tc3_SelectHTMLAndPDF_PageDisplayedAccording" )
  public void tc4_TogglePromptPanel_PromptPanelEnableDisable() {
    this.log.info( "tc4_TogglePromptPanel_PromptPanelEnableDisable" );
    this.elemHelper.SwitchToDefault( BaseTest.driver );

    /*
     * ## Step 1
     */
    this.elemHelper.FocusElement( BaseTest.driver, By.id( "selectorObject" ) );
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.cssSelector( "div#sampleObject iframe" ) );
    this.elemHelper.SwitchToFrame( BaseTest.driver, "sampleObject_prptFrame" );
    Assert.assertNotNull( this.elemHelper.FindElement( BaseTest.driver, By.id( "reportControlPanel" ) ) );
    this.elemHelper.Click( BaseTest.driver, By.xpath( "//span[@id='toolbar-parameterToggle']/span" ) );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.id( "reportControlPanel" ) );
    Assert.assertFalse( this.elemHelper.WaitForElementNotPresent( BaseTest.driver, By.id( "reportControlPanel" ), 2 ) );

    /*
     * ## Step 2
     */
    this.elemHelper.Click( BaseTest.driver, By.xpath( "//span[@id='toolbar-parameterToggle']/span" ) );
    Assert.assertNotNull( this.elemHelper.FindElement( BaseTest.driver, By.id( "reportControlPanel" ) ) );
  }

  /**
   * ############################### Test Case 5 ###############################
   *
   * Test Case Name:
   *    Select Several Products
   * Description:
   *    The test case pretends to validate that when we select a product a new
   *    report is generated.
   * Steps:
   *    1. Disable Classic Cars and assert results
   *    2. Enable Motorcycles and assert results
   *
   */
  @Test(
      dependsOnMethods = "com.pentaho.ctools.cdf.samples.require.documentation.PrptComponent.tc4_TogglePromptPanel_PromptPanelEnableDisable" )
  public void tc5_SelectSeveralProducts_ReportIsRefreshed() {
    this.log.info( "tc5_SelectSeveralProducts_ReportIsRefreshed" );
    this.elemHelper.SwitchToDefault( BaseTest.driver );
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.cssSelector( "div#sampleObject iframe" ) );
    this.elemHelper.SwitchToFrame( BaseTest.driver, "sampleObject_prptFrame" );

    /*
     * ## Step 1
     */
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( BaseTest.driver, By.xpath( "//div[@class='pentaho-toggle-button-container']/div/div/button" ) );
    Assert.assertNotNull( element );
    String text = element.getText();
    Assert.assertEquals( "Classic Cars", text );
    element.click();
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.id( "glasspane" ), 5 );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.id( "glasspane" ) );
    this.elemHelper.SwitchToFrame( BaseTest.driver, "reportContent" );
    element = this.elemHelper.WaitForElementPresenceAndVisible( BaseTest.driver, By.xpath( "//tbody/tr" ) );
    Assert.assertNotNull( element );
    this.elemHelper.WaitForTextPresence( BaseTest.driver, By.xpath( "//tbody/tr" ), "LINE: Planes" );
    text = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//tbody/tr" ) );
    Assert.assertEquals( "LINE: Planes", text );
    this.elemHelper.WaitForTextPresence( BaseTest.driver, By.xpath( "//tbody/tr[3]/td" ), "Autoart Studio Design" );
    text = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//tbody/tr[3]/td" ) );
    Assert.assertEquals( "Autoart Studio Design", text );
    this.elemHelper.WaitForTextPresence( BaseTest.driver, By.xpath( "//tbody/tr[5]/td[3]/a" ), "P-51-D Mustang" );
    text = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//tbody/tr[5]/td[3]/a" ) );
    Assert.assertEquals( "P-51-D Mustang", text );
    text = this.elemHelper.GetAttribute( BaseTest.driver, By.xpath( "//tbody/tr[5]/td[3]/a" ), "href" );
    Assert.assertEquals( "http://images.google.com/images?q=P-51-D%20Mustang", text );

    /*
     * ## Step 2
     */
    this.elemHelper.SwitchToDefault( BaseTest.driver );
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.cssSelector( "div#sampleObject iframe" ) );
    this.elemHelper.SwitchToFrame( BaseTest.driver, "sampleObject_prptFrame" );
    element = this.elemHelper.WaitForElementPresenceAndVisible( BaseTest.driver, By.xpath( "//div[@class='pentaho-toggle-button-container']/div/div[2]/button" ) );
    Assert.assertNotNull( element );
    text = element.getText();
    Assert.assertEquals( "Motorcycles", text );
    element.click();
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.id( "glasspane" ), 5 );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.id( "glasspane" ) );
    this.elemHelper.SwitchToFrame( BaseTest.driver, "reportContent" );
    element = this.elemHelper.WaitForElementPresenceAndVisible( BaseTest.driver, By.xpath( "//tbody/tr" ) );
    Assert.assertNotNull( element );
    this.elemHelper.WaitForTextPresence( BaseTest.driver, By.xpath( "//tbody/tr" ), "LINE: Motorcycles" );
    text = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//tbody/tr" ) );
    Assert.assertEquals( "LINE: Motorcycles", text );
    this.elemHelper.WaitForTextPresence( BaseTest.driver, By.xpath( "//tbody/tr[3]/td" ), "Autoart Studio Design" );
    text = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//tbody/tr[3]/td" ) );
    Assert.assertEquals( "Autoart Studio Design", text );
    this.elemHelper.WaitForTextPresence( BaseTest.driver, By.xpath( "//tbody/tr[5]/td[3]/a" ), "1997 BMW F650 ST" );
    text = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//tbody/tr[5]/td[3]/a" ) );
    Assert.assertEquals( "1997 BMW F650 ST", text );
    text = this.elemHelper.GetAttribute( BaseTest.driver, By.xpath( "//tbody/tr[5]/td[3]/a" ), "href" );
    Assert.assertEquals( "http://images.google.com/images?q=1997%20BMW%20F650%20ST", text );

  }

  /**
   * ############################### Test Case 6 ###############################
   *
   * Test Case Name:
   *    Output Type
   * Description:
   *    We pretend to test all Output Type options.
   * Steps:
   *    1. Select: HTML (Paginated)
   *    2. Select: HTML (Single Page)
   *    3. Select: PDF
   *    4. Select: Excel
   *    5. Select: Excel 2007
   *    6. Select: Comma Separated Value
   *    7. Select: Rich-Text-Format
   *    8. Select: Text
   * @throws InterruptedException
   */
  @Test(
      dependsOnMethods = "com.pentaho.ctools.cdf.samples.require.documentation.PrptComponent.tc5_SelectSeveralProducts_ReportIsRefreshed" )
  public void tc6_SelectAllOutputTypeOptions_DialogBoxIsRaised() {
    this.log.info( "tc6_SelectAllOutputTypeOptions_DialogBoxIsRaised" );
    this.elemHelper.SwitchToDefault( BaseTest.driver );

    final DirectoryWatcher dw = new DirectoryWatcher();

    /*
     * ## Step 1
     */
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.cssSelector( "div#sampleObject iframe" ) );
    this.elemHelper.SwitchToFrame( BaseTest.driver, "sampleObject_prptFrame" );
    Select select = new Select( this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@class='parameter']/div[2]/select" ) ) );
    select.selectByValue( "table/html;page-mode=page" );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.id( "glasspane" ) );
    //Check the generated image
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.cssSelector( "iframe#reportContent" ) );
    this.elemHelper.SwitchToFrame( BaseTest.driver, "reportContent" );
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( BaseTest.driver, By.xpath( "//tbody/tr" ) );
    Assert.assertNotNull( element );
    this.elemHelper.WaitForTextPresence( BaseTest.driver, By.xpath( "//tbody/tr" ), "LINE: Motorcycles" );
    String text = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//tbody/tr" ) );
    Assert.assertEquals( "LINE: Motorcycles", text );
    this.elemHelper.WaitForTextPresence( BaseTest.driver, By.xpath( "//tbody/tr[3]/td" ), "Autoart Studio Design" );
    text = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//tbody/tr[3]/td" ) );
    Assert.assertEquals( "Autoart Studio Design", text );
    this.elemHelper.WaitForTextPresence( BaseTest.driver, By.xpath( "//tbody/tr[5]/td[3]/a" ), "1997 BMW F650 ST" );
    text = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//tbody/tr[5]/td[3]/a" ) );
    Assert.assertEquals( "1997 BMW F650 ST", text );
    text = this.elemHelper.GetAttribute( BaseTest.driver, By.xpath( "//tbody/tr[5]/td[3]/a" ), "href" );
    Assert.assertEquals( "http://images.google.com/images?q=1997%20BMW%20F650%20ST", text );

    /*
     * ## Step 2
     */
    this.elemHelper.SwitchToDefault( BaseTest.driver );
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.cssSelector( "div#sampleObject iframe" ) );
    this.elemHelper.SwitchToFrame( BaseTest.driver, "sampleObject_prptFrame" );
    select = new Select( this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@class='parameter']/div[2]/select" ) ) );
    select.selectByValue( "table/html;page-mode=stream" );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.id( "glasspane" ) );
    //Check the generated image
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.cssSelector( "iframe#reportContent" ) );
    this.elemHelper.SwitchToFrame( BaseTest.driver, "reportContent" );
    element = this.elemHelper.WaitForElementPresenceAndVisible( BaseTest.driver, By.xpath( "//tbody/tr" ) );
    Assert.assertNotNull( element );
    this.elemHelper.WaitForTextPresence( BaseTest.driver, By.xpath( "//tbody/tr" ), "LINE: Motorcycles" );
    text = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//tbody/tr" ) );
    Assert.assertEquals( "LINE: Motorcycles", text );
    this.elemHelper.WaitForTextPresence( BaseTest.driver, By.xpath( "//tbody/tr[3]/td" ), "Autoart Studio Design" );
    text = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//tbody/tr[3]/td" ) );
    Assert.assertEquals( "Autoart Studio Design", text );
    this.elemHelper.WaitForTextPresence( BaseTest.driver, By.xpath( "//tbody/tr[5]/td[3]/a" ), "1997 BMW F650 ST" );
    text = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//tbody/tr[5]/td[3]/a" ) );
    Assert.assertEquals( "1997 BMW F650 ST", text );
    text = this.elemHelper.GetAttribute( BaseTest.driver, By.xpath( "//tbody/tr[5]/td[3]/a" ), "href" );
    Assert.assertEquals( "http://images.google.com/images?q=1997%20BMW%20F650%20ST", text );

    /*
     * ## Step 3
     */
    this.elemHelper.SwitchToDefault( BaseTest.driver );
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.cssSelector( "div#sampleObject iframe" ) );
    this.elemHelper.SwitchToFrame( BaseTest.driver, "sampleObject_prptFrame" );
    select = new Select( this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@class='parameter']/div[2]/select" ) ) );
    select.selectByValue( "pageable/pdf" );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.id( "glasspane" ) );
    //Check the generated image
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.cssSelector( "iframe#reportContent" ) );
    this.elemHelper.SwitchToFrame( BaseTest.driver, "reportContent" );
    final WebElement elemTextLayer = this.elemHelper.WaitForElementPresenceAndVisible( BaseTest.driver, By.cssSelector( "div#viewerContainer div#viewer.pdfViewer div.page div.textLayer" ) );
    Assert.assertNotNull( elemTextLayer );
    final WebElement elemTextLayerdiv1 = this.elemHelper.WaitForElementPresenceAndVisible( BaseTest.driver, By.cssSelector( "div#viewerContainer div#viewer.pdfViewer div.page div.textLayer span" ) );
    Assert.assertNotNull( elemTextLayerdiv1 );
    final WebElement elemTextLayerdiv2 = this.elemHelper.WaitForElementPresenceAndVisible( BaseTest.driver, By.cssSelector( "div#viewerContainer div#viewer.pdfViewer div.page div.textLayer span:nth-child(2)" ) );
    Assert.assertNotNull( elemTextLayerdiv2 );
    final WebElement elemTextLayerdiv3 = this.elemHelper.WaitForElementPresenceAndVisible( BaseTest.driver, By.cssSelector( "div#viewerContainer div#viewer.pdfViewer div.page div.textLayer span:nth-child(3)" ) );
    Assert.assertNotNull( elemTextLayerdiv3 );
    this.elemHelper.WaitForTextPresence( BaseTest.driver, By.cssSelector( "div#viewerContainer div#viewer.pdfViewer div.page div.textLayer span" ), "L I N E :", 60 );
    text = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.cssSelector( "div#viewerContainer div#viewer.pdfViewer div.page div.textLayer span" ) );
    Assert.assertEquals( "L I N E :", text );
    this.elemHelper.WaitForTextPresence( BaseTest.driver, By.cssSelector( "div#viewerContainer div#viewer.pdfViewer div.page div.textLayer span:nth-child(2)" ), "M o t o r c y c l e s", 60 );
    text = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.cssSelector( "div#viewerContainer div#viewer.pdfViewer div.page div.textLayer span:nth-child(2)" ) );
    Assert.assertEquals( "M o t o r c y c l e s", text );
    this.elemHelper.WaitForTextPresence( BaseTest.driver, By.cssSelector( "div#viewerContainer div#viewer.pdfViewer div.page div.textLayer span:nth-child(3)" ), "MSRP", 60 );
    text = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.cssSelector( "div#viewerContainer div#viewer.pdfViewer div.page div.textLayer span:nth-child(3)" ) );
    Assert.assertEquals( "MSRP", text );

    /*
     * ## Step 4
     */
    this.elemHelper.SwitchToDefault( BaseTest.driver );
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.cssSelector( "div#sampleObject iframe" ) );
    this.elemHelper.SwitchToFrame( BaseTest.driver, "sampleObject_prptFrame" );
    new File( BaseTest.downloadDir + "\\InventorybyLine.xls" ).delete();
    select = new Select( this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@class='parameter']/div[2]/select" ) ) );
    select.selectByValue( "table/excel;page-mode=flow" );
    //Wait for file to be created in the destination dir
    dw.WatchForCreate( BaseTest.downloadDir );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.id( "glasspane" ) );
    Assert.assertTrue( new File( BaseTest.downloadDir + "\\InventorybyLine.xls" ).exists() );
    new File( BaseTest.downloadDir + "\\InventorybyLine.xls" ).delete();

    /*
     * ## Step 5
     */
    new File( BaseTest.downloadDir + "/InventorybyLine.xlsx" ).delete();
    select = new Select( this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@class='parameter']/div[2]/select" ) ) );
    select.selectByValue( "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;page-mode=flow" );
    //Wait for file to be created in the destination dir
    dw.WatchForCreate( BaseTest.downloadDir );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.id( "glasspane" ) );
    Assert.assertTrue( new File( BaseTest.downloadDir + "/InventorybyLine.xlsx" ).exists() );
    new File( BaseTest.downloadDir + "/InventorybyLine.xlsx" ).delete();

    /*
     * ## Step 6
     */
    new File( BaseTest.downloadDir + "/InventorybyLine.csv" ).delete();
    select = new Select( this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@class='parameter']/div[2]/select" ) ) );
    select.selectByValue( "table/csv;page-mode=stream" );
    //Wait for file to be created in the destination dir
    dw.WatchForCreate( BaseTest.downloadDir );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.id( "glasspane" ) );
    Assert.assertTrue( new File( BaseTest.downloadDir + "/InventorybyLine.csv" ).exists() );
    new File( BaseTest.downloadDir + "/InventorybyLine.csv" ).delete();

    /*
     * ## Step 7
     */
    new File( BaseTest.downloadDir + "/InventorybyLine.rtf" ).delete();
    select = new Select( this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@class='parameter']/div[2]/select" ) ) );
    select.selectByValue( "table/rtf;page-mode=flow" );
    //Wait for file to be created in the destination dir
    dw.WatchForCreate( BaseTest.downloadDir );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.id( "glasspane" ) );
    Assert.assertTrue( new File( BaseTest.downloadDir + "/InventorybyLine.rtf" ).exists() );
    new File( BaseTest.downloadDir + "/InventorybyLine.rtf" ).delete();

    /*
     * ## Step 8
     */
    this.elemHelper.SwitchToDefault( BaseTest.driver );
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.cssSelector( "div#sampleObject iframe" ) );
    this.elemHelper.SwitchToFrame( BaseTest.driver, "sampleObject_prptFrame" );
    select = new Select( this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@class='parameter']/div[2]/select" ) ) );
    select.selectByValue( "pageable/text" );
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.id( "glasspane" ), 5 );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.id( "glasspane" ) );
    //Check the generated image
    this.elemHelper.WaitForElementPresence( BaseTest.driver, By.cssSelector( "iframe#reportContent" ) );
    final WebDriver reportContentFrame = this.elemHelper.SwitchToFrame( BaseTest.driver, "reportContent" );
    element = this.elemHelper.WaitForElementPresenceAndVisible( reportContentFrame, By.xpath( "//pre" ) );
    Assert.assertNotNull( element );
    text = this.elemHelper.WaitForElementPresentGetText( reportContentFrame, By.xpath( "//pre" ) );
    Assert.assertTrue( text.contains( "LINE: Motorcycles" ) );
  }
}
