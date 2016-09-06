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
package com.pentaho.ctools.issues.cde;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.selenium.BaseTest;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-347
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-924
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDE347 extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDE347.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Asserting correct rendering of charts of CDE's Bullet Chart testcase
   *
   * Description:
   *    The test pretends validate the CDE-347 issue, so when user previews the Bullet Chart
   *    test case dashboard, coming from edit mode, the charts are rendered as expected.
   *
   * Steps:
   *    1. Assert elements on page
   *    2. Click preview button and focus on fancybox
   *    3. Assert charts are rendered correctly
   */
  @Test
  public void tc01_BulletChartTestCase_ChartsRendered() {
    this.log.info( "tc01_BulletChartTestCase_ChartsRendered" );

    /*
     * ## Step 1
     */
    //Go to Bullet Chart sample
    driver.get( baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3Accc_bullet.wcdf/edit" );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    //Wait for buttons: Layout. Components, Datasources AND Preview
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@title='Datasources Panel']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@title='Components Panel']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@title='Layout Panel']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@title='Preview your Dashboard']" ) );
    assertNotNull( element );
    String c1r1Text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//tr/td" ) );
    String c2r1Text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//tr/td[2]" ) );
    String c1r4Text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//tr[6]/td" ) );
    String c2r4Text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//tr[6]/td[2]" ) );
    assertEquals( "Resource", c1r1Text );
    assertEquals( "template", c2r1Text );
    assertEquals( "Row", c1r4Text );
    assertEquals( "obj1", c2r4Text );

    /*
     * ## Step 2
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@title='Preview your Dashboard']" ) );
    assertNotNull( element );
    this.elemHelper.Click( driver, By.id( "previewButton" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "fancybox-content" ) );
    assertNotNull( element );
    WebElement elementFrame = this.elemHelper.FindElement( driver, By.xpath( "//iframe" ) );
    WebDriver frame = driver.switchTo().frame( elementFrame );

    /*
     * ## Step 3
     */
    this.elemHelper.WaitForElementInvisibility( frame, By.cssSelector( "div.blockUI.blockOverlay" ) );
    WebElement obj1 = this.elemHelper.WaitForElementPresenceAndVisible( frame, By.xpath( "//div[@id='obj1protovis']//*[local-name()='path']" ) );
    WebElement obj2 = this.elemHelper.WaitForElementPresenceAndVisible( frame, By.xpath( "//div[@id='obj1protovis']//*[local-name()='path'][2]" ) );
    WebElement obj3 = this.elemHelper.WaitForElementPresenceAndVisible( frame, By.xpath( "//div[@id='obj2protovis']//*[local-name()='g'][2]/*[local-name()='rect']" ) );
    WebElement obj4 = this.elemHelper.WaitForElementPresenceAndVisible( frame, By.xpath( "//div[@id='obj3protovis']//*[local-name()='path']" ) );
    WebElement obj5 = this.elemHelper.WaitForElementPresenceAndVisible( frame, By.xpath( "//div[@id='obj4protovis']//*[local-name()='path']" ) );
    WebElement obj6 = this.elemHelper.WaitForElementPresenceAndVisible( frame, By.xpath( "//div[@id='obj5protovis']//*[local-name()='path']" ) );
    assertNotNull( obj1 );
    assertNotNull( obj2 );
    assertNotNull( obj3 );
    assertNotNull( obj4 );
    assertNotNull( obj5 );
    assertNotNull( obj6 );
  }
}
