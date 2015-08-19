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
package com.pentaho.ctools.cdf.require;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.BaseTest;
import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;

/**
 * Testing the functionalities related with Template Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class TemplateComponent extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  //Log instance
  private final Logger log = LogManager.getLogger( TemplateComponent.class );

  /**
   * Go to the TemplateComponent web page.
   */
  @BeforeClass
  public void setUpTestCase() {
    //Go to AddinReference
    driver.get( PageUrl.TEMPLATE_COMPONENT_REQUIRE );

    //NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Page Content
   * Description:
   *    This test pretends to validate the contents present in the page.
   * Steps:
   *    1. Check sample title.
   *    2. Check sample description.
   */
  @Test
  public void tc1_PageContent_ContentPresent() {
    this.log.info( "tc1_PageContent_ContentPresent" );

    /*
     * ## Step 1
     */
    // Page title
    assertEquals( "Community Dashboard Framework", driver.getTitle() );
    //Sample Title
    String sampleTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//span[2]" ) );
    assertEquals( "TemplateComponent", sampleTitle );

    /*
     * ## Step 2
     */
    //Sample Description
    String sampleDescTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//h3" ) );
    String sampleDescription = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//p" ) );
    assertEquals( "Description", sampleDescTitle );
    assertEquals( "This is a component that lets you define templates and render them using mustache or underscore, to easily display your data. Like in the Table Component you can create and use addIns to further costumize and enhance the data you are displaying.", sampleDescription );

    /*
     * ## Step 3
     */
    //Options
    String optionsTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//h3[2]" ) );
    String options1 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//dt[7]" ) );
    String options2 = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//dt[8]" ) );
    assertEquals( "Options", optionsTitle );
    assertEquals( "refreshPeriod", options1 );
    assertEquals( "executeAtStart", options2 );

    /*
     * ## Step 4
     */
    //Samples
    String samplesTitle = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//h3[4]" ) );
    assertEquals( "Sample", samplesTitle );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Reload Sample
   * Description:
   *    Reload the sample (not refresh page).
   * Steps:
   *    1. Click in Code and then click in button 'Try me'.
   */
  @Test
  public void tc2_ReloadSample_SampleReadyToUse() {
    // ## Step 1
    // Render again the sample
    this.elemHelper.FindElement( driver, By.xpath( "//div[@id='example']/ul/li[2]/a" ) ).click();
    this.elemHelper.FindElement( driver, By.xpath( "//div[@id='code']/button" ) ).click();

    // NOTE - we have to wait for loading disappear
    this.elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    // Now sample element must be displayed
    assertTrue( this.elemHelper.FindElement( driver, By.id( "sample" ) ).isDisplayed() );

    //Check the number of divs with id 'SampleObject'
    //Hence, we guarantee when click Try Me the previous div is replaced
    int nSampleObject = driver.findElements( By.id( "sampleObject" ) ).size();
    assertEquals( 1, nSampleObject );
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Assert all elements are displayed correctly
   * Description:
   *    We pretend validate application of Template Component.
   * Steps:
   *    1. Assert all six elements have correct text
   */
  @Test
  public void tc3_TemplateElements_DisplayedCorrectly() {
    // ## Step 1
    //First element
    WebElement firstElement = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='sampleObject']/div/div[@class='templateRow'][1]" ) );
    assertNotNull( firstElement );
    String title = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObject']/div/div[@class='templateRow'][1]/div[@class='title']" ) );
    String desc = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObject']/div/div[@class='templateRow'][1]/div[@class='desc']" ) );
    String value = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObject']/div/div[@class='templateRow'][1]/div[@class='value']" ) );
    assertEquals( "ESC", title );
    assertEquals( "Euro+ Shopping Channel", desc );
    assertEquals( "912.3k", value );

    //Second element
    WebElement secondElement = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='sampleObject']/div/div[@class='templateRow'][2]" ) );
    assertNotNull( secondElement );
    title = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObject']/div/div[@class='templateRow'][2]/div[@class='title']" ) );
    desc = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObject']/div/div[@class='templateRow'][2]/div[@class='desc']" ) );
    value = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObject']/div/div[@class='templateRow'][2]/div[@class='value']" ) );
    assertEquals( "MGD", title );
    assertEquals( "Mini Gifts Distributors Ltd.", desc );
    assertEquals( "654.9k", value );

    //Third element
    WebElement thirdElement = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='sampleObject']/div/div[@class='templateRow'][3]" ) );
    assertNotNull( thirdElement );
    title = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObject']/div/div[@class='templateRow'][3]/div[@class='title']" ) );
    desc = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObject']/div/div[@class='templateRow'][3]/div[@class='desc']" ) );
    value = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObject']/div/div[@class='templateRow'][3]/div[@class='value']" ) );
    assertEquals( "ACC", title );
    assertEquals( "Australian Collectors, Co.", desc );
    assertEquals( "201.0k", value );

    //Fourth element
    WebElement fourthElement = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='sampleObject']/div/div[@class='templateRow'][4]" ) );
    assertNotNull( fourthElement );
    title = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObject']/div/div[@class='templateRow'][4]/div[@class='title']" ) );
    desc = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObject']/div/div[@class='templateRow'][4]/div[@class='desc']" ) );
    value = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObject']/div/div[@class='templateRow'][4]/div[@class='value']" ) );
    assertEquals( "MMI", title );
    assertEquals( "Muscle Machine Inc", desc );
    assertEquals( "197.7k", value );

    //Third element
    WebElement fifthElement = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='sampleObject']/div/div[@class='templateRow'][5]" ) );
    assertNotNull( fifthElement );
    title = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObject']/div/div[@class='templateRow'][5]/div[@class='title']" ) );
    desc = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObject']/div/div[@class='templateRow'][5]/div[@class='desc']" ) );
    value = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObject']/div/div[@class='templateRow'][5]/div[@class='value']" ) );
    assertEquals( "LRG", title );
    assertEquals( "La Rochelle Gifts", desc );
    assertEquals( "180.1k", value );

    //Third element
    WebElement sixthElement = this.elemHelper.FindElement( driver, By.xpath( "//div[@id='sampleObject']/div/div[@class='templateRow'][6]" ) );
    assertNotNull( sixthElement );
    title = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObject']/div/div[@class='templateRow'][6]/div[@class='title']" ) );
    desc = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObject']/div/div[@class='templateRow'][6]/div[@class='desc']" ) );
    value = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id='sampleObject']/div/div[@class='templateRow'][6]/div[@class='value']" ) );
    assertEquals( "DUS", title );
    assertEquals( "Down Under Souveniers, Inc", desc );
    assertEquals( "174.1k", value );
  }
}
