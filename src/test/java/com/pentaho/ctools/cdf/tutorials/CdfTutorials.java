package com.pentaho.ctools.cdf.tutorials;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.selenium.BaseTest;

public class CdfTutorials extends BaseTest{
	// Access to wrapper for webdriver
	private static final ElementHelper elemHelper = new ElementHelper();
	//Log instance
	private static final Logger log = LogManager.getLogger( CdfTutorials.class );
	
	/**
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
	
	/**
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
	
	/**
	 * Check if the logo that links to webdetails website at the bottom of the page is working correctly. 
	 */
	public static void checkWebdetailsLogoLink ()
	{
		//Click on the Webdetails logo
		elemHelper.Click(driver, By.xpath("//*[@id='footer']/div[1]/a"));
		
		//Check if page was loaded
		assertNotNull(elemHelper.WaitForElementPresence(driver, By.xpath("//header//a[@href='http://www.webdetails.pt']")));
		
		//Back
		driver.navigate().back();
	}
	
	/**
	 * Check every tooltips in the sample, click it and make sure the code file that pops up isn't empty.
	 * 
	 * Steps: 
	 * 		1.For every tab:
	 * 			1.1 Open the tab
	 * 			1.2 Find all the tooltips in the sample (icons with a "?" image in the graphics).
	 * 			1.3 If no tooltips were found:
	 * 				1.3.1 Do nothing.
	 * 			1.4 If tooltips were found, for each of them:
	 * 				1.4.1 Click the tooltip. Content of the code file for the sample pops up.
	 * 				1.4.2 Check if code file it's not empty.
	 * 				1.4.3 Close the pop up.
	 */
	public static void checkTooltips (int nSteps)
	{
		log.info( "checkTooltips" );
		
		//Check if first tab is present and is named "Intro"
		assertEquals(elemHelper.WaitForElementPresentGetText(driver, By.xpath("//*[@id='0']/a")),"Intro");

		//For every tab
		for(int i = 1 ; i <= nSteps ; i++ )
		{
			String locator = String.format("//*[@id='%d']/a", i);
			String stepName = String.format("Step%d", i);
			
			assertEquals(elemHelper.WaitForElementPresentGetText(driver, By.xpath(locator)),stepName);
			
			//Open the tab
			elemHelper.Click(driver, By.xpath(locator));
			
			//Find all the tooltips in the sample (icons with a "?" image in the graphics)
			List<WebElement> tooltips = driver.findElements(By.xpath("//img[@class='samplesTooltip']"));
			
			//Do nothing if no tooltips were found
			if(tooltips == null)
				return;
			else
				//If tooltips were found, for each of them 
				for(WebElement tooltip : tooltips)
				{
					//Click the tooltip
					tooltip.click();
					
					//Check if code file it's not empty
					assertNotEquals(elemHelper.WaitForElementPresentGetText(driver, By.xpath("//textarea")),"");
					
					//Close pop up
					elemHelper.Click(driver, By.xpath("//button[contains(text(),'Close')]"));
					
					/*******TO-DO: wait for element to disappear******/
				}
		}
	}
}
