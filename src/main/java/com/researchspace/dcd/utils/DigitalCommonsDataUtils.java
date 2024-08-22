package com.researchspace.dcd.utils;

import java.net.MalformedURLException;

public class DigitalCommonsDataUtils {

  private DigitalCommonsDataUtils() {
  }

  /***
   *
   * Utils methods to convert the web base url of Digital Commons Data into the
   * api base url of it
   *
   * @param webDcdBaseUrl the url of the home user page in the web
   *                      browser (i.e.: https://data.mendeley.com)
   * @return the url of the api base end points
   * @throws MalformedURLException
   */
  public static String getApiBaseUrl(String webDcdBaseUrl) {
    return webDcdBaseUrl.replace("://", "://api.");
  }

  /***
   *
   * Utils methods to convert the web base url of Digital Commons Data into the
   * auth base url of it
   *
   * @param webDcdBaseUrl the url of the home user page in the web
   *                      browser (i.e.: https://data.mendeley.com)
   * @return the url of the api base end points
   * @throws MalformedURLException
   */
  public static String getAuthBaseUrl(String webDcdBaseUrl) {
    return webDcdBaseUrl.replace("://", "://auth.");
  }

  /***
   *
   * Utils methods to convert the web base url of Digital Commons Data into the
   * upload base url of it
   *
   * @param webDcdBaseUrl the url of the home user page in the web
   *                      browser (i.e.: https://data.mendeley.com)
   * @return the url of the api base end points
   * @throws MalformedURLException
   */
  public static String getUploadBaseUrl(String webDcdBaseUrl) {
    return webDcdBaseUrl.replace("://", "://uploads.");
  }
  
}
