/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2016 by Pentaho : http://www.pentaho.com
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
package com.pentaho.ctools.issues.cdf;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.selenium.BaseTest;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDF-149
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1025
 *
 * NOTE
 * To test this script it is required to have CDF plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class CDF149 extends BaseTest {
	// Access to wrapper for webdriver
	private final ElementHelper elemHelper = new ElementHelper();
	// Log instance
	private final Logger log = LogManager.getLogger( CDF149.class );

	/**
	 * ############################### Test Case 1 ###############################
	 *
	 * Test Case Name:
	 *    Assert Values Normalized property is exposed and working
	 *
	 * Description:
	 *    The test pretends validate the CDF-149 issue, so Values Normalized property is exposed and when set
	 *    to True makes chart bars appear as percentage.
	 *
	 * Steps:
	 *    1. Open CDE Sample, go to Components Panel and select the Bar Chart
	 *    2. Click Advanced Properties and set Values Normalized to true
	 *    3. Click Preview and assert bars are rendered as percentage of total
	 */
	@Test
	public void tc01_CCCBarChart_ValuesNormalized() {
		this.log.info( "tc01_CCCBarChart_ValuesNormalized" );

		/*
		 * ## Step 1
		 */
		//Open CDE Sample
		this.elemHelper.Get( baseUrl + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Acde_sample1.wcdf/wcdf.edit" );

		//Assert Panel selectors and select Components
		WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='cdfdd-modes']/div[@title='Datasources Panel']" ) );
		assertNotNull( element );
		element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='cdfdd-modes']/div[@title='Components Panel']" ) );
		assertNotNull( element );
		element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@class='cdfdd-modes']/div[@title='Layout Panel']" ) );
		assertNotNull( element );
		this.elemHelper.Click( driver, By.xpath( "//div[@class='cdfdd-modes']/div[@title='Components Panel']/a" ) );
		String text = this.elemHelper.GetAttribute( driver, By.xpath( "//div[@class='cdfdd-modes']/div[@title='Components Panel']" ), "class" );
		assertEquals( "panelButton panelButton-active", text );

		//Select Bar Chart
		element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='cdfdd-components-components']//tr[@id='CHARTS']/td/span" ) );
		assertNotNull( element );
		element.click();
		element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//div[@id='cdfdd-components-components']//tr[2]/td" ) );
		assertNotNull( element );
		text = element.getText();
		assertEquals( "CCC Bar Chart", text );
		element.click();
		text = this.elemHelper.GetAttribute( driver, By.xpath( "//div[@id='cdfdd-components-components']//tr[2]" ), "class" );
		assertEquals( "child-of-CHARTS initialized collapsed ui-state-active", text );

		/*
		 * ## Step 2
		 */
		//Click Advanced Properties
		element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div.advancedProperties.propertiesUnSelected" ) );
		assertNotNull( element );
		element.click();
		element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']//td[@title='Priority for component execution component. Lower values have higher priority']" ) );
		assertNotNull( element );
		element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.cssSelector( "div.advancedProperties.propertiesSelected" ) );
		assertNotNull( element );

		//Find Values Normalized property and set it's value to True
		element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']//td[text()='valuesNormalized']" ) );
		assertNotNull( element );
		element = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']//td[text()='valuesNormalized']/../td[2]" ) );
		assertNotNull( element );
		element.click();
		WebElement normalizedInput = this.elemHelper.WaitForElementPresenceAndVisible( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']//td[text()='valuesNormalized']/../td[2]/form/input" ) );
		normalizedInput.clear();
		normalizedInput.sendKeys( "True" );
		normalizedInput.sendKeys( Keys.ENTER );
		text = this.elemHelper.WaitForElementPresentGetText( driver, By.xpath( "//table[@id='table-cdfdd-components-properties']//td[text()='valuesNormalized']/../td[2]" ) );
		assertEquals( "True", text );

		/*
		 * ## Step 3
		 */
		//Click Preview button
		this.elemHelper.Click( driver, By.id( "previewButton" ) );

		//Wait for fancybox and assert chart is rendered and height legends are to 100
		WebElement elementFrame = this.elemHelper.FindElement( driver, By.xpath( "//iframe" ) );
		WebDriver frame = this.elemHelper.SwitchToFrame( driver, elementFrame );
		this.elemHelper.WaitForElementInvisibility( driver, By.cssSelector( "div.blockUI.blockOverlay" ) );
		element = this.elemHelper.WaitForElementPresenceAndVisible( frame, By.id( "chartprotovis" ) );
		assertNotNull( element );
		element = this.elemHelper.WaitForElementPresenceAndVisible( frame, By.xpath( "//div[@id='chartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][4]/*[local-name()='rect'][2]" ) );
		assertNotNull( element );
		element = this.elemHelper.WaitForElementPresenceAndVisible( frame, By.xpath( "//div[@id='chartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][4]/*[local-name()='rect'][3]" ) );
		assertNotNull( element );
		element = this.elemHelper.WaitForElementPresenceAndVisible( frame, By.xpath( "//div[@id='chartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][4]/*[local-name()='rect'][1]" ) );
		assertNotNull( element );
		element = this.elemHelper.WaitForElementPresenceAndVisible( frame, By.xpath( "//div[@id='chartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][4]/*[local-name()='rect'][4]" ) );
		assertNotNull( element );
		element = this.elemHelper.WaitForElementPresenceAndVisible( frame, By.xpath( "//div[@id='chartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][4]/*[local-name()='rect'][5]" ) );
		assertNotNull( element );
		element = this.elemHelper.WaitForElementPresenceAndVisible( frame, By.xpath( "//div[@id='chartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][4]/*[local-name()='rect'][6]" ) );
		assertNotNull( element );
		element = this.elemHelper.WaitForElementPresenceAndVisible( frame, By.xpath( "//div[@id='chartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][2]/*[local-name()='g']/*[local-name()='g']/*[local-name()='g'][4]/*[local-name()='rect'][7]" ) );
		assertNotNull( element );
		element = this.elemHelper.WaitForElementPresenceAndVisible( frame, By.xpath( "//div[@id='chartprotovis']/*[local-name()='svg' and namespace-uri()='http://www.w3.org/2000/svg']/*[local-name()='g']/*[local-name()='g'][3]/*[local-name()='g']/*[local-name()='g'][5]/*[local-name()='g']//*[local-name()='text'][text()='100']" ) );
		assertNotNull( element );
	}
}
