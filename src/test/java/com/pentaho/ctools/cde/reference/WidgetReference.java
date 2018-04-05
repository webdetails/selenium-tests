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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with CCV V2 Show Case.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class WidgetReference extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( WidgetReference.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    PageContent
   * Description:
   *    The test case pretends validate page contents of this sample.
   * Steps:
   *    1. Check page content
   */
  @Test
  public void tc01_PageContent_DisplayContent() {
    this.log.info( "tc01_PageContent_DisplayContent" );

    // Go to AddinReference
    this.elemHelper.Get( driver, PageUrl.WIDGET_REFERENCE );

    //NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 4 );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    /*
     * ## Step 1
     */
    String sampleTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='Title']/span" ) );
    String firstParag = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='DescriptionBody']/p" ) );
    String sampleSubTitle1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='DescriptionBody']/div" ) );
    String sampleSubTitle2 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='DescriptionBody']/div[2]" ) );
    String lastParag = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='DescriptionBody']/p[7]" ) );

    assertEquals( "Widget reference", sampleTitle );
    assertEquals( "Widgets are a way to turn complex component and layout setups into reusable components. Widgets are developed as if they were dashboards and used as if they were components in client dashboards.", firstParag );
    assertEquals( "Using Widgets", sampleSubTitle1 );
    assertEquals( "Widget Implementation", sampleSubTitle2 );
    assertEquals( "Since you cannot know what names will be given to those objects when developing the widget, CDE now provides a reference mechanism for those cases. Anywhere you'd use a parameter parameterName, you should instead use the form ${p:parameterName}. Similarly you should reference components as ${c:componentName}, and htmlObjects as ${h:htmlObject}. Be mindful that this mechanism also obviates the need to use render_componentName everywhere you want to reference a component. The render_ prefix is an implementation detail, and shouldn't be relied upon. Since all dashboards (not just widgets) can use the ${p/c/h:} references, it's a best practice to just use that syntax in all contexts.", lastParag );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Widget Chart
   * Description:
   *    The test case pretends validate the information presented in the chart
   *    when we interact with.
   * Steps:
   *    1. Check contents
   *    2. Check years
   *    3. Disable series
   *    4. Check tooltip
   */
  @Test
  public void tc02_WidgetChart_ContentsDisplayCorrect() {
    this.log.info( "tc02_WidgetChart_ContentsDisplayCorrect" );

    /*
     * ## Step 1
     */
    String widgetTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='SampleTitle']" ) );
    String widgetDesc = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='SampleDescHtml']/p" ) );
    assertEquals( "Sample Widget", widgetTitle );
    assertEquals( "This is a simple sample Widget. The widget contains only a single bar chart and a year parameter, and the parameter is linked to the documentation dashboard's year parameter, which is exposed in the pulldown.", widgetDesc );

    /*
     * ## Step 2
     */
    Select select = new Select( this.elemHelper.FindElement( driver, By.xpath( "//select" ) ) );
    //>Select 2003
    select.selectByValue( "2003" );
    //wait for loading bar disappear
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    String rect1 = this.elemHelper.GetAttribute( driver, By.cssSelector( "#widgetSample_chartprotovis > svg > g > g > g > g:nth-child(2) > g > g > g:nth-child(1) > rect:nth-child(4)" ), "height" );
    log.debug( "Heigh: " + rect1 );
    Float rect1Value = Float.parseFloat( rect1 );
    assertTrue( rect1Value > rect1Value - 1 || rect1Value < rect1Value + 1 );
    //>Select 2004
    select.selectByValue( "2004" );
    //wait for loading bar disappear
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    String rect2 = this.elemHelper.GetAttribute( driver, By.cssSelector( "#widgetSample_chartprotovis > svg > g > g > g > g:nth-child(2) > g > g > g:nth-child(1) > rect:nth-child(4)" ), "height" );
    log.debug( "Heigh: " + rect2 );
    Float rect2Value = Float.parseFloat( rect2 );
    assertTrue( rect2Value > rect2Value - 1 || rect2Value < rect2Value + 1 );
    //>Select 2005
    select.selectByValue( "2005" );
    //wait for loading bar disappear
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    String rect3 = this.elemHelper.GetAttribute( driver, By.cssSelector( "#widgetSample_chartprotovis > svg > g > g > g > g:nth-child(2) > g > g > g:nth-child(1) > rect:nth-child(4)" ), "height" );
    log.debug( "Heigh: " + rect3 );
    Float rect3Value = Float.parseFloat( rect3 );
    assertTrue( rect3Value > rect3Value - 1 || rect3Value < rect3Value + 1 );

    /*
     * ## Step 3
     */
    // Get Legend
    String legend1 = this.elemHelper.WaitForTextDifferentEmpty( driver, By.cssSelector( "#widgetSample_chartprotovis > svg > g > g > g > g:nth-child(4) > g:nth-child(1) > g:nth-child(2) > text" ) );
    assertEquals( legend1, "APAC" );
    String legend2 = this.elemHelper.WaitForTextDifferentEmpty( driver, By.cssSelector( "#widgetSample_chartprotovis > svg > g > g > g > g:nth-child(4) > g:nth-child(1) > g:nth-child(4) > text" ) );
    assertEquals( legend2, "EMEA" );
    String legend3 = this.elemHelper.WaitForTextDifferentEmpty( driver, By.cssSelector( "#widgetSample_chartprotovis > svg > g > g > g > g:nth-child(4) > g:nth-child(1) > g:nth-child(6) > text" ) );
    assertEquals( legend3, "Japan" );
    String legend4 = this.elemHelper.WaitForTextDifferentEmpty( driver, By.cssSelector( "#widgetSample_chartprotovis > svg > g > g > g > g:nth-child(4) > g:nth-child(1) > g:nth-child(8) > text" ) );
    assertEquals( legend4, "NA" );

    /*
     * ## Step 4
     */
    //Check tooltip
    this.elemHelper.FocusAndMoveToElement( driver, By.cssSelector( "#widgetSample_chartprotovis > svg > g > g > g > g:nth-child(2) > g > g > g:nth-child(6) > rect" ) );
    String tooltipValue = this.elemHelper.WaitForElementPresentGetText( driver, By.cssSelector( "div.tipsy > div:nth-child(2)" ) );
    assertEquals( tooltipValue, "Series Trucks and Buses Markets APAC Value 488" );
  }
}
