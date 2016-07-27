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
package com.pentaho.gui.web.ctools.cde;

public class Popup {

  private String title;
  private String message;

  public Popup( String title, String message ) {
    this.title = title;
    this.message = message;
  }

  /**
   * The method shall return the title of the popup.
   * 
   * @return The title in popup.
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * The method shall return the message display in the popup.
   * 
   * @return The message in popup.
   */
  public String getMessage() {
    return this.message;
  }
}
