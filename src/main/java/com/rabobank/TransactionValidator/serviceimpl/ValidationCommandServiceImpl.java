package com.rabobank.TransactionValidator.serviceimpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.rabobank.TransactionValidator.service.ValidationCommandService;
import com.rabobank.TransactionValidator.utils.Constants;

/**
 * 
 * @author johnbrittom
 *
 *This is service class implementing interface ValidationCommandService, provides implementation for all the necessary methods for validating the rabobank transaction statements
 */
@Service
public class ValidationCommandServiceImpl implements ValidationCommandService {

	Logger LOGGER = LoggerFactory.getLogger(ValidationCommandServiceImpl.class);

	/**
	 * This method validates the CSV type statements
	 * 
	 * @param fileName -> the name of the statement file
	 * @param fileFormat -> the file format (csv/ xml)
	 * @param inputPath -> the location where the statement files exists
	 * @param outputPath -> the location where the failed transaction report needs to be generated 
	 * @return Nothing
	 */
	@Override
	public void validateCSVStatements(String fileName, String fileFormat, String inputPath, String outputPath) {
		String filePath = inputPath + "/" + fileName + "." + fileFormat;
		try {
			BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
			List<String> transactionReference=new ArrayList<String>();
			List<String> failedTransactions = new ArrayList<String>();
			String record;
			String[] fields;
			BigDecimal startBalance;
			BigDecimal mutation;
			BigDecimal endBalance;
			BigDecimal expectedEndBalance;
			boolean firstLine=true;
			while((record = csvReader.readLine()) != null){
				if(firstLine){
					firstLine=false;
					continue;
				}
				fields = record.split("\\,");
				
				// Validation 1: all transaction references should be unique
				if(transactionReference.contains(fields[0])){
					failedTransactions.add(fields[0]+Constants.DELIMITTER+fields[2]);
					continue;
				}else{
					transactionReference.add(fields[0]);
				}
				// Validation 2: the end balance needs to be validated
				startBalance = new BigDecimal(fields[3]);
				mutation = new BigDecimal(fields[4]);
				endBalance = new BigDecimal(fields[5]);
				expectedEndBalance = startBalance.add(mutation);
				if(endBalance.compareTo(expectedEndBalance) !=0){
					failedTransactions.add(fields[0]+Constants.DELIMITTER+fields[2]);
				}
			}
			csvReader.close();
			
            //Generate failed transactions report
			writeOutput(fileName, Constants.FILE_FORMAT_CSV, outputPath, failedTransactions);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method validates the XML type statements
	 * 
	 * @param fileName -> the name of the statement file
	 * @param fileFormat -> the file format (csv/ xml)
	 * @param inputPath -> the location where the statement files exists
	 * @param outputPath -> the location where the failed transaction report needs to be generated 
	 * @return Nothing
	 */
	@Override
	public void validateXMLStatements(String fileName, String fileFormat, String inputPath, String outputPath) {
		String filePath = inputPath + "/" + fileName + "." + fileFormat;
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
        	List<String> transactionReference=new ArrayList<String>();
			List<String> failedTransactions = new ArrayList<String>();
			BigDecimal startBalance;
			BigDecimal mutation;
			BigDecimal endBalance;
			BigDecimal expectedEndBalance;
			String referenceAttribute;
			String transactionDescription;
			
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList recordNodeList = doc.getElementsByTagName("record");
            
            for (int i = 0; i < recordNodeList.getLength(); i++) {
                Node recordNode = recordNodeList.item(i);
                if(Node.ELEMENT_NODE == recordNode.getNodeType()) {
                    Element element = (Element) recordNode;
                    referenceAttribute=element.getAttribute("reference");
                    transactionDescription=getTagValue(element, "description");
                    
                 // Validation 1: all transaction references should be unique
    				if(transactionReference.contains(referenceAttribute)){
    					failedTransactions.add(referenceAttribute+Constants.DELIMITTER+transactionDescription);
    					continue;
    				}else{
    					transactionReference.add(referenceAttribute);
    				}
    				// Validation 2: the end balance needs to be validated
    				startBalance = new BigDecimal(getTagValue(element, "startBalance"));
    				mutation = new BigDecimal(getTagValue(element, "mutation"));
    				endBalance = new BigDecimal(getTagValue(element, "endBalance"));
    				expectedEndBalance = startBalance.add(mutation);
    				if(endBalance.compareTo(expectedEndBalance) !=0){
    					failedTransactions.add(referenceAttribute+Constants.DELIMITTER+transactionDescription);
    				}
                }
            }
            
            //Generate failed transactions report
            writeOutput(fileName, Constants.FILE_FORMAT_XML, outputPath, failedTransactions);
            
        } catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        }
	}
	
	/**
	 * This method parses and gets the value of the given xml tag
	 * 
	 * @param element -> the xml element  where the tag exists 
	 * @param tag -> the tag to get the value
	 * @return the value of the given xml tag
	 */
	@Override
	public String getTagValue(Element element, String tag){
		NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
	}
	
	/**
	 * This method creates failed transactions report
	 * 
	 * @param element -> the xml element  where the tag exists 
	 * @param tag -> the tag to get the value
	 * @return the value of the given xml tag
	 */
	@Override
	public boolean writeOutput(String fileName, String fileFormat, String filePath, List<String> failedTransactions){
        try {
        	File file = new File(filePath+"\\"+fileName+"_"+fileFormat+"_failedTransactionsReport.txt");
			if (file.createNewFile()){
				FileWriter fileWriter = new FileWriter(file);
				fileWriter.write("Transaction Reference \t Description \n");
				failedTransactions.forEach(record -> {
					try {
						String[] data = record.split(Constants.DELIMITTER);
						fileWriter.write(data[0] + "\t\t\t\t\t" + data[1]+"\n");
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
				fileWriter.close();
				return true;
			}else{
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
