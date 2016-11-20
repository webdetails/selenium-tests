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
package com.pentaho.ctools.issues.cdf;

import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.selenium.BaseTest;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDF-474
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1107
 *
 * NOTE
 * To test this script it is required to have CDF plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class Select2Component extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( Select2Component.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Tests related to the Selec2 Component
   *
   * Description:
   *    CDF-474: Assert auto-width property can be set to false
   *
   * Steps:
   *    1. Open created sample, open fist drop down and get width of it
   *    2. Open second drop down and assert it has different width
   */
  @Test
  public void tc1_Select2Component_AutoWidth() {
    this.log.info( "tc1_Select2Component_AutoWidth" );

    /*
     * ## Step 1
     */
    //Open Created sample and click button
    this.elemHelper.Get( baseUrl + "api/repos/%3Apublic%3AIssues%3ACDF%3ACDF-474%3ACDF-474.wcdf/generatedContent" );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 60 );

    //Click first dropdown
    WebElement selectExpander = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='s2id_autogen1']/a/span[2]/b" ) );
    assertNotNull( selectExpander );
    selectExpander.click();
    WebElement selectDrop = this.elemHelper.FindElement( driver, By.id( "select2-drop" ) );
    assertNotNull( selectDrop );
    String elementwidth = selectDrop.getAttribute( "style" );
    assertTrue( elementwidth.contains( "width: 66px;" ) );
    selectExpander = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='s2id_autogen1']/a/span[2]/b" ) );
    assertNotNull( selectExpander );
    selectExpander.click();
    this.elemHelper.WaitForElementNotPresent( driver, By.id( "select2-drop" ) );

    /*
     * ## Step 2
     */
    //Click second dropdown
    selectExpander = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='s2id_autogen3']/a/span[2]/b" ) );
    assertNotNull( selectExpander );
    selectExpander.click();
    selectDrop = this.elemHelper.FindElement( driver, By.id( "select2-drop" ) );
    assertNotNull( selectDrop );
    String elementwidth2 = selectDrop.getAttribute( "style" );
    assertTrue( elementwidth2.contains( "width: 190px;" ) );
    selectExpander = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='s2id_autogen3']/a/span[2]/b" ) );
    assertNotNull( selectExpander );
    selectExpander.click();
    this.elemHelper.WaitForElementNotPresent( driver, By.id( "select2-drop" ) );

  }
}
