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

package com.pentaho.ctools.issues.cda;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDA-55
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-964
 *
 * NOTE
 * To test this script it is required to have CDA plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDA55 extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDA55.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert running SQL query on KEttle with multiple parameters works
   * Description:
   *    The test pretends validate the CDA-55 issue, the when running a query with multiple parameters on Kettle
   *    with SQL Table Input works.
   *
   * Steps:
   *    1. Select "Sample query on SteelWheelsSales" on "dataAccessSelector"
   *    2. Wait for and assert elements and text on page
   *
   */
  @Test
  public void tc01_CdaFileViewer_KettleMultipleParam() {
    this.log.info( "tc01_CdaFileViewer_KettleMultipleParam" );

    /*
     * ## Step 1
     */
    // Go to User Console
    this.elemHelper.Get( driver, PageUrl.ISSUES_CDA_55 );

    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "dataAccessSelector" ) );
    assertNotNull( element );
    Select select = new Select( this.elemHelper.FindElement( driver, By.id( "dataAccessSelector" ) ) );
    select.selectByVisibleText( "Sample query on SteelWheelsSales" );
    this.elemHelper.FindElement( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='cachethis']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='queryUrl']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//button[@id='button']" ) );
    assertNotNull( element );

    /*
     * ## Step 2
     */
    //Check the presented contains
    assertEquals( this.elemHelper.GetAttribute( driver, By.id( "countries" ), "value" ), "France;USA" );
    assertEquals( this.elemHelper.GetAttribute( driver, By.id( "Costumers" ), "value" ), "103;112" );
    //Check we have two elements and no more than that
    String textPaging = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "contents_info" ) );
    assertEquals( "View 1 to 2 of 2 elements", textPaging );
    //Check text on table
    String columnOneRowOne = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='contents']/tbody/tr/td" ) );
    String columnTwoRowOne = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='contents']/tbody/tr/td[2]" ) );
    assertEquals( "103", columnOneRowOne );
    assertEquals( "Atelier graphique", columnTwoRowOne );
    String columnOneRowTwo = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='contents']/tbody/tr[2]/td" ) );
    String columnTwoRowTwo = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='contents']/tbody/tr[2]/td[2]" ) );
    assertEquals( "112", columnOneRowTwo );
    assertEquals( "Signal Gift Stores", columnTwoRowTwo );

  }

}
