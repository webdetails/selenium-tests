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
package org.pentaho.ctools.cde.require;

import static org.junit.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * Testing the functionalities related with Addin Reference edit mode.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class AddinReferenceEdit {
  // Instance of the driver (browser emulator)
  private static WebDriver DRIVER;
  // The base url to be append the relative url in test
  private static String BASE_URL;
  //Log instance
  private static Logger LOG = LogManager.getLogger( AddinReferenceEdit.class );

  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( DRIVER );

  @BeforeClass
  public static void setUpClass() {
    LOG.info( "setUp##" + AddinReferenceEdit.class.getSimpleName() );
    DRIVER = CToolsTestSuite.getDriver();
    BASE_URL = CToolsTestSuite.getBaseUrl();
  }

  @Before
  public void setUpTestCase() {
    //To use after test case run.
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    ChangeAddinReferenceSample
   * Description:
   *    Here we want to change the AddinReference title to use a different font
   *    size.
   * Steps:
   *    1. Check the value on default, which is 18.
   *    2. Edit the sample to have title with font size 34.
   *    3. Check the value on the sample was changed.
   */
  @Test( timeout = 90000 )
  public void tc01_ChangeAddinReferenceSample_FontSizeWasChanged() {
    LOG.info( "tc01_ChangeAddinReferenceSample_FontSizeWasChanged" );

    /*
     * ## Step 1
     */
    //Go to AddinReference
    DRIVER.get( BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Atests%3AAddIns%3AaddIns.wcdf/generatedContent" );
    ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='Title']/span" ) );
    WebElement titleWithFontSize18 = ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='Title']/span" ) );
    assertEquals( "font-size: 18px;", titleWithFontSize18.getAttribute( "style" ) );

    /*
     * ## Step 2
     */
    AddinReferenceEdit.ChangeFontSize( "34" );

    /*
     * ## Step 3
     */
    //Go to AddinReference
    DRIVER.get( BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Atests%3AAddIns%3AaddIns.wcdf/generatedContent" );
    //NOTE - we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    titleWithFontSize18 = ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='Title']/span" ) );
    assertEquals( "font-size: 34px;", titleWithFontSize18.getAttribute( "style" ) );
  }

  /**
   *
   * @param value
   */
  private static void ChangeFontSize( String value ) {
    DRIVER.get( BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Atests%3AAddIns%3AaddIns.wcdf/wcdf.edit" );

    //Expand first row - Title
    ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.cssSelector( "span.expander" ) );
    ElementHelper.ClickJS( DRIVER, By.cssSelector( "span.expander" ) );
    //Click in HTML to open the Properties
    Actions acts = new Actions( DRIVER );
    acts.click( ElementHelper.FindElement( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]/td[1]" ) ) );
    acts.build().perform();
    //Click in field 'Font Size' to be editable
    ElementHelper.ClickJS( DRIVER, By.xpath( "//table[@id='table-cdfdd-layout-properties']/tbody/tr[3]/td[2]" ) );
    //Write 34
    ElementHelper.FindElement( DRIVER, By.name( "value" ) ).clear();
    ElementHelper.FindElement( DRIVER, By.xpath( "//form[@class='cdfddInput']/input" ) ).sendKeys( value );
    ElementHelper.FindElement( DRIVER, By.xpath( "//form[@class='cdfddInput']/input" ) ).submit();
    //Save the changes
    ElementHelper.Click( DRIVER, By.linkText( "Save" ) );
    //Wait for element present and invisible
    ElementHelper.WaitForElementVisibility( DRIVER, By.xpath( "//div[@id='notifyBar']" ) );
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[@id='notifyBar']" ) );
  }

  @AfterClass
  public static void tearDownClass() {
    ChangeFontSize( "18" );

    LOG.info( "tearDown##" + AddinReferenceEdit.class.getSimpleName() );
  }
}
