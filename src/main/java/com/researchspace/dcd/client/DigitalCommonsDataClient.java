package com.researchspace.dcd.client;

import com.researchspace.dcd.model.DigitalCommonDataSubmission;
import com.researchspace.dcd.model.DigitalCommonsDataDataset;
import com.researchspace.dcd.model.DigitalCommonsDataFile;
import java.io.File;
import java.io.IOException;

/**
 * API Client wrapper for making calls to DMP API.
 */
public interface DigitalCommonsDataClient {

	/**
	 * Create a DigitalCommonsData Deposition.
	 *
	 * A DigitalCommonsData Dataset is used for editing and uploading records to DigitalCommonsData
	 * and acts as a conceptual grouping of files. Here, we provide two methods
	 * for creating a new Deposition; one with predefined metadata and one
	 * without. In either case, a new Dataset is created on the DigitalCommonsData
	 * server and its details are returned.
	 *
	 * @param submission The metadata to associate with the Deposition.
	 * @return The created DigitalCommonsData deposition.
	 */
	DigitalCommonsDataDataset createDataset(DigitalCommonDataSubmission submission) throws IOException;

	Boolean deleteDataset(String datasetId);

	Boolean isConnectionOpen();

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
