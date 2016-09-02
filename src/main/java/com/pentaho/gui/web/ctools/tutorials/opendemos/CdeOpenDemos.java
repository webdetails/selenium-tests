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

package com.pentaho.gui.web.ctools.tutorials.opendemos;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Hashtable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

public class CdeOpenDemos extends BaseTest {
  // Access to wrapper for webdriver
  private static final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private static final Logger log = LogManager.getLogger( CdeOpenDemos.class );

  private static Hashtable<String, String> demos;

  @BeforeClass
  public void init() {
    demos = new Hashtable<>();

    demos.put( "T-Wars", "//ul[@class='navSlides']/li[1]/img" );
    demos.put( "Automotive", "//ul[@class='navSlides']/li[2]/img" );
    demos.put( "Theme Park", "//ul[@class='navSlides']/li[3]/img" );
    demos.put( "Retail.Co", "//ul[@class='navSlides']/li[4]/img" );
    demos.put( "Number One", "//ul[@class='navSlides']/li[5]/img" );
  }

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

  @Test
  public void tc1_checkTitlesAndLinksToDemos() {
    String demoNumberXpath = String.format( "//*[@id='info']/h2" );
    String demoTitleXpath = String.format( "//*[@id='info']/h1" );

    for ( String title : demos.keySet() ) {
      // Wait for the loading icon to disappear
      elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

      // Click on Demo thumbnail
      elemHelper.Click( driver, By.xpath( demos.get( title ) ) );

      // Assert Title Number
      assertTrue( ( elemHelper.WaitForElementPresentGetText( driver, By.xpath( demoNumberXpath ) ).contains( "Demo 0" ) ) );

      // Assert Title Name
      assertEquals( elemHelper.WaitForElementPresentGetText( driver, By.xpath( demoTitleXpath ) ), title );
    }
  }

  public String viewDemo( String name ) {

    // Click on Demo thumbnail
    elemHelper.Click( driver, By.xpath( demos.get( name ) ) );

    String winHandleBefore = driver.getWindowHandle();

    // Click on the "View Demo" button
    elemHelper.Click( driver, By.xpath( "//p[contains(text(),'View Demo')]" ) );

    for ( String winHandle : driver.getWindowHandles() ) {
      driver.switchTo().window( winHandle );
    }

    return winHandleBefore;
  }
}
