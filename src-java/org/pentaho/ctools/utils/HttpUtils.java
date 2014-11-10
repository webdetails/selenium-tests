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
