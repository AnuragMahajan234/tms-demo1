package org.yash.rms.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelUtil {

	private static final Logger logger = LoggerFactory
			.getLogger(ExcelUtil.class);

	public static <T> List<T> readExcel(Class<T> clazz, File file,
			List<String> errorList) throws IOException, InstantiationException,
			IllegalAccessException, Exception {
		return readExcel(clazz, new FileInputStream(file), file.getName(),
				errorList);
	}

	public static <T> List<T> readExcel(Class<T> clazz,
			InputStream fileInputStream, String fileName, List<String> errorList)
			throws IOException, InstantiationException, IllegalAccessException,
			Exception {
		List<T> list = new ArrayList<T>();
		// If Excel annotation is present that means we will use this class to
		// parse and populate excel file
		// ResourceExcelMapping excelMapping = new ResourceExcelMapping();
		String ext = " ";
		int mid = fileName.lastIndexOf(".");
		ext = fileName.substring(mid + 1, fileName.length());
		System.out.println("Extension =" + ext);
		if (clazz.isAnnotationPresent(Excel.class)) {
			Map<String, Field> clazzFieldMap = new HashMap<String, Field>();
			for (Field field : clazz.getDeclaredFields()) {
				ExcelColumn ec = field.getAnnotation(ExcelColumn.class);
				if (ec != null && null != ec.columnName()
						&& !"".equalsIgnoreCase(ec.columnName().trim())) {
					// if developer had put something as columnName use it
					// instead of field name
					clazzFieldMap.put(ec.columnName(), field);
				} else {
					clazzFieldMap.put(field.getName(), field);
				}
			}

			try {

				// create the work sheet.

				WorkSheet mySheet = SAXEventUtility
						.processXSSFSheet(fileInputStream);

				List<ObjectType> fieldExcellist = new ArrayList<ObjectType>();
				// get all headers of excel
				List<String> excelColumns = getHeader(mySheet, 0);
				if (!excelColumns.isEmpty() && excelColumns.size() != 0) {
					for (String coulmn : excelColumns) {
						// get matching field of clazz for this column and
						// populate that into objectTypes
						Field field = clazzFieldMap.get(coulmn); // this will
																	// bring
																	// field ,
																	// if excel
																	// column
																	// matches
																	// field
																	// name or
																	// columnName
																	// of
																	// ExcelCoulmn
						if (field == null)
							field = clazzFieldMap.get(coulmn
									.replaceAll(" ", "")); // if we didnt found
															// field than
															// replace all
															// spaces from excel
															// coulmn and try to
															// find field name
						if (field == null) {
							// this will help to ignore values of this column
							// while populating object
							fieldExcellist.add(new ObjectType(coulmn));
						} else {
							ExcelColumn ec = field
									.getAnnotation(ExcelColumn.class);
							if (ec != null)
								fieldExcellist.add(new ObjectType(field,
										coulmn, ec.fieldMapTo()));
							else
								fieldExcellist
										.add(new ObjectType(coulmn, field));
						}
					}

					int rowCount = null != mySheet.getRows() ? mySheet
							.getRows().size() : 0;
					int startIndex = 1;
					for (int rowCounter = startIndex; rowCounter < rowCount; rowCounter++) {
						T iClass = clazz.newInstance();
						Row myRow = mySheet.getRow(rowCounter);
						Integer colCount = 0;
						for (ObjectType objectType : fieldExcellist) {
							if (objectType.getFieldName() == null) {
								colCount++;
								continue;
							}

							// excelMapping = (ResourceExcelMapping) iClass;

							Cell myCell = myRow.getCell(colCount);
							if (myCell != null) {
								Class<?> fieldType = objectType.getFieldName()
										.getType();
								objectType.getFieldName().setAccessible(true);
								if (myCell.getCellType() == Cell.CELL_TYPE_STRING) {

									try {
										if (fieldType
												.isAssignableFrom(String.class)) {
											objectType
													.getFieldName()
													.set(iClass,
															myCell.getStringCellValue());

										} else if (fieldType
												.isAssignableFrom(Integer.class)) {
											objectType
													.getFieldName()
													.set(iClass,
															Integer.parseInt(myCell
																	.getStringCellValue()));
										}
									} catch (NumberFormatException e) {
										// errorList.add("Enter Integer IDs for employee "+excelMapping.getYashEmpId()+" for column  "
										// + objectType.getExcelColumn() +
										// "  "+e.getMessage());
										errorList.add(".....Excel Util......: "
												+ e.getMessage());
									}

								} else if (myCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
									// its a double, so for fields of type
									// integer we need to call intValue()

									try {
										if (fieldType
												.isAssignableFrom(Integer.class)) {
											objectType
													.getFieldName()
													.set(iClass,
															(int) myCell
																	.getNumericCellValue());
										} else if (fieldType
												.isAssignableFrom(Date.class)) {
											// date columns also comes here for
											// XSSF files...
											objectType.getFieldName().set(
													iClass,
													myCell.getDateCellValue());
										} else if (fieldType
												.isAssignableFrom(String.class)) {
											long x;

											if (myCell.getCellType() == Cell.CELL_TYPE_NUMERIC)
												x = (long) myCell
														.getNumericCellValue();

											else
												x = -1;

											objectType.getFieldName().set(
													iClass, String.valueOf(x));
										}
									} catch (NumberFormatException e) {
										// errorList.add("Enter Integer IDs for employee "+excelMapping.getYashEmpId()+" for column  "
										// + objectType.getFieldName() +
										// "  "+e.getMessage());
										errorList
												.add(".....Excel Util1......: "
														+ e.getMessage());
									}
								} else if (myCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
									try {
										if (fieldType
												.isAssignableFrom(Boolean.class)) {
											objectType
													.getFieldName()
													.set(iClass,
															myCell.getBooleanCellValue());
										}
									} catch (IllegalArgumentException e) {
										// errorList.add("Enter Integer IDs for employee "+excelMapping.getYashEmpId()+" for column  "
										// + objectType.getFieldName() +
										// "  "+e.getMessage());
										errorList
												.add(".....Excel Util2......: "
														+ e.getMessage());
									}
								}

							}
							colCount++;
						}
						list.add(iClass);
					}
				}

			} catch (Exception ioe) {
				logger.info("------Exception occured while uploading the resources------"
						+ ioe);
				throw ioe;
			}
		}
		return list;
	}

	public static List<String> getHeader(WorkSheet mySheet,
			Integer rowStartNumber) {
		int colHeaderRowIndex = 0;
		if (rowStartNumber != null) {
			colHeaderRowIndex = rowStartNumber;
		}
		List<String> headerList = new ArrayList<String>();
		try {
			/** Get the first sheet from workbook **/
			// Sheet mySheet = myWorkBook.getSheetAt(0);
			/** We now need something to iterate through the cells. **/
			if (mySheet.getRows().size() != 0) {
				Row myRow = mySheet.getRow(colHeaderRowIndex);
				Map<Integer, Cell> cellMap = myRow.getCellMap();
				/*
				 * for(Map.Entry<Integer, Cell> cell : cellMap.entrySet()){
				 * headerList.add(cell.getValue().getStringCellValue()); }
				 */
				int cellMapSize = null != cellMap ? cellMap.size() : 0;
				for (int cellMapStartIndex = 0; cellMapStartIndex < cellMapSize; cellMapStartIndex++) {
					headerList.add(cellMap.get(cellMapStartIndex)
							.getStringCellValue());
				}
			} else {
				logger.info("------Blank sheet------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return headerList;
	}

}
