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
package com.pentaho.gui.web.puc;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.PUCSettings;
import com.pentaho.ctools.utils.PageUrl;

public class BrowseFiles {
  // The driver
  private final WebDriver driver;
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Logging instance
  private final Logger LOG = LogManager.getLogger( BrowseFiles.class );

  public BrowseFiles( final WebDriver driver ) {
    this.driver = driver;

    this.GoToBrowseFiles();
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
      this.elemHelper.SwitchToDefault( this.driver );

      WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "viewmenu" ) );
      Assert.assertNotNull( element );
      element.click();
      element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//td[@id='showHiddenFilesMenuItem']" ) );
      Assert.assertNotNull( element );
      String text = element.getAttribute( "class" );
      if ( text.equals( "gwt-MenuItem-checkbox-unchecked" ) ) {
        element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//td[@id='showHiddenFilesMenuItem']" ) );
        element.click();
        // wait for visibility of waiting pop-up
        this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

        this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='spinner large-spinner']" ) );
        this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );

        this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "filemenu" ) );
        element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "viewmenu" ) );
        Assert.assertNotNull( element );
        element.click();

        element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//td[@id='showHiddenFilesMenuItem']" ) );
        Assert.assertNotNull( element );
        text = element.getAttribute( "class" );
        Assert.assertEquals( "gwt-MenuItem-checkbox-checked", text );
        element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "viewmenu" ) );
        Assert.assertNotNull( element );
        element.click();
      } else {
        element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "viewmenu" ) );
        element.click();
      }

      // back to browse files
      Assert.assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//iframe[@id='browser.perspective']" ) ) );
      this.elemHelper.SwitchToFrame( this.driver, "browser.perspective" );

      PUCSettings.SHOWHIDDENFILES = true;
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
  public boolean DeleteFile( final String path ) {
    boolean fileDelete = false;

    // Check if the file exist
    if ( this.SelectFile( path ) ) {
      this.elemHelper.Click( this.driver, By.id( "deleteButton" ) );

      this.elemHelper.SwitchToDefault( this.driver );
      this.elemHelper.Click( this.driver, By.id( "okButton" ) );
      // wait for invisibility of waiting pop-up
      //this.elemHelper.WaitForElementPresence( this.DRIVER, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );
      //this.elemHelper.WaitForElementNotPresent( this.DRIVER, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );
      // Wait for loading views Folders and Files
      this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='spinner large-spinner']" ) );
      this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );

      this.elemHelper.SwitchToFrame( this.driver, "browser.perspective" );
      fileDelete = this.elemHelper.WaitForElementNotPresent( this.driver, By.cssSelector( "div.file[path='" + path + "']" ) );
    } else {
      fileDelete = true; // since it does not exist
      this.LOG.warn( "The file to delete is not found" );
    }

    return fileDelete;
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
  public void DeleteMultipleFilesByName( final String path, final String name ) {
    this.LOG.debug( "DeleteMultipleFilesByName::Enter" );
    if ( this.SelectFolder( path ) ) {
      this.LOG.debug( "Deleting [" + path + "] with name [" + name + "]" );

      final WebElement elemWidgetFile = this.elemHelper.WaitForElementPresence( this.driver, By.cssSelector( "div[title='" + name + ".wcdf']" ), 1 );
      if ( elemWidgetFile != null ) {
        final WebElement listFiles = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='fileBrowserFiles']/div[2]" ) );
        final List<WebElement> theNamedFiles = listFiles.findElements( By.xpath( "//div[@class='title' and contains(text(),'" + name + "')]" ) );

        // Check if the widget named exist
        if ( theNamedFiles != null ) {
          if ( theNamedFiles.size() > 0 ) {
            final WebElement[] arraytheNamedFiles = new WebElement[theNamedFiles.size()];
            theNamedFiles.toArray( arraytheNamedFiles );

            // Where we want to select three files
            // <the widget>
            // <the widget>.cdfde
            // <the widget>.component.xml
            // To do that we select each file (using the CONTROL key) and delete them.
            final Actions action = new Actions( this.driver );
            action.keyDown( Keys.CONTROL );
            for ( final WebElement arraytheNamedFile : arraytheNamedFiles ) {
              action.click( arraytheNamedFile );
            }
            action.keyUp( Keys.CONTROL );
            action.build().perform();

            // Here we still in the iframe
            Assert.assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "deleteButton" ) ) );
            this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "deleteButton" ) ).click();
            // Go back to root html
            this.elemHelper.SwitchToDefault( this.driver );
            Assert.assertEquals( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.cssSelector( "div.gwt-HTML" ) ).getText(), "Are you sure you want to move all selected items to the trash?" );
            this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "okButton" ) ).click();

            // wait for visibility of waiting pop-up
            this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

            Assert.assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "applicationShell" ) ) );
            Assert.assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//iframe[@id='browser.perspective']" ) ) );
            this.elemHelper.SwitchToFrame( this.driver, "browser.perspective" );

            Assert.assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "fileBrowser" ) ) );
            this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='spinner large-spinner']" ) );
            this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );

            this.elemHelper.Click( this.driver, By.id( "refreshBrowserIcon" ) );
            this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='spinner large-spinner']" ) );
            this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );

            // Assert Panels
            Assert.assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='fileBrowserFolders']" ) ) );
            Assert.assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='fileBrowserFiles']" ) ) );
            Assert.assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='fileBrowserButtons']" ) ) );
            this.LOG.info( "Exit: Assert browser perspective shown" );
          } else {
            this.LOG.debug( "No file exist!" );
          }
        } else {
          this.LOG.debug( "Null - No file exist!" );
        }
      } else {
        this.LOG.warn( "The widget does not exist." );
      }
    } else {
      this.LOG.info( "Files were not deleted or folder was not found!" );
    }
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
  public void EmptyFolder( final String path ) {
    if ( this.SelectFolder( path ) ) {
      final WebElement listFiles = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='fileBrowserFiles']/div[2]" ) );
      final List<WebElement> AllFolderFiles = listFiles.findElements( By.xpath( "//div[@class='title']" ) );

      // Check if the widget named exist
      if ( AllFolderFiles != null ) {
        if ( AllFolderFiles.size() > 0 ) {
          final WebElement[] arrayAllFolderFiles = new WebElement[AllFolderFiles.size()];
          AllFolderFiles.toArray( arrayAllFolderFiles );

          // Where we want to select three files
          // <the widget>
          // <the widget>.cdfde
          // <the widget>.component.xml
          // To do that we select each file (using the CONTROL key) and delete them.
          final Actions action = new Actions( this.driver );
          action.keyDown( Keys.CONTROL );
          for ( final WebElement arrayAllFolderFile : arrayAllFolderFiles ) {
            action.click( arrayAllFolderFile );
          }
          action.keyUp( Keys.CONTROL );
          action.build().perform();

          // Here we still in the iframe
          Assert.assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "deleteButton" ) ) );
          this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "deleteButton" ) ).click();
          // Go back to root html
          this.elemHelper.SwitchToDefault( this.driver );
          Assert.assertEquals( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.cssSelector( "div.gwt-HTML" ) ).getText(), "Are you sure you want to move all selected items to the trash?" );
          this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "okButton" ) ).click();

          // wait for visibility of waiting pop-up
          this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

          this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='spinner large-spinner']" ) );
          this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );

        }
      }
    } else {
      this.LOG.info( "folder not emptied for it was not found" );
    }
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
    this.LOG.info( "Enter: EmptyTrash" );
    this.LOG.info( "Enter: Checking if Trash is empty" );
    // Select Trash folder
    Assert.assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@path='.trash']/div/div[@class='title']" ) ) );
    this.elemHelper.Click( this.driver, By.xpath( "//div[@path='.trash']/div/div[@class='title']" ) );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='spinner large-spinner']" ) );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );

    // If Trash not empty, empty it
    final WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='fileBrowserFiles']/div[2]/div[@class='emptyFolder']" ), 3 );
    this.LOG.info( "Exit: Checking if Trash is empty" );
    if ( element == null ) {
      this.LOG.info( "Enter: Emptying Trash" );
      Assert.assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='fileBrowserButtons']//button[@id='purge']" ) ) );
      this.elemHelper.Click( this.driver, By.xpath( "//div[@id='fileBrowserButtons']//button[@id='purge']" ) );
      this.elemHelper.SwitchToDefault( this.driver );
      Assert.assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='dialogTopCenterInner']" ) ) );
      String text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//div[@class='dialogTopCenterInner']/div" ), "Empty Trash" );
      Assert.assertEquals( "Empty Trash", text );
      Assert.assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "okButton" ) ) );
      this.elemHelper.Click( this.driver, By.id( "okButton" ) );
      this.elemHelper.SwitchToFrame( this.driver, "browser.perspective" );
      this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );
      this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='spinner large-spinner']" ) );
      this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );
      Assert.assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='fileBrowserFiles']/div[2]/div" ) ) );
      text = this.elemHelper.WaitForTextPresence( this.driver, By.xpath( "//div[@id='fileBrowserFiles']/div[2]/div/span" ), "There are no files in this folder.", 300 );
      Assert.assertEquals( "There are no files in this folder.", text );
      this.LOG.info( "Exit: Emptying Trash" );
    }
    this.LOG.info( "Exit: EmptyTrash" );
  }

  /**
   * This method will navigate to the Browse Files page
   *
   */
  public void GoToBrowseFiles() {
    this.LOG.info( "Enter: GoToBrowseFiles" );

    this.elemHelper.Get( this.driver, PageUrl.PUC );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

    this.elemHelper.SwitchToFrame( this.driver, "home.perspective" );
    this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='well sidebar']" ) );

    this.LOG.info( "Enter: Click Browse Files Button" );
    // Click in 'Browse Files'
    this.elemHelper.Click( this.driver, By.xpath( "//div[@class='well sidebar']/button" ) );
    this.LOG.info( "Exit: Click Browse Files Button" );

    this.LOG.info( "Enter: Assert browser perspective shown" );
    // Focus Browser Perspective and refresh repository
    this.elemHelper.SwitchToDefault( this.driver );
    Assert.assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "applicationShell" ) ) );
    Assert.assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//iframe[@id='browser.perspective']" ) ) );
    this.elemHelper.SwitchToFrame( this.driver, "browser.perspective" );

    Assert.assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "fileBrowser" ) ) );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='spinner large-spinner']" ) );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );

    this.elemHelper.Click( this.driver, By.id( "refreshBrowserIcon" ) );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='spinner large-spinner']" ) );
    this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );

    // Assert Panels
    Assert.assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='fileBrowserFolders']" ) ) );
    Assert.assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='fileBrowserFiles']" ) ) );
    Assert.assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='fileBrowserButtons']" ) ) );
    this.LOG.info( "Exit: Assert browser perspective shown" );
    this.LOG.info( "Exit: GoToBrowseFiles" );
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
  public boolean SelectFile( final String path ) {
    this.LOG.info( "SelectFile::Enter" );

    boolean selected = false;
    boolean folderSelected = false;
    final int lastSlash = path.lastIndexOf( '/' );
    final String pathWithOnlyFolders = path.substring( 0, lastSlash );
    final String fileToSelect = path.substring( lastSlash + 1 );
    //String[] folders = pathWithOnlyFolders.split( "/" );
    this.LOG.info( "Enter: Expanding Path [" + path + "]" );
    this.LOG.info( "Enter: Expanding Folder [" + pathWithOnlyFolders + "]" );
    this.LOG.info( "Enter: Expanding Select File [" + fileToSelect + "]" );

    folderSelected = this.SelectFolder( pathWithOnlyFolders );
    if ( folderSelected ) {
      this.LOG.info( "Enter: Selecting File" );
      final String selector = "//div[@id='fileBrowserFiles']/div[2]/div[@path='" + path + "']";
      final String selectorExpand = "//div[@id='fileBrowserFiles']/div[2]/div[@path='" + path + "']/div[2]";
      final WebElement selectFile = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( selectorExpand ), 3 );
      if ( selectFile != null ) {
        this.elemHelper.Click( this.driver, By.xpath( selectorExpand ) );
        this.elemHelper.WaitForAttributeValue( this.driver, By.xpath( selector ), "class", "file selected" );
        final String text = this.elemHelper.GetAttribute( this.driver, By.xpath( selector ), "class" );
        Assert.assertEquals( "file selected", text );

        selected = true;
      } else {
        this.LOG.info( "File " + fileToSelect + " was not found" );
      }
      this.LOG.info( "Exit: Selecting File" );
    } else {
      this.LOG.warn( "The folder was not selected as expected!" );
    }

    this.LOG.info( "SelectFile::Exit" );

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
  public boolean SelectFolder( final String path ) {
    this.LOG.info( "SelectFolder::Enter" );
    boolean selected = false;
    final String[] folders = path.split( "/" );
    String currentFolder = "";
    this.LOG.info( "Enter: Expanding Path [" + path + "]" );

    for ( int i = 1; i < folders.length; i++ ) {
      currentFolder = currentFolder + "/" + folders[i];
      this.LOG.info( "Expanding Path [" + currentFolder + "]" );

      if ( i == folders.length - 1 ) {
        final String selector = "//div[@id='fileBrowserFolders']/div[2]//div[@path='" + currentFolder + "']";
        final String selectorTitle = "//div[@id='fileBrowserFolders']/div[2]//div[@path='" + currentFolder + "']/div/div[3]";
        final WebElement selectFolder = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( selectorTitle ), 2 );
        if ( selectFolder != null ) {
          this.elemHelper.Click( this.driver, By.xpath( selectorTitle ) );
          this.elemHelper.WaitForAttributeValue( this.driver, By.xpath( selector ), "class", "selected" );
          final String text = this.elemHelper.GetAttribute( this.driver, By.xpath( selector ), "class" );
          Assert.assertTrue( text.contains( "selected" ) );

          selected = true;
        } else {
          this.LOG.info( "Folder " + currentFolder + " was not found" );
        }
      } else {
        // Check if folder is opened
        final String selector = "//div[@id='fileBrowserFolders']/div[2]//div[@path='" + currentFolder + "']";
        final WebElement folderExist = this.elemHelper.WaitForElementPresence( this.driver, By.xpath( selector ), 1 );
        if ( folderExist != null ) {
          final String isFolderOpen = this.elemHelper.GetAttribute( this.driver, By.xpath( selector ), "class" );
          if ( !isFolderOpen.contains( "open" ) ) {
            // If folder exists select it
            final String selectorExpand = "//div[@id='fileBrowserFolders']/div[2]//div[@path='" + currentFolder + "']/div/div[@class='expandCollapse']";
            final WebElement folderToExpand = this.elemHelper.WaitForElementPresence( this.driver, By.xpath( selectorExpand ) );
            if ( folderToExpand != null ) {
              this.elemHelper.Click( this.driver, By.xpath( selectorExpand ) );
              this.elemHelper.WaitForAttributeValue( this.driver, By.xpath( selector ), "class", "open" );
              final String checkFolderOpen = this.elemHelper.GetAttribute( this.driver, By.xpath( selector ), "class" );
              Assert.assertTrue( checkFolderOpen.contains( "open" ) );

            } else {
              this.LOG.info( "Folder " + currentFolder + " was not found" );
              break;
            }
          } else {
            this.LOG.info( "Folder " + currentFolder + " is already opened" );
          }
        } else {
          this.LOG.info( "Folder " + currentFolder + " was not found" );
          break;
        }
      }
    }
    this.LOG.info( "SelectFolder::Exit" );

    return selected;
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
      this.elemHelper.SwitchToDefault( this.driver );

      WebElement element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "viewmenu" ) );
      Assert.assertNotNull( element );
      element.click();
      element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//td[@id='showHiddenFilesMenuItem']" ) );
      Assert.assertNotNull( element );
      String text = element.getAttribute( "class" );
      if ( text.equals( "gwt-MenuItem-checkbox-checked" ) ) {
        element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//td[@id='showHiddenFilesMenuItem']" ) );
        element.click();
        // wait for visibility of waiting pop-up
        this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

        this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='spinner large-spinner']" ) );
        this.elemHelper.WaitForElementInvisibility( this.driver, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );

        this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "filemenu" ) );
        element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "viewmenu" ) );
        Assert.assertNotNull( element );
        element.click();

        element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//td[@id='showHiddenFilesMenuItem']" ) );
        Assert.assertNotNull( element );
        text = element.getAttribute( "class" );
        Assert.assertEquals( "gwt-MenuItem-checkbox-unchecked", text );
        element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "viewmenu" ) );
        Assert.assertNotNull( element );
        element.click();
      } else {
        element = this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "viewmenu" ) );
        element.click();
      }
      // back to browse files
      Assert.assertNotNull( this.elemHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//iframe[@id='browser.perspective']" ) ) );
      this.elemHelper.SwitchToFrame( this.driver, "browser.perspective" );

      PUCSettings.SHOWHIDDENFILES = false;
    }
  }
}
