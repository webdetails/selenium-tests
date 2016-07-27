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
package com.pentaho.gui.web.puc.sparkl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PluginCard {

  private String name;
  private String id;
  private String description;
  private static Logger log = LogManager.getLogger( PluginCard.class );

  public PluginCard( String name, String id, String description ) {
    this.name = name;
    this.id = id;
    this.description = description;
  }

  /**
   * The method shall return the name of the element.
   * 
   * @return The name of the element.
   */
  public String getName() {
    return this.name;
  }

  /**
   * The method shall set the name of the element.
   * 
   * @param The name of the element.
   */
  public void setName( String name ) {
    this.name = name;
  }

  /**
   * The method shall return the type of the element.
   * 
   * @return The type of the element.
   */
  public String getId() {
    return this.id;
  }

  /**
   * The method shall set the type of the element.
   * 
   * @param The type of the element.
   */
  public void setId( String id ) {
    this.id = id;
  }

  /**
   * The method shall return the template of the element.
   * 
   * @return The template of the element.
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * The method shall set the template of the element.
   * 
   * @param The template of the element.
   */
  public void setDescription( String description ) {
    this.description = description;
  }

  /**
   * The method shall print the plugin Card attributes.
   * 
   * @param plugin.
   */
  public void PrintPlugin( PluginCard plugin ) {
    log.info( "name: " + plugin.getName() + ", id: " + plugin.getId() + ", description: " + plugin.getDescription() );
  }

  /**
   * The method shall compare the info on this plugin with another.
   * 
   * @param plugin.
   */
  public boolean equals( Object plugin ) {
    boolean equals = false;
    if ( plugin instanceof PluginCard ) {
      PluginCard plug = (PluginCard) plugin;
      PrintPlugin( this );
      PrintPlugin( plug );
      if ( this.name.equals( plug.getName() ) && this.id.equals( plug.getId() ) && this.description.equals( plug.getDescription() ) ) {
        equals = true;
      }
    }
    return equals;
  }
}
