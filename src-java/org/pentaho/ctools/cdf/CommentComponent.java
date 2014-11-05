/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2014 by Pentaho : http://www.pentaho.com
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

package org.pentaho.ctools.cdf;

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
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * Testing the functionalities related with Comment Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CommentComponent {
  //Instance of the driver (browser emulator)
  private static WebDriver       driver;
  // Instance to be used on wait commands
  private static Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private static String          baseUrl;
  //Log instance
  private static Logger          log                = LogManager.getLogger(CommentComponent.class);

  @Rule
  public ScreenshotTestRule      screenshotTestRule = new ScreenshotTestRule(driver);

  /**
   * Shall initialized the test before run each test case.
   */
  @BeforeClass
  public static void setUp() {
    log.debug("setup");
    driver = CToolsTestSuite.getDriver();
    wait = CToolsTestSuite.getWait();
    baseUrl = CToolsTestSuite.getBaseUrl();

    // Go to sample
    init();
  }

  /**
   * Go to the CommentComponent web page.
   */
  public static void init() {
    // The URL for the CheckComponent under CDF samples
    // This samples is in: Public/plugin-samples/CDF/Documentation/Component
    // Reference/Core Components/CommentComponent
    driver.get(baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A79-CommentsComponent%3Acomments_component.xcdf/generatedContent");

    // Not we have to wait for loading disappear
    ElementHelper.IsElementInvisible(driver, By.xpath("//div[@class='blockUI blockOverlay']"));
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
  @Test
  public void tc1_PageContent_DisplayTitle() {
    // Wait for title become visible and with value 'Community Dashboard Framework'
    wait.until(ExpectedConditions.titleContains("Community Dashboard Framework"));
    // Wait for visibility of 'VisualizationAPIComponent'
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dashboardContent']/div/div/div/h2/span[2]")));

    // Validate the sample that we are testing is the one
    assertEquals("Community Dashboard Framework", driver.getTitle());
    assertEquals("CommentsComponent", ElementHelper.GetText(driver, By.xpath("//div[@id='dashboardContent']/div/div/div/h2/span[2]")));
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
    // ## Step 1
    // Render again the sample
    ElementHelper.FindElement(driver, By.xpath("//div[@id='example']/ul/li[2]/a")).click();
    ElementHelper.FindElement(driver, By.xpath("//div[@id='code']/button")).click();

    // Not we have to wait for loading disappear
    ElementHelper.IsElementInvisible(driver, By.xpath("//div[@class='blockUI blockOverlay']"));

    // Now sample element must be displayed
    assertTrue(ElementHelper.FindElement(driver, By.id("sample")).isDisplayed());

    //Check the number of divs with id 'nCommentComponent'
    //Hence, we guarantee when click Try Me the previous div is replaced
    int nCommentComponent = driver.findElements(By.cssSelector("commentComponent")).size();
    assertEquals(1, nCommentComponent);
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
  @Test
  public void tc3_DisplayComponent_CheckDisplayedPage() {
    log.debug("tc3_SelectEachItem_AlertDisplayed");
    log.info("ToDEL: enter: tc3_DisplayComponent_CheckDisplayedPage");
    /*
     * Guarantee no comments displayed
     */
    cleanAllComments();

    // ## Step 1
    String noComments = ElementHelper.GetText(driver, By.cssSelector("div.comment"));
    assertEquals("No Comments to show!", noComments);

    String addComments = ElementHelper.GetText(driver, By.cssSelector("div.addComment"));
    assertEquals("Add Comment", addComments);

    String refreshComments = ElementHelper.GetText(driver, By.cssSelector("div.navigateRefresh"));
    assertEquals("Refresh", refreshComments);
    log.info("ToDEL: exit: tc3_DisplayComponent_CheckDisplayedPage");
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
  @Test
  public void tc4_AddComponent_CommentIsDisplayed() {
    log.debug("tc3_SelectEachItem_AlertDisplayed");
    log.info("ToDEL: enter: tc4_AddComponent_CommentIsDisplayed");
    /*
     * Guarantee no comments displayed
     */
    cleanAllComments();

    String smallText = "Remove this comment please";
    String longText = "The policy states that a pre departure beverage should be served to First Class/Envoy Class customers on every flight. Beverages given by flight attendants (including bottles of water) must be collected before or during the walk through before takeoff/landing. Because we have not integrated ALL P/P's between East/West, you may find that the West f/a's collect before closing the a/c door as it was/may still be policy. Reasons short of laziness to not serve? Catering hasn't provided adequate supplies yet. Steady stream of passengers because the flight is way late and the agents have a steady flow of customers line up. Even then, they should try. Short of that, there is NO excuse.";
    String specCharText = "+´~~º-113334233&#$%66/8&%$53";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);

    /*
     * ## Step 1
     */
    ElementHelper.FindElement(driver, By.cssSelector("div.addComment")).click();
    //After click in add, check if the button add, save and cancel are displayed
    String noComments = ElementHelper.GetText(driver, By.cssSelector("div.comment"));
    assertEquals("No Comments to show!", noComments);
    String addComments = ElementHelper.GetText(driver, By.cssSelector("div.addComment"));
    assertEquals("Add Comment", addComments);
    String cancelComments = ElementHelper.GetText(driver, By.cssSelector("div.cancelComment"));
    assertEquals("Cancel", cancelComments);
    String saveComments = ElementHelper.GetText(driver, By.cssSelector("div.saveComment"));
    assertEquals("Save", saveComments);
    //Insert the text
    ElementHelper.FindElement(driver, By.cssSelector("textarea.addCommentText")).sendKeys(smallText);
    ElementHelper.FindElement(driver, By.cssSelector("div.saveComment")).click();
    Date timeAddedComment1 = new Date();
    //wait for the page rendered
    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.navigateRefresh")));
    //Check if the comment was added
    String strCommentTimeAdded = "admin, " + sdf.format(timeAddedComment1);
    String commentDetails1 = ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObject']/div/div/div[1]/div"));
    assertThat("Comment added: " + commentDetails1, commentDetails1, CoreMatchers.containsString(strCommentTimeAdded));
    String commentAdded1 = ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObject']/div/div/div[1]/div[2]/div"));
    assertEquals(commentAdded1, smallText);

    /*
     * ## Step 2
     */
    ElementHelper.FindElement(driver, By.cssSelector("div.addComment")).click();
    //After click in add, check if the button add, save and cancel are displayed
    addComments = ElementHelper.GetText(driver, By.cssSelector("div.addComment"));
    assertEquals("Add Comment", addComments);
    cancelComments = ElementHelper.GetText(driver, By.cssSelector("div.cancelComment"));
    assertEquals("Cancel", cancelComments);
    saveComments = ElementHelper.GetText(driver, By.cssSelector("div.saveComment"));
    assertEquals("Save", saveComments);
    //Insert the text
    ElementHelper.FindElement(driver, By.cssSelector("textarea.addCommentText")).sendKeys(longText);
    ElementHelper.FindElement(driver, By.cssSelector("div.saveComment")).click();
    Date timeAddedComment2 = new Date();
    //wait for the page rendered (and for the two added comments persist
    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.navigateRefresh")));
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='sampleObject']/div/div/div[1]")));
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='sampleObject']/div/div/div[2]")));
    //Check if the comment was added
    //Comment added 2
    String strCommentTimeAdded2 = "admin, " + sdf.format(timeAddedComment2);
    String commentDetails2 = ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObject']/div/div/div[1]/div"));
    assertThat("Comment added: " + commentDetails2, commentDetails2, CoreMatchers.containsString(strCommentTimeAdded2));
    String commentAdded2 = ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObject']/div/div/div[1]/div[2]/div"));
    assertEquals(commentAdded2, longText);
    //Comment added 1
    commentDetails1 = ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObject']/div/div/div[2]/div"));
    assertThat("Comment added: " + commentDetails1, commentDetails1, CoreMatchers.containsString(strCommentTimeAdded));
    commentAdded1 = ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObject']/div/div/div[2]/div[2]/div"));
    assertEquals(commentAdded1, smallText);

    /*
     * ## Step 3
     */
    ElementHelper.FindElement(driver, By.cssSelector("div.addComment")).click();
    //After click in add, check if the button add, save and cancel are displayed
    addComments = ElementHelper.GetText(driver, By.cssSelector("div.addComment"));
    assertEquals("Add Comment", addComments);
    cancelComments = ElementHelper.GetText(driver, By.cssSelector("div.cancelComment"));
    assertEquals("Cancel", cancelComments);
    saveComments = ElementHelper.GetText(driver, By.cssSelector("div.saveComment"));
    assertEquals("Save", saveComments);
    //Insert the text
    ElementHelper.FindElement(driver, By.cssSelector("textarea.addCommentText")).sendKeys(specCharText);
    ElementHelper.FindElement(driver, By.cssSelector("div.saveComment")).click();
    Date timeAddedComment3 = new Date();
    //wait for the page rendered (and for the two added comments persist
    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.navigateRefresh")));
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='sampleObject']/div/div/div[1]")));
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='sampleObject']/div/div/div[2]")));
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='sampleObject']/div/div/div[3]")));
    //Check if the comment was added
    //Comment added 3
    String strCommentTimeAdded3 = "admin, " + sdf.format(timeAddedComment3);
    String commentDetails3 = ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObject']/div/div/div[1]/div"));
    assertThat("Comment added: " + commentDetails3, commentDetails3, CoreMatchers.containsString(strCommentTimeAdded3));
    String commentAdded3 = ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObject']/div/div/div[1]/div[2]/div"));
    assertEquals(commentAdded3, specCharText);
    //Comment added 2
    strCommentTimeAdded2 = "" + sdf.format(timeAddedComment2);
    commentDetails2 = ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObject']/div/div/div[2]/div"));
    assertThat("Comment added: " + commentDetails2, commentDetails2, CoreMatchers.containsString(strCommentTimeAdded2));
    commentAdded2 = ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObject']/div/div/div[2]/div[2]/div"));
    assertEquals(commentAdded2, longText);
    //Comment added 1
    commentDetails1 = ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObject']/div/div/div[3]/div"));
    assertThat("Comment added: " + commentDetails1, commentDetails1, CoreMatchers.containsString(strCommentTimeAdded));
    commentAdded1 = ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObject']/div/div/div[3]/div[2]/div"));
    assertEquals(commentAdded1, smallText);

    log.info("ToDEL: exit: tc4_AddComponent_CommentIsDisplayed");
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
  @Test
  public void tc5_RemoveComponent_CommentRemoved() {
    log.debug("tc5_RemoveComponent_CommentRemoved");
    log.info("ToDEL: enter: tc5_RemoveComponent_CommentRemoved");
    /*
     * Guarantee no comments displayed
     */
    cleanAllComments();

    String commentText = "Some comment!";
    String noComments = ElementHelper.GetText(driver, By.cssSelector("div.comment"));
    assertEquals("No Comments to show!", noComments);
    String addComments = ElementHelper.GetText(driver, By.cssSelector("div.addComment"));
    assertEquals("Add Comment", addComments);

    /*
     * ## Step 1
     */
    ElementHelper.FindElement(driver, By.cssSelector("div.addComment")).click();
    //Insert the text
    ElementHelper.FindElement(driver, By.cssSelector("textarea.addCommentText")).sendKeys(commentText);
    ElementHelper.FindElement(driver, By.cssSelector("div.saveComment")).click();
    //wait for the page rendered
    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.navigateRefresh")));
    //Check if the comment was added
    String commentAdded = ElementHelper.GetText(driver, By.xpath("//div[@id='sampleObject']/div/div/div[1]/div[2]/div"));
    assertEquals(commentAdded, commentText);

    /*
     * ## Step 2
     */
    Actions acts = new Actions(driver);
    acts.moveToElement(ElementHelper.FindElement(driver, By.xpath("//div[@id='sampleObject']/div/div/div[1]/div[2]/div")));
    acts.build().perform();
    ElementHelper.FindElement(driver, By.cssSelector("div.archive")).click();
    //wait for no element present
    ElementHelper.IsElementInvisible(driver, By.cssSelector("div.archive"));
    //Check we don't have more comments
    noComments = ElementHelper.GetText(driver, By.cssSelector("div.comment"));
    assertEquals("No Comments to show!", noComments);
    addComments = ElementHelper.GetText(driver, By.cssSelector("div.addComment"));
    assertEquals("Add Comment", addComments);
    String refreshComments = ElementHelper.GetText(driver, By.cssSelector("div.navigateRefresh"));
    assertEquals("Refresh", refreshComments);

    log.info("ToDEL: exit: tc5_RemoveComponent_CommentRemoved");
  }

  /**
   * This method shall clean all existence comments.
   */
  private static void cleanAllComments() {
    log.info("Remove comments");

    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

    List<WebElement> listEraseComments = driver.findElements(By.cssSelector("div.archive"));
    int nIteractions = listEraseComments.size();
    if (nIteractions > 0) {
      log.debug("We have comments to remove");
      log.info("ToDEL: We have comments to remove " + nIteractions);
      for (int i = 1; i <= nIteractions; i++) {
        log.info("ToDEL: Iteration " + i);
        Actions acts = new Actions(driver);
        acts.moveToElement(ElementHelper.FindElement(driver, By.xpath("//div[@id='sampleObject']/div/div/div[1]/div[2]/div")));
        acts.build().perform();
        log.info("ToDEL: mouse over");
        ElementHelper.FindElement(driver, By.cssSelector("div.archive")).click();
        log.info("ToDEL: click");

        if (i != nIteractions) {
          log.info("ToDEL: check if was updated");
          int nTry = 1000;
          while (nTry > 0) {
            log.info("ToDEL: check for updated");
            int nowSize = driver.findElements(By.cssSelector("div.archive")).size();
            log.debug("size: " + nowSize + "Expected: " + (nIteractions - i));
            log.info("ToDEL: size: " + nowSize + "Expected: " + (nIteractions - i));
            //int nExpectedSize = nIteractions - i;
            log.info("ToDEL: expected size: " + nExpectedSize);
            if (nowSize == nIteractions - i) {
              log.info("ToDEL: look next");
              break;//The code was updated
            }
            nTry--;
          }
        }
      }
    }
    log.info("ToDEL: leave remove comments");
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @AfterClass
  public static void tearDown() {
    log.debug("tearDown");
    cleanAllComments();
  }
}
