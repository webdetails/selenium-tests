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
package org.pentaho.ctools.cgg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.PageUrl;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * Testing the functionalities related with Login.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class BarChart {

  // Instance of the driver (browser emulator)
  private WebDriver driver = CToolsTestSuite.getDriver();
  //Access to wrapper for webdriver
  private ElementHelper elemHelper = new ElementHelper();
  //Log instance
  private static final Logger LOG = LogManager.getLogger( BarChart.class );

  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( this.driver );

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
  @Test( timeout = 60000 )
  public void tc1_BarChart_ImageRendered() {
    LOG.debug( "tc1_BarChart_ImageRendered" );
    //## Step 1
    this.driver.get( PageUrl.BAR_CHART );

    WebElement elementImage = this.elemHelper.FindElement( this.driver, By.cssSelector( "img" ) );
    assertNotNull( elementImage );
    String attrWidth = elementImage.getAttribute( "width" );
    String attrHeight = elementImage.getAttribute( "height" );
    String attrSrc = elementImage.getAttribute( "src" );

    assertEquals( "852", attrWidth );
    assertEquals( "637", attrHeight );
    assertEquals( PageUrl.BAR_CHART, attrSrc );
  }

}
