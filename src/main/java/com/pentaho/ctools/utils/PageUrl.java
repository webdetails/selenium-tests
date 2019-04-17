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
  public static final String SAMPLE_START_HERE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:start_here.xcdf/generatedContent";
  public static final String SAMPLE_START_HERE_DASHBOARDTYPE_CLEAN = SAMPLE_START_HERE + "?dashboardType=clean";
  // FOLDER - Samples
  public static final String SAMPLE_BLUEPRINT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:20-samples:blueprint:blueprint.xcdf/generatedContent";
  public static final String SAMPLE_BLUEPRINT_DASHBOARDTYPE_CLEAN = SAMPLE_BLUEPRINT + "?dashboardType=clean";
  // FOLDER - Documentation
  public static final String ANALYZER_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:legacy:30-documentation:30-component_reference:10-core:90-AnalyzerComponent:analyzer_component.xcdf/generatedContent";
  public static final String AUTOCOMPLETE_BOX_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:legacy:30-documentation:30-component_reference:10-core:58-AutocompleteBoxComponent:autocomplete_component.xcdf/generatedContent";
  public static final String BUTTON_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:legacy:30-documentation:30-component_reference:10-core:82-ButtonComponent:button_component.xcdf/generatedContent";
  public static final String CHECK_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:legacy:30-documentation:30-component_reference:10-core:55-CheckComponent:check_component.xcdf/generatedContent";
  public static final String COMMENT_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:legacy:30-documentation:30-component_reference:10-core:79-CommentsComponent:comments_component.xcdf/generatedContent";
  public static final String DATEINPUT_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:legacy:30-documentation:30-component_reference:10-core:40-DateInputComponent:date_input_component.xcdf/generatedContent";
  public static final String DATERANGEINPUT_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:legacy:30-documentation:30-component_reference:10-core:43-DateRangeInputComponent:date_range_component.xcdf/generatedContent";
  public static final String EXECUTE_ANALYZER_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:legacy:30-documentation:30-component_reference:10-core:91-ExecuteAnalyzerComponent:execute_analyzer_component.xcdf/generatedContent";
  public static final String EXECUTE_PRPT_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:legacy:30-documentation:30-component_reference:10-core:85-ExecutePrptComponent:execute_prpt_component.xcdf/generatedContent";
  public static final String EXECUTE_XACTION_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:legacy:30-documentation:30-component_reference:10-core:76-ExecuteXactionComponent:execute_xaction_component.xcdf/generatedContent";
  public static final String MONTH_PICKER_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:legacy:30-documentation:30-component_reference:10-core:46-MonthPickerComponent:month_picker_component.xcdf/generatedContent";
  public static final String MULTIBUTTON_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:legacy:30-documentation:30-component_reference:10-core:56-MultiButtonComponent:multibutton_component.xcdf/generatedContent";
  public static final String PRPT_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:legacy:30-documentation:30-component_reference:10-core:63-PentahoReportingComponent:prpt_component.xcdf/generatedContent";
  public static final String QUERY_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:legacy:30-documentation:30-component_reference:10-core:70-QueryComponent:query_component.xcdf/generatedContent";
  public static final String RADIO_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:legacy:30-documentation:30-component_reference:10-core:52-RadioComponent:radio_component.xcdf/generatedContent";
  public static final String SCHEDULE_PRPT_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:legacy:30-documentation:30-component_reference:10-core:86-SchedulePrptComponent:schedule_prpt_component.xcdf/generatedContent";
  public static final String SELECT_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:legacy:30-documentation:30-component_reference:10-core:16-SelectComponent:select_component.xcdf/generatedContent";
  public static final String SELECT_MULTI_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:legacy:30-documentation:30-component_reference:10-core:19-SelectMultiComponent:select_multi_component.xcdf/generatedContent";
  public static final String TABLE_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:legacy:30-documentation:30-component_reference:10-core:64-TableComponent:table_component.xcdf/generatedContent";
  public static final String TEXT_AREA_INPUT_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:legacy:30-documentation:30-component_reference:10-core:38-TextAreaInputComponent:text_area_input_component.xcdf/generatedContent";
  public static final String TEXT_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:legacy:30-documentation:30-component_reference:10-core:34-TextComponent:text_component.xcdf/generatedContent";
  public static final String TEXT_INPUT_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:legacy:30-documentation:30-component_reference:10-core:37-TextInputComponent:text_input_component.xcdf/generatedContent";
  public static final String VISUALIZATION_API_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:legacy:30-documentation:30-component_reference:10-core:60-VisualizationAPIComponent:visualization_component.xcdf/generatedContent";
  public static final String XACTION_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:legacy:30-documentation:30-component_reference:10-core:10-XactionComponent:xaction_component.xcdf/generatedContent";

  /*
   * CDF - REQUIRE
   */
  public static final String ANALYZER_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:90-AnalyzerComponent:analyzer_component.xcdf/generatedContent";
  public static final String AUTOCOMPLETE_BOX_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:58-AutocompleteBoxComponent:autocomplete_component.xcdf/generatedContent";
  public static final String BUTTON_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:82-ButtonComponent:button_component.xcdf/generatedContent";
  public static final String CHECK_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:55-CheckComponent:check_component.xcdf/generatedContent";
  public static final String COMMENT_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:79-CommentsComponent:comments_component.xcdf/generatedContent";
  public static final String DATEINPUT_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:40-DateInputComponent:date_input_component.xcdf/generatedContent";
  public static final String DATERANGEINPUT_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:43-DateRangeInputComponent:date_range_component.xcdf/generatedContent";
  public static final String EXECUTE_ANALYZER_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:91-ExecuteAnalyzerComponent:execute_analyzer_component.xcdf/generatedContent";
  public static final String EXECUTE_PRPT_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:85-ExecutePrptComponent:execute_prpt_component.xcdf/generatedContent";
  public static final String EXECUTE_XACTION_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:76-ExecuteXactionComponent:execute_xaction_component.xcdf/generatedContent";
  public static final String MONTH_PICKER_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:46-MonthPickerComponent:month_picker_component.xcdf/generatedContent";
  public static final String MULTIBUTTON_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:56-MultiButtonComponent:multibutton_component.xcdf/generatedContent";
  public static final String PRPT_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:63-PentahoReportingComponent:prpt_component.xcdf/generatedContent";
  public static final String QUERY_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:70-QueryComponent:query_component.xcdf/generatedContent";
  public static final String RADIO_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:52-RadioComponent:radio_component.xcdf/generatedContent";
  public static final String SCHEDULE_PRPT_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:86-SchedulePrptComponent:schedule_prpt_component.xcdf/generatedContent";
  public static final String SELECT_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:16-SelectComponent:select_component.xcdf/generatedContent";
  public static final String SELECT_MULTI_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:19-SelectMultiComponent:select_multi_component.xcdf/generatedContent";
  public static final String TABLE_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:64-TableComponent:table_component.xcdf/generatedContent";
  public static final String TEMPLATE_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:66-TemplateComponent:template_component.xcdf/generatedContent";
  public static final String TEXT_AREA_INPUT_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:38-TextAreaInputComponent:text_area_input_component.xcdf/generatedContent";
  public static final String TEXT_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:34-TextComponent:text_component.xcdf/generatedContent";
  public static final String TEXT_INPUT_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:37-TextInputComponent:text_input_component.xcdf/generatedContent";
  public static final String VISUALIZATION_API_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:60-VisualizationAPIComponent:visualization_component.xcdf/generatedContent";
  public static final String XACTION_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf:30-documentation:30-component_reference:10-core:10-XactionComponent:xaction_component.xcdf/generatedContent";

  /*
   * CDF - TUTORIALS
   */
  public static final String CDF_TUTORIAL_HOW_TO = BASE_URL + "api/repos/:public:ctools-samples:cdf-samples:0-howToStart:howToStart.xcdf/generatedContent";
  public static final String CDF_TUTORIAL_FIRST_DASHBOARD = BASE_URL + "api/repos/:public:ctools-samples:cdf-samples:1-firstDashboard:firstDashboard.xcdf/generatedContent";
  public static final String CDF_TUTORIAL_REPLACING_SECURE_PROMPT = BASE_URL + "api/repos/:public:ctools-samples:cdf-samples:2-replacingSecurePrompt:replacingSecurePrompt.xcdf/generatedContent";
  public static final String CDF_TUTORIAL_CHARTS = BASE_URL + "api/repos/:public:ctools-samples:cdf-samples:3-charts:charts1.xcdf/generatedContent";
  public static final String CDF_TUTORIAL_TEMPLATING = BASE_URL + "api/repos/:public:ctools-samples:cdf-samples:4-templating:templating.xcdf/generatedContent";
  public static final String CDF_TUTORIAL_TIMEPLOTS_DATE_PICKERS = BASE_URL + "api/repos/:public:ctools-samples:cdf-samples:5-timeplot:timeplot.xcdf/generatedContent";
  public static final String CDF_TUTORIAL_MONDRIAN_ROLES = BASE_URL + "api/repos/:public:ctools-samples:cdf-samples:6-roles:mondrianRoles.xcdf/generatedContent";
  public static final String CDF_TUTORIAL_DIAL_TRAFFIC = BASE_URL + "api/repos/:public:ctools-samples:cdf-samples:7-dialTraffic:dialTraffic.xcdf/generatedContent";
  public static final String CDF_TUTORIAL_GEO_DASHBOARD = BASE_URL + "api/repos/:public:ctools-samples:cdf-samples:8-map:map.xcdf/generatedContent";
  public static final String CDF_TUTORIAL_TABLE_SPARKLINES = BASE_URL + "api/repos/:public:ctools-samples:cdf-samples:9-tableSparklines:tableSparklines.xcdf/generatedContent";
  public static final String CDF_TUTORIAL_DRILLABLE_DASHBOARD = BASE_URL + "api/repos/:public:ctools-samples:cdf-samples:10-drillable:drillable.xcdf/generatedContent";

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
  public static final String SAMPLE_DASHBOARD = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:legacy:cde_sample1.wcdf/generatedContent";
  public static final String SAMPLE_DASHBOARD_EDIT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:legacy:cde_sample1.wcdf/wcdf.edit";
  public static final String CGG_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:legacy:tests:CggComponent:cggComponent.wcdf/generatedContent";
  public static final String ADDIN_REFERENCE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:legacy:tests:addIns.wcdf/generatedContent";
  public static final String ADDIN_REFERENCE_EDIT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:legacy:tests:addIns.wcdf/wcdf.edit";
  public static final String AJAX_REQUEST_REFERENCE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:legacy:tests:ajaxRequest.wcdf/generatedContent";
  public static final String BULLET_CHART_TEST_CASE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:legacy:tests:ccc_bullet.wcdf/generatedContent";
  public static final String CCCV2_SHOWCASE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:legacy:tests:testCCCv2-II.wcdf/generatedContent";
  public static final String DUPLICATE_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:legacy:tests:DuplicateComponent:duplicateComponent.wcdf/generatedContent";
  public static final String EXPORT_POPUP_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:legacy:tests:ExportPopup:ExportPopupComponent.wcdf/generatedContent";
  public static final String FILTER_REFERENCE_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:legacy:tests:FilterComponent:filter_reference.wcdf/generatedContent";
  public static final String FILTER_VISUAL_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:legacy:tests:FilterComponent:filter_visual_guide.wcdf/generatedContent";
  public static final String FILTER_ADDIN_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:legacy:tests:FilterComponent:filter_addIn_accordion.wcdf/generatedContent";
  public static final String MAP_COMPONENT_REFERENCE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:legacy:tests:NewMapComponent:maps.wcdf/generatedContent";
  public static final String MAP_COMPONENT_REFERENCE_EDIT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:legacy:tests:NewMapComponent:maps.wcdf/wcdf.edit";
  public static final String MAP_COMPONENT_FULL_TEST = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:legacy:tests:NewMapComponent:FullMapTest.wcdf/generatedContent";
  public static final String OLAP_SELECTOR_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:legacy:tests:OlapSelector:olapSelector.wcdf/generatedContent";
  public static final String POPUP_COMPONENT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:legacy:tests:popup.wcdf/generatedContent";
  public static final String VIZUALIZATION_API_REFRENCE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:legacy:tests:VisualizationAPIreference.wcdf/generatedContent";
  public static final String WIDGET_REFERENCE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:legacy:tests:widgets.wcdf/generatedContent";
  public static final String REAL_TIMTE_DASHBOARD_LEGACY = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:legacy:realtime:real_time.wcdf/generatedContent";

  /*
  * CDE - REQUIRE
  */
  public static final String SAMPLE_DASHBOARD_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:cde_sample1.wcdf/generatedContent";
  public static final String ADDIN_REFERENCE_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:tests:AddIns:addIns.wcdf/generatedContent";
  public static final String ADDIN_REFERENCE_REQUIRE_EDIT = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:tests:AddIns:addIns.wcdf/wcdf.edit";
  public static final String AJAX_REQUEST_REFERENCE_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:tests:AjaxRequest:ajaxRequest.wcdf/generatedContent";
  public static final String BULLET_CHART_TEST_CASE_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:tests:ccc_bullet.wcdf/generatedContent";
  public static final String CCCV2_SHOWCASE_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:tests:testCCCv2-II.wcdf/generatedContent";
  public static final String CGG_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:tests:CggComponent:cggComponent.wcdf/generatedContent";
  public static final String DASHBOARD_MODULE_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:dashboard:dashboard_module.xcdf/generatedContent";
  public static final String DASHBOARD_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:tests:DashboardComponent:DashboardComponent.wcdf/generatedContent";
  public static final String DUPLICATE_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:tests:DuplicateComponent:duplicateComponent.wcdf/generatedContent";
  public static final String EXPORT_POPUP_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:tests:ExportPopup:ExportPopupComponent.wcdf/generatedContent";
  public static final String FILTER_REFERENCE_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:tests:FilterComponent:filter_reference.wcdf/generatedContent";
  public static final String FILTER_VISUAL_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:tests:FilterComponent:filter_visual_guide.wcdf/generatedContent";
  public static final String FILTER_ADDIN_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:tests:FilterComponent:filter_addIn_accordion.wcdf/generatedContent";
  public static final String MAP_COMPONENT_REFERENCE_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:tests:NewMapComponent:maps.wcdf/generatedContent";
  public static final String MAP_COMPONENT_FULL_TEST_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:tests:NewMapComponent:FullMapTest.wcdf/generatedContent";
  public static final String OLAP_SELECTOR_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:tests:OlapSelector:olapSelector.wcdf/generatedContent";
  public static final String POPUP_COMPONENT_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:tests:PopupComponent:popup.wcdf/generatedContent";
  public static final String VIZUALIZATION_API_REFRENCE_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:tests:VisualizationApi:VisualizationAPIreference.wcdf/generatedContent";
  public static final String REAL_TIMTE_DASHBOARD_REQUIRE = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:realtime:real_time.wcdf/generatedContent";

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
  public static final String OPEN_DEMOS = BASE_URL + "api/repos/:public:ctools-samples:PublicDemo:PublicDemo.wcdf/generatedContent?ts=1470326530929";

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
  public static final String DATASOURCE_TEST = BASE_URL + "api/repos/:public:plugin-samples:cda:cda_test.xcdf/generatedContent";
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
  public static final String BAR_CHART_VIZ2 = BASE_URL + "plugin/cgg/api/services/draw?script=/public/testBarChart-viz2.js&outputType=png";
  public static final String BAR_CHART_VIZ3 = BASE_URL + "plugin/cgg/api/services/draw?script=/public/testBarChart-viz3.js&outputType=png";
  public static final String DIAL_CHART = BASE_URL + "plugin/cgg/api/services/draw?script=/public/dial.js&outputType=svg&paramvalue=35";
  public static final String SCATTER_CHART_VIZ2 = BASE_URL + "plugin/cgg/api/services/draw?script=/public/testScatterChart-viz2.js&outputType=svg";
  public static final String SCATTER_CHART_VIZ3 = BASE_URL + "plugin/cgg/api/services/draw?script=/public/testScatterChart-viz3.js&outputType=svg";

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
  public static final String ISSUES_CDF_379 = BASE_URL + "api/repos/:public:Issues:CDE:CDE-379:Chart2.wcdf/generatedContent";
  public static final String ISSUES_CDF_406 = BASE_URL + "api/repos/:public:Issues:CDF:CDF-406:CDF406.wcdf/generatedContent";
  public static final String ISSUES_CDF_424 = BASE_URL + "api/repos/:public:Issues:CDF:CDF-424:CDF-424.wcdf/generatedContent";
  public static final String ISSUES_CDF_430 = BASE_URL + "api/repos/:public:Issues:CDF:CDF-430:CDE:i18nTest.wcdf/generatedContent";
  public static final String ISSUES_CDF_430_LOCALE_EN_US = ISSUES_CDF_430 + "?locale=en-US";
  public static final String ISSUES_CDF_430_EDIT = BASE_URL + "api/repos/:public:Issues:CDF:CDF-430:CDE:i18nTest.wcdf/edit";
  public static final String ISSUES_CDF_435 = BASE_URL + "api/repos/:public:Issues:CDF:CDF-435:Issue_435.wcdf/generatedContent";
  public static final String ISSUES_CDF_442 = BASE_URL + "api/repos/:public:Issues:CDF:CDF-442:CDF442.wcdf/generatedContent";
  public static final String ISSUES_CDF_469 = BASE_URL + "api/repos/:public:Issues:CDF:CDF-469:cdf-469.wcdf/generatedContent";
  public static final String ISSUES_CDF_474 = BASE_URL + "api/repos/:public:Issues:CDF:CDF-474:CDF-474.wcdf/generatedContent";
  public static final String ISSUES_CDF_501 = BASE_URL + "api/repos/:public:Issues:CDF:CDF-501:url_param.wcdf/generatedContent?paramtype=success&type=awesome";
  public static final String ISSUES_CDF_548 = BASE_URL + "api/repos/:public:Issues:CDF:CDF-548:CDF-548.wcdf/generatedContent";
  public static final String ISSUES_CDF_548_EDIT = BASE_URL + "api/repos/:public:Issues:CDF:CDF-548:CDF-548.wcdf/edit";
  public static final String ISSUES_AUTO_INCLUDES = BASE_URL + "api/repos/:public:Issues:CDF:CDF-595:CDF-595.wcdf/generatedContent";
  public static final String ISSUES_TABLE_EXPAND = BASE_URL + "api/repos/:public:Issues:CDF:TableExpandTest:tableExpandTest.wcdf/generatedContent";
  /*
   * CDE - Issues
   */
  public static final String ISSUES_CDE_269 = BASE_URL + "plugin/pentaho-cdf-dd/api/renderer/getHeaders?solution=&path=/public/plugin-samples/pentaho-cdf-dd&file=cde_sample1.wcdf&absolute=true&root=localhost:8080&scheme=https";
  public static final String ISSUES_CDE_342 = BASE_URL + "api/repos/:public:Issues:CDE:CDE-342:test_simple_ac.wcdf/generatedContent";
  public static final String ISSUES_CDE_347 = BASE_URL + "api/repos/:public:plugin-samples:pentaho-cdf-dd:tests:ccc_bullet.wcdf/edit";
  public static final String ISSUES_CDE_366 = BASE_URL + "api/repos/:public:CDE366.wcdf/generatedContent";
  public static final String ISSUES_CDE_366_EDIT = BASE_URL + "api/repos/:public:CDE366.wcdf/edit";
  public static final String ISSUES_CDE_379 = BASE_URL + "api/repos/:public:Issues:CDE:CDE-379:Chart1.wcdf/generatedContent";
  public static final String ISSUES_CDE_379_CDA = BASE_URL + "plugin/cda/api/previewQuery?path=/public/Issues/CDE/CDE-379/Chart1.cda";
  public static final String ISSUES_CDE_379_EDIT = BASE_URL + "api/repos/:public:Issues:CDE:CDE-379:Chart1.wcdf/wcdf.edit";
  public static final String ISSUES_CDE_394 = BASE_URL + "api/repos/:public:Issues:CDE:CDE-394:CDE-394%25282%2529.wcdf/generatedContent";
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
