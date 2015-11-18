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
package com.pentaho.ctools.cde.require;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
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
  //Log instance
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
    //Go to AddinReference
    driver.get( PageUrl.ADDIN_REFERENCE_REQUIRE );
    //NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 5 );
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
    //Go to AddinReference
    driver.get( PageUrl.ADDIN_REFERENCE_REQUIRE );
    //NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 5 );
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
    driver.get( PageUrl.ADDIN_REFERENCE_REQUIRE_EDIT );

    //Expand first row - Title
    this.elemHelper.ClickJS( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[5]/td/span" ) );
    //Click in HTML to open the Properties
    Actions acts = new Actions( driver );
    acts.click( this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-layout-tree']/tbody/tr[6]/td[1]" ) ) );
    acts.build().perform();
    //Click in field 'Font Size' to be editable
    this.elemHelper.ClickJS( driver, By.xpath( "//table[@id='table-cdfdd-layout-properties']/tbody/tr[3]/td[2]" ) );
    //Write 34
    this.elemHelper.FindElement( driver, By.name( "value" ) ).clear();
    this.elemHelper.SendKeys( driver, By.name( "value" ), value );
    this.elemHelper.FindElement( driver, By.name( "value" ) ).submit();
    this.bFontChanged = true;
    //Save the changes
    this.elemHelper.ClickJS( driver, By.linkText( "Save" ) );
    //Wait for element present and invisible
    this.elemHelper.WaitForElementVisibility( driver, By.xpath( "//div[@id='notifyBar']" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='notifyBar']" ) );
  }

  @AfterClass( alwaysRun = true )
  public void tearDownClass() {
    this.log.info( "tearDownClass##" + AddinReferenceEdit.class.getSimpleName() );
    if ( this.bFontChanged ) {
      ChangeFontSize( "18" );
    }
  }
}
