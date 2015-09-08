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
package com.pentaho.ctools.issues.cda;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.BaseTest;
import com.pentaho.ctools.utils.ElementHelper;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDA-122
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1121
 *
 * NOTE
 * To test this script it is required to have CDA plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDAChangeProperty extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDAChangeProperty.class );
  // this.failure variable ==1 if test did not fail
  private int failure = 1;

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Changes to Datasources properties
   * Description:
   *    CDA-122: Adding parameter to Column property has effect immediately
   * Steps:
   *    1. Open sample, select datasource and confirm column name
   *    2. Edit Column property and save
   *    3. Open sample again, select datasource and assert new column name
   *    4. Edit Column property, undo previous change and save
   */
  @Test
  public void tc01_CDAEdit_ChangeColumnRefreshCache() {
    this.log.info( "tc01_CDAEdit_ChangeColumnRefreshCache" );

    /*
     * ## Step 1
     */
    // Open Sample and select query
    driver.get( baseUrl + "plugin/cda/api/previewQuery?path=/public/Issues/CDE/CDE-379/Chart1.cda" );

    WebElement dataSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "dataAccessSelector" ) );
    assertNotNull( dataSelector );
    Select select = new Select( this.elemHelper.FindElement( driver, By.id( "dataAccessSelector" ) ) );
    select.selectByValue( "Test1" );
    this.elemHelper.FindElement( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    WebElement refreshButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='button']" ) );
    assertNotNull( refreshButton );
    WebElement cacheButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='cachethis']" ) );
    assertNotNull( cacheButton );
    WebElement queryButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='queryUrl']" ) );
    assertNotNull( queryButton );

    // assert Column name
    WebElement columnHeader = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='contents']/thead/tr/th" ) );
    assertNotNull( columnHeader );
    String columnText = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='contents']/thead/tr/th" ) ).getText();
    assertEquals( "CUSTOMER", columnText );

    /*
     * ## Step 2
     */
    //Open file to edit CDA and go to Datasources Panel
    driver.get( baseUrl + "api/repos/%3Apublic%3AIssues%3ACDE%3ACDE-379%3AChart1.wcdf/wcdf.edit" );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    WebElement buttonDatasources = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='datasourcesPanelButton']" ) );
    assertNotNull( buttonDatasources );
    buttonDatasources.click();

    //Select datasource and click Column Property
    WebElement groupExpander = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//tr[@id='SQL']/td/span" ) );
    assertNotNull( groupExpander );
    groupExpander.click();
    WebElement scriptableSource = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[2]/td" ) );
    assertNotNull( scriptableSource );
    scriptableSource.click();
    WebElement columnProperty = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//td[@title='Column Configuration']/../td[2]" ) );
    assertNotNull( columnProperty );
    columnProperty.click();
    WebElement columnPopup = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "popupstates" ) );
    assertNotNull( columnPopup );

    //Add parameter, click Ok and assert parameter was added
    WebElement indexInput = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "arg_0" ) );
    assertNotNull( indexInput );
    indexInput.sendKeys( "0" );
    WebElement nameInput = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "val_0" ) );
    assertNotNull( nameInput );
    nameInput.sendKeys( "testing" );
    WebElement okButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "popup_state0_buttonOk" ) );
    assertNotNull( okButton );
    okButton.click();
    this.elemHelper.WaitForElementNotPresent( driver, By.id( "popupstates" ) );
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//td[@title='Column Configuration']/../td[2]" ), "[[\"0\",\"testing\"]]" );
    String columnsParameter = this.elemHelper.FindElement( driver, By.xpath( "//td[@title='Column Configuration']/../td[2]" ) ).getText();
    assertEquals( "[[\"0\",\"testing\"]]", columnsParameter );

    //Save dashboard
    WebElement saveButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "Save" ) );
    assertNotNull( saveButton );
    saveButton.click();
    WebElement notifyBar = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "notifyBar" ) );
    assertNotNull( notifyBar );
    this.failure = 0;

    /*
     * ## Step 3
     */
    // Open Sample and select query
    driver.get( baseUrl + "plugin/cda/api/previewQuery?path=/public/Issues/CDE/CDE-379/Chart1.cda" );

    dataSelector = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "dataAccessSelector" ) );
    assertNotNull( dataSelector );
    select = new Select( this.elemHelper.FindElement( driver, By.id( "dataAccessSelector" ) ) );
    select.selectByValue( "Test1" );
    this.elemHelper.FindElement( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    refreshButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='button']" ) );
    assertNotNull( refreshButton );
    cacheButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='cachethis']" ) );
    assertNotNull( cacheButton );
    queryButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='queryUrl']" ) );
    assertNotNull( queryButton );

    // assert Column name
    columnHeader = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='contents']/thead/tr/th" ) );
    assertNotNull( columnHeader );
    columnText = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='contents']/thead/tr/th" ) ).getText();
    assertEquals( "testing", columnText );

    /*
     * ## Step 4
     */
    //Open file to edit CDA and go to Datasources Panel
    driver.get( baseUrl + "api/repos/%3Apublic%3AIssues%3ACDE%3ACDE-379%3AChart1.wcdf/wcdf.edit" );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    buttonDatasources = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='datasourcesPanelButton']" ) );
    assertNotNull( buttonDatasources );
    buttonDatasources.click();

    //Select datasource and click Column Property
    groupExpander = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//tr[@id='SQL']/td/span" ) );
    assertNotNull( groupExpander );
    groupExpander.click();
    scriptableSource = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[2]/td" ) );
    assertNotNull( scriptableSource );
    scriptableSource.click();
    columnProperty = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//td[@title='Column Configuration']/../td[2]" ) );
    assertNotNull( columnProperty );
    columnProperty.click();
    columnPopup = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "popupstates" ) );
    assertNotNull( columnPopup );

    //Remove parameter, click Ok and assert parameter was removed
    WebElement removeButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "remove_button_0" ) );
    assertNotNull( removeButton );
    removeButton.click();
    WebElement confirmButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "button.popup-remove-selection" ) );
    assertNotNull( confirmButton );
    confirmButton.click();
    this.elemHelper.WaitForElementNotPresent( driver, By.id( "arg_0" ) );
    this.elemHelper.WaitForElementNotPresent( driver, By.id( "val_0" ) );
    okButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "popup_state0_buttonOk" ) );
    assertNotNull( okButton );
    okButton.click();
    this.elemHelper.WaitForElementNotPresent( driver, By.id( "popupstates" ) );
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//td[@title='Column Configuration']/../td[2]" ), "[]" );
    columnsParameter = this.elemHelper.FindElement( driver, By.xpath( "//td[@title='Column Configuration']/../td[2]" ) ).getText();
    assertEquals( "[]", columnsParameter );

    //Save dashboard
    saveButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "Save" ) );
    assertNotNull( saveButton );
    saveButton.click();
    notifyBar = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "notifyBar" ) );
    assertNotNull( notifyBar );
    this.failure = 1;
  }

  @AfterClass( alwaysRun = true )
  public void tearDown() {
    this.log.info( "tearDownClass" );
    if ( this.failure == 0 ) {
      //Open file to edit CDA and go to Datasources Panel
      driver.get( baseUrl + "api/repos/%3Apublic%3AIssues%3ACDE%3ACDE-379%3AChart1.wcdf/wcdf.edit" );
      this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
      WebElement buttonDatasources = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='datasourcesPanelButton']" ) );
      assertNotNull( buttonDatasources );
      buttonDatasources.click();

      //Select datasource and click Column Property
      WebElement groupExpander = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//tr[@id='SQL']/td/span" ) );
      assertNotNull( groupExpander );
      groupExpander.click();
      WebElement scriptableSource = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[2]/td" ) );
      assertNotNull( scriptableSource );
      scriptableSource.click();
      WebElement columnProperty = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//td[@title='Column Configuration']/../td[2]" ) );
      assertNotNull( columnProperty );
      columnProperty.click();
      WebElement columnPopup = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "popupstates" ) );
      assertNotNull( columnPopup );

      //Remove parameter, click Ok and assert parameter was removed
      WebElement removeButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "remove_button_0" ) );
      assertNotNull( removeButton );
      removeButton.click();
      WebElement confirmButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "button.popup-remove-selection" ) );
      assertNotNull( confirmButton );
      confirmButton.click();
      this.elemHelper.WaitForElementNotPresent( driver, By.id( "arg_0" ) );
      this.elemHelper.WaitForElementNotPresent( driver, By.id( "val_0" ) );
      WebElement okButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "popup_state0_buttonOk" ) );
      assertNotNull( okButton );
      okButton.click();
      this.elemHelper.WaitForElementNotPresent( driver, By.id( "popupstates" ) );
      this.elemHelper.WaitForTextPresence( driver, By.xpath( "//td[@title='Column Configuration']/../td[2]" ), "[]" );
      String columnsParameter = this.elemHelper.FindElement( driver, By.xpath( "//td[@title='Column Configuration']/../td[2]" ) ).getText();
      assertEquals( "[]", columnsParameter );

      //Save dashboard
      WebElement saveButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "Save" ) );
      assertNotNull( saveButton );
      saveButton.click();
      WebElement notifyBar = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "notifyBar" ) );
      assertNotNull( notifyBar );
    }
  }
}
