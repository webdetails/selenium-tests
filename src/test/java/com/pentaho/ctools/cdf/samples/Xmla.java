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
package com.pentaho.ctools.cdf.samples;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with XMLA query.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class Xmla extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  //Log instance
  private final Logger log = LogManager.getLogger( Xmla.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert query results for XMLA first sample
   */
  @Test
  public void tc1_XmlaQueryEnable_CheckForEnable() {
    this.log.info( "tc1_XmlaQueryEnable_CheckForEnable" );
    //To set up this test we must enable xmla for SteelWheels datasource
    this.elemHelper.Get( driver, PageUrl.PUC );

    // NOTE
    //wait for visibility of waiting pop-up
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.busy-indicator-container.waitPopup" ), 20 );
    this.elemHelper.WaitForElementNotPresent( driver, By.cssSelector( "div.busy-indicator-container.waitPopup" ) );
    //Wait to load the new page
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='pucUserDropDown']/table/tbody/tr/td/div" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//iframe[@id='home.perspective']" ) );

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
    WebElement manualButton = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='manualRadio']//input" ) );
    assertNotNull( manualButton );
    this.elemHelper.ClickJS( driver, By.xpath( "//table[@id='manualRadio']//input" ) );
    //Check if is already "TRUE"
    String setAsTrue = this.elemHelper.WaitForTextDifferentEmpty( driver, By.xpath( "//div[@id='analysisParametersTree']//div[2]//tr[3]/td[2]" ) );
    if ( setAsTrue.equalsIgnoreCase( "true" ) ) {
      assertEquals( setAsTrue, "true" );
    } else {
      WebElement enableXmla = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='analysisPreferencesDeck']//div[contains(text(),'EnableXmla')]" ) );
      assertNotNull( enableXmla );
      enableXmla.click();
      WebElement editXmla = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='editutton']/img" ) );
      assertNotNull( editXmla );
      editXmla.click();
      WebElement editXmlaInput = this.elemHelper.FindElement( driver, By.id( "paramValueTextBox" ) );
      assertNotNull( editXmlaInput );
      editXmlaInput.clear();
      editXmlaInput.sendKeys( "true" );
      WebElement okButton = this.elemHelper.FindElement( driver, By.id( "analysisParametersDialog_accept" ) );
      assertNotNull( okButton );
      okButton.click();
      this.elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@class='pentaho-dialog'][3]" ) );
      setAsTrue = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='analysisParametersTree']//div[2]//tr[3]/td[2]" ) ).getText();
      assertEquals( setAsTrue, "true" );
      WebElement saveButton = this.elemHelper.FindElementInvisible( driver, By.id( "importDialog_accept" ) );
      assertNotNull( saveButton );
      this.elemHelper.ClickElementInvisible( driver, By.id( "importDialog_accept" ) );
      this.elemHelper.WaitForElementNotPresent( driver, By.id( "importDialog_accept" ) );
      WebElement closeButton = this.elemHelper.FindElement( driver, By.id( "datasourceAdminDialog_cancel" ) );
      assertNotNull( closeButton );
      closeButton.click();
      this.elemHelper.WaitForElementNotPresent( driver, By.id( "datasourceAdminDialog_cancel" ) );
    }
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Assert query results for XMLA Discover
   */
  @Test
  public void tc2_XmlaDiscover_GetSampleResults() {
    this.log.info( "tc3_XmlaQuery_GetSampleResults" );
    //Open first sample
    this.elemHelper.Get( driver, PageUrl.XMLA_DISCOVER );

    //Wait for loading to disappear
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 5 );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 300 );

    //Assert query results
    WebElement queryResult = this.elemHelper.FindElement( driver, By.id( "sampleObjectResult" ) );
    assertNotNull( queryResult );
    String resultText = queryResult.getText();
    assertTrue( resultText.contains( "{\"resultset\":" ) );

  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Assert query results for XMLA Query
   */
  @Test
  public void tc3_XmlaQuery_GetSampleResults() {
    this.log.info( "tc3_XmlaQuery_GetSampleResults" );
    //Open the second Xmla sample
    this.elemHelper.Get( driver, PageUrl.XMLA_QUERY );

    //Wait for loading to disappear
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 5 );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 300 );

    //Assert query results
    WebElement queryResult = this.elemHelper.FindElement( driver, By.id( "sampleObjectResult" ) );
    assertNotNull( queryResult );
    String resultText = queryResult.getText();
    assertTrue( resultText.contains( "{\"resultset\":" ) );
  }

}
