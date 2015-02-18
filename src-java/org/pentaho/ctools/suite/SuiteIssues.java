/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2014 by Pentaho : http://www.pentaho.com
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
package org.pentaho.ctools.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.pentaho.ctools.issues.cda.CDA100;
import org.pentaho.ctools.issues.cda.CDA103;
import org.pentaho.ctools.issues.cda.CDA108;
import org.pentaho.ctools.issues.cda.CDA109;
import org.pentaho.ctools.issues.cda.CDA45;
import org.pentaho.ctools.issues.cda.CDA46;
import org.pentaho.ctools.issues.cda.CDA55;
import org.pentaho.ctools.issues.cda.CDA99;
import org.pentaho.ctools.issues.cde.CDE342;
import org.pentaho.ctools.issues.cde.CDE347;
import org.pentaho.ctools.issues.cde.CDE367;
import org.pentaho.ctools.issues.cde.CDE384;
import org.pentaho.ctools.issues.cde.CDE392;
import org.pentaho.ctools.issues.cde.CDE394;
import org.pentaho.ctools.issues.cde.CDE395;
import org.pentaho.ctools.issues.cde.CDE396;
import org.pentaho.ctools.issues.cde.CDE399;
import org.pentaho.ctools.issues.cde.CDE402;
import org.pentaho.ctools.issues.cde.CDE407;
import org.pentaho.ctools.issues.cde.CDE408;
import org.pentaho.ctools.issues.cde.CDE413;
import org.pentaho.ctools.issues.cdf.CDF424;
import org.pentaho.ctools.issues.cdf.CDF435;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  //Issues CDA
  CDA45.class,
  CDA46.class,
  CDA55.class,
  CDA99.class,
  CDA100.class,
  CDA103.class,
  CDA108.class,
  CDA109.class,

  //Issues CDE
  CDE342.class,
  CDE347.class,
  CDE367.class,
  CDE384.class,
  CDE392.class,
  CDE394.class,
  CDE395.class,
  CDE396.class,
  CDE399.class,
  CDE402.class,
  CDE407.class,
  CDE408.class,
  CDE413.class,

  //Issues CDF
  CDF424.class,
  CDF435.class

})
public class SuiteIssues {}
