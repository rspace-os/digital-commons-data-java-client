package com.researchspace.dcd;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withUnauthorizedRequest;

import com.researchspace.dcd.client.DigitalCommonsDataClientImpl;
import com.researchspace.dcd.model.DigitalCommonDataSubmission;
import com.researchspace.dcd.model.DigitalCommonsDataDataset;
import com.researchspace.dcd.model.DigitalCommonsDataFile;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;


class DigitalCommonsDataClientTest {

  private final static String API_ACCESS_TOKEN = "API_ACCESS_TOKEN";

  private DigitalCommonsDataClientImpl digitalCommonsDataClientImpl;
  private MockRestServiceServer mockServer;


  @BeforeEach
  public void startUp() throws MalformedURLException {
    digitalCommonsDataClientImpl = new DigitalCommonsDataClientImpl(
        new URL("https://data.mendeley.com"), API_ACCESS_TOKEN);
    mockServer = MockRestServiceServer.createServer(digitalCommonsDataClientImpl.getRestTemplate());
  }

  @Test
  public void testCreateDatasetSuccessful() throws IOException {
    String newDatasetResponseJson = IOUtils.resourceToString(
        "/json/depositionCreationResponse.json",
        Charset.defaultCharset());
    mockServer.expect(requestTo(
            containsString("https://api.data.mendeley.com/active-data-entities/datasets/drafts")))
        .andExpect(method(HttpMethod.POST))
        .andRespond(withSuccess(newDatasetResponseJson, MediaType.APPLICATION_JSON));

    DigitalCommonDataSubmission submission = new DigitalCommonDataSubmission("test_code_1",
        "test_code_1", "Rspace");

    DigitalCommonsDataDataset deposition = digitalCommonsDataClientImpl.createDataset(submission);
    assertNotNull(deposition);
    assertEquals(deposition.getId(), "d7s7hypvt4");
  }

  @Test
  public void testCreateDatasetFailed() {
    mockServer.expect(requestTo(
            containsString("https://api.data.mendeley.com/active-data-entities/datasets/drafts")))
        .andExpect(method(HttpMethod.POST))
        .andRespond(withUnauthorizedRequest());

    DigitalCommonDataSubmission submission = new DigitalCommonDataSubmission("test_code_1",
        "test_code_1", "Rspace");

    HttpClientErrorException thrown = assertThrows(
        HttpClientErrorException.class,
        () -> digitalCommonsDataClientImpl.createDataset(submission)
    );
    assertEquals("401 Unauthorized: [no body]", thrown.getMessage());

  }

  @Test
  public void testDeleteDatasetSuccessful() {
    String datasetId = "d7s7hypvt4";
    mockServer.expect(requestTo(
            containsString(
                "https://api.data.mendeley.com/active-data-entities/datasets/drafts/" + datasetId)))
        .andExpect(method(HttpMethod.DELETE))
        .andRespond(withSuccess());

    Boolean datasetDeleted = digitalCommonsDataClientImpl.deleteDataset(datasetId);
    assertTrue(datasetDeleted);
  }

  @Test
  public void testDeleteDatasetFailed() {
    String datasetId = "d7s7hypvt4";
    mockServer.expect(requestTo(
            containsString(
                "https://api.data.mendeley.com/active-data-entities/datasets/drafts/" + datasetId)))
        .andExpect(method(HttpMethod.DELETE))
        .andRespond(withUnauthorizedRequest());

    HttpClientErrorException thrown = assertThrows(
        HttpClientErrorException.class,
        () -> digitalCommonsDataClientImpl.deleteDataset(datasetId)
    );
    assertEquals("401 Unauthorized: [no body]", thrown.getMessage());

  }

  @Test
  public void testIsConnectionOpen() {
    mockServer.expect(requestTo(
            containsString(
                "https://api.data.mendeley.com/active-data-entities/datasets/drafts/FAKE_ID")))
        .andRespond(withStatus(HttpStatus.NOT_FOUND)
            .body("{\"message\":\"Draft dataset 'FAKE_ID' not found\"}"));

    Boolean datasetDeleted = digitalCommonsDataClientImpl.testConnection();
    assertTrue(datasetDeleted);
  }

  @Test
  public void testIsConnectionNotOpen() {
    mockServer.expect(requestTo(
            containsString(
                "https://api.data.mendeley.com/active-data-entities/datasets/drafts/FAKE_ID")))
        .andRespond(withUnauthorizedRequest());

    Boolean datasetDeleted = digitalCommonsDataClientImpl.testConnection();
    assertFalse(datasetDeleted);
  }

  @Test
  public void testDepositFileSucceed() throws IOException {

    String fileDepositResponse = IOUtils.resourceToString("/json/fileDepositResponse.json",
        Charset.defaultCharset());
    String bindFileToDatasetResponse = IOUtils.resourceToString(
        "/json/bindFileDatasetResponse.json",
        Charset.defaultCharset());
    String datasetId = "d7s7hypvt4";

    DigitalCommonsDataDataset dataset = DigitalCommonsDataDataset.builder()
        .id(datasetId).description("description").build();

    mockServer.expect(requestTo(
            containsString("https://uploads.data.mendeley.com/uploads")))
        .andExpect(method(HttpMethod.POST))
        .andRespond(withSuccess(fileDepositResponse, MediaType.APPLICATION_JSON));

    mockServer.expect(requestTo(
            containsString(
                "https://api.data.mendeley.com/active-data-entities/datasets/drafts/" + datasetId
                    + "/files")))
        .andExpect(method(HttpMethod.POST))
        .andRespond(withSuccess(bindFileToDatasetResponse, MediaType.APPLICATION_JSON));

    File file = new File("src/test/resources/files/example.txt");
    DigitalCommonsDataFile depositedFile = digitalCommonsDataClientImpl.depositFile(dataset,
        "example.txt ", file);
    assertNotNull(depositedFile);
    assertEquals("790f0bbd-dbe9-4535-a230-897b4a44d1c2", depositedFile.getId());

  }

  @Test
  public void testDepositFileFailed() throws IOException {
    String datasetId = "d7s7hypvt4";
    DigitalCommonsDataDataset dataset = DigitalCommonsDataDataset.builder()
        .id(datasetId).description("description").build();

    mockServer.expect(requestTo(
            containsString("https://uploads.data.mendeley.com/uploads")))
        .andExpect(method(HttpMethod.POST))
        .andRespond(withUnauthorizedRequest());

    mockServer.expect(requestTo(
            containsString(
                "https://api.data.mendeley.com/active-data-entities/datasets/drafts/" + datasetId
                    + "/files")))
        .andExpect(method(HttpMethod.POST))
        .andRespond(withUnauthorizedRequest());

    File file = new File("src/test/resources/files/example.txt");


    HttpClientErrorException thrown = assertThrows(
        HttpClientErrorException.class,
        () -> digitalCommonsDataClientImpl.depositFile(dataset, "example.txt ", file)
    );
    assertEquals("401 Unauthorized: [no body]", thrown.getMessage());

  }


}