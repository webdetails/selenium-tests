package org.pentaho.ctools.main;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LogoutPentaho {
  // Instance of the driver (browser emulator)
  private WebDriver driver;
  // Instance to be used on wait commands
  private Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private String baseUrl;
  
  @Before
  public void setUp() {
    driver = CToolsTestSuite.getDriver();
    wait = CToolsTestSuite.getWait();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  @Test
  public void testLogoutPentaho() throws Exception {
    driver.get(baseUrl + "Home");

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[@id='home.perspective']")));
    assertEquals("Pentaho User Console", driver.getTitle());
    assertNotNull(driver.findElement(By.xpath("//iframe[@id='home.perspective']")));


    /*
    for (int second = 0;; second++) {
    	if (second >= 60) fail("timeout");
    	try { if (isElementPresent(By.xpath("//iframe[@id=\"home.perspective\"]"))) break; } catch (Exception e) {}
    	Thread.sleep(1000);
    }*/

    /*
    for (int second = 0;; second++) {
    	if (second >= 60) fail("timeout");
    	try { if (isElementPresent(By.xpath("//div[@id='pucUserDropDown']"))) break; } catch (Exception e) {}
    	Thread.sleep(1000);
    }*/




    driver.findElement(By.xpath("//div[@id='pucUserDropDown']/table/tbody/tr/td/div")).click();
    driver.findElement(By.xpath("//div[@id='customDropdownPopupMinor']/div/div/table/tbody/tr/td")).click();
    /*
    for (int second = 0;; second++) {
    	if (second >= 60) fail("timeout");
    	try { if (isElementPresent(By.xpath("//form[@id=\"login\"]"))) break; } catch (Exception e) {}
    	Thread.sleep(1000);
    }*/


    //Wait for form display
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='login-form-container']/div/h1")));
    assertNotNull(driver.findElement(By.xpath("//div[@id='login-form-container']/div/h1")));
  }

  @After
  public void tearDown() { }
}
