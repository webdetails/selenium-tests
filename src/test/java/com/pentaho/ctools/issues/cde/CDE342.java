/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2002 - 2026 by Pentaho Canada Inc. : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2030-06-15
 ******************************************************************************/

package com.pentaho.ctools.issues.cde;

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
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-342
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-942
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDE342 extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDE342.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert autocomplete works properly
   *
   * Description:
   *    The test pretends validate the CDE-342 issue, so when user writes on the
   *    autocomplete input field, the options available update accordingly.
   *
   * Steps:
   *    1. Write on input field and check options available
   *    2. Write something where no options should be available
   *    3. Write something where only one option should be available
   */
  @Test
  public void tc1_CDEDashboard_AutocompleteWorks() {
    this.log.info( "tc1_CDEDashboard_AutocompleteWorks" );

    /*
     * ## Step 1
     */
    // Go to Issue sample
    this.elemHelper.Get( driver, PageUrl.ISSUES_CDE_342 );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    //Wait for Input field
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "col1" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//input[@class='ui-autocomplete-input']" ) );
    assertNotNull( element );
    this.elemHelper.FindElement( driver, By.xpath( "//div[@id='col1']/input" ) ).sendKeys( "A" );
    String option1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//ul[@id='ui-id-1']/li/a" ) );
    String option2 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//ul[@id='ui-id-1']/li[2]/a" ) );
    String option3 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//ul[@id='ui-id-1']/li[3]/a" ) );
    String option4 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//ul[@id='ui-id-1']/li[4]/a" ) );
    String option5 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//ul[@id='ui-id-1']/li[5]/a" ) );
    assertEquals( "Anna's Decorations, Ltd", option1 );
    assertEquals( "Australian Collectors, Co.", option2 );
    assertEquals( "Souveniers And Things Co.", option3 );
    assertEquals( "Australian Gift Network, Co", option4 );
    assertEquals( "Australian Collectables, Ltd", option5 );

    /*
     * ## Step 2
     */
    this.elemHelper.FindElement( driver, By.xpath( "//div[@id='col1']/input" ) ).clear();
    this.elemHelper.FindElement( driver, By.xpath( "//div[@id='col1']/input" ) ).sendKeys( "ert" );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//ul[@id='ui-id-1']/li/a" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//ul[@id='ui-id-1']/li/a" ) );

    /*
     * ## Step 3
     */
    this.elemHelper.FindElement( driver, By.xpath( "//div[@id='col1']/input" ) ).clear();
    this.elemHelper.FindElement( driver, By.xpath( "//div[@id='col1']/input" ) ).sendKeys( "Anna" );
    option2 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//ul[@id='ui-id-1']/li/a" ) );
    assertEquals( "Anna's Decorations, Ltd", option2 );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//ul[@id='ui-id-1']/li[2]/a" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//ul[@id='ui-id-1']/li[2]/a" ) );

  }
}
