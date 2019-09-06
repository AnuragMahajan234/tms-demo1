/**
 * 
 */
package org.yash.rms.excel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import org.yash.rms.report.dto.ModuleReport;
import org.yash.rms.report.dto.WeeklyData;
import org.yash.rms.util.Constants;

/**
 * @author bhakti.barve
 *
 */

@SuppressWarnings("unchecked")
public class ModuleReportExcelUtility extends AbstractExcelView {
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbookModuleReport, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.debug("Started buildExcelDocument in ModuleReportExcelUtility Class");
		HSSFSheet moduleReportSheet = workbookModuleReport.createSheet("moduleReportSheet");

		Integer count = 0;

		boolean plannedHours = (Boolean) request.getAttribute("plannedHours");
		boolean billedHours = (Boolean) request.getAttribute("billedHours");
		boolean productiveHours = (Boolean) request.getAttribute("productiveHours");
		boolean nonProductiveHours = (Boolean) request.getAttribute("nonProductiveHours");

		if (plannedHours)
			count++;
		if (billedHours)
			count++;
		if (productiveHours)
			count++;
		if (nonProductiveHours)
			count++;

		response.setHeader("Content-Disposition", "attachment; filename=Module_Report.xls");

		moduleReportSheet.setDefaultColumnWidth(28);

		Map<String, Map<String, List<ModuleReport>>> moduleReportList = (Map<String, Map<String, List<ModuleReport>>>) model.get("moduleReportsList");

		int totalColumnsModuleReport = 0;

		if (!moduleReportList.isEmpty()) {

			Map<Integer, Integer> totalModuleHourCellNumber = new TreeMap<Integer, Integer>();

			Set<Entry<String, Map<String, List<ModuleReport>>>> moduleReportSet = moduleReportList.entrySet();

			CellStyle styleHeaderPeach = setCellStylePeach(workbookModuleReport);

			CellStyle styleHeaderYellow = setCellStyleYellow(workbookModuleReport);

			CellStyle styleHeaderOrange = setCellStyleOrange(workbookModuleReport);

			CellStyle cellFont = setFontStyle(workbookModuleReport);

			// First Header
			// style first row

			HSSFRow headerRowModuleReport = moduleReportSheet.createRow(0);

			Cell cellHeader = headerRowModuleReport.createCell(0);
			cellHeader.setCellValue("ModuleName");
			cellHeader.setCellStyle(styleHeaderYellow);

			moduleReportSheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));

			Cell cellHeader1 = headerRowModuleReport.createCell(1);
			cellHeader1.setCellValue("ProjectName");

			moduleReportSheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
			cellHeader1.setCellStyle(styleHeaderYellow);

			Cell cellHeader2 = headerRowModuleReport.createCell(2);
			cellHeader2.setCellValue("EmployeeName");

			moduleReportSheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 2));
			cellHeader2.setCellStyle(styleHeaderYellow);

			Map<Date, String> weekWiseReportData = new TreeMap<Date, String>();

			Iterator<Entry<String, Map<String, List<ModuleReport>>>> iterator = moduleReportSet.iterator();

			while (iterator.hasNext()) {

				Entry<String, Map<String, List<ModuleReport>>> entrySetModuleReport = iterator.next();

				Map<String, List<ModuleReport>> projectMapModuleReport = entrySetModuleReport.getValue();

				Set<Entry<String, List<ModuleReport>>> projectSetModuleReport = projectMapModuleReport.entrySet();

				Iterator<Entry<String, List<ModuleReport>>> projectMapIterator = projectSetModuleReport.iterator();

				while (projectMapIterator.hasNext()) {

					Entry<String, List<ModuleReport>> entrySetModuleReportProject = projectMapIterator.next();

					List<ModuleReport> resourceListProject = entrySetModuleReportProject.getValue();

					for (ModuleReport moduleReportResourceList : resourceListProject) {

						List<WeeklyData> weeklyLists = moduleReportResourceList.getWeeklyData();

						for (WeeklyData weeklyData : weeklyLists) {

							if (!weekWiseReportData.containsKey(weeklyData.getWeekEndDate())) {

								if (weeklyData.getWeekStartDate() != null) {

									weekWiseReportData.put(weeklyData.getWeekEndDate(),Constants.DATE_FORMAT_REPORTS.format(weeklyData
											.getWeekStartDate())+ "-"+ Constants.DATE_FORMAT_REPORTS.format(weeklyData.getWeekEndDate()));
								}
							}

						}
					}

				}
			}

			int rowNumberModuleHoursWeek = 3;

			Set<Entry<Date, String>> weekSet = weekWiseReportData.entrySet();

			Iterator<Entry<Date, String>> weekIterator = weekSet.iterator();

			while (weekIterator.hasNext()) {

				Entry<Date, String> entrySet = weekIterator.next();

				String weeks = entrySet.getValue();

				cellHeader1 = headerRowModuleReport.createCell(rowNumberModuleHoursWeek);

				cellHeader1.setCellStyle(styleHeaderYellow);

				cellHeader1.setCellValue(weeks);

				moduleReportSheet.addMergedRegion(new CellRangeAddress(0, 0, rowNumberModuleHoursWeek, rowNumberModuleHoursWeek + (count - 1)));

				rowNumberModuleHoursWeek = rowNumberModuleHoursWeek + count;
			}

			rowNumberModuleHoursWeek = 3;

			// style second row
			HSSFRow headerRowModuleReport1 = moduleReportSheet.createRow(1);

			for (int s = 0; s < weekWiseReportData.size(); s++) {

				cellHeader1 = headerRowModuleReport1.createCell(0);
				cellHeader1.setCellValue("");
				cellHeader1.setCellStyle(styleHeaderYellow);

				cellHeader1 = headerRowModuleReport1.createCell(1);
				cellHeader1.setCellValue("");
				cellHeader1.setCellStyle(styleHeaderYellow);

				cellHeader1 = headerRowModuleReport1.createCell(2);
				cellHeader1.setCellValue("");
				cellHeader1.setCellStyle(styleHeaderYellow);

				if (plannedHours) {
					cellHeader1 = headerRowModuleReport1.createCell(rowNumberModuleHoursWeek++);
					cellHeader1.setCellValue("Planned Hours");
					cellHeader1.setCellStyle(styleHeaderYellow);
				}

				if (productiveHours) {
					cellHeader1 = headerRowModuleReport1.createCell(rowNumberModuleHoursWeek++);
					cellHeader1.setCellValue("Reported - Productive Hours");
					cellHeader1.setCellStyle(styleHeaderYellow);
				}

				if (nonProductiveHours) {
					cellHeader1 = headerRowModuleReport1.createCell(rowNumberModuleHoursWeek++);
					cellHeader1.setCellValue("Reported - Non Productive Hours");
					cellHeader1.setCellStyle(styleHeaderYellow);
				}

				if (billedHours) {
					cellHeader1 = headerRowModuleReport1.createCell(rowNumberModuleHoursWeek++);
					cellHeader1.setCellValue("Billed Hours");
					cellHeader1.setCellStyle(styleHeaderYellow);
				}

			}

			int rowNumberModuleHours = 2;
			int totalResourceInModuleReport = 0;

			Iterator<Entry<String, Map<String, List<ModuleReport>>>> iterator1 = moduleReportSet.iterator();

			while (iterator1.hasNext()) {

				// style third row
				HSSFRow headerRowModuleReport2 = moduleReportSheet.createRow(rowNumberModuleHours);

				int moduleHoursRowNum = 0;

				Entry<String, Map<String, List<ModuleReport>>> entrySet = iterator1.next();

				String moduleName = entrySet.getKey();

				Map<String, List<ModuleReport>> projectMap = entrySet.getValue();

				Set<Entry<String, List<ModuleReport>>> projectSet = projectMap.entrySet();

				String projectName[] = new String[projectMap.size()];

				Iterator<Entry<String, List<ModuleReport>>> projectMapIterator = projectSet.iterator();

				int columnIndexProjectMap = 0;

				List<List<ModuleReport>> resourcelist = new ArrayList<List<ModuleReport>>();

				int sizeOfResourceList = 1;

				Integer[] sizoOfProjectColumn = new Integer[projectMap.size()];

				String project = null;

				while (projectMapIterator.hasNext()) {

					Entry<String, List<ModuleReport>> entrySetprojectMapIterator = projectMapIterator.next();

					project = entrySetprojectMapIterator.getKey();

					projectName[columnIndexProjectMap] = project;

					sizoOfProjectColumn[columnIndexProjectMap++] = entrySetprojectMapIterator.getValue().size() + 1;

					sizeOfResourceList = sizeOfResourceList + (entrySetprojectMapIterator.getValue().size() + 1);

					resourcelist.add(entrySetprojectMapIterator.getValue());

				}

				cellHeader1 = headerRowModuleReport2.createCell(0);
				cellHeader1.setCellValue(moduleName);
				cellHeader1.setCellStyle(styleHeaderPeach);

				int LastRowForModule = sizeOfResourceList + 1 + totalResourceInModuleReport;
				int LastRowforModuleName = LastRowForModule;

				cellHeader1 = headerRowModuleReport2.createCell(1);
				cellHeader1.setCellValue("");
				cellHeader1.setCellStyle(styleHeaderPeach);

				cellHeader1 = headerRowModuleReport2.createCell(2);
				cellHeader1.setCellValue("Total Module Hours");
				cellHeader1.setCellStyle(styleHeaderPeach);

				moduleHoursRowNum = headerRowModuleReport2.getRowNum();

				totalModuleHourCellNumber.put(moduleHoursRowNum, LastRowforModuleName);

				int rowNumberModuleReportProject = 0;

				int rowNumberCurrentIndex = rowNumberModuleHours + 1;

				for (String projects : projectName) {

					// style fourth row

					HSSFRow headerRowModuleReport3 = moduleReportSheet.createRow(rowNumberCurrentIndex);

					cellHeader1 = headerRowModuleReport3.createCell(0);
					cellHeader1.setCellValue("");
					cellHeader1.setCellStyle(styleHeaderPeach);

					List<ModuleReport> listModuleReportProject = resourcelist.get(rowNumberModuleReportProject);

					HSSFCell cellHeader30 = headerRowModuleReport3.createCell(1);
					cellHeader30.setCellValue(projects);
					cellHeader30.setCellStyle(styleHeaderOrange);

					int rowNumber = 1;

					cellHeader30 = headerRowModuleReport3.createCell(2);
					cellHeader30.setCellValue("Total Hours Project");
					cellHeader30.setCellStyle(styleHeaderOrange);

					List<Integer> weekSize = new ArrayList<Integer>();

					for (ModuleReport resources : listModuleReportProject) {

						weekSize.add(resources.getWeeklyData().size());
					}

					Collections.sort(weekSize, Collections.reverseOrder());

					for (ModuleReport resources : listModuleReportProject) {

						// style fifth row
						HSSFRow headerRowModuleReport5 = moduleReportSheet.createRow(rowNumberCurrentIndex + rowNumber);

						cellHeader1 = headerRowModuleReport5.createCell(0);
						cellHeader1.setCellValue("");
						cellHeader1.setCellStyle(styleHeaderPeach);

						cellHeader1 = headerRowModuleReport5.createCell(1);
						cellHeader1.setCellValue("");
						cellHeader1.setCellStyle(styleHeaderOrange);

						Cell cellHeader22 = headerRowModuleReport5.createCell(2);
						cellHeader22.setCellValue(resources.getEmployeeName());
						cellHeader22.setCellStyle(cellFont);

						List<WeeklyData> weeklydata = resources.getWeeklyData();

						int rowNumberModuleHoursWeekEndDate = 3;

						rowNumberModuleHoursWeek = 3;
						weekIterator = weekSet.iterator();

						while (weekIterator.hasNext()) {

							Date weekEnd = weekIterator.next().getKey();

							for (int d = 0; d < weeklydata.size(); d++) {

								if (weeklydata.get(d).getWeekEndDate().compareTo(weekEnd) == 0) {

									if (plannedHours) {
										cellHeader22 = headerRowModuleReport5.createCell(rowNumberModuleHoursWeekEndDate++);
										if (null != weeklydata.get(d).getPlannedHours()) {

											cellHeader22.setCellValue(weeklydata.get(d).getPlannedHours());
											cellHeader22.setCellStyle(cellFont);

										} else {

											cellHeader22.setCellValue(0);
											cellHeader22.setCellStyle(cellFont);

										}
									}

									if (productiveHours) {
										cellHeader22 = headerRowModuleReport5.createCell(rowNumberModuleHoursWeekEndDate++);
										cellHeader22.setCellValue(weeklydata.get(d).getProductiveHours());
										cellHeader22.setCellStyle(cellFont);
									}

									if (nonProductiveHours) {
										cellHeader22 = headerRowModuleReport5.createCell(rowNumberModuleHoursWeekEndDate++);
										cellHeader22.setCellValue(weeklydata.get(d).getNonProductiveHours());
										cellHeader22.setCellStyle(cellFont);
									}

									if (billedHours) {
										cellHeader22 = headerRowModuleReport5.createCell(rowNumberModuleHoursWeekEndDate++);

										if (null != weeklydata.get(d).getBilledHours()) {

											cellHeader22.setCellValue(weeklydata.get(d).getBilledHours());
											cellHeader22.setCellStyle(cellFont);

										} else {

											cellHeader22.setCellValue(0);
											cellHeader22.setCellStyle(cellFont);

										}
									}

								}
							}
						}

						rowNumber++;

						totalColumnsModuleReport = rowNumberModuleHoursWeekEndDate;
					}

					// Rowspan Merging for Project Name
					moduleReportSheet.addMergedRegion(new CellRangeAddress(rowNumberCurrentIndex, (rowNumberCurrentIndex
							+ sizoOfProjectColumn[rowNumberModuleReportProject] - 1), 1, 1));

					rowNumberCurrentIndex = rowNumberCurrentIndex + sizoOfProjectColumn[rowNumberModuleReportProject];

					rowNumberModuleReportProject++;

					// Rowspan Merging for Name
					moduleReportSheet.addMergedRegion(new CellRangeAddress(rowNumberModuleHours, LastRowforModuleName, 0, 0));

				}

				rowNumberModuleHours = LastRowforModuleName + 1;

				totalResourceInModuleReport = totalResourceInModuleReport + sizeOfResourceList;

			}

			// Sum Of Project Hours

			int rowIndexProjectHours = 0;

			Map<Integer, List<Integer>> projectHourCellMap = new TreeMap<Integer, List<Integer>>();

			List<Integer> projectHoursCell = null;

			for (int columnIndex = 3; columnIndex < totalColumnsModuleReport; columnIndex++) {

				double sumOfProjectHours = 0.0;

				projectHoursCell = new ArrayList<Integer>();

				for (int rowIndex = 4; rowIndex <= moduleReportSheet.getLastRowNum() + 1; rowIndex++) {

					int rowIndex2 = rowIndex - rowIndexProjectHours - 1;

					if (moduleReportSheet.getRow(rowIndex) != null) {

						Cell cell = moduleReportSheet.getRow(rowIndex).getCell(columnIndex);

						if (cell != null && cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {

							sumOfProjectHours = sumOfProjectHours + cell.getNumericCellValue();

							rowIndexProjectHours++;

						} else {

							if (moduleReportSheet.getRow(rowIndex2).getCell(columnIndex) == null) {

								Cell cellSumOfProjectHours = moduleReportSheet.getRow(rowIndex2).createCell(columnIndex);

								cellSumOfProjectHours.setCellStyle(styleHeaderOrange);
							}

							moduleReportSheet.getRow(rowIndex2).getCell(columnIndex).setCellValue(sumOfProjectHours);

							moduleReportSheet.getRow(rowIndex2).getCell(columnIndex).setCellStyle(styleHeaderOrange);

							projectHoursCell.add(rowIndex2);

							sumOfProjectHours = 0.0;

							rowIndexProjectHours = 0;

						}
					} else {

						if (moduleReportSheet.getRow(rowIndex2).getCell(columnIndex) == null) {

							Cell cellSumOfProjectHours = moduleReportSheet.getRow(rowIndex2).createCell(columnIndex);

							cellSumOfProjectHours.setCellStyle(styleHeaderOrange);
						}

						moduleReportSheet.getRow(rowIndex2).getCell(columnIndex).setCellValue(sumOfProjectHours);

						moduleReportSheet.getRow(rowIndex2).getCell(columnIndex).setCellStyle(styleHeaderOrange);

						projectHoursCell.add(rowIndex2);

						sumOfProjectHours = 0.0;

						rowIndexProjectHours = 0;
					}

				}

				projectHourCellMap.put(columnIndex, projectHoursCell);
			}

			// Total module Hours Calculation

			Set<Entry<Integer, Integer>> totalModuleHours = totalModuleHourCellNumber.entrySet();

			Iterator<Entry<Integer, Integer>> ModuleReportTotalHoursIterator = totalModuleHours.iterator();

			Set<Entry<Integer, List<Integer>>> totalProjectHours = projectHourCellMap.entrySet();

			Iterator<Entry<Integer, List<Integer>>> totalProjectHourIterator = null;

			while (ModuleReportTotalHoursIterator.hasNext()) {

				Entry<Integer, Integer> ModuleReporttotalModuleHours = ModuleReportTotalHoursIterator.next();

				Integer moduleHourIndex = ModuleReporttotalModuleHours.getKey();

				Integer lastIndexOfModule = ModuleReporttotalModuleHours.getValue();

				totalProjectHourIterator = totalProjectHours.iterator();

				while (totalProjectHourIterator.hasNext()) {

					Entry<Integer, List<Integer>> ModuleReportTotalProjectHours = totalProjectHourIterator.next();

					Integer cellIndexTotalProjectHours = ModuleReportTotalProjectHours.getKey();

					List<Integer> sumOfProjectHoursRowIndex = ModuleReportTotalProjectHours.getValue();

					double sumOfProjectHours = 0;

					for (Integer sumOfProjectHoursCurrentIndex : sumOfProjectHoursRowIndex) {

						if (sumOfProjectHoursCurrentIndex < lastIndexOfModule && sumOfProjectHoursCurrentIndex > moduleHourIndex) {

							sumOfProjectHours = sumOfProjectHours
									+ moduleReportSheet.getRow(sumOfProjectHoursCurrentIndex).getCell(cellIndexTotalProjectHours)
											.getNumericCellValue();
						}
					}

					if (null == moduleReportSheet.getRow(moduleHourIndex).getCell(cellIndexTotalProjectHours)) {

						Cell cellSumOfProjectHours = moduleReportSheet.getRow(moduleHourIndex).createCell(cellIndexTotalProjectHours);

						cellSumOfProjectHours.setCellStyle(styleHeaderPeach);
					}

					moduleReportSheet.getRow(moduleHourIndex).getCell(cellIndexTotalProjectHours).setCellValue(sumOfProjectHours);

					moduleReportSheet.getRow(moduleHourIndex).getCell(cellIndexTotalProjectHours).setCellStyle(styleHeaderPeach);
				}

			}
		}

	}

	private static CellStyle setCellStylePeach(HSSFWorkbook workbookModuleReport) {

		CellStyle styleHeader = workbookModuleReport.createCellStyle();

		headerBoundry(workbookModuleReport, styleHeader);

		styleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		HSSFPalette palette = workbookModuleReport.getCustomPalette();

		palette.setColorAtIndex(new Byte((byte) 47), new Byte((byte) 210), new Byte((byte) 180), new Byte((byte) 140));

		styleHeader.setFillForegroundColor(palette.getColor(47).getIndex());

		return styleHeader;
	}

	private static CellStyle setCellStyleYellow(HSSFWorkbook workbookModuleReport) {

		CellStyle styleHeader = workbookModuleReport.createCellStyle();

		headerBoundry(workbookModuleReport, styleHeader);

		styleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		HSSFPalette palette = workbookModuleReport.getCustomPalette();

		palette.setColorAtIndex(new Byte((byte) 48), new Byte((byte) 255), new Byte((byte) 215), new Byte((byte) 0));

		styleHeader.setFillForegroundColor(palette.getColor(48).getIndex());

		return styleHeader;
	}

	private static CellStyle setCellStyleOrange(HSSFWorkbook workbookModuleReport) {

		CellStyle styleHeader = workbookModuleReport.createCellStyle();

		headerBoundry(workbookModuleReport, styleHeader);

		styleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		HSSFPalette palette = workbookModuleReport.getCustomPalette();

		palette.setColorAtIndex(new Byte((byte) 49), new Byte((byte) 244), new Byte((byte) 164), new Byte((byte) 96));

		styleHeader.setFillForegroundColor(palette.getColor(49).getIndex());

		return styleHeader;
	}

	private static void headerBoundry(HSSFWorkbook workbookModuleReport, CellStyle styleHeader) {

		Font font = workbookModuleReport.createFont();

		font.setFontName("Calibri");

		font.setFontHeightInPoints((short) 11);// Create font
		font.setBoldweight(Font.BOLDWEIGHT_NORMAL);// Make font bold
		styleHeader.setFont(font);

		styleHeader.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		styleHeader.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		styleHeader.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		styleHeader.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);

		styleHeader.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleHeader.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

	}

	private static CellStyle setFontStyle(HSSFWorkbook workbookModuleReport) {

		CellStyle styleHeader = workbookModuleReport.createCellStyle();

		Font font = workbookModuleReport.createFont();
		font.setFontName("Calibri");

		font.setFontHeightInPoints((short) 10);// Create font

		styleHeader.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleHeader.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleHeader.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleHeader.setBorderRight(HSSFCellStyle.BORDER_THIN);

		styleHeader.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		styleHeader.setFont(font);

		return styleHeader;
	}
}
