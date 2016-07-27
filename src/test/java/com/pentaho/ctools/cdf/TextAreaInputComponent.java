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
package com.pentaho.ctools.cdf;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with Text Area Input Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class TextAreaInputComponent extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  //Log instance
  private final Logger log = LogManager.getLogger( TextAreaInputComponent.class );

  /**
   * Go to the TextAreaInputComponent web page.
   */
  @BeforeClass
  public void setUpTestCase() {
    //Go to TextAreaInputComponent
    driver.get( baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A38-TextAreaInputComponent%3Atext_area_input_component.xcdf/generatedContent" );

    //NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Page Content
   * Description:
   *    This test pretends to validate the contents present in the page.
   * Steps:
   *    1. Check sample title.
   *    2. Check sample description.
   */
  @Test
  public void tc1_PageContent_ContentPresent() {
    this.log.info( "tc1_PageContent_ContentPresent" );

    /*
     * ## Step 1
     */
    // Page title
    assertEquals( "Community Dashboard Framework", driver.getTitle() );
    //Sample Title
    String sampleTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//span[2]" ) );
    assertEquals( "TextAreaInputComponent", sampleTitle );

    /*
     * ## Step 2
     */
    //Sample Description
    String sampleDescTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//h3" ) );
    String sampleDescription = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//p" ) );
    assertEquals( "Description", sampleDescTitle );
    assertEquals( "Renders a multi-line text input box to collect user input. Change event is fired after user edits the content and removes the focus from the box. Pre/postChange functions can be used to make data validation.", sampleDescription );

    /*
     * ## Step 3
     */
    //Options
    String optionsTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//h3[2]" ) );
    String options1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//dt[7]" ) );
    String options2 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//dt[8]" ) );
    assertEquals( "Options", optionsTitle );
    assertEquals( "charWidth", options1 );
    assertEquals( "maxChars", options2 );

    /*
     * ## Step 4
     */
    //Samples
    String samplesTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//h3[3]" ) );
    assertEquals( "Sample", samplesTitle );
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
    // ## Step 1
    // Render again the sample
    this.elemHelper.FindElement( driver, By.xpath( "//div[@id='example']/ul/li[2]/a" ) ).click();
    this.elemHelper.FindElement( driver, By.xpath( "//div[@id='code']/button" ) ).click();

    // NOTE - we have to wait for loading disappear
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
   *    Insert a small text
   * Description:
   *    We pretend validate when we insert a small text an alert is raised.
   * Steps:
   *    1. Insert text
   *    2. Check for alert
   *    3. Check the input text inserted
   */
  @Test
  public void tc3_InputSmallPhrase_AlertDispayed() {
    // ## Step 1
    String strInputString = "Hello World!";

    this.elemHelper.SendKeys( driver, By.id( "myInput" ), strInputString );
    this.elemHelper.Click( driver, By.xpath( "//h3[3]" ) );

    // ## Step 2
    String confirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    String expectedCnfText = "you typed: " + strInputString;

    assertEquals( expectedCnfText, confirmationMsg );
  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name:
   *    Insert a long text
   * Description:
   *    We pretend validate when we insert a long text an alert is raised.
   * Steps:
   *    1. Insert text
   *    2. Check for alert
   *    3. Check the input text inserted
   */
  @Test
  public void tc4_InputLongPhrase_AlertDispayed() {
    // ## Step 1
    String strInputString = "Hello World! Hello World! Hello World! Hello World! Hello World! Hello World!";
    strInputString += strInputString;
    strInputString += strInputString;
    strInputString += strInputString;
    strInputString += strInputString;

    this.elemHelper.Clear( driver, By.id( "myInput" ) );
    this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    this.elemHelper.SendKeys( driver, By.id( "myInput" ), strInputString );
    this.elemHelper.Click( driver, By.xpath( "//h3[3]" ) );

    // ## Step 2
    String confirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    String expectedCnfText = "you typed: " + strInputString;

    assertEquals( expectedCnfText, confirmationMsg );
  }

  /**
   * ############################### Test Case 5 ###############################
   *
   * Test Case Name:
   *    Insert special characters
   * Description:
   *    We pretend validate when we insert a special characters an alert is
   *    raised.
   * Steps:
   *    1. Insert text
   *    2. Check for alert
   *    3. Check the input text inserted
   */
  @Test
  public void tc5_InputSpecialPhrase_AlertDispayed() {
    // ## Step 1
    String strInputString = "`|!\"1#$%&/()=?*»ª:_Ç<>/*-+";

    this.elemHelper.Clear( driver, By.id( "myInput" ) );
    this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    this.elemHelper.SendKeys( driver, By.id( "myInput" ), strInputString );
    this.elemHelper.Click( driver, By.xpath( "//h3[3]" ) );

    // ## Step 2
    String confirmationMsg = this.elemHelper.WaitForAlertReturnConfirmationMsg( driver );
    String expectedCnfText = "you typed: " + strInputString;

    assertEquals( expectedCnfText, confirmationMsg );
  }
}
