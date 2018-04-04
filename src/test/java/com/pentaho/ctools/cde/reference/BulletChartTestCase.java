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
package com.pentaho.ctools.cde.reference;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with Bullet Chart test case.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class BulletChartTestCase extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( BulletChartTestCase.class );

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Open Sample Page
   */
  @Test
  public void tc00_OpenSamplePage_Display() {
    this.log.info( "tc00_OpenSamplePage_Display" );

    // Go to AddinReference
    this.elemHelper.Get( BaseTest.driver, PageUrl.BULLET_CHART_TEST_CASE );

    //NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    PageContent
   * Description:
   *    The test case pretends validate charts presented.
   * Steps:
   *    1. Check chart 1 - No data sent
   *    2. Check chart 2 - Returning one value only (65)
   *    3. Check chart 3 - Returning name and value
   *    4. Check chart 4 - Title, value and marker
   *    5. Check chart 5 - Complete dataset
   */
  @Test
  public void tc01_ChartContent_DisplayedCorrect() {
    this.log.info( "tc01_ChartContent_DisplayedCorrect" );

    /*
     * ## Step 0
     */
    //Check page title
    final String expectedTitle = "Bullet chart testcase";
    final String actualTitle = this.elemHelper.WaitForTitle( BaseTest.driver, expectedTitle );
    Assert.assertEquals( actualTitle, expectedTitle );
    //Check title
    final String title = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.cssSelector( "#title > span" ) );
    Assert.assertEquals( title, "Bullet chart test case" );

    /*
     * ## Step 1
     */
    //Chart 1
    //Check title
    final String subtitle1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.cssSelector( "#subtitle1 > span" ) );
    Assert.assertEquals( "No data sent", subtitle1 );
    //Check series title and subtitle
    final String cht1Title1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='obj1protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][6]/*[name()='text']" ) );
    Assert.assertEquals( "Title", cht1Title1 );
    final String cht1Subtitle1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='obj1protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][7]/*[name()='text']" ) );
    Assert.assertEquals( "No data", cht1Subtitle1 );
    //Check chart
    final WebElement cht1SizeBar = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='obj1protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][2]/*[name()='rect']" ) );
    Assert.assertNotNull( cht1SizeBar );
    Assert.assertEquals( "202.11111111111111", cht1SizeBar.getAttribute( "width" ) );
    final WebElement cht1RectWhite1 = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='obj1protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][3]/*[name()='path'][1]" ) );
    final WebElement cht1RectWhite2 = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='obj1protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][3]/*[name()='path'][2]" ) );
    Assert.assertNotNull( cht1RectWhite1 );
    Assert.assertNotNull( cht1RectWhite2 );
    Assert.assertEquals( "translate(78.46666666666665,15) ", cht1RectWhite1.getAttribute( "transform" ) );
    Assert.assertEquals( "translate(156.9333333333333,15) ", cht1RectWhite2.getAttribute( "transform" ) );

    /*
     * ## Step 2
     */
    //Chart 2
    //Check title
    final String subtitle2 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.id( "subtitle2" ) );
    Assert.assertEquals( "Returning one value only (65)", subtitle2 );
    //Check serie title and subtitle
    final String cht2Title1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='obj2protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][6]/*[name()='text']" ) );
    Assert.assertEquals( "Value only", cht2Title1 );
    final String cht2Subtitle1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='obj2protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][7]/*[name()='text']" ) );
    Assert.assertEquals( "value is 65", cht2Subtitle1 );
    //Check chart
    final WebElement cht2SizeBar = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='obj2protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][2]/*[name()='rect']" ) );
    Assert.assertNotNull( cht2SizeBar );
    Assert.assertEquals( "154.55555555555554", cht2SizeBar.getAttribute( "width" ) );
    final WebElement cht2RectWhite1 = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='obj2protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][3]/*[name()='path'][1]" ) );
    Assert.assertNotNull( cht2RectWhite1 );
    Assert.assertEquals( "translate(71.33333333333333,12.5) ", cht2RectWhite1.getAttribute( "transform" ) );

    /*
     * ## Step 3
     */
    //>Chart 3
    //>>Chart 31
    //Check title
    final String subtitle3 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.id( "subtitle3" ) );
    Assert.assertEquals( "Returning name and value", subtitle3 );
    //Check series title and subtitle
    final String cht31Title1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='obj3protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][6]/*[name()='text']" ) );
    Assert.assertEquals( "Atelier graphique", cht31Title1 );
    final String cht31Subtitle1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='obj3protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][7]/*[name()='text']" ) );
    Assert.assertEquals( "Customer", cht31Subtitle1 );
    //Check chart
    final WebElement cht31SizeBar = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='obj3protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][2]/*[name()='rect']" ) );
    Assert.assertNotNull( cht31SizeBar );
    Assert.assertEquals( "178.26666666666668", cht31SizeBar.getAttribute( "width" ) );
    final WebElement cht31RectWhite1 = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='obj3protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][3]/*[name()='path'][1]" ) );
    Assert.assertNotNull( cht31RectWhite1 );
    Assert.assertEquals( "translate(509.3333333333333,10) ", cht31RectWhite1.getAttribute( "transform" ) );
    //>>Chart 32
    //Check series title and subtitle
    final String cht32Title1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='obj3protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][2]/*[name()='g'][6]/*[name()='text']" ) );
    Assert.assertEquals( "Signal Gift Stores", cht32Title1 );
    final String cht32Subtitle1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='obj3protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][2]/*[name()='g'][7]/*[name()='text']" ) );
    Assert.assertEquals( "Customer", cht32Subtitle1 );
    //Check chart
    final WebElement cht32SizeBar = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='obj3protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][2]/*[name()='g'][2]/*[name()='rect']" ) );
    Assert.assertNotNull( cht32SizeBar );
    Assert.assertEquals( "609.5022222222223", cht32SizeBar.getAttribute( "width" ) );
    final WebElement cht32RectWhite1 = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='obj3protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][2]/*[name()='g'][3]/*[name()='path'][1]" ) );
    Assert.assertNotNull( cht32RectWhite1 );
    Assert.assertEquals( "translate(509.3333333333333,10) ", cht32RectWhite1.getAttribute( "transform" ) );
    //>>Chart 33
    //Check series title and subtitle
    final String cht33Title1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='obj3protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][3]/*[name()='g'][6]/*[name()='text']" ) );
    Assert.assertEquals( "Australian Collectors, Co.", cht33Title1 );
    final String cht33Subtitle1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='obj3protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][3]/*[name()='g'][7]/*[name()='text']" ) );
    Assert.assertEquals( "Customer", cht33Subtitle1 );
    //Check chart
    final WebElement cht33SizeBar = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='obj3protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][3]/*[name()='g'][2]/*[name()='rect']" ) );
    Assert.assertNotNull( cht33SizeBar );
    Assert.assertEquals( "764", cht33SizeBar.getAttribute( "width" ) );
    final WebElement cht33RectWhite1 = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='obj3protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][3]/*[name()='g'][3]/*[name()='path'][1]" ) );
    Assert.assertNotNull( cht33RectWhite1 );
    Assert.assertEquals( "translate(390.79283887468034,10) ", cht33RectWhite1.getAttribute( "transform" ) );
    //>>Chart 34
    final String cht34Title1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='obj3protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][4]/*[name()='g'][6]/*[name()='text']" ) );
    Assert.assertEquals( "La Rochelle Gifts", cht34Title1 );
    final String cht34Subtitle1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='obj3protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][4]/*[name()='g'][7]/*[name()='text']" ) );
    Assert.assertEquals( "Customer", cht34Subtitle1 );
    //Check chart
    final WebElement cht34SizeBar = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='obj3protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][4]/*[name()='g'][2]/*[name()='rect']" ) );
    Assert.assertNotNull( cht34SizeBar );
    Assert.assertEquals( "764", cht34SizeBar.getAttribute( "width" ) );
    final WebElement cht34RectWhite1 = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='obj3protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][4]/*[name()='g'][3]/*[name()='path'][1]" ) );
    Assert.assertNotNull( cht34RectWhite1 );
    Assert.assertEquals( "translate(387.8172588832487,10) ", cht34RectWhite1.getAttribute( "transform" ) );
    //>>Chart 35
    final String cht35Title1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='obj3protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][5]/*[name()='g'][6]/*[name()='text']" ) );
    Assert.assertEquals( "Baane Mini Imports", cht35Title1 );
    final String cht35Subtitle1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='obj3protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][5]/*[name()='g'][7]/*[name()='text']" ) );
    Assert.assertEquals( "Customer", cht35Subtitle1 );
    //Check chart
    final WebElement cht35SizeBar = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='obj3protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][5]/*[name()='g'][2]/*[name()='rect']" ) );
    Assert.assertNotNull( cht35SizeBar );
    Assert.assertEquals( "693.5422222222222", cht35SizeBar.getAttribute( "width" ) );
    final WebElement cht35RectWhite1 = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='obj3protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][5]/*[name()='g'][3]/*[name()='path'][1]" ) );
    Assert.assertNotNull( cht35RectWhite1 );
    Assert.assertEquals( "translate(509.3333333333333,10) ", cht35RectWhite1.getAttribute( "transform" ) );
    //>>Chart 36
    final String cht36Title1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='obj3protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][6]/*[name()='g'][6]/*[name()='text']" ) );
    Assert.assertEquals( "Mini Gifts Distributors Ltd.", cht36Title1 );
    final String cht36Subtitle1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='obj3protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][6]/*[name()='g'][7]/*[name()='text']" ) );
    Assert.assertEquals( "Customer", cht36Subtitle1 );
    //Check chart
    final WebElement cht36SizeBar = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='obj3protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][6]/*[name()='g'][2]/*[name()='rect']" ) );
    Assert.assertNotNull( cht36SizeBar );
    Assert.assertEquals( "764", cht36SizeBar.getAttribute( "width" ) );
    final WebElement cht36RectWhite1 = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='obj3protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][6]/*[name()='g'][3]/*[name()='path'][1]" ) );
    Assert.assertNotNull( cht36RectWhite1 );
    Assert.assertEquals( "translate(217.76722090261282,10) ", cht36RectWhite1.getAttribute( "transform" ) );
    //>>Chart 37
    final String cht37Title1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='obj3protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][7]/*[name()='g'][6]/*[name()='text']" ) );
    Assert.assertEquals( "Havel & Zbyszek Co", cht37Title1 );
    final String cht37Subtitle1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='obj3protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][7]/*[name()='g'][7]/*[name()='text']" ) );
    Assert.assertEquals( "Customer", cht37Subtitle1 );
    //Check chart
    final WebElement cht37SizeBar = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='obj3protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][7]/*[name()='g'][2]/*[name()='rect']" ), 1 );
    Assert.assertNull( cht37SizeBar );
    final WebElement cht37RectWhite1 = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='obj3protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][7]/*[name()='g'][3]/*[name()='path'][1]" ) );
    Assert.assertNotNull( cht37RectWhite1 );
    Assert.assertEquals( "translate(509.3333333333333,10) ", cht37RectWhite1.getAttribute( "transform" ) );
    //>>Chart 38
    final String cht38Title1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='obj3protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][8]/*[name()='g'][6]/*[name()='text']" ) );
    Assert.assertEquals( "Blauer See Auto, Co.", cht38Title1 );
    final String cht38Subtitle1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='obj3protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][8]/*[name()='g'][7]/*[name()='text']" ) );
    Assert.assertEquals( "Customer", cht38Subtitle1 );
    //Check chart
    final WebElement cht38SizeBar = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='obj3protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][8]/*[name()='g'][2]/*[name()='rect']" ) );
    Assert.assertNotNull( cht38SizeBar );
    Assert.assertEquals( "506.7866666666667", cht38SizeBar.getAttribute( "width" ) );
    final WebElement cht38RectWhite1 = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='obj3protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][8]/*[name()='g'][3]/*[name()='path'][1]" ) );
    Assert.assertNotNull( cht38RectWhite1 );
    Assert.assertEquals( "translate(509.3333333333333,10) ", cht38RectWhite1.getAttribute( "transform" ) );

    /*
     * ## Step 4
     */
    this.elemHelper.FocusAndMoveToElement( driver, By.cssSelector( "div.webdetailsFooterCtools" ) );
    //Chart 4
    //Check title
    final String subtitle4 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.id( "subtitle4" ) );
    Assert.assertEquals( "Title, value and marker", subtitle4 );
    //Check serie title and subtitle
    final String cht4Title1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='obj4protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][6]/*[name()='text']" ) );
    Assert.assertEquals( "Atelier graphique", cht4Title1 );
    final String cht4Subtitle1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='obj4protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][7]/*[name()='text']" ) );
    Assert.assertEquals( "Subtitle", cht4Subtitle1 );
    //Check chart
    final WebElement cht4SizeBar = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='obj4protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][2]/*[name()='rect']" ) );
    Assert.assertNotNull( cht4SizeBar );
    final WebElement cht4RectWhite1 = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='obj4protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][3]/*[name()='path'][1]" ) );
    Assert.assertNotNull( cht4RectWhite1 );

    /*
     * ## Step 5
     */
    //Chart 5
    //Check title
    final String subtitle5 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.id( "subtitle5" ) );
    Assert.assertEquals( "Complete dataset", subtitle5 );
    //Check serie title and subtitle
    final String cht5Title1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='obj5protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][6]/*[name()='text']" ) );
    Assert.assertEquals( "Atelier graphique", cht5Title1 );
    final String cht5Subtitle1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='obj5protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][7]/*[name()='text']" ) );
    Assert.assertEquals( "Carine Schmitt", cht5Subtitle1 );
    //Check chart
    final WebElement cht5SizeBar = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='obj5protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][2]/*[name()='rect']" ) );
    Assert.assertNotNull( cht5SizeBar );
    final WebElement cht5RectWhite1 = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='obj5protovis']/*[name()='svg']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g']/*[name()='g'][3]/*[name()='path'][1]" ) );
    Assert.assertNotNull( cht5RectWhite1 );
  }
}
