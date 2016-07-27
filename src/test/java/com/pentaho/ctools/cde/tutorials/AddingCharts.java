package com.pentaho.ctools.cde.tutorials;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with CDE Tutorials - Adding Charts.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */

public class AddingCharts extends BaseTest {
	  // Access to wrapper for webdriver
	  private final ElementHelper elemHelper = new ElementHelper();
	  //Log instance
	  private final Logger log = LogManager.getLogger( AddingCharts.class );
	  
	  WebElement h1,h2,h3;
	  
	  /**
	   * ############################### Setup ###############################
	   *
	   * Description:
	   * 	Open Adding Charts Page
	   */
	  @BeforeClass
	  public void openAddingChartsPage()
	  {
		  this.log.info( "openAddingChartsPage" );
		  
		  this.elemHelper.Click(driver, By.xpath("//*[@id='sideMenu']/ul/a[5]/li"));
		  
		  assertEquals("Adding Charts", this.elemHelper.WaitForElementPresentGetText(driver,By.xpath("//div[@id= 'mainContent']/h1")));
		  
		  return;
	  }

	  /**
	   * ############################### Test Case 0 ###############################
	   *
	   * Test Case Name:
	   *    Adding Charts Page Sections
	   *    
	   * Test Case Description:
	   * 	Check sections in Adding Charts page.
	   * 
	   * Test Steps:
	   * 		1. Check if headings are present and correctly displayed;
	   */
	  @Test
	  public void tc0_AddingChartsSections_Displayed()
	  {
		  this.log.info( "tc0_AddingChartsSections_Displayed" );
		  
		  //Heading #1
		  h1 = this.elemHelper.WaitForElementPresence(driver,By.xpath("//*[@id='headingOne']/a/h4"));
		  assertNotNull(h1);

		  //Heading #2
		  h2 = this.elemHelper.WaitForElementPresence(driver,By.xpath("//*[@id='headingTwo']/a/h4"));
		  assertNotNull(h2);

		  //Heading #3 
		  h3 = this.elemHelper.WaitForElementPresence(driver,By.xpath("//*[@id='headingThree']/a/h4"));
		  assertNotNull(h3);
		  
 		  return;
	  }
	  
	  /**
	   * ############################### Test Case 1 ###############################
	   *
	   * Test Case Name:
	   *    Check Images
	   *    
	   * Test Case Description:
	   * 	Check if printscreens are present. 
	   * 
	   * Test Steps:
	   * 		1. Expand headers;
	   * 		2. Assert if images are present;
	   * 		3. Check images URL returns HTTP Status OK (200).
	   */
	  @Test
	  public void tc1_CheckImages_Displayed()
	  {
		  this.log.info( "tc1_CheckImages_Displayed" );
		  		  
		  //Step #1 - Expand Headers
		  h1.click();
		  h2.click();
		  h3.click();
		  
		  //Step #2 - Assert all images are present and if they have a valid src (url - 200 OK) 
		  CdeTutorials.checkImagesPresenceAndHttpStatus("collapseOne");
		  CdeTutorials.checkImagesPresenceAndHttpStatus("collapseTwo");
		  CdeTutorials.checkImagesPresenceAndHttpStatus("collapseThree");
		  
		  //Collapse Headers
		  h1.click();
		  h2.click();
		  h3.click();
		  
		  return;  
	  }
	  
	  /**
	   * ############################### Test Case 2 ###############################
	   *
	   * Test Case Name:
	   *    Preview and Edit Links.
	   *    
	   * Test Case Description:
	   * 	Check Preview Dashboard and Edit Dashboard links.
	   * 
	   * Test Steps:
	   * 		1. Assert if links are present;
	   * 		2. Check if pages are loaded. 
	   */
	  @Test
	  public void tc2_PreviewAndEditLinks_Displayed()
	  {
		  this.log.info( "tc2_PreviewAndEditLinks_Displayed" );
		  
		  //Multiple Preview/Edit buttons 
		  List <WebElement> editLinks = driver.findElements(By.xpath("//a[contains(text(),'Edit')]"));
		  List <WebElement> previewLinks = driver.findElements(By.xpath("//a[contains(text(),'Preview')]"));
		  
		  //Iterators for the Edit & Preview links Lists
		  Iterator <WebElement> editLink = editLinks.iterator();
		  Iterator <WebElement> previewLink = previewLinks.iterator();
		  
		  while(editLink.hasNext() && previewLink.hasNext())
		  {
			  //Assert Edit Dashboard link is present, page loads successfuly and gets the name of the CDE Doc
			  String docName = CdeTutorials.getCdeDocName(editLink.next());
			  
			  //Preview links is present and page is loaded 
			  CdeTutorials.clickAndCheckPageLoaded(	previewLink.next(),
													By.xpath("//title"),
													docName);
		  }
		  
		  return;
	  }
}