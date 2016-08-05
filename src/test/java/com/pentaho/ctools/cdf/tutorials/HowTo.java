package com.pentaho.ctools.cdf.tutorials;

import static org.testng.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

public class HowTo extends BaseTest {
	// Access to wrapper for webdriver
	private static final ElementHelper elemHelper = new ElementHelper();
	//Log instance
	private static final Logger log = LogManager.getLogger( HowTo.class );

	/**
	* ############################### Test Case 0 ###############################
	*
	* Description:
	* 	Opens How To tutorial and check if the page was loaded.
	*/
	@Test
	public void tc0_openHowTo() {
		log.info( "openHowTo" );
		
		//Open CDE Tutorials Welcome page 
		elemHelper.Get(driver, PageUrl.CDF_TUTORIAL_HOW_TO);
		
		//Test if page was loaded
		assertEquals(elemHelper.WaitForElementPresentGetText(driver, By.xpath("//*[@id='title']/h1")),"How to Start");
					  
		return;
	}
	
	/**
	   * ############################### Test Case 1 ###############################
	   *
	   * Test Case Name:
	   *    Check Tabs Names
	   *    
	   * Test Case Description:
	   * 	Calls a function that check that the first tab is named "Intro" and the remaining tabs are named accordingly to the step number (e.g. Step1...StepX).
	   * 	The function will call another function that will check if every tab has a right panel with a title similar to the tab name.
	   * Test Steps:
	   * 		1. Call the function checkTabs with the quantity of steps that the sample has.
	   */
	@Test
	public void tc1_checkTabsNames()
	{
		log.info( "checkTabsNames" );
		
		CdfTutorials.checkTabs(5);
	}
	
	/**
	   * ############################### Test Case 2 ###############################
	   *
	   * Test Case Name:
	   *    Check Webdetails Logo
	   *    
	   * Test Case Description:
	   * 	Calls a function that clicks on the Webdetails logo at the bottom of the page and check if the page was loaded.
	   * Test Steps:
	   * 		1. Call the function checkWebdetailsLogoLink.
	   */
	@Test
	public void tc2_checkWebdetailsLogo()
	{
		log.info( "checkWebdetailsLogo" );
		
		CdfTutorials.checkWebdetailsLogoLink();
	}
	
	/**
	   * ############################### Test Case 3 ###############################
	   *
	   * Test Case Name:
	   *    Check Tooltips 
	   *    
	   * Test Case Description:
	   * 	Calls a function that checks every tooltips in every tab of the sample, clicks it and make sure the code file that pops up isn't empty.
	   * Test Steps:
	   * 		1. Call the function checkTooltips.
	   */
	@Test
	public void tc3_checkTooltips()
	{
		CdfTutorials.checkTooltips(5);
	}
}
