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
package org.pentaho.ctools.cde.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * Testing the functionalities related with Olap Selector Component (Sniff Test).
 *
 * Naming convention for test: 'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class OlapSelectorComponent {

  // Instance of the driver (browser emulator)
  private static WebDriver       DRIVER;
  // Instance to be used on wait commands
  private static Wait<WebDriver> WAIT;
  // The base url to be append the relative url in test
  private static String          BASE_URL;
  // Log instance
  private static Logger          LOG                = LogManager.getLogger( OlapSelectorComponent.class );

  @Rule
  public ScreenshotTestRule      screenshotTestRule = new ScreenshotTestRule( DRIVER );

  @BeforeClass
  public static void setUpClass() {
    LOG.info( "setUp##" + OlapSelectorComponent.class.getSimpleName() );
    DRIVER = CToolsTestSuite.getDriver();
    WAIT = CToolsTestSuite.getWait();
    BASE_URL = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
      * Test Case Name:
   *    Sniff test to sample
   *
   * Description:
   *    This test is to assert simple functionality of sample 
   *
   * Steps:
   *    1. Opne sample and assert selector button is shown
   *    2. Click button and assert options are shown
   *    3. Make options and click ok, assert options were correctly selected
   *    
   */
  @Test( timeout = 60000 )
  public void tc01_OlapSelectorComponent_SniffTestSample() {
    LOG.info( "tc01_OlapSelectorComponent_SniffTestSample" );

    /*
     * ## Step 1
     */
    // Go to Olap Selector Sample
    DRIVER.get( BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3AOlapSelector%3AolapSelector.wcdf/generatedContent" );

    // NOTE - we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility( DRIVER, By.cssSelector( "div.blockUI.blockOverlay" ) );

    // Check page title
    WAIT.until( ExpectedConditions.titleIs( "olapSelector" ) );
    assertEquals( "olapSelector", DRIVER.getTitle() );

    // Wait for Selector button
    WebElement selectorButton = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='column1']/div/div/div[@class='title']" ) );
    assertNotNull( selectorButton );

    /*
     * ## Step 2
     */
    //click selector button
    selectorButton.click();

    //Assert Options are shown
    WebElement optionList = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='column1']/div/div/div[@class='optionList']" ) );
    assertNotNull( optionList );
    WebElement firstSelection = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@class='options']//span[@title='Alpha Cognac']" ) );
    assertNotNull( firstSelection );
    String firstName = firstSelection.getText();
    WebElement secondSelection = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@class='options']//span[@title='Asian Shopping Network, Co']" ) );
    assertNotNull( secondSelection );
    String secondName = secondSelection.getText();
    WebElement thirdSelection = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@class='options']//span[@title='Diecast Collectables']" ) );
    assertNotNull( thirdSelection );
    String thirdName = thirdSelection.getText();

    /*
     * ## Step 3
     */
    ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@class='options']//span[@title='Alpha Cognac']" ) ).click();
    ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@class='options']//span[@title='Asian Shopping Network, Co']" ) ).click();
    ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@class='options']//span[@title='Diecast Collectables']" ) ).click();
    WebElement applyButton = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@class='footer']/div[@class='button validate']" ) );
    assertNotNull( applyButton );
    applyButton.click();
    ElementHelper.WaitForElementNotPresent( DRIVER, By.xpath( "//div[@id='column1']/div/div/div[@class='optionList']" ) );
    String selectedCustomers = ElementHelper.WaitForElementPresentGetText( DRIVER, By.id( "columnLabel" ) );
    String expectedCustomers = "[\"[Customers].[" + firstName + "]\"\"[Customers].[" + secondName + "]\"\"[Customers].[" + thirdName + "]\"]";
    assertEquals( expectedCustomers, selectedCustomers );

  }

  @AfterClass
  public static void tearDownClass() {
    LOG.info( "tearDown##" + OlapSelectorComponent.class.getSimpleName() );
  }
}
