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
