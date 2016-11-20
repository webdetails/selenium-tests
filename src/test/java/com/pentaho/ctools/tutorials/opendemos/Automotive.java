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

public class Automotive extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( Automotive.class );

  private CdeOpenDemos cde = new CdeOpenDemos();

  private final String[] markets = { "EMEA",
                                     "APAC",
                                     "Japan",
                                     "NA" };

  private final String[] years = { "2003",
                                   "2004",
                                   "2005" };

  private final String[] titles = { "Sales by Country",
                                    "Period Sales vs Target",
                                    "Efficiency",
                                    "Quantity Top 5 Lines",
                                    "Sales Monthly Breakdown" };

  private final String[] chartIds = { "salesByCountryChartObjprotovis",
                                      "salesTargetChartObjprotovis",
                                      "efficiencyChartObjprotovis",
                                      "topLinesChartObjprotovis",
                                      "monthlySalesChartObjprotovis" };

  private String winHandleBefore;

  @Test
  public void tc0_viewDemo() {
    log.info( "tc0_viewDemo" );

    this.winHandleBefore = cde.viewDemo( "Automotive" );

    // Wait for the loading icon to disappear
    elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    assertTrue( ( this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//*[@id='headerLogo']" ) ).contains( "Automotive & Co." ) ) );
  }

  @Test
  public void tc1_checkTitlesAndGraphicsPresence() {

    for ( String market : markets ) {

      //Wait for the loading icon to disappear
      elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

      //Expand markets selector (top right corner)
      elemHelper.Click( driver, By.xpath( "//div[@id='marketSelectorObj']//a[contains(@class,'chzn-single')]" ) );

      //Locator of the next market in the list
      String marketLocator = String.format( "//div[@class='chzn-drop']//li[contains(text(),'%s')]", market );

      //Click in the next market in the list
      elemHelper.Click( driver, By.xpath( marketLocator ) );

      for ( String year : years ) {

        //Wait for the loading icon to disappear
        elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

        //Expand markets selector (top right corner)
        elemHelper.Click( driver, By.xpath( "//div[@id='yearSelectorObj']//a[contains(@class,'chzn-single')]" ) );

        //Locator of the next market in the list
        String yearLocator = String.format( "//div[@class='chzn-drop']//li[contains(text(),'%s')]", year );

        //Click in the next market in the list
        elemHelper.Click( driver, By.xpath( yearLocator ) );

        for ( String title : titles ) {

          //Locator of the next chart title in the list
          String titleLocator = String.format( "//span[contains(text(),'%s')]", title );

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

    }

  }

  @AfterClass
  public void closeDemo() {
    driver.close();

    driver.switchTo().window( this.winHandleBefore );
  }
}
