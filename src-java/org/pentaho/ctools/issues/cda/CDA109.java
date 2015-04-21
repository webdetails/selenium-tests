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
package org.pentaho.ctools.issues.cda;

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
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDA-109
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-954
 *
 * NOTE
 * To test this script it is required to have CDA plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDA109 {
  // Instance of the driver (browser emulator)
  private static WebDriver DRIVER;
  // The base url to be append the relative url in test
  private static String BASE_URL;
  // Log instance
  private static Logger LOG = LogManager.getLogger( CDA109.class );
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( DRIVER );

  @BeforeClass
  public static void setUpClass() {
    LOG.info( "setUp##" + CDA109.class.getSimpleName() );
    DRIVER = CToolsTestSuite.getDriver();
    BASE_URL = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Asserting ability to edit CDA file with spaces in the path
   * Description:
   *    The test pretends validate the CDA-109 issue, when editing a CDA file that has spaces
   *    in the path the file is shown correctly.
   *
   * Steps:
   *    1. Assert path to file
   *    2. Assert query is correctly shown
   *
   */
  @Test( timeout = 120000 )
  public void tc01_CdaFileEditor_SpacePathWorks() {
    LOG.info( "tc01_CdaFileEditor_SpacePathWorks" );

    /*
     * ## Step 1
     */
    //Go to User Console
    DRIVER.get( BASE_URL + "plugin/cda/api/editFile?path=/public/Issues/CDA/CDA%20-%20109/.cda" );

    //Wait for Elements outside of iFrame
    WebElement element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@class='webdetailsLogo']" ) );
    assertNotNull( element );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//span[@id='staticfile']" ) );
    assertNotNull( element );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//button[@id='save']" ) );
    assertNotNull( element );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//button[@id='reload']" ) );
    assertNotNull( element );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//button[@id='preview']" ) );
    assertNotNull( element );
    String filePath = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//span[@id='staticfile']" ) );
    assertEquals( "/public/Issues/CDA/CDA - 109/.cda", filePath );

    /*
     * ## Step 2
     */
    WebElement elementFrame = ElementHelper.FindElement( DRIVER, By.xpath( "//iframe" ) );
    WebDriver frame = DRIVER.switchTo().frame( elementFrame );

    element = ElementHelper.WaitForElementPresenceAndVisible( frame, By.xpath( "//pre[@id='editArea']/div[2]/div[1]/div[3]" ) );
    assertNotNull( element );
    String ln1Text = ElementHelper.WaitForElementPresentGetText( frame, By.xpath( "//pre[@id='editArea']/div[2]/div[1]/div[3]/div[2]/span[2]" ) );
    assertEquals( "CDADescriptor", ln1Text );
    ln1Text = ElementHelper.WaitForElementPresentGetText( frame, By.xpath( "//pre[@id='editArea']/div[2]/div[1]/div[3]/div[3]/span[2]" ) );
    assertEquals( "DataSources", ln1Text );
    ln1Text = ElementHelper.WaitForElementPresentGetText( frame, By.xpath( "//pre[@id='editArea']/div[2]/div[1]/div[3]/div[4]/span[3]" ) );
    assertEquals( "Connection", ln1Text );
    ln1Text = ElementHelper.WaitForElementPresentGetText( frame, By.xpath( "//pre[@id='editArea']/div[2]/div[1]/div[3]/div[5]/span[4]" ) );
    assertEquals( "Catalog", ln1Text );
  }

  @AfterClass
  public static void tearDownClass() {
    LOG.info( "tearDown##" + CDA109.class.getSimpleName() );
  }
}
