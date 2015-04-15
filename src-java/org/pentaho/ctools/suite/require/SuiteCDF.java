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
package org.pentaho.ctools.suite.require;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.pentaho.ctools.cdf.require.AutoCompleteBoxComponent;
import org.pentaho.ctools.cdf.require.ButtonComponent;
import org.pentaho.ctools.cdf.require.CheckComponent;
import org.pentaho.ctools.cdf.require.DateInputComponent;
import org.pentaho.ctools.cdf.require.DateRangeInputComponent;
import org.pentaho.ctools.cdf.require.DialComponent;
import org.pentaho.ctools.cdf.require.ExecutePrptComponent;
import org.pentaho.ctools.cdf.require.ExecuteXactionComponent;
import org.pentaho.ctools.cdf.require.JFreeChartComponent;
import org.pentaho.ctools.cdf.require.MetaLayerHomeDashboard;
import org.pentaho.ctools.cdf.require.MonthPickerComponent;
import org.pentaho.ctools.cdf.require.MultiButtonComponent;
import org.pentaho.ctools.cdf.require.OpenFlashChartComponent;
import org.pentaho.ctools.cdf.require.PrptComponent;
import org.pentaho.ctools.cdf.require.RadioComponent;
import org.pentaho.ctools.cdf.require.SchedulePrptComponent;
import org.pentaho.ctools.cdf.require.SelectComponent;
import org.pentaho.ctools.cdf.require.SelectMultiComponent;
import org.pentaho.ctools.cdf.require.TableComponent;
import org.pentaho.ctools.cdf.require.TextAreaInputComponent;
import org.pentaho.ctools.cdf.require.TextComponent;
import org.pentaho.ctools.cdf.require.TextInputComponent;
import org.pentaho.ctools.cdf.require.TimePlotComponent;
import org.pentaho.ctools.cdf.require.TrafficComponent;
import org.pentaho.ctools.cdf.require.VisualizationAPIComponent;
import org.pentaho.ctools.cdf.require.XactionComponent;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  AutoCompleteBoxComponent.class,
  ButtonComponent.class,
  CheckComponent.class,
  //CommentComponent.class,
  DateInputComponent.class,
  DateRangeInputComponent.class,
  DialComponent.class,
  ExecutePrptComponent.class,
  ExecuteXactionComponent.class,
  JFreeChartComponent.class,
  MetaLayerHomeDashboard.class,
  MonthPickerComponent.class,
  MultiButtonComponent.class,
  OpenFlashChartComponent.class,
  PrptComponent.class,
  //QueryComponent.class,
  RadioComponent.class,
  SchedulePrptComponent.class,
  SelectComponent.class,
  SelectMultiComponent.class,
  TableComponent.class,
  TextAreaInputComponent.class,
  TextComponent.class,
  TextInputComponent.class,
  TimePlotComponent.class,
  TrafficComponent.class,
  VisualizationAPIComponent.class,
  XactionComponent.class
})
public class SuiteCDF {}
