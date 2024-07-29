package com.researchspace.dcd.client;

import com.researchspace.dcd.model.DigitalCommonDataSubmission;
import com.researchspace.dcd.model.DigitalCommonsDataBindingRequest;
import com.researchspace.dcd.model.DigitalCommonsDataBindingResponse;
import com.researchspace.dcd.model.DigitalCommonsDataDataset;
import com.researchspace.dcd.model.DigitalCommonsDataDatasetCreationRequest;
import com.researchspace.dcd.model.DigitalCommonsDataFile;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Getter
@Setter
@Slf4j
public class DigitalCommonsDataClientImpl implements DigitalCommonsDataClient {

  private final URL webUrlBase;
  private final URL apiUrlBase;
  private final URL uploadUrlBase;
  private final String token;
  private RestTemplate restTemplate;

  @SneakyThrows
  public DigitalCommonsDataClientImpl(URL webUrlBase, String token) {
    this.webUrlBase = webUrlBase;
    this.apiUrlBase = getApiBaseUrl(this.webUrlBase.toString());
    this.uploadUrlBase = getUploadBaseUrl(this.webUrlBase.toString());
    this.token = token;
    this.restTemplate = new RestTemplate();
  }

  private URL getApiBaseUrl(String baseUrl) throws MalformedURLException {
    return new URL(baseUrl.replace("://", "://api."));
  }

  private URL getUploadBaseUrl(String baseUrl) throws MalformedURLException {
    return new URL(baseUrl.replace("://", "://uploads."));
  }

  @Override
  public Boolean testConnection() {
    final String FAKE_ID = "FAKE_ID";
    String expectedMsg =
				"404 Not Found: \"{\"message\":\"Draft dataset '" + FAKE_ID + "' not found\"}\"";
    try {
      deleteDataset(FAKE_ID);
    } catch (HttpClientErrorException clientEx) {
      if (expectedMsg.equals(clientEx.getMessage())) {
        return Boolean.TRUE;
      }
    } catch (Exception e) {
      log.error("Couldn't perform test action {}", e.getMessage());
      return Boolean.FALSE;
    }
    return Boolean.FALSE;
  }

  @Override
  public DigitalCommonsDataDataset createDataset(DigitalCommonDataSubmission submission) {
    DigitalCommonsDataDatasetCreationRequest request = DigitalCommonsDataDatasetCreationRequest.builder()
        .name(submission.getTitle())
        .description(submission.getDescription())
        .method(submission.getUploadType())
        .build();

    return restTemplate
        .exchange(
            this.apiUrlBase + "/active-data-entities/datasets/drafts",
            HttpMethod.POST,
            new HttpEntity<>(request, getHttpHeaders()),
            DigitalCommonsDataDataset.class)
        .getBody();
  }

  @Override
  public Boolean deleteDataset(String datasetId) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + this.token);
    restTemplate
        .exchange(
            this.apiUrlBase + "/active-data-entities/datasets/drafts/" + datasetId,
            HttpMethod.DELETE,
            new HttpEntity<>(headers), Object.class);

    return Boolean.TRUE;
  }


  @Override
  public DigitalCommonsDataFile depositFile(DigitalCommonsDataDataset dataset,
      String filename, File file) throws IOException {
    FileSystemResource fileSystemResource = new FileSystemResource(file);

    HttpHeaders headers = getHttpHeaders();
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    headers.setContentLength(fileSystemResource.contentLength());

    // 1. send the file
    DigitalCommonsDataFile digitalCommonsDataFile = restTemplate.exchange(
        this.uploadUrlBase + "/uploads",
        HttpMethod.POST,
        new HttpEntity<>(fileSystemResource, headers),
        DigitalCommonsDataFile.class
    ).getBody();

    // 2. Link file and dataset
    String mediaType = Files.probeContentType(file.toPath());
    DigitalCommonsDataBindingRequest bindingRequest = DigitalCommonsDataBindingRequest.builder()
        .ticketId(digitalCommonsDataFile.getId())
        .filename(filename)
        .size(file.length())
        .description(dataset.getDescription())
        .mediaType(mediaType)
        .build();

    DigitalCommonsDataBindingResponse bindDataset = restTemplate.exchange(
        this.apiUrlBase + "/active-data-entities/datasets/drafts/"
            + dataset.getId() + "/files",
        HttpMethod.POST,
        new HttpEntity<>(bindingRequest, getHttpHeaders()),
        DigitalCommonsDataBindingResponse.class
    ).getBody();

    return digitalCommonsDataFile;
  }

  private HttpHeaders getHttpHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.add("Authorization", "Bearer " + this.token);
    return headers;
  }

}
