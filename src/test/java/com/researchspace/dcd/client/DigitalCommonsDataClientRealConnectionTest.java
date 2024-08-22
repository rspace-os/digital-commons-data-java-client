package com.researchspace.dcd.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.researchspace.dcd.client.DigitalCommonsDataClientImpl;
import com.researchspace.dcd.model.DigitalCommonDataSubmission;
import com.researchspace.dcd.model.DigitalCommonsDataDataset;
import com.researchspace.dcd.model.DigitalCommonsDataFile;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;


@Disabled("This test can only be ran manually since it needs a valid access token")
class DigitalCommonsDataClientRealConnectionTest {

  public static final String ACCESS_TOKEN = "___PASTE___TOKEN___HERE___";
  private DigitalCommonsDataClientImpl digitalCommonsDataClientImpl;

  @BeforeEach
  public void startUp() throws MalformedURLException {
    digitalCommonsDataClientImpl = new DigitalCommonsDataClientImpl(
        new URL("https://data.mendeley.com"), ACCESS_TOKEN);
  }

  @Test
  public void testCreateDatasetUploadFileAndDeleteDataset() throws IOException {
    long random = System.currentTimeMillis();

    DigitalCommonDataSubmission toSubmit = DigitalCommonDataSubmission.builder()
        .title("repo_deposit_test" + random)
        .description("repo_nik_test" + random)
        .uploadType("file_test" + random)
        .build();

    DigitalCommonsDataDataset dataset = digitalCommonsDataClientImpl.createDataset(toSubmit);
    assertNotNull(dataset);

    File file = new File("src/test/resources/files/example.txt");
    DigitalCommonsDataFile depositedFile = digitalCommonsDataClientImpl.depositFile(dataset,
        "example", file);
    assertNotNull(depositedFile);

    Boolean deleted = digitalCommonsDataClientImpl.deleteDataset(dataset.getId());
    assertTrue(deleted);
  }

  @Test
  public void testConnectionAuthIsAlive() {
    String expectedMsg = "404 Not Found: \"{\"message\":\"Draft dataset 'fake_id' not found\"}\"";
    try {
      digitalCommonsDataClientImpl.deleteDataset("fake_id");
    } catch (HttpClientErrorException clientEx) {
      assertEquals(expectedMsg, clientEx.getMessage(), "The connection is not open");
    } catch (Exception e) {
      assertTrue(false, "The connection is not open");
    }
  }

}
