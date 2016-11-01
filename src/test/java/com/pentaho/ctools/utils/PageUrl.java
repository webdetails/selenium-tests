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
	public static final String PRPT_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A63-PentahoReportingComponent%3Aprpt_component.xcdf/generatedContent";
	public static final String TEXT_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A34-TextComponent%3Atext_component.xcdf/generatedContent";
	public static final String TIMEPLOT_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A31-TimePlotComponent%3Atimeplot_component.xcdf/generatedContent";
	public static final String TRAFFIC_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A28-TrafficComponent%3Atraffic_component.xcdf/generatedContent";
	public static final String VISUALIZATION_API_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3A30-documentation%3A30-component_reference%3A10-core%3A60-VisualizationAPIComponent%3Avisualization_component.xcdf/generatedContent";
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
	public static final String EXECUTE_ANALYZER_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A91-ExecuteAnalyzerComponent%3Aexecute_analyzer_component.xcdf/generatedContent";
	public static final String EXECUTE_PRPT_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A85-ExecutePrptComponent%3Aexecute_prpt_component.xcdf/generatedContent";
	public static final String EXECUTE_XACTION_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A76-ExecuteXactionComponent%3Aexecute_xaction_component.xcdf/generatedContent";
	public static final String JFREE_CHART_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A22-JFreeChartComponent%3Ajfreechart_component.xcdf/generatedContent";
	public static final String PRPT_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A63-PentahoReportingComponent%3Aprpt_component.xcdf/generatedContent";
	public static final String TEMPLATE_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A66-TemplateComponent%3Atemplate_component.xcdf/generatedContent";
	public static final String TEXT_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A34-TextComponent%3Atext_component.xcdf/generatedContent";
	public static final String TIME_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A31-TimePlotComponent%3Atimeplot_component.xcdf/generatedContent";
	public static final String TRAFFIC_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A28-TrafficComponent%3Atraffic_component.xcdf/generatedContent";
	public static final String VISUALIZATION_API_COMPONENT_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf%3Apentaho-cdf-require%3A30-documentation%3A30-component_reference%3A10-core%3A60-VisualizationAPIComponent%3Avisualization_component.xcdf/generatedContent";
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
	public static final String ADDIN_REFERENCE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3AaddIns.wcdf/generatedContent";
	public static final String ADDIN_REFERENCE_EDIT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3AaddIns.wcdf/wcdf.edit";
	public static final String CCCV2_SHOWCASE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3AtestCCCv2-II.wcdf/generatedContent";
	public static final String BULLET_CHART_TEST_CASE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3Accc_bullet.wcdf/generatedContent";
	public static final String DUPLICATE_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3ADuplicateComponent%3AduplicateComponent.wcdf/generatedContent";
	public static final String EXPORT_POPUP_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3AExportPopup%3AExportPopupComponent.wcdf/generatedContent";
	public static final String FILTER_REFERENCE_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3AFilterComponent%3Afilter_reference.wcdf/generatedContent";
	public static final String FILTER_VISUAL_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3AFilterComponent%3Afilter_visual_guide.wcdf/generatedContent";
	public static final String FILTER_ADDIN_COMPONENT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3AFilterComponent%3Afilter_addIn_accordion.wcdf/generatedContent";
	public static final String MAP_COMPONENT_REFERENCE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3ANewMapComponent%3Amaps.wcdf/generatedContent";
	public static final String MAP_COMPONENT_REFERENCE_EDIT_6x = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3ANewMapComponent%3Amaps.wcdf/edit";
	public static final String MAP_COMPONENT_FULL_TEST = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Atests%3ANewMapComponent%3AFullMapTest.wcdf/generatedContent";

	/*
	* CDE - REQUIRE
	*/
	public static final String SAMPLE_DASHBOARD_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Acde_sample1.wcdf/generatedContent";
	public static final String ADDIN_REFERENCE_REQUIRE = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Atests%3AAddIns%3AaddIns.wcdf/generatedContent";
	public static final String ADDIN_REFERENCE_REQUIRE_EDIT = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Apentaho-cdf-dd%3Apentaho-cdf-dd-require%3Atests%3AAddIns%3AaddIns.wcdf/wcdf.edit";
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

	/*
	 * CDE Dashboard
	 */
	public static final String CDE_DASHBOARD = BASE_URL + "api/repos/wcdf/new";

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
	 * CDA - Samples
	 */
	public static final String DATASOURCE_TEST = BASE_URL + "api/repos/%3Apublic%3Aplugin-samples%3Acda%3Acda_test.xcdf/generatedContent";
	public static final String MONDRIAN_JNDI = BASE_URL + "plugin/cda/api/previewQuery?path=%2Fpublic%2Fplugin-samples%2Fcda%2Fcdafiles%2Fmondrian-jndi.cda";
	public static final String OLAP4J_EDIT = BASE_URL + "plugin/cda/api/editFile?path=/public/plugin-samples/cda/cdafiles/olap4j.cda";

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
	public static final String ISSUES_CDF_424 = BASE_URL + "api/repos/%3Apublic%3AIssues%3ACDF%3ACDF-424%3ACDF-424.wcdf/generatedContent";
	/*
	 * CDE - Issues
	 */
	public static final String ISSUES_CDE_366 = BASE_URL + "api/repos/:public:CDE366.wcdf/generatedContent";
	public static final String ISSUES_CDE_366_EDIT = BASE_URL + "api/repos/:public:CDE366.wcdf/edit";
	public static final String ISSUES_CDE_379 = BASE_URL + "api/repos/%3Apublic%3AIssues%3ACDE%3ACDE-379%3AChart1.wcdf/generatedContent";
	public static final String ISSUES_CDE_379_EDIT = BASE_URL + "api/repos/%3Apublic%3AIssues%3ACDE%3ACDE-379%3AChart1.wcdf/wcdf.edit";

	/*###############################################
	 #                                              #
	 #																							#
	 #                  SECURITY                    #
	 #																							#
	 #                                              #
	 ###############################################*/
	public static final String SEC_CDE_JACKRABBIT_REPOSITORY = BASE_URL + "plugin/pentaho-cdf-dd/api/resources/system/jackrabbit/repository.xml";
	public static final String SEC_CDE_JACKRABBIT_GET_REPOSITORY = BASE_URL + "plugin/pentaho-cdf-dd/api/resources/get?resource=../jackrabbit/repository.xml";
}
