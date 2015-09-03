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
package com.pentaho.ctools.issues.cde;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.BaseTest;
import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-410
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-994
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDEDataPanel extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDEDataPanel.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    This test is meant to validate all issues related to CDE's Data Panel
   *
   * Description:
   *    CDE-410: Shortcuts work
   *
   * Steps:
   *    1. Open CDE sample in edit mode, go to data panel and add some datasources
   *    2. Assert down, up, left and right shortcuts work on components panel
   *    3. Assert tab and enter shortcuts work on data panel
   *        
   **/
  @Test
  public void tc01_CDEDashboardEdit_DataPanel() {
    this.log.info( "tc01_CDEDashboardEdit_DataPanel" );

    /*
     * ## Step 1
     */
    //Open CDE sample in edit mode
    driver.get( PageUrl.CDE_DASHBOARD );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Go to Components Panel
    this.elemHelper.Click( driver, By.xpath( "//div[@title='Datasources Panel']/a" ) );

    //Add some Datasources
    WebElement mdxExpander = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='cdfdd-datasources-palletePallete']//a[contains(text(),'MDX Queries')]/../span" ) );
    assertNotNull( mdxExpander );
    mdxExpander.click();
    WebElement addDenormalMdx = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='cdfdd-datasources-palletePallete']//a[@title='denormalizedMdx over mondrianJdbc']" ) );
    assertNotNull( addDenormalMdx );
    addDenormalMdx.click();
    WebElement addMDX = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='cdfdd-datasources-palletePallete']//a[@title='mdx over mondrianJndi']" ) );
    assertNotNull( addMDX );
    addMDX.click();
    WebElement sqlExpander = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='cdfdd-datasources-palletePallete']//a[contains(text(),'SQL Queries')]/../span" ) );
    assertNotNull( sqlExpander );
    sqlExpander.click();
    WebElement addSqlJdbc = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='cdfdd-datasources-palletePallete']//a[@title='sql over sqlJdbc']" ) );
    assertNotNull( addSqlJdbc );
    addSqlJdbc.click();
    WebElement addSqlJndi = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='cdfdd-datasources-palletePallete']//a[@title='sql over sqlJndi']" ) );
    assertNotNull( addSqlJndi );
    addSqlJndi.click();

    /*
     * ## Step 2
     */
    //Select first chart group
    this.elemHelper.Click( driver, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr/td" ) );
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr" ), "class", "expanded initialized parent ui-state-active" );
    String mdxGroup = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr" ) ).getAttribute( "class" );
    assertEquals( mdxGroup, "expanded initialized parent ui-state-active" );

    //Assert groups are expanded and clicking left will collapse them, also assert down arrow moves between showing elements
    WebElement mdxDenormal = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[2]" ) );
    assertNotNull( mdxDenormal );
    WebElement sqlJdbc = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[5]" ) );
    assertNotNull( sqlJdbc );
    Actions a = new Actions( driver );
    a.sendKeys( Keys.LEFT ).sendKeys( Keys.DOWN ).sendKeys( Keys.LEFT ).build().perform();
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[4]" ), "class", "initialized parent ui-state-active collapsed" );
    String otherGroup = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[4]" ) ).getAttribute( "class" );
    assertEquals( otherGroup, "initialized parent ui-state-active collapsed" );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[2]" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[5]" ) );

    //Assert clicking right arrow expands groups    
    a.sendKeys( Keys.RIGHT ).sendKeys( Keys.UP ).sendKeys( Keys.RIGHT ).build().perform();
    mdxDenormal = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[2]" ) );
    assertNotNull( mdxDenormal );
    sqlJdbc = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[5]" ) );
    assertNotNull( sqlJdbc );

    //Go to to sqlJndi and assert it's selected
    a.sendKeys( Keys.DOWN ).sendKeys( Keys.DOWN ).sendKeys( Keys.DOWN ).sendKeys( Keys.DOWN ).sendKeys( Keys.DOWN ).build().perform();
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[6]" ), "class", "child-of-SQL initialized collapsed ui-state-active" );
    String areaChartClass = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-datasources-datasources']/tbody/tr[6]" ) ).getAttribute( "class" );
    assertEquals( areaChartClass, "child-of-SQL initialized collapsed ui-state-active" );

    /*
     * ## Step 3
     */
    //Click tab key and assert focus has changed to properties table
    a.sendKeys( Keys.TAB ).build().perform();
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr" ), "class", "initialized ui-state-active" );
    String nameProperty = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr" ) ).getAttribute( "class" );
    assertEquals( nameProperty, "initialized ui-state-active" );

    //Click enter to change following properties "Name"
    a.sendKeys( Keys.ENTER ).sendKeys( "a" ).sendKeys( Keys.ENTER ).sendKeys( Keys.DOWN ).sendKeys( Keys.DOWN ).sendKeys( Keys.DOWN ).sendKeys( Keys.DOWN ).sendKeys( Keys.DOWN ).build().perform();

    /*
     * ## Step 4
     */
    //Change "Parameter" and assert using down arrow to navigate is blocked
    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[6]" ), "class", "initialized ui-state-active" );
    String parameterProperty = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[6]" ) ).getAttribute( "class" );
    assertEquals( parameterProperty, "initialized ui-state-active" );
    a.sendKeys( Keys.ENTER ).build().perform();
    assertNotNull( this.elemHelper.FindElement( driver, By.id( "popup" ) ) );
    assertNotNull( this.elemHelper.FindElement( driver, By.id( "arg_0" ) ) );
    assertNotNull( this.elemHelper.FindElement( driver, By.id( "val_0" ) ) );
    a.sendKeys( Keys.TAB ).sendKeys( Keys.TAB ).sendKeys( Keys.DOWN ).sendKeys( Keys.DOWN ).sendKeys( Keys.UP ).build().perform();
    WebElement closePopup = this.elemHelper.FindElement( driver, By.id( "popup_state0_buttonCancel" ) );
    assertNotNull( closePopup );
    closePopup.click();
    assertTrue( this.elemHelper.WaitForElementNotPresent( driver, By.id( "popup" ) ) );

    this.elemHelper.WaitForAttributeValue( driver, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[6]" ), "class", "initialized ui-state-active" );
    parameterProperty = this.elemHelper.FindElement( driver, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[6]" ) ).getAttribute( "class" );
    assertEquals( parameterProperty, "initialized ui-state-active" );

    //assert values are changed
    String nameValue = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr/td[2]" ) );
    assertEquals( nameValue, "a" );

  }
}
