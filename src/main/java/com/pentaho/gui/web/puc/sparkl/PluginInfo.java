package com.pentaho.gui.web.puc.sparkl;

public class PluginInfo {

  private String name;
  private String date;
  private String version;
  private String description;
  private String authorName;
  private String authorEmail;
  private String authorCompany;
  private String authorCompanyUrl;

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
   * The method shall return the anme of the plugin.
   * 
   * @return The name of the plugin.
   */
  public String getName() {
    return this.name;
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
   * The method shall return the version of the plugin.
   * 
   * @return The version of the plugin.
   */
  public String getVersion() {
    return this.version;
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
   * The method shall return the author of the plugin.
   * 
   * @return The author of the plugin.
   */
  public String getAuthorName() {
    return this.authorName;
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
   * The method shall return the author company of the plugin.
   * 
   * @return The author company of the plugin.
   */
  public String getAuthorCompany() {
    return this.authorCompany;
  }

  /**
   * The method shall return the author company URL of the plugin.
   * 
   * @return The author company URL of the plugin.
   */
  public String getAuthorCompanyUrl() {
    return this.authorCompanyUrl;
  }
}
