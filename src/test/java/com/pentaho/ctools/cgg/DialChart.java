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
package com.pentaho.ctools.cgg;

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
 * Testing the functionalities related with CGG - Dial Chart.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class DialChart extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  //Log instance
  private final Logger log = LogManager.getLogger( DialChart.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Dial  Chart
   * Description:
   *    We pretend to check if a image is display with dial in a chart.
   * Steps:
   *    1. Open the dial chart.
   */
  @Test
  public void tc1_DialChart_ImageRendered() {
    this.log.info( "tc1_DialChart_ImageRendered" );
    /*
     * ## Step 1
     */
    driver.get( PageUrl.DIAL_CHART );

    //Check Pointer
    WebElement elemPointer = this.elemHelper.FindElement( driver, By.xpath( "//*[local-name()='g'][6]/*[local-name()='polygon'][@id='ponteiro' and @transform='rotate(62.99999999999999,300,275)']" ) );
    WebElement basePointers = this.elemHelper.FindElement( driver, By.xpath( "//*[local-name()='g'][6]/*[local-name()='path'][@id='base_ponteiro']" ) );
    assertNotNull( elemPointer );
    assertNotNull( basePointers );

    //Check Colors
    WebElement elemColor1 = this.elemHelper.FindElement( driver, By.xpath( "//*[local-name()='g'][6]/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='path'][@fill='rgb(255,0,0)']" ) );
    WebElement elemColor2 = this.elemHelper.FindElement( driver, By.xpath( "//*[local-name()='g'][6]/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='path'][2][@fill='rgb(255,255,0)']" ) );
    WebElement elemColor3 = this.elemHelper.FindElement( driver, By.xpath( "//*[local-name()='g'][6]/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='path'][3][@fill='rgb(0,128,0)']" ) );
    assertNotNull( elemColor1 );
    assertNotNull( elemColor2 );
    assertNotNull( elemColor3 );
  }
}
