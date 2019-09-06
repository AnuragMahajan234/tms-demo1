package org.yash.rms.util;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.AbstractView;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class RMGReportPDFWriter<E> extends AbstractView {

	private static final Logger logger = LoggerFactory.getLogger(RMGReportPDFWriter.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws FileNotFoundException, Exception {

		/* startDate and endDate variable is for future reference. */

		// String startDate = (String) model.get("startDate");
		// String endDate = (String) model.get("endDate");

		String year = (String) model.get("year");
		String month = (String) model.get("monthName");
		String monthName = new DateFormatSymbols().getMonths()[Integer.parseInt(month) - 1];

		List resourceMetricsList = (List) model.get("resourceMetricsList");

		List RRFMetricsList = (List) model.get("RRFMetricsList");

		// List resourceMetricsByBGList = (List)
		// model.get("resourceMetricsByBGList");

		List resourceMetricsByBGBUList = (List) model.get("resourceMetricsByBGBUList");

		List gradewiseReportList = (List) model.get("gradewiseReportList");

		List BGBUMetricesList = (List) model.get("BGBUMetricesList");

		List benchGradeReportList = (List) model.get("benchGradeReportList");

		List benchGradeDaysWiseReportList = (List) model.get("benchGradeDaysWiseReportList");

		List benchSkillReportList = (List) model.get("benchSkillReportList");

		List projectsClosingIn_3_MonthsReportList = (List) model.get("projectsClosingIn_3_MonthsReportList");

		List skillsReleasingIn_3_MonthsReportList = (List) model.get("skillsReleasingIn_3_MonthsReportList");

		List selectedBG_BU = (List) model.get("selectedBG_BU");

		List<String> BUList = new ArrayList<String>();

		List<String> BGList = new ArrayList<String>();

		for (int i = 0; i < selectedBG_BU.size(); i++) {
			if (i % 2 == 0)
				BGList.add((String) selectedBG_BU.get(i));
			else
				BUList.add((String) selectedBG_BU.get(i));
		}

		List<String> concatedBG_BU = new ArrayList<String>();
		for (int i = 0; i < BUList.size(); i++) {
			concatedBG_BU.add(BGList.get(i) + "-" + BUList.get(i));
		}

		try {
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition",
					"attachment; filename= " + "RMGReport_" + monthName + "_" + year + ".pdf");
			Font coloumHeadingBoldFont = null;
			Font coloumContentRegularFont = null;

			try {
				FontFactory.register("/fonts/Calibri_Bold.ttf", "Calibri_Bold_Font");
				coloumHeadingBoldFont = FontFactory.getFont("Calibri_Bold_Font", 10,
						new BaseColor(Color.decode("#2d2d2d")));

				FontFactory.register("/fonts/Calibri_Regular.ttf", "Calibri_Regular_Font");

				coloumContentRegularFont = FontFactory.getFont("Calibri_Regular_Font", 10,
						new BaseColor(Color.decode("#333333")));
			} catch (Exception ex) {
				coloumHeadingBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD,
						WebColors.getRGBColor("#2d2d2d"));
				coloumContentRegularFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL,
						WebColors.getRGBColor("#2d2d2d"));
				logger.error("Exception Occurred while getting Font file " + ex.getMessage());
				ex.printStackTrace();
			}

			String reportId = "ID: YASH-TAC-001-T001 V4.0";
			String reportName = "RMG Report for " + monthName + " " + year;

			ClassLoader loader = RMGReportPDFWriter.class.getClassLoader();
			Image companyLogo = Image.getInstance(loader.getResource("images/logo.jpg"));
			companyLogo.scaleToFit(60f, 70f);

			RMGReportHeaderFooterEvent event = new RMGReportHeaderFooterEvent(reportName, reportId, companyLogo);

			Document document = new Document(PageSize.A4, 10, 10, 80, 30);
			boolean isNewPageAdded = false;

			PdfWriter pdfWriter = PdfWriter.getInstance(document, response.getOutputStream());
			pdfWriter.setPageEvent(event);
			document.open();

			// PdfPTable pdfHeaderTable = PDFWriterUtiliy.getHeaderTable("YASH
			// TECHNOLOGIES", "RMG Report");
			// document.add(pdfHeaderTable);
			
//			Below method calling generating Resource Metrics report

			PdfPTable resourceMetricsTable = PDFWriterUtiliy.addResourceMetricsTable(2, resourceMetricsList,
					coloumContentRegularFont, coloumHeadingBoldFont, monthName, year, concatedBG_BU);
			document.add(resourceMetricsTable);
			resourceMetricsTable.flushContent();

			if (spaceLeftInPage(pdfWriter, document) < 30f) {
				document.newPage();
				isNewPageAdded = true;
			} else
				isNewPageAdded = false;

//			Below method calling generating RRF Metrics report
			
			PdfPTable RRFMetricsTableHeading = PDFWriterUtiliy.addRRFMetricsTableHeading(9, coloumHeadingBoldFont,
					monthName, year);
			PdfPTable RRFMetricsTable = PDFWriterUtiliy.addRRFMetricsTable(9, coloumContentRegularFont,
					coloumHeadingBoldFont, RRFMetricsList, monthName, year, concatedBG_BU);
			if (!isNewPageAdded)
				RRFMetricsTableHeading.setSpacingBefore(20f);
			document.add(RRFMetricsTableHeading);
			document.add(RRFMetricsTable);
			
			PdfPTable RRFMetricsTableNote =  PDFWriterUtiliy.RRFMetricsTableNote(9, coloumContentRegularFont, coloumHeadingBoldFont, monthName, year);
			document.add(RRFMetricsTableNote);

			RRFMetricsTableHeading.flushContent();
			RRFMetricsTable.flushContent();
			RRFMetricsTableNote.flushContent();
			
			if (spaceLeftInPage(pdfWriter, document) < 30f) {
				document.newPage();
				isNewPageAdded = true;
			} else
				isNewPageAdded = false;
			
//			Below method calling generating Resource Metrics Report according to BG BU Unit report

			PdfPTable tableHeading = PDFWriterUtiliy.addResourceMetricsByBGBUTableHeading(9, coloumHeadingBoldFont,
					monthName, year);
			PdfPTable resourceMetricsByBGTable = PDFWriterUtiliy.addResourceMetricsByBGBUTable(9,
					coloumContentRegularFont, coloumHeadingBoldFont, resourceMetricsByBGBUList, concatedBG_BU, year,
					monthName);
			if (!isNewPageAdded)
				tableHeading.setSpacingBefore(20f);
			document.add(tableHeading);
			document.add(resourceMetricsByBGTable);
			tableHeading.flushContent();
			resourceMetricsByBGTable.flushContent();
			
			if (spaceLeftInPage(pdfWriter, document) < 30f) {
				document.newPage();
				isNewPageAdded = true;
			} else
				isNewPageAdded = false;

//			Below method calling generating BG BU Metrics Report
			
			tableHeading = PDFWriterUtiliy.addBGBUMetricesTableHeading(12, coloumHeadingBoldFont, monthName, year);
			PdfPTable BGBUMetricesTable = PDFWriterUtiliy.addBGBUMetricesTable(12, coloumContentRegularFont,
					coloumHeadingBoldFont, BGBUMetricesList, monthName, year, concatedBG_BU);
			if (!isNewPageAdded)
				tableHeading.setSpacingBefore(20f);
			document.add(tableHeading);
			document.add(BGBUMetricesTable);
			
			
			PdfPTable BGBUMetricesTableNote = PDFWriterUtiliy.BGBUMetricsNote(12, coloumContentRegularFont, coloumHeadingBoldFont, monthName, year);
			document.add(BGBUMetricesTableNote);
			
			if (spaceLeftInPage(pdfWriter, document) < 30f) {
				document.newPage();
				isNewPageAdded = true;
			} else
				isNewPageAdded = false;
			
			tableHeading.flushContent();
			BGBUMetricesTable.flushContent();
			BGBUMetricesTableNote.flushContent();
			
//			Below method calling generating Report according to grade wise.
			
			tableHeading = PDFWriterUtiliy.addGradewiseReportTableHeading(7, coloumHeadingBoldFont, monthName, year);
			PdfPTable gradewiseReportTable = PDFWriterUtiliy.addGradewiseReportTable(7, coloumContentRegularFont,
					coloumHeadingBoldFont, gradewiseReportList, monthName, year, concatedBG_BU);
			if (!isNewPageAdded)
				tableHeading.setSpacingBefore(20f);
			document.add(tableHeading);
			document.add(gradewiseReportTable);
			tableHeading.flushContent();
			gradewiseReportTable.flushContent();
			
			
			if (spaceLeftInPage(pdfWriter, document) < 30f) {
				document.newPage();
				isNewPageAdded = true;
			} else
				isNewPageAdded = false;

//			Below method calling generating Bench grade Report
			
			tableHeading = PDFWriterUtiliy.addBenchGradeReportTableHeading(5, coloumHeadingBoldFont, monthName, year);
			PdfPTable benchGradeReportTable = PDFWriterUtiliy.addBenchGradeReportTable(5, coloumContentRegularFont,
					coloumHeadingBoldFont, benchGradeReportList, monthName, year, concatedBG_BU);
			if (!isNewPageAdded)
				tableHeading.setSpacingBefore(20f);
			document.add(tableHeading);
			document.add(benchGradeReportTable);
			tableHeading.flushContent();
			benchGradeReportTable.flushContent();
			
			if (spaceLeftInPage(pdfWriter, document) < 30f) {
				document.newPage();
				isNewPageAdded = true;
			} else
				isNewPageAdded = false;

//			Below method calling generating Bench grade Report according to  grade wise.
			
			tableHeading = PDFWriterUtiliy.addBenchGradeDaysWiseReportTableHeading(4, coloumContentRegularFont,
					monthName, year);
			PdfPTable benchGradeDaysWiseReportTable = PDFWriterUtiliy.addBenchGradeDaysWiseReportTable(4,
					coloumContentRegularFont, coloumHeadingBoldFont, benchGradeDaysWiseReportList, monthName, year,
					concatedBG_BU);
			if (!isNewPageAdded)
				tableHeading.setSpacingBefore(20f);
			document.add(tableHeading);
			document.add(benchGradeDaysWiseReportTable);
			tableHeading.flushContent();
			benchGradeDaysWiseReportTable.flushContent();
			
			if (spaceLeftInPage(pdfWriter, document) < 30f) {
				document.newPage();
				isNewPageAdded = true;
			} else
				isNewPageAdded = false;

//			Below method calling generating Bench Skill Report
			
			tableHeading = PDFWriterUtiliy.addBenchSkillReportTableHeading(7, coloumHeadingBoldFont, monthName, year);
			PdfPTable benchSkillReportTable = PDFWriterUtiliy.addBenchSkillReportTable(7, coloumContentRegularFont,
					coloumHeadingBoldFont, benchSkillReportList, monthName, year, concatedBG_BU);
			if (!isNewPageAdded)
				tableHeading.setSpacingBefore(20f);
			document.add(tableHeading);
			document.add(benchSkillReportTable);
			tableHeading.flushContent();
			benchSkillReportTable.flushContent();

			// Don't delete below commented code.
			// Below code puts an additional note on report which describes
			// that This RMG Report is under development. More Table and this
			// report will going to be upload soon.

			/*
			 * Font additinalNoteFont = FontFactory.getFont("Calibri_Bold_Font",
			 * 12, new BaseColor(Color.RED)); String note =
			 * "Note : This RMG Report is under development. More Table and this report will going to be uploaded soon."
			 * ; Paragraph paragraph = new Paragraph(note, additinalNoteFont);
			 * paragraph.setAlignment(Element.ALIGN_LEFT);
			 * paragraph.setIndentationLeft(40); document.add(paragraph);
			 */

			if (spaceLeftInPage(pdfWriter, document) < 30f) {
				document.newPage();
				isNewPageAdded = true;
			} else
				isNewPageAdded = false;

//			Below method calling generating Project Closing Report which is going to be closed in upcoming 3 months.
			
			tableHeading = PDFWriterUtiliy.addProjectsClosingIn_3_MonthsReportTableHeading(6, coloumHeadingBoldFont,
					monthName, year);
			PdfPTable projectsClosingIn_3_MonthsReportTable = PDFWriterUtiliy.addProjectsClosingIn_3_MonthsReportTable(
					6, coloumContentRegularFont, coloumHeadingBoldFont, projectsClosingIn_3_MonthsReportList, monthName,
					year, concatedBG_BU);
			if (!isNewPageAdded)
				tableHeading.setSpacingBefore(20f);
			document.add(tableHeading);
			document.add(projectsClosingIn_3_MonthsReportTable);
			tableHeading.flushContent();
			projectsClosingIn_3_MonthsReportTable.flushContent();
			
			if (spaceLeftInPage(pdfWriter, document) < 30f) {
				document.newPage();
				isNewPageAdded = true;
			} else
				isNewPageAdded = false;
			
//			Below method calling generating Skilling Releasing Report which is going to be released in upcoming 3 months.
			
			tableHeading = PDFWriterUtiliy.addSkillsReleasingIn_3_MonthsReportTableHeading(6, coloumHeadingBoldFont,
					monthName, year);
			PdfPTable skillsReleasingIn_3_MonthsReportTable = PDFWriterUtiliy.addSkillsReleasingIn_3_MonthsReportTable(
					6, coloumContentRegularFont, coloumHeadingBoldFont, skillsReleasingIn_3_MonthsReportList, monthName,
					year, concatedBG_BU);
			if (!isNewPageAdded)
				projectsClosingIn_3_MonthsReportTable.setSpacingBefore(20f);
			document.add(tableHeading);
			document.add(skillsReleasingIn_3_MonthsReportTable);
			tableHeading.flushContent();
			skillsReleasingIn_3_MonthsReportTable.flushContent();

			document.close();

		} catch (DocumentException e) {
			logger.error("PDF File DocumentException Occurred  " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("PDF File Exception Occurred  " + e.getMessage());
			e.printStackTrace();
		}

	}

	private static float spaceLeftInPage(PdfWriter pdfWriter, Document document) {
		float remainingPageSpacePercent = 0f;
		try {
			float usablePageHight = document.getPageSize().getHeight() - document.bottomMargin() - document.topMargin();
			if (usablePageHight < 1)
				usablePageHight = 1;
			float remainingPageSpace = pdfWriter.getVerticalPosition(false) - document.bottomMargin();
			if (remainingPageSpace > document.bottomMargin())
				remainingPageSpacePercent = (remainingPageSpace / usablePageHight) * 100;

		} catch (Exception ex) {
			logger.error("Exception occured while calcualting Space left in page --" + ex.getMessage());
			ex.printStackTrace();
		}
		return remainingPageSpacePercent;
	}

}
