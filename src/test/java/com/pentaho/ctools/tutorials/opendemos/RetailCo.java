package com.pentaho.ctools.tutorials.opendemos;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.gui.web.ctools.tutorials.opendemos.CdeOpenDemos;
import com.pentaho.selenium.BaseTest;

public class RetailCo extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  //Log instance
  private final Logger log = LogManager.getLogger( RetailCo.class );

  private CdeOpenDemos cde = new CdeOpenDemos();

  private String winHandleBefore;

  @Test
  public void tc0_viewDemo() {
    log.info( "tc0_viewDemo" );

    this.winHandleBefore = cde.viewDemo( "Retail.Co" );

    // Wait for the loading icon to appear
    elemHelper.WaitForElementPresence( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );
    // Wait for the loading icon to disappear
    elemHelper.WaitForElementNotPresent( driver, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    assertTrue( ( this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//*[contains(@class,'mainTitle')]" ) ).contains( "Retail.Co" ) ) );
  }

  @Test
  public void tc1_checkTitlesAndGraphicsPresence() {
    //First Title
    assertEquals( this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//span[contains(text(),'Analysis')]" ) ), "Analysis" );

    //Second Title
    assertEquals( this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//span[contains(text(),'Product Sales vs Last Year')]" ) ), "Product Sales vs Last Year" );

    //Dropdowns
    List<WebElement> dropdowns = elemHelper.FindElements( driver, By.xpath( "//a[@class='chzn-single']" ) );

    //Quantity of dropdowns visible and available
    assertEquals( dropdowns.size(), 3 );

    //click the first dropdown
    dropdowns.get( 0 ).click();

    //Check the dropdown list elements
    assertEquals( elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//*[@id='collectionFilter']//li[1]" ) ), "All" );
    assertEquals( elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//*[@id='collectionFilter']//li[2]" ) ), "Children" );
    assertEquals( elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//*[@id='collectionFilter']//li[3]" ) ), "Men" );
    assertEquals( elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//*[@id='collectionFilter']//li[4]" ) ), "Women" );

    //click the first dropdown
    dropdowns.get( 1 ).click();

    //Check the dropdown list elements
    assertEquals( elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//*[@id='brandFilter']//li[1]" ) ), "All" );
    assertEquals( elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//*[@id='brandFilter']//li[2]" ) ), "Calvin Klein" );
    assertEquals( elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//*[@id='brandFilter']//li[3]" ) ), "Diesel" );
    assertEquals( elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//*[@id='brandFilter']//li[4]" ) ), "Linea" );
    assertEquals( elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//*[@id='brandFilter']//li[5]" ) ), "Ralph Lauren" );

    //click the first dropdown
    dropdowns.get( 2 ).click();

    //Check the dropdown list elements
    assertEquals( elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//*[@id='regionFilter']//li[1]" ) ), "All" );
    assertEquals( elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//*[@id='regionFilter']//li[2]" ) ), "USA" );
    assertEquals( elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//*[@id='regionFilter']//li[3]" ) ), "Europe" );
    assertEquals( elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//*[@id='regionFilter']//li[4]" ) ), "UK" );

    //Graphic
    assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "lineChartObjprotovis" ) ) );

    //Third Title
    assertEquals( this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//span[contains(text(),'Product Orders')]" ) ), "Product Orders" );

    //Summary Table
    assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "summaryTable" ) ) );

    //Details Table
    assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( driver, By.id( "detailsTable" ) ) );
  }

  @AfterClass
  public void closeDemo() {
    driver.close();

    driver.switchTo().window( this.winHandleBefore );
  }
}
