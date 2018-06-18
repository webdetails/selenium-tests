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
package com.pentaho.ctools.cdf.samples.require.documentation;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with AutocompleteBox.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class AutoCompleteBoxComponent extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( AutoCompleteBoxComponent.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    AutocompleteBox
   * Description:
   *    We pretend to test the component AutocompleteBox, so when we enter any
   *    key, the text box should show a list of related data (that contains the
   *    same string).
   * Steps:
   *    1. Open the AutocompleteBoxComponent.
   *    2. Execute the "Try me".
   *    3. Press a on text box and check the autocomplete.
   */
  @Test
  public void tc1_AutocompleteBox_DataAreListed() {
    this.log.info( "tc1_AutocompleteBox_DataAreListed" );
    /*
     * ## Step 1
     */
    this.elemHelper.Get( driver, PageUrl.AUTOCOMPLETE_BOX_COMPONENT_REQUIRE );

    //NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    //Wait for title become visible and with value 'Community Dashboard Framework'
    String pageTitle = this.elemHelper.WaitForTitle( driver, "Community Dashboard Framework" );
    // Validate the sample that we are testing is the one
    String expectedSampleTitle = "AutocompleteBoxComponent";
    String actualSampleTitle = this.elemHelper.WaitForTextPresence( driver, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ), expectedSampleTitle );

    assertEquals( actualSampleTitle, expectedSampleTitle );
    assertEquals( pageTitle, "Community Dashboard Framework" );

    /*
     * ## Step 2
     */
    //Render again the sample
    this.elemHelper.Click( driver, By.xpath( "//div[@id='example']/ul/li[2]/a" ) );
    this.elemHelper.Click( driver, By.xpath( "//div[@id='code']/button" ) );
    //NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    //Now sample element must be displayed
    assertTrue( this.elemHelper.FindElement( driver, By.id( "sample" ) ).isDisplayed() );

    /*
     * ## Step 3
     */
    //Key press 'a'
    this.elemHelper.SendKeys( driver, By.cssSelector( "input.autocomplete-input.ui-autocomplete-input" ), "a" );

    //Retrieve data by pressing key 'a'
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "ul.ui-autocomplete.ui-front.ui-menu.ui-widget.ui-widget-content.ui-corner-all" ), 45 );
    assertNotNull( this.elemHelper.FindElement( driver, By.cssSelector( "ul.ui-autocomplete.ui-front.ui-menu.ui-widget.ui-widget-content.ui-corner-all" ) ) );

    //Check the values presented
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='autocomplete-container']/ul/li[1]/a" ) );
    String value1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@class='autocomplete-container']/ul/li[1]/a" ) );
    String value2 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@class='autocomplete-container']/ul/li[2]/a" ) );
    String value3 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@class='autocomplete-container']/ul/li[3]/a" ) );
    String value4 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@class='autocomplete-container']/ul/li[4]/a" ) );
    String value5 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@class='autocomplete-container']/ul/li[5]/a" ) );
    String value6 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@class='autocomplete-container']/ul/li[6]/a" ) );
    String value7 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@class='autocomplete-container']/ul/li[7]/a" ) );
    assertEquals( "Australian Collectors, Co.", value1 );
    assertEquals( "AV Stores, Co.", value2 );
    assertEquals( "Anna's Decorations, Ltd", value3 );
    assertEquals( "Amica Models & Co.", value4 );
    assertEquals( "Auto Canal+ Petit", value5 );
    assertEquals( "Alpha Cognac", value6 );
    assertEquals( "Auto Associés & Cie.", value7 );
  }
}
