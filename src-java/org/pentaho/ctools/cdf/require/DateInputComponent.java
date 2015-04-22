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
package org.pentaho.ctools.cdf.require;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * Testing the functionalities related with DateInputComponent.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class DateInputComponent {

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
   *    DateInputComponent
   * Description:
   *    We pretend to check the component when user pick a data an alert message
   *    is displayed indicating the date picked.
   * Steps:
   *    1. Go to Pentaho solution web page.
   *    2. Render the component again.
   *    3. Choose the date '2011-10-23'.
   */
  @Test( timeout = 60000 )
  public void tc1_DataInput_DisplayPopupWithPickedDate() {
    //## Step 1
    this.driver.get( this.baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A40-DateInputComponent%3Adate_input_component.xcdf/generatedContent" );

    //NOTE - we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Wait for visibility of 'DateInputComponent'
    ElementHelper.WaitForElementVisibility( this.driver, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) );
    // Validate the sample that we are testing is the one
    assertEquals( "Community Dashboard Framework", this.driver.getTitle() );
    assertEquals( "DateInputComponent", ElementHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );

    //## Step 2
    //Render again the sample
    ElementHelper.FindElement( this.driver, By.xpath( "//div[@id='example']/ul/li[2]/a" ) ).click();
    ElementHelper.FindElement( this.driver, By.xpath( "//div[@id='code']/button" ) ).click();
    //NOTE - we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    //Now sample element must be displayed
    assertTrue( ElementHelper.FindElement( this.driver, By.id( "sample" ) ).isDisplayed() );

    //## Step 3
    //Pick a date
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "myInput" ) ) );
    ElementHelper.FindElement( this.driver, By.id( "myInput" ) ).sendKeys( "''" );

    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.id( "ui-datepicker-div" ) ) );
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.className( "ui-datepicker-month" ) ) );
    this.wait.until( ExpectedConditions.visibilityOfElementLocated( By.className( "ui-datepicker-year" ) ) );
    final Select month = new Select( ElementHelper.FindElement( this.driver, By.className( "ui-datepicker-month" ) ) );
    month.selectByValue( "9" );
    final Select year = new Select( ElementHelper.FindElement( this.driver, By.className( "ui-datepicker-year" ) ) );
    year.selectByValue( "2011" );
    //Day 23
    ElementHelper.FindElement( this.driver, By.xpath( "//table[@class='ui-datepicker-calendar']//tbody//tr[5]/td/a" ) ).sendKeys( Keys.ENTER );

    this.wait.until( ExpectedConditions.alertIsPresent() );
    final Alert alert = this.driver.switchTo().alert();
    final String confirmationMsg = alert.getText();
    alert.accept();

    /*##########################################################################
      EXPECTED RESULT:
      - The popup alert shall displayed the data picked.
     #########################################################################*/
    assertEquals( confirmationMsg, "You chose: 2011-10-23" );
  }

  @After
  public void tearDown() {
    //To use after test case run.
  }
}
