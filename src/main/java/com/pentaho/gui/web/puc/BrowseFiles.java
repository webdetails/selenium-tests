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
package com.pentaho.gui.web.puc;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PUCSettings;
import com.pentaho.ctools.utils.PageUrl;

public class BrowseFiles {

  // The driver
  private WebDriver DRIVER;
  // Access to wrapper for webdriver
  private ElementHelper elemHelper = new ElementHelper();
  // Logging instance
  private static Logger LOG = LogManager.getLogger( BrowseFiles.class );

  public BrowseFiles( WebDriver driver ) {
    this.DRIVER = driver;

    GoToBrowseFiles();
  }

  /**
   * This method will navigate to the Browse Files page
   *
   */
  private void GoToBrowseFiles() {
    LOG.info( "Enter: GoToBrowseFiles" );

    this.DRIVER.get( PageUrl.PUC );
    this.elemHelper.WaitForElementInvisibility( this.DRIVER, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

    this.DRIVER.switchTo().frame( "home.perspective" );
    this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.xpath( "//div[@class='well sidebar']" ) );

    LOG.info( "Enter: Click Browse Files Button" );
    // Click in 'Browse Files'
    this.elemHelper.Click( this.DRIVER, By.xpath( "//div[@class='well sidebar']/button" ) );
    LOG.info( "Exit: Click Browse Files Button" );

    LOG.info( "Enter: Assert browser perspective shown" );
    // Focus Browser Perspective and refresh repository
    this.DRIVER.switchTo().defaultContent();
    assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.id( "applicationShell" ) ) );
    assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.xpath( "//iframe[@id='browser.perspective']" ) ) );
    this.DRIVER.switchTo().frame( "browser.perspective" );

    assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.id( "fileBrowser" ) ) );
    this.elemHelper.WaitForElementInvisibility( this.DRIVER, By.xpath( "//div[@class='spinner large-spinner']" ) );
    this.elemHelper.WaitForElementInvisibility( this.DRIVER, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );

    this.elemHelper.Click( this.DRIVER, By.id( "refreshBrowserIcon" ) );
    this.elemHelper.WaitForElementInvisibility( this.DRIVER, By.xpath( "//div[@class='spinner large-spinner']" ) );
    this.elemHelper.WaitForElementInvisibility( this.DRIVER, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );

    // Assert Panels
    assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.xpath( "//div[@id='fileBrowserFolders']" ) ) );
    assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.xpath( "//div[@id='fileBrowserFiles']" ) ) );
    assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.xpath( "//div[@id='fileBrowserButtons']" ) ) );
    LOG.info( "Exit: Assert browser perspective shown" );
    LOG.info( "Exit: GoToBrowseFiles" );
  }

  /**
   * This method will empty the Trash
   *
   * It will first check if the Trash is empty and if not, proceed to emptying it.
   *
   * This method must be called from the Browse Files page with the driver set to the browser perspective
   *
   */
  public void EmptyTrash() {
    LOG.info( "Enter: EmptyTrash" );
    LOG.info( "Enter: Checking if Trash is empty" );
    // Select Trash folder
    assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.xpath( "//div[@path='.trash']/div/div[@class='title']" ) ) );
    this.elemHelper.Click( this.DRIVER, By.xpath( "//div[@path='.trash']/div/div[@class='title']" ) );
    this.elemHelper.WaitForElementInvisibility( this.DRIVER, By.xpath( "//div[@class='spinner large-spinner']" ) );
    this.elemHelper.WaitForElementInvisibility( this.DRIVER, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );

    // If Trash not empty, empty it
    WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.xpath( "//div[@id='fileBrowserFiles']/div[2]/div[@class='emptyFolder']" ), 3 );
    LOG.info( "Exit: Checking if Trash is empty" );
    if ( element == null ) {
      LOG.info( "Enter: Emptying Trash" );
      assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.xpath( "//div[@id='fileBrowserButtons']//button[@id='purge']" ) ) );
      this.elemHelper.Click( this.DRIVER, By.xpath( "//div[@id='fileBrowserButtons']//button[@id='purge']" ) );
      this.DRIVER.switchTo().defaultContent();
      assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.xpath( "//div[@class='dialogTopCenterInner']" ) ) );
      String text = this.elemHelper.WaitForTextPresence( this.DRIVER, By.xpath( "//div[@class='dialogTopCenterInner']/div" ), "Empty Trash" );
      assertEquals( "Empty Trash", text );
      assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.id( "okButton" ) ) );
      this.elemHelper.Click( this.DRIVER, By.id( "okButton" ) );
      this.DRIVER.switchTo().frame( "browser.perspective" );
      this.elemHelper.WaitForElementInvisibility( this.DRIVER, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );
      this.elemHelper.WaitForElementInvisibility( this.DRIVER, By.xpath( "//div[@class='spinner large-spinner']" ) );
      this.elemHelper.WaitForElementInvisibility( this.DRIVER, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );
      assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.xpath( "//div[@id='fileBrowserFiles']/div[2]/div" ) ) );
      text = this.elemHelper.WaitForTextPresence( this.DRIVER, By.xpath( "//div[@id='fileBrowserFiles']/div[2]/div/span" ), "There are no files in this folder.", 300 );
      assertEquals( "There are no files in this folder.", text );
      LOG.info( "Exit: Emptying Trash" );
    }
    LOG.info( "Exit: EmptyTrash" );
  }

  /**
   * This method will select a file in the repository given the full path to it, including the file name. It will return
   * true if the file was successfully selected
   *
   * Ex. "/public/plugin-samples/pentaho-cdf-dd/cde_sample1.wcdf" in this case the expand arrow will be pressed until
   * the pentaho-cdf-dd folder, that folder will be clicked to show the files contained and cde_sample1.wcdf will be
   * selected on the files table.
   *
   * If in any step no div is found with the corresponding path the method will return false
   *
   * @param path
   * @returns selected
   *
   */
  public boolean SelectFile( String path ) {
    LOG.info( "SelectFile::Enter" );

    boolean selected = false;
    boolean folderSelected = false;
    int lastSlash = path.lastIndexOf( '/' );
    String pathWithOnlyFolders = path.substring( 0, lastSlash );
    String fileToSelect = path.substring( lastSlash + 1 );
    //String[] folders = pathWithOnlyFolders.split( "/" );
    LOG.info( "Enter: Expanding Path [" + path + "]" );
    LOG.info( "Enter: Expanding Folder [" + pathWithOnlyFolders + "]" );
    LOG.info( "Enter: Expanding Select File [" + fileToSelect + "]" );

    folderSelected = SelectFolder( pathWithOnlyFolders );
    if ( folderSelected ) {
      LOG.info( "Enter: Selecting File" );
      String selector = "//div[@id='fileBrowserFiles']/div[2]/div[@path='" + path + "']";
      String selectorExpand = "//div[@id='fileBrowserFiles']/div[2]/div[@path='" + path + "']/div[2]";
      WebElement selectFile = this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.xpath( selectorExpand ), 3 );
      if ( selectFile != null ) {
        this.elemHelper.Click( this.DRIVER, By.xpath( selectorExpand ) );
        this.elemHelper.WaitForAttributeValue( this.DRIVER, By.xpath( selector ), "class", "file selected" );
        String text = this.elemHelper.GetAttribute( this.DRIVER, By.xpath( selector ), "class" );
        assertEquals( "file selected", text );

        selected = true;
      } else {
        LOG.info( "File " + fileToSelect + " was not found" );
      }
      LOG.info( "Exit: Selecting File" );
    } else {
      LOG.warn( "The folder was not selected as expected!" );
    }

    LOG.info( "SelectFile::Exit" );

    return selected;
  }

  /**
   * This method will select a folder in the repository given the full path to it, including the folder name It will
   * return true if the folder was successfully selected
   *
   * Ex. "/public/plugin-samples/pentaho-cdf-dd" in this case the expand arrow will be pressed until the pentaho-cdf-dd
   * folder, that folder will be clicked.
   *
   * If in any step no div is found with the corresponding path the method will return false
   *
   * @param path
   * @returns selected
   *
   */
  public boolean SelectFolder( String path ) {
    LOG.info( "SelectFolder::Enter" );
    Boolean selected = false;
    String[] folders = path.split( "/" );
    String currentFolder = "";
    LOG.info( "Enter: Expanding Path [" + path + "]" );

    for ( int i = 1; i < folders.length; i++ ) {
      currentFolder = currentFolder + "/" + folders[i];
      LOG.info( "Expanding Path [" + currentFolder + "]" );

      if ( i == folders.length - 1 ) {
        String selector = "//div[@id='fileBrowserFolders']/div[2]//div[@path='" + currentFolder + "']";
        String selectorTitle = "//div[@id='fileBrowserFolders']/div[2]//div[@path='" + currentFolder + "']/div/div[3]";
        WebElement selectFolder = this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.xpath( selectorTitle ), 2 );
        if ( selectFolder != null ) {
          this.elemHelper.Click( this.DRIVER, By.xpath( selectorTitle ) );
          this.elemHelper.WaitForAttributeValue( this.DRIVER, By.xpath( selector ), "class", "selected" );
          String text = this.elemHelper.GetAttribute( this.DRIVER, By.xpath( selector ), "class" );
          assertTrue( text.contains( "selected" ) );

          selected = true;
        } else {
          LOG.info( "Folder " + currentFolder + " was not found" );
        }
      } else {
        // Check if folder is opened
        String selector = "//div[@id='fileBrowserFolders']/div[2]//div[@path='" + currentFolder + "']";
        WebElement folderExist = this.elemHelper.WaitForElementPresence( this.DRIVER, By.xpath( selector ), 1 );
        if ( folderExist != null ) {
          String isFolderOpen = this.elemHelper.GetAttribute( this.DRIVER, By.xpath( selector ), "class" );
          if ( !isFolderOpen.contains( "open" ) ) {
            // If folder exists select it
            String selectorExpand = "//div[@id='fileBrowserFolders']/div[2]//div[@path='" + currentFolder + "']/div/div[@class='expandCollapse']";
            WebElement folderToExpand = this.elemHelper.WaitForElementPresence( this.DRIVER, By.xpath( selectorExpand ) );
            if ( folderToExpand != null ) {
              this.elemHelper.Click( this.DRIVER, By.xpath( selectorExpand ) );
              this.elemHelper.WaitForAttributeValue( this.DRIVER, By.xpath( selector ), "class", "open" );
              String checkFolderOpen = this.elemHelper.GetAttribute( this.DRIVER, By.xpath( selector ), "class" );
              assertTrue( checkFolderOpen.contains( "open" ) );

            } else {
              LOG.info( "Folder " + currentFolder + " was not found" );
              break;
            }
          } else {
            LOG.info( "Folder " + currentFolder + " is already opened" );
          }
        } else {
          LOG.info( "Folder " + currentFolder + " was not found" );
          break;
        }
      }
    }
    LOG.info( "SelectFolder::Exit" );

    return selected;
  }

  /**
   * This method will check the Show Hidden Files option on the View menu
   *
   * It has to be called from the Browse Files page and will end with the driver set to the browser.perspective
   *
   */
  public void CheckShowHiddenFiles() {
    if ( !PUCSettings.SHOWHIDDENFILES ) {
      // This is being done because this method is called from within browse files and view menu is on PUC
      this.DRIVER.switchTo().defaultContent();

      WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.id( "viewmenu" ) );
      assertNotNull( element );
      element.click();
      element = this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.xpath( "//td[@id='showHiddenFilesMenuItem']" ) );
      assertNotNull( element );
      String text = element.getAttribute( "class" );
      if ( text.equals( "gwt-MenuItem-checkbox-unchecked" ) ) {
        element = this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.xpath( "//td[@id='showHiddenFilesMenuItem']" ) );
        element.click();
        // wait for visibility of waiting pop-up
        this.elemHelper.WaitForElementInvisibility( this.DRIVER, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

        this.elemHelper.WaitForElementInvisibility( this.DRIVER, By.xpath( "//div[@class='spinner large-spinner']" ) );
        this.elemHelper.WaitForElementInvisibility( this.DRIVER, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );

        this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.id( "filemenu" ) );
        element = this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.id( "viewmenu" ) );
        assertNotNull( element );
        element.click();

        element = this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.xpath( "//td[@id='showHiddenFilesMenuItem']" ) );
        assertNotNull( element );
        text = element.getAttribute( "class" );
        assertEquals( "gwt-MenuItem-checkbox-checked", text );
        element = this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.id( "viewmenu" ) );
        assertNotNull( element );
        element.click();
      } else {
        element = this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.id( "viewmenu" ) );
        element.click();
      }

      // back to browse files
      assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.xpath( "//iframe[@id='browser.perspective']" ) ) );
      this.DRIVER.switchTo().frame( "browser.perspective" );

      PUCSettings.SHOWHIDDENFILES = true;
    }
  }

  /**
   * This method will uncheck the Show Hidden Files option on the View menu
   *
   * It has to be called from the Browse Files page and will end with the driver set to the browser.perspective
   *
   */
  public void UncheckShowHiddenFiles() {
    if ( !PUCSettings.SHOWHIDDENFILES ) {
      // This is being done because this method is called from within browse files and view menu is on PUC
      this.DRIVER.switchTo().defaultContent();

      WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.id( "viewmenu" ) );
      assertNotNull( element );
      element.click();
      element = this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.xpath( "//td[@id='showHiddenFilesMenuItem']" ) );
      assertNotNull( element );
      String text = element.getAttribute( "class" );
      if ( text.equals( "gwt-MenuItem-checkbox-checked" ) ) {
        element = this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.xpath( "//td[@id='showHiddenFilesMenuItem']" ) );
        element.click();
        // wait for visibility of waiting pop-up
        this.elemHelper.WaitForElementInvisibility( this.DRIVER, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

        this.elemHelper.WaitForElementInvisibility( this.DRIVER, By.xpath( "//div[@class='spinner large-spinner']" ) );
        this.elemHelper.WaitForElementInvisibility( this.DRIVER, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );

        this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.id( "filemenu" ) );
        element = this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.id( "viewmenu" ) );
        assertNotNull( element );
        element.click();

        element = this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.xpath( "//td[@id='showHiddenFilesMenuItem']" ) );
        assertNotNull( element );
        text = element.getAttribute( "class" );
        assertEquals( "gwt-MenuItem-checkbox-unchecked", text );
        element = this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.id( "viewmenu" ) );
        assertNotNull( element );
        element.click();
      } else {
        element = this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.id( "viewmenu" ) );
        element.click();
      }
      // back to browse files
      assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.xpath( "//iframe[@id='browser.perspective']" ) ) );
      this.DRIVER.switchTo().frame( "browser.perspective" );

      PUCSettings.SHOWHIDDENFILES = false;
    }
  }

  /**
   * This method will delete a file in the repository given the full path to it, including the file name
   *
   * Ex. "/public/plugin-samples/pentaho-cdf-dd/cde_sample1.wcdf" the SelectFile method will be called with the path
   * provided, it it returns false an info will be shown on the logs saying the file wasn't deleted for it wasn't found.
   * If it returns true, Delete button will be clicked on File Actions, it will wait for popup and confirm the delete
   * action.
   *
   * @param path
   */
  public boolean DeleteFile( String path ) {
    boolean fileDelete = false;

    // Check if the file exist
    if ( SelectFile( path ) ) {
      this.elemHelper.Click( this.DRIVER, By.id( "deleteButton" ) );

      this.DRIVER.switchTo().defaultContent();
      this.elemHelper.Click( this.DRIVER, By.id( "okButton" ) );
      // wait for invisibility of waiting pop-up
      //this.elemHelper.WaitForElementPresence( this.DRIVER, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );
      //this.elemHelper.WaitForElementNotPresent( this.DRIVER, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );
      // Wait for loading views Folders and Files
      this.elemHelper.WaitForElementInvisibility( this.DRIVER, By.xpath( "//div[@class='spinner large-spinner']" ) );
      this.elemHelper.WaitForElementInvisibility( this.DRIVER, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );

      this.DRIVER.switchTo().frame( "browser.perspective" );
      fileDelete = this.elemHelper.WaitForElementNotPresent( this.DRIVER, By.cssSelector( "div.file[path='" + path + "']" ) );
    } else {
      fileDelete = true; // since it does not exist
      LOG.warn( "The file to delete is not found" );
    }

    return fileDelete;
  }

  /**
   * This method will empty a folder in the repository given the full path to it, including the folder name
   *
   * Ex. "/public/plugin-samples/pentaho-cdf-dd" the SelectFolder method will be called with the path provided, it it
   * returns false an info will be shown on the logs saying the file wasn't deleted for it wasn't found. If it returns
   * true, all files shown on the files table will be selected, Delete button will be clicked on File Actions, it will
   * wait for popup and confirm the delete action.
   *
   * @param path
   */
  public void EmptyFolder( String path ) {
    if ( SelectFolder( path ) ) {
      WebElement listFiles = this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.xpath( "//div[@id='fileBrowserFiles']/div[2]" ) );
      List<WebElement> AllFolderFiles = listFiles.findElements( By.xpath( "//div[@class='title']" ) );

      // Check if the widget named exist
      if ( AllFolderFiles != null ) {
        if ( AllFolderFiles.size() > 0 ) {
          WebElement[] arrayAllFolderFiles = new WebElement[AllFolderFiles.size()];
          AllFolderFiles.toArray( arrayAllFolderFiles );

          // Where we want to select three files
          // <the widget>
          // <the widget>.cdfde
          // <the widget>.component.xml
          // To do that we select each file (using the CONTROL key) and delete them.
          Actions action = new Actions( this.DRIVER );
          action.keyDown( Keys.CONTROL );
          for ( WebElement arrayAllFolderFile : arrayAllFolderFiles ) {
            action.click( arrayAllFolderFile );
          }
          action.keyUp( Keys.CONTROL );
          action.build().perform();

          // Here we still in the iframe
          assertNotNull( this.elemHelper.WaitForElementVisibility( this.DRIVER, By.id( "deleteButton" ) ) );
          this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.id( "deleteButton" ) ).click();
          // Go back to root html
          this.DRIVER.switchTo().defaultContent();
          assertEquals( this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.cssSelector( "div.gwt-HTML" ) ).getText(), "Are you sure you want to move all selected items to the trash?" );
          this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.id( "okButton" ) ).click();

          // wait for visibility of waiting pop-up
          this.elemHelper.WaitForElementInvisibility( this.DRIVER, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

          this.elemHelper.WaitForElementInvisibility( this.DRIVER, By.xpath( "//div[@class='spinner large-spinner']" ) );
          this.elemHelper.WaitForElementInvisibility( this.DRIVER, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );

        }
      }
    } else {
      LOG.info( "folder not emptied for it was not found" );
    }
  }

  /**
   * Given the path to a folder this method will delete all files located in that folder that contain a specific string
   * in the name
   *
   * Ex. "/public/plugin-samples/pentaho-cdf-dd" the SelectFolder method will be called with the path provided, it it
   * returns false an info will be shown on the logs saying the file wasn't deleted for it wasn't found.
   *
   * If it returns true, all files containing string "name" provided in the name will be selected, Delete button will be
   * clicked on File Actions, it will wait for popup and confirm the delete action.
   *
   * @param path
   * @param name
   */
  public void DeleteMultipleFilesByName( String path, String name ) {
    LOG.debug( "DeleteMultipleFilesByName::Enter" );
    if ( SelectFolder( path ) ) {
      LOG.debug( "Deleting [" + path + "] with name [" + name + "]" );

      WebElement elemWidgetFile = this.elemHelper.WaitForElementPresence( this.DRIVER, By.cssSelector( "div[title='" + name + ".wcdf']" ), 1 );
      if ( elemWidgetFile != null ) {
        WebElement listFiles = this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.xpath( "//div[@id='fileBrowserFiles']/div[2]" ) );
        List<WebElement> theNamedFiles = listFiles.findElements( By.xpath( "//div[@class='title' and contains(text(),'" + name + "')]" ) );

        // Check if the widget named exist
        if ( theNamedFiles != null ) {
          if ( theNamedFiles.size() > 0 ) {
            WebElement[] arraytheNamedFiles = new WebElement[theNamedFiles.size()];
            theNamedFiles.toArray( arraytheNamedFiles );

            // Where we want to select three files
            // <the widget>
            // <the widget>.cdfde
            // <the widget>.component.xml
            // To do that we select each file (using the CONTROL key) and delete them.
            Actions action = new Actions( this.DRIVER );
            action.keyDown( Keys.CONTROL );
            for ( WebElement arraytheNamedFile : arraytheNamedFiles ) {
              action.click( arraytheNamedFile );
            }
            action.keyUp( Keys.CONTROL );
            action.build().perform();

            // Here we still in the iframe
            assertNotNull( this.elemHelper.WaitForElementVisibility( this.DRIVER, By.id( "deleteButton" ) ) );
            this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.id( "deleteButton" ) ).click();
            // Go back to root html
            this.DRIVER.switchTo().defaultContent();
            assertEquals( this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.cssSelector( "div.gwt-HTML" ) ).getText(), "Are you sure you want to move all selected items to the trash?" );
            this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.id( "okButton" ) ).click();

            // wait for visibility of waiting pop-up
            this.elemHelper.WaitForElementInvisibility( this.DRIVER, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

            assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.id( "applicationShell" ) ) );
            assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.xpath( "//iframe[@id='browser.perspective']" ) ) );
            this.DRIVER.switchTo().frame( "browser.perspective" );

            assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.id( "fileBrowser" ) ) );
            this.elemHelper.WaitForElementInvisibility( this.DRIVER, By.xpath( "//div[@class='spinner large-spinner']" ) );
            this.elemHelper.WaitForElementInvisibility( this.DRIVER, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );

            this.elemHelper.Click( this.DRIVER, By.id( "refreshBrowserIcon" ) );
            this.elemHelper.WaitForElementInvisibility( this.DRIVER, By.xpath( "//div[@class='spinner large-spinner']" ) );
            this.elemHelper.WaitForElementInvisibility( this.DRIVER, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );

            // Assert Panels
            assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.xpath( "//div[@id='fileBrowserFolders']" ) ) );
            assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.xpath( "//div[@id='fileBrowserFiles']" ) ) );
            assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.DRIVER, By.xpath( "//div[@id='fileBrowserButtons']" ) ) );
            LOG.info( "Exit: Assert browser perspective shown" );
          } else {
            LOG.debug( "No file exist!" );
          }
        } else {
          LOG.debug( "Null - No file exist!" );
        }
      } else {
        LOG.warn( "The widget does not exist." );
      }
    } else {
      LOG.info( "Files were not deleted or folder was not found!" );
    }
  }
}
