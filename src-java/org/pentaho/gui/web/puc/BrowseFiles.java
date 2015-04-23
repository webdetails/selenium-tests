package org.pentaho.gui.web.puc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.PUCSettings;

public class BrowseFiles {
  private WebDriver driver;
  private static Logger log = LogManager.getLogger( BrowseFiles.class );

  public BrowseFiles( WebDriver driver ) {
    this.driver = driver;

    GoToBrowseFiles();
  }

  /**
   * This method will navigate to the Browse Files page
   *
   */
  private void GoToBrowseFiles() {
    log.info( "Enter: GoToBrowseFiles" );

    this.driver.get( CToolsTestSuite.getBaseUrl() );
    ElementHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

    this.driver.switchTo().frame( "home.perspective" );
    ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='well sidebar']" ) );

    log.info( "Enter: Click Browse Files Button" );
    // Click in 'Browse Files'
    ElementHelper.Click( this.driver, By.xpath( "//div[@class='well sidebar']/button" ) );
    log.info( "Exit: Click Browse Files Button" );

    log.info( "Enter: Assert browser perspective shown" );
    // Focus Browser Perspective and refresh repository
    this.driver.switchTo().defaultContent();
    assertNotNull( ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "applicationShell" ) ) );
    assertNotNull( ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//iframe[@id='browser.perspective']" ) ) );
    this.driver.switchTo().frame( "browser.perspective" );

    assertNotNull( ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "fileBrowser" ) ) );
    ElementHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='spinner large-spinner']" ) );
    ElementHelper.WaitForElementInvisibility( this.driver, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );

    ElementHelper.Click( this.driver, By.id( "refreshBrowserIcon" ) );
    ElementHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='spinner large-spinner']" ) );
    ElementHelper.WaitForElementInvisibility( this.driver, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );

    // Assert Panels
    assertNotNull( ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='fileBrowserFolders']" ) ) );
    assertNotNull( ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='fileBrowserFiles']" ) ) );
    assertNotNull( ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='fileBrowserButtons']" ) ) );
    log.info( "Exit: Assert browser perspective shown" );
    log.info( "Exit: GoToBrowseFiles" );
  }

  /**
   * This method will empty the Trash
   *
   * It will first check if the Trash is empty and if not, proceed to emptying it.
   *
   * This method must be called from the Browse Files page with the this.driver set to the browser perspective
   *
   */
  public void EmptyTrash() {
    log.info( "Enter: EmptyTrash" );
    log.info( "Enter: Checking if Trash is empty" );
    // Select Trash folder
    assertNotNull( ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@path='.trash']/div/div[@class='title']" ) ) );
    ElementHelper.Click( this.driver, By.xpath( "//div[@path='.trash']/div/div[@class='title']" ) );
    ElementHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='spinner large-spinner']" ) );
    ElementHelper.WaitForElementInvisibility( this.driver, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );

    // If Trash not empty, empty it
    WebElement element = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='fileBrowserFiles']/div[2]/div[@class='emptyFolder']" ), 3 );
    log.info( "Exit: Checking if Trash is empty" );
    if ( element == null ) {
      log.info( "Enter: Emptying Trash" );
      assertNotNull( ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='fileBrowserButtons']//button[@id='purge']" ) ) );
      ElementHelper.Click( this.driver, By.xpath( "//div[@id='fileBrowserButtons']//button[@id='purge']" ) );
      this.driver.switchTo().defaultContent();
      assertNotNull( ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@class='dialogTopCenterInner']" ) ) );
      String text = ElementHelper.WaitForTextPresence( this.driver, By.xpath( "//div[@class='dialogTopCenterInner']/div" ), "Empty Trash" );
      assertEquals( "Empty Trash", text );
      assertNotNull( ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "okButton" ) ) );
      ElementHelper.Click( this.driver, By.id( "okButton" ) );
      this.driver.switchTo().frame( "browser.perspective" );
      ElementHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );
      ElementHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='spinner large-spinner']" ) );
      ElementHelper.WaitForElementInvisibility( this.driver, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );
      assertNotNull( ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='fileBrowserFiles']/div[2]/div" ) ) );
      text = ElementHelper.WaitForTextPresence( this.driver, By.xpath( "//div[@id='fileBrowserFiles']/div[2]/div/span" ), "There are no files in this folder." );
      assertEquals( "There are no files in this folder.", text );
      log.info( "Exit: Emptying Trash" );
    }
    log.info( "Exit: EmptyTrash" );
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
    log.info( "Enter: SelectFile" );
    boolean selected = true;
    String[] folders = path.split( "/" );
    log.info( "Enter: Expanding Path [" + path + "]" );

    String currentFolder = "";
    for ( int i = 1; i < folders.length; i++ ) {
      currentFolder = currentFolder + "/" + folders[i];
      if ( i == folders.length - 1 ) {
        log.info( "Enter: Selecting File" );

        String selector = "//div[@id='fileBrowserFiles']/div[2]/div[@path='" + currentFolder + "']";
        String selectorExpand = "//div[@id='fileBrowserFiles']/div[2]/div[@path='" + currentFolder + "']/div[2]";
        WebElement selectFile = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( selectorExpand ), 3 );
        if ( selectFile != null ) {
          ElementHelper.Click( this.driver, By.xpath( selectorExpand ) );
          ElementHelper.WaitForAttributeValue( this.driver, By.xpath( selector ), "class", "file selected" );
          String text = ElementHelper.GetAttribute( this.driver, By.xpath( selector ), "class" );
          assertEquals( "file selected", text );
          log.info( "Exit: Selecting File" );
        } else {
          log.info( "File " + currentFolder + " was not found" );
          selected = false;
        }
      } else if ( i == folders.length - 2 ) {
        log.info( "Exit: Expanding Path" );
        log.info( "Enter: Selecting Folder" );

        String selector = "//div[@id='fileBrowserFolders']/div[2]//div[@path='" + currentFolder + "']";
        String selectorTitle = "//div[@id='fileBrowserFolders']/div[2]//div[@path='" + currentFolder + "']/div/div[3]";
        WebElement selectFolder = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( selectorTitle ) );
        if ( selectFolder != null ) {
          ElementHelper.Click( this.driver, By.xpath( selectorTitle ) );
          ElementHelper.WaitForAttributeValue( this.driver, By.xpath( selector ), "class", "selected" );
          String text = ElementHelper.GetAttribute( this.driver, By.xpath( selector ), "class" );
          assertTrue( text.contains( "selected" ) );
          log.info( "Enter: Selecting Folder" );
        } else {
          log.info( "Folder " + currentFolder + " was not found" );
          selected = false;
        }
      } else {
        // Check if folder is opened
        String selector = "//div[@id='fileBrowserFolders']/div[2]//div[@path='" + currentFolder + "']";
        String isFolderOpen = ElementHelper.GetAttribute( this.driver, By.xpath( selector ), "class" );
        if ( !isFolderOpen.contains( "open" ) ) {
          // If folder exists select it
          String selectorExpand = "//div[@id='fileBrowserFolders']/div[2]//div[@path='" + currentFolder + "']/div/div[@class='expandCollapse']";
          WebElement folderToExpand = ElementHelper.WaitForElementPresence( this.driver, By.xpath( selectorExpand ) );
          if ( folderToExpand != null ) {
            ElementHelper.Click( this.driver, By.xpath( selectorExpand ) );
            ElementHelper.WaitForAttributeValue( this.driver, By.xpath( selector ), "class", "open" );
            String checkFolderOpen = ElementHelper.GetAttribute( this.driver, By.xpath( selector ), "class" );
            assertTrue( checkFolderOpen.contains( "open" ) );
            log.info( "Exit: Expanding Path" );
          } else {
            log.info( "Folder " + currentFolder + " was not found" );
            selected = false;
          }
        } else {
          log.info( "Folder " + currentFolder + " is already opened" );
        }
      }
    }
    log.info( "Exit: SelectFile" );

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
    log.info( "Enter: SelectFolder" );
    Boolean selected = true;
    String[] folders = path.split( "/" );
    log.info( "Enter: Expanding Path" );
    for ( int i = 1; i < folders.length; i++ ) {
      folders[i] = folders[i - 1] + "/" + folders[i];
      if ( i == folders.length - 1 ) {
        log.info( "Exit: Expanding Path" );
        log.info( "Enter: Selecting Folder" );
        if ( ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']/div/div[3]" ) ) != null ) {
          assertNotNull( ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']/div/div[3]" ) ) );
          ElementHelper.Click( this.driver, By.xpath( "//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']/div/div[3]" ) );
          ElementHelper.WaitForAttributeValue( this.driver, By.xpath( "//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']" ), "class", "selected" );
          String text = ElementHelper.GetAttribute( this.driver, By.xpath( "//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']" ), "class" );
          assertTrue( text.contains( "selected" ) );
        } else {
          log.info( "Folder " + folders[i] + " was not found" );
          selected = false;
        }
        log.info( "Exit: Selecting Folder" );
      } else {
        if ( ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']/div/div[@class='expandCollapse']" ) ) != null ) {
          assertNotNull( ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']/div/div[@class='expandCollapse']" ) ) );
          ElementHelper.Click( this.driver, By.xpath( "//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']/div/div[@class='expandCollapse']" ) );
          ElementHelper.WaitForAttributeValue( this.driver, By.xpath( "//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']" ), "class", "open" );
          String text = ElementHelper.GetAttribute( this.driver, By.xpath( "//div[@id='fileBrowserFolders']/div[2]//div[@path='" + folders[i] + "']" ), "class" );
          assertTrue( text.contains( "open" ) );
        } else {
          log.info( "Folder " + folders[i] + " was not found" );
          selected = false;
        }
      }
      log.info( "Exit: SelectFolder" );
    }
    return selected;
  }

  /**
   * This method will check the Show Hidden Files option on the View menu
   *
   * It has to be called from the Browse Files page and will end with the this.driver set to the browser.perspective
   *
   */
  public void CheckShowHiddenFiles() {
    if ( !PUCSettings.SHOWHIDDENFILES ) {
      // This is being done because this method is called from within browse files and view menu is on PUC
      this.driver.switchTo().defaultContent();

      WebElement element = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "viewmenu" ) );
      assertNotNull( element );
      element.click();
      element = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//td[@id='showHiddenFilesMenuItem']" ) );
      assertNotNull( element );
      String text = element.getAttribute( "class" );
      if ( text.equals( "gwt-MenuItem-checkbox-unchecked" ) ) {
        element = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//td[@id='showHiddenFilesMenuItem']" ) );
        element.click();
        // wait for visibility of waiting pop-up
        ElementHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

        ElementHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='spinner large-spinner']" ) );
        ElementHelper.WaitForElementInvisibility( this.driver, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );

        ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "filemenu" ) );
        element = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "viewmenu" ) );
        assertNotNull( element );
        element.click();

        element = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//td[@id='showHiddenFilesMenuItem']" ) );
        assertNotNull( element );
        text = element.getAttribute( "class" );
        assertEquals( "gwt-MenuItem-checkbox-checked", text );
        element = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "viewmenu" ) );
        assertNotNull( element );
        element.click();
      } else {
        element = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "viewmenu" ) );
        element.click();
      }
    }
    // back to browse files
    assertNotNull( ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//iframe[@id='browser.perspective']" ) ) );
    this.driver.switchTo().frame( "browser.perspective" );

    PUCSettings.SHOWHIDDENFILES = true;
  }

  /**
   * This method will uncheck the Show Hidden Files option on the View menu
   *
   * It has to be called from the Browse Files page and will end with the this.driver set to the browser.perspective
   *
   */
  public void UncheckShowHiddenFiles() {
    // This is being done because this method is called from within browse files and view menu is on PUC
    this.driver.switchTo().defaultContent();

    WebElement element = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "viewmenu" ) );
    assertNotNull( element );
    element.click();
    element = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//td[@id='showHiddenFilesMenuItem']" ) );
    assertNotNull( element );
    String text = element.getAttribute( "class" );
    if ( text.equals( "gwt-MenuItem-checkbox-checked" ) ) {
      element = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//td[@id='showHiddenFilesMenuItem']" ) );
      element.click();
      // wait for visibility of waiting pop-up
      ElementHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

      ElementHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='spinner large-spinner']" ) );
      ElementHelper.WaitForElementInvisibility( this.driver, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );

      ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "filemenu" ) );
      element = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "viewmenu" ) );
      assertNotNull( element );
      element.click();

      element = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//td[@id='showHiddenFilesMenuItem']" ) );
      assertNotNull( element );
      text = element.getAttribute( "class" );
      assertEquals( "gwt-MenuItem-checkbox-unchecked", text );
      element = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "viewmenu" ) );
      assertNotNull( element );
      element.click();
    } else {
      element = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "viewmenu" ) );
      element.click();
    }
    // back to browse files
    assertNotNull( ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//iframe[@id='browser.perspective']" ) ) );
    this.driver.switchTo().frame( "browser.perspective" );

    PUCSettings.SHOWHIDDENFILES = false;
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
      ElementHelper.Click( this.driver, By.id( "deleteButton" ) );

      this.driver.switchTo().defaultContent();
      ElementHelper.Click( this.driver, By.id( "okButton" ) );
      // wait for invisibility of waiting pop-up
      ElementHelper.WaitForElementPresence( this.driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );
      ElementHelper.WaitForElementNotPresent( this.driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );
      // Wait for loading views Folders and Files
      ElementHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='spinner large-spinner']" ) );
      ElementHelper.WaitForElementInvisibility( this.driver, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );

      this.driver.switchTo().frame( "browser.perspective" );
      fileDelete = ElementHelper.WaitForElementNotPresent( this.driver, By.cssSelector( "div.file[path='" + path + "']" ) );
    } else {
      fileDelete = true; // since it does not exist
      log.warn( "The file to delete is not found" );
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
      WebElement listFiles = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='fileBrowserFiles']/div[2]" ) );
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
          Actions action = new Actions( this.driver );
          action.keyDown( Keys.CONTROL );
          for ( WebElement arrayAllFolderFile : arrayAllFolderFiles ) {
            action.click( arrayAllFolderFile );
          }
          action.keyUp( Keys.CONTROL );
          action.build().perform();

          // Here we still in the iframe
          assertNotNull( ElementHelper.WaitForElementVisibility( this.driver, By.id( "deleteButton" ) ) );
          ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "deleteButton" ) ).click();
          // Go back to root html
          this.driver.switchTo().defaultContent();
          assertEquals( ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.cssSelector( "div.gwt-HTML" ) ).getText(), "Are you sure you want to move all selected items to the trash?" );
          ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "okButton" ) ).click();

          // wait for visibility of waiting pop-up
          ElementHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

          ElementHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='spinner large-spinner']" ) );
          ElementHelper.WaitForElementInvisibility( this.driver, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );

        }
      }
    } else {
      log.info( "folder not emptied for it was not found" );
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
    if ( SelectFolder( path ) ) {
      WebElement listFiles = ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.xpath( "//div[@id='fileBrowserFiles']/div[2]" ) );
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
          Actions action = new Actions( this.driver );
          action.keyDown( Keys.CONTROL );
          for ( WebElement arraytheNamedFile : arraytheNamedFiles ) {
            action.click( arraytheNamedFile );
          }
          action.keyUp( Keys.CONTROL );
          action.build().perform();

          // Here we still in the iframe
          assertNotNull( ElementHelper.WaitForElementVisibility( this.driver, By.id( "deleteButton" ) ) );
          ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "deleteButton" ) ).click();
          // Go back to root html
          this.driver.switchTo().defaultContent();
          assertEquals( ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.cssSelector( "div.gwt-HTML" ) ).getText(), "Are you sure you want to move all selected items to the trash?" );
          ElementHelper.WaitForElementPresenceAndVisible( this.driver, By.id( "okButton" ) ).click();

          // wait for visibility of waiting pop-up
          ElementHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='busy-indicator-container waitPopup']" ) );

          ElementHelper.WaitForElementInvisibility( this.driver, By.xpath( "//div[@class='spinner large-spinner']" ) );
          ElementHelper.WaitForElementInvisibility( this.driver, By.xpath( "(//div[@class='spinner large-spinner'])[2]" ) );

        }
      }
    } else {
      log.info( "files were not deleted for folder was not found" );
    }
  }
}
