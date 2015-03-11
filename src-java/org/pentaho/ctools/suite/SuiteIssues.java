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
import org.pentaho.ctools.issues.cda.CDA106;
import org.pentaho.ctools.issues.cda.CDA108;
import org.pentaho.ctools.issues.cda.CDA109;
import org.pentaho.ctools.issues.cda.CDA112;
import org.pentaho.ctools.issues.cda.CDA121;
import org.pentaho.ctools.issues.cda.CDA45;
import org.pentaho.ctools.issues.cda.CDA46;
import org.pentaho.ctools.issues.cda.CDA55;
import org.pentaho.ctools.issues.cda.CDA99;
import org.pentaho.ctools.issues.cde.CDE149;
import org.pentaho.ctools.issues.cde.CDE269;
import org.pentaho.ctools.issues.cde.CDE270;
import org.pentaho.ctools.issues.cde.CDE286;
import org.pentaho.ctools.issues.cde.CDE342;
import org.pentaho.ctools.issues.cde.CDE347;
import org.pentaho.ctools.issues.cde.CDE367;
import org.pentaho.ctools.issues.cde.CDE384;
import org.pentaho.ctools.issues.cde.CDE388;
import org.pentaho.ctools.issues.cde.CDE392;
import org.pentaho.ctools.issues.cde.CDE394;
import org.pentaho.ctools.issues.cde.CDE395;
import org.pentaho.ctools.issues.cde.CDE396;
import org.pentaho.ctools.issues.cde.CDE399;
import org.pentaho.ctools.issues.cde.CDE402;
import org.pentaho.ctools.issues.cde.CDE403;
import org.pentaho.ctools.issues.cde.CDE404;
import org.pentaho.ctools.issues.cde.CDE406;
import org.pentaho.ctools.issues.cde.CDE407;
import org.pentaho.ctools.issues.cde.CDE408;
import org.pentaho.ctools.issues.cde.CDE410;
import org.pentaho.ctools.issues.cde.CDE412;
import org.pentaho.ctools.issues.cde.CDE413;
import org.pentaho.ctools.issues.cde.CDE417;
import org.pentaho.ctools.issues.cde.CDE424;
import org.pentaho.ctools.issues.cde.CDE425;
import org.pentaho.ctools.issues.cde.CDE432;
import org.pentaho.ctools.issues.cde.CDE438;
import org.pentaho.ctools.issues.cde.CDE439;
import org.pentaho.ctools.issues.cdf.CDF379;
import org.pentaho.ctools.issues.cdf.CDF406;
import org.pentaho.ctools.issues.cdf.CDF424;
import org.pentaho.ctools.issues.cdf.CDF430;
import org.pentaho.ctools.issues.cdf.CDF435;
import org.pentaho.ctools.issues.cdf.CDF442;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  //Issues CDA
  CDA45.class,
  CDA46.class,
  CDA55.class,
  CDA99.class,
  CDA100.class,
  CDA103.class,
  CDA106.class,
  CDA108.class,
  CDA109.class,
  CDA112.class,
  CDA121.class,

  //Issues CDE
  CDE149.class,
  CDE269.class,
  CDE270.class,
  CDE286.class,
  CDE342.class,
  CDE347.class,
  CDE367.class,
  CDE384.class,
  CDE388.class,
  CDE392.class,
  CDE394.class,
  CDE395.class,
  CDE396.class,
  CDE399.class,
  CDE402.class,
  CDE403.class,
  CDE404.class,
  CDE406.class,
  CDE407.class,
  CDE408.class,
  CDE410.class,
  CDE412.class,
  CDE413.class,
  CDE417.class,
  CDE424.class,
  CDE425.class,
  CDE432.class,
  CDE438.class,
  CDE439.class,

  //Issues CDF
  CDF379.class,
  CDF406.class,
  CDF424.class,
  CDF430.class,
  CDF435.class,
  CDF442.class

})
public class SuiteIssues {}
