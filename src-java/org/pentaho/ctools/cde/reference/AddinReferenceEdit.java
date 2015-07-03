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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.PageUrl;
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
  // Font size as changed
  private Boolean bFontChanged = false;
  // Instance of the driver (browser emulator)
  private final WebDriver driver = CToolsTestSuite.getDriver();
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  //Log instance
  private final Logger log = LogManager.getLogger( AddinReferenceEdit.class );

  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( this.driver );

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
  @Test
  public void tc01_ChangeAddinReferenceSample_FontSizeWasChanged() {
    this.log.info( "tc01_ChangeAddinReferenceSample_FontSizeWasChanged" );

    /*
     * ## Step 1
     */
    //Go to AddinReference
    this.driver.get( PageUrl.ADDIN_REFERENCE );
    //NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( this.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    // Wait for title
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='Title']/span" ) );
    String fontSize18 = this.elemHelper.GetAttribute( this.driver, By.xpath( "//div[@id='Title']/span" ), "style" );
    assertEquals( "font-size: 18px;", fontSize18 );

    /*
     * ## Step 2
     */
    this.ChangeFontSize( "34" );

    /*
     * ## Step 3
     */
    //Go to AddinReference
    this.driver.get( PageUrl.ADDIN_REFERENCE );
    //NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( this.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    String fontSize34 = this.elemHelper.GetAttribute( this.driver, By.xpath( "//div[@id='Title']/span" ), "style" );
    assertEquals( "font-size: 34px;", fontSize34 );
  }

  /**
   *
   * @param value
   */
  private void ChangeFontSize( String value ) {
    this.log.info( "ChangeFontSize" );
    this.driver.get( PageUrl.ADDIN_REFERENCE_EDIT );

    //Expand first row - Title
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.cssSelector( "span.expander" ) );
    this.elemHelper.ClickJS( this.driver, By.cssSelector( "span.expander" ) );
    //Click in HTML to open the Properties
    Actions acts = new Actions( this.driver );
    acts.click( this.elemHelper.FindElement( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]/td[1]" ) ) );
    acts.build().perform();
    //Click in field 'Font Size' to be editable
    this.elemHelper.ClickJS( this.driver, By.xpath( "//table[@id='table-cdfdd-layout-properties']/tbody/tr[3]/td[2]" ) );
    //Write 34
    this.elemHelper.FindElement( this.driver, By.name( "value" ) ).clear();
    this.elemHelper.ClickAndSendKeys( this.driver, By.xpath( "//form[@class='cdfddInput']/input" ), value );
    this.elemHelper.FindElement( this.driver, By.xpath( "//form[@class='cdfddInput']/input" ) ).submit();
    this.bFontChanged = true;
    //Save the changes
    this.elemHelper.ClickJS( this.driver, By.linkText( "Save" ) );
    //Wait for element present and invisible
    this.elemHelper.WaitForElementVisibility( this.driver, By.xpath( "//div[@id='notifyBar']" ) );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@id='notifyBar']" ) );
  }

  @After
  public void tearDown() {
    this.log.info( "tearDown##" + AddinReferenceEdit.class.getSimpleName() );
    if ( this.bFontChanged ) {
      ChangeFontSize( "18" );
    }
  }
}
