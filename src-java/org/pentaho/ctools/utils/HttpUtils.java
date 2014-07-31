package org.pentaho.ctools.utils;

import java.lang.Exception;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {

  public static int GetHttpStatus(String url) throws Exception{
      URL iurl = new URL(url);
      HttpURLConnection uc = (HttpURLConnection) iurl.openConnection();
      uc.connect();
    return uc.getResponseCode();
  }
}
