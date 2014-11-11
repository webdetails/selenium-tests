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
package org.pentaho.ctools.utils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pentaho.ctools.cda.CDACacheManager;

public class HttpUtils {

  private static Logger log = LogManager.getLogger(CDACacheManager.class);

  /**
   * This method shall return the status of HTTP request.
   *
   * @param url
   * @return
   * @throws Exception
   */
  public static int GetHttpStatus(String url) {
    int nHttpStatus = HttpStatus.SC_EXPECTATION_FAILED;

    try {
      URL oUrl = new URL(url);
      URLConnection uc = oUrl.openConnection();
      uc.connect();
      nHttpStatus = ((HttpURLConnection) uc).getResponseCode();
    } catch (Exception ex) {
      log.error(ex.getMessage());
    }

    return nHttpStatus;
  }
}
