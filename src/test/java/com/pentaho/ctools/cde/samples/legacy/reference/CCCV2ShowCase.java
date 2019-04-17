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
package com.pentaho.ctools.cde.samples.legacy.reference;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with CCV V2 Show Case.
 *
 * Naming convention for test: 'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CCCV2ShowCase extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CCCV2ShowCase.class );

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Open sample page.
   */
  @Test
  public void tc00_OpenSamplePage_Display() {
    this.log.info( "tc00_OpenSamplePage_Display" );

    // Go to AddinReference
    this.elemHelper.Get( BaseTest.driver, PageUrl.CCCV2_SHOWCASE );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    // Check page title
    final String expectedTitle = "CCC V2 ShowCase";
    final String actualTitle = this.elemHelper.WaitForTitle( BaseTest.driver, expectedTitle );
    Assert.assertEquals( actualTitle, expectedTitle );
    // Check title
    final String title = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='Headers']/div" ) );
    Assert.assertEquals( title, "CCC v2 - Show Case" );
    // Wait for the charts load
    // search for width of barchartrect1
    // search for width of barchartrect2
    // search for width of barchartrect3
    final WebElement barchartrect1 = this.elemHelper.WaitForElementPresenceAndVisible( BaseTest.driver, By.xpath( "//div[@id='BarChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='rect'][1][@width>192]" ), 10 );
    Assert.assertNotNull( barchartrect1 );
    final WebElement barchartrect2 = this.elemHelper.WaitForElementPresenceAndVisible( BaseTest.driver, By.xpath( "//div[@id='BarChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='rect'][2][@width>249]" ), 10 );
    Assert.assertNotNull( barchartrect2 );
    final WebElement barchartrect3 = this.elemHelper.WaitForElementPresenceAndVisible( BaseTest.driver, By.xpath( "//div[@id='BarChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='rect'][3][@width>100]" ), 10 );
    Assert.assertNotNull( barchartrect3 );
    // search for cy of line1
    // search for cy of line7
    // search for cy of line11
    final WebElement cyofline1 = this.elemHelper.WaitForElementPresenceAndVisible( BaseTest.driver, By.xpath( "//div[@id='LineChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='circle'][1][@cy>108]" ), 10 );
    Assert.assertNotNull( cyofline1 );
    final WebElement cyofline7 = this.elemHelper.WaitForElementPresenceAndVisible( BaseTest.driver, By.xpath( "//div[@id='LineChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='circle'][7][@cy>122]" ), 10 );
    Assert.assertNotNull( cyofline7 );
    final WebElement cyofline11 = this.elemHelper.WaitForElementPresenceAndVisible( BaseTest.driver, By.xpath( "//div[@id='LineChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='circle'][11][@cy>22]" ), 10 );
    Assert.assertNotNull( cyofline11 );
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    BarChart
   * Description:
   *    The test case pretends validate the bar chart information.
   * Steps:
   *    1. Check Bar Chart
   */
  @Test
  public void tc01_ChartContent_DisplayedCorrect() {
    this.log.info( "tc01_ChartContent_DisplayedCorrect" );

    /*
     * ## Step 1 - Bar Chart
     */
    final String barChartTitle = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.id( "BarChartTitleRow" ) );
    Assert.assertEquals( "Bar Chart", barChartTitle );
    // Check bars
    this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='BarChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='rect'][2]" ) );
    final WebElement barChartRect1 = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='BarChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='rect'][1]" ) );
    final WebElement barChartRect2 = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='BarChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='rect'][2]" ) );
    final WebElement barChartRect3 = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='BarChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='rect'][3]" ) );
    Assert.assertNotNull( barChartRect1 );
    Assert.assertNotNull( barChartRect2 );
    Assert.assertNotNull( barChartRect3 );
    final double barChartRect1Width = Double.parseDouble( barChartRect1.getAttribute( "width" ) );
    final double barChartRect2Width = Double.parseDouble( barChartRect2.getAttribute( "width" ) );
    final double barChartRect3Width = Double.parseDouble( barChartRect3.getAttribute( "width" ) );
    MatcherAssert.assertThat( "Current width: " + barChartRect1Width, Double.valueOf( barChartRect1Width ), Matchers.greaterThan( Double.valueOf( 192 ) ) );
    MatcherAssert.assertThat( "Current width: " + barChartRect2Width, Double.valueOf( barChartRect2Width ), Matchers.greaterThan( Double.valueOf( 250 ) ) );
    MatcherAssert.assertThat( "Current width: " + barChartRect3Width, Double.valueOf( barChartRect3Width ), Matchers.greaterThan( Double.valueOf( 100 ) ) );
    // Check bars value
    final String barChartRectValue1 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='BarChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text'][1]" ) );
    final String barChartRectValue2 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='BarChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text'][2]" ) );
    final String barChartRectValue3 = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@id='BarChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text'][3]" ) );
    Assert.assertEquals( "3.68M", barChartRectValue1 );
    Assert.assertEquals( "4.99M", barChartRectValue2 );
    Assert.assertEquals( "1.98M", barChartRectValue3 );

    // Mouse hover elements
    this.elemHelper.MoveToElement( BaseTest.driver, By.cssSelector( "#barChartprotovis > svg > g > g > g:nth-child(2) > g > g > g:nth-child(1) > rect:nth-child(2)" ) );
    final String seriesLabel = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[1]/td[1]/span" ) );
    Assert.assertEquals( seriesLabel, "Series" );
    final String seriesValue = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[1]/td[3]/span" ) );
    Assert.assertEquals( seriesValue, "Sales" );
    final String timeLabel = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[2]/td[1]/span" ) );
    Assert.assertEquals( timeLabel, "Time" );
    final String timeValue = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[2]/td[3]/span" ) );
    Assert.assertEquals( timeValue, "2004" );
    final String valueLabel = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[3]/td[1]/span" ) );
    Assert.assertEquals( valueLabel, "Value" );
    final String valueValue = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[3]/td[3]/span" ) );
    Assert.assertEquals( valueValue, "4,987,739.84" );

    // The below code need to be executed to remove the previous tooltip
    this.elemHelper.FocusAndMoveToElement( BaseTest.driver, By.id( "LineChartTitleRow" ) );
    final boolean bNotPresent = this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "div.tipsy.tipsy-s" ) );
    Assert.assertTrue( bNotPresent );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    LineChart
   * Description:
   *    The test case pretends validate the line chart information.
   * Steps:
   *    1. Check Line Chart
   */
  @Test
  public void tc02_ChartContent_DisplayedCorrect() {
    this.log.info( "tc02_ChartContent_DisplayedCorrect" );

    /*
     * ## Step 2 - Line Chart
     */
    final String lineChartTitle = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.id( "LineChartTitleRow" ) );
    Assert.assertEquals( "Line Chart", lineChartTitle );
    // Check lines
    final WebElement lineChartCircle1 = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='LineChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='circle'][1]" ) );
    final WebElement lineChartCircle2 = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='LineChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='circle'][7]" ) );
    final WebElement lineChartCircle3 = this.elemHelper.FindElement( BaseTest.driver, By.xpath( "//div[@id='LineChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='circle'][11]" ) );
    final double lineChartCircle1Cy = Double.parseDouble( lineChartCircle1.getAttribute( "cy" ) );
    final double lineChartCircle2Cy = Double.parseDouble( lineChartCircle2.getAttribute( "cy" ) );
    final double lineChartCircle3Cy = Double.parseDouble( lineChartCircle3.getAttribute( "cy" ) );
    MatcherAssert.assertThat( "Current cy: " + lineChartCircle1Cy, Double.valueOf( lineChartCircle1Cy ), Matchers.greaterThan( Double.valueOf( 108 ) ) );
    MatcherAssert.assertThat( "Current cy: " + lineChartCircle2Cy, Double.valueOf( lineChartCircle2Cy ), Matchers.greaterThan( Double.valueOf( 122 ) ) );
    MatcherAssert.assertThat( "Current cy: " + lineChartCircle3Cy, Double.valueOf( lineChartCircle3Cy ), Matchers.greaterThan( Double.valueOf( 22 ) ) );

    // Mouse hover elements
    this.elemHelper.FocusAndMoveToElement( BaseTest.driver, By.cssSelector( "#LineChartprotovis > svg > g > g > g:nth-child(3) > g > g > g:nth-child(3) > circle:nth-child(2)" ) );
    final String measuresLabel = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[1]/td[1]/span" ) );
    Assert.assertEquals( "Measures", measuresLabel );
    final String measuresValue = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[1]/td[3]/span" ) );
    Assert.assertEquals( "Quantity", measuresValue );
    final String categoryLabel = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[2]/td[1]/span" ) );
    Assert.assertEquals( "Category", categoryLabel );
    final String categoryValue = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[2]/td[3]/span" ) );
    Assert.assertEquals( "Feb", categoryValue );
    final String valueLineValue = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.xpath( "//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[4]/td[3]/span" ) );
    Assert.assertEquals( "7,959", valueLineValue );
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    LineChart
   * Description:
   *    The test case pretends validate the pie chart information.
   * Steps:
   *    1. Check Pie Chart
   */
  @Test
  public void tc03_ChartContent_DisplayedCorrect() {
    this.log.info( "tc03_ChartContent_DisplayedCorrect" );

    /*
      * ## Step 1 - Pie Chart
      */
    final String pieChartTitle = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.id( "PieChartTitleRow" ) );
    Assert.assertEquals( "Pie Chart", pieChartTitle );
    // Check pies
    final WebElement pieChartCircle1 = this.elemHelper.FindElement( BaseTest.driver, By.cssSelector( "#pieChartprotovis > svg > g > g:nth-child(2) > g > g:nth-child(1) > path:nth-child(1)" ) );
    Assert.assertNotNull( pieChartCircle1 );
    final WebElement pieChartCircle2 = this.elemHelper.FindElement( BaseTest.driver, By.cssSelector( "#pieChartprotovis > svg > g > g:nth-child(2) > g > g:nth-child(1) > path:nth-child(2)" ) );
    Assert.assertNotNull( pieChartCircle2 );
    final WebElement pieChartCircle3 = this.elemHelper.FindElement( BaseTest.driver, By.cssSelector( "#pieChartprotovis > svg > g > g:nth-child(2) > g > g:nth-child(1) > path:nth-child(3)" ) );
    Assert.assertNotNull( pieChartCircle3 );
    // Interact with pie chart disabling two series
    // Series 2003
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#pieChartprotovis > svg > g > g:nth-child(1) > g > g > g > g:nth-child(1) > g:nth-child(2) > text" ) );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "#pieChartprotovis > svg > g > g:nth-child(2) > g > g:nth-child(1) > path:nth-child(3)" ) );
    // Series 2004
    this.elemHelper.Click( BaseTest.driver, By.cssSelector( "#pieChartprotovis > svg > g > g:nth-child(1) > g > g > g > g:nth-child(2) > g:nth-child(2) > text" ) );
    this.elemHelper.WaitForElementInvisibility( BaseTest.driver, By.cssSelector( "#pieChartprotovis > svg > g > g:nth-child(2) > g > g:nth-child(1) > path:nth-child(2)" ) );
    final boolean noCircle2 = this.elemHelper.WaitForElementNotPresent( BaseTest.driver, By.cssSelector( "#pieChartprotovis > svg > g > g:nth-child(2) > g > g:nth-child(1) > path:nth-child(2)" ), 1 );
    Assert.assertTrue( noCircle2 );
    // Check value of the serie2005
    final String serie2005Value = this.elemHelper.WaitForElementPresentGetText( BaseTest.driver, By.cssSelector( "#pieChartprotovis > svg > g > g:nth-child(2) > g > g:nth-child(2) > text" ) );
    Assert.assertEquals( "1.98M", serie2005Value );
  }
}
