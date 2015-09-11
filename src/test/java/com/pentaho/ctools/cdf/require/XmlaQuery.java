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
package com.pentaho.ctools.cdf.require;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.BaseTest;
import com.pentaho.ctools.utils.ElementHelper;

/**
 * Testing the functionalities related with XMLA query.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class XmlaQuery extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  //Log instance
  private final Logger log = LogManager.getLogger( XmlaQuery.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert query results for XMLA first sample
   */
  @Test
  public void tc1_XmlaQuery_SampleResults() {
    this.log.info( "tc1_XmlaQuery_SampleResults" );
    //To set up this test we must enable xmla for SteelWheels datasource
    driver.get( baseUrl );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.FindElement( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );
    this.elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

    //Open File menu and select Manage Data Sources
    WebElement fileMenu = this.elemHelper.FindElement( driver, By.id( "filemenu" ) );
    assertNotNull( fileMenu );
    fileMenu.click();
    WebElement manageSources = this.elemHelper.FindElement( driver, By.id( "manageDatasourceItem" ) );
    assertNotNull( manageSources );
    manageSources.click();
    //Wait for datasources popup to open click "SteelWheels Analysis" and click edit
    WebElement dataPopup = this.elemHelper.FindElement( driver, By.xpath( "//div[@class='pentaho-dialog']" ) );
    assertNotNull( dataPopup );
    WebElement steelSource = this.elemHelper.FindElement( driver, By.xpath( "//div[@class='pentaho-dialog']//tbody//div[contains(text(),'SteelWheels')]" ) );
    assertNotNull( steelSource );
    steelSource.click();
    WebElement settingsButton = this.elemHelper.FindElement( driver, By.xpath( "//div[@class='pentaho-dialog']//div[@id='datasourceDropdownButton']/img" ) );
    assertNotNull( settingsButton );
    settingsButton.click();
    WebElement optionsPopup = this.elemHelper.FindElement( driver, By.xpath( "//div[@class='popupContent']" ) );
    assertNotNull( optionsPopup );
    WebElement editButton = this.elemHelper.FindElement( driver, By.xpath( "//div[@class='popupContent']//td[contains(text(),'Edit...')]" ) );
    assertNotNull( editButton );
    editButton.click();

    //On new popup select "Manual enter" and set Enable Xmla as true
    WebElement editPopup = this.elemHelper.FindElement( driver, By.xpath( "//div[@class='pentaho-dialog'][2]" ) );
    assertNotNull( editPopup );
    WebElement manualButton = this.elemHelper.FindElement( driver, By.xpath( "//div[@class='pentaho-dialog'][2]//table[@id='manualRadio']//input" ) );
    assertNotNull( manualButton );
    manualButton.click();
    WebElement enableXmla = this.elemHelper.FindElement( driver, By.xpath( "//div[@class='pentaho-dialog'][2]//div[@id='analysisPreferencesDeck']//div[contains(text(),'EnableXmla')]" ) );
    assertNotNull( enableXmla );
    enableXmla.click();
    WebElement editXmla = this.elemHelper.FindElement( driver, By.xpath( "//div[@class='pentaho-dialog'][2]//div[@id='editutton']/img" ) );
    assertNotNull( editXmla );
    editXmla.click();
    WebElement editXmlaInput = this.elemHelper.FindElement( driver, By.xpath( "//div[@class='pentaho-dialog'][3]//input[@id='paramValueTextBox']" ) );
    assertNotNull( editXmlaInput );
    editXmlaInput.clear();
    editXmlaInput.sendKeys( "true" );
    WebElement okButton = this.elemHelper.FindElement( driver, By.id( "analysisParametersDialog_accept" ) );
    assertNotNull( okButton );
    okButton.click();
    this.elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@class='pentaho-dialog'][3]" ) );
    String setAsTrue = this.elemHelper.FindElement( driver, By.xpath( "//div[@class='pentaho-dialog'][2]//div[@id='analysisPreferencesDeck']//div[contains(text(),'EnableXmla')]/../../td[2]/div" ) ).getText();
    assertEquals( setAsTrue, "true" );
    WebElement saveButton = this.elemHelper.FindElementInvisible( driver, By.xpath( "//div[@class='pentaho-dialog'][2]//button[@id='importDialog_accept']" ) );
    assertNotNull( saveButton );
    this.elemHelper.ClickElementInvisible( driver, By.xpath( "//div[@class='pentaho-dialog'][2]//button[@id='importDialog_accept']" ) );
    this.elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@class='pentaho-dialog'][2]" ) );
    WebElement closeButton = this.elemHelper.FindElement( driver, By.id( "datasourceAdminDialog_cancel" ) );
    assertNotNull( closeButton );
    closeButton.click();
    this.elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@class='pentaho-dialog']" ) );

    //Open first sample
    driver.get( baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A20-samples%3Aqueries%3AXMLADiscover%3AxmlaDiscover.xcdf/generatedContent" );

    //Wait for loading to disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Assert query results
    WebElement queryResult = this.elemHelper.FindElement( driver, By.id( "sampleObjectResult" ) );
    assertNotNull( queryResult );
    String resultText = queryResult.getText();
    assertTrue( resultText.contains( "{\"resultset\":" ) );

  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Assert query results for XMLA second sample
   */
  @Test
  public void tc2_XmlaQuery_SecondSampleResults() {
    this.log.info( "tc2_XmlaQuery_SecondSampleResults" );
    //Open the second Xmla sample
    driver.get( baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A20-samples%3Aqueries%3AXMLA%3Axmla.xcdf/generatedContent" );

    //Wait for loading to disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Assert query results
    WebElement queryResult = this.elemHelper.FindElement( driver, By.id( "sampleObjectResult" ) );
    assertNotNull( queryResult );
    String resultText = queryResult.getText();
    assertTrue( resultText.contains( "{\"resultset\":" ) );

  }

}
