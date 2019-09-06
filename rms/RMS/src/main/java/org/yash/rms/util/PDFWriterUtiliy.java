package org.yash.rms.util;

import java.awt.Color;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class PDFWriterUtiliy {

	private static final Logger logger = LoggerFactory.getLogger(PDFWriterUtiliy.class);

	public static void addMetaData(Document document, String title, String subject, String keywords, String author,
			String creator) {

		document.addTitle(title);
		document.addSubject(subject);
		document.addKeywords(keywords);
		document.addAuthor(author);
		document.addCreator(creator);
	}

	public static PdfPTable getHeaderTable(String headerName)
			throws MalformedURLException, IOException, DocumentException {
		ClassLoader loader1 = PDFWriterUtiliy.class.getClassLoader();

		Image img1 = Image.getInstance(loader1.getResource("images/logo.jpg"));

		PdfPTable table = new PdfPTable(3);
		table.setTotalWidth(500);
		table.setLockedWidth(true);
		table.setWidths(new float[] { 1, 3, 2 });

		PdfPCell cell;

		cell = new PdfPCell();
		cell.addElement(img1);
		cell.setRowspan(2);
		table.addCell(cell);

		Phrase phrase = new Phrase("YASH TECHNOLOGIES", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD));
		table.addCell(phrase);

		table.addCell("");

		Phrase phrase2 = new Phrase(headerName, new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD));
		table.addCell(phrase2);

		Phrase phrase3 = new Phrase("ID: YASH-TAC-001-T001 V4.0", new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD));
		table.addCell(phrase3);
		return table;
	}

	 /**
     * Construct Header of PDF File on each page. it includes yash logo, 
     * company name, and Report name in header.
     *
     * @param companyName company name (yash technology Pvt. Ltd.)
     * @param ReportName Report name which is going to be generated.
     * @return  PDFTable object which draws a table on document.
     */
	
	public static PdfPTable getHeaderTable(String companyName, String ReportName)
			throws MalformedURLException, IOException, DocumentException {

		ClassLoader loader1 = PDFWriterUtiliy.class.getClassLoader();

		Image img1 = Image.getInstance(loader1.getResource("images/logo.jpg"));
		Font boldFont = calibriBoldFont(12, new BaseColor(Color.decode("#333333")), Font.UNDEFINED);

		PdfPTable table = new PdfPTable(3);
		table.setTotalWidth(500);
		table.setLockedWidth(true);
		table.setWidths(new float[] { 1, 3, 2 });

		PdfPCell cell = null;
		cell = new PdfPCell();
		cell.addElement(img1);
		cell.setRowspan(2);
		table.addCell(cell);

		cell = new PdfPCell();
		Phrase phrase = new Phrase(companyName, boldFont);
		cell.setPaddingBottom((float) 6.0);
		cell.setPaddingLeft((float) 4.0);
		cell.setPhrase(phrase);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setColspan(2);
		table.addCell(cell);

		cell = new PdfPCell();
		Phrase phrase2 = new Phrase(ReportName, boldFont);
		cell.setPaddingBottom((float) 6.0);
		cell.setPaddingLeft((float) 4.0);
		cell.setPhrase(phrase2);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell();
		Phrase phrase3 = new Phrase("ID: YASH-TAC-001-T001 V4.0", boldFont);
		cell.setPaddingBottom((float) 6.0);
		cell.setPaddingLeft((float) 4.0);
		cell.setPhrase(phrase3);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		return table;
	}

	public static PdfPTable addTable(int coulmnCount, Font textAlignement, Map<String, String> data)
			throws DocumentException, IOException {

		PdfPTable table = new PdfPTable(coulmnCount);

		PdfPCell cell = null;

		Phrase dataValue = null;

		for (Map.Entry<String, String> entry : data.entrySet()) {
			cell = new PdfPCell();
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			dataValue = new Phrase();
			dataValue.setFont(textAlignement);
			dataValue.add(entry.getKey() + entry.getValue());

			cell.setPaddingBottom((float) 2.0);
			cell.setPhrase(dataValue);
			cell.setPaddingTop((float) 2.0);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

			cell.setRowspan(2);
			table.addCell(cell);
		}

		table.setSpacingAfter((float) 20);
		table.setTotalWidth(400);
		table.setLockedWidth(true);
		return table;
	}

	public static PdfPTable addTableWithList(int coulmnCount, Font textAlignement, Map<String, List<String>> skillData)
			throws DocumentException, IOException {

		PdfPTable table = new PdfPTable(coulmnCount);

		PdfPCell cell = null;

		Phrase dataValue = null;

		for (Map.Entry<String, List<String>> entry : skillData.entrySet()) {
			cell = new PdfPCell();
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);

			dataValue = new Phrase();
			dataValue.setFont(textAlignement);
			dataValue.add(entry.getKey() + " : " + String.join(",", entry.getValue()));

			cell.setPhrase(dataValue);
			cell.addElement(dataValue);
			// cell.addElement(createList(entry.getValue()));

			table.addCell(cell);

		}

		table.setSpacingBefore((float) 10);

		return table;
	}

	public static PdfPCell createImageCell(String path) throws DocumentException, IOException {

		Image img = Image.getInstance(path);

		PdfPCell cell = new PdfPCell(img, true);

		return cell;
	}

	public static com.itextpdf.text.List createList(List<String> data)
			throws BadElementException, MalformedURLException, IOException {

		com.itextpdf.text.List bulletList = new com.itextpdf.text.List();
		// bulletList.setListSymbol("\u2022");

		for (String bulletValue : data) {

			bulletList.add(new ListItem(bulletValue, new Font(FontFamily.TIMES_ROMAN, 8, Font.NORMAL)));
		}

		bulletList.setIndentationLeft(30);

		return bulletList;
	}

	public static PdfPTable addTableForHeading(int i, Font textAlignement, String headerName) {

		PdfPTable table = new PdfPTable(1);
		Paragraph para = new Paragraph(headerName, textAlignement);
		PdfPCell cell = new PdfPCell();
		cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
		cell.addElement(para);
		table.addCell(cell);
		table.setSpacingAfter((float) 20);
		table.setTotalWidth(400);
		table.setLockedWidth(true);
		return table;
	}

	public static PdfPTable getTableForData(int columnCount, Font textAlignement, Map<String, String> data) {

		PdfPTable table = new PdfPTable(2);
		for (Map.Entry<String, String> entry : data.entrySet()) {
			Paragraph para1 = new Paragraph(entry.getKey(), textAlignement);
			PdfPCell cell1 = new PdfPCell();
			cell1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			cell1.addElement(para1);
			table.addCell(cell1);

			Paragraph para2 = new Paragraph(entry.getValue(), textAlignement);
			PdfPCell cell2 = new PdfPCell();
			cell2.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			cell2.addElement(para2);
			table.addCell(cell2);
		}
		table.setSpacingAfter((float) 20);
		table.setTotalWidth(400);
		table.setLockedWidth(true);
		return table;
	}

	 /**
     * Construct Resource Metrics table on document. this table contains 
     * count of all active resources, resources join in month and year month,
     * resource left in year and left in year month, bill-able (%) with bill-able and
     * non bill-able , bill-able (%) with bill-able and
     * non bill-able excluding non-bill-able investment and  , 
     * bill-able (%) with bill-able and non bill-able excluding
     * non-bill-able investment and trainee.
     *
     * @param coulmnCount the number of column having a table.
     * @param resourceMetricsList a list of objects having above specified
     * details for a selected BG-BU
     * @param regularFont Normal Font used to draw table content.
     * @param BoldFont Bold Font used to draw table content.
     * @param month Month name for which report report is going to be fetched.
     * @param year year of report to being fetched.
     * @param concatedBG_BU selected BG-BU list
     * 
     * @author rahul.shah
     * @return  PDFTable object which draws a Resource metrics table on document.
     */
	
	public static PdfPTable addResourceMetricsTable(int coulmnCount, List<Object[]> resourceMetricsList,
			Font regularFont, Font boldFont, String month, String year, List<String> concatedBG_BU)
			throws DocumentException, IOException {

		PdfPTable table = new PdfPTable(coulmnCount);
		table.setSpacingAfter(20f);
		table.setTotalWidth(500);
		table.setLockedWidth(true);
		table.setWidths(new float[] { 2, 1 });

		Phrase dataValue = new Phrase("Resource Metrics",
				calibriRegularFont(18, new BaseColor(Color.decode("#333333")), Font.NORMAL));
		PdfPCell cell = null;
		cell = new PdfPCell();
		cell.setPhrase(dataValue);
		cell.setPaddingBottom((float) 20.0);
		cell.setPaddingLeft((float) 4.0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.setColspan(coulmnCount);
		table.addCell(cell);

		Font headaingBoldFont = calibriBoldFont(10, new BaseColor(Color.decode("#eeeeee")), Font.UNDEFINED);

		cell = addHeadingCellLeftJustified(headaingBoldFont, "Label", "#468dcb");
		cell.setPaddingLeft(10f);
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingBoldFont, "Value", "#468dcb");
		cell.setBorder(0);
		table.addCell(cell);

		DecimalFormat df2 = new DecimalFormat("###.##");
		int count = 0;
		int totalNumberOfActiveResources = 0;
		int joinInYear = 0;
		int joinInYearMonth = 0;
		int leftInYear = 0;
		int leftInYearMonth = 0;
		double yearMonthAttrition = 0;
		int yearMonthAttritionAvg = 0;
		double billableNonBillable = 0;
		int billableNonBillableAvg = 0;
		double billableNonBillableExceptInv = 0;
		int billableNonBillableExceptInvAvg = 0;
		double billableNonBillableExceptInvTrainee = 0;
		int billableNonBillableExceptInvTraineeAvg = 0;
		String temp = "";

		List<Object[]> localResourceMetricsList = new LinkedList<Object[]>();

		if (resourceMetricsList != null && !resourceMetricsList.isEmpty()) {
			for (int i = 0; i < resourceMetricsList.size(); i++) {
				Object[] obj = (Object[]) resourceMetricsList.get(i);
				if (concatedBG_BU.contains(obj[0].toString() + "-" + obj[1].toString()))
					localResourceMetricsList.add(obj);
			}
		}

		if (!localResourceMetricsList.isEmpty()) {
			for (int i = 0; i < localResourceMetricsList.size(); i++) {
				Object[] obj = (Object[]) localResourceMetricsList.get(i);
				for (int j = 0; j < obj.length; j++) {
					if (j >= 2 && j <= 6) {
						if (j == 2)
							totalNumberOfActiveResources = totalNumberOfActiveResources
									+ Integer.parseInt(obj[j].toString());
						else if (j == 3)
							joinInYear = joinInYear + Integer.parseInt(obj[j].toString());
						else if (j == 4)
							joinInYearMonth = joinInYearMonth + Integer.parseInt(obj[j].toString());
						else if (j == 5)
							leftInYear = leftInYear + Integer.parseInt(obj[j].toString());
						else if (j == 6)
							leftInYearMonth = leftInYearMonth + Integer.parseInt(obj[j].toString());
					} else if (j >= 7) {
						if (j == 7) {
							temp = (obj[j] == null) ? "0" : (obj[j].toString()).replace("%", "");
							yearMonthAttrition = yearMonthAttrition + Double.parseDouble(temp);
							if (Double.parseDouble(temp) > 0)
								yearMonthAttritionAvg++;
						} else if (j == 8) {
							temp = (obj[j] == null) ? "0" : (obj[j].toString()).replace("%", "");
							billableNonBillable = billableNonBillable + Double.parseDouble(temp);
							if (Double.parseDouble(temp) > 0)
								billableNonBillableAvg++;
						} else if (j == 9) {
							temp = (obj[j] == null) ? "0" : (obj[j].toString()).replace("%", "");
							billableNonBillableExceptInv = billableNonBillableExceptInv + Double.parseDouble(temp);
							if (Double.parseDouble(temp) > 0)
								billableNonBillableExceptInvAvg++;
						} else if (j == 10) {
							temp = (obj[j] == null) ? "0" : (obj[j].toString()).replace("%", "");
							billableNonBillableExceptInvTrainee = billableNonBillableExceptInvTrainee
									+ Double.parseDouble(temp);
							if (Double.parseDouble(temp) > 0)
								billableNonBillableExceptInvTraineeAvg++;
						}
					}
				}
				count++;
			}

			Map<String, Object> data = new LinkedHashMap<String, Object>();

			data.put("Number of Active Resources", totalNumberOfActiveResources);
			data.put("Resources Joined in " + year + " (including " + month + ")", joinInYear);
			data.put("Resources Joined in " + month + " " + year, joinInYearMonth);
			data.put("Resources Left in " + year + " (including " + month + ")", leftInYear);
			data.put("Resources Left in " + month + " " + year, leftInYearMonth);
			if (totalNumberOfActiveResources > 0)
				yearMonthAttrition = (Double.valueOf(leftInYearMonth)
						/ (Double.valueOf(totalNumberOfActiveResources) + Double.valueOf(leftInYearMonth)))*100;
			data.put("Attrition in " + month + " " + year, df2.format(yearMonthAttrition) + "%");
			if (billableNonBillableAvg > 0)
				billableNonBillable = billableNonBillable / billableNonBillableAvg;
			data.put("Billable (%) (Billable and Non-Billable) ", df2.format(billableNonBillable) + "%");
			if (billableNonBillableExceptInvAvg > 0)
				billableNonBillableExceptInv = billableNonBillableExceptInv / billableNonBillableExceptInvAvg;
			data.put("Billable (%) (Billable and Non-Billable (excluding non-billable investment))",
					df2.format(billableNonBillableExceptInv) + "%");
			if (billableNonBillableExceptInvTraineeAvg > 0)
				billableNonBillableExceptInvTrainee = billableNonBillableExceptInvTrainee
						/ billableNonBillableExceptInvTraineeAvg;
			data.put("Billable (%) (Billable and Non-Billable (excluding non billable investment and trainee))",
					df2.format(billableNonBillableExceptInvTrainee) + "%");

			for (Map.Entry<String, Object> entry : data.entrySet()) {
				PdfPCell pdfPCell = addDataCellLeftJustified(regularFont, entry.getKey(), null);
				pdfPCell.setBorderWidthLeft(1f);
				cell.setPaddingLeft(10f);
				pdfPCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
				table.addCell(pdfPCell);

				pdfPCell = addDataCellCenterJustified(regularFont, entry.getValue().toString(), null);
				pdfPCell.setBorderWidthRight(1f);
				pdfPCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
				table.addCell(pdfPCell);
			}
		}

		if (localResourceMetricsList.isEmpty() && count == 0)
			table.addCell(dataNotAvailableCell(coulmnCount, regularFont));

		return table;
	}

	
	 /**
     * Constructs RRF Table. This table shows Resource Required for a project 
     * weather internal or external resource. also show how many vacancy required ,
     * how many are sill position opened, how many closed, hold, not fit, Lost
     * and how many resource submission has been done.
     *
     * @param coulmnCount the number of column having a table.
     * @param regularFont Normal Font used to draw table content.
     * @param BoldFont Bold Font used to draw table content.
     * @param resourceMetricsList a list of objects having above specified
     * details for a selected BG-BU
     * @param month Month name for which report report is going to be fetched.
     * @param year year of report to being fetched.
     * @param concatedBG_BU selected BG-BU list
     * 
     * @author rahul.shah
     * 
     * @return  PDFTable object which draws a Resource metrics table on document.
     */
	
	public static PdfPTable addRRFMetricsTable(int coulmnCount, Font regularFont, Font boldFont,
			List<Object[]> rrfMetricsList, String monthName, String year, List<String> concatedBG_BU)
			throws DocumentException, IOException {

		PdfPTable table = new PdfPTable(coulmnCount);
		table.setSpacingAfter(5f);
		table.setTotalWidth(500);
		table.setLockedWidth(true);
		table.setWidths(new float[] { .8f, .8f, 1f, 1f, 1f, 1f, 1f, 1f, 1f });
		table.setHeaderRows(1);

		Font headaingBoldFont = calibriBoldFont(10, new BaseColor(Color.decode("#eeeeee")), Font.UNDEFINED);

		PdfPCell cell = addHeadingCellCenterJustified(headaingBoldFont, "BG", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingBoldFont, "BU", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingBoldFont, "Total Position", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingBoldFont, "Position Open", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingBoldFont, "Position Closed", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingBoldFont, "Resource Not Fit", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingBoldFont, "Resource on Hold", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingBoldFont, "Resource Lost", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingBoldFont, "Submission", "#468dcb");
		cell.setBorder(0);
		table.addCell(cell);

		int count = 0;

		List<Object[]> localRRFList = null;

		if (concatedBG_BU != null && rrfMetricsList != null && !concatedBG_BU.isEmpty() && !rrfMetricsList.isEmpty()) {

			localRRFList = new LinkedList<Object[]>();

			for (int i = 0; i < rrfMetricsList.size(); i++) {
				Object[] obj = (Object[]) rrfMetricsList.get(i);
				if (concatedBG_BU.contains(obj[0].toString()))
					localRRFList.add(obj);
			}

			int grandTotalPosition = 0;
			int grandOpen = 0;
			int grandClose = 0;
			int grandSubmission = 0;
			int grandNotFit = 0;
			int grandOnHold = 0;
			int grandLost = 0;

			if (localRRFList.size() > 0) {
				for (int i = 0; i < localRRFList.size(); i++) {
					count++;
					Object[] obj = localRRFList.get(i);
					for (int j = 0; j < obj.length; j++) {
						if (j == 0) {
							String[] BGBU = obj[j].toString().split("-");
							StringBuilder BG = new StringBuilder("");
							StringBuilder BU = new StringBuilder("");
							if (BGBU.length < 3) {
								BG.append(BGBU[0]);
								BU.append(BGBU[1]);
							} else {
								BG.append(BGBU[0]).append("-").append(BGBU[1]);
								BU = concateBUs(BGBU, "-", 2);
							}
							PdfPCell pCell = addDataCellCenterJustified(regularFont, BG.toString(), null);
							pCell.setBorderWidthLeft(1f);
							pCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
							table.addCell(pCell);
							table.addCell(addDataCellCenterJustified(regularFont, BU.toString(), null));
						}

						else {
							if (obj[j] == null) {
								PdfPCell pCell = addDataCellCenterJustified(regularFont, "0", null);
								if (j == 7) {
									pCell.setBorderWidthRight(1f);
									pCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
								}
								table.addCell(pCell);
							} else {
								PdfPCell pCell = addDataCellCenterJustified(regularFont, obj[j].toString(), null);
								if (j == 7) {
									pCell.setBorderWidthRight(1f);
									pCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
								}
								table.addCell(pCell);
								if (j == 1)
									grandTotalPosition = grandTotalPosition + Integer.parseInt(obj[j].toString());
								else if (j == 2)
									grandOpen = grandOpen + Integer.parseInt(obj[j].toString());
								else if (j == 3)
									grandClose = grandClose + Integer.parseInt(obj[j].toString());
								else if (j == 4)
									grandNotFit = grandNotFit + Integer.parseInt(obj[j].toString());
								else if (j == 5)
									grandOnHold = grandOnHold + Integer.parseInt(obj[j].toString());
								else if (j == 6)
									grandLost = grandLost + Integer.parseInt(obj[j].toString());
								else if (j == 7)
									grandSubmission = grandSubmission + Integer.parseInt(obj[j].toString());
							}
						}

					}
				}

				/*
				 * adding cells in table to represent grand total of all
				 * individual columns.
				 */

				PdfPCell colorCell = addDataCellCenterJustified(boldFont, "Grand Total", "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				colorCell.setBorderWidthLeft(1f);
				colorCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, "", "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, String.valueOf(grandTotalPosition), "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, String.valueOf(grandOpen), "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, String.valueOf(grandClose), "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, String.valueOf(grandNotFit), "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, String.valueOf(grandOnHold), "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, String.valueOf(grandLost), "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, String.valueOf(grandSubmission), "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				colorCell.setBorderWidthRight(1f);
				colorCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
				table.addCell(colorCell);
			}
		}

		if (count == 0)
			table.addCell(dataNotAvailableCell(coulmnCount, regularFont));
		return table;
	}

	public static PdfPTable addResourceMetricsByBGBUTable(int coulmnCount, Font regularFont, Font boldFont,
			List<Object[]> resourceMetricsByBGList, List<String> concatedBG_BU, String year, String monthName)
			throws DocumentException, IOException {

		int count = 0;
		PdfPTable table = new PdfPTable(coulmnCount);
		table.setSpacingAfter((float) 20);
		table.setTotalWidth(500);
		table.setLockedWidth(true);
		table.setWidths(new float[] { 1.3f, 1.2f, 1f, 1f, 1f, 1f, 1f, 1f, 1.2f });
		table.setHeaderRows(2);

		Font headaingBoldFont = calibriBoldFont(10, new BaseColor(Color.decode("#eeeeee")), Font.UNDEFINED);

		// Top level heading started.

		PdfPCell cell = addHeadingCellCenterJustified(headaingBoldFont, "", "#005ba4");
		cell.setColspan(2);
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingBoldFont, "As on Date", "#005ba4");
		cell.setColspan(2);
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingBoldFont, "Monthly", "#005ba4");
		cell.setColspan(2);
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingBoldFont, "Yearly", "#005ba4");
		cell.setColspan(2);
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingBoldFont, "", "#005ba4");
		cell.setBorderWidthRight(.5f);
		table.addCell(cell);

		// Second Level heading started.

		cell = addHeadingCellCenterJustified(headaingBoldFont, "BG/BU", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingBoldFont, "Total Active Resources", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingBoldFont, "Resource Joined", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingBoldFont, "Resource Left", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingBoldFont, "Resource Joined", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingBoldFont, "Resource Left", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingBoldFont, "Resource Joined", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingBoldFont, "Resource Left", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingBoldFont, monthName + " " + year + " Attrition", "#468dcb");
		cell.setBorderWidthRight(.5f);
		table.addCell(cell);

		DecimalFormat df2 = new DecimalFormat("###.##");
		int numOfResource = 0;
		int joinAsOnDate = 0;
		int leftAsOnDate = 0;
		int joinedInYear = 0;
		int joinedInMonthYear = 0;
		int leftInYear = 0;
		int leftInMonthYear = 0;
		double attritionSum = 0;
		int attritionAvg = 0;

		int grandNumOfResource = 0;
		int grandJoinAsOnDate = 0;
		int grandLeftAsOnDate = 0;
		int grandJoinedInYear = 0;
		int grandJoinedInMonthYear = 0;
		int grandLeftInYear = 0;
		int grandLeftInMonthYear = 0;
		int grandAttritionAvg = 0;
		double grandAttritionSum = 0;

		String prevBG = "";
		String currentBG = "";
		String prveBGArrray[] = concatedBG_BU.get(0).split("-");

		List<Object[]> localResourceMetricsList = null;

		if (resourceMetricsByBGList != null && concatedBG_BU != null && !resourceMetricsByBGList.isEmpty()
				&& !concatedBG_BU.isEmpty()) {
			if (prveBGArrray.length < 3)
				prevBG = prveBGArrray[0];
			else
				prevBG = prveBGArrray[0] + "-" + prveBGArrray[1];

			localResourceMetricsList = new LinkedList<Object[]>();

			for (int i = 0; i < resourceMetricsByBGList.size(); i++) {
				Object[] obj = (Object[]) resourceMetricsByBGList.get(i);
				if (concatedBG_BU.contains(obj[0].toString() + "-" + obj[1].toString()))
					localResourceMetricsList.add(obj);
			}

			for (int i = 0; i < localResourceMetricsList.size(); i++) {
				Object[] obj = (Object[]) localResourceMetricsList.get(i);

				for (int j = 0; j < obj.length; j++) {
					currentBG = obj[0].toString().trim();
					if (prevBG.equalsIgnoreCase(currentBG) || prevBG.contains(currentBG)) {
						if (j == 0) {
							PdfPCell pCell = addDataCellCenterJustified(regularFont,
									obj[j].toString() + "/" + obj[j + 1].toString(), null);
							pCell.setBorderWidthLeft(1f);
							pCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
							table.addCell(pCell);
						} else if (j == 1)
							continue;
						else {
							String str = "";
							if (obj[j] == null)
								str = (j == 9) ? "0.00%" : "0";
							else
								str = obj[j].toString();

							PdfPCell pCell = addDataCellCenterJustified(regularFont, str, null);
							if (j == 2) {
								numOfResource = numOfResource + Integer.parseInt(str);
								grandNumOfResource = grandNumOfResource + Integer.parseInt(str);
							} else if (j == 3) {
								joinAsOnDate = joinAsOnDate + Integer.parseInt(str);
								grandJoinAsOnDate = grandJoinAsOnDate + Integer.parseInt(str);
							} else if (j == 4) {
								leftAsOnDate = leftAsOnDate + Integer.parseInt(str);
								grandLeftAsOnDate = grandLeftAsOnDate + Integer.parseInt(str);
							} else if (j == 5) {
								joinedInMonthYear = joinedInMonthYear + Integer.parseInt(str);
								grandJoinedInMonthYear = grandJoinedInMonthYear + Integer.parseInt(str);
							} else if (j == 6) {
								leftInMonthYear = leftInMonthYear + Integer.parseInt(str);
								grandLeftInMonthYear = grandLeftInMonthYear + Integer.parseInt(str);
							} else if (j == 7) {
								joinedInYear = joinedInYear + Integer.parseInt(str);
								grandJoinedInYear = grandJoinedInYear + Integer.parseInt(str);
							}

							else if (j == 8) {
								leftInYear = leftInYear + Integer.parseInt(str);
								grandLeftInYear = grandLeftInYear + Integer.parseInt(str);
							}

							else if (j == 9) {
								pCell.setBorderWidthRight(1f);
								pCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
								str = (str).replace("%", "");
								attritionSum = attritionSum + Double.parseDouble(str);
								grandAttritionSum = grandAttritionSum + Double.parseDouble(str);
								if (Double.parseDouble(str) > 0) {
									attritionAvg++;
									grandAttritionAvg++;
								}
							}
							table.addCell(pCell);
						}
					} else {

						PdfPCell colorCell = addDataCellCenterJustified(boldFont, prevBG + " Total", "#ececec");
						colorCell.setBorderWidthLeft(1f);
						colorCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, "" + numOfResource, "#ececec");
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, "" + joinAsOnDate, "#ececec");
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, "" + leftAsOnDate, "#ececec");
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, "" + joinedInMonthYear, "#ececec");
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, "" + leftInMonthYear, "#ececec");
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, "" + joinedInYear, "#ececec");
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, "" + leftInYear, "#ececec");
						table.addCell(colorCell);

						if (attritionAvg > 0)
							attritionSum = attritionSum / attritionAvg;

						colorCell = addDataCellCenterJustified(boldFont, df2.format(attritionSum) + "%", "#ececec");
						colorCell.setBorderWidthRight(1f);
						colorCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
						table.addCell(colorCell);

						prevBG = currentBG;

						numOfResource = joinAsOnDate = leftAsOnDate = joinedInYear = joinedInMonthYear = leftInYear = leftInMonthYear = 0;
						attritionAvg = 0;
						attritionSum = 0;

						if (j == 0) {
							PdfPCell pCell = addDataCellCenterJustified(regularFont,
									obj[j].toString() + "/" + obj[j + 1].toString(), null);
							pCell.setBorderWidthLeft(1f);
							pCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
							table.addCell(pCell);
						}
					}
				}
				count++;
			}

			if (!localResourceMetricsList.isEmpty() && count > 0) {

				PdfPCell colorCell = addDataCellCenterJustified(boldFont, prevBG + " Total", "#ececec");
				colorCell.setBorderWidthBottom(0);
				colorCell.setBorderWidthLeft(1f);
				colorCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, "" + numOfResource, "#ececec");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, "" + joinAsOnDate, "#ececec");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, "" + leftAsOnDate, "#ececec");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, "" + joinedInMonthYear, "#ececec");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, "" + leftInMonthYear, "#ececec");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, "" + joinedInYear, "#ececec");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, "" + leftInYear, "#ececec");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				if (attritionAvg > 0)
					attritionSum = attritionSum / attritionAvg;

				colorCell = addDataCellCenterJustified(boldFont, df2.format(attritionSum) + "%", "#ececec");
				colorCell.setBorderWidthBottom(0);
				colorCell.setBorderWidthRight(1f);
				colorCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
				table.addCell(colorCell);

				Font totalFontColor = calibriBoldFont(10, new BaseColor(Color.decode("#333333")), Font.UNDEFINED);

				colorCell = addDataCellCenterJustified(totalFontColor, "BG Grand Total", "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				colorCell.setBorderWidthLeft(1f);
				colorCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(totalFontColor, "" + grandNumOfResource, "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(totalFontColor, "" + grandJoinAsOnDate, "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(totalFontColor, "" + grandLeftAsOnDate, "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(totalFontColor, "" + grandJoinedInMonthYear, "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(totalFontColor, "" + grandLeftInMonthYear, "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(totalFontColor, "" + grandJoinedInYear, "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(totalFontColor, "" + grandLeftInYear, "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				if (grandAttritionAvg > 0)
					grandAttritionSum = grandAttritionSum / grandAttritionAvg;

				colorCell = addDataCellCenterJustified(totalFontColor, df2.format(grandAttritionSum) + "%", "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				colorCell.setBorderWidthRight(1f);
				colorCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
				table.addCell(colorCell);
			}
		}

		if (localResourceMetricsList == null || count == 0)
			table.addCell(dataNotAvailableCell(coulmnCount, regularFont));

		return table;
	}

	public static PdfPTable addGradewiseReportTable(int coulmnCount, Font regularFont, Font boldFont,
			List<Object[]> gradewiseReportListTemp, String monthName, String year, List<String> concatedBG_BU)
			throws DocumentException, IOException {

		PdfPTable table = new PdfPTable(coulmnCount);
		table.setSpacingAfter((float) 20);
		table.setTotalWidth(500);
		table.setLockedWidth(true);
		table.setWidths(new float[] { 1f, 1f, 1.2f, 1f, 1.2f, 1f, 1f });
		table.setHeaderRows(2);

		Font headaingBoldFont = calibriBoldFont(10, new BaseColor(Color.decode("#eeeeee")), Font.UNDEFINED);

		PdfPCell cell = addHeadingCellCenterJustified(headaingBoldFont, "", "#005ba4");
		cell.setColspan(2);
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingBoldFont, "Year " + year, "#005ba4");
		cell.setColspan(4);
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingBoldFont, "", "#005ba4");
		cell.setBorderWidthRight(0);
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingBoldFont, "Grade", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingBoldFont, "Active Resources", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingBoldFont, "Joined in " + year + " (including " + monthName + ")",
				"#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingBoldFont, "Joined in " + monthName, "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingBoldFont, "Left in " + year + " (including " + monthName + ")",
				"#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingBoldFont, "Left in " + monthName, "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingBoldFont, monthName + " Attrition", "#468dcb");
		cell.setBorderWidthRight(0);
		table.addCell(cell);

		DecimalFormat df2 = new DecimalFormat("###.##");
		int numOfResource = 0;
		int joinedInYear = 0;
		int joinedInMonthYear = 0;
		int leftInYear = 0;
		int leftInMonthYear = 0;
		double attritionSum = 0;
		double attritionAvg = 0;
		String temp = "";
		Map<String, Object[]> gradeWiseMap = new TreeMap<String, Object[]>();
		String tempGrade = "";
		int tempSum;
		double doubleSum;
		double doubleSum1;

		List<Object[]> localbenchGradeReportList = new LinkedList<Object[]>();

		if (gradewiseReportListTemp != null && !gradewiseReportListTemp.isEmpty()) {
			for (int i = 0; i < gradewiseReportListTemp.size(); i++) {
				Object[] obj = (Object[]) gradewiseReportListTemp.get(i);
				if (concatedBG_BU.contains(obj[1].toString() + "-" + obj[2].toString()))
					localbenchGradeReportList.add(obj);
			}
		}

		for (int i = 0; i < localbenchGradeReportList.size(); i++) {
			Object[] obj = (Object[]) localbenchGradeReportList.get(i);
			tempGrade = obj[0].toString();
			if (gradeWiseMap.containsKey(tempGrade)) {
				Object[] obj1 = gradeWiseMap.get(tempGrade);
				if (obj1[1] == null)
					obj1[1] = "0";
				if (obj[3] == null)
					obj[3] = "0";
				tempSum = Integer.parseInt(obj1[1].toString()) + Integer.parseInt(obj[3].toString());
				obj1[1] = tempSum + "";
				if (obj1[2] == null)
					obj1[2] = "0";
				if (obj[4] == null)
					obj[4] = "0";
				tempSum = Integer.parseInt(obj1[2].toString()) + Integer.parseInt(obj[4].toString());
				obj1[2] = tempSum + "";
				if (obj1[3] == null)
					obj1[3] = "0";
				if (obj[5] == null)
					obj[5] = "0";
				tempSum = Integer.parseInt(obj1[3].toString()) + Integer.parseInt(obj[5].toString());
				obj1[3] = tempSum + "";
				if (obj1[4] == null)
					obj1[4] = "0";
				if (obj[6] == null)
					obj[6] = "0";
				tempSum = Integer.parseInt(obj1[4].toString()) + Integer.parseInt(obj[6].toString());
				obj1[4] = tempSum + "";
				if (obj1[5] == null)
					obj1[5] = "0";
				if (obj[7] == null)
					obj[7] = "0";

				tempSum = Integer.parseInt(obj1[5].toString()) + Integer.parseInt(obj[7].toString());
				obj1[5] = tempSum + "";

				if (obj1[6] == null)
					obj1[6] = "0.00%";
				if (obj[8] == null)
					obj[8] = "0.00%";

				temp = (obj1[6].toString()).replace("%", "");
				doubleSum = Double.parseDouble(temp);
				temp = (obj[8].toString()).replace("%", "");
				doubleSum1 = Double.parseDouble(temp);

				doubleSum = doubleSum + doubleSum1;
				
				obj1[6] = doubleSum + "%";

				gradeWiseMap.put(obj[0].toString(), obj1);
			} else {
				Object obj1[] = new Object[7];
				obj1[0] = obj[0];
				obj1[1] = obj[3];
				obj1[2] = obj[4];
				obj1[3] = obj[5];
				obj1[4] = obj[6];
				obj1[5] = obj[7];
				obj1[6] = obj[8];
				gradeWiseMap.put(obj[0].toString(), obj1);
			}

		}

		List<Object[]> gradewiseReportList = new ArrayList<Object[]>();
		if (gradeWiseMap.size() > 0) {
			for (Map.Entry<String, Object[]> entry : gradeWiseMap.entrySet()) {
				gradewiseReportList.add(entry.getValue());
			}
		}

		if (gradewiseReportList.size() > 0) {
			for (int i = 0; i < gradewiseReportList.size(); i++) {
				Object[] obj = (Object[]) gradewiseReportList.get(i);

				for (int j = 0; j < obj.length; j++) {
					if (j == 0) {
						PdfPCell pCell = addDataCellCenterJustified(regularFont, obj[j].toString(), null);
						pCell.setBorderWidthLeft(1f);
						pCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
						table.addCell(pCell);
					} else {
						PdfPCell pCell = null;
						if (j == 1) {
							pCell = addDataCellCenterJustified(regularFont, obj[j].toString(), null);
							numOfResource = numOfResource + Integer.parseInt(obj[j].toString());
						} else if (j == 2) {
							pCell = addDataCellCenterJustified(regularFont, obj[j].toString(), null);
							joinedInYear = joinedInYear + Integer.parseInt(obj[j].toString());
						} else if (j == 3) {
							pCell = addDataCellCenterJustified(regularFont, obj[j].toString(), null);
							joinedInMonthYear = joinedInMonthYear + Integer.parseInt(obj[j].toString());
						} else if (j == 4) {
							pCell = addDataCellCenterJustified(regularFont, obj[j].toString(), null);
							leftInYear = leftInYear + Integer.parseInt(obj[j].toString());

						} else if (j == 5) {
							pCell = addDataCellCenterJustified(regularFont, obj[j].toString(), null);
							leftInMonthYear = leftInMonthYear + Integer.parseInt(obj[j].toString());

						} else if (j == 6) {
							double totalActiveResource = Double.parseDouble(obj[1].toString());
							double leftInMonth = Double.parseDouble(obj[5].toString());

							double attritionLocalSum = 0;
							totalActiveResource = totalActiveResource + leftInMonth;
							if (totalActiveResource > 0) {
								attritionLocalSum = (leftInMonth / (totalActiveResource)) * 100;
							}
							if (attritionLocalSum > 0)
								attritionAvg++;
							attritionSum = attritionSum + attritionLocalSum;

							pCell = addDataCellCenterJustified(regularFont, df2.format(attritionLocalSum) + "%", null);
							pCell.setBorderWidthRight(1f);
							pCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));

						}
						table.addCell(pCell);
					}
				}
				if (i == gradewiseReportList.size() - 1) {
					PdfPCell colorCell = addDataCellCenterJustified(boldFont, "Total", "#d0e2f2");
					colorCell.setBorderWidthBottom(0);
					colorCell.setBorderWidthLeft(1f);
					colorCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
					table.addCell(colorCell);

					colorCell = addDataCellCenterJustified(boldFont, "" + numOfResource, "#d0e2f2");
					colorCell.setBorderWidthBottom(0);
					table.addCell(colorCell);

					colorCell = addDataCellCenterJustified(boldFont, "" + joinedInYear, "#d0e2f2");
					colorCell.setBorderWidthBottom(0);
					table.addCell(colorCell);

					colorCell = addDataCellCenterJustified(boldFont, "" + joinedInMonthYear, "#d0e2f2");
					colorCell.setBorderWidthBottom(0);
					table.addCell(colorCell);

					colorCell = addDataCellCenterJustified(boldFont, "" + leftInYear, "#d0e2f2");
					colorCell.setBorderWidthBottom(0);
					table.addCell(colorCell);

					colorCell = addDataCellCenterJustified(boldFont, "" + leftInMonthYear, "#d0e2f2");
					colorCell.setBorderWidthBottom(0);
					table.addCell(colorCell);

					if (attritionAvg > 0)
						attritionSum = attritionSum / attritionAvg;

					colorCell = addDataCellCenterJustified(boldFont, df2.format(attritionSum) + "%", "#d0e2f2");
					colorCell.setBorderWidthBottom(0);
					colorCell.setBorderWidthRight(1f);
					colorCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
					table.addCell(colorCell);
				}
			}
		}

		else
			table.addCell(dataNotAvailableCell(coulmnCount, regularFont));

		return table;
	}

	public static PdfPTable addBGBUMetricesTable(int coulmnCount, Font regularFont, Font boldFont,
			List<Object[]> BGBUMetricesList, String monthName, String year, List<String> concatedBG_BU)
			throws DocumentException, IOException {

		int count = 0;
		PdfPTable table = new PdfPTable(coulmnCount);
		table.setSpacingAfter(5f);
		table.setTotalWidth(500);
		table.setWidths(new float[] { 1.6f, .9f, .9f, .9f, .9f, .9f, 1.3f, 1.5f, 1.6f, 2f, 1.3f, 1.3f });
		table.setLockedWidth(true);
		table.setHeaderRows(1);

		Font headaingFont = calibriBoldFont(10, new BaseColor(Color.decode("#eeeeee")), Font.UNDEFINED);

		PdfPCell cell = addHeadingCellCenterJustified(headaingFont, "BG/BU", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "AR", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "MR", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "BR", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "NBR", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "UAR", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "B (%)", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "BNB (%)", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "BNB-Ex-Inv (%)", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "BNB-Ex-Inv-Trn (%)", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "UA (%)", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "Util (%)", "#468dcb");
		cell.setBorder(0);
		table.addCell(cell);

		DecimalFormat df2 = new DecimalFormat("###.##");
		int totalNumberOfActiveResources = 0;
		int mangResource = 0;
		int billedResource = 0;
		int unbilledResource = 0;
		int unallocatedResources = 0;
		double billingSum = 0;
		double billableNonBillableSum = 0;
		double BNBExInvestSum = 0;
		double BNBExInvestTraineeSum = 0;
		double unallocateSum = 0;
		double utilizationSum = 0;
		int unallocateAvg = 0;
		int billingAvg = 0;
		int utilizationAvg = 0;
		int billableNonBillableAvg = 0;
		int BNBExInvestAvg = 0;
		int BNBExInvestTraineeAvg = 0;
		int grandTotalNumberOfActiveResources = 0;
		int grandMangResource = 0;
		int grandBilledResource = 0;
		int grandUnbilledResource = 0;
		int grandUnallocatedResources = 0;
		double grandBillingSum = 0;
		int grandBillingAvg = 0;
		double grandUtilizationSum = 0;
		int grandUtilizationAvg = 0;
		double grandUnallocatSum = 0;
		int grandUnallocatAvg = 0;
		double grandBillableNonBillableSum = 0;
		double grandBNBExInvestSum = 0;
		double grandBNBExInvestTraineeSum = 0;
		int grandBillableNonBillableAvg = 0;
		int grandBNBExInvestAvg = 0;
		int grandBNBExInvestTraineeAvg = 0;
		String temp = "";
		String prevBG = "";
		String currentBG = "";
		String prveBGArrray[] = concatedBG_BU.get(0).split("-");

		if (BGBUMetricesList.size() > 0 && concatedBG_BU.size() > 0) {
			if (prveBGArrray.length < 3)
				prevBG = prveBGArrray[0];
			else
				prevBG = prveBGArrray[0] + "-" + prveBGArrray[1];
		}

		for (int i = 0; i < BGBUMetricesList.size(); i++) {
			Object[] obj = (Object[]) BGBUMetricesList.get(i);

			for (int j = 0; j < obj.length; j++) {
				if (concatedBG_BU.contains(obj[0].toString() + "-" + obj[1].toString())) {
					currentBG = obj[0].toString();
					if (prevBG.equalsIgnoreCase(currentBG)) {
						if (j == 0) {
							PdfPCell pCell = addDataCellCenterJustified(regularFont,
									obj[j].toString() + "/" + obj[j + 1].toString(), null);
							pCell.setBorderWidthLeft(1f);
							pCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
							table.addCell(pCell);
						} else if (j == 1)
							continue;
						else {
							if (obj[j] == null) {
								String s1 = (j > 6) ? "0.00%" : "0";
								PdfPCell pCell = addDataCellCenterJustified(regularFont, s1, null);
								if (j == 12) {
									pCell.setBorderWidthRight(1f);
									pCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
								}
								table.addCell(pCell);
							} else {
								PdfPCell pCell = addDataCellCenterJustified(regularFont, obj[j].toString(), null);
								if (j == 12) {
									pCell.setBorderWidthRight(1f);
									pCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
								}
								table.addCell(pCell);

								if (j == 2) {
									totalNumberOfActiveResources = totalNumberOfActiveResources
											+ Integer.parseInt(obj[j].toString());
									grandTotalNumberOfActiveResources = grandTotalNumberOfActiveResources
											+ Integer.parseInt(obj[j].toString());
								}
								if (j == 3) {
									mangResource = mangResource + Integer.parseInt(obj[j].toString());
									grandMangResource = grandMangResource + Integer.parseInt(obj[j].toString());
								}
								if (j == 4) {
									billedResource = billedResource + Integer.parseInt(obj[j].toString());
									grandBilledResource = grandBilledResource + Integer.parseInt(obj[j].toString());
								}
								if (j == 5) {
									unbilledResource = unbilledResource + Integer.parseInt(obj[j].toString());
									grandUnbilledResource = grandUnbilledResource + Integer.parseInt(obj[j].toString());
								}
								if (j == 6) {
									unallocatedResources = unallocatedResources + Integer.parseInt(obj[j].toString());
									grandUnallocatedResources = grandUnallocatedResources
											+ Integer.parseInt(obj[j].toString());
								}

								if (j == 7) {
									temp = (obj[j].toString()).replace("%", "");
									billingSum = billingSum + Double.parseDouble(temp);
									grandBillingSum = grandBillingSum + Double.parseDouble(temp);
									if (Double.parseDouble(temp) > 0) {
										billingAvg++;
										grandBillingAvg++;
									}
								}

								if (j == 8) {
									temp = (obj[j].toString()).replace("%", "");
									billableNonBillableSum = billableNonBillableSum + Double.parseDouble(temp);
									grandBillableNonBillableSum = grandBillableNonBillableSum
											+ Double.parseDouble(temp);
									if (Double.parseDouble(temp) > 0) {
										billableNonBillableAvg++;
										grandBillableNonBillableAvg++;
									}
								}

								if (j == 9) {
									temp = (obj[j].toString()).replace("%", "");
									BNBExInvestSum = BNBExInvestSum + Double.parseDouble(temp);
									grandBNBExInvestSum = grandBNBExInvestSum + Double.parseDouble(temp);
									if (Double.parseDouble(temp) > 0) {
										BNBExInvestAvg++;
										grandBNBExInvestAvg++;
									}
								}

								if (j == 10) {
									temp = (obj[j].toString()).replace("%", "");
									BNBExInvestTraineeSum = BNBExInvestTraineeSum + Double.parseDouble(temp);
									grandBNBExInvestTraineeSum = grandBNBExInvestTraineeSum + Double.parseDouble(temp);
									if (Double.parseDouble(temp) > 0) {
										BNBExInvestTraineeAvg++;
										grandBNBExInvestTraineeAvg++;
									}
								}

								if (j == 11) {
									temp = (obj[j].toString()).replace("%", "");
									unallocateSum = unallocateSum + Double.parseDouble(temp);
									grandUnallocatSum = grandUnallocatSum + Double.parseDouble(temp);
									if (Double.parseDouble(temp) > 0) {
										unallocateAvg++;
										grandUnallocatAvg++;
									}
								}
								if (j == 12) {
									temp = (obj[j].toString()).replace("%", "");
									utilizationSum = utilizationSum + Double.parseDouble(temp);
									grandUtilizationSum = grandUtilizationSum + Double.parseDouble(temp);
									if (Double.parseDouble(temp) > 0) {
										utilizationAvg++;
										grandUtilizationAvg++;
									}
								}
							}
						}
					} else {
						PdfPCell colorCell = addDataCellCenterJustified(boldFont, prevBG + " Total", "#ececec");
						colorCell.setBorderWidthLeft(1f);
						colorCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, "" + totalNumberOfActiveResources, "#ececec");
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, "" + mangResource, "#ececec");
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, "" + billedResource, "#ececec");
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, "" + unbilledResource, "#ececec");
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, "" + unallocatedResources, "#ececec");
						table.addCell(colorCell);

						if (billingAvg > 0)
							billingSum = billingSum / billingAvg;

						colorCell = addDataCellCenterJustified(boldFont, df2.format(billingSum) + "%", "#ececec");
						table.addCell(colorCell);

						if (billableNonBillableAvg > 0)
							billableNonBillableSum = billableNonBillableSum / billableNonBillableAvg;

						colorCell = addDataCellCenterJustified(boldFont, df2.format(billableNonBillableSum) + "%",
								"#ececec");
						table.addCell(colorCell);

						if (BNBExInvestAvg > 0)
							BNBExInvestSum = BNBExInvestSum / BNBExInvestAvg;

						colorCell = addDataCellCenterJustified(boldFont, df2.format(BNBExInvestSum) + "%", "#ececec");
						table.addCell(colorCell);

						if (BNBExInvestTraineeAvg > 0)
							BNBExInvestTraineeSum = BNBExInvestTraineeSum / BNBExInvestTraineeAvg;

						colorCell = addDataCellCenterJustified(boldFont, df2.format(BNBExInvestTraineeSum) + "%",
								"#ececec");
						table.addCell(colorCell);

						if (unallocateAvg > 0)
							unallocateSum = unallocateSum / unallocateAvg;

						colorCell = addDataCellCenterJustified(boldFont, df2.format(unallocateSum) + "%", "#ececec");
						table.addCell(colorCell);

						if (utilizationAvg > 0)
							utilizationSum = utilizationSum / utilizationAvg;

						colorCell = addDataCellCenterJustified(boldFont, df2.format(utilizationSum) + "%", "#ececec");
						colorCell.setBorderWidthRight(1f);
						colorCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
						table.addCell(colorCell);

						prevBG = currentBG;

						totalNumberOfActiveResources = mangResource = billedResource = unbilledResource = unallocatedResources = 0;
						billingSum = unallocateSum = utilizationSum = 0;
						billingAvg = utilizationAvg = unallocateAvg = 0;
						billableNonBillableSum = BNBExInvestSum = BNBExInvestTraineeSum = 0;
						billableNonBillableAvg = BNBExInvestAvg = BNBExInvestTraineeAvg = 0;

						if (j == 0) {
							PdfPCell pCell = addDataCellCenterJustified(regularFont,
									obj[j].toString() + "/" + obj[j + 1].toString(), null);
							pCell.setBorderWidthLeft(1f);
							pCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
							table.addCell(pCell);
						} else {

							if (obj[j] == null) {
								String s1 = (j > 6) ? "0.00%" : "0";
								PdfPCell pCell = addDataCellCenterJustified(regularFont, s1, null);
								if (j == 12) {
									pCell.setBorderWidthRight(1f);
									pCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
								}
								table.addCell(pCell);
							} else {
								table.addCell(addDataCellCenterJustified(regularFont, obj[j].toString(), null));
							}
						}
					}
					count++;
				} else
					break;
			}

			if (i == BGBUMetricesList.size() - 1 && count > 0) {
				PdfPCell colorCell = addDataCellCenterJustified(boldFont, prevBG + " Total", "#ececec");
				colorCell.setBorderWidthBottom(0);
				colorCell.setBorderWidthLeft(1f);
				colorCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, totalNumberOfActiveResources + "", "#ececec");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, "" + mangResource, "#ececec");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, "" + billedResource, "#ececec");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, "" + unbilledResource, "#ececec");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, "" + unallocatedResources, "#ececec");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				if (billingAvg > 0)
					billingSum = billingSum / billingAvg;

				colorCell = addDataCellCenterJustified(boldFont, df2.format(billingSum) + "%", "#ececec");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				if (billableNonBillableAvg > 0)
					billableNonBillableSum = billableNonBillableSum / billableNonBillableAvg;

				colorCell = addDataCellCenterJustified(boldFont, df2.format(billableNonBillableSum) + "%", "#ececec");
				table.addCell(colorCell);

				if (BNBExInvestAvg > 0)
					BNBExInvestSum = BNBExInvestSum / BNBExInvestAvg;

				colorCell = addDataCellCenterJustified(boldFont, df2.format(BNBExInvestSum) + "%", "#ececec");
				table.addCell(colorCell);

				if (BNBExInvestTraineeAvg > 0)
					BNBExInvestTraineeSum = BNBExInvestTraineeSum / BNBExInvestTraineeAvg;

				colorCell = addDataCellCenterJustified(boldFont, df2.format(BNBExInvestTraineeSum) + "%", "#ececec");
				table.addCell(colorCell);

				if (unallocateAvg > 0)
					unallocateSum = unallocateSum / unallocateAvg;

				colorCell = addDataCellCenterJustified(boldFont, df2.format(unallocateSum) + "%", "#ececec");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				if (utilizationAvg > 0)
					utilizationSum = utilizationSum / utilizationAvg;

				colorCell = addDataCellCenterJustified(boldFont, df2.format(utilizationSum) + "%", "#ececec");
				colorCell.setBorderWidthBottom(0);
				colorCell.setBorderWidthRight(1f);
				colorCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
				table.addCell(colorCell);

				// adding cells to show grand total of all units selected.

				colorCell = addDataCellCenterJustified(boldFont, "Grand Total", "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				colorCell.setBorderWidthLeft(1f);
				colorCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, grandTotalNumberOfActiveResources + "", "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, "" + grandMangResource, "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, "" + grandBilledResource, "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, "" + grandUnbilledResource, "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, "" + grandUnallocatedResources, "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				if (grandBillingAvg > 0)
					grandBillingSum = grandBillingSum / grandBillingAvg;

				colorCell = addDataCellCenterJustified(boldFont, df2.format(grandBillingSum) + "%", "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				if (grandBillableNonBillableAvg > 0)
					grandBillableNonBillableSum = grandBillableNonBillableSum / grandBillableNonBillableAvg;

				colorCell = addDataCellCenterJustified(boldFont, df2.format(grandBillableNonBillableSum) + "%",
						"#d0e2f2");
				table.addCell(colorCell);

				if (grandBNBExInvestAvg > 0)
					grandBNBExInvestSum = grandBNBExInvestSum / grandBNBExInvestAvg;

				colorCell = addDataCellCenterJustified(boldFont, df2.format(BNBExInvestSum) + "%", "#d0e2f2");
				table.addCell(colorCell);

				if (grandBNBExInvestTraineeAvg > 0)
					grandBNBExInvestTraineeSum = grandBNBExInvestTraineeSum / grandBNBExInvestTraineeAvg;

				colorCell = addDataCellCenterJustified(boldFont, df2.format(grandBNBExInvestTraineeSum) + "%",
						"#d0e2f2");
				table.addCell(colorCell);

				if (grandUnallocatAvg > 0)
					grandUnallocatSum = grandUnallocatSum / grandUnallocatAvg;

				colorCell = addDataCellCenterJustified(boldFont, df2.format(grandUnallocatSum) + "%", "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				if (grandUtilizationAvg > 0)
					grandUtilizationSum = grandUtilizationSum / grandUtilizationAvg;

				colorCell = addDataCellCenterJustified(boldFont, df2.format(grandUtilizationSum) + "%", "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				colorCell.setBorderWidthRight(1f);
				colorCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
				table.addCell(colorCell);

			}
		}

		if (count == 0)
			table.addCell(dataNotAvailableCell(coulmnCount, regularFont));

		return table;
	}

	public static PdfPTable addBenchGradeReportTable(int coulmnCount, Font regularFont, Font boldFont,
			List<Object[]> benchGradeReportList, String monthName, String year, List<String> concatedBG_BU)
			throws DocumentException, IOException {

		PdfPTable table = new PdfPTable(coulmnCount);
		table.setTotalWidth(500);
		table.setWidths(new float[] { .6f, 1.1f, 1.1f, 1.1f, 1.1f });
		table.setLockedWidth(true);
		table.setSpacingAfter((float) 20);
		table.setHeaderRows(1);

		Font headaingFont = calibriBoldFont(10, new BaseColor(Color.decode("#eeeeee")), Font.UNDEFINED);

		PdfPCell cell = addHeadingCellCenterJustified(headaingFont, "Grade", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "0 to 1 month on Bench", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "1 to 2 month on Bench", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "2 to 3 month on Bench", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "More than 3 Months on Bench", "#468dcb");
		cell.setBorder(0);
		table.addCell(cell);

		int zeroToOne = 0;
		int oneToTwo = 0;
		int twoToThree = 0;
		int moreThanThree = 0;

		String tempGrade = "";
		int tempSum;

		List<Object[]> localbenchGradeReportList = new LinkedList<Object[]>();

		if (benchGradeReportList != null && !benchGradeReportList.isEmpty()) {
			for (int i = 0; i < benchGradeReportList.size(); i++) {
				Object[] obj = (Object[]) benchGradeReportList.get(i);
				if (concatedBG_BU.contains(obj[0].toString() + "-" + obj[1].toString()))
					localbenchGradeReportList.add(obj);
			}
		}

		Map<String, Object[]> gradeWiseMap = new TreeMap<String, Object[]>();

		for (int i = 0; i < localbenchGradeReportList.size(); i++) {
			Object[] obj = (Object[]) localbenchGradeReportList.get(i);
			if (concatedBG_BU.contains(obj[0].toString() + "-" + obj[1].toString())) {
				tempGrade = obj[2].toString();
				if (gradeWiseMap.containsKey(tempGrade)) {
					Object[] obj1 = gradeWiseMap.get(tempGrade);
					if (obj1[1] == null)
						obj1[1] = "0";
					if (obj[3] == null)
						obj[3] = "0";
					tempSum = Integer.parseInt(obj1[1].toString()) + Integer.parseInt(obj[3].toString());
					obj1[1] = tempSum + "";
					if (obj1[2] == null)
						obj1[2] = "0";
					if (obj[4] == null)
						obj[4] = "0";
					tempSum = Integer.parseInt(obj1[2].toString()) + Integer.parseInt(obj[4].toString());
					obj1[2] = tempSum + "";
					if (obj1[3] == null)
						obj1[3] = "0";
					if (obj[5] == null)
						obj[5] = "0";
					tempSum = Integer.parseInt(obj1[3].toString()) + Integer.parseInt(obj[5].toString());
					obj1[3] = tempSum + "";
					if (obj1[4] == null)
						obj1[4] = "0";
					if (obj[6] == null)
						obj[6] = "0";
					tempSum = Integer.parseInt(obj1[4].toString()) + Integer.parseInt(obj[6].toString());
					obj1[4] = tempSum + "";

					gradeWiseMap.put(obj1[0].toString(), obj1);
				} else {
					Object obj1[] = new Object[5];
					obj1[0] = obj[2];
					obj1[1] = obj[3];
					obj1[2] = obj[4];
					obj1[3] = obj[5];
					obj1[4] = obj[6];
					gradeWiseMap.put(obj1[0].toString(), obj1);
				}
			}
		}

		List<Object[]> gradewiseReportList = new LinkedList<Object[]>();

		if (!gradeWiseMap.isEmpty()) {
			for (Map.Entry<String, Object[]> entry : gradeWiseMap.entrySet()) {
				gradewiseReportList.add(entry.getValue());
			}
		}
		if (!gradewiseReportList.isEmpty()) {
			for (int i = 0; i < gradewiseReportList.size(); i++) {
				Object[] obj = (Object[]) gradewiseReportList.get(i);

				for (int j = 0; j < obj.length; j++) {
					if (j == 0) {
						PdfPCell pCell = addDataCellCenterJustified(regularFont, obj[j].toString(), null);
						pCell.setBorderWidthLeft(1f);
						pCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
						table.addCell(pCell);
					} else {
						if (obj[j] == null) {
							PdfPCell pCell = addDataCellCenterJustified(regularFont, "0", null);
							if (j == 4) {
								pCell.setBorderWidthRight(1f);
								pCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
							}
							table.addCell(pCell);
						} else {
							PdfPCell pCell = addDataCellCenterJustified(regularFont, obj[j].toString(), null);
							if (j == 4) {
								pCell.setBorderWidthRight(1f);
								pCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
							}
							table.addCell(pCell);
							if (j == 1)
								zeroToOne = zeroToOne + Integer.parseInt(obj[1].toString());
							if (j == 2)
								oneToTwo = oneToTwo + Integer.parseInt(obj[2].toString());
							if (j == 3)
								twoToThree = twoToThree + Integer.parseInt(obj[3].toString());
							if (j == 4)
								moreThanThree = moreThanThree + Integer.parseInt(obj[4].toString());
						}
					}
				}
			}
			PdfPCell colorCell = addDataCellCenterJustified(boldFont, "Total", "#d0e2f2");
			colorCell.setBorderWidthBottom(0);
			colorCell.setBorderWidthLeft(1f);
			colorCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
			table.addCell(colorCell);

			colorCell = addDataCellCenterJustified(boldFont, "" + zeroToOne, "#d0e2f2");
			colorCell.setBorderWidthBottom(0);
			table.addCell(colorCell);

			colorCell = addDataCellCenterJustified(boldFont, "" + oneToTwo, "#d0e2f2");
			colorCell.setBorderWidthBottom(0);
			table.addCell(colorCell);

			colorCell = addDataCellCenterJustified(boldFont, "" + twoToThree, "#d0e2f2");
			colorCell.setBorderWidthBottom(0);
			table.addCell(colorCell);

			colorCell = addDataCellCenterJustified(boldFont, "" + moreThanThree, "#d0e2f2");
			colorCell.setBorderWidthBottom(0);
			colorCell.setBorderWidthRight(1f);
			colorCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
			table.addCell(colorCell);

		} else
			table.addCell(dataNotAvailableCell(coulmnCount, regularFont));

		return table;
	}

	public static PdfPTable addBenchGradeDaysWiseReportTable(int coulmnCount, Font regularFont, Font boldFont,
			List<Object[]> benchGradeDaysWiseReportList, String monthName, String year, List<String> concatedBG_BU)
			throws DocumentException, IOException {

		PdfPTable table = new PdfPTable(coulmnCount);
		table.setSpacingAfter((float) 20);
		table.setTotalWidth(500);
		table.setLockedWidth(true);
		table.setWidths(new float[] { 1, 2, 2, 2 });
		table.setHeaderRows(1);

		Font headaingFont = calibriBoldFont(10, new BaseColor(Color.decode("#eeeeee")), Font.UNDEFINED);

		PdfPCell cell = addHeadingCellCenterJustified(headaingFont, "Grade", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "Days on Bench", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "Resources on Bench", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "Average Bench Period", "#468dcb");
		cell.setBorder(0);
		table.addCell(cell);

		int daysOnBench = 0;
		int resourcesOnBench = 0;
		int averageBenchPeriod = 0;

		String tempGrade = "";
		int tempSum;

		List<Object[]> localbenchGradeDayWiseReportList = new LinkedList<Object[]>();

		if (benchGradeDaysWiseReportList != null && !benchGradeDaysWiseReportList.isEmpty()) {
			for (int i = 0; i < benchGradeDaysWiseReportList.size(); i++) {
				Object[] obj = (Object[]) benchGradeDaysWiseReportList.get(i);
				if (concatedBG_BU.contains(obj[0].toString() + "-" + obj[1].toString()))
					localbenchGradeDayWiseReportList.add(obj);
			}
		}

		Map<String, Object[]> benchGradeDayWiseMap = new TreeMap<String, Object[]>();

		for (int i = 0; i < localbenchGradeDayWiseReportList.size(); i++) {
			Object[] obj = (Object[]) localbenchGradeDayWiseReportList.get(i);
			if (concatedBG_BU.contains(obj[0].toString() + "-" + obj[1].toString())) {
				tempGrade = obj[2].toString();
				if (benchGradeDayWiseMap.containsKey(tempGrade)) {
					Object[] obj1 = benchGradeDayWiseMap.get(tempGrade);
					if (obj1[1] == null)
						obj1[1] = "0";
					if (obj[3] == null)
						obj[3] = "0";
					tempSum = Integer.parseInt(obj1[1].toString()) + Integer.parseInt(obj[3].toString());
					obj1[1] = tempSum + "";
					if (obj1[2] == null)
						obj1[2] = "0";
					if (obj[4] == null)
						obj[4] = "0";
					tempSum = Integer.parseInt(obj1[2].toString()) + Integer.parseInt(obj[4].toString());
					obj1[2] = tempSum + "";
					if (obj1[3] == null)
						obj1[3] = "0";
					if (obj[5] == null)
						obj[5] = "0";
					tempSum = Integer.parseInt(obj1[3].toString()) + Integer.parseInt(obj[5].toString());
					obj1[3] = tempSum + "";

					benchGradeDayWiseMap.put(obj1[0].toString(), obj1);
				} else {
					Object obj1[] = new Object[4];
					obj1[0] = obj[2];
					obj1[1] = obj[3];
					obj1[2] = obj[4];
					obj1[3] = obj[5];
					benchGradeDayWiseMap.put(obj1[0].toString(), obj1);
				}
			}
		}

		List<Object[]> gradeDayWiseReportList = new LinkedList<Object[]>();

		if (!benchGradeDayWiseMap.isEmpty()) {
			for (Map.Entry<String, Object[]> entry : benchGradeDayWiseMap.entrySet()) {
				gradeDayWiseReportList.add(entry.getValue());
			}
		}

		if (!gradeDayWiseReportList.isEmpty()) {
			for (int i = 0; i < gradeDayWiseReportList.size(); i++) {

				Object[] obj = (Object[]) gradeDayWiseReportList.get(i);

				for (int j = 0; j < obj.length; j++) {
					if (j == 0) {
						PdfPCell pCell = addDataCellCenterJustified(regularFont, obj[j].toString(), null);
						pCell.setBorderWidthLeft(1f);
						pCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
						table.addCell(pCell);
					} else {
						if (obj[j] == null) {
							PdfPCell pCell = addDataCellCenterJustified(regularFont, "0", null);
							if (j == 3) {
								pCell.setBorderWidthRight(1f);
								pCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
							}
							table.addCell(pCell);
						} else {
							PdfPCell pCell = addDataCellCenterJustified(regularFont, obj[j].toString(), null);
							if (j == 3) {
								pCell.setBorderWidthRight(1f);
								pCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
							}
							table.addCell(pCell);
							if (j == 1)
								daysOnBench = daysOnBench + Integer.parseInt(obj[1].toString());
							if (j == 2)
								resourcesOnBench = resourcesOnBench + Integer.parseInt(obj[2].toString());
							if (j == 3)
								averageBenchPeriod = averageBenchPeriod + Integer.parseInt(obj[3].toString());
						}
					}
				}
			}
			PdfPCell colorCell = addDataCellCenterJustified(boldFont, "Total", "#d0e2f2");
			colorCell.setBorderWidthBottom(0);
			colorCell.setBorderWidthLeft(1f);
			colorCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
			table.addCell(colorCell);

			colorCell = addDataCellCenterJustified(boldFont, "" + daysOnBench, "#d0e2f2");
			colorCell.setBorderWidthBottom(0);
			table.addCell(colorCell);

			colorCell = addDataCellCenterJustified(boldFont, "" + resourcesOnBench, "#d0e2f2");
			colorCell.setBorderWidthBottom(0);
			table.addCell(colorCell);

			colorCell = addDataCellCenterJustified(boldFont, "" + averageBenchPeriod, "#d0e2f2");
			colorCell.setBorderWidthBottom(0);
			colorCell.setBorderWidthRight(1f);
			colorCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
			table.addCell(colorCell);

		} else
			table.addCell(dataNotAvailableCell(coulmnCount, regularFont));

		return table;
	}

	public static PdfPTable addBenchSkillReportTable(int coulmnCount, Font regularFont, Font boldFont,
			List<Object[]> benchSkillReportList, String monthName, String year, List<String> concatedBG_BU)
			throws DocumentException, IOException {
		int count = 0;
		PdfPTable table = new PdfPTable(coulmnCount);
		table.setSpacingAfter((float) 20);
		table.setTotalWidth(500);
		table.setLockedWidth(true);
		table.setWidths(new float[] { 1.5f, 1.5f, 2.5f, 1.7f, 1.7f, 1.7f, 2f });
		table.setHeaderRows(1);

		Font headaingFont = calibriBoldFont(10, new BaseColor(Color.decode("#eeeeee")), Font.UNDEFINED);

		PdfPCell cell = addHeadingCellCenterJustified(headaingFont, "BG", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "BU", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "Skill", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "0 to 1 Month on Bench", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "1 to 2 Month on Bench", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "2 to 3 Month on Bench", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "More than 3 Months on Bench", "#468dcb");
		cell.setBorder(0);
		table.addCell(cell);

		// DecimalFormat df2 = new DecimalFormat("###.##");
		int zeroToOne = 0;
		int grandZeroToOne = 0;
		int oneToTwo = 0;
		int grandOnetoTwo = 0;
		int twoToThree = 0;
		int grandTwoToThree = 0;
		int moreThanThree = 0;
		int grandMorethanThree = 0;

		int zeroToOneSAP = 0;
		int grandZeroToOneSAP = 0;
		int oneToTwoSAP = 0;
		int grandOneToTwoSAP = 0;
		int twoToThreeSAP = 0;
		int grandTwoToThreeSAP = 0;
		int moreThanThreeSAP = 0;
		int grandMoreThanThreeSAP = 0;

		String skill = "";
		String prevBG = "";
		String prevBU = "";
		String currentBG = "";
		String currentBU = "";

		List<Object[]> localbenchSkillReportList = null;

		if (benchSkillReportList != null && concatedBG_BU != null && !benchSkillReportList.isEmpty()
				&& !concatedBG_BU.isEmpty()) {

			localbenchSkillReportList = new ArrayList<Object[]>();

			for (int i = 0; i < benchSkillReportList.size(); i++) {
				Object[] obj = (Object[]) benchSkillReportList.get(i);
				if (concatedBG_BU.contains(obj[0].toString() + "-" + obj[1].toString()))
					localbenchSkillReportList.add(obj);
			}

			if (localbenchSkillReportList.size() > 0) {
				Object[] obj2 = (Object[]) localbenchSkillReportList.get(0);
				prevBG = obj2[0].toString();
				prevBU = obj2[1].toString();

				for (int i = 0; i < localbenchSkillReportList.size(); i++) {

					Object[] obj = (Object[]) localbenchSkillReportList.get(i);

					for (int j = 0; j < obj.length; j++) {

						currentBG = obj[0].toString();
						currentBU = obj[1].toString();

						if (prevBG.equalsIgnoreCase(currentBG)) {
							if (prevBU.equalsIgnoreCase(currentBU)) {
								if (j == 0) {
									PdfPCell pCell = addDataCellCenterJustified(regularFont, obj[j].toString(), null);
									pCell.setBorderWidthLeft(1f);
									pCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
									table.addCell(pCell);
								} else {
									if (obj[j] == null) {
										PdfPCell pCell = addDataCellCenterJustified(regularFont, "0", null);
										if (j == 6) {
											pCell.setBorderWidthRight(1f);
											pCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
										}
										table.addCell(pCell);
									} else {
										PdfPCell pCell = addDataCellCenterJustified(regularFont, obj[j].toString(),
												null);
										if (j == 6) {
											pCell.setBorderWidthRight(1f);
											pCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
										}
										table.addCell(pCell);
										skill = obj[2].toString().toUpperCase();
										if (j == 3) {
											zeroToOne = zeroToOne + Integer.parseInt(obj[3].toString());
											grandZeroToOne = grandZeroToOne + Integer.parseInt(obj[3].toString());
											if (skill.contains("SAP")) {
												zeroToOneSAP = zeroToOneSAP + Integer.parseInt(obj[3].toString());
												grandZeroToOneSAP = grandZeroToOneSAP
														+ Integer.parseInt(obj[3].toString());
											}
										}
										if (j == 4) {
											oneToTwo = oneToTwo + Integer.parseInt(obj[4].toString());
											grandOnetoTwo = grandOnetoTwo + Integer.parseInt(obj[4].toString());
											if (skill.contains("SAP")) {
												oneToTwoSAP = oneToTwoSAP + Integer.parseInt(obj[4].toString());
												grandOneToTwoSAP = grandOneToTwoSAP
														+ Integer.parseInt(obj[4].toString());
											}
										}
										if (j == 5) {
											twoToThree = twoToThree + Integer.parseInt(obj[5].toString());
											grandTwoToThree = grandTwoToThree + Integer.parseInt(obj[5].toString());
											if (skill.contains("SAP")) {
												twoToThreeSAP = twoToThreeSAP + Integer.parseInt(obj[5].toString());
												grandTwoToThreeSAP = grandTwoToThreeSAP
														+ Integer.parseInt(obj[5].toString());
											}
										}
										if (j == 6) {
											moreThanThree = moreThanThree + Integer.parseInt(obj[6].toString());
											grandMorethanThree = grandMorethanThree
													+ Integer.parseInt(obj[6].toString());
											if (skill.contains("SAP")) {
												moreThanThreeSAP = moreThanThreeSAP
														+ Integer.parseInt(obj[6].toString());
												grandMoreThanThreeSAP = grandMoreThanThreeSAP
														+ Integer.parseInt(obj[6].toString());
											}
										}
									}
								}

							} else {
								if (j == 0) {
									PdfPCell pCell = addDataCellCenterJustified(regularFont, obj[j].toString(), null);
									pCell.setBorderWidthLeft(1f);
									pCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
									table.addCell(pCell);
								} else {
									if (obj[j] == null) {
										PdfPCell pCell = addDataCellCenterJustified(regularFont, "0", null);
										if (j == 6) {
											pCell.setBorderWidthRight(1f);
											pCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
										}
										table.addCell(pCell);
									} else {
										PdfPCell pCell = addDataCellCenterJustified(regularFont, obj[j].toString(),
												null);
										if (j == 6) {
											pCell.setBorderWidthRight(1f);
											pCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
										}
										table.addCell(pCell);

										skill = obj[2].toString().toUpperCase();
										if (j == 3) {
											zeroToOne = zeroToOne + Integer.parseInt(obj[3].toString());
											grandZeroToOne = grandZeroToOne + Integer.parseInt(obj[3].toString());
											if (skill.contains("SAP")) {
												zeroToOneSAP = zeroToOneSAP + Integer.parseInt(obj[3].toString());
												grandZeroToOneSAP = grandZeroToOneSAP
														+ Integer.parseInt(obj[3].toString());
											}
										}
										if (j == 4) {
											oneToTwo = oneToTwo + Integer.parseInt(obj[4].toString());
											grandOnetoTwo = grandOnetoTwo + Integer.parseInt(obj[4].toString());
											if (skill.contains("SAP")) {
												oneToTwoSAP = oneToTwoSAP + Integer.parseInt(obj[4].toString());
												grandOneToTwoSAP = grandOneToTwoSAP
														+ Integer.parseInt(obj[4].toString());
											}
										}
										if (j == 5) {
											twoToThree = twoToThree + Integer.parseInt(obj[5].toString());
											grandTwoToThree = grandTwoToThree + Integer.parseInt(obj[5].toString());
											if (skill.contains("SAP")) {
												twoToThreeSAP = twoToThreeSAP + Integer.parseInt(obj[5].toString());
												grandTwoToThreeSAP = grandTwoToThreeSAP
														+ Integer.parseInt(obj[5].toString());
											}
										}
										if (j == 6) {
											moreThanThree = moreThanThree + Integer.parseInt(obj[6].toString());
											grandMorethanThree = grandMorethanThree
													+ Integer.parseInt(obj[6].toString());
											if (skill.contains("SAP")) {
												moreThanThreeSAP = moreThanThreeSAP
														+ Integer.parseInt(obj[6].toString());
												grandMoreThanThreeSAP = grandMoreThanThreeSAP
														+ Integer.parseInt(obj[6].toString());
											}
										}
									}
								}
							}

						} else {
							PdfPCell colorCell = addDataCellCenterJustified(boldFont, prevBG + " Total", "#ececec");
							colorCell.setBackgroundColor(WebColors.getRGBColor("#ececec"));
							colorCell.setBorderWidthLeft(1f);
							colorCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
							table.addCell(colorCell);

							table.addCell(addDataCellCenterJustified(boldFont, "", "#ececec"));

							table.addCell(addDataCellCenterJustified(boldFont, "", "#ececec"));

							table.addCell(addDataCellCenterJustified(boldFont, String.valueOf(zeroToOne), "#ececec"));

							table.addCell(addDataCellCenterJustified(boldFont, String.valueOf(oneToTwo), "#ececec"));

							table.addCell(addDataCellCenterJustified(boldFont, String.valueOf(twoToThree), "#ececec"));

							colorCell = addDataCellCenterJustified(boldFont, String.valueOf(moreThanThree), "#ececec");
							colorCell.setBorderWidthRight(1f);
							colorCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
							table.addCell(colorCell);

							colorCell = addDataCellCenterJustified(boldFont, prevBG + " SAP Total", "#ececec");
							colorCell.setBorderWidthLeft(1f);
							colorCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
							table.addCell(colorCell);

							table.addCell(addDataCellCenterJustified(boldFont, "", "#ececec"));

							table.addCell(addDataCellCenterJustified(boldFont, "", "#ececec"));

							table.addCell(
									addDataCellCenterJustified(boldFont, String.valueOf(zeroToOneSAP), "#ececec"));

							table.addCell(addDataCellCenterJustified(boldFont, String.valueOf(oneToTwoSAP), "#ececec"));

							table.addCell(
									addDataCellCenterJustified(boldFont, String.valueOf(twoToThreeSAP), "#ececec"));

							colorCell = addDataCellCenterJustified(boldFont, String.valueOf(moreThanThreeSAP),
									"#ececec");
							colorCell.setBorderWidthRight(1f);
							colorCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
							table.addCell(colorCell);

							prevBG = currentBG;
							prevBU = currentBU = obj[1].toString();

							zeroToOne = 0;
							oneToTwo = 0;
							twoToThree = 0;
							moreThanThree = 0;

							zeroToOneSAP = 0;
							oneToTwoSAP = 0;
							twoToThreeSAP = 0;
							moreThanThreeSAP = 0;

							if (j == 0) {
								PdfPCell pCell = addDataCellCenterJustified(regularFont, obj[j].toString(), null);
								pCell.setBorderWidthLeft(1f);
								pCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
								table.addCell(pCell);
							} else {
								if (obj[j] == null) {
									PdfPCell pCell = addDataCellCenterJustified(regularFont, "0", null);
									if (j == 6) {
										pCell.setBorderWidthRight(1f);
										pCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
									}
									table.addCell(pCell);
								} else {
									PdfPCell pCell = addDataCellCenterJustified(regularFont, obj[j].toString(), null);
									if (j == 6) {
										pCell.setBorderWidthRight(1f);
										pCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
									}
									table.addCell(pCell);
								}
							}
						}
					}
					count++;

					if (i == localbenchSkillReportList.size() - 1 && count > 0) {

						PdfPCell colorCell = addDataCellCenterJustified(boldFont, prevBG + " Total", "#ececec");
						colorCell.setBorderWidthLeft(1f);
						colorCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
						table.addCell(colorCell);

						table.addCell(addDataCellCenterJustified(boldFont, "", "#ececec"));

						table.addCell(addDataCellCenterJustified(boldFont, "", "#ececec"));

						table.addCell(addDataCellCenterJustified(boldFont, String.valueOf(zeroToOne), "#ececec"));

						table.addCell(addDataCellCenterJustified(boldFont, String.valueOf(oneToTwo), "#ececec"));

						table.addCell(addDataCellCenterJustified(boldFont, String.valueOf(twoToThree), "#ececec"));

						colorCell = addDataCellCenterJustified(boldFont, String.valueOf(moreThanThree), "#ececec");
						colorCell.setBorderWidthRight(1f);
						colorCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, prevBG + " SAP Total", "#ececec");
						colorCell.setBorderWidthBottom(0);
						colorCell.setBorderWidthLeft(1f);
						colorCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, "", "#ececec");
						colorCell.setBorderWidthBottom(0);
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, "", "#ececec");
						colorCell.setBorderWidthBottom(0);
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, String.valueOf(zeroToOneSAP), "#ececec");
						colorCell.setBorderWidthBottom(0);
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, String.valueOf(oneToTwoSAP), "#ececec");
						colorCell.setBorderWidthBottom(0);
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, String.valueOf(twoToThreeSAP), "#ececec");
						colorCell.setBorderWidthBottom(0);
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, String.valueOf(moreThanThreeSAP), "#ececec");
						colorCell.setBorderWidthBottom(0);
						colorCell.setBorderWidthRight(1f);
						colorCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, "Grand Total", "#d0e2f2");
						colorCell.setBorderWidthLeft(1f);
						colorCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
						colorCell.setBorderWidthBottom(0);
						colorCell.setBorderWidthBottom(.5f);
						colorCell.setBorderColorBottom(WebColors.getRGBColor("#c6c6c6"));
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, "", "#d0e2f2");
						colorCell.setBorderWidthBottom(0);
						colorCell.setBorderWidthBottom(.5f);
						colorCell.setBorderColorBottom(WebColors.getRGBColor("#c6c6c6"));
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, "", "#d0e2f2");
						colorCell.setBorderWidthBottom(0);
						colorCell.setBorderWidthBottom(.5f);
						colorCell.setBorderColorBottom(WebColors.getRGBColor("#c6c6c6"));
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, String.valueOf(grandZeroToOne), "#d0e2f2");
						colorCell.setBorderWidthBottom(0);
						colorCell.setBorderWidthBottom(.5f);
						colorCell.setBorderColorBottom(WebColors.getRGBColor("#c6c6c6"));
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, String.valueOf(grandOnetoTwo), "#d0e2f2");
						colorCell.setBorderWidthBottom(0);
						colorCell.setBorderWidthBottom(.5f);
						colorCell.setBorderColorBottom(WebColors.getRGBColor("#c6c6c6"));
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, String.valueOf(grandTwoToThree), "#d0e2f2");
						colorCell.setBorderWidthBottom(0);
						colorCell.setBorderWidthBottom(.5f);
						colorCell.setBorderColorBottom(WebColors.getRGBColor("#c6c6c6"));
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, String.valueOf(grandMorethanThree), "#d0e2f2");
						colorCell.setBorderWidthRight(1f);
						colorCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
						colorCell.setBorderWidthBottom(0);
						colorCell.setBorderWidthBottom(.5f);
						colorCell.setBorderColorBottom(WebColors.getRGBColor("#c6c6c6"));
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, "SAP Grand Total", "#d0e2f2");
						colorCell.setBorderWidthBottom(0);
						colorCell.setBorderWidthLeft(1f);
						colorCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, "", "#d0e2f2");
						colorCell.setBorderWidthBottom(0);
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, "", "#d0e2f2");
						colorCell.setBorderWidthBottom(0);
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, String.valueOf(grandZeroToOneSAP), "#d0e2f2");
						colorCell.setBorderWidthBottom(0);
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, String.valueOf(grandOneToTwoSAP), "#d0e2f2");
						colorCell.setBorderWidthBottom(0);
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, String.valueOf(grandTwoToThreeSAP), "#d0e2f2");
						colorCell.setBorderWidthBottom(0);
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, String.valueOf(grandMoreThanThreeSAP),
								"#d0e2f2");
						colorCell.setBorderWidthBottom(0);
						colorCell.setBorderWidthRight(1f);
						colorCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
						table.addCell(colorCell);
					}
				}

			}
		}

		if (localbenchSkillReportList == null || count == 0)
			table.addCell(dataNotAvailableCell(coulmnCount, regularFont));

		return table;
	}

	public static PdfPTable addProjectsClosingIn_3_MonthsReportTable(int coulmnCount, Font regularFont, Font boldFont,
			List<Object[]> projectsClosingIn_3_MonthsReportList, String monthName, String year,
			List<String> concatedBG_BU) throws DocumentException, IOException {
		int count = 0;

		PdfPTable table = new PdfPTable(coulmnCount);
		table.setSpacingAfter((float) 20);
		table.setTotalWidth(500);
		table.setLockedWidth(true);
		table.setWidths(new float[] { 1, 1, 1, 2, 2, 2 });
		table.setHeaderRows(1);

		Font headaingFont = calibriBoldFont(10, new BaseColor(Color.decode("#eeeeee")), Font.UNDEFINED);

		PdfPCell cell = addHeadingCellCenterJustified(headaingFont, "Project", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "BG", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "BU", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "Resources in Projects ending in 0-1 month", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "Resources in Projects ending in 1-2 month", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "Resources in Projects ending in 2-3 month", "#468dcb");
		cell.setBorder(0);
		table.addCell(cell);

		int resourcesInZeroToOne = 0;
		int resourcesInOneToTwo = 0;
		int resourcesInTwoToThree = 0;

		List<Object[]> localProjectsClosingIn_3_MonthsReportList = new ArrayList<Object[]>();

		for (int i = 0; i < projectsClosingIn_3_MonthsReportList.size(); i++) {
			Object[] obj = (Object[]) projectsClosingIn_3_MonthsReportList.get(i);
			if (concatedBG_BU.contains(obj[1].toString() + "-" + obj[2].toString()))
				localProjectsClosingIn_3_MonthsReportList.add(obj);
		}

		for (int i = 0; i < localProjectsClosingIn_3_MonthsReportList.size(); i++) {
			Object[] obj = (Object[]) localProjectsClosingIn_3_MonthsReportList.get(i);

			for (int j = 0; j < obj.length; j++) {
				if (concatedBG_BU.contains(obj[1].toString() + "-" + obj[2].toString())) {
					if (j == 0) {
						PdfPCell pCell = addDataCellCenterJustified(regularFont, obj[j].toString(), null);
						pCell.setBorderWidthLeft(1f);
						pCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
						table.addCell(pCell);
					} else {
						if (obj[j] == null) {
							PdfPCell pCell = addDataCellCenterJustified(regularFont, "0", null);
							if (j == 5) {
								pCell.setBorderWidthRight(1f);
								pCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
							}
							table.addCell(pCell);
						} else {
							PdfPCell pCell = addDataCellCenterJustified(regularFont, obj[j].toString(), null);
							if (j == 5) {
								pCell.setBorderWidthRight(1f);
								pCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
							}
							table.addCell(pCell);
							if (j == 3) {
								resourcesInZeroToOne = resourcesInZeroToOne + Integer.parseInt(obj[3].toString());
							}
							if (j == 4) {
								resourcesInOneToTwo = resourcesInOneToTwo + Integer.parseInt(obj[4].toString());
							}
							if (j == 5) {
								resourcesInTwoToThree = resourcesInTwoToThree + Integer.parseInt(obj[5].toString());
							}
						}
					}
					count++;
				} else
					break;
			}

			if (i == localProjectsClosingIn_3_MonthsReportList.size() - 1 && count > 0) {
				PdfPCell colorCell = addDataCellCenterJustified(boldFont, "Grand Total", "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				colorCell.setBorderWidthLeft(1f);
				colorCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, "", "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, "", "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, "" + resourcesInZeroToOne, "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, "" + resourcesInOneToTwo, "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, "" + resourcesInTwoToThree, "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				colorCell.setBorderWidthRight(1f);
				colorCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
				table.addCell(colorCell);
			}
		}

		if (count == 0)
			table.addCell(dataNotAvailableCell(coulmnCount, regularFont));

		return table;
	}

	public static PdfPTable addSkillsReleasingIn_3_MonthsReportTable(int coulmnCount, Font regularFont, Font boldFont,
			List<Object[]> skillsReleasingIn_3_MonthsReportList, String monthName, String year,
			List<String> concatedBG_BU) throws DocumentException, IOException {

		PdfPTable table = new PdfPTable(coulmnCount);
		table.setSpacingAfter((float) 20);
		table.setTotalWidth(500);
		table.setLockedWidth(true);
		table.setWidths(new float[] { 1.2f, 1.2f, 1.6f, 1.6f, 1.6f, 1.6f });
		table.setHeaderRows(1);

		Font headaingFont = calibriBoldFont(10, new BaseColor(Color.decode("#eeeeee")), Font.UNDEFINED);

		PdfPCell cell = addHeadingCellCenterJustified(headaingFont, "BG", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "BU", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "Skill", "#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "Resources on Skills getting released in 0-1 month",
				"#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "Resources on Skills getting released in 1-2 month",
				"#468dcb");
		cell.setBorderWidthRight(.5f);
		cell.setBorderColorRight(WebColors.getRGBColor("#cccccc"));
		table.addCell(cell);

		cell = addHeadingCellCenterJustified(headaingFont, "Resources on Skills getting released in 2-3 month",
				"#468dcb");
		cell.setBorder(0);
		table.addCell(cell);

		int count = 0;
		int zeroToOne = 0;
		int oneToTwo = 0;
		int twoToThree = 0;

		int grandZeroToOne = 0;
		int grandOneToTwo = 0;
		int grandTwoToThree = 0;

		String prevBG = "";
		String currentBG = "";

		List<Object[]> localSkillsReleasingIn_3_MonthsReportList = new ArrayList<Object[]>();

		for (int i = 0; i < skillsReleasingIn_3_MonthsReportList.size(); i++) {
			Object[] obj = (Object[]) skillsReleasingIn_3_MonthsReportList.get(i);
			if (concatedBG_BU.contains(obj[0].toString() + "-" + obj[1].toString()))
				localSkillsReleasingIn_3_MonthsReportList.add(obj);

		}

		if (localSkillsReleasingIn_3_MonthsReportList.size() > 0 && concatedBG_BU.size() > 0) {
			Object[] obj = (Object[]) localSkillsReleasingIn_3_MonthsReportList.get(0);
			prevBG = obj[0].toString();
		}

		for (int i = 0; i < localSkillsReleasingIn_3_MonthsReportList.size(); i++) {

			Object[] obj = (Object[]) localSkillsReleasingIn_3_MonthsReportList.get(i);

			for (int j = 0; j < obj.length; j++) {
				if (concatedBG_BU.contains(obj[0].toString() + "-" + obj[1].toString())) {
					currentBG = obj[0].toString();
					if (prevBG.equalsIgnoreCase(currentBG)) {
						if (j == 0) {
							PdfPCell pCell = addDataCellCenterJustified(regularFont, obj[j].toString(), null);
							pCell.setBorderWidthLeft(1f);
							pCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
							table.addCell(pCell);
						} else {
							String value = (obj[j] == null) ? "0" : obj[j].toString();
							PdfPCell pCell = addDataCellCenterJustified(regularFont, value, null);
							if (j == 5) {
								pCell.setBorderWidthRight(1f);
								pCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
							}
							table.addCell(pCell);
							if (j == 3) {
								zeroToOne = zeroToOne + Integer.parseInt(obj[j].toString());
								grandZeroToOne = grandZeroToOne + Integer.parseInt(obj[j].toString());
							}

							if (j == 4) {
								oneToTwo = oneToTwo + Integer.parseInt(obj[j].toString());
								grandOneToTwo = grandOneToTwo + Integer.parseInt(obj[j].toString());
							}

							if (j == 5) {
								twoToThree = twoToThree + Integer.parseInt(obj[j].toString());
								grandTwoToThree = grandTwoToThree + Integer.parseInt(obj[j].toString());
							}
						}

					} else {

						PdfPCell colorCell = addDataCellCenterJustified(boldFont, prevBG + " Total", "#ececec");
						colorCell.setBackgroundColor(WebColors.getRGBColor("#ececec"));
						colorCell.setBorderWidthLeft(1f);
						colorCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, "", "#ececec");
						colorCell.setBorderWidthBottom(0);
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, "", "#ececec");
						colorCell.setBorderWidthBottom(0);
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, "" + zeroToOne, "#ececec");
						colorCell.setBorderWidthBottom(0);
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, "" + oneToTwo, "#ececec");
						colorCell.setBorderWidthBottom(0);
						table.addCell(colorCell);

						colorCell = addDataCellCenterJustified(boldFont, "" + twoToThree, "#ececec");
						colorCell.setBorderWidthBottom(0);
						colorCell.setBorderWidthRight(1f);
						colorCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
						table.addCell(colorCell);

						prevBG = currentBG;

						zeroToOne = 0;
						oneToTwo = 0;
						twoToThree = 0;

						if (j <= 2) {
							PdfPCell pCell = addDataCellCenterJustified(regularFont, obj[j].toString(), null);
							if (j == 0) {
								pCell.setBorderWidthLeft(1f);
								pCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
							}
							table.addCell(pCell);
						} else {
							String value = (obj[j] == null) ? "0" : obj[j].toString();
							PdfPCell pCell = addDataCellCenterJustified(regularFont, value, null);
							if (j == 5) {
								pCell.setBorderWidthRight(1f);
								pCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
							}
							table.addCell(pCell);
						}
					}

					count++;
				} else
					break;
			}

			if (i == localSkillsReleasingIn_3_MonthsReportList.size() - 1 && count > 0) {
				PdfPCell colorCell = addDataCellCenterJustified(boldFont, prevBG + " Total", "#ececec");
				colorCell.setBorderWidthBottom(0);
				colorCell.setBorderWidthLeft(1f);
				colorCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, "", "#ececec");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, "", "#ececec");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, "" + zeroToOne, "#ececec");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, "" + oneToTwo, "#ececec");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, "" + twoToThree, "#ececec");
				colorCell.setBorderWidthBottom(0);
				colorCell.setBorderWidthRight(1f);
				colorCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, "Grand Total", "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				colorCell.setBorderWidthLeft(1f);
				colorCell.setBorderColorLeft(WebColors.getRGBColor("#b0b0b0"));
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, "", "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, "", "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, "" + grandZeroToOne, "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, "" + grandOneToTwo, "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				table.addCell(colorCell);

				colorCell = addDataCellCenterJustified(boldFont, "" + grandTwoToThree, "#d0e2f2");
				colorCell.setBorderWidthBottom(0);
				colorCell.setBorderWidthRight(1f);
				colorCell.setBorderColorRight(WebColors.getRGBColor("#b0b0b0"));
				table.addCell(colorCell);
			}
		}

		if (count == 0)
			table.addCell(dataNotAvailableCell(coulmnCount, regularFont));

		return table;
	}

	public static PdfPCell addHeadingCellCenterJustified(Font textAlignement, String value, String bcColor) {
		PdfPCell cell = new PdfPCell();
		Phrase dataValue = new Phrase(value, textAlignement);
		cell.setPaddingBottom((float) 6);
		cell.setPhrase(dataValue);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		if (bcColor != null) {
			cell.setBackgroundColor(WebColors.getRGBColor(bcColor));
			cell.setBorderColor(WebColors.getRGBColor(bcColor));
		}

		return cell;
	}

	public static PdfPCell addHeadingCellLeftJustified(Font textAlignement, String value, String bcColor) {
		PdfPCell cell = new PdfPCell();
		Phrase dataValue = new Phrase(value, textAlignement);
		cell.setPaddingBottom((float) 6);
		cell.setPhrase(dataValue);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		if (bcColor != null && !bcColor.isEmpty()) {
			cell.setBackgroundColor(WebColors.getRGBColor(bcColor));
			cell.setBorderColor(WebColors.getRGBColor(bcColor));
		}

		return cell;
	}

	public static PdfPCell addDataCellLeftJustified(Font textAlignement, String value, String bcColor) {
		PdfPCell cell = new PdfPCell();
		Phrase dataValue = new Phrase(value, textAlignement);
		cell.setPaddingBottom((float) 6.0);
		cell.setPaddingLeft((float) 4);
		cell.setPhrase(dataValue);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.setBorderWidthBottom(1f);
		cell.setBorderColorBottom(WebColors.getRGBColor("#dddddd"));
		if (bcColor != null && !bcColor.isEmpty())
			cell.setBackgroundColor(WebColors.getRGBColor(bcColor));
		return cell;
	}

	public static PdfPTable addRRFMetricsTableHeading(int coulmnCount, Font boldFont, String monthName, String year)
			throws DocumentException {

		PdfPTable table = new PdfPTable(coulmnCount);
		table.setTotalWidth(500);
		table.setLockedWidth(true);
		table.setWidths(new float[] { .8f, .8f, 1f, 1f, 1f, 1f, 1f, 1f, 1f });

		PdfPCell cell = new PdfPCell();
		Phrase dataValue = new Phrase("RRF Metrics",
				calibriRegularFont(18, new BaseColor(Color.decode("#333333")), Font.NORMAL));
		cell.setPhrase(dataValue);
		cell.setPaddingBottom((float) 20.0);
		cell.setPaddingLeft((float) 4.0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.setColspan(coulmnCount);
		table.addCell(cell);

		return table;
	}

	public static PdfPTable addResourceMetricsByBGBUTableHeading(int coulmnCount, Font boldFont, String monthName,
			String year) throws DocumentException {
		PdfPTable table = new PdfPTable(coulmnCount);
		table.setTotalWidth(500);
		table.setLockedWidth(true);
		table.setWidths(new float[] { 1.3f, 1.2f, 1f, 1f, 1f, 1f, 1f, 1f, 1.2f });

		PdfPCell cell = new PdfPCell();
		Phrase dataValue = new Phrase();
		dataValue.setFont(calibriRegularFont(18, new BaseColor(Color.decode("#333333")), Font.NORMAL));
		dataValue.add("RMG Report for the month of " + monthName + " " + year + " - BG BU Report");
		cell.setPhrase(dataValue);
		cell.setPaddingBottom((float) 20.0);
		cell.setPaddingLeft((float) 4.0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.setColspan(coulmnCount);
		table.addCell(cell);
		return table;
	}

	public static PdfPTable addGradewiseReportTableHeading(int coulmnCount, Font boldFont, String monthName,
			String year) throws DocumentException {
		PdfPTable table = new PdfPTable(coulmnCount);
		table.setTotalWidth(500);
		table.setLockedWidth(true);
		table.setWidths(new float[] { 1f, 1f, 1.2f, 1f, 1.2f, 1f, 1f });

		PdfPCell cell = new PdfPCell();
		Phrase dataValue = new Phrase();
		dataValue.setFont(calibriRegularFont(18, new BaseColor(Color.decode("#333333")), Font.NORMAL));
		dataValue.add("RMG Report for the month of " + monthName + " " + year + " - Gradewise Report");
		cell.setPhrase(dataValue);
		cell.setPaddingBottom((float) 20.0);
		cell.setPaddingLeft((float) 4.0);
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

		cell.setColspan(coulmnCount);
		table.addCell(cell);
		return table;
	}

	public static PdfPTable addBGBUMetricesTableHeading(int coulmnCount, Font boldFont, String monthName, String year)
			throws DocumentException {
		PdfPTable table = new PdfPTable(coulmnCount);
		table.setTotalWidth(500);
		table.setWidths(new float[] { 1.6f, .9f, .9f, .9f, .9f, .9f, 1.3f, 1.5f, 1.6f, 2f, 1.3f, 1.3f });
		table.setLockedWidth(true);

		PdfPCell cell = new PdfPCell();
		cell = new PdfPCell();
		Phrase dataValue = new Phrase();
		dataValue.setFont(calibriRegularFont(18, new BaseColor(Color.decode("#333333")), Font.NORMAL));
		dataValue.add("RMG Report for the month of " + monthName + " " + year + " - BG BU Metrics");
		cell.setPhrase(dataValue);
		cell.setPaddingBottom((float) 20.0);
		cell.setPaddingLeft((float) 4.0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.setColspan(coulmnCount);
		table.addCell(cell);
		return table;

	}

	public static PdfPTable addBenchGradeReportTableHeading(int coulmnCount, Font boldFont, String monthName,
			String year) throws DocumentException {
		PdfPTable table = new PdfPTable(coulmnCount);
		table.setTotalWidth(500);
		table.setWidths(new float[] { .6f, 1.1f, 1.1f, 1.1f, 1.1f });
		table.setLockedWidth(true);

		PdfPCell cell = null;
		Phrase dataValue = null;

		cell = new PdfPCell();
		dataValue = new Phrase();
		dataValue.setFont(calibriRegularFont(18, new BaseColor(Color.decode("#333333")), Font.NORMAL));
		dataValue.add("RMG Report for the month of " + monthName + " " + year + " - Bench Grade Report");
		cell.setPhrase(dataValue);
		cell.setPaddingBottom((float) 20.0);
		cell.setPaddingLeft((float) 4.0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.setColspan(coulmnCount);
		table.addCell(cell);
		return table;
	}

	public static PdfPTable addBenchGradeDaysWiseReportTableHeading(int coulmnCount, Font boldFont, String monthName,
			String year) throws DocumentException {
		PdfPTable table = new PdfPTable(coulmnCount);
		table.setTotalWidth(500);
		table.setLockedWidth(true);
		table.setWidths(new float[] { 1, 2, 2, 2 });

		PdfPCell cell = null;
		Phrase dataValue = null;

		cell = new PdfPCell();
		dataValue = new Phrase();
		dataValue.setFont(calibriRegularFont(18, new BaseColor(Color.decode("#333333")), Font.NORMAL));
		dataValue.add("RMG Report for the month of " + monthName + " " + year + " - Bench Grade Days-Wise Report");
		cell.setPhrase(dataValue);
		cell.setPaddingBottom((float) 20.0);
		cell.setPaddingLeft((float) 4.0);
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setColspan(4);
		table.addCell(cell);

		return table;
	}

	public static PdfPTable addProjectsClosingIn_3_MonthsReportTableHeading(int coulmnCount, Font boldFont,
			String monthName, String year) throws DocumentException {
		PdfPTable table = new PdfPTable(coulmnCount);
		table.setTotalWidth(500);
		table.setLockedWidth(true);
		table.setWidths(new float[] { 1, 1, 1, 2, 2, 2 });

		PdfPCell cell = null;
		Phrase dataValue = null;

		cell = new PdfPCell();
		dataValue = new Phrase();
		dataValue.setFont(calibriRegularFont(18, new BaseColor(Color.decode("#333333")), Font.NORMAL));
		dataValue.add("RMG Report for the month of " + monthName + " " + year
				+ " - Resources in Projects Closing in upcoming 3 Months");
		cell.setPhrase(dataValue);
		cell.setPaddingBottom((float) 20.0);
		cell.setPaddingLeft((float) 4.0);
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

		cell.setColspan(coulmnCount);
		table.addCell(cell);

		return table;
	}

	public static PdfPTable addBenchSkillReportTableHeading(int coulmnCount, Font boldFont, String monthName,
			String year) throws DocumentException {

		PdfPTable table = new PdfPTable(coulmnCount);
		table.setTotalWidth(500);
		table.setLockedWidth(true);
		table.setWidths(new float[] { 1.5f, 1.5f, 2.5f, 1.7f, 1.7f, 1.7f, 2f });

		PdfPCell cell = new PdfPCell();
		Phrase dataValue = new Phrase();
		dataValue.setFont(calibriRegularFont(18, new BaseColor(Color.decode("#333333")), Font.NORMAL));
		dataValue.add("RMG Report for the month of " + monthName + " " + year + " - Bench Skill Report");
		cell.setPhrase(dataValue);
		cell.setPaddingBottom((float) 20.0);
		cell.setPaddingLeft((float) 4.0);
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setColspan(coulmnCount);
		table.addCell(cell);

		return table;
	}

	public static PdfPTable addSkillsReleasingIn_3_MonthsReportTableHeading(int coulmnCount, Font boldFont,
			String monthName, String year) throws DocumentException {
		PdfPTable table = new PdfPTable(coulmnCount);
		table.setTotalWidth(500);
		table.setLockedWidth(true);
		table.setWidths(new float[] { 1.2f, 1.2f, 1.6f, 1.6f, 1.6f, 1.6f });

		PdfPCell cell = new PdfPCell();
		Phrase dataValue = new Phrase();
		dataValue.setFont(calibriRegularFont(18, new BaseColor(Color.decode("#333333")), Font.NORMAL));
		dataValue.add("RMG Report for the month of " + monthName + " " + year
				+ " - Resources of Skills Releasing in upcoming 3 Months");
		cell.setPhrase(dataValue);
		cell.setPaddingBottom((float) 20.0);
		cell.setPaddingLeft((float) 4.0);
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setColspan(coulmnCount);
		table.addCell(cell);

		return table;
	}

	private static PdfPCell dataNotAvailableCell(int coulmnCount, Font regularFont) {
		PdfPTable table = new PdfPTable(coulmnCount);
		PdfPCell cell = new PdfPCell();
		Phrase dataValue = new Phrase("No data available for the selected month", regularFont);
		cell.setPaddingBottom(6f);
		cell.setPaddingTop(6f);
		cell.setPhrase(dataValue);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		// cell.setBorder(PdfPCell.NO_BORDER);
		cell.setBorderWidth(1f);
		cell.setBorderColor(WebColors.getRGBColor("#dddddd"));
		cell.setBorderWidthTop(0f);
		cell.setColspan(coulmnCount);
		table.addCell(cell);

		return cell;
	}

	public static PdfPCell addDataCellCenterJustified(Font textAlignement, String value, String backgroundColor) {
		PdfPCell cell = new PdfPCell();
		Phrase dataValue = new Phrase(value, textAlignement);
		cell.setPaddingBottom((float) 6);
		cell.setPhrase(dataValue);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.setBorderWidthBottom(1f);
		cell.setBorderColorBottom(WebColors.getRGBColor("#dddddd"));
		if (backgroundColor != null && !backgroundColor.isEmpty())
			cell.setBackgroundColor(WebColors.getRGBColor(backgroundColor));

		return cell;
	}

	public static PdfPCell addDataCellLeftJustifiedForBGBUMetricsABR(Font textAlignement, String value,
			int HorAlignment, String backgroundColor) {
		PdfPCell cell = new PdfPCell();
		Phrase dataValue = new Phrase(value, textAlignement);
		cell.setPaddingBottom(6f);
		cell.setPhrase(dataValue);
		cell.setHorizontalAlignment(HorAlignment);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorder(PdfPCell.NO_BORDER);
		if (backgroundColor != null && !backgroundColor.isEmpty())
			cell.setBackgroundColor(WebColors.getRGBColor(backgroundColor));

		return cell;
	}

	public static Font calibriRegularFont(final float fontSize, final BaseColor baseColor, int fontStyle) {
		Font regularFont = null;
		try {
			FontFactory.register("/fonts/Calibri_Regular.ttf", "Calibri_Regular_Font");
			// regularFont = FontFactory.getFont("Calibri_Regular_Font",
			// fontSize, baseColor);
			regularFont = FontFactory.getFont("Calibri_Regular_Font", fontSize, fontStyle, baseColor);
		} catch (Exception ex) {
			regularFont = new Font(Font.FontFamily.TIMES_ROMAN, fontSize, Font.NORMAL, baseColor);
			logger.error("Exception Occurred while getting Font file " + ex.getMessage());
			ex.printStackTrace();
		}
		return regularFont;
	}

	public static Font calibriBoldFont(final float fontSize, final BaseColor baseColor, int fontStyle) {
		Font boldFont = null;
		try {
			FontFactory.register("/fonts/Calibri_Bold.ttf", "Calibri_Bold_Font");
			// boldFont = FontFactory.getFont("Calibri_Bold_Font", fontSize,
			// baseColor);
			boldFont = FontFactory.getFont("Calibri_Bold_Font", fontSize, fontStyle, baseColor);
		} catch (Exception ex) {
			boldFont = new Font(Font.FontFamily.TIMES_ROMAN, fontSize, Font.BOLD, baseColor);
			logger.error("Exception Occurred while getting Font file " + ex.getMessage());
			ex.printStackTrace();
		}
		return boldFont;
	}

	private static StringBuilder concateBUs(String[] s, String separator, int indexPosition) {
		StringBuilder result = new StringBuilder("");
		if (s.length > indexPosition) {
			result.append(s[indexPosition]); // start with the indexed element
			for (int i = indexPosition + 1; i < s.length; i++) {
				result = result.append(separator).append(s[i]);
			}
		}
		return result;
	}

	public static PdfPTable BGBUMetricsNote(int coulmnCount, Font regularFont, Font boldFont, String monthName,
			String year) throws DocumentException {

		PdfPTable table = new PdfPTable(coulmnCount);
		table.setSpacingAfter(20f);
		table.setTotalWidth(500);
		table.setWidths(new float[] { 1.5f, 1f, 1f, 1f, 1f, 1f, 1f, 1.4f, 1.8f, 2f, 1.2f, 1.3f });
		table.setLockedWidth(true);

		// adding abbreviation for heading

		Font regularItalic = calibriRegularFont(10, WebColors.getRGBColor("#333333"), Font.ITALIC);
		Font boldItalic = calibriRegularFont(10, WebColors.getRGBColor("#2d2d2d"), Font.BOLDITALIC);

		PdfPCell colorCell = addDataCellLeftJustifiedForBGBUMetricsABR(boldItalic, "Note : ", Element.ALIGN_LEFT, null);
		colorCell.setPaddingTop(15f);
		colorCell.setPaddingLeft(8f);
		table.addCell(colorCell);

		String abbreviation = String.format(
				"These are the abbreviations used for the above table's (BG BU Metrics) title (Starting from left to right):");
		colorCell = addDataCellLeftJustifiedForBGBUMetricsABR(regularItalic, abbreviation, Element.ALIGN_LEFT, null);
		colorCell.setColspan(coulmnCount - 1);
		colorCell.setPaddingTop(15f);
		colorCell.setPaddingLeft(-3f);
		table.addCell(colorCell);

		colorCell = addDataCellLeftJustifiedForBGBUMetricsABR(boldFont, "AR  =", Element.ALIGN_LEFT, null);
		colorCell.setPaddingLeft(8f);
		table.addCell(colorCell);

		colorCell = addDataCellLeftJustifiedForBGBUMetricsABR(regularFont, "Active Resources", Element.ALIGN_LEFT,
				null);
		colorCell.setColspan(coulmnCount - 1);
		colorCell.setPaddingLeft(-3f);
		table.addCell(colorCell);

		colorCell = addDataCellLeftJustifiedForBGBUMetricsABR(boldFont, "MR  =", Element.ALIGN_LEFT, null);
		colorCell.setPaddingLeft(8f);
		table.addCell(colorCell);

		colorCell = addDataCellLeftJustifiedForBGBUMetricsABR(regularFont, "Management Resources", Element.ALIGN_LEFT,
				null);
		colorCell.setColspan(coulmnCount - 1);
		colorCell.setPaddingLeft(-3f);
		table.addCell(colorCell);

		colorCell = addDataCellLeftJustifiedForBGBUMetricsABR(boldFont, "BR  =", Element.ALIGN_LEFT, null);
		colorCell.setPaddingLeft(8f);
		table.addCell(colorCell);

		colorCell = addDataCellLeftJustifiedForBGBUMetricsABR(regularFont, "Billable Resources", Element.ALIGN_LEFT,
				null);
		colorCell.setColspan(coulmnCount - 1);
		colorCell.setPaddingLeft(-3f);
		table.addCell(colorCell);

		colorCell = addDataCellLeftJustifiedForBGBUMetricsABR(boldFont, "NBR  =", Element.ALIGN_LEFT, null);
		colorCell.setPaddingLeft(8f);
		table.addCell(colorCell);

		colorCell = addDataCellLeftJustifiedForBGBUMetricsABR(regularFont, "Non-Billable Resources", Element.ALIGN_LEFT,
				null);
		colorCell.setColspan(coulmnCount - 1);
		colorCell.setPaddingLeft(-2f);
		table.addCell(colorCell);

		colorCell = addDataCellLeftJustifiedForBGBUMetricsABR(boldFont, "UAR  =", Element.ALIGN_LEFT, null);
		colorCell.setPaddingLeft(8f);
		table.addCell(colorCell);

		colorCell = addDataCellLeftJustifiedForBGBUMetricsABR(regularFont, "Un-Allocated Resources", Element.ALIGN_LEFT,
				null);
		colorCell.setColspan(coulmnCount - 1);
		colorCell.setPaddingLeft(-2f);
		table.addCell(colorCell);

		colorCell = addDataCellLeftJustifiedForBGBUMetricsABR(boldFont, "B (%)  =", Element.ALIGN_LEFT, null);
		colorCell.setPaddingLeft(8f);
		table.addCell(colorCell);

		colorCell = addDataCellLeftJustifiedForBGBUMetricsABR(regularFont, "Billable Resource (%)", Element.ALIGN_LEFT,
				null);
		colorCell.setColspan(coulmnCount - 1);
		table.addCell(colorCell);

		colorCell = addDataCellLeftJustifiedForBGBUMetricsABR(boldFont, "BNB (%)  =", Element.ALIGN_LEFT, null);
		colorCell.setColspan(2);
		colorCell.setPaddingLeft(8f);
		table.addCell(colorCell);

		colorCell = addDataCellLeftJustifiedForBGBUMetricsABR(regularFont,
				"Billable Resource (%) (Billable and Non-Billable)", Element.ALIGN_LEFT, null);
		colorCell.setColspan(coulmnCount - 2);
		colorCell.setPaddingLeft(-15f);
		table.addCell(colorCell);

		colorCell = addDataCellLeftJustifiedForBGBUMetricsABR(boldFont, "BNB-Ex-Inv (%) =", Element.ALIGN_LEFT, null);
		colorCell.setColspan(2);
		colorCell.setPaddingLeft(8f);
		table.addCell(colorCell);

		colorCell = addDataCellLeftJustifiedForBGBUMetricsABR(regularFont,
				"Billable Resource (%)  (Billable and Non-Billable (excluding Non-billable investment))",
				Element.ALIGN_LEFT, null);
		colorCell.setColspan(coulmnCount - 2);
		colorCell.setPaddingLeft(5f);
		table.addCell(colorCell);

		colorCell = addDataCellLeftJustifiedForBGBUMetricsABR(boldFont, "BNB-Ex-Inv-Trn (%)   =", Element.ALIGN_LEFT,
				null);
		colorCell.setColspan(3);
		colorCell.setPaddingLeft(8f);
		table.addCell(colorCell);

		colorCell = addDataCellLeftJustifiedForBGBUMetricsABR(regularFont,
				"Billable Resource (%)  (Billable and Non-Billable (excluding Non-billable investment and Trainee))",
				Element.ALIGN_LEFT, null);
		colorCell.setColspan(coulmnCount - 3);
		colorCell.setPaddingLeft(-3f);
		table.addCell(colorCell);

		colorCell = addDataCellLeftJustifiedForBGBUMetricsABR(boldFont, "UA (%)  =", Element.ALIGN_LEFT, null);
		colorCell.setPaddingLeft(8f);
		table.addCell(colorCell);

		colorCell = addDataCellLeftJustifiedForBGBUMetricsABR(regularFont, " Un-Allocation Resource (%)",
				Element.ALIGN_LEFT, null);
		colorCell.setColspan(coulmnCount - 1);
		table.addCell(colorCell);

		colorCell = addDataCellLeftJustifiedForBGBUMetricsABR(boldFont, "Util (%) =", Element.ALIGN_LEFT, null);
		colorCell.setPaddingLeft(8f);
		table.addCell(colorCell);

		colorCell = addDataCellLeftJustifiedForBGBUMetricsABR(regularFont, " Utilization Resource (%)",
				Element.ALIGN_LEFT, null);
		colorCell.setColspan(coulmnCount - 1);
		table.addCell(colorCell);

		return table;
	}

	public static PdfPTable RRFMetricsTableNote(int coulmnCount, Font regularFont, Font boldFont, String monthName,
			String year) throws DocumentException {

		PdfPTable table = new PdfPTable(coulmnCount);
		table.setSpacingAfter((float) 20);
		table.setTotalWidth(500);
		table.setLockedWidth(true);
		table.setWidths(new float[] { .7f, .7f, 1f, 1f, 1f, 1f, 1f, 1f, 1f });

		// adding abbreviation for heading

		Font regularItalic = calibriRegularFont(10, WebColors.getRGBColor("#333333"), Font.ITALIC);
		Font boldItalic = calibriRegularFont(10, WebColors.getRGBColor("#2d2d2d"), Font.BOLDITALIC);

		PdfPCell colorCell = addDataCellLeftJustifiedForBGBUMetricsABR(boldItalic, "Note : ", Element.ALIGN_CENTER,
				null);
		colorCell.setVerticalAlignment(Element.ALIGN_TOP);
		colorCell.setPaddingTop(15f);
		colorCell.setPaddingLeft(5f);
		table.addCell(colorCell);

		String abbreviation = String.format(
				"Submission Count is independent to Total Position count. It may differ. There may be any number of Resource submission.");
		colorCell = addDataCellLeftJustifiedForBGBUMetricsABR(regularItalic, abbreviation, Element.ALIGN_LEFT, null);
		colorCell.setColspan(coulmnCount - 1);
		colorCell.setVerticalAlignment(Element.ALIGN_TOP);
		colorCell.setPaddingTop(15f);
		table.addCell(colorCell);

		return table;
	}

}
