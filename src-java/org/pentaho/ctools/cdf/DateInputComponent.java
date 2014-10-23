/*!*****************************************************************************
*
* Selenium Tests For CTools
*
* Copyright (C) 2002-2014 by Pentaho : http://www.pentaho.com
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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Testing the functionalities related with DateInputComponent.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class DateInputComponent {
  // Instance of the driver (browser emulator)
  private WebDriver driver;
  // Instance to be used on wait commands
  private Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private String baseUrl;

  @Before
  public void setUp() throws Exception {
    driver = CToolsTestSuite.getDriver();
    wait = CToolsTestSuite.getWait();
    baseUrl = CToolsTestSuite.getBaseUrl();
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
  @Test
  public void tc1_DataInput_DisplayPopupWithPickedDate() {
  	//## Step 1
  	driver.get(baseUrl + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:40-DateInputComponent:date_input_component.xcdf/generatedContent");

    //Not we have to wait for loading disappear
    ElementHelper.IsElementInvisible(driver, By.xpath("//div[@class='blockUI blockOverlay']"));

    //Wait for visibility of 'DateInputComponent'
    ElementHelper.IsElementDisplayed(driver, By.xpath("//div[@id='dashboardContent']/div/div/div/h2/span[2]"));
    // Validate the sample that we are testing is the one
    assertEquals("Community Dashboard Framework", driver.getTitle());
    assertEquals("DateInputComponent", ElementHelper.GetText(driver, By.xpath("//div[@id='dashboardContent']/div/div/div/h2/span[2]")));

    
    //## Step 2
    //Render again the sample
  	ElementHelper.FindElement(driver, By.xpath("//div[@id='example']/ul/li[2]/a")).click();
    ElementHelper.FindElement(driver, By.xpath("//div[@id='code']/button")).click();
    //Not we have to wait for loading disappear
    ElementHelper.IsElementInvisible(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
    //Now sample element must be displayed
    assertTrue(ElementHelper.FindElement(driver, By.id("sample")).isDisplayed());

    
    //## Step 3
    //Pick a date
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myInput")));
    ElementHelper.FindElement(driver, By.id("myInput")).sendKeys("''");
    
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ui-datepicker-div")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ui-datepicker-month")));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ui-datepicker-year")));
    Select month = new Select(ElementHelper.FindElement(driver, By.className("ui-datepicker-month")));
    month.selectByValue("9");
    Select year = new Select(ElementHelper.FindElement(driver, By.className("ui-datepicker-year")));
    year.selectByValue("2011");
    //Day 23
    ElementHelper.FindElement(driver, By.xpath("//table[@class='ui-datepicker-calendar']//tbody//tr[5]/td/a")).sendKeys(Keys.ENTER);


    wait.until(ExpectedConditions.alertIsPresent());
    Alert alert = driver.switchTo().alert();
    String confirmationMsg = alert.getText();
    alert.accept();


    /*##########################################################################
      EXPECTED RESULT:
      - The popup alert shall displayed the data picked.
     #########################################################################*/
    assertEquals(confirmationMsg, "You chose: 2011-10-23");
  }

  @After
  public void tearDown() { }
}
