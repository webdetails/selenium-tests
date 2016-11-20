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
import static org.testng.Assert.assertTrue;

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
 * - http://jira.pentaho.com/browse/QUALITY-1027
 *
 * NOTE
 * To test this script it is required to have CDF plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDF474 extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDF474.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert autoWidth property of select2 plugin works as expected.
   *
   * Description:
   *    Assert drop down's width matches expected.
   *
   * Steps:
   *    1. Open Issue's prepared sample, click to expand drop down and assert it's style
   */
  @Test
  public void tc01_SelectComponent_Select2AutoWidth() {
    this.log.info( "tc01_SelectComponent_Select2AutoWidth" );

    /*
     * ## Step 1
     */
    // Go to New CDE Dashboard
    this.elemHelper.Get( baseUrl + "api/repos/:public:Issues:CDF:CDF-474:CDF-474.wcdf/generatedContent" );

    // Wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='col1']/div/a/span[2]/b" ) );
    assertNotNull( element );
    element.click();
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "select2-drop" ) );
    assertNotNull( element );
    String text = element.getAttribute( "style" );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='col2']/div/a/span[2]/b" ) );
    assertNotNull( element );
    element.click();
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "select2-drop" ) );
    assertNotNull( element );
    String text1 = element.getAttribute( "style" );
    assertTrue( text != text1 );
  }
}
