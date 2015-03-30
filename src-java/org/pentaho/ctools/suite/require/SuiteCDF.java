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
import org.pentaho.ctools.cdf.AutoCompleteBoxComponent;
import org.pentaho.ctools.cdf.ButtonComponent;
import org.pentaho.ctools.cdf.CheckComponent;
import org.pentaho.ctools.cdf.CommentComponent;
import org.pentaho.ctools.cdf.DateInputComponent;
import org.pentaho.ctools.cdf.DateRangeInputComponent;
import org.pentaho.ctools.cdf.DialComponent;
import org.pentaho.ctools.cdf.ExecutePrptComponent;
import org.pentaho.ctools.cdf.ExecuteXactionComponent;
import org.pentaho.ctools.cdf.JFreeChartComponent;
import org.pentaho.ctools.cdf.MetaLayerHomeDashboard;
import org.pentaho.ctools.cdf.MonthPickerComponent;
import org.pentaho.ctools.cdf.MultiButtonComponent;
import org.pentaho.ctools.cdf.OpenFlashChartComponent;
import org.pentaho.ctools.cdf.PrptComponent;
import org.pentaho.ctools.cdf.RadioComponent;
import org.pentaho.ctools.cdf.SchedulePrptComponent;
import org.pentaho.ctools.cdf.SelectComponent;
import org.pentaho.ctools.cdf.SelectMultiComponent;
import org.pentaho.ctools.cdf.TableComponent;
import org.pentaho.ctools.cdf.TextAreaInputComponent;
import org.pentaho.ctools.cdf.TextInputComponent;
import org.pentaho.ctools.cdf.TimePlotComponent;
import org.pentaho.ctools.cdf.TrafficComponent;
import org.pentaho.ctools.cdf.VisualizationAPIComponent;
import org.pentaho.ctools.cdf.XactionComponent;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  AutoCompleteBoxComponent.class,
  ButtonComponent.class,
  CheckComponent.class,
  CommentComponent.class,
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
  org.pentaho.ctools.cdf.TextComponent.class,
  TextInputComponent.class,
  TimePlotComponent.class,
  TrafficComponent.class,
  VisualizationAPIComponent.class,
  XactionComponent.class
})
public class SuiteCDF {}
