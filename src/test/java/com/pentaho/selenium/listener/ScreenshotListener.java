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
package com.pentaho.selenium.listener;

import static com.pentaho.selenium.BaseTest.getDriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class ScreenshotListener extends TestListenerAdapter {

  @Override
  public void onTestFailure( ITestResult failingTest ) {
    try {
      WebDriver driver = getDriver();

      final String className = failingTest.getInstanceName();
      final String testCaseName = failingTest.getName();
      final String dir = className.replace( ".", "/" ) + "/";
      final String createDir = "reports-java/screenshots/" + dir;

      new File( createDir ).mkdirs(); // Insure directory is there

      try (FileOutputStream out = new FileOutputStream( createDir + testCaseName + ".png" )) {
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
