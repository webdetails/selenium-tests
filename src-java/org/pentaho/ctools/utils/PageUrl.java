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

import org.pentaho.ctools.suite.CToolsTestSuite;

public class PageUrl {

  //The current base url to navigate between pages.
  private static final String BASE_URL = CToolsTestSuite.getBaseUrl();

  /*
   * PUC
   */
  public static final String PUC = BASE_URL + "Home";

  /*
   * CDF
   */
  public static final String AUTOCOMPLETE_BOX_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:58-AutocompleteBoxComponent:autocomplete_component.xcdf/generatedContent";

  /*
   * CDF - REQUIRE
   */
  public static final String AUTOCOMPLETE_BOX_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A58-AutocompleteBoxComponent%3Aautocomplete_component.xcdf/generatedContent";

  /*
   * CDE
   */
  public static final String MAP_COMPONENT_REFERENCE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3Amaps.wcdf/generatedContent";
  public static final String MAP_COMPONENT_FULL_TEST = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3AFullMapTest.wcdf/generatedContent";

  /*
   * CDE - REQUIRE
   */
  public static final String MAP_COMPONENT_REFERENCE_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Atests%3ANewMapComponent%3Amaps.wcdf/generatedContent";
  public static final String MAP_COMPONENT_FULL_TEST_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Atests%3ANewMapComponent%3AFullMapTest.wcdf/generatedContent";
  /*
   * CDA
   */

  /*
   * CDE Dashboard
   */
  public static final String CDE_DASHBOARD = BASE_URL + "api/repos/wcdf/new";

  /*
   * Issues
   */

}
