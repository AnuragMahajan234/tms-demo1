package org.yash.rms.excel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import org.yash.rms.report.dto.ResourceRequisitionProfileStatusReport;

public class RequestRequisitionExcelWeeklyReportView extends AbstractExcelView {

    private static final String RRF_REPORT_FILE_NAME = "RRF_WEEKLY_REPORT";

    private static final String FONT_CALIBRI = "Calibri";

    private static final String RRF_REPORT_EXCEL_SHEET_NAME = "RRF Report";

    private static final String[] HEADERNAME = { "", "Client", "Open", "Submitted", "Shortlisted", "Rejected", "closed" };

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("----------Entered into RequestRequisitionReportExcelView and start to build excel document-----------");

        response.reset();
        response.setContentType("application/vnd.ms-excel");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();

        response.setHeader("Content-Disposition", "attachment; filename=" + RRF_REPORT_FILE_NAME + "_" + dateFormat.format(todayDate) + ".xls");

        try {
            prepareExcelReportForRequestRequisition(model, workbook);
            workbook.write(response.getOutputStream());
        } catch (ParseException e) {
            logger.error("Error in parse date.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.debug("----------Exit from RequestRequisitionReportExcelView and build excel document finished-----------");
    }

    private void prepareExcelReportForRequestRequisition(Map<String, Object> model, HSSFWorkbook workbook) throws ParseException {

        String startDate = (String) model.get("startDate");
        String endDate = (String) model.get("endDate");
        String statusIds = (String) model.get("statusIds");

        HSSFSheet sheet = ExcelUtility.getHSSFSheet(workbook, RRF_REPORT_EXCEL_SHEET_NAME);
        setHeadersInExcel(workbook, sheet, startDate, endDate, statusIds);

        List<ResourceRequisitionProfileStatusReport> profileStatusReportList = (List<ResourceRequisitionProfileStatusReport>) model.get("profileStatusReportList");
        prapreExcelDataTable(sheet, profileStatusReportList, workbook);

    }

    public HSSFColor setCustColor(HSSFWorkbook workbook, byte r, byte g, byte b) {
        HSSFPalette palette = workbook.getCustomPalette();
        HSSFColor hssfColor = null;
        try {
            hssfColor = palette.findColor(r, g, b);
            if (hssfColor == null) {
                palette.setColorAtIndex(HSSFColor.LAVENDER.index, r, g, b);
                hssfColor = palette.getColor(HSSFColor.LAVENDER.index);
            }
        } catch (Exception e) {
            logger.error(e);
        }

        return hssfColor;
    }

    private void setHeadersInExcel(HSSFWorkbook workbook, HSSFSheet sheet, String startDate, String endDate, String statusIds) throws ParseException {

        if (startDate != null && startDate.trim().length() > 0) {
        }
        if (endDate != null && endDate.trim().length() > 0) {
        }

        /* set back ground color for first 4 row */
        HSSFCellStyle xssfCellStyleForDataCellResource = ExcelUtility.getHSSFCellStyleHeader(workbook);
        HSSFColor lightGray = setCustColor(workbook, (byte) 49, (byte) 134, (byte) 155);
        xssfCellStyleForDataCellResource.setFillForegroundColor(lightGray.getIndex());
        xssfCellStyleForDataCellResource.setBorderLeft(HSSFCellStyle.NO_FILL);
        xssfCellStyleForDataCellResource.setBorderBottom(HSSFCellStyle.NO_FILL);
        xssfCellStyleForDataCellResource.setBorderTop(HSSFCellStyle.NO_FILL);
        xssfCellStyleForDataCellResource.setBorderRight(HSSFCellStyle.NO_FILL);
        xssfCellStyleForDataCellResource.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.WHITE.index);
        xssfCellStyleForDataCellResource.setFont(font);

    }

    private void prapreExcelDataTable(HSSFSheet sheet, List<ResourceRequisitionProfileStatusReport> profileStatusReportList, HSSFWorkbook workbook) {

        HSSFRow row = null;
        HSSFRow subRow = null;
        int rowIndex = 0;
        int columnIndex = 0;
        Cell cell = null;
        int lastRowIndex = 0;
        int initialIndex = 0;

        HSSFCellStyle xssfCellStyleForDataCell = ExcelUtility.getHSSFCellStyleForDataCell(workbook);
        Font font = workbook.createFont();

        // setting the cell style for main data table
        CellStyle cellStyle = workbook.createCellStyle();
        setCellStyleForMainTable(cellStyle, font);

        CellStyle cellStyleSub = workbook.createCellStyle();
        setCellStyleForMainTable(cellStyleSub, font);
        // Setting the cell style for Open Status
        CellStyle cellStatusStyle = workbook.createCellStyle();
        setCellStyleForStatusCell(cellStatusStyle, font);

        CellStyle cellTotPosStyle = workbook.createCellStyle();
        setCellStyleForTotPosCell(cellTotPosStyle, font, workbook);
        CellStyle cellOpenStyle = workbook.createCellStyle();
        setCellStyleForOpenCell(cellOpenStyle, font, workbook);
        // setting the cell style for sub data table
        CellStyle cellStyleForSubTable = workbook.createCellStyle();
        Font fontForSubtable = workbook.createFont();
        setCellStyleForSubTable(cellStyleForSubTable, fontForSubtable, workbook);
        CellStyle cellStyleForSubTableDates = workbook.createCellStyle();
        setCellStyleForSubTable(cellStyleForSubTableDates, fontForSubtable, workbook);

        // setting the cell style for sub data table
        HSSFCellStyle cellStyleForSubTableHeader = workbook.createCellStyle();
        Font fontForSubtableHeader = workbook.createFont();
        setCellStyleForSubTableHeader(cellStyleForSubTableHeader, fontForSubtableHeader, workbook);

        /* setting the headers */
        row = sheet.createRow(0);
        setStyleForTableHeader(xssfCellStyleForDataCell, workbook);
        ExcelUtility.createHeaders(row, xssfCellStyleForDataCell, font, HEADERNAME, true);
        HSSFCellStyle simpleCellStyle = workbook.createCellStyle();
        setSimpleCellStyle(simpleCellStyle, workbook);

        if (profileStatusReportList != null && !profileStatusReportList.isEmpty()) {
            for (ResourceRequisitionProfileStatusReport reportData : profileStatusReportList) {

                columnIndex = 0;
                initialIndex = ++rowIndex;
                row = sheet.createRow(rowIndex);

                cell = row.createCell(columnIndex);
                cell.setCellValue("Total");
                cell.setCellStyle(cellStyle);
                //sheet.autoSizeColumn(columnIndex);
                columnIndex++;

                cell = row.createCell(columnIndex);
                cell.setCellValue(reportData.getClientName());
                cell.setCellStyle(cellStyle);
                //sheet.autoSizeColumn(columnIndex);
                columnIndex++;

                cell = row.createCell(columnIndex);
                cell.setCellValue(Integer.valueOf(reportData.getTotalOpen()));
                cell.setCellStyle(cellTotPosStyle);
                //sheet.autoSizeColumn(columnIndex);
                columnIndex++;

                cell = row.createCell(columnIndex);
                if (reportData.getSubmittedProfileStatus() != null) {
                    if ((Integer.valueOf(reportData.getSubmittedProfileStatus().getExternalResourceCount()) != null) && (Integer.valueOf(reportData.getSubmittedProfileStatus().getInternalResourceCount()) != null)) {
                        cell.setCellValue(Integer.valueOf(reportData.getSubmittedProfileStatus().getExternalResourceCount()) + Integer.valueOf(reportData.getSubmittedProfileStatus().getInternalResourceCount()));
                    }
                } else {
                    cell.setCellValue(Integer.valueOf(0));
                }
                cell.setCellStyle(cellOpenStyle);
                //sheet.autoSizeColumn(columnIndex);
                columnIndex++;

                cell = row.createCell(columnIndex);
                if (reportData.getShortlistedProfileStatus() != null) {
                    if ((Integer.valueOf(reportData.getShortlistedProfileStatus().getExternalResourceCount()) != null) && (Integer.valueOf(reportData.getShortlistedProfileStatus().getInternalResourceCount()) != null)) {
                        cell.setCellValue(Integer.valueOf(reportData.getShortlistedProfileStatus().getExternalResourceCount()) + Integer.valueOf(reportData.getShortlistedProfileStatus().getInternalResourceCount()));
                    }
                } else {
                    cell.setCellValue(Integer.valueOf(0));
                }
                cell.setCellStyle(cellTotPosStyle);
                //sheet.autoSizeColumn(columnIndex);
                columnIndex++;

                cell = row.createCell(columnIndex);
                if (reportData.getRejectedProfileStatus() != null) {
                    if ((Integer.valueOf(reportData.getRejectedProfileStatus().getExternalResourceCount()) != null) && (Integer.valueOf(reportData.getRejectedProfileStatus().getInternalResourceCount()) != null)) {
                        cell.setCellValue(Integer.valueOf(reportData.getRejectedProfileStatus().getExternalResourceCount()) + Integer.valueOf(reportData.getRejectedProfileStatus().getInternalResourceCount()));
                    }
                } else {
                    cell.setCellValue(Integer.valueOf(0));
                }
                cell.setCellStyle(cellOpenStyle);
                //sheet.autoSizeColumn(columnIndex);
                columnIndex++;

                cell = row.createCell(columnIndex);
                if (reportData.getClosedProfileStatus() != null) {
                    if ((Integer.valueOf(reportData.getClosedProfileStatus().getExternalResourceCount()) != null) && (Integer.valueOf(reportData.getClosedProfileStatus().getInternalResourceCount()) != null)) {
                        cell.setCellValue(Integer.valueOf(reportData.getClosedProfileStatus().getExternalResourceCount()) + Integer.valueOf(reportData.getClosedProfileStatus().getInternalResourceCount()));
                    }
                } else {
                    cell.setCellValue(Integer.valueOf(0));
                }
                cell.setCellStyle(cellTotPosStyle);
                //sheet.autoSizeColumn(columnIndex);
                columnIndex++;

                subRow = sheet.createRow(++rowIndex);
                ExcelUtility.createHSSFCellForHeaders((short) 0, subRow, cellStyleForSubTableHeader, font, "External", true);

                HSSFRow firstRow = sheet.createRow(++rowIndex);
                ExcelUtility.createHSSFCellForHeaders((short) 0, firstRow, cellStyleForSubTableHeader, font, "Internal", true);

                createSubDataTable(reportData, rowIndex, sheet, cellStyleForSubTable, workbook, cellStyleForSubTableDates, subRow, firstRow);

                logger.debug("Initial Index = " + initialIndex + 1);
                logger.debug("Last Index = " + (rowIndex));
                sheet.groupRow(initialIndex + 1, rowIndex);
                sheet.setRowGroupCollapsed(initialIndex + 1, true);
                sheet.setRowSumsBelow(false);
            }
            //sheet.autoSizeColumn((short) 2);
        }
    }

    private void createSubDataTable(ResourceRequisitionProfileStatusReport reportData, int rowIndex, HSSFSheet sheet, CellStyle cellStyle, HSSFWorkbook workbook, CellStyle cellStyleForSubTableDates, HSSFRow subRow, HSSFRow firstRow) {
        Cell cell = null;
        Cell cell1 = null;
        int columnIndex = 3;
        int columnIndex1 = 3;

        cell = subRow.createCell(columnIndex);
        if (reportData.getSubmittedProfileStatus() != null) {
            if (Integer.valueOf(reportData.getSubmittedProfileStatus().getExternalResourceCount()) != null) {
                cell.setCellValue(Integer.valueOf(reportData.getSubmittedProfileStatus().getExternalResourceCount()));
            }
        } else {
            cell.setCellValue(Integer.valueOf(0));
        }
        cell.setCellStyle(cellStyle);
        columnIndex++;

        cell = subRow.createCell(columnIndex);
        if (reportData.getShortlistedProfileStatus() != null) {
            if (Integer.valueOf(reportData.getShortlistedProfileStatus().getExternalResourceCount()) != null) {
                cell.setCellValue(Integer.valueOf(reportData.getShortlistedProfileStatus().getExternalResourceCount()));
            }
        } else {
            cell.setCellValue(Integer.valueOf(0));
        }
        cell.setCellStyle(cellStyle);
        columnIndex++;

        cell = subRow.createCell(columnIndex);
        if (reportData.getRejectedProfileStatus() != null) {
            if (Integer.valueOf(reportData.getRejectedProfileStatus().getExternalResourceCount()) != null) {
                cell.setCellValue(Integer.valueOf(reportData.getRejectedProfileStatus().getExternalResourceCount()));
            }
        } else {
            cell.setCellValue(Integer.valueOf(0));
        }
        cell.setCellStyle(cellStyle);
        columnIndex++;

        cell = subRow.createCell(columnIndex);
        if (reportData.getClosedProfileStatus() != null) {
            if (Integer.valueOf(reportData.getClosedProfileStatus().getExternalResourceCount()) != null) {
                cell.setCellValue(Integer.valueOf(reportData.getClosedProfileStatus().getExternalResourceCount()));
            }
        } else {
            cell.setCellValue(Integer.valueOf(0));
        }
        cell.setCellStyle(cellStyle);
        columnIndex++;

        cell1 = firstRow.createCell(columnIndex1);
        if (reportData.getSubmittedProfileStatus() != null) {
            if (Integer.valueOf(reportData.getSubmittedProfileStatus().getInternalResourceCount()) != null) {
                cell1.setCellValue(Integer.valueOf(reportData.getSubmittedProfileStatus().getInternalResourceCount()));
            }
        } else {
            cell1.setCellValue(Integer.valueOf(0));
        }
        cell1.setCellStyle(cellStyle);
        columnIndex1++;

        cell1 = firstRow.createCell(columnIndex1);
        if (reportData.getShortlistedProfileStatus() != null) {
            if (Integer.valueOf(reportData.getShortlistedProfileStatus().getInternalResourceCount()) != null) {
                cell1.setCellValue(Integer.valueOf(reportData.getShortlistedProfileStatus().getInternalResourceCount()));
            }
        } else {
            cell1.setCellValue(Integer.valueOf(0));
        }
        cell1.setCellStyle(cellStyle);
        columnIndex1++;

        cell1 = firstRow.createCell(columnIndex1);
        if (reportData.getRejectedProfileStatus() != null) {
            if (Integer.valueOf(reportData.getRejectedProfileStatus().getInternalResourceCount()) != null) {
                cell1.setCellValue(Integer.valueOf(reportData.getRejectedProfileStatus().getInternalResourceCount()));
            }
        } else {
            cell1.setCellValue(Integer.valueOf(0));
        }
        cell1.setCellStyle(cellStyle);
        columnIndex1++;

        cell1 = firstRow.createCell(columnIndex1);
        if (reportData.getClosedProfileStatus() != null) {
            if (Integer.valueOf(reportData.getClosedProfileStatus().getInternalResourceCount()) != null) {
                cell1.setCellValue(Integer.valueOf(reportData.getClosedProfileStatus().getInternalResourceCount()));
            }
        } else {
            cell1.setCellValue(Integer.valueOf(0));
        }
        cell1.setCellStyle(cellStyle);
        columnIndex1++;

    }

    private void setCellStyleForMainTable(CellStyle cellStyle, Font font) {
        cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        cellStyle.setFillPattern(XSSFCellStyle.BORDER_THIN);
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        font.setFontName("Arial");
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setItalic(true);
        font.setFontHeightInPoints((short) 8);
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
    }

    private void setCellStyleForStatusCell(CellStyle cellStyle, Font font) {
        cellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        cellStyle.setFillPattern(XSSFCellStyle.BORDER_THIN);
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        font.setFontName("Arial");
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setItalic(true);
        font.setFontHeightInPoints((short) 8);
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
    }

    private void setCellStyleForTotPosCell(CellStyle cellStyle, Font font, HSSFWorkbook workbook) {

        HSSFPalette palette = workbook.getCustomPalette();
        // get the color which most closely matches the color you want to use
        HSSFColor myColor = palette.findSimilarColor(252, 213, 180);
        // get the palette index of that color
        short palIndex = myColor.getIndex();
        // code to get the style for the cell goes here
        cellStyle.setFillForegroundColor(palIndex);
        cellStyle.setFillPattern(XSSFCellStyle.BORDER_THIN);
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        font.setFontName("Arial");
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setItalic(true);
        font.setFontHeightInPoints((short) 8);
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
    }

    private void setCellStyleForOpenCell(CellStyle cellStyle, Font font, HSSFWorkbook workbook) {

        cellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());

        cellStyle.setFillPattern(XSSFCellStyle.BORDER_THIN);
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        font.setFontName("Arial");
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setItalic(true);
        font.setFontHeightInPoints((short) 8);
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
    }

    private void setSimpleCellStyle(CellStyle cellStyle, HSSFWorkbook workbook) {
        cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        cellStyle.setFillPattern(XSSFCellStyle.BORDER_THIN);
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.WHITE.index);
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
    }

    private void setCellStyleForSubTable(CellStyle cellStyle, Font font, HSSFWorkbook workbook) {

        HSSFPalette palette = workbook.getCustomPalette();
        cellStyle.setFillForegroundColor(palette.getColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex()).getIndex());
        cellStyle.setFillPattern(XSSFCellStyle.BORDER_THIN);
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        font.setFontHeightInPoints((short) 8);
        font.setFontName(FONT_CALIBRI);
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
    }

    private void setCellStyleForSubTableHeader(HSSFCellStyle cellStyle, Font font, HSSFWorkbook workbook) {

        // HSSFWorkbook hwb = new HSSFWorkbook();
        HSSFPalette palette = workbook.getCustomPalette();
        // get the color which most closely matches the color you want to use
        HSSFColor myColor = palette.findSimilarColor(225, 240, 249);
        // get the palette index of that color
        short palIndex = myColor.getIndex();
        // code to get the style for the cell goes here
        cellStyle.setFillForegroundColor(palIndex);
        cellStyle.setFillPattern(XSSFCellStyle.BORDER_THIN);
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short) 8);
        font.setFontName(FONT_CALIBRI);
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
    }

    private void setStyleForTableHeader(HSSFCellStyle xssfCellStyleForDataCellResource, HSSFWorkbook workbook) {

        HSSFColor lightGray = setCustColor(workbook, (byte) 49, (byte) 134, (byte) 155);
        xssfCellStyleForDataCellResource.setFillForegroundColor(lightGray.getIndex());
        xssfCellStyleForDataCellResource.setBorderLeft(HSSFCellStyle.NO_FILL);
        xssfCellStyleForDataCellResource.setBorderBottom(HSSFCellStyle.NO_FILL);
        xssfCellStyleForDataCellResource.setBorderTop(HSSFCellStyle.NO_FILL);
        xssfCellStyleForDataCellResource.setBorderRight(HSSFCellStyle.NO_FILL);
        xssfCellStyleForDataCellResource.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 8);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.WHITE.index);
        xssfCellStyleForDataCellResource.setFont(font);
        xssfCellStyleForDataCellResource.setWrapText(true);

    }
}
