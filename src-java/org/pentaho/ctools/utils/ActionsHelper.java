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
package org.pentaho.ctools.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ActionsHelper extends Actions {

  //Log instance
  private static Logger log = LogManager.getLogger( ActionsHelper.class );
  //The driver to access the page
  private WebDriver driver;

  /**
   * TODO
   *
   * @param driver
   */
  public ActionsHelper( WebDriver driver ) {
    super( driver );
    this.driver = driver;
  }

  /**
   * TODO
   *
   * @param locator
   */
  public void MouseOver( By locator ) {
    try {
      WebElement element = ElementHelper.WaitForElementPresenceAndVisible( this.driver, locator );
      this.moveToElement( element );
      this.perform();
    } catch ( StaleElementReferenceException sere ) {
      log.warn( "Stale Element Reference Exception" );
      this.MouseOver( locator );
    }
  }
}
