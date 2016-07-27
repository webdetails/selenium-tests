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
 * Testing the functionalities related with CDE Tutorials - CCC: Extension Points.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */

public class ExtensionPoints extends BaseTest {
	  // Access to wrapper for webdriver
	  private final ElementHelper elemHelper = new ElementHelper();
	  //Log instance
	  private final Logger log = LogManager.getLogger( ExtensionPoints.class );
	  
	  WebElement h1,h2;
	  
	  /**
	   * ############################### Setup ###############################
	   *
	   * Description:
	   * 	Open Extension Points Page
	   */
	  @BeforeClass
	  public void openExtensionPointsPage()
	  {
		  this.log.info( "openExtensionPointsPage" );
		  
		  this.elemHelper.Click(driver, By.xpath("//*[@id='sideMenu']/ul/a[11]/li"));
		  
		  assertEquals("CCC: Extension Points", this.elemHelper.WaitForElementPresentGetText(driver,By.xpath("//div[@id= 'mainContent']/h1")));
		  
		  return;
	  }

	  /**
	   * ############################### Test Case 0 ###############################
	   *
	   * Test Case Name:
	   *    Extension Points Page Sections
	   *    
	   * Test Case Description:
	   * 	Check sections in Extension Points page.
	   * 
	   * Test Steps:
	   * 		1. Check if headings are present and correctly displayed;
	   */
	  @Test
	  public void tc0_ExtensionPointsSections_Displayed()
	  {
		  this.log.info( "tc0_ExtensionPointsSections_Displayed" );
		  
		  //Heading #1
		  h1 = this.elemHelper.WaitForElementPresence(driver,By.xpath("//*[@id='headingTwo']/a/h4"));
		  assertNotNull(h1);

		  //Heading #2
		  h2 = this.elemHelper.WaitForElementPresence(driver,By.xpath("//*[@id='headingThree']/a/h4"));
		  assertNotNull(h2);
		  
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
		  
		  //Step #2 - Assert all images are present and if they have a valid src (url - 200 OK) 
		  CdeTutorials.checkImagesPresenceAndHttpStatus("collapseOne");
		  CdeTutorials.checkImagesPresenceAndHttpStatus("collapseTwo");
		  
		  //Collapse Headers
		  h1.click();
		  h2.click();
		  
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
	  
	  /**
	   * ############################### Test Case 3 ###############################
	   *
	   * Test Case Name:
	   *    Text links
	   *    
	   * Test Case Description:
	   * 	Click on all links in the text and check the pages are loaded. 
	   * 
	   * Test Steps:
	   * 	Steps:
	   * 		1. Check Protovis link;
	   * 		2. Check CCC link;
	   */
	  @Test
	  public void tc3_TextLinks_Displayed()
	  {
		  this.log.info( "tc3_TextLinks_Displayed" );
		  
		  //Expand headers
		  h1.click();
		  h2.click();
		  
		  List <WebElement> links = driver.findElements(By.xpath("//a[contains(text(),'Label')]"));
		  
		  //Step #1
		  CdeTutorials.clickAndCheckPageLoaded(	By.xpath("//p/a[contains(text(),'Protovis')]"),
				  								By.xpath("//title"),
				  								"Protovis");
		  //Step #2
		  CdeTutorials.clickAndCheckPageLoaded(	By.xpath("//p/a[contains(text(),'CCC')]"),
												By.xpath("//h1[contains(text(),'CCC')]"),
												"CCC");
		  //Step #3
		  CdeTutorials.clickAndCheckPageLoaded(	By.xpath("//a[contains(text(),'radians')]"),
												By.xpath("//title"),
												"Radian - Wikipedia, the free encyclopedia");
		  
		  for (WebElement link : links)
		  {
			  CdeTutorials.clickAndCheckPageLoaded(	link,
													By.xpath("//title"),
													"Protovis - Labels");
		  }
		  
		  //Collapse headers
		  h1.click();
		  h2.click();
		  
		  return;
	  }
}