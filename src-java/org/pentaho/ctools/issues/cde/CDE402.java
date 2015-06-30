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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.PageUrl;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-402
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-927
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDE402 {
  // Instance of the driver (browser emulator)
  private final WebDriver driver = CToolsTestSuite.getDriver();
  //Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDE402.class );
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( this.driver );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Check ability to apply template to new CDE Dashboard
   *
   * Description:
   *    The test pretends validate the CDE-396 issue, so when user tries to apply a template
   *    to a new CDE dashboard, CDE asks for confirmation and then applies the template.
   *
   * Steps:
   *    1. Assert elements on page and click "Create New" button
   *    2. Wait for options to load and then click "CDE Dashboard" button
   *    3. Wait for new Dashboard to be created, assert elements on page and click "Apply template"
   *    4. Wait for options to appear and select a template
   *    5. Wait for confirmation of command and confirm
   *    6. Assert that selected template was applied
   */
  @Test( timeout = 60000 )
  public void tc01_NewCdeDashboard_ApplyTemplate() {
    this.log.info( "tc01_NewCdeDashboard_ApplyTemplate" );

    /*
     * ## Step 1
     */
    this.driver.get( PageUrl.CDE_DASHBOARD );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //assert buttons and click Apply template
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@title='Save as Template']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@title='Apply Template']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@title='Add Resource']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@title='Add Bootstrap Panel']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@title='Add FreeForm']" ) );
    assertNotNull( element );
    this.elemHelper.Click( this.driver, By.xpath( "//a[@title='Apply Template']" ) );

    /*
     * ## Step 4
     */
    this.driver.switchTo().defaultContent();
    this.elemHelper.WaitForFrameReady( this.driver, By.id( "popupTemplatebox" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "popupTemplatebox" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "thumbs" ) );
    assertNotNull( element );
    String templateText = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='thumbs']/div[2]/p" ) ).getText();
    assertEquals( "Two Columns Template", templateText );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@id='thumbs']/div[2]/p" ) );
    this.elemHelper.WaitForAttributeValue( this.driver, By.xpath( "//div[@id='thumbs']/div[2]" ), "class", "hover active" );
    String text = this.elemHelper.GetAttribute( this.driver, By.xpath( "//div[@id='thumbs']/div[2]" ), "class" );
    assertEquals( "hover active", text );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='popupTemplatebuttons']/button[@id='popupTemplate_state0_buttonOk']" ) );
    assertNotNull( element );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@class='popupTemplatebuttons']/button[@id='popupTemplate_state0_buttonOk']" ) );

    /*
     * ## Step 5
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='popupTemplatemessage']" ) );
    assertNotNull( element );
    WebElement elem = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "popupTemplate" ) );
    assertNotNull( elem );
    Dimension size = elem.getSize();
    int height = size.getHeight();
    assertEquals( height, 183 );
    int width = size.getWidth();
    assertEquals( width, 743 );
    String warningText = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@class='popupTemplatemessage']" ) );
    assertEquals( "Are you sure you want to load the template?WARNING: Dashboard Layout will be overwritten!", warningText );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='popupTemplatebuttons']/button[@id='popupTemplate_state0_buttonOk']" ) );
    assertNotNull( element );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@class='popupTemplatebuttons']/button[@id='popupTemplate_state0_buttonOk']" ) );

    /*
     * ## Step 6
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[5]/td/span" ) );
    assertNotNull( element );
    this.elemHelper.Click( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[5]/td/span" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]" ) );
    assertNotNull( element );
    String tr6tdText = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]/td" ) );
    String tr6td2Text = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]/td[2]" ) );
    String tr7tdText = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]/td" ) );
    String tr7td2Text = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[7]/td[2]" ) );
    assertEquals( "Column", tr6tdText );
    assertEquals( "Panel_1", tr6td2Text );
    assertEquals( "Column", tr7tdText );
    assertEquals( "Panel_2", tr7td2Text );
  }

}
