package com.rabobank.TransactionValidator.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.rabobank.TransactionValidator.service.ValidationCommandService;
import com.rabobank.TransactionValidator.utils.Constants;

/**
 * 
 * @author johnbrittom
 * 
 * This class creates a command called validate-transactions which will validate the rabobank transactions statement
 */

@ShellComponent
public class ValidationCommand {

	@Autowired
	private ValidationCommandService validationCommandService;

	Logger LOGGER = LoggerFactory.getLogger(ValidationCommand.class);

	
	/**
	 * This method creates and exposes command validate-transactions
	 * @param statementFilename -> the name of the statement
	 * @param fileFormat -> the file format (csv/xml)
	 * @param inputPath -> the location where the statement file exists
	 * @param outputPath - > the location where the failed transaction report needs to be generated
	 */
	
	@ShellMethod("Validate the bank transaction statements")
	public void validateTransactions(@ShellOption(value = { "-N", "--filename" }) String statementFilename,
			@ShellOption(value = { "-F", "--fileformat" }) String fileFormat,
			@ShellOption(value = { "-I", "--inputpath" }) String inputPath,
			@ShellOption(value = { "-O", "--outputpath" }) String outputPath) {

		LOGGER.info("=====> Validating the \"{}\" statement of type \"{}\" from \"{}\" and output will be \"{}\"",
				new Object[] { statementFilename, fileFormat, inputPath, outputPath });
		
		try {
			if(Constants.FILE_FORMAT_CSV.equalsIgnoreCase(fileFormat)){
				validationCommandService.validateCSVStatements(statementFilename, fileFormat, inputPath, outputPath);
			}else if(Constants.FILE_FORMAT_XML.equalsIgnoreCase(fileFormat)){
				validationCommandService.validateXMLStatements(statementFilename, fileFormat, inputPath, outputPath);
			}else{
				LOGGER.error("\nPlease enter a valid fileformat(csv/xml)");
			}
		} catch (Exception exception) {
			LOGGER.error("Exception occurred: ", exception);
			System.exit(-1);
		}
		System.exit(0);
	}
}