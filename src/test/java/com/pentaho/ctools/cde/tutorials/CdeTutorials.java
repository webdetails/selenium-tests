package com.pentaho.ctools.cde.tutorials;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.List;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.HttpUtils;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with CDE Tutorials.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CdeTutorials extends BaseTest {
	  // Access to wrapper for webdriver
	  private static final ElementHelper elemHelper = new ElementHelper();
	  //Log instance
	  private static final Logger log = LogManager.getLogger( CdeTutorials.class );
	  
	  /**
	   * ############################### Test Case 0 ###############################
	   *
	   * Description:
	   * 	Opens CDE Tutorials in Welcome page.
	   */
	  @Test
	  public static void tc0_open() {
		  log.info( "tc0_open" );
		  
		  //wait for invisibility of waiting pop-up
		  elemHelper.WaitForElementInvisibility( driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );
		  
		  //Open CDE Tutorials Welcome page 
		  elemHelper.Get(driver, PageUrl.CDE_TUTORIALS_WELCOME);
		  
		  //Test if page was loaded
		  assertEquals("Welcome", elemHelper.WaitForElementPresentGetText(driver, By.xpath("//*[@id='mainContent']/h1")));
		  
		  return;
	  }
	  
	  /**
	   * Description:
	   * 	Click on a link and check the page was loaded by confirming a presence of some text.
	   * 
	   * Parameters:
	   * 	linkLocator (By): 		Locator of the link where user wants to click;
	   * 	textLocator (By):		Locator of the text the driver needs to find in order to confirm the page was loaded;
	   * 	elementText (String):	Text the driver needs to find in order to confirm the page was loaded.
	   * 
	   * Returns: nothing.
	   */
	  public static void clickAndCheckPageLoaded (By linkLocator, By textLocator, String elementText)
	  {
		  log.info( "clickAndCheckPageLoaded" );
		  
		  // Store the current window handle
		  String winHandleBefore = driver.getWindowHandle();
		  
		  //Click given link
		  elemHelper.Click(driver, linkLocator);
		  
		  // Switch to new window opened
		  for(String winHandle : driver.getWindowHandles()){
		      driver.switchTo().window(winHandle);
		  }

		  //Confirm page was loaded
		  assertEquals(elemHelper.WaitForElementPresentGetText(driver, textLocator),elementText);
		  
		  
		  // Close the new window, if that window no more required
		  driver.close();

		  // Switch back to original browser (first window)
		  driver.switchTo().window(winHandleBefore);
		  
		  return;
	  }
	  
	  /**
	   * Description:
	   * 	Click on a link and check the page was loaded by confirming a presence of some text.
	   * 
	   * Parameters:
	   * 	element (WebElement): 	element where user wants to click;
	   * 	textLocator (By):		Locator of the text the driver needs to find in order to confirm the page was loaded;
	   * 	elementText (String):	Text the driver needs to find in order to confirm the page was loaded.
	   * 
	   * Returns: nothing.
	   */
	  public static void clickAndCheckPageLoaded (WebElement element, By textLocator, String elementText)
	  {
		  log.info( "clickAndCheckPageLoaded" );
		  
		  // Store the current window handle
		  String winHandleBefore = driver.getWindowHandle();
		  
		  //Click given link
		  element.click();
		  
		  // Switch to new window opened
		  for(String winHandle : driver.getWindowHandles()){
		      driver.switchTo().window(winHandle);
		  }

		  //Confirm page was loaded
		  assertEquals(elemHelper.WaitForElementPresentGetText(driver, textLocator),elementText);
		  
		  
		  // Close the new window, if that window no more required
		  driver.close();

		  // Switch back to original browser (first window)
		  driver.switchTo().window(winHandleBefore);
		  
		  return;
	  }
	  
	  /**
	   * Description:
	   * 	Click on a link and check the page was loaded by confirming a presence of some text. Gets the CDE Document name and returns it. 
	   * 
	   * Parameters:
	   * 	linkLocator (By): 		Locator of the link where user wants to click;
	   * 	textLocator (By):		Locator of the text the driver needs to find in order to confirm the page was loaded;
	   * 	elementText (String):	Text the driver needs to find in order to confirm the page was loaded.
	   * 
	   * Returns: 
	   * 	docName (String): 		Name of the CDE document.
	   */
	  public static String getCdeDocName (By linkLocator)
	  {
		  log.info( "clickAndCheckPageLoaded" );
		  
		  // Store the current window handle
		  String winHandleBefore = driver.getWindowHandle();
		  
		  //Click given link
		  elemHelper.Click(driver, linkLocator);
		  
		  // Switch to new window opened
		  for(String winHandle : driver.getWindowHandles()){
		      driver.switchTo().window(winHandle);
		  }
		  
		  //Get CDE Document Name
		  String docName = elemHelper.WaitForElementPresentGetText(driver, By.xpath("//*[@class='cdfdd-title']"));

		  //Confirm page was loaded
		  assertEquals(elemHelper.WaitForElementPresentGetText(driver, By.xpath("//title[contains(text(),'Webdetails CDE')]")),"Webdetails CDE");
		  
		  
		  // Close the new window, if that window no more required
		  driver.close();

		  // Switch back to original browser (first window)
		  driver.switchTo().window(winHandleBefore);
		  
		  return docName;
	  }
	  
	  /**
	   * Description:
	   * 	Click on a link and check the page was loaded by confirming a presence of some text. Gets the CDE Document name and returns it. 
	   * 
	   * Parameters:
	   * 	element (WebElement): 	WebElement where the user wants to click;
	   * 	textLocator (By):		Locator of the text the driver needs to find in order to confirm the page was loaded;
	   * 	elementText (String):	Text the driver needs to find in order to confirm the page was loaded.
	   * 
	   * Returns: 
	   * 	docName (String): 		Name of the CDE document.
	   */
	  public static String getCdeDocName (WebElement element)
	  {
		  log.info( "clickAndCheckPageLoaded" );
		  
		  // Store the current window handle
		  String winHandleBefore = driver.getWindowHandle();
		  
		  //Click given link
		  element.click();
		  
		  // Switch to new window opened
		  for(String winHandle : driver.getWindowHandles()){
		      driver.switchTo().window(winHandle);
		  }
		  
		  //Get CDE Document Name
		  String docName = elemHelper.WaitForElementPresentGetText(driver, By.xpath("//*[@class='cdfdd-title']"));

		  //Confirm page was loaded
		  assertEquals(elemHelper.WaitForElementPresentGetText(driver, By.xpath("//title[contains(text(),'Webdetails CDE')]")),"Webdetails CDE");
		  
		  
		  // Close the new window, if that window no more required
		  driver.close();

		  // Switch back to original browser (first window)
		  driver.switchTo().window(winHandleBefore);
		  
		  return docName;
	  }
	  
	  /**
	   * Description:
	   * 	Assert if images are present and if get requests with the images src as url returns HTTP Status 200 OK.
	   * 
	   * Parameters:
	   * 	parentId - Web element containing the images with id = parentID
	   * 
	   * Returns: nothing.
	   */
	  public static void checkImagesPresenceAndHttpStatus (String parentId)
	  {
		  //Builds the Locator to find all the images under the web element with id = parentID
		  String xpathLocator = String.format("//*[@id='%s']//img", parentId);
		  
				  
		  //Locates every image under the parentId web element
		  List<WebElement> img = driver.findElements(By.xpath(xpathLocator));
		  
		  //Test if images were found
		  assertNotNull(img);
		  
		  //Iterates through every image
		  for(WebElement we : img)
		  {
			  //Assert if get request returns HTTP Status 200 OK. URL = image src attribute
			  assertEquals(HttpUtils.GetHttpStatus(we.getAttribute("src"),pentahoBaServerUsername, pentahoBaServerPassword),HttpStatus.SC_OK);
		  }
		  
		  return;
	  }
	  
}