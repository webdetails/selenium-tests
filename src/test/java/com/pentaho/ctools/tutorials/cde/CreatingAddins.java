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
package com.pentaho.ctools.tutorials.cde;

import static org.testng.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.gui.web.ctools.tutorials.cde.CdeTutorials;
import com.pentaho.selenium.BaseTest;

public class CreatingAddins extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CreatingAddins.class );
  // Instance to access CDE Tutorial
  private final CdeTutorials cdetutorial = new CdeTutorials();

  /**
   * ############################### Setup ###############################
   *
   * Description:
   *    Open Creating Add-ins Page
   */
  @BeforeClass
  public void openCreatingAddinsPage() {
    this.log.info( "openCreatingAddinsPage" );

    //Wait for the loading icon to disappear
    elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    this.elemHelper.Click( driver, By.xpath( "//*[@id='sideMenu']/ul/a[17]/li" ) );

    assertEquals( "Creating Add-ins", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id= 'mainContent']/h1" ) ) );

    return;
  }

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Text links
   *    
   * Test Case Description:
   *    Click on all links in the text and check the pages are loaded. 
   * 
   * Test Steps:
   *    1. Check Sparklines link.
   */
  @Test
  public void tc0_TextLinks_Displayed() {
    this.log.info( "tc0_TextLinks_Displayed" );

    /*
     * Step #1
     */
    this.cdetutorial.clickAndCheckPageLoaded( By.xpath( "//p/a[contains(text(),'sparkline using the jquery plugin')]" ), By.xpath( "//title" ), "jQuery Sparklines" );

    return;
  }

}
