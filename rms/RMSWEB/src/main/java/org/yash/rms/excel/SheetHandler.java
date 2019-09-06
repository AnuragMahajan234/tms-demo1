package org.yash.rms.excel;

import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
public class SheetHandler extends DefaultHandler {

	private ReadOnlySharedStringsTable sst;
	private String lastContents;
	private String lastCellType = "";
	private String lastColRef = "";
	private String lastRowRef = "";
	private boolean emptyRow = true;
	private Row row;
	private WorkSheet wroksheet = new WorkSheet();
	public static final String CELL_NAME = "c";
	public static final String ROW_NAME = "row";
	public static final String CELL_REFERENCE = "r";
	public static final String ROW_REFERENCE = "r";
	public static final String CELL_TYPE = "t";

	public SheetHandler(ReadOnlySharedStringsTable sst) {
		this.sst = sst;
	}

	public void startElement(String uri, String localName, String name,	Attributes attributes) throws SAXException {
		if (name.equals(ROW_NAME)) {
			emptyRow = true;
			row = new Row();
			wroksheet.getRows().add(row);
			String rowRef = attributes.getValue(ROW_REFERENCE);
			lastRowRef = rowRef;
		} else if (name.equals(CELL_NAME)) {
			String colRef = attributes.getValue(CELL_REFERENCE);

			String cellType = attributes.getValue(CELL_TYPE);
			if (cellType == null) {
				cellType = Cell.CELL_TYPE_NUMERIC;
			}
			lastCellType = cellType;
			lastColRef = colRef;
		}
		// Clear contents cache
		lastContents = "";
	}

	public void endElement(String uri, String localName, String name) throws SAXException {
		if (name.equals(CELL_NAME)) {
			Cell cell = new Cell();
			Integer columnIndex = nameToColumnIndex(lastColRef.replace(lastRowRef, ""));
			cell.setColumnIndex(columnIndex);
			if (lastCellType.trim().equalsIgnoreCase(Cell.CELL_TYPE_STRING)) {
				cell.setCellType(Cell.CELL_TYPE_STRING);
				if (lastContents != null) {
					cell.setCellValue(lastContents);
					if (lastContents.length() == 0) {
						cell.setCellType(Cell.CELL_TYPE_BLANK);
					}
					emptyRow = false;
				} else {
					cell = null;
				}
			} else if (lastCellType.trim().equalsIgnoreCase(Cell.CELL_TYPE_SSTINDEX)) {
				cell.setCellType(Cell.CELL_TYPE_STRING);//Because we will get Text String from SST Index 			
				try {
					int idx = Integer.parseInt(lastContents);
					XSSFRichTextString rtss = new XSSFRichTextString(sst.getEntryAt(idx));
					if(rtss.toString().length() == 0){
						cell.setCellType(Cell.CELL_TYPE_BLANK);
					}
					cell.setCellValue(rtss.toString());
					emptyRow = false;
				} catch (NumberFormatException ex){
					cell = null;
				}
			} else if (lastCellType.trim().equalsIgnoreCase(Cell.CELL_TYPE_NUMERIC)) {
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				Double cellValue = stringToDouble(lastContents);
				cell.setCellValue(cellValue);
				if(cellValue != null){
					emptyRow = false;
				} else {
					cell = null;
				}
			} else if (lastCellType.trim().equalsIgnoreCase(Cell.CELL_TYPE_BOOLEAN)) {
				cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
				Boolean cellValue = stringToBoolean(lastContents);
				cell.setCellValue(lastContents);
				if(cellValue != null){
					emptyRow = false;
				} else {
					cell = null;
				}
			} else if (lastCellType.trim().equalsIgnoreCase(Cell.CELL_TYPE_FORMULA)) {
				cell.setCellType(Cell.CELL_TYPE_FORMULA);
				cell.setCellValue(lastContents);
				emptyRow = false;
			} else if (lastCellType.trim().equalsIgnoreCase(Cell.CELL_TYPE_ERROR)) {
				cell.setCellType(Cell.CELL_TYPE_ERROR);
				cell.setCellValue("ERROR:" + lastContents);
				emptyRow = false;
			}

			row.getCellMap().put(columnIndex, cell);
		} else if (name.equals(ROW_NAME)) {
			if(emptyRow && wroksheet.getRows().size() > 0){
				wroksheet.getRows().remove(wroksheet.getRows().size()-1);
			}			
		}
	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		lastContents += new String(ch, start, length);
	}

	private Double stringToDouble(String cellValue) {
		if (cellValue == null || cellValue.isEmpty()) {
			return null;
		}
		Double value = null;
		try{
			value = Double.valueOf(cellValue);
		}		
		catch (NumberFormatException e) {
			//e.printStackTrace();
		}
		return value;
	}
	
	private Boolean stringToBoolean(String cellValue) {
		if (cellValue == null || cellValue.isEmpty()) {
			return null;
		}
		
		if(cellValue.equalsIgnoreCase("true")){
			return true;
		} else if (cellValue.equalsIgnoreCase("false")){
			return false;
		} else {
			char first = cellValue.charAt(0);
			return first == '0' ? false : true;
		}
	}	

	/**
	 * Converts an Excel column name like "C" to a zero-based index.
	 * 
	 * @param name
	 * @return Index corresponding to the specified name
	 */
	private int nameToColumnIndex(String name) {
		int column = -1;
		for (int i = 0; i < name.length(); ++i) {
			int c = name.charAt(i);
			column = (column + 1) * 26 + c - 'A';
		}
		return column;
	}

	public WorkSheet getWroksheet() {
		return wroksheet;
	}

}
