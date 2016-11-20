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
package com.pentaho.ctools.sparkl;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.pentaho.gui.web.puc.Sparkl;
import com.pentaho.gui.web.puc.sparkl.PluginCard;
import com.pentaho.gui.web.puc.sparkl.PluginElement;
import com.pentaho.gui.web.puc.sparkl.PluginInfo;
import com.pentaho.selenium.BaseTest;

public class PluginDetails extends BaseTest {

	private final Logger log = LogManager.getLogger( PluginDetails.class );

	/**
	 * ############################### Test Case 1 ###############################
	 *
	 * Test Case Name:
	 *    Able to edit existing plugin
	 * Description:
	 *    This test will validate the ability to edit existing plugins
	 *
	 * Steps:
	 *    1. Open Sparkl in Edit mode and assert layout
	 *    2. Assert saving of plugin information
	 *    3. Assert layout of elements view
	 *    4. Open existing dashboard and endpoint
	 *    5. Edit existing dashboard
	 *    6. Delete and cancel dashboard and endpoint
	 */
	@Test
	public void tc1_Sparkl_Edit() {
		log.info( "tc1_Sparkl_Edit" );

		/*
		 *  Step 1
		 */
		Sparkl sparkl = new Sparkl( driver );
		sparkl.OpenSparkl();
		PluginCard plugin = new PluginCard( "ATest", "aTest", "testDescription" );
		sparkl.GoToEditPage( plugin );

		/*
		 *  Step 2
		 */
		String testString = "testtesttesttest";

		//Store current info
		PluginInfo currentInfo = sparkl.GetPluginInfo();
		PluginInfo newInfo = new PluginInfo( testString, testString, testString, testString, testString, testString, testString, testString );

		//Enter new info and assert it is saved correctly
		newInfo = sparkl.EnterPluginInfo( newInfo );
		sparkl.SavePluginInfo();
		PluginInfo shownInfo = sparkl.GetPluginInfo();
		assertEquals( shownInfo, newInfo );

		//Enter previous info and assert it is saved correctly
		currentInfo = sparkl.EnterPluginInfo( currentInfo );
		sparkl.SavePluginInfo();
		shownInfo = sparkl.GetPluginInfo();
		assertEquals( shownInfo, currentInfo );

		/*
		 *  Step 3
		 */
		//is create element present and working
		sparkl.GoToElements();
		PluginElement testDash = new PluginElement( "test", "dashboard", "clean", false );
		sparkl.CreateElement( testDash );
		sparkl.DeleteElement( testDash );

		//is filtering working
		sparkl.FilterElements( "dashboard" );
		assertTrue( sparkl.AssertElementsFiltered( "dashboard" ) );
		sparkl.FilterElements( "endpoint" );
		assertTrue( sparkl.AssertElementsFiltered( "endpoint" ) );

		// Go back to all before clicking refresh
		sparkl.FilterElements( "all" );
		assertTrue( sparkl.RefreshElements() );

		/*
		 *  Step 4
		 */
		//Open main dashboard and assert correct page is loaded
		sparkl.OpenSparkl();
		PluginCard pluginSparkl = new PluginCard( "Sparkl", "sparkl", "Create and explore pentaho plugins." );
		sparkl.GoToEditPage( pluginSparkl );
		sparkl.GoToElements();
		PluginElement mainDash = new PluginElement( "main", "dashboard", "clean", true );
		String dashboard = sparkl.ViewDashboard( mainDash );
		assertEquals( dashboard, "main" );

		//Run createelement endpoint and assert correct page is loaded
		PluginElement createEnd = new PluginElement( "createelement", "endpoint", "ktr", true );
		String endpoint = sparkl.RunEndpoint( createEnd );
		assertEquals( endpoint, "createelement" );

		/*
		 *  Step 5
		 */
		//Edit dashboard
		//Open main dashboard and assert correct page is loaded
		PluginElement variableDash = new PluginElement( "variableinfo", "dashboard", "clean", true );
		String editDashboard = sparkl.EditDashboard( variableDash );
		assertEquals( editDashboard, "variableinfo" );
		/*
		 *  Step 6
		 */
		//delete dashboard
		PluginElement pluginDash = new PluginElement( "plugininfo", "dashboard", "clean", true );
		String deletePlugininfo = sparkl.DeleteCancelElement( pluginDash );
		assertEquals( "You are about to delete plugininfo. Please, press OK to continue...", deletePlugininfo );

		//delete dashboard
		PluginElement deleteEnd = new PluginElement( "deleteplugin", "endpoint", "ktr", true );
		String deleteDeleteplugin = sparkl.DeleteCancelElement( deleteEnd );
		assertEquals( "You are about to delete deleteplugin. Please, press OK to continue...", deleteDeleteplugin );

	}
}
