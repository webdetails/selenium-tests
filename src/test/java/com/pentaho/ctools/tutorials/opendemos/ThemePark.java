package com.pentaho.ctools.tutorials.opendemos;

import static org.testng.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.gui.web.ctools.tutorials.opendemos.CdeOpenDemos;
import com.pentaho.selenium.BaseTest;

public class ThemePark extends BaseTest {
	// Access to wrapper for webdriver
	private final ElementHelper elemHelper = new ElementHelper();
	// Log instance
	private final Logger log = LogManager.getLogger( ThemePark.class );

	private CdeOpenDemos cde = new CdeOpenDemos();

	private String winHandleBefore;

	private final String[] parks = { "All Parks",
	                                 "WonderIsland",
	                                 "AdventurePark",
	                                 "OceanWorld" };

	private final String[] titles = { "Visits",
	                                  "All Time Visits",
	                                  "Fans Club",
	                                  "Bestsellers",
	                                  "Most Popular" };

	private final String[] chartIds = { "lineChartprotovis",
	                                    "fansclubChartprotovis",
	                                    "bestsellerChartprotovis",
	                                    "mostpopularChartprotovis" };

	@Test
	public void tc0_viewDemo() {
		log.info( "tc0_viewDemo" );

		this.winHandleBefore = cde.viewDemo( "Theme Park" );

		// Wait for the loading icon to disappear
		elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

		assertNotNull( this.elemHelper.WaitForElementPresence( driver, By.xpath( "//title[contains(text(),'Theme Park Dashboard')]" ) ) );
	}

	@Test
	public void tc1_checkTitlesAndGraphicsPresence() {

		for ( String park : parks ) {

			//Locator of the next chart title in the list
			String parkLocator = String.format( "//*[@id='visitsTableTable']//td[contains(text(),'%s')]", park );

			//Assert that the title exists
			assertNotNull( this.elemHelper.WaitForElementPresence( driver, By.xpath( parkLocator ) ) );

			//Wait for the loading icon to disappear
			elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

			//Click on the park, in the parks table
			elemHelper.Click( driver, By.xpath( parkLocator ) );
		}

		for ( String title : titles ) {

			//Locator of the next chart title in the list
			String titleLocator = String.format( "//h2[text()='%s']", title );

			//Assert that the title exists
			assertNotNull( this.elemHelper.WaitForElementPresence( driver, By.xpath( titleLocator ) ) );
		}

		for ( String chartId : chartIds ) {

			//Locator of the next chart id in the list
			String chartLocator = String.format( "//*[@id='%s']", chartId );

			//Assert that the title exists
			assertNotNull( this.elemHelper.WaitForElementPresence( driver, By.xpath( chartLocator ) ) );
		}

	}

	@AfterClass
	public void closeDemo() {
		driver.close();

		driver.switchTo().window( this.winHandleBefore );
	}
}
