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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pentaho.ctools.suite.CToolsTestSuite;
import com.pentaho.ctools.utils.BaseTest;
import com.pentaho.ctools.utils.ElementHelper;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDA-109
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-954
 *
 * NOTE
 * To test this script it is required to have CDA plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDA109 extends BaseTest {
  // The base url to be append the relative url in test
  private final String baseUrl = CToolsTestSuite.getBaseUrl();
  //Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDA109.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Asserting ability to edit CDA file with spaces in the path
   * Description:
   *    The test pretends validate the CDA-109 issue, when editing a CDA file that has spaces
   *    in the path the file is shown correctly.
   *
   * Steps:
   *    1. Assert path to file
   *    2. Assert query is correctly shown
   *
   */
  @Test
  public void tc01_CdaFileEditor_SpacePathWorks() {
    this.log.info( "tc01_CdaFileEditor_SpacePathWorks" );

    /*
     * ## Step 1
     */
    //Go to User Console
    this.driver.get( this.baseUrl + "plugin/cda/api/editFile?path=/public/Issues/CDA/CDA%20-%20109/.cda" );

    //Wait for Elements outside of iFrame
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='webdetailsLogo']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//span[@id='staticfile']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//button[@id='save']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//button[@id='reload']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//button[@id='preview']" ) );
    assertNotNull( element );
    String filePath = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//span[@id='staticfile']" ) );
    assertEquals( "/public/Issues/CDA/CDA - 109/.cda", filePath );

    /*
     * ## Step 2
     */
    WebElement elementFrame = this.elemHelper.FindElement( this.driver, By.xpath( "//iframe" ) );
    WebDriver frame = this.driver.switchTo().frame( elementFrame );

    element = this.elemHelper.WaitForElementPresenceAndVisible( frame, By.xpath( "//pre[@id='editArea']/div[2]/div[1]/div[3]" ) );
    assertNotNull( element );
    String ln1Text = this.elemHelper.WaitForElementPresentGetText( frame, By.xpath( "//pre[@id='editArea']/div[2]/div[1]/div[3]/div[2]/span[2]" ) );
    assertEquals( "CDADescriptor", ln1Text );
    ln1Text = this.elemHelper.WaitForElementPresentGetText( frame, By.xpath( "//pre[@id='editArea']/div[2]/div[1]/div[3]/div[3]/span[2]" ) );
    assertEquals( "DataSources", ln1Text );
    ln1Text = this.elemHelper.WaitForElementPresentGetText( frame, By.xpath( "//pre[@id='editArea']/div[2]/div[1]/div[3]/div[4]/span[3]" ) );
    assertEquals( "Connection", ln1Text );
    ln1Text = this.elemHelper.WaitForElementPresentGetText( frame, By.xpath( "//pre[@id='editArea']/div[2]/div[1]/div[3]/div[5]/span[4]" ) );
    assertEquals( "Catalog", ln1Text );
  }
}
