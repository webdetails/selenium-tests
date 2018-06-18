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
package com.pentaho.ctools.cde.samples.legacy.reference;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with Addin Reference edit mode.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class AddinReferenceEdit extends BaseTest {
  // Font size as changed
  private Boolean bFontChanged = false;
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( AddinReferenceEdit.class );

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
    // Go to AddinReference
    this.elemHelper.Get( driver, PageUrl.ADDIN_REFERENCE );
    //NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    // Wait for title
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='Title']/span" ), 2 );
    String fontSize18Expected = "font-size: 18px;";
    String fontSize18Actual = this.elemHelper.GetAttribute( driver, By.xpath( "//div[@id='Title']/span" ), "style", 1 );
    assertTrue( fontSize18Actual.equals( "" ) || fontSize18Actual.equals( fontSize18Expected ) );

    /*
     * ## Step 2
     */
    this.ChangeFontSize( "34" );

    /*
     * ## Step 3
     */
    // Go to AddinReference
    this.elemHelper.Get( driver, PageUrl.ADDIN_REFERENCE );
    //NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    String fontSize34 = this.elemHelper.GetAttribute( driver, By.xpath( "//div[@id='Title']/span" ), "style" );
    assertEquals( "font-size: 34px;", fontSize34 );
  }

  /**
   *
   * @param value
   */
  private void ChangeFontSize( String value ) {
    this.log.info( "ChangeFontSize" );
    this.elemHelper.Get( driver, PageUrl.ADDIN_REFERENCE_EDIT );

    //Expand first row - Title
    this.elemHelper.ClickJS( driver, By.cssSelector( ".table-cdfdd-layout-tree > tbody > tr:nth-child(5) > td > span" ) );
    //Click in HTML to open the Properties
    this.elemHelper.Click( driver, By.cssSelector( ".table-cdfdd-layout-tree > tbody > tr:nth-child(6) > td" ) );
    //Click in field 'Font Size' to be editable
    this.elemHelper.ClickJS( driver, By.cssSelector( ".table-cdfdd-layout-properties > tbody > tr:nth-child(3) > td:nth-child(2)" ) );
    //Write 34
    this.elemHelper.Clear( driver, By.name( "value" ) );
    this.elemHelper.ClickJS( driver, By.cssSelector( ".table-cdfdd-layout-properties > tbody > tr:nth-child(3) > td:nth-child(2)" ) );
    this.elemHelper.SendKeysAndSubmit( driver, By.name( "value" ), value );
    this.bFontChanged = true;
    //Save the changes
    this.elemHelper.ClickJS( driver, By.linkText( "Save" ) );
    //Wait for element present and invisible
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='notifyBar']" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='notifyBar']" ) );
  }

  @Override
  @AfterClass( alwaysRun = true )
  public void tearDownClass() {
    this.log.info( "tearDownClass##" + AddinReferenceEdit.class.getSimpleName() );
    if ( this.bFontChanged ) {
      ChangeFontSize( "18" );
    }
  }
}
