package com.pentaho.ctools.cde.tutorials;

import static org.testng.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with CDE Tutorials Menu.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CdeTutorialsMenu extends BaseTest {
	  // Access to wrapper for webdriver
	  private final ElementHelper elemHelper = new ElementHelper();
	  //Log instance
	  private final Logger log = LogManager.getLogger( CdeTutorialsMenu.class );
	  
	  /**
	   * ############################### Test Case 0 ###############################
	   *
	   * Test Case Name:
	   *    Sidebar Links
	   *    
	   * Test Case Description:
	   * 	Check if every link is correctly displayed and ordered  
	   * 
	   * Test Steps:
	   * 		1. Get the ordered link;
	   * 		2. Assert if link is correctly displayed.
	   */
	  @Test
	  public void tc0_SidebarLinks_Ordered()
	  {
		  this.log.info( "tc0_SidebarLinks_Verify" );
		  
		  //Titles of CDE Tutorials pages
		  String[] titles = {	"Welcome",
				  				"The CDE Interface",
				  				"Hello World", 
				  				"A One-Chart Dashboard",
				  				"Adding Charts",
				  				"Reacting to Parameters",
				  				"Making the Charts Talk to Each Other",
				  				"Understanding Layout Engine",
				  				"Adding External Resources",
				  				"CCC: Enhancing Charts",
				  				"CCC: Extension Points",
				  				"CCC: Configuring Datasets",
				  				"CDA: Datasources",
				  				"CDA: The Editor",
				  				"Creating Tables",
				  				"Using Table Add-ins",
				  				"Creating Add-Ins",
				  				"Creating Components",
				  				"Getting the Most out of CDA",
				  				"Where to Go From Here"
				  				};
		  
		  //Assert if the titles of the pages are correct in the sidebar. Get every li item using an index
		  for (int i=1; i<= 20 ; i++)
		  {
			  //titleLocator - xpath locator of the sidebar title
			  String titleLocator = String.format("//*[@id='sideMenu']/ul/a[%d]/li", i);
			  
			  //Step #1
			  //Append the index to the title (e.g. "1. Welcome")
			  String title = String.format("%d. %s", i, titles[i-1]) ;
			  
			  //Step #2
			  //Assert if the titles of the pages are correctly ordered and displayed
			  assertEquals(title, this.elemHelper.WaitForElementPresentGetText(driver,By.xpath(titleLocator)));
		  }
		
		  return;
	  }
	  
	  /**
	   * ############################### Test Case 1 ###############################
	   *
	   * Test Case Name:
	   *    Page Titles
	   *    
	   * Test Case Description:
	   * 	Check if every page content displayed (verifying the titles).
	   * 
	   * Test Steps:
	   * 		1. Get the next page link;
	   * 		2. Click on the link to the page;
	   * 		3. Assert if page title is displayed.
	   */
	  @Test
	  public void tc1_PageTitles_Displayed()
	  {
		  this.log.info( "tc1_PageTitles_Displayed" );
		  
		  //Titles of CDE Tutorials pages
		  String[] titles = {	"Welcome",
				  				"The CDE Interface",
				  				"Hello World", 
				  				"A One-Chart Dashboard",
				  				"Adding Charts",
				  				"Reacting to Parameters",
				  				"Making the Charts Talk to Each Other",
				  				"Understanding Layout Engine",
				  				"Adding External Resources",
				  				"CCC: Enhancing Charts",
				  				"CCC: Extension Points",
				  				"CCC: Configuring Datasets",
				  				"CDA: Datasources",
				  				"CDA: The Editor",
				  				"Creating Tables",
				  				"Using Table Add-ins",
				  				"Creating Add-ins",
				  				"Creating Components",
				  				"Getting the Most out of CDA",
				  				"Where to Go From Here"
				  				};
		  
		  //titleLocator - xpath locator of the title inside the page 
		  String titleLocator = "//div[@id= 'mainContent']/h1";
		  
		  for (int i=1; i<= 20 ; i++)
		  {
			  //Step #1
			  //linkLocator - xpath locator of the sidebar title
			  String linkLocator = String.format("//*[@id='sideMenu']/ul/a[%d]/li", i);
			
			  //Append the index to the title (e.g. "1. Welcome")
			  String title = String.format("%s",titles[i-1]) ;
			  
			  //Step #2
			  //Click on the sidebar link
			  this.elemHelper.Click(driver, By.xpath(linkLocator));
			  
			  //Step #3
			  //Wait for page title to appear and assert if the titles of the pages are correctly displayed
			  assertEquals(title, this.elemHelper.WaitForElementPresentGetText(driver,By.xpath(titleLocator)));
		  }
		  
		  return;
	  }
}



