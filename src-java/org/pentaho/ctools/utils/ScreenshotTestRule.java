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
package org.pentaho.ctools.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * The code was extracted from:
 * http://blogs.steeplesoft.com/posts/2012/01/24/grabbing-screenshots-of-failed-selenium-tests/
 * 
 * @author webdetails
 */
public class ScreenshotTestRule implements MethodRule {

  WebDriver driver;

  public ScreenshotTestRule( final WebDriver inDriver ) {
    this.driver = inDriver;
  }

  @Override
  public Statement apply( final Statement statement, final FrameworkMethod frameworkMethod, final Object o ) {
    return new Statement() {

      @Override
      public void evaluate() throws Throwable {
        try {
          statement.evaluate();
        } catch ( final Throwable t ) {
          final String packageName = o.getClass().getPackage().getName();
          final String className = o.getClass().getSimpleName();
          final String dir = packageName + "/" + className + "/";
          this.captureScreenshot( dir, frameworkMethod.getName() );
          throw t; // rethrow to allow the failure to be reported to JUnit
        }
      }

      public void captureScreenshot( final String dir, final String fileName ) {
        try {
          final String createDir = "reports-java/" + dir;
          new File( createDir ).mkdirs(); // Insure directory is there
          FileOutputStream out = null;
          try {
            out = new FileOutputStream( createDir + "screenshot-" + fileName + ".png" );
            out.write( ( (TakesScreenshot) ScreenshotTestRule.this.driver ).getScreenshotAs( OutputType.BYTES ) );
            out.close();
          } catch ( final FileNotFoundException fnfe ) {
            // File not found
          } catch ( final Exception e ) {
            out.close();
          }
        } catch ( final Exception e ) {
          // No need to crash the tests if the screenshot fails
        }
      }
    };
  }
}
