package com.pentaho.gui.web.puc.sparkl;

public class PluginElement {

  private String name;
  private String type;
  private String template;
  private boolean admin;

  public PluginElement( String name, String type, String template, boolean admin ) {
    this.name = name;
    this.type = type;
    this.template = template;
    this.admin = admin;
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
  public String getType() {
    return this.type;
  }

  /**
   * The method shall set the type of the element.
   * 
   * @param The type of the element.
   */
  public void setType( String type ) {
    this.type = type;
  }

  /**
   * The method shall return the template of the element.
   * 
   * @return The template of the element.
   */
  public String getTemplate() {
    return this.template;
  }

  /**
   * The method shall set the template of the element.
   * 
   * @param The template of the element.
   */
  public void setTemplate( String template ) {
    this.template = template;
  }

  /**
   * The method shall return the admin of the element.
   * 
   * @return The admin of the element.
   */
  public boolean getAdmin() {
    return this.admin;
  }

  /**
   * The method shall set the admin of the element.
   * 
   * @param The admin of the element.
   */
  public void setAdmin( boolean admin ) {
    this.admin = admin;
  }
}
