package org.yash.rms.excel;


import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class SAXEventUtility {

	/**
	 * The return from this is a list of rows containing a map with the cell values.
	 * The maps in the list have the key value being the cell column index eg: "0", "1", "2"
	 * The value in the maps are the actual cell values.
	 * @param path
	 * @return 
	 * @throws IOException
	 * @throws OpenXML4JException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public static WorkSheet processXSSFSheet(InputStream inputStream) throws IOException, OpenXML4JException, SAXException, ParserConfigurationException{
		OPCPackage pkg = OPCPackage.open(inputStream);
		XSSFReader r = new XSSFReader( pkg );
		ReadOnlySharedStringsTable sst = new ReadOnlySharedStringsTable(pkg);
		XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator)r.getSheetsData();
		//Need to process only 0th sheet
		InputStream stream = iter.next();
		WorkSheet workSheet = processSheet(sst, stream);
		stream.close();
		return workSheet;
		
	}
	
	private static WorkSheet processSheet(ReadOnlySharedStringsTable sst, InputStream sheetInputStream) 
	throws IOException, ParserConfigurationException, SAXException {
		InputSource sheetSource = new InputSource(sheetInputStream);
		SAXParserFactory saxFactory = SAXParserFactory.newInstance();
		SAXParser saxParser = saxFactory.newSAXParser();
		XMLReader sheetParser = saxParser.getXMLReader();
		ContentHandler handler = new SheetHandler(sst);
		sheetParser.setContentHandler(handler);
		sheetParser.parse(sheetSource);
		return ((SheetHandler)handler).getWroksheet();
	}

}