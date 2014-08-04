package org.pentaho.ctools.cdf;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * NOTE - The test was created regarding issue CDF-318
 */
public class MetaLayerHomeDashboard {
  // Instance of the driver (browser emulator)
  private WebDriver driver;
  // Instance to be used on wait commands
  private Wait<WebDriver> wait;
  // The base url to be append the relative url in test
  private String baseUrl;

  @Before
  public void setUp() throws Exception {
    driver = CToolsTestSuite.getDriver();
    wait = CToolsTestSuite.getWait();
    baseUrl = CToolsTestSuite.getBaseUrl();
  }

  @Test
  public void testCDFAutoCompleteBoxComponent() throws Exception {
    driver.get(baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A20-samples%3Ahome_dashboard_2%3Ahome_dashboard_metalyer.xcdf/generatedContent");

    //Wait for visibility of 'topTenCustomersDetailsObject' the text 'Details'
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='topTenCustomersDetailsObject']")));
    assertEquals("Community Dashboard Framework", driver.getTitle());

    WebElement linkDetails = driver.findElement(By.xpath("//div[@id='topTenCustomersDetailsObject']/a"));
    assertEquals("Details...", linkDetails.getText());

    //click on the 'Details...'
    linkDetails.click();

    //Wait for the frame
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[@id='fancybox-frame']")));


    WebElement frame = driver.findElement(By.xpath("//iframe[@id='fancybox-frame']"));
    String valueFrameAttrSrc = frame.getAttribute("src");

    ///pentaho/plugin/jpivot/Pivot?solution=system&path=%2Fpublic%2Fplugin-samples%2Fpentaho-cdf%2Factions&action=jpivot.xaction&width=500&height=600
    //Check if we have the sizes 500 and 600
    assertTrue(StringUtils.containsIgnoreCase(valueFrameAttrSrc, "action=jpivot.xaction&width=500&height=600"));


    //Wait for the element be visible.
    driver.switchTo().frame("fancybox-frame");
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='internal_content']")));
    assertNotNull(driver.findElement(By.xpath("//div[@id='internal_content']")));

    assertEquals("Measures", driver.findElement(By.xpath("//div[@id='internal_content']/table/tbody/tr[2]/td[2]/p/table/tbody/tr/th[2]")).getText());
  }

  @After
  public void tearDown() { }
}
