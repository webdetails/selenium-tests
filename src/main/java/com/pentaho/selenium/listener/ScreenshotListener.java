/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2002 - 2026 by Pentaho Canada Inc. : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2030-06-15
 ******************************************************************************/

package com.pentaho.selenium.listener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.pentaho.selenium.BaseTest;

public class ScreenshotListener extends TestListenerAdapter {

	private final Logger log = LogManager.getLogger( ScreenshotListener.class );

	@Override
	public void onTestFailure( ITestResult failingTest ) {
		try {
			WebDriver driver = BaseTest.getDriver();

			final String className = failingTest.getInstanceName();
			final String testCaseName = failingTest.getName();
			final String dir = className.replace( ".", "/" ) + "/";
			final String createDir = "reports-java/screenshots/" + dir;

			new File( createDir ).mkdirs(); // Insure directory is there

			try ( FileOutputStream out = new FileOutputStream( createDir + testCaseName + ".png" ) ) {
				this.log.debug( "Class: " + className );
				this.log.debug( "Test Case: " + testCaseName );
				this.log.debug( "Exception: " + ( failingTest.getThrowable() != null
				    ? "(Class)" + failingTest.getThrowable().getClass().getName() + "(Message)" + failingTest.getThrowable().getMessage()
				    : "<no exception>" ) );
				out.write( ( (TakesScreenshot) driver ).getScreenshotAs( OutputType.BYTES ) );
			} catch ( final FileNotFoundException fnfe ) {
				fnfe.printStackTrace();
			} catch ( IOException ioe ) {
				ioe.printStackTrace();
			} catch ( final Exception e ) {
				e.printStackTrace();
			}
		} catch ( Exception ex ) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param className
	 * @param testCaseName
	 */
	public void printScreen(String className, String testCaseName){
	  try {
    WebDriver driver = BaseTest.getDriver();

    final String dir = className.replace( ".", "/" ) + "/";
    final String createDir = "reports-java/screenshots/" + dir;

    new File( createDir ).mkdirs(); // Insure directory is there

    try ( FileOutputStream out = new FileOutputStream( createDir + testCaseName + ".png" ) ) {
       out.write( ( (TakesScreenshot) driver ).getScreenshotAs( OutputType.BYTES ) );
    } catch ( final FileNotFoundException fnfe ) {
      fnfe.printStackTrace();
    } catch ( IOException ioe ) {
      ioe.printStackTrace();
    } catch ( final Exception e ) {
      e.printStackTrace();
    }
  } catch ( Exception ex ) {
    ex.printStackTrace();
  }
	}
}
