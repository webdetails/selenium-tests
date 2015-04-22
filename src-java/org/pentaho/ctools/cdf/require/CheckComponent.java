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
package org.pentaho.ctools.cdf.require;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
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
  //Instance of the DRIVER (browser emulator)
  private static WebDriver DRIVER;
  // Instance to be used on wait commands
  private static Wait<WebDriver> WAIT;
  // The base url to be append the relative url in test
  private static String BASE_URL;

  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( DRIVER );

  /**
   * Shall initialized the test before run each test case.
   */
  @BeforeClass
  public static void setUp() {
    DRIVER = CToolsTestSuite.getDriver();
    WAIT = CToolsTestSuite.getWait();
    BASE_URL = CToolsTestSuite.getBaseUrl();

    // Go to sample
    init();
  }

  /**
   * Go to the CheckComponent web page.
   */
  public static void init() {
    // The URL for the CheckComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Documentation/Component
    // Reference/Core Components/CheckComponent
    DRIVER.get( BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A55-CheckComponent%3Acheck_component.xcdf/generatedContent" );

    // NOTE - we have to wait for loading disappear
    ElementHelper.WaitForElementPresence( DRIVER, By.cssSelector( "div.blockUI.blockOverlay" ) );
    ElementHelper.WaitForElementInvisibility( DRIVER, By.cssSelector( "div.blockUI.blockOverlay" ) );
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
  @Test( timeout = 60000 )
  public void tc1_PageContent_DisplayTitle() {
    // Wait for title become visible and with value 'Community Dashboard Framework'
    WAIT.until( ExpectedConditions.titleContains( "Community Dashboard Framework" ) );
    // Wait for visibility of 'VisualizationAPIComponent'
    WAIT.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );

    // Validate the sample that we are testing is the one
    assertEquals( "Community Dashboard Framework", DRIVER.getTitle() );
    assertEquals( "CheckComponent", ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );
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
  @Test( timeout = 60000 )
  public void tc2_ReloadSample_SampleReadyToUse() {
    // ## Step 1
    // Render again the sample
    ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='example']/ul/li[2]/a" ) ).click();
    ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='code']/button" ) ).click();

    // NOTE - we have to wait for loading disappear
    ElementHelper.WaitForElementPresence( DRIVER, By.cssSelector( "div.blockUI.blockOverlay" ) );
    ElementHelper.WaitForElementInvisibility( DRIVER, By.cssSelector( "div.blockUI.blockOverlay" ) );

    // Now sample element must be displayed
    assertTrue( ElementHelper.FindElement( DRIVER, By.id( "sample" ) ).isDisplayed() );
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
  @Test( timeout = 60000 )
  public void tc3_CheckEachOption_AfterCheckAnAlertIsDisplayed() {
    String confirmationMsg = "";
    // ## Step 1
    //Click in Southern
    ElementHelper.Click( DRIVER, By.xpath( "//input[@name='checkComponent' and @value='Southern']" ) );

    WAIT.until( ExpectedConditions.alertIsPresent() );
    Alert alert = DRIVER.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern", confirmationMsg );

    // ## Step 2
    //Click in Eastern
    ElementHelper.Click( DRIVER, By.xpath( "//input[@name='checkComponent' and @value='Eastern']" ) );

    WAIT.until( ExpectedConditions.alertIsPresent() );
    alert = DRIVER.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern,Eastern", confirmationMsg );

    // ## Step 3
    //Click in Central
    ElementHelper.Click( DRIVER, By.xpath( "//input[@name='checkComponent' and @value='Central']" ) );

    WAIT.until( ExpectedConditions.alertIsPresent() );
    alert = DRIVER.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern,Eastern,Central", confirmationMsg );

    // ## Step 4
    //Click in Western
    ElementHelper.Click( DRIVER, By.xpath( "//input[@name='checkComponent' and @value='Western']" ) );

    WAIT.until( ExpectedConditions.alertIsPresent() );
    alert = DRIVER.switchTo().alert();
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
  @Test( timeout = 60000 )
  public void tc4_UncheckedEachOption_AfterUncheckAnAlertIsDisplayed() {
    String confirmationMsg = "";
    // ## Step 1
    //Click in Southern
    ElementHelper.Click( DRIVER, By.xpath( "//input[@name='checkComponent' and @value='Southern']" ) );

    WAIT.until( ExpectedConditions.alertIsPresent() );
    Alert alert = DRIVER.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Eastern,Central,Western", confirmationMsg );

    // ## Step 2
    //Click in Eastern
    ElementHelper.Click( DRIVER, By.xpath( "//input[@name='checkComponent' and @value='Eastern']" ) );

    WAIT.until( ExpectedConditions.alertIsPresent() );
    alert = DRIVER.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Central,Western", confirmationMsg );

    // ## Step 3
    //Click in Central
    ElementHelper.Click( DRIVER, By.xpath( "//input[@name='checkComponent' and @value='Central']" ) );

    WAIT.until( ExpectedConditions.alertIsPresent() );
    alert = DRIVER.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Western", confirmationMsg );

    // ## Step 4
    //Click in Western
    ElementHelper.Click( DRIVER, By.xpath( "//input[@name='checkComponent' and @value='Western']" ) );

    WAIT.until( ExpectedConditions.alertIsPresent() );
    alert = DRIVER.switchTo().alert();
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
  @Test( timeout = 60000 )
  public void tc5_UncheckedEachOption_AfterUncheckAnAlertIsDisplayed() {
    String confirmationMsg = "";
    //Click in Central
    ElementHelper.Click( DRIVER, By.xpath( "//input[@name='checkComponent' and @value='Central']" ) );

    WAIT.until( ExpectedConditions.alertIsPresent() );
    Alert alert = DRIVER.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Central", confirmationMsg );

    //Click in Southern
    ElementHelper.Click( DRIVER, By.xpath( "//input[@name='checkComponent' and @value='Southern']" ) );

    WAIT.until( ExpectedConditions.alertIsPresent() );
    alert = DRIVER.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Southern,Central", confirmationMsg );

    //UnChecked Southern
    ElementHelper.Click( DRIVER, By.xpath( "//input[@name='checkComponent' and @value='Southern']" ) );

    WAIT.until( ExpectedConditions.alertIsPresent() );
    alert = DRIVER.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Central", confirmationMsg );

    //Click in Western
    ElementHelper.Click( DRIVER, By.xpath( "//input[@name='checkComponent' and @value='Western']" ) );

    WAIT.until( ExpectedConditions.alertIsPresent() );
    alert = DRIVER.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Central,Western", confirmationMsg );

    //Click in Western
    ElementHelper.Click( DRIVER, By.xpath( "//input[@name='checkComponent' and @value='Eastern']" ) );

    WAIT.until( ExpectedConditions.alertIsPresent() );
    alert = DRIVER.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Eastern,Central,Western", confirmationMsg );

    //Unchecked Central
    ElementHelper.Click( DRIVER, By.xpath( "//input[@name='checkComponent' and @value='Central']" ) );

    WAIT.until( ExpectedConditions.alertIsPresent() );
    alert = DRIVER.switchTo().alert();
    confirmationMsg = alert.getText();
    alert.accept();
    assertEquals( "you chose: Eastern,Western", confirmationMsg );
  }

  @AfterClass
  public static void tearDown() {
    //To use when class run all test cases.
  }
}
