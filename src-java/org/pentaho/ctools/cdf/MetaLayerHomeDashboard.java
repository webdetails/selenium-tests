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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.StringUtils;
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
import org.pentaho.ctools.utils.HttpUtils;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * Testing the functionalities related with MetaLayerHome.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
/**
 * NOTE - The test was created regarding issue CDF-318
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class MetaLayerHomeDashboard {

  //Instance of the driver (browser emulator)
  private static WebDriver DRIVER;
  // Instance to be used on wait commands
  private static Wait<WebDriver> WAIT;
  // The base url to be append the relative url in test
  private static String BASE_URL;
  //Access to wrapper for webdriver
  private ElementHelper elemHelper = new ElementHelper();
  //Log instance
  private static Logger LOG = LogManager.getLogger( MetaLayerHomeDashboard.class );

  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( DRIVER );

  @BeforeClass
  public static void setUpClass() {
    LOG.info( "setUp##" + MetaLayerHomeDashboard.class.getSimpleName() );
    DRIVER = CToolsTestSuite.getDriver();
    WAIT = CToolsTestSuite.getWait();
    BASE_URL = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    MetaLayer Home Dashboard - clicking details
   * Description:
   *    We pretend to validate when user click on 'Details...' a pop-up message
   *    is displayed.
   * Steps:
   *    1. Open the MetaLayer Home Dashboard.
   *    2. Click in 'Details...'.
   *    3. Check if we have width = 500 and height = 600
   */
  @ Test
  public void tc1_LinkDetails_PopupJPivot() {
    LOG.info( "tc1_LinkDetails_PopupJPivot" );
    /*
     * ## Step 1
     */
    DRIVER.get( BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A20-samples%3Ahome_dashboard_2%3Ahome_dashboard_metalyer.xcdf/generatedContent" );

    //NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Wait for title become visible and with value 'Community Dashboard Framework'
    WAIT.until( ExpectedConditions.titleContains( "Community Dashboard Framework" ) );
    //Wait for visibility of 'Top Ten Customers'
    WAIT.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='titleObject']" ) ) );
    // Validate the sample that we are testing is the one
    assertEquals( "Community Dashboard Framework", DRIVER.getTitle() );
    assertEquals( "Top Ten Customers", this.elemHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='titleObject']" ) ) );

    /*
     * ## Step 2
     */
    //Wait for visibility of 'topTenCustomersDetailsObject' the text 'Details'
    WebElement linkDetails = this.elemHelper.FindElement( DRIVER, By.linkText( "Details..." ) );
    assertEquals( "Details...", linkDetails.getText() );
    //click on the 'Details...'
    linkDetails.click();

    /*
     * ## Step 3
     */
    //Wait for the frame
    this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.id( "fancybox-content" ) );
    this.elemHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//iframe" ) );
    WebElement frame = this.elemHelper.FindElement( DRIVER, By.xpath( "//iframe" ) );
    String valueFrameAttrSrc = frame.getAttribute( "src" );

    ///pentaho/plugin/jpivot/Pivot?solution=system&path=%2Fpublic%2Fplugin-samples%2Fpentaho-cdf%2Factions&action=jpivot.xaction&width=500&height=600
    //Check if we have the sizes 500 and 600
    assertTrue( StringUtils.containsIgnoreCase( valueFrameAttrSrc, "action=jpivot.xaction&width=500&height=600" ) );

    //NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Wait for the element be visible.
    WebDriver driverFrame = DRIVER.switchTo().frame( frame );
    assertNotNull( this.elemHelper.FindElement( driverFrame, By.xpath( "//div[@id='internal_content']" ) ) );
    assertEquals( "Measures", this.elemHelper.WaitForElementPresentGetText( driverFrame, By.xpath( "//div[@id='internal_content']/table/tbody/tr[2]/td[2]/p/table/tbody/tr/th[2]" ) ) );
    assertEquals( "Australian Collectors, Co.", this.elemHelper.WaitForElementPresentGetText( driverFrame, By.xpath( "//div[@id='internal_content']/table[1]/tbody/tr[2]/td[2]/p[1]/table/tbody/tr[5]/th/div" ) ) );
    assertEquals( "180,125", this.elemHelper.WaitForElementPresentGetText( driverFrame, By.xpath( "//div[@id='internal_content']/table[1]/tbody/tr[2]/td[2]/p[1]/table/tbody/tr[7]/td" ) ) );

    //Close pop-up
    DRIVER.switchTo().defaultContent();
    WAIT.until( ExpectedConditions.elementToBeClickable( By.id( "fancybox-close" ) ) );
    String background = this.elemHelper.FindElement( DRIVER, By.cssSelector( "#fancybox-close" ) ).getCssValue( "background-image" );
    String background1 = background.substring( background.indexOf( 34 ) + 1, background.lastIndexOf( 34 ) );
    assertEquals( "http://localhost:8080/pentaho/api/repos/pentaho-cdf/js-legacy/lib/fancybox/fancybox.png", background1 );
    this.elemHelper.FindElement( DRIVER, By.id( "fancybox-close" ) ).click();
    this.elemHelper.WaitForElementInvisibility( DRIVER, By.id( "fancybox-content" ) );
    assertEquals( "200", Integer.toString( HttpUtils.GetResponseCode( background1, "admin", "password" ) ) );

  }

  @AfterClass
  public static void tearDownClass() {
    LOG.info( "tearDown##" + MetaLayerHomeDashboard.class.getSimpleName() );
  }
}
