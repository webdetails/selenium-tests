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

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

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
   *    The test pretends validate the CDE-505 issue, so when user clicks to add parameters the list shown is 
   *    presented in alphabetical order. Also we want to validate the CDE-507 issue where a "hidden" option is added to
   *    Table's selectable ColumnTypes. For CDE-534 asserting that empty entry is added when opening parameters
   *    and column types.
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
    driver.get( PageUrl.CDE_DASHBOARD );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    WebElement componentsButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='componentsPanelButton']" ) );
    assertNotNull( componentsButton );
    componentsButton.click();
    WebElement componentsPanel = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "panel-componentens_panel" ) );
    assertNotNull( componentsPanel );

    /*
     * ## Step 2
     */
    //Expand Generic
    WebElement genericExpander = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[3]/h3/span" ) );
    assertNotNull( genericExpander );
    genericExpander.click();

    //Add parameter "a"
    WebElement addParameter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='Simple parameter']" ) );
    assertNotNull( addParameter );
    addParameter.click();
    WebElement addedParameter1 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[2]" ) );
    assertNotNull( addedParameter1 );
    WebElement parameterName1 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody//td[@title='Name']/../td[2]/form/input" ) );
    assertNotNull( parameterName1 );
    parameterName1.sendKeys( "a" );

    //Add parameter "b"
    addParameter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='Simple parameter']" ) );
    assertNotNull( addParameter );
    addParameter.click();
    WebElement addedParameter2 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[3]" ) );
    assertNotNull( addedParameter2 );
    WebElement parameterName2 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody//td[@title='Name']/../td[2]/form/input" ) );
    assertNotNull( parameterName2 );
    parameterName2.sendKeys( "b" );

    //Add parameter "test"
    addParameter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='Simple parameter']" ) );
    assertNotNull( addParameter );
    addParameter.click();
    WebElement addedParameter3 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[4]" ) );
    assertNotNull( addedParameter3 );
    WebElement parameterName3 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody//td[@title='Name']/../td[2]/form/input" ) );
    assertNotNull( parameterName3 );
    parameterName3.sendKeys( "test" );

    //Add parameter "test.ing"
    addParameter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='Simple parameter']" ) );
    assertNotNull( addParameter );
    addParameter.click();
    WebElement addedParameter4 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[5]" ) );
    assertNotNull( addedParameter4 );
    WebElement parameterName4 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody//td[@title='Name']/../td[2]/form/input" ) );
    assertNotNull( parameterName4 );
    parameterName4.sendKeys( "test.ing" );

    //Add parameter "testing"
    addParameter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='Simple parameter']" ) );
    assertNotNull( addParameter );
    addParameter.click();
    WebElement addedParameter5 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[6]" ) );
    assertNotNull( addedParameter5 );
    WebElement parameterName5 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody//td[@title='Name']/../td[2]/form/input" ) );
    assertNotNull( parameterName5 );
    parameterName5.sendKeys( "testing" );

    //Add parameter "..."
    addParameter = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='Simple parameter']" ) );
    assertNotNull( addParameter );
    addParameter.click();
    WebElement addedParameter6 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[7]" ) );
    assertNotNull( addedParameter6 );
    WebElement parameterName6 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody//td[@title='Name']/../td[2]/form/input" ) );
    assertNotNull( parameterName6 );
    parameterName6.sendKeys( "..." );

    /*
     * ## Step 3
     */
    //Expand Charts
    WebElement othersExpander = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div[2]/h3/span" ) );
    assertNotNull( othersExpander );
    othersExpander.click();

    //Add CCC Chart
    WebElement addTable = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//a[@title='table Component']" ) );
    assertNotNull( addTable );
    addTable.click();
    WebElement addedTable = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[9]" ) );
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
    WebElement paramPopupButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "parameter_button_0" ) );
    assertNotNull( paramPopupButton );
    paramPopupButton.click();
    WebElement paramList = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "parameterList" ) );
    assertNotNull( paramList );

    //Assert list of parameters is alphabetically ordered
    String param1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='parameterList']/ul/li[1]/div" ) );
    String param2 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='parameterList']/ul/li[2]/div" ) );
    String param3 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='parameterList']/ul/li[3]/div" ) );
    String param4 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='parameterList']/ul/li[4]/div" ) );
    String param5 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='parameterList']/ul/li[5]/div" ) );
    String param6 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='parameterList']/ul/li[6]/div" ) );
    assertEquals( "...", param1 );
    assertEquals( "a", param2 );
    assertEquals( "b", param3 );
    assertEquals( "test", param4 );
    assertEquals( "test.ing", param5 );
    assertEquals( "testing", param6 );
    WebElement cancelButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='parameterList']/../..//button[@id='popup_state0_buttonCancel']" ) );
    assertNotNull( cancelButton );
    cancelButton.click();
    this.elemHelper.WaitForElementNotPresent( driver, By.id( "parameterList" ) );
    cancelButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "popup_state0_buttonCancel" ) );
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
    Robot robot;
    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_DOWN );
      robot.keyRelease( KeyEvent.VK_DOWN );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }
    WebElement columnList = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='StringArray']/ul" ) );
    assertNotNull( columnList );
    WebElement hiddenOption = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='StringArray']/ul//a[contains(text(),'hidden')]" ) );
    assertNotNull( hiddenOption );

  }

}
