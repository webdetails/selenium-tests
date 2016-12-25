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
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-286
 * 
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1016
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDE286 extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDE286.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Asserting CGG Dial Chart can be exported and viewed via URL
   *
   * Description:
   *    The test pretends validate the CDE-286 issue, so when user exports a CGG Dial Component it can
   *    be viewed via the URL given.
   *
   * Steps:
   *    1. Wait for new Dashboard to be created, assert elements on page and click "Components Panel"
   *    2. Add CGG Dial Component and fill it's properties
   *    3. Click "Shift+G" to create export file and choose URL
   *    4. Add "&param=25" to URL and assert Dial Component is properly shown
   * @throws InterruptedException 
   *
   */
  @Test
  public void tc01_NewCdeDashboard_CggDialComponentExport() {
    this.log.info( "tc01_NewCdeDashboard_CggDialComponentExport" );

    /*
     * ## Step 1
     */
    //Go to New CDE Dashboard
    driver.get( PageUrl.CDE_DASHBOARD );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
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
    this.elemHelper.Click( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div/h3/span" ) );
    this.elemHelper.Click( driver, By.xpath( "//div[@id='cdfdd-components-palletePallete']/div/div/ul/li[24]/a[@title='CGG Dial Chart']" ) );
    String componentName = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='table-cdfdd-components-components']/tbody/tr[2]/td" ) );
    assertEquals( componentName, "CGG Dial Component" );

    //Add Name
    String expectedChartName = "dial";
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
    this.elemHelper.ClickAndSendKeys( driver, By.id( "arg_0" ), strColor3 );
    //Add the second color
    this.elemHelper.ClickAndSendKeys( driver, By.id( "arg_1" ), strColor2 );
    //Add the third color
    this.elemHelper.ClickAndSendKeys( driver, By.id( "arg_2" ), strColor1 );

    //Submit
    this.elemHelper.Click( driver, By.id( "popup_state0_buttonOk" ) );
    //Wait For Popup Disappear
    this.elemHelper.WaitForElementNotPresent( driver, By.id( "popupbox" ) );
    //Check the colors array
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[2]/td[2]" ), "[\"" + strColor3 + "\",\"" + strColor2 + "\",\"" + strColor1 + "\"]" );
    String rangeColorArray = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[2]/td[2]" ) );
    assertEquals( "[\"brown\",\"green\",\"blue\"]", rangeColorArray );

    //Add Intervals Array
    String strInterval0 = "100";
    String strInterval1 = "50";
    String strInterval2 = "25";
    String strInterval3 = "0";
    this.elemHelper.Click( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[3]/td[2]" ) );
    //We need to wait for the animation finish for the display popup
    this.elemHelper.FindElement( driver, By.id( "popup" ) );
    //Add intervals
    //this.elemHelper.Click( driver, By.cssSelector( "button.popup-add-row-button" ) );
    WebElement elemInterArg0 = this.elemHelper.FindElement( driver, By.id( "arg_0" ) );
    this.elemHelper.Click( driver, By.cssSelector( "button.popup-add-row-button" ) ); // Add arg1
    WebElement elemInterArg1 = this.elemHelper.FindElement( driver, By.id( "arg_1" ) );
    this.elemHelper.Click( driver, By.cssSelector( "button.popup-add-row-button" ) ); // Add arg2
    WebElement elemInterArg2 = this.elemHelper.FindElement( driver, By.id( "arg_2" ) );
    this.elemHelper.Click( driver, By.cssSelector( "button.popup-add-row-button" ) ); // Add arg3
    WebElement elemInterArg3 = this.elemHelper.FindElement( driver, By.id( "arg_3" ) );
    assertNotNull( elemInterArg0 );
    assertNotNull( elemInterArg1 );
    assertNotNull( elemInterArg2 );
    assertNotNull( elemInterArg3 );
    //Add interval 0
    this.elemHelper.ClickAndSendKeys( driver, By.id( "arg_0" ), strInterval3 );
    //Add interval 1
    this.elemHelper.ClickAndSendKeys( driver, By.id( "arg_1" ), strInterval2 );
    //Add interval 2
    this.elemHelper.ClickAndSendKeys( driver, By.id( "arg_2" ), strInterval1 );
    //Add interval 3
    this.elemHelper.ClickAndSendKeys( driver, By.id( "arg_3" ), strInterval0 );

    // Submit
    this.elemHelper.ClickJS( driver, By.id( "popup_state0_buttonOk" ) );
    //Wait For Popup Disappear
    this.elemHelper.WaitForElementNotPresent( driver, By.id( "popupbox" ) );
    //Check if was saved
    String expectedIntervalArray = "[\"" + strInterval3 + "\",\"" + strInterval2 + "\",\"" + strInterval1 + "\",\"" + strInterval0 + "\"]";
    this.elemHelper.WaitForTextPresence( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[3]/td[2]" ), expectedIntervalArray );
    String actualIntervalsArray = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[3]/td[2]" ) );
    assertEquals( expectedIntervalArray, actualIntervalsArray );

    //Add Parameter
    String expectedParameter = "27";
    this.elemHelper.Click( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[4]/td[2]" ) );
    this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[4]/td[2]/form/input" ) ).sendKeys( expectedParameter );
    this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[4]/td[2]/form/input" ) ).submit();
    String actualParameter = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[4]/td[2]" ) );
    assertEquals( expectedParameter, actualParameter );

    //Add Height
    String expectedHeight = "321";
    this.elemHelper.Click( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[6]/td[2]" ) );
    this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[6]/td[2]/form/input" ) ).sendKeys( expectedHeight );
    this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[6]/td[2]/form/input" ) ).submit();
    String actualHeight = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[6]/td[2]" ) );
    assertEquals( expectedHeight, actualHeight );

    //Add Width
    String expectedWidth = "215";
    this.elemHelper.Click( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[7]/td[2]" ) );
    this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[7]/td[2]/form/input" ) ).sendKeys( expectedWidth );
    this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[7]/td[2]/form/input" ) ).submit();
    String actualWidth = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']/tbody/tr[7]/td[2]" ) );
    assertEquals( expectedWidth, actualWidth );

    /*
     * ## Step 3
     */
    Actions a = new Actions( driver );
    a.keyDown( Keys.SHIFT ).sendKeys( "g" ).keyUp( Keys.SHIFT ).build().perform();
    WebElement cggDialog = this.elemHelper.WaitForElementPresence( driver, By.id( "cggDialog" ) );
    assertNotNull( cggDialog );
    String actualCggDialogTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='cggDialog']/h3" ) );
    assertEquals( "Choose what charts to render as CGG", actualCggDialogTitle );
    String actualChartNameTorender = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='cggDialog']/div/span" ) );
    assertEquals( expectedChartName, actualChartNameTorender );
    this.elemHelper.Click( driver, By.xpath( "//div[@id='cggDialog']/div/input" ) );
    this.elemHelper.Click( driver, By.xpath( "//div[@id='cggDialog']/div/button" ) );
    String actualUrl = this.elemHelper.GetAttribute( driver, By.xpath( "//div[@id='cggDialog']/div/div/input" ), "value" );
    String expectedURL = baseUrl + "plugin/cgg/api/services/draw?script=/system/pentaho-cdf-dd/resources/custom/components/cgg/charts/dial.js&paramcolors=" + strColor3 + "&paramcolors=" + strColor2 + "&paramcolors=" + strColor1 + "&paramscale=" + strInterval3 + "&paramscale=" + strInterval2 + "&paramscale=" + strInterval1 + "&paramscale=" + strInterval0 + "&height=" + expectedHeight + "&width=" + expectedWidth + "&outputType=png";
    assertEquals( expectedURL, actualUrl );

    /*
     * ## Step 4
     */
    driver.get( expectedURL + "&paramvalue=25" );
    WebElement elemImg = this.elemHelper.FindElement( driver, By.cssSelector( "img" ) );
    assertNotNull( elemImg );
    String actualImgUrl = this.elemHelper.GetAttribute( driver, By.cssSelector( "img" ), "src" );
    assertEquals( actualImgUrl, expectedURL + "&paramvalue=25" );

  }

}
