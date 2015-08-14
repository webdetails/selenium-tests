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
package com.pentaho.ctools.issues.cde;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.BaseTest;
import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-384
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-940
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDE384 extends BaseTest {
  //Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDE384.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Check external resources path when editing
   *
   * Description:
   *    The test pretends validate the CDE-384 issue, so when user edits two different external
   *    resources the path to both of them is accurate.
   *
   * Steps:
   *    1. Wait for new Dashboard to be created, assert elements on page and click "Add Resource"
   *    2. Wait for popup to appear, assert text and click "Ok"
   *    3. Wait for properties and click on "^", wait for popup and expand "public", expand "plugin-samples",
   *    expand "pentaho-cdf-dd", click "cdeReference.css" and click "Ok"
   *    4. Click to edit resource, wait for popup, assert text on path, click "X"
   *    5. Go to Datasources Panel, expand Community Data Access and click CDA Datasource
   *    6. On properties click "^", wait for popup and expand "public", expand "plugin-samples",
   *    expand "cda", expand "cdafiles", click "compundJoin.cda" and click "Ok"
   *    7. Click to edit cda file, wait for popup, assert text on path, click "X"
   *    8. Go to Layout Panel, click to edit resource, wait for popup, assert text on path, click "X"
   */
  @Test
  public void tc01_ExternalResources_AssertPath() {
    this.log.info( "tc01_ExternalResources_AssertPath" );

    /*
     * ## Step 1
     */
    //Go to New CDE Dashboard
    this.driver.get( PageUrl.CDE_DASHBOARD );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
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
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@title='Add Row']" ) );
    assertNotNull( element );
    this.elemHelper.Click( this.driver, By.xpath( "//a[@title='Add Resource']" ) );

    /*
     * ## Step 2
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//select[@id='resourceType']" ) );
    assertNotNull( element );
    Select select = new Select( this.elemHelper.FindElement( this.driver, By.xpath( "//select[@id='resourceType']" ) ) );
    select.selectByValue( "Css" );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//select[@id='resourceSource']" ) );
    assertNotNull( element );
    Select select1 = new Select( this.elemHelper.FindElement( this.driver, By.xpath( "//select[@id='resourceSource']" ) ) );
    select1.selectByValue( "file" );
    this.elemHelper.Click( this.driver, By.xpath( "//button[@id='popup_state0_buttonOk']" ) );

    /*
     * ## Step 3
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//button[@class='cdfdd-resourceFileExplorerRender']" ) );
    assertNotNull( element );
    this.elemHelper.Click( this.driver, By.xpath( "//button[@class='cdfdd-resourceFileExplorerRender']" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "container_id" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='container_id']//a[@rel='public/']" ) );
    assertNotNull( element );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@id='container_id']//a[@rel='public/']" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='container_id']//a[@rel='public/plugin-samples/']" ) );
    assertNotNull( element );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@id='container_id']//a[@rel='public/plugin-samples/']" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='container_id']//a[@rel='public/plugin-samples/pentaho-cdf-dd/']" ) );
    assertNotNull( element );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@id='container_id']//a[@rel='public/plugin-samples/pentaho-cdf-dd/']" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='container_id']//a[@rel='public/plugin-samples/pentaho-cdf-dd/cdeReference.css']" ) );
    assertNotNull( element );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@id='container_id']//a[@rel='public/plugin-samples/pentaho-cdf-dd/cdeReference.css']" ) );
    this.elemHelper.Click( this.driver, By.xpath( "//button[@id='popup_browse_buttonOk']" ) );
    this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//div[@class='cdfdd-resourceFileNameRender']" ), "${solution:/public/plugin-samples/pentaho-cdf-dd/cdeReference.css}" );
    String pathToResource = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='cdfdd-resourceFileNameRender']" ) ).getText();
    assertEquals( "${solution:/public/plugin-samples/pentaho-cdf-dd/cdeReference.css}", pathToResource );

    /*
     * ## Step 4
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//button[@class='cdfddInput']" ) );
    assertNotNull( element );
    this.elemHelper.Click( this.driver, By.xpath( "//button[@class='cdfddInput']" ) );
    WebElement elementFrame = this.elemHelper.FindElement( this.driver, By.xpath( "//iframe" ) );
    WebDriver frame = this.driver.switchTo().frame( elementFrame );
    element = this.elemHelper.WaitForElementPresenceAndVisible( frame, By.xpath( "//span[@id='infoArea']" ) );
    assertNotNull( element );
    String pathText = this.elemHelper.WaitForTextPresence( frame, By.xpath( "//span[@id='infoArea']" ), "/public/plugin-samples/pentaho-cdf-dd/cdeReference.css" );
    assertEquals( "/public/plugin-samples/pentaho-cdf-dd/cdeReference.css", pathText );
    this.driver.switchTo().defaultContent();
    this.elemHelper.Click( this.driver, By.id( "popup_edit_buttonClose" ) );

    /*
     * ## Step 5
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='layoutPanelButton']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='componentsPanelButton']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='datasourcesPanelButton']" ) );
    assertNotNull( element );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@class='datasourcesPanelButton']" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='cdfdd-datasources-pallete']/div/div[2]/h3/a" ) );
    assertNotNull( element );
    String cdaText = this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='cdfdd-datasources-pallete']/div/div[2]/h3/a" ) );
    assertEquals( "Community Data Access", cdaText );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@id='cdfdd-datasources-pallete']/div/div[2]/h3/a" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//a[@title='CDA Data Source']" ) );
    assertNotNull( element );
    this.elemHelper.Click( this.driver, By.xpath( "//a[@title='CDA Data Source']" ) );

    /*
     * ## Step 6
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='table-cdfdd-datasources-properties']//button[@class='cdfdd-resourceFileExplorerRender']" ) );
    assertNotNull( element );
    this.elemHelper.Click( this.driver, By.xpath( "//table[@id='table-cdfdd-datasources-properties']//button[@class='cdfdd-resourceFileExplorerRender']" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "container_id" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='container_id']//a[@rel='public/']" ) );
    assertNotNull( element );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@id='container_id']//a[@rel='public/']" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='container_id']//a[@rel='public/plugin-samples/']" ) );
    assertNotNull( element );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@id='container_id']//a[@rel='public/plugin-samples/']" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='container_id']//a[@rel='public/plugin-samples/cda/']" ) );
    assertNotNull( element );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@id='container_id']//a[@rel='public/plugin-samples/cda/']" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='container_id']//a[@rel='public/plugin-samples/cda/cdafiles/']" ) );
    assertNotNull( element );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@id='container_id']//a[@rel='public/plugin-samples/cda/cdafiles/']" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='container_id']//a[@rel='public/plugin-samples/cda/cdafiles/compoundJoin.cda']" ) );
    assertNotNull( element );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@id='container_id']//a[@rel='public/plugin-samples/cda/cdafiles/compoundJoin.cda']" ) );
    this.elemHelper.Click( this.driver, By.xpath( "//button[@id='popup_browse_buttonOk']" ) );

    /*
     * ## Step 7
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='table-cdfdd-datasources-properties']//button[@class='cdfddInput']" ) );
    assertNotNull( element );
    this.elemHelper.Click( this.driver, By.xpath( "//table[@id='table-cdfdd-datasources-properties']//button[@class='cdfddInput']" ) );
    elementFrame = this.elemHelper.FindElement( this.driver, By.xpath( "//iframe" ) );
    frame = this.driver.switchTo().frame( elementFrame );
    element = this.elemHelper.WaitForElementPresenceAndVisible( frame, By.xpath( "//span[@id='infoArea']" ) );
    assertNotNull( element );
    String pathText1 = this.elemHelper.WaitForTextPresence( frame, By.xpath( "//span[@id='infoArea']" ), "/public/plugin-samples/cda/cdafiles/compoundJoin.cda" );
    assertEquals( "/public/plugin-samples/cda/cdafiles/compoundJoin.cda", pathText1 );
    this.driver.switchTo().defaultContent();
    this.elemHelper.Click( this.driver, By.id( "popup_edit_buttonClose" ) );

    /*
     * ## Step 8
     */
    this.driver.switchTo().defaultContent();
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='layoutPanelButton']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='componentsPanelButton']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='datasourcesPanelButton']" ) );
    assertNotNull( element );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@class='layoutPanelButton']" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//button[@class='cdfddInput']" ) );
    assertNotNull( element );
    this.elemHelper.Click( this.driver, By.xpath( "//button[@class='cdfddInput']" ) );
    elementFrame = this.elemHelper.FindElement( this.driver, By.xpath( "//iframe" ) );
    frame = this.driver.switchTo().frame( elementFrame );
    element = this.elemHelper.WaitForElementPresenceAndVisible( frame, By.xpath( "//span[@id='infoArea']" ) );
    assertNotNull( element );
    String pathText3 = this.elemHelper.WaitForTextPresence( frame, By.xpath( "//span[@id='infoArea']" ), "/public/plugin-samples/pentaho-cdf-dd/cdeReference.css" );
    assertEquals( "/public/plugin-samples/pentaho-cdf-dd/cdeReference.css", pathText3 );
    this.driver.switchTo().defaultContent();
    this.elemHelper.Click( this.driver, By.id( "popup_edit_buttonClose" ) );
  }
}
