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
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.BaseTest;
import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-413
 * - http://jira.pentaho.com/browse/CDE-569
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-936
 * - http://jira.pentaho.com/browse/QUALITY-1146
 * 
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDE413 extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDE413.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Check "Column Type" editor has nothing unexpected
   *
   * Description:
   *    The test pretends validate the CDE-413 issue, so when user edits column types of a table component
   *    the editor has nothing written other than the title of the input fields. Also validating the CDE-569
   *    issue where after adding arg and value to parameters and clicking OK, added data persists.
   *
   * Steps:
   *    1. Wait for new Dashboard to be created, assert elements on page and click "Components Panel"
   *    2. Wait for Components panel to be shown, expand "Others" and click "Table COmponent"
   *    3. Wait for table Component to be added and then click "Column Types" to edit
   *    4. Wait for popup to appear, click "Add" and then assert all elements on popup. Close popup
   *    5. Open Parameters popup and add 3 pairs of arg/value
   *    6. Click Ok, assert values are shown, open popup and assert pairs are still there
   */
  @Test
  public void tc01_NewCdeDashboard_ColumnTypeEditor() {
    this.log.info( "tc01_NewCdeDashboard_ColumnTypeEditor" );

    /*
     * ## Step 1
     */
    //Go to New CDE Dashboard
    driver.get( PageUrl.CDE_DASHBOARD );
    //assert buttons
    WebElement buttonSaveTemplate = this.elemHelper.WaitForElementPresence( driver, By.xpath( "//a[@title='Save as Template']" ) );
    WebElement buttonApplyTemplate = this.elemHelper.WaitForElementPresence( driver, By.xpath( "//a[@title='Apply Template']" ) );
    WebElement buttonAddResource = this.elemHelper.WaitForElementPresence( driver, By.xpath( "//a[@title='Add Resource']" ) );
    WebElement buttonAddBoostrap = this.elemHelper.WaitForElementPresence( driver, By.xpath( "//a[@title='Add Bootstrap Panel']" ) );
    WebElement buttonAddFreeForm = this.elemHelper.WaitForElementPresence( driver, By.xpath( "//a[@title='Add FreeForm']" ) );
    WebElement buttonAddRow = this.elemHelper.WaitForElementPresence( driver, By.xpath( "//a[@title='Add Row']" ) );
    WebElement buttonLayout = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='layoutPanelButton']" ) );
    WebElement buttonComponents = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='componentsPanelButton']" ) );
    WebElement buttonDatasources = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='datasourcesPanelButton']" ) );
    assertNotNull( buttonSaveTemplate );
    assertNotNull( buttonApplyTemplate );
    assertNotNull( buttonAddResource );
    assertNotNull( buttonAddBoostrap );
    assertNotNull( buttonAddFreeForm );
    assertNotNull( buttonAddRow );
    assertNotNull( buttonLayout );
    assertNotNull( buttonComponents );
    assertNotNull( buttonDatasources );
    this.elemHelper.Click( driver, By.cssSelector( "div.componentsPanelButton" ) );

    /*
     * ## Step 2
     */
    String otherText = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[2]/h3/a" ) );
    assertEquals( "Others", otherText );
    this.elemHelper.Click( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[2]/h3/a" ) );
    WebElement optionTableComponent = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "a[title='table Component']" ) );
    assertNotNull( optionTableComponent );
    this.elemHelper.Click( driver, By.cssSelector( "a[title='table Component']" ) );

    /*
     * ## Step 3
     */
    //Click in the columnTypes - property
    WebElement propertyColumnTypes = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='cdfdd-components-properties']//tr[4]/td[2]" ) );
    assertNotNull( propertyColumnTypes );
    this.elemHelper.Click( driver, By.xpath( "//div[@id='cdfdd-components-properties']//tr[4]/td[2]" ) );
    //We need to wait for the animation finish for the display popup
    this.elemHelper.WaitForAttributeValueEqualsTo( driver, By.id( "popup" ), "style", "position: absolute; top: 15%; left: 50%; margin-left: -143px; z-index: 1000;" );

    /*
     * ## Step 4
     */
    this.elemHelper.Click( driver, By.cssSelector( "input.StringArrayAddButton" ) );
    WebElement arg0RemoveElement = this.elemHelper.FindElement( driver, By.id( "remove_button_0" ) );
    WebElement arg0InputElement = this.elemHelper.FindElement( driver, By.id( "arg_0" ) );
    WebElement arg0DragDropIconElement = this.elemHelper.FindElement( driver, By.cssSelector( "div.StringArrayDragIcon" ) );
    WebElement closeButton = this.elemHelper.FindElement( driver, By.cssSelector( "div.popupclose" ) );
    assertNotNull( arg0RemoveElement );
    assertNotNull( arg0InputElement );
    assertNotNull( arg0DragDropIconElement );
    assertNotNull( closeButton );
    closeButton.click();
    this.elemHelper.WaitForElementNotPresent( driver, By.id( "popupstates" ) );

    /*
     * ## Step 5
     */
    String arg0 = "0";
    String arg1 = "1";
    String arg2 = "2";
    String val0 = "a";
    String val1 = "b";
    String val2 = "c";

    //Open parameters popup
    WebElement parameterButton = this.elemHelper.FindElement( driver, By.xpath( "//td[@title=' Parameters to pass to the component']/../td[2]" ) );
    assertNotNull( parameterButton );
    parameterButton.click();
    WebElement parameterList = this.elemHelper.FindElement( driver, By.id( "StringList" ) );
    assertNotNull( parameterList );

    //Add 3 pairs arg/value
    WebElement arg0Input = this.elemHelper.FindElement( driver, By.id( "arg_0" ) );
    WebElement value0Input = this.elemHelper.FindElement( driver, By.id( "val_0" ) );
    assertNotNull( arg0Input );
    assertNotNull( value0Input );
    arg0Input.sendKeys( arg0 );
    value0Input.sendKeys( val0 );
    this.elemHelper.Click( driver, By.cssSelector( "input.StringListAddButton" ) );
    WebElement arg1Input = this.elemHelper.FindElement( driver, By.id( "arg_1" ) );
    WebElement value1Input = this.elemHelper.FindElement( driver, By.id( "val_1" ) );
    assertNotNull( arg1Input );
    assertNotNull( value1Input );
    arg1Input.sendKeys( arg1 );
    value1Input.sendKeys( val1 );
    this.elemHelper.Click( driver, By.cssSelector( "input.StringListAddButton" ) );
    WebElement arg2Input = this.elemHelper.FindElement( driver, By.id( "arg_2" ) );
    WebElement value2Input = this.elemHelper.FindElement( driver, By.id( "val_2" ) );
    assertNotNull( arg2Input );
    assertNotNull( value2Input );
    arg2Input.sendKeys( arg2 );
    value2Input.sendKeys( val2 );
    WebElement okButton = this.elemHelper.FindElement( driver, By.id( "popup_state0_buttonOk" ) );
    assertNotNull( okButton );
    okButton.click();
    this.elemHelper.WaitForElementNotPresent( driver, By.id( "popupstates" ) );

    /*
     * ## Step 6
     */
    //Assert parameter text is shown
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//td[@title=' Parameters to pass to the component']/../td[2]" ), "[[\"0\",\"a\"],[\"1\",\"b\"] (...)" );
    String parameterText = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//td[@title=' Parameters to pass to the component']/../td[2]" ) );
    assertEquals( "[[\"0\",\"a\"],[\"1\",\"b\"] (...)", parameterText );

    //Open parameter popup
    parameterButton = this.elemHelper.FindElement( driver, By.xpath( "//td[@title=' Parameters to pass to the component']/../td[2]" ) );
    assertNotNull( parameterButton );
    parameterButton.click();
    parameterList = this.elemHelper.FindElement( driver, By.id( "StringList" ) );
    assertNotNull( parameterList );

    //Assert values are kept
    String arg0Actual = this.elemHelper.GetAttribute( driver, By.id( "arg_0" ), "value" );
    String val0Actual = this.elemHelper.GetAttribute( driver, By.id( "val_0" ), "value" );
    String arg1Actual = this.elemHelper.GetAttribute( driver, By.id( "arg_1" ), "value" );
    String val1Actual = this.elemHelper.GetAttribute( driver, By.id( "val_1" ), "value" );
    String arg2Actual = this.elemHelper.GetAttribute( driver, By.id( "arg_2" ), "value" );
    String val2Actual = this.elemHelper.GetAttribute( driver, By.id( "val_2" ), "value" );
    assertEquals( arg0, arg0Actual );
    assertEquals( arg1, arg1Actual );
    assertEquals( arg2, arg2Actual );
    assertEquals( val0, val0Actual );
    assertEquals( val1, val1Actual );
    assertEquals( val2, val2Actual );
  }
}
