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
package org.pentaho.ctools.cdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * Testing the functionalities related with AutocompleteBox.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class AutoCompleteBoxComponent {
  // Instance of the driver (browser emulator)
  private WebDriver driver;
  // Instance to be used on wait commands
  private Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private String baseUrl;

  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( this.driver );

  @Before
  public void setUp() throws Exception {
    this.driver = CToolsTestSuite.getDriver();
    this.wait = CToolsTestSuite.getWait();
    this.baseUrl = CToolsTestSuite.getBaseUrl();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    AutocompleteBox
   * Description:
   *    We pretend to test the component AutocompleteBox, so when we enter any 
   *    key, the text box should show a list of related data (that contains the
   *    same string).
   * Steps:
   *    1. Open the AutocompleteBoxComponent.
   *    2. Execute the "Try me".
   *    3. Press a on text box and check the autocomplete.
   */
  @Test( timeout = 90000 )
  public void tc1_AutocompleteBox_DataAreListed() {
    //## Step 1
    this.driver.get( this.baseUrl + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:58-AutocompleteBoxComponent:autocomplete_component.xcdf/generatedContent" );

    //NOTE - we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Wait for title become visible and with value 'Community Dashboard Framework'
    this.wait.until( ExpectedConditions.titleContains( "Community Dashboard Framework" ) );
    //Wait for visibility of 'AutocompleteBoxComponent'
    ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) );
    // Validate the sample that we are testing is the one
    assertEquals( "Community Dashboard Framework", this.driver.getTitle() );
    assertEquals( "AutocompleteBoxComponent", ElementHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );

    //## Step 2
    //Render again the sample 
    ElementHelper.Click( this.driver, By.xpath( "//div[@id='example']/ul/li[2]/a" ) );
    ElementHelper.Click( this.driver, By.xpath( "//div[@id='code']/button" ) );
    //NOTE - we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    //Now sample element must be displayed
    assertTrue( ElementHelper.FindElement( this.driver, By.id( "sample" ) ).isDisplayed() );

    //## Step 3
    //Key press 'a'
    ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "sampleObjectautoboxInput" ) );
    ElementHelper.FindElement( this.driver, By.id( "sampleObjectautoboxInput" ) ).sendKeys( "a" );

    //Retrieve data by pressing key 'a'
    ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "listElement" ), 45 );
    assertNotNull( ElementHelper.FindElement( this.driver, By.id( "listElement" ) ) );

    assertTrue( ElementHelper.FindElement( this.driver, By.xpath( "//ul[@class='autobox-list']/li/input" ) ).isEnabled() );
    assertTrue( ElementHelper.FindElement( this.driver, By.xpath( "//ul[@class='autobox-list']/li[2]/input" ) ).isEnabled() );
    assertTrue( ElementHelper.FindElement( this.driver, By.xpath( "//ul[@class='autobox-list']/li[3]/input" ) ).isEnabled() );
    assertTrue( ElementHelper.FindElement( this.driver, By.xpath( "//ul[@class='autobox-list']/li[4]/input" ) ).isEnabled() );
    assertTrue( ElementHelper.FindElement( this.driver, By.xpath( "//ul[@class='autobox-list']/li[5]/input" ) ).isEnabled() );
    assertTrue( ElementHelper.FindElement( this.driver, By.xpath( "//ul[@class='autobox-list']/li[6]/input" ) ).isEnabled() );
    assertTrue( ElementHelper.FindElement( this.driver, By.xpath( "//ul[@class='autobox-list']/li[7]/input" ) ).isEnabled() );
    assertTrue( ElementHelper.FindElement( this.driver, By.xpath( "//ul[@class='autobox-list']/li[8]/input" ) ).isEnabled() );
    assertTrue( ElementHelper.FindElement( this.driver, By.xpath( "//ul[@class='autobox-list']/li[9]/input" ) ).isEnabled() );
    assertTrue( ElementHelper.FindElement( this.driver, By.xpath( "//ul[@class='autobox-list']/li[10]/input" ) ).isEnabled() );
    assertTrue( ElementHelper.FindElement( this.driver, By.xpath( "//ul[@class='autobox-list']/li[11]/input" ) ).isEnabled() );
  }

  @After
  public void tearDown() {
    //To use after test case run.
  }
}
