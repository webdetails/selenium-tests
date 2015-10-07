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
import static org.testng.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
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
   *    1. Opne sample and assert selector button is shown
   *    2. Click button and assert options are shown
   *    3. Make options and click ok, assert options were correctly selected
   *    
   */
  @Test
  public void tc01_OlapSelectorComponent_SniffTestSample() {
    this.log.info( "tc01_OlapSelectorComponent_SniffTestSample" );

    /*
     * ## Step 1
     */
    // Go to Olap Selector Sample
    driver.get( baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Atests%3AOlapSelector%3AolapSelector.wcdf/generatedContent" );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    // Check page title
    String pageTitle = this.elemHelper.WaitForTitle( driver, "olapSelector" );
    assertEquals( "olapSelector", pageTitle );

    // Wait for Selector button
    WebElement selectorButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='column1']/div/div/div[@class='title']" ) );
    assertNotNull( selectorButton );

    /*
     * ## Step 2
     */
    //click selector button
    selectorButton.click();

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
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='options']//span[@title='Alpha Cognac']" ) ).click();
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='options']//span[@title='Asian Shopping Network, Co']" ) ).click();
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='options']//span[@title='Diecast Collectables']" ) ).click();
    WebElement applyButton = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='footer']/div[@class='button validate']" ) );
    assertNotNull( applyButton );
    applyButton.click();
    this.elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@id='column1']/div/div/div[@class='optionList']" ) );
    String selectedCustomers = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "columnLabel" ) );
    String expectedCustomers = "[\"[Customers].[" + firstName + "]\"\"[Customers].[" + secondName + "]\"\"[Customers].[" + thirdName + "]\"]";
    assertEquals( expectedCustomers, selectedCustomers );
  }
}
