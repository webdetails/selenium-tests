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
package com.pentaho.ctools.issues.cde;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.BaseTest;
import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDE-403
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-991
 *
 * NOTE
 * To test this script it is required to have CDE plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDE403 extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( CDE403.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Focus goes to opened input field
   *
   * Description:
   *    The test pretends validate the CDE-403 issue, so when user opens an
   *    input field the focus goes automatically to it.
   *
   * Steps:
   *    TODO
   */
  @Test
  public void tc01_CdeDashboard_InputFieldFocus() {
    this.log.info( "tc01_CdeDashboard_InputFieldFocus" );

    //Go to New CDE Dashboard
    driver.get( PageUrl.CDE_DASHBOARD );
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    /*
     * ## Step 1
     */
    //Assert elements on page and go to Datasources Panel
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='datasourcesPanelButton']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "previewButton" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='layoutPanelButton']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='componentsPanelButton']" ) );
    assertNotNull( element );
    this.elemHelper.Click( driver, By.xpath( "//div[@class='datasourcesPanelButton']" ) );

    /*
     * ## Step 2
     */
    //Add MDX query element and click Parameters
    this.elemHelper.ClickElementInvisible( driver, By.xpath( "//a[@title='denormalizedMdx over mondrianJdbc']" ) );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//td[@title='Query to be executed in the selected datasource']" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[8]/td[2]/div/button" ) );
    assertNotNull( element );
    this.elemHelper.Click( driver, By.xpath( "//table[@id='table-cdfdd-datasources-properties']/tbody/tr[8]/td[2]/div/button" ) );

    /*
     * ## Step 3
     */
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "wizardDialogBody" ) );
    assertNotNull( element );
    element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "wizardEditor" ) );
    assertNotNull( element );
    Robot robot;
    try {
      robot = new Robot();
      robot.keyPress( KeyEvent.VK_T );
      robot.keyRelease( KeyEvent.VK_T );
      robot.keyPress( KeyEvent.VK_E );
      robot.keyRelease( KeyEvent.VK_E );
      robot.keyPress( KeyEvent.VK_S );
      robot.keyRelease( KeyEvent.VK_S );
      robot.keyPress( KeyEvent.VK_T );
      robot.keyRelease( KeyEvent.VK_T );
    } catch ( AWTException e ) {
      e.printStackTrace();
    }

    /*
     * ## Step 4
     */
    String text = this.elemHelper.WaitForTextPresence( driver, By.xpath( "//pre[@id='wizardEditor']/div[2]/div/div[3]/div" ), "testselect {} ON COLUMNS," );
    assertEquals( "testselect {} ON COLUMNS,", text );
  }
}
