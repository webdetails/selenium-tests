package com.pentaho.ctools.cde.tutorials;

import static org.testng.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.selenium.BaseTest;

public class CreatingAddins extends BaseTest {
	  // Access to wrapper for webdriver
	  private final ElementHelper elemHelper = new ElementHelper();  
	  //Log instance
	  private final Logger log = LogManager.getLogger( CreatingAddins.class );
	  
	  /**
	   * ############################### Setup ###############################
	   *
	   * Description:
	   * 	Open Creating Add-ins Page
	   */
	  @BeforeClass
	  public void openCreatingAddinsPage()
	  {
		  this.log.info( "openCreatingAddinsPage" );
		  
		  this.elemHelper.Click(driver, By.xpath("//*[@id='sideMenu']/ul/a[17]/li"));
		  
		  assertEquals("Creating Add-ins", this.elemHelper.WaitForElementPresentGetText(driver,By.xpath("//div[@id= 'mainContent']/h1")));
		  
		  return;
	  }
	  
	  /**
	   * ############################### Test Case 0 ###############################
	   *
	   * Test Case Name:
	   *    Text links
	   *    
	   * Test Case Description:
	   * 	Click on all links in the text and check the pages are loaded. 
	   * 
	   * Test Steps:
	   * 	Steps:
	   * 		1. Check Sparklines link.
	   */
	  @Test
	  public void tc0_TextLinks_Displayed()
	  {
		  this.log.info( "tc0_TextLinks_Displayed" );
		  
		  //Step #1
		  CdeTutorials.clickAndCheckPageLoaded(	By.xpath("//p/a[contains(text(),'sparkline using the jquery plugin')]"),
				  								By.xpath("//title"),
				  								"jQuery Sparklines");
		  
		  return;
	  }

}
