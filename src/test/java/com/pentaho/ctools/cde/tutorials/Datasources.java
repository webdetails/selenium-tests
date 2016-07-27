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

public class Datasources extends BaseTest {
	  // Access to wrapper for webdriver
	  private final ElementHelper elemHelper = new ElementHelper();  
	  //Log instance
	  private final Logger log = LogManager.getLogger( Datasources.class );
	  
	  /**
	   * ############################### Setup ###############################
	   *
	   * Description:
	   * 	Open CDA: Datasources Page
	   */
	  @BeforeClass
	  public void openDatasourcesPage()
	  {
		  this.log.info( "openDatasourcesPage" );
		  
		  this.elemHelper.Click(driver, By.xpath("//*[@id='sideMenu']/ul/a[13]/li"));
		  
		  assertEquals("CDA: Datasources", this.elemHelper.WaitForElementPresentGetText(driver,By.xpath("//div[@id= 'mainContent']/h1")));
		  
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

}
