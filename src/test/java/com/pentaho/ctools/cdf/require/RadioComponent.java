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
package com.pentaho.ctools.cdf.require;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with Radio Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class RadioComponent extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  //Log instance
  private final Logger log = LogManager.getLogger( RadioComponent.class );

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Open Sample Page
   */
  @Test
  public void tc0_OpenSamplePage_Display() {
    this.log.info( "tc0_OpenSamplePage_Display" );

    // The URL for the RadioComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Documentation/Component Reference/Core Components/RadioComponent
    driver.get( baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A52-RadioComponent%3Aradio_component.xcdf/generatedContent" );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
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
    wait.until( ExpectedConditions.titleContains( "Community Dashboard Framework" ) );
    // Wait for visibility of 'VisualizationAPIComponent'
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );

    // Validate the sample that we are testing is the one
    assertEquals( "Community Dashboard Framework", driver.getTitle() );
    assertEquals( "RadioComponent", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );
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

    /*
     * ## Step 1
     */
    // Render again the sample
    this.elemHelper.FindElement( driver, By.xpath( "//div[@id='example']/ul/li[2]/a" ) ).click();
    this.elemHelper.FindElement( driver, By.xpath( "//div[@id='code']/button" ) ).click();

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    // Now sample element must be displayed
    assertTrue( this.elemHelper.FindElement( driver, By.id( "sample" ) ).isDisplayed() );

    //Check the number of divs with id 'SampleObject'
    //Hence, we guarantee when click Try Me the previous div is replaced
    int nSampleObject = driver.findElements( By.id( "sampleObject" ) ).size();
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
   *    1. Select Eastern
   *    2. Select Central
   *    3. Select Western
   *    4. Select Southern
   */
  @Test
  public void tc3_SelectEachItem_AlertDisplayed() {
    this.log.info( "tc3_SelectEachItem_AlertDisplayed" );

    /*
     * ## Step 1
     */
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//input[@value='Eastern']" ) ) );
    this.elemHelper.FindElement( driver, By.xpath( "//input[@value='Eastern']" ) ).click();
    wait.until( ExpectedConditions.alertIsPresent() );
    Alert alert = driver.switchTo().alert();
    String confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Eastern", confirmationMsg );

    /*
     * ## Step 2
     */
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//input[@value='Central']" ) ) );
    this.elemHelper.FindElement( driver, By.xpath( "//input[@value='Central']" ) ).click();
    wait.until( ExpectedConditions.alertIsPresent() );
    alert = driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Central", confirmationMsg );

    /*
     * ## Step 3
     */
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//input[@value='Western']" ) ) );
    this.elemHelper.FindElement( driver, By.xpath( "//input[@value='Western']" ) ).click();
    wait.until( ExpectedConditions.alertIsPresent() );
    alert = driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Western", confirmationMsg );

    /*
     * ## Step 4
     */
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//input[@value='Southern']" ) ) );
    this.elemHelper.FindElement( driver, By.xpath( "//input[@value='Southern']" ) ).click();
    wait.until( ExpectedConditions.alertIsPresent() );
    alert = driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern", confirmationMsg );
  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name:
   *    Select arbitrary options
   * Description:
   *    We pretend validate the selection every available options but arbitrary.
   * Steps:
   *    1. Select Western
   *    2. Select Southern
   *    3. Select Central
   *    4. Select Western
   */
  @Test
  public void tc4_SelectArbitrary_AlertDisplayed() {
    this.log.info( "tc4_SelectArbitrary_AlertDisplayed" );

    /*
     * ## Step 1
     */
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//input[@value='Western']" ) ) );
    this.elemHelper.FindElement( driver, By.xpath( "//input[@value='Western']" ) ).click();
    wait.until( ExpectedConditions.alertIsPresent() );
    Alert alert = driver.switchTo().alert();
    String confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Western", confirmationMsg );

    /*
     * ## Step 2
     */
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//input[@value='Southern']" ) ) );
    this.elemHelper.FindElement( driver, By.xpath( "//input[@value='Southern']" ) ).click();
    wait.until( ExpectedConditions.alertIsPresent() );
    alert = driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern", confirmationMsg );

    /*
     * ## Step 3
     */
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//input[@value='Central']" ) ) );
    this.elemHelper.FindElement( driver, By.xpath( "//input[@value='Central']" ) ).click();
    wait.until( ExpectedConditions.alertIsPresent() );
    alert = driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Central", confirmationMsg );

    /*
     * ## Step 4
     */
    wait.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//input[@value='Western']" ) ) );
    this.elemHelper.FindElement( driver, By.xpath( "//input[@value='Western']" ) ).click();
    wait.until( ExpectedConditions.alertIsPresent() );
    alert = driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Western", confirmationMsg );
  }
}
