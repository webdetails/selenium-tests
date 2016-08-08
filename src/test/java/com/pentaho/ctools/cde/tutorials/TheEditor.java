package com.pentaho.ctools.cde.tutorials;

import static org.testng.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.selenium.BaseTest;

public class TheEditor extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  //Log instance
  private final Logger log = LogManager.getLogger( TheEditor.class );

  /**
   * ############################### Setup ###############################
   *
   * Description:
   * 	Open CDA: The Editor Page
   */
  @BeforeClass
  public void openDatasourcesPage() {
    this.log.info( "openTheEditorPage" );

    this.elemHelper.Click( driver, By.xpath( "//*[@id='sideMenu']/ul/a[14]/li" ) );

    assertEquals( "CDA: The Editor", this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//div[@id= 'mainContent']/h1" ) ) );

    return;
  }

  /**
   * ############################### Test Case 0 ###############################
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
  public void tc0_CheckImages_Displayed() {
    this.log.info( "tc0_CheckImages_Displayed" );

    //Step #2 - Assert all images are present and if they have a valid src (url - 200 OK) 
    CdeTutorials.checkImagesPresenceAndHttpStatus( "mainContent" );

    return;
  }
}
