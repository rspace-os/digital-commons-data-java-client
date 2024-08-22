package com.researchspace.dcd.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;



class DigitalCommonsDataUtilsTest {

  public static final String WEB_BASE_URL = "https://data.mendeley.com";

  @Test
  void testGetApiBaseUrl() {
    assertEquals("https://api.data.mendeley.com",
        DigitalCommonsDataUtils.getApiBaseUrl(WEB_BASE_URL));
  }

  @Test
  void testGetUploadBaseUrl() {
    assertEquals("https://uploads.data.mendeley.com",
        DigitalCommonsDataUtils.getUploadBaseUrl(WEB_BASE_URL));
  }

  @Test
  void testGetAuthBaseUrl() {
    assertEquals("https://auth.data.mendeley.com",
        DigitalCommonsDataUtils.getAuthBaseUrl(WEB_BASE_URL));
  }


}