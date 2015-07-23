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
  public static final String PUC_LOGIN = BASE_URL + "Login";

  /*
   * CDF
   */
  public static final String AUTOCOMPLETE_BOX_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:58-AutocompleteBoxComponent:autocomplete_component.xcdf/generatedContent";
  public static final String BUTTON_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A82-ButtonComponent%3Abutton_component.xcdf/generatedContent";
  public static final String CHECK_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A55-CheckComponent%3Acheck_component.xcdf/generatedContent";
  public static final String COMMENT_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A79-CommentsComponent%3Acomments_component.xcdf/generatedContent";
  public static final String DATEINPUT_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:40-DateInputComponent:date_input_component.xcdf/generatedContent";
  public static final String JFREE_CHART_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A22-JFreeChartComponent%3Ajfreechart_component.xcdf/generatedContent";
  public static final String EXECUTE_PRPT_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A85-ExecutePrptComponent%3Aexecute_prpt_component.xcdf/generatedContent";
  public static final String PRPT_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A63-PentahoReportingComponent%3Aprpt_component.xcdf/generatedContent";
  public static final String TEXT_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A34-TextComponent%3Atext_component.xcdf/generatedContent";
  public static final String TIMEPLOT_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A31-TimePlotComponent%3Atimeplot_component.xcdf/generatedContent";
  public static final String TRAFFIC_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A28-TrafficComponent%3Atraffic_component.xcdf/generatedContent";

  /*
   * CDF - REQUIRE
   */
  public static final String AUTOCOMPLETE_BOX_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A58-AutocompleteBoxComponent%3Aautocomplete_component.xcdf/generatedContent";
  public static final String BUTTON_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A82-ButtonComponent%3Abutton_component.xcdf/generatedContent";
  public static final String CHECK_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A55-CheckComponent%3Acheck_component.xcdf/generatedContent";
  public static final String COMMENT_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A79-CommentsComponent%3Acomments_component.xcdf/generatedContent";
  public static final String DATEINPUT_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A40-DateInputComponent%3Adate_input_component.xcdf/generatedContent";
  public static final String EXECUTE_PRPT_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A85-ExecutePrptComponent%3Aexecute_prpt_component.xcdf/generatedContent";
  public static final String JFREE_CHART_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A22-JFreeChartComponent%3Ajfreechart_component.xcdf/generatedContent";
  public static final String PRPT_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A63-PentahoReportingComponent%3Aprpt_component.xcdf/generatedContent";
  public static final String TEXT_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A34-TextComponent%3Atext_component.xcdf/generatedContent";
  public static final String TIME_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A31-TimePlotComponent%3Atimeplot_component.xcdf/generatedContent";
  public static final String TRAFFIC_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A28-TrafficComponent%3Atraffic_component.xcdf/generatedContent";

  /*
   * CDE
   */
  public static final String SAMPLE_DASHBOARD = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Acde_sample1.wcdf/generatedContent";
  public static final String ADDIN_REFERENCE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3AaddIns.wcdf/generatedContent";
  public static final String ADDIN_REFERENCE_EDIT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3AaddIns.wcdf/wcdf.edit";
  public static final String BULLET_CHART_TEST_CASE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3Accc_bullet.wcdf/generatedContent";
  public static final String DUPLICATE_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3ADuplicateComponent%3AduplicateComponent.wcdf/generatedContent";
  public static final String EXPORT_POPUP_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3AExportPopup%3AExportPopupComponent.wcdf/generatedContent";
  public static final String MAP_COMPONENT_REFERENCE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3Amaps.wcdf/generatedContent";
  public static final String MAP_COMPONENT_FULL_TEST = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3AFullMapTest.wcdf/generatedContent";

  /*
   * CDE - REQUIRE
   */
  public static final String SAMPLE_DASHBOARD_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Acde_sample1.wcdf/generatedContent";
  public static final String ADDIN_REFERENCE_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Atests%3AAddIns%3AaddIns.wcdf/generatedContent";
  public static final String ADDIN_REFERENCE_REQUIRE_EDIT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Atests%3AAddIns%3AaddIns.wcdf/wcdf.edit";
  public static final String BULLET_CHART_TEST_CASE_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Atests%3Accc_bullet.wcdf/generatedContent";
  public static final String DUPLICATE_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Atests%3ADuplicateComponent%3AduplicateComponent.wcdf/generatedContent";
  public static final String EXPORT_POPUP_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Atests%3AExportPopup%3AExportPopupComponent.wcdf/generatedContent";
  public static final String MAP_COMPONENT_REFERENCE_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Atests%3ANewMapComponent%3Amaps.wcdf/generatedContent";
  public static final String MAP_COMPONENT_FULL_TEST_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Atests%3ANewMapComponent%3AFullMapTest.wcdf/generatedContent";

  /*
   * CDA
   */
  public static final String DATASOURCE_TEST = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Acda%3Acda_test.xcdf/generatedContent";
  public static final String MONDRIAN_JNDI = BASE_URL + "plugin/cda/api/previewQuery?path=%2Fpublic%2Fplugin-samples%2Fcda%2Fcdafiles%2Fmondrian-jndi.cda";

  /*
   * CDE Dashboard
   */
  public static final String CDE_DASHBOARD = BASE_URL + "api/repos/wcdf/new";

  /*
   * CGG 
   */
  public static final String BAR_CHART = BASE_URL + "plugin/cgg/api/services/draw?script=/public/testBarChart.js&outputType=png";

  /*
   * Issues
   */

}
