package com.pentaho.ctools.tutorials.cde;

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
import static org.testng.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/** 
 * Testing the functionalities related with CDE Tutorials.
 *
 * Naming convention for test: 'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class OpenCdeTutorials extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( OpenCdeTutorials.class );

  /**
   * ############################### Test Case 0 ###############################
   *
   * Description: Opens CDE Tutorials in Welcome page.
   */
  @Test
  public void tc0_open() {
    this.log.info( "tc0_open" );

    // wait for invisibility of waiting pop-up
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

    // Open CDE Tutorials Welcome page
    this.elemHelper.Get( driver, PageUrl.CDE_TUTORIALS_WELCOME );

    // Test if page was loaded
    assertEquals( "Welcome", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//*[@id='mainContent']/h1" ) ) );
  }
}
