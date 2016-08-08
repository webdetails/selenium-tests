package com.pentaho.ctools.cde.tutorials;

import static org.testng.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.selenium.BaseTest;

public class WhereToGo extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  //Log instance
  private final Logger log = LogManager.getLogger( WhereToGo.class );

  //Headings of the page
  WebElement[] headings = new WebElement[5];

  /**
   * ############################### Setup ###############################
   *
   * Description:
   * 	Open Where To Go From Here Page
   */
  @BeforeClass
  public void openWhereToGoPage() {
    this.log.info( "openWhereToGotPage" );

    this.elemHelper.Click( driver, By.xpath( "//*[@id='sideMenu']/ul/a[20]/li" ) );

    assertEquals( "Where to Go From Here", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id= 'mainContent']/h1" ) ) );

    this.headings[0] = this.elemHelper.FindElement( driver, By.xpath( "//*[@id='headingOne']/a" ) );
    this.headings[1] = this.elemHelper.FindElement( driver, By.xpath( "//*[@id='headingTwo']/a" ) );
    this.headings[2] = this.elemHelper.FindElement( driver, By.xpath( "//*[@id='headingThree']/a" ) );
    this.headings[3] = this.elemHelper.FindElement( driver, By.xpath( "//*[@id='headingFour']/a" ) );
    this.headings[4] = this.elemHelper.FindElement( driver, By.xpath( "//*[@id='headingFive']/a" ) );

    return;
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    CTools link list
   *    
   * Test Case Description:
   * 	Click on all CTools links in CTools list and check the pages are loaded. 
   * 
   * Test Steps:
   * 	Steps:
   * 		1. Check C-tools links;
   * 		2. Check if C-tools pages are loaded.
   */
  @Test
  public void tc1_CtoolsLinkList_Displayed() {
    this.log.info( "tc1_WelcomePageSecions_Displayed" );

    String[] ctools = { "CDF",
        "CDE",
        "CDA",
        "CCC",
        "CGG",
        "CDV",
        "CDC",
        "CST",
        "CBF" };

    //Step #1
    //Expand 1st header
    this.headings[0].click();

    //Check link and if pages are loaded for every C-Tool
    for ( int i = 0; i < ctools.length; i++ ) {
      String ctoolLocator = String.format( "//ul[@class='ctools-suite']/li[%d]/a", ( i + 1 ) );

      //Step #2
      CdeTutorials.clickAndCheckPageLoaded( By.xpath( ctoolLocator ), By.xpath( "//h1[contains(@class,'ctool-name')]" ), ctools[i] );
    }

    //Collapse 1st header
    this.headings[0].click();

    return;
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Text links
   *    
   * Test Case Description:
   * 	Click on all links in the text and check the pages are loaded. 
   * 
   * Test Steps:
   * 	Steps:
   * 		1. Expand headers;
   * 		2. Check all links;
   * 		3. Collapse headers.
   */
  @Test
  public void tc2_OtherLinks_Displayed() {
    this.log.info( "tc2_TextLinks_Displayed" );

    //Expand headers
    for ( WebElement h : this.headings )
      h.click();

    CdeTutorials.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'HTML')]" ), By.xpath( "//title" ), "HTML Tutorial" );

    CdeTutorials.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'CSS')]" ), By.xpath( "//title" ), "CSS Tutorial" );

    CdeTutorials.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'jQuery')]" ), By.xpath( "//title" ), "jQuery" );

    CdeTutorials.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'JavaScript (JS)')]" ), By.xpath( "//title" ), "JavaScript and HTML DOM Reference" );

    CdeTutorials.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'Bootstrap')]" ), By.xpath( "//title" ), "Bootstrap · The world's most popular mobile-first and responsive front-end framework." );

    CdeTutorials.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'Protovis')]" ), By.xpath( "//title" ), "Protovis" );

    CdeTutorials.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'Datatables')]" ), By.xpath( "//title" ), "DataTables | Table plug-in for jQuery" );

    CdeTutorials.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'Sparklines')]" ), By.xpath( "//title" ), "jQuery Sparklines" );

    CdeTutorials.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'Chosen')]" ), By.xpath( "//title" ), "Chosen: A jQuery Plugin by Harvest to Tame Unwieldy Select Boxes" );

    CdeTutorials.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'Select2')]" ), By.xpath( "//title" ), "Select2 - The jQuery replacement for select boxes" );

    CdeTutorials.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'Facebook page')]" ), By.xpath( "//title" ), "Webdetails" );

    CdeTutorials.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'Pedro Alves Blog')]" ), By.xpath( "//title" ), "Pedro Alves on Business Intelligence" );

    CdeTutorials.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'Community Tools - CTools')]" ), By.xpath( "//title" ), "Community Tools - CTools" );

    CdeTutorials.clickAndCheckPageLoaded( By.xpath( "//a[contains(text(),'here')]" ), By.xpath( "//title" ), "Webdetails • Business Analytics" );

    //Collapse headers
    for ( WebElement h : this.headings )
      h.click();

    return;
  }
}
