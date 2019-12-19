package com.rabobank.TransactionValidator.service;

import java.util.List;

import org.w3c.dom.Element;

/**
 * 
 * @author johnbrittom
 *
 *         This is an interface contains the necessary methods for validating
 *         the rabobank statements
 **/
public interface ValidationCommandService {

	/**
	 * This method validates the CSV type statements
	 * 
	 * @param fileName
	 *            -> the name of the statement file
	 * @param fileFormat
	 *            -> the file format (csv/ xml)
	 * @param inputPath
	 *            -> the location where the statement files exists
	 * @param outputPath
	 *            -> the location where the failed transaction report needs to
	 *            be generated
	 * @return Nothing
	 */
	public void validateCSVStatements(String fileName, String fileFormat, String inputPath, String outputPath);

	/**
	 * This method validates the XML type statements
	 * 
	 * @param fileName
	 *            -> the name of the statement file
	 * @param fileFormat
	 *            -> the file format (csv/ xml)
	 * @param inputPath
	 *            -> the location where the statement files exists
	 * @param outputPath
	 *            -> the location where the failed transaction report needs to
	 *            be generated
	 * @return Nothing
	 */
	public void validateXMLStatements(String fileName, String fileFormat, String inputPath, String outputPath);

	/**
	 * This method parses and gets the value of the given xml tag
	 * 
	 * @param element
	 *            -> the xml element where the tag exists
	 * @param tag
	 *            -> the tag to get the value
	 * @return the value of the given xml tag
	 */
	public String getTagValue(Element element, String tag);

	/**
	 * This method creates failed transactions report
	 * 
	 * @param element
	 *            -> the xml element where the tag exists
	 * @param tag
	 *            -> the tag to get the value
	 * @return the value of the given xml tag
	 */
	public boolean writeOutput(String fileName, String fileFormat, String filePath, List<String> failedTransactions);
}
