package org.yash.rms.excel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

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
import org.yash.rms.report.dto.TeamReport;
import org.yash.rms.report.dto.WeeklyData;
import org.yash.rms.util.Constants;

@SuppressWarnings("unchecked")
public class TeamReportExcelUtility extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbookTeamReport, HttpServletRequest request, HttpServletResponse response) throws Exception {

		logger.debug("Started buildExcelDocument in TeamReportExcelUtility Class");

		HSSFSheet teamReportSheet = workbookTeamReport.createSheet("TeamReportSheet");

		response.setHeader("Content-Disposition", "attachment; filename=Team_Report.xls");

		teamReportSheet.setDefaultColumnWidth(28);

		Map<String, Map<String, List<TeamReport>>> teamReportList = (Map<String, Map<String, List<TeamReport>>>) model.get("teamReportsList");

		int totalColumnsTeamReport = 0;

		if (!teamReportList.isEmpty()) {

			Map<Integer, Integer> totalTeamHourCellNumber = new TreeMap<Integer, Integer>();

			Set<Entry<String, Map<String, List<TeamReport>>>> teamReportSet = teamReportList.entrySet();

			CellStyle styleHeaderPeach = setCellStylePeach(workbookTeamReport);

			CellStyle styleHeaderYellow = setCellStyleYellow(workbookTeamReport);

			CellStyle styleHeaderOrange = setCellStyleOrange(workbookTeamReport);

			CellStyle cellFont = setFontStyle(workbookTeamReport);

			// First Header
			// style first row

			HSSFRow headerRowTeamReport = teamReportSheet.createRow(0);

			Cell cellHeader = headerRowTeamReport.createCell(0);
			cellHeader.setCellValue("TeamName");
			cellHeader.setCellStyle(styleHeaderYellow);

			teamReportSheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));

			Cell cellHeader1 = headerRowTeamReport.createCell(1);
			cellHeader1.setCellValue("ProjectName");

			teamReportSheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
			cellHeader1.setCellStyle(styleHeaderYellow);

			Cell cellHeader2 = headerRowTeamReport.createCell(2);
			cellHeader2.setCellValue("EmployeeName");

			teamReportSheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 2));
			cellHeader2.setCellStyle(styleHeaderYellow);

			Map<Date, String> weekWiseReportData = new TreeMap<Date, String>();

			Iterator<Entry<String, Map<String, List<TeamReport>>>> iterator = teamReportSet.iterator();

			while (iterator.hasNext()) {

				Entry<String, Map<String, List<TeamReport>>> entrySetTeamReport = iterator.next();

				Map<String, List<TeamReport>> projectMapTeamReport = entrySetTeamReport.getValue();

				Set<Entry<String, List<TeamReport>>> projectSetTeamReport = projectMapTeamReport.entrySet();

				Iterator<Entry<String, List<TeamReport>>> projectMapIterator = projectSetTeamReport.iterator();

				while (projectMapIterator.hasNext()) {

					Entry<String, List<TeamReport>> entrySetTeamReportProject = projectMapIterator.next();

					List<TeamReport> resourceListProject = entrySetTeamReportProject.getValue();

					for (TeamReport teamReportResourceList : resourceListProject) {

						List<WeeklyData> weeklyLists = teamReportResourceList.getWeeklyData();

						for (WeeklyData weeklyData : weeklyLists) {

							if (!weekWiseReportData.containsKey(weeklyData.getWeekEndDate())) {

								if (weeklyData.getWeekStartDate() != null) {

									weekWiseReportData.put(weeklyData.getWeekEndDate(), Constants.DATE_FORMAT_REPORTS.format(weeklyData.getWeekStartDate()) + " To "
											+ Constants.DATE_FORMAT_REPORTS.format(weeklyData.getWeekEndDate()));

								}
							}

						}
					}

				}
			}

			int rowNumberTeamHoursWeek = 3;

			Set<Entry<Date, String>> weekSet = weekWiseReportData.entrySet();

			Iterator<Entry<Date, String>> weekIterator = weekSet.iterator();
			int i = 0;

			while (weekIterator.hasNext()) {

				Entry<Date, String> entrySet = weekIterator.next();

				String weeks = entrySet.getValue();

				cellHeader1 = headerRowTeamReport.createCell(rowNumberTeamHoursWeek);

				if(i%2 == 1)
					cellHeader1.setCellStyle(styleHeaderYellow);
				else
					cellHeader1.setCellStyle(styleHeaderOrange);

				cellHeader1.setCellValue(weeks);

				teamReportSheet.addMergedRegion(new CellRangeAddress(0, 0, rowNumberTeamHoursWeek, rowNumberTeamHoursWeek + 3));

				rowNumberTeamHoursWeek = rowNumberTeamHoursWeek + 4;
				
				i++;
			}

			rowNumberTeamHoursWeek = 3;

			// style second row
			HSSFRow headerRowTeamReport1 = teamReportSheet.createRow(1);

			for (int s = 0; s < weekWiseReportData.size(); s++) {

				cellHeader1 = headerRowTeamReport1.createCell(0);
				cellHeader1.setCellValue("");
				cellHeader1.setCellStyle(styleHeaderYellow);

				cellHeader1 = headerRowTeamReport1.createCell(1);
				cellHeader1.setCellValue("");
				cellHeader1.setCellStyle(styleHeaderYellow);

				cellHeader1 = headerRowTeamReport1.createCell(2);
				cellHeader1.setCellValue("");
				cellHeader1.setCellStyle(styleHeaderYellow);

				cellHeader1 = headerRowTeamReport1.createCell(rowNumberTeamHoursWeek++);
				cellHeader1.setCellValue("Planned Hours");
				if(s%2 == 1)
					cellHeader1.setCellStyle(styleHeaderYellow);
				else
					cellHeader1.setCellStyle(styleHeaderOrange);

				cellHeader1 = headerRowTeamReport1.createCell(rowNumberTeamHoursWeek++);
				cellHeader1.setCellValue("Reported - Productive Hours");
				if(s%2 == 1)
					cellHeader1.setCellStyle(styleHeaderYellow);
				else
					cellHeader1.setCellStyle(styleHeaderOrange);

				cellHeader1 = headerRowTeamReport1.createCell(rowNumberTeamHoursWeek++);
				cellHeader1.setCellValue("Reported - Non Productive Hours");
				if(s%2 == 1)
					cellHeader1.setCellStyle(styleHeaderYellow);
				else
					cellHeader1.setCellStyle(styleHeaderOrange);

				cellHeader1 = headerRowTeamReport1.createCell(rowNumberTeamHoursWeek++);
				cellHeader1.setCellValue("Billed Hours");
				if(s%2 == 1)
					cellHeader1.setCellStyle(styleHeaderYellow);
				else
					cellHeader1.setCellStyle(styleHeaderOrange);

			}

			int rowNumberTeamHours = 2;
			int totalResourceInTeamReport = 0;

			Iterator<Entry<String, Map<String, List<TeamReport>>>> iterator1 = teamReportSet.iterator();

			while (iterator1.hasNext()) {

				// style third row
				HSSFRow headerRowTeamReport2 = teamReportSheet.createRow(rowNumberTeamHours);

				int teamHoursRowNum = 0;

				Entry<String, Map<String, List<TeamReport>>> entrySet = iterator1.next();

				String teamName = entrySet.getKey();

				Map<String, List<TeamReport>> projectMap = entrySet.getValue();

				Set<Entry<String, List<TeamReport>>> projectSet = projectMap.entrySet();

				String projectName[] = new String[projectMap.size()];

				Iterator<Entry<String, List<TeamReport>>> projectMapIterator = projectSet.iterator();

				int columnIndexProjectMap = 0;

				List<List<TeamReport>> resourcelist = new ArrayList<List<TeamReport>>();

				int sizeOfResourceList = 1;

				Integer[] sizoOfProjectColumn = new Integer[projectMap.size()];

				String project = null;

				while (projectMapIterator.hasNext()) {

					Entry<String, List<TeamReport>> entrySetprojectMapIterator = projectMapIterator.next();

					project = entrySetprojectMapIterator.getKey();

					projectName[columnIndexProjectMap] = project;

					sizoOfProjectColumn[columnIndexProjectMap++] = entrySetprojectMapIterator.getValue().size() + 1;

					sizeOfResourceList = sizeOfResourceList + (entrySetprojectMapIterator.getValue().size() + 1);

					resourcelist.add(entrySetprojectMapIterator.getValue());

				}

				cellHeader1 = headerRowTeamReport2.createCell(0);
				cellHeader1.setCellValue(teamName);
				cellHeader1.setCellStyle(styleHeaderPeach);

				int LastRowForTeam = sizeOfResourceList + 1 + totalResourceInTeamReport;
				int LastRowforTeamName = LastRowForTeam;

				cellHeader1 = headerRowTeamReport2.createCell(1);
				cellHeader1.setCellValue("");
				cellHeader1.setCellStyle(styleHeaderPeach);

				cellHeader1 = headerRowTeamReport2.createCell(2);
				cellHeader1.setCellValue("Total Hours Team");
				cellHeader1.setCellStyle(styleHeaderPeach);

				teamHoursRowNum = headerRowTeamReport2.getRowNum();

				totalTeamHourCellNumber.put(teamHoursRowNum, LastRowforTeamName);

				int rowNumberTeamReportProject = 0;

				int rowNumberCurrentIndex = rowNumberTeamHours + 1;

				for (String projects : projectName) {

					// style fourth row

					HSSFRow headerRowTeamReport3 = teamReportSheet.createRow(rowNumberCurrentIndex);

					cellHeader1 = headerRowTeamReport3.createCell(0);
					cellHeader1.setCellValue("");
					cellHeader1.setCellStyle(styleHeaderPeach);

					List<TeamReport> listTeamReportProject = resourcelist.get(rowNumberTeamReportProject);

					HSSFCell cellHeader30 = headerRowTeamReport3.createCell(1);
					cellHeader30.setCellValue(projects);
					cellHeader30.setCellStyle(styleHeaderOrange);

					int rowNumber = 1;

					cellHeader30 = headerRowTeamReport3.createCell(2);
					cellHeader30.setCellValue("Total Hours Project");
					cellHeader30.setCellStyle(styleHeaderOrange);

					List<Integer> weekSize = new ArrayList<Integer>();

					for (TeamReport resources : listTeamReportProject) {

						weekSize.add(resources.getWeeklyData().size());
					}

					Collections.sort(weekSize, Collections.reverseOrder());

					for (TeamReport resources : listTeamReportProject) {

						// style fifth row
						HSSFRow headerRowTeamReport5 = teamReportSheet.createRow(rowNumberCurrentIndex + rowNumber);

						cellHeader1 = headerRowTeamReport5.createCell(0);
						cellHeader1.setCellValue("");
						cellHeader1.setCellStyle(styleHeaderPeach);

						cellHeader1 = headerRowTeamReport5.createCell(1);
						cellHeader1.setCellValue("");
						cellHeader1.setCellStyle(styleHeaderOrange);

						Cell cellHeader22 = headerRowTeamReport5.createCell(2);
						cellHeader22.setCellValue(resources.getEmployeeName());
						cellHeader22.setCellStyle(cellFont);

						List<WeeklyData> weeklydata = resources.getWeeklyData();

						int rowNumberTeamHoursWeekEndDate = 3;

						rowNumberTeamHoursWeek = 3;
						weekIterator = weekSet.iterator();

						while (weekIterator.hasNext()) {

							Date weekEnd = weekIterator.next().getKey();

							for (int d = 0; d < weeklydata.size(); d++) {

								if (weeklydata.get(d).getWeekEndDate().compareTo(weekEnd) == 0) {

									cellHeader22 = headerRowTeamReport5.createCell(rowNumberTeamHoursWeekEndDate++);

									if (null != weeklydata.get(d).getPlannedHours()) {

										cellHeader22.setCellValue(weeklydata.get(d).getPlannedHours());
										cellHeader22.setCellStyle(cellFont);

									} else {

										cellHeader22.setCellValue(0);
										cellHeader22.setCellStyle(cellFont);

									}
									cellHeader22 = headerRowTeamReport5.createCell(rowNumberTeamHoursWeekEndDate++);
									cellHeader22.setCellValue(weeklydata.get(d).getProductiveHours());
									cellHeader22.setCellStyle(cellFont);

									cellHeader22 = headerRowTeamReport5.createCell(rowNumberTeamHoursWeekEndDate++);
									cellHeader22.setCellValue(weeklydata.get(d).getNonProductiveHours());
									cellHeader22.setCellStyle(cellFont);

									cellHeader22 = headerRowTeamReport5.createCell(rowNumberTeamHoursWeekEndDate++);

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

						rowNumber++;

						totalColumnsTeamReport = rowNumberTeamHoursWeekEndDate;
					}

					// Rowspan Merging for Project Name
					teamReportSheet.addMergedRegion(new CellRangeAddress(rowNumberCurrentIndex, (rowNumberCurrentIndex + sizoOfProjectColumn[rowNumberTeamReportProject] - 1), 1, 1));

					rowNumberCurrentIndex = rowNumberCurrentIndex + sizoOfProjectColumn[rowNumberTeamReportProject];

					rowNumberTeamReportProject++;

					// Rowspan Merging for team Name
					teamReportSheet.addMergedRegion(new CellRangeAddress(rowNumberTeamHours, LastRowforTeamName, 0, 0));

				}

				rowNumberTeamHours = LastRowforTeamName + 1;

				totalResourceInTeamReport = totalResourceInTeamReport + sizeOfResourceList;

			}

			// Sum Of Project Hours

			int rowIndexProjectHours = 0;

			Map<Integer, List<Integer>> projectHourCellMap = new TreeMap<Integer, List<Integer>>();

			List<Integer> projectHoursCell = null;

			for (int columnIndex = 3; columnIndex < totalColumnsTeamReport; columnIndex++) {

				double sumOfProjectHours = 0.0;

				projectHoursCell = new ArrayList<Integer>();

				for (int rowIndex = 4; rowIndex <= teamReportSheet.getLastRowNum() + 1; rowIndex++) {

					int rowIndex2 = rowIndex - rowIndexProjectHours - 1;

					if (teamReportSheet.getRow(rowIndex) != null) {

						Cell cell = teamReportSheet.getRow(rowIndex).getCell(columnIndex);

						if (cell != null && cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {

							sumOfProjectHours = sumOfProjectHours + cell.getNumericCellValue();

							rowIndexProjectHours++;

						} else {

							if (teamReportSheet.getRow(rowIndex2).getCell(columnIndex) == null) {

								Cell cellSumOfProjectHours = teamReportSheet.getRow(rowIndex2).createCell(columnIndex);

								cellSumOfProjectHours.setCellStyle(styleHeaderOrange);
							}

							teamReportSheet.getRow(rowIndex2).getCell(columnIndex).setCellValue(sumOfProjectHours);

							teamReportSheet.getRow(rowIndex2).getCell(columnIndex).setCellStyle(styleHeaderOrange);

							projectHoursCell.add(rowIndex2);

							sumOfProjectHours = 0.0;

							rowIndexProjectHours = 0;

						}
					} else {

						if (teamReportSheet.getRow(rowIndex2).getCell(columnIndex) == null) {

							Cell cellSumOfProjectHours = teamReportSheet.getRow(rowIndex2).createCell(columnIndex);

							cellSumOfProjectHours.setCellStyle(styleHeaderOrange);
						}

						teamReportSheet.getRow(rowIndex2).getCell(columnIndex).setCellValue(sumOfProjectHours);

						teamReportSheet.getRow(rowIndex2).getCell(columnIndex).setCellStyle(styleHeaderOrange);

						projectHoursCell.add(rowIndex2);

						sumOfProjectHours = 0.0;

						rowIndexProjectHours = 0;
					}

				}

				projectHourCellMap.put(columnIndex, projectHoursCell);
			}

			// Total Team Hours Calculation

			Set<Entry<Integer, Integer>> totalTeamHours = totalTeamHourCellNumber.entrySet();

			Iterator<Entry<Integer, Integer>> teamReportTotalHoursIterator = totalTeamHours.iterator();

			Set<Entry<Integer, List<Integer>>> totalProjectHours = projectHourCellMap.entrySet();

			Iterator<Entry<Integer, List<Integer>>> totalProjectHourIterator = null;

			while (teamReportTotalHoursIterator.hasNext()) {

				Entry<Integer, Integer> teamReportTotalTeamHours = teamReportTotalHoursIterator.next();

				Integer teamHourIndex = teamReportTotalTeamHours.getKey();

				Integer lastIndexOfTeam = teamReportTotalTeamHours.getValue();

				totalProjectHourIterator = totalProjectHours.iterator();

				while (totalProjectHourIterator.hasNext()) {

					Entry<Integer, List<Integer>> teamReportTotalProjectHours = totalProjectHourIterator.next();

					Integer cellIndexTotalProjectHours = teamReportTotalProjectHours.getKey();

					List<Integer> sumOfProjectHoursRowIndex = teamReportTotalProjectHours.getValue();

					double sumOfProjectHours = 0;

					for (Integer sumOfProjectHoursCurrentIndex : sumOfProjectHoursRowIndex) {

						if (sumOfProjectHoursCurrentIndex < lastIndexOfTeam && sumOfProjectHoursCurrentIndex > teamHourIndex) {

							sumOfProjectHours = sumOfProjectHours + teamReportSheet.getRow(sumOfProjectHoursCurrentIndex).getCell(cellIndexTotalProjectHours).getNumericCellValue();
						}
					}

					if (null == teamReportSheet.getRow(teamHourIndex).getCell(cellIndexTotalProjectHours)) {

						Cell cellSumOfProjectHours = teamReportSheet.getRow(teamHourIndex).createCell(cellIndexTotalProjectHours);

						cellSumOfProjectHours.setCellStyle(styleHeaderPeach);
					}

					teamReportSheet.getRow(teamHourIndex).getCell(cellIndexTotalProjectHours).setCellValue(sumOfProjectHours);

					teamReportSheet.getRow(teamHourIndex).getCell(cellIndexTotalProjectHours).setCellStyle(styleHeaderPeach);
				}

			}
		}

	}

	private static CellStyle setCellStylePeach(HSSFWorkbook workbookTeamReport) {

		CellStyle styleHeader = workbookTeamReport.createCellStyle();

		headerBoundry(workbookTeamReport, styleHeader);

		styleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		HSSFPalette palette = workbookTeamReport.getCustomPalette();

		palette.setColorAtIndex(new Byte((byte) 47), new Byte((byte) 210), new Byte((byte) 180), new Byte((byte) 140));

		styleHeader.setFillForegroundColor(palette.getColor(47).getIndex());

		return styleHeader;
	}

	private static CellStyle setCellStyleYellow(HSSFWorkbook workbookTeamReport) {

		CellStyle styleHeader = workbookTeamReport.createCellStyle();

		headerBoundry(workbookTeamReport, styleHeader);

		styleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		HSSFPalette palette = workbookTeamReport.getCustomPalette();

		palette.setColorAtIndex(new Byte((byte) 48), new Byte((byte) 255), new Byte((byte) 215), new Byte((byte) 0));

		styleHeader.setFillForegroundColor(palette.getColor(48).getIndex());

		return styleHeader;
	}

	private static CellStyle setCellStyleOrange(HSSFWorkbook workbookTeamReport) {

		CellStyle styleHeader = workbookTeamReport.createCellStyle();

		headerBoundry(workbookTeamReport, styleHeader);

		styleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		HSSFPalette palette = workbookTeamReport.getCustomPalette();

		palette.setColorAtIndex(new Byte((byte) 49), new Byte((byte) 244), new Byte((byte) 164), new Byte((byte) 96));

		styleHeader.setFillForegroundColor(palette.getColor(49).getIndex());

		return styleHeader;
	}

	private static void headerBoundry(HSSFWorkbook workbookTeamReport, CellStyle styleHeader) {

		Font font = workbookTeamReport.createFont();

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

	private static CellStyle setFontStyle(HSSFWorkbook workbookTeamReport) {

		CellStyle styleHeader = workbookTeamReport.createCellStyle();

		Font font = workbookTeamReport.createFont();
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