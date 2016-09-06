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

package com.pentaho.ctools.tutorials.opendemos;

import static org.testng.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

public class OpenOpenDemos extends BaseTest {
  // Access to wrapper for webdriver
  private static final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private static final Logger log = LogManager.getLogger( OpenOpenDemos.class );

  /**
   * Open Open Demos Homepage.
   */
  @Test
  public void tc0_open() {
    log.info( "tc0_apen" );

    // wait for invisibility of waiting pop-up
    elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

    // Open CDE Tutorials Welcome page
    elemHelper.Get( driver, PageUrl.OPEN_DEMOS );

    // Test if page was loaded
    assertEquals( "OPEN DEMOS", elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//p[@class='title']" ) ) );

    return;
  }
}
