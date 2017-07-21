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
package com.pentaho.ctools.cde;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-438
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-999
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CggComponent extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CggComponent.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert CGG works properly
   *
   * Description:
   *    The test pretends validate the CDE-438 issue, so CGG sample renders correctly.
   *
   * Steps:
   *    1. Open Cgg Component sample and assert elements on page
   *    2. Assert first chart is rendered
   *    3. Assert second chart is rendered
   */
  @Test
  public void tc1_CDEDashboard_CggComponentWorks() {
    this.log.info( "tc1_CDEDashboard_CggComponentWorks" );

    /*
     * ## Step 1
     */
    //Open CGG Component sample
    this.elemHelper.Get( driver, PageUrl.CGG_COMPONENT );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    WebElement element = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='content']/div/div[2]/div" ) );
    assertNotNull( element );
    String title = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='content']/div/div[2]/div" ) );
    assertEquals( "Cgg Component sample", title );
    element = this.elemHelper.FindElement( driver, By.id( "column1" ) );
    assertNotNull( element );
    element = this.elemHelper.FindElement( driver, By.id( "column2" ) );
    assertNotNull( element );

    /*
     * ## Step 2
     */
    element = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='column1']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][1]" ) );
    assertNotNull( element );
    element = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='column1']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][2]" ) );
    assertNotNull( element );
    element = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='column1']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][3]" ) );
    assertNotNull( element );
    element = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='column1']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][4]" ) );
    assertNotNull( element );
    element = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='column1']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][5]" ) );
    assertNotNull( element );
    element = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='column1']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][6]" ) );
    assertNotNull( element );
    element = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='column1']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][7]" ) );
    assertNotNull( element );
    element = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='column1']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][8]" ) );
    assertNotNull( element );
    element = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='column1']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][9]" ) );
    assertNotNull( element );
    element = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='column1']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][10]" ) );
    assertNotNull( element );
    element = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='column1']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][11]" ) );
    assertNotNull( element );

    /*
     * ## Step 3
     */
    element = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='column2']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][1]" ) );
    assertNotNull( element );
    element = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='column2']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][2]" ) );
    assertNotNull( element );
    element = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='column2']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][3]" ) );
    assertNotNull( element );
    element = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='column2']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][4]" ) );
    assertNotNull( element );
    element = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='column2']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][5]" ) );
    assertNotNull( element );
    element = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='column2']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][6]" ) );
    assertNotNull( element );
    element = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='column2']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][2]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='g'][1]/*[local-name()='rect'][7]" ) );
    assertNotNull( element );
  }
}
