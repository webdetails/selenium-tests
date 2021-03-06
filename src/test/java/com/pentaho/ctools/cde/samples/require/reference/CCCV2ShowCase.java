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
package com.pentaho.ctools.cde.samples.require.reference;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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
    this.elemHelper.Get( driver, PageUrl.CCCV2_SHOWCASE_REQUIRE );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ), 1 );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    // Check page title
    String expectedTitle = "CCC V2 ShowCase";
    String actualTitle = this.elemHelper.WaitForTitle( driver, expectedTitle );
    assertEquals( actualTitle, expectedTitle );
    // Check title
    String title = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='Headers']/div" ) );
    assertEquals( "CCC v2 - Show Case", title );
    // Wait for the charts load
    // search for width of barchartrect1
    // search for width of barchartrect2
    // search for width of barchartrect3
    WebElement barchartrect1 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='BarChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='rect'][1][@width>192]" ), 10 );
    assertNotNull(barchartrect1);
    WebElement barchartrect2 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='BarChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='rect'][2][@width>249]" ), 10 );
    assertNotNull(barchartrect2);
    WebElement barchartrect3 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='BarChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='rect'][3][@width>100]" ), 10 );
    assertNotNull(barchartrect3);
    // search for cy of line1
    // search for cy of line7
    // search for cy of line11
    WebElement cyofline1 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='LineChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='circle'][1][@cy>108]" ), 10 );
    assertNotNull(cyofline1);
    WebElement cyofline7 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='LineChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='circle'][7][@cy>122]" ), 10 );
    assertNotNull(cyofline7);
    WebElement cyofline11 = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='LineChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='circle'][11][@cy>22]" ), 10 );
    assertNotNull(cyofline11);
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
  //@Test
  public void tc01_ChartContent_DisplayedCorrect() {
    this.log.info( "tc01_ChartContent_DisplayedCorrect" );

    /*
     * ## Step 1 - Bar Chart
     */
    String barChartTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "BarChartTitleRow" ) );
    assertEquals( "Bar Chart", barChartTitle );
    // Check bars
    this.elemHelper.FindElement( driver, By.xpath( "//div[@id='BarChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='rect'][2]" ) );
    WebElement barChartRect1 = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='BarChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='rect'][1]" ) );
    WebElement barChartRect2 = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='BarChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='rect'][2]" ) );
    WebElement barChartRect3 = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='BarChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][1]/*[local-name()='rect'][3]" ) );
    assertNotNull( barChartRect1 );
    assertNotNull( barChartRect2 );
    assertNotNull( barChartRect3 );
    double barChartRect1Width = Double.parseDouble( barChartRect1.getAttribute( "width" ) );
    double barChartRect2Width = Double.parseDouble( barChartRect2.getAttribute( "width" ) );
    double barChartRect3Width = Double.parseDouble( barChartRect3.getAttribute( "width" ) );
    assertThat( "Current width: " + barChartRect1Width, Double.valueOf( barChartRect1Width ), greaterThan( Double.valueOf( 192 ) ) );
    assertThat( "Current width: " + barChartRect2Width, Double.valueOf( barChartRect2Width ), greaterThan( Double.valueOf( 250 ) ) );
    assertThat( "Current width: " + barChartRect3Width, Double.valueOf( barChartRect3Width ), greaterThan( Double.valueOf( 100 ) ) );
    // Check bars value
    String barChartRectValue1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='BarChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text'][1]" ) );
    String barChartRectValue2 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='BarChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text'][2]" ) );
    String barChartRectValue3 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='BarChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='text'][3]" ) );
    assertEquals( "3.68M", barChartRectValue1 );
    assertEquals( "4.99M", barChartRectValue2 );
    assertEquals( "1.98M", barChartRectValue3 );

    // Mouse hover elements
    this.elemHelper.MoveToElement( driver, By.cssSelector( "#barChartprotovis > svg > g > g > g:nth-child(2) > g > g > g:nth-child(1) > rect:nth-child(2)" ) );
    String seriesLabel = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[1]/td[1]/span" ) );
    assertEquals( seriesLabel, "Series" );
    String seriesValue = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[1]/td[3]/span" ) );
    assertEquals( seriesValue, "Sales" );
    String timeLabel = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[2]/td[1]/span" ) );
    assertEquals( timeLabel, "Time" );
    String timeValue = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[2]/td[3]/span" ) );
    assertEquals( timeValue, "2004" );
    String valueLabel = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[3]/td[1]/span" ) );
    assertEquals( valueLabel, "Value" );
    String valueValue = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[3]/td[3]/span" ) );
    assertEquals( valueValue, "4,987,739.84" );
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
     * ## Step 1 - Line Chart
     */
    String lineChartTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "LineChartTitleRow" ) );
    assertEquals( "Line Chart", lineChartTitle );
    // Check lines
    WebElement lineChartCircle1 = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='LineChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='circle'][1]" ) );
    WebElement lineChartCircle2 = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='LineChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='circle'][7]" ) );
    WebElement lineChartCircle3 = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='LineChartBodyRow']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='circle'][11]" ) );
    double lineChartCircle1Cy = Double.parseDouble( lineChartCircle1.getAttribute( "cy" ) );
    double lineChartCircle2Cy = Double.parseDouble( lineChartCircle2.getAttribute( "cy" ) );
    double lineChartCircle3Cy = Double.parseDouble( lineChartCircle3.getAttribute( "cy" ) );
    assertThat( "Current cy: " + lineChartCircle1Cy, Double.valueOf( lineChartCircle1Cy ), greaterThan( Double.valueOf( 108 ) ) );
    assertThat( "Current cy: " + lineChartCircle2Cy, Double.valueOf( lineChartCircle2Cy ), greaterThan( Double.valueOf( 122 ) ) );
    assertThat( "Current cy: " + lineChartCircle3Cy, Double.valueOf( lineChartCircle3Cy ), greaterThan( Double.valueOf( 22 ) ) );

    // Mouse hover elements
    this.elemHelper.FocusAndMoveToElement( driver, By.cssSelector( "#LineChartprotovis > svg > g > g > g:nth-child(3) > g > g > g:nth-child(3) > circle:nth-child(2)" ) );
    String measuresLabel = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[1]/td[1]/span" ) );
    assertEquals( "Measures", measuresLabel );
    String measuresValue = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[1]/td[3]/span" ) );
    assertEquals( "Quantity", measuresValue );
    String categoryLabel = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[2]/td[1]/span" ) );
    assertEquals( "Category", categoryLabel );
    String categoryValue = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[2]/td[3]/span" ) );
    assertEquals( "Feb", categoryValue );
    String valueLineValue = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@class='tipsy tipsy-s']/div[2]/div/table/tbody/tr[4]/td[3]/span" ) );
    assertEquals( "7,959", valueLineValue );
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
    String pieChartTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "PieChartTitleRow" ) );
    assertEquals( "Pie Chart", pieChartTitle );
    // Check pies
    WebElement pieChartCircle1 = this.elemHelper.FindElement( driver, By.cssSelector( "#pieChartprotovis > svg > g > g:nth-child(2) > g > g:nth-child(1) > path:nth-child(1)" ) );
    assertNotNull( pieChartCircle1 );
    WebElement pieChartCircle2 = this.elemHelper.FindElement( driver, By.cssSelector( "#pieChartprotovis > svg > g > g:nth-child(2) > g > g:nth-child(1) > path:nth-child(2)" ) );
    assertNotNull( pieChartCircle2 );
    WebElement pieChartCircle3 = this.elemHelper.FindElement( driver, By.cssSelector( "#pieChartprotovis > svg > g > g:nth-child(2) > g > g:nth-child(1) > path:nth-child(3)" ) );
    assertNotNull( pieChartCircle3 );
    // Interact with pie chart disabling two series
    // Series 2003
    this.elemHelper.Click( driver, By.cssSelector( "#pieChartprotovis > svg > g > g:nth-child(1) > g > g > g > g:nth-child(1) > g:nth-child(2) > text" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "#pieChartprotovis > svg > g > g:nth-child(2) > g > g:nth-child(1) > path:nth-child(3)" ) );
    // Series 2004
    this.elemHelper.Click( driver, By.cssSelector( "#pieChartprotovis > svg > g > g:nth-child(1) > g > g > g > g:nth-child(2) > g:nth-child(2) > text" ) );
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "#pieChartprotovis > svg > g > g:nth-child(2) > g > g:nth-child(1) > path:nth-child(2)" ) );
    boolean noCircle2 = this.elemHelper.WaitForElementNotPresent( driver, By.cssSelector( "#pieChartprotovis > svg > g > g:nth-child(2) > g > g:nth-child(1) > path:nth-child(2)" ), 1 );
    assertTrue( noCircle2 );
    // Check value of the serie2005
    String serie2005Value = this.elemHelper.WaitForElementPresentGetText( driver, By.cssSelector( "#pieChartprotovis > svg > g > g:nth-child(2) > g > g:nth-child(2) > text" ) );
    assertEquals( "1.98M", serie2005Value );
  }
}
