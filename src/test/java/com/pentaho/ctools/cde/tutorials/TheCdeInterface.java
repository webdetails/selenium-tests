package com.pentaho.ctools.cde.tutorials;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with CDE Tutorials - The CDE Inteface.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */

public class TheCdeInterface extends BaseTest {
	  // Access to wrapper for webdriver
	  private final ElementHelper elemHelper = new ElementHelper();
	  //Log instance
	  private final Logger log = LogManager.getLogger( TheCdeInterface.class );
	  
	  WebElement h1, h2, h3 ;
	  
	  /**
	   * ############################### Setup ###############################
	   *
	   * Description:
	   * 	Open The CDE Interface Page
	   */
	  @BeforeClass
	  public void openTheCdeInterfacePage()
	  {
		  this.log.info( "openTheCdeInterfacePage" );
		  
		  this.elemHelper.Click(driver, By.xpath("//*[@id='sideMenu']/ul/a[2]/li"));
		  
		  assertEquals("The CDE Interface", this.elemHelper.WaitForElementPresentGetText(driver,By.xpath("//div[@id= 'mainContent']/h1")));
		  
		  return;
	  }

	  /**
	   * ############################### Test Case 0 ###############################
	   *
	   * Test Case Name:
	   *    Interface Page Sections
	   *    
	   * Test Case Description:
	   * 	Check sections in The CDE Interface page.
	   * 
	   * Test Steps:
	   * 		1. Check if headings are present and correctly displayed;
	   */
	  @Test
	  public void tc0_InterfacePageSections_Displayed()
	  {
		  this.log.info( "tc0_InterfacePageSections_Displayed" );
		  
		  
		  //Heading #1 - The Layout Perspective
		  h1 = this.elemHelper.WaitForElementPresence(driver,By.xpath("//*[@id='headingOne']/a/h4"));
		  assertNotNull(h1);
		  
		  //Heading #2 - The Components Perspective
		  h2 = this.elemHelper.WaitForElementPresence(driver,By.xpath("//*[@id='headingTwo']/a/h4"));
		  assertNotNull(h2);

		  //Heading #3 - The Datasources Perspective
		  h3 = this.elemHelper.WaitForElementPresence(driver,By.xpath("//*[@id='headingThree']/a/h4"));
		  assertNotNull(h3);

		  //Layout, Components and Datasources
		  assertNotNull(this.elemHelper.WaitForElementPresence(driver,By.xpath("//*[@id='mainContent']/h2")));
		  
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
		  
		  //Step #2 - Assert images are present
		  CdeTutorials.checkImagesPresenceAndHttpStatus("mainContent");
		  CdeTutorials.checkImagesPresenceAndHttpStatus("collapseOne");
		  CdeTutorials.checkImagesPresenceAndHttpStatus("collapseTwo");
		  CdeTutorials.checkImagesPresenceAndHttpStatus("collapseThree");

		  //Collapse Headers
		  h3.click();
		  h1.click();
		  h2.click();
		  
		  return;  
	  }
	  
	  /**
	   * ############################### Test Case 2 ###############################
	   *
	   * Test Case Name:
	   *    Check CDA Link
	   *    
	   * Test Case Description:
	   * 	Check if link is present and page is loaded. 
	   * 
	   * Test Steps:
	   * 		1. Click CDA link;
	   * 		2. Assert page is loaded.
	   */
	  @Test
	  public void tc2_CheckCdaLink_Displayed()
	  {
		  this.log.info( "tc2_CheckCdaLink_Displayed" );
		  
		  //Expand Datasources header
		  h3.click();
		  
		  //Step #1 & #2
		  CdeTutorials.clickAndCheckPageLoaded(	By.xpath("//a[contains(text(),'Community Data Access')]"),
										By.xpath("//h1[contains(text(),'CDA')]"),
										"CDA");	  
		  
		  return;  
	  }

}
