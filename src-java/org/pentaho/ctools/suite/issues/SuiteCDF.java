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
package org.pentaho.ctools.suite.issues;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.pentaho.ctools.issues.cdf.CDF149;
import org.pentaho.ctools.issues.cdf.CDF379;
import org.pentaho.ctools.issues.cdf.CDF406;
import org.pentaho.ctools.issues.cdf.CDF424;
import org.pentaho.ctools.issues.cdf.CDF430;
import org.pentaho.ctools.issues.cdf.CDF435;
import org.pentaho.ctools.issues.cdf.CDF442;
import org.pentaho.ctools.issues.cdf.CDF469;
import org.pentaho.ctools.issues.cdf.CDF474;
import org.pentaho.ctools.issues.cdf.CDF486;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  //Issues CDF
  CDF149.class,
  CDF379.class,
  CDF406.class,
  CDF424.class,
  CDF430.class,
  CDF435.class,
  CDF442.class,
  CDF469.class,
  CDF474.class,
  CDF486.class

})
public class SuiteCDF {}
