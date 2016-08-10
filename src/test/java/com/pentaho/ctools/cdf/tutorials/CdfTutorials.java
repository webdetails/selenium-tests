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
package com.pentaho.ctools.cdf.tutorials;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.selenium.BaseTest;

public class CdfTutorials extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CdfTutorials.class );

  /**
   * Check if first tab is present and is named "Intro" and the remain tabs are present and named accordingly to the
   * number of the step they represent.
   */
  public void checkTabs( int nSteps ) {
    this.log.info( "checkTabs" );

    // Check if first tab is present and is named "Intro"
    assertEquals( this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//*[@id='0']/a" ) ), "Intro" );

    // Check if the remain tabs are present and named accordingly to the number of the step they represent
    for ( int i = 1; i <= nSteps; i++ ) {
      String locator = String.format( "//*[@id='%d']/a", i );
      String stepName = String.format( "Step%d", i );

      assertEquals( this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( locator ) ), stepName );

      this.elemHelper.Click( driver, By.xpath( locator ) );

      tabsMatchRightPanel( i );
    }
  }

  /**
   * Check if the tabs name match the title on the right panel
   */
  public void tabsMatchRightPanel( int stepNumber ) {
    // xpath locator for the title
    String titleLocator = String.format( "//*[@id='step%d']/h3", stepNumber );

    // Title matching the step name/number
    String title = String.format( "Step %d", stepNumber );

    assertEquals( this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( titleLocator ) ), title );
  }

  /**
   * Check if the logo that links to webdetails website at the bottom of the page is working correctly.
   */
  public void checkWebdetailsLogoLink() {
    // Click on the Webdetails logo
    this.elemHelper.Click( driver, By.xpath( "//*[@id='footer']/div[1]/a" ) );

    // Check if page was loaded
    assertNotNull( this.elemHelper.WaitForElementPresence( driver, By.xpath( "//header//a[@href='http://www.webdetails.pt']" ) ) );

    // Back
    driver.navigate().back();
  }

  /**
   * Check every tooltips in the sample, click it and make sure the code file that pops up isn't empty.
   * 
   * Steps: 1.For every tab: 1.1 Open the tab 1.2 Find all the tooltips in the sample (icons with a "?" image in the
   * graphics).
   *
   * 1.3 If tooltips were found, for each of them: 1.3.1 Click the tooltip. Content of the code file for the sample pops
   * up. 1.3.2 Check if code file it's not empty. 1.3.3 Close the pop up.
   */
  public void checkTooltips( int nSteps ) {
    this.log.info( "checkTooltips" );

    // Check if first tab is present and is named "Intro"
    assertEquals( this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//*[@id='0']/a" ) ), "Intro" );

    // For every tab
    for ( int i = 1; i <= nSteps; i++ ) {
      String locator = String.format( "//*[@id='%d']/a", i );
      String stepName = String.format( "Step%d", i );

      assertEquals( this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( locator ) ), stepName );

      // Open the tab
      this.elemHelper.Click( driver, By.xpath( locator ) );

      // Prevent the next line of code to wait for the timeout
      driver.manage().timeouts().implicitlyWait( 0, TimeUnit.SECONDS );

      Boolean result = false;

      while ( result == false ) {
        try {
          // Find all the tooltips in the sample (icons with a "?" image in the graphics)
          List<WebElement> tooltips = this.elemHelper.FindElements( driver, By.xpath( "//img[@class='samplesTooltip']" ) );

          // If tooltips were found, for each of them
          for ( WebElement tooltip : tooltips ) {
            // Click the tooltip
            tooltip.click();

            // Check if code file it's not empty
            assertNotEquals( this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//textarea" ) ), "" );

            // Close pop up
            this.elemHelper.Click( driver, By.xpath( "//button[contains(text(),'Close')]" ) );

            // Wait for pop up to disappear
            this.elemHelper.WaitForElementNotPresent( driver, By.xpath( "//button[contains(text(),'Close')]" ) );
          }

          result = true;
        } catch ( StaleElementReferenceException e ) {
          this.log.warn( "Stale Element Exception", e );
          result = false;
        }
      }
    }
  }
}
