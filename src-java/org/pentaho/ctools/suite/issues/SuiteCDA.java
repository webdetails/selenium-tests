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
import org.pentaho.ctools.issues.cda.CDA100;
import org.pentaho.ctools.issues.cda.CDA103;
import org.pentaho.ctools.issues.cda.CDA106;
import org.pentaho.ctools.issues.cda.CDA108;
import org.pentaho.ctools.issues.cda.CDA109;
import org.pentaho.ctools.issues.cda.CDA110;
import org.pentaho.ctools.issues.cda.CDA112;
import org.pentaho.ctools.issues.cda.CDA118;
import org.pentaho.ctools.issues.cda.CDA121;
import org.pentaho.ctools.issues.cda.CDA45;
import org.pentaho.ctools.issues.cda.CDA46;
import org.pentaho.ctools.issues.cda.CDA55;
import org.pentaho.ctools.issues.cda.CDA99;

@RunWith( Suite.class )
@Suite.SuiteClasses( {
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
    CDA110.class,
    CDA112.class,
    CDA118.class,
    CDA121.class } )
public class SuiteCDA {
  //It runs the Suite.SuiteClasses.
}
