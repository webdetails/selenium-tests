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

public class PluginInfo {

  private String name;
  private String date;
  private String version;
  private String description;
  private String authorName;
  private String authorEmail;
  private String authorCompany;
  private String authorCompanyUrl;
  private static Logger log = LogManager.getLogger( PluginInfo.class );

  public PluginInfo( String name, String date, String version, String description, String authorName,
      String authorEmail, String authorCompany, String authorCompanyUrl ) {
    this.name = name;
    this.date = date;
    this.version = version;
    this.description = description;
    this.authorName = authorName;
    this.authorEmail = authorEmail;
    this.authorCompany = authorCompany;
    this.authorCompanyUrl = authorCompanyUrl;
  }

  /**
   * The method shall return the name of the plugin.
   * 
   * @return The name of the plugin.
   */
  public String getName() {
    return this.name;
  }

  /**
   * The method shall set the name of the plugin.
   * 
   * @param The name of the plugin.
   */
  public void setName( String name ) {
    this.name = name;
  }

  /**
   * The method shall return the date of the plugin.
   * 
   * @return The date of the plugin.
   */
  public String getDate() {
    return this.date;
  }

  /**
   * The method shall set the date of the plugin.
   * 
   * @param The date of the plugin.
   */
  public void setDate( String date ) {
    this.date = date;
  }

  /**
   * The method shall return the version of the plugin.
   * 
   * @return The version of the plugin.
   */
  public String getVersion() {
    return this.version;
  }

  /**
   * The method shall set the version of the plugin.
   * 
   * @param The version of the plugin.
   */
  public void setVersion( String version ) {
    this.version = version;
  }

  /**
   * The method shall return the description of the plugin.
   * 
   * @return The description of the plugin.
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * The method shall set the description of the plugin.
   * 
   * @param The description of the plugin.
   */
  public void setDescription( String description ) {
    this.description = description;
  }

  /**
   * The method shall return the author of the plugin.
   * 
   * @return The author of the plugin.
   */
  public String getAuthorName() {
    return this.authorName;
  }

  /**
   * The method shall set the author of the plugin.
   * 
   * @param The author of the plugin.
   */
  public void setAuthorName( String authorName ) {
    this.authorName = authorName;
  }

  /**
   * The method shall return the author email of the plugin.
   * 
   * @return The author email of the plugin.
   */
  public String getAuthorEmail() {
    return this.authorEmail;
  }

  /**
   * The method shall set the author email of the plugin.
   * 
   * @param The author email of the plugin.
   */
  public void setAuthorEmail( String authorEmail ) {
    this.authorEmail = authorEmail;
  }

  /**
   * The method shall return the author company of the plugin.
   * 
   * @return The author company of the plugin.
   */
  public String getAuthorCompany() {
    return this.authorCompany;
  }

  /**
   * The method shall set the author company of the plugin.
   * 
   * @param The author company of the plugin.
   */
  public void setAuthorCompany( String authorCompany ) {
    this.authorCompany = authorCompany;
  }

  /**
   * The method shall return the author company URL of the plugin.
   * 
   * @return The author company URL of the plugin.
   */
  public String getAuthorCompanyUrl() {
    return this.authorCompanyUrl;
  }

  /**
   * The method shall set the author company URL of the plugin.
   * 
   * @param The author company URL of the plugin.
   */
  public void setAuthorCompanyUrl( String authorCompanyUrl ) {
    this.authorCompanyUrl = authorCompanyUrl;
  }

  /**
   * The method shall print hte plugin attributes.
   * 
   * @param plugin.
   */
  public void PrintPlugin( PluginInfo plugin ) {
    log.info( "name: " + plugin.getName() + ", date: " + plugin.getDate() + ", version: " + plugin.getVersion() + ", description: " + plugin.getDescription() + ", authorName: " + plugin.getAuthorName() + ", authorEmail: " + plugin.getAuthorEmail() + ", authorCompany: " + plugin.getAuthorCompany() + ", authorCompanyUrl: " + plugin.getAuthorCompanyUrl() );
  }

  /**
   * The method shall compare the info on this plugin with another.
   * 
   * @param plugin.
   */
  public boolean equals( Object plugin ) {
    boolean equals = false;
    if ( plugin instanceof PluginInfo ) {
      PluginInfo plug = (PluginInfo) plugin;
      PrintPlugin( this );
      PrintPlugin( plug );
      if ( this.name.equals( plug.getName() ) && this.version.equals( plug.getVersion() ) && this.date.equals( plug.getDate() ) && this.description.equals( plug.getDescription() ) && this.authorName.equals( plug.getAuthorName() ) && this.authorEmail.equals( plug.getAuthorEmail() ) && this.authorCompany.equals( plug.getAuthorCompany() ) && this.authorCompanyUrl.equals( plug.getAuthorCompanyUrl() ) ) {
        equals = true;
      }
    }
    return equals;
  }
}
