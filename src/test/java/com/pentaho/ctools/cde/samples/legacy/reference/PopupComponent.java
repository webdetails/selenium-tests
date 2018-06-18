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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
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
 * Testing the functionalities related with Popup Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class PopupComponent extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( PopupComponent.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    PageContent
   * Description:
   *    The test case pretends validate the contents of the sample is presented
   *    in the page.
   * Steps:
   *    1. Check page content
   */
  @Test
  public void tc01_PageContent_DisplayContent() {
    this.log.info( "tc01_PageContent_DisplayContent" );

    // Go to AddinReference
    this.elemHelper.Get( driver, PageUrl.POPUP_COMPONENT );

    //NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    /*
     * ## Step 1
     */
    String strTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='Title']" ) );
    String strDescription = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='DescriptionTitle']" ) );
    String strUsage = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='Usage']" ) );
    String strUsageDesc = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='UsageDesc']/p" ) );
    String strUsageDescNote = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='UsageDesc']/i" ) );

    assertEquals( "Popup Component Reference", strTitle );
    assertEquals( "Description", strDescription );
    assertEquals( "Usage", strUsage );
    assertEquals( "There are 3 steps for defining the popup component", strUsageDesc );
    assertEquals( "Note that when you create a component with name 'popup1' CDE will generate a js variable 'named render_popup1'", strUsageDescNote );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    PopupExample1
   * Description:
   *    The test case pretends validate if the popup works as expect.
   * Steps:
   *    1. Check page contents
   *    2. Check popup display
   */
  @Test
  public void tc02_PopupExample1_PopupDisplay() {
    this.log.info( "tc02_PopupExample1_PopupDisplay" );

    /*
     * ## Step 1
     */
    String strExampleTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='Example1Title']" ) );
    String strExampleDesc = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='Example1Desc']/p" ) );
    String strButtonName = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='example1Obj']/button" ) );

    assertEquals( "Example 1", strExampleTitle );
    assertEquals( "Button popup with the default settings. You can drag it around and close on the button", strExampleDesc );
    assertEquals( "Popup", strButtonName );

    /*
     * ## Step 2
     */
    this.elemHelper.ClickJS( driver, By.xpath( "//div[@id='example1Obj']/button" ) );
    this.elemHelper.WaitForElementPresence( driver, By.xpath( "//div[@id='popupContent1']" ) );
    //Wait for the charts load
    this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//*[3][@width>13]" ) );
    //Check text
    String textInPopup = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='text']" ) );
    assertEquals( "This will appear in the popup", textInPopup );
    //Check rect in chart
    WebElement elemRect1 = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='popupContent1']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='rect'][1]" ) );
    WebElement elemRect2 = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='popupContent1']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='rect'][2]" ) );
    WebElement elemRect3 = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='popupContent1']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='rect'][3]" ) );
    WebElement elemRect4 = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='popupContent1']/div/div/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g']/*[local-name()='rect'][4]" ) );
    assertNotNull( elemRect1 );
    assertNotNull( elemRect2 );
    assertNotNull( elemRect3 );
    assertNotNull( elemRect4 );
    assertEquals( "35.521816666666666", elemRect1.getAttribute( "width" ) ); //36.27305471479055
    assertEquals( "136.75265000000002", elemRect2.getAttribute( "width" ) ); //139.64478231479157
    assertEquals( "13.579274999999999", elemRect3.getAttribute( "width" ) ); //13.866458173700408
    assertEquals( "104.68426666666666", elemRect4.getAttribute( "width" ) ); //106.89819634537434
    //Check subtitles
    this.elemHelper.FocusAndMoveToElement( driver, By.cssSelector( "#chartprotovis > svg > g > g:nth-child(3) > g > g:nth-child(2) > g > g > g > rect:nth-child(2)" ) );
    String tooltipValue = this.elemHelper.WaitForElementPresentGetText( driver, By.cssSelector( "div.tipsy > div:nth-child(2) > div > table > tbody > tr:nth-child(3) > td:nth-child(3) > span" ) );
    assertEquals( "49,578", tooltipValue );
    //Close popup
    this.elemHelper.ClickJS( driver, By.cssSelector( "a.close" ) );
    //wait for popup disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "a.close" ) );
    WebElement element = this.elemHelper.FindElementInvisible( driver, By.cssSelector( "a.close" ) );
    assertFalse( element.isDisplayed() );
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    PopupExample2
   * Description:
   *    The test case pretends validate if the popup works as expect.
   * Steps:
   *    1. Check page contents
   *    2. Check popup display
   */
  @Test
  public void tc03_PopupExample2_PopupDisplay() {
    this.log.info( "tc03_PopupExample2_PopupDisplay" );

    /*
     * ## Step 1
     */
    String strExampleTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "Example2Title" ) );
    String strExampleDesc = this.elemHelper.WaitForElementPresentGetText( driver, By.cssSelector( "#Example2Desc > p" ) );
    String strButtonName = this.elemHelper.WaitForElementPresentGetText( driver, By.cssSelector( "#example2Obj > button" ) );

    assertEquals( strExampleTitle, "Example 2" );
    assertEquals( strExampleDesc, "Button popup appearing in the west, can't be dragged and clicking anywhere outside the element will popup the button" );
    assertEquals( strButtonName, "Popup" );

    /*
     * ## Step 2
     */
    this.elemHelper.ClickJS( driver, By.cssSelector( "#example2Obj > button" ) );
    this.elemHelper.WaitForElementPresence( driver, By.id( "popupContent2" ) );
    //Check text
    String textInPopup = this.elemHelper.WaitForElementPresentGetText( driver, By.id( "popupContent2" ) );
    assertEquals( "A simple text that can be used as a tooltip", textInPopup );
    //Close popup
    this.elemHelper.ClickJS( driver, By.cssSelector( "div.popupComponent.east > a.close" ) );
    //wait for popup disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.popupComponent.east > a.close" ) );
    WebElement element = this.elemHelper.FindElementInvisible( driver, By.cssSelector( "div.popupComponent.east > a.close" ) );
    assertNotNull( element );
    assertFalse( element.isDisplayed() );
  }
}
