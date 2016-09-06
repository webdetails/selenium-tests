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
package com.pentaho.ctools.cgg;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
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
 * Testing the functionalities related with Login.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class BarChart extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  //Log instance
  private final Logger log = LogManager.getLogger( BarChart.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Bar Chart
   * Description:
   *    We pretend to check if an image is displayed with bares in a chart.
   * Steps:
   *    1. Open the bar chart.
   */
  @Test
  public void tc1_BarChart_ImageRendered() {
    this.log.info( "tc1_BarChart_ImageRendered" );

    /*
     * ## Step 1
     */
    driver.get( PageUrl.BAR_CHART );

    WebElement elementImage = this.elemHelper.FindElement( driver, By.cssSelector( "img" ) );
    assertNotNull( elementImage );
    String attrWidth = elementImage.getAttribute( "width" );
    String attrHeight = elementImage.getAttribute( "height" );
    String attrSrc = elementImage.getAttribute( "src" );

    assertFalse( attrWidth.isEmpty() );
    assertFalse( attrHeight.isEmpty() );
    assertTrue( ( Integer.parseInt( attrWidth ) > 800 ) ? true : false, "the number " + attrWidth );
    assertTrue( ( Integer.parseInt( attrHeight ) > 600 ) ? true : false, "the number " + attrHeight );
    assertEquals( attrSrc, PageUrl.BAR_CHART );
  }
}
