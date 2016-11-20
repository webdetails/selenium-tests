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
package com.pentaho.ctools.cde.require;

import static org.testng.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with Ajax Request Reference.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class AjaxRequestReference extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( AjaxRequestReference.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    PageContent
   * Description:
   *    The test case pretends validate the contents presented in the sample.
   * Steps:
   *    1. Check page content.
   */
  @Test
  public void tc01_PageContent_InformationPresent() {
    this.log.info( "tc01_PageContent_InformationPresent" );

    // Go to AddinReference
    this.elemHelper.Get( driver, PageUrl.AJAX_REQUEST_REFERENCE_REQUIRE );

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementPresence( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
    this.elemHelper.WaitForElementNotPresent( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );

    /*
     * ## Step 1
     */
    //Check page title

    String expectedTitle = "Ajax Request Reference";
    String actualTitle = this.elemHelper.WaitForTitle( driver, expectedTitle );
    assertEquals( actualTitle, expectedTitle );
    //Check title
    String title = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='Title']/span" ) );
    assertEquals( "Ajax Request Reference", title );
    //Check first paragh
    String expParag = "Ajax Request Component provides a way to build an Ajax request. Given an url, a response type and list of parameters it's possible to build an jQuery ajax call, which its result will be stored in the resultvar.";
    String actParag = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='DescriptionBody']/p" ) );
    assertEquals( expParag, actParag );
    //Check subtitle
    String subtitle = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='DescriptionBody']/div" ) );
    assertEquals( "Component Parameters", subtitle );
    //Check parag 1
    String parag1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='DescriptionBody']/p[2]" ) );
    assertEquals( "The Component definition supports the following arguments:", parag1 );
    //Check subtitle2
    String subtitle2 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='DescriptionBody']/div[2]" ) );
    assertEquals( "Default values", subtitle2 );
    //Check quote
    String quote = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='DescriptionBody']/blockquote/pre" ) );
    assertEquals( "defaults: { ajaxRequestType: json, asyncCall: true }", quote );
    //Check result
    String jsonQueryExpected = "{\"queryInfo\":{\"totalRows\":\"19\"},\"resultset\":[[\"Car\",\"Red\",10],[\"Car\",\"Blue\",20],[\"Car\",\"Green\",30],[\"Car\",\"Yellow\",5],[\"Car\",\"Black\",25],[\"Car\",\"White\",7],[\"Bike\",\"Red\",20],[\"Bike\",\"Blue\",20],[\"Bike\",\"Green\",40],[\"Bike\",\"Yellow\",80],[\"Bike\",\"Black\",1],[\"Bike\",\"White\",23],[\"Ship\",\"Red\",2],[\"Ship\",\"Blue\",7],[\"Plane\",\"Red\",5],[\"Plane\",\"Blue\",4],[\"Train\",\"Red\",50],[\"Train\",\"Blue\",50],[\"Train\",\"Green\",7]],\"metadata\":[{\"colIndex\":0,\"colType\":\"String\",\"colName\":\"series\"},{\"colIndex\":1,\"colType\":\"String\",\"colName\":\"category\"},{\"colIndex\":2,\"colType\":\"Integer\",\"colName\":\"value\"}]}";
    String jsonQueryActual = this.elemHelper.WaitForTextDifferentEmpty( driver, By.id( "column1" ) );
    JsonParser parser = new JsonParser();
    JsonElement actual = parser.parse( jsonQueryActual );
    JsonElement expected = parser.parse( jsonQueryExpected );
    assertEquals( actual, expected );
  }
}
