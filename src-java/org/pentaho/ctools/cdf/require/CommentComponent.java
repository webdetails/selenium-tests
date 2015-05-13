/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2015 by Pentaho : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package org.pentaho.ctools.cdf.require;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.CoreMatchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.PageUrl;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * Testing the functionalities related with Comment Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class CommentComponent {

  //Instance of the driver (browser emulator)
  private static WebDriver DRIVER;
  // Instance to be used on wait commands
  private static Wait<WebDriver> WAIT;
  //Log instance
  private static Logger LOG = LogManager.getLogger( CommentComponent.class );

  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( DRIVER );

  /**
   * Shall initialized the test before run each test case.
   */
  @BeforeClass
  public static void setUp() {
    LOG.info( "setUp##" + CommentComponent.class.getSimpleName() );
    DRIVER = CToolsTestSuite.getDriver();
    WAIT = CToolsTestSuite.getWait();
  }

  /**
   * ############################### Test Case 0 ###############################
   *
   * Test Case Name:
   *    Open Sample Page
   */
  @Test( timeout = 60000 )
  public void tc0_OpenSamplePage() {
    LOG.debug( "tc0_OpenSamplePage" );
    // The URL for the CommentComponent under CDF samples
    // This sample is in: 
    // ::Public/plugin-samples/CDF/Require Samples/Documentation/Component Reference/Core Components/CommentComponent
    DRIVER.get( PageUrl.COMMENT_COMPONENT_REQUIRE );

    // NOTE - we have to wait for loading disappear
    ElementHelper.WaitForElementPresence( DRIVER, By.cssSelector( "div.blockUI.blockOverlay" ), 2 );
    ElementHelper.WaitForElementInvisibility( DRIVER, By.cssSelector( "div.blockUI.blockOverlay" ) );
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Reload Sample
   * Description:
   *    Reload the sample (not refresh page).
   * Steps:
   *    1. Click in Code and then click in button 'Try me'.
   */
  @Test( timeout = 60000 )
  public void tc1_PageContent_DisplayTitle() {
    LOG.debug( "tc1_PageContent_DisplayTitle" );
    // Wait for title become visible and with value 'Community Dashboard Framework'
    WAIT.until( ExpectedConditions.titleContains( "Community Dashboard Framework" ) );
    // Wait for visibility of 'VisualizationAPIComponent'
    WAIT.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );

    // Validate the sample that we are testing is the one
    assertEquals( "Community Dashboard Framework", DRIVER.getTitle() );
    assertEquals( "CommentsComponent", ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Reload Sample
   * Description:
   *    Reload the sample (not refresh page).
   * Steps:
   *    1. Click in Code and then click in button 'Try me'.
   */
  public void tc2_ReloadSample_SampleReadyToUse() {
    LOG.debug( "tc2_ReloadSample_SampleReadyToUse" );
    // ## Step 1
    // Render again the sample
    ElementHelper.ClickJS( DRIVER, By.xpath( "//div[@id='example']/ul/li[2]/a" ) );
    ElementHelper.ClickJS( DRIVER, By.xpath( "//div[@id='code']/button" ) );

    // NOTE - we have to wait for loading disappear
    ElementHelper.WaitForElementPresence( DRIVER, By.cssSelector( "div.blockUI.blockOverlay" ), 2 );
    ElementHelper.WaitForElementInvisibility( DRIVER, By.cssSelector( "div.blockUI.blockOverlay" ) );

    // Now sample element must be displayed
    assertTrue( ElementHelper.FindElement( DRIVER, By.id( "sample" ) ).isDisplayed() );

    //Check the number of divs with id 'nCommentComponent'
    //Hence, we guarantee when click Try Me the previous div is replaced
    int nCommentComponent = DRIVER.findElements( By.cssSelector( "commentComponent" ) ).size();
    assertEquals( 1, nCommentComponent );
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Display Page
   * Description:
   *    We pretend validate the contents presented on displayed page.
   * Steps:
   *    1. Check the contents of display page
   */
  @Test( timeout = 60000 )
  public void tc3_DisplayComponent_CheckDisplayedPage() {
    LOG.debug( "tc3_DisplayComponent_CheckDisplayedPage" );
    /*
     * Guarantee no comments displayed
     */
    cleanAllComments();

    // ## Step 1
    String noComments = ElementHelper.WaitForElementPresentGetText( DRIVER, By.cssSelector( "div.comment" ) );
    assertEquals( "No Comments to show!", noComments );

    String addComments = ElementHelper.WaitForElementPresentGetText( DRIVER, By.cssSelector( "div.addComment" ) );
    assertEquals( "Add Comment", addComments );

    String refreshComments = ElementHelper.WaitForElementPresentGetText( DRIVER, By.cssSelector( "div.navigateRefresh" ) );
    assertEquals( "Refresh", refreshComments );
  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name:
   *    Display Page
   * Description:
   *    We pretend validate the comments added are listed.
   * Steps:
   *    1. Add one comment
   *    2. Add another comment
   *    3. Add another comment
   */
  @Test( timeout = 60000 )
  public void tc4_AddComponent_CommentIsDisplayed() {
    LOG.debug( "tc4_AddComponent_CommentIsDisplayed" );
    /*
     * Guarantee no comments displayed
     */
    cleanAllComments();

    String smallText = "Remove this comment please";
    String longText = "The policy states that a pre departure beverage should be served to First Class/Envoy Class customers on every flight. Beverages given by flight attendants (including bottles of water) must be collected before or during the walk through before takeoff/landing. Because we have not integrated ALL P/P's between East/West, you may find that the West f/a's collect before closing the a/c door as it was/may still be policy. Reasons short of laziness to not serve? Catering hasn't provided adequate supplies yet. Steady stream of passengers because the flight is way late and the agents have a steady flow of customers line up. Even then, they should try. Short of that, there is NO excuse.";
    String specCharText = "+´~~º-113334233&#$%66/8&%$53";
    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm", Locale.US );

    /*
     * ## Step 1
     */
    ElementHelper.ClickJS( DRIVER, By.cssSelector( "div.addComment" ) );
    //After click in add, check if the button add, save and cancel are displayed
    String noComments = ElementHelper.WaitForElementPresentGetText( DRIVER, By.cssSelector( "div.comment" ) );
    assertEquals( "No Comments to show!", noComments );
    String addComments = ElementHelper.WaitForElementPresentGetText( DRIVER, By.cssSelector( "div.addComment" ) );
    assertEquals( "Add Comment", addComments );
    String cancelComments = ElementHelper.WaitForElementPresentGetText( DRIVER, By.cssSelector( "div.cancelComment" ) );
    assertEquals( "Cancel", cancelComments );
    String saveComments = ElementHelper.WaitForElementPresentGetText( DRIVER, By.cssSelector( "div.saveComment" ) );
    assertEquals( "Save", saveComments );
    //Insert the text
    ElementHelper.FindElement( DRIVER, By.cssSelector( "textarea.addCommentText" ) ).sendKeys( smallText );
    ElementHelper.ClickJS( DRIVER, By.cssSelector( "div.saveComment" ) );
    Date timeAddedComment1 = new Date();
    //wait for the page rendered
    WAIT.until( ExpectedConditions.presenceOfElementLocated( By.cssSelector( "div.navigateRefresh" ) ) );
    //Check if the comment was added
    String strCommentTimeAdded = "admin, " + sdf.format( timeAddedComment1 );
    // Need to remove the last digit of minutes to avoid failing. Because, we 
    // could catch something like 14:22 and the comment was added at 14:21:59
    strCommentTimeAdded = strCommentTimeAdded.substring( 0, strCommentTimeAdded.length() - 1 );
    String commentDetails1 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='sampleObject']/div/div/div[1]/div" ) );
    assertThat( "Comment added: " + commentDetails1, commentDetails1, CoreMatchers.containsString( strCommentTimeAdded ) );
    String commentAdded1 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='sampleObject']/div/div/div[1]/div[2]/div" ) );
    assertEquals( commentAdded1, smallText );

    /*
     * ## Step 2
     */
    ElementHelper.ClickJS( DRIVER, By.cssSelector( "div.addComment" ) );
    //After click in add, check if the button add, save and cancel are displayed
    addComments = ElementHelper.WaitForElementPresentGetText( DRIVER, By.cssSelector( "div.addComment" ) );
    assertEquals( "Add Comment", addComments );
    cancelComments = ElementHelper.WaitForElementPresentGetText( DRIVER, By.cssSelector( "div.cancelComment" ) );
    assertEquals( "Cancel", cancelComments );
    saveComments = ElementHelper.WaitForElementPresentGetText( DRIVER, By.cssSelector( "div.saveComment" ) );
    assertEquals( "Save", saveComments );
    //Insert the text
    ElementHelper.FindElement( DRIVER, By.cssSelector( "textarea.addCommentText" ) ).sendKeys( longText );
    ElementHelper.ClickJS( DRIVER, By.cssSelector( "div.saveComment" ) );
    Date timeAddedComment2 = new Date();
    //wait for the page rendered (and for the two added comments persist
    WAIT.until( ExpectedConditions.presenceOfElementLocated( By.cssSelector( "div.navigateRefresh" ) ) );
    WAIT.until( ExpectedConditions.presenceOfElementLocated( By.xpath( "//div[@id='sampleObject']/div/div/div[1]" ) ) );
    WAIT.until( ExpectedConditions.presenceOfElementLocated( By.xpath( "//div[@id='sampleObject']/div/div/div[2]" ) ) );
    //Check if the comment was added
    //Comment added 2
    String strCommentTimeAdded2 = "admin, " + sdf.format( timeAddedComment2 );
    // Need to remove the last digit of minutes to avoid failing. Because, we 
    // could catch something like 14:22 and the comment was added at 14:21:59
    strCommentTimeAdded2 = strCommentTimeAdded2.substring( 0, strCommentTimeAdded2.length() - 1 );
    String commentDetails2 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='sampleObject']/div/div/div[1]/div" ) );
    assertThat( "Comment added: " + commentDetails2, commentDetails2, CoreMatchers.containsString( strCommentTimeAdded2 ) );
    String commentAdded2 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='sampleObject']/div/div/div[1]/div[2]/div" ) );
    assertEquals( commentAdded2, longText );
    //Comment added 1
    commentDetails1 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='sampleObject']/div/div/div[2]/div" ) );
    assertThat( "Comment added: " + commentDetails1, commentDetails1, CoreMatchers.containsString( strCommentTimeAdded ) );
    commentAdded1 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='sampleObject']/div/div/div[2]/div[2]/div" ) );
    assertEquals( commentAdded1, smallText );

    /*
     * ## Step 3
     */
    ElementHelper.ClickJS( DRIVER, By.cssSelector( "div.addComment" ) );
    //After click in add, check if the button add, save and cancel are displayed
    addComments = ElementHelper.WaitForElementPresentGetText( DRIVER, By.cssSelector( "div.addComment" ) );
    assertEquals( "Add Comment", addComments );
    cancelComments = ElementHelper.WaitForElementPresentGetText( DRIVER, By.cssSelector( "div.cancelComment" ) );
    assertEquals( "Cancel", cancelComments );
    saveComments = ElementHelper.WaitForElementPresentGetText( DRIVER, By.cssSelector( "div.saveComment" ) );
    assertEquals( "Save", saveComments );
    //Insert the text
    ElementHelper.FindElement( DRIVER, By.cssSelector( "textarea.addCommentText" ) ).sendKeys( specCharText );
    ElementHelper.ClickJS( DRIVER, By.cssSelector( "div.saveComment" ) );
    Date timeAddedComment3 = new Date();
    //wait for the page rendered (and for the two added comments persist
    WAIT.until( ExpectedConditions.presenceOfElementLocated( By.cssSelector( "div.navigateRefresh" ) ) );
    WAIT.until( ExpectedConditions.presenceOfElementLocated( By.xpath( "//div[@id='sampleObject']/div/div/div[1]" ) ) );
    WAIT.until( ExpectedConditions.presenceOfElementLocated( By.xpath( "//div[@id='sampleObject']/div/div/div[2]" ) ) );
    WAIT.until( ExpectedConditions.presenceOfElementLocated( By.xpath( "//div[@id='sampleObject']/div/div/div[3]" ) ) );
    //Check if the comment was added
    //Comment added 3
    String strCommentTimeAdded3 = "admin, " + sdf.format( timeAddedComment3 );
    // Need to remove the last digit of minutes to avoid failing. Because, we 
    // could catch something like 14:22 and the comment was added at 14:21:59
    strCommentTimeAdded3 = strCommentTimeAdded3.substring( 0, strCommentTimeAdded3.length() - 1 );
    String commentDetails3 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='sampleObject']/div/div/div[1]/div" ) );
    assertThat( "Comment added: " + commentDetails3, commentDetails3, CoreMatchers.containsString( strCommentTimeAdded3 ) );
    String commentAdded3 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='sampleObject']/div/div/div[1]/div[2]/div" ) );
    assertEquals( commentAdded3, specCharText );
    //Comment added 2
    strCommentTimeAdded2 = "" + sdf.format( timeAddedComment2 );
    commentDetails2 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='sampleObject']/div/div/div[2]/div" ) );
    assertThat( "Comment added: " + commentDetails2, commentDetails2, CoreMatchers.containsString( strCommentTimeAdded2 ) );
    commentAdded2 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='sampleObject']/div/div/div[2]/div[2]/div" ) );
    assertEquals( commentAdded2, longText );
    //Comment added 1
    commentDetails1 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='sampleObject']/div/div/div[3]/div" ) );
    assertThat( "Comment added: " + commentDetails1, commentDetails1, CoreMatchers.containsString( strCommentTimeAdded ) );
    commentAdded1 = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='sampleObject']/div/div/div[3]/div[2]/div" ) );
    assertEquals( commentAdded1, smallText );
  }

  /**
   * ############################### Test Case 5 ###############################
   *
   * Test Case Name:
   *    Display Page
   * Description:
   *    We pretend validate to check if a comment is removed.
   * Steps:
   *    1. Add a comment
   *    2. Remove added comment
   */
  @Test( timeout = 60000 )
  public void tc5_RemoveComponent_CommentRemoved() {
    LOG.debug( "tc5_RemoveComponent_CommentRemoved" );
    /*
     * Guarantee no comments displayed
     */
    cleanAllComments();

    String commentText = "Some comment!";
    String noComments = ElementHelper.WaitForElementPresentGetText( DRIVER, By.cssSelector( "div.comment" ) );
    assertEquals( "No Comments to show!", noComments );
    String addComments = ElementHelper.WaitForElementPresentGetText( DRIVER, By.cssSelector( "div.addComment" ) );
    assertEquals( "Add Comment", addComments );

    /*
     * ## Step 1
     */
    ElementHelper.ClickJS( DRIVER, By.cssSelector( "div.addComment" ) );
    //Insert the text
    ElementHelper.FindElement( DRIVER, By.cssSelector( "textarea.addCommentText" ) ).sendKeys( commentText );
    ElementHelper.ClickJS( DRIVER, By.cssSelector( "div.saveComment" ) );
    //wait for the page rendered
    WAIT.until( ExpectedConditions.presenceOfElementLocated( By.cssSelector( "div.navigateRefresh" ) ) );
    //Check if the comment was added
    String commentAdded = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='sampleObject']/div/div/div[1]/div[2]/div" ) );
    assertEquals( commentAdded, commentText );

    /*
     * ## Step 2
     */
    Actions acts = new Actions( DRIVER );
    acts.moveToElement( ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='sampleObject']/div/div/div[1]/div[2]/div" ) ) );
    acts.build().perform();
    acts.perform();
    acts.moveToElement( ElementHelper.FindElement( DRIVER, By.cssSelector( "div.archive" ) ) );
    acts.click();
    acts.perform();
    //Check we don't have more comments
    noComments = ElementHelper.WaitForElementPresentGetText( DRIVER, By.cssSelector( "div.comment" ) );
    assertEquals( "No Comments to show!", noComments );
    addComments = ElementHelper.WaitForElementPresentGetText( DRIVER, By.cssSelector( "div.addComment" ) );
    assertEquals( "Add Comment", addComments );
    String refreshComments = ElementHelper.WaitForElementPresentGetText( DRIVER, By.cssSelector( "div.navigateRefresh" ) );
    assertEquals( "Refresh", refreshComments );
  }

  /**
   * This method shall clean all existence comments.
   */
  private static void cleanAllComments() {
    LOG.info( "Remove comments" );

    DRIVER.manage().timeouts().implicitlyWait( 2, TimeUnit.SECONDS );

    List<WebElement> listEraseComments = DRIVER.findElements( By.cssSelector( "div.archive" ) );
    int nIteractions = listEraseComments.size();
    LOG.info( "Number elements to remove: " + nIteractions );
    if ( nIteractions > 0 ) {
      LOG.debug( "We have comments to remove" );
      for ( int i = 1; i <= nIteractions; i++ ) {
        Actions acts = new Actions( DRIVER );
        acts.moveToElement( ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='sampleObject']/div/div/div/div[2]/div[2]" ) ) );
        acts.perform();
        acts.moveToElement( ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='sampleObject']/div/div/div/div[2]/div[2]/div" ) ) );
        acts.click();
        acts.perform();
        LOG.debug( "One comment removed." );
      }
    }
    DRIVER.manage().timeouts().implicitlyWait( 30, TimeUnit.SECONDS );
  }

  @AfterClass
  public static void tearDown() {
    LOG.debug( "tearDown" );
    cleanAllComments();
  }
}
