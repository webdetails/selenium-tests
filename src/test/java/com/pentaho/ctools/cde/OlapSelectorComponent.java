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
package com.pentaho.ctools.cde;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with Olap Selector Component (Sniff Test).
 *
 * Naming convention for test: 'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class OlapSelectorComponent extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( OlapSelectorComponent.class );

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
   *    1. Open sample and assert selector button is shown
   *    2. Click button and assert options are shown
   *    3. Make options and click ok, assert options were correctly selected
   *
   */
  @Test
  public void tc1_OlapSelectorComponent_SniffTestSample() {
    this.log.info( "tc1_OlapSelectorComponent_SniffTestSample" );

    /*
     * ## Step 1
     */
    // Go to Olap Selector Sample
    this.elemHelper.Get( driver, PageUrl.OLAP_SELECTOR_COMPONENT );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    // Check page title
    String expectedTitle = "olapSelector";
    String actualTitle = this.elemHelper.WaitForTitle( driver, expectedTitle );
    assertEquals( actualTitle, expectedTitle );

    // Wait for Selector button
    WebElement selectorButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='column1']/div/div/div[@class='title']" ) );
    assertNotNull( selectorButton );

    /*
     * ## Step 2
     */
    //click selector button
    this.elemHelper.Click( driver, By.xpath( "//div[@id='column1']/div/div/div[@class='title']" ) );

    //Assert Options are shown
    WebElement optionList = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='column1']/div/div/div[@class='optionList']" ) );
    assertNotNull( optionList );
    WebElement firstSelection = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='options']//span[@title='Alpha Cognac']" ) );
    assertNotNull( firstSelection );
    String firstName = firstSelection.getText();
    WebElement secondSelection = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='options']//span[@title='Asian Shopping Network, Co']" ) );
    assertNotNull( secondSelection );
    String secondName = secondSelection.getText();
    WebElement thirdSelection = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='options']//span[@title='Diecast Collectables']" ) );
    assertNotNull( thirdSelection );
    String thirdName = thirdSelection.getText();

    /*
     * ## Step 3
     */
    this.elemHelper.Click( driver, By.xpath( "//div[@class='options']//span[@title='Alpha Cognac']" ) );
    this.elemHelper.Click( driver, By.xpath( "//div[@class='options']//span[@title='Asian Shopping Network, Co']" ) );
    this.elemHelper.Click( driver, By.xpath( "//div[@class='options']//span[@title='Diecast Collectables']" ) );
    WebElement applyButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='footer']/div[@class='button validate']" ) );
    assertNotNull( applyButton );
    applyButton.click();
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@id='column1']/div/div/div[@class='optionList']" ) );
    String selectedCustomers = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "columnLabel" ) );
    String expectedCustomers = "[\"[Customers].[" + firstName + "]\" \"[Customers].[" + secondName + "]\" \"[Customers].[" + thirdName + "]\"]";
    assertEquals( selectedCustomers, expectedCustomers );
  }
}
