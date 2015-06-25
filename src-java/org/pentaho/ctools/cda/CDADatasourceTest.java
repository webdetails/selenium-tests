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
package org.pentaho.ctools.cda;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.HttpUtils;
import org.pentaho.ctools.utils.PageUrl;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * Testing the functionalities related with CDA Cache Manager.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CDADatasourceTest {

  // Instance of the driver (browser emulator)
  private WebDriver driver = CToolsTestSuite.getDriver();
  // Log instance
  private final Logger log = LogManager.getLogger( CDADatasourceTest.class );

  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( this.driver );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Datasource Test
   * Description:
   *    The test case pretends to validate the return result of each query.
   * Steps:
   *    1. Check the result displayed
   */
  @Test( timeout = 180000 )
  public void tc1_DatasourceTest_ResultOK() {
    this.log.info( "tc1_DatasourceTest_ResultOK" );

    /*
     * Step 0 - Go to web page.
     */
    //Go to the CDA Cache Manager web page.
    this.driver.get( PageUrl.DATASOURCE_TEST );
    //wait for element to be visible
    ElementHelper.WaitForElementVisibility( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[15]/td" ) );
    // NOTE - we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    //Test Link CDA Documentation
    String urlCdaDoc = ElementHelper.FindElement( this.driver, By.linkText( "CDA Documentation" ) ).getAttribute( "href" );
    assertEquals( HttpStatus.SC_OK, HttpUtils.GetHttpStatus( urlCdaDoc ) );
    //Blog Post
    String urlBlogPost = ElementHelper.FindElement( this.driver, By.linkText( "Blog Post" ) ).getAttribute( "href" );
    assertEquals( HttpStatus.SC_OK, HttpUtils.GetHttpStatus( urlBlogPost ) );
    //PDFBrochure
    String urlPdfBrochure = ElementHelper.FindElement( this.driver, By.linkText( "PDF Brochure" ) ).getAttribute( "href" );
    assertEquals( HttpStatus.SC_OK, HttpUtils.GetHttpStatus( urlPdfBrochure ) );

    //sqk,jdbc
    String accessMethod1 = ElementHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[2]/td" ) );
    String sample1 = ElementHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[2]/td[3]/a" ) );
    WebElement element1 = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[2]/td[4]/img[@src='resources/ok.png']" ) );
    //sql.jndi
    String accessMethod2 = ElementHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[3]/td" ) );
    String sample2 = ElementHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[3]/td[3]/a" ) );
    WebElement element2 = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[3]/td[4]/img[@src='resources/ok.png']" ) );
    //sql.stringarray.jndi
    String accessMethod3 = ElementHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[4]/td" ) );
    String sample3 = ElementHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[4]/td[3]/a" ) );
    WebElement element3 = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[4]/td[4]/img[@src='resources/ok.png']" ) );
    //mondrian.jdbc
    String accessMethod4 = ElementHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[5]/td" ) );
    String sample4 = ElementHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[5]/td[3]/a" ) );
    WebElement element4 = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[5]/td[4]/img[@src='resources/ok.png']" ) );
    //mondrian.jndi
    String accessMethod5 = ElementHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[6]/td" ) );
    String sample5 = ElementHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[6]/td[3]/a" ) );
    WebElement element5 = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[6]/td[4]/img[@src='resources/ok.png']" ) );
    // mondrian.jdbc (denormalized)
    String accessMethod6 = ElementHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[7]/td" ) );
    String sample6 = ElementHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[7]/td[3]/a" ) );
    WebElement element6 = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[7]/td[4]/img[@src='resources/ok.png']" ) );
    //mondrian.jndi (denormalized)
    String accessMethod7 = ElementHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[8]/td" ) );
    String sample7 = ElementHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[8]/td[3]/a" ) );
    WebElement element7 = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[8]/td[4]/img[@src='resources/ok.png']" ) );
    //olap4j
    String accessMethod8 = ElementHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[9]/td" ) );
    String sample8 = ElementHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[9]/td[3]/a" ) );
    WebElement element8 = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[9]/td[4]/img[@src='resources/ok.png']" ) );
    //kettle
    String accessMethod9 = ElementHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[10]/td" ) );
    String sample9 = ElementHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[10]/td[3]/a" ) );
    WebElement element9 = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[10]/td[4]/img[@src='resources/ok.png']" ) );
    //metadata
    String accessMethod10 = ElementHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[11]/td" ) );
    String sample10 = ElementHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[11]/td[3]/a" ) );
    WebElement element10 = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[11]/td[4]/img[@src='resources/ok.png']" ) );
    //scripting
    String accessMethod11 = ElementHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[12]/td" ) );
    String sample11 = ElementHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[12]/td[3]/a" ) );
    WebElement element11 = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[12]/td[4]/img[@src='resources/ok.png']" ) );
    //scripting (json object)
    String accessMethod12 = ElementHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[13]/td" ) );
    String sample12 = ElementHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[13]/td[3]/a" ) );
    WebElement element12 = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[13]/td[4]/img[@src='resources/ok.png']" ) );
    //Xpath
    String accessMethod13 = ElementHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[14]/td" ) );
    String sample13 = ElementHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[14]/td[3]/a" ) );
    WebElement element13 = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[14]/td[4]/img[@src='resources/ok.png']" ) );
    //Compound join
    String accessMethod14 = ElementHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[15]/td" ) );
    String sample14 = ElementHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[15]/td[3]/a" ) );
    WebElement element14 = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[15]/td[4]/img[@src='resources/ok.png']" ) );
    //Compound Union
    String accessMethod15 = ElementHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[16]/td" ) );
    String sample15 = ElementHelper.WaitForElementPresentGetText( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[16]/td[3]/a" ) );
    WebElement element15 = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//table[@id='testTable']/tbody/tr[16]/td[4]/img[@src='resources/ok.png']" ) );

    assertEquals( "sql.jdbc", accessMethod1 );
    assertEquals( "cdafiles/sql-jdbc.cda", sample1 );
    assertNotNull( element1 );
    assertEquals( "sql.jndi", accessMethod2 );
    assertEquals( "cdafiles/sql-jndi.cda", sample2 );
    assertNotNull( element2 );
    assertEquals( "sql.stringarray.jndi", accessMethod3 );
    assertEquals( "cdafiles/sql-stringArray-jndi.cda", sample3 );
    assertNotNull( element3 );
    assertEquals( "mondrian.jdbc", accessMethod4 );
    assertEquals( "cdafiles/mondrian-jdbc.cda", sample4 );
    assertNotNull( element4 );
    assertEquals( "mondrian.jndi", accessMethod5 );
    assertEquals( "cdafiles/mondrian-jndi.cda", sample5 );
    assertNotNull( element5 );
    assertEquals( "mondrian.jdbc (denormalized)", accessMethod6 );
    assertEquals( "cdafiles/denormalized-mondrian-jndi.cda", sample6 );
    assertNotNull( element6 );
    assertEquals( "mondrian.jndi (denormalized)", accessMethod7 );
    assertEquals( "cdafiles/denormalized-mondrian-jndi.cda", sample7 );
    assertNotNull( element7 );
    assertEquals( "olap4j", accessMethod8 );
    assertEquals( "cdafiles/olap4j.cda", sample8 );
    assertNotNull( element8 );
    assertEquals( "kettle", accessMethod9 );
    assertEquals( "cdafiles/kettle.cda", sample9 );
    assertNotNull( element9 );
    assertEquals( "metadata", accessMethod10 );
    assertEquals( "cdafiles/metadata.cda", sample10 );
    assertNotNull( element10 );
    assertEquals( "scripting", accessMethod11 );
    assertEquals( "cdafiles/scripting.cda", sample11 );
    assertNotNull( element11 );
    assertEquals( "scripting (json object)", accessMethod12 );
    assertEquals( "cdafiles/json-scripting.cda", sample12 );
    assertNotNull( element12 );
    assertEquals( "xpath", accessMethod13 );
    assertEquals( "cdafiles/xpath.cda", sample13 );
    assertNotNull( element13 );
    assertEquals( "Compound join", accessMethod14 );
    assertEquals( "cdafiles/compoundJoin.cda", sample14 );
    assertNotNull( element14 );
    assertEquals( "Compound Union", accessMethod15 );
    assertEquals( "cdafiles/compoundUnion.cda", sample15 );
    assertNotNull( element15 );
  }
}
