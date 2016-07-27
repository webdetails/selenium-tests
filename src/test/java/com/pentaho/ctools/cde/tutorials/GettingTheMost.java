package com.pentaho.ctools.cde.tutorials;

import static org.testng.Assert.assertEquals;

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

public class GettingTheMost extends BaseTest {
	  // Access to wrapper for webdriver
	  private final ElementHelper elemHelper = new ElementHelper();  
	  //Log instance
	  private final Logger log = LogManager.getLogger( GettingTheMost.class );
	  
	  /**
	   * ############################### Setup ###############################
	   *
	   * Description:
	   * 	Open Getting the Most out of CDA Page
	   */
	  @BeforeClass
	  public void openGettingTheMostPage()
	  {
		  this.log.info( "openGettingTheMostPage" );
		  
		  this.elemHelper.Click(driver, By.xpath("//*[@id='sideMenu']/ul/a[19]/li"));
		  
		  assertEquals("Getting the Most out of CDA", this.elemHelper.WaitForElementPresentGetText(driver,By.xpath("//div[@id= 'mainContent']/h1")));
		  
		  return;
	  }
	  
	  /**
	   * ############################### Test Case 0 ###############################
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
	  public void tc0_PreviewAndEditLinks_Displayed()
	  {
		  this.log.info( "tc0_PreviewAndEditLinks_Displayed" );
		  
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
	   * ############################### Test Case 1 ###############################
	   *
	   * Test Case Name:
	   *    Check Images
	   *    
	   * Test Case Description:
	   * 	Check if printscreens are present. 
	   * 
	   * Test Steps:
	   * 		1. Assert if images are present;
	   * 		2. Check images URL returns HTTP Status OK (200).
	   */
	  @Test
	  public void tc1_CheckImages_Displayed()
	  {
		  this.log.info( "tc1_CheckImages_Displayed" );

		  CdeTutorials.checkImagesPresenceAndHttpStatus("mainContent");
		  
		  return;
	  }

}