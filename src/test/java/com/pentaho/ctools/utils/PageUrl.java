/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2016 by Pentaho : http://www.pentaho.com
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
package com.pentaho.ctools.utils;

import com.pentaho.selenium.BaseTest;

public class PageUrl extends BaseTest {

  //The current base url to navigate between pages.
  private static final String BASE_URL = baseUrl;

  /*###############################################
   #                                              #
   #																							#
   #                     PUC                      #
   #																							#
   #                                              #
   ###############################################*/
  /*
   * PUC
   */
  public static final String PUC = BASE_URL + "Home";
  public static final String PUC_LOCALE_EN_US = BASE_URL + "Home?locale=en-US";
  public static final String PUC_LOGIN = BASE_URL + "Login";

  /*###############################################
   #                                              #
   #																							#
   #                     CDF                      #
   #																							#
   #                                              #
   ###############################################*/
  /*
   * CDF - Legacy
   */
  public static final String SAMPLE_START_HERE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Astart_here.xcdf/generatedContent";
  public static final String SAMPLE_START_HERE_DASHBOARDTYPE_CLEAN = SAMPLE_START_HERE + "?dashboardType=clean";
  // FOLDER - Samples
  public static final String SAMPLE_BLUEPRINT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A20-samples%3Ablueprint%3Ablueprint.xcdf/generatedContent";
  public static final String SAMPLE_BLUEPRINT_DASHBOARDTYPE_CLEAN = SAMPLE_BLUEPRINT + "?dashboardType=clean";
  // FOLDER - Documentation
  public static final String ANALYZER_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:90-AnalyzerComponent:analyzer_component.xcdf/generatedContent";
  public static final String AUTOCOMPLETE_BOX_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:58-AutocompleteBoxComponent:autocomplete_component.xcdf/generatedContent";
  public static final String BUTTON_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A82-ButtonComponent%3Abutton_component.xcdf/generatedContent";
  public static final String CHECK_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A55-CheckComponent%3Acheck_component.xcdf/generatedContent";
  public static final String COMMENT_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A79-CommentsComponent%3Acomments_component.xcdf/generatedContent";
  public static final String DATEINPUT_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:40-DateInputComponent:date_input_component.xcdf/generatedContent";
  public static final String DATERANGEINPUT_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:43-DateRangeInputComponent:date_range_component.xcdf/generatedContent";
  public static final String DIAL_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A25-DialComponent%3Adial_component.xcdf/generatedContent";
  public static final String JFREE_CHART_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A22-JFreeChartComponent%3Ajfreechart_component.xcdf/generatedContent";
  public static final String EXECUTE_ANALYZER_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A91-ExecuteAnalyzerComponent%3Aexecute_analyzer_component.xcdf/generatedContent";
  public static final String EXECUTE_PRPT_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A85-ExecutePrptComponent%3Aexecute_prpt_component.xcdf/generatedContent";
  public static final String EXECUTE_XACTION_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A76-ExecuteXactionComponent%3Aexecute_xaction_component.xcdf/generatedContent";
  public static final String METALAYER_HOME_DASHBOARD = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A20-samples%3Ahome_dashboard_2%3Ahome_dashboard_metalayer.xcdf/generatedContent";
  public static final String MONTH_PICKER_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A46-MonthPickerComponent%3Amonth_picker_component.xcdf/generatedContent";
  public static final String MULTIBUTTON_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A56-MultiButtonComponent%3Amultibutton_component.xcdf/generatedContent";
  public static final String OPEN_FLASH_CHART_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A26-OpenFlashChartComponent%3Aopenflashchart_component.xcdf/generatedContent";
  public static final String PRPT_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A63-PentahoReportingComponent%3Aprpt_component.xcdf/generatedContent";
  public static final String QUERY_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A70-QueryComponent%3Aquery_component.xcdf/generatedContent";
  public static final String RADIO_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A52-RadioComponent%3Aradio_component.xcdf/generatedContent";
  public static final String SCHEDULE_PRPT_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:86-SchedulePrptComponent:schedule_prpt_component.xcdf/generatedContent";
  public static final String SELECT_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A16-SelectComponent%3Aselect_component.xcdf/generatedContent";
  public static final String SELECT_MULTI_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A19-SelectMultiComponent%3Aselect_multi_component.xcdf/generatedContent";
  public static final String TABLE_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:64-TableComponent:table_component.xcdf/generatedContent";
  public static final String TEXT_AREA_INPUT_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A38-TextAreaInputComponent%3Atext_area_input_component.xcdf/generatedContent";
  public static final String TEXT_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A34-TextComponent%3Atext_component.xcdf/generatedContent";
  public static final String TEXT_INPUT_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A37-TextInputComponent%3Atext_input_component.xcdf/generatedContent";
  public static final String TIMEPLOT_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A31-TimePlotComponent%3Atimeplot_component.xcdf/generatedContent";
  public static final String TRAFFIC_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A28-TrafficComponent%3Atraffic_component.xcdf/generatedContent";
  public static final String VISUALIZATION_API_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A60-VisualizationAPIComponent%3Avisualization_component.xcdf/generatedContent";
  public static final String XACTION_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A10-XactionComponent%3Axaction_component.xcdf/generatedContent";
  public static final String XMLA_DISCOVER = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A20-samples%3Aqueries%3AXMLADiscover%3AxmlaDiscover.xcdf/generatedContent";
  public static final String XMLA_QUERY = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A20-samples%3Aqueries%3AXMLA%3Axmla.xcdf/generatedContent";

  /*
   * CDF - REQUIRE
   */
  public static final String ANALYZER_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:pentaho-cdf-require:30-documentation:30-component_reference:10-core:90-AnalyzerComponent:analyzer_component.xcdf/generatedContent";
  public static final String AUTOCOMPLETE_BOX_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A58-AutocompleteBoxComponent%3Aautocomplete_component.xcdf/generatedContent";
  public static final String BUTTON_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A82-ButtonComponent%3Abutton_component.xcdf/generatedContent";
  public static final String CHECK_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A55-CheckComponent%3Acheck_component.xcdf/generatedContent";
  public static final String COMMENT_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A79-CommentsComponent%3Acomments_component.xcdf/generatedContent";
  public static final String DATEINPUT_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A40-DateInputComponent%3Adate_input_component.xcdf/generatedContent";
  public static final String DATERANGEINPUT_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A43-DateRangeInputComponent%3Adate_range_component.xcdf/generatedContent";
  public static final String DIAL_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A25-DialComponent%3Adial_component.xcdf/generatedContent";
  public static final String JFREE_CHART_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A22-JFreeChartComponent%3Ajfreechart_component.xcdf/generatedContent";
  public static final String EXECUTE_ANALYZER_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A91-ExecuteAnalyzerComponent%3Aexecute_analyzer_component.xcdf/generatedContent";
  public static final String EXECUTE_PRPT_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A85-ExecutePrptComponent%3Aexecute_prpt_component.xcdf/generatedContent";
  public static final String EXECUTE_XACTION_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A76-ExecuteXactionComponent%3Aexecute_xaction_component.xcdf/generatedContent";
  public static final String METALAYER_HOME_DASHBOARD_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A20-samples%3Ahome_dashboard_2%3Ahome_dashboard_metalayer.xcdf/generatedContent";
  public static final String MONTH_PICKER_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A46-MonthPickerComponent%3Amonth_picker_component.xcdf/generatedContent";
  public static final String MULTIBUTTON_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A56-MultiButtonComponent%3Amultibutton_component.xcdf/generatedContent";
  public static final String OPEN_FLASH_CHART_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A26-OpenFlashChartComponent%3Aopenflashchart_component.xcdf/generatedContent";
  public static final String PRPT_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A63-PentahoReportingComponent%3Aprpt_component.xcdf/generatedContent";
  public static final String QUERY_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A70-QueryComponent%3Aquery_component.xcdf/generatedContent";
  public static final String RADIO_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A52-RadioComponent%3Aradio_component.xcdf/generatedContent";
  public static final String SCHEDULE_PRPT_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A86-SchedulePrptComponent%3Aschedule_prpt_component.xcdf/generatedContent";
  public static final String SELECT_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A16-SelectComponent%3Aselect_component.xcdf/generatedContent";
  public static final String SELECT_MULTI_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A19-SelectMultiComponent%3Aselect_multi_component.xcdf/generatedContent";
  public static final String TABLE_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A64-TableComponent%3Atable_component.xcdf/generatedContent";
  public static final String TEMPLATE_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A66-TemplateComponent%3Atemplate_component.xcdf/generatedContent";
  public static final String TEXT_AREA_INPUT_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A38-TextareaInputComponent%3Atext_area_input_component.xcdf/generatedContent";
  public static final String TEXT_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A34-TextComponent%3Atext_component.xcdf/generatedContent";
  public static final String TEXT_INPUT_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A37-TextInputComponent%3Atext_input_component.xcdf/generatedContent";
  public static final String TIME_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A31-TimePlotComponent%3Atimeplot_component.xcdf/generatedContent";
  public static final String TRAFFIC_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A28-TrafficComponent%3Atraffic_component.xcdf/generatedContent";
  public static final String VISUALIZATION_API_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A60-VisualizationAPIComponent%3Avisualization_component.xcdf/generatedContent";
  public static final String XACTION_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A10-XactionComponent%3Axaction_component.xcdf/generatedContent";
  public static final String XMLA_DISCOVER_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A20-samples%3Aqueries%3AXMLADiscover%3AxmlaDiscover.xcdf/generatedContent";
  public static final String XMLA_QUERY_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A20-samples%3Aqueries%3AXMLA%3Axmla.xcdf/generatedContent";

  /*
   * CDF - TUTORIALS
   */
  public static final String CDF_TUTORIAL_HOW_TO = BASE_URL + "api/repos/%3Apublic%3Actools-samples%3Acdf-samples%3A0-howToStart%3AhowToStart.xcdf/generatedContent";
  public static final String CDF_TUTORIAL_FIRST_DASHBOARD = BASE_URL + "api/repos/%3Apublic%3Actools-samples%3Acdf-samples%3A1-firstDashboard%3AfirstDashboard.xcdf/generatedContent";
  public static final String CDF_TUTORIAL_REPLACING_SECURE_PROMPT = BASE_URL + "api/repos/%3Apublic%3Actools-samples%3Acdf-samples%3A2-replacingSecurePrompt%3AreplacingSecurePrompt.xcdf/generatedContent";
  public static final String CDF_TUTORIAL_CHARTS = BASE_URL + "api/repos/%3Apublic%3Actools-samples%3Acdf-samples%3A3-charts%3Acharts1.xcdf/generatedContent";
  public static final String CDF_TUTORIAL_TEMPLATING = BASE_URL + "api/repos/%3Apublic%3Actools-samples%3Acdf-samples%3A4-templating%3Atemplating.xcdf/generatedContent";
  public static final String CDF_TUTORIAL_TIMEPLOTS_DATE_PICKERS = BASE_URL + "api/repos/%3Apublic%3Actools-samples%3Acdf-samples%3A5-timeplot%3Atimeplot.xcdf/generatedContent";
  public static final String CDF_TUTORIAL_MONDRIAN_ROLES = BASE_URL + "api/repos/%3Apublic%3Actools-samples%3Acdf-samples%3A6-roles%3AmondrianRoles.xcdf/generatedContent";
  public static final String CDF_TUTORIAL_DIAL_TRAFFIC = BASE_URL + "api/repos/%3Apublic%3Actools-samples%3Acdf-samples%3A7-dialTraffic%3AdialTraffic.xcdf/generatedContent";
  public static final String CDF_TUTORIAL_GEO_DASHBOARD = BASE_URL + "api/repos/%3Apublic%3Actools-samples%3Acdf-samples%3A8-map%3Amap.xcdf/generatedContent";
  public static final String CDF_TUTORIAL_TABLE_SPARKLINES = BASE_URL + "api/repos/%3Apublic%3Actools-samples%3Acdf-samples%3A9-tableSparklines%3AtableSparklines.xcdf/generatedContent";
  public static final String CDF_TUTORIAL_DRILLABLE_DASHBOARD = BASE_URL + "api/repos/%3Apublic%3Actools-samples%3Acdf-samples%3A10-drillable%3Adrillable.xcdf/generatedContent";

  /*###############################################
   #                                              #
   #																							#
   #                     CDE                      #
   #																							#
   #                                              #
   ###############################################*/
  /*
   * CDE - Legacy
   */
  public static final String SAMPLE_DASHBOARD = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Acde_sample1.wcdf/generatedContent";
  public static final String SAMPLE_DASHBOARD_EDIT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Acde_sample1.wcdf/wcdf.edit";
  public static final String CGG_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3ACggComponent%3AcggComponent.wcdf/generatedContent";
  public static final String ADDIN_REFERENCE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3AaddIns.wcdf/generatedContent";
  public static final String ADDIN_REFERENCE_EDIT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3AaddIns.wcdf/wcdf.edit";
  public static final String AJAX_REQUEST_REFERENCE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3AajaxRequest.wcdf/generatedContent";
  public static final String CCCV2_SHOWCASE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3AtestCCCv2-II.wcdf/generatedContent";
  public static final String BULLET_CHART_TEST_CASE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3Accc_bullet.wcdf/generatedContent";
  public static final String DUPLICATE_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3ADuplicateComponent%3AduplicateComponent.wcdf/generatedContent";
  public static final String EXPORT_POPUP_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3AExportPopup%3AExportPopupComponent.wcdf/generatedContent";
  public static final String FILTER_REFERENCE_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3AFilterComponent%3Afilter_reference.wcdf/generatedContent";
  public static final String FILTER_VISUAL_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3AFilterComponent%3Afilter_visual_guide.wcdf/generatedContent";
  public static final String FILTER_ADDIN_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3AFilterComponent%3Afilter_addIn_accordion.wcdf/generatedContent";
  public static final String MAP_COMPONENT_REFERENCE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3ANewMapComponent%3Amaps.wcdf/generatedContent";
  public static final String MAP_COMPONENT_REFERENCE_EDIT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3ANewMapComponent%3Amaps.wcdf/wcdf.edit";
  public static final String MAP_COMPONENT_FULL_TEST = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3ANewMapComponent%3AFullMapTest.wcdf/generatedContent";
  public static final String OLAP_SELECTOR_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3AOlapSelector%3AolapSelector.wcdf/generatedContent";
  public static final String POPUP_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3Apopup.wcdf/generatedContent";
  public static final String WIDGET_REFERENCE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3Awidgets.wcdf/generatedContent";

  /*
  * CDE - REQUIRE
  */
  public static final String SAMPLE_DASHBOARD_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Acde_sample1.wcdf/generatedContent";
  public static final String ADDIN_REFERENCE_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Atests%3AAddIns%3AaddIns.wcdf/generatedContent";
  public static final String ADDIN_REFERENCE_REQUIRE_EDIT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Atests%3AAddIns%3AaddIns.wcdf/wcdf.edit";
  public static final String AJAX_REQUEST_REFERENCE_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Atests%3AAjaxRequest%3AajaxRequest.wcdf/generatedContent";
  public static final String CCCV2_SHOWCASE_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Atests%3AtestCCCv2-II.wcdf/generatedContent";
  public static final String BULLET_CHART_TEST_CASE_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Atests%3Accc_bullet.wcdf/generatedContent";
  public static final String DASHBOARD_MODULE_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Adashboard%3Adashboard_module.xcdf/generatedContent";
  public static final String DASHBOARD_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Atests%3ADashboardComponent%3ADashboardComponent.wcdf/generatedContent";
  public static final String DUPLICATE_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Atests%3ADuplicateComponent%3AduplicateComponent.wcdf/generatedContent";
  public static final String EXPORT_POPUP_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Atests%3AExportPopup%3AExportPopupComponent.wcdf/generatedContent";
  public static final String FILTER_REFERENCE_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Atests%3AFilterComponent%3Afilter_reference.wcdf/generatedContent";
  public static final String FILTER_VISUAL_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Atests%3AFilterComponent%3Afilter_visual_guide.wcdf/generatedContent";
  public static final String FILTER_ADDIN_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Atests%3AFilterComponent%3Afilter_addIn_accordion.wcdf/generatedContent";
  public static final String MAP_COMPONENT_REFERENCE_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Atests%3ANewMapComponent%3Amaps.wcdf/generatedContent";
  public static final String MAP_COMPONENT_FULL_TEST_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Atests%3ANewMapComponent%3AFullMapTest.wcdf/generatedContent";
  public static final String OLAP_SELECTOR_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Atests%3AOlapSelector%3AolapSelector.wcdf/generatedContent";
  public static final String POPUP_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Atests%3APopupComponent%3Apopup.wcdf/generatedContent";

  /*
   * CDE Dashboard
   */
  public static final String CDE_DASHBOARD = BASE_URL + "api/repos/wcdf/new";

  /*
   * CDE Refresh
   */
  public static final String CDE_REFRESH = BASE_URL + "plugin/pentaho-cdf-dd/api/renderer/refresh";

  /*
   * CDE - Tutorials
   */
  public static final String CDE_TUTORIALS_WELCOME = BASE_URL + "api/repos/:public:ctools-samples:CDE Tutorial:01-welcome.wcdf/generatedContent";

  /*
   * CDE - OPEN DEMOS
   */
  public static final String OPEN_DEMOS = BASE_URL + "api/repos/%3Apublic%3Actools-samples%3APublicDemo%3APublicDemo.wcdf/generatedContent?ts=1470326530929";

  /*###############################################
   #                                              #
   #																							#
   #                     CDA                      #
   #																							#
   #                                              #
   ###############################################*/
  /*
   * Cache Manager
   */
  public static final String CDA_CACHE_MANAGER = BASE_URL + "plugin/cda/api/manageCache";
  /*
   * CDA - Samples
   */
  public static final String DATASOURCE_TEST = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Acda%3Acda_test.xcdf/generatedContent";
  public static final String MONDRIAN_JNDI = BASE_URL + "plugin/cda/api/previewQuery?path=%2Fpublic%2Fplugin-samples%2Fcda%2Fcdafiles%2Fmondrian-jndi.cda";
  public static final String OLAP4J_EDIT = BASE_URL + "plugin/cda/api/editFile?path=/public/plugin-samples/cda/cdafiles/olap4j.cda";
  public static final String SQL_STRINGARRAY_JNDI = BASE_URL + "plugin/cda/api/previewQuery?path=/public/plugin-samples/cda/cdafiles/sql-stringArray-jndi.cda";
  public static final String SQL_JDBC = BASE_URL + "plugin/cda/api/previewQuery?path=/public/plugin-samples/cda/cdafiles/sql-jdbc.cda";

  /*###############################################
   #                                              #
   #																							#
   #                     CGG                      #
   #																							#
   #                                              #
   ###############################################*/
  /*
   * CGG - Samples
   */
  public static final String BAR_CHART = BASE_URL + "plugin/cgg/api/services/draw?script=/public/testBarChart.js&outputType=png";
  public static final String DIAL_CHART = BASE_URL + "plugin/cgg/api/services/draw?script=/public/dial.js&outputType=svg&paramvalue=35";
  public static final String SCATTER_CHART = BASE_URL + "plugin/cgg/api/services/draw?script=/public/testScatterChart.js&outputType=svg";

  /*###############################################
   #                                              #
   #																							#
   #                REGRESSIONS                   #
   #																							#
   #                                              #
   ###############################################*/
  /*
   * CDF - Issues
   */
  public static final String ISSUES_CDF_379 = BASE_URL + "api/repos/%3Apublic%3AIssues%3ACDE%3ACDE-379%3AChart2.wcdf/generatedContent";
  public static final String ISSUES_CDF_406 = BASE_URL + "api/repos/%3Apublic%3AIssues%3ACDF%3ACDF-406%3ACDF406.wcdf/generatedContent";
  public static final String ISSUES_CDF_424 = BASE_URL + "api/repos/%3Apublic%3AIssues%3ACDF%3ACDF-424%3ACDF-424.wcdf/generatedContent";
  public static final String ISSUES_CDF_430 = BASE_URL + "api/repos/%3Apublic%3AIssues%3ACDF%3ACDF-430%3ACDE%3Ai18nTest.wcdf/generatedContent";
  public static final String ISSUES_CDF_430_LOCALE_EN_US = ISSUES_CDF_430 + "?locale=en-US";
  public static final String ISSUES_CDF_430_EDIT = BASE_URL + "api/repos/%3Apublic%3AIssues%3ACDF%3ACDF-430%3ACDE%3Ai18nTest.wcdf/edit";
  public static final String ISSUES_CDF_435 = BASE_URL + "api/repos/%3Apublic%3AIssues%3ACDF%3ACDF-435%3AIssue_435.wcdf/generatedContent";
  public static final String ISSUES_CDF_442 = BASE_URL + "api/repos/%3Apublic%3AIssues%3ACDF%3ACDF-442%3ACDF442.wcdf/generatedContent";
  public static final String ISSUES_CDF_469 = BASE_URL + "api/repos/%3Apublic%3AIssues%3ACDF%3ACDF-469%3Acdf-469.wcdf/generatedContent";
  public static final String ISSUES_CDF_474 = BASE_URL + "api/repos/:public:Issues:CDF:CDF-474:CDF-474.wcdf/generatedContent";
  public static final String ISSUES_CDF_501 = BASE_URL + "api/repos/%3Apublic%3AIssues%3ACDF%3ACDF-501%3Aurl_param.wcdf/generatedContent?paramtype=success&type=awesome";
  public static final String ISSUES_CDF_548 = BASE_URL + "api/repos/%3Apublic%3AIssues%3ACDF%3ACDF-548%3ACDF-548.wcdf/generatedContent";
  public static final String ISSUES_CDF_548_EDIT = BASE_URL + "api/repos/%3Apublic%3AIssues%3ACDF%3ACDF-548%3ACDF-548.wcdf/edit";
  public static final String ISSUES_AUTO_INCLUDES = BASE_URL + "api/repos/%3Apublic%3AIssues%3ACDF%3ACDF-595%3ACDF-595.wcdf/generatedContent";
  public static final String ISSUES_TABLE_EXPAND = BASE_URL + "api/repos/%3Apublic%3AIssues%3ACDF%3ATableExpandTest%3AtableExpandTest.wcdf/generatedContent";
  /*
   * CDE - Issues
   */
  public static final String ISSUES_CDE_269 = BASE_URL + "plugin/pentaho-cdf-dd/api/renderer/getHeaders?solution=&path=/public/plugin-samples/pentaho-cdf-dd&file=cde_sample1.wcdf&absolute=true&root=localhost:8080&scheme=https";
  public static final String ISSUES_CDE_342 = BASE_URL + "api/repos/%3Apublic%3AIssues%3ACDE%3ACDE-342%3Atest_simple_ac.wcdf/generatedContent";
  public static final String ISSUES_CDE_347 = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3Accc_bullet.wcdf/edit";
  public static final String ISSUES_CDE_366 = BASE_URL + "api/repos/:public:CDE366.wcdf/generatedContent";
  public static final String ISSUES_CDE_366_EDIT = BASE_URL + "api/repos/:public:CDE366.wcdf/edit";
  public static final String ISSUES_CDE_379 = BASE_URL + "api/repos/%3Apublic%3AIssues%3ACDE%3ACDE-379%3AChart1.wcdf/generatedContent";
  public static final String ISSUES_CDE_379_CDA = BASE_URL + "plugin/cda/api/previewQuery?path=/public/Issues/CDE/CDE-379/Chart1.cda";
  public static final String ISSUES_CDE_379_EDIT = BASE_URL + "api/repos/%3Apublic%3AIssues%3ACDE%3ACDE-379%3AChart1.wcdf/wcdf.edit";
  public static final String ISSUES_CDE_394 = BASE_URL + "api/repos/%3Apublic%3AIssues%3ACDE%3ACDE-394%3ACDE-394%25282%2529.wcdf/generatedContent";
  public static final String ISSUES_CDE_404 = BASE_URL + "plugin/CDE404/api/i18ntest";
  public static final String ISSUES_CDE_404_EDIT = BASE_URL + "plugin/pentaho-cdf-dd/api/renderer/edit?absolute=false&inferScheme=false&file=Test.wcdf&path=%2FCDE404%2Fdashboards%2F&solution=system&mode=edit";
  public static final String ISSUES_CDE_404_EDIT2 = BASE_URL + "plugin/pentaho-cdf-dd/api/renderer/edit?absolute=false&inferScheme=false&file=i18nTest.wcdf&path=%2FCDE404%2Fdashboards%2F&solution=system&mode=edit";
  public static final String ISSUES_CDE_446_CDA = BASE_URL + "plugin/cda/api/previewQuery?path=/public/Issues/CDE/CDE-446/CDE-446.cda";

  /*
   * CDA - Issues
   */
  public static final String ISSUES_CDA_55 = BASE_URL + "plugin/cda/api/previewQuery?path=/public/Issues/CDA/CDA-55/sample-kettle-ParamArray.cda";
  public static final String ISSUES_CDA_100 = BASE_URL + "plugin/cda/api/previewQuery?path=/public/Issues/CDA/CDA-100/CDA-100.cda";
  public static final String ISSUES_CDA_109 = BASE_URL + "plugin/cda/api/editFile?path=/public/Issues/CDA/CDA%20-%20109/.cda";
  public static final String ISSUES_CDA_110 = BASE_URL + "plugin/cda/api/previewQuery?path=/public/Issues/CDA/CDA-110/cda110.cda";
  public static final String ISSUES_CDA_112 = BASE_URL + "plugin/cda/api/previewQuery?path=/public/Issues/CDA/CDA-112/cda112.cda";
  public static final String ISSUES_CDA_118 = BASE_URL + "plugin/cda/api/previewQuery?path=/public/Issues/CDA/CDA-118/sql-jndi.cda";
  public static final String ISSUES_CDA_118_DOQUERY = BASE_URL + "plugin/cda/api/doQuery?paramsales=10000&paramorderDate=2004-03-01&path=%2Fpublic%2FIssues%2FCDA%2FCDA-118%2Fsql-jndi.cda&dataAccessId=1&outputIndexId=1&&outputColumnName=STATUS";
  public static final String ISSUES_CDA_118_COPY = BASE_URL + "plugin/cda/api/previewQuery?path=/public/Issues/CDA/CDA-118/sql-jndi-Copy.cda";
  public static final String ISSUES_CDA_130 = BASE_URL + "plugin/cda/api/previewQuery?path=/public/Issues/CDA/CDA-130/json-scripting.cda";

  /*###############################################
   #                                              #
   #																							#
   #                  SECURITY                    #
   #																							#
   #                                              #
   ###############################################*/
  public static final String SEC_CDE_JACKRABBIT_REPOSITORY = BASE_URL + "plugin/pentaho-cdf-dd/api/resources/system/jackrabbit/repository.xml";
  public static final String SEC_CDE_JACKRABBIT_GET_REPOSITORY = BASE_URL + "plugin/pentaho-cdf-dd/api/resources/get?resource=../jackrabbit/repository.xml";

  /*###############################################
  #                                              #
  #                                              #
  #                   OTHERS                     #
  #                                              #
  #                                              #
  ###############################################*/
  public static final String SPARKL = BASE_URL + "plugin/sparkl/api/main";
  public static final String CTE_TEMPLATE = BASE_URL + "plugin/cte/api/edit?path=/public/plugin-samples/pentaho-cdf/template.html";

}
