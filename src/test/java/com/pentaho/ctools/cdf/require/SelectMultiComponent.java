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
package com.pentaho.ctools.cdf.require;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.Test;

import com.pentaho.ctools.suite.CToolsTestSuite;
import com.pentaho.ctools.utils.BaseTest;
import com.pentaho.ctools.utils.ElementHelper;

/**
 * Testing the functionalities related with Select Multi Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class SelectMultiComponent extends BaseTest {
  // Instance to be used on wait commands
  private final Wait<WebDriver> wait = CToolsTestSuite.getWait();
  // The base url to be append the relative url in test
  private final String baseUrl = CToolsTestSuite.getBaseUrl();
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( SelectMultiComponent.class );

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Open Sample Page
   */
  @Test
  public void tc0_OpenSamplePage_Display() {
    this.log.info( "tc0_OpenSamplePage_Display" );

    // The URL for the SelectMultiComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Documentation/Component Reference/Core Components/SelectMultiComponent
    this.driver.get( this.baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A19-SelectMultiComponent%3Aselect_multi_component.xcdf/generatedContent" );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( this.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Reload Sample
   * Description:
   *    Reload the sample (not refresh page).
   * Steps:
   *    1. Click in Code and then click in button 'Try me'.
   */
  @Test
  public void tc1_PageContent_DisplayTitle() {
    this.log.info( "tc1_PageContent_DisplayTitle" );

    // Wait for title become visible and with value 'Community Dashboard Framework'
    this.wait.until( ExpectedConditions.titleContains( "Community Dashboard Framework" ) );
    // Wait for visibility of 'VisualizationAPIComponent'
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );

    // Validate the sample that we are testing is the one
    assertEquals( "Community Dashboard Framework", this.driver.getTitle() );
    assertEquals( "SelectMultiComponent", this.elemHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Reload Sample
   * Description:
   *    Reload the sample (not refresh page).
   * Steps:
   *    1. Click in Code and then click in button 'Try me'.
   */
  @Test
  public void tc2_ReloadSample_SampleReadyToUse() {
    this.log.info( "tc2_ReloadSample_SampleReadyToUse" );

    // ## Step 1
    // Render again the sample
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='example']/ul/li[2]/a" ) ).click();
    this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='code']/button" ) ).click();

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( this.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    // Now sample element must be displayed
    assertTrue( this.elemHelper.FindElement( this.driver, By.id( "sample" ) ).isDisplayed() );

    //Check the number of divs with id 'SampleObject'
    //Hence, we guarantee when click Try Me the previous div is replaced
    int nSampleObject = this.driver.findElements( By.id( "sampleObject" ) ).size();
    assertEquals( 1, nSampleObject );
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Select options one by one
   * Description:
   *    We pretend validate the selection of each option one by one.
   * Steps:
   *    1. Select Southern
   *    2. Select Eastern
   *    3. Select Central
   *    4. Select Western
   */
  @Test
  public void tc3_SelectEachItem_AlertDisplayed() {
    this.log.info( "tc3_SelectEachItem_AlertDisplayed" );

    // ## Step 1
    this.wait.until( ExpectedConditions.visibilityOfAllElementsLocatedBy( By.cssSelector( "select" ) ) );
    Select list = new Select( this.elemHelper.FindElement( this.driver, By.cssSelector( "select" ) ) );
    list.selectByValue( "Southern" );
    this.wait.until( ExpectedConditions.alertIsPresent() );
    Alert alert = this.driver.switchTo().alert();
    String confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern", confirmationMsg );

    // ## Step 2
    list = new Select( this.elemHelper.FindElement( this.driver, By.cssSelector( "select" ) ) );
    list.deselectByValue( "Southern" );
    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    alert.accept();

    list = new Select( this.elemHelper.FindElement( this.driver, By.cssSelector( "select" ) ) );
    list.selectByValue( "Eastern" );
    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Eastern", confirmationMsg );

    // ## Step 3
    list = new Select( this.elemHelper.FindElement( this.driver, By.cssSelector( "select" ) ) );
    list.deselectByValue( "Eastern" );
    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    alert.accept();

    list = new Select( this.elemHelper.FindElement( this.driver, By.cssSelector( "select" ) ) );
    list.selectByValue( "Central" );
    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Central", confirmationMsg );

    // ## Step 4
    list = new Select( this.elemHelper.FindElement( this.driver, By.cssSelector( "select" ) ) );
    list.deselectByValue( "Central" );
    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    alert.accept();

    list = new Select( this.elemHelper.FindElement( this.driver, By.cssSelector( "select" ) ) );
    list.selectByValue( "Western" );
    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Western", confirmationMsg );

    //RESET
    list = new Select( this.elemHelper.FindElement( this.driver, By.cssSelector( "select" ) ) );
    list.deselectByValue( "Western" );
    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    alert.accept();
  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name:
   *    Select and deselect all
   * Description:
   *    We pretend validate the selection and deselection of all options.
   * Steps:
   *    1. Select all options
   *    2. Deselect all options
   */
  @Test
  public void tc4_SelectAll_AlertDisplayed() {
    this.log.info( "tc4_SelectAll_AlertDisplayed" );

    // ## Step 1
    this.wait.until( ExpectedConditions.visibilityOfAllElementsLocatedBy( By.cssSelector( "select" ) ) );
    Select list = new Select( this.elemHelper.FindElement( this.driver, By.cssSelector( "select" ) ) );
    list.selectByValue( "Southern" );

    this.wait.until( ExpectedConditions.alertIsPresent() );
    Alert alert = this.driver.switchTo().alert();
    String confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern", confirmationMsg );

    list = new Select( this.elemHelper.FindElement( this.driver, By.cssSelector( "select" ) ) );
    list.selectByValue( "Eastern" );
    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern,Eastern", confirmationMsg );

    list = new Select( this.elemHelper.FindElement( this.driver, By.cssSelector( "select" ) ) );
    list.selectByValue( "Central" );
    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern,Eastern,Central", confirmationMsg );

    list = new Select( this.elemHelper.FindElement( this.driver, By.cssSelector( "select" ) ) );
    list.selectByValue( "Western" );
    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern,Eastern,Central,Western", confirmationMsg );

    // ## Step 2
    list = new Select( this.elemHelper.FindElement( this.driver, By.cssSelector( "select" ) ) );
    list.deselectByValue( "Southern" );
    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Eastern,Central,Western", confirmationMsg );

    list = new Select( this.elemHelper.FindElement( this.driver, By.cssSelector( "select" ) ) );
    list.deselectByValue( "Eastern" );
    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Central,Western", confirmationMsg );

    list = new Select( this.elemHelper.FindElement( this.driver, By.cssSelector( "select" ) ) );
    list.deselectByValue( "Central" );
    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Western", confirmationMsg );

    list = new Select( this.elemHelper.FindElement( this.driver, By.cssSelector( "select" ) ) );
    list.deselectByValue( "Western" );
    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: ", confirmationMsg );
  }

  /**
   * ############################### Test Case 5 ###############################
   *
   * Test Case Name:
   *    Select arbitrary
   * Description:
   *    We pretend validate the selection options arbitrary.
   * Steps:
   *    1. Select Arbitrary
   */
  @Test
  public void tc5_SelectArbitrary_AlertDisplayed() {
    this.log.info( "tc5_SelectArbitrary_AlertDisplayed" );

    // ## Step 1
    this.wait.until( ExpectedConditions.visibilityOfAllElementsLocatedBy( By.cssSelector( "select" ) ) );
    Select list = new Select( this.elemHelper.FindElement( this.driver, By.cssSelector( "select" ) ) );
    list.selectByValue( "Eastern" );
    this.wait.until( ExpectedConditions.alertIsPresent() );
    Alert alert = this.driver.switchTo().alert();
    String confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Eastern", confirmationMsg );

    list = new Select( this.elemHelper.FindElement( this.driver, By.cssSelector( "select" ) ) );
    list.selectByValue( "Central" );
    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Eastern,Central", confirmationMsg );

    list = new Select( this.elemHelper.FindElement( this.driver, By.cssSelector( "select" ) ) );
    list.selectByValue( "Southern" );
    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern,Eastern,Central", confirmationMsg );

    list = new Select( this.elemHelper.FindElement( this.driver, By.cssSelector( "select" ) ) );
    list.deselectByValue( "Eastern" );
    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern,Central", confirmationMsg );

    list = new Select( this.elemHelper.FindElement( this.driver, By.cssSelector( "select" ) ) );
    list.selectByValue( "Eastern" );
    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern,Eastern,Central", confirmationMsg );

    list = new Select( this.elemHelper.FindElement( this.driver, By.cssSelector( "select" ) ) );
    list.selectByValue( "Western" );
    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern,Eastern,Central,Western", confirmationMsg );

    list = new Select( this.elemHelper.FindElement( this.driver, By.cssSelector( "select" ) ) );
    list.deselectByValue( "Central" );
    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern,Eastern,Western", confirmationMsg );
  }
}
