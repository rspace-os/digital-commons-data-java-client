package com.researchspace.dcd.client;

import com.researchspace.dcd.model.DigitalCommonDataSubmission;
import com.researchspace.dcd.model.DigitalCommonsDataDataset;
import com.researchspace.dcd.model.DigitalCommonsDataFile;
import java.io.File;
import java.io.IOException;

/**
 * API Client wrapper for making calls to Digital Commons Data API.
 */
public interface DigitalCommonsDataClient {

	/**
	 * Create a DigitalCommonsData Deposition.
	 *
	 * A DigitalCommonsData Dataset is used for  uploading records to DigitalCommonsData
	 * and acts as a conceptual grouping of files. Here, we provide one method
	 * for creating a new dataset
	 *
	 * @param submission The metadata to associate with the Deposition.
	 * @return The created DigitalCommonsDataDataset deposition.
	 */
	DigitalCommonsDataDataset createDataset(DigitalCommonDataSubmission submission) throws IOException;

	Boolean deleteDataset(String datasetId);

	Boolean testConnection();

	/**
	 * Once a DigitalCommonsData dataset has been created, files can be deposited within
	 * it.
	 *
	 * @param dataset The dataset into which the file will be deposited.
	 * @param filename The name of the file to be deposited.
	 * @param file The file to be deposited.
	 * @return The details of the successful deposit.
	 */
	DigitalCommonsDataFile depositFile(DigitalCommonsDataDataset dataset, String filename, File file) throws IOException;

}
