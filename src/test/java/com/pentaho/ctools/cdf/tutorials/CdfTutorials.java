package com.pentaho.ctools.cdf.tutorials;

import static org.testng.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.selenium.BaseTest;

public class CdfTutorials extends BaseTest{
	// Access to wrapper for webdriver
	private static final ElementHelper elemHelper = new ElementHelper();
	//Log instance
	private static final Logger log = LogManager.getLogger( CdfTutorials.class );
	
	/*
	 * Check if first tab is present and is named "Intro" and the remain tabs are present and named accordingly to the number of the step they represent.
	 */
	public static void checkTabs (int nSteps)
	{
		log.info( "checkTabs" );
		
		//Check if first tab is present and is named "Intro"
		assertEquals(elemHelper.WaitForElementPresentGetText(driver, By.xpath("//*[@id='0']/a")),"Intro");

		//Check if the remain tabs are present and named accordingly to the number of the step they represent
		for(int i = 1 ; i <= nSteps ; i++ )
		{
			String locator = String.format("//*[@id='%d']/a", i);
			String stepName = String.format("Step%d", i);
			
			assertEquals(elemHelper.WaitForElementPresentGetText(driver, By.xpath(locator)),stepName);
			
			elemHelper.Click(driver, By.xpath(locator));
			
			tabsMatchRightPanel(i);
		}
	}
	
	/*
	 * Check if the tabs name match the title on the right panel
	 */
	public static void tabsMatchRightPanel(int stepNumber)
	{
		//xpath locator for the title 
		String titleLocator = String.format("//*[@id='step%d']/h3", stepNumber);
		
		//Title matching the step name/number
		String title = String.format("Step %d", stepNumber);
		
		assertEquals(elemHelper.WaitForElementPresentGetText(driver, By.xpath(titleLocator)),title);
	}
}
