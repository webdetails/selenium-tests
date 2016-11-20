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
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-267
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-931
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDEMapComponentReference extends BaseTest {
  // Log instance
  private final Logger log = LogManager.getLogger( CDEMapComponentReference.class );
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Changing width and height of map popup has desired effect
   *
   * Description:
   *    The test pretends validate the CDE-267 issue, editing the width and
   *    height of the popup generated when clicking a map's marker has the
   *    desired effect.
   *
   * Steps:
   *    1. Open sample in edit mode, go to components Panel and select GeoLocalized Map
   *    2. Go to advanced properties, set popupWidth to 300 and popupHeight to 100 and click preview
   *    3. Find map with alterations, click a marker and assert shown popup has chosen width and height
   */
  @Test
  public void tc01_CdeDashboard_MapPopupDimensions() {
    this.log.info( "tc01_CdeDashboard_MapPopupDimensions" );

    /*
     * ## Step 1
     */
    //Open sample in edit mode
    this.elemHelper.Get( driver, PageUrl.MAP_COMPONENT_REFERENCE_EDIT );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    //assert buttons and go to Components Panel
    WebElement layoutPanel = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='layoutPanelButton']" ) );
    assertNotNull( layoutPanel );
    WebElement componentPanel = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='componentsPanelButton']" ) );
    assertNotNull( componentPanel );
    WebElement dataPanel = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='datasourcesPanelButton']" ) );
    assertNotNull( dataPanel );
    componentPanel.click();

    //Assert components table and expand Maps
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='cdfdd-panels']/div[@id='panel-layout_panel']" ) );
    WebElement componentsPanel = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='cdfdd-panels']/div[@id='panel-componentens_panel']" ) );
    assertNotNull( componentsPanel );
    WebElement tableTitle = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='panel-componentens_panel']//div[@id='cdfdd-components-components']/div/div/div" ) );
    assertNotNull( tableTitle );
    WebElement groupExpander = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='cdfdd-components-components']/div/div[2]/table/tbody/tr/td/span" ) );
    assertNotNull( groupExpander );
    groupExpander.click();

    //Find and select GeoLocalized Map
    WebElement geoMap = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='cdfdd-components-components']/div/div[2]/table/tbody//td[contains(text(), 'GeoLocalizedMap')]" ) );
    assertNotNull( geoMap );
    geoMap.click();

    /*
     * ## Step 2
     */
    //Click Advanced Properties and wait for it to be selected
    WebElement advancedProperties = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='cdfdd-components-properties']/div/div/div[@class='advancedProperties propertiesUnSelected']" ) );
    assertNotNull( advancedProperties );
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='cdfdd-components-properties']/div/div/div[@class='advancedProperties propertiesUnSelected']" ) );
    this.elemHelper.WaitForAttributeValueEqualsTo( driver, By.xpath( "//div[@id='cdfdd-components-properties']/div/div/div[2]" ), "class", "advancedProperties propertiesSelected" );

    //Look for Popup Height and change it's value to 100
    WebElement popupHeight = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody//td[@title=' Height for the popup window']/../td[2]" ) );
    assertNotNull( popupHeight );
    popupHeight.click();
    WebElement inputHeight = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody//td[@title=' Height for the popup window']/../td[2]/form/input" ) );
    inputHeight.clear();
    inputHeight.sendKeys( "100" );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody//td[@title=' Height for the popup window']" ) ).click();

    //Look for Popup Width and change it's value to 300
    WebElement popupWidth = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody//td[@title=' Width for the popup window ']/../td[2]" ) );
    assertNotNull( popupWidth );
    popupWidth.click();
    WebElement inputWidth = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody//td[@title=' Width for the popup window ']/../td[2]/form/input" ) );
    inputWidth.clear();
    inputWidth.sendKeys( "300" );
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody//td[@title=' Width for the popup window ']" ) ).click();

    //Click Preview and wait for fancybox to show
    WebElement previewButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "previewButton" ) );
    assertNotNull( previewButton );
    previewButton.click();
    WebElement fancyboxFrame = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "fancybox-frame" ) );
    assertNotNull( fancyboxFrame );

    /*
     * ## Step 3
     */
    //Switch to iFrame
    this.elemHelper.SwitchToFrame( driver, fancyboxFrame );

    //Wait for blocker to disappear
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    //Look for map that was changed and assert it's width and height
    WebElement mapWithGeo = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "testWithGeoLocalization" ) );
    assertNotNull( mapWithGeo );
    WebElement marker = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='testWithGeoLocalization']/div[2]/div/div/div[5]/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='circle'][4]" ) );
    assertNotNull( marker );
    marker.click();
    WebElement popup = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div.olPopupContent" ) );
    assertNotNull( popup );

    assertEquals( popup.getAttribute( "style" ), "width: 300px; height: 100px; position: relative;" );
  }
}
