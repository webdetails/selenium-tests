package com.pentaho.ctools.tutorials.opendemos;

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

public class NumberOne extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( NumberOne.class );

  private CdeOpenDemos cde = new CdeOpenDemos();

  private String winHandleBefore;

  private final String[] titles = { "Total GPs",
                                    "Race Wins",
                                    "Podiums",
                                    "Poles",
                                    "Fastest Laps",
                                    "Championships",
                                    "Season Review",
                                    "Summary Table" };

  @Test
  public void tc0_viewDemo() {
    log.info( "tc0_viewDemo" );

    this.winHandleBefore = cde.viewDemo( "Number One" );

    // Wait for the loading icon to disappear
    elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    assertTrue( ( this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//*[@id='titleColumn']/span" ) ).contains( "Number One." ) ) );
  }

  @Test
  public void tc1_checkTitlesAndGraphicsPresence() {

    for ( String title : titles ) {
      String titleLocator = String.format( "//span[contains(text(),'%s')]", title );

      assertNotNull( this.elemHelper.WaitForElementPresence( driver, By.xpath( titleLocator ) ) );
    }

    //First Graphic
    assertNotNull( this.elemHelper.WaitForElementPresence( driver, By.xpath( "//*[@id='racesChartObjprotovis']" ) ) );

    //First Table
    assertNotNull( this.elemHelper.WaitForElementPresence( driver, By.xpath( "//*[@id='racesChartTableObjTable']" ) ) );

    //Second Table
    assertNotNull( this.elemHelper.WaitForElementPresence( driver, By.xpath( "//*[@id='tableObjTable']" ) ) );

  }

  @AfterClass
  public void closeDemo() {
    driver.close();

    driver.switchTo().window( this.winHandleBefore );
  }
}
