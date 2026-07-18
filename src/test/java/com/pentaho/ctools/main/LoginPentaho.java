/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2002 - 2026 by Pentaho Canada Inc. : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2030-06-15
 ******************************************************************************/

package com.pentaho.ctools.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.pentaho.gui.web.puc.LoginPage;
import com.pentaho.selenium.BaseTest;

/**
 * Testing the functionalities related with Login.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
public class LoginPentaho extends BaseTest {
  // Log instance
  private final Logger log = LogManager.getLogger( LoginPentaho.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Authentication
   * Description:
   *    With an administrator user, we check if user can authenticate in the
   *    system.
   * Steps:
   *    1. Go to Pentaho solution web page.
   *    2. Enter user and password.
   *    3. User authenticated, and user name of logged user is displayed.
   */
  @Test
  public void tc1_Login_SuccessAuthentication() {
    this.log.info( "tc1_Login_SuccessAuthentication" );

    /*
     * ## Step 1
     */
    LoginPage login = new LoginPage( driver );

    /*
     * ## Step 2
     */
    login.Login( pentahoBaServerUsername, pentahoBaServerPassword );

  }
}
