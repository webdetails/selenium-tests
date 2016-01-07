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
import static org.testng.AssertJUnit.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-399
 * - http://jira.pentaho.com/browse/CDE-410
 * - http://jira.pentaho.com/browse/CDE-529
 * - http://jira.pentaho.com/browse/CDF-504
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-966
 * - http://jira.pentaho.com/browse/QUALITY-994
 * - http://jira.pentaho.com/browse/QUALITY-1143 
 * - http://jira.pentaho.com/browse/QUALITY-1111
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDEComponentPanel extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDEComponentPanel.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    This test validates all issues related to CDE's Component Panel
   *
   * Description:
   *    399: Accordion works as expected
   *    410: Shortcuts work
   *    529: Opening a popup blocks the use of the shortcuts to navigate
   *    CDF-504: baseAxisFont is exposed in Advanced Properties of CCC chart components
   *    
   * Steps:
   *    1. Open CDE sample in edit mode, go to components panel and add some components
   *    2. Assert down, up, left and right shortcuts work on components panel
   *    3. Assert tab and enter shortcuts work on components panel
   *    4. Assert opening parameter popup blocks navigation by arrows
   *    5. Go to chart Advanced Properties and assert baseAxisFont is there
   */
  @Test
  public void tc01_CDEDashboardEdit_ComponentPanel() {
    this.log.info( "tc01_CDEDashboardEdit_ComponentPanel" );

    /*
     * ## Step 1
     */
    //Open CDE sample in edit mode
    driver.get( PageUrl.CDE_DASHBOARD );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    //Go to Components Panel
    this.elemHelper.Click( driver, By.xpath( "//div[@title='Components Panel']/a" ) );

    //Assert behavior of accordion
    WebElement otherExpander = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[2]/h3/a" ) );
    assertNotNull( otherExpander );
    String otherText = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[2]/h3/a" ) );
    assertEquals( "Others", otherText );
    this.elemHelper.Click( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[2]/h3/a" ) );
    WebElement tableComponent = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='table Component']" ) );
    assertNotNull( tableComponent );
    WebElement genericExpander = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[3]/h3/a" ) );
    assertNotNull( genericExpander );
    String genericText = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[3]/h3/a" ) );
    assertEquals( "Generic", genericText );
    this.elemHelper.Click( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[3]/h3/a" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//a[@title='table Component']" ) );

    //Add some Components
    WebElement chartExpander = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div/h3/span" ) );
    assertNotNull( chartExpander );
    chartExpander.click();
    WebElement addAreaChart = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div//a[@title='CCC Area Chart']" ) );
    assertNotNull( addAreaChart );
    addAreaChart.click();
    WebElement addBulletChart = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div//a[@title='CCC Bullet Chart']" ) );
    assertNotNull( addBulletChart );
    addBulletChart.click();
    otherExpander = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[2]/h3/span" ) );
    assertNotNull( otherExpander );
    otherExpander.click();
    WebElement addTable = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[2]//a[@title='table Component']" ) );
    assertNotNull( addTable );
    addTable.click();
    WebElement addButton = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[2]//a[@title='Button Component']" ) );
    assertNotNull( addButton );
    addButton.click();

    /*
     * ## Step 2
     */
    //Select first chart group
    this.elemHelper.Click( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr/td" ) );
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr" ), "class", "expanded initialized parent ui-state-active" );
    String chartGroup = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr" ) ).getAttribute( "class" );
    assertEquals( chartGroup, "expanded initialized parent ui-state-active" );

    //Assert groups are expanded and clicking left will collapse them, also assert down arrow moves between showing elements
    WebElement areaChart = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[2]" ) );
    assertNotNull( areaChart );
    tableComponent = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[5]" ) );
    assertNotNull( tableComponent );
    Actions a = new Actions( driver );
    a.sendKeys( Keys.LEFT ).sendKeys( Keys.DOWN ).sendKeys( Keys.LEFT ).build().perform();
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[4]" ), "class", "initialized parent ui-state-active collapsed" );
    String otherGroup = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[4]" ) ).getAttribute( "class" );
    assertEquals( otherGroup, "initialized parent ui-state-active collapsed" );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[2]" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[5]" ) );

    //Assert clicking right arrow expands groups    
    a.sendKeys( Keys.RIGHT ).sendKeys( Keys.UP ).sendKeys( Keys.RIGHT ).build().perform();
    areaChart = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[2]" ) );
    assertNotNull( areaChart );
    tableComponent = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[5]" ) );
    assertNotNull( tableComponent );

    //Go to to table component and assert it's selected
    a.sendKeys( Keys.DOWN ).sendKeys( Keys.DOWN ).sendKeys( Keys.DOWN ).sendKeys( Keys.DOWN ).build().perform();
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[5]" ), "class", "child-of-OTHERCOMPONENTS initialized collapsed ui-state-active" );
    String tableClass = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[5]" ) ).getAttribute( "class" );
    assertEquals( tableClass, "child-of-OTHERCOMPONENTS initialized collapsed ui-state-active" );

    /*
     * ## Step 3
     */
    //Click tab key and assert focus has changed to properties table
    a.sendKeys( Keys.TAB ).build().perform();
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr" ), "class", "initialized ui-state-active" );
    String nameProperty = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr" ) ).getAttribute( "class" );
    assertEquals( nameProperty, "initialized ui-state-active" );

    //Click enter to change following properties "Name" and "Parameters"
    a.sendKeys( Keys.ENTER ).sendKeys( "a" ).sendKeys( Keys.ENTER ).sendKeys( Keys.DOWN ).sendKeys( Keys.DOWN ).build().perform();

    /*
     * ## Step 4
     */
    //Change "Parameter" and assert using down arrow to navigate is blocked
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[3]" ), "class", "initialized ui-state-active" );
    String parameterProperty = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[3]" ) ).getAttribute( "class" );
    assertEquals( parameterProperty, "initialized ui-state-active" );
    a.sendKeys( Keys.ENTER ).build().perform();
    assertNotNull( this.elemHelper.FindElement( driver, By.id( "popup" ) ) );
    a.sendKeys( Keys.TAB ).build().perform();
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[3]" ), "class", "initialized ui-state-active" );
    parameterProperty = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[3]" ) ).getAttribute( "class" );
    assertEquals( parameterProperty, "initialized ui-state-active" );
    a.sendKeys( Keys.DOWN ).build().perform();
    WebElement closePopup = this.elemHelper.FindElement( driver, By.id( "popup_state0_buttonCancel" ) );
    assertNotNull( closePopup );
    closePopup.click();
    assertTrue( this.elemHelper.WaitForElementNotPresent( driver, By.id( "popup" ) ) );
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[3]" ), "class", "initialized ui-state-active" );
    parameterProperty = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[3]" ) ).getAttribute( "class" );
    assertEquals( parameterProperty, "initialized ui-state-active" );

    //assert values are changed
    String nameValue = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr/td[2]" ) );
    assertEquals( nameValue, "a" );

    /*
     * ## Step 5
     */
    areaChart = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-components']//td[contains(text(),'CCC Area Chart')]" ) );
    assertNotNull( areaChart );
    areaChart.click();
    WebElement advancedProperties = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='cdfdd-components-properties']//div[@class='advancedProperties propertiesUnSelected']" ) );
    assertNotNull( advancedProperties );
    advancedProperties.click();
    WebElement baseAxisFont = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']//td[@title='The font used by the panel.']" ) );
    assertNotNull( baseAxisFont );

  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    This test validates all issues related to CDE's Component Panel
   *
   * Description:
   *    450: Able to reorder properties
   *    
   * Steps:
   *    1. Open CDE sample in edit mode, go to components panel and add Dial Chart
   *    2. Add colors and assert their order can be changed
   */
  @Test
  public void tc02_CDEDashboardEdit_ComponentPropertyReorder() {
    this.log.info( "tc02_CDEDashboardEdit_ComponentPropertyReorder" );

    /*
     * ## Step 1
     */
    //Open CDE sample in edit mode
    driver.get( PageUrl.CDE_DASHBOARD );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    //Go to Components Panel
    this.elemHelper.Click( driver, By.xpath( "//div[@title='Components Panel']/a" ) );

    this.elemHelper.Click( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div/h3/span" ) );
    this.elemHelper.Click( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div/div/ul/li[24]/a[@title='CGG Dial Chart']" ) );
    String componentName = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[2]/td" ) );
    assertEquals( "CGG Dial Chart", componentName );

    /*
     * ## Step 2
     */
    //Add Name
    /*String expectedChartName = "dial";
    this.elemHelper.FindElement( driver, By.xpath( "//div[@id='cdfdd-components-properties']/div/div[2]/table/tbody/tr/td[2]/form/input" ) ).sendKeys( "dial" );
    this.elemHelper.FindElement( driver, By.xpath( "//div[@id='cdfdd-components-properties']/div/div[2]/table/tbody/tr/td[2]/form/input" ) ).submit();
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//div[@id='cdfdd-components-properties']/div/div[2]/table/tbody/tr/td[2]" ), "dial" );
    String actualChartName = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='cdfdd-components-properties']/div/div[2]/table/tbody/tr/td[2]" ) );
    assertEquals( expectedChartName, actualChartName );

    //Add Color Range
    String strColor1 = "blue";
    String strColor2 = "green";
    String strColor3 = "brown";
    this.elemHelper.Click( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[2]/td[2]" ) );
    //We need to wait for the animation finish for the display popup
    this.elemHelper.FindElement( driver, By.id( "popup" ) );
    //Add Colors
    //this.elemHelper.Click( driver, By.cssSelector( "button.popup-add-row-button" ) );
    WebElement elemArg0 = this.elemHelper.FindElement( driver, By.id( "arg_0" ) );
    this.elemHelper.Click( driver, By.cssSelector( "button.popup-add-row-button" ) );
    WebElement elemArg1 = this.elemHelper.FindElement( driver, By.id( "arg_1" ) );
    this.elemHelper.Click( driver, By.cssSelector( "button.popup-add-row-button" ) );
    WebElement elemArg2 = this.elemHelper.FindElement( driver, By.id( "arg_2" ) );
    assertNotNull( elemArg0 );
    assertNotNull( elemArg1 );
    assertNotNull( elemArg2 );
    //Add the first color
    this.elemHelper.ClickAndSendKeys( driver, By.id( "arg_0" ), strColor1 );
    //Add the second color
    this.elemHelper.ClickAndSendKeys( driver, By.id( "arg_1" ), strColor2 );
    //Add the third color
    this.elemHelper.ClickAndSendKeys( driver, By.id( "arg_2" ), strColor3 );
    //CDE-450 assert able to change order of colors
    this.elemHelper.MoveToElementAndClick( driver, By.xpath( "//div[@id='drag_icon_0']/span" ) );
    this.elemHelper.WaitForAttributeValue( driver, By.id( "parameters_0" ), "class", "popup_drag_hover", 2 );
    this.elemHelper.DragAndDrop( driver, By.xpath( "//div[@id='drag_icon_0']/span" ), By.xpath( "//div[@id='drag_icon_2']/span" ) );
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//div[@class='popup-list-body ui-sortable']/div" ), "id", "parameters_1" );
    String firstParameter = this.elemHelper.GetAttribute( driver, By.xpath( "//div[@class='popup-list-body ui-sortable']/div" ), "id" );
    assertEquals( "parameters_1", firstParameter );
    this.elemHelper.MoveToElementAndClick( driver, By.xpath( "//div[@id='drag_icon_1']/span" ) );
    this.elemHelper.WaitForAttributeValue( driver, By.id( "parameters_1" ), "class", "popup_drag_hover", 2 );
    this.elemHelper.DragAndDrop( driver, By.xpath( "//div[@id='drag_icon_1']/span" ), By.xpath( "//div[@id='drag_icon_2']/span" ) );
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//div[@class='popup-list-body ui-sortable']/div" ), "id", "parameters_2" );
    firstParameter = this.elemHelper.GetAttribute( driver, By.xpath( "//div[@class='popup-list-body ui-sortable']/div" ), "id" );
    assertEquals( "parameters_2", firstParameter );
    //Submit
    this.elemHelper.Click( driver, By.id( "popup_state0_buttonOk" ) );
    //Wait For Popup Disappear
    this.elemHelper.WaitForElementNotPresent( driver, By.id( "popupbox" ) );
    //Check the colors array
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[2]/td[2]" ), "[\"" + strColor3 + "\",\"" + strColor2 + "\",\"" + strColor1 + "\"]" );
    String rangeColorArray = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[2]/td[2]" ) );
    assertEquals( "[\"brown\",\"green\",\"blue\"]", rangeColorArray );*/

  }
}
