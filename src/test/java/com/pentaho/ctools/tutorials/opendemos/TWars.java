package com.pentaho.ctools.tutorials.opendemos;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.gui.web.ctools.tutorials.opendemos.CdeOpenDemos;
import com.pentaho.selenium.BaseTest;

public class TWars extends BaseTest {
  // Access to wrapper for webdriver
  private static final ElementHelper elemHelper = new ElementHelper();
  //Log instance
  private static final Logger log = LogManager.getLogger( TWars.class );

  private CdeOpenDemos cde = new CdeOpenDemos();
  
  private String winHandleBefore;

  @Test
  public void tc0_viewDemo() {
    log.info( "tc0_viewDemo" );

    this.winHandleBefore = cde.viewDemo( "T-Wars" );
    
    assertTrue( ( elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//*[@id='logoSubTitle']/p" ) ).contains( "Welcome "+pentahoBaServerUsername+"." ) ) );
  }

  @Test
  public void tc1_checkTitlesAndGraphicsPresence() {
    //First Title
    assertEquals( elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//*[@id='broughtdownlabel']/p" ) ), "T-Fighters Brought Down" );

    //First Grphic
    assertNotNull( elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "broughtdownprotovis" ) ) );

    //Second Title
    assertEquals( elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//*[@id='clumsylabel']/p" ) ), "Why Are T-Fighters So Clumsy? Fan's Opinion Through The Years" );

    //Second Grphic
    assertNotNull( elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "clumsyprotovis" ) ) );

    //Third Title
    assertEquals( elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//*[@id='tfighterslabel']/p" ) ), "T-Fighters Brought Down By Character" );

    //Third Grphic
    assertNotNull( elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "defender_vs_avengerprotovis" ) ) );
  }

  @AfterClass
  public void closeDemo() {
    driver.close();

    driver.switchTo().window( this.winHandleBefore );
  }
}
