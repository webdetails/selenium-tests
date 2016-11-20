package com.pentaho.ctools.security;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.net.HttpURLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.pentaho.ctools.utils.ElementHelper;
import com.pentaho.ctools.utils.HttpUtils;
import com.pentaho.ctools.utils.PageUrl;
import com.pentaho.gui.error.ErrorPage;
import com.pentaho.selenium.BaseTest;

public class AccessSystemResources extends BaseTest {
  // Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( AccessSystemResources.class );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Access denied to specific webpage
   *
   * Description:
   *    The test case pretends to validate when user tries to access system files
   *    returns access denied, on the page:
   *    plugin/pentaho-cdf-dd/api/resources/system/jackrabbit/repository.xml
   *
   * Steps:
   *    1. Check 401 on http status when access the page.
   */
  @Test
  public void tc1_AccessPage_FailAccessing() {
    this.log.info( "tc1_AccessPage_FailAccessing" );

    this.elemHelper.Get( driver, PageUrl.SEC_CDE_JACKRABBIT_REPOSITORY );

    //Wait for form display
    ErrorPage epage = new ErrorPage( driver );
    assertTrue( epage.isPageSorryWeDidTryPresent() );

    //Check if return HTTP Status 401
    assertEquals( HttpUtils.GetHttpStatus( PageUrl.SEC_CDE_JACKRABBIT_REPOSITORY ), HttpURLConnection.HTTP_UNAUTHORIZED );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Access denied to specific webpage
   *
   * Description:
   *    The test case pretends to validate when user tries to access system files
   *    returns access denied, on the page:
   *    plugin/pentaho-cdf-dd/api/resources/get?resource=../jackrabbit/repository.xml
   *
   * Steps:
   *    1. Check 401 on http status when access the page.
   */
  @Test
  public void tc2_AccessPage2_FailAccessing() {
    this.log.info( "tc2_AccessPage2_FailAccessing" );

    this.elemHelper.Get( driver, PageUrl.SEC_CDE_JACKRABBIT_GET_REPOSITORY );

    //Wait for form display
    ErrorPage epage = new ErrorPage( driver );
    assertTrue( epage.isPageSorryWeDidTryPresent() );

    //Check if return HTTP Status 401
    assertEquals( HttpUtils.GetHttpStatus( PageUrl.SEC_CDE_JACKRABBIT_GET_REPOSITORY ), HttpURLConnection.HTTP_UNAUTHORIZED );
  }
}
