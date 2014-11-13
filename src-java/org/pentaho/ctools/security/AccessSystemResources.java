package org.pentaho.ctools.security;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.HttpUtils;
import org.pentaho.ctools.utils.ScreenshotTestRule;

import java.net.HttpURLConnection;

import static org.junit.Assert.assertEquals;

public class AccessSystemResources {
  // Instance of the driver (browser emulator)
  private WebDriver driver;
  // Instance to be used on wait commands
  private Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private String baseUrl;
  
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(driver);

  @Before
  public void setUp() {
    driver = CToolsTestSuite.getDriver();
    wait = CToolsTestSuite.getWait();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  @Test(timeout = 60000)
  public void testLoginPentaho() throws Exception {
    String url = baseUrl + "plugin/pentaho-cdf-dd/api/resources/system/jackrabbit/repository.xml?v=1406646663089";
    driver.get(url);

    //Wait for form display
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body")));
    assertEquals(driver.getTitle(), "Unavailable");
    assertEquals(driver.findElement(By.xpath("//body/div/div/div")).getText(), "Sorry. We really did try.");
    assertEquals(driver.findElement(By.xpath("//body/div/div/div[2]")).getText(), "Something went wrong. Please try again");
    assertEquals(driver.findElement(By.xpath("//body/div/div/div[3]")).getText(), "or contact your administrator.");
    //Check if return HTTP Status 401
    assertEquals(HttpUtils.GetHttpStatus(url), HttpURLConnection.HTTP_UNAUTHORIZED);
  }

  @After
  public void tearDown(){}
}
