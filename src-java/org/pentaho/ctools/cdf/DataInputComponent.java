package org.pentaho.ctools.cdf;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;

import static org.junit.Assert.assertEquals;

public class DataInputComponent {
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
  public void testPickADateAndCheckForAlert() throws Exception {
    driver.get(baseUrl + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:40-DateInputComponent:date_input_component.xcdf/generatedContent");

    //Wait for visibility of 'DateInputComponent'
    ElementHelper.IsElementDisplayed(driver, By.xpath("//div[@id='dashboardContent']/div/div/div/h2/span[2]"));

    // Validate the sample that we are testing is the one
    assertEquals("Community Dashboard Framework", driver.getTitle());
    assertEquals("DateInputComponent", driver.findElement(By.xpath("//div[@id='dashboardContent']/div/div/div/h2/span[2]")).getText());

    //Render again the sample
    driver.findElement(By.xpath("//div[@id='example']/ul/li[2]/a")).click();
    driver.findElement(By.cssSelector("#code > button")).click();

    //Pick a date
    ElementHelper.IsElementDisplayed(driver, By.id("myInput"));

    driver.findElement(By.id("myInput")).sendKeys("''");
    Select month = new Select(driver.findElement(By.className("ui-datepicker-month")));
    month.selectByValue("9");
    Select year = new Select(driver.findElement(By.className("ui-datepicker-year")));
    year.selectByValue("2011");
    //Day 23
    driver.findElement(By.xpath("//table[@class='ui-datepicker-calendar']//tbody//tr[5]/td/a")).click();


    wait.until(ExpectedConditions.alertIsPresent());
    Alert alert = driver.switchTo().alert();
    String confirmationMsg = alert.getText();
    alert.accept();

    /*##########################################################################
      EXPECTED RESULT:
      - The popup alert shall displayed the data picked.
     #########################################################################*/
    assertEquals(confirmationMsg, "You chose: 2011-10-23");
  }

  @After
  public void tearDown() { }
}
