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
package org.pentaho.ctools.cdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.PageUrl;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * Testing the functionalities related with Check Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CheckComponent {
  //Instance of the driver (browser emulator)
  private final WebDriver driver = CToolsTestSuite.getDriver();
  // Instance to be used on wait commands
  private final Wait<WebDriver> wait = CToolsTestSuite.getWait();
  //Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  //Log instance
  private final Logger log = LogManager.getLogger( CheckComponent.class );

  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( this.driver );

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Open Sample
   */
  @Test
  public void tc0_OpenSamplePage_Dipslay() {
    // The URL for the CheckComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Documentation/Component Reference/Core Components/CheckComponent
    this.driver.get( PageUrl.CHECK_COMPONENT );

    // NOTE - we have to wait for loading disappear
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
    String pageTitle = this.elemHelper.WaitForTitle( this.driver, "Community Dashboard Framework" );

    // Wait for visibility of 'CheckComponent'
    String sampleTitle = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ), "CheckComponent" );

    // Validate the sample that we are testing is the one
    assertEquals( "Community Dashboard Framework", pageTitle );
    assertEquals( "CheckComponent", sampleTitle );
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
    this.elemHelper.ClickJS( this.driver, By.xpath( "//div[@id='example']/ul/li[2]/a" ) );
    this.elemHelper.ClickJS( this.driver, By.xpath( "//div[@id='code']/button" ) );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( this.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    // Now sample element must be displayed
    assertTrue( this.elemHelper.FindElement( this.driver, By.id( "sample" ) ).isDisplayed() );
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Check options
   * Description:
   *    Here we pretend to check each option one by one and validate if an alert
   *    is raised with correct message.
   * Steps:
   *    1. Check in Southern and validate alert
   *    2. Check in Eastern and validate alert
   *    3. Check in Central and validate alert
   *    4. Check in Western and validate alert
   */
  @Test
  public void tc3_CheckEachOption_AfterCheckAnAlertIsDisplayed() {
    this.log.info( "tc3_CheckEachOption_AfterCheckAnAlertIsDisplayed" );
    String confirmationMsg = "";
    // ## Step 1
    //Click in Southern
    this.elemHelper.ClickJS( this.driver, By.xpath( "//input[@name='regionSelector' and @value='Southern']" ) );

    this.wait.until( ExpectedConditions.alertIsPresent() );
    Alert alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern", confirmationMsg );

    // ## Step 2
    //Click in Eastern
    this.elemHelper.ClickJS( this.driver, By.xpath( "//input[@name='regionSelector' and @value='Eastern']" ) );

    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern,Eastern", confirmationMsg );

    // ## Step 3
    //Click in Central
    this.elemHelper.ClickJS( this.driver, By.xpath( "//input[@name='regionSelector' and @value='Central']" ) );

    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern,Eastern,Central", confirmationMsg );

    // ## Step 4
    //Click in Western
    this.elemHelper.ClickJS( this.driver, By.xpath( "//input[@name='regionSelector' and @value='Western']" ) );

    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern,Eastern,Central,Western", confirmationMsg );
  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name:
   *    Unchecked options
   * Description:
   *    Here we pretend to unchecked each option one by one and validate if an
   *    alert is raised with correct message.
   * Steps:
   *    1. Unchecked Southern and validate alert
   *    2. Unchecked Eastern and validate alert
   *    3. Unchecked Central and validate alert
   *    4. Unchecked Western and validate alert
   */
  @Test
  public void tc4_UncheckedEachOption_AfterUncheckAnAlertIsDisplayed() {
    this.log.info( "tc4_UncheckedEachOption_AfterUncheckAnAlertIsDisplayed" );
    String confirmationMsg = "";
    // ## Step 1
    //Click in Southern
    this.elemHelper.ClickJS( this.driver, By.xpath( "//input[@name='regionSelector' and @value='Southern']" ) );

    this.wait.until( ExpectedConditions.alertIsPresent() );
    Alert alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Eastern,Central,Western", confirmationMsg );

    // ## Step 2
    //Click in Eastern
    this.elemHelper.ClickJS( this.driver, By.xpath( "//input[@name='regionSelector' and @value='Eastern']" ) );

    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Central,Western", confirmationMsg );

    // ## Step 3
    //Click in Central
    this.elemHelper.ClickJS( this.driver, By.xpath( "//input[@name='regionSelector' and @value='Central']" ) );

    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Western", confirmationMsg );

    // ## Step 4
    //Click in Western
    this.elemHelper.ClickJS( this.driver, By.xpath( "//input[@name='regionSelector' and @value='Western']" ) );

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
   *    Check and Unchecked Arbitrary
   * Description:
   *    Here we pretend to check and unchecked arbitrary and validate the alert
   *    message according each action (check and unchecked).
   * Steps:
   *    1. Check and unchecked arbitrary, and validate alert message
   */
  @Test
  public void tc5_UncheckedEachOption_AfterUncheckAnAlertIsDisplayed() {
    this.log.info( "tc5_UncheckedEachOption_AfterUncheckAnAlertIsDisplayed" );
    String confirmationMsg = "";
    //Click in Central
    this.elemHelper.ClickJS( this.driver, By.xpath( "//input[@name='regionSelector' and @value='Central']" ) );

    this.wait.until( ExpectedConditions.alertIsPresent() );
    Alert alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Central", confirmationMsg );

    //Click in Southern
    this.elemHelper.ClickJS( this.driver, By.xpath( "//input[@name='regionSelector' and @value='Southern']" ) );

    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern,Central", confirmationMsg );

    //UnChecked Southern
    this.elemHelper.ClickJS( this.driver, By.xpath( "//input[@name='regionSelector' and @value='Southern']" ) );

    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Central", confirmationMsg );

    //Click in Western
    this.elemHelper.ClickJS( this.driver, By.xpath( "//input[@name='regionSelector' and @value='Western']" ) );

    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Central,Western", confirmationMsg );

    //Click in Western
    this.elemHelper.ClickJS( this.driver, By.xpath( "//input[@name='regionSelector' and @value='Eastern']" ) );

    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Eastern,Central,Western", confirmationMsg );

    //Unchecked Central
    this.elemHelper.ClickJS( this.driver, By.xpath( "//input[@name='regionSelector' and @value='Central']" ) );

    this.wait.until( ExpectedConditions.alertIsPresent() );
    alert = this.driver.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Eastern,Western", confirmationMsg );
  }
}
