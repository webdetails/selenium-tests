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
import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.gui.web.ctools.cde.CDEditor;
import com.pentaho.gui.web.ctools.cde.Popup;
import com.pentaho.selenium.BaseTest;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-505
 * - http://jira.pentaho.com/browse/CDE-507
 * - http://jira.pentaho.com/browse/CDE-534
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1094
 * - http://jira.pentaho.com/browse/QUALITY-1095
 * - http://jira.pentaho.com/browse/QUALITY-1100
 * 
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDE505 extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDE505.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    CHeck parameter list is presented in alphabetical order
   *
   * Description:
   *    The test pretends validate the CDE-505 issue, so when user clicks to add 
   *    parameters the list shown is presented in alphabetical order. Also we 
   *    want to validate the CDE-507 issue where a "hidden" option is added to
   *    Table's selectable ColumnTypes. For CDE-534 asserting that empty entry 
   *    is added when opening parameters and column types.
   *
   * Steps:
   *    1. Wait for new Dashboard to be created, go to components panel
   *    2. Add parameters and name them "a", "b", "test", "test.ing", "testing" and "..."
   *    3. Add a table and click to add parameters. Assert empty parameter is added (CDE534)
   *    4. Click to show list and assert parameters are shown in alphabetical order
   *    5. Click Column types and assert "hidden" option is shown in list. Assert empty Arg is added (CDE534)
   *    
   */

  @Test
  public void tc01_ParametersList_SortedAlphabetically() {
    this.log.info( "tc01_ParametersList_SortedAlphabetically" );

    /*
     * ## Step 1
     */
    //Go to New CDE Dashboard
    CDEditor cdeditor = new CDEditor( driver );
    cdeditor.GoToNewCDE();
    cdeditor.GoToComponentPanel();

    /*
     * ## Step 2
     */
    //Expand Generic
    WebElement genericExpander = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[3]/h3/span" ) );
    assertNotNull( genericExpander );
    genericExpander.click();

    //Add parameter "a"
    String value1 = "a";
    WebElement addParameter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='Simple parameter']" ) );
    assertNotNull( addParameter );
    this.elemHelper.ClickJS( driver, By.xpath( "//a[@title='Simple parameter']" ) );
    WebElement addedParameter1 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[2]" ) );
    assertNotNull( addedParameter1 );
    WebElement parameterName1 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "form.cdfddInput input" ) );
    assertNotNull( parameterName1 );
    this.elemHelper.SendKeysAndSubmit( driver, By.cssSelector( "form.cdfddInput input" ), value1 );
    String parameterValue1 = this.elemHelper.WaitForTextDifferentEmpty( driver, By.xpath( "//td[contains(text(), 'Name')]/../td[2]" ) );
    assertEquals( parameterValue1, value1 );
    parameterValue1 = this.elemHelper.WaitForTextDifferentEmpty( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[2]/td[2]" ) );
    assertEquals( parameterValue1, value1 );

    //Add parameter "z"
    String value2 = "z";
    addParameter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='Simple parameter']" ) );
    assertNotNull( addParameter );
    this.elemHelper.ClickJS( driver, By.xpath( "//a[@title='Simple parameter']" ) );
    WebElement addedParameter2 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[3]" ) );
    assertNotNull( addedParameter2 );
    WebElement parameterName2 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "form.cdfddInput input" ) );
    assertNotNull( parameterName2 );
    this.elemHelper.SendKeysAndSubmit( driver, By.cssSelector( "form.cdfddInput input" ), value2 );
    String parameterValue2 = this.elemHelper.WaitForTextDifferentEmpty( driver, By.xpath( "//td[contains(text(), 'Name')]/../td[2]" ) );
    assertEquals( parameterValue2, value2 );
    parameterValue2 = this.elemHelper.WaitForTextDifferentEmpty( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[3]/td[2]" ) );
    assertEquals( parameterValue2, value2 );

    //Add parameter "test"
    String value3 = "test";
    addParameter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='Simple parameter']" ) );
    assertNotNull( addParameter );
    this.elemHelper.ClickJS( driver, By.xpath( "//a[@title='Simple parameter']" ) );
    WebElement addedParameter3 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[4]" ) );
    assertNotNull( addedParameter3 );
    WebElement parameterName3 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "form.cdfddInput input" ) );
    assertNotNull( parameterName3 );
    this.elemHelper.SendKeysAndSubmit( driver, By.cssSelector( "form.cdfddInput input" ), value3 );
    String parameterValue3 = this.elemHelper.WaitForTextDifferentEmpty( driver, By.xpath( "//td[contains(text(), 'Name')]/../td[2]" ) );
    assertEquals( parameterValue3, value3 );
    parameterValue3 = this.elemHelper.WaitForTextDifferentEmpty( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[4]/td[2]" ) );
    assertEquals( parameterValue3, value3 );

    //Add parameter "test.ing"
    String value4 = "test.ing";
    String value5 = "testing";
    addParameter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='Simple parameter']" ) );
    assertNotNull( addParameter );
    this.elemHelper.ClickJS( driver, By.xpath( "//a[@title='Simple parameter']" ) );
    WebElement addedParameter4 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[5]" ) );
    assertNotNull( addedParameter4 );
    WebElement parameterName4 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "form.cdfddInput input" ) );
    assertNotNull( parameterName4 );
    this.elemHelper.SendKeysAndSubmit( driver, By.cssSelector( "form.cdfddInput input" ), value4 );
    assertTrue( cdeditor.PopupPresent() );
    Popup dialogPopup = cdeditor.Popup();
    assertNotNull( dialogPopup );
    assertEquals( dialogPopup.getTitle(), "Invalid Input" );
    assertEquals( dialogPopup.getMessage(), "Argument " + value4 + " invalid. Can only contain alphanumeric characters and the special _ character" );
    assertTrue( cdeditor.PopupNotPresent() );
    this.elemHelper.SendKeysAndSubmit( driver, By.cssSelector( "form.cdfddInput input" ), value5 );
    String parameterValue5 = this.elemHelper.WaitForTextDifferentEmpty( driver, By.xpath( "//td[contains(text(), 'Name')]/../td[2]" ) );
    assertEquals( parameterValue5, value5 );
    parameterValue5 = this.elemHelper.WaitForTextDifferentEmpty( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[5]/td[2]" ) );
    assertEquals( parameterValue5, value5 );

    //Add parameter "..."
    String value6 = "...";
    String value7 = "1test_ing";
    addParameter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='Simple parameter']" ) );
    assertNotNull( addParameter );
    addParameter.click();
    WebElement addedParameter6 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[6]" ) );
    assertNotNull( addedParameter6 );
    WebElement parameterName6 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "form.cdfddInput input" ) );
    assertNotNull( parameterName6 );
    this.elemHelper.SendKeysAndSubmit( driver, By.cssSelector( "form.cdfddInput input" ), value6 );
    assertTrue( cdeditor.PopupPresent() );
    Popup dialogPopup2 = cdeditor.Popup();
    assertNotNull( dialogPopup2 );
    assertEquals( dialogPopup2.getTitle(), "Invalid Input" );
    assertEquals( dialogPopup2.getMessage(), "Argument " + value6 + " invalid. Can only contain alphanumeric characters and the special _ character" );
    assertTrue( cdeditor.PopupNotPresent() );
    this.elemHelper.SendKeysAndSubmit( driver, By.cssSelector( "form.cdfddInput input" ), value7 );
    String parameterValue7 = this.elemHelper.WaitForTextDifferentEmpty( driver, By.xpath( "//td[contains(text(), 'Name')]/../td[2]" ) );
    assertEquals( parameterValue7, value7 );
    parameterValue7 = this.elemHelper.WaitForTextDifferentEmpty( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[6]/td[2]" ) );
    assertEquals( parameterValue7, value7 );

    /*
     * ## Step 3
     */
    //Expand Charts
    WebElement othersExpander = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[2]/h3/span" ) );
    assertNotNull( othersExpander );
    othersExpander.click();

    //Add Table Component
    WebElement addTable = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='table Component']" ) );
    assertNotNull( addTable );
    addTable.click();
    WebElement addedTable = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[7]" ) );
    assertNotNull( addedTable );

    //Open Parameter assigning dialog
    WebElement paramProperty = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody//td[@title=' Parameters to pass to the component']/../td[2]" ) );
    assertNotNull( paramProperty );
    paramProperty.click();
    WebElement paramPopup = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "popup" ) );
    assertNotNull( paramPopup );

    /*
     * ## Step 4
     */
    //Click button to open parameter list
    WebElement paramPopupButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "val_0" ) );
    assertNotNull( paramPopupButton );
    paramPopupButton.click();

    //Press down to list all parameters added previews
    Actions a = new Actions( driver );
    a.sendKeys( Keys.DOWN ).build().perform();
    WebElement paramList = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "ui-id-1" ) );
    assertNotNull( paramList );

    //Assert list of parameters is alphabetically ordered
    String param1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='popup_state_state0']//ul/li[1]/a" ) );
    String param2 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='popup_state_state0']//ul/li[2]/a" ) );
    String param3 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='popup_state_state0']//ul/li[3]/a" ) );
    String param4 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='popup_state_state0']//ul/li[4]/a" ) );
    String param5 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='popup_state_state0']//ul/li[5]/a" ) );
    assertEquals( param1, value7 );
    assertEquals( param2, value1 );
    assertEquals( param3, value3 );
    assertEquals( param4, value5 );
    assertEquals( param5, value2 );

    WebElement cancelButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "popup_state0_buttonCancel" ) );
    assertNotNull( cancelButton );
    cancelButton.click();
    this.elemHelper.WaitForElementNotPresent( driver, By.id( "popupstates" ) );

    /*
     * ## Step 5
     */
    WebElement columnTypes = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody//td[@title='The table column types. Example: [numeric, string, circle, link, sparkline]']/../td[2]" ) );
    assertNotNull( columnTypes );
    columnTypes.click();
    WebElement columnPopup = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "popup" ) );
    assertNotNull( columnPopup );
    WebElement columnType = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "arg_0" ) );
    assertNotNull( columnType );
    columnType.click();

    a.sendKeys( Keys.DOWN ).build().perform();
    WebElement columnList = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='popup_state_state0']//ul" ) );
    assertNotNull( columnList );
    WebElement hiddenOption = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='popup_state_state0']//ul//a[contains(text(),'hidden')]" ) );
    assertNotNull( hiddenOption );
  }
}
